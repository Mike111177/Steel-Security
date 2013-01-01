package net.othercraft.steelsecurity.data.databases;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PriorityQueue;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;


public final class Database {
    
    private final PriorityQueue<QueueSegment> queue = new PriorityQueue<QueueSegment>();

    private final DatabaseType type;
    private final JavaPlugin plugin;
    private final String url;
    private final Logger log;

    /**
     * Connect to a MySQL database
     * 
     * @param plugin
     *            the plugin handle
     * @param host
     *            MySQL server address
     * @param database
     *            MySQL database name
     * @param user
     *            MySQL access username
     * @param password
     *            MySQL access password
     */
    public Database(final JavaPlugin plugin, final String host, final String database, final String user, final String password) {
	this.plugin = plugin;
	url = "jdbc:mysql://" + host + "/" + database + "?user=" + user + "&password=" + password;
	log = plugin.getServer().getLogger();
	type = DatabaseType.MYSQL;
	initDriver("com.mysql.jdbc.Driver");
	initQueue();
    }
    public void addToQueue(QueueSegment segment){
	queue.add(segment);
    }

    /**
     * Connect/create a SQLite database
     * 
     * @param plugin
     *            the plugin handle
     * @param filePath
     *            database storage path/name.extension
     */
    public Database(final JavaPlugin plugin, final String filePath) {
	this.plugin = plugin;
	url = "jdbc:sqlite:" + new File(filePath).getAbsolutePath();
	log = plugin.getServer().getLogger();
	type = DatabaseType.SQLLITE;
	initDriver("org.sqlite.JDBC");
	initQueue();
    }

    private void initQueue() {
	System.out.println("Schedular initiated");
	plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
	    @Override
	    public void run() {
		proccessQueue();
	    }
	}, 1, 1);
	
    }
    private void initDriver(final String driver) {
	try {
	    Class.forName(driver);
	} catch (final Exception e) {
	    log.severe("Database driver error:" + e.getMessage());
	}
    }

    /**
     * Get a string from query(), automatically checks for null.
     * 
     * @param result
     *            the returned value of query()
     * @param column
     *            the column number, starts from 1
     * @return integer value of the column
     */
    public int resultInt(ResultSet result, int column) {
	if (result == null)
	    return 0;

	try {
	    result.next();
	    int integer = result.getInt(column);
	    result.close();

	    return integer;
	} catch (SQLException e) {
	    log.severe("Database result error: " + e.getMessage());
	}

	return 0;
    }

    /**
     * Get a string from query(), automatically checks for null.
     * 
     * @param result
     *            the returned value of query()
     * @param column
     *            the column number, starts from 1
     * @return string value of the column or null
     */
    public String resultString(ResultSet result, int column) {
	if (result == null)
	    return null;

	try {
	    result.next();
	    String string = result.getString(column);
	    result.close();

	    return string;
	} catch (SQLException e) {
	    log.severe("Database result error: " + e.getMessage());
	}

	return null;
    }

    /**
     * Sends a query to the SQL Returns a ResultSet if there's anything to return
     * 
     * @param query
     *            the SQL query string
     * @return ResultSet or null
     */
    public ResultSet query(final String query) {
	return query(query, false);
    }

    /**
     * Sends a query to the SQL Returns a ResultSet if there's anything to return
     * 
     * @param query
     *            the SQL query string
     * @param retry
     *            set to true to retry query if locked
     * @return ResultSet or null
     */
    public ResultSet query(final String query, final boolean retry) {
	try {
	    final Connection connection = DriverManager.getConnection(url);
	    final PreparedStatement statement = connection.prepareStatement(query);

	    if (statement.execute())
		return statement.getResultSet();
	} catch (final SQLException e) {
	    final String msg = e.getMessage();

	    log.severe("Database query error: " + msg);

	    if (retry && msg.contains("_BUSY")) {
		log.severe("Retrying query...");

		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		    @Override
		    public void run() {
			query(query);
		    }
		}, 20);
	    }
	}

	return null;
    }
    private void proccessQueue(){
	QueueSegment segment = queue.poll();
	if (segment!=null){
	    segment.getResultListener().result(query(segment.getQuery()), segment.getId());
	}
    }
    public void dump(){
	while (queue.size()>0){
	    proccessQueue();
	}
    }
    public DatabaseType getType() {
	return type;
    }
}
enum DatabaseType {
    SQLLITE, MYSQL
}
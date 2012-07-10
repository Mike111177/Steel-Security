package net.othercraft.steelsecurity;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

import net.othercraft.steelsecurity.antihack.other.derp.UpsideDown;
import net.othercraft.steelsecurity.commands.GameModeCmdCatch;
import net.othercraft.steelsecurity.commands.Sts;
import net.othercraft.steelsecurity.commands.Vanish;
import net.othercraft.steelsecurity.data.Violations;
import net.othercraft.steelsecurity.data.databases.DatabaseManager;
import net.othercraft.steelsecurity.listeners.BlockBlacklist;
import net.othercraft.steelsecurity.listeners.ChatFilter;
import net.othercraft.steelsecurity.listeners.ConsoleCommandMessage;
import net.othercraft.steelsecurity.listeners.JoinMessage;
import net.othercraft.steelsecurity.listeners.LoginLimiter;
import net.othercraft.steelsecurity.listeners.PlayerConfigListener;
import net.othercraft.steelsecurity.listeners.SpectateManager;
import net.othercraft.steelsecurity.ticketsystem.TicketManager;
import net.othercraft.steelsecurity.utils.ExtraConfigManager;
import net.othercraft.steelsecurity.utils.FlatFileLogger;

import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main extends JavaPlugin {

    public static Main instance;

    private Sts base;

    @SuppressWarnings("unused")
    private ChatFilter cf;
    @SuppressWarnings("unused")
    private JoinMessage jm;
    @SuppressWarnings("unused")
    private LoginLimiter ll;
    private PlayerConfigListener pcl;
    @SuppressWarnings("unused")
    private GameModeCmdCatch gmcc;
    @SuppressWarnings("unused")
    private BlockBlacklist blbl;
    public SpectateManager spm;
    private Vanish vm;
    private Violations vio;
    @SuppressWarnings("unused")
    private UpsideDown upd;
    @SuppressWarnings("unused")
    private DatabaseManager dbm = new DatabaseManager();
    private ExtraConfigManager anticm;
    private ExtraConfigManager data;
    private ExtraConfigManager logc;
    @SuppressWarnings("unused")
    private ConsoleCommandMessage cmm;
    private static final Logger log = Logger.getLogger("Minecraft");
    private File dataFolder = null;

    private Double currentVersion;
    private String versionName;
    private String newVersionName;
    private double newVersion;
    private TicketManager tickm;
    
    

    public void onEnable() {
	versionName = getDescription().getVersion().split("-")[0];
	currentVersion = Double.valueOf(versionName.replaceFirst("\\.", ""));
	this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {

	    @Override
	    public void run() {
		try {
		    newVersion = updateCheck(currentVersion);
		    if (newVersion > currentVersion) {
			log.warning("Steel Security" + newVersionName + " is out!");
			log.warning("You are running: Steel Security " + versionName);
			log.warning("Update Steel Security at: http://dev.bukkit.org/server-mods/steel-security");
		    }
		} catch (Exception e) {
		    // ignore exceptions
		}
	    }

	}, 0, 432000);
	dataFolder = getDataFolder();
	File ticketDataFolder = new File(dataFolder + File.separator + "Tickets");
	config();
	instance = this;
	registerListeners();
	commands(ticketDataFolder);
	playerChecks();
    }
    protected ExtraConfigManager dataConfig(){
	return data;
    }
    protected ExtraConfigManager logConfig(){
	return logc;
    }
    protected ExtraConfigManager antiHackConfig(){
	return anticm;
    }
    
    public ExtraConfigManager getNewConfig(File folder, String name){
	return new ExtraConfigManager(folder, name);
    }
    public FlatFileLogger getNewLog(File folder, String name){
	return new FlatFileLogger(folder, name);
    }
    
    private void config() {
	anticm = new ExtraConfigManager(dataFolder, "AntiHack");
	logc = new ExtraConfigManager(dataFolder, "Database");
	data = new ExtraConfigManager(dataFolder, "Logging");
	new Config(this, anticm, logc, data).loadConfiguration();
    }

    private void playerChecks() {
	pcl.checkAll();
	spm.registerAll();
	vm.registerAll();
	vio.engageAll();
    }
    public double updateCheck(double currentVersion) throws Exception {
        String pluginUrlString = "http://dev.bukkit.org/server-mods/steel-security/files.rss";
        try {
            URL url = new URL(pluginUrlString);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openConnection().getInputStream());
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("item");
            Node firstNode = nodes.item(0);
            if (firstNode.getNodeType() == 1) {
                Element firstElement = (Element)firstNode;
                NodeList firstElementTagName = firstElement.getElementsByTagName("title");
                Element firstNameElement = (Element) firstElementTagName.item(0);
                NodeList firstNodes = firstNameElement.getChildNodes();
                newVersionName = firstNodes.item(0).getNodeValue().replace("Steel Security", "");
                return Double.valueOf(newVersionName.replaceFirst("\\.", "").trim());
            }
        }
        catch (Exception localException) {
        }
        return currentVersion;
    }

    private void commands(File tickdata) {// register commands here
	base = new Sts("base", this, spm, vm);
	getCommand("sts").setExecutor(base);
	tickm = new TicketManager(tickdata);
	getCommand("ticket").setExecutor(tickm);
    }

    private void registerListeners() {// register listeners here
	vio = new Violations(null, this);
	cf = new ChatFilter(null, this, vio);
	jm = new JoinMessage(null, this);
	ll = new LoginLimiter(null, this);
	pcl = new PlayerConfigListener(null, this);
	gmcc = new GameModeCmdCatch(null, this);
	blbl = new BlockBlacklist(null, this);
	vm = new Vanish(null, this, log);
	cmm = new ConsoleCommandMessage(null, this, log);
	spm = new SpectateManager(null, this, vm, log);
	upd = new UpsideDown(null, this, vio);
	vm.specGet();
    }

    public void onDisable() {
	spm.stopAll();
	vm.stopAll();
    }
    public double getLatestVersion() {
	return newVersion;
    }
    public double getCurrentVersion() {
	return currentVersion;
    }
    public String getLatestVersionName() {
	return newVersionName;
    }
    public String getCurrentVersionName() {
	return versionName;
    }
    public Logger getLogger(){
	return log;
    }
    public void registerCommandError() {
	// TODO Set a class for errored commands go to
    }
}
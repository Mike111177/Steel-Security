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
import net.othercraft.steelsecurity.data.logging.ChatLogger;
import net.othercraft.steelsecurity.hooks.Spout;
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
import org.bukkit.scheduler.BukkitScheduler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
@SuppressWarnings("unused")
public class SteelSecurity extends JavaPlugin {

    public static JavaPlugin instance;

    public static boolean spoutEnabled;
    public static boolean weEnabled;
    public static boolean vaultEnabled;
    public static boolean mcBansEnabled;

    private static Sts base;

    private static ChatFilter chatFilter;
    private static JoinMessage joinMessage;
    private static LoginLimiter loginLimiter;
    private static PlayerConfigListener pcl;
    private static GameModeCmdCatch gmcc;
    private static BlockBlacklist blockBlacklist;
    public static SpectateManager spectateManager;
    private static Vanish vanishManager;
    private static Violations vio;
    private static UpsideDown upd;
    private static ExtraConfigManager anticm;
    private static ExtraConfigManager data;
    private static ExtraConfigManager logc;
    private static ConsoleCommandMessage cmm;
    private static Config config;
    private static ChatLogger logger;
    public static transient final Logger LOG = Logger.getLogger("Minecraft");
    private static File dataFolder = null;
    final transient public BukkitScheduler sch = getServer().getScheduler();

    private static Double currentVersion;
    private static String versionName;
    private static String newVersionName;
    private static double newVersion;
    private static TicketManager tickm;
    
    private static final String PLUGINURLSTRING = "http://dev.bukkit.org/server-mods/steel-security/files.rss";

    public void onEnable() {
	versionName = getDescription().getVersion().split("-")[0];
	currentVersion = Double.valueOf(versionName.replaceFirst("\\.", ""));
	sch.scheduleAsyncRepeatingTask(this, new Runnable() {
	    @Override
	    public void run() {
		try {
		    newVersion = updateCheck(currentVersion);
		    if (newVersion > currentVersion) {
			LOG.warning("Steel Security" + newVersionName + " is out!");
			LOG.warning("You are running: Steel Security " + versionName);
			LOG.warning("Update Steel Security at: http://dev.bukkit.org/server-mods/steel-security");
		    }
		} catch (Exception e) {
		    // ignore exceptions
		}
	    }

	}, 0, 432000);
	sch.scheduleSyncDelayedTask(this, new Spout(this), 20);
	dataFolder = getDataFolder();
	final File ticketDataFolder = new File(dataFolder + File.separator + "Tickets");
	initConfig();
	instance = this;
	registerListeners();
	commands(ticketDataFolder);
	playerChecks();
    }

    protected ExtraConfigManager dataConfig() {
	return data;
    }

    protected ExtraConfigManager logConfig() {
	return logc;
    }

    protected ExtraConfigManager antiHackConfig() {
	return anticm;
    }

    /**
     * @param folder
     *            The folder to put the config in
     * @param name
     *            the name of the config file (".yml" will be added automaticly)
     * @return A class that would work just like a normal config file
     */
    public final ExtraConfigManager getNewConfig(final File folder, final String name) {
	return new ExtraConfigManager(folder, name);
    }

    public final FlatFileLogger getNewLog(final File folder,final String name) {
	return new FlatFileLogger(folder, name);
    }

    private void initConfig() {
	anticm = new ExtraConfigManager(dataFolder, "AntiHack");
	logc = new ExtraConfigManager(dataFolder, "Database");
	data = new ExtraConfigManager(dataFolder, "Logging");
	config = new Config(this, anticm, logc, data);
	config.loadConfiguration();
    }

    private void playerChecks() {
	pcl.checkAll();
	spectateManager.registerAll();
	vanishManager.registerAll();
	vio.engageAll();
    }

    public final double updateCheck(final double currentVersion) {
	Double result = 0.0;
	try {
	    final URL url = new URL(PLUGINURLSTRING);
	    final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openConnection().getInputStream());
	    doc.getDocumentElement().normalize();
	    final NodeList nodes = doc.getElementsByTagName("item");
	    final Node firstNode = nodes.item(0);
	    if (firstNode.getNodeType() == 1) {
		final Element firstElement = (Element) firstNode;
		final NodeList firstTagName = firstElement.getElementsByTagName("title");
		final Element firstNameElement = (Element) firstTagName.item(0);
		final NodeList firstNodes = firstNameElement.getChildNodes();
		newVersionName = firstNodes.item(0).getNodeValue().replace("Steel Security", "");
		result = Double.valueOf(newVersionName.replaceFirst("\\.", "").trim());
	    }
	} catch (Exception localException) {
	    result = currentVersion;
	}
	return result;
    }

    private final void commands(final File tickdata) {// register commands here
	base = new Sts(this, spectateManager, vanishManager, gmcc);
	getCommand("sts").setExecutor(base);
	tickm = new TicketManager(tickdata,this);
	getCommand("ticket").setExecutor(tickm);
    }

    private void registerListeners() {// register listeners here
	vio = new Violations(this);
	chatFilter = new ChatFilter(this, vio);
	joinMessage = new JoinMessage(this);
	loginLimiter = new LoginLimiter(this);
	pcl = new PlayerConfigListener(this);
	gmcc = new GameModeCmdCatch(this);
	blockBlacklist = new BlockBlacklist(this);
	vanishManager = new Vanish(this);
	cmm = new ConsoleCommandMessage(this);
	spectateManager = new SpectateManager(this, vanishManager);
	upd = new UpsideDown(vio,this);
	vanishManager.specGet();
	logger = new ChatLogger(this);
    }

    public void onDisable() {
	spectateManager.stopAll();
	vanishManager.stopAll();
	tickm.saveAll();
    }

    /**
     * Get the lastest verison
     * 
     * @return the lastest version
     */
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
    public void registerCommandError() {
	// TODO Set a class for errored commands go to
    }
}
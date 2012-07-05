package net.othercraft.steelsecurity;

import net.othercraft.steelsecurity.antihack.other.derp.UpsideDown;
import net.othercraft.steelsecurity.commands.GameModeCmdCatch;
import net.othercraft.steelsecurity.commands.Sts;
import net.othercraft.steelsecurity.commands.Vanish;
import net.othercraft.steelsecurity.data.DatabaseManager;
import net.othercraft.steelsecurity.data.Violations;
import net.othercraft.steelsecurity.listeners.BlockBlacklist;
import net.othercraft.steelsecurity.listeners.ChatFilter;
import net.othercraft.steelsecurity.listeners.JoinMessage;
import net.othercraft.steelsecurity.listeners.LoginLimiter;
import net.othercraft.steelsecurity.listeners.PlayerConfigListener;
import net.othercraft.steelsecurity.listeners.SpectateManager;
import net.othercraft.steelsecurity.utils.AntiHackConfigManager;
import net.othercraft.steelsecurity.utils.DatabaseConfigManager;

import org.bukkit.plugin.java.JavaPlugin;

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
    private DatabaseManager dbm = new DatabaseManager();
    private DatabaseConfigManager dbcm = new DatabaseConfigManager(this);
    private AntiHackConfigManager anticm = new AntiHackConfigManager(this);

    public void onEnable() {
	config();
	instance = this;
	registerListeners();
	commands();
	playerChecks();
    }

    private void config() {
	new Config(this, dbcm, anticm).loadConfiguration();
	
    }

    private void playerChecks() {
	pcl.checkAll();
	spm.registerAll();
	vm.registerAll();
	vio.engageAll();
    }

    private void commands() {// register commands here
	base = new Sts("base", this, spm, vm);
	getCommand("sts").setExecutor(base);
    }

    private void registerListeners() {// register listeners here
	vio = new Violations(null, this);
	cf = new ChatFilter(null, this, vio);
	jm = new JoinMessage(null, this);
	ll = new LoginLimiter(null, this);
	pcl = new PlayerConfigListener(null, this);
	gmcc = new GameModeCmdCatch(null, this);
	blbl = new BlockBlacklist(null, this);
	vm = new Vanish(null, this);
	spm = new SpectateManager(null, this, vm);
	upd = new UpsideDown(null, this, vio);
	vm.specGet();
    }

    public void onDisable() {
	spm.stopAll();
	vm.stopAll();
    }
}
package net.othercraft.steelsecurity.data.violations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ViolationsManager extends SSCmdExe {
    
    
    public static final String ADD = "VIOLATION_ADDED";
    public static final String SUBTRACT = "VIOLATION_SUBTRACTED";
    public static final String RESET = "VIOLATION_RESET";
    public static final String SET = "VIOLATION_SET";
    public static final String ENGAGE = "VIOLATION_ENGAGED";
    public static final String DISENGAGE = "VIOLATION_DISENGAGED";
    

    private JavaPlugin plugin;

    public ViolationsManager(final SteelSecurity instance) {
	super("Violations", true, instance);
	plugin = instance;
    }

    private static final Map<String, Violation> VIOLATIONS = new HashMap<String, Violation>();

    
    
    public static boolean registerViolation(final String violationName){
	if (!VIOLATIONS.containsKey(violationName)){
	    VIOLATIONS.put(violationName, new Violation(violationName));
	    return true;
	}
	return false;
    }
    
    public static boolean registerViolationListener(final String violationName,final IViolationListener listener){
	if (!VIOLATIONS.containsKey(violationName)){
	    return VIOLATIONS.get(violationName).addListener(listener);
	}
	return false;
    }
    
    public static void engageAll() {
	for (Violation vio : VIOLATIONS.values()){
	    vio.engageAll();
	}

    }
    public static void disengageAll() {
	for (Violation vio : VIOLATIONS.values()){
	    vio.disengageAll();
	}
	

    }
    /**
     * Used to add 1 to a players' violation.
     * @param violation The String identifier of the violation.
     * @param player The player to add one to their violation.
     * @return Whether or not the change was successful.
     */
    public static boolean add(final String violation,final  Player player){
	return add(violation, player ,1);
    }
    public static boolean add(final String violation,final  Player player, final int amount) {
	if (VIOLATIONS.containsKey(violation)){
	    VIOLATIONS.get(violation).add(amount, player);
	    return true;
	}
	return false;
    }
    
    
    
    
    /**
     * Used to subtract 1 from a players' violation.
     * @param violation The String identifier of the violation.
     * @param player The player to subtract one from their violation.
     * @return Whether or not the change was successful.
     */
    public static boolean subtract(final String violation,final  Player player){
	return subtract(violation, player ,1);
    }
    public static boolean subtract(final String violation,final  Player player, final int amount) {
	if (VIOLATIONS.containsKey(violation)){
	    VIOLATIONS.get(violation).subtract(amount, player);
	    return true;
	}
	return false;
    }
    
    
    
    public static boolean reset(final String violation,final  Player player) {
	if (VIOLATIONS.containsKey(violation)){
	    VIOLATIONS.get(violation).reset(player);
	    return true;
	}
	return false;
    }
    
    
    public static boolean set(final String violation,final  Player player, final int amount) {
	if (VIOLATIONS.containsKey(violation)){
	    VIOLATIONS.get(violation).set(amount, player);
	    return true;
	}
	return false;
    }
}

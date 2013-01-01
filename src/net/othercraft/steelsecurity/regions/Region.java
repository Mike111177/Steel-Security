package net.othercraft.steelsecurity.regions;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

public abstract class Region<Block> {
    
    public final transient Player player;
   
    
    public final transient RegionShape shape;
    
    public Region(final RegionShape shape,final Player player){
	this.shape = shape;
	this.player = player;
    }
    
    public final void displayRegion(){
	List<Block> blocks = getBorder();
	//TODO make it flash these blocks
    }
    
    public abstract int getArea();
    
    public abstract List<Block> getContainedBlocks();
    
    public abstract List<Block> getBorder();
    
    public abstract List<Block> getSurface();
    
    public final Player getPlayer(){
	return player;
    } 

}
enum RegionShape{
    CUBOID("Cuboid"),
    SPHERE("Sphere"),
    CYLINDER("Cylinder"),
    HGON("Hgon"),
    NGON("Ngon"),
    VGON("Vgon"),
    ELIPSEOID("Elipseoid");
    private final String value;
    private final static Map<String, RegionShape> BY_ID = Maps.newHashMap();
    private RegionShape(final String shape){
	this.value = shape;
    }
    public String getValue(){
	return value;
    }
    public static RegionShape getByValue(final String shape) {
	return BY_ID.get(shape);
    }
    static{
	for (RegionShape shape : values()) {
	    BY_ID.put(shape.getValue(), shape);
	}
    }
}

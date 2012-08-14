package net.othercraft.steelsecurity.regions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Region {
    
    public final Player player;
   
    
    public final RegionShape shape;
    
    public Region(RegionShape shape, Player player){
	this.shape = shape;
	this.player = player;
    }
    
    public abstract void displayRegion();
    
    public abstract int getArea();
    
    public abstract Location[] getContainedBlocks();
    
    public Player getPlayer(){
	return player;
    } 

}
enum RegionShape{
    CUBOID,SPHERE,CYLINDER,HGON,NGON,VGON,ELIPSEOID
}

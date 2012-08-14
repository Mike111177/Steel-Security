package net.othercraft.steelsecurity.regions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Cuboid extends Region{
    
    private Location loc1;
    private Location loc2;
    

    public Cuboid(Location loc1, Location loc2, Player player) {
	super(RegionShape.CUBOID, player);
	this.loc1 = loc1;
	this.loc2 = loc2;
    }

    @Override
    public void displayRegion() {
	// TODO Auto-generated method stub
	
    }

    @Override
    public int getArea() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public Location[] getContainedBlocks() {
	// TODO Auto-generated method stub
	return null;
    }

}

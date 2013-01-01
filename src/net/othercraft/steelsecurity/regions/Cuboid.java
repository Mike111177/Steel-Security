package net.othercraft.steelsecurity.regions;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public final class Cuboid extends Region<Block>{
    
    private Location loc1;
    private Location loc2;
    
    

    public Cuboid(final Location loc1,final Location loc2,final Player player) {
	super(RegionShape.CUBOID, player);
	this.setLoc1(loc1);
	this.setLoc2(loc2);
    }

    @Override
    public int getArea() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public List<Block> getContainedBlocks() {
	// TODO Auto-generated method stub
	return null;
    }
    

    @Override
    public List<Block> getBorder() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Block> getSurface() {
	// TODO Auto-generated method stub
	return null;
    }

    public Location getLoc2() {
	return loc2;
    }

    public void setLoc2(final Location loc2) {
	this.loc2 = loc2;
    }

    public Location getLoc1() {
	return loc1;
    }

    public void setLoc1(final Location loc1) {
	this.loc1 = loc1;
    }
    

}

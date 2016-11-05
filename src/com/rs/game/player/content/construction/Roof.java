package com.rs.game.player.content.construction;

import com.rs.game.RegionBuilder;

public enum Roof {
	ROOF1(233, 634, RegionBuilder.NORTH, RegionBuilder.SOUTH),
    ROOF2(235, 634, RegionBuilder.NORTH, RegionBuilder.EAST, RegionBuilder.SOUTH),
    ROOF3(235, 634, RegionBuilder.NORTH, RegionBuilder.EAST, RegionBuilder.SOUTH, RegionBuilder.NORTH);
     
    private int chunkX, chunkY;
    private int[] dirs;
     
    private Roof(int chunkX, int chunkY, int... dirs) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.dirs = dirs;
    }
    
    public int getChunkX() {
    	return chunkX;
    }
    
    public int getChunkY() {
    	return chunkY;
    }
    
    public int[] getDirs() {
    	return dirs;
    }

}

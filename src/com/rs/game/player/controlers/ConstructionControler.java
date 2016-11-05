package com.rs.game.player.controlers;

import com.rs.game.RegionBuilder;
import com.rs.game.WorldTile;
import com.rs.game.player.content.construction.House;

public class ConstructionControler extends Controler {
	
	private House house;
	private int[] boundChuncks;
	
	@Override
	public void start() {
		//house = new House();
		boundChuncks = RegionBuilder.findEmptyChunkBound(8, 8); 
		//house.initHouse(boundChuncks, true);
		player.setNextWorldTile(new WorldTile(boundChuncks[0]*8 + 35, boundChuncks[1]*8 + 35,0));
		player.getPackets().sendGameMessage("Welcome to your house! Make a bed or wardrobe!");
	}
	
	boolean remove = true;
}
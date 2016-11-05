package com.rs.game.player.controlers.undergroundpass;

import com.rs.game.WorldTile;

public class UndergroundPass {

	
	public boolean processMagicTeleport(WorldTile toTile) {
		//player.getPackets().sendGameMessage("You can't teleport out of this!");
		return false;
	}

	
	public boolean processItemTeleport(WorldTile toTile) {
	//	player.getPackets().sendGameMessage("You can't teleport out of the arena!");
		return false;
	}

	
	public boolean processObjectTeleport(WorldTile toTile) {
	//	player.getPackets().sendGameMessage("You can't teleport out of the arena!");
		return false;
	}
	
}

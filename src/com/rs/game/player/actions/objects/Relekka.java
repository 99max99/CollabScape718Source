package com.rs.game.player.actions.objects;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;

/*
 * @Author Danny
 * Varrock City
 */

public class Relekka {
	
	public static void FremDungExit(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(2795, 3615, 0), 1, 2);
	}
	
	public static void FremDungEnter(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(2808, 10002, 0), 1, 2);
	}
	
	public static void IceStryke(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(3485, 5511, 0));
	}
	
	public static void IceStrykeExit(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(2736, 3731, 0));
	}
	
	public static void IceStrykeChamber(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(3435, 5646, 0));
	}
	
	public static void IceStrykeChamberExit(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(3509, 5516, 0));
	}
	
	public static void BrineExit(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(2729, 3734, 0));
	}
	
	public static void KeldExit(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(2780, 10161, 0), 1, 2);
	}
	
	public static void KeldEnter(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(2838, 10124, 0), 1, 2);
	}
	
	public static void DwarfEnter(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(1520, 4704, 0), 1, 2);
	}
	
	public static void DwarfExit(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(2819, 10158, 0), 1, 2);
	}
	
	public static void TunnelEnter(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(2773, 10162, 0), 1, 2);
	}
	
	public static void TunnelExit(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(2730, 3713, 0), 1, 2);
	}
	
	public static void LavaEnter(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(2177, 5663, 0), 1, 2);
	}
	
	public static void LavaExit(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(2939, 10197, 0), 1, 2);
	}

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 77053:
		case 34395:
		case 42793:
		case 42891:
		case 5998:
		case 5989:
		case 5973:
		case 45008:
		case 5008:
		case 5014:
		case 56990:
		case 56989:
		case 23158:
		case 23157:
		case 48188:
		case 48189:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 48188) { 
			Relekka.IceStrykeChamber(player, object); 
		}
		if (id == 48189) { 
			Relekka.IceStrykeChamberExit(player, object); 
		}
		if (id == 77053) { 
			Relekka.FremDungExit(player, object); 
		}
		if (id == 34395) { 
			Relekka.FremDungEnter(player, object); 
		}
		if (id == 42793) { 
			Relekka.IceStryke(player, object); 
		}
		if (id == 42891) { 
			Relekka.IceStrykeExit(player, object); 
		}
		if (id == 5998) { 
			Relekka.KeldExit(player, object); 
		}
		if (id == 5973) { 
			Relekka.KeldEnter(player, object); 
		}
		if (id == 5989) { 
			Relekka.DwarfEnter(player, object); 
		}
		if (id == 45008) { 
			Relekka.DwarfExit(player, object); 
		}
		if (id == 5008) { 
			Relekka.TunnelEnter(player, object); 
		}
		if (id == 5014) { 
			Relekka.TunnelExit(player, object); 
		}
		if (id == 56990) { 
			Relekka.LavaEnter(player, object); 
		}
		if (id == 56989) { 
			Relekka.LavaExit(player, object); 
		}
		if (id == 23157 || id == 23158) { 
			Relekka.BrineExit(player, object); 
		}
		
	}

}

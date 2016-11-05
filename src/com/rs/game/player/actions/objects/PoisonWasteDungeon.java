package com.rs.game.player.actions.objects;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.magic.Magic;

/*
 * @Author Adam
 * Poison Waste Slayer Dungeon (Warped Creatures)
 */

public class PoisonWasteDungeon {
	
	
	public static void HandleLadders(Player player,
			final WorldObject object) {
		if (object.getId() == 26518){
			
				if (object.getX() == 1991 && object.getY() == 4175)
					player.useStairs(828, new WorldTile(1991, 4176, 1), 1, 2);	
				else if (object.getX() == 2041 && object.getY() == 4172)
					player.useStairs(828, new WorldTile(2042, 4172, 1), 1, 2);	
				else if (object.getX() == 2041 && object.getY() == 4189)
					player.useStairs(828, new WorldTile(2042, 4189, 1), 1, 2);
				else if (object.getX() == 1998 && object.getY() == 4218)
					player.useStairs(828, new WorldTile(1998, 4219, 1), 1, 2);	
				else if (object.getX() == 2011 && object.getY() == 4218)
					player.useStairs(828, new WorldTile(2011, 4219, 1), 1, 2);
				
				
			
				
			
		}
		if (object.getId() == 26519){
			
				
			if (object.getX() == 1991 && object.getY() == 4175)
				player.useStairs(828, new WorldTile(1991, 4175, 0), 1, 2);	
			else if (object.getX() == 2041 && object.getY() == 4172)
				player.useStairs(828, new WorldTile(2041, 4172, 0), 1, 2);	
			else if (object.getX() == 2041 && object.getY() == 4189)
				player.useStairs(828, new WorldTile(2041, 4189, 0), 1, 2);	
			else if (object.getX() == 1998 && object.getY() == 4218)
				player.useStairs(828, new WorldTile(1998, 4218, 0), 1, 2);	
			else if (object.getX() == 2011 && object.getY() == 4218)
				player.useStairs(828, new WorldTile(2011, 4218, 0), 1, 2);
			
		}
		
	}
	public static void HandleEntrance(Player player,
			final WorldObject object) {
		
			player.setNextWorldTile(new WorldTile(1985, 4173, 0));
		
		
		

	}
	public static void HandleExit(Player player,
			final WorldObject object) {
		
			player.setNextWorldTile(new WorldTile(2321, 3100, 0));
		
		
		

	}
	
	
	
	

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 26684:
		case 26685:
		case 26686:
			
		case 26570:
		case 26571:
		case 26572:
		case 26573:
		case 26574:
		case 26575:
			
		case 26518:
		case 26519:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 26684 || id == 26685 || id == 26686) { 
			PoisonWasteDungeon.HandleEntrance(player, object);
		}
		if (id == 26570 || id == 26571 || id == 26572 || id == 26573 || id == 26574 || id == 26575) { 
			PoisonWasteDungeon.HandleExit(player, object);
		}
		if (id == 26518 || id == 26519) { 
			PoisonWasteDungeon.HandleLadders(player, object);
		}
		
		
	}

}
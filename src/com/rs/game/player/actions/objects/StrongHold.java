package com.rs.game.player.actions.objects;

import com.rs.game.WorldObject;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.magic.Magic;

/*
 * @Author Danny
 * Security Stronghold
 * Help from Adam
 */

public class StrongHold {
	
	public static void Stairs(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(1860, 5244, 0), 1, 2);
	}

	public static void Exit(Player player, final WorldObject object) {
		player.getPackets().sendGameMessage("You escape the dungeon.");
		player.useStairs(828, new WorldTile(3081, 3421, 0), 1, 2);
	}

	public static void DeadGuy(Player player, final WorldObject object) {
		player.getPackets().sendGameMessage("He looks like he's been dead for quite some time.");
	}

	public static void Door(Player player, final WorldObject object) {
		if (object.getX() > player.getX() && object.getY() == player.getY()) {
			player.useStairs(2246, new WorldTile((player.getX() + 2), player.getY(), player.getPlane()), 1, 2);
			
		} else if (object.getX() < player.getX() && object.getY() == player.getY()) {
			player.useStairs(2246, new WorldTile((player.getX() - 2), player.getY(), player.getPlane()), 1, 2);
			
		} else if (object.getX() == player.getX() && object.getY() > player.getY()) {
			player.useStairs(2246, new WorldTile(player.getX(), (player.getY() + 2), player.getPlane()), 1, 2);
			
		} else if (object.getX() == player.getX() && object.getY() < player.getY()) {
			player.useStairs(2246, new WorldTile(player.getX(), (player.getY() - 2), player.getPlane()), 1, 2);
		} else {
			player.getPackets().sendGameMessage("You can't open the door from this angle.");
			return;
		}
	}
	
	public static void ChestOne(Player player, final WorldObject object) {
		 if  (player.strongHoldChestOne == false) {
			 player.strongHoldChestOne = true;
			 player.strongHoldOne = true;
			 player.getInventory().addItem(9005, 1);//Fancy Boots
			 player.getInventory().addItem(9006, 1);//Fighting Boots
			 player.getInventory().addItemMoneyPouch(995, 10000);//10k
			 } else if (player.strongHoldChestOne == true) {
			 player.getPackets().sendGameMessage("You have already looted this chest.");
			 }
	}
	
	public static void ChestTwo(Player player, final WorldObject object) {
		 if  (player.strongHoldChestTwo == false) {
			 player.strongHoldChestTwo = true;
			 player.strongHoldTwo = true;
			 player.getInventory().addItem(11838, 1);//Rune Set
			 player.getInventory().addItemMoneyPouch(995, 10000);//10k
			 } else if (player.strongHoldChestTwo == true) {
			 player.getPackets().sendGameMessage("You have already looted this sack.");
			 }
	}
	
	public static void ChestThree(Player player, final WorldObject object) {
		 if  (player.strongHoldChestThree == false) {
			 player.strongHoldChestThree = true;
			 player.strongHoldThree = true;
			 player.getInventory().addItem(554, 1000);//Runes
			 player.getInventory().addItem(555, 1000);//Runes
			 player.getInventory().addItem(556, 1000);//Runes
			 player.getInventory().addItem(557, 1000);//Runes
			 player.getInventory().addItem(558, 1000);//Runes
			 player.getInventory().addItem(559, 1000);//Runes
			 player.getInventory().addItem(560, 1000);//Runes
			 player.getInventory().addItem(561, 1000);//Runes
			 player.getInventory().addItem(562, 1000);//Runes
			 player.getInventory().addItem(563, 1000);//Runes
			 player.getInventory().addItem(564, 1000);//Runes
			 player.getInventory().addItem(565, 1000);//Runes
			 player.getInventory().addItem(566, 1000);//Runes
			 player.getInventory().addItemMoneyPouch(995, 10000);//10k
			 } else if (player.strongHoldChestThree == true) {
			 player.getPackets().sendGameMessage("You have already looted this box.");
			 }
			}
	
	public static void ChestFour(Player player, final WorldObject object) {
		 if  (player.strongHoldChestFour == false) {
			 player.strongHoldChestFour = true;
			 player.getInventory().addItem(9672, 1);//Pros Helm
			 player.getInventory().addItem(9674, 1);//Pros Body
			 player.getInventory().addItem(9676, 1);//Pros Legs
			 player.getInventory().addItem(9678, 1);//Pros Legs
			 player.getInventory().addItemMoneyPouch(995, 10000);//10k
			 player.strongHoldFour = true;
			 } else if (player.strongHoldChestFour == true) {
			 player.getPackets().sendGameMessage("You have already looted this cradle.");
			 }
			}
	
	public static void Stair1(Player player, final WorldObject object) {
		 player.useStairs(827, new WorldTile(2042, 5245, 0), 1, 2);
		 player.strongHoldOne = true;
		}
	
	public static void Stair2(Player player, final WorldObject object) {
		 player.useStairs(828, new WorldTile(1902, 5223, 0), 1, 2);
	}
	
	public static void Stair3(Player player, final WorldObject object) {
		 player.useStairs(827, new WorldTile(2122, 5251, 0), 1, 2);
		 player.strongHoldTwo = true;
		}
	
	public static void Stair4(Player player, final WorldObject object) {
		 player.useStairs(828, new WorldTile(2026, 5219, 0), 1, 2);
	}
	
	public static void Stair5(Player player, final WorldObject object) {
		 player.useStairs(827, new WorldTile(2358, 5215, 0), 1, 2);
		 player.strongHoldTwo = true;
		}
	
	public static void Stair6(Player player, final WorldObject object) {
		 player.useStairs(828, new WorldTile(2147, 5284, 0), 1, 2);
	}
	
	public static void Portal1(Player player, final WorldObject object) {
		 if  (player.strongHoldOne == true) {
			  Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1913, 5220, 0));
			  } else if (player.strongHoldOne == false) {
			  player.getPackets().sendGameMessage("You must complete this level before you may use this portal.");
			 } 
			}
	
	public static void Portal2(Player player, final WorldObject object) {
		 if  (player.strongHoldTwo == true) {
			  Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2021, 5223, 0));
			  } else if (player.strongHoldTwo == false) {
			  player.getPackets().sendGameMessage("You must complete this level before you may use this portal.");
			 } 
			}
	
	public static void Portal3(Player player, final WorldObject object) {
		 if  (player.strongHoldThree == true) {
			  Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2146, 5287, 0));
			  } else if (player.strongHoldThree == false) {
			  player.getPackets().sendGameMessage("You must complete this level before you may use this portal.");
			 }
			}
	
	public static void Portal4(Player player, final WorldObject object) {
		 if  (player.strongHoldFour == true) {
			  Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2341, 5219, 0));
			  } else if (player.strongHoldFour == false) {
			  player.getPackets().sendGameMessage("You must complete this level before you may use this portal.");
			 }
			}
	
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if ((id == 16154) && (object.getX() == 3081) && (object.getY() == 3420)) {
			StrongHold.Stairs(player, object);
		}
		if ((id == 16148 || id == 16078 || id == 16112 || id == 16048)) {
			StrongHold.Exit(player, object);
		}
		if ((id == 16152) && (object.getX() == 1860) && (object.getY() == 5240)) {
			StrongHold.DeadGuy(player, object);
		}
		if ((id == 16124) || (id == 16123) || (id == 16044) || (id == 16043) || (id == 16089) || id == (16090) || id == (16065) || 
				id == (16066)) {
			StrongHold.Door(player, object);
		}
		if ((id == 16152) && (object.getX() == 1860) && (object.getY() == 5240)) {
			StrongHold.DeadGuy(player, object);
		}
		if (id == 16135) {
			StrongHold.ChestOne(player, object);
		}
		if (id == 16077) {
			StrongHold.ChestTwo(player, object);
		}
		if (id == 16118) {
			StrongHold.ChestThree(player, object);
		}
		if (id == 16047) {
			StrongHold.ChestFour(player, object);
		}
		if (id == 16149) {
			StrongHold.Stair1(player, object);
		}
		if (id == 16080) {
			StrongHold.Stair2(player, object);
		}
		if (id == 16081) {
			StrongHold.Stair3(player, object);
		}
		if (id == 16114) {
			StrongHold.Stair4(player, object);
		}
		if (id == 16115) {
			StrongHold.Stair5(player, object);
		}
		if (id == 16049) {
			StrongHold.Stair6(player, object);
		}
		if (id == 16150) {
			StrongHold.Portal1(player, object);
		}
		if (id == 16082) {
			StrongHold.Portal2(player, object);
		}
		if (id == 16116) {
			StrongHold.Portal3(player, object);
		}
		if (id == 16050) {
			StrongHold.Portal4(player, object);
		}
		
	}

}

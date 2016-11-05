package com.rs.game.player.content;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class ShootingStar {
	
	/**
	 * Halp make it cleaner pls
	 */
	
	public void mineShootingStar(Player player) {
		if (crashedStar == 1) {//Fally Park
		World.star++;
		player.recievedGift = false;
		if (World.star == 14) {
		World.spawnObject(new WorldObject(38661, 10, 0 , 3028, 3365, 0), true);
		World.deleteObject(new WorldObject(38661, 10, 0 , 3028, 3365, 0));
		} else if (World.star == 24) {
		World.spawnObject(new WorldObject(38662, 10, 0 , 3028, 3364, 0), true);
		World.deleteObject(new WorldObject(38661, 10, 0 , 3028, 3364, 0));
		} else if (World.star == 35) {
		World.spawnObject(new WorldObject(38663, 10, 0 , 3028, 3363, 0), true);
		World.deleteObject(new WorldObject(38661, 10, 0 , 3028, 3363, 0));
		} else if (World.star == 48) {
		World.spawnObject(new WorldObject(38664, 10, 0 , 3028, 3362, 0), true);
		World.deleteObject(new WorldObject(38661, 10, 0 , 3028, 3362, 0));
		} else if (World.star == 56) {
		World.spawnObject(new WorldObject(38665, 10, 0 , 3028, 3361, 0), true);
		World.deleteObject(new WorldObject(38661, 10, 0 , 3028, 3361, 0));
		} else if (World.star == 63) {
		World.spawnObject(new WorldObject(38666, 10, 0 , 3028, 3360, 0), true);
		World.deleteObject(new WorldObject(38661, 10, 0 , 3028, 3360, 0));
		} else if (World.star == 76) {
		World.spawnObject(new WorldObject(38667, 10, 0 , 3028, 3359, 0), true);
		} else if (World.star >= 80) {
			player.starSprite = true;
			player.stopAll();
			World.spawnObject(new WorldObject(-1, 10, 0 , 3028, 3359, 0), true);
			World.spawnNPC(8091, new WorldTile(3028, 3359, 0), -1, true, true);
			World.removeStarSprite(player);
			//World.spawnStar();
			}
		} else if (crashedStar == 2) {//Rimmington Mine
			World.star++;
			player.recievedGift = false;
			if (World.star == 14) {
			World.spawnObject(new WorldObject(38661, 10, 0 , 2974, 3238, 0), true);
			World.deleteObject(new WorldObject(38661, 10, 0 , 2974, 3238, 0));
			} else if (World.star == 24) {
			World.spawnObject(new WorldObject(38662, 10, 0 , 2974, 3239, 0), true);
			World.deleteObject(new WorldObject(38661, 10, 0 , 2974, 3239, 0));
			} else if (World.star == 35) {
			World.spawnObject(new WorldObject(38663, 10, 0 , 2974, 3240, 0), true);
			World.deleteObject(new WorldObject(38661, 10, 0 , 2974, 3240, 0));
			} else if (World.star == 48) {
			World.spawnObject(new WorldObject(38664, 10, 0 , 2974, 3241, 0), true);
			World.deleteObject(new WorldObject(38661, 10, 0 , 2974, 3241, 0));
			} else if (World.star == 56) {
			World.spawnObject(new WorldObject(38665, 10, 0 , 2974, 3242, 0), true);
			World.deleteObject(new WorldObject(38661, 10, 0 , 2974, 3242, 0));
			} else if (World.star == 63) {
			World.spawnObject(new WorldObject(38666, 10, 0 , 2974, 3243, 0), true);
			World.deleteObject(new WorldObject(38661, 10, 0 , 2974, 3243, 0));
			} else if (World.star == 76) {
			World.spawnObject(new WorldObject(38667, 10, 0 , 2974, 3244, 0), true);
			} else if (World.star >= 80) {
				player.starSprite = true;
				player.stopAll();
				World.spawnObject(new WorldObject(-1, 10, 0 , 2974, 3244, 0), true);
				World.spawnNPC(8091, new WorldTile(2974, 3244, 0), -1, true, true);
				World.removeStarSprite(player);
				//World.spawnStar();
				}
		} else { // Varrock Square
			World.star++;
			player.recievedGift = false;
			if (World.star == 14) {
			World.spawnObject(new WorldObject(38661, 10, 0 , 3228, 3369, 0), true);
			World.deleteObject(new WorldObject(38661, 10, 0 , 3228, 3369, 0));
			} else if (World.star == 24) {
			World.spawnObject(new WorldObject(38662, 10, 0 , 3228, 3368, 0), true);
			World.deleteObject(new WorldObject(38661, 10, 0 , 3228, 3368, 0));
			} else if (World.star == 35) {
			World.spawnObject(new WorldObject(38663, 10, 0 , 3228, 3367, 0), true);
			World.deleteObject(new WorldObject(38661, 10, 0 , 3228, 3367, 0));
			} else if (World.star == 48) {
			World.spawnObject(new WorldObject(38664, 10, 0 , 3228, 3366, 0), true);
			World.deleteObject(new WorldObject(38661, 10, 0 , 3228, 3366, 0));
			} else if (World.star == 56) {
			World.spawnObject(new WorldObject(38665, 10, 0 , 3228, 3365, 0), true);
			World.deleteObject(new WorldObject(38661, 10, 0 , 3228, 3365, 0));
			} else if (World.star == 63) {
			World.spawnObject(new WorldObject(38666, 10, 0 , 3228, 3364, 0), true);
			World.deleteObject(new WorldObject(38661, 10, 0 , 3228, 3364, 0));
			} else if (World.star == 76) {
			World.spawnObject(new WorldObject(38667, 10, 0 , 3228, 3363, 0), true);
			} else if (World.star >= 80) {
				player.starSprite = true;
				player.stopAll();
				World.spawnObject(new WorldObject(-1, 10, 0 , 3228, 3363, 0), true);
				World.spawnNPC(8091, new WorldTile(3228, 3363, 0), -1, true, true);
				World.removeStarSprite(player);
				//World.spawnStar();
			}
		}
	}
	
	public static int crashedStar;
	
	/**
	 * Gets random world tiles and sets an int for a check
	 */
	
	public static void spawnRandomStar() {
		WorldTile tile = new WorldTile(3028, 3365, 0);
		WorldTile tile2 = new WorldTile(2974, 3238, 0);
		WorldTile tile3 = new WorldTile(3245, 3509, 0);
		WorldTile[] tiles = {tile, tile2, tile3};
		World.spawnObject(new WorldObject(38660, 10, 0 , tiles[Utils.random(0, 3)]), true);
		if (tiles[Utils.random(0, 3)] == tile) {
			//Falador Spawn
			crashedStar = 1;
			World.sendWorldMessage("<img=7><col=ff0000>News: A Shooting Star has just struck Falador!", false);
			World.spawnObject(new WorldObject(38660, 10, 0 , 3028, 3365, 0), true);
		} else if (tiles[Utils.random(0, 3)] == tile2) {
			//Rimmington Mine Spawn
			crashedStar = 2;
			World.sendWorldMessage("<img=7><col=ff0000>News: A Shooting Star has just struck Rimmington Mines!", false);
			World.spawnObject(new WorldObject(38660, 10, 0 , 2974, 3238, 0), true);
		} else if (tiles[Utils.random(0, 3)] == tile3) {
			//Varrock Spawn
			crashedStar = 3;
			World.sendWorldMessage("<img=7><col=ff0000>News: A Shooting Star has just struck Varrock lodestone area!", false);
			World.spawnObject(new WorldObject(38660, 10, 0 , 3228, 3369, 0), true);
		}
	}

}

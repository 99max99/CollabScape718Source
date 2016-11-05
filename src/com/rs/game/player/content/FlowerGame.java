package com.rs.game.player.content;


import java.util.Random;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.player.Player;

/**
 * 
 * @author Viper
 *
 */

public class FlowerGame {
	
	static int[] flowerColours = {1, 2981, 2983, 2980, 2984, 2985, 2986, 2987, 2988};
	  
	public static void plant(Player player) {
		Random random = new Random();
		for (int i = 0; i < 1; i++) {
		int colour = random.nextInt(8) + 1;
		int FLOWER = flowerColours[colour];
        if (!player.addWalkSteps(player.getX() - 1, player.getY(), 1))
            if (!player.addWalkSteps(player.getX() + 1, player.getY(), 1))
                if (!player.addWalkSteps(player.getX(), player.getY() + 1, 1))
                    player.addWalkSteps(player.getX(), player.getY() - 1, 1);
        player.getInventory().deleteItem(299, 1);
        World.spawnObject(new WorldObject(FLOWER, 10, -1, player.getX(), player.getY(), player.getPlane()), true);
		}
	}

}
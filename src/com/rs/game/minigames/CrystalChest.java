package com.rs.game.minigames;

import com.rs.game.player.Player;

public class CrystalChest
{

		
		public static final int[] KEY_HALVES = { 985, 987 };
		public static final int KEY = 989;
		public static final int Animation = 881;
		
		/**
		 * Represents the key being made.
		 * Using tooth halves.
		 */
		public static void makeKey(Player p){
			if (p.getInventory().containsItem(toothHalf(), 1) 
					&& p.getInventory().containsItem(loopHalf(), 1)){
				p.getInventory().deleteItem(toothHalf(), 1);
				p.getInventory().deleteItem(loopHalf(), 1);
				p.getInventory().addItem(KEY, 1);
				p.sendMessage("You succesfully make a crytal key.");
			}
		}
		
		/**
		 * If the player can open the chest.
		 */
		public static boolean canOpen(Player p){
			if(p.getInventory().containsItem(KEY, 1)){
				return true;
			}else{
				p.sendMessage("<col=FFFF00>This chest is locked.</col>");
				return false;
			}
		}

		/**
		 * Represents the toothHalf of the key.
		 */
		public static int toothHalf(){
			return KEY_HALVES[0];
		}
		
		/**
		 * Represent the loop half of the key.
		 */
		public static int loopHalf(){
			return KEY_HALVES[1];
		}
		
	}
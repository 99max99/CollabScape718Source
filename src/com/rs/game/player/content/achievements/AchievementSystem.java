package com.rs.game.player.content.achievements;

import com.rs.game.player.Player;

/**
 * 
 * @author Xiles
 *
 */
public class AchievementSystem {
	
	public static int AchievementBoard = 205;
 
	public static void AchievementBoard(Player player) {
		if (player.ToggleSystem = true) {
			/*
			 * Category
			 */
			player.getPackets().sendIComponentText(AchievementBoard, 61, "Mining");
			player.getPackets().sendIComponentText(AchievementBoard, 57, "Woodcutting");
			player.getPackets().sendIComponentText(AchievementBoard, 53, "The RuneSpan");
			player.getPackets().sendIComponentText(AchievementBoard, 49, "Firemaking");
			player.getPackets().sendIComponentText(AchievementBoard, 65, "Achievement Board");
			player.getPackets().sendIComponentText(AchievementBoard, 64, "Welcome to the Achievement Board." +
					" Click one of the achievements above to see your progress.");
		player.getInterfaceManager().sendInterface(AchievementBoard);
	} else {
		player.sm("You have closed the Achievement Board.");
	}
	}
	public static void sendMining(Player player) {
			player.closeInterfaces();
			player.getPackets().sendIComponentText(AchievementBoard, 61, "Mining");
			player.getPackets().sendIComponentText(AchievementBoard, 57, "Woodcutting");
			player.getPackets().sendIComponentText(AchievementBoard, 53, "The RuneSpan");
			player.getPackets().sendIComponentText(AchievementBoard, 49, "Firemaking");
			/*Title*/
			player.getPackets().sendIComponentText(AchievementBoard, 65, "Mining");
			player.getPackets().sendIComponentText(AchievementBoard, 64, "Your achievement is to mine 1000 ores of any kind. " +
															 "So far you have mined "+ player.MiningAchievement +" ores.");
			player.getInterfaceManager().sendInterface(AchievementBoard);
	} 
	public static void sendWoodcutting(Player player) {
		player.closeInterfaces();
		player.getPackets().sendIComponentText(AchievementBoard, 61, "Mining");
		player.getPackets().sendIComponentText(AchievementBoard, 57, "Woodcutting");
		player.getPackets().sendIComponentText(AchievementBoard, 53, "The RuneSpan");
		player.getPackets().sendIComponentText(AchievementBoard, 49, "Firemaking");
		/*Title*/
		player.getPackets().sendIComponentText(AchievementBoard, 65, "Woodcutting");
		player.getPackets().sendIComponentText(AchievementBoard, 64, "Your achievement is to cut 750 Ivy Vines. " +
				"So far you have cut "+ player.IvyAchievement +" Ivy Vines.");
		player.getInterfaceManager().sendInterface(AchievementBoard);
	} 
	
	public static void sendRuneSpan(Player player) {
	player.closeInterfaces();
	player.getPackets().sendIComponentText(AchievementBoard, 61, "Mining");
	player.getPackets().sendIComponentText(AchievementBoard, 57, "Woodcutting");
	player.getPackets().sendIComponentText(AchievementBoard, 53, "The RuneSpan");
	player.getPackets().sendIComponentText(AchievementBoard, 49, "Firemaking");
	/*Title*/
	player.getPackets().sendIComponentText(AchievementBoard, 65, "The RuneSpan");
	player.getPackets().sendIComponentText(AchievementBoard, 64, "Your achievement is to siphon 300 creatures from The RuneSpan. " +
			"So far you have siphoned "+ player.SiphonAchievement +" creatures.");
	player.getInterfaceManager().sendInterface(AchievementBoard);
	} 

	public static void sendFiremaking(Player player) {
	player.closeInterfaces();
	player.getPackets().sendIComponentText(AchievementBoard, 61, "Mining");
	player.getPackets().sendIComponentText(AchievementBoard, 57, "Woodcutting");
	player.getPackets().sendIComponentText(AchievementBoard, 53, "The RuneSpan");
	player.getPackets().sendIComponentText(AchievementBoard, 49, "Firemaking");
	/*Title*/
	player.getPackets().sendIComponentText(AchievementBoard, 65, "Firemaking");
	player.getPackets().sendIComponentText(AchievementBoard, 64, "Your achievement is to burn 1500 logs of any kind. " +
			"So far you have burned "+ player.BurnAchievement +" logs.");
	player.getInterfaceManager().sendInterface(AchievementBoard);
	} 
	 public static void handleButtons(Player player, int componentId) {
		  if (componentId == 49) {	
		        sendFiremaking(player);
		        }
		  if (componentId == 57) {	
		        sendWoodcutting(player);
		        }
		  if (componentId == 53) {	
		        sendRuneSpan(player);
		        }
	        if (componentId == 61) {	
	        	sendMining(player);
	        }
	 }

	
}

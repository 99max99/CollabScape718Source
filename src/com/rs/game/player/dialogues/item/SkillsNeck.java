package com.rs.game.player.dialogues.item;

import com.rs.game.player.content.LavaFlowMines;
import com.rs.Settings;
import com.rs.game.WorldTile;
import com.rs.game.minigames.rfd.RecipeforDisaster;
import com.rs.game.player.Skills;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.controlers.*;
import com.rs.game.player.controlers.dung.RuneDungGame;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

/**
 * @author Danny
 */


public class SkillsNeck extends Dialogue {

	public SkillsNeck() {
	}

	@Override
	public void start() {
		if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame){
			player.getPackets().sendGameMessage("no.");
			end();
			} else {

		stage = 1;
		sendOptionsDialogue("Elite Guilds (Level 100+)", "Fishing Guild",
				"Mining Guild", "Crafting Guild", "Cooking Guild",
				"More");
			}

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) { //Fishing
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2614, 3384, 0));
				player.sm("Welcome to the Elite Fishing Guild, "+ player.getDisplayName() + ".");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			} else if (componentId == OPTION_2) {//Mining
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3022, 3341, 0));
				player.sm("Welcome to the Elite Mining Guild, "+ player.getDisplayName() + ".");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			} else if (componentId == OPTION_3) {//Crafting
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2933, 3290, 0));
				player.sm("Welcome to the Elite Crafting Guild, "+ player.getDisplayName() + ".");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			} else if (componentId == OPTION_4) {//Cooking
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3143, 3442, 0));
				player.sm("Welcome to the Elite Cooking Guild, "+ player.getDisplayName() + ".");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
			} else if (componentId == OPTION_5) {//page 2 of teleport options
				sendOptionsDialogue("Elite Guilds (Level 100+)",
						"Herblore Guild");
				stage = 2;
			}
			
			
		} else if (stage == 3) { 
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2674, 3715, 0));
				player.sm("Welcome to Rock Crabs!");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3168, 2983, 0));
				player.sm("Welcome to the Bandit Camp!");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
		} else if (componentId == OPTION_3) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3412, 3512, 0));
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
				player.sm("Welcome to Ghouls!");
		} else if (componentId == OPTION_4) {
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3577, 9927, 0));
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			player.getControlerManager().forceStop();
			player.getControlerManager().removeControlerWithoutCheck();
				player.sm("You teleport to the experiment dungeon");
		} else if (componentId == OPTION_5) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				player.sm("Coming soon..");
		}
			
		}
	}
			@Override
			public void finish() {
			}
	

}

package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.magic.Magic;

public class MTHighLevelDungeons extends Dialogue {
	
	

	@Override
	public void start() {
		sendOptionsDialogue("High Level Dungeons/Areas",
				"God Wars Dungeon",
				"Ancient Cavern",
				"Forinthry Dungeon",
				"Frost Dragons",
				"More");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1 ) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2881, 5311, 0));
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2512, 3511, 0));
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3080, 10057, 0));
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1312, 4528, 0));
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("High Level Dungeons/Areas - Page 2",
						"Ape Atoll Temple",
						"Tirannwn Elf Camp",
						"Evil Chicken's Lair",
						"Ogre Enclave",
						"More");
				stage = 1;
			}
		} else if (stage == 1) {
			if (componentId == OPTION_1 ) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2787, 2786, 0));
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2203, 3253, 0));
			} else if (componentId == OPTION_3) {
				//Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1576, 4363, 0));
				player.sm("Disabled.");
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2589, 9411, 0));
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("High Level Dungeons/Areas - Page 2",
						"Gorak Plane",
						"Ourania Cave",
						"None");
				stage = 2;
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1 ) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3038, 5346, 0));
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3271, 4861, 0));
			} else if (componentId == OPTION_3) {
				end();
			}
		}
		 
	}

	@Override
	public void finish() {

	}
}
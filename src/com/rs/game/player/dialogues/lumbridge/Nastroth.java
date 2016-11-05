package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;

public class Nastroth extends Dialogue {

	
	
	@Override
	public void start() {
		
		if (player.getEquipment().wearingArmour()) {
			sendDialogue("Please remove your armour first.");
			stage = -2;
		} else {
		sendOptionsDialogue("Which skill do you wish to reset?", "Reset Attack",
				"Reset Strength", "Reset Defence", "Reset Ranged", "Next");
		stage = 1;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getSkills().set(Skills.ATTACK, 1);
				player.getSkills().setXp(Skills.ATTACK, Skills.getXPForLevel(1));
				player.getAppearence().generateAppearenceData();
				end();
				
			} else if (componentId == OPTION_2) {
				player.getSkills().set(Skills.STRENGTH, 1);
				player.getSkills().setXp(Skills.STRENGTH, Skills.getXPForLevel(1));
				player.getAppearence().generateAppearenceData();
				end();
				
			} else if (componentId == OPTION_3) {
				player.getSkills().set(Skills.DEFENCE, 1);
				player.getSkills().setXp(Skills.DEFENCE, Skills.getXPForLevel(1));
				player.getAppearence().generateAppearenceData();
				end();

			} else if (componentId == OPTION_4) {
				player.getSkills().set(Skills.RANGE, 1);
				player.getSkills().setXp(Skills.RANGE, Skills.getXPForLevel(1));
				player.getAppearence().generateAppearenceData();
				end();

			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Which skill do you wish to reset?", "Reset Prayer",
						"Reset Magic", "Reset Constitution", "Reset Summoning");
				stage = 2;
			}
			
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				player.getSkills().set(Skills.PRAYER, 1);
				player.getSkills().setXp(Skills.PRAYER, Skills.getXPForLevel(1));
				player.getAppearence().generateAppearenceData();
				end();
				
			} else if (componentId == OPTION_2) {
				player.getSkills().set(Skills.MAGIC, 1);
				player.getSkills().setXp(Skills.MAGIC, Skills.getXPForLevel(1));
				player.getAppearence().generateAppearenceData();
				end();
				
			} else if (componentId == OPTION_3) {
				player.getSkills().set(Skills.HITPOINTS, 10);
				player.getSkills().setXp(Skills.HITPOINTS, Skills.getXPForLevel(10));
				player.getAppearence().generateAppearenceData();
				end();
				
			} else if (componentId == OPTION_4) {
				player.getSkills().set(Skills.SUMMONING, 1);
				player.getSkills().setXp(Skills.SUMMONING, Skills.getXPForLevel(1));
				player.getAppearence().generateAppearenceData();
				end();
			}
			
		} else if (stage == -2) {
			end();
		}
	}

	@Override
	public void finish() {
	}

}
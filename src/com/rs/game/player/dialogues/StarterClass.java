package com.rs.game.player.dialogues;

import java.util.ArrayList;

import com.rs.game.player.Skills;


public class StarterClass extends Dialogue {
	
	private static ArrayList<String> ips = new ArrayList<String>();

	@Override
	public void start() {
		sendDialogue("",
				"");
		stage = 50;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 50) {
				player.isPker = false;
			    String ip = player.getSession() != null ? player.getSession().getIP() : null;
			    if (ip != null && !ips.contains(ip)) {
				ips.add(ip);
				stage = 1;
				player.lock();
				sendOptionsDialogue("Picking Your Starter Class!",
						"Warrior (Melee)",
						"Archer (Ranged)",
						"Sorcerer (Magic)",
						"Skiller (Non-Combat)");
			    } else {
					if (!player.choseGameMode) {
						stage = 50;
					sendDialogue("Please Pick your Start Class!",
							"Please note this doesnt effect whats skills you can train.");
					} else {
						player.starterstage = 3;
						player.getInterfaceManager().sendInterfaces();
						player.closeInterfaces();
						player.unlock();
						player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
						player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0, -1, false);
					}
			    }
			
		} else if (stage == 0) {
			if (componentId == OPTION_1) {
                player.getInventory().addItem(9186, 10);//Rune crossbow
                player.getInventory().addItem(9244, 1000); //Dragon Bolt (e)
                player.getInventory().addItem(6586, 10);//amulet of fury
                player.getInventory().addItem(1950, 10);//Chef hat
                player.getInventory().addItem(545, 10);//Monk robe top
                player.getInventory().addItem(5699, 10);//Dragon dagger
                player.getInventory().addItem(4588, 10);//Dragon scimitar
                player.getInventory().addItem(2498, 10);//Black D’hide chaps
                player.getInventory().addItem(565, 10000);//Blood rune
                player.getInventory().addItem(555, 10000);//Water rune
                player.getInventory().addItem(560, 10000);//Death rune
                player.getInventory().addItem(6569, 10);//obsidian cape
                player.getInventory().addItem(15273, 1000);//1k rocktails
                player.getInventory().addItem(6686, 250);//Saradomin brew (4)
                player.getInventory().addItem(3025, 250);//Super restore (4)
                player.getInventory().addItem(2441, 250);//Super strenght pot(4)
                player.getInventory().addItem(2437, 250);//super attack pot (4)
                player.getInventory().addItem(6738, 10);//Breserker ring
                player.getInventory().addItem(2443, 250);//super defence pot (4)
                player.getInventory().addItemMoneyPouch(995, 10000000);//10M cash
                //crystal
                player.getSkills().set(0, 60);
                player.getSkills().setXp(0, Skills.getXPForLevel(60));
                player.getSkills().set(2, 99);
                player.getSkills().setXp(2, Skills.getXPForLevel(99));
                player.getSkills().set(5, 53);
                player.getSkills().setXp(5, Skills.getXPForLevel(53));
                player.getSkills().set(4, 99);
                player.getSkills().setXp(4, Skills.getXPForLevel(99));
                player.getSkills().set(6, 99);
                player.getSkills().setXp(6, Skills.getXPForLevel(99));
                 player.starterstage = 3;
			} else if (componentId == OPTION_2) {
                player.getInventory().addItem(6917, 10);//Infinity robe top
                player.getInventory().addItem(6925, 10);//Infinity robe bottom
                player.getInventory().addItem(6586, 10);//amulet of fury
                player.getInventory().addItem(4676, 10);//Ancient staff
                player.getInventory().addItem(1080, 10);//Rune Platelegs 
                player.getInventory().addItem(1128, 10);//Rune Platebody
                player.getInventory().addItem(4152, 10);//abyssal whip
                player.getInventory().addItem(3752, 10);//berserker helm
                player.getInventory().addItem(5699, 10);//Dragon dagger
                player.getInventory().addItem(2498, 10);//Black D'hide chaps
                player.getInventory().addItem(2504, 10);//Black D'hide body
                player.getInventory().addItem(555, 10000);//water rune
                player.getInventory().addItem(565, 10000);//blood rune
                player.getInventory().addItem(9075, 10000);//Astral rune
                player.getInventory().addItem(557, 10);// Earth rune                
                player.getInventory().addItem(560, 10000);//Death rune
                player.getInventory().addItem(6569, 10);//obsidian cape
                player.getInventory().addItem(15273, 1000);//1k rocktails
                player.getInventory().addItem(6686, 250);//Saradomin brew (4)
                player.getInventory().addItem(3025, 250);//Super restore (4)
                player.getInventory().addItem(2441, 250);//Super strenght pot(4)
                player.getInventory().addItem(2437, 250);//super attack pot (4)
                player.getInventory().addItem(6738, 10);//Breserker ring
                player.getInventory().addItem(2443, 250);//super defence pot (4)
                player.getInventory().addItemMoneyPouch(995, 10000000);//10M cash
                //crystal 
                player.getSkills().set(0, 99);
                player.getSkills().setXp(0, Skills.getXPForLevel(99));
                player.getSkills().set(1, 45);
                player.getSkills().setXp(1, Skills.getXPForLevel(45));
                player.getSkills().set(2, 99);
                player.getSkills().setXp(2, Skills.getXPForLevel(99));
                player.getSkills().set(3, 99);
                player.getSkills().setXp(3, Skills.getXPForLevel(99));
                player.getSkills().set(4, 99);
                player.getSkills().setXp(4, Skills.getXPForLevel(99));
                player.getSkills().set(5, 95);
                player.getSkills().setXp(5, Skills.getXPForLevel(99));
                player.getSkills().set(6, 99);
                player.getSkills().setXp(6, Skills.getXPForLevel(99));
                player.starterstage = 3;
			} else if (componentId == OPTION_3) {
                player.getInventory().addItem(4152, 10);//abyssal whip         
                player.getInventory().addItem(1080, 10);//Rune platelegs
                player.getInventory().addItem(1128, 10);//Rune platebody
                player.getInventory().addItem(10843, 10);//Helm of Nietiznot
                player.getInventory().addItem(6586, 10);//Amulet of fury
                player.getInventory().addItem(11733, 10);//Dragon boots
                player.getInventory().addItem(5699, 10);//Dragon dagger(p)
                player.getInventory().addItem(560, 5000);// Death rune
                player.getInventory().addItem(557, 10000);//Earth rune
                player.getInventory().addItem(9075, 5000);//Astral rune
                player.getInventory().addItem(6569, 10);//obsidian cape
                player.getInventory().addItem(15273, 1000);//1k rocktails
                player.getInventory().addItem(6686, 250);//Saradomin brew (4)
                player.getInventory().addItem(3025, 250);//Super restore (4)
                player.getInventory().addItem(2441, 250);//Super strenght pot(4)
                player.getInventory().addItem(2437, 250);//super attack pot (4)
                player.getInventory().addItem(6738, 10);//Breserker ring
                player.getInventory().addItem(2443, 250);//super defence pot (4)
                player.getInventory().addItemMoneyPouch(995, 10000000);//10M cash
                //crystal 
                player.getSkills().set(0, 99);
                player.getSkills().setXp(0, Skills.getXPForLevel(99));
                player.getSkills().set(1, 99);
                player.getSkills().setXp(1, Skills.getXPForLevel(99));
                player.getSkills().set(2, 99);
                player.getSkills().setXp(2, Skills.getXPForLevel(99));
                player.getSkills().set(3, 99);
                player.getSkills().setXp(3, Skills.getXPForLevel(99));
                player.getSkills().set(4, 99);
                player.getSkills().setXp(4, Skills.getXPForLevel(99));
                player.getSkills().set(5, 99);
                player.getSkills().setXp(5, Skills.getXPForLevel(99));
                player.getSkills().set(6, 99);
                player.getSkills().setXp(6, Skills.getXPForLevel(99));
                player.getSkills().set(23, 99);
                player.getSkills().setXp(23, Skills.getXPForLevel(99));
                player.starterstage = 3;
			} else if (componentId == OPTION_4) {
                player.getInventory().addItem(4102, 10);//Mystic robe top
                player.getInventory().addItem(4104, 10);//Mystic robe bottom
                player.getInventory().addItem(6586, 10);//amulet of fury
                player.getInventory().addItem(4676, 10);//ancient staff
                player.getInventory().addItem(1080, 10);//Rune Platelegs 
                player.getInventory().addItem(1128, 10);//Rune Platebody
                player.getInventory().addItem(4152, 10);//abyssal whip
                player.getInventory().addItem(10843, 10);//Helm of neitzinot
                player.getInventory().addItem(5699, 10);//Dragon dagger
                player.getInventory().addItem(2504, 10);//Black D’hide body
                player.getInventory().addItem(2498, 10);//Black D’hide chaps
                player.getInventory().addItem(565, 10000);//Blood rune
                player.getInventory().addItem(555, 10000);//Water rune
                player.getInventory().addItem(560, 10000);//Death rune
                player.getInventory().addItem(6569, 10);//obsidian cape
                player.getInventory().addItem(15273, 1000);//1k rocktails
                player.getInventory().addItem(6686, 250);//Saradomin brew (4)
                player.getInventory().addItem(3025, 250);//Super restore (4)
                player.getInventory().addItem(2441, 250);//Super strenght pot(4)
                player.getInventory().addItem(2437, 250);//super attack pot (4)
                player.getInventory().addItem(6738, 10);//Breserker ring
                player.getInventory().addItem(2443, 250);//super defence pot (4)
                player.getInventory().addItemMoneyPouch(995, 10000000);//10M cash
                //crystal 
                player.getSkills().set(0, 60);
                player.getSkills().setXp(0, Skills.getXPForLevel(60));
                player.getSkills().set(1, 75);
                player.getSkills().setXp(1, Skills.getXPForLevel(75));
                player.getSkills().set(2, 90);
                player.getSkills().setXp(2, Skills.getXPForLevel(90));
                player.getSkills().set(3, 99);
                player.getSkills().setXp(3, Skills.getXPForLevel(99));
                player.getSkills().set(4, 90);
                player.getSkills().setXp(4, Skills.getXPForLevel(90));
                player.getSkills().set(5, 70);
                player.getSkills().setXp(5, Skills.getXPForLevel(70));
                player.getSkills().set(6, 94);
                player.getSkills().setXp(6, Skills.getXPForLevel(94));
                player.starterstage = 3;
			} else if (componentId == OPTION_5) {
                player.getInventory().addItem(1164, 10);//rune full helmet
                player.getInventory().addItem(3134, 10);//granite shield
                player.getInventory().addItem(6586, 10);//amulet of fury
                player.getInventory().addItem(9186, 10);//rune crossbow
                player.getInventory().addItem(1080, 10);//Rune Platelegs 
                player.getInventory().addItem(9244, 1000);//Dragon bolt (e)
                player.getInventory().addItem(2504, 10);//Black D’hide body
                player.getInventory().addItem(11236, 10);//dark bow
                player.getInventory().addItem(11212, 10);//Dragon arrow
                player.getInventory().addItem(9075, 10000);//Astral rune
                player.getInventory().addItem(557, 10);// Earth rune                
                player.getInventory().addItem(560, 10000);//Death rune
                player.getInventory().addItem(6569, 10);//obsidian cape
                player.getInventory().addItem(15273, 1000);//1k rocktails
                player.getInventory().addItem(6686, 250);//Saradomin brew (4)
                player.getInventory().addItem(3025, 250);//Super restore (4)
                player.getInventory().addItem(2441, 250);//Super strenght pot(4)
                player.getInventory().addItem(2437, 250);//super attack pot (4)
                player.getInventory().addItem(6738, 10);//Breserker ring
                player.getInventory().addItem(2443, 250);//super defence pot (4)
                player.getInventory().addItemMoneyPouch(995, 10000000);//10M cash
                //crystal 
                player.getSkills().set(0, 75);
                player.getSkills().setXp(0, Skills.getXPForLevel(75));
                player.getSkills().set(1, 99);
                player.getSkills().setXp(1, Skills.getXPForLevel(99));
                player.getSkills().set(2, 75);
                player.getSkills().setXp(2, Skills.getXPForLevel(75));
                player.getSkills().set(3, 99);
                player.getSkills().setXp(3, Skills.getXPForLevel(99));
                player.getSkills().set(4, 99);
                player.getSkills().setXp(4, Skills.getXPForLevel(99));
                player.getSkills().set(5, 70);
                player.getSkills().setXp(5, Skills.getXPForLevel(70));
                player.getSkills().set(6, 99);
                player.getSkills().setXp(6, Skills.getXPForLevel(99));
                player.getSkills().set(23, 99);
                player.getSkills().setXp(23, Skills.getXPForLevel(99));
                player.starterstage = 3;
			}
			player.getInterfaceManager().sendInterfaces();
			player.closeInterfaces();
			player.unlock();
			player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
			player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0, -1, false);
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
                 player.getInventory().addItem(11814, 1);//Bronze Armour Set
                 player.getInventory().addItem(11834, 1);//addy Armour Set
                 player.getInventory().addItem(6568, 1);//Obby Cape
                 player.getInventory().addItem(1725, 1);//Amulet of strength
                 player.getInventory().addItem(1321, 1);//Bronze Scimitar
                 player.getInventory().addItem(1333, 1);//Rune Scimitar
                 player.getInventory().addItem(4587, 1);//Dragon Scimitar
                 player.getInventory().addItem(386, 250);//Shark
                 player.getInventory().addItem(15273, 100);//Rocktail
                 player.getInventory().addItem(2435, 15);//Prayer Potions
                 player.getInventory().addItem(2429, 10);//Attack Potions
                 player.getInventory().addItem(114, 10);//strength Potions
                 player.getInventory().addItem(2433, 10);//Defence Potions
                 player.getInventory().addItemMoneyPouch(995, 500000);//2.5m cash
					player.starterstage = 3;
					player.getInterfaceManager().sendInterfaces();
					player.closeInterfaces();
					player.unlock();
					player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
					player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0, -1, false);
                 //Crystal
                 player.starterstage = 3;
			} else if (componentId == OPTION_2) {
				player.getInventory().addItem(11864, 1);//Green Dhide Set
				player.getInventory().addItem(26777, 1);//Ava's Attractor
				player.getInventory().addItem(1478, 1);//Amulet of Accuracy
                player.getInventory().addItem(1061, 1);//Leather boots
                player.getInventory().addItem(1129, 1);//leather Body
                player.getInventory().addItem(1095, 1);//Leather Chaps
                player.getInventory().addItem(1063, 1);//Leather vambs
                player.getInventory().addItem(841, 1);//Shortbow
                player.getInventory().addItem(861, 1);//Magic Shortbow
                player.getInventory().addItem(882, 500);//bronze arrows
                player.getInventory().addItem(386, 250);//Shark
                player.getInventory().addItem(15273, 100);//Rocktail
                player.getInventory().addItem(2435, 15);//Prayer Potions
                player.getInventory().addItem(2445, 10);//Range Potions
                player.getInventory().addItem(2433, 10);//Defence Potions  
                player.getInventory().addItemMoneyPouch(995, 500000);//cash
				player.starterstage = 3;
				player.getInterfaceManager().sendInterfaces();
				player.closeInterfaces();
				player.unlock();
				player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
				player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0, -1, false);
                //Crystal
                player.starterstage = 3;
			} else if (componentId == OPTION_3) {
                player.getInventory().addItem(11872, 1);//Mystic Set
                player.getInventory().addItem(2412, 1);//Sardomin Cape
                player.getInventory().addItem(1727, 1);//Amulet of Magic
                player.getInventory().addItem(577, 1);//Wizard Robe top
                player.getInventory().addItem(1011, 1);//Wizard skirt
                player.getInventory().addItem(579, 1);//Wizard Hat
                player.getInventory().addItem(1389, 1);//Staff
                player.getInventory().addItem(556, 500);//Air Runes
                player.getInventory().addItem(555, 500);//Water Runes
                player.getInventory().addItem(554, 500);//Fire runes
                player.getInventory().addItem(557, 500);//Earth Runes
                player.getInventory().addItem(558, 500);//Mind Runes
                player.getInventory().addItem(562, 500);//Chaos Runes
                player.getInventory().addItem(386, 250);//Shark
                player.getInventory().addItem(15273, 100);//Rocktail
                player.getInventory().addItem(2435, 15);//Prayer Potions
                player.getInventory().addItem(3041, 10);//Magic Potions
                player.getInventory().addItem(2433, 10);//Defence Potions
                player.getInventory().addItemMoneyPouch(995, 500000);//cash
				player.starterstage = 3;
				player.getInterfaceManager().sendInterfaces();
				player.closeInterfaces();
				player.unlock();
				player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
				player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0, -1, false);
                //Crystal
                player.starterstage = 3;
			} else if (componentId == OPTION_4) {
                player.getInventory().addItem(1949, 1);//Chef's Hat
                player.getInventory().addItem(6180, 1);//Lederhosen Top
                player.getInventory().addItem(6181, 1);//Lederhosen Shorts
                player.getInventory().addItem(6182, 1);//Lederhosen Hat
                player.getInventory().addItem(1351, 1);//bronze axe
                player.getInventory().addItem(1265, 1);//bronze Pickaxe
                player.getInventory().addItem(303, 1);//Fishing net
                player.getInventory().addItem(10006, 1);//Bird snare
                player.getInventory().addItem(2347, 1);//Hammer
                player.getInventory().addItem(590, 1);//Tinderbox
                player.getInventory().addItem(1733, 1);//Needle
                player.getInventory().addItem(1734, 100);//Thread
                player.getInventory().addItem(946, 1);//Knife
                player.getInventory().addItem(1755, 1);//Chisel
                player.getInventory().addItem(233, 1);//Pestle and mortar
                player.getInventory().addItem(1512, 50);//Logs
                player.getInventory().addItem(2350, 50);//Bronze Bar
                player.getInventory().addItem(228, 50);//Vial of water
                player.getInventory().addItem(1626, 50);//Opals
                player.getInventory().addItemMoneyPouch(995, 500000);//cash
				player.starterstage = 3;
				player.getInterfaceManager().sendInterfaces();
				player.closeInterfaces();
				player.unlock();
				player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
				player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0, -1, false);
                //Crystal
                player.starterstage = 3;
			}
			if (!player.choseGameMode) {
				stage = 50;
			} else {
				player.starterstage = 3;
				player.getInterfaceManager().sendInterfaces();
				player.closeInterfaces();
				player.unlock();
				player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
				player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0,
						-1, false);
			}
		}		else if (stage == 2) {
		
			
			player.getInterfaceManager().sendInterfaces();
			player.closeInterfaces();
			player.unlock();
			player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
			player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0,
					-1, false);
	}       else if (stage == 3) {
		if (componentId == OPTION_1) {
			player.gameMode = 0;
			player.sm("You are now playing on regular mode, if you chose a harder difficulty, you can unlock more content.");

		}
		player.starterstage = 3;
		player.getInterfaceManager().sendInterfaces();
		player.closeInterfaces();
		player.unlock();
		player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
		player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0,
				-1, false);
		}
		 
	}

	@Override
	public void finish() {

	}
}

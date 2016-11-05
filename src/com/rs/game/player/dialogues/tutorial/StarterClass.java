package com.rs.game.player.dialogues.tutorial;

import com.rs.game.player.dialogues.Dialogue;


public class StarterClass extends Dialogue {
	
	

	@Override
	public void start() {
		stage = 1;
		player.lock();
		sendOptionsDialogue("Picking Your Starter Class!",
				"Warrior (Melee)",
				"Archer (Ranged)",
				"Sorcerer (Magic)",
				"Skiller (Non-Combat)");
		
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case 1:
			if (componentId == OPTION_1) {
				player.getInterfaceManager().sendInterfaces();
				player.closeInterfaces();
				player.unlock();
				player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
				player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0,
						-1, false);
                 player.getInventory().addItem(11814, 1);//Bronze Armour Set
                 player.getInventory().addItem(11834, 1);//addy Armour Set
                 player.getInventory().addItem(6568, 1);//Obby Cape
                 player.getInventory().addItem(1725, 1);//Amulet of strength
                 player.getInventory().addItem(7142, 1);//Rapier
                 player.getInventory().addItem(4587, 1);//Dragon Scimitar
                 player.getInventory().addItem(386, 250);//Shark
                 player.getInventory().addItem(15273, 100);//Rocktail
                 player.getInventory().addItem(2435, 15);//Prayer Potions
                 player.getInventory().addItem(2429, 10);//Attack Potions
                 player.getInventory().addItem(114, 10);//strength Potions
                 player.getInventory().addItem(2433, 10);//Defence Potions
                 player.getInventory().addItemMoneyPouch(995, 2500000);//2.5m cash
                 //player.getInventory().addItem(6099, 1);//Crystal
                 player.starterstage = 3;
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().sendInterfaces();
				player.closeInterfaces();
				player.unlock();
				player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
				player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0,
						-1, false);
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
                player.getInventory().addItemMoneyPouch(995, 2500000);//2.5m cash
               // player.getInventory().addItem(6099, 1);//Crystal
                player.starterstage = 3;
			} else if (componentId == OPTION_3) {
				player.getInterfaceManager().sendInterfaces();
				player.closeInterfaces();
				player.unlock();
				player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
				player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0,
						-1, false);
                player.getInventory().addItem(11902, 1);//Enchanted Set
                player.getInventory().addItem(11872, 1);//Mystic Set
                player.getInventory().addItem(2412, 1);//Sardomin Cape
                player.getInventory().addItem(1727, 1);//Amulet of Magic
                player.getInventory().addItem(577, 1);//Wizard Robe top
                player.getInventory().addItem(1011, 1);//Wizard skirt
                player.getInventory().addItem(579, 1);//Wizard Hat
                player.getInventory().addItem(2579, 1);//Wizard Boots
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
                player.getInventory().addItemMoneyPouch(995, 2500000);//2.5m cash
               // player.getInventory().addItem(6099, 1);//Crystal
                player.starterstage = 3;
			} else if (componentId == OPTION_4) {
				player.getInterfaceManager().sendInterfaces();
				player.closeInterfaces();
				player.unlock();
				player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
				player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0,
						-1, false);
                player.getInventory().addItem(1949, 1);//Chef's Hat
                player.getInventory().addItem(3844, 1);//Book of Balance
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
                player.getInventory().addItemMoneyPouch(995, 2500000);//2.5m cash
               // player.getInventory().addItem(6099, 1);//Crystal
                player.starterstage = 3;
			}
			break;
		case 2:
		
			
			player.getInterfaceManager().sendInterfaces();
			player.closeInterfaces();
			player.unlock();
			player.getPackets().sendGameMessage("You may now enter the realm of Noszscape through this portal");
			player.getHintIconsManager().addHintIcon(1348, 5199, 0, 100, 0, 0,
					-1, false);
			break;
		}
		 
	}

	@Override
	public void finish() {

	}
}

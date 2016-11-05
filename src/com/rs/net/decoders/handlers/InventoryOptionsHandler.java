package com.rs.net.decoders.handlers;

import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import com.rs.Settings;
import com.rs.game.player.content.LividFarm;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cores.CoresManager;
import com.rs.cores.WorldThread;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.npc.pet.Pet;
import com.rs.game.player.ClueScrolls;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.BoltTipFletching;
import com.rs.game.player.actions.BoltTipFletching.BoltTips;
import com.rs.game.player.actions.BoxAction;
import com.rs.game.player.actions.BoxAction.HunterEquipment;
import com.rs.game.player.actions.Firemaking;
import com.rs.game.player.actions.Fletching;
import com.rs.game.player.actions.Fletching.Fletch;
import com.rs.game.player.actions.GemCutting;
import com.rs.game.player.actions.GemCutting.Gem;
import com.rs.game.player.actions.HerbCleaning;
import com.rs.game.player.actions.Herblore;
import com.rs.game.player.actions.LeatherCrafting;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.actions.Summoning.Pouches;
import com.rs.game.player.actions.divination.DivinePlacing;
import com.rs.game.player.actions.divination.HarvestWisp;
import com.rs.game.player.content.AncientEffigies;
import com.rs.game.player.content.ArmourSets;
import com.rs.game.player.content.ArmourSets.Sets;
import com.rs.game.player.content.Burying.Bone;
import com.rs.game.player.content.ImplingLoot;
import com.rs.game.player.content.RepairItems.BrokenItems;
import com.rs.game.player.content.Scattering.Ash;
import com.rs.game.player.content.WeaponPoison;
import com.rs.game.player.content.farming.PatchConstants;
import com.rs.game.player.content.magic.Alchemy;
import com.rs.game.player.content.magic.Enchanting;
import com.rs.game.player.content.magic.Lunars;
import com.rs.game.player.content.Dicing;
import com.rs.game.player.content.Foods;
import com.rs.game.player.content.FriendChatsManager;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.content.Pots;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.controlers.Barrows;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.SorceressGarden;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.player.content.ToyHorsey;

import java.text.DecimalFormat;

import com.rs.io.InputStream;
import com.rs.utils.Logger;
import com.rs.utils.Misc;
import com.rs.utils.Utils;
import com.rs.game.player.LendingManager;
import com.rs.game.player.content.Lend;
import com.rs.game.player.content.Slayer;
import com.rs.game.npc.others.ConditionalDeath;
import com.rs.game.player.content.ItemSets;
import com.rs.game.player.content.JewllerySmithing;
import com.rs.game.player.content.Lamps;
import com.rs.game.player.dialogues.SqirkFruitSqueeze;
import com.rs.game.player.dialogues.SqirkFruitSqueeze.SqirkFruit;
import com.rs.game.player.dialogues.LeatherCraftingD;
import com.rs.game.player.MoneyPouch;

public class InventoryOptionsHandler {

	public static void handleItemOption2(final Player player, final int slotId,
			final int itemId, Item item) {
		if (Firemaking.isFiremaking(player, itemId))
			return;
		if (itemId == 4155) {
		    player.getSlayerManager().checkKillsLeft();
		}
		if (itemId == 6583 || itemId == 7927) {
	    JewllerySmithing.ringTransformation(player, itemId);
		}
		if (itemId == 18338) {
			if (player.sapphires <= 0 && player.rubies <= 0 && player.emeralds <= 0 && player.diamonds <= 0) {
				player.sm("Your gem pouch is currently empty.");
			} else {
				player.getInventory().addItem(1622, player.emeralds);
				player.getInventory().addItem(1624, player.sapphires);
				player.getInventory().addItem(1620, player.rubies);
				player.getInventory().addItem(1619, player.diamonds);
				player.emeralds = 0;
				player.sapphires = 0;
				player.rubies = 0;
				player.diamonds = 0;
				player.sm("You have successfully emptied your gem bag.");
			}
		}

		if (itemId == 18339) {
			if (player.coal <= 0) {
				player.sm("Your coal pouch is currently empty.");
			} else {
				player.getInventory().addItem(453, 1);
				player.coal--;
				player.sm("You have successfully taken a piece of coal from your coal bag.");
			}
		}
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			if (itemId == 5509)
				pouch = 0;
			if (itemId == 5510)
				pouch = 1;
			if (itemId == 5512)
				pouch = 2;
			if (itemId == 5514)
				pouch = 3;
			Runecrafting.emptyPouch(player, pouch);
			player.stopAll(false);
		} else if (itemId == 29981) {
			player.setHitpoints(Short.MAX_VALUE);
		} else if (itemId == 15262)
			    ItemSets.openSkillPack(player, itemId, 12183, 5000, player.getInventory().getAmountOf(itemId));
			else if (itemId == 15362)
			    ItemSets.openSkillPack(player, itemId, 230, 50, player.getInventory().getAmountOf(itemId));
			else if (itemId == 15363)
			    ItemSets.openSkillPack(player, itemId, 228, 50, player.getInventory().getAmountOf(itemId));
			else if (itemId == 15364)
			    ItemSets.openSkillPack(player, itemId, 222, 50, player.getInventory().getAmountOf(itemId));
			else if (itemId == 15365)
			    ItemSets.openSkillPack(player, itemId, 9979, 50, player.getInventory().getAmountOf(itemId));
		else {
			if (player.isEquipDisabled())
				return;
			long passedTime = Utils.currentTimeMillis()
					- WorldThread.LAST_CYCLE_CTM;
			WorldTasksManager.schedule(new WorldTask() {

				@Override
				public void run() {
					List<Integer> slots = player.getSwitchItemCache();
					int[] slot = new int[slots.size()];
					for (int i = 0; i < slot.length; i++)
						slot[i] = slots.get(i);
					player.getSwitchItemCache().clear();
					ButtonHandler.sendWear(player, slot);
					player.stopAll(false, true, false);
				}
			}, passedTime >= 600 ? 0 : passedTime > 330 ? 1 : 0);
			if (player.getSwitchItemCache().contains(slotId))
				return;
			player.getSwitchItemCache().add(slotId);
		}
	}
	
	public static void dig(final Player player) {
		player.resetWalkSteps();
		player.setNextAnimation(new Animation(830));
		player.lock();
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				player.unlock();
				if (Barrows.digIntoGrave(player))
					return;
				if(player.getX() == 3005 && player.getY() == 3376
						|| player.getX() == 2999 && player.getY() == 3375
						|| player.getX() == 2996 && player.getY() == 3377
						|| player.getX() == 2989 && player.getY() == 3378
						|| player.getX() == 2987 && player.getY() == 3387
						|| player.getX() == 2984 && player.getY() == 3387) {
					//mole
					player.setNextWorldTile(new WorldTile(1752, 5137, 0));
					player.getPackets().sendGameMessage("You seem to have dropped down into a network of mole tunnels.");
					return;
					
				}
				if (player.getX() >= 2747 && player.getX() <= 2750
						&& player.getY() >= 3733 && player.getY() <= 3736) {
					//Dungeon brine rats
					player.setNextWorldTile(new WorldTile(2697, 10120, 0));
					player.getPackets().sendGameMessage("You seem to have dropped down into a strange cave.");
					return;
					
				}
				if (ClueScrolls.digSpot(player)) {
					return;
				}
				player.getPackets().sendGameMessage("You find nothing.");
			}
			
		});
	}

	public static void handleItemOption1(final Player player, final int slotId,
			final int itemId, Item item) {
		for (int i : ClueScrolls.ScrollIds) {
			if (itemId == i) {
				if (ClueScrolls.Scrolls.getMap(itemId) != null) {
					ClueScrolls.showMap(player,
							ClueScrolls.Scrolls.getMap(itemId));
					return;
				}
				if (ClueScrolls.Scrolls.getObjMap(itemId) != null) {
					ClueScrolls.showObjectMap(player,
							ClueScrolls.Scrolls.getObjMap(itemId));
					return;
				}
				if (ClueScrolls.Scrolls.getRiddles(itemId) != null) {
					ClueScrolls.showRiddle(player,
							ClueScrolls.Scrolls.getRiddles(itemId));
					return;
				}
			}

		}
		Pouches pouches = Pouches.forId(itemId);
		if (pouches != null) {
			if (player.getUsername().equalsIgnoreCase("richie")) {
			player.sm("You have lost the rights to summon familiars...");
			} else {
			Summoning.spawnFamiliar(player, pouches);
			}
		}
		
			if (itemId == 2700 || itemId == 13080 || itemId == 13010 || itemId == 19064) {
				if (!player.finishedClue) {
				if (itemId == 2700)
					player.clueLevel = 0;
				else if (itemId == 13080)
					player.clueLevel = 1;
				else if (itemId == 13010)
					player.clueLevel = 2;
				else if (itemId == 19064)
					player.clueLevel = 3;
				player.getInventory().deleteItem(itemId, 1);
				player.getInventory().addItem(2717, 1);
				player.clueChance = 0;
				player.finishedClue = true;
				} else {
				player.sm("You must finish your current clue in order to start a new one.");
				}
				return;
			}
		if (itemId == 2717) {
			ClueScrolls.giveReward(player);
		}

		Ash ash = Ash.forId(itemId);
		if (ash != null) {
			Ash.scatter(player, slotId);
			return;
		}
		int leatherIndex = LeatherCraftingD.getIndex(item.getId());
		if (leatherIndex != -1) {
		    player.getDialogueManager().startDialogue("LeatherCraftingD", leatherIndex);
		    return;
		}
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		if (Foods.eat(player, item, slotId))
			return;
		if (itemId >= 15086 && itemId <= 15100) {
			Dicing.handleRoll(player, itemId, false); 
			return;
		}
		// DIVINATION BLOON CREATEION
		if(itemId == 29313) {
			HarvestWisp.createboon(player, 29313, 100, 29373, 6, 10);
		}
		if(itemId == 29190) {
			HarvestWisp.createboon(player, 29190, 300, 29374, 10, 20);
		}
		if(itemId == 29315) {
			HarvestWisp.createboon(player, 29315, 500, 29375, 14, 30);
		}
		if(itemId == 29316) {
			HarvestWisp.createboon(player, 29316, 600, 29376, 18, 40);
		}
		if(itemId == 29193) {
			HarvestWisp.createboon(player, 29193, 800, 29377, 22, 50);
		}
		if(itemId == 29194) {
			HarvestWisp.createboon(player, 29194, 1000, 29378, 26, 60);
		}
		if(itemId == 29195) {
			HarvestWisp.createboon(player, 29195, 1250, 29379, 30, 70);
		}
		if(itemId == 29196) {
			HarvestWisp.createboon(player, 29196, 1500, 29380, 34, 80);
		}
		if(itemId == 29197) {
			HarvestWisp.createboon(player, 29197, 1750, 29381, 38, 85);
		}
		if(itemId == 29198) {
			HarvestWisp.createboon(player, 29198, 2000, 29382, 42, 90);
		}
		if(itemId == 29323) {
			HarvestWisp.createboon(player, 29323, 2250, 29383, 46, 95);
		}
		if (itemId == 29324) {
			HarvestWisp.createboon(player, 29324, 2250, 29383, 46, 95);
		}
		if (itemId == 4613) {
			player.setNextAnimation(new Animation(1902));
			player.spinTimer = 8;
			World.spinPlate(player);
		}
		if (itemId == 15073) {
			if (player.isPker) {
				player.getPackets().sendGameMessage("Pkers cannot use the Assassin's Skill.");
				return;
			}
			player.getInterfaceManager().sendAssassin();
		}
		if (itemId == 20121 || itemId == 20122 || itemId == 20123 || itemId == 20124) {
			if (player.getInventory().containsItem(20121, 1) && player.getInventory().containsItem(20122, 1)
					&& player.getInventory().containsItem(20123, 1) && player.getInventory().containsItem(20124, 1)) {
			player.getInventory().deleteItem(20121, 1);
			player.getInventory().deleteItem(20122, 1);
			player.getInventory().deleteItem(20123, 1);
			player.getInventory().deleteItem(20124, 1);
			player.getInventory().addItem(20120, 1);
			player.getPackets().sendGameMessage("You place the parts together to create a Frozen Key.");
			} else {
			player.getPackets().sendGameMessage("You need all four parts to create the key.");	
			}
		}
		if (itemId == 10952) {
	    if (Slayer.isUsingBell(player))
		return;
		}
		if (itemId == 12844) {
			player.setNextAnimation(new Animation(8990, 0));
		}
		if (itemId == 6199) {
			Random r = new Random();
			int RandomItems[] = {10548, 10551,4151,6655,6656,6654,10055, 10057, 10059, 10061, 10063, 10065, 10067,4012,4012,4012,4012,4012,4012,4012,4012};
			//int[] RandomItems = {6654, 6655, 6656, 10053, 10055, 10057, 10059, 10061, 10063, 10065, 10067, 6180, 6181, 6182, 6188, 6184, 6185, 6186, 6187, 3057, 3058, 3059, 3060, 3061, 8950, 2997, }; //Other ids go in there as well
			player.getInventory().deleteItem(6199, 1);
			player.getInventory().addItem(RandomItems[r.nextInt(RandomItems.length)], 1);
			//World.sendWorldMessage(Utils.formatPlayerNameForDisplay(player.getUsername()) + " <img=3><shad=7401DF> has just opened a godwars box</shad> <img=3>", false);
			player.getPackets().sendGameMessage("You've recieved an item from the Mystery Box!");
			return;
		}
		
		if (itemId == 18768) {	
			Random t = new Random();
			Random a = new Random();
			int RandomItems[] = {1514, 1516, 537, 15271, 384, 3001, 218, 2482, 2999, 220, 208, 454, 452, 450};
			int RandomAmount[] = {25,50,100,125,150,175,200,225,250,275,300,325,350,375,400};

			if(player.setskillbox == 10){
				player.getInventory().deleteItem(18768, 1);
			player.getInventory().addItem(RandomItems[t.nextInt(RandomItems.length)], RandomAmount[a.nextInt(RandomAmount.length)]);
			player.getPackets().sendGameMessage("You've recieved a reward from the Mystery Box!");
			player.getInventory().deleteItem(18768, 1);
			player.setskillbox = 0;
			} else if (player.setskillbox == 100){
				Random b = new Random();
				int RandomItemss[] = {4151, 6585, 6737, 6735, 6733, 6731, 11732, 4716, 4720, 4722, 4718, 4732, 4736, 4738,4734,4708,4712,4714,4710,4745,4749,4751,4747,4724,4728,4730,4726,4753,4757,4759,4755,10828,1128,1080,4132,4588,5680,4087,4585,1149,1187,6569,6524};
				player.getInventory().deleteItem(18768, 1);
				player.getInventory().addItem(RandomItemss[b.nextInt(RandomItemss.length)], 1);
				player.getPackets().sendGameMessage("You've recieved a reward from the Mystery Box!");
				player.setskillbox = 0;
			}
			return;
		}
		int randomnesttt = Utils.getRandom(34);
		int[] randomnestt = {14589, 5100,5099,5098,
				5097,5096, 5321, 5323, 5320, 
				5322, 5324, 5319, 5318, 5291, 
				5292, 5293, 5294, 5295, 5296, 
				5297, 5298, 14870, 5299, 5300, 
				5301, 5302, 5303, 5304, 21621
				, 5374, 5373, 5372, 5371, 5370};
		int randomnestID = randomnestt[randomnesttt];
		if (itemId == 5070) {
			//player.sendMessage("Test");
			player.getInventory().deleteItem(5070, 1);
			player.getInventory().addItem(randomnestID, 3);
		}
		if (itemId == 2520 || itemId == 2522 || itemId == 2524 || itemId == 2526) {
			ToyHorsey.play(player);
		}
		if (itemId == 6950) {
			player.getDialogueManager().startDialogue("LividOrb");
		}
		if (itemId == 18336) {
			player.sm("Keep this in your inventory while farming for a chance to recieve seeds back.");
		}
		if (itemId == 19670) {
			player.sm("Keep this in your inventory while smithing for a chance on recieving an extra bar.");
		}
		if (itemId == 19890) {
			player.sm("Keep this in your inventory while doing herblore for a chance of not using your secondary.");
		}
		if (itemId == 15262) {
			if (!player.getInventory().containsOneItem(12183) && !player.getInventory().hasFreeSlots()) {
				player.getPackets().sendGameMessage("You don't have enough space in your inventory.");
				return;
			}
			player.getInventory().deleteItem(15262, 1);
			player.getInventory().addItem(12183, 5000);
		}
		if (itemId == 20704) {
		LividFarm.bunchPlants(player);
		}
		if (itemId == 771) {// Dramen branch
		player.getInventory().deleteItem(771, 1);
		player.getInventory().addItem(772, 1);
		player.getInventory().refresh();
                return;
                }
		if (itemId == 18338) {
			player.sm("You currently have:");
			player.sm(""+player.sapphires+"xSapphires");
			player.sm(""+player.emeralds+"xEmeralds");
			player.sm(""+player.rubies+"xRubies");
			player.sm(""+player.diamonds+"xDiamonds");
		}
		if (itemId == 18339) {
			player.sm("You currently have "+player.coal+" pieces of coal in your coal bag.");
		}
		if (itemId == 6542)
			player.getPackets().sendGameMessage("I can't open this present, this is for the imp!");
		if (Pots.pot(player, item, slotId))
			return;
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			if (itemId == 5509)
				pouch = 0;
			if (itemId == 5510)
				pouch = 1;
			if (itemId == 5512)
				pouch = 2;
			if (itemId == 5514)
				pouch = 3;
			Runecrafting.fillPouch(player, pouch);
			return;
		}
		if (itemId == 22370) {
			Summoning.openDreadnipInterface(player);
		}
		if (itemId == 11949) { //GLOBE SNOWGLOBE
			player.lock(3);
					World.spawnObject(
							new WorldObject(28297, 10, 0,
									player.getX(), player.getY(), player
									.getPlane()), true);
			player.setNextAnimation(new Animation(1745));
			player.getInterfaceManager().sendInterface(659);
			player.getInventory().addItem(11951, 27);
			player.sm("The snow globe fills your inventory with snow!");
		}
		
		if (itemId == 1535 || itemId == 1536 || itemId == 1537) { //Map Pieces
			if (player.getInventory().containsItem(1535, 1) && player.getInventory().containsItem(1536, 1) && player.getInventory().containsItem(1537, 1)) {
				player.getInventory().deleteItem(1535, 1);
				player.getInventory().deleteItem(1536, 1);
				player.getInventory().deleteItem(1537, 1);
				player.getInventory().addItem(1538, 1);
				player.sm("You fit the pieces together and make map!");
			} else {
				player.sm("You need all three pieces to create a map!");	
			}
		}
		

		if (itemId == 952) {// spade
			dig(player);
			return;
		}
		if (itemId == 6) //Cannon
			player.getDwarfCannon().checkLocation();
		if (itemId == 20494) {//Gold Cannon
			player.getDwarfCannon().checkGoldLocation();
		}
		if (itemId == 20498) {//Royal Cannon

			player.getDwarfCannon().checkRoyalLocation();
		}
		if (HerbCleaning.clean(player, item, slotId))
			return;
		Bone bone = Bone.forId(itemId);
		if (bone != null) {
			Bone.bury(player, slotId);
			return;
		}
		if (itemId == 4597)
			player.sm("Let's not be nosey and read Santa's note...");
		if (itemId == 299) {
			//FlowerGame.plant(player);
		}
		if (itemId == 19832) {
			player.setNextWorldTile(new WorldTile(3048, 5837, 1));
		}
		if (Magic.useTabTeleport(player, itemId))
			return;
		if (itemId == AncientEffigies.SATED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.GORGED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.NOURISHED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.STARVED_ANCIENT_EFFIGY)
			player.getDialogueManager().startDialogue("AncientEffigiesD",
					itemId);
		else if (itemId == 6099){
			if (player.getJailed() > Utils.currentTimeMillis()) {
				player.getPackets().sendGameMessage("No, no... I stay...");	
			} else {
			player.monsterPageOne = true;
			player.monsterPageTwo = false;
			/*if (player.crystalcharges <= 0) {
				player.getPackets().sendGameMessage("You must recharge your crystal with the '::buycharges' with Loyalty Tokens.");
				player.getPackets().sendGameMessage("Don't worry, everything is accessible from our world.");
				player.getPackets().sendGameMessage("You can reach every monster, location, minigame, etc.");
				player.getPackets().sendGameMessage("Our interactive world provides sailing, enchanted jewlery, gnome gliders, and more!");	
			} else {
			player.crystalcharges--;
			player.getPackets().sendGameMessage("You now have "+player.crystalcharges+" left in your crystal teleport.");	*/
			player.getDialogueManager().startDialogue("TeleportCrystal");
			//}
			}
	}
		else if (itemId == 4251) {
		player.getEctophial().ProcessTeleportation(player);
	}
		else if (item.getDefinitions().containsOption(0, "Craft") || item.getDefinitions().containsOption(0, "Fletch")) {
		    if (player.getInventory().containsItemToolBelt(946, 1)) {
			Fletch fletch = Fletching.isFletching(item, new Item(946));
			if (fletch != null) {
			    player.getDialogueManager().startDialogue("FletchingD", fletch);
			    return;
			}
		    } else if (player.getInventory().containsItemToolBelt(1755, 1)) {
			Fletch fletch = Fletching.isFletching(item, new Item(1755));
			if (fletch != null) {
			    player.getDialogueManager().startDialogue("FletchingD", fletch);
			    return;
			}
		    } else
			player.getDialogueManager().startDialogue("ItemMessage", "You need a knife or chisle to complete the action.", 946);
		}
		else if (itemId == 455) {
			player.finishBarCrawl();
			player.getInterfaceManager().sendBarcrawl();
			
		}
		else if (itemId == 4155)
		    player.getDialogueManager().startDialogue("EnchantedGemDialouge", player.getSlayerManager().getCurrentMaster().getNPCId());
		else if (itemId == 14057) // broomstick
		    player.setNextAnimation(new Animation(10532));
		else if (itemId == SqirkFruitSqueeze.SqirkFruit.AUTUMM.getFruitId())
		    player.getDialogueManager().startDialogue("SqirkFruitSqueeze", SqirkFruit.AUTUMM);
		else if (itemId == SqirkFruitSqueeze.SqirkFruit.SPRING.getFruitId())
		    player.getDialogueManager().startDialogue("SqirkFruitSqueeze", SqirkFruit.SPRING);
		else if (itemId == SqirkFruitSqueeze.SqirkFruit.SUMMER.getFruitId())
		    player.getDialogueManager().startDialogue("SqirkFruitSqueeze", SqirkFruit.SUMMER);
		else if (itemId == SqirkFruitSqueeze.SqirkFruit.WINTER.getFruitId())
		    player.getDialogueManager().startDialogue("SqirkFruitSqueeze", SqirkFruit.WINTER);
		else if (itemId == 15262)
		    ItemSets.openSkillPack(player, itemId, 12183, 5000, 1);
		else if (itemId == 15362)
		    ItemSets.openSkillPack(player, itemId, 230, 50, 1);
		else if (itemId == 15363)
		    ItemSets.openSkillPack(player, itemId, 228, 50, 1);
		else if (itemId == 15364)
		    ItemSets.openSkillPack(player, itemId, 222, 50, 1);
		else if (itemId == 15365)
		    ItemSets.openSkillPack(player, itemId, 9979, 50, 1);
		else if (itemId == 1917) {
			player.getPackets().sendGameMessage("You Drink the Beer.");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(1917, 1);
			
		}
		// DIVINATION \\
		// mining \\
		else if (itemId == 31041) {
			player.getPackets().sendGameMessage("You place a portable forge infront of you.");
			WorldTasksManager.schedule(new WorldTask() {
			    int ticks;
			    @Override
			    public void run() {
				ticks++;
			if(ticks == 1){
				player.getInventory().deleteItem(31041, 1);
				final WorldObject divinerune = new WorldObject(89767,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object spawning
				player.faceObject(divinerune); // forces player to face where he is putting the object
				World.spawnTemporaryObject(divinerune, 60000); // dont touch
				stop();
			}
			    return;
			    }  
		}, 0, 0);
		}
		else if (itemId == 29294) {
			DivinePlacing.placeDivine(player, itemId, 87285, 34107, 1, 14);	
			//DivinePlacing.fucker = player;
			player.getPackets().sendGameMessage("You spawn a divine bronze rock.");
		}
		else if (itemId == 29295) {
			player.getPackets().sendGameMessage("You spawn a divine iron rock.");
			DivinePlacing.placeDivine(player, itemId, 87286, 57572, 15, 14);	
		}
		else if (itemId == 29296) {
			DivinePlacing.placeDivine(player, itemId, 87287, 87266, 30, 14);
			player.getPackets().sendGameMessage("You spawn a divine coal rock.");
		}
		else if (itemId == 29297 ) {
			DivinePlacing.placeDivine(player, itemId, 87288, 87267, 55, 14);
			player.getPackets().sendGameMessage("You spawn a divine mithril rock.");	
		}
		else if (itemId == 29298) {
			DivinePlacing.placeDivine(player, itemId, 87289, 87268, 70, 14);
			player.getPackets().sendGameMessage("You spawn a divine adamantite rock.");	
		}
		else if (itemId == 29299) {
			DivinePlacing.placeDivine(player, itemId, 87290, 87269, 85, 14);
			player.getPackets().sendGameMessage("You spawn a divine runite rock.");
		}
		// divine trees
		else if (itemId == 29304) {
			DivinePlacing.placeDivine(player, itemId, 87295, 87274, 1, 8);
			player.getPackets().sendGameMessage("You spawn a divine tree.");
		}
		else if (itemId == 29305) {
			DivinePlacing.placeDivine(player, itemId, 87296, 87275, 15, 8);
			player.getPackets().sendGameMessage("You spawn a divine oak tree.");
		}
		else if (itemId == 29306) {
			DivinePlacing.placeDivine(player, itemId, 87297, 87276, 30, 8);
			player.getPackets().sendGameMessage("You spawn a divine willow tree.");
		}
		else if (itemId == 29307) {
			DivinePlacing.placeDivine(player, itemId, 87298, 87277, 45, 8);
			player.getPackets().sendGameMessage("You spawn a divine maple tree.");
		}
		else if (itemId == 29308) {
			DivinePlacing.placeDivine(player, itemId, 87299, 87278, 60, 8);
			player.getPackets().sendGameMessage("You spawn a divine yew tree.");
		}
		else if (itemId == 29309) {
			DivinePlacing.placeDivine(player, itemId, 87300, 87279, 75, 8);
			player.getPackets().sendGameMessage("You spawn a divine magic tree.");
		}
		// herbolore
		else if (itemId == 29310) {
			DivinePlacing.placeDivine(player, itemId, 87301, 87280, 9, 15);
			player.getPackets().sendGameMessage("You spawn a divine herb patch I.");
		}
		else if (itemId == 29311) {
			DivinePlacing.placeDivine(player, itemId, 87302, 87281, 44, 15);
			player.getPackets().sendGameMessage("You spawn a divine herb patch II.");
		}
		else if (itemId == 29312) {
			DivinePlacing.placeDivine(player, itemId, 87303, 87282, 67, 15);
			player.getPackets().sendGameMessage("You spawn a divine herb patch III.");
		}
		//hunting
		else if (itemId == 29300) {
			DivinePlacing.placeDivine(player, itemId, 87291, 87270, 1, 21);
			player.getPackets().sendGameMessage("You spawn a divine kebbit burrow.");
		}
		else if (itemId == 29301) {
			DivinePlacing.placeDivine(player, itemId, 87292, 87271, 1, 21);
			player.getPackets().sendGameMessage("You spawn a divine bird snare.");
		}
		else if (itemId == 29302) {
			DivinePlacing.placeDivine(player, itemId, 87293, 87272, 23, 21);
			player.getPackets().sendGameMessage("You spawn a divine deadfall trap.");
		}
		else if (itemId == 29303) {
			DivinePlacing.placeDivine(player, itemId, 87294, 87273, 53, 21);
			player.getPackets().sendGameMessage("You spawn a divine box trap.");
		}
		// fishing 
		else if (itemId == 31080) {
			DivinePlacing.placeDivine(player, itemId, 90232, 90223, 10, 10);
			player.getPackets().sendGameMessage("You spawn a divine crayfish bubble.");
		}
		else if (itemId == 31081) {
			DivinePlacing.placeDivine(player, itemId, 90233, 90224, 10, 10);
			player.getPackets().sendGameMessage("You spawn a divine herring bubble.");
		}
		else if (itemId == 31082) {
			DivinePlacing.placeDivine(player, itemId, 90234, 90225, 20, 10);
			player.getPackets().sendGameMessage("You spawn a divine trout bubble.");
		}
		else if (itemId == 31083) {
			DivinePlacing.placeDivine(player, itemId, 90235, 90226, 30, 10);
			player.getPackets().sendGameMessage("You spawn a divine salmon bubble.");
		}
		else if (itemId == 31084) {
			DivinePlacing.placeDivine(player, itemId, 90236, 90227, 40, 10);
			player.getPackets().sendGameMessage("You spawn a divine lobster bubble.");
		}
		else if (itemId == 31085) {
			DivinePlacing.placeDivine(player, itemId, 90237, 90228, 50, 10);
			player.getPackets().sendGameMessage("You spawn a divine swordfish bubble.");
		}
		else if (itemId == 31086) {
			DivinePlacing.placeDivine(player, itemId, 90238, 90229, 76, 10);
			player.getPackets().sendGameMessage("You spawn a divine shark bubble.");
		}
		else if (itemId == 31087) {
			DivinePlacing.placeDivine(player, itemId, 90239, 90230, 85, 10);
			player.getPackets().sendGameMessage("You spawn a divine cavefish bubble.");
		}
		else if (itemId == 31088) {
			DivinePlacing.placeDivine(player, itemId, 90240, 90231, 90, 10);
			player.getPackets().sendGameMessage("You spawn a divine rocktail bubble.");
		}
		//div not being used
		else if (itemId == 31310) {
			DivinePlacing.placeDivine(player, itemId, 66526, 66528, 65, 1);
			player.getPackets().sendGameMessage("You spawn a divine simulacrum I.");
		}
		else if (itemId == 31311) {
			DivinePlacing.placeDivine(player, itemId, 66529, 66531, 65, 1);
			player.getPackets().sendGameMessage("You spawn a divine simulacrum II.");
		}
		// end of divination \\
		else if (itemId == 5763) {
			player.getPackets().sendGameMessage("You Drink the Cider.");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(5763, 1);
			
		}
		else if (itemId == 1905) {
			player.getPackets().sendGameMessage("You Drink the Asgarnian Ale.");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(1905, 1);
			player.getAppearence().setRenderEmote(52);
			
		}
		else if (itemId == 1909) {
			player.getPackets().sendGameMessage("You Drink the Greenman's Ale.");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(1909, 1);
			player.getAppearence().setRenderEmote(52);
			
		}
		else if (itemId == 5755) {
			player.getPackets().sendGameMessage("You Drink the Chef's Delight.");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(5755, 1);
			player.getAppearence().setRenderEmote(52);
			
		}
		else if (itemId == 1911) {
			player.getPackets().sendGameMessage("You Drink the Dragon Bitter.");
			player.setNextForceTalk(new ForceTalk("Holy shit that was intense!"));
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(1911, 1);
			player.getAppearence().setRenderEmote(290);
			
		}
		else if (itemId == 1907) {
			player.getPackets().sendGameMessage("You Drink the Wizard's MindBomb");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(1907, 1);
			
		}
		else if (itemId == 3801) {
			player.getPackets().sendGameMessage("You Drink the Keg of Beer...And feel Quite Drunk...");
			player.setNextAnimation(new Animation(1330));
			player.getInventory().deleteItem(3801, 1);
			
		}
		else if (itemId == 3803) {
			player.getPackets().sendGameMessage("You drink the Beer");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(3803, 1);
			
		}
		else if (itemId == 431) {
			player.getPackets().sendGameMessage("You drink the rum");
			player.setNextAnimation(new Animation(829));
			player.getInventory().deleteItem(431, 1);
			
		}
		else if (itemId == 21576) {
			player.getDialogueManager().startDialogue("DrakansMedallion");
		}
		else if (itemId == 11328) {
			player.getInventory().deleteItem(11328, 1);
			player.getInventory().addItem(11324, 1);
			}
		else if (itemId == 11330) {
			player.getInventory().deleteItem(11330, 1);
			player.getInventory().addItem(11324, 1);
			}
		else if (itemId == 11332) {
			player.getInventory().deleteItem(11332, 1);
			player.getInventory().addItem(11324, 1);
			}
		
		//Armour sets Opening
		else if (itemId == 11814) {//Bronze (l)
			player.getInventory().deleteItem(11814, 1);
			player.getInventory().addItem(1075, 1);
			player.getInventory().addItem(1103, 1);
			player.getInventory().addItem(1155, 1);
			player.getInventory().addItem(1189, 1);
			}
		else if (itemId == 11818) {//iron (l)
			player.getInventory().deleteItem(11818, 1);
			player.getInventory().addItem(1067, 1);
			player.getInventory().addItem(1115, 1);
			player.getInventory().addItem(1153, 1);
			player.getInventory().addItem(1191, 1);
			}
		else if (itemId == 11822) {//steel (l)
			player.getInventory().deleteItem(11822, 1);
			player.getInventory().addItem(1069, 1);
			player.getInventory().addItem(1119, 1);
			player.getInventory().addItem(1157, 1);
			player.getInventory().addItem(1193, 1);
			}
		else if (itemId == 11826) {//black (l)
			player.getInventory().deleteItem(11826, 1);
			player.getInventory().addItem(1077, 1);
			player.getInventory().addItem(1125, 1);
			player.getInventory().addItem(1165, 1);
			player.getInventory().addItem(1195, 1);
			}
		else if (itemId == 11830) {//mithril (l)
			player.getInventory().deleteItem(11830, 1);
			player.getInventory().addItem(1071, 1);
			player.getInventory().addItem(1121, 1);
			player.getInventory().addItem(1159, 1);
			player.getInventory().addItem(1197, 1);
			}
		else if (itemId == 11834) {//adamant (l)
			player.getInventory().deleteItem(11834, 1);
			player.getInventory().addItem(1073, 1);
			player.getInventory().addItem(1123, 1);
			player.getInventory().addItem(1161, 1);
			player.getInventory().addItem(1199, 1);
			}
		else if (itemId == 11838) {//Rune (l)
			player.getInventory().deleteItem(11838, 1);
			player.getInventory().addItem(1079, 1);
			player.getInventory().addItem(1127, 1);
			player.getInventory().addItem(1163, 1);
			player.getInventory().addItem(1201, 1);
			}
		else if (itemId == 14527) {//elite black night
			player.getInventory().deleteItem(14527, 1);
			player.getInventory().addItem(14490, 1);
			player.getInventory().addItem(14492, 1);
			player.getInventory().addItem(14494, 1);
			}
		else if (itemId == 11942) {//Rockshell
			player.getInventory().deleteItem(11942, 1);
			player.getInventory().addItem(6128, 1);
			player.getInventory().addItem(6129, 1);
			player.getInventory().addItem(6130, 1);
			player.getInventory().addItem(6145, 1);
			player.getInventory().addItem(6151, 1);
			}
		else if (itemId == 11842) {//dragon chain-mail (l)
			player.getInventory().deleteItem(11842, 1);
			player.getInventory().addItem(4087, 1);
			player.getInventory().addItem(3140, 1);
			player.getInventory().addItem(1149, 1);
			}
		else if (itemId == 11844) {//dragon chain-mail (sk)
			player.getInventory().deleteItem(11844, 1);
			player.getInventory().addItem(4585, 1);
			player.getInventory().addItem(3140, 1);
			player.getInventory().addItem(1149, 1);
			}
		else if (itemId == 14529) {//dragon Plate armour (l)
			player.getInventory().deleteItem(14529, 1);
			player.getInventory().addItem(4087, 1);
			player.getInventory().addItem(14479, 1);
			player.getInventory().addItem(11335, 1);
			}
		else if (itemId == 14529) {//dragon Plate armour (sk)
			player.getInventory().deleteItem(14529, 1);
			player.getInventory().addItem(4585, 1);
			player.getInventory().addItem(14479, 1);
			player.getInventory().addItem(11335, 1);
			}
		
		//Dhide armour
		
		else if (itemId == 11864) {//green dhide
			player.getInventory().deleteItem(11864, 1);
			player.getInventory().addItem(1135, 1);
			player.getInventory().addItem(1099, 1);
			player.getInventory().addItem(1065, 1);
			}
		else if (itemId == 11866) {//blue dhide
			player.getInventory().deleteItem(11866, 1);
			player.getInventory().addItem(2499, 1);
			player.getInventory().addItem(2493, 1);
			player.getInventory().addItem(2487, 1);
			}
		else if (itemId == 11868) {//Red dhide
			player.getInventory().deleteItem(11868, 1);
			player.getInventory().addItem(2501, 1);
			player.getInventory().addItem(2495, 1);
			player.getInventory().addItem(2489, 1);
			}
		else if (itemId == 11870) {//Black Dhide
			player.getInventory().deleteItem(11870, 1);
			player.getInventory().addItem(2503, 1);
			player.getInventory().addItem(2497, 1);
			player.getInventory().addItem(2491, 1);
			}
		else if (itemId == 11944) {//Spined armour set
			player.getInventory().deleteItem(11944, 1);
			player.getInventory().addItem(6131, 1);
			player.getInventory().addItem(6133, 1);
			player.getInventory().addItem(6135, 1);
			player.getInventory().addItem(6143, 1);
			player.getInventory().addItem(6149, 1);
			}
		else if (itemId == 11920) {//Blessed Green dhide
			player.getInventory().deleteItem(11920, 1);
			player.getInventory().addItem(10376, 1);
			player.getInventory().addItem(10378, 1);
			player.getInventory().addItem(10380, 1);
			player.getInventory().addItem(10382, 1);
			}
		else if (itemId == 11922) {//Blessed Blue dhide
			player.getInventory().deleteItem(11922, 1);
			player.getInventory().addItem(10384, 1);
			player.getInventory().addItem(10386, 1);
			player.getInventory().addItem(10388, 1);
			player.getInventory().addItem(10390, 1);
			}
		else if (itemId == 11924) {//Blessed red dhide
			player.getInventory().deleteItem(11924, 1);
			player.getInventory().addItem(10368, 1);
			player.getInventory().addItem(10370, 1);
			player.getInventory().addItem(10372, 1);
			player.getInventory().addItem(10374, 1);
			}
		else if (itemId == 19582) {//Blessed dyed brown dhide
			player.getInventory().deleteItem(19582, 1);
			player.getInventory().addItem(19451, 1);
			player.getInventory().addItem(19453, 1);
			player.getInventory().addItem(19455, 1);
			player.getInventory().addItem(19457, 1);
			}
		else if (itemId == 19584) {//Blessed dyed purple dhide
			player.getInventory().deleteItem(19584, 1);
			player.getInventory().addItem(19443, 1);
			player.getInventory().addItem(19445, 1);
			player.getInventory().addItem(19447, 1);
			player.getInventory().addItem(19449, 1);
			}
		else if (itemId == 19586) {//Blessed dyed silver dhide
			player.getInventory().deleteItem(19586, 1);
			player.getInventory().addItem(19459, 1);
			player.getInventory().addItem(19461, 1);
			player.getInventory().addItem(19463, 1);
			player.getInventory().addItem(19465, 1);
			}
		else if (itemId == 24386) {//Royal Dhide
			player.getInventory().deleteItem(24386, 1);
			player.getInventory().addItem(24382, 1);
			player.getInventory().addItem(24379, 1);
			player.getInventory().addItem(24376, 1);
			}
		
		//Mage
		else if (itemId == 11902) {//Enchanted
			player.getInventory().deleteItem(11902, 1);
			player.getInventory().addItem(7398, 1);
			player.getInventory().addItem(7399, 1);
			player.getInventory().addItem(7400, 1);
			}
		else if (itemId == 11874) {//Infinity robes
			player.getInventory().deleteItem(11874, 1);
			player.getInventory().addItem(6916, 1);
			player.getInventory().addItem(6918, 1);
			player.getInventory().addItem(6920, 1);
			player.getInventory().addItem(6922, 1);
			player.getInventory().addItem(6924, 1);
			}
		else if (itemId == 14525) {//Dragon 'hai
			player.getInventory().deleteItem(14525, 1);
			player.getInventory().addItem(14497, 1);
			player.getInventory().addItem(14499, 1);
			player.getInventory().addItem(14501, 1);
			}
		else if (itemId == 11876) {//Splitbark
			player.getInventory().deleteItem(11876, 1);
			player.getInventory().addItem(3385, 1);
			player.getInventory().addItem(3387, 1);
			player.getInventory().addItem(3389, 1);
			player.getInventory().addItem(3391, 1);
			player.getInventory().addItem(3393, 1);
			}
		else if (itemId == 11946) {//skeletal
			player.getInventory().deleteItem(11946, 1);
			player.getInventory().addItem(6137, 1);
			player.getInventory().addItem(6139, 1);
			player.getInventory().addItem(6141, 1);
			player.getInventory().addItem(6147, 1);
			player.getInventory().addItem(6153, 1);
			}
		
		else if (itemId == 11872) {//Blue mystic
			player.getInventory().deleteItem(11872, 1);
			player.getInventory().addItem(4089, 1);
			player.getInventory().addItem(4091, 1);
			player.getInventory().addItem(4093, 1);
			player.getInventory().addItem(4095, 1);
			player.getInventory().addItem(4097, 1);
			}
		
		else if (itemId == 11962) {//dark mystic
			player.getInventory().deleteItem(11962, 1);
			player.getInventory().addItem(4099, 1);
			player.getInventory().addItem(4101, 1);
			player.getInventory().addItem(4103, 1);
			player.getInventory().addItem(4105, 1);
			player.getInventory().addItem(4107, 1);
			}
		else if (itemId == 11960) {//Light Mystic
			player.getInventory().deleteItem(11960, 1);
			player.getInventory().addItem(4109, 1);
			player.getInventory().addItem(4111, 1);
			player.getInventory().addItem(4113, 1);
			player.getInventory().addItem(4115, 1);
			player.getInventory().addItem(4117, 1);
			}
		
		
		//Third age armour
		
		else if (itemId == 11858) {//Melee
			player.getInventory().deleteItem(11858, 1);
			player.getInventory().addItem(10350, 1);
			player.getInventory().addItem(10348, 1);
			player.getInventory().addItem(10346, 1);
			player.getInventory().addItem(10352, 1);
			}
		else if (itemId == 11860) {//Range
			player.getInventory().deleteItem(11860, 1);
			player.getInventory().addItem(10334, 1);
			player.getInventory().addItem(10330, 1);
			player.getInventory().addItem(10332, 1);
			player.getInventory().addItem(10336, 1);
			}
		else if (itemId == 11862) {//mage
			player.getInventory().deleteItem(11862, 1);
			player.getInventory().addItem(10342, 1);
			player.getInventory().addItem(10338, 1);
			player.getInventory().addItem(10340, 1);
			player.getInventory().addItem(10344, 1);
			}
		else if (itemId == 19580) {//Druidic
			player.getInventory().deleteItem(19580, 1);
			player.getInventory().addItem(19309, 1);
			player.getInventory().addItem(19311, 1);
			player.getInventory().addItem(19314, 1);
			player.getInventory().addItem(19317, 1);
			player.getInventory().addItem(19320, 1);
			}
		
		//Barrows sets
		else if (itemId == 11846) {//Ahrims
		player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
		player.getInventory().deleteItem(11846, 1);
		player.getInventory().addItem(4708, 1);
		player.getInventory().addItem(4710, 1);
		player.getInventory().addItem(4712, 1);
		player.getInventory().addItem(4714, 1);
		}
		else if (itemId == 11848) {//Dharoks
			player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
			player.getInventory().deleteItem(11848, 1);
			player.getInventory().addItem(4716, 1);
			player.getInventory().addItem(4718, 1);
			player.getInventory().addItem(4720, 1);
			player.getInventory().addItem(4722, 1);
		}
		else if (itemId == 11850) {//Guthans
			player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
			player.getInventory().deleteItem(11850, 1);
			player.getInventory().addItem(4724, 1);
			player.getInventory().addItem(4726, 1);
			player.getInventory().addItem(4728, 1);
			player.getInventory().addItem(4730, 1);
		}
		else if (itemId == 11852) {//Karils
			player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
			player.getInventory().deleteItem(11852, 1);
			player.getInventory().addItem(4732, 1);
			player.getInventory().addItem(4734, 1);
			player.getInventory().addItem(4736, 1);
			player.getInventory().addItem(4738, 1);
		}
		else if (itemId == 11854) {//Torags
			player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
			player.getInventory().deleteItem(11854, 1);
			player.getInventory().addItem(4745, 1);
			player.getInventory().addItem(4747, 1);
			player.getInventory().addItem(4749, 1);
			player.getInventory().addItem(4751, 1);
		}
		else if (itemId == 11856) {//Veracs
			player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
			player.getInventory().deleteItem(11856, 1);
			player.getInventory().addItem(4753, 1);
			player.getInventory().addItem(4755, 1);
			player.getInventory().addItem(4757, 1);
			player.getInventory().addItem(4759, 1);
		}
		else if (itemId == 21768) {//Akrisaes
			player.getPackets().sendGameMessage("You open The barrows set and claim the armour.");
			player.getInventory().deleteItem(21768, 1);
			player.getInventory().addItem(21736, 1);
			player.getInventory().addItem(21744, 1);
			player.getInventory().addItem(21752, 1);
			player.getInventory().addItem(21760, 1);
		}
		else if (itemId >= 23653 && itemId <= 23658)
			FightKiln.useCrystal(player, itemId);
		if(itemId == 405) {
			int[] reward = { 20000, 30000, 40000, 50000, 75000 };
			int won = reward[Utils.random(reward.length-1)];
			player.getInventory().deleteItem(405, 1);
			player.getInventory().addItemMoneyPouch(new Item(995, won));
			player.getPackets().sendGameMessage("The casket slowly opens... You receive "+ won + " coins!");
		} else if (itemId == 24154 || itemId == 24155) {
			    player.getSquealOfFortune().processItemClick(slotId, itemId, item);
		/*} else if (itemId == 15389) {
			player.getSkills().addXp(Skills.ATTACK, 20000);
			player.getSkills().addXp(Skills.STRENGTH, 20000);
			player.getSkills().addXp(Skills.DEFENCE, 20000);
			player.getSkills().addXp(Skills.RANGE, 20000);
			player.getSkills().addXp(Skills.MAGIC, 20000);
			player.getSkills().addXp(Skills.PRAYER, 20000);
			player.getSkills().addXp(Skills.RUNECRAFTING, 20000);
			player.getSkills().addXp(Skills.CONSTRUCTION, 20000);
			player.getSkills().addXp(Skills.DUNGEONEERING, 20000);
			player.getSkills().addXp(Skills.HITPOINTS, 20000);
			player.getSkills().addXp(Skills.AGILITY, 20000);
			player.getSkills().addXp(Skills.HERBLORE, 20000);
			player.getSkills().addXp(Skills.THIEVING, 20000);
			player.getSkills().addXp(Skills.CRAFTING, 20000);
			player.getSkills().addXp(Skills.FLETCHING, 20000);
			player.getSkills().addXp(Skills.SLAYER, 20000);
			player.getSkills().addXp(Skills.HUNTER, 20000);
			player.getSkills().addXp(Skills.MINING, 20000);
			player.getSkills().addXp(Skills.SMITHING, 20000);
			player.getSkills().addXp(Skills.FISHING, 20000);
			player.getSkills().addXp(Skills.COOKING, 20000);
			player.getSkills().addXp(Skills.FIREMAKING, 20000);
			player.getSkills().addXp(Skills.FARMING, 20000);
			player.getSkills().addXp(Skills.WOODCUTTING, 20000);
			player.getSkills().addXp(Skills.SUMMONING, 20000);
			player.getInventory().deleteItem(15389, 1);
		} else if (itemId == 23717) {
			player.getSkills().addXp(Skills.ATTACK, 250);
			player.getInventory().deleteItem(23717, 1);
		} else if (itemId == 23721) {
			player.getSkills().addXp(Skills.STRENGTH, 250);
			player.getInventory().deleteItem(23721, 1);
		} else if (itemId == 23725) {
			player.getSkills().addXp(Skills.DEFENCE, 250);
			player.getInventory().deleteItem(23725, 1);
		} else if (itemId == 23729) {
			player.getSkills().addXp(Skills.RANGE, 250);
			player.getInventory().deleteItem(23729, 1);
		} else if (itemId == 23733) {
			player.getSkills().addXp(Skills.MAGIC, 250);
			player.getInventory().deleteItem(23733, 1);
		} else if (itemId == 23737) {
			player.getSkills().addXp(Skills.PRAYER, 250);
			player.getInventory().deleteItem(23737, 1);
		} else if (itemId == 23741) {
			player.getSkills().addXp(Skills.RUNECRAFTING, 250);
			player.getInventory().deleteItem(23741, 1);
		} else if (itemId == 23745) {
			player.getSkills().addXp(Skills.CONSTRUCTION, 250);
			player.getInventory().deleteItem(23745, 1);
		} else if (itemId == 23749) {
			player.getSkills().addXp(Skills.DUNGEONEERING, 250);
			player.getInventory().deleteItem(23749, 1);
		} else if (itemId == 23753) {
			player.getSkills().addXp(Skills.HITPOINTS, 250);
			player.getInventory().deleteItem(23753, 1);
		} else if (itemId == 23757) {
			player.getSkills().addXp(Skills.AGILITY, 250);
			player.getInventory().deleteItem(23757, 1);
		} else if (itemId == 23761) {
			player.getSkills().addXp(Skills.HERBLORE, 250);
			player.getInventory().deleteItem(23761, 1);
		} else if (itemId == 23765) {
			player.getSkills().addXp(Skills.THIEVING, 250);
			player.getInventory().deleteItem(23765, 1);
		} else if (itemId == 23769) {
			player.getSkills().addXp(Skills.CRAFTING, 250);
			player.getInventory().deleteItem(23769, 1);
		} else if (itemId == 23774) {
			player.getSkills().addXp(Skills.FLETCHING, 250);
			player.getInventory().deleteItem(23774, 1);
		} else if (itemId == 23778) {
			player.getSkills().addXp(Skills.SLAYER, 250);
			player.getInventory().deleteItem(23778, 1);
		} else if (itemId == 23782) {
			player.getSkills().addXp(Skills.HUNTER, 250);
			player.getInventory().deleteItem(23782, 1);
		} else if (itemId == 23786) {
			player.getSkills().addXp(Skills.MINING, 250);
			player.getInventory().deleteItem(23786, 1);
		} else if (itemId == 23790) {
			player.getSkills().addXp(Skills.SMITHING, 250);
			player.getInventory().deleteItem(23790, 1);
		} else if (itemId == 23794) {
			player.getSkills().addXp(Skills.FISHING, 250);
			player.getInventory().deleteItem(23794, 1);
		} else if (itemId == 23798) {
			player.getSkills().addXp(Skills.COOKING, 250);
			player.getInventory().deleteItem(23798, 1);
		} else if (itemId == 23802) {
			player.getSkills().addXp(Skills.FIREMAKING, 250);
			player.getInventory().deleteItem(23802, 1);
		} else if (itemId == 23806) {
			player.getSkills().addXp(Skills.WOODCUTTING, 250);
			player.getInventory().deleteItem(23806, 1);
		} else if (itemId == 23810) {
			player.getSkills().addXp(Skills.FARMING, 250);
			player.getInventory().deleteItem(23810, 1);
		} else if (itemId == 23814) {
			player.getSkills().addXp(Skills.SUMMONING, 250);
			player.getInventory().deleteItem(23814, 1);
		} else if (itemId == 23718) {
			player.getSkills().addXp(Skills.ATTACK, 500);
			player.getInventory().deleteItem(23718, 1);
		} else if (itemId == 23722) {
			player.getSkills().addXp(Skills.STRENGTH, 500);
			player.getInventory().deleteItem(23722, 1);
		} else if (itemId == 23726) {
			player.getSkills().addXp(Skills.DEFENCE, 500);
			player.getInventory().deleteItem(23726, 1);
		} else if (itemId == 23730) {
			player.getSkills().addXp(Skills.RANGE, 500);
			player.getInventory().deleteItem(23730, 1);
		} else if (itemId == 23734) {
			player.getSkills().addXp(Skills.MAGIC, 500);
			player.getInventory().deleteItem(23734, 1);
		} else if (itemId == 23738) {
			player.getSkills().addXp(Skills.PRAYER, 500);
			player.getInventory().deleteItem(23738, 1);
		} else if (itemId == 23742) {
			player.getSkills().addXp(Skills.RUNECRAFTING, 500);
			player.getInventory().deleteItem(23742, 1);
		} else if (itemId == 23746) {
			player.getSkills().addXp(Skills.CONSTRUCTION, 500);
			player.getInventory().deleteItem(23746, 1);
		} else if (itemId == 23750) {
			player.getSkills().addXp(Skills.DUNGEONEERING, 500);
			player.getInventory().deleteItem(23750, 1);
		} else if (itemId == 23754) {
			player.getSkills().addXp(Skills.HITPOINTS, 500);
			player.getInventory().deleteItem(23754, 1);
		} else if (itemId == 23758) {
			player.getSkills().addXp(Skills.AGILITY, 500);
			player.getInventory().deleteItem(23758, 1);
		} else if (itemId == 23762) {
			player.getSkills().addXp(Skills.HERBLORE, 500);
			player.getInventory().deleteItem(23762, 1);
		} else if (itemId == 23766) {
			player.getSkills().addXp(Skills.THIEVING, 500);
			player.getInventory().deleteItem(23766, 1);
		} else if (itemId == 23770) {
			player.getSkills().addXp(Skills.CRAFTING, 500);
			player.getInventory().deleteItem(23770, 1);
		} else if (itemId == 23775) {
			player.getSkills().addXp(Skills.FLETCHING, 500);
			player.getInventory().deleteItem(23775, 1);
		} else if (itemId == 23779) {
			player.getSkills().addXp(Skills.SLAYER, 500);
			player.getInventory().deleteItem(23779, 1);
		} else if (itemId == 23783) {
			player.getSkills().addXp(Skills.HUNTER, 500);
			player.getInventory().deleteItem(23783, 1);
		} else if (itemId == 23787) {
			player.getSkills().addXp(Skills.MINING, 500);
			player.getInventory().deleteItem(23787, 1);
		} else if (itemId == 23791) {
			player.getSkills().addXp(Skills.SMITHING, 500);
			player.getInventory().deleteItem(23791, 1);
		} else if (itemId == 23795) {
			player.getSkills().addXp(Skills.FISHING, 500);
			player.getInventory().deleteItem(23795, 1);
		} else if (itemId == 23799) {
			player.getSkills().addXp(Skills.COOKING, 500);
			player.getInventory().deleteItem(23799, 1);
		} else if (itemId == 23803) {
			player.getSkills().addXp(Skills.FIREMAKING, 500);
			player.getInventory().deleteItem(23803, 1);
		} else if (itemId == 23807) {
			player.getSkills().addXp(Skills.WOODCUTTING, 500);
			player.getInventory().deleteItem(23807, 1);
		} else if (itemId == 23811) {
			player.getSkills().addXp(Skills.FARMING, 500);
			player.getInventory().deleteItem(23811, 1);
		} else if (itemId == 23815) {
			player.getSkills().addXp(Skills.SUMMONING, 500);
			player.getInventory().deleteItem(23815, 1);
		} else if (itemId == 23719) {
			player.getSkills().addXp(Skills.ATTACK, 750);
			player.getInventory().deleteItem(23719, 1);
		} else if (itemId == 23723) {
			player.getSkills().addXp(Skills.STRENGTH, 750);
			player.getInventory().deleteItem(23723, 1);
		} else if (itemId == 23727) {
			player.getSkills().addXp(Skills.DEFENCE, 750);
			player.getInventory().deleteItem(23727, 1);
		} else if (itemId == 23731) {
			player.getSkills().addXp(Skills.RANGE, 750);
			player.getInventory().deleteItem(23731, 1);
		} else if (itemId == 23735) {
			player.getSkills().addXp(Skills.MAGIC, 750);
			player.getInventory().deleteItem(23735, 1);
		} else if (itemId == 23739) {
			player.getSkills().addXp(Skills.PRAYER, 750);
			player.getInventory().deleteItem(23739, 1);
		} else if (itemId == 23743) {
			player.getSkills().addXp(Skills.RUNECRAFTING, 750);
			player.getInventory().deleteItem(23743, 1);
		} else if (itemId == 23747) {
			player.getSkills().addXp(Skills.CONSTRUCTION, 750);
			player.getInventory().deleteItem(23747, 1);
		} else if (itemId == 23751) {
			player.getSkills().addXp(Skills.DUNGEONEERING, 750);
			player.getInventory().deleteItem(23751, 1);
		} else if (itemId == 23755) {
			player.getSkills().addXp(Skills.HITPOINTS, 750);
			player.getInventory().deleteItem(23755, 1);
		} else if (itemId == 23759) {
			player.getSkills().addXp(Skills.AGILITY, 750);
			player.getInventory().deleteItem(23759, 1);
		} else if (itemId == 23763) {
			player.getSkills().addXp(Skills.HERBLORE, 750);
			player.getInventory().deleteItem(23763, 1);
		} else if (itemId == 23767) {
			player.getSkills().addXp(Skills.THIEVING, 750);
			player.getInventory().deleteItem(23767, 1);
		} else if (itemId == 23771) {
			player.getSkills().addXp(Skills.CRAFTING, 750);
			player.getInventory().deleteItem(23771, 1);
		} else if (itemId == 23776) {
			player.getSkills().addXp(Skills.FLETCHING, 750);
			player.getInventory().deleteItem(23776, 1);
		} else if (itemId == 23780) {
			player.getSkills().addXp(Skills.SLAYER, 750);
			player.getInventory().deleteItem(23780, 1);
		} else if (itemId == 23784) {
			player.getSkills().addXp(Skills.HUNTER, 750);
			player.getInventory().deleteItem(23784, 1);
		} else if (itemId == 23788) {
			player.getSkills().addXp(Skills.MINING, 750);
			player.getInventory().deleteItem(23788, 1);
		} else if (itemId == 23792) {
			player.getSkills().addXp(Skills.SMITHING, 750);
			player.getInventory().deleteItem(23792, 1);
		} else if (itemId == 23796) {
			player.getSkills().addXp(Skills.FISHING, 750);
			player.getInventory().deleteItem(23796, 1);
		} else if (itemId == 23800) {
			player.getSkills().addXp(Skills.COOKING, 750);
			player.getInventory().deleteItem(23800, 1);
		} else if (itemId == 23804) {
			player.getSkills().addXp(Skills.FIREMAKING, 750);
			player.getInventory().deleteItem(23804, 1);
		} else if (itemId == 23808) {
			player.getSkills().addXp(Skills.WOODCUTTING, 750);
			player.getInventory().deleteItem(23808, 1);
		} else if (itemId == 23812) {
			player.getSkills().addXp(Skills.FARMING, 750);
			player.getInventory().deleteItem(23812, 1);
		} else if (itemId == 23816) {
			player.getSkills().addXp(Skills.SUMMONING, 750);
			player.getInventory().deleteItem(23816, 1);
		} else if (itemId == 23720) {
			player.getSkills().addXp(Skills.ATTACK, 1000);
			player.getInventory().deleteItem(23720, 1);
		} else if (itemId == 23724) {
			player.getSkills().addXp(Skills.STRENGTH, 1000);
			player.getInventory().deleteItem(23724, 1);
		} else if (itemId == 23728) {
			player.getSkills().addXp(Skills.DEFENCE, 1000);
			player.getInventory().deleteItem(23728, 1);
		} else if (itemId == 23732) {
			player.getSkills().addXp(Skills.RANGE, 1000);
			player.getInventory().deleteItem(23732, 1);
		} else if (itemId == 23736) {
			player.getSkills().addXp(Skills.MAGIC, 1000);
			player.getInventory().deleteItem(23736, 1);
		} else if (itemId == 23740) {
			player.getSkills().addXp(Skills.PRAYER, 1000);
			player.getInventory().deleteItem(23740, 1);
		} else if (itemId == 23744) {
			player.getSkills().addXp(Skills.RUNECRAFTING, 1000);
			player.getInventory().deleteItem(23744, 1);
		} else if (itemId == 23748) {
			player.getSkills().addXp(Skills.CONSTRUCTION, 1000);
			player.getInventory().deleteItem(23748, 1);
		} else if (itemId == 23752) {
			player.getSkills().addXp(Skills.DUNGEONEERING, 1000);
			player.getInventory().deleteItem(23752, 1);
		} else if (itemId == 23756) {
			player.getSkills().addXp(Skills.HITPOINTS, 1000);
			player.getInventory().deleteItem(23756, 1);
		} else if (itemId == 23760) {
			player.getSkills().addXp(Skills.AGILITY, 1000);
			player.getInventory().deleteItem(23760, 1);
		} else if (itemId == 23764) {
			player.getSkills().addXp(Skills.HERBLORE, 1000);
			player.getInventory().deleteItem(23764, 1);
		} else if (itemId == 23768) {
			player.getSkills().addXp(Skills.THIEVING, 1000);
			player.getInventory().deleteItem(23768, 1);
		} else if (itemId == 23772) {
			player.getSkills().addXp(Skills.CRAFTING, 1000);
			player.getInventory().deleteItem(23772, 1);
		} else if (itemId == 23777) {
			player.getSkills().addXp(Skills.FLETCHING, 1000);
			player.getInventory().deleteItem(23777, 1);
		} else if (itemId == 23781) {
			player.getSkills().addXp(Skills.SLAYER, 1000);
			player.getInventory().deleteItem(23781, 1);
		} else if (itemId == 23785) {
			player.getSkills().addXp(Skills.HUNTER, 1000);
			player.getInventory().deleteItem(23785, 1);
		} else if (itemId == 23789) {
			player.getSkills().addXp(Skills.MINING, 1000);
			player.getInventory().deleteItem(23789, 1);
		} else if (itemId == 23793) {
			player.getSkills().addXp(Skills.SMITHING, 1000);
			player.getInventory().deleteItem(23793, 1);
		} else if (itemId == 23797) {
			player.getSkills().addXp(Skills.FISHING, 1000);
			player.getInventory().deleteItem(23797, 1);
		} else if (itemId == 23801) {
			player.getSkills().addXp(Skills.COOKING, 1000);
			player.getInventory().deleteItem(23801, 1);
		} else if (itemId == 23805) {
			player.getSkills().addXp(Skills.FIREMAKING, 1000);
			player.getInventory().deleteItem(23805, 1);
		} else if (itemId == 23809) {
			player.getSkills().addXp(Skills.WOODCUTTING, 1000);
			player.getInventory().deleteItem(23809, 1);
		} else if (itemId == 23813) {
			player.getSkills().addXp(Skills.FARMING, 1000);
			player.getInventory().deleteItem(23813, 1);
		} else if (itemId == 23817) {
			player.getSkills().addXp(Skills.SUMMONING, 1000);
			player.getInventory().deleteItem(23817, 1);
		} else if (itemId == 24300) {
			player.getSkills().addXp(Skills.ATTACK, 200000);
			player.getSkills().addXp(Skills.STRENGTH, 200000);
			player.getSkills().addXp(Skills.DEFENCE, 200000);
			player.getSkills().addXp(Skills.RANGE, 200000);
			player.getSkills().addXp(Skills.MAGIC, 200000);
			player.getSkills().addXp(Skills.PRAYER, 200000);
			player.getSkills().addXp(Skills.RUNECRAFTING, 200000);
			player.getSkills().addXp(Skills.CONSTRUCTION, 200000);
			player.getSkills().addXp(Skills.DUNGEONEERING, 200000);
			player.getSkills().addXp(Skills.HITPOINTS, 200000);
			player.getSkills().addXp(Skills.AGILITY, 200000);
			player.getSkills().addXp(Skills.HERBLORE, 200000);
			player.getSkills().addXp(Skills.THIEVING, 200000);
			player.getSkills().addXp(Skills.CRAFTING, 200000);
			player.getSkills().addXp(Skills.FLETCHING, 200000);
			player.getSkills().addXp(Skills.SLAYER, 200000);
			player.getSkills().addXp(Skills.HUNTER, 200000);
			player.getSkills().addXp(Skills.MINING, 200000);
			player.getSkills().addXp(Skills.SMITHING, 200000);
			player.getSkills().addXp(Skills.FISHING, 200000);
			player.getSkills().addXp(Skills.COOKING, 200000);
			player.getSkills().addXp(Skills.FIREMAKING, 200000);
			player.getSkills().addXp(Skills.WOODCUTTING, 200000);
			player.getSkills().addXp(Skills.FARMING, 200000);
			player.getSkills().addXp(Skills.SUMMONING, 200000);
			player.getInventory().deleteItem(24300, 1);*/
		} else if (Lamps.isSelectable(itemId) || Lamps.isSkillLamp(itemId) || Lamps.isOtherLamp(itemId)) {
			    Lamps.processLampClick(player, slotId, itemId);
		} else if (itemId == 1856) {// Information Book
			player.getInterfaceManager().sendHelpBook();
			
			//for (int i = 31; i < 300; i++)
				//player.getPackets().sendIComponentText(275, i, "");
		} else if (itemId == HunterEquipment.BOX.getId()) // almost done
			player.getActionManager().setAction(new BoxAction(HunterEquipment.BOX));
		else if (itemId == HunterEquipment.BRID_SNARE.getId())
			player.getActionManager().setAction(
					new BoxAction(HunterEquipment.BRID_SNARE));
		else if (item.getDefinitions().getName().startsWith("Burnt")) 
			player.getDialogueManager().startDialogue("SimplePlayerMessage", "Ugh, this is inedible.");
		
		if (Settings.DEBUG)
			Logger.log("ItemHandler", "Item Select:" + itemId + ", Slot Id:"
					+ slotId);
	}

	/*
	 * returns the other
	 */
	public static Item contains(int id1, Item item1, Item item2) {
		if (item1.getId() == id1)
			return item2;
		if (item2.getId() == id1)
			return item1;
		return null;
	}

	public static boolean contains(int id1, int id2, Item... items) {
		boolean containsId1 = false;
		boolean containsId2 = false;
		for (Item item : items) {
			if (item.getId() == id1)
				containsId1 = true;
			else if (item.getId() == id2)
				containsId2 = true;
		}
		return containsId1 && containsId2;
	}

	public static void handleItemOnItem(final Player player, InputStream stream) {
		/*int itemUsedWithId = stream.readShort();
		int toSlot = stream.readShortLE128();
		int interfaceId = stream.readInt() >> 16;
		int interfaceId2 = stream.readInt() >> 16;
		int fromSlot = stream.readShort();
		int itemUsedId = stream.readShortLE128();*/
		int itemUsedWithId = stream.readInt();
	    int toSlot = stream.readShortLE128();
	    int hash1 = stream.readInt();
	    int hash2 = stream.readInt();
	    int interfaceId = hash1 >> 16;
	    int interfaceId2 = hash2 >> 16;
	    int comp1 = hash1 & 0xFFFF;
	    int fromSlot = stream.readShort();
	    int itemUsedId = stream.readInt();
	    if (interfaceId == 192 && interfaceId2 == 679) {
			Item item = player.getInventory().getItem(toSlot);
			if (item == null)
				return;
			switch(comp1) {
			case 59:
				Alchemy.handleAlchemy(player, item, false);
				break;
			case 38:
				Alchemy.handleAlchemy(player, item, true);
				break;
			case 50:
				Alchemy.handleSuperheat(player, item);
				break;
		    case 29:
		    case 41:
		    case 53:
		    case 61:
		    case 76:
		    case 88:
			Enchanting.processMagicEnchantSpell(player, toSlot, Enchanting.getJewleryIndex(comp1));
			default:
				if (player.getRights() == 2) {
					player.getPackets().sendGameMessage("Unhandled spell: component1: "+comp1+" slotId: "+toSlot);
				}
				break;
			}
			return;
		}
		if (interfaceId == 430 && interfaceId2 == 679) {
			Item item = player.getInventory().getItem(toSlot);
			if (item == null)
				return;
			switch(comp1) {
			case 33:
				Lunars.handlePlankMake(player, item);
				break;
			case 50:
				Lunars.handleRestorePotionShare(player, item);
				break;
			case 72:
				Lunars.handleLeatherMake(player, item);
				break;
			case 49:
				Lunars.handleBoostPotionShare(player, item);
				break;
			default:
				if (player.getRights() == 2) {
					player.getPackets().sendGameMessage("Unhandled lunar spell: component1: "+comp1+" slotId: "+toSlot);
				}
				break;
			}
			return;
		}
		if ((interfaceId2 == 747 || interfaceId2 == 662)
				&& interfaceId == Inventory.INVENTORY_INTERFACE) {
			if (player.getFamiliar() != null) {
				player.getFamiliar().setSpecial(true);
				if (player.getFamiliar().getSpecialAttack() == SpecialAttack.ITEM) {
					if (player.getFamiliar().hasSpecialOn())
						player.getFamiliar().submitSpecial(toSlot);
				}
			}
			return;
		}
		if (interfaceId == Inventory.INVENTORY_INTERFACE
				&& interfaceId == interfaceId2
				&& !player.getInterfaceManager().containsInventoryInter()) {
			if (toSlot >= 28 || fromSlot >= 28)
				return;
			Item usedWith = player.getInventory().getItem(toSlot);
			Item itemUsed = player.getInventory().getItem(fromSlot);
			if (itemUsed == null || usedWith == null
					|| itemUsed.getId() != itemUsedId
					|| usedWith.getId() != itemUsedWithId)
				return;
			player.stopAll();
			if (!player.getControlerManager().canUseItemOnItem(itemUsed,
					usedWith))
				return;
			Fletch fletch = Fletching.isFletching(usedWith, itemUsed);
			if (fletch != null) {
				player.getDialogueManager().startDialogue("FletchingD", fletch);
				return;
			}
			if((itemUsed.getId() == 6573 && usedWith.getId() == 1759) || (itemUsed.getId() == 1759 && usedWith.getId() == 6573)) {
				if (player.getInventory().containsItem(6573, 1) && player.getInventory().containsItem(1759, 1)) {
				player.getInventory().deleteItem(6573, 1);
				player.getInventory().deleteItem(1759, 1);
				player.getInventory().addItem(6581, 1);
				player.sendMessage("You succesfully make a Onyx amulet.");
				} else {
				player.sendMessage("You need both parts to make this amulet.");
				}
			        return;
		    }
			if ((itemUsed.getId() == 985  && usedWith.getId() == 987)|| (itemUsed.getId() == 987 && usedWith.getId() == 985)){
				if (player.getInventory().containsItem(985, 1) && player.getInventory().containsItem(987, 1)) {
				player.getInventory().deleteItem(985, 1);
				player.getInventory().deleteItem(987, 1);
				player.getInventory().addItem(989, 1);
				player.sendMessage("You succesfully make a crytal key.");
				} else {
				player.sendMessage("You need both parts to make this key.");
				}
			        return;
		    }
		    int leatherIndex = LeatherCraftingD.getIndex(itemUsedId) == -1 ? LeatherCraftingD.getIndex(usedWith.getId()) : LeatherCraftingD.getIndex(itemUsedId);
		    if (leatherIndex != -1 && ((itemUsedId == 1733 || usedWith.getId() == 1733) || LeatherCraftingD.isExtraItem(usedWith.getId()) || LeatherCraftingD.isExtraItem(itemUsedId))) {
			player.getDialogueManager().startDialogue("LeatherCraftingD", leatherIndex);
			return;
		    }
		    if (itemUsed.getId() == 187 || itemUsed.getId() == 5937 || itemUsed.getId() == 5940 || usedWith.getId() == 187 || usedWith.getId() == 5937 || usedWith.getId() == 5940)
		    WeaponPoison.handleItemInteract(player, itemUsed, usedWith);
			if ((itemUsed.getId() >= 1617 && itemUsed.getId() <= 1624) && usedWith.getId() == 18338) {
				if (player.gembagspace < 100) {
				if (itemUsed.getId() == 1617) {
					player.diamonds++;
					player.getInventory().deleteItem(1617, 1);
				} else if (itemUsed.getId() == 1619) {
					player.rubies++;
					player.getInventory().deleteItem(1619, 1);
				} else if (itemUsed.getId() == 1621) {
					player.emeralds++;
					player.getInventory().deleteItem(1621, 1);
				} else if (itemUsed.getId() == 1623) {
					player.sapphires++;
					player.getInventory().deleteItem(1623, 1);
				}
				player.gembagspace++;
				} else {
					player.sm("Your gem bag is too full to carry anymore uncut gems.");
				}
			}
			if (itemUsed.getId() == 4155 && usedWith.getId() == 8007) {
				 if (player.getSkills().getLevel(6) > 65) {
				player.getInventory().deleteItem(4155, 1);
				player.getInventory().deleteItem(8007, 1);
				//player.coal++;
				player.getSkills().addXp(6, 200);
				player.getInventory().addItem(11014 , 1);
				player.sm("You infuse the items together to make a Slayer Teleporter.");
				} else {
					player.sm("You need a magic level of 65 to infuse these items together.");
				}
			}
			if (itemUsed.getId() == 453 && usedWith.getId() == 18339) {
				if (player.coal < 27) {
				player.getInventory().deleteItem(453, 1);
				player.coal++;
				player.sm("You add a piece of coal to your coal bag.");
				} else {
					player.sm("Your coal bag is too full to carry anymore coal.");
				}
			}
			if ((itemUsed.getId() == 1785 && usedWith.getId() == 18330)
					|| (itemUsed.getId() == 18330 && usedWith.getId() == 1785)) {
				player.getInventory().deleteItem(18330, 1);
				player.getInventory().deleteItem(851, 1);
				player.getInventory().addItem(18331, 1);
				player.sm("You attach the two pieces together to create a maple sheildbow (sighted).");
			}
			if ((itemUsed.getId() == 851 && usedWith.getId() == 23193)
					|| (itemUsed.getId() == 23193 && usedWith.getId() == 851)) {
				if (player.getSkills().getLevel(Skills.CRAFTING) >= 89) {
					player.getInventory().deleteItem(23193, 1);
					player.getInventory().addItem(23191, 1);
					player.getSkills().addXp(Skills.CRAFTING, 40);
					player.setNextAnimation(new Animation(884));
					player.sm("You succesfully create an empty potion flask.");
				} else {
					player.sm("You need a crafting level of atleast 89 to create a potion flask.");
				}
			}
			/**
			 * Dye armor ( shadow , third age, barrows )
			 */
			//if (itemUsed.getId() == 33296 && usedWith.getId() == 31725){ // shadow dye noxious staff
				if (usedWith.getId() == 33296 || usedWith.getId() == 31725) {
				//player.sendMessage("test");
				if (player.getInventory().containsItem(33296, 1) && player.getInventory().containsItem(31725, 1)) {
					player.getInventory().deleteItem(33296, 1);
					player.getInventory().deleteItem(31725, 1);
					player.getInventory().addItem(33396, 1);
					player.sendMessage("You succesfully dye your your noxious staff.");
				} 
			}
			/**
			 * Flask Making
			 */

			// Attack (4)
			if (usedWith.getId() == 121 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(121, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(121, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23201, 1);
				}
			}
			// Attack (3)
			if (usedWith.getId() == 121 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(121, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(121, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23201, 1);
				}
			}
			// Attack (2)
			if (usedWith.getId() == 123 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(123, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(123, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23203, 1);
				}
			}
			// Attack (1)
			if (usedWith.getId() == 125 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(125, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(125, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23205, 1);
				}
			}
			// Super Attack (1) into Super Attack Flask (5)
			if (usedWith.getId() == 149 || usedWith.getId() == 23257) {
				if (player.getInventory().containsItem(149, 1)
						&& player.getInventory().containsItem(23257, 1)) {
					player.getInventory().deleteItem(149, 1);
					player.getInventory().deleteItem(23257, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23255, 1);
				}
			}
			// Super Attack (1) into Super Attack Flask (4)
			if (usedWith.getId() == 149 || usedWith.getId() == 23259) {
				if (player.getInventory().containsItem(149, 1)
						&& player.getInventory().containsItem(23259, 1)) {
					player.getInventory().deleteItem(149, 1);
					player.getInventory().deleteItem(23259, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23257, 1);
				}
			}
			// Super Attack (1) into Super Attack Flask (3)
			if (usedWith.getId() == 149 || usedWith.getId() == 23261) {
				if (player.getInventory().containsItem(149, 1)
						&& player.getInventory().containsItem(23261, 1)) {
					player.getInventory().deleteItem(149, 1);
					player.getInventory().deleteItem(23261, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23259, 1);
				}
			}
			// Super Attack (1) into Super Attack Flask (2)
			if (usedWith.getId() == 149 || usedWith.getId() == 23263) {
				if (player.getInventory().containsItem(149, 1)
						&& player.getInventory().containsItem(23263, 1)) {
					player.getInventory().deleteItem(149, 1);
					player.getInventory().deleteItem(23263, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23261, 1);
				}
			}
			// Super Attack (1) into Super Attack Flask (1)
			if (usedWith.getId() == 149 || usedWith.getId() == 23265) {
				if (player.getInventory().containsItem(149, 1)
						&& player.getInventory().containsItem(23265, 1)) {
					player.getInventory().deleteItem(149, 1);
					player.getInventory().deleteItem(23265, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23263, 1);
				}
			}
			// Super Attack (2) into Super Attack Flask (5)
			if (usedWith.getId() == 147 || usedWith.getId() == 23257) {
				if (player.getInventory().containsItem(147, 1)
						&& player.getInventory().containsItem(23257, 1)) {
					player.getInventory().deleteItem(147, 1);
					player.getInventory().deleteItem(23257, 1);
					player.getInventory().addItem(149, 1);
					player.getInventory().addItem(23255, 1);
				}
			}
			// Super Attack (2) into Super Attack Flask (4)
			if (usedWith.getId() == 147 || usedWith.getId() == 23259) {
				if (player.getInventory().containsItem(147, 1)
						&& player.getInventory().containsItem(23259, 1)) {
					player.getInventory().deleteItem(147, 1);
					player.getInventory().deleteItem(23259, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23255, 1);
				}
			}
			// Super Attack (2) into Super Attack Flask (3)
			if (usedWith.getId() == 147 || usedWith.getId() == 23261) {
				if (player.getInventory().containsItem(147, 1)
						&& player.getInventory().containsItem(23261, 1)) {
					player.getInventory().deleteItem(147, 1);
					player.getInventory().deleteItem(23261, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23257, 1);
				}
			}
			// Super Attack (2) into Super Attack Flask (2)
			if (usedWith.getId() == 147 || usedWith.getId() == 23263) {
				if (player.getInventory().containsItem(147, 1)
						&& player.getInventory().containsItem(23263, 1)) {
					player.getInventory().deleteItem(147, 1);
					player.getInventory().deleteItem(23263, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23259, 1);
				}
			}
			// Super Attack (2) into Super Attack Flask (1)
			if (usedWith.getId() == 147 || usedWith.getId() == 23265) {
				if (player.getInventory().containsItem(147, 1)
						&& player.getInventory().containsItem(23265, 1)) {
					player.getInventory().deleteItem(147, 1);
					player.getInventory().deleteItem(23265, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23261, 1);
				}
			}
			// Super Attack (3) into Super Attack Flask (5)
			if (usedWith.getId() == 145 || usedWith.getId() == 23257) {
				if (player.getInventory().containsItem(145, 1)
						&& player.getInventory().containsItem(23257, 1)) {
					player.getInventory().deleteItem(145, 1);
					player.getInventory().deleteItem(23257, 1);
					player.getInventory().addItem(147, 1);
					player.getInventory().addItem(23255, 1);
				}
			}
			// Super Attack (3) into Super Attack Flask (4)
			if (usedWith.getId() == 145 || usedWith.getId() == 23259) {
				if (player.getInventory().containsItem(145, 1)
						&& player.getInventory().containsItem(23259, 1)) {
					player.getInventory().deleteItem(145, 1);
					player.getInventory().deleteItem(23259, 1);
					player.getInventory().addItem(149, 1);
					player.getInventory().addItem(23255, 1);
				}
			}
			// Super Attack (3) into Super Attack Flask (3)
			if (usedWith.getId() == 145 || usedWith.getId() == 23261) {
				if (player.getInventory().containsItem(145, 1)
						&& player.getInventory().containsItem(23261, 1)) {
					player.getInventory().deleteItem(145, 1);
					player.getInventory().deleteItem(23261, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23255, 1);
				}
			}
			// Super Attack (3) into Super Attack Flask (2)
			if (usedWith.getId() == 145 || usedWith.getId() == 23263) {
				if (player.getInventory().containsItem(145, 1)
						&& player.getInventory().containsItem(23263, 1)) {
					player.getInventory().deleteItem(145, 1);
					player.getInventory().deleteItem(23263, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23257, 1);
				}
			}
			// Super Attack (3) into Super Attack Flask (1)
			if (usedWith.getId() == 145 || usedWith.getId() == 23265) {
				if (player.getInventory().containsItem(145, 1)
						&& player.getInventory().containsItem(23265, 1)) {
					player.getInventory().deleteItem(145, 1);
					player.getInventory().deleteItem(23265, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23259, 1);
				}
			}
			// Super Attack (4) into Super Attack Flask (5)
			if (usedWith.getId() == 2436 || usedWith.getId() == 23257) {
				if (player.getInventory().containsItem(2436, 1)
						&& player.getInventory().containsItem(23257, 1)) {
					player.getInventory().deleteItem(2436, 1);
					player.getInventory().deleteItem(23257, 1);
					player.getInventory().addItem(145, 1);
					player.getInventory().addItem(23255, 1);
				}
			}
			// Super Attack (4) into Super Attack Flask (4)
			if (usedWith.getId() == 2436 || usedWith.getId() == 23259) {
				if (player.getInventory().containsItem(2436, 1)
						&& player.getInventory().containsItem(23259, 1)) {
					player.getInventory().deleteItem(2436, 1);
					player.getInventory().deleteItem(23259, 1);
					player.getInventory().addItem(147, 1);
					player.getInventory().addItem(23255, 1);
				}
			}
			// Super Attack (4) into Super Attack Flask (3)
			if (usedWith.getId() == 2436 || usedWith.getId() == 23261) {
				if (player.getInventory().containsItem(2436, 1)
						&& player.getInventory().containsItem(23261, 1)) {
					player.getInventory().deleteItem(2436, 1);
					player.getInventory().deleteItem(23261, 1);
					player.getInventory().addItem(149, 1);
					player.getInventory().addItem(23255, 1);
				}
			}
			// Super Attack (4) into Super Attack Flask (2)
			if (usedWith.getId() == 2436 || usedWith.getId() == 23263) {
				if (player.getInventory().containsItem(2436, 1)
						&& player.getInventory().containsItem(23263, 1)) {
					player.getInventory().deleteItem(2436, 1);
					player.getInventory().deleteItem(23263, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23255, 1);
				}
			}
			// Super Attack (4) into Super Attack Flask (1)
			if (usedWith.getId() == 2436 || usedWith.getId() == 23265) {
				if (player.getInventory().containsItem(2436, 1)
						&& player.getInventory().containsItem(23265, 1)) {
					player.getInventory().deleteItem(2436, 1);
					player.getInventory().deleteItem(23265, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23257, 1);
				}
			}
			// Super Attack (4) into Empty Flask
			if (usedWith.getId() == 2436 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(2436, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(2436, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23259, 1);
				}
			}
			// Super Attack (3) into Empty Flask
			if (usedWith.getId() == 145 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(145, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(145, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23261, 1);
				}
			}
			// Super Attack (2) into Empty Flask
			if (usedWith.getId() == 147 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(147, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(147, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23263, 1);
				}
			}
			// Super Attack (1) into Empty Flask
			if (usedWith.getId() == 149 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(149, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(149, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23265, 1);
				}
			}
			// Super Strength (1) into Super Strength Flask (5)
			if (usedWith.getId() == 161 || usedWith.getId() == 23281) {
				if (player.getInventory().containsItem(161, 1)
						&& player.getInventory().containsItem(23281, 1)) {
					player.getInventory().deleteItem(161, 1);
					player.getInventory().deleteItem(23281, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23279, 1);
				}
			}
			// Super Strength (1) into Super Strength Flask (4)
			if (usedWith.getId() == 161 || usedWith.getId() == 23283) {
				if (player.getInventory().containsItem(161, 1)
						&& player.getInventory().containsItem(23283, 1)) {
					player.getInventory().deleteItem(161, 1);
					player.getInventory().deleteItem(23283, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23281, 1);
				}
			}
			// Super Strength (1) into Super Strength Flask (3)
			if (usedWith.getId() == 161 || usedWith.getId() == 23285) {
				if (player.getInventory().containsItem(161, 1)
						&& player.getInventory().containsItem(23285, 1)) {
					player.getInventory().deleteItem(161, 1);
					player.getInventory().deleteItem(23285, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23283, 1);
				}
			}
			// Super Strength (1) into Super Strength Flask (2)
			if (usedWith.getId() == 161 || usedWith.getId() == 23287) {
				if (player.getInventory().containsItem(161, 1)
						&& player.getInventory().containsItem(23287, 1)) {
					player.getInventory().deleteItem(161, 1);
					player.getInventory().deleteItem(23287, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23285, 1);
				}
			}
			// Super Strength (1) into Super Strength Flask (1)
			if (usedWith.getId() == 161 || usedWith.getId() == 23289) {
				if (player.getInventory().containsItem(161, 1)
						&& player.getInventory().containsItem(23289, 1)) {
					player.getInventory().deleteItem(161, 1);
					player.getInventory().deleteItem(23289, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23287, 1);
				}
			}
			// Super Strength (2) into Super Strength Flask (5)
			if (usedWith.getId() == 159 || usedWith.getId() == 23281) {
				if (player.getInventory().containsItem(159, 1)
						&& player.getInventory().containsItem(23281, 1)) {
					player.getInventory().deleteItem(159, 1);
					player.getInventory().deleteItem(23281, 1);
					player.getInventory().addItem(161, 1);
					player.getInventory().addItem(23279, 1);
				}
			}
			// Super Strength (2) into Super Strength Flask (4)
			if (usedWith.getId() == 159 || usedWith.getId() == 23283) {
				if (player.getInventory().containsItem(159, 1)
						&& player.getInventory().containsItem(23283, 1)) {
					player.getInventory().deleteItem(159, 1);
					player.getInventory().deleteItem(23283, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23279, 1);
				}
			}
			// Super Strength (2) into Super Strength Flask (3)
			if (usedWith.getId() == 159 || usedWith.getId() == 23285) {
				if (player.getInventory().containsItem(159, 1)
						&& player.getInventory().containsItem(23285, 1)) {
					player.getInventory().deleteItem(159, 1);
					player.getInventory().deleteItem(23285, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23281, 1);
				}
			}
			// Super Strength (2) into Super Strength Flask (2)
			if (usedWith.getId() == 159 || usedWith.getId() == 23287) {
				if (player.getInventory().containsItem(159, 1)
						&& player.getInventory().containsItem(23287, 1)) {
					player.getInventory().deleteItem(159, 1);
					player.getInventory().deleteItem(23287, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23283, 1);
				}
			}
			// Super Strength (2) into Super Strength Flask (1)
			if (usedWith.getId() == 159 || usedWith.getId() == 23289) {
				if (player.getInventory().containsItem(159, 1)
						&& player.getInventory().containsItem(23289, 1)) {
					player.getInventory().deleteItem(159, 1);
					player.getInventory().deleteItem(23289, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23285, 1);
				}
			}
			// Super Strength (3) into Super Strength Flask (5)
			if (usedWith.getId() == 157 || usedWith.getId() == 23281) {
				if (player.getInventory().containsItem(157, 1)
						&& player.getInventory().containsItem(23281, 1)) {
					player.getInventory().deleteItem(157, 1);
					player.getInventory().deleteItem(23281, 1);
					player.getInventory().addItem(159, 1);
					player.getInventory().addItem(23279, 1);
				}
			}
			// Super Strength (3) into Super Strength Flask (4)
			if (usedWith.getId() == 157 || usedWith.getId() == 23283) {
				if (player.getInventory().containsItem(157, 1)
						&& player.getInventory().containsItem(23283, 1)) {
					player.getInventory().deleteItem(157, 1);
					player.getInventory().deleteItem(23283, 1);
					player.getInventory().addItem(161, 1);
					player.getInventory().addItem(23279, 1);
				}
			}
			// Super Strength (3) into Super Strength Flask (3)
			if (usedWith.getId() == 157 || usedWith.getId() == 23285) {
				if (player.getInventory().containsItem(157, 1)
						&& player.getInventory().containsItem(23285, 1)) {
					player.getInventory().deleteItem(157, 1);
					player.getInventory().deleteItem(23285, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23279, 1);
				}
			}
			// Super Strength (3) into Super Strength Flask (2)
			if (usedWith.getId() == 157 || usedWith.getId() == 23287) {
				if (player.getInventory().containsItem(157, 1)
						&& player.getInventory().containsItem(23287, 1)) {
					player.getInventory().deleteItem(157, 1);
					player.getInventory().deleteItem(23287, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23281, 1);
				}
			}
			// Super Strength (3) into Super Strength Flask (1)
			if (usedWith.getId() == 157 || usedWith.getId() == 23289) {
				if (player.getInventory().containsItem(157, 1)
						&& player.getInventory().containsItem(23289, 1)) {
					player.getInventory().deleteItem(157, 1);
					player.getInventory().deleteItem(23289, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23283, 1);
				}
			}
			// Super Strength (4) into Super Strength Flask (5)
			if (usedWith.getId() == 2440 || usedWith.getId() == 23281) {
				if (player.getInventory().containsItem(2440, 1)
						&& player.getInventory().containsItem(23281, 1)) {
					player.getInventory().deleteItem(2440, 1);
					player.getInventory().deleteItem(23281, 1);
					player.getInventory().addItem(157, 1);
					player.getInventory().addItem(23279, 1);
				}
			}
			// Super Strength (4) into Super Strength Flask (4)
			if (usedWith.getId() == 2440 || usedWith.getId() == 23283) {
				if (player.getInventory().containsItem(2440, 1)
						&& player.getInventory().containsItem(23283, 1)) {
					player.getInventory().deleteItem(2440, 1);
					player.getInventory().deleteItem(23283, 1);
					player.getInventory().addItem(159, 1);
					player.getInventory().addItem(23279, 1);
				}
			}
			// Super Strength (4) into Super Strength Flask (3)
			if (usedWith.getId() == 2440 || usedWith.getId() == 23285) {
				if (player.getInventory().containsItem(2440, 1)
						&& player.getInventory().containsItem(23285, 1)) {
					player.getInventory().deleteItem(2440, 1);
					player.getInventory().deleteItem(23285, 1);
					player.getInventory().addItem(161, 1);
					player.getInventory().addItem(23279, 1);
				}
			}
			// Super Strength (4) into Super Strength Flask (2)
			if (usedWith.getId() == 2440 || usedWith.getId() == 23287) {
				if (player.getInventory().containsItem(2440, 1)
						&& player.getInventory().containsItem(23287, 1)) {
					player.getInventory().deleteItem(2440, 1);
					player.getInventory().deleteItem(23287, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23279, 1);
				}
			}
			// Super Strength (4) into Super Strength Flask (1)
			if (usedWith.getId() == 2440 || usedWith.getId() == 23289) {
				if (player.getInventory().containsItem(2440, 1)
						&& player.getInventory().containsItem(23289, 1)) {
					player.getInventory().deleteItem(2440, 1);
					player.getInventory().deleteItem(23289, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23281, 1);
				}
			}
			// Super Strength (4) into Empty Flask
			if (usedWith.getId() == 2440 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(2440, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(2440, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23283, 1);
				}
			}
			// Super Strength (3) into Empty Flask
			if (usedWith.getId() == 157 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(157, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(157, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23285, 1);
				}
			}
			// Super Strength (2) into Empty Flask
			if (usedWith.getId() == 159 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(159, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(159, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23287, 1);
				}
			}
			// Super Strength (1) into Empty Flask
			if (usedWith.getId() == 161 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(161, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(161, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23289, 1);
				}
			}
			// Super Defence (1) into Super Defence Flask (5)
			if (usedWith.getId() == 167 || usedWith.getId() == 23293) {
				if (player.getInventory().containsItem(167, 1)
						&& player.getInventory().containsItem(23293, 1)) {
					player.getInventory().deleteItem(167, 1);
					player.getInventory().deleteItem(23293, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23291, 1);
				}
			}
			// Super Defence (1) into Super Defence Flask (4)
			if (usedWith.getId() == 167 || usedWith.getId() == 23295) {
				if (player.getInventory().containsItem(167, 1)
						&& player.getInventory().containsItem(23295, 1)) {
					player.getInventory().deleteItem(167, 1);
					player.getInventory().deleteItem(23295, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23293, 1);
				}
			}
			// Super Defence (1) into Super Defence Flask (3)
			if (usedWith.getId() == 167 || usedWith.getId() == 23297) {
				if (player.getInventory().containsItem(167, 1)
						&& player.getInventory().containsItem(23297, 1)) {
					player.getInventory().deleteItem(167, 1);
					player.getInventory().deleteItem(23297, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23295, 1);
				}
			}
			// Super Defence (1) into Super Defence Flask (2)
			if (usedWith.getId() == 167 || usedWith.getId() == 23299) {
				if (player.getInventory().containsItem(167, 1)
						&& player.getInventory().containsItem(23299, 1)) {
					player.getInventory().deleteItem(167, 1);
					player.getInventory().deleteItem(23299, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23297, 1);
				}
			}
			// Super Defence (1) into Super Defence Flask (1)
			if (usedWith.getId() == 167 || usedWith.getId() == 23301) {
				if (player.getInventory().containsItem(167, 1)
						&& player.getInventory().containsItem(23301, 1)) {
					player.getInventory().deleteItem(167, 1);
					player.getInventory().deleteItem(23301, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23299, 1);
				}
			}
			// Super Defence (2) into Super Defence Flask (5)
			if (usedWith.getId() == 165 || usedWith.getId() == 23293) {
				if (player.getInventory().containsItem(165, 1)
						&& player.getInventory().containsItem(23293, 1)) {
					player.getInventory().deleteItem(165, 1);
					player.getInventory().deleteItem(23293, 1);
					player.getInventory().addItem(167, 1);
					player.getInventory().addItem(23291, 1);
				}
			}
			// Super Defence (2) into Super Defence Flask (4)
			if (usedWith.getId() == 165 || usedWith.getId() == 23295) {
				if (player.getInventory().containsItem(165, 1)
						&& player.getInventory().containsItem(23295, 1)) {
					player.getInventory().deleteItem(165, 1);
					player.getInventory().deleteItem(23295, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23291, 1);
				}
			}
			// Super Defence (2) into Super Defence Flask (3)
			if (usedWith.getId() == 165 || usedWith.getId() == 23297) {
				if (player.getInventory().containsItem(165, 1)
						&& player.getInventory().containsItem(23297, 1)) {
					player.getInventory().deleteItem(165, 1);
					player.getInventory().deleteItem(23297, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23293, 1);
				}
			}
			// Super Defence (2) into Super Defence Flask (2)
			if (usedWith.getId() == 165 || usedWith.getId() == 23299) {
				if (player.getInventory().containsItem(165, 1)
						&& player.getInventory().containsItem(23299, 1)) {
					player.getInventory().deleteItem(165, 1);
					player.getInventory().deleteItem(23299, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23295, 1);
				}
			}
			// Super Defence (2) into Super Defence Flask (1)
			if (usedWith.getId() == 165 || usedWith.getId() == 23301) {
				if (player.getInventory().containsItem(165, 1)
						&& player.getInventory().containsItem(23301, 1)) {
					player.getInventory().deleteItem(165, 1);
					player.getInventory().deleteItem(23301, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23297, 1);
				}
			}
			// Super Defence (3) into Super Defence Flask (5)
			if (usedWith.getId() == 163 || usedWith.getId() == 23293) {
				if (player.getInventory().containsItem(163, 1)
						&& player.getInventory().containsItem(23293, 1)) {
					player.getInventory().deleteItem(163, 1);
					player.getInventory().deleteItem(23293, 1);
					player.getInventory().addItem(165, 1);
					player.getInventory().addItem(23291, 1);
				}
			}
			// Super Defence (3) into Super Defence Flask (4)
			if (usedWith.getId() == 163 || usedWith.getId() == 23295) {
				if (player.getInventory().containsItem(163, 1)
						&& player.getInventory().containsItem(23295, 1)) {
					player.getInventory().deleteItem(163, 1);
					player.getInventory().deleteItem(23295, 1);
					player.getInventory().addItem(167, 1);
					player.getInventory().addItem(23291, 1);
				}
			}
			// Super Defence (3) into Super Defence Flask (3)
			if (usedWith.getId() == 163 || usedWith.getId() == 23297) {
				if (player.getInventory().containsItem(163, 1)
						&& player.getInventory().containsItem(23297, 1)) {
					player.getInventory().deleteItem(163, 1);
					player.getInventory().deleteItem(23297, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23291, 1);
				}
			}
			// Super Defence (3) into Super Defence Flask (2)
			if (usedWith.getId() == 163 || usedWith.getId() == 23299) {
				if (player.getInventory().containsItem(163, 1)
						&& player.getInventory().containsItem(23299, 1)) {
					player.getInventory().deleteItem(163, 1);
					player.getInventory().deleteItem(23299, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23293, 1);
				}
			}
			// Super Defence (3) into Super Defence Flask (1)
			if (usedWith.getId() == 163 || usedWith.getId() == 23301) {
				if (player.getInventory().containsItem(163, 1)
						&& player.getInventory().containsItem(23301, 1)) {
					player.getInventory().deleteItem(163, 1);
					player.getInventory().deleteItem(23301, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23295, 1);
				}
			}
			// Super Defence (4) into Super Defence Flask (5)
			if (usedWith.getId() == 2442 || usedWith.getId() == 23293) {
				if (player.getInventory().containsItem(2442, 1)
						&& player.getInventory().containsItem(23293, 1)) {
					player.getInventory().deleteItem(2442, 1);
					player.getInventory().deleteItem(23293, 1);
					player.getInventory().addItem(163, 1);
					player.getInventory().addItem(23291, 1);
				}
			}
			// Super Defence (4) into Super Defence Flask (4)
			if (usedWith.getId() == 2442 || usedWith.getId() == 23295) {
				if (player.getInventory().containsItem(2442, 1)
						&& player.getInventory().containsItem(23295, 1)) {
					player.getInventory().deleteItem(2442, 1);
					player.getInventory().deleteItem(23295, 1);
					player.getInventory().addItem(165, 1);
					player.getInventory().addItem(23291, 1);
				}
			}
			// Super Defence (4) into Super Defence Flask (3)
			if (usedWith.getId() == 2442 || usedWith.getId() == 23297) {
				if (player.getInventory().containsItem(2442, 1)
						&& player.getInventory().containsItem(23297, 1)) {
					player.getInventory().deleteItem(2442, 1);
					player.getInventory().deleteItem(23297, 1);
					player.getInventory().addItem(167, 1);
					player.getInventory().addItem(23291, 1);
				}
			}
			// Super Defence (4) into Super Defence Flask (2)
			if (usedWith.getId() == 2442 || usedWith.getId() == 23299) {
				if (player.getInventory().containsItem(2442, 1)
						&& player.getInventory().containsItem(23299, 1)) {
					player.getInventory().deleteItem(2442, 1);
					player.getInventory().deleteItem(23299, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23291, 1);
				}
			}
			// Super Defence (4) into Super Defence Flask (1)
			if (usedWith.getId() == 2442 || usedWith.getId() == 23301) {
				if (player.getInventory().containsItem(2442, 1)
						&& player.getInventory().containsItem(23301, 1)) {
					player.getInventory().deleteItem(2442, 1);
					player.getInventory().deleteItem(23301, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23293, 1);
				}
			}
			// Super Defence (4)
			if (usedWith.getId() == 2442 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(2442, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(2442, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23295, 1);
				}
			}
			// Super Defence (3)
			if (usedWith.getId() == 163 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(163, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(163, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23297, 1);
				}
			}
			// Super Defence (2)
			if (usedWith.getId() == 165 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(165, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(165, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23299, 1);
				}
			}
			// Super Defence (1)
			if (usedWith.getId() == 167 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(167, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(167, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23301, 1);
				}
			}

			/**
			 * Overloads
			 */

			// Overload (1) into Overload Flask (5)
			if (usedWith.getId() == 15335 || usedWith.getId() == 23532) {
				if (player.getInventory().containsItem(15335, 1)
						&& player.getInventory().containsItem(26751, 1)) {
					player.getInventory().deleteItem(15335, 1);
					player.getInventory().deleteItem(23532, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
				}
			}
			// Overload (1) into Overload Flask (4)
			if (usedWith.getId() == 15335 || usedWith.getId() == 23533) {
				if (player.getInventory().containsItem(15335, 1)
						&& player.getInventory().containsItem(23533, 1)) {
					player.getInventory().deleteItem(15335, 1);
					player.getInventory().deleteItem(23533, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23532, 1); // id for (5)
				}
			}
			// Overload (1) into Overload Flask (3)
			if (usedWith.getId() == 15335 || usedWith.getId() == 23534) {
				if (player.getInventory().containsItem(15335, 1)
						&& player.getInventory().containsItem(23534, 1)) {
					player.getInventory().deleteItem(15335, 1);
					player.getInventory().deleteItem(23534, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23533, 1); // id for (4)
				}
			}
			// Overload (1) into Overload Flask (2)
			if (usedWith.getId() == 15335 || usedWith.getId() == 23535) {
				if (player.getInventory().containsItem(15335, 1)
						&& player.getInventory().containsItem(23535, 1)) {
					player.getInventory().deleteItem(15335, 1);
					player.getInventory().deleteItem(23535, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23534, 1); // id for (3)
				}
			}
			// Overload (1) into Overload Flask (1)
			if (usedWith.getId() == 15335 || usedWith.getId() == 23536) {
				if (player.getInventory().containsItem(15335, 1)
						&& player.getInventory().containsItem(23536, 1)) {
					player.getInventory().deleteItem(15335, 1);
					player.getInventory().deleteItem(23536, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23535, 1); // id for (2)
				}
			}
			// Overload (2) into Overload Flask (5)
			if (usedWith.getId() == 15334 || usedWith.getId() == 23532) {
				if (player.getInventory().containsItem(15334, 1)
						&& player.getInventory().containsItem(23532, 1)) {
					player.getInventory().deleteItem(15334, 1);
					player.getInventory().deleteItem(23532, 1);
					player.getInventory().addItem(167, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
				}
			}
			// Overload (2) into Overload Flask (4)
			if (usedWith.getId() == 15334 || usedWith.getId() == 23533) {
				if (player.getInventory().containsItem(15334, 1)
						&& player.getInventory().containsItem(23533, 1)) {
					player.getInventory().deleteItem(15334, 1);
					player.getInventory().deleteItem(23533, 1);
					player.getInventory().addItem(167, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
				}
			}
			// Overload (2) into Overload Flask (3)
			if (usedWith.getId() == 15334 || usedWith.getId() == 23534) {
				if (player.getInventory().containsItem(15334, 1)
						&& player.getInventory().containsItem(23534, 1)) {
					player.getInventory().deleteItem(15334, 1);
					player.getInventory().deleteItem(23534, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23532, 1); // id for (5)
				}
			}
			// Overload (2) into Overload Flask (2)
			if (usedWith.getId() == 15334 || usedWith.getId() == 23535) {
				if (player.getInventory().containsItem(15334, 1)
						&& player.getInventory().containsItem(23535, 1)) {
					player.getInventory().deleteItem(15334, 1);
					player.getInventory().deleteItem(23535, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23533, 1); // id for (4)
				}
			}
			// Overload (2) into Overload Flask (1)
			if (usedWith.getId() == 15334 || usedWith.getId() == 23536) {
				if (player.getInventory().containsItem(15334, 1)
						&& player.getInventory().containsItem(23536, 1)) {
					player.getInventory().deleteItem(15334, 1);
					player.getInventory().deleteItem(23536, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23534, 1); // id for (3)
				}
			}
			// Overload (3) into Overload Flask (5)
			if (usedWith.getId() == 15333 || usedWith.getId() == 23532) {
				if (player.getInventory().containsItem(15333, 1)
						&& player.getInventory().containsItem(23532, 1)) {
					player.getInventory().deleteItem(15333, 1);
					player.getInventory().deleteItem(23532, 1);
					player.getInventory().addItem(15333, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
				}
			}
			// Overload (3) into Overload Flask (4)
			if (usedWith.getId() == 15333 || usedWith.getId() == 23533) {
				if (player.getInventory().containsItem(15333, 1)
						&& player.getInventory().containsItem(23533, 1)) {
					player.getInventory().deleteItem(15333, 1);
					player.getInventory().deleteItem(23533, 1);
					player.getInventory().addItem(167, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
				}
			}
			// Overload (3) into Overload Flask (3)
			if (usedWith.getId() == 15333 || usedWith.getId() == 23534) {
				if (player.getInventory().containsItem(15333, 1)
						&& player.getInventory().containsItem(23534, 1)) {
					player.getInventory().deleteItem(15333, 1);
					player.getInventory().deleteItem(23534, 1);
					player.getInventory().addItem(167, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
				}
			}
			// Overload (3) into Overload Flask (2)
			if (usedWith.getId() == 15333 || usedWith.getId() == 23535) {
				if (player.getInventory().containsItem(15333, 1)
						&& player.getInventory().containsItem(23535, 1)) {
					player.getInventory().deleteItem(15333, 1);
					player.getInventory().deleteItem(23535, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23532, 1); // id for (5)
				}
			}
			// Overload (3) into Overload Flask (1)
			if (usedWith.getId() == 15333 || usedWith.getId() == 23536) {
				if (player.getInventory().containsItem(15333, 1)
						&& player.getInventory().containsItem(23536, 1)) {
					player.getInventory().deleteItem(15333, 1);
					player.getInventory().deleteItem(23536, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23533, 1); // id for (4)
				}
			}
			// Overload (4) into Overload Flask (5)
			if (usedWith.getId() == 15332 || usedWith.getId() == 23532) {
				if (player.getInventory().containsItem(15332, 1)
						&& player.getInventory().containsItem(23532, 1)) {
					player.getInventory().deleteItem(15332, 1);
					player.getInventory().deleteItem(23532, 1);
					player.getInventory().addItem(163, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
				}
			}
			// Overload (4) into Overload Flask (4)
			if (usedWith.getId() == 15332 || usedWith.getId() == 23533) {
				if (player.getInventory().containsItem(15332, 1)
						&& player.getInventory().containsItem(23533, 1)) {
					player.getInventory().deleteItem(15332, 1);
					player.getInventory().deleteItem(23533, 1);
					player.getInventory().addItem(165, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
				}
			}
			// Overload (4) into Overload Flask (3)
			if (usedWith.getId() == 15332 || usedWith.getId() == 23534) {
				if (player.getInventory().containsItem(15332, 1)
						&& player.getInventory().containsItem(23534, 1)) {
					player.getInventory().deleteItem(15332, 1);
					player.getInventory().deleteItem(23534, 1);
					player.getInventory().addItem(167, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
				}
			}
			// Overload (4) into Overload Flask (2)
			if (usedWith.getId() == 15332 || usedWith.getId() == 23535) {
				if (player.getInventory().containsItem(15332, 1)
						&& player.getInventory().containsItem(23535, 1)) {
					player.getInventory().deleteItem(15332, 1);
					player.getInventory().deleteItem(23535, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23531, 1); // id for (6)
				}
			}
			// Overload (4) into Overload Flask (1)
			if (usedWith.getId() == 15332 || usedWith.getId() == 23536) {
				if (player.getInventory().containsItem(15332, 1)
						&& player.getInventory().containsItem(23536, 1)) {
					player.getInventory().deleteItem(15332, 1);
					player.getInventory().deleteItem(23536, 1);
					player.getInventory().addItem(229, 1);
					player.getInventory().addItem(23532, 1); // id for (5)
				}
			}
			// Overload (1)
			if (usedWith.getId() == 15335 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(15335, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(15335, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23536, 1);
				}
			}
			// Overload (2)
			if (usedWith.getId() == 15334 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(15334, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(15334, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23535, 1);
				}
			}
			// Overload (3)
			if (usedWith.getId() == 15333 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(15333, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(15333, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23534, 1);
				}
			}
			// Overload (4)
			if (usedWith.getId() == 15332 || usedWith.getId() == 23191) {
				if (player.getInventory().containsItem(15332, 1)
						&& player.getInventory().containsItem(23191, 1)) {
					player.getInventory().deleteItem(15332, 1);
					player.getInventory().deleteItem(23191, 1);
					player.getInventory().addItem(23533, 1);
				}
			}
			//Dragon Sq shield
			  if (itemUsed.getId() == 2368 || usedWith.getId() == 2366) {
	                if (player.getInventory().containsItem(2366, 1)
	                                && player.getInventory().containsItem(2368, 1)) {
	                        player.getInventory().deleteItem(2366, 1);
	                        player.getInventory().deleteItem(2368, 1);
	                        player.getInventory().addItem(1187, 1);
							player.getPackets().sendGameMessage("You create a Dragon sq shield.");
	                }
	            }
			  if (itemUsed.getId() == 2366 || usedWith.getId() == 2368) {
	                if (player.getInventory().containsItem(2366, 1)
	                                && player.getInventory().containsItem(2368, 1)) {
	                        player.getInventory().deleteItem(2366, 1);
	                        player.getInventory().deleteItem(2368, 1);
	                        player.getInventory().addItem(1187, 1);
							player.getPackets().sendGameMessage("You create a Dragon sq shield.");
	                }
	            }
			  //doogle sardine
			  if (itemUsed.getId() == 327 || usedWith.getId() == 1573) {
	                if (player.getInventory().containsItem(1573, 1)
	                                && player.getInventory().containsItem(327, 1)) {
	                        player.getInventory().deleteItem(1573, 1);
	                        player.getInventory().deleteItem(327, 1);
	                        player.getInventory().addItem(1552, 1);
							player.getPackets().sendGameMessage("You wrap the sardine in the doogle leaf.");
	                }
	            }
			  if (itemUsed.getId() == 1573 || usedWith.getId() == 327) {
	                if (player.getInventory().containsItem(1573, 1)
	                                && player.getInventory().containsItem(327, 1)) {
	                        player.getInventory().deleteItem(1573, 1);
	                        player.getInventory().deleteItem(327, 1);
	                        player.getInventory().addItem(1552, 1);
							player.getPackets().sendGameMessage("You wrap the sardine in the doogle leaf.");
	                }
	            }
			  
			  //
			
			//Dragonfire shield
            if (itemUsed.getId() == 11286 || usedWith.getId() == 1540) {
                if (player.getInventory().containsItem(11286, 1)
                                && player.getInventory().containsItem(1540, 1)) {
                        player.getInventory().deleteItem(11286, 1);
                        player.getInventory().deleteItem(1540, 1);
                        player.getInventory().addItem(11283, 1);
						player.getPackets().sendGameMessage("You create a Dragonfire Shield!");
                }
            }
            if (itemUsed.getId() == 1540 || usedWith.getId() == 11286) {
                if (player.getInventory().containsItem(11286, 1)
                                && player.getInventory().containsItem(1540, 1)) {
                        player.getInventory().deleteItem(11286, 1);
                        player.getInventory().deleteItem(1540, 1);
                        player.getInventory().addItem(11283, 1);
						player.getPackets().sendGameMessage("You create a Dragonfire Shield!");
                }
            }
            //Fire Arrows
            if (itemUsed.getId() == 1485 || usedWith.getId() == 882) {
                if (player.getInventory().containsItem(1485, 1)
                                && player.getInventory().containsItem(882, 1)) {
                        player.getInventory().deleteItem(1485, 1);
                        player.getInventory().deleteItem(882, 1);
                        player.getInventory().addItem(942, 1);
						player.getPackets().sendGameMessage("You create a bronze fire arrow.");
                }
            }
            if (itemUsed.getId() == 882 || usedWith.getId() == 1485) {
                if (player.getInventory().containsItem(1485, 1)
                                && player.getInventory().containsItem(882, 1)) {
                        player.getInventory().deleteItem(1485, 1);
                        player.getInventory().deleteItem(882, 1);
                        player.getInventory().addItem(942, 1);
						player.getPackets().sendGameMessage("You create a bronze fire arrow.");
                }
            }
            //Roe
            if (itemUsed.getId() == 946 || usedWith.getId() == 11328) {
                if (player.getInventory().containsItem(946, 1)
                                && player.getInventory().containsItem(11328, 1)) {
                  
                        player.getInventory().deleteItem(11328, 1);
                        player.getInventory().addItem(11324, 1);
                }
            }
            if (itemUsed.getId() == 11328 || usedWith.getId() == 946) {
                if (player.getInventory().containsItem(946, 1)
                                && player.getInventory().containsItem(11328, 1)) {
                
                        player.getInventory().deleteItem(11328, 1);
                        player.getInventory().addItem(11324, 1);
                }
            }
            
            if (itemUsed.getId() == 946 || usedWith.getId() == 11330) {
                if (player.getInventory().containsItem(946, 1)
                                && player.getInventory().containsItem(11330, 1)) {
                    
                        player.getInventory().deleteItem(11330, 1);
                        player.getInventory().addItem(11324, 1);
                }
            }
            if (itemUsed.getId() == 11330 || usedWith.getId() == 946) {
                if (player.getInventory().containsItem(946, 1)
                                && player.getInventory().containsItem(11330, 1)) {
                     
                        player.getInventory().deleteItem(11330, 1);
                        player.getInventory().addItem(11324, 1);
                }
            }
            if (contains(272, 273, itemUsed, usedWith) || (contains(273, 272, itemUsed, usedWith))) {
			player.getInventory().deleteItem(272, 1);
			player.getInventory().deleteItem(273, 1);
			player.getInventory().addItem(274, 1);
            }
            if (itemUsed.getId() == 946 || usedWith.getId() == 11332) {
                if (player.getInventory().containsItem(946, 1)
                                && player.getInventory().containsItem(11332, 1)) {
                      
                        player.getInventory().deleteItem(11332, 1);
                        player.getInventory().addItem(11324, 1);
                }
            }
            if (itemUsed.getId() == 11332 || usedWith.getId() == 946) {
                if (player.getInventory().containsItem(946, 1)
                                && player.getInventory().containsItem(11332, 1)) {
                        
                        player.getInventory().deleteItem(11332, 1);
                        player.getInventory().addItem(11324, 1);
                }
            }
            //SS Godswords
            if (itemUsed.getId() == 11690 || usedWith.getId() == 13746) {
                if (player.getInventory().containsItem(11690, 1)
                                && player.getInventory().containsItem(13746, 1)) {
                        player.getInventory().deleteItem(11690, 1);
                        player.getInventory().deleteItem(13746, 1);
                        player.getInventory().addItem(29961, 1);
						player.getPackets().sendGameMessage("You attach the sigil to the blade and create an Arcane godsword.");
                }
            }
            if (itemUsed.getId() == 11690 || usedWith.getId() == 13748) {
                if (player.getInventory().containsItem(11690, 1)
                                && player.getInventory().containsItem(13748, 1)) {
                        player.getInventory().deleteItem(11690, 1);
                        player.getInventory().deleteItem(13748, 1);
                        player.getInventory().addItem(29960, 1);
						player.getPackets().sendGameMessage("You attach the sigil to the blade and create a Divine godsword.");
                }
            }
            if (itemUsed.getId() == 11690 || usedWith.getId() == 13750) {
                if (player.getInventory().containsItem(11690, 1)
                                && player.getInventory().containsItem(13750, 1)) {
                        player.getInventory().deleteItem(11690, 1);
                        player.getInventory().deleteItem(13750, 1);
                        player.getInventory().addItem(29959, 1);
						player.getPackets().sendGameMessage("You attach the sigil to the blade and create an Elysian godsword.");
                }
             }
            if (itemUsed.getId() == 11690 || usedWith.getId() == 13752) {
                if (player.getInventory().containsItem(11690, 1)
                                && player.getInventory().containsItem(13752, 1)) {
                        player.getInventory().deleteItem(11690, 1);
                        player.getInventory().deleteItem(13752, 1);
                        player.getInventory().addItem(29958, 1);
						player.getPackets().sendGameMessage("You attach the sigil to the blade and create a Spectral godsword.");
                }
            }
            //Godswords
			if (itemUsed.getId() == 11710 || usedWith.getId() == 11712 || usedWith.getId() == 11714) {
                    if (player.getInventory().containsItem(11710, 1)
                                    && player.getInventory().containsItem(11712, 1)
                                    && player.getInventory().containsItem(11714, 1)) {
                            player.getInventory().deleteItem(11710, 1);
                            player.getInventory().deleteItem(11712, 1);
                            player.getInventory().deleteItem(11714, 1);
                            player.getInventory().addItem(11690, 1);
							player.getPackets().sendGameMessage("You made a godsword blade.");
                    }
                }
                if (itemUsed.getId() == 11690 || usedWith.getId() == 11702) {
                    if (player.getInventory().containsItem(11690, 1)
                                    && player.getInventory().containsItem(11702, 1)) {
                            player.getInventory().deleteItem(11690, 1);
                            player.getInventory().deleteItem(11702, 1);
                            player.getInventory().addItem(11694, 1);
							player.getPackets().sendGameMessage("You attach the hilt to the blade and make an Armadyl godsword.");
                    }
                }
                if (itemUsed.getId() == 11690 || usedWith.getId() == 11704) {
                    if (player.getInventory().containsItem(11690, 1)
                                    && player.getInventory().containsItem(11704, 1)) {
                            player.getInventory().deleteItem(11690, 1);
                            player.getInventory().deleteItem(11704, 1);
                            player.getInventory().addItem(11696, 1);
							player.getPackets().sendGameMessage("You attach the hilt to the blade and make an Bandos godsword.");
                    }
                }
                if (itemUsed.getId() == 11690 || usedWith.getId() == 11706) {
                    if (player.getInventory().containsItem(11690, 1)
                                    && player.getInventory().containsItem(11706, 1)) {
                            player.getInventory().deleteItem(11690, 1);
                            player.getInventory().deleteItem(11706, 1);
                            player.getInventory().addItem(11698, 1);
							player.getPackets().sendGameMessage("You attach the hilt to the blade and make an Saradomin godsword.");
                    }
                 }
                if (itemUsed.getId() == 11690 || usedWith.getId() == 11708) {
                    if (player.getInventory().containsItem(11690, 1)
                                    && player.getInventory().containsItem(11708, 1)) {
                            player.getInventory().deleteItem(11690, 1);
                            player.getInventory().deleteItem(11708, 1);
                            player.getInventory().addItem(11700, 1);
							player.getPackets().sendGameMessage("You attach the hilt to the blade and make an Zamorak godsword.");
                    }
                }
                //Polypore staff
                if (itemUsed.getId() == 22498 || usedWith.getId() == 22448 || usedWith.getId() == 554) {
                    if (player.getInventory().containsItem(22498, 1)
                                    && player.getInventory().containsItem(22448, 3000)
                                    && player.getInventory().containsItem(554, 15000)
                                    && (player.getSkills().getLevel(Skills.FARMING) > 79)) {
                            player.getInventory().deleteItem(22498, 1);
                            player.getInventory().deleteItem(22448, 3000);
                            player.getInventory().deleteItem(554, 15000);
                            player.getInventory().addItem(22494, 1);
							player.getPackets().sendGameMessage("You craft a Polypore Staff.");
                    } else {
                    	player.getDialogueManager().startDialogue("SimpleMessage","You must have 80 farming, 15k fire runes, 3k polypore spore and 1 polypore stick to make this.");
                   return;
                    }
                }
                if (itemUsed.getId() == 22448|| usedWith.getId() == 22498 || usedWith.getId() == 554) {
                    if (player.getInventory().containsItem(22498, 1)
                                    && player.getInventory().containsItem(22448, 3000)
                                    && player.getInventory().containsItem(554, 15000)
                                    && (player.getSkills().getLevel(Skills.FARMING) > 79)) {
                            player.getInventory().deleteItem(22498, 1);
                            player.getInventory().deleteItem(22448, 3000);
                            player.getInventory().deleteItem(554, 15000);
                            player.getInventory().addItem(22494, 1);
							player.getPackets().sendGameMessage("You craft a Polypore Staff.");
                    } else {
                    	player.getDialogueManager().startDialogue("SimpleMessage","You must have 80 farming, 15k fire runes, 3k polypore spore and 1 polypore stick to make this.");
                   return;
                    }
                }
                //Ganodermic robes
                if (itemUsed.getId() == 22451 && usedWith.getId() == 22452) {//Visor
                	player.getDialogueManager().startDialogue("PolyVisor");
                }
                if (itemUsed.getId() == 22451 && usedWith.getId() == 22454) {//Leggings
                	player.getDialogueManager().startDialogue("PolyLeggings");
                }
                if (itemUsed.getId() == 22451 && usedWith.getId() == 22456) {//poncho
                	player.getDialogueManager().startDialogue("PolyPoncho");
                }
                //Grifolic Robes
                if (itemUsed.getId() == 22450 && usedWith.getId() == 22452) {//Visor
                	player.getDialogueManager().startDialogue("PolyVisor");
                }
                if (itemUsed.getId() == 22450 && usedWith.getId() == 22454) {//Leggings
                	player.getDialogueManager().startDialogue("PolyLeggings");
                }
                if (itemUsed.getId() == 22450 && usedWith.getId() == 22456) {//poncho
                	player.getDialogueManager().startDialogue("PolyPoncho");
                }
                //Fungal Robes
                if (itemUsed.getId() == 22449 && usedWith.getId() == 22452) {//Visor
                	player.getDialogueManager().startDialogue("PolyVisor");
                }
                if (itemUsed.getId() == 22449 && usedWith.getId() == 22454) {//Leggings
                	player.getDialogueManager().startDialogue("PolyLeggings");
                }
                if (itemUsed.getId() == 22449 && usedWith.getId() == 22456) {//poncho
                	player.getDialogueManager().startDialogue("PolyPoncho");
                }
			int herblore = Herblore.isHerbloreSkill(itemUsed, usedWith);
			if (herblore > -1) {
				player.getDialogueManager().startDialogue("HerbloreD",
						herblore, itemUsed, usedWith);
				return;
			}
			if (itemUsed.getId() == LeatherCrafting.NEEDLE.getId()
					|| usedWith.getId() == LeatherCrafting.NEEDLE.getId()) {
				if (LeatherCrafting
						.handleItemOnItem(player, itemUsed, usedWith)) {
					return;
				}
			}
			Sets set = ArmourSets.getArmourSet(itemUsedId, itemUsedWithId);
			if (set != null) {
				ArmourSets.exchangeSets(player, set);
				return;
			}
			if (Firemaking.isFiremaking(player, itemUsed, usedWith))
				return;
			//Gem Cutting
			else if (contains(1755, Gem.LIMESTONE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.LIMESTONE);
			else if (contains(1755, Gem.OPAL.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.OPAL);
			else if (contains(1755, Gem.JADE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.JADE);
			else if (contains(1755, Gem.RED_TOPAZ.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.RED_TOPAZ);
			else if (contains(1755, Gem.SAPPHIRE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.SAPPHIRE);
			else if (contains(1755, Gem.EMERALD.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.EMERALD);
			else if (contains(1755, Gem.RUBY.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.RUBY);
			else if (contains(1755, Gem.DIAMOND.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.DIAMOND);
			else if (contains(1755, Gem.DRAGONSTONE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.DRAGONSTONE);
			else if (contains(1755, Gem.ONYX.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.ONYX);
			//Bolt Tip Making
			else if (contains(1755, BoltTips.OPAL.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.OPAL);
			else if (contains(1755, BoltTips.JADE.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.JADE);
			else if (contains(1755, BoltTips.RED_TOPAZ.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.RED_TOPAZ);
			else if (contains(1755, BoltTips.SAPPHIRE.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.SAPPHIRE);
			else if (contains(1755, BoltTips.EMERALD.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.EMERALD);
			else if (contains(1755, BoltTips.RUBY.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.RUBY);
			else if (contains(1755, BoltTips.DIAMOND.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.DIAMOND);
			else if (contains(1755, BoltTips.DRAGONSTONE.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.DRAGONSTONE);
			else if (contains(1755, BoltTips.ONYX.getGemName(), itemUsed, usedWith))
				BoltTipFletching.boltFletch(player, BoltTips.ONYX);
			
				else if (itemUsed.getId() == 21369 && usedWith.getId() == 4151){
				player.getInventory().deleteItem(21369, 1);
				player.getInventory().deleteItem(4151, 1);
				player.getInventory().addItem(21371, 1);
				player.getPackets().sendGameMessage("Good job, you have succesfully combined a whip and vine into a vine whip.");
				}
				else if (itemUsed.getId() == 4151 && usedWith.getId() == 21369){
				player.getInventory().deleteItem(21369, 1);
				player.getInventory().deleteItem(4151, 1);
				player.getInventory().addItem(21371, 1);
				player.getPackets().sendGameMessage("Good job, you have succesfully combined a whip and vine into a vine whip.");
				}
				else if (itemUsed.getId() == 13734 && usedWith.getId() == 13754){
				player.getInventory().deleteItem(13734, 1);
				player.getInventory().deleteItem(13754, 1);
				player.getInventory().addItem(13736, 1);
				player.getPackets().sendGameMessage("You have poured the holy elixir on a spirit shield making it unleash Blessed powers.");
				}
				else if (itemUsed.getId() == 13754 && usedWith.getId() == 13734){
				player.getInventory().deleteItem(13734, 1);
				player.getInventory().deleteItem(13754, 1);
				player.getInventory().addItem(13736, 1);
				player.getPackets().sendGameMessage("You have poured the holy elixir on a spirit shield making it unleash Blessed powers.");
				}
				else if (itemUsed.getId() == 13736 && usedWith.getId() == 13748){
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13748, 1);
				player.getInventory().addItem(13740, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Divine Powers.");
				}
				else if (itemUsed.getId() == 13736 && usedWith.getId() == 13750){
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13750, 1);
				player.getInventory().addItem(13742, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Elysian Powers.");
				}
				else if (itemUsed.getId() == 13736 && usedWith.getId() == 13746){
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13746, 1);
				player.getInventory().addItem(13738, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Arcane Powers.");
				}
				else if (itemUsed.getId() == 13746 && usedWith.getId() == 13736){
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13746, 1);
				player.getInventory().addItem(13738, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Arcane Powers.");
				}
				else if (itemUsed.getId() == 13736 && usedWith.getId() == 13752){
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13752, 1);
				player.getInventory().addItem(13744, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Spectral Powers.");
				}
				else if (itemUsed.getId() == 13752 && usedWith.getId() == 13736){
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13752, 1);
				player.getInventory().addItem(13744, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Spectral Powers.");
				}
			else
				;
				//player.getPackets().sendGameMessage(
				//		"Nothing interesting happens.");
			if (Settings.DEBUG)
				Logger.log("ItemHandler", "Used:" + itemUsed.getId()
						+ ", With:" + usedWith.getId());
		}
	}

	public static void handleItemOption3(Player player, int slotId, int itemId,
			Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		if (itemId == 20767 || itemId == 20769 || itemId == 20771)
			SkillCapeCustomizer.startCustomizing(player, itemId);
		//else if(itemId >= 15084 && itemId <= 15100)
			//player.getDialogueManager().startDialogue("DiceBag", itemId);
		if (itemId == 4155) {
		    player.getSlayerManager().checkKillsLeft();
		}
		else if(itemId == 24437 || itemId == 24439 || itemId == 24440 || itemId == 24441) 
			player.getDialogueManager().startDialogue("FlamingSkull", item, slotId);
		else if (Equipment.getItemSlot(itemId) == Equipment.SLOT_AURA)
			player.getAuraManager().sendTimeRemaining(itemId);
		else if (ImplingLoot.isJar(itemId)) {
			ImplingLoot.HandleItem(player, itemId);
			player.implingCount++;
		return;
			}
		else if (itemId == 18338) {
			if (player.sapphires <= 0 && player.rubies <= 0 && player.emeralds <= 0 && player.diamonds <= 0) {
				player.sm("Your gem pouch is currently empty.");
			} else {
				player.emeralds = 0;
				player.sapphires = 0;
				player.rubies = 0;
				player.diamonds = 0;
				player.sm("You have successfully emptied your gem bag.");
			}
		}
		else if (itemId == 18339) {
			if (player.coal <= 0) {
				player.sm("Your coal pouch is currently empty.");
			} else {
				player.getInventory().addItem(454, player.coal);
				player.coal = 0;
				player.sm("You have successfully emptied your coal bag.");
			}
		}
		else if (itemId == 21576) {
			
				player.sm("The medallion currently has: " + player.drakanCharges + " charges.");
			
		}
		else if(itemId == 11014){
			//player.sendMessage("Test3");
			player.getDialogueManager().startDialogue("SummonSlayerportal");
			return;
		
		}
		else if (itemId == 15440 || itemId == 15439) {
			if (player.horn == 0)
				player.getPackets().sendGameMessage("Your penance horn is out of charges.");
			else
				player.getPackets().sendGameMessage("Your penance horn currently holds "+player.horn+" charges.");
		}
	 else if (itemId == 29981) 
		 player.setPrayerDelay(Short.MAX_VALUE);
	 else if (itemId == 4151) {
		if (player.getInventory().containsItem(29932, 1)) {
			player.getInventory().deleteItem(29932, 1);
			player.getInventory().deleteItem(4151, 1);
			player.getInventory().addItem(29974, 1);
			player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
		} else {
			player.getPackets().sendGameMessage("You must have one upgrade token to upgrade this weapon.");
		}
		} else if (itemId == 1275) {
			if (player.getInventory().containsItem(29932, 1)) {
				player.getInventory().deleteItem(29932, 1);
				player.getInventory().deleteItem(1275, 1);
				player.getInventory().addItem(29925, 1);
				player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
			} else {
				player.getPackets().sendGameMessage("You must have one upgrade token to upgrade this weapon.");
			}
		}
	 else if (itemId == 14484) {
		if (player.getInventory().containsItem(29932, 2)) {
			player.getInventory().deleteItem(29932, 2);
			player.getInventory().deleteItem(14484, 1);
			player.getInventory().addItem(29969, 1);
			player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
		} else {
			player.getPackets().sendGameMessage("You must have two upgrade token to upgrade this weapon.");
		}
	}
	}

	public static void handleItemOption4(Player player, int slotId, int itemId,
			Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		if (itemId == 21371) {
				if (player.getInventory().containsItem(29932, 3)) {
					player.getInventory().deleteItem(29932, 3);
					player.getInventory().deleteItem(21371, 1);
					player.getInventory().addItem(29962, 1);
					player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
				} else {
					player.getPackets().sendGameMessage("You must have three upgrade token to upgrade this weapon.");
				}
				} else if (itemId == 11694) {
					if (player.getInventory().containsItem(29932, 2)) {
						player.getInventory().deleteItem(29932, 2);
						player.getInventory().deleteItem(11694, 1);
						player.getInventory().addItem(29973, 1);
						player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
					} else {
						player.getPackets().sendGameMessage("You must have two upgrade token to upgrade this weapon.");
					}
				} else if (itemId == 11696) {
					if (player.getInventory().containsItem(29932, 2)) {
						player.getInventory().deleteItem(29932, 2);
						player.getInventory().deleteItem(11696, 1);
						player.getInventory().addItem(29972, 1);
						player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
					} else {
						player.getPackets().sendGameMessage("You must have two upgrade token to upgrade this weapon.");
					}
				} else if (itemId == 11698) {
					if (player.getInventory().containsItem(29932, 2)) {
						player.getInventory().deleteItem(29932, 2);
						player.getInventory().deleteItem(11698, 1);
						player.getInventory().addItem(29971, 1);
						player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
					} else {
						player.getPackets().sendGameMessage("You must have two upgrade token to upgrade this weapon.");
					}
				} else if (itemId == 11700) {
					if (player.getInventory().containsItem(29932, 2)) {
						player.getInventory().deleteItem(29932, 2);
						player.getInventory().deleteItem(11700, 1);
						player.getInventory().addItem(29970, 1);
						player.getPackets().sendGameMessage("Your weapon seems to be a lot more powerful.");
					} else {
						player.getPackets().sendGameMessage("You must have two upgrade token to upgrade this weapon.");
					}
			 }
	}

	public static void handleItemOption5(Player player, int slotId, int itemId,
			Item item) {
		System.out.println("Item: "+item.getId()+" Option 5.");
		Logger.logMessage("Item: "+item.getId()+" Option 5.");
	}
	
	private static String getFormattedNumber(int amount) {
		return new DecimalFormat("#,###,##0").format(amount).toString();
	}

	public static void handleItemOption6(Player player, int slotId, int itemId,
			Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		if (player.getToolbelt().addItem(slotId, item))
		    return;
		if (itemId == 14057)
		    SorceressGarden.teleportToSocreressGarden(player, true);
		Pouches pouches = Pouches.forId(itemId);
		if (pouches != null) {
			if (player.getUsername().equalsIgnoreCase("richie")) {
			player.sm("You have lost the rights to summon familiars...");
			} else {
			Summoning.spawnFamiliar(player, pouches);
			}
		}
		
		else if (itemId == 1438)
			Runecrafting.locate(player, 3127, 3405);
		else if (itemId == 1440)
			Runecrafting.locate(player, 3306, 3474);
		else if (itemId == 1442)
			Runecrafting.locate(player, 3313, 3255);
		else if (itemId == 1444)
			Runecrafting.locate(player, 3185, 3165);
		else if (itemId == 1446)
			Runecrafting.locate(player, 3053, 3445);
		else if (itemId == 1448)
			Runecrafting.locate(player, 2982, 3514);
		else if (itemId <= 1712 && itemId >= 1706 || itemId >= 10354
				&& itemId <= 10362)
			player.getDialogueManager().startDialogue("Transportation",
					"Edgeville", new WorldTile(3087, 3496, 0), "Karamja",
					new WorldTile(2918, 3176, 0), "Draynor Village",
					new WorldTile(3105, 3251, 0), "Al Kharid",
					new WorldTile(3293, 3163, 0), itemId);
		else if (itemId == 995) {
			player.getMoneyPouch().sendDynamicInteraction(item.getAmount(), false, MoneyPouch.TYPE_POUCH_INVENTORY);
		}
		else if (itemId == 1704 || itemId == 10352)
			player.getPackets()
					.sendGameMessage(
							"The amulet has ran out of charges. You need to recharge it if you wish it use it once more.");
		else if (itemId >= 3853 && itemId <= 3867)
			player.getDialogueManager().startDialogue("Transportation",
					"Burthrope Games Room", new WorldTile(2880, 3559, 0),
					"Barbarian Outpost", new WorldTile(2519, 3571, 0),
					"Gamers' Grotto", new WorldTile(2970, 9679, 0),
					"Corporeal Beast", new WorldTile(2886, 4377, 0), itemId);
		else if (itemId >= 2552 && itemId <= 2566)
			player.getDialogueManager().startDialogue("Transportation",
					"Duel Arena", new WorldTile(3366, 3267, 0),
					"Castle Wars", new WorldTile(2443, 3088, 0),
					"Fist Of Guthix", new WorldTile(1701, 5600, 0),
					"Mobilising Armies", new WorldTile(2414, 2842, 0), itemId);
		else if (itemId <= 1712 && itemId >= 1706 || itemId >= 10354
				&& itemId <= 10362)
			player.getDialogueManager().startDialogue("Transportation",
					"Sumona", new WorldTile(3361, 2998, 0), "Slayer Tower",
					new WorldTile(3429, 3534, 0), "Fremennik Slayer Dungeon",
					new WorldTile(2793, 3616, 0), "Tarn's Lair",
					new WorldTile(2773, 4540, 0), itemId);
		else if (itemId >= 11105 && itemId <= 11111) {
			player.getDialogueManager().startDialogue("SkillsNeck");
			if (item.getId() >= 3853 && item.getId() <= 3865
					|| item.getId() >= 10354 && item.getId() <= 10361
					|| item.getId() >= 11105 && item.getId() <= 11111) {
				item.setId(item.getId() + 2);
			} 
			player.getInventory().refresh(
					player.getInventory().getItems().getThisItemSlot(item));
		} else if (itemId >= 11118 && itemId <= 11127)
			player.getDialogueManager().startDialogue("Transportation",
					"Warrior's Guild", new WorldTile(2867, 3543, 0),
					"Champion's Guild", new WorldTile(3190, 3361, 0),
					"Monastery", new WorldTile(3052, 3490, 0),
					"Ranging Guild", new WorldTile(2664, 3433, 0), itemId);
	}




	public static void handleItemOption7(Player player, int slotId, int itemId,
			Item item) {
		long time = Utils.currentTimeMillis();
		Player.dropLog(player, item.getId());
		if (player.getLockDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		if (!player.getControlerManager().canDropItem(item))
			return;
		player.stopAll(false);
		if (item.getDefinitions().isOverSized()) {
			player.getPackets().sendGameMessage("The item appears to be oversized.");
			player.getInventory().deleteItem(item);
			return;
		}
		if (LendingManager.isLendedItem(player, item)) {
			Lend lend;
			if ((lend = LendingManager.getLend(player)) != null) {
				if (lend.getItem().getDefinitions().getLendId() == item.getId()) {
					player.getDialogueManager().startDialogue("DiscardLend",
							lend);
					return;
				}
			}
			return;
		}
		if (player.isBurying == true) {
			player.getPackets().sendGameMessage("You can't drop items while your burying.");
			return;
		}
		if(player.getRights() == 2 && !player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("reddragon") && !player.getUsername().equalsIgnoreCase("")) {
			player.getPackets().sendGameMessage("You can't drop items.");
			return;
		}
		if (player.getUsername().equalsIgnoreCase("richie")) {
			player.getPackets().sendGameMessage("You can't drop items.");
			return;
		}
		if (player.getPetManager().spawnPet(itemId, true)) {
			return;
		}
		player.getInventory().deleteItem(slotId, item);
		if (player.getCharges().degradeCompletly(item))
			return;
		World.addGroundItem(item, new WorldTile(player), player, true, 60);
		//player.getDialogueManager().startDialogue("DestroyItemOption", slotId, item);
		player.getPackets().sendSound(2739, 0, 1);
	
	}
	
	public static void handleItemOption8(Player player, int slotId, int itemId,
			Item item) {
		player.getInventory().sendExamine(slotId);
		player.sm("STAGED ON DEATH: "+item.getDefinitions().getStageOnDeath()+"");
	}
	
	public static void handleItemOnPlayer(final Player player,
			final Player usedOn, final int itemId) {
		player.setCoordsEvent(new CoordsEvent(usedOn, new Runnable() {
			public void run() {
				player.faceEntity(usedOn);
				if (usedOn.getInterfaceManager().containsScreenInter()) {
					player.sendMessage(usedOn.getDisplayName() + " is busy.");
					return;
				}
				if (player.getAttackedByDelay() + 10000 > Utils.currentTimeMillis()) {
					player.sm("You can't do this during combat.");
					return;
				}
				if (usedOn.getAttackedByDelay() + 10000 > Utils.currentTimeMillis()) {
					player.sm("You cannot send a request to a player in combat.");
					return;
				}
				switch (itemId) {
				case 4155:
					player.getSlayerManager().invitePlayer(usedOn);
					break;
				case 11951:
					player.getInventory().deleteItem(11951, 1);
					player.faceEntity(usedOn);
					player.setNextAnimation(new Animation(7530));
					World.sendProjectile(player, player, usedOn, 1281, 21, 21, 90, 65, 50, 0);
					CoresManager.fastExecutor.schedule(new TimerTask() {
						int snowballtime = 3;
						public void run() {
							try {
						if(snowballtime==1){
							usedOn.setNextGraphics(new Graphics(1282));
						}
						if (this == null || snowballtime <= 0) {
							cancel();
					    return; 
						}
						if (snowballtime >= 0) {
							snowballtime--;
						}
							} catch (Throwable e) {
								Logger.handle(e);
							}
						}
					}, 0, 600);
					break;
				default:
					//player.sendMessage("Nothing interesting happens.");
					break;
				}
			}
		}, usedOn.getSize()));
	}

	public static void handleItemOnNPC(final Player player, final NPC npc, final Item item) {
		if (item == null) {
			return;
		}
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				if (!player.getInventory().containsItem(item.getId(), item.getAmount())) {
					return;
				}
				if (npc instanceof Pet) {
					player.faceEntity(npc);
					player.getPetManager().eat(item.getId(), (Pet) npc);
					return;
				}
				if (npc.getDefinitions().name.contains("ool leprech")) {
					for (int produceId : PatchConstants.produces) {
						if (produceId == item.getId()) {
							int num = player.getInventory().getNumerOf(produceId);
							player.getInventory().deleteItem(produceId, num);
							player.getInventory().addItem(new Item(ItemDefinitions.getItemDefinitions(produceId).getCertId(), num));
							return;
						}
					}
					player.getPackets().sendGameMessage("The leprechaun cannot note that item for you.");
				}
			if (npc instanceof ConditionalDeath) {
			    ((ConditionalDeath) npc).useHammer(player);
			
			}
				if (npc.getId() == 519) {
					if (BrokenItems.forId(item.getId()) == null) {
					player.getDialogueManager().startDialogue("SimpleMessage","You cant repair this item.");
					return;
					}
					player.getDialogueManager().startDialogue("Repair", 945, item.getId());
					
					return;
			}
			}
		}, npc.getSize()));
	}
}
package com.rs.net.decoders.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import com.rs.Settings;
import com.rs.cache.loaders.ClientScriptMap;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.CustomisedShop.MyShopItem;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.minigames.CastleWars;
import com.rs.game.minigames.Crucible;
import com.rs.game.minigames.pest.CommendationExchange;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.npc.others.GraveStone;
import com.rs.game.player.CombatDefinitions;
import com.rs.game.player.EmotesManager;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.FightPitsViewingOrb;
import com.rs.game.player.actions.Rest;
import com.rs.game.player.actions.Smithing.ForgingInterface;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.content.AdventurersLog;
import com.rs.game.player.content.ArtisanWorkshop;
import com.rs.game.player.content.GnomeGlider;
import com.rs.game.player.content.GraveStoneSelection;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.ItemSets;
import com.rs.game.player.content.JewllerySmithing;
import com.rs.game.player.content.News;
import com.rs.game.player.content.Notes.Note;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.SandwichLady;
import com.rs.game.player.content.Shop;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.SkillsDialogue;
import com.rs.game.player.content.SpiritTree;
import com.rs.game.player.content.TeleTabs;
import com.rs.game.player.content.achievements.AchievementSystem;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.player.content.construction.House;
import com.rs.game.player.content.dungeoneering.DungeonRewards;
import com.rs.game.player.content.magic.Enchanting;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.PestInvasion;
import com.rs.game.player.controlers.SorceressGarden;
import com.rs.game.player.controlers.dung.RuneDungGame;
import com.rs.game.player.controlers.events.DeathEvent;
import com.rs.game.player.dialogues.LevelUp;
import com.rs.game.player.dialogues.Transportation;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.io.InputStream;
import com.rs.net.decoders.WorldPacketsDecoder;
import com.rs.player.actions.summoning.SummoningScroll;
import com.rs.utils.ItemExamines;
import com.rs.utils.Logger;
import com.rs.utils.Misc;
import com.rs.utils.Utils;


public class ButtonHandler {




	public static void handleButtons(final Player player, InputStream stream,
			int packetId) {
		int interfaceHash = stream.readIntV2();
		int interfaceId = interfaceHash >> 16;
		if (Utils.getInterfaceDefinitionsSize() <= interfaceId) {
			// hack, or server error or client error
			// player.getSession().getChannel().close();
			return;
		}
		if (!World.containsLobbyPlayer(player.getUsername())) {
			if (player.isDead() || !player.getInterfaceManager().containsInterface(interfaceId)) {
				return;
			}
		}
		final int componentId = interfaceHash - (interfaceId << 16);
		if (componentId != 65535
				&& Utils.getInterfaceDefinitionsComponentsSize(interfaceId) <= componentId) {
			// hack, or server error or client error
			// player.getSession().getChannel().close();
			return;
		}
		final int itemId = stream.readInt();
		final int slotId = stream.readUnsignedShortLE128();
		if (!player.getControlerManager().processButtonClick(interfaceId,
				componentId, slotId, packetId))
			return;
		if (!player.getControlerManager().processButtonClick(interfaceId, componentId, slotId, itemId, packetId))
		    return;
		switch(interfaceId) {
		case 1266:
			if (player.getAttributes().get("editting_own_store") != null) {
				if (componentId == 0) {
					switch(packetId) {
					case 14: //sell
						player.getAttributes().put("sending_mystore_item", slotId);
						player.getPackets().sendRunScript(108,
								new Object[] { "How much do you want to sell this for?" });
						break;
					}
				}
				return;
			}
			break;
		case 1171:
			if (player.getAttributes().get("editting_own_store") != null) {
				List<MyShopItem> items = player.getCustomisedShop().getShopItems();
				if (slotId >= items.size()) {
					player.sendMessage("That item no longer exists in the shop!");
					return;
				}
				MyShopItem item = items.get(slotId);
				if (componentId == 7) {
					if (packetId == 14) {
						player.getCustomisedShop().removeItem(item, true);
						player.getInventory().addItem(item.getItem());
						player.getCustomisedShop().sendOwnShop();
					}
				}
				return;
			}
			Player target = (Player) player.getAttributes().get("viewing_player_shop");
			if (target != null) {
				List<MyShopItem> items = target.getCustomisedShop().getShopItems();
				if (slotId >= items.size()) {
					player.sendMessage("That item no longer exists in the shop!");
					return;
				}
				MyShopItem item = items.get(slotId);
				if (componentId == 7) {
					if (packetId == 14)
						player.sendMessage(item.getItem().getName() + " costs " + Misc.format(item.getPrice()) + " coins in " + target.getDisplayName() + "'s shop.");
					else {
						if (!World.containsPlayer(target.getUsername())) {
							player.sm("That player is not online to receive the money.");
							return;
						} 
						if (player.takeMoney(item.getPrice())) {
							player.getInventory().addItem(item.getItem());
							target.getCustomisedShop().removeItem(item, false);
							target.getCustomisedShop().open(player);
						}else {
							player.sendMessage("You do not have enough coins to buy " + item.getItem().getName() + " from " + target.getDisplayName() + "'s store!");
						}
					}
				}
			}
			break;
		}
		if (interfaceId == 297) {
		SandwichLady.getInstance().handleButtons(player, interfaceId, componentId);
		}
		if (interfaceId == 1011) {
	    CommendationExchange.handleButtonOptions(player, componentId);
		}
		if (interfaceId == 17) {
	    if (componentId == 28)
		sendItemsKeptOnDeath(player, player.getVarsManager().getBitValue(9226) == 0);
		}
		if (interfaceId == 940) {
		DungeonRewards.handleButtons(player, componentId, slotId, packetId);
		}
	    if (interfaceId == 1072) {
		   ArtisanWorkshop.handleButtons(player, componentId);
		}
	    if (interfaceId == 72) {
		   if (componentId == 68)
			   player.getPackets().sendOpenURL("https://login.skype.com/account/signup-form");
		   else if (componentId == 67)
			   player.getPackets().sendOpenURL(Settings.RAGE_SKYPE);
		   else if (componentId == 66)
			   player.getPackets().sendOpenURL("skype:kjell.serranne");
		   else if (componentId == 65)
			   player.getPackets().sendOpenURL("skype:ben.edwards951");
		   else if (componentId == 64)
			   player.getPackets().sendOpenURL("skype:mabrox97");
		   else if (componentId == 73)
			   player.getPackets().sendOpenURL("http://www.skype.com/en/download-skype/skype-for-computer/");
		   else if (componentId == 72)
			   player.getPackets().sendOpenURL("skype:rodiksowns1");
		   else if (componentId == 71)
			   player.getPackets().sendOpenURL("skype:mtm11496");
		   else if (componentId == 70)
			   player.getPackets().sendOpenURL("skype:mt8097");
		   else if (componentId == 69)
			   player.getPackets().sendOpenURL("skype:colesp3");
		}
   if ((interfaceId == 548 && componentId == 194) || (interfaceId == 746 && componentId == 204)) {
	   player.getMoneyPouch().switchPouch();
   }
   if ((interfaceId == 746 && componentId == 207) || (interfaceId == 548 && componentId == 159)) {
	   
	if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
	    player.getMoneyPouch().switchPouch();
	else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
	    player.getMoneyPouch().withdrawPouch();
	else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
	    player.getMoneyPouch().examinePouch();
	else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
	    if (player.getInterfaceManager().containsScreenInter() || player.isLocked()) {
		player.getPackets().sendGameMessage("Please finish what you're doing before opening the price checker.");
		return;
	    }
	    player.stopAll();
	    player.getPriceCheckManager().openPriceCheck();
	}
    }
		if (interfaceId == 548 || interfaceId == 746) {
			if ((interfaceId == 548 && componentId == 148)
					|| (interfaceId == 746 && componentId == 199)) {
				if (player.getInterfaceManager().containsScreenInter()
						|| player.getInterfaceManager()
						.containsInventoryInter()) {
					// TODO cant open sound
					player.getPackets()
					.sendGameMessage(
							"Please finish what you're doing before opening the world map.");
					return;
				}
				// world map open
				player.getPackets().sendWindowsPane(755, 0);
				int posHash = player.getX() << 14 | player.getY();
				player.getPackets().sendGlobalConfig(622, posHash); // map open
				// center
				// pos
				player.getPackets().sendGlobalConfig(674, posHash); // player
				// position
				//Quests
			} else if ((interfaceId == 548 && componentId == 17)
					|| (interfaceId == 746 && componentId == 54)) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getSkills().switchXPDisplay();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getSkills().switchXPPopup();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getSkills().setupXPCounter();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					if(player.isInDung()) {
						player.getPackets().sendGameMessage("No.");
						return;
					}
					if (player.getInterfaceManager().containsScreenInter()) {
						player.getPackets()
						.sendGameMessage(
								"Please finish what you're doing before opening the price checker.");
						return;
					}
					player.stopAll();
					player.getPriceCheckManager().openPriceCheck();
				}
			}
		
		
		
		//Dark Invasion
		else if (interfaceId == 267) {
			switch (componentId) {

			case 24:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.ATTACK, 5);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased attack xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 49:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.ATTACK, 25);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased attack xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 56:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.ATTACK, 50);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased attack xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 35:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.STRENGTH, 5);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased strength xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 50:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.STRENGTH, 25);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased strength xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 57:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.STRENGTH, 50);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased strength xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * DEFENCE XP
			 */
			case 36:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.DEFENCE, 5);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased defence xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 51:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.DEFENCE, 25);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased defence xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 58:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.DEFENCE, 50);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased defence xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * RANGE XP
			 */
			case 37:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.RANGE, 5);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased range xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 52:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.RANGE, 25);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased range xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			case 59:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.RANGE, 50);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased range xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * MAGIC XP
			 */
			case 38:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.MAGIC, 5);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased magic xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 53:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.MAGIC, 25);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased magic xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 60:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.MAGIC, 50);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased magic xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * HITPOINTS XP
			 */
			case 39:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.HITPOINTS, 5);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased hitpoints(hp) xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 54:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.HITPOINTS, 25);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased hitpoints(hp) xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 61:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.HITPOINTS, 50);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased hitpoints(hp) xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * PRAYER XP
			 */
			case 40:
				if (player.getPlayerData().getInvasionPoints() >= 1) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 1);
					player.getSkills().addXp(Skills.PRAYER, 5);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased prayer xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 55:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 5);
					player.getSkills().addXp(Skills.PRAYER, 25);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased prayer xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;

			case 62:
				if (player.getPlayerData().getInvasionPoints() >= 10) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 10);
					player.getSkills().addXp(Skills.PRAYER, 50);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased prayer xp from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * HERBS
			 */
			case 45:
				if (player.getPlayerData().getInvasionPoints() >= 15) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 15);
					int[] herbNoted = { 995, 960 }; // CHANGE THIS LATER ON TODO
					int[] amount = { 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
							17, 18, 19, 20 };
					int herbReward = herbNoted[Utils
							.random(herbNoted.length - 1)];
					int herbAmount = amount[Utils.random(amount.length - 1)];
					player.getBank().addItem(herbReward, herbAmount, true);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased some herbs from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * ORE
			 */
			case 46:
				if (player.getPlayerData().getInvasionPoints() >= 15) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 15);
					int[] oreNoted = { 995, 960 }; // CHANGE THIS LATER ON TODO
					int[] amounts = { 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
							16, 17, 18, 19, 20 };
					int oreReward = oreNoted[Utils.random(oreNoted.length - 1)];
					int oreAmount = amounts[Utils.random(amounts.length - 1)];
					player.getBank().addItem(oreReward, oreAmount, true);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased some ores from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * SEEDS
			 */
			case 48:
				if (player.getPlayerData().getInvasionPoints() >= 15) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 15);
					int[] seeds = { 995, 960 }; // CHANGE THIS LATER ON TODO
					int[] amountOfSeed = { 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
							15, 16, 17, 18, 19, 20 };
					int seedsReward = seeds[Utils.random(seeds.length - 1)];
					int seedAmount = amountOfSeed[Utils
							.random(amountOfSeed.length - 1)];
					player.getBank().addItem(seedsReward, seedAmount, true);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased some seeds from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID KNIGHT MACE
			 */
			case 41:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(8841, 1, true);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased a Void Knight Mace from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID KNIGHT TOP
			 */
			case 42:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(8839, 1, true);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased a Void Knight Top from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID KNIGHT ROBE
			 */
			case 43:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(8840, 1, true);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased a Void Knight Robe from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID KNIGHT GLOVES
			 */
			case 44:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(8842, 1, true);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased a Void Knight Gloves from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID MAGE HELM
			 */
			case 67:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(11663, 1, true);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased a Void Mage Helm from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID RANGE HELM
			 */
			case 68:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(11664, 1, true);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased a Void Range Helm from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID MELEE HELM
			 */
			case 69:
				if (player.getPlayerData().getInvasionPoints() >= 500) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 500);
					player.getBank().addItem(11665, 1, true);
					player.getDialogueManager().startDialogue(
							"SimpleMessage",
							"You have purchased a Void Melee Helm from the Invasion shop and now have "
									+ player.getPlayerData()
											.getInvasionPoints() + " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/*
			 * VOID KNIGHT COMMENDATION
			 */
			case 70:
				if (player.getPlayerData().getInvasionPoints() >= 5) {
					player.getPlayerData().setInvasionPoints(
							player.getPlayerData().getInvasionPoints() - 5);
					player.getBank().addItem(19642, 1, true);
					player.getDialogueManager()
							.startDialogue(
									"SimpleMessage",
									"You have purchased a Void Knight Commendation from the Invasion shop and now have "
											+ player.getPlayerData()
													.getInvasionPoints()
											+ " points.");
				} else {
					player.getPackets()
							.sendGameMessage(
									"Sorry, you don't have enough points to purchase this.");
				}
				break;
			/* END OF SHOP LIST */
			}
		}
		
		/*else if (interfaceId == 1019) {
            if(componentId == 16)
            	//player.getPackets().sendOpenURL("http://noszscape.org/");
            if (componentId == 18) {
				//if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame){
				//player.getPackets().sendGameMessage("Um, no your in Dungeoneering. GEE EFF SMUGGLER!");
				//} else {
            	//TicketSystem.requestTicket(player);
		}
		}*/
	//}
		
		if (interfaceId == 205) {
			   AchievementSystem.handleButtons(player, componentId);
			}
		
		if (interfaceId == 497) {
			if (componentId == 4)
				player.closeInterfaces();
		}
		
		if (interfaceId == 95) { //Sailing
			if (componentId == 23) {
				player.getPackets().sendGameMessage("You sail to the port of Tyras.");
				player.setNextWorldTile(new WorldTile(2268, 3244, 0));
			} else if (componentId == 33) {
				player.getPackets().sendGameMessage("You sail to the port of Ooglog.");
				player.setNextWorldTile(new WorldTile(2623, 2857, 0));
			} else if (componentId == 29) {
				player.getPackets().sendGameMessage("You sail to the port of Khazard.");
				player.setNextWorldTile(new WorldTile(2623, 2857, 0));
			} else if (componentId == 28) {
				player.getPackets().sendGameMessage("You sail to the port of Brimhaven.");
				player.setNextWorldTile(new WorldTile(2760, 3238, 0));
			} else if (componentId == 30) {
				player.getPackets().sendGameMessage("You sail to the port of Sarim.");
				player.setNextWorldTile(new WorldTile(3038, 3192, 0));
			} else if (componentId == 27) {
				player.getPackets().sendGameMessage("You sail to the port of Karamja.");
				player.setNextWorldTile(new WorldTile(2954, 3158, 0));
			} else if (componentId == 26) {
				player.getPackets().sendGameMessage("You sail to the port of the Shipyard.");
				player.setNextWorldTile(new WorldTile(3001, 3032, 0));
			} else if (componentId == 25) {
				player.getPackets().sendGameMessage("You sail to the port of Catherby.");
				player.setNextWorldTile(new WorldTile(2792, 3414, 0));
			} else if (componentId == 32) {
				if (player.DS < 7) {
					player.getPackets().sendGameMessage("You must have completed Dragon Slayer to sail to Crandor.");
				} else {
				player.getPackets().sendGameMessage("You sail to the port of Ooglog.");
				player.setNextWorldTile(new WorldTile(2623, 2857, 0));
				}
			} else if (componentId == 24) {
				player.getPackets().sendGameMessage("You sail to the port of Phasmatys.");
				player.setNextWorldTile(new WorldTile(3702, 3503, 0));
			} else if (componentId == 31) {
				player.getPackets().sendGameMessage("You sail to the port of Mos Le'Harmless.");
				player.setNextWorldTile(new WorldTile(3671, 2931, 0));
			}
				
		}
		
		if (interfaceId == 506) {
            if (componentId == 2) { //Player Rankings
                player.getInterfaceManager().sendInterface(275);
                player.getPackets().sendIComponentText(275, 1, "Player Rankings");
                player.getPackets().sendIComponentText(275, 10, "<img=1><col=FF0000><shad=000000>Main Developer: Astro");
                player.getPackets().sendIComponentText(275, 11, "<img=1><col=FF0000><shad=000000>Co Owners: Time");
               // player.getPackets().sendIComponentText(275, 12, "<img=1><col=0000ff><shad=00ffff>Co Developers: Nick");
                player.getPackets().sendIComponentText(275, 13, "");
                player.getPackets().sendIComponentText(275, 14, "<img=1><col=FFFF00><shad=000000>Administrators: ");
                player.getPackets().sendIComponentText(275, 15, "<img=0><col=FFFFFF><shad=000000>Moderators: , ");
                player.getPackets().sendIComponentText(275, 16, "<img=14><col=3299CC>Player Support: ");
                player.getPackets().sendIComponentText(275, 17, "");
                player.getPackets().sendIComponentText(275, 18, "");
                player.getPackets().sendIComponentText(275, 19, "<img=5><col=FF0000><shad=000000>Staff Applications Are Current Open On Forums!<img=5>");
                player.getPackets().sendIComponentText(275, 20, "");
                player.getPackets().sendIComponentText(275, 21, "");
        }
			if (componentId == 4) { //Appearance/Settings
				player.getDialogueManager().startDialogue("PickOne");
			}
			if (componentId == 6) { //Teleports
				player.getDialogueManager().startDialogue("TeleportCrystal");
			}
			if (componentId == 8) { //Donator Information
				player.getPackets().sendOpenURL("http://www.exylum.org/donate");
                player.getInterfaceManager().sendInterface(275);
                player.getPackets().sendIComponentText(275, 1, "<img=8><col=00FF00><shad=000000>Donator Information<img=8>");
                player.getPackets().sendIComponentText(275, 2, "");
                player.getPackets().sendIComponentText(275, 10, "");
                player.getPackets().sendIComponentText(275, 11, "<img=13><col=0000FF>Prices for ranks/spins/points below!<img=13>");
                player.getPackets().sendIComponentText(275, 12, "<img=11><col=FF00>Regular Donator Features<img=11>");
                player.getPackets().sendIComponentText(275, 13, "Access to the extremely awesome Donator Zone.");
                player.getPackets().sendIComponentText(275, 14, "Ability to set a custom title. ::title 1-60");
                player.getPackets().sendIComponentText(275, 15, "Ability to change the color of your skin. ::blueskin ::greenskin");
                player.getPackets().sendIComponentText(275, 16, "Ability to bank anywhere EXCEPT in minigames. ::bank");
                player.getPackets().sendIComponentText(275, 17, "Increased EXP Rates from 1 to 1.2.");
                player.getPackets().sendIComponentText(275, 18, "An epic rank and yell title, and a red donator icon.");
                player.getPackets().sendIComponentText(275, 19, "Access to beta features before they are released!");
                player.getPackets().sendIComponentText(275, 20, "Be able to Summon Pak-Yak and Bunyip.");
                player.getPackets().sendIComponentText(275, 21, "Access to a custom minigame Refuge of Fear.");
                player.getPackets().sendIComponentText(275, 22, "Increased Loyalty Points per 30 minutes of play.");
                player.getPackets().sendIComponentText(275, 23, "Access to about 8+ extra shops.");
                player.getPackets().sendIComponentText(275, 24, "Ability to yell. ::yell");

                player.getPackets().sendIComponentText(275, 25, "<img=8><col=006600><shad=000000>Extreme Donator Features<img=8>");
                player.getPackets().sendIComponentText(275, 26, "Get all the features of a Regular Donator.");
                player.getPackets().sendIComponentText(275, 27, "Increased EXP Rates from 1 to 1.4");
                player.getPackets().sendIComponentText(275, 28, "Ability to use 5 new pets.");
                player.getPackets().sendIComponentText(275, 29, "Ability to use wing auras and special tier auras.");
                player.getPackets().sendIComponentText(275, 30, "Slightly increased drop rates.");
                player.getPackets().sendIComponentText(275, 31, "A Green donator symbol.");

                player.getPackets().sendIComponentText(275, 32, "<img=12><col=0000FF><shad=000000>Legendary Donator Features<img=12>");
                player.getPackets().sendIComponentText(275, 33, "Get all the features of a Regular Donator and Extreme.");
                player.getPackets().sendIComponentText(275, 34, "Ultimate priority over other players.");
                player.getPackets().sendIComponentText(275, 35, "Better chances at the Squeal of Fortune");
                player.getPackets().sendIComponentText(275, 36, "To be able to have the ultimate pet, TzRek Jad");
                player.getPackets().sendIComponentText(275, 37, "Increased EXP Rates from 1 to 1.75");
                player.getPackets().sendIComponentText(275, 38, "Mithril Seeds, to play Hot/Cold");
                player.getPackets().sendIComponentText(275, 39, "Drop rates increase giving you a better chance for rare items!");
                player.getPackets().sendIComponentText(275, 40, "::Setdisplay/::RemoveDisplay - Changes your in-game name");
                player.getPackets().sendIComponentText(275, 41, "A Blue donator symbol.");
                player.getPackets().sendIComponentText(275, 42, "Ability to request custom titles.");
                
                player.getPackets().sendIComponentText(275, 43, "<img=13><col=ffa34c><shad=000000>Supreme Donator Features<img=13>");
                player.getPackets().sendIComponentText(275, 44, "Get all the features of a Regular, Extreme, and Legendary Donator.");
                player.getPackets().sendIComponentText(275, 45, "Increased EXP Rates from 1 to 2.");
                player.getPackets().sendIComponentText(275, 46, "Access to the Supreme Donator Shop");
                player.getPackets().sendIComponentText(275, 47, "Ability to purchase and use the Golden and Royal Cannons");
                player.getPackets().sendIComponentText(275, 48, "The good feeling of helping us pay for our server costs ($50+ per month)");
                player.getPackets().sendIComponentText(275, 49, "A bonus of 500 Donator Tokens");
                player.getPackets().sendIComponentText(275, 50, "Access to an exclusive training area");
                player.getPackets().sendIComponentText(275, 51, "Ability to have a custom yell tag coded for you");
                player.getPackets().sendIComponentText(275, 52, "A Gold donator symbol.");
                player.getPackets().sendIComponentText(275, 53, "Suggest more benefits for this rank to Nosz!");
                
                player.getPackets().sendIComponentText(275, 54, "<img=16><col=6C21ED><shad=000000>Divine Donator Features<img=14>");
                player.getPackets().sendIComponentText(275, 55, "Get all the features of a Regular, Extreme, Legendary, and Supreme Donator.");
                player.getPackets().sendIComponentText(275, 56, "Increased EXP Rates from 2 to 2.5.");
                player.getPackets().sendIComponentText(275, 57, "Access to the Divine Donator Shop");
                player.getPackets().sendIComponentText(275, 58, "Unlimited supply of Supreme Corruption for wings");
                player.getPackets().sendIComponentText(275, 59, "Praise from all the players, you will stand out");
                player.getPackets().sendIComponentText(275, 60, "A bonus of 750 Donator Tokens");
                player.getPackets().sendIComponentText(275, 61, "A custom pet, coded just for you (or for others if you like)");
                player.getPackets().sendIComponentText(275, 62, "Ability to have a custom yell tag/title coded for you");
                player.getPackets().sendIComponentText(275, 63, "A Purple donator symbol.");
                player.getPackets().sendIComponentText(275, 64, "Suggest more benefits for this rank to Nosz!");
                
                player.getPackets().sendIComponentText(275, 65, "<img=15><col=ffffff><shad=000000>Angelic Donator Features<img=14>");
                player.getPackets().sendIComponentText(275, 66, "Get all the features of a Regular, Extreme, Legendary, Divine, and Supreme Donator.");
                player.getPackets().sendIComponentText(275, 67, "Increased EXP Rates from 2.5 to 3.");
                player.getPackets().sendIComponentText(275, 68, "Access to the Angelic Donator Shop");
                player.getPackets().sendIComponentText(275, 69, "Unlimited supply of Supreme Salvation for wings");
                player.getPackets().sendIComponentText(275, 70, "Praise from all the players, you will stand out");
                player.getPackets().sendIComponentText(275, 71, "A bonus of 1000 Donator Tokens");
                player.getPackets().sendIComponentText(275, 72, "");
                player.getPackets().sendIComponentText(275, 73, "Access to new items such as Godcrusher and Tokhaar Warlord");
                player.getPackets().sendIComponentText(275, 74, "A White donator symbol.");
                player.getPackets().sendIComponentText(275, 75, "Suggest more benefits for this rank to Nosz!");
                
                

                player.getPackets().sendIComponentText(275, 76, "<img=11><col=0000FF><shad=000000>Donation Options<img=11>");
                player.getPackets().sendIComponentText(275, 77, "<col=FF0000>Regular Donator<col=000000>-<col=FFA500>10$/20m EoC/5m 07");
                player.getPackets().sendIComponentText(275, 78, "<col=006600>Extreme Donator<col=000000>-<col=FFA500>15$/30m EoC/7m 07");
                player.getPackets().sendIComponentText(275, 79, "<col=0000FF>Legendary Donator<col=000000>-<col=FFA500>25$/50m EoC/10m 07");
                player.getPackets().sendIComponentText(275, 80, "<col=ffa34c>Supreme Donator<col=000000>-<col=FFA500>50$/100m EoC/20m 07");
                player.getPackets().sendIComponentText(275, 81, "<col=6C21ED>Divine Donator<col=000000>-<col=FFA500>100$/200m EoC/40m 07");
                player.getPackets().sendIComponentText(275, 82, "<col=ffffff>Angelic Donator<col=000000>-<col=FFA500>200$/400m EoC/80m 07");
                
                player.getPackets().sendIComponentText(275, 83, "<img=8><col=0000FF><shad=000000>Spin Purchases<img=8>");
                player.getPackets().sendIComponentText(275, 84, "Spins are priced at 0.25$/1m EoC/125k 07");
                player.getPackets().sendIComponentText(275, 85, "For every 10 spins you buy, you get 5 free!");
                player.getPackets().sendIComponentText(275, 86, "<img=12><col=0000FF><shad=000000>Donator Tokens<img=12>");
                player.getPackets().sendIComponentText(275, 87, "You recieve 100 donator tokens per 1$/4m EoC/500k 07 you donate");
        }
            if (componentId == 10) { //Vote
                player.getPackets().sendGameMessage("Once your votes go through you will recieve your reward!");
                player.getPackets().sendOpenURL("http://www.exylum.org/vote/");
    }
			if (componentId == 14) { //Highscores
				player.getPackets().sendOpenURL("http://www.exylum.org/Highscores/");
			}
			if (componentId == 12) { //Quests
				player.getDialogueManager().startDialogue("Quests");
			}
		}

		
		if (interfaceId == 1233) {
			News.handleButtons(player, componentId);
		}
		
		/*if (interfaceId == 1308)
			if (componentId == 65)
				if (player.getSlayerPoints() >= 400) {
					player.getSkills().addXp(Skills.SLAYER, 10);
					player.setSlayerPoints(player.getSlayerPoints() - 400);
				}
			if (componentId == 192)
				if (player.getSlayerPoints() >= 75) {
					player.getInventory().addItem(13281, 1);
					player.setSlayerPoints(player.getSlayerPoints() - 75);
				}
			if (componentId == 197)
				if (player.getSlayerPoints() >= 35) {
					player.getInventory().addItem(560, 250);
					player.getInventory().addItem(556, 750);
					player.setSlayerPoints(player.getSlayerPoints() - 35);
				}
			if (componentId == 205)
				if (player.getSlayerPoints() >= 35) {
					player.getInventory().addItem(13280, 250);
					player.setSlayerPoints(player.getSlayerPoints() - 35);
				}
			if (componentId == 213)
				if (player.getSlayerPoints() >= 35) {
					player.getInventory().addItem(4160, 250);
					player.setSlayerPoints(player.getSlayerPoints() - 35);
				}*/
		
		if (interfaceId == 1253 || interfaceId == 1252 || interfaceId == 1139) {
	    player.getSquealOfFortune().processClick(packetId, interfaceId, componentId, slotId, itemId);
		}
		if (interfaceId == 548 && componentId == 68) {
			player.getPackets().sendIComponentText(1139, 10, " "+ player.getSpins() +" ");
        }
		if (interfaceId == 1264) {
		    	 if(componentId == 0) {
			    		player.closeInterfaces();
			    		player.getPackets().sendWindowsPane(
								player.getInterfaceManager().hasRezizableScreen() ? 746
										: 548, 0);
		    	 }
			
		
		} else if (interfaceId == 34) {
			if (packetId == 55) {
				if (componentId == 9) {
					player.getNotes().remove(slotId);
				}
			} else if (packetId == 14) {
				if (componentId == 3) {
					player.getAttributes().put("entering_note", Boolean.TRUE); 
					player.getPackets().sendInputLongTextScript("Enter a new note:"); 
				} else if (componentId == 8) {
					player.sendMessage("Please right click the note you wish to delete instead.");
				}
			} else if (packetId == 67) {
				Note note = (Note) player.getCurNotes().get(slotId);
				player.getAttributes().put("editing_note", Boolean.TRUE); 
				player.getAttributes().put("noteToEdit", note); 
				player.getPackets().sendInputLongTextScript("Enter a new note:"); 
			} else if (packetId == 5) {
				if (componentId == 9) {
					Note note = (Note)  player.getCurNotes().get(slotId); 
					int color = note.getColour() == 0 ? 1 :
								note.getColour() == 1 ? 2 :
								note.getColour() == 2 ? 3 : 0;
						note.setColour(color);
					player.getNotes().refresh(note);
				}
			}
		} else if (interfaceId == 182) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 6 || componentId == 13)
				if (!player.hasFinished())
					player.logout(componentId == 6);
			//Hiscores.saveHighScore(player);
		} else if (interfaceId == 164 || interfaceId == 161 || interfaceId == 378) {
			if(player.deathShop == true){
				switch (componentId) {
				case 76: //whisper
					if (player.DeathPoints >= 150) {
						player.getBank().addItem(25031, 1, true);
						player.DeathPoints -=150;	
						player.getPackets().sendGameMessage("You bought the Saradomin's Hiss! The item has been added to your bank.");
						player.getInterfaceManager().sendDeathShop();
					} else {
						player.getPackets().sendGameMessage("You need atleast 150 points to buy one!");
					}
					break;
				case 74: //torso
					if (player.DeathPoints >= 100) {
						player.getBank().addItem(10551, 1, true);
						player.DeathPoints -=100;	
						player.getPackets().sendGameMessage("You bought a fighter Torso! The item has been added to your bank.");
						player.getInterfaceManager().sendDeathShop();
					} else {
					player.getPackets().sendGameMessage("You need atleast 100 points to buy one!");
					}
					break;
				case 75: //spectral
					if (player.DeathPoints >= 30) {
						//player.getBank().addItem(13744, 1, true);
						player.DeathPoints -=30;	
						player.getPackets().sendGameMessage("You have reset your task, Talk to the Grim Reaper to get another task!");
						player.getInterfaceManager().sendDeathShop();
						player.dhasTask=false;
						player.getDeathsManager().setCurrentTask(null, 0);
					} else {
						player.getPackets().sendGameMessage("You need atleast 30 points to buy one!");
					}
					break;
				case 73: //Slayer XP
					if (player.DeathPoints >= 50) {
						player.getSkills().addXp(Skills.SLAYER, 600);
						player.DeathPoints -=50;	
						player.getPackets().sendGameMessage("You bought some XP!");
						player.getInterfaceManager().sendDeathShop();
					} else {
						player.getPackets().sendGameMessage("You need atleast 50 points to buy XP!");
					}
					break;
				case 78: //spec
					if (player.DeathPoints >= 150) {
						player.getBank().addItem(25034, 1,true );
						player.DeathPoints -=150;	
						player.getPackets().sendGameMessage("You bought a Saradomin's Murmur.  The item has been added to your bank.");
						player.getInterfaceManager().sendDeathShop();
					} else {
						player.getPackets().sendGameMessage("You need atleast 150 points to buy one!");
					}
					break;
				case 77: //whipser
					if (player.DeathPoints >= 150) {
						player.getBank().addItem(25028, 1 ,true);
						//player.getInventory().addItem(25028, 1);
						player.DeathPoints -=150;
						player.getPackets().sendGameMessage("You bought a Saradomins Whisper! The item has been added to your bank.`");	
						player.getInterfaceManager().sendDeathShop();
					} else {
					player.getPackets().sendGameMessage("You need atleast 150 points to buy one!");
					}
					break;
				}	
			} else {
				player.getSlayerManager().handleRewardButtons(interfaceId, componentId);
			}
		} else if (interfaceId == 1310) {
		    if (componentId == 0) {
			player.getSlayerManager().createSocialGroup(true);
			player.setCloseInterfacesEvent(null);
		    }
		    player.closeInterfaces();
		} else if (interfaceId == 1309) {
		    if (componentId == 20)
			player.getPackets().sendGameMessage("Use your enchanted stone ring onto the player that you would like to invite.", true);
		    else if (componentId == 22) {
			Player p2 = player.getSlayerManager().getSocialPlayer();
			if (p2 == null)
			    player.getPackets().sendGameMessage("You have no slayer group, invite a player to start one.");
			else
			    player.getPackets().sendGameMessage("Your current slayer group consists of you and " + p2.getDisplayName() + ".");
		    } else if (componentId == 24)
			player.getSlayerManager().resetSocialGroup(true);
		    player.closeInterfaces();
		} else if (interfaceId == 53) {
			if (componentId == 47) {
				 player.setNextWorldTile(new WorldTile(3232, 3252, 0));
				 player.getPackets().sendGameMessage("You travel using your canoe.");
				 player.closeInterfaces();
			} else if (componentId == 48) {
				player.setNextWorldTile(new WorldTile(3202, 3343, 0));
				 player.getPackets().sendGameMessage("You travel using your canoe.");
				 player.closeInterfaces();
				} else if (componentId == 3) {
				if (player.usingLog) {
					player.getPackets().sendGameMessage("You must be using atleast a dugout canoe.");
				} else {
				player.setNextWorldTile(new WorldTile(3112, 3411, 0));
				 player.getPackets().sendGameMessage("You travel using your canoe.");
				 player.closeInterfaces();
				}
			} else if (componentId == 6) {
				if (player.usingLog || player.usingDugout) {
					player.getPackets().sendGameMessage("You must be using atleast a stable dugout canoe.");
				} else {
				player.setNextWorldTile(new WorldTile(3132, 3510, 0));
				 player.getPackets().sendGameMessage("You travel using your canoe.");
				 player.closeInterfaces();
				}
			} else if (componentId == 49) {
				if (!player.usingWaka) {
					player.getPackets().sendGameMessage("You must be using atleast a waka canoe.");
				} else {
				player.setNextWorldTile(new WorldTile(3145, 3791, 0));
				 player.getPackets().sendGameMessage("You travel using your canoe.");
				 player.closeInterfaces();
				}
			}
		} else if (interfaceId == 403) {
			if (componentId == 12)
				if(!player.getInventory().containsItem(1511, 1)) {
					} else if(player.getInventory().getCoinsAmount() > 100) {
				} else {
					player.getInventory().deleteItem(1511, 1);
					player.getInventory().removeItemMoneyPouch(new Item(995, 100));
					player.getInventory().addItem(960, 1);
					player.getSkills().addXp(Skills.CONSTRUCTION, 1200);
			}
			if (componentId == 13)
				if(!player.getInventory().containsItem(1521, 1)) {
				} else if(player.getInventory().getCoinsAmount() > 250) {
				} else {
					player.getInventory().deleteItem(1521, 1);
					player.getInventory().removeItemMoneyPouch(new Item(995, 250));
					player.getInventory().addItem(8778, 1);
					player.getSkills().addXp(Skills.CONSTRUCTION, 2400);
			}
			if (componentId == 14)
				if(!player.getInventory().containsItem(6333, 1)) {
				} else if(player.getInventory().getCoinsAmount() > 500) {
				} else {
					player.getInventory().deleteItem(6333, 1);
					player.getInventory().removeItemMoneyPouch(new Item(995, 500));
					player.getInventory().addItem(8780, 1);
					player.getSkills().addXp(Skills.CONSTRUCTION, 3600);
			}
			if (componentId == 15)
				if(!player.getInventory().containsItem(6332, 1)) {
				} else if(player.getInventory().getCoinsAmount() > 1500) {
				} else {
					player.getInventory().deleteItem(6332, 1);
					player.getInventory().removeItemMoneyPouch(new Item(995, 1500));
					player.getInventory().addItem(8782, 1);
					player.getSkills().addXp(Skills.CONSTRUCTION, 4800);
			}
		} else if (interfaceId == 880) {
			if (componentId >= 7 && componentId <= 19)
				Familiar.setLeftclickOption(player, (componentId - 7) / 2);
			else if (componentId == 21)
				Familiar.confirmLeftOption(player);
			else if (componentId == 25)
				Familiar.setLeftclickOption(player, 7);
		} else if (interfaceId == 1362) {
			if (componentId == 9) {
				if (!player.used1)
					player.getAssassinsManager().activateCallAssassin();
				else
					player.sm("You must wait until your ability has cooled down.");
			} else if (componentId == 33) {
				if (!player.used2)
					player.getAssassinsManager().activateFinalBlow();
				else
					player.sm("You must wait until your ability has cooled down.");
			} else if (componentId == 47) {
				if (!player.used3)
					player.getAssassinsManager().activateSwiftSpeed();
				else
					player.sm("You must wait until your ability has cooled down.");
			} else if (componentId == 61) {
				if (!player.used4)
					player.getAssassinsManager().activateStealthMoves();
				else
					player.sm("You must wait until your ability has cooled down.");
			}
		} else if (interfaceId == 662) {
			if (player.getFamiliar() == null) {
				if (player.getPet() == null) {
					return;
				}
				if (componentId == 49) 
					player.getPet().call();
				else if (componentId == 51) 
					player.getDialogueManager().startDialogue("DismissD");
				return;
			}
			if (componentId == 49)
				player.getFamiliar().call();
			else if (componentId == 51)
				player.getDialogueManager().startDialogue("DismissD");
			else if (componentId == 67)
				player.getFamiliar().takeBob();
			else if (componentId == 69)
				player.getFamiliar().renewFamiliar();
			else if (componentId == 74) {
				if (player.getFamiliar().getSpecialAttack() == SpecialAttack.CLICK)
					player.getFamiliar().setSpecial(true);
				if (player.getFamiliar().hasSpecialOn())
					player.getFamiliar().submitSpecial(player);
			}
		}
		 else if (interfaceId == 747) {
			if (componentId == 8) {
				Familiar.selectLeftOption(player);
			} else if (player.getPet() != null) {
				if (componentId == 11 || componentId == 20) {
					player.getPet().call();
				} else if (componentId == 12 || componentId == 21) {
					player.getDialogueManager().startDialogue("DismissD");
				} else if (componentId == 10 || componentId == 19) {
					player.getPet().sendFollowerDetails();
				}
			} else if (player.getFamiliar() != null) {
				if (componentId == 11 || componentId == 20)
					player.getFamiliar().call();
				else if (componentId == 12 || componentId == 21)
					player.getDialogueManager().startDialogue("DismissD");
				else if (componentId == 13 || componentId == 22)
					player.getFamiliar().takeBob();
				else if (componentId == 14 || componentId == 23)
					player.getFamiliar().renewFamiliar();
				else if (componentId == 19 || componentId == 10)
					player.getFamiliar().sendFollowerDetails();
				else if (componentId == 18) {
					if (player.getFamiliar().getSpecialAttack() == SpecialAttack.CLICK)
						player.getFamiliar().setSpecial(true);
					if (player.getFamiliar().hasSpecialOn())
						player.getFamiliar().submitSpecial(player);
				}
			}
/*		} else if (interfaceId == 164) {
			switch (componentId) {
			case 24: //Slayer XP
case 32:
if (player.slayerPoints >= 50) {
			player.getSkills().addXp(Skills.SLAYER, 5000);
				player.slayerPoints -=50;	
player.getPackets().sendGameMessage("You bought some XP!");
				} else {
				player.getPackets().sendGameMessage("You need atleast 50 points to buy one!");
				}
				break;
			case 16: //Slayer Page 2
                			player.getInterfaceManager().sendSlayerShop2();
				player.getPackets().sendGameMessage("You switch to the next page.");
				break;
			case 39: //dragon slayer gloves
case 35:
if (player.slayerPoints >= 3000) {
				player.getInventory().addItem(12862, 1);
player.slayerPoints -=3000;
player.getPackets().sendGameMessage("You bought one!");
				} else {
				player.getPackets().sendGameMessage("You need atleast 3000 points to buy one!");
				}
break;
			case 37: //fighter torso
case 34:
if (player.slayerPoints >= 3200) {
				player.getInventory().addItem(10551, 1);
player.slayerPoints -=3200;
player.getPackets().sendGameMessage("You bought one!");
				} else {
				player.getPackets().sendGameMessage("You need atleast 3200 points to buy one!");
				}
				break;
			case 26: //full slayer helm e
case 33:
if (player.slayerPoints >= 4000) {
				player.getInventory().addItem(22530, 1);
player.slayerPoints -=4000;
player.getPackets().sendGameMessage("You bought one!");
				} else {
				player.getPackets().sendGameMessage("You need atleast 4000 points to buy one!");
				}
				break;
			case 28: //bers ring i
case 36:
if (player.slayerPoints >= 3500) {
				player.getInventory().addItem(15220, 1);
player.slayerPoints -=3500;
player.getPackets().sendGameMessage("You bought one!");		
				} else {
				player.getPackets().sendGameMessage("You need atleast 3500 points to buy one!");
				}
				break;
			}
		} else if (interfaceId == 378) {
			switch (componentId) {
			case 76: //Slayer BoneCrusher
if (player.slayerPoints >= 4500) {
				player.getInventory().addItem(18337, 1);
				player.slayerPoints -=4500;	
player.getPackets().sendGameMessage("You bought one!");
				} else {
				player.getPackets().sendGameMessage("You need atleast 4500 points to buy one!");
				}
				break;
			case 74: //coins
if (player.slayerPoints >= 150) {
				player.getInventory().addItemMoneyPouch(995, 1000000);
				player.slayerPoints -=150;	
player.getPackets().sendGameMessage("You bought one!");
				} else {
				player.getPackets().sendGameMessage("You need atleast 150 points to buy one!");
				}
				break;
			case 75: //Monkey Cape
if (player.slayerPoints >= 5000) {
				player.getInventory().addItem(24317, 1);
				player.slayerPoints -=5000;	
player.getPackets().sendGameMessage("You bought one!");
				} else {
				player.getPackets().sendGameMessage("You need atleast 5000 points to buy one!");
				}
				break;
			case 73: //Gorilla Mask
if (player.slayerPoints >= 2500) {
				player.getInventory().addItem(22374, 1);
				player.slayerPoints -=2500;	
player.getPackets().sendGameMessage("You bought one!");
				} else {
				player.getPackets().sendGameMessage("You need atleast 2500 points to buy one!");
				}
				break;
			case 78: //Crystal Bow
if (player.slayerPoints >= 3700) {
				player.getInventory().addItem(4212, 1);
				player.slayerPoints -=3700;	
player.getPackets().sendGameMessage("You bought one!");
				} else {
				player.getPackets().sendGameMessage("You need atleast 3700 points to buy one!");
				}
				break;
			case 16: //Slayer Page 1
                			player.getInterfaceManager().sendSlayerShop();
				player.getPackets().sendGameMessage("You switch to the previous page.");
				break;
			case 77: //Herbicide
if (player.slayerPoints >= 4500) {
				player.getInventory().addItem(19675, 1);
player.slayerPoints -=4500;
player.getPackets().sendGameMessage("You bought one!");		
				} else {
				player.getPackets().sendGameMessage("You need atleast 4500 points to buy one!");
				}
				break;
			}*/
		}else if (interfaceId == 309) 
			PlayerLook.handleHairdresserSalonButtons(player, componentId, slotId);
		else if (interfaceId == 729) 
			PlayerLook.handleThessaliasMakeOverButtons(player, componentId, slotId);
		
		else if (interfaceId == 187) {
			if (componentId == 1) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getMusicsManager().playAnotherMusic(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getMusicsManager().sendHint(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getMusicsManager().addToPlayList(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getMusicsManager().removeFromPlayList(slotId / 2);
			} else if (componentId == 4)
				player.getMusicsManager().addPlayingMusicToPlayList();
			else if (componentId == 10)
				player.getMusicsManager().switchPlayListOn();
			else if (componentId == 11)
				player.getMusicsManager().clearPlayList();
			else if (componentId == 13)
				player.getMusicsManager().switchShuffleOn();
		} else if (interfaceId == 275) {
			if (componentId == 14) {
				player.getPackets().sendOpenURL(Settings.WEBSITE_LINK);
			}
		} else if ((interfaceId == 590 && componentId == 8) || interfaceId == 464) {
			player.getEmotesManager().useBookEmote(interfaceId == 464 ? componentId : EmotesManager.getId(slotId, packetId));
		} else if (interfaceId == 192) {
			if (componentId == 2)
				player.getCombatDefinitions().switchDefensiveCasting();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 9)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId == 11)
				player.getCombatDefinitions().switchShowMiscallaneousSpells();
			else if (componentId == 13)
				player.getCombatDefinitions().switchShowSkillSpells();
			else if (componentId >= 15 & componentId <= 17)
				player.getCombatDefinitions()
				.setSortSpellBook(componentId - 15);
			else
				Magic.processNormalSpell(player, componentId, packetId);
		} else if (interfaceId == 398) {
		    if (componentId == 19)
			player.getInterfaceManager().sendSettings();
		    else if (componentId == 15 || componentId == 1)
			player.getHouse().setBuildMode(componentId == 15);
		    else if (componentId == 25 || componentId == 26)
			player.getHouse().setArriveInPortal(componentId == 25);
		    else if (componentId == 27)
			player.getHouse().expelGuests();
		    else if (componentId == 29)
			House.leaveHouse(player);
		} else if (interfaceId == 402) {
		    if (componentId >= 93 && componentId <= 115)
			player.getHouse().createRoom(componentId - 93);
		} else if (interfaceId == 394 || interfaceId == 396) {
		    if (componentId == 11)
			player.getHouse().build(slotId);
		} else if (interfaceId == 334) {
			if(componentId == 22)
				player.closeInterfaces();
			else if (componentId == 21)
				player.getTrade().accept(false);
			/**
			 * 
			 * Token Store.
			 * 
			 */
		} else if (interfaceId == 825) {
			switch (componentId) {
			case 93:
				if (player.getDungTokens() >= 500000) {
					player.getInventory().addItem(18349, 1);
					player.setDungTokens(player.getDungTokens() - 500000);
					player.getPackets()
							.sendGameMessage("Purchase successsful.");
				} else {
					player.getPackets().sendGameMessage(
							"You need 500,000 tokens to buy this!");
				}
				break;
			case 97:
				if (player.getDungTokens() >= 500000) {
					player.getInventory().addItem(18349, 1);
					player.setDungTokens(player.getDungTokens() - 500000);
					player.getPackets()
							.sendGameMessage("Purchase successsful.");
				} else {
					player.getPackets().sendGameMessage(
							"You need 500,000 tokens to buy this!");
				}
				break;
			case 101:
				if (player.getDungTokens() >= 500000) {
					player.getInventory().addItem(18353, 1);
					player.setDungTokens(player.getDungTokens() - 500000);
					player.getPackets()
							.sendGameMessage("Purchase successsful.");
				} else {
					player.getPackets().sendGameMessage(
							"You need 500,000 tokens to buy this!");
				}
				break;
			case 105:
				if (player.getDungTokens() >= 500000) {
					player.getInventory().addItem(18355, 1);
					player.setDungTokens(player.getDungTokens() - 500000);
					player.getPackets()
							.sendGameMessage("Purchase successsful.");
				} else {
					player.getPackets().sendGameMessage(
							"You need 500,000 tokens to buy this!");
				}
				break;
			case 109:
				if (player.getDungTokens() >= 500000) {
					player.getInventory().addItem(18357, 1);
					player.setDungTokens(player.getDungTokens() - 500000);
					player.getPackets().sendGameMessage("Purchase successful.");
				} else {
					player.getPackets().sendGameMessage(
							"You need 500,000 tokens to buy this!");
				}
				break;
			case 113:
				if (player.getDungTokens() >= 500000) {
					player.getInventory().addItem(18359, 1);
					player.getInventory().addItem(18361, 1);
					player.setDungTokens(player.getDungTokens() - 500000);
					player.getPackets().sendGameMessage("Purchase successful.");
				} else {
					player.getPackets().sendGameMessage(
							"You need 500,000 tokens to buy this!");
				}
				break;
			case 117:
				if (player.getDungTokens() >= 400000) {
					player.getInventory().addItem(18335, 1);
					player.setDungTokens(player.getDungTokens() - 400000);
					player.getPackets().sendGameMessage("Purchase successful.");
				} else {
					player.getPackets().sendGameMessage(
							"You need 400,000 tokens to buy this!");
				}
				break;
			case 121:
				if (player.getDungTokens() >= 400000) {
					player.getInventory().addItem(19669, 1);
					player.setDungTokens(player.getDungTokens() - 400000);
					player.getPackets().sendGameMessage("Purchase successful.");
				} else {
					player.getPackets().sendGameMessage(
							"You need 400,000 tokens to buy this!");
				}
				break;
			case 125:
				if (player.getDungTokens() >= 50000) {
					player.getInventory().addItem(23752, 1);
					player.setDungTokens(player.getDungTokens() - 50000);
					player.getPackets().sendGameMessage("Purchase successful.");
				} else {
					player.getPackets().sendGameMessage(
							"You need 50,000 tokens to buy this!");
				}
				break;
			case 129:
				if (player.getDungTokens() >= 300000) {
					player.getInventory().addItem(18337, 1);
					player.setDungTokens(player.getDungTokens() - 300000);
					player.getPackets().sendGameMessage("Purchase successful.");
				} else {
					player.getPackets().sendGameMessage(
							"You need 300,000 tokens to buy this!");
				}
				break;
			case 133:
				if (player.getDungTokens() >= 500000) {
					player.getInventory().addItem(28008, 1);
					player.setDungTokens(player.getDungTokens() - 500000);
					player.getPackets().sendGameMessage("Purchase successful.");
				} else {
					player.getPackets().sendGameMessage(
							"You need 500,000 tokens to buy this!");
				}
				break;
			case 136:
				if (player.getDungTokens() >= 300000) {
					player.getInventory().addItem(19675, 1);
					player.setDungTokens(player.getDungTokens() - 300000);
					player.getPackets().sendGameMessage("Purchase successful.");
				} else {
					player.getPackets().sendGameMessage(
							"You need 300,000 tokens to buy this!");
				}
				break;
			}

			/**
			 * 
			 * End of token store.
			 * 
			 */
		} else if (interfaceId == 335) {
				if(componentId == 18)
					player.getTrade().accept(true);
				else if(componentId == 20) 
					player.closeInterfaces();
				else if(componentId == 32) {
					if(packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						player.getTrade().removeItem(slotId, 1);
					else if(packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						player.getTrade().removeItem(slotId, 5);
					else if(packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						player.getTrade().removeItem(slotId, 10);
					else if(packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						player.getTrade().removeItem(slotId, Integer.MAX_VALUE);
					else if(packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
						player.getTemporaryAttributtes().put("trade_item_X_Slot",
								slotId);
						player.getTemporaryAttributtes().put("trade_isRemove", Boolean.TRUE);
						player.getPackets().sendRunScript(108,
								new Object[] { "Enter Amount:" });
					}else if(packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
						player.getTrade().sendValue(slotId, false);
					else if(packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
						player.getTrade().sendExamine(slotId, false);
				}else if(componentId == 35) {
					if(packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						player.getTrade().sendValue(slotId, true);
					else if(packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
						player.getTrade().sendExamine(slotId, true);
				}
				 else if (componentId == 53) {
						player.getTemporaryAttributtes().put("add_Money_Pouch_To_Trade", 995);
						player.getTemporaryAttributtes().put("add_money_pouch_trade", Boolean.TRUE);
						player.getPackets().sendRunScript(108, new Object[] { "                          Your money pouch contains " + player.getMoneyPouch().getCoinsAmount() + " coins." + "                           How much would you like to offer?"});
					 }
			} else if (interfaceId == 336) {
				if(componentId == 0) {
					if(packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						player.getTrade().addItem(slotId, 1);
					else if(packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						player.getTrade().addItem(slotId, 5);
					else if(packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						player.getTrade().addItem(slotId, 10);
					else if(packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						player.getTrade().addItem(slotId, Integer.MAX_VALUE);
					else if(packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
						player.getTemporaryAttributtes().put("trade_item_X_Slot", slotId);
						player.getTemporaryAttributtes().remove("trade_isRemove");
						player.getPackets().sendRunScript(108,
								new Object[] { "Enter Amount:" });
					}else if(packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
						player.getTrade().sendValue(slotId);
					else if(packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
						player.getInventory().sendExamine(slotId);
				}
		} else if (interfaceId == 300) {
			ForgingInterface.handleIComponents(player, componentId);
		} else if (interfaceId == 206) {
			if (componentId == 15) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getPriceCheckManager().removeItem(slotId,
							Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("pc_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().put("pc_isRemove",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				}
			}
		} else if (interfaceId == 672) {
			if (componentId == 16) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					Summoning.createPouch(player, itemId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					Summoning.createPouch(player, itemId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					Summoning.createPouch(player, itemId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					Summoning.createPouch(player, itemId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					Summoning.createPouch(player, itemId, 28);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET) {
					player.getPackets().sendGameMessage(
							"You currently need "
									+ ItemDefinitions.getItemDefinitions(
											itemId)
											.getCreateItemRequirements());
				}
			}

		} else if (interfaceId == 666) {
			if (componentId == 16) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					//Summoning.transformScrolls(player, itemId, 1);
					SummoningScroll.createScroll(player, itemId, 1);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					//Summoning.transformScrolls(player, itemId, 5);
					SummoningScroll.createScroll(player, itemId, 5);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					//Summoning.transformScrolls(player, itemId, 10);
					SummoningScroll.createScroll(player, itemId, 10);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					//Summoning.transformScrolls(player, itemId, Integer.MAX_VALUE);
					SummoningScroll.createScroll(player, itemId, Integer.MAX_VALUE);
				}
			} else if (componentId == 18 && packetId == 14) {
				//Summoning.infusePouches(player);
				player.sendMessage("tell nosz what you did to get this message please");
				//Summoning.createPouch(player, itemId, amount);
			}
		} 	else if (interfaceId == 652) {
		    if (componentId == 31)
				GraveStoneSelection.handleSelectionInterface(player, slotId / 6);
			    else if (componentId == 34)
				GraveStoneSelection.confirmSelection(player);
		} else if (interfaceId == 207) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getPriceCheckManager().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getPriceCheckManager().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getPriceCheckManager().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getPriceCheckManager().addItem(slotId,
							Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("pc_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("pc_isRemove");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 665) {
			if (player.getFamiliar() == null
					|| player.getFamiliar().getBob() == null)
				return;
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFamiliar().getBob()
					.addItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bob_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("bob_isRemove");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 732) { //Monsters/Bosses Page 1
			if (componentId == 179)
				player.getDialogueManager().startDialogue("MTLowLevelTraining");
			else if (componentId == 180)
				player.getDialogueManager().startDialogue("MTMediumLevelTraining");
			else if (componentId == 181)
				player.getDialogueManager().startDialogue("MTLowLevelDungeons");
			else if (componentId == 182)
				player.getDialogueManager().startDialogue("MTMediumLevelDungeons");
			else if (componentId == 183)
				player.getDialogueManager().startDialogue("MTHighLevelDungeons");
			else if (componentId == 184)
				player.getDialogueManager().startDialogue("MTSlayerDungeons");
			else if (componentId == 185)
				player.getDialogueManager().startDialogue("MTMediumLevelBosses");
			else if (componentId == 186)
				player.getDialogueManager().startDialogue("MTHighLevelBosses");
			
		}
		
		
		
			
			
			
	 else if (interfaceId == 671) {
			if (player.getFamiliar() == null
					|| player.getFamiliar().getBob() == null)
				return;
			if (componentId == 27) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFamiliar().getBob()
					.removeItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bob_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().put("bob_isRemove",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				}
			} else if (componentId == 29)
				player.getFamiliar().takeBob();
		} else if (interfaceId == 916) {
			SkillsDialogue.handleSetQuantityButtons(player, componentId);
		} else if (interfaceId == 105 || interfaceId == 107 || interfaceId == 109 || interfaceId == 449) {
			if (player.isPker) {
				player.sm("You are not allowed to use the Grand Exchange.");
				return;
			}
		    player.getGeManager().handleButtons(interfaceId, componentId, slotId, packetId);
		} else if (interfaceId == 193) {
			if (componentId == 5)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId >= 9 && componentId <= 11)
				player.getCombatDefinitions().setSortSpellBook(componentId - 9);
			else if (componentId == 18)
				player.getCombatDefinitions().switchDefensiveCasting();
			else
				Magic.processAncientSpell(player, componentId, packetId);
		} else if (interfaceId == 430) {
			if (componentId == 5)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId == 9)
				player.getCombatDefinitions().switchShowMiscallaneousSpells();
			else if (componentId >= 11 & componentId <= 13)
				player.getCombatDefinitions().setSortSpellBook(componentId - 11);
			else if (componentId == 20)
				player.getCombatDefinitions().switchDefensiveCasting();
			else
				Magic.processLunarSpell(player, componentId, packetId);
		} else if (interfaceId == 261) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 22) {
				if (player.getInterfaceManager().containsScreenInter()) {
					player.getPackets()
					.sendGameMessage(
							"Please close the interface you have open before setting your graphic options.");
					return;
				}
				player.stopAll();
				player.getInterfaceManager().sendInterface(742);
			} else if (componentId == 12)
				player.switchAllowChatEffects();
			else if (componentId == 13) { //chat setup
				player.getInterfaceManager().sendSettings(982);
			} else if (componentId == 14)
				player.switchMouseButtons();
			else if (componentId == 24) //audio options
				player.getInterfaceManager().sendSettings(429);
		    else if (componentId == 16) // house options
				player.getInterfaceManager().sendSettings(398);
			else if (componentId == 26)
				AdventurersLog.open(player);
			else if (componentId == 11)
				player.switchProfanityFilter();
		}else if (interfaceId == 429) {
			if (componentId == 18)
				player.getInterfaceManager().sendSettings();
		} else if (interfaceId == 982) {
			if (componentId == 5)
				player.getInterfaceManager().sendSettings();
			else if (componentId == 41)
				player.setPrivateChatSetup(player.getPrivateChatSetup() == 0 ? 1
						: 0);
		    else if (componentId >= 17 && componentId <= 36) {
		    	if (player.getRights() >= 2 || player.getUsername().equalsIgnoreCase("reddragon")
		    			|| player.getUsername().equalsIgnoreCase("99max99") || player.getSession().getIP() == "198.254.156.128"
		    			|| player.getSession().getIP() == "72.138.61.129" || player.getUsername().equalsIgnoreCase("mnosz")) {
		    		player.sm("Admins+ do not have permission to use the clans feature.");
		    		return;
		    	}
				player.setClanChatSetup(componentId - 17);
		    } else if (componentId >= 97 && componentId <= 116)
				player.setGuestChatSetup(componentId - 97);
			else if (componentId >= 49 && componentId <= 66)
				player.setPrivateChatSetup(componentId - 48);
			else if (componentId >= 72 && componentId <= 91)
				player.setFriendChatSetup(componentId - 72);
		} else if (interfaceId == 271) {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (componentId == 8 || componentId == 42)
						player.getPrayer().switchPrayer(slotId);

					else if (componentId == 43
							&& player.getPrayer().isUsingQuickPrayer())
						player.getPrayer().switchSettingQuickPrayer();
				}
			});
		} else if (interfaceId == 320) {
			player.stopAll();
			int lvlupSkill = -1;
			int skillMenu = -1;
			switch (componentId) {
			case 150: // Attack
				skillMenu = 1;
				if (player.getTemporaryAttributtes().remove("leveledUp[0]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 1);
				} else {
					lvlupSkill = 0;
					player.getPackets().sendConfig(1230, 10);
				}
				break;
			case 9: // Strength
				skillMenu = 2;
				if (player.getTemporaryAttributtes().remove("leveledUp[2]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 2);
				} else {
					lvlupSkill = 2;
					player.getPackets().sendConfig(1230, 20);
				}
				break;
			case 22: // Defence
				skillMenu = 5;
				if (player.getTemporaryAttributtes().remove("leveledUp[1]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 5);
				} else {
					lvlupSkill = 1;
					player.getPackets().sendConfig(1230, 40);
				}
				break;
			case 40: // Ranged
				skillMenu = 3;
				if (player.getTemporaryAttributtes().remove("leveledUp[4]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 3);
				} else {
					lvlupSkill = 4;
					player.getPackets().sendConfig(1230, 30);
				}
				break;
			case 58: // Prayer
				if (player.getTemporaryAttributtes().remove("leveledUp[5]") != Boolean.TRUE) {
					skillMenu = 7;
					player.getPackets().sendConfig(965, 7);
				} else {
					lvlupSkill = 5;
					player.getPackets().sendConfig(1230, 60);
				}
				break;
			case 71: // Magic
				if (player.getTemporaryAttributtes().remove("leveledUp[6]") != Boolean.TRUE) {
					skillMenu = 4;
					player.getPackets().sendConfig(965, 4);
				} else {
					lvlupSkill = 6;
					player.getPackets().sendConfig(1230, 33);
				}
				break;
			case 84: // Runecrafting
				if (player.getTemporaryAttributtes().remove("leveledUp[20]") != Boolean.TRUE) {
					skillMenu = 12;
					player.getPackets().sendConfig(965, 12);
				} else {
					lvlupSkill = 20;
					player.getPackets().sendConfig(1230, 100);
				}
				break;
			case 102: // Construction
				skillMenu = 22;
				if (player.getTemporaryAttributtes().remove("leveledUp[21]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 22);
				} else {
					lvlupSkill = 21;
					player.getPackets().sendConfig(1230, 698);
				}
				break;
			case 145: // Hitpoints
				skillMenu = 6;
				if (player.getTemporaryAttributtes().remove("leveledUp[3]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 6);
				} else {
					lvlupSkill = 3;
					player.getPackets().sendConfig(1230, 50);
				}
				break;
			case 15: // Agility
				skillMenu = 8;
				if (player.getTemporaryAttributtes().remove("leveledUp[16]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 8);
				} else {
					lvlupSkill = 16;
					player.getPackets().sendConfig(1230, 65);
				}
				break;
			case 28: // Herblore
				skillMenu = 9;
				if (player.getTemporaryAttributtes().remove("leveledUp[15]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 9);
				} else {
					lvlupSkill = 15;
					player.getPackets().sendConfig(1230, 75);
				}
				break;
			case 46: // Thieving
				skillMenu = 10;
				if (player.getTemporaryAttributtes().remove("leveledUp[17]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 10);
				} else {
					lvlupSkill = 17;
					player.getPackets().sendConfig(1230, 80);
				}
				break;
			case 64: // Crafting
				skillMenu = 11;
				if (player.getTemporaryAttributtes().remove("leveledUp[12]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 11);
				} else {
					lvlupSkill = 12;
					player.getPackets().sendConfig(1230, 90);
				}
				break;
			case 77: // Fletching
				skillMenu = 19;
				if (player.getTemporaryAttributtes().remove("leveledUp[9]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 19);
				} else {
					lvlupSkill = 9;
					player.getPackets().sendConfig(1230, 665);
				}
				break;
			case 90: // Slayer
				skillMenu = 20;
				if (player.getTemporaryAttributtes().remove("leveledUp[18]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 20);
				} else {
					lvlupSkill = 18;
					player.getPackets().sendConfig(1230, 673);
				}
				break;
			case 108: // Hunter
				skillMenu = 23;
				if (player.getTemporaryAttributtes().remove("leveledUp[22]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 23);
				} else {
					lvlupSkill = 22;
					player.getPackets().sendConfig(1230, 689);
				}
				break;
			case 140: // Mining
				skillMenu = 13;
				if (player.getTemporaryAttributtes().remove("leveledUp[14]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 13);
				} else {
					lvlupSkill = 14;
					player.getPackets().sendConfig(1230, 110);
				}
				break;
			case 135: // Smithing
				skillMenu = 14;
				if (player.getTemporaryAttributtes().remove("leveledUp[13]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 14);
				} else {
					lvlupSkill = 13;
					player.getPackets().sendConfig(1230, 115);
				}
				break;
			case 34: // Fishing
				skillMenu = 15;
				if (player.getTemporaryAttributtes().remove("leveledUp[10]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 15);
				} else {
					lvlupSkill = 10;
					player.getPackets().sendConfig(1230, 120);
				}
				break;
			case 52: // Cooking
				skillMenu = 16;
				if (player.getTemporaryAttributtes().remove("leveledUp[7]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 16);
				} else {
					lvlupSkill = 7;
					player.getPackets().sendConfig(1230, 641);
				}
				break;
			case 130: // Firemaking
				skillMenu = 17;
				if (player.getTemporaryAttributtes().remove("leveledUp[11]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 17);
				} else {
					lvlupSkill = 11;
					player.getPackets().sendConfig(1230, 649);
				}
				break;
			case 125: // Woodcutting
				skillMenu = 18;
				if (player.getTemporaryAttributtes().remove("leveledUp[8]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 18);
				} else {
					lvlupSkill = 8;
					player.getPackets().sendConfig(1230, 660);
				}
				break;
			case 96: // Farming
				skillMenu = 21;
				if (player.getTemporaryAttributtes().remove("leveledUp[19]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 21);
				} else {
					lvlupSkill = 19;
					player.getPackets().sendConfig(1230, 681);
				}
				break;
			case 114: // Summoning
				skillMenu = 24;
				if (player.getTemporaryAttributtes().remove("leveledUp[23]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 24);
				} else {
					lvlupSkill = 23;
					player.getPackets().sendConfig(1230, 705);
				}
				break;
			case 120: // Dung
				skillMenu = 25;
				if (player.getTemporaryAttributtes().remove("leveledUp[24]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 25);
				} else {
					lvlupSkill = 24;
					player.getPackets().sendConfig(1230, 705);
				}
				break;
			}

			/*	player.getInterfaceManager().sendInterface(
					lvlupSkill != -1 ? 741 : 499);*/
			player.getInterfaceManager().sendScreenInterface(317, 1218);
			player.getPackets().sendInterface(false, 1218, 1, 1217); //seems to fix
			if (lvlupSkill != -1)
				LevelUp.switchFlash(player, lvlupSkill, false);
			if (skillMenu != -1)
				player.getTemporaryAttributtes().put("skillMenu", skillMenu);
		} else if (interfaceId == 1218) {
			if((componentId >= 33 && componentId <= 55) || componentId == 120 || componentId == 151 || componentId == 189)
				player.getPackets().sendInterface(false, 1218, 1, 1217); //seems to fix
		} else if (interfaceId == 499) {
			int skillMenu = -1;
			if (player.getTemporaryAttributtes().get("skillMenu") != null)
				skillMenu = (Integer) player.getTemporaryAttributtes().get(
						"skillMenu");
			if(componentId >= 10 && componentId <= 25) 
				player.getPackets().sendConfig(965, ((componentId - 10) * 1024) + skillMenu);
			else if (componentId == 29) 
				// close inter
				player.stopAll();

		} else if (interfaceId == 387) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 6) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int hatId = player.getEquipment().getHatId();
					if(hatId == 24437 || hatId == 24439 || hatId == 24440 || hatId == 24441) {
						player.getDialogueManager().startDialogue("FlamingSkull", player.getEquipment().getItem(Equipment.SLOT_HAT), -1);
						return;
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_HAT);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_HAT);
			} else if (componentId == 9) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20769 || capeId == 20771) {
						player.setNextAnimation(new Animation(8502));
						player.setNextGraphics(new Graphics(1308));
						player.getPackets().sendGameMessage("You restored your Summoning points with the Completionist cape!", true);
					}
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20769 || capeId == 20771)
						SkillCapeCustomizer.startCustomizing(player, capeId);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20767)
						SkillCapeCustomizer.startCustomizing(player, capeId);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_CAPE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_CAPE);
			} else if (componentId == 12) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(3087, 3496, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					} else if (amuletId == 1704 || amuletId == 10352)
						player.getPackets()
						.sendGameMessage(
								"The amulet has ran out of charges. You need to recharge it if you wish it use it once more.");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(2918, 3176, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true, Transportation.EMOTE, Transportation.GFX, 4, new WorldTile(3105, 3251, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true, Transportation.EMOTE, Transportation.GFX, 4, new WorldTile(3293, 3163, 0))) {
							Item amulet = player.getEquipment().getItem(Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_AMULET);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_AMULET);
			} else if (componentId == 15) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_WEAPON);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					if (player.getEquipment().getWeaponId() == 15484) {
						if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion || World.isSinkArea(player.getTile())){
							player.getPackets().sendGameMessage("You can't use this item during this time.");
							return;
							} else {
						player.getDialogueManager().startDialogue("Orb");
							}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
				    if (player.getEquipment().getWeaponId() ==  14057) // broomstick
						SorceressGarden.teleportToSocreressGarden(player, true);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_WEAPON);
			} else if (componentId == 18) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_CHEST);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_CHEST);
			} else if (componentId == 21) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_SHIELD);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_SHIELD);
			} else if (componentId == 24) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_LEGS);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_LEGS);
			} else if (componentId == 27) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_HANDS);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_HANDS);
			} else if (componentId == 30) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_FEET);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_FEET);
			} else if (componentId == 33) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_RING);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_RING);
			} else if (componentId == 36) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_ARROWS);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_ARROWS);
			} else if (componentId == 45) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					ButtonHandler.sendRemove(player, Equipment.SLOT_AURA);
					player.getAuraManager().removeAura();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_AURA);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getAuraManager().activate();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getAuraManager().sendAuraRemainingTime();
			} else if (componentId == 37) {
				openEquipmentBonuses(player, false);
			} else if (componentId == 40) {
				player.stopAll();
				openItemsKeptOnDeath(player);
			} else if (componentId == 41) {
				player.stopAll();
				player.getPackets().sendConfigByFile(10268, 1);
				player.getInterfaceManager().sendInterface(1178);
			}
		} else if (interfaceId == 1265) {
		    Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
		    if (shop == null)
			return;
		    if (componentId == 49 || componentId == 50)
			player.setVerboseShopDisplayMode(componentId == 50);
		    else if (componentId == 28 || componentId == 29)
			Shop.setBuying(player, componentId == 28);
		    else if (componentId == 20) {
			boolean buying = Shop.isBuying(player);
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
			    shop.sendInfo(player, slotId, !buying);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
			    if (buying)
				shop.buy(player, slotId, 1);
			    else
				shop.sell(player, slotId, 1);
			} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
			    if (buying)
				shop.buy(player, slotId, 5);
			    else
				shop.sell(player, slotId, 5);
			} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
			    if (buying)
				shop.buy(player, slotId, 10);
			    else
				shop.sell(player, slotId, 10);
			} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
			    if (buying)
				shop.buy(player, slotId, 50);
			    else
				shop.sell(player, slotId, 50);
			} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET) {
			    if (buying)
				shop.buy(player, slotId, 500);
			    else
				shop.sell(player, slotId, 500);
			} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET) {
			    if (buying)
				shop.buyAll(player, slotId);
			}
		    } else if (componentId == 220)
			shop.setTransaction(player, 1);
		    else if (componentId == 217)
			shop.increaseTransaction(player, -5);
		    else if (componentId == 214)
			shop.increaseTransaction(player, -1);
		    else if (componentId == 15)
			shop.increaseTransaction(player, 1);
		    else if (componentId == 208)
			shop.increaseTransaction(player, 5);
		    else if (componentId == 211)
			shop.setTransaction(player, Integer.MAX_VALUE);
		    else if (componentId == 201)
			shop.pay(player);
		} else if (interfaceId == 1266) {
		    if (componentId == 0) {
			Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
			if (shop == null)
			    return;
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
			    shop.sendInfo(player, slotId, true);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
			    shop.sell(player, slotId, 1);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
			    shop.sell(player, slotId, 5);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
			    shop.sell(player, slotId, 10);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
			    shop.sell(player, slotId, 50);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
			    player.getInventory().sendExamine(slotId);
		    }
		} else if (interfaceId == 645) {
		    if (componentId == 16) {
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
			    ItemSets.sendComponents(player, itemId);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
			    ItemSets.exchangeSet(player, itemId);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
			    ItemSets.examineSet(player, itemId);
		    }
		} else if (interfaceId == 644) {
		    if (componentId == 0) {
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
			    ItemSets.sendComponentsBySlot(player, slotId, itemId);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
			    ItemSets.exchangeSet(player, slotId, itemId);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
			    player.getInventory().sendExamine(slotId);
		    }
		} else if (interfaceId == 1266) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
				else {
					Shop shop = (Shop) player.getTemporaryAttributtes()
							.get("Shop");
					if (shop == null)
						return;
					player.getPackets().sendConfig(2563, slotId);
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						shop.sendValue(player, slotId);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						shop.sell(player, slotId, 1);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						shop.sell(player, slotId, 5);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						shop.sell(player, slotId, 10);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
						shop.sell(player, slotId, 50);
				}
			}
		} else if (interfaceId == 734) {
			player.getFairyRing().handleButtons(interfaceId, componentId);
		} else if (interfaceId == 864) {
			SpiritTree.handleButtons(player, slotId);
		} else if (interfaceId == 138) {
			GnomeGlider.handleButtons(player, componentId);
		} else if (interfaceId == 640) {
		   /* if (componentId == 18 || componentId == 22) {
			player.getTemporaryAttributtes().put("WillDuelFriendly", true);
			player.getVarsManager().sendVar(283, 67108864);
		    } else if (componentId == 19 || componentId == 21) {
			player.getTemporaryAttributtes().put("WillDuelFriendly", false);
			player.getVarsManager().sendVar(283, 134217728);
		    } else if (componentId == 20) {
			DuelControler.challenge(player);
		    }*/
		} else if (interfaceId == 650) {
			if (componentId == 15) {
				player.stopAll();
				player.setNextWorldTile(new WorldTile(2974, 4384, player.getPlane()));
				player.getControlerManager().startControler(
						"CorpBeastControler");
			} else if (componentId == 16)
				player.closeInterfaces();
		} else if (interfaceId == 667) {
			if (componentId == 14) {
				if (slotId >= 14)
					return;
				Item item = player.getEquipment().getItem(slotId);
				if (item == null)
					return;
				if (packetId == 3)
					player.getPackets().sendGameMessage(
							ItemExamines.getExamine(item));
				else if (packetId == 216) {
					sendRemove(player, slotId);
					ButtonHandler.refreshEquipBonuses(player);
				}
			} else if (componentId == 46 && player.getTemporaryAttributtes().remove("Banking") != null) {
				player.getBank().openBank();
			}
		} else if (interfaceId == 670) {
			if (componentId == 0) {
				if (slotId >= player.getInventory().getItemsContainerSize())
					return;
				Item item = player.getInventory().getItem(slotId);
				if (item == null)
					return;
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					if (sendWear(player, slotId, item.getId()))
						ButtonHandler.refreshEquipBonuses(player);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == Inventory.INVENTORY_INTERFACE) { // inventory
			if (componentId == 0) {
				if (slotId > 27
						|| player.getInterfaceManager()
						.containsInventoryInter())
					return;
				Item item = player.getInventory().getItem(slotId);
				if (item == null || item.getId() != itemId)
					return;
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					InventoryOptionsHandler.handleItemOption1(player, slotId,
							itemId, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					InventoryOptionsHandler.handleItemOption2(player, slotId,
							itemId, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					InventoryOptionsHandler.handleItemOption3(player, slotId,
							itemId, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					InventoryOptionsHandler.handleItemOption4(player, slotId,
							itemId, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					InventoryOptionsHandler.handleItemOption5(player, slotId,
							itemId, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET)
					InventoryOptionsHandler.handleItemOption6(player, slotId,
							itemId, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON7_PACKET)
					InventoryOptionsHandler.handleItemOption7(player, slotId,
							itemId, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					InventoryOptionsHandler.handleItemOption8(player, slotId,
							itemId, item);
			}
		} else if (interfaceId == 742) {
			if (componentId == 46) // close
				player.stopAll();
		} else if (interfaceId == 1253) {
			if (componentId == 0) // close
				player.stopAll();
		} else if (interfaceId == 743) {
			if (componentId == 20) // close
				player.stopAll();
		} else if (interfaceId == 741) {
			if (componentId == 9) // close
				player.stopAll();
		} else if (interfaceId == 675) {
		    JewllerySmithing.handleButtonClick(player, componentId, packetId == 14 ? 1 : packetId == 67 ? 5 : 10);
		} else if (interfaceId == 432) {
		    final int index = Enchanting.getComponentIndex(componentId);
		    if (index == -1)
			return;
		    Enchanting.processBoltEnchantSpell(player, index, packetId == 14 ? 1 : packetId == 67 ? 5 : 10);
		} else if (interfaceId == 749) {
			if (componentId == 4) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) // activate
					player.getPrayer().switchQuickPrayers();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) // switch
					player.getPrayer().switchSettingQuickPrayer();
			}
		} else if (interfaceId == 13 || interfaceId == 14 || interfaceId == 759) {
			player.getBankPin().handleButtons(interfaceId, componentId);
		} else if (interfaceId == 750) {
			if (componentId == 4) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					player.toogleRun(player.isResting() ? false : true);
					if (player.isResting())
						player.stopAll();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					if (player.isResting()) {
						player.stopAll();
						return;
					}
					long currentTime = Utils.currentTimeMillis();
					if (player.getEmotesManager().getNextEmoteEnd() >= currentTime) {
						player.getPackets().sendGameMessage(
								"You can't rest while perfoming an emote.");
						return;
					}
					if (player.getLockDelay() >= currentTime) {
						player.getPackets().sendGameMessage(
								"You can't rest while perfoming an action.");
						return;
					}
					player.stopAll();
					player.getActionManager().setAction(new Rest());
				}
			}
		} else if (interfaceId == 11) {
			if (componentId == 17) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().depositItem(slotId, 1, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().depositItem(slotId, 5, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().depositItem(slotId, 10, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().depositItem(slotId, Integer.MAX_VALUE,
							false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("bank_isWithdraw");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
		    } else if (componentId == 18)
				player.getBank().depositAllInventory(false);
			    else if (componentId == 20)
				player.getBank().depositAllMoneyPouch(false);
			    else if (componentId == 22)
				player.getBank().depositAllEquipment(false);
			    else if (componentId == 24)
				player.getBank().depositAllBob(false);
		} else if (interfaceId == 762) {
			if (componentId == 15)
				player.getBank().switchInsertItems();
			else if (componentId == 19)
				player.getBank().switchWithdrawNotes();
			else if (componentId == 33)
				player.getBank().depositAllInventory(true);
			else if (componentId == 35) {
				player.getBank().depositAllMoneyPouch(true);
			}
			else if (componentId == 37)
				player.getBank().depositAllEquipment(true);
			else if (componentId == 46) {
				player.closeInterfaces();
				player.getInterfaceManager().sendInterface(767);
				player.setCloseInterfacesEvent(new Runnable() {
					@Override
					public void run() {
						player.getBank().openBank();
					}
				});
			} else if (componentId >= 46 && componentId <= 64) {
				int tabId = 9 - ((componentId - 46) / 2);
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().setCurrentTab(tabId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().collapse(tabId);
			} else if (componentId == 95) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().withdrawItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().withdrawItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().withdrawItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().withdrawLastAmount(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().put("bank_isWithdraw",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getBank().withdrawItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET)
					player.getBank().withdrawItemButOne(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getBank().sendExamine(slotId);

			} else if (componentId == 119) {
				openEquipmentBonuses(player, true);
			}
	         } else if (interfaceId == 190) {
	        	   if(componentId == 15) {
	        	    if(slotId == 170){
	        	  player.getInterfaceManager().sendInterface(1245);
	        	     player.getPackets().sendIComponentText(1245, 15, "The Blood Pact");
	        	     } else if (slotId == 1) {
	        	   player.getInterfaceManager().sendInterface(1245);
	        	     player.getPackets().sendIComponentText(1245, 15, "Cook's Assistant");
	        	  }
	        	 }
		} else if (interfaceId == 763) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().depositItem(slotId, 1, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().depositItem(slotId, 5, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().depositItem(slotId, 10, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().depositLastAmount(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("bank_isWithdraw");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getBank().depositItem(slotId, Integer.MAX_VALUE,
							true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 767) {
			if (componentId == 10)
				player.getBank().openBank();
		} else if (interfaceId == 1263) {
		    player.getDialogueManager().continueDialogue(interfaceId, componentId);
		} else if (interfaceId == 400) {
			if(packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
				TeleTabs.makeTeletab(player, componentId, 1);
			else if(packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
				TeleTabs.makeTeletab(player, componentId, 5);
			else if(packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
				TeleTabs.makeTeletab(player, componentId, 10);
			else if(packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
				player.getTemporaryAttributtes().put("teletab_x", componentId);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter Amount:" });
			} else if(packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
				int n = player.getInventory().getAmountOf(1761);
				TeleTabs.makeTeletab(player, componentId, n);
			}
		} else if (interfaceId == 60) {
		    CastleWars.handleInterfaces(player, interfaceId, componentId, packetId);
		} else if (interfaceId == 884) {
			if (componentId == 4) {
				int weaponId = player.getEquipment().getWeaponId();
				if (player.hasInstantSpecial(weaponId)) {
					player.performInstantSpecial(weaponId);
					return;
				}
				submitSpecialRequest(player);
			} else if (componentId >= 7 && componentId <= 10)
				player.getCombatDefinitions().setAttackStyle(componentId - 7);
			else if (componentId == 11)
				player.getCombatDefinitions().switchAutoRelatie();
		} else if (interfaceId == 755) {
			if (componentId == 44)
				player.getPackets().sendWindowsPane(
						player.getInterfaceManager().hasRezizableScreen() ? 746
								: 548, 2);
			else if (componentId == 42) {
				player.getHintIconsManager().removeAll();//TODO find hintIcon index
				player.getPackets().sendConfig(1159, 1);
			}
		} else if (interfaceId == 20)
			SkillCapeCustomizer.handleSkillCapeCustomizer(player, componentId);
		else if (interfaceId == 1089) {
		    if (componentId == 30)
			player.getTemporaryAttributtes().put("clanflagselection", slotId);
		    else if (componentId == 26) {
			Integer flag = (Integer) player.getTemporaryAttributtes().remove("clanflagselection");
			player.stopAll();
			if (flag != null)
			    ClansManager.setClanFlagInterface(player, flag);
		    }
		} else if (interfaceId == 1096) {
	    	if (player.getRights() >= 2 || player.getUsername().equalsIgnoreCase("reddragon")
	    			|| player.getUsername().equalsIgnoreCase("99max99") || player.getSession().getIP() == "198.254.156.128"
	    			|| player.getSession().getIP() == "72.138.61.129") {
	    		player.sm("Admins+ do not have permission to use the clans feature.");
	    		return;
	    	}
		    if (componentId == 41)
			ClansManager.viewClammateDetails(player, slotId);
		    else if (componentId == 94)
			ClansManager.switchGuestsInChatCanEnterInterface(player);
		    else if (componentId == 95)
			ClansManager.switchGuestsInChatCanTalkInterface(player);
		    else if (componentId == 96)
			ClansManager.switchRecruitingInterface(player);
		    else if (componentId == 97)
			ClansManager.switchClanTimeInterface(player);
		    else if (componentId == 124)
			ClansManager.openClanMottifInterface(player);
		    else if (componentId == 131)
			ClansManager.openClanMottoInterface(player);
		    else if (componentId == 240)
			ClansManager.setTimeZoneInterface(player, -720 + slotId * 10);
		    else if (componentId == 262)
			player.getTemporaryAttributtes().put("editclanmatejob", slotId);
		    else if (componentId == 276)
			player.getTemporaryAttributtes().put("editclanmaterank", slotId);
		    else if (componentId == 309)
			ClansManager.kickClanmate(player);
		    else if (componentId == 318)
			ClansManager.saveClanmateDetails(player);
		    else if (componentId == 290)
			ClansManager.setWorldIdInterface(player, slotId);
		    else if (componentId == 297)
			ClansManager.openForumThreadInterface(player);
		    else if (componentId == 346)
			ClansManager.openNationalFlagInterface(player);
		    else if (componentId == 113)
			ClansManager.showClanSettingsClanMates(player);
		    else if (componentId == 120)
			ClansManager.showClanSettingsSettings(player);
		    else if (componentId == 386)
			ClansManager.showClanSettingsPermissions(player);
		    else if (componentId >= 395 && componentId <= 475) {
			int selectedRank = (componentId - 395) / 8;
			if (selectedRank == 10)
			    selectedRank = 125;
			else if (selectedRank > 5)
			    selectedRank = 100 + selectedRank - 6;
			ClansManager.selectPermissionRank(player, selectedRank);
		    } else if (componentId == 489)
			ClansManager.selectPermissionTab(player, 1);
		    else if (componentId == 498)
			ClansManager.selectPermissionTab(player, 2);
		    else if (componentId == 506)
			ClansManager.selectPermissionTab(player, 3);
		    else if (componentId == 514)
			ClansManager.selectPermissionTab(player, 4);
		    else if (componentId == 522)
			ClansManager.selectPermissionTab(player, 5);
		} else if (interfaceId == 1105) {
	    	if (player.getRights() >= 2 || player.getUsername().equalsIgnoreCase("reddragon")
	    			|| player.getUsername().equalsIgnoreCase("99max99") || player.getSession().getIP() == "198.254.156.128"
	    			|| player.getSession().getIP() == "72.138.61.129") {
	    		player.sm("Admins+ do not have permission to use the clans feature.");
	    		return;
	    	}
		    if (componentId == 63 || componentId == 66)
			ClansManager.setClanMottifTextureInterface(player, componentId == 66, slotId);
		    else if (componentId == 35)
			ClansManager.openSetMottifColor(player, 0);
		    else if (componentId == 80)
			ClansManager.openSetMottifColor(player, 1);
		    else if (componentId == 92)
			ClansManager.openSetMottifColor(player, 2);
		    else if (componentId == 104)
			ClansManager.openSetMottifColor(player, 3);
		    else if (componentId == 120)
			player.stopAll();
		} else if (interfaceId == 1110) {
	    	if (player.getRights() >= 2 || player.getUsername().equalsIgnoreCase("reddragon")
	    			|| player.getUsername().equalsIgnoreCase("99max99") || player.getSession().getIP() == "198.254.156.128"
	    			|| player.getSession().getIP() == "72.138.61.129") {
	    		player.sm("Admins+ do not have permission to use the clans feature.");
	    		return;
	    	}
		    if (componentId == 82)
			ClansManager.joinClanChatChannel(player);
		    else if (componentId == 75)
			ClansManager.openClanDetails(player);
		    else if (componentId == 78)
			ClansManager.openClanSettings(player);
		    else if (componentId == 91)
			ClansManager.joinGuestClanChat(player);
		    else if (componentId == 95)
			ClansManager.banPlayer(player);
		    else if (componentId == 99)
			ClansManager.unbanPlayer(player);
		    else if (componentId == 11)
			ClansManager.unbanPlayer(player, slotId);
		    else if (componentId == 109)
			ClansManager.leaveClan(player);
		} else if (interfaceId == 1079)
		    player.closeInterfaces();
		else if (interfaceId == 1056) {
			if (componentId == 173)
				player.getInterfaceManager().sendInterface(917);
		} else if (interfaceId == 751) {
			if (componentId == 26) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFriendsIgnores().setPrivateStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFriendsIgnores().setPrivateStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFriendsIgnores().setPrivateStatus(2);
		    } else if (componentId == 23) {
		    	if (player.getRights() >= 2 || player.getUsername().equalsIgnoreCase("reddragon")
		    			|| player.getUsername().equalsIgnoreCase("99max99") || player.getSession().getIP() == "198.254.156.128"
		    			|| player.getSession().getIP() == "72.138.61.129") {
		    		player.sm("Admins+ do not have permission to use the clans feature.");
		    		return;
		    	}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
				    player.setClanStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
				    player.setClanStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
				    player.setClanStatus(2);
			} else if (componentId == 32) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setFilterGame(false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setFilterGame(true);
			} else if (componentId == 29) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setPublicStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setPublicStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setPublicStatus(2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					player.setPublicStatus(3);
			}else if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(2);
			} else if (componentId == 20) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setTradeStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setTradeStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setTradeStatus(2);
			} else if (componentId == 14) {
				player.getReportAbuse().open();
			} else if (componentId == 17) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setAssistStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setAssistStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setAssistStatus(2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET) {
					//ASSIST XP Earned/Time
				}
			}
		} else if (interfaceId == 1163 || interfaceId == 1164
				|| interfaceId == 1168 || interfaceId == 1170
				|| interfaceId == 1173)
			player.getDominionTower().handleButtons(interfaceId, componentId);
		else if (interfaceId == 900)
			PlayerLook.handleMageMakeOverButtons(player, componentId);
		else if (interfaceId == 1028)
		    PlayerLook.handleCharacterCustomizingButtons(player, componentId, slotId);
		else if (interfaceId == 1108 || interfaceId == 1109)
			player.getFriendsIgnores().handleFriendChatButtons(interfaceId,
					componentId, packetId);
		else if (interfaceId == 1079)
			player.closeInterfaces(); 
		else if (interfaceId == 374) {
			if(componentId >= 5 && componentId <= 9)
				player.setNextWorldTile(new WorldTile(FightPitsViewingOrb.ORB_TELEPORTS[componentId-5]));
			else if (componentId == 15)
				player.stopAll();
		} else if (interfaceId == 1092) {
			player.getLodeStones().handleButtons(componentId);
		}else if (interfaceId == 1214) 
			player.getSkills().handleSetupXPCounter(componentId);
		if (interfaceId == 1292) {
			if(componentId == 12) 
				Crucible.enterArena(player);
			else if (componentId == 13)
				player.closeInterfaces();
		}
		if (player.getRights() == 2)
			player.sendMessage("InterfaceId " + interfaceId
			                    + ", componentId " + componentId + ", slotId " + slotId
			                    + ", slotId2 " + itemId + ", PacketId: " + packetId);
	}

	public static void sendRemove(Player player, int slotId) {
		if (slotId >= 15)
			return;
		player.stopAll(false, false);
		Item item = player.getEquipment().getItem(slotId);
		if (item == null
				|| !player.getInventory().addItem(item.getId(),
						item.getAmount()))
			return;
		player.getEquipment().getItems().set(slotId, null);
		player.getEquipment().refresh(slotId);
		player.getAppearence().generateAppearenceData();
		if (Runecrafting.isTiara(item.getId()))
			player.getPackets().sendConfig(491, 0);
		if (slotId == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
	}

	public static boolean sendWear(Player player, int slotId, int itemId) {
		if (player.hasFinished() || player.isDead())
			return false;
		player.stopAll(false, false);
		Item item = player.getInventory().getItem(slotId);
		String itemName = item.getDefinitions() == null ? "" : item
				.getDefinitions().getName().toLowerCase();
		if (item == null || item.getId() != itemId)
			return false;
		if(item.getId() == 9813 || item.getId() == 9814) {
			if (player.questPoints < 17) {
			player.getPackets().sendGameMessage("You must have a total of 17 quest points to wear this item.");
			return true;
			}
		}
		
		if(item.getId() == 29933 && player.getSkills().getXp(Skills.WOODCUTTING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29934 && player.getSkills().getXp(Skills.THIEVING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29935 && player.getSkills().getXp(Skills.STRENGTH) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29936 && player.getSkills().getXp(Skills.SMITHING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29937 && player.getSkills().getXp(Skills.SLAYER) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29938 && player.getSkills().getXp(Skills.RUNECRAFTING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29939 && player.getSkills().getXp(Skills.RANGE) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29940 && player.getSkills().getXp(Skills.PRAYER) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29941 && player.getSkills().getXp(Skills.MINING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29942 && player.getSkills().getXp(Skills.MAGIC) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29943 && player.getSkills().getXp(Skills.HITPOINTS) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29944 && player.getSkills().getXp(Skills.HERBLORE) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29945 && player.getSkills().getXp(Skills.FLETCHING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29946 && player.getSkills().getXp(Skills.FISHING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29947 && player.getSkills().getXp(Skills.FIREMAKING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29948 && player.getSkills().getXp(Skills.FARMING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29949 && player.getSkills().getXp(Skills.DEFENCE) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29950 && player.getSkills().getXp(Skills.CRAFTING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29951 && player.getSkills().getXp(Skills.COOKING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29952 && player.getSkills().getXp(Skills.AGILITY) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29953 && player.getSkills().getXp(Skills.ATTACK) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29963 && player.getSkills().getXp(Skills.CONSTRUCTION) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29965 && player.getSkills().getXp(Skills.SUMMONING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29966 && player.getSkills().getXp(Skills.HUNTER) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		
		if(item.getId() >= 20769 && item.getId() <= 20772) {
			if (!player.hasRequirements()) {
				player.getPackets().sendGameMessage("You do not meet all the requirements, for a list of them use ::requirements.");
			return true;
			}
		}
		if(item.getId() == 20767 || item.getId() == 20768) {
			if (player.getSkills().getTotalLevel() < 2496) {
			player.getPackets().sendGameMessage("You must have achieved the highest level in every skill to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20754) {
			if (player.getSkills().getTotalLevel() < 260) {
			player.getPackets().sendGameMessage("You must have a total level of 260 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20755) {
			if (player.getSkills().getTotalLevel() < 520) {
			player.getPackets().sendGameMessage("You must have a total level of 520 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20756) {
			if (player.getSkills().getTotalLevel() < 780) {
			player.getPackets().sendGameMessage("You must have a total level of 780 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20757) {
			if (player.getSkills().getTotalLevel() < 1040) {
			player.getPackets().sendGameMessage("You must have a total level of 1040 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20758) {
			if (player.getSkills().getTotalLevel() < 1300) {
			player.getPackets().sendGameMessage("You must have a total level of 1300 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20759) {
			if (player.getSkills().getTotalLevel() < 1560) {
			player.getPackets().sendGameMessage("You must have a total level of 1560 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20760) {
			if (player.getSkills().getTotalLevel() < 1820) {
			player.getPackets().sendGameMessage("You must have a total level of 1820 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20761) {
			if (player.getSkills().getTotalLevel() < 2080) {
			player.getPackets().sendGameMessage("You must have a total level of 2080 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20762) {
			if (player.getSkills().getTotalLevel() < 2340) {
			player.getPackets().sendGameMessage("You must have a total level of 2340 to wear this item.");
			return true;
			}
		}
		if (item.getDefinitions().isNoted()
				|| !item.getDefinitions().isWearItem(player.getAppearence().isMale())) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return true;
		}
		for (String strings : Settings.DONATOR_ITEMS) {
			if (itemName.contains(strings) && !player.isDonator()) {
				player.getPackets().sendGameMessage(
						"You need to be a donator to equip " + itemName + ".");
				return true;
			}
		}
		for (String strings : Settings.EXTREME_DONATOR_ITEMS) {
			if (itemName.contains(strings) && !player.isExtremeDonator()) {
				player.getPackets().sendGameMessage(
						"You need to be a extreme donator to equip " + itemName + ".");
				return true;
			}
		}

		/*for (String strings : Settings.VOTE_REQUIRED_ITEMS) {
			if (itemName.toLowerCase().contains(strings) && !player.hasVoted()) {
				player.getPackets().sendGameMessage("You need to vote to wear the prod item "+itemName+" for 24 hours, type ::vote to vote.");
				return true;
			}
		}*/
		int targetSlot = Equipment.getItemSlot(itemId);
		if (targetSlot == -1) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return true;
		}
		System.out.println("PAST THIS");
		if(!ItemConstants.canWear(item, player))
			return true;
		boolean isTwoHandedWeapon = targetSlot == 3
				&& Equipment.isTwoHandedWeapon(item);
		if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots()
				&& player.getEquipment().hasShield()) {
			player.getPackets().sendGameMessage(
					"Not enough free space in your inventory.");
			return true;
		}
		HashMap<Integer, Integer> requiriments = item.getDefinitions()
				.getWearingSkillRequiriments();
		boolean hasRequiriments = true;
		if (requiriments != null) {
			for (int skillId : requiriments.keySet()) {
				if (skillId > 24 || skillId < 0)
					continue;
				int level = requiriments.get(skillId);
				if (level < 0 || level > 120)
					continue;
				if (player.getSkills().getLevelForXp(skillId) < level) {
					if (hasRequiriments) {
						player.getPackets()
						.sendGameMessage(
								"You are not high enough level to use this item.");
					}
					hasRequiriments = false;
					String name = Skills.SKILL_NAME[skillId].toLowerCase();
					player.getPackets().sendGameMessage(
							"You need to have a"
									+ (name.startsWith("a") ? "n" : "") + " "
									+ name + " level of " + level + ".");
				}

			}
		}
		if (!hasRequiriments)
			return true;
		if (!player.getControlerManager().canEquip(targetSlot, itemId))
			return false;
		player.stopAll(false, false);
		player.getInventory().deleteItem(slotId, item);
		if (targetSlot == 3) {
			if (isTwoHandedWeapon && player.getEquipment().getItem(5) != null) {
				if (!player.getInventory().addItem(
						player.getEquipment().getItem(5).getId(),
						player.getEquipment().getItem(5).getAmount())) {
					player.getInventory().getItems().set(slotId, item);
					player.getInventory().refresh(slotId);
					return true;
				}
				player.getEquipment().getItems().set(5, null);
			}
		} else if (targetSlot == 5) {
			if (player.getEquipment().getItem(3) != null
					&& Equipment.isTwoHandedWeapon(player.getEquipment()
							.getItem(3))) {
				if (!player.getInventory().addItem(
						player.getEquipment().getItem(3).getId(),
						player.getEquipment().getItem(3).getAmount())) {
					player.getInventory().getItems().set(slotId, item);
					player.getInventory().refresh(slotId);
					return true;
				}
				player.getEquipment().getItems().set(3, null);
			}

		}
		if (player.getEquipment().getItem(targetSlot) != null
				&& (itemId != player.getEquipment().getItem(targetSlot).getId() || !item
				.getDefinitions().isStackable())) {
			if (player.getInventory().getItems().get(slotId) == null) {
				player.getInventory()
				.getItems()
				.set(slotId,
						new Item(player.getEquipment()
								.getItem(targetSlot).getId(), player
								.getEquipment().getItem(targetSlot)
								.getAmount()));
				player.getInventory().refresh(slotId);
			} else
				player.getInventory().addItem(
						new Item(player.getEquipment().getItem(targetSlot)
								.getId(), player.getEquipment()
								.getItem(targetSlot).getAmount()));
			player.getEquipment().getItems().set(targetSlot, null);
		}
		if(targetSlot == Equipment.SLOT_AURA)
			player.getAuraManager().removeAura();
		int oldAmt = 0;
		if (player.getEquipment().getItem(targetSlot) != null) {
			oldAmt = player.getEquipment().getItem(targetSlot).getAmount();
		}
		Item item2 = new Item(itemId, oldAmt + item.getAmount());
		player.getEquipment().getItems().set(targetSlot, item2);
		player.getEquipment().refresh(targetSlot,
				targetSlot == 3 ? 5 : targetSlot == 3 ? 0 : 3);
		player.getAppearence().generateAppearenceData();
		player.getPackets().sendSound(2240, 0, 1);
		if (targetSlot == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
		player.getCharges().wear(targetSlot);
		return true;
	}

	public static boolean sendWear2(Player player, int slotId, int itemId) {
		if (player.hasFinished() || player.isDead())
			return false;
		player.stopAll(false, false);
		Item item = player.getInventory().getItem(slotId);
		if (item == null || item.getId() != itemId)
			return false;
		if((itemId == 4565 || itemId == 4084) && player.getRights() != 2) {
			player.getPackets().sendGameMessage("You've to be a administrator to wear this item.");
			return true;
		}
		if(item.getId() == 9813 || item.getId() == 9814) {
			if (player.questPoints < 17) {
			player.getPackets().sendGameMessage("You must have a total of 17 quest points to wear this item.");
			return true;
			}
		}
		if(item.getId() >= 20769 && item.getId() <= 20772) {
			if (!player.hasRequirements()) {
				player.getPackets().sendGameMessage("You do not meet all the requirements, for a list of them use ::requirements.");
			return true;
			}
		}
		if(item.getId() == 20767 || item.getId() == 20768) {
			if (player.getSkills().getTotalLevel() < 2496) {
			player.getPackets().sendGameMessage("You must have achieved the highest level in every skill to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20754) {
			if (player.getSkills().getTotalLevel() < 260) {
			player.getPackets().sendGameMessage("You must have a total level of 260 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20755) {
			if (player.getSkills().getTotalLevel() < 520) {
			player.getPackets().sendGameMessage("You must have a total level of 520 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20756) {
			if (player.getSkills().getTotalLevel() < 780) {
			player.getPackets().sendGameMessage("You must have a total level of 780 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20757) {
			if (player.getSkills().getTotalLevel() < 1040) {
			player.getPackets().sendGameMessage("You must have a total level of 1040 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20758) {
			if (player.getSkills().getTotalLevel() < 1300) {
			player.getPackets().sendGameMessage("You must have a total level of 1300 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20759) {
			if (player.getSkills().getTotalLevel() < 1560) {
			player.getPackets().sendGameMessage("You must have a total level of 1560 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20760) {
			if (player.getSkills().getTotalLevel() < 1820) {
			player.getPackets().sendGameMessage("You must have a total level of 1820 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20761) {
			if (player.getSkills().getTotalLevel() < 2080) {
			player.getPackets().sendGameMessage("You must have a total level of 2080 to wear this item.");
			return true;
			}
		}
		if(item.getId() == 20762) {
			if (player.getSkills().getTotalLevel() < 2340) {
			player.getPackets().sendGameMessage("You must have a total level of 2340 to wear this item.");
			return true;
			}
		}
		
		
		if(item.getId() == 29933 && player.getSkills().getXp(Skills.WOODCUTTING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29934 && player.getSkills().getXp(Skills.THIEVING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29935 && player.getSkills().getXp(Skills.STRENGTH) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29936 && player.getSkills().getXp(Skills.SMITHING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29937 && player.getSkills().getXp(Skills.SLAYER) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29938 && player.getSkills().getXp(Skills.RUNECRAFTING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29939 && player.getSkills().getXp(Skills.RANGE) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29940 && player.getSkills().getXp(Skills.PRAYER) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29941 && player.getSkills().getXp(Skills.MINING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29942 && player.getSkills().getXp(Skills.MAGIC) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29943 && player.getSkills().getXp(Skills.HITPOINTS) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29944 && player.getSkills().getXp(Skills.HERBLORE) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29945 && player.getSkills().getXp(Skills.FLETCHING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29946 && player.getSkills().getXp(Skills.FISHING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29947 && player.getSkills().getXp(Skills.FIREMAKING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29948 && player.getSkills().getXp(Skills.FARMING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29949 && player.getSkills().getXp(Skills.DEFENCE) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29950 && player.getSkills().getXp(Skills.CRAFTING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29951 && player.getSkills().getXp(Skills.COOKING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29952 && player.getSkills().getXp(Skills.AGILITY) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29953 && player.getSkills().getXp(Skills.ATTACK) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29963 && player.getSkills().getXp(Skills.CONSTRUCTION) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29965 && player.getSkills().getXp(Skills.SUMMONING) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		if(item.getId() == 29966 && player.getSkills().getXp(Skills.HUNTER) < 104273167){
			player.getPackets().sendGameMessage("You need over 104,273,167 xp to use this item.");
			return true;
		}
		
		if (player.isPker) {
			if (item.getName().contains("offhand") || item.getName().contains("off") || item.getName().contains("hand") || item.getName().contains("off-hand") || item.getName().contains("drygore") || item.getName().contains("deathtouched") || item.getName().contains("perfect")) {
				player.getPackets().sendGameMessage("Pkers are not allowed to use this item.");
				return true;
			}
		}
		if (item.getDefinitions().isNoted()
				|| !item.getDefinitions().isWearItem(player.getAppearence().isMale()) && itemId != 4084) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return false;
		}
		String itemName = item.getDefinitions() == null ? "" : item
				.getDefinitions().getName().toLowerCase();
		for (String strings : Settings.DONATOR_ITEMS) {
			if (itemName.contains(strings) && !player.isDonator()) {
				player.getPackets().sendGameMessage(
						"You need to be a donator to equip " + itemName + ".");
				return false;
			}
		}
		for (String strings : Settings.EXTREME_DONATOR_ITEMS) {
			if (itemName.contains(strings) && !player.isExtremeDonator()) {
				player.getPackets().sendGameMessage(
						"You need to be a extreme donator to equip " + itemName + ".");
				return true;
			}
		}

		/*for (String strings : Settings.VOTE_REQUIRED_ITEMS) {
			if (itemName.toLowerCase().contains(strings) && !player.hasVoted()) {
				player.getPackets().sendGameMessage("You need to vote to wear the prod item "+itemName+" for 24 hours, type ::vote to vote.");
				return true;
			}
		}*/
		int targetSlot = Equipment.getItemSlot(itemId);
		if(itemId == 4084)
			targetSlot = 3;
		if (targetSlot == -1) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return false;
		}
		if(!ItemConstants.canWear(item, player))
			return false;
		boolean isTwoHandedWeapon = targetSlot == 3
				&& Equipment.isTwoHandedWeapon(item);
		if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots()
				&& player.getEquipment().hasShield()) {
			player.getPackets().sendGameMessage(
					"Not enough free space in your inventory.");
			return false;
		}
		HashMap<Integer, Integer> requiriments = item.getDefinitions()
				.getWearingSkillRequiriments();
		boolean hasRequiriments = true;
		if(item.getId() >= 29933 && item.getId() <= 29953 || item.getId() >=29963 && item.getId() <=29966){
		}else{
		if (requiriments != null) {
			for (int skillId : requiriments.keySet()) {
				if (skillId > 24 || skillId < 0)
					continue;
				int level = requiriments.get(skillId);
				if (level < 0 || level > 120)
					continue;
				if (player.getSkills().getLevelForXp(skillId) < level) {
					if (hasRequiriments)
						player.getPackets()
						.sendGameMessage(
								"You are not high enough level to use this item.");
					hasRequiriments = false;
					String name = Skills.SKILL_NAME[skillId].toLowerCase();
					player.getPackets().sendGameMessage(
							"You need to have a"
									+ (name.startsWith("a") ? "n" : "") + " "
									+ name + " level of " + level + ".");
				}

			}
		}
		}
		if (!hasRequiriments)
			return false;
		if (!player.getControlerManager().canEquip(targetSlot, itemId))
			return false;
		player.getInventory().getItems().remove(slotId, item);
		if (targetSlot == 3) {
			if (isTwoHandedWeapon && player.getEquipment().getItem(5) != null) {
				if (!player.getInventory().getItems()
						.add(player.getEquipment().getItem(5))) {
					player.getInventory().getItems().set(slotId, item);
					return false;
				}
				player.getEquipment().getItems().set(5, null);
			}
		} else if (targetSlot == 5) {
			if (player.getEquipment().getItem(3) != null
					&& Equipment.isTwoHandedWeapon(player.getEquipment()
							.getItem(3))) {
				if (!player.getInventory().getItems()
						.add(player.getEquipment().getItem(3))) {
					player.getInventory().getItems().set(slotId, item);
					return false;
				}
				player.getEquipment().getItems().set(3, null);
			}

		}
		if (player.getEquipment().getItem(targetSlot) != null
				&& (itemId != player.getEquipment().getItem(targetSlot).getId() || !item
				.getDefinitions().isStackable())) {
			if (player.getInventory().getItems().get(slotId) == null) {
				player.getInventory()
				.getItems()
				.set(slotId,
						new Item(player.getEquipment()
								.getItem(targetSlot).getId(), player
								.getEquipment().getItem(targetSlot)
								.getAmount()));
			} else
				player.getInventory()
				.getItems()
				.add(new Item(player.getEquipment().getItem(targetSlot)
						.getId(), player.getEquipment()
						.getItem(targetSlot).getAmount()));
			player.getEquipment().getItems().set(targetSlot, null);
		}
		if(targetSlot == Equipment.SLOT_AURA)
			player.getAuraManager().removeAura();
		int oldAmt = 0;
		if (player.getEquipment().getItem(targetSlot) != null) {
			oldAmt = player.getEquipment().getItem(targetSlot).getAmount();
		}
		Item item2 = new Item(itemId, oldAmt + item.getAmount());
		player.getEquipment().getItems().set(targetSlot, item2);
		player.getEquipment().refresh(targetSlot,
				targetSlot == 3 ? 5 : targetSlot == 3 ? 0 : 3);
		if (targetSlot == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
		player.getCharges().wear(targetSlot);
		return true;
	}

	public static void submitSpecialRequest(final Player player) {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							player.getCombatDefinitions().switchUsingSpecialAttack();
						}
					}, 0);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 200);
	}

	public static void sendWear(Player player, int[] slotIds) {
		if (player.hasFinished() || player.isDead())
			return;
		boolean worn = false;
		Item[] copy = player.getInventory().getItems().getItemsCopy();
		for (int slotId : slotIds) {
			Item item = player.getInventory().getItem(slotId);
			if (item == null)
				continue;
			if (sendWear2(player, slotId, item.getId()))
				worn = true;
		}
		player.getInventory().refreshItems(copy);
		if (worn) {
			player.getAppearence().generateAppearenceData();
			player.getPackets().sendSound(2240, 0, 1);
		}
	}

    public static void openEquipmentBonuses(final Player player, boolean banking) {
	player.stopAll();
	player.getInterfaceManager().sendInventoryInterface(670);
	player.getInterfaceManager().sendInterface(667);
	player.getVarsManager().sendVarBit(4894, banking ? 1 : 0);
	player.getPackets().sendRunScript(787, 1);
	player.getPackets().sendItems(93, player.getInventory().getItems());
	player.getPackets().sendInterSetItemsOptionsScript(670, 0, 93, 4, 7, "Equip", "Compare", "Stats", "Examine");
	player.getPackets().sendUnlockIComponentOptionSlots(670, 0, 0, 27, 0, 1, 2, 3);
	player.getPackets().sendIComponentSettings(667, 14, 0, 13, 1030);
	refreshEquipBonuses(player);
	if (banking) {
	    player.getTemporaryAttributtes().put("Banking", Boolean.TRUE);
	    player.setCloseInterfacesEvent(new Runnable() {
		@Override
		public void run() {
		    player.getTemporaryAttributtes().remove("Banking");
		    player.getVarsManager().sendVarBit(4894, 0);
		}
	    });
	}
    }
	
	
	
    public static void openItemsKeptOnDeath(Player player) {
	player.getInterfaceManager().sendInterface(17);
	sendItemsKeptOnDeath(player, false);
    }

    public static void sendItemsKeptOnDeath(Player player, boolean wilderness) {
	boolean skulled = player.hasSkull();
	Integer[][] slots = GraveStone.getItemSlotsKeptOnDeath(player, wilderness, skulled, player.getPrayer().isProtectingItem());
	Item[][] items = GraveStone.getItemsKeptOnDeath(player, slots);
	long riskedWealth = 0;
	long carriedWealth = 0;
	for (Item item : items[1])
	    carriedWealth = riskedWealth += item.getDefinitions().getTipitPrice() * item.getAmount();
	for (Item item : items[0])
	    carriedWealth += item.getDefinitions().getTipitPrice() * item.getAmount();
	if (slots[0].length > 0) {
	    for (int i = 0; i < slots[0].length; i++)
		player.getVarsManager().sendVarBit(9222 + i, slots[0][i]);
	    player.getVarsManager().sendVarBit(9227, slots[0].length);
	} else {
	    player.getVarsManager().sendVarBit(9222, -1);
	    player.getVarsManager().sendVarBit(9227, 1);
	}
	player.getVarsManager().sendVarBit(9226, wilderness ? 1 : 0);
	player.getVarsManager().sendVarBit(9229, skulled ? 1 : 0);
	StringBuffer text = new StringBuffer();
	text.append("The number of items kept on").append("<br>").append("death is normally 3.").append("<br>").append("<br>").append("<br>");
	if (wilderness) {
	    text.append("Your gravestone will not").append("<br>").append("appear.");
	} else {
	    int time = GraveStone.getMaximumTicks(player.getGraveStone());
	    int seconds = (int) (time * 0.6);
	    int minutes = seconds / 60;
	    seconds -= minutes * 60;

	    text.append("Gravestone:").append("<br>").append(ClientScriptMap.getMap(1099).getStringValue(player.getGraveStone())).append("<br>").append("<br>").append("Initial duration:").append("<br>").append(minutes + ":" + (seconds < 10 ? "0" : "") + seconds).append("<br>");
	}
	text.append("<br>").append("<br>").append("Carried wealth:").append("<br>").append(carriedWealth > Integer.MAX_VALUE ? "Too high!" : Utils.getFormattedNumber((int) carriedWealth)).append("<br>").append("<br>").append("Risked wealth:").append("<br>").append(riskedWealth > Integer.MAX_VALUE ? "Too high!" : Utils.getFormattedNumber((int) riskedWealth)).append("<br>").append("<br>");
	if (wilderness) {
	    text.append("Your hub will be set to:").append("<br>").append("Edgeville.");
	} else {
	    text.append("Current hub: " + ClientScriptMap.getMap(3792).getStringValue(DeathEvent.getCurrentHub(player)));
	}
	player.getPackets().sendGlobalString(352, text.toString());
    }

	public static void refreshEquipBonuses(Player player) {
		int strBonus = player.getCombatDefinitions().getBonuses()[14];
		if (strBonus >= 205)
			strBonus = 205;
		player.getPackets().sendIComponentText(667, 28,
				"Stab: +" + player.getCombatDefinitions().getBonuses()[0]);
		player.getPackets().sendIComponentText(667, 29,
				"Slash: +" + player.getCombatDefinitions().getBonuses()[1]);
		player.getPackets().sendIComponentText(667, 30,
				"Crush: +" + player.getCombatDefinitions().getBonuses()[2]);
		player.getPackets().sendIComponentText(667, 31,
				"Magic: +" + player.getCombatDefinitions().getBonuses()[3]);
		player.getPackets().sendIComponentText(667, 32,
				"Range: +" + player.getCombatDefinitions().getBonuses()[4]);
		player.getPackets().sendIComponentText(667, 33,
				"Stab: +" + player.getCombatDefinitions().getBonuses()[5]);
		player.getPackets().sendIComponentText(667, 34,
				"Slash: +" + player.getCombatDefinitions().getBonuses()[6]);
		player.getPackets().sendIComponentText(667, 35,
				"Crush: +" + player.getCombatDefinitions().getBonuses()[7]);
		player.getPackets().sendIComponentText(667, 36,
				"Magic: +" + player.getCombatDefinitions().getBonuses()[8]);
		player.getPackets().sendIComponentText(667, 37,
				"Range: +" + player.getCombatDefinitions().getBonuses()[9]);
		player.getPackets().sendIComponentText(667, 38,
				"Summoning: +" + player.getCombatDefinitions().getBonuses()[10]);
		player.getPackets().sendIComponentText(667, 39, 
				"Absorb Melee: +" + player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MELEE_BONUS] + "%");
		player.getPackets().sendIComponentText(667, 40,
				"Absorb Magic: +" + player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MAGE_BONUS] + "%");
		player.getPackets().sendIComponentText(667, 41,
				"Absorb Ranged: +" + player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_RANGE_BONUS]+ "%");
		player.getPackets().sendIComponentText(667, 42,
				"Strength: " + strBonus);
		player.getPackets().sendIComponentText(667, 43,
				"Ranged Str: " + player.getCombatDefinitions().getBonuses()[15]);
		player.getPackets().sendIComponentText(667, 44,
				"Prayer: +" + player.getCombatDefinitions().getBonuses()[16]);
		player.getPackets().sendIComponentText(667,45,"Magic Damage: +" + player.getCombatDefinitions().getBonuses()[17] + "%");
	}

	public static void sendMissionBoard(Player player) {
		// TODO Auto-generated method stub
		
	}
}
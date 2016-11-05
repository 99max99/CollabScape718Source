package com.rs.game.player.content;

import java.util.Random;

import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;

/*
 * @Author Danny
 */

public class PenguinEvent {
	
	/*
	 * Penguin Ids
	 */
	
	public static int BARREL = 8104;
	public static int BUSH = 8105;
	public static int CACTUS = 8107;
	public static int CRATE = 8108;
	public static int ROCK = 8109;
	public static int TOADSTOOL = 8110;
	public static int SNOWMAN = 14766;
	public static int PUMPKIN = 14415;
	
	public static String current;
	
	public enum Penguins {
		DIGSITE(new WorldTile(3338, 3433, 0), "Maybe this is a good enough fossil for the museum...", BUSH), 
		DUELBANK(new WorldTile(3380, 3272, 0), "On such a losing streak I need to get more items.", CRATE),
		DUELENTRANCE(new WorldTile(3315, 3227, 0), "Maybe I can make bank here.", CACTUS),
		MORTTON(new WorldTile(3518, 3264, 0), "This place looks abandoned. Who else would want to live near those brothers?", BARREL),
		ABANDONEDMINE(new WorldTile(2783, 4494, 0), "This place is abandoned, maybe I should find something to light my way?.", TOADSTOOL),
		RODIKSZONE(new WorldTile(4753, 4655, 0), "Shhhh! Secret Zone.", BARREL),
		OOGLOGPORTALS(new WorldTile(2594, 2855, 0), "Maybe around home somewhere..", BARREL),
		RIMMINGTONMINING(new WorldTile(2975, 3248, 0), "This penguin can be found at home.", ROCK),
		LUMBRIDGEEVENT(new WorldTile(3240, 3180, 0), "Woah! That ghost came out of nowhere.", PUMPKIN),
		WHITEWOLFMOUNTAINGLIDER(new WorldTile(2852, 3495, 0), "I can see Catherby from here!", SNOWMAN),
		CHAOSALTER(new WorldTile(3247, 3609, 0), "Let me pray here that I don't get PK'd.", ROCK),
		OOGLOGPIER(new WorldTile(2623, 2856, 0), "Platypus Holes right over there.", BARREL),
		OOGLOGMININGROCK(new WorldTile(2585, 2878, 0), "Can be found surrounding home.", BARREL),
		CLOCKTOWER(new WorldTile(2563, 3244, 0), "Only time will tell if I can find this penguin.", CRATE),
		NORTHBATTLEFIELD(new WorldTile(2533, 3256, 0), "A battle rages near this penguin", BARREL),
		GNOMEMAZE(new WorldTile(2535, 3162, 1), "It's easy to get lost in this maze. It's harder to find this penguin.", BARREL),
		LUMBYKITCHENBASEMENT(new WorldTile(3219, 9615, 0), "Maybe I can find some supplies to assist this chef", BARREL),
		WARRIORSCAMP(new WorldTile(3154, 3204, 0), "This penguin wants to be like the mighty warriors here.", BARREL),
		NARDA(new WorldTile(3431, 2898, 0), "Why would this penguin travel all the way to this city on the desert?", CACTUS),
		ROCKCRABSWEST(new WorldTile(2670, 3728, 0), "This penguin wants to train like all the other noobs", ROCK),
		LUMBERYARD(new WorldTile(3310, 3499, 0), "Gertrude would be happy I found this cat.", CRATE),
		HAMLAIR(new WorldTile(3176, 9655, 0), "How in gods name did that penguin pick that lock?", CRATE),
		DRAYNORMANOR(new WorldTile(3117, 3357, 1), "This place gives me the creeps..", PUMPKIN),
		CLANCAMP(new WorldTile(2957, 3291, 0), "This penguin must be fascinated with the big spinning ball.", BARREL),
		ZANARIS(new WorldTile(2402, 4445, 0), "This place is so colorful.", TOADSTOOL),
		WESTARDOUGNE(new WorldTile(2521, 3304, 0), "The gallows must be for sick people.", BARREL),
		GOLDENAPPLETREE(new WorldTile(2758, 3608, 0), "What?! Golden Sheep?!.", BUSH),
		FALLYPARTYROOM(new WorldTile(3046, 3373, 2), "PAAAAAAAAAARRRTTTTTTYYYYYY!!!.", SNOWMAN),
		WILLFRED(new WorldTile(2945, 3436, 0), "Wilfred, those stairs look intimidating.", BARREL),
		CAMELOTCASTLE(new WorldTile(2765, 3498, 1), "There's a lot of Sir's in this castle.", CRATE),
		POTATOE(new WorldTile(3147, 3278, 0), "Wait..that's not a potatoe!", BUSH),
		LLEYTA(new WorldTile(2332, 3178, 0), "Aren't those elves or something?", ROCK),
		CASTLEWARS(new WorldTile(2447, 3096, 0), "Zamorak vs. Saradomin!", BARREL),
		OGRESPITROAST(new WorldTile(2324, 3062, 0), "mmmm that spit roast looks tasty.", BARREL),
		WITCHHAVEN(new WorldTile(2718, 3272, 0), "I don't think the witches would like me here...", BARREL),
		SHILOVILLAGE(new WorldTile(2855, 2946, 0), "This isn't home anymore..", BUSH),
		ABBEY(new WorldTile(3382, 3156, 0), "Those are pretty big monsters near the abbey.", CACTUS),
		EXAMCENTRE(new WorldTile(3341, 3336, 0), "Hope I can pass the test!", BUSH),
		SABREKYATTS(new WorldTile(2740, 3783, 0), "wouldn't want to bother these sabre kyatt's.", SNOWMAN),
		RUINS(new WorldTile(2971, 3701, 0), "This place was left to ruin.", ROCK),
		NEXENTRANCE(new WorldTile(2864, 5219, 0), "How did that penguin get a frozen key?", SNOWMAN),
		OBSERVATORY_BRIDGE(new WorldTile(2459, 3178, 0), "Maybe I can use this bridge to get to the Obser- Oh...Nevermind.", ROCK),
		SWAMPTOADS(new WorldTile(2417, 3514, 0), "Maybe I can get rich picking up these toads..", TOADSTOOL),
		FORGOTTENCEMETARY(new WorldTile(2980, 3764, 0), "I guess this cemetary wasn't so forgotten.", ROCK),
		SOULWARS(new WorldTile(1898, 3177, 0), "Battle for your soul.", BARREL),
		MACGRUBERSWOOD(new WorldTile(2638, 3487, 0), "Maybe MacGruber can point me in the right direction.", BUSH),
		REDPORTAL(new WorldTile(2984, 5520, 0), "This portal leads to where people go to PK.", ROCK),
		FIGHTARENA(new WorldTile(2567, 3192, 0), "We must fight this evil amongst the other slave fighters.", ROCK),
		YANILLE(new WorldTile(2544, 3106, 0), "House Party!", BUSH);
	
		private WorldTile tile;
		private String hint;
		private int id;
	
		private Penguins(WorldTile tile, String hint, int id) {
			this.tile = tile;
			this.hint = hint;
			this.id = id;
		}
		
		public WorldTile getTile() {
			return tile;
		}
		
		public int getId() {
			return id;
		}
		
		public String getHint() {
			return hint;
		}
		
		
	}
	
	public static boolean isPenguin(int id) {
		switch (id) {
		case 8104:
		case 8105:
		case 8107:
		case 8108:
		case 8109:
		case 8110:
		case 14766:
		case 14415:
			return true;
		default:
		return false;
		}
	}
	
	
	public static void spotPenguin(Player player, NPC npc) {
		if (!player.penguin) {
			player.penguin = true;
			player.penguinpoints++;
			player.penguins++;
			player.faceEntity(npc);
			player.setNextAnimation(new Animation(10355));
			player.sm("You successfully spot the penguin and have been rewarded a penguin point.");
			player.sm("You now have "+player.penguinpoints+" penguin points.");
		} else {
			player.sm("You have already spotted a penguin, please wait till the next event.");
		}
			return;
	}
	
	public static void startEvent() {
	    int pick = new Random().nextInt(Penguins.values().length);
		Penguins penguin = Penguins.values()[pick];
		current = penguin.getHint();
		World.sendWorldMessage("<img=7><col=ff0000>[Penguin Event] A penguin has been spawned, find it to earn a reward!", false);
		World.sendWorldMessage("<img=7><col=ff0000>[Penguin Event] Hint: "+penguin.getHint()+"", false);
		World.spawnNPC(penguin.getId(), penguin.getTile(), -1, true, true);
		return;
	}

}

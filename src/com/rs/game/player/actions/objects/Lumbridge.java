package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.quests.CooksAssistant;
import com.rs.utils.Misc;


/*
 * @Author Danny
 * Lumbridge City
 */

public class Lumbridge {
	
	public static void TopLadder(Player player,
			final WorldObject object) {
		if (object.getId() == 36771){
			player.useStairs(828, new WorldTile(3207, 3222, 3), 1, 2);
		} else if (object.getId() == 36772){
			player.useStairs(828, new WorldTile(3207, 3224, 2), 1, 2);	
		}
	}
	public static void Swamp1(Player player,
			final WorldObject object) {
			player.useStairs(828, new WorldTile(2540, 5773, 3), 1, 2);
	}
	public static void Swamp2(Player player,
			final WorldObject object) {
			player.useStairs(828, new WorldTile(3226, 9542, 0), 1, 2);
	}
	public static void OldTutTrapDoor(Player player,
			final WorldObject object) {
		if (object.getId() == 45784){
			player.useStairs(828, new WorldTile(3230, 3240, 0), 1, 2);	
		}
		if (object.getId() == 37683)
			player.useStairs(827, new WorldTile(3680, 4941, 0), 1, 2);	
		
		
	}
	public static void LumbridgeThiefGuild(Player player,
			final WorldObject object) {
		
			player.useStairs(827, new WorldTile(4762, 5763, 0), 1, 2);
		
	}
	public static void LumbridgeThiefGuildExit(Player player,
			final WorldObject object) {
	
			player.useStairs(828, new WorldTile(3223, 3269, 0), 1, 2);	
		
	}
	public static void LumbridgeCellar(Player player,
			final WorldObject object) {
		if (object.getId() == 36687){
			player.useStairs(827, new WorldTile(3208, 9616, 0), 1, 2);
		} else if (object.getId() == 29355){
			player.useStairs(828, new WorldTile(3210, 3216, 0), 1, 2);	
		}
	}
	public static void SqueezeThroughWall(Player player,
			final WorldObject object) {
		if (object.getX() == 3219){
			player.useStairs(844, new WorldTile(3221, 9618, 0), 1, 2);
		} else {
			player.useStairs(844, new WorldTile(3219, 9618, 0), 1, 2);	
		}
	}
	public static void HandleStairs(Player player,
			final WorldObject object) {
		//Up
		if (object.getId() == 45483){
			if (object.getX() == 3230 && object.getY() == 3209){
				player.setNextWorldTile(new WorldTile(3229, 3209, 1));
			} else if (object.getX() == 3230 && object.getY() == 3205){
				player.setNextWorldTile(new WorldTile(3229, 3205, 1));
			} else if (object.getX() == 3232 && object.getY() == 3239){
				player.setNextWorldTile(new WorldTile(3232, 3241, 1));
			} else if (object.getX() == 3186 && object.getY() == 3270){
				player.setNextWorldTile(new WorldTile(3188, 3270, 1));
			} else if (object.getX() == 3225 && object.getY() == 3288){
				player.setNextWorldTile(new WorldTile(3225, 3287, 1));
			}
		}
		if (object.getId() == 45481){
			if (object.getX() == 3237 && object.getY() == 3196){
				player.setNextWorldTile(new WorldTile(3237, 3195, 1));
			} else if (object.getX() == 3215 && object.getY() == 3239){
				player.setNextWorldTile(new WorldTile(3214, 3239, 1));
			} else if (object.getX() == 3200 && object.getY() == 3242){
				player.setNextWorldTile(new WorldTile(3200, 3243, 1));
			} else if (object.getX() == 3193 && object.getY() == 3255){
				player.setNextWorldTile(new WorldTile(3195, 3255, 1));
			} else if (object.getX() == 3212 && object.getY() == 3256){
				player.setNextWorldTile(new WorldTile(3214, 3256, 1));
			} else if (object.getX() == 3225 && object.getY() == 3265){
				player.setNextWorldTile(new WorldTile(3225, 3264, 1));
			}
		}
		//Down
		if (object.getId() == 45484){
			if (object.getX() == 3230 && object.getY() == 3209){
				player.setNextWorldTile(new WorldTile(3232, 3209, 0));
			} else if (object.getX() == 3230 && object.getY() == 3205){
				player.setNextWorldTile(new WorldTile(3232, 3205, 0));
			} else if (object.getX() == 3232 && (object.getY() == 3239)){
				player.setNextWorldTile(new WorldTile(3232, 3238, 0));
			} else if (object.getX() == 3186 && (object.getY() == 3270)){
				player.setNextWorldTile(new WorldTile(3185, 3270, 0));
			} else if (object.getX() == 3225 && object.getY() == 3288){
				player.setNextWorldTile(new WorldTile(3225, 3290, 0));
			}
		}
		if (object.getId() == 45482){
			if (object.getX() == 3237 && (object.getY() == 3196)){
				player.setNextWorldTile(new WorldTile(3237, 3198, 0));
			} else if (object.getX() == 3215 && (object.getY() == 3239)){
				player.setNextWorldTile(new WorldTile(3217, 3239, 0));
			} else if (object.getX() == 3200 && (object.getY() == 3245)){
				player.setNextWorldTile(new WorldTile(3200, 3246, 0));
			} else if (object.getX() == 3193 && (object.getY() == 3255)){
				player.setNextWorldTile(new WorldTile(3192, 3255, 0));
			} else if (object.getX() == 3212 && (object.getY() == 3256)){
				player.setNextWorldTile(new WorldTile(3211, 3256, 0));
			} else if (object.getX() == 3225 && (object.getY() == 3265)){
				player.setNextWorldTile(new WorldTile(3225, 3267, 0));
			}
		}
	}
	public static void LumbridgeFlag(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("It is too windy to raise the flag, we don't want to tear it right?");
	}
	
	public static void flowerBox(Player player,
			final WorldObject object) {
		int random = Misc.random(0, 8);
		int flowers[] = {2460, 2462, 2464, 2466, 2468, 2470, 2472, 2474, 2476};
		player.getPackets().sendGameMessage("You take some flowers from the box.");
		player.getInventory().addItem(flowers[random], 1);
	}
	
	public static void GrainHopper(Player player,
			final WorldObject object) {
		if (!player.getInventory().containsItem(1931, 1)
				&& player.hasGrainInHopper == true) {
			player.getPackets().sendGameMessage("You need a pot to fill the flour with.");
		} else if (player.getInventory().containsItem(1931, 1)
				&& player.hasGrainInHopper == true) {
			player.getPackets().sendGameMessage("You operate the controls and fill your pot with flour.");
			player.getInventory().deleteItem(1931, 1);
			player.getInventory().addItem(1933, 1);
			player.hasGrainInHopper = false;
		} else {
			player.getPackets().sendGameMessage("You see no reason why you need to operate the controls.");
		}
	}
	
	public static void MilkCowQuest(Player player,
			final WorldObject object) {
		CooksAssistant.handleCowMilking(player);
	}
	public static void ChurchOrgan(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("You try to play the organ but you give up because of your lack of musical talent.");
	}
	public static void SearchSack(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("You can't find anything useful in the sack.");
	}
	public static void SearchCrate(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("You can't find anything you'd want to use in this crate.");
	}
	
	public static void RingBell(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("Ring... Ding... Dong... Ring... SCREECH!");
	}
	
	public static void Help(Player player,
			final WorldObject object) {
		player.getInterfaceManager().sendHelp();
	}
	
	public static void TakeHammer(Player player,
			final WorldObject object) {
		player.getInventory().addItem(2347, 1);
		player.getPackets().sendGameMessage("You take a hammer from the crate.");
	}
	
	public static void FindSkull(Player player,
			final WorldObject object) {
		player.getInventory().addItem(553, 1);
		if (player.RG == 4){
		player.RG = 5;
		}
		player.getPackets().sendGameMessage("You find a muddy skull, this should be placed on the Ghost's grave.");
	}
	
	public static void HandleCoffin(Player player,
			final WorldObject object) {
		World.spawnNPC(457, new WorldTile(3250, 3194, 0), -1, true);
		player.getPackets().sendGameMessage("A ghost appears, I should talk to him.");
	}
	
	public static void LockedTrapDoor(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("The trap door appears to be locked.");
	}
	
	public static void useTrap(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("The trap door appears to be locked, I should come back another time.");
	}
	
	public static void decorateCoffin(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("You come to decorate the coffin....");
		World.spawnNPC(4387, new WorldTile(3242, 3178, 0), -1, true);
		player.setNextForceTalk(new ForceTalk("WHAT THE FUCK ITS A GHOST?!?!!"));
	}
	
	public static int emoteId(Player player) {
		int emote = -1;
		int level = player.getSkills().getLevel(8);
		int weaponId = player.getEquipment().getWeaponId();
		if (weaponId != -1) {
			switch (weaponId) {
			case 6739: // dragon axe
				if (level >= 61)
					emote = 2846;
				break;
			case 1359: // rune axe
				if (level >= 41)
					emote = 867;
				break;
			case 1357: // adam axe
				if (level >= 31)
					emote = 869;
				break;
			case 1355: // mit axe
				if (level >= 21)
					emote = 871;
				break;
			case 1361: // black axe
				if (level >= 11)
					emote = 873;
				break;
			case 1353: // steel axe
				if (level >= 6)
					emote = 875;
				break;
			case 1349: // iron axe
				emote = 877;
				break;
			case 1351: // bronze axe
				emote = 879;
				break;
			case 13661: // Inferno adze
				if (level >= 61)
					emote = 10251;
				break;
			}
		}
		if (player.getInventory().containsOneItem(6739)) {
			if (level >= 61)
				emote = 2846;
		}
		if (player.getInventory().containsOneItem(1359)) {
			if (level >= 41)
				emote = 867;
		}
		if (player.getInventory().containsOneItem(1357)) {
			if (level >= 31)
				emote = 869;
		}
		if (player.getInventory().containsOneItem(1355)) {
			if (level >= 21)
				emote = 871;
		}
		if (player.getInventory().containsOneItem(1361)) {
			if (level >= 11)
				emote = 873;
		}
		if (player.getInventory().containsOneItem(1353)) {
			if (level >= 6)
				emote = 875;
		}
		if (player.getInventory().containsOneItem(1349)) {
			emote = 877;
		}
		if (player.getInventory().containsOneItem(1351)) {
			emote = 879;
		}
		if (player.getInventory().containsOneItem(13661)) {
			if (level >= 61)
				emote = 10251;
		}
		return emote;
	}
	
	public static void CutDown(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("You cut down the tree and it falls over.");
		int chopemote = emoteId(player);
		player.setNextAnimation(new Animation(chopemote));
		player.lock(2);
		WorldObject fallenTree = new WorldObject(12146,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());		
		player.faceObject(fallenTree);
		World.spawnObject(fallenTree, true);
		//World.spawnTemporaryObject(fallenTree, 1000000000, true);
	}
	
	public static void createLog(Player player,
			final WorldObject object) {
		int chopemote = emoteId(player);
		player.setNextAnimation(new Animation(chopemote));
		player.lock(2);
		WorldObject canoe = new WorldObject(12147,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());	
		player.faceObject(canoe);
		World.spawnObject(canoe, true);
	}
	
	public static void createDugout(Player player,
			final WorldObject object) {
		int chopemote = emoteId(player);
		player.setNextAnimation(new Animation(chopemote));
		player.lock(2);
		WorldObject canoe = new WorldObject(12148,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());	
		player.faceObject(canoe);
		World.spawnObject(canoe, true);
	}
	
	public static void createStableDugout(Player player,
			final WorldObject object) {
		int chopemote = emoteId(player);
		player.setNextAnimation(new Animation(chopemote));
		player.lock(2);
		WorldObject canoe = new WorldObject(12149,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());	
		player.faceObject(canoe);
		World.spawnObject(canoe, true);
	}
	
	public static void createWaka(Player player,
			final WorldObject object) {
		int chopemote = emoteId(player);
		player.setNextAnimation(new Animation(chopemote));
		player.lock(2);
		WorldObject canoe = new WorldObject(12150,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());	
		player.faceObject(canoe);
		World.spawnObject(canoe, true);
	}
	
	public static void pushLog(Player player,
			final WorldObject object) {
		player.setNextAnimation(new Animation(7321));
		player.lock(2);
		WorldObject canoe = new WorldObject(12151,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());	
		player.faceObject(canoe);
		World.spawnObject(canoe, true);
	}
	
	public static void pushDugout(Player player,
			final WorldObject object) {
		player.setNextAnimation(new Animation(7321));
		player.lock(2);
		WorldObject canoe = new WorldObject(12152,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());	
		player.faceObject(canoe);
		World.spawnObject(canoe, true);
	}
	
	public static void pushStableDugout(Player player,
			final WorldObject object) {
		player.setNextAnimation(new Animation(7321));
		player.lock(2);
		WorldObject canoe = new WorldObject(12153,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());	
		player.faceObject(canoe);
		World.spawnObject(canoe, true);
	}
	
	public static void pushWaka(Player player,
			final WorldObject object) {
		player.setNextAnimation(new Animation(7321));
		player.lock(2);
		WorldObject canoe = new WorldObject(12154,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());	
		player.faceObject(canoe);
		World.spawnObject(canoe, true);
	}
	
	public static void carveCanoe(Player player,
			final WorldObject object) {
	if (player.getSkills().getLevel(Skills.WOODCUTTING) < 12) {
			player.sm("You require a Woodcutting level of 12 to create a log canoe.");
	} else if (player.getSkills().getLevel(Skills.WOODCUTTING) >= 12 && player.getSkills().getLevel(Skills.WOODCUTTING) < 27) {
				createLog(player, object);
				player.sm("You create a log canoe.");
	} else if (player.getSkills().getLevel(Skills.WOODCUTTING) >= 27 && player.getSkills().getLevel(Skills.WOODCUTTING) < 42) {
			Lumbridge.createDugout(player, object);
			player.sm("You create a dugout canoe.");
	} else if (player.getSkills().getLevel(Skills.WOODCUTTING) >= 47 && player.getSkills().getLevel(Skills.WOODCUTTING) < 57) {
		Lumbridge.createStableDugout(player, object);
		player.sm("You create a stable dugout canoe.");
	} else {
			Lumbridge.createWaka(player, object);
			player.sm("You create a waka canoe.");
		}
}
	
	public static void useLog(Player player,
			final WorldObject object) {
		World.deleteObject(new WorldTile(object.getX(),
				object.getY(), object.getPlane()));	
		WorldObject base = new WorldObject(12163,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());	
		World.spawnObject(base, true);
		player.usingLog = true;
		player.usingWaka = false;
		player.usingDugout = false;
		player.usingStableDugout = false;
		player.getInterfaceManager().sendInterface(53);
	}
	
	public static void useDugout(Player player,
			final WorldObject object) {
		World.deleteObject(new WorldTile(object.getX(),
				object.getY(), object.getPlane()));	
		WorldObject base = new WorldObject(12163,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());	
		World.spawnObject(base, true);
		player.usingDugout = true;
		player.usingLog = false;
		player.usingWaka = false;
		player.usingStableDugout = false;
		player.getInterfaceManager().sendInterface(53);
	}
	
	public static void useStableDugout(Player player,
			final WorldObject object) {
		World.deleteObject(new WorldTile(object.getX(),
				object.getY(), object.getPlane()));	
		WorldObject base = new WorldObject(12163,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());	
		World.spawnObject(base, true);
		player.usingStableDugout = true;
		player.usingLog = false;
		player.usingDugout = false;
		player.usingWaka = false;
		player.getInterfaceManager().sendInterface(53);
	}
	
	public static void useWaka(Player player,
			final WorldObject object) {
		World.deleteObject(new WorldTile(object.getX(),
				object.getY(), object.getPlane()));	
		WorldObject base = new WorldObject(12163,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());	
		World.spawnObject(base, true);
		player.usingWaka = true;
		player.usingLog = false;
		player.usingDugout = false;
		player.usingStableDugout = false;
		player.getInterfaceManager().sendInterface(53);
	}
	
	public static void HandleLadders(Player player,
			final WorldObject object) {
		//Up
		if (object.getId() == 36986){
			player.useStairs(828, new WorldTile(3246, 3213, 1), 1, 2);	
		}
		if (object.getId() == 36984){
			player.useStairs(828, new WorldTile(3240, 3213, 1), 1, 2);	
		}
		if (object.getId() == 36988){
			player.useStairs(828, new WorldTile((player.getX() - 1), (player.getY() - 1), (player.getPlane() + 1)), 1, 2);	
		}
		if (object.getId() == 36989){
			player.useStairs(828, new WorldTile((player.getX() + 1), (player.getY() - 1), (player.getPlane() + 1)), 1, 2);	
		}
		if (object.getId() == 36795){
			player.useStairs(828, new WorldTile(3165, 3307, 1), 1, 2);	
		}
		if (object.getId() == 5493){
			player.useStairs(828, new WorldTile(3165, 3251, 0), 1, 2);	
		}
		//Down
		if (object.getId() == 36987){
			player.useStairs(828, new WorldTile(3245, 3213, 0), 1, 2);	
		}
		if (object.getId() == 36985){
			player.useStairs(828, new WorldTile(3242, 3213, 0), 1, 2);	
		}
		if (object.getId() == 36990){
			player.useStairs(828, new WorldTile((player.getX() + 1), (player.getY() + 1), (player.getPlane() - 1)), 1, 2);	
		}
		if (object.getId() == 36991){
			player.useStairs(828, new WorldTile((player.getX() - 1), (player.getY() + 1), (player.getPlane() - 1)), 1, 2);	
		}
	}
	public static void LumbridgeSwampDungeonEntrance(Player player,
			final WorldObject object) {
			player.setNextWorldTile(new WorldTile(3168, 9572, 0));
	
	}
	public static void LumbridgeSwampDungeonExit(Player player,
			final WorldObject object) {
			player.useStairs(828, new WorldTile(3169, 3171, 0), 1, 2);	
	
	}
	public static void CandleBox(Player player,
			final WorldObject object) {
		player.getInventory().addItem(36, 1);
	}
	public static void CabbageBox(Player player,
			final WorldObject object) {
		player.getInventory().addItem(1965, 1);
	}
	public static void grapple1(Player player,
			final WorldObject object) {
		if (player.withinDistance(new WorldTile(object), 10)) {
		if (player.getEquipment().getWeaponId() == 9181 && player.getInventory().containsItem(9418, 1)) {
			player.getPackets().sendGameMessage("You grapple across to the other side.");
			player.setNextWorldTile(new WorldTile(3255, 3181, 0));
		} else {
			player.getPackets().sendGameMessage("You must be wielding a mithril crossbow and have a grapple in your inventory.");
		}
		} else {
			player.getPackets().sendGameMessage("You are too far away from the object.");
		}
	}
	public static void grapple2(Player player,
			final WorldObject object) {
		if (player.withinDistance(new WorldTile(object), 10)) {
		if (player.getEquipment().getWeaponId() == 9181 && player.getInventory().containsItem(9418, 1)) {
			player.getPackets().sendGameMessage("You grapple across to the other side.");
			player.setNextWorldTile(new WorldTile(3248, 3180, 0));
		} else {
			player.getPackets().sendGameMessage("You must be wielding a mithril crossbow and have a grapple in your inventory.");
		}
		} else {
			player.getPackets().sendGameMessage("You are too far away from the object.");
		}
	}
	
	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 13622: 
		case 36771:
		case 36772:
		case 37335:
		case 36687:
		case 29355:
		case 6898:
		case 45483:
		case 45481:
		case 36978:
		case 36984:
		case 36985:
		case 6986:
		case 36987:
		case 36988:
		case 36989:
		case 36990:
		case 36991:
		case 36795:
		case 52309:
		case 52308:
		case 32049:
		case 31139:
		case 24202:
		case 37683:
		case 45784:
		case 45482:
		case 45484:
		case 47721:
		case 2718:
		case 36976:
		case 31299:
		case 15468:
		case 36986:
		case 47713:
		case 2145:
		case 5492:
		case 5501:
		case 5947:
		case 5946:
		case 30848:
		case 5493:
		case 49100:
		case 12144:
		case 12146:
		case 12147:
		case 12148:
		case 12149:
		case 12150:
		case 12151:
		case 12152:
		case 12153:
		case 12154:
		case 12163:
		case 12164:
		case 12165:
		case 12166:
		case 15765:
		case 76843:
		case 76824:
		case 244:
		case 241:
		case 32944:
		case 6658:
			return true;
		default:
		return false;
		}
	}

	
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 36771 || id == 36772) { //Object ID
			Lumbridge.TopLadder(player, object); //Method of Action
		}
		if (id == 37335) { //Object ID
			Lumbridge.LumbridgeFlag(player, object); //Method of Action
		}
		if (id == 36687 || id == 29355) { //Object ID
			Lumbridge.LumbridgeCellar(player, object); //Method of Action
		}
		if (id == 6898) { //Object ID
			Lumbridge.SqueezeThroughWall(player, object); //Method of Action
		}
		if (id == 45481 || id == 45483 || id == 45482 || id == 45484) { //Object ID
			Lumbridge.HandleStairs(player, object); //Method of Action
		}
		if (id == 36978) { //Object ID
			Lumbridge.ChurchOrgan(player, object); //Method of Action
		}
		if (id == 36984 || id == 36795 || id == 36985 || id == 36986 || id == 36988
				|| id == 36989 || id == 36990 || id == 36991 || id == 36987 || id == 5493) { //Object ID
			Lumbridge.HandleLadders(player, object); //Method of Action
		}
		if (id == 32049) { //Object ID
			Lumbridge.SearchSack(player, object); //Method of Action
		}
		if (id == 31139 || id == 24202) { //Object ID
			Lumbridge.SearchCrate(player, object); //Method of Action
		}
		if (id == 37683 || id == 45784) { //Object ID
			Lumbridge.OldTutTrapDoor(player, object); //Method of Action
		}
		if (id == 47721) { //Object ID
			Lumbridge.MilkCowQuest(player, object); //Method of Action
		}
		if (id == 2718) { //Object ID
			Lumbridge.GrainHopper(player, object); //Method of Action
		}
		if (id == 52309) { //Object ID
			Lumbridge.LumbridgeThiefGuild(player, object); //Method of Action
		}
		if (id == 52308) { //Object ID
			Lumbridge.LumbridgeThiefGuildExit(player, object); //Method of Action
		}
		if (id == 36976) {
			Lumbridge.RingBell(player, object);
		}
		if (id == 31299) {
			Lumbridge.Help(player, object);
		}
		if (id == 15468) {
			Lumbridge.TakeHammer(player, object);
		}
		if (id == 47713) {
			Lumbridge.FindSkull(player, object);
		}
		if (id == 2145) {
			Lumbridge.HandleCoffin(player, object);
		}
		if (id == 5492) {
			Lumbridge.LockedTrapDoor(player, object);
		}
		if (id == 5947) { //entrance
			Lumbridge.LumbridgeSwampDungeonEntrance(player, object);
		}
		if (id == 5946) { //Exit
			Lumbridge.LumbridgeSwampDungeonExit(player, object);
		}
		if (id == 30848) { //candles
			Lumbridge.CandleBox(player, object);
		}
		if (id == 49100) { //Cabbage Box
			Lumbridge.CabbageBox(player, object);
		}
		if (id >= 12163 && id <= 12166) { //Cut Down Canoe
			Lumbridge.CutDown(player, object);
		}
		if (id == 12146) { //Carve Canoe
			Lumbridge.carveCanoe(player, object);
		}
		if (id == 12147) { //Push Canoe
			Lumbridge.pushLog(player, object);
		}
		if (id == 12148) { //Push Canoe
			Lumbridge.pushDugout(player, object);
		}
		if (id == 12149) { //Push Canoe
			Lumbridge.pushStableDugout(player, object);
		}
		if (id == 12150) { //Push Canoe
			Lumbridge.pushWaka(player, object);
		}
		if (id == 12151) { //Use Canoe
			Lumbridge.useLog(player, object);
		}
		if (id == 12152) { //Use Canoe
			Lumbridge.useDugout(player, object);
		}
		if (id == 12153) { //Use Canoe
			Lumbridge.useStableDugout(player, object);
		}
		if (id == 12154) { //Use Canoe
			Lumbridge.useWaka(player, object);
		}
		if (id == 15765) { 
			Lumbridge.useTrap(player, object);
		}
		if (id == 76843) { 
			Lumbridge.flowerBox(player, object);
		}
		if (id == 76824) { 
			Lumbridge.decorateCoffin(player, object);
		}
		if (id == 244) { 
			Lumbridge.grapple1(player, object);
		}
		if (id == 241) { 
			Lumbridge.grapple2(player, object);
		}
		if (id == 32944) { 
			Lumbridge.Swamp1(player, object);
		}
		if (id == 6658) { 
			Lumbridge.Swamp2(player, object);
		}
	}

}

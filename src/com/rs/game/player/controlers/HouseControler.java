package com.rs.game.player.controlers;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Cooking;
import com.rs.game.player.actions.SitChair;
import com.rs.game.player.actions.Cooking.Cookables;
import com.rs.game.player.content.BonesOnAltar;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.content.BonesOnAltar.Bones;
import com.rs.game.player.content.RepairItems.BrokenItems;
import com.rs.game.player.content.construction.House;
import com.rs.game.player.content.construction.HouseConstants;
import com.rs.game.player.content.construction.HouseConstants.Builds;
import com.rs.game.player.dialogues.ChangeElement;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class HouseControler extends Controler {

    private House house;

    @Override
    public void start() {
	this.house = (House) getArguments()[0];
	getArguments()[0] = null; //its was gonna be saved unless somehow in a server restart but lets be safe
    }
    

    /**
     * return process normaly
     */
    @Override
    public boolean processObjectClick5(WorldObject object) {
	System.out.println(object.getXInChunk() + ", " + object.getYInChunk() + ", 5");
	if (object.getDefinitions().containsOption(4, "Build")) {
	    if (!house.isOwner(player)) {
		player.getPackets().sendGameMessage("You can only do that in your own house.");
		return false;
	    }
	    if (house.isDoor(object)) {
		house.openRoomCreationMenu(object);
	    } else {
		for (Builds build : HouseConstants.Builds.values()) {
		    if (build.containsId(object.getId())) {
			house.openBuildInterface(object, build);
			return false;
		    }

		}
	    }
	} else if (object.getDefinitions().containsOption(4, "Remove")) {
	    if (!house.isOwner(player)) {
		player.getPackets().sendGameMessage("You can only do that in your own house.");
		return false;
	    }
	    house.openRemoveBuild(object);
	}
	return false;
    }
    
	public String getTime() { 
		SimpleDateFormat format = new SimpleDateFormat("h:mm a zzz"); 
		return format.format(new Date());
	}

    @Override
    public boolean processObjectClick1(WorldObject object) {
	System.out.println(object.getXInChunk() + ", " + object.getYInChunk() + ", 1");
	if (object.getId() == HouseConstants.HObject.EXIT_PORTAL.getId())
	    house.leaveHouse(player, House.KICKED);
	else if (object.getId() == HouseConstants.HObject.CLAY_FIREPLACE.getId() || object.getId() == HouseConstants.HObject.STONE_FIREPLACE.getId() || object.getId() == HouseConstants.HObject.MARBLE_FIREPLACE.getId())
	    player.getPackets().sendGameMessage("Use some logs on the fireplace in order to light it.");
	else if (object.getId() == HouseConstants.HObject.TOOL_STORE_1.getId())
		player.getDialogueManager().startDialogue("ToolStore", 1);
	else if (object.getId() == HouseConstants.HObject.TOOL_STORE_2.getId())
		player.getDialogueManager().startDialogue("ToolStore", 2);
	else if (object.getId() == HouseConstants.HObject.TOOL_STORE_3.getId())
		player.getDialogueManager().startDialogue("ToolStore", 3);
	else if (object.getId() == HouseConstants.HObject.TOOL_STORE_4.getId())
		player.getDialogueManager().startDialogue("ToolStore", 4);
	else if (object.getId() == HouseConstants.HObject.TOOL_STORE_5.getId())
		player.getDialogueManager().startDialogue("ToolStore", 5);
	else if (object.getId() == HouseConstants.HObject.PLUMING_STAND.getId())
		player.sm("Use a rune full helm on this stand to decorate it.");
	else if (object.getId() == HouseConstants.HObject.SHIELD_EASEL.getId())
		player.sm("Use a rune full helm or a rune kiteshield on this stand to decorate it.");
	else if (object.getId() == HouseConstants.HObject.BANNER_EASEL.getId())
		player.sm("Use a rune full helm, a rune kiteshield, or a bolt of cloth on this stand to decorate it.");
	else if (HouseConstants.Builds.LAMP.containsObject(object))
	    player.getPackets().sendGameMessage("Use some clean marrentil on the burner in order to light it.");
	else if (object.getId() == HouseConstants.HObject.SHAVING_STAND.getId() || object.getId() == HouseConstants.HObject.OAK_SHAVING_STAND.getId() || object.getId() == HouseConstants.HObject.OAK_DRESSER.getId() || object.getId() == HouseConstants.HObject.TEAK_DRESSER.getId() || object.getId() == HouseConstants.HObject.FANCY_TEAK_DRESSER.getId() || object.getId() == HouseConstants.HObject.MAHOGANY_DRESSER.getId() || object.getId() == HouseConstants.HObject.GILDED_DRESSER.getId())
		PlayerLook.openHairdresserSalon(player);
	else if (object.getId() == HouseConstants.HObject.SHOE_BOX.getId() || object.getId() == HouseConstants.HObject.OAK_DRAWERS.getId() || object.getId() == HouseConstants.HObject.OAK_WARDROBE.getId() || object.getId() == HouseConstants.HObject.TEAK_DRAWERS.getId() || object.getId() == HouseConstants.HObject.TEAK_WARDROBE.getId() || object.getId() == HouseConstants.HObject.MAHOGANY_WARDROBE.getId() || object.getId() == HouseConstants.HObject.GILDED_WARDROBE.getId())
		PlayerLook.openThessaliasMakeOver(player);
	else if (object.getId() == HouseConstants.HObject.OAK_CLOCK.getId() || object.getId() == HouseConstants.HObject.TEAK_CLOCK.getId() || object.getId() == HouseConstants.HObject.GILDED_CLOCK.getId())
		player.sm("The clock reads the current time: "+getTime()+".");
    else if (object.getId() == HouseConstants.HObject.EAGLE_LECTURN.getId() || object.getId() == HouseConstants.HObject.DEMON_LECTURN.getId() || object.getId() == HouseConstants.HObject.TEAK_EAGLE_LECTURN.getId() || object.getId() == HouseConstants.HObject.TEAK_DEMON_LECTURN.getId() || object.getId() == HouseConstants.HObject.MAHOGANY_EAGLE_LECTURN.getId() || object.getId() == HouseConstants.HObject.MAHOGANY_DEMON_LECTURN.getId()) {
		player.getInterfaceManager().sendInterface(400);
		player.getPackets().sendConfig(261, 3);
	} else if (object.getId() >= HouseConstants.HObject.CRUDE_WOODEN_CHAIR.getId() && object.getId() <= HouseConstants.HObject.MAHOGANY_ARMCHAIR.getId()) {
	    int chair = object.getId() - HouseConstants.HObject.CRUDE_WOODEN_CHAIR.getId();
	    player.getActionManager().setAction(new SitChair(player, chair, object));
	} else if (HouseConstants.Builds.ALTAR.containsObject(object)) {
		if (player.getPrayer().getPrayerpoints() < player
				.getSkills().getLevelForXp(Skills.PRAYER) * 10) {
			player.lock(12);
			player.setNextAnimation(new Animation(12563));
			player.getPrayer().setPrayerpoints(
					(int) ((player.getSkills().getLevelForXp(
							Skills.PRAYER) * 10) * 1.15));
			player.getPrayer().refreshPrayerPoints();
		}
	} else if (HouseConstants.Builds.COMBAT_RING.containsObject(object)) {
		final WorldObject ring = object;
	    if (!player.inRing) {
	    	player.setCanPvp(true);
	    	player.inRing = true;
			WorldTasksManager.schedule(new WorldTask() {
				boolean secondloop;

				@Override
				public void run() {
					if (!secondloop) {
						secondloop = true;
						player.getAppearence().setRenderEmote(594);
						if (ring.getX() == player.getX()) {
							if (player.getY() > ring.getY())
								player.addWalkSteps(player.getX(), (player.getY() - 1), -1, false);
							else
								player.addWalkSteps(player.getX(), (player.getY() + 1), -1, false);
						} else if (ring.getY() == player.getY()) {
							if (player.getX() > ring.getX())
								player.addWalkSteps((player.getX() - 1), player.getY(), -1, false);
							else
								player.addWalkSteps((player.getX() + 1), player.getY(), -1, false);
						}
						player.sm("You jump over the ring...");
					} else {
						player.getAppearence().setRenderEmote(-1);
						player.getPackets().sendGameMessage(
								"... and make it into the arena.", true);
						stop();
					}
				}
			}, 0, 1);
	    } else {
	    	player.setCanPvp(false);
	    	player.inRing = false;
			WorldTasksManager.schedule(new WorldTask() {
				boolean secondloop;

				@Override
				public void run() {
					if (!secondloop) {
						secondloop = true;
						player.getAppearence().setRenderEmote(594);
						if (ring.getX() == player.getX()) {
							if (player.getY() > ring.getY())
								player.addWalkSteps(player.getX(), (player.getY() - 1), -1, false);
							else
								player.addWalkSteps(player.getX(), (player.getY() + 1), -1, false);
						} else if (ring.getY() == player.getY()) {
							if (player.getX() > ring.getX())
								player.addWalkSteps((player.getX() - 1), player.getY(), -1, false);
							else
								player.addWalkSteps((player.getX() + 1), player.getY(), -1, false);
						}
						player.sm("You jump over the ring...");
					} else {
						player.getAppearence().setRenderEmote(-1);
						player.getPackets().sendGameMessage(
								"... and make it outside the arena.", true);
						stop();
					}
				}
			}, 0, 1);
	    }
	}else if (HouseConstants.Builds.BOOKCASE.containsObject(object))
	    player.getPackets().sendGameMessage("You search the bookcase but find nothing.");	
	else if (HouseConstants.Builds.DINING_BENCH_1.containsObject(object) || HouseConstants.Builds.DINING_BENCH_2.containsObject(object))
		SitBenches(player, object);
	else if (HouseConstants.Builds.SHELVES.containsObject(object) || HouseConstants.Builds.SHELVES_2.containsObject(object))
		player.getDialogueManager().startDialogue("Shelves");
	else if (HouseConstants.Builds.LARDER.containsObject(object))
		player.getDialogueManager().startDialogue("Larders");
	else if (HouseConstants.Builds.CRAFTING.containsObject(object))
		player.getDialogueManager().startDialogue("CraftingBench");
	else if (HouseConstants.Builds.WEAPONS_RACK.containsObject(object))
		player.getDialogueManager().startDialogue("WeaponsRack");
	else if (HouseConstants.Builds.MUSICAL.containsObject(object))
		player.sm("You attempt to play the "+object.getDefinitions().getName()+" but you are just so bad.");
	else if (HouseConstants.Builds.TELESCOPE.containsObject(object))
	    player.getPackets().sendGameMessage("You look through the telescope and observe the stars, even though it is day time.");	
	else if (object.getId() == HouseConstants.HObject.ALCHEMICAL_CHART.getId() || object.getId() == HouseConstants.HObject.ASTRONOMICAL_CHART.getId() || object.getId() == HouseConstants.HObject.INFERNAL.getId())
	    player.getPackets().sendGameMessage("You read the chart and learn some information about the field.");	
	else if (object.getId() == HouseConstants.HObject.VARROCKP.getId()) {
		player.getControlerManager().removeControlerWithoutCheck();
	    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3212, 3424, 0));
	} else if (object.getId() == HouseConstants.HObject.LUMBRIDGEP.getId()) {
		player.getControlerManager().removeControlerWithoutCheck();
	    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3222, 3218, 0));
    } else if (object.getId() == HouseConstants.HObject.FALADORP.getId()) {
    	player.getControlerManager().removeControlerWithoutCheck();
	    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2964, 3379, 0));
	} else if (object.getId() == HouseConstants.HObject.CAMELOTP.getId()) {
		player.getControlerManager().removeControlerWithoutCheck();
	    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2757, 3478, 0));
	} else if (object.getId() == HouseConstants.HObject.ARDOUGNEP.getId()) {
		player.getControlerManager().removeControlerWithoutCheck();
	    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2664, 3305, 0));
	} else if (object.getId() == HouseConstants.HObject.YANILLEP.getId()) {
		player.getControlerManager().removeControlerWithoutCheck();
	    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2605, 3093, 0));
	} else if (object.getId() == HouseConstants.HObject.KHARYRLLP.getId()) {
		player.getControlerManager().removeControlerWithoutCheck();
	    Magic.sendAncientTeleportSpell(player, 0, 0, new WorldTile(3495, 3490, 0));
	} else if (HouseConstants.Builds.STAIRCASE.containsObject(object) || HouseConstants.Builds.STAIRCASE_DOWN.containsObject(object)) {
	    if (object.getDefinitions().getOption(1).equals("Climb"))
		player.getDialogueManager().startDialogue("ClimbHouseStairD", object);
	    else
		house.climbStaircase(object, object.getDefinitions().getOption(1).equals("Climb-up"));
	} else 
		player.sm("This object's action has not been added yet.");
	return false;
    }

    public boolean handleItemOnObject(WorldObject object, Item item) {
	if (object.getId() == HouseConstants.HObject.CLAY_FIREPLACE.getId() || object.getId() == HouseConstants.HObject.STONE_FIREPLACE.getId() || object.getId() == HouseConstants.HObject.MARBLE_FIREPLACE.getId()) {
	    if (item.getId() != 1511) {
		player.getPackets().sendGameMessage("Only ordinary logs can be used to light the fireplace.");
		return false;
	    }
	    if (!player.getInventory().containsItemToolBelt(590)) {
		player.getPackets().sendGameMessage("You do not have the required items to light this.");
		return false;
	    }
	    player.lock(2);
	    player.setNextAnimation(new Animation(3658));
	    player.getSkills().addXp(Skills.FIREMAKING, 40);
	    WorldObject objectR = new WorldObject(object);
	    objectR.setId(object.getId() + 1);
	    //wiki says: If you light a fire in your fireplace, then change the graphic settings, the fire will disappear meaning its not realy spawned
	    for (Player player : house.getPlayers())
		player.getPackets().sendSpawnedObject(objectR);
	    return false;
	} else if (object.getId() == HouseConstants.HObject.PLUMING_STAND.getId()) {
		if (item.getId() == 1163)
		player.getDialogueManager().startDialogue("Heraldry", item);	
	} else if (object.getId() == HouseConstants.HObject.SHIELD_EASEL.getId()) {
		if (item.getId() == 1163 || item.getId() == 1201)
			player.getDialogueManager().startDialogue("Heraldry", item);	
    } else if (object.getId() == HouseConstants.HObject.BANNER_EASEL.getId()) {
		if (item.getId() == 1163 || item.getId() == 1201 || item.getId() == 8790)
			player.getDialogueManager().startDialogue("Heraldry", item);	
	} else if (HouseConstants.Builds.LAMP.containsObject(object)) {
	    if (item.getId() != 251) {
		player.getPackets().sendGameMessage("Only clean marrentil can be used to light this.");
		return false;
	    }
	    if (!player.getInventory().containsItemToolBelt(590)) {
		player.getPackets().sendGameMessage("You do not have the required items to light this.");
		return false;
	    }
	    player.lock(2);
	    player.setNextAnimation(new Animation(3658));
	    player.getSkills().addXp(Skills.FIREMAKING, 40);
	    player.getInventory().deleteItem(251, 1);
	    WorldObject objectR = new WorldObject(object);
	    objectR.setId(object.getId() + 1);
	    //wiki says: If you light a fire in your fireplace, then change the graphic settings, the fire will disappear meaning its not realy spawned
	    for (Player player : house.getPlayers())
		player.getPackets().sendSpawnedObject(objectR);
	    return false;
	} else if (HouseConstants.Builds.REPAIR.containsObject(object)) {
		if (BrokenItems.forId(item.getId()) == null) {
		player.getDialogueManager().startDialogue("SimpleMessage","You cant repair this item.");
		return false;
		}
		player.getDialogueManager().startDialogue("Repair", 945, item.getId());	
		return false;
	} else if (object.getId() == HouseConstants.HObject.CRYSTAL_BALL.getId() || object.getId() == HouseConstants.HObject.ELEMENTAL_SPHERE.getId() || object.getId() == HouseConstants.HObject.CRYSTAL_OF_POWER.getId()) {
		if (ChangeElement.isStaff(item.getId()) && ChangeElement.hasStaff(player)) {
		player.getDialogueManager().startDialogue("ChangeElement", item.getId());
		return false;
		} else {
		player.sm("You cannot use this item!");
		return false;
		}
	} else if (HouseConstants.Builds.BARRELS.containsObject(object)) {
		Bones bone = BonesOnAltar.isGood(item);
		if(bone != null) {
			player.getDialogueManager().startDialogue("PrayerD", bone, object);
			return true;
		} else {
			player.getPackets().sendGameMessage("Nothing interesting happens.");
			return true;
		}
	} else if (HouseConstants.Builds.BARRELS.containsObject(object)) {
		if (item.getId() == 1919 && object.getId() == 13568) { //BEER BARREL
		player.getInventory().deleteItem(1919, 1);
		player.getInventory().addItem(1917, 1);
		} else if (item.getId() == 1919 && object.getId() == 13569) { //CIDER BARREL
		player.getInventory().deleteItem(1919, 1);
		player.getInventory().addItem(5763, 1);
		} else if (item.getId() == 1919 && object.getId() == 13570) { //ASGARNIAN BARREL
		player.getInventory().deleteItem(1919, 1);
		player.getInventory().addItem(1905, 1);
		} else if (item.getId() == 1919 && object.getId() == 13571) { //GREENMANS BARREL
		player.getInventory().deleteItem(1919, 1);
		player.getInventory().addItem(1909, 1);
		} else if (item.getId() == 1919 && object.getId() == 13572) { //DRAGON BITTER BARREL
		player.getInventory().deleteItem(1919, 1);
		player.getInventory().addItem(1911, 1);
		} else if (item.getId() == 1919 && object.getId() == 13573) { //CHEF'S DELIGHT BARREL
		player.getInventory().deleteItem(1919, 1);
		player.getInventory().addItem(5755, 1);
		}
		player.sm("You fill up your beer glass.");
		return false;
	} else if (HouseConstants.Builds.SINK.containsObject(object)) {
		if (item.getId() == 229) {
			player.getInventory().addItem(227, 1);
			player.getInventory().deleteItem(229, 1);
			player.out("You fill the vial with water.");
		} else if (item.getId() == 1925) {
			player.getInventory().addItem(1929, 1);
			player.getInventory().deleteItem(1925, 1);
			player.out("You fill the bucket with water.");
		} else if (item.getId() == 1825) {
			player.getInventory().addItem(1823, 1);
			player.getInventory().deleteItem(1825, 1);
			player.out("You fill the waterskin with water.");
		} else if (item.getId() == 1827) {
			player.getInventory().addItem(1823, 1);
			player.getInventory().deleteItem(1827, 1);
			player.out("You fill the waterskin with water.");
		} else if (item.getId() == 1829) {
			player.getInventory().addItem(1823, 1);
			player.getInventory().deleteItem(1829, 1);
			player.out("You fill the waterskin with water.");
		} else if (item.getId() == 1831) {
			player.getInventory().addItem(1823, 1);
			player.getInventory().deleteItem(1831, 1);
			player.out("You fill the waterskin with water.");
		} else if (item.getId() == 1923) {
			player.getInventory().addItem(1921, 1);
			player.getInventory().deleteItem(1923, 1);
			player.out("You fill the bowl with water.");
		} else if (item.getId() == 1935) {
			player.getInventory().addItem(1937, 1);
			player.getInventory().deleteItem(1935, 1);
			player.out("You fill the jug with water.");
		} else if (item.getId() == 7728) {
			player.getInventory().addItem(4458, 1);
			player.getInventory().deleteItem(7728, 1);
			player.out("You fill the cup with water.");
		} else if (item.getId() == 5333) {
			player.getInventory().addItem(5340, 1);
			player.getInventory().deleteItem(5333, 1);
			player.out("You fill the watering can with water.");
		} else if (item.getId() == 5331) {
			player.getInventory().addItem(5340, 1);
			player.getInventory().deleteItem(5331, 1);
			player.out("You fill the watering can with water.");
		} else if (item.getId() == 5334) {
			player.getInventory().addItem(5340, 1);
			player.getInventory().deleteItem(5334, 1);
			player.out("You fill the watering can with water.");
		} else if (item.getId() == 5335) {
			player.getInventory().addItem(5340, 1);
			player.getInventory().deleteItem(5335, 1);
			player.out("You fill the watering can with water.");
		} else if (item.getId() == 5336) {
			player.getInventory().addItem(5340, 1);
			player.getInventory().deleteItem(5336, 1);
			player.out("You fill the watering can with water.");
		} else if (item.getId() == 5337) {
			player.getInventory().addItem(5340, 1);
			player.getInventory().deleteItem(5337, 1);
			player.out("You fill the watering can with water.");
		} else if (item.getId() == 5338) {
			player.getInventory().addItem(5340, 1);
			player.getInventory().deleteItem(5338, 1);
			player.out("You fill the watering can with water.");
		} else if (item.getId() == 5339) {
			player.getInventory().addItem(5340, 1);
			player.getInventory().deleteItem(5339, 1);
			player.out("You fill the watering can with water.");
		} else {
			player.out("You cannot fill this item with water.");
		}
		return false;
	}else if(HouseConstants.Builds.STOVE.containsObject(object)) {
	    Cookables cook = Cooking.isCookingSkill(item);
	    if (cook != null) {
		player.getDialogueManager().startDialogue("CookingD", cook, object);
		return false;
	    }
	    player.getDialogueManager().startDialogue("SimpleMessage", "You can't cook that on a " + (object.getDefinitions().name.equals("Fire") ? "fire" : "range") + ".");
	    return false;
	}
	return true;
    }
    
    public boolean canDropItem(Item item) {
	if(house.isBuildMode()) {
	    player.getPackets().sendGameMessage("You cannot drop items while in building mode.");
	    return false;
	}
	return true;
    }

    @Override
    public boolean processObjectClick2(WorldObject object) {
	System.out.println(object.getXInChunk() + ", " + object.getYInChunk() + ", 2");
	if (object.getId() == HouseConstants.HObject.EXIT_PORTAL.getId())
	    house.switchLock(player);
	else if (HouseConstants.Builds.STAIRCASE.containsObject(object) || HouseConstants.Builds.STAIRCASE_DOWN.containsObject(object))
	    house.climbStaircase(object, true);
	return false;
    }

    @Override
    public boolean processObjectClick3(WorldObject object) {
	if (HouseConstants.Builds.STAIRCASE.containsObject(object) || HouseConstants.Builds.STAIRCASE_DOWN.containsObject(object))
	    house.climbStaircase(object, false);
	return false;
    }

    @Override
    public boolean processObjectClick4(WorldObject object) {
	if (HouseConstants.Builds.STAIRCASE.containsObject(object) || HouseConstants.Builds.STAIRCASE_DOWN.containsObject(object))
	    house.removeRoom();
	return false;
    }
    
    @Override
    public boolean checkWalkStep(int lastX, int lastY, int nextX, int nextY) {
	return !house.isSky(nextX, nextY, player.getPlane());
    }


    //shouldnt happen but lets imagine somehow in a server restart
    @Override
    public boolean login() {
       player.inRing = false;
       player.setCanPvp(false);
	player.setNextWorldTile(new WorldTile(2534, 2836, 0));
	removeControler();
	return false; //remove controller manualy since i dont want to call forceclose
    }

    @Override
    public void magicTeleported(int type) {
    player.inRing = false;
    player.setCanPvp(false);
    player.setNextWorldTile(new WorldTile(2534, 2836, 0));
    }

    //shouldnt happen
    @Override
    public void forceClose() {
        player.inRing = false;
        player.setCanPvp(false);
    	player.setNextWorldTile(new WorldTile(2534, 2836, 0));
    }

    public House getHouse() {
	return house;
    }
    
	@Override
	public boolean sendDeath() {
		player.lock(7);
		player.stopAll();
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					player.inRing = false;
					player.setCanPvp(false);
					player.setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					removeControler();
					player.getPackets().sendGameMessage("You have been killed!");
				} else if (loop == 3) {
					player.setNextWorldTile(new WorldTile(2534, 2836, 0));
					player.reset();
					player.setNextAnimation(new Animation(-1));
				} else if (loop == 4) {
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return false;
	}
    
    private static WorldTile benchTile;
    
	public static void SitBenches(Player player,
			final WorldObject object) {
		int emote = 0;
		switch (object.getId()) {
		case 13300:
			emote = 4089;
			break;
		case 13301:
			emote = 4091;
			break;
		case 13302:
			emote = 4093;
			break;
		case 13303:
			emote = 4095;
			break;
		case 13304:
			emote = 4097;
			break;
		case 13305:
			emote = 4099;
			break;
		case 13306:
			emote = 4101;
			break;
		}
		benchTile = object;
		player.setNextWorldTile(benchTile);
		player.setNextAnimation(new Animation(emote));
	}
    

}

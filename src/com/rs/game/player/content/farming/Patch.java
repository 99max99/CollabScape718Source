package com.rs.game.player.content.farming;

import java.io.Serializable;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Woodcutting;
import com.rs.game.player.content.farming.PatchConstants.AllotmentPatch;
import com.rs.game.player.content.farming.PatchConstants.BellPatch;
import com.rs.game.player.content.farming.PatchConstants.BushPatch;
import com.rs.game.player.content.farming.PatchConstants.CactusPatch;
import com.rs.game.player.content.farming.PatchConstants.FlowerPatch;
import com.rs.game.player.content.farming.PatchConstants.FruitTreePatch;
import com.rs.game.player.content.farming.PatchConstants.HerbPatch;
import com.rs.game.player.content.farming.PatchConstants.HopPatch;
import com.rs.game.player.content.farming.PatchConstants.MushroomPatch;
import com.rs.game.player.content.farming.PatchConstants.TreePatch;
import com.rs.game.player.content.farming.PatchConstants.VineHerbPatch;
import com.rs.utils.Utils;

public class Patch implements Serializable {

	private static final long serialVersionUID = 8675250713093831368L;
	
	public int[] states;
	
	public int configByFile = -1;
	public int objectId = -1;
	public int configIndex = 0;
	public int rakedState = 0;
	public int state = 0;
	public int maxState = 4;
	public int currentSeed = -1;
	public int yield = -1;
	public boolean grown = false;
	public boolean raked = false;
	public boolean healthChecked = false;
	public boolean diseased = false;
	public boolean dead = false;
	public boolean watered = false;
	public boolean protectedPatch = false;
	public int composted = 0;
	
	public void setComposted(int type) {
		this.composted = type;
	}
	
	public boolean isSuperComposted() {
		return composted == 2;
	}
	
	public boolean isComposted() {
		return composted == 1;
	}
	
	public Patch(int configByFile, int objectId) {
		this.objectId = objectId;
		this.configByFile = configByFile;
		this.configIndex = 0;
		this.state = 0;
		this.rakedState = 0;
		this.yield = -1;
		this.grown = false;
		this.raked = false;
		this.healthChecked = false;
		this.diseased = false;
		this.dead = false;
	}
	
	public void handleCuring(Player player, int itemId) {
		if (diseased) {
			if (itemId == -1) {
				diseased = false;
			} else {
				if (itemId == 6036) {
					player.setNextAnimation(new Animation(2288));
					player.getInventory().deleteItem(6036, 1);
					player.getInventory().addItem(229, 1);
					diseased = false;
				}
			}
		} else {
			player.getPackets().sendGameMessage("This plant doesn't need curing.");
		}
		updatePatch(player);
	}

	public void handleCompost(Player player, int itemId) {
		if (composted == 0) {
			player.setNextAnimation(new Animation(2283));
			player.getInventory().deleteItem(itemId, 1);
			player.getInventory().addItem(1925, 1);
			player.getPackets().sendGameMessage("You pour some compost on the patch.");
			composted = itemId == 6032 ? 1 : 2;
		} else {
			player.getPackets().sendGameMessage("This patch is already composted.");
		}
	}

	public void handleWatering(Player player, int itemId) {
		if (itemId == 5331) {
			player.getPackets().sendGameMessage("You need to fill your watering can.");
			return;
		}
		if (currentSeed == 14589)
			return;
		if (watered) {
			player.getPackets().sendGameMessage("This patch is already watered.");
			return;
		}
		if (AllotmentPatch.forId(currentSeed) != null || FlowerPatch.forId(currentSeed) != null || HopPatch.forId(currentSeed) != null) {
			if (state < maxState) {
				if (itemId >= 5334 && itemId <= 5340) {
					player.getInventory().deleteItem(itemId, 1);
					player.getInventory().addItem(itemId-1, 1);
				} else if (itemId == 5333) {
					player.getInventory().deleteItem(itemId, 1);
					player.getInventory().addItem(itemId-2, 1);
				}
				player.setNextAnimation(new Animation(2293));
				watered = true;
			} else {
				player.getPackets().sendGameMessage("This patch doesn't look like it needs water.");
			}
		} else {
			player.getPackets().sendGameMessage("This patch is not waterable.");
		}
		updatePatch(player);
	}
	
	public void handlePlanting(Player player, int seedId) {
		if (!player.getInventory().containsItem(PatchConstants.SEED_DIBBER, 1)) {
			player.getPackets().sendGameMessage("You need a seed dibber to plant seeds.");
			return;
		}
		if (!player.getInventory().containsItem(seedId, 1))
			return;
		
		if (!raked) {
			player.getPackets().sendGameMessage("This patch needs weeding first.");
			return;
		}
		if (currentSeed != -1) {
			player.getPackets().sendGameMessage("There is already something growing in this patch.");
			return;
		}
		if (objectId == 18816) {
			protectedPatch = true;
		}
		if (objectId == 7572) {
			BellPatch bell = BellPatch.forId(seedId);
			if (bell != null) {
				if (player.getSkills().getLevel(Skills.FARMING) < bell.getRequirement()) {
					player.getPackets().sendGameMessage("You need a farming level of "+bell.getRequirement()+" to plant that.");
					return;
				}
				player.setNextAnimation(new Animation(2291));
				player.getInventory().deleteItem(bell.getSeed(), 1);
				this.currentSeed = bell.getSeed();
				this.configIndex = bell.getConfigId();
				this.maxState = 4;
				this.yield = 1;
				player.getSkills().addXp(Skills.FARMING, bell.getXp()/4);
				player.getPackets().sendGameMessage("You plant the seeds in the patch.");
			} else {
				player.getPackets().sendGameMessage("You can't plant that here!");
			}
			protectedPatch = true;
		} else if (objectId == 56682 || objectId == 56683) {
			VineHerbPatch vineHerb = VineHerbPatch.forId(seedId);
			if (vineHerb != null) {
				if (player.getSkills().getLevel(Skills.FARMING) < vineHerb.getRequirement()) {
					player.getPackets().sendGameMessage("You need a farming level of "+vineHerb.getRequirement()+" to plant that.");
					return;
				}
				player.setNextAnimation(new Animation(2291));
				player.getInventory().deleteItem(vineHerb.getSeed(), 1);
				this.currentSeed = vineHerb.getSeed();
				this.configIndex = vineHerb.getConfigId();
				this.maxState = 4;
				player.getSkills().addXp(Skills.FARMING, vineHerb.getXp()/2);
				player.getPackets().sendGameMessage("You plant the seeds in the patch.");
			} else {
				player.getPackets().sendGameMessage("You can't plant that here!");
			}
		}
		if (objectId >= 7577 && objectId <= 7580) {
			BushPatch bush = BushPatch.forId(seedId);
			if (bush != null) {
				if (player.getSkills().getLevel(Skills.FARMING) < bush.getRequirement()) {
					player.getPackets().sendGameMessage("You need a farming level of "+bush.getRequirement()+" to plant that.");
					return;
				}
				player.setNextAnimation(new Animation(2291));
				player.getInventory().deleteItem(bush.getSeed(), 1);
				this.currentSeed = bush.getSeed();
				this.configIndex = bush.getConfigId();
				this.maxState = bush.getMaxState();
				this.yield = 4;
				if (seedId == 5106)
					protectedPatch = true;
				player.getSkills().addXp(Skills.FARMING, bush.getXp());
				player.getPackets().sendGameMessage("You plant the seeds in the patch.");
			} else {
				player.getPackets().sendGameMessage("You can't plant that here!");
			}
		}
		if (objectId >= 7847 && objectId <= 7850) {
			FlowerPatch flower = FlowerPatch.forId(seedId);
			if (flower != null) {
				if (player.getSkills().getLevel(Skills.FARMING) < flower.getRequirement()) {
					player.getPackets().sendGameMessage("You need a farming level of "+flower.getRequirement()+" to plant that.");
					return;
				}
				player.setNextAnimation(new Animation(2291));
				player.getInventory().deleteItem(flower.getSeed(), 1);
				this.currentSeed = flower.getSeed();
				this.configIndex = flower.getConfigId();
				this.maxState = 4;
				player.getSkills().addXp(Skills.FARMING, 4);
				player.getPackets().sendGameMessage("You plant the seeds in the patch.");
				if (seedId == 14589)
					protectedPatch = true;
			} else {
				player.getPackets().sendGameMessage("You can't plant that here!");
			}
		}
		if (objectId == 18816 || (objectId >= 8150 && objectId <= 8153)) {
			HerbPatch herb = HerbPatch.forId(seedId);
			if (herb != null) {
				if (player.getSkills().getLevel(Skills.FARMING) < herb.getRequirement()) {
					player.getPackets().sendGameMessage("You need a farming level of "+herb.getRequirement()+" to plant that.");
					return;
				}
				player.setNextAnimation(new Animation(2291));
				player.getInventory().deleteItem(herb.getSeed(), 1);
				this.currentSeed = herb.getSeed();
				this.configIndex = herb.getConfigId();
				this.maxState = 4;
				player.getSkills().addXp(Skills.FARMING, herb.getXp()/3);
				player.getPackets().sendGameMessage("You plant the seeds in the patch.");
			} else {
				player.getPackets().sendGameMessage("You can't plant that here!");
			}
		}
		if (objectId >= 8550 && objectId <= 8557) {
			AllotmentPatch allotment = AllotmentPatch.forId(seedId);
			if (allotment != null) {
				if (player.getSkills().getLevel(Skills.FARMING) < allotment.getRequirement()) {
					player.getPackets().sendGameMessage("You need a farming level of "+allotment.getRequirement()+" to plant that.");
					return;
				}
				if (!player.getInventory().containsItem(allotment.getSeed(), 3)) {
					player.getPackets().sendGameMessage("You need 3 seeds to plant an allotment.");
					return;
				}
				player.setNextAnimation(new Animation(2291));
				player.getInventory().deleteItem(allotment.getSeed(), 3);
				this.currentSeed = allotment.getSeed();
				this.configIndex = allotment.getConfigId();
				this.maxState = allotment.getMaxStage();
				player.getSkills().addXp(Skills.FARMING, allotment.getXp()/3);
				player.getPackets().sendGameMessage("You plant the seeds in the patch.");
			} else {
				player.getPackets().sendGameMessage("You can't plant that here!");
			}
		}
		if (objectId == 8337) {
			MushroomPatch mushroom = MushroomPatch.forId(seedId);
			if (mushroom != null) {
				if (player.getSkills().getLevel(Skills.FARMING) < mushroom.getRequirement()) {
					player.getPackets().sendGameMessage("You need a farming level of "+mushroom.getRequirement()+" to plant that.");
					return;
				}
				player.setNextAnimation(new Animation(2291));
				player.getInventory().deleteItem(mushroom.getSeed(), 1);
				this.currentSeed = mushroom.getSeed();
				this.configIndex = mushroom.getConfigId();
				this.maxState = 6;
				player.getSkills().addXp(Skills.FARMING, mushroom.getXp());
				player.getPackets().sendGameMessage("You plant the seeds in the patch.");
			} else {
				player.getPackets().sendGameMessage("You can't plant that here!");
			}
			protectedPatch = true;
		}
		if (objectId == 7771) {
			CactusPatch cactus = CactusPatch.forId(seedId);
			if (cactus != null) {
				if (player.getSkills().getLevel(Skills.FARMING) < cactus.getRequirement()) {
					player.getPackets().sendGameMessage("You need a farming level of "+cactus.getRequirement()+" to plant that.");
					return;
				}
				player.setNextAnimation(new Animation(2291));
				player.getInventory().deleteItem(cactus.getSeed(), 1);
				this.currentSeed = cactus.getSeed();
				this.configIndex = cactus.getConfigId();
				this.maxState = 8;
				player.getSkills().addXp(Skills.FARMING, cactus.getXp());
				player.getPackets().sendGameMessage("You plant the seeds in the patch.");
			} else {
				player.getPackets().sendGameMessage("You can't plant that here!");
			}
			protectedPatch = true;
		}
		if (objectId == 7964 || objectId == 7965 || objectId == 7962 || objectId == 7963 || objectId == 28919 || objectId == 56667) {
			FruitTreePatch tree = FruitTreePatch.forId(seedId);
			if (tree != null) {
				if (player.getSkills().getLevel(Skills.FARMING) < tree.getRequirement()) {
					player.getPackets().sendGameMessage("You need a farming level of "+tree.getRequirement()+" to plant that.");
					return;
				}
				player.setNextAnimation(new Animation(2291));
				player.getInventory().deleteItem(tree.getSeed(), 1);
				this.currentSeed = tree.getSeed();
				this.configIndex = tree.getConfigId();
				this.maxState = 6;
				this.yield = 6;
				player.getSkills().addXp(Skills.FARMING, tree.getPickXp());
				player.getPackets().sendGameMessage("You plant the seeds in the patch.");
			} else {
				player.getPackets().sendGameMessage("You can't plant that here!");
			}
		}
		if (objectId == 8389 || objectId == 19147 || objectId == 8391 || objectId == 8388 || objectId == 8390) {
			TreePatch tree = TreePatch.forId(seedId);
			if (tree != null) {
				if (player.getSkills().getLevel(Skills.FARMING) < tree.getRequirement()) {
					player.getPackets().sendGameMessage("You need a farming level of "+tree.getRequirement()+" to plant that.");
					return;
				}
				player.setNextAnimation(new Animation(2291));
				player.getInventory().deleteItem(tree.getSeed(), 1);
				this.currentSeed = tree.getSeed();
				this.configIndex = tree.getConfigId();
				this.maxState = tree.getMaxState();
				this.yield = Utils.random(3, 15);
				player.getSkills().addXp(Skills.FARMING, tree.getPlantXp());
				player.getPackets().sendGameMessage("You plant the seeds in the patch.");
			} else {
				player.getPackets().sendGameMessage("You can't plant that here!");
			}
		}
		if (objectId >= 8173 && objectId <= 8176) {
			HopPatch hop = HopPatch.forId(seedId);
			if (hop != null) {
				if (player.getSkills().getLevel(Skills.FARMING) < hop.getRequirement()) {
					player.getPackets().sendGameMessage("You need a farming level of "+hop.getRequirement()+" to plant that.");
					return;
				}
				if (!player.getInventory().containsItem(hop.getSeed(), 4)) {
					player.getPackets().sendGameMessage("You need 4 seeds to plant a hop.");
					return;
				}
				player.setNextAnimation(new Animation(2291));
				player.getInventory().deleteItem(hop.getSeed(), 4);
				this.currentSeed = hop.getSeed();
				this.configIndex = hop.getConfigId();
				this.maxState = hop.getMaxStage();
				player.getSkills().addXp(Skills.FARMING, hop.getXp());
				player.getPackets().sendGameMessage("You plant the seeds in the patch.");
			} else {
				player.getPackets().sendGameMessage("You can't plant that here!");
			}
		}
		updatePatch(player);
	}
	
	public void handleOption1(Player player) {
		if (!raked) {
			if (!player.getInventory().containsItem(PatchConstants.RAKE, 1)) {
				player.getPackets().sendGameMessage("You need a rake to rake the patch.");
				return;
			}
			if (!player.getInventory().hasFreeSlots()) {
				player.getPackets().sendGameMessage("You don't have enough inventory space.");
				return;
			}
			player.setNextAnimation(new Animation(2273));
			player.getPackets().sendGameMessage("Raking...");
			rake(player);
			player.getSkills().addXp(Skills.FARMING, 3);
			updatePatch(player);
			return;
		}
		if (grown) {
			if (FruitTreePatch.forId(currentSeed) != null) {
				if (yield == 0) {
					int axeAnim = Woodcutting.getAxeAnim(player);
					if (axeAnim != -1) {
						player.setNextAnimation(new Animation(axeAnim));
						if (player.getInventory().containsItem(18336, 1)) {
							if (Utils.getRandom(10) == 5) {
								player.getPackets().sendGameMessage("You recovered a seed from the patch!");
								player.getInventory().addItem(currentSeed, 1);
							}
						}
						clear();
						updatePatch(player);
					} else {
						player.getPackets().sendGameMessage("You need a hatchet that you are able to use with your woodcutting level to cut this tree.");
					}
					return;
				}
				if (!healthChecked) {
					player.getPackets().sendGameMessage("You examine the tree for signs of disease and find that it is in perfect health.");
					player.getSkills().addXp(Skills.FARMING, FruitTreePatch.forId(currentSeed).getXp());
					healthChecked = true;
				} else {
					player.getActionManager().setAction(new HarvestAction(player, this));
				}
			} else if (TreePatch.forId(currentSeed) != null) {
					if (yield == 0) {
						if (player.getInventory().containsItem(PatchConstants.SPADE, 1)) {
							player.setNextAnimation(new Animation(830));
							if (player.getInventory().containsItem(18336, 1)) {
								if (Utils.getRandom(10) == 5) {
									player.getPackets().sendGameMessage("You recovered a seed from the patch!");
									player.getInventory().addItem(currentSeed, 1);
								}
							}
							clear();
							updatePatch(player);
						} else {
							player.getPackets().sendGameMessage("You need a spade to clear the stump.");
						}
						return;
					}
					if (!healthChecked) {
						player.getPackets().sendGameMessage("You examine the tree for signs of disease and find that it is in perfect health.");
						player.getSkills().addXp(Skills.FARMING, TreePatch.forId(currentSeed).getXp());
						healthChecked = true;
					} else {
						player.getActionManager().setAction(new HarvestAction(player, this));
					}
			} else {
				if (BellPatch.forId(currentSeed) != null) {
					if (player.getEquipment().wearingGloves()) {
						player.getActionManager().setAction(new HarvestAction(player, this));
					} else {
						player.applyHit(new Hit(player, 20, HitLook.POISON_DAMAGE));
						player.setNextForceTalk(new ForceTalk("Ouch!"));
					}
				} else {
					player.getActionManager().setAction(new HarvestAction(player, this));
				}
			}
			updatePatch(player);
			return;
		}
	}
	
	public void handleInspection(Player player) {
		if (!raked) {
			player.getDialogueManager().startDialogue("SimpleMessage", "This patch needs weeding.");
			return;
		}
		if (dead) {
			player.getDialogueManager().startDialogue("SimpleMessage", "This patch is dead. Dig it up and plant a new one.");
			return;
		}
		if (diseased) {
			player.getDialogueManager().startDialogue("SimpleMessage", "This patch is diseased. It needs curing.");
			return;
		}
		if (configIndex == 0) {
			player.getDialogueManager().startDialogue("SimpleMessage", "The patch is cleared and ready for planting.");
			return;
		}
		if (!grown) {
			switch(state) {
			case 0:
				player.getDialogueManager().startDialogue("SimpleMessage", "Some "+ItemDefinitions.getItemDefinitions(currentSeed).getName().toLowerCase()+" have just been planted here.");
				break;
			default:
				player.getDialogueManager().startDialogue("SimpleMessage", "Some "+ItemDefinitions.getItemDefinitions(currentSeed).getName().replace(" seed", "").toLowerCase()+" are growing here.");
			}
			updatePatch(player);
			return;
		}
		player.getDialogueManager().startDialogue("SimpleMessage", "This patch looks ready to harvest.");
	}
	
	public void grow() {
		if (currentSeed == -1 && rakedState > 0) {
			rakedState--;
			if (raked)
				raked = false;
			return;
		}
		
		if (BushPatch.forId(currentSeed) != null) {
			if (grown && yield < 4) {
				yield++;
				return;
			}
		}
		if (FruitTreePatch.forId(currentSeed) != null || TreePatch.forId(currentSeed) != null) {
			if (grown && healthChecked && yield < 6) {
				yield++;
				return;
			}
		}
		
		if (grown || currentSeed == -1)
			return;
		
		if (diseased)
			dead = true;
		if (diseased || dead)
			return;
		
		if (state < (maxState-1) && !dead && !diseased && protectedPatch == false) {
			double diseaseChance = 10.0;
			if (watered)
				diseaseChance -= 1.0;
			if (composted == 1)
				diseaseChance -= 1.0;
			if (composted == 2)
				diseaseChance -= 2.0;
			if (Utils.getRandomDouble(100.0) < diseaseChance) {
				diseased = true;
			}
		}
		
		if (state < maxState-1) {
			watered = false;
			state += 1;
		} else if (state == maxState-1) {
			watered = false;
			state += 1;
			grown = true;
		}
	}
	
	public void clear() {
		configIndex = 0;
		rakedState = 3;
		state = 0;
		currentSeed = -1;
		grown = false;
		healthChecked = false;
		raked = true;
		diseased = false;
		dead = false;
		watered = false;
		protectedPatch = false;
	}
	
	public void rake(Player player) {
		switch(rakedState) {
		case 0:
		case 1:
			rakedState++;
			break;
		case 2:
			rakedState++;
			raked = true;
			break;
		default:
			break;
		}
		player.getInventory().addItem(6055, 1);
	}
	
	public void updatePatch(Player player) {
		int finalConfig = 0;
		if (configByFile == -1)
			return;
		if (!raked || configIndex == 0) {
			finalConfig = rakedState;
		} else {
			if (BushPatch.forId(currentSeed) != null) {
				if (!grown)
					finalConfig = configIndex+state;
				else
					finalConfig = configIndex+state+yield;
			} else if (FruitTreePatch.forId(currentSeed) != null) {
				if (healthChecked) {
					finalConfig = FruitTreePatch.forId(currentSeed).getPickFruit()-6+yield;
				} else {
					if (state != maxState)
						finalConfig = configIndex+state;
					else
						finalConfig = FruitTreePatch.forId(currentSeed).getCheckHealth();
				}
			} else if (TreePatch.forId(currentSeed) != null) {
				if (healthChecked && yield > 0) {
					finalConfig = TreePatch.forId(currentSeed).getChopDown();
				} else {
					if (yield == 0)
						finalConfig = TreePatch.forId(currentSeed).getStump();
					else
						finalConfig = configIndex+state;
				}
			} else {
				finalConfig = configIndex+state;
			}
		}
		
		int flag = 0x00;
		if (AllotmentPatch.forId(currentSeed) != null || FlowerPatch.forId(currentSeed) != null || HopPatch.forId(currentSeed) != null) {
			if (diseased)
				flag = 0x02;
			if (watered)
				flag = 0x01;
			if (dead)
				flag = 0x03;
			finalConfig = finalConfig + (flag << 6);
		} else if (TreePatch.forId(currentSeed) != null) {
			if (diseased)
				flag = 0x01;
			if (dead)
				flag = 0x02;
			finalConfig = finalConfig + (flag << 6);
		} else if (FruitTreePatch.forId(currentSeed) != null) {
			if (diseased)
				flag = 12;
			if (dead)
				flag = 12+7;
			finalConfig = finalConfig + flag;
		} else if (HerbPatch.forId(currentSeed) != null) {
			if (diseased)
				flag = 0x01;
			if (dead)
				flag = 0x02;
			if (flag != 0)
				finalConfig = finalConfig + (flag << 7)+1;
		} else if (BushPatch.forId(currentSeed) != null) {
			if (diseased)
				flag = 0x01;
			if (dead)
				flag = 0x02;
			if (flag != 0)
				finalConfig = finalConfig + (flag << 6);
		} else if (VineHerbPatch.forId(currentSeed) != null) {
			if (diseased)
				flag = 27;
			if (dead)
				flag = 39;
			finalConfig = finalConfig + flag;
		}
		
		player.getPackets().sendConfigByFile(configByFile, finalConfig);
	}

	public void handleClear(Player player) {
		if (dead) {
			player.setNextAnimation(new Animation(830));
			clear();
			updatePatch(player);
		}
		if (BushPatch.forId(currentSeed) != null && grown) {
			player.setNextAnimation(new Animation(830));
			clear();
			updatePatch(player);
		}
		if (TreePatch.forId(currentSeed) != null) {
			if (player.getInventory().containsItem(PatchConstants.SPADE, 1)) {
				if (grown) {
					player.setNextAnimation(new Animation(830));
					clear();
					updatePatch(player);
					player.getPackets().sendGameMessage("You clear the tree.");
				} else {
					player.getPackets().sendGameMessage("The tree needs to have grown before you can clear it.");
				}
			} else {
				player.getPackets().sendGameMessage("You need a spade to clear the patch.");
			}
		}
		return;
	}
}

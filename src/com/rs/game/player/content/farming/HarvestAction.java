package com.rs.game.player.content.farming;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
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

public class HarvestAction extends Action {
	
	private Patch patch;
	private AllotmentPatch allotment;
	private HerbPatch herb;
	private FlowerPatch flower;
	private VineHerbPatch vineHerb;
	private CactusPatch cactus;
	private MushroomPatch mushroom;
	private FruitTreePatch fruit;
	private HopPatch hop;
	private BellPatch bell;
	private TreePatch tree;
	private BushPatch bush;
	private int produce;
	private int xp;
	private int requiredItem;
	private int animation;
	
	
	public HarvestAction(Player player, Patch patch) {
		this.patch = patch;
		allotment = AllotmentPatch.forId(patch.currentSeed);
		herb = HerbPatch.forId(patch.currentSeed);
		flower = FlowerPatch.forId(patch.currentSeed);
		vineHerb = VineHerbPatch.forId(patch.currentSeed);
		cactus = CactusPatch.forId(patch.currentSeed);
		mushroom = MushroomPatch.forId(patch.currentSeed);
		fruit = FruitTreePatch.forId(patch.currentSeed);
		hop = HopPatch.forId(patch.currentSeed);
		tree = TreePatch.forId(patch.currentSeed);
		bell = BellPatch.forId(patch.currentSeed);
		bush = BushPatch.forId(patch.currentSeed);
		
		if (tree != null) {
			xp = 0;
			produce = tree.getProduceId();
			requiredItem = -1;
		} else if (fruit != null) {
			xp = fruit.getPickXp();
			produce = fruit.getProduceId();
			requiredItem = -1;
		} else if (bush != null) {
			xp = bush.getXp();
			produce = bush.getProduceId();
			requiredItem = -1;
		} else if (allotment != null) {
			xp = allotment.getXp();
			if (patch.yield < 1)
				patch.yield = Utils.random(3, 15);
			produce = allotment.getProduceId();
			requiredItem = PatchConstants.SPADE;
			if (patch.composted != 0) {
				patch.yield += Utils.random(0, 2);
			}
		} else if (hop != null) {
			xp = hop.getXp();
			if (patch.yield < 1)
				patch.yield = Utils.random(3, 35);
			produce = hop.getProduceId();
			requiredItem = PatchConstants.SPADE;
			if (patch.composted != 0) {
				patch.yield += Utils.random(0, 2);
			}
		} else if (herb != null) {
			xp = herb.getXp();
			produce = herb.getProduceId();
			if (patch.yield < 1)
				patch.yield = Utils.random(3, 8);
			requiredItem = -1;
			if (patch.composted != 0) {
				patch.yield += Utils.random(0, 2);
			}
		} else if (flower != null) {
			xp = flower.getXp();
			if (patch.yield < 1)
				patch.yield = 1;
			produce = flower.getProduceId();
			requiredItem = -1;
		} else if (cactus != null) {
			xp = cactus.getXp();
			if (patch.yield < 1)
				patch.yield = 8;
			produce = cactus.getProduceId();
			requiredItem = -1;
			if (patch.composted != 0) {
				patch.yield += Utils.random(0, 2);
			}
		} else if (bell != null) {
			xp = bell.getXp();
			if (patch.yield < 1)
				patch.yield = 1;
			produce = bell.getProduceId();
			requiredItem = -1;
		} else if (mushroom != null) {
			xp = mushroom.getXp();
			if (patch.yield < 1)
				patch.yield = 9;
			produce = mushroom.getProduceId();
			requiredItem = PatchConstants.SPADE;
		} else if (vineHerb != null) {
			xp = vineHerb.getXp();
			if (player.hasJujuGodBoost())
				xp *= 1.10;
			produce = vineHerb.getProduceId();
			if (patch.yield < 1) {
				patch.yield = Utils.random(3, 8);
			}
			requiredItem = -1;
			if (patch.composted != 0) {
				patch.yield += Utils.random(0, 2);
			}
		}
	}
	
	public boolean checkAll(Player player) {
		if (tree != null) {
			animation = Woodcutting.getAxeAnim(player);
			if (animation == -1) {
				player.getPackets().sendGameMessage("You need a hatchet that you are able to use at your Woodcutting level to chop this.");
				return false;
			}
		}
		if (requiredItem != -1 && !player.getInventory().containsItemToolBelt(requiredItem, 1)) {
			player.getPackets().sendGameMessage("You need a "+ItemDefinitions.getItemDefinitions(requiredItem).getName().toLowerCase()+" to harvest your plants.");
			return false;
		}
		if (!player.getInventory().hasFreeSlots()) {
			player.getPackets().sendGameMessage("Your inventory is full.");
			return false;
		}
		if (patch.yield <= 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean start(Player player) {
		if (checkAll(player)) {			
			return true;
		}
		return false;
	}

	@Override
	public boolean process(Player player) {
		if (checkAll(player)) {
			return true;
		}
		return false;
	}

	@Override
	public int processWithDelay(Player player) {
		if (checkAll(player)) {
			if (allotment != null || hop != null) {
				player.setNextAnimation(new Animation(830));
			} else if (cactus != null || fruit != null || bell != null || bush != null) {
				player.setNextAnimation(new Animation(2280));
			} else if (herb != null || vineHerb != null) {
				player.setNextAnimation(new Animation(2282));
			} else if (mushroom != null || flower != null) {
				player.setNextAnimation(new Animation(827));
			} else if (tree != null) {
				player.setNextAnimation(new Animation(animation));
			}
			
			if (produce != 0) {
				if (patch.yield == 1 && tree == null && fruit == null && bush == null) {
					if (player.getInventory().containsItem(18336, 1)) {
						if (Utils.getRandom(10) == 5) {
							player.getPackets().sendGameMessage("You recovered a seed from the patch!");
							player.getInventory().addItem(patch.currentSeed, 1);
						}
					}
				}
				patch.yield--;
				player.getInventory().addItem(produce, 1);
				if (tree != null || fruit != null)
					player.harvestedTrees++;
				if (player.hasJujuFarmingBoost() && (herb != null || vineHerb != null)) {
					if (Utils.getRandom(3) == 2) {
						player.getInventory().addItem(produce, 1);
					}
				}
				if (xp > 0)
					player.getSkills().addXp(Skills.FARMING, xp);
				else
					player.getSkills().addXp(Skills.WOODCUTTING, 10);
				patch.updatePatch(player);
			}
			
			if (patch.yield <= 0) {
				if (fruit == null && tree == null && bush == null) {
					patch.clear();
					patch.updatePatch(player);
				} else {
					player.getPackets().sendGameMessage("This "+bush == null ? "tree" : "bush"+" has nothing more to yield.");
				}
				stop(player);
			}
		}
		return 2;
	}

	@Override
	public void stop(Player player) {
		
	}

}

package com.rs.game.player.actions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.rs.game.Animation;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Misc;

/**
 * 
 * @author Swirl
 *
 */
public class Farming implements Serializable {


	private transient Player player;
	
	private static final long serialVersionUID = 147342384798739L;

	public static int RAKE = 5341;
	public static int SEED_DIBBER = 5343;
	public static int SECATEURS = 5329;
	public static int INFERNO_ADZE = 13661;
	public static int DRAGON_AXE = 6739;
	public static int RUNE_AXE = 1359;
	public static int ADAMANT_AXE = 1357;
	public static int MITHRIL_AXE = 1355;
	public static int BLACK_AXE = 1361;
	public static int STEEL_AXE = 1353;
	public static int IRON_AXE = 1349;
	public static int BRONZE_AXE = 1351;
	public static int SPADE = 952;
	public static int WEEDS = 6055;
	public static final Animation RAKE_ANIMATION = new Animation(2273);
	public static final Animation PLANT_ANIMATION = new Animation(2291);
	public static final Animation PICK_HERB = new Animation(2281);
	public static final Animation PICK_ALLOTMENT = new Animation(830);
	public static final Animation CHOP_TREE = new Animation(867);
	public static final Animation INFERNO_ADZE_CHOP = new Animation(10251);
	public static final Animation DRAGON_AXE_CHOP = new Animation(2846);
	public static final Animation RUNE_AXE_CHOP = new Animation(867);
	public static final Animation ADAMANT_AXE_CHOP = new Animation(869);
	public static final Animation MITHRIL_AXE_CHOP = new Animation(871);
	public static final Animation BLACK_AXE_CHOP = new Animation(873);
	public static final Animation STEEL_AXE_CHOP = new Animation(875);
	public static final Animation IRON_AXE_CHOP = new Animation(877);
	public static final Animation BRONZE_AXE_CHOP = new Animation(879);
	public static boolean raked;
	
	public static enum Patches {
		FALADOR_NORTH_ALLOTMENT(8550, 708),
		FALADOR_SOUTH_ALLOTMENT(8551, 709),
		FALADOR_HERB(8150, 780),
		FALADOR_FLOWER(7847, 728),
		CATHERBY_NORTH_ALLOTMENT(8552, 710),
		CATHERBY_SOUTH_ALLOTMENT(8553, 711),
		CATHERBY_HERB(8151, 781),
		CATHERBY_FLOWER(7848, 729),
		ARDY_NORTH_ALLOTMENT(8554, 712),
		ARDY_SOUTH_ALLOTMENT(8555, 713),
		ARDY_HERB(8152, 782),
		ARDY_FLOWER(7849, 730),
		CANIFIS_NORTH_ALLOTMENT(8556, 714),
		CANIFIS_SOUTH_ALLOTMENT(8557, 715),
		CANIFIS_HERB(8153, 783),
		CANIFIS_FLOWER(7850, 731),
		LUMMY_TREE_PATCH(8391, 703),
		VARROCK_TREE_PATCH(8390, 702),
		FALADOR_TREE_PATCH(8389, 701),
		TAVERLY_TREE_PATCH(8388, 700);
		
		public static Map<Integer, Patches> patches = new HashMap<Integer, Patches>();
		
		public static Patches forId(int id) {
			return patches.get(id);
		}
		
		static {
			for (Patches patch : Patches.values()) {
				patches.put(patch.objectId, patch);
			}
		}
		
		private int objectId;
		private int configId;
		
		Patches(int objectId, int configId) {
			this.objectId = objectId;
			this.configId = configId;
		}
		
		public int getObjectId() {
			return objectId;
		}
		
		public int getConfigId() {
			return configId;
		}
	}
	
	public static enum Trees {
		OAK(5370, 1521, 15, 20, 22, 8, 5, 3692),
		WILLOW(5371, 1519, 30, 33, 35, 15, 7, 5933),
		MAPLE(5372, 1517, 45, 49, 51, 24, 9, -1),
		YEW(5373, 1515, 60, 85, 87, 35, 11, -1),
		MAGIC(5374, 1513, 75, 136, 138, 48, 13, -1);
	
		private int sapling;
		private int product;
		private int req;
		private double plantXp;
		private double harvestXp;
		private int configId;
		private int stages;
		private int branch;
	
	public static Map<Integer, Trees> trees = new HashMap<Integer, Trees>();
	
	public static Trees forId(int id) {
		return trees.get(id);
	}
	
	static {
		for (Trees tree : Trees.values()) {
			trees.put(tree.sapling, tree);
		}
	}
	
		private Trees(int sapling, int product, int req, double plantXp, double harvestXp, int configId, int stages, int branch) {
			this.sapling = sapling;
			this.product = product;
			this.req = req;
			this.plantXp = plantXp;
			this.harvestXp = harvestXp;
			this.configId = configId;
			this.stages = stages;
			this.branch = branch;
		}
	
		public int getSapling() {
			return sapling;
		}
		
		public int getBranch() {
			return branch;
		}
	
		public int getProduct() {
			return product;
		}
	
		public int getReq() {
			return req;
		}
	
		public double getHarvestXp() {
			return harvestXp;
		}
	
		public double getPlantXp() {
			return plantXp;
		}
	
		public int getConfigId() {
			return configId;
		}
		
		public int getStages() {
			return stages;
		}
	}
	
	public static enum Herbs {
		GUAM(5291, 199, 9, 15, 17, 4),
		MARRENTILL(5292, 201, 14, 20, 22, 11),
		TARROMIN(5293, 203, 19, 24, 26, 18),
		HARRALANDER(5294, 205, 26, 30, 32, 25),
		RANARR(5295, 207, 32, 35, 37, 32),
		TOADFLAX(5296, 3049, 38, 40, 42, 39),
		IRIT(5297, 209, 44, 48, 50, 46),
		AVANTOE(5298, 211, 50, 60, 62, 53),
		WERGALI(14870, 14836, 46, 54, 56, 60),
		KWUARM(5299, 213, 56, 69, 78, 69),
		SNAPDRAGON(5300, 3051, 62, 87.5, 98.5, 75),
		CADANTINE(5301, 215, 67, 106.5, 120, 82),
		LANTADYME(5302, 2485, 73, 134.5, 151.5, 89),
		DWARF_WEED(5303, 217, 79, 170.5, 192, 96),
		TORSTOL(5304, 219, 85, 199.5, 22.5, 103),
		FELLSTALK(21621, 21626, 91, 225, 315.6, 103);
		
		private int seed;
		private int product;
		private int req;
		private double plantXp;
		private double harvestXp;
		private int configId;
		
		public static Map<Integer, Herbs> herbs = new HashMap<Integer, Herbs>();
		
		public static Herbs forId(int id) {
			return herbs.get(id);
		}
		
		static {
			for (Herbs herb : Herbs.values()) {
				herbs.put(herb.seed, herb);
			}
		}
		
		private Herbs(int seed, int product, int req, double plantXp, double harvestXp, int configId) {
			this.seed = seed;
			this.product = product;
			this.req = req;
			this.plantXp = plantXp;
			this.harvestXp = harvestXp;
			this.configId = configId;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getProduct() {
			return product;
		}
		
		public int getReq() {
			return req;
		}
		
		public double getHarvestXp() {
			return harvestXp;
		}
		
		public double getPlantXp() {
			return plantXp;
		}
		
		public int getConfigId() {
			return configId;
		}
	}
	
	public static enum Allotments {
		POTATO(5318, 1942, 1, 8, 10, 6, 4),
		ONION(5319, 1957, 5, 12, 14, 13, 4),
		CABBAGE(5324, 1965, 7, 14, 16, 20, 4),
		TOMATOES(5322, 1982, 12, 21, 23, 27, 4),
		SWEETCORN(5320, 5986, 20, 29, 31, 34, 6),
		STRAWERRIES(5323, 5504, 31, 39, 41, 43, 6),
		WATERMELON(5321, 5982, 47, 48.5, 54.5, 52, 8);
		
		private int seed;
		private int product;
		private int req;
		private double plantXp;
		private double harvestXp;
		private int configId;
		private int stages;
		
		public static Map<Integer, Allotments> allotments = new HashMap<Integer, Allotments>();
		
		public static Allotments forId(int id) {
			return allotments.get(id);
		}
		
		static {
			for (Allotments allotment : Allotments.values()) {
				allotments.put(allotment.seed, allotment);
			}
		}
		
		private Allotments(int seed, int product, int req, double plantXp, double harvestXp, int configId, int stages) {
			this.seed = seed;
			this.product = product;
			this.req = req;
			this.plantXp = plantXp;
			this.harvestXp = harvestXp;
			this.configId = configId;
			this.stages = stages;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getProduct() {
			return product;
		}
		
		public int getReq() {
			return req;
		}
		
		public double getHarvestXp() {
			return harvestXp;
		}
		
		public double getPlantXp() {
			return plantXp;
		}
		
		public int getConfigId() {
			return configId;
		}
		
		public int getStages() {
			return stages;
		}
	}
	
	public static enum Flowers {
		MARIGOLD(5096, 6010, 2, 8.5, 12, 8, 4),
		ROSEMARY(5097, 6014, 11, 12, 15, 13, 4),
		NASTURTIUM(5098, 6012, 24, 19.5, 20, 18, 4),
		WOAD(5099, 1793, 25, 20.5, 22, 23, 4),
		LIMPWURT(5100, 225, 26, 21.5, 24.5, 28, 5),
		LILY(14589, 14583, 52, 70, 74.8, 37, 4);
		
		private int seed;
		private int product;
		private int req;
		private double plantXp;
		private double harvestXp;
		private int configId;
		private int stages;
		
		public static Map<Integer, Flowers> flowers = new HashMap<Integer, Flowers>();
		
		public static Flowers forId(int id) {
			return flowers.get(id);
		}
		
		static {
			for (Flowers flower : Flowers.values()) {
				flowers.put(flower.seed, flower);
			}
		}
		
		private Flowers(int seed, int product, int req, double plantXp, double harvestXp, int configId, int stages) {
			this.seed = seed;
			this.product = product;
			this.req = req;
			this.plantXp = plantXp;
			this.harvestXp = harvestXp;
			this.configId = configId;
			this.stages = stages;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getProduct() {
			return product;
		}
		
		public int getReq() {
			return req;
		}
		
		public double getHarvestXp() {
			return harvestXp;
		}
		
		public double getPlantXp() {
			return plantXp;
		}
		
		public int getConfigId() {
			return configId;
		}
		
		public int getStages() {
			return stages;
		}
	}
	
	/**
	 * Grows the weeds back into the patch
	 * @param player
	 * @param objectId
	 */
	
	public static void growWeeds(final Player player, final int objectId) {
		WorldTasksManager.schedule(new WorldTask() {
			int stage;
			Patches patch = Patches.forId(objectId);
			
			@Override
			public void run() {
				if (stage == 0) {
					player.getPackets().sendConfigByFile(patch.getConfigId(), 2);
				} else if (stage == 3) {
					player.getPackets().sendConfigByFile(patch.getConfigId(), 1);
				} else if (stage == 6) {
					player.getPackets().sendConfigByFile(patch.getConfigId(), -1);
					
					if (objectId == Patches.FALADOR_FLOWER.getObjectId()) {
						player.setFaladorFlowerPatchRaked(false);
					}
					if (objectId == Patches.FALADOR_HERB.getObjectId()) {
						player.setFaladorHerbPatchRaked(false);
					}
					if (objectId == Patches.FALADOR_NORTH_ALLOTMENT.getObjectId()) {
						player.setFaladorNorthAllotmentPatchRaked(false);
					}
					if (objectId == Patches.FALADOR_SOUTH_ALLOTMENT.getObjectId()) {
						player.setFaladorSouthAllotmentPatchRaked(false);
					}
					
					//Catherby
					
					if (objectId == Patches.CATHERBY_FLOWER.getObjectId()) {
						player.setCatherbyFlowerPatchRaked(false);
					}
					if (objectId == Patches.CATHERBY_HERB.getObjectId()) {
						player.setCatherbyHerbPatchRaked(false);
					}
					if (objectId == Patches.CATHERBY_NORTH_ALLOTMENT.getObjectId()) {
						player.setCatherbyNorthAllotmentPatchRaked(false);
					}
					if (objectId == Patches.CATHERBY_SOUTH_ALLOTMENT.getObjectId()) {
						player.setCatherbySouthAllotmentPatchRaked(false);
					}
					
					//Ardougne
					
					if (objectId == Patches.ARDY_FLOWER.getObjectId()) {
						player.setArdougneFlowerPatchRaked(false);
					}
					if (objectId == Patches.ARDY_HERB.getObjectId()) {
						player.setArdougneHerbPatchRaked(false);
					}
					if (objectId == Patches.ARDY_NORTH_ALLOTMENT.getObjectId()) {
						player.setArdougneNorthAllotmentPatchRaked(false);
					}
					if (objectId == Patches.ARDY_SOUTH_ALLOTMENT.getObjectId()) {
						player.setArdougneSouthAllotmentPatchRaked(false);
					}
					
					//Canifis
					
					if (objectId == Patches.CANIFIS_FLOWER.getObjectId()) {
						player.setCanifisFlowerPatchRaked(false);
					}
					if (objectId == Patches.CANIFIS_HERB.getObjectId()) {
						player.setCanifisHerbPatchRaked(false);
					}
					if (objectId == Patches.CANIFIS_NORTH_ALLOTMENT.getObjectId()) {
						player.setCanifisNorthAllotmentPatchRaked(false);
					}
					if (objectId == Patches.CANIFIS_SOUTH_ALLOTMENT.getObjectId()) {
						player.setCanifisSouthAllotmentPatchRaked(false);
					}
					
					//Lumbridge
					
					if (objectId == Patches.LUMMY_TREE_PATCH.getObjectId()) {
						player.setLummyTreePatchRaked(false);
					}
					
					//Varrock
					
					if (objectId == Patches.VARROCK_TREE_PATCH.getObjectId()) {
						player.setVarrockTreePatchRaked(false);
					}
					
					//Falador
					
					if (objectId == Patches.FALADOR_TREE_PATCH.getObjectId()) {
						player.setFaladorTreePatchRaked(false);
					}
					
					//Taverly
					
					if (objectId == Patches.TAVERLY_TREE_PATCH.getObjectId()) {
						player.setTaverlyTreePatchRaked(false);
					}
				}
				stage ++;
			}
		}, 0, 1);
	}
	
	/**
	 * Starts the player to rake the selected patch
	 * @param player
	 * @param objectId
	 */
	
	public static void startRake(final Player player, final int objectId) {
		if (player.getInventory().getFreeSlots() < 3) {
			player.getPackets().sendGameMessage("You don't have enough inventory space.");
		}
		else if (player.getInventory().containsItem(RAKE, 1)) {
			WorldTasksManager.schedule(new WorldTask() {
				int stage;
				Patches patch = Patches.forId(objectId);
				
				@Override
				public void run() {
					if (stage == 0) {
						player.lock();
						player.setNextAnimation(RAKE_ANIMATION);
						player.getPackets().sendConfigByFile(patch.getConfigId(), 1);
						player.getInventory().addItem(WEEDS, 1);
					} else if (stage == 3) {
						player.setNextAnimation(RAKE_ANIMATION);
						player.getPackets().sendConfigByFile(patch.getConfigId(), 2);
						player.getInventory().addItem(WEEDS, 1);
					} else if (stage == 6) {
						player.setNextAnimation(RAKE_ANIMATION);
						player.getPackets().sendConfigByFile(patch.getConfigId(), 3);
						player.getInventory().addItem(WEEDS, 1);
						player.unlock();
						
						//Falador
						
						if (objectId == Patches.FALADOR_FLOWER.getObjectId()) {
							player.setFaladorFlowerPatchRaked(true);
						}
						if (objectId == Patches.FALADOR_HERB.getObjectId()) {
							player.setFaladorHerbPatchRaked(true);
						}
						if (objectId == Patches.FALADOR_NORTH_ALLOTMENT.getObjectId()) {
							player.setFaladorNorthAllotmentPatchRaked(true);
						}
						if (objectId == Patches.FALADOR_SOUTH_ALLOTMENT.getObjectId()) {
							player.setFaladorSouthAllotmentPatchRaked(true);
						}
						
						//Catherby
						
						if (objectId == Patches.CATHERBY_FLOWER.getObjectId()) {
							player.setCatherbyFlowerPatchRaked(true);
						}
						if (objectId == Patches.CATHERBY_HERB.getObjectId()) {
							player.setCatherbyHerbPatchRaked(true);
						}
						if (objectId == Patches.CATHERBY_NORTH_ALLOTMENT.getObjectId()) {
							player.setCatherbyNorthAllotmentPatchRaked(true);
						}
						if (objectId == Patches.CATHERBY_SOUTH_ALLOTMENT.getObjectId()) {
							player.setCatherbySouthAllotmentPatchRaked(true);
						}
						
						//Ardougne
						
						if (objectId == Patches.ARDY_FLOWER.getObjectId()) {
							player.setArdougneFlowerPatchRaked(true);
						}
						if (objectId == Patches.ARDY_HERB.getObjectId()) {
							player.setArdougneHerbPatchRaked(true);
						}
						if (objectId == Patches.ARDY_NORTH_ALLOTMENT.getObjectId()) {
							player.setArdougneNorthAllotmentPatchRaked(true);
						}
						if (objectId == Patches.ARDY_SOUTH_ALLOTMENT.getObjectId()) {
							player.setArdougneSouthAllotmentPatchRaked(true);
						}
						
						//Canifis
						
						if (objectId == Patches.CANIFIS_FLOWER.getObjectId()) {
							player.setCanifisFlowerPatchRaked(true);
						}
						if (objectId == Patches.CANIFIS_HERB.getObjectId()) {
							player.setCanifisHerbPatchRaked(true);
						}
						if (objectId == Patches.CANIFIS_NORTH_ALLOTMENT.getObjectId()) {
							player.setCanifisNorthAllotmentPatchRaked(true);
						}
						if (objectId == Patches.CANIFIS_SOUTH_ALLOTMENT.getObjectId()) {
							player.setCanifisSouthAllotmentPatchRaked(true);
						}
						
						//Lumbridge
						
						if (objectId == Patches.LUMMY_TREE_PATCH.getObjectId()) {
							player.setLummyTreePatchRaked(true);
						}
						
						//Varrock
						
						if (objectId == Patches.VARROCK_TREE_PATCH.getObjectId()) {
							player.setVarrockTreePatchRaked(true);
						}
						
						//Falador
						
						if (objectId == Patches.FALADOR_TREE_PATCH.getObjectId()) {
							player.setFaladorTreePatchRaked(true);
						}
						
						//Taverly
						
						if (objectId == Patches.TAVERLY_TREE_PATCH.getObjectId()) {
							player.setTaverlyTreePatchRaked(true);
						}
						
					}
					stage++;
				}
			}, 0, 1);
		} else {
			player.getPackets().sendGameMessage("You need a rake to rake these weeds.");
			return;
		}
	}
	
	/**
	 * Plants the tree sapling in the selected patch
	 * @param player
	 * @param sapling
	 * @param objectId
	 */
	
	public static void plantTreeSapling(final Player player, final int sapling, final int objectId) {
		final Trees tree = Trees.forId(sapling);
		final Patches patch = Patches.forId(objectId);
		if (player.getSkills().getLevelForXp(Skills.FARMING) < tree.getReq()) {
			player.getPackets().sendGameMessage("You need a Farming level of " + tree.getReq() + " to plant this.");
			return;
		}
		if (player.getInventory().containsItem(SEED_DIBBER, 1)) {
			if (Trees.trees.containsKey(sapling)) {
				WorldTasksManager.schedule(new WorldTask() {
					int stage;
					
					@Override
					public void run() {
						if (stage == 0) {
							player.lock();
							player.setNextAnimation(PLANT_ANIMATION);
							if (objectId == Patches.LUMMY_TREE_PATCH.getObjectId()) {
								player.setLummyTreePatch(tree.getSapling());
							}
							if (objectId == Patches.VARROCK_TREE_PATCH.getObjectId()) {
								player.setVarrockTreePatch(tree.getSapling());
							}
							if (objectId == Patches.FALADOR_TREE_PATCH.getObjectId()) {
								player.setFaladorTreePatch(tree.getSapling());
							}
							if (objectId == Patches.TAVERLY_TREE_PATCH.getObjectId()) {
								player.setTaverlyTreePatch(tree.getSapling());
							}
							player.getSkills().addXp(Skills.FARMING, tree.getPlantXp());
							player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId());
							player.getInventory().deleteItem(tree.getSapling(), 1);
							player.unlock();
							} else if (stage == 6 && tree.getStages() >= 1) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 1);
							} else if (stage == 12 && tree.getStages() >= 2) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 2);
							} else if (stage == 18 && tree.getStages() >= 3) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 3);
							} else if (stage == 24 && tree.getStages() >= 4) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 4);
							} else if (stage == 30 && tree.getStages() >= 5) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 5);
							} else if (stage == 36 && tree.getStages() >= 6) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 6);
							} else if (stage == 42 && tree.getStages() >= 7) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 7);
							} else if (stage == 48 && tree.getStages() >= 8) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 8);
							} else if (stage == 54 && tree.getStages() >= 9) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 9);
							} else if (stage == 60 && tree.getStages() >= 10) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 10);
							} else if (stage == 66 && tree.getStages() >= 11) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 11);
							} else if (stage == 72 && tree.getStages() >= 12) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 12);
							} else if (stage == 78 && tree.getStages() >= 13) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 13);
							} else if (stage == 84 && tree.getStages() >= 14) {
								player.getPackets().sendConfigByFile(patch.getConfigId(), tree.getConfigId() + 14);
							}
							stage++;
						}
					}, 0, 1);
				} else {
					player.getPackets().sendGameMessage("You can't plant that here.");
				return;
			}
		} else {
			player.getPackets().sendGameMessage("You need a seed dibber to plant this sapling.");
		return;
		}
	}
	
	/**
	 * Plants the herb seed into the selected patch
	 * @param player
	 * @param seed
	 * @param objectId
	 */
	
	public static void plantHerbSeed(final Player player, final int seed, final int objectId) {
		final Herbs herb = Herbs.forId(seed);
		final Patches patch = Patches.forId(objectId);
		if (player.getSkills().getLevelForXp(Skills.FARMING) < herb.getReq()) {
			player.getPackets().sendGameMessage("You need a Farming level of " + herb.getReq() + " to plant this.");
			return;
		}
		if (player.getInventory().containsItem(SEED_DIBBER, 1)) {
			if (Herbs.herbs.containsKey(seed)) {
				WorldTasksManager.schedule(new WorldTask() {
					int stage;
					
					@Override
					public void run() {
						if (stage == 0) {
							player.lock();
							player.setNextAnimation(PLANT_ANIMATION);
							if (objectId == Patches.FALADOR_HERB.getObjectId()) {
								player.setFaladorHerbPatch(herb.getSeed());
							}
							if (objectId == Patches.CATHERBY_HERB.getObjectId()) {
								player.setCatherbyHerbPatch(herb.getSeed());
							}
							if (objectId == Patches.ARDY_HERB.getObjectId()) {
								player.setArdougneHerbPatch(herb.getSeed());
							}
							if (objectId == Patches.CANIFIS_HERB.getObjectId()) {
								player.setCanifisHerbPatch(herb.getSeed());
							}
							player.getSkills().addXp(Skills.FARMING, herb.getPlantXp());
							player.getPackets().sendConfigByFile(patch.getConfigId(), herb.getConfigId());
							player.getInventory().deleteItem(herb.getSeed(), 1);
							player.unlock();
						} else if (stage == 6) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), herb.getConfigId() + 1);
						} else if (stage == 12) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), herb.getConfigId() + 2);
						} else if (stage == 18) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), herb.getConfigId() + 3);
						} else if (stage == 24) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), herb.getConfigId() + 4);
						}
						stage++;
					}
				}, 0, 1);
			} else {
				player.getPackets().sendGameMessage("You can't plant that here.");
				return;
			}
		} else {
			player.getPackets().sendGameMessage("You need a seed dibber to plant these seeds.");
			return;
		}
	}
	
	/**
	 * Plants the seed into the selected patch
	 * @param player
	 * @param seed
	 * @param objectId
	 */
	
	public static void plantAllotmentSeed(final Player player, final int seed, final int objectId) {
		final Allotments allotment = Allotments.forId(seed);
		final Patches patch = Patches.forId(objectId);
		if (player.getSkills().getLevelForXp(Skills.FARMING) < allotment.getReq()) {
			player.getPackets().sendGameMessage("You need a Farming level of " + allotment.getReq() + " to plant this.");
			return;
		}
		if (player.getInventory().containsItem(SEED_DIBBER, 1) && player.getInventory().containsItem(allotment.getSeed(), 3)) {
			if (Allotments.allotments.containsKey(seed)) {
				WorldTasksManager.schedule(new WorldTask() {
					int stage;
					
					@Override
					public void run() {
						if (stage == 0) {
							player.lock();
							player.setNextAnimation(PLANT_ANIMATION);
							if (objectId == Patches.FALADOR_NORTH_ALLOTMENT.getObjectId()) {
								player.setFaladorNorthAllotmentPatch(allotment.getSeed());
							} else if (objectId == Patches.FALADOR_SOUTH_ALLOTMENT.getObjectId()) {
								player.setFaladorSouthAllotmentPatch(allotment.getSeed());
							}
							if (objectId == Patches.CATHERBY_NORTH_ALLOTMENT.getObjectId()) {
								player.setCatherbyNorthAllotmentPatch(allotment.getSeed());
							} else if (objectId == Patches.CATHERBY_SOUTH_ALLOTMENT.getObjectId()) {
								player.setCatherbySouthAllotmentPatch(allotment.getSeed());
							}
							if (objectId == Patches.ARDY_NORTH_ALLOTMENT.getObjectId()) {
								player.setArdougneNorthAllotmentPatch(allotment.getSeed());
							} else if (objectId == Patches.ARDY_SOUTH_ALLOTMENT.getObjectId()) {
								player.setArdougneSouthAllotmentPatch(allotment.getSeed());
							}
							if (objectId == Patches.CANIFIS_NORTH_ALLOTMENT.getObjectId()) {
								player.setCanifisNorthAllotmentPatch(allotment.getSeed());
							} else if (objectId == Patches.CANIFIS_SOUTH_ALLOTMENT.getObjectId()) {
								player.setCanifisSouthAllotmentPatch(allotment.getSeed());
							}
							player.getSkills().addXp(Skills.FARMING, allotment.getPlantXp());
							player.getPackets().sendConfigByFile(patch.getConfigId(), allotment.getConfigId());
							player.getInventory().deleteItem(allotment.getSeed(), 3);
							player.unlock();
						} else if (stage == 6 && allotment.getStages() >= 1) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), allotment.getConfigId() + 1);
						} else if (stage == 12 && allotment.getStages() >= 2) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), allotment.getConfigId() + 2);
						} else if (stage == 18 && allotment.getStages() >= 3) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), allotment.getConfigId() + 3);
						} else if (stage == 24 && allotment.getStages() >= 4) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), allotment.getConfigId() + 4);
						} else if (stage == 30 && allotment.getStages() >= 5) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), allotment.getConfigId() + 5);
						} else if (stage == 36 && allotment.getStages() >= 6) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), allotment.getConfigId() + 6);
						} else if (stage == 42 && allotment.getStages() >= 7) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), allotment.getConfigId() + 7);
						} else if (stage == 48 && allotment.getStages() >= 8) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), allotment.getConfigId() + 8);
						}
						stage++;
					}
				}, 0, 1);
			} else {
				player.getPackets().sendGameMessage("You can't plant that here.");
				return;
			}
		} else if (!player.getInventory().containsItem(SEED_DIBBER, 1)) {
			player.getPackets().sendGameMessage("You need a seed dibber to plant these seeds.");
			return;
		} else if (!player.getInventory().containsItem(allotment.getSeed(), 3)) {
			player.getPackets().sendGameMessage("You need atleast three seeds to plant.");
			return;
		}
	}
	
	/**
	 * Plants the flower seed into the allotment
	 * @param player
	 * @param seed
	 * @param objectId
	 */
	
	public static void plantFlowerSeed(final Player player, final int seed, final int objectId) {
		final Flowers flower = Flowers.forId(seed);
		final Patches patch = Patches.forId(objectId);
		if (player.getSkills().getLevelForXp(Skills.FARMING) < flower.getReq()) {
			player.getPackets().sendGameMessage("You need a Farming level of " + flower.getReq() + " to plant this.");
			return;
		}
		if (player.getInventory().containsItem(SEED_DIBBER, 1)) {
			if (Flowers.flowers.containsKey(seed)) {
				WorldTasksManager.schedule(new WorldTask() {
					int stage;
					
					@Override
					public void run() {
						if (stage == 0) {
							player.lock();
							player.setNextAnimation(PLANT_ANIMATION);
							if (objectId == Patches.FALADOR_FLOWER.getObjectId()) {
								player.setFaladorFlowerPatch(flower.getSeed());
							}
							if (objectId == Patches.CATHERBY_FLOWER.getObjectId()) {
								player.setCatherbyFlowerPatch(flower.getSeed());
							}
							if (objectId == Patches.ARDY_FLOWER.getObjectId()) {
								player.setArdougneFlowerPatch(flower.getSeed());
							}
							if (objectId == Patches.CANIFIS_FLOWER.getObjectId()) {
								player.setCanifisFlowerPatch(flower.getSeed());
							}
							player.getSkills().addXp(Skills.FARMING, flower.getPlantXp());
							player.getPackets().sendConfigByFile(patch.getConfigId(), flower.getConfigId());
							player.getInventory().deleteItem(flower.getSeed(), 1);
							player.unlock();
						} else if (stage == 6 && flower.getStages() >= 1) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), flower.getConfigId() + 1);
						} else if (stage == 12 && flower.getStages() >= 2) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), flower.getConfigId() + 2);
						} else if (stage == 18 && flower.getStages() >= 3) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), flower.getConfigId() + 3);
						} else if (stage == 24 && flower.getStages() >= 4) {
							player.getPackets().sendConfigByFile(patch.getConfigId(), flower.getConfigId() + 4);
						}
						stage++;
					}
				}, 0, 1);
			} else {
				player.getPackets().sendGameMessage("You can't plant that here.");
				return;
			}
		} else {
			player.getPackets().sendGameMessage("You need a seed dibber to plant these seeds.");
			return;
		}
	}
	
	/**
	 * Harvests tree saplings from tree patches
	 * @param player
	 * @param objectId
	 * @param sapling
	 */
	
	public static void harvestTreeSapling(final Player player, final int objectId, int sapling) {
		final Trees tree = Trees.forId(sapling);
		int weaponId = player.getEquipment().getWeaponId();
		if (player.getInventory().getFreeSlots() < 3) {
			player.getPackets().sendGameMessage("You need atleast three free inventory spaces to harvest.");
		} else if (weaponId != -1) {
			switch (weaponId) {
			
			case 13661:
				player.setNextAnimation(INFERNO_ADZE_CHOP);
				break;
				
			case 6739:
				player.setNextAnimation(DRAGON_AXE_CHOP);
				break;
				
			case 1359:
				player.setNextAnimation(RUNE_AXE_CHOP);
				break;
				
			case 1357:
				player.setNextAnimation(ADAMANT_AXE_CHOP);
				break;
				
			case 1355:
				player.setNextAnimation(MITHRIL_AXE_CHOP);
				break;
				
			case 1361:
				player.setNextAnimation(BLACK_AXE_CHOP);
				break;
				
			case 1353:
				player.setNextAnimation(STEEL_AXE_CHOP);
				break;
				
			case 1349:
				player.setNextAnimation(IRON_AXE_CHOP);
				break;
				
			case 1351:
				player.setNextAnimation(BRONZE_AXE_CHOP);
				break;
			}
			int amount = 3;
			double xp = amount * tree.getHarvestXp();
			if (amount < 3 && player.getInventory().getFreeSlots() <= 3) {
				player.getSkills().addXp(Skills.FARMING, xp * 3);
				if (player.getInventory().containsItem(18336, 1)) {
					int chance = Misc.random(20);
					if (chance == 1) {
						player.getInventory().addItem(tree.getSapling(), 1);
					}
				}
				player.getInventory().addItem(tree.getProduct(), 3);
				player.getInventory().addItem(tree.getBranch(), 1);
				growWeeds(player, objectId);
				if (objectId == Patches.LUMMY_TREE_PATCH.getObjectId()) {
					growWeeds(player, objectId);
					player.setLummyTreePatch(-1);
					player.setLummyTreePatchRaked(false);
				}
				if (objectId == Patches.VARROCK_TREE_PATCH.getObjectId()) {
					growWeeds(player, objectId);
					player.setVarrockTreePatch(-1);
					player.setVarrockTreePatchRaked(false);
				}
				if (objectId == Patches.FALADOR_TREE_PATCH.getObjectId()) {
					growWeeds(player, objectId);
					player.setFaladorTreePatch(-1);
					player.setFaladorTreePatchRaked(false);
				}
				if (objectId == Patches.TAVERLY_TREE_PATCH.getObjectId()) {
					growWeeds(player, objectId);
					player.setTaverlyTreePatch(-1);
					player.setTaverlyTreePatchRaked(false);
				}
			} else if (player.getInventory().getFreeSlots() < amount) {
				amount = player.getInventory().getFreeSlots();
				player.getSkills().addXp(Skills.FARMING, xp);
				if (player.getInventory().containsItem(18336, 1)) {
					int chance = Misc.random(20);
					if (chance == 1) {
						player.getInventory().addItem(tree.getSapling(), 1);
					}
				}
				player.getInventory().addItem(tree.getProduct(), amount);
				growWeeds(player, objectId);
				if (objectId == Patches.LUMMY_TREE_PATCH.getObjectId()) {
					growWeeds(player, objectId);
					player.setLummyTreePatch(-1);
					player.setLummyTreePatchRaked(false);
				}
				if (objectId == Patches.VARROCK_TREE_PATCH.getObjectId()) {
					growWeeds(player, objectId);
					player.setVarrockTreePatch(-1);
					player.setVarrockTreePatchRaked(false);
				}
				if (objectId == Patches.FALADOR_TREE_PATCH.getObjectId()) {
					growWeeds(player, objectId);
					player.setFaladorTreePatch(-1);
					player.setFaladorTreePatchRaked(false);
				}
				if (objectId == Patches.TAVERLY_TREE_PATCH.getObjectId()) {
					growWeeds(player, objectId);
					player.setTaverlyTreePatch(-1);
					player.setTaverlyTreePatchRaked(false);
				}
			} else {
				player.getSkills().addXp(Skills.FARMING, xp);
				if (player.getInventory().containsItem(18336, 1)) {
					int chance = Misc.random(20);
					if (chance == 1) {
						player.getInventory().addItem(tree.getSapling(), 1);
					}
				}
				player.getInventory().addItem(tree.getProduct(), amount);
				growWeeds(player, objectId);
				if (objectId == Patches.LUMMY_TREE_PATCH.getObjectId()) {
					growWeeds(player, objectId);
					player.setLummyTreePatch(-1);
					player.setLummyTreePatchRaked(false);
				}
				if (objectId == Patches.VARROCK_TREE_PATCH.getObjectId()) {
					growWeeds(player, objectId);
					player.setVarrockTreePatch(-1);
					player.setVarrockTreePatchRaked(false);
				}
				if (objectId == Patches.FALADOR_TREE_PATCH.getObjectId()) {
					growWeeds(player, objectId);
					player.setFaladorTreePatch(-1);
					player.setFaladorTreePatchRaked(false);
				}
				if (objectId == Patches.TAVERLY_TREE_PATCH.getObjectId()) {
					growWeeds(player, objectId);
					player.setTaverlyTreePatch(-1);
					player.setTaverlyTreePatchRaked(false);
				}
			}
		} else {
			player.getPackets().sendGameMessage("You need a hatchet to chop down this tree.");
			return;
		}
	}
	
	/**
	 * Harvests the herbs from the patch.
	 * @param player
	 * @param objectId
	 * @param seed
	 */
	
	public static void harvestHerb(final Player player, final int objectId, int seed) {
		final Herbs herb = Herbs.forId(seed);
		if (player.getInventory().getFreeSlots() < 3) {
			player.getPackets().sendGameMessage("You need atleast three free inventory spaces to harvest.");
		} else if (player.getInventory().containsItem(SECATEURS, 1)) {
			int amount = 3;
			double xp = amount * herb.getHarvestXp();
			if (amount < 3 && player.getInventory().getFreeSlots() <= 3) {
				player.setNextAnimation(PICK_HERB);
				player.getSkills().addXp(Skills.FARMING, xp * 3);
				if (player.getInventory().containsItem(18336, 1)) {
					int chance = Misc.random(10);
					if (chance == 1) {
						player.getInventory().addItem(herb.getSeed(), 1);
					}
				}
				player.getInventory().addItem(herb.getProduct(), 3);
				growWeeds(player, objectId);
				if (objectId == Patches.FALADOR_HERB.getObjectId()) {
					player.setFaladorHerbPatch(-1);
					player.setFaladorHerbPatchRaked(false);
				}
				if (objectId == Patches.CATHERBY_HERB.getObjectId()) {
					player.setCatherbyHerbPatch(-1);
					player.setCatherbyHerbPatchRaked(false);
				}
				if (objectId == Patches.ARDY_HERB.getObjectId()) {
					player.setArdougneHerbPatch(-1);
					player.setArdougneHerbPatchRaked(false);
				}
				if (objectId == Patches.CANIFIS_HERB.getObjectId()) {
					player.setCanifisHerbPatch(-1);
					player.setCanifisHerbPatchRaked(false);
				}
			} else if (player.getInventory().getFreeSlots() < amount) {
				amount = player.getInventory().getFreeSlots();
				player.setNextAnimation(PICK_HERB);
				player.getSkills().addXp(Skills.FARMING, xp);
				if (player.getInventory().containsItem(18336, 1)) {
					int chance = Misc.random(10);
					if (chance == 1) {
						player.getInventory().addItem(herb.getSeed(), 1);
					}
				}
				player.getInventory().addItem(herb.getProduct(), amount);
				growWeeds(player, objectId);
				if (objectId == Patches.FALADOR_HERB.getObjectId()) {
					player.setFaladorHerbPatch(-1);
					player.setFaladorHerbPatchRaked(false);
				}
				if (objectId == Patches.CATHERBY_HERB.getObjectId()) {
					player.setCatherbyHerbPatch(-1);
					player.setCatherbyHerbPatchRaked(false);
				}
				if (objectId == Patches.ARDY_HERB.getObjectId()) {
					player.setArdougneHerbPatch(-1);
					player.setArdougneHerbPatchRaked(false);
				}
				if (objectId == Patches.CANIFIS_HERB.getObjectId()) {
					player.setCanifisHerbPatch(-1);
					player.setCanifisHerbPatchRaked(false);
				}
			} else {
				player.setNextAnimation(PICK_HERB);
				player.getSkills().addXp(Skills.FARMING, xp);
				if (player.getInventory().containsItem(18336, 1)) {
					int chance = Misc.random(10);
					if (chance == 1) {
						player.getInventory().addItem(herb.getSeed(), 1);
					}
				}
				player.getInventory().addItem(herb.getProduct(), amount);
				growWeeds(player, objectId);
				if (objectId == Patches.FALADOR_HERB.getObjectId()) {
					player.setFaladorHerbPatch(-1);
					player.setFaladorHerbPatchRaked(false);
				}
				if (objectId == Patches.CATHERBY_HERB.getObjectId()) {
					player.setCatherbyHerbPatch(-1);
					player.setCatherbyHerbPatchRaked(false);
				}
				if (objectId == Patches.ARDY_HERB.getObjectId()) {
					player.setArdougneHerbPatch(-1);
					player.setArdougneHerbPatchRaked(false);
				}
				if (objectId == Patches.CANIFIS_HERB.getObjectId()) {
					player.setCanifisHerbPatch(-1);
					player.setCanifisHerbPatchRaked(false);
				}
			}
		} else {
			player.getPackets().sendGameMessage("You need secateurs to harvest these herbs.");
			return;
		}
	}
	
	/**
	 * Harvests the seed from allotments
	 * @param player
	 * @param objectId
	 * @param seed
	 */
	
	public static void harvestAllotment(final Player player, final int objectId, int seed) {
		final Allotments allotment = Allotments.forId(seed);
		if (player.getInventory().getFreeSlots() < 3) {
			player.getPackets().sendGameMessage("You need atleast three free inventory spaces to harvest.");
		} else if (player.getInventory().containsItem(SPADE, 1)) {
			int amount = 3;
			double xp = amount * allotment.getHarvestXp();
			if (amount < 3 && player.getInventory().getFreeSlots() <= 3) {
				player.setNextAnimation(PICK_ALLOTMENT);
				player.getSkills().addXp(Skills.FARMING, xp * 3);
				if (player.getInventory().containsItem(18336, 1)) {
					int chance = Misc.random(10);
					if (chance == 1) {
						player.getInventory().addItem(allotment.getSeed(), 1);
					}
				}
				player.getInventory().addItem(allotment.getProduct(), 3);
				growWeeds(player, objectId);
				if (objectId == Patches.FALADOR_NORTH_ALLOTMENT.getObjectId()) {
					player.setFaladorNorthAllotmentPatch(-1);
					player.setFaladorNorthAllotmentPatchRaked(false);
				} else if (objectId == Patches.FALADOR_SOUTH_ALLOTMENT.getObjectId()) {
					player.setFaladorSouthAllotmentPatch(-1);
					player.setFaladorSouthAllotmentPatchRaked(false);
				}
				if (objectId == Patches.CATHERBY_NORTH_ALLOTMENT.getObjectId()) {
					player.setCatherbyNorthAllotmentPatch(-1);
					player.setCatherbyNorthAllotmentPatchRaked(false);
				} else if (objectId == Patches.CATHERBY_SOUTH_ALLOTMENT.getObjectId()) {
					player.setCatherbySouthAllotmentPatch(-1);
					player.setCatherbySouthAllotmentPatchRaked(false);
				}
				if (objectId == Patches.ARDY_NORTH_ALLOTMENT.getObjectId()) {
					player.setArdougneNorthAllotmentPatch(-1);
					player.setArdougneNorthAllotmentPatchRaked(false);
				} else if (objectId == Patches.ARDY_SOUTH_ALLOTMENT.getObjectId()) {
					player.setArdougneSouthAllotmentPatch(-1);
					player.setArdougneSouthAllotmentPatchRaked(false);
				}
				if (objectId == Patches.CANIFIS_NORTH_ALLOTMENT.getObjectId()) {
					player.setCanifisNorthAllotmentPatch(-1);
					player.setCanifisNorthAllotmentPatchRaked(false);
				} else if (objectId == Patches.CANIFIS_SOUTH_ALLOTMENT.getObjectId()) {
					player.setCanifisSouthAllotmentPatch(-1);
					player.setCanifisSouthAllotmentPatchRaked(false);
				}
			} else if (player.getInventory().getFreeSlots() < amount) {
				amount = player.getInventory().getFreeSlots();
				player.setNextAnimation(PICK_ALLOTMENT);
				player.getSkills().addXp(Skills.FARMING, xp);
				if (player.getInventory().containsItem(18336, 1)) {
					int chance = Misc.random(10);
					if (chance == 1) {
						player.getInventory().addItem(allotment.getSeed(), 1);
					}
				}
				player.getInventory().addItem(allotment.getProduct(), amount);
				growWeeds(player, objectId);
				if (objectId == Patches.FALADOR_NORTH_ALLOTMENT.getObjectId()) {
					player.setFaladorNorthAllotmentPatch(-1);
					player.setFaladorNorthAllotmentPatchRaked(false);
				} else if (objectId == Patches.FALADOR_SOUTH_ALLOTMENT.getObjectId()) {
					player.setFaladorSouthAllotmentPatch(-1);
					player.setFaladorSouthAllotmentPatchRaked(false);
				}
				if (objectId == Patches.CATHERBY_NORTH_ALLOTMENT.getObjectId()) {
					player.setCatherbyNorthAllotmentPatch(-1);
					player.setCatherbyNorthAllotmentPatchRaked(false);
				} else if (objectId == Patches.CATHERBY_SOUTH_ALLOTMENT.getObjectId()) {
					player.setCatherbySouthAllotmentPatch(-1);
					player.setCatherbySouthAllotmentPatchRaked(false);
				}
				if (objectId == Patches.ARDY_NORTH_ALLOTMENT.getObjectId()) {
					player.setArdougneNorthAllotmentPatch(-1);
					player.setArdougneNorthAllotmentPatchRaked(false);
				} else if (objectId == Patches.ARDY_SOUTH_ALLOTMENT.getObjectId()) {
					player.setArdougneSouthAllotmentPatch(-1);
					player.setArdougneSouthAllotmentPatchRaked(false);
				}
				if (objectId == Patches.CANIFIS_NORTH_ALLOTMENT.getObjectId()) {
					player.setCanifisNorthAllotmentPatch(-1);
					player.setCanifisNorthAllotmentPatchRaked(false);
				} else if (objectId == Patches.CANIFIS_SOUTH_ALLOTMENT.getObjectId()) {
					player.setCanifisSouthAllotmentPatch(-1);
					player.setCanifisSouthAllotmentPatchRaked(false);
				}
			} else {
				player.setNextAnimation(PICK_ALLOTMENT);
				player.getSkills().addXp(Skills.FARMING, xp);
				if (player.getInventory().containsItem(18336, 1)) {
					int chance = Misc.random(10);
					if (chance == 1) {
						player.getInventory().addItem(allotment.getSeed(), 1);
					}
				}
				player.getInventory().addItem(allotment.getProduct(), amount);
				growWeeds(player, objectId);
				if (objectId == Patches.FALADOR_NORTH_ALLOTMENT.getObjectId()) {
					player.setFaladorNorthAllotmentPatch(-1);
					player.setFaladorNorthAllotmentPatchRaked(false);
				} else if (objectId == Patches.FALADOR_SOUTH_ALLOTMENT.getObjectId()) {
					player.setFaladorSouthAllotmentPatch(-1);
					player.setFaladorSouthAllotmentPatchRaked(false);
				}
				if (objectId == Patches.CATHERBY_NORTH_ALLOTMENT.getObjectId()) {
					player.setCatherbyNorthAllotmentPatch(-1);
					player.setCatherbyNorthAllotmentPatchRaked(false);
				} else if (objectId == Patches.CATHERBY_SOUTH_ALLOTMENT.getObjectId()) {
					player.setCatherbySouthAllotmentPatch(-1);
					player.setCatherbySouthAllotmentPatchRaked(false);
				}
				if (objectId == Patches.ARDY_NORTH_ALLOTMENT.getObjectId()) {
					player.setArdougneNorthAllotmentPatch(-1);
					player.setArdougneNorthAllotmentPatchRaked(false);
				} else if (objectId == Patches.ARDY_SOUTH_ALLOTMENT.getObjectId()) {
					player.setArdougneSouthAllotmentPatch(-1);
					player.setArdougneSouthAllotmentPatchRaked(false);
				}
				if (objectId == Patches.CANIFIS_NORTH_ALLOTMENT.getObjectId()) {
					player.setCanifisNorthAllotmentPatch(-1);
					player.setCanifisNorthAllotmentPatchRaked(false);
				} else if (objectId == Patches.CANIFIS_SOUTH_ALLOTMENT.getObjectId()) {
					player.setCanifisSouthAllotmentPatch(-1);
					player.setCanifisSouthAllotmentPatchRaked(false);
				}
			}
		} else {
			player.getPackets().sendGameMessage("You need a spade to harvest this allotment.");
			return;
		}
	}
	
	/**
	 * Harvests flowers from the flower allotment
	 * @param player
	 * @param objectId
	 * @param seed
	 */
	
	public static void harvestFlower(final Player player, final int objectId, int seed) {
		final Flowers flower = Flowers.forId(seed);
		if (player.getInventory().getFreeSlots() < 3) {
			player.getPackets().sendGameMessage("You need atleast three free inventory spaces to harvest.");
		} else if (player.getInventory().containsItem(SPADE, 1)) {
			int amount = 3;
			double xp = amount * flower.getHarvestXp();
			if (player.getInventory().getFreeSlots() < amount) {
				amount = player.getInventory().getFreeSlots();
				player.setNextAnimation(PICK_ALLOTMENT);
				player.getSkills().addXp(Skills.FARMING, xp * 3);
				if (player.getInventory().containsItem(18336, 1)) {
					int chance = Misc.random(10);
					if (chance == 1) {
						player.getInventory().addItem(flower.getSeed(), 1);
					}
				}
				player.getInventory().addItem(flower.getProduct(), amount);
				growWeeds(player, objectId);
				if (objectId == Patches.FALADOR_FLOWER.getObjectId()) {
					player.setFaladorFlowerPatch(-1);
					player.setFaladorFlowerPatchRaked(false);
				}
				if (objectId == Patches.CATHERBY_FLOWER.getObjectId()) {
					player.setCatherbyFlowerPatch(-1);
					player.setCatherbyFlowerPatchRaked(false);
				}
				if (objectId == Patches.ARDY_FLOWER.getObjectId()) {
					player.setArdougneFlowerPatch(-1);
					player.setArdougneFlowerPatchRaked(false);
				}
				if (objectId == Patches.CANIFIS_FLOWER.getObjectId()) {
					player.setCanifisFlowerPatch(-1);
					player.setCanifisFlowerPatchRaked(false);
				}
			} else {
				player.setNextAnimation(PICK_ALLOTMENT);
				player.getSkills().addXp(Skills.FARMING, xp);
				if (player.getInventory().containsItem(18336, 1)) {
					int chance = Misc.random(10);
					if (chance == 1) {
						player.getInventory().addItem(flower.getSeed(), 1);
					}
				}
				player.getInventory().addItem(flower.getProduct(), amount);
				growWeeds(player, objectId);
				if (objectId == Patches.FALADOR_FLOWER.getObjectId()) {
					player.setFaladorFlowerPatch(-1);
					player.setFaladorFlowerPatchRaked(false);
				}
				if (objectId == Patches.CATHERBY_FLOWER.getObjectId()) {
					player.setCatherbyFlowerPatch(-1);
					player.setCatherbyFlowerPatchRaked(false);
				}
				if (objectId == Patches.ARDY_FLOWER.getObjectId()) {
					player.setArdougneFlowerPatch(-1);
					player.setArdougneFlowerPatchRaked(false);
				}
				if (objectId == Patches.CANIFIS_FLOWER.getObjectId()) {
					player.setCanifisFlowerPatch(-1);
					player.setCanifisFlowerPatchRaked(false);
				}
			}
		} else {
			player.getPackets().sendGameMessage("You need a spade to harvest this flower.");
			return;
		}
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
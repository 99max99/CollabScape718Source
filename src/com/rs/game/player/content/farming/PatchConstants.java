package com.rs.game.player.content.farming;

import java.util.HashMap;
import java.util.Map;

public class PatchConstants {
	
	//CONFIG 9867 EDGEVILLE TORN
	
	public final static int SPADE = 952;
	public final static int RAKE = 5341;
	public final static int SEED_DIBBER = 5343;
	public final static int SECATEURS = 5329;
	
	public static int[] produces = { 199, 201, 203, 205, 207, 3049, 209, 211, 213, 3051, 215, 2485, 217, 219, 21626, //herbs
									 1942, 1957, 1965, 1982, 5986, 5504, 5982, //allotments
									 6010, 6014, 6012, 1793, 255, 6055,
									 21622, 6016, //flowers
									 6006, 5994, 5996, 5931, 5998, 6000, 6002, 1955, 1963, 2108, 5970, 2114, 5972, 5974}; //fruit and hops
	
	public enum WorldPatches {
		ARDOUGNE_FLOWER( 7849, 730, 0), 
		CANIFIS_FLOWER(  7850, 731, 1),
		CATHERBY_FLOWER( 7848, 729, 2), 
		FALADOR_FLOWER(  7847, 728, 3), 
		
		ARDOUGNE_HERB(8152, 782, 4), 
		CANIFIS_HERB( 8153, 783, 5),
		CATHERBY_HERB(8151, 781, 6), 
		FALADOR_HERB( 8150, 780, 7), 

		ARDOUGNE_VEGETABLE_1(8554, 712, 8),
		ARDOUGNE_VEGETABLE_2(8555, 713, 9), 
		CANIFIS_VEGETABLE_1(8557, 715, 10), 
		CANIFIS_VEGETABLE_2(8556, 714, 11),
		CATHERBY_VEGETABLE_1(8552, 710, 12), 
		CATHERBY_VEGETABLE_2(8553, 711, 13), 
		FALADOR_VEGETABLE_1(8550, 708, 14),
		FALADOR_VEGETABLE_2(8551, 709, 15),
		
		HABITAT_HERB1(56682, 8350, 16),
		HABITAT_HERB2(56683, 8351, 17),
		HABITAT_BUSH(56562, 8355, 18), //TODO
		HABITAT_FLOWER(56685, 8352, 19), //TODO
		
		MUSHRROM_PATCH1(8337, 746, 20), 
		
		CACTUS_PATCH1(7771, 744, 21),
		
		TROLLHEIM_HERB(18816, 2788, 22),
		
		BRIMHAVEN_FRUIT(7964, 706, 23),
		CATHERBY_FRUIT(7965, 707, 24), 
		GNOME_FRUIT(7962, 704, 25), 
		GNOME_MAZE_FRUIT(7963, 705, 26),
		LLETYA_FRUIT(28919, 4317, 27), 
		HABITAT_FRUIT(56667, 8353, 28),
		
		ENTRANNA_HOPS( 8174, 717, 29),
		LUMBRIDGE_HOPS(8175, 718, 30), 
		WOOD_HOPS(     8176, 719, 31), 
		YANILLE_HOPS(  8173, 716, 32),
		
		FALADOR_TREE(  8389, 701, 33), 
		GNOME_TREE(  19147, 2953, 34), 
		LUMRBIDGE_TREE(8391, 703, 35),
		TAVERLY_TREE(  8388, 700, 36), 
		VARROCK_TREE(  8390, 702, 37),
		
		DRAYNOR_BELLADONNA(  7572, 748, 38),
		
		VARROCK_BUSH(  7577, 732, 39),
		RIMMINGTON_BUSH(  7578, 733, 40), //TODO
		ARDY_BUSH(  7580, 735, 41),
		MISC_BUSH(  7579, 734, 42); //bushes, calquat, and vine patches
		//HABITAT_FEATURE CONFIG: 8354
		
		private static Map<Integer, WorldPatches> herbs = new HashMap<Integer, WorldPatches>();

		public static WorldPatches forId(int id) {
			return herbs.get(id);
		}

		static {
			for (WorldPatches herb : WorldPatches.values())
				herbs.put(herb.objectId, herb);
		}
		
		private int objectId;
		private int configId;
		private int arrayIndex;
		
		private WorldPatches(int objectId, int configId, int arrayIndex) {
			this.objectId = objectId;
			this.configId = configId;
			this.arrayIndex = arrayIndex;
		}
		
		public int getObjectId() {
			return objectId;
		}
		
		public int getConfigId() {
			return configId;
		}
		
		public int getArrayIndex() {
			return arrayIndex;
		}
	}
	
	public enum VineHerbPatch {
		ERZI(19897, 19984,  58, 87,  0x04),
		ARGW(19907, 19985,  65, 125, 0x04),
		UGUN(19902, 19986,  70, 152, 0x04),
		SHEN(19912, 19987,  76, 160, 0x04),
		SAMA(19917, 19988,  80, 190, 0x04);

		private static Map<Integer, VineHerbPatch> herbs = new HashMap<Integer, VineHerbPatch>();

		public static VineHerbPatch forId(int id) {
			return herbs.get(id);
		}

		static {
			for (VineHerbPatch herb : VineHerbPatch.values())
				herbs.put(herb.seed, herb);
		}

		private int configId;
		private int req;
		private int seed;
		private int produce;
		private int xp;

		private VineHerbPatch(int seed, int produce, int req, int xp, int configId) {
			this.configId = configId;
			this.req = req;
			this.xp = xp;
			this.produce = produce;
			this.seed = seed;
		}
		
		public int getProduceId() {
			return produce;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getXp() {
			return xp;
		}

		public int getConfigId() {
			return configId;
		}

		public int getRequirement() {
			return req;
		}
	}
	
	public enum BushPatch {
		REDBER(5101, 1951, 10, 5,  5,   5),
		CADAVA(5102, 753,  22, 7,  15,  6),
		DWELLB(5103, 2126, 36, 12, 26,  7),
		JANGER(5104, 247,  48, 19, 38,  8),
		WHITEB(5105, 239,  59, 29, 51,  8),
		POISON(5106, 6018, 70, 45, 197, 8);

		private static Map<Integer, BushPatch> herbs = new HashMap<Integer, BushPatch>();

		public static BushPatch forId(int id) {
			return herbs.get(id);
		}

		static {
			for (BushPatch herb : BushPatch.values())
				herbs.put(herb.seed, herb);
		}

		private int configId;
		private int req;
		private int seed;
		private int produce;
		private int xp;
		private int maxState;

		private BushPatch(int seed, int produce, int req, int xp, int configId, int maxState) {
			this.configId = configId;
			this.req = req;
			this.xp = xp;
			this.produce = produce;
			this.seed = seed;
			this.maxState = maxState;
		}
		
		public int getMaxState() {
			return maxState;
		}
		
		public int getProduceId() {
			return produce;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getXp() {
			return xp;
		}

		public int getConfigId() {
			return configId;
		}

		public int getRequirement() {
			return req;
		}
	}
	
	public enum MushroomPatch {
		MORCHELLA(21620, 21622, 74, 77, 26); //32 max (6 stages)

		private static Map<Integer, MushroomPatch> herbs = new HashMap<Integer, MushroomPatch>();

		public static MushroomPatch forId(int id) {
			return herbs.get(id);
		}

		static {
			for (MushroomPatch herb : MushroomPatch.values())
				herbs.put(herb.seed, herb);
		}

		private int configId;
		private int req;
		private int seed;
		private int produce;
		private int xp;

		private MushroomPatch(int seed, int produce, int req, int xp, int configId) {
			this.configId = configId;
			this.req = req;
			this.xp = xp;
			this.produce = produce;
			this.seed = seed;
		}
		
		public int getProduceId() {
			return produce;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getXp() {
			return xp;
		}

		public int getConfigId() {
			return configId;
		}

		public int getRequirement() {
			return req;
		}
	}
	
	public enum CactusPatch {
		CACTUS(5280, 6016, 55, 25, 8); //16 max (8 stages)

		private static Map<Integer, CactusPatch> herbs = new HashMap<Integer, CactusPatch>();

		public static CactusPatch forId(int id) {
			return herbs.get(id);
		}

		static {
			for (CactusPatch herb : CactusPatch.values())
				herbs.put(herb.seed, herb);
		}

		private int configId;
		private int req;
		private int seed;
		private int produce;
		private int xp;

		private CactusPatch(int seed, int produce, int req, int xp, int configId) {
			this.configId = configId;
			this.req = req;
			this.xp = xp;
			this.produce = produce;
			this.seed = seed;
		}
		
		public int getProduceId() {
			return produce;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getXp() {
			return xp;
		}

		public int getConfigId() {
			return configId;
		}

		public int getRequirement() {
			return req;
		}
	}
	
	public enum BellPatch {
		BELLADONNA(5281, 2398, 63, 512, 4);

		private static Map<Integer, BellPatch> herbs = new HashMap<Integer, BellPatch>();

		public static BellPatch forId(int id) {
			return herbs.get(id);
		}

		static {
			for (BellPatch herb : BellPatch.values())
				herbs.put(herb.seed, herb);
		}

		private int configId;
		private int req;
		private int seed;
		private int produce;
		private int xp;

		private BellPatch(int seed, int produce, int req, int xp, int configId) {
			this.configId = configId;
			this.req = req;
			this.xp = xp;
			this.produce = produce;
			this.seed = seed;
		}
		
		public int getProduceId() {
			return produce;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getXp() {
			return xp;
		}

		public int getConfigId() {
			return configId;
		}

		public int getRequirement() {
			return req;
		}
	}
	
	public enum HerbPatch {
		GUAM(5291, 199,   9, 12,  0x04),
		MARR(5292, 201,  14, 15,  0x0b),
		TARR(5293, 203,  19, 18,  0x12),
		HARR(5294, 205,  26, 24,  0x19),
		RANA(5295, 207,  32, 30,  0x20),
		TOAD(5296, 3049, 38, 38,  0x27),
		IRIT(5297, 209,  44, 48,  0x2e),
		AVAN(5298, 211,  50, 61,  0x35),
		KWUA(5299, 213,  56, 78,  0x44),
		SNAP(5300, 3051, 62, 98,  0x4b),
		CADA(5301, 215,  67, 120, 0x52),
		LANT(5302, 2485, 73, 151, 0x59),
		DWAR(5303, 217,  79, 192, 0x60),
		TORS(5304, 219,  85, 244, 0x67), 
		FELL(21621, 21626, 91, 316, 0x04);

		private static Map<Integer, HerbPatch> herbs = new HashMap<Integer, HerbPatch>();

		public static HerbPatch forId(int id) {
			return herbs.get(id);
		}

		static {
			for (HerbPatch herb : HerbPatch.values())
				herbs.put(herb.seed, herb);
		}

		private int configId;
		private int req;
		private int seed;
		private int produce;
		private int xp;

		private HerbPatch(int seed, int produce, int req, int xp, int configId) {
			this.configId = configId;
			this.req = req;
			this.xp = xp;
			this.produce = produce;
			this.seed = seed;
		}
		
		public int getProduceId() {
			return produce;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getXp() {
			return xp;
		}

		public int getConfigId() {
			return configId;
		}

		public int getRequirement() {
			return req;
		}
	}
	
	public enum AllotmentPatch {
		POTATO(     5318, 1942,  1,  9,  6, 4),
		ONIONS(     5319, 1957,  5,  11, 13, 4),
		CABBAGE(    5324, 1965,  7,  12, 20, 4),
		TOMATOES(   5322, 1982,  12, 14, 27, 4),
		SWEETCORN(  5320, 5986,  20, 19, 34, 6), 
		STRAWERRIES(5323, 5504,  31, 29, 43, 6),
		WATERMELON( 5321, 5982,  47, 55, 52, 8);

		private static Map<Integer, AllotmentPatch> herbs = new HashMap<Integer, AllotmentPatch>();

		public static AllotmentPatch forId(int id) {
			return herbs.get(id);
		}

		static {
			for (AllotmentPatch herb : AllotmentPatch.values())
				herbs.put(herb.seed, herb);
		}

		private int configId;
		private int req;
		private int seed;
		private int produce;
		private int xp;
		private int maxStage;

		private AllotmentPatch(int seed, int produce, int req, int xp, int configId, int maxStage) {
			this.configId = configId;
			this.req = req;
			this.xp = xp;
			this.produce = produce;
			this.seed = seed;
			this.maxStage = maxStage;
		}
		
		public int getProduceId() {
			return produce;
		}
		
		public int getMaxStage() {
			return maxStage;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getXp() {
			return xp;
		}

		public int getConfigId() {
			return configId;
		}

		public int getRequirement() {
			return req;
		}
	}
	
	public enum HopPatch {
		BARL(5305, 6006, 3, 10, 49, 4),
		HAMM(5307, 5994, 4, 10, 4, 4),
		ASGA(5308, 5996, 8, 12, 11, 5),
		JUTE(5306, 5931, 13, 15, 56, 5),
		YANI(5309, 5998, 16, 16, 19, 6),
		KRAN(5310, 6000, 21, 20, 28, 7),
		WILD(5311, 6002, 28, 26, 38, 8);

		private static Map<Integer, HopPatch> herbs = new HashMap<Integer, HopPatch>();

		public static HopPatch forId(int id) {
			return herbs.get(id);
		}

		static {
			for (HopPatch herb : HopPatch.values())
				herbs.put(herb.seed, herb);
		}

		private int configId;
		private int req;
		private int seed;
		private int produce;
		private int xp;
		private int maxStage;

		private HopPatch(int seed, int produce, int req, int xp, int configId, int maxStage) {
			this.configId = configId;
			this.req = req;
			this.xp = xp;
			this.produce = produce;
			this.seed = seed;
			this.maxStage = maxStage;
		}
		
		public int getProduceId() {
			return produce;
		}
		
		public int getMaxStage() {
			return maxStage;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getXp() {
			return xp;
		}

		public int getConfigId() {
			return configId;
		}

		public int getRequirement() {
			return req;
		}
	}
	
	public enum FlowerPatch {
		MARIGOLD(  5096, 6010, 2,  47,  0x08),
		ROSEMARY(  5097, 6014, 11, 66,  0x0d),
		NASTURTIUM(5098, 6012, 24, 111, 0x12),
		WOAD(      5099, 1793, 25, 115, 0x17),
		LIMPWURT(  5100,  225, 26, 120, 0x1c), 
		LILY(   14589, 14583,  52, 250, 0x25);

		private static Map<Integer, FlowerPatch> herbs = new HashMap<Integer, FlowerPatch>();

		public static FlowerPatch forId(int id) {
			return herbs.get(id);
		}

		static {
			for (FlowerPatch herb : FlowerPatch.values())
				herbs.put(herb.seed, herb);
		}

		private int configId;
		private int req;
		private int seed;
		private int produce;
		private int xp;

		private FlowerPatch(int seed, int produce, int req, int xp, int configId) {
			this.configId = configId;
			this.req = req;
			this.xp = xp;
			this.produce = produce;
			this.seed = seed;
		}
		
		public int getProduceId() {
			return produce;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getXp() {
			return xp;
		}

		public int getConfigId() {
			return configId;
		}

		public int getRequirement() {
			return req;
		}
	}
	
	/*****************************************************************
	 * TREE STARTING
	 * 
	 * ***************************************************************
	 */
	
	public enum FruitTreePatch {
		APPLE(    5283, 1955, 27, 1199, 8,   34,  20,  14),
		BANANNA(  5284, 1963, 33, 1750, 35,  61,  47,  41),
		ORANGE(   5285, 2108, 39, 2470, 72,  98,  84,  78),
		CURRY(    5286, 5970, 42, 2907, 99,  125, 111, 105),
		PINEAPPLE(5287, 2114, 51, 4605, 136, 162, 148, 142), //Fruit can be picked 6 times
		PAPAYA(   5288, 5972, 57, 6146, 163, 189, 175, 169),
		COCONUT(  5289, 5974, 68, 10150, 200, 226, 212, 206);
		

		private static Map<Integer, FruitTreePatch> herbs = new HashMap<Integer, FruitTreePatch>();

		public static FruitTreePatch forId(int id) {
			return herbs.get(id);
		}

		static {
			for (FruitTreePatch herb : FruitTreePatch.values())
				herbs.put(herb.seed, herb);
		}

		private int configId;
		private int req;
		private int seed;
		private int produce;
		private int xp;
		private int checkHealth;
		private int pickFruit;
		private int chopDown;

		private FruitTreePatch(int seed, int produce, int req, int xp, int configId, int checkHealth, int pickFruit, int chopDown) {
			this.configId = configId;
			this.req = req;
			this.xp = xp;
			this.produce = produce;
			this.seed = seed;
			this.checkHealth = checkHealth;
			this.pickFruit = pickFruit;
			this.chopDown = chopDown;
		}
		
		public int getProduceId() {
			return produce;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getXp() {
			return xp;
		}
		
		public int getPickXp() {
			return xp/41;
		}

		public int getConfigId() {
			return configId;
		}

		public int getRequirement() {
			return req;
		}

		public int getCheckHealth() {
			return checkHealth;
		}
		
		public int getPickFruit() {
			return pickFruit;
		}

		public int getChopDown() {
			return chopDown;
		}
	}
	
	public enum TreePatch {
		OAK(5312, 1521, 15, 467,   8,  4, 13, 14),
		WIL(5313, 1519, 30, 1456,  15, 6, 22, 23),
		MAP(5314, 1517, 45, 3403,  24, 8, 33, 34),
		YEW(5315, 1515, 60, 7069,  35, 10, 46, 47),
		MAG(5316, 1513, 75, 13768, 48, 12, 61, 62);
		

		private static Map<Integer, TreePatch> herbs = new HashMap<Integer, TreePatch>();

		public static TreePatch forId(int id) {
			return herbs.get(id);
		}

		static {
			for (TreePatch herb : TreePatch.values())
				herbs.put(herb.seed, herb);
		}

		private int configId;
		private int req;
		private int seed;
		private int produce;
		private int xp;
		private int maxState;
		private int stump;
		private int chopDown;

		private TreePatch(int seed, int produce, int req, int xp, int configId, int maxState, int chopDown, int stump) {
			this.configId = configId;
			this.req = req;
			this.xp = xp;
			this.produce = produce;
			this.seed = seed;
			this.maxState = maxState;
			this.stump = stump;
			this.chopDown = chopDown;
		}
		
		public int getProduceId() {
			return produce;
		}
		
		public int getSeed() {
			return seed;
		}
		
		public int getXp() {
			return xp;
		}
		
		public int getConfigId() {
			return configId;
		}

		public int getRequirement() {
			return req;
		}
		
		public int getPlantXp() {
			return req*2;
		}

		public int getMaxState() {
			return maxState;
		}
		
		public int getStump() {
			return stump;
		}

		public int getChopDown() {
			return chopDown;
		}
	}

}

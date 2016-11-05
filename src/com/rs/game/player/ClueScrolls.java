package com.rs.game.player;


import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.utils.Logger;
import com.rs.utils.Misc;
import com.rs.utils.Utils;

	public class ClueScrolls {

		public static int[] ScrollIds = { 2677, 2678, 2679, 2680, 2681, 2682, 2683,
			2684, 2685, 2686, 2687, 2688, 2689, 2690, 2691, 2692, 2693, 2694,
			2695, 2696, 2697, 2698 };

		public static enum Scrolls {
			Scroll1(new int[] { ScrollIds[0], 1 }, Maps.Map1), Scroll2(new int[] {
			ScrollIds[1], 1 }, Maps.Map2), Scroll3(new int[] {
			ScrollIds[2], 1 }, Maps.Map3), Scroll4(new int[] {
			ScrollIds[3], 1 }, Maps.Map4), Scroll5(new int[] {
			ScrollIds[4], 1 }, Maps.Map5), Scroll6(new int[] {
			ScrollIds[5], 1 }, Maps.Map6), Scroll7(new int[] {
			ScrollIds[6], 1 }, Maps.Map7), Scroll8(new int[] {
			ScrollIds[7], 1 }, Maps.Map8), Scroll9(new int[] {
			ScrollIds[8], 1 }, Maps.Map9), Scroll10(new int[] {
			ScrollIds[9], 1 }, Maps.Map10), Scroll11(new int[] {
			ScrollIds[10], 1 }, Maps.Map11), Scroll12(new int[] {
			ScrollIds[11], 1 }, Maps.Map12), Scroll13(new int[] {
			ScrollIds[12], 1 }, Maps.Map13), Scroll14(new int[] {
			ScrollIds[13], 1 }, Maps.Map14), Scroll15(new int[] {
			ScrollIds[14], 1 }, Maps.Map15), Scroll16(new int[] {
			ScrollIds[15], 1 }, ObjectMaps.Map1), Scroll17(new int[] {
			ScrollIds[16], 1 }, ObjectMaps.Map2), Scroll18(new int[] {
			ScrollIds[17], 1 }, Riddles.Riddle1), Scroll19(new int[] {
			ScrollIds[18], 1 }, Riddles.Riddle2), Scroll20(new int[] {
			ScrollIds[19], 1 }, Riddles.Riddle3), Scroll21(new int[] {
			ScrollIds[20], 1 }, Riddles.Riddle4), Scroll22(new int[] {
			ScrollIds[21], 1 }, Riddles.Riddle5);

		public static Maps getMap(int itemid) {
			System.out.println("getting map");
			Logger.logMessage("getting map");
			for (Scrolls scroll : Scrolls.values()) {
				if (scroll.id == itemid) {
					if (scroll.getHiding() == null)
						continue;
					else
						return scroll.getHiding();
				}
			}
			return null;
		}

		public static Riddles getRiddles(int itemid) {
			for (Scrolls scroll : Scrolls.values()) {
				if (scroll.id == itemid) {
					if (scroll.getRiddle() == null)
						continue;
					else
						return scroll.getRiddle();
				}
			}
			return null;
		}

		public Riddles getRiddle() {
			return riddle;
		}

		public static ObjectMaps getObjMap(int itemid) {
			for (Scrolls scroll : Scrolls.values()) {
				if (scroll.id == itemid) {
					if (scroll.getLocationMap() == null)
						continue;
					else
						return scroll.getLocationMap();
				}
			}
			return null;
		}

		public ObjectMaps getLocationMap() {
			return locationmap;
		}

		public Maps getHiding() {
			return hiding;
		}

		int[] infos;
		Maps hiding;
		int id, level;
		ObjectMaps locationmap;
		Riddles riddle;

		private Scrolls(int[] info, Riddles riddle) {
			this.infos = info;
			this.riddle = riddle;
			this.id = info[0];
			this.level = info[1];
		}

		private Scrolls(int[] info, ObjectMaps map) {
			this.infos = info;
			this.locationmap = map;
			this.id = info[0];
			this.level = info[1];
		}

		private Scrolls(int[] info, Maps hiden) {
			this.infos = info;
			this.id = info[0];
			this.level = info[1];
			this.hiding = hiden;
		}
	}

	private enum Riddles {
		Riddle1(22, new int[] { 2967, 4386, 2970, 4380 }, new String[] {
				"There once was a villan", "of grey and white",
				"he also had a bit of bage", "do a clap outside his cave",
				"to scare him off", "", "", "" }), // Corp
		Riddle2(15, new int[] { 3190, 9828, 3193, 9825 }, new String[] {
				"I am a token of the greatest love",
				"I have no beginning or end",
				"Go to the place where money is lent",
				"Jig by the gate to be my friend!", "", "", "", "" }), // Varrock
																		// Bank
																		// Basement
		Riddle3(28, new int[] { 3162, 3255, 3171, 3244 }, new String[] {
				"For the reward you seek", "a city of lumber and bridge",
				"is west of a place that you", "must go to get some ham",
				"once outside do a lean", " to meat Mr. Mean!", "", "" }), // Ham
																			// Entrance
		Riddle4(14, new int[] { 2987, 3123, 3001, 3109 }, new String[] {
				"Near a ring known to teleport", "On a point full of mud",
				"A simple emote is needed",
				"An emote known as skipping or dance!", "", "", "", "" }), Riddle5(
				30, new int[] { 2884, 3449, 2898, 3438 }, new String[] {
						"This reward will require a bit",
						"For the first thing you will", "Need to be at a den",
						"and you have to be a rouge",
						"You must have an idea outside",
						"Of its entrance to get a reward!", "", "" });// Mudsckipper
																		// Point
		int[] locations;
		String[] riddles;
		int emoteid;

		private Riddles(int id, int[] location, String[] riddles) {
			this.locations = location;
			this.riddles = riddles;
			this.emoteid = id;
		}
		// Riddle interface 345
	}

	private enum ObjectMaps {
		Map1(358, new int[] { 18506, 2457, 3182 },
				"Near an observatory meant for getting a compas on RS!"), Map2(
				361, new int[] { 46331, 2565, 3248 },
				"Just south of a city known for thieving and outside a tower of clock!");

		int objectid, objectx, objecty;
		@SuppressWarnings("unused")
		int[] objectinfo;
		String hint;
		int interid;

		private ObjectMaps(int interid, int[] object, String text) {
			this.hint = text;
			this.interid = interid;
			this.objectinfo = object;
			this.objectid = object[0];
			this.objectx = object[1];
			this.objecty = object[2];
		}
	}

	private enum Maps {
		Map1(337, 2971, 3414,
				"If you Fala by A Door you might need help on this one!"), Map2(
				338, 3021, 3912,
				"In beetween a lava blaze and a near Deathly Agility Course!"), Map3(
				339, 2722, 3339,
				"South of where legneds may be placed, and east of great thieving!"), Map4(
				341,
				3435,
				3265,
				"South of a muchky mucky mucky mucky swamp lands, and barely north of Haunted Mines!"), Map5(
				344, 2665, 3561,
				"West of a murderous Mansion, and south of a city of vikings!"), Map6(
				346, 3166, 3359,
				"Slightly South of a city of great knights and lots of Shops!"), Map7(
				347, 3290, 3372,
				"A mining place located near a city of great knights and lots of Shops"), Map8(
				348, 3092, 3225,
				"Slightly south of a village known for thieving masters of farming!"), Map9(
				351, 3043, 3398,
				"North East Corner of a city based around a castle with a mort around it!"), Map10(
				352, 2906, 3295,
				"Right next to a guild known for people with skilled hands!"), Map11(
				353, 2616, 3077,
				"In a city that Rhymes with tan i will, if you say it really fast!"), Map12(
				354, 2612, 3482,
				"West of some woods that sound like Mc Jagger!"), Map13(356,
				3110, 3152, "South of a tower full of magical people!"), Map14(
				360,
				2652,
				3232,
				"North of a tower known to give life and south of a city that contains thieving!"), Map15(
				362, 2923, 3210,
				"West of the place best known for starting a house!");

		String chat;
		int interfaceId, xcoord, ycoord;

		private Maps(int interid, int x, int y, String hint) {
			this.interfaceId = interid;
			this.xcoord = x;
			this.ycoord = y;
			this.chat = hint;
		}
	}

	public static Scrolls hasClue(Player p) {
		for (Scrolls scroll : Scrolls.values()) {
			if (p.getInventory().containsOneItem(scroll.id)) {
				return scroll;
			}
		}
		return null;
	}

	public static ObjectMaps hasObjectMapClue(Player p, int scrollid) {
		for (Scrolls scroll : Scrolls.values()) {
			if (scroll.getLocationMap() == null)
				continue;
			else {
				if (scroll.id == scrollid) {
					if (p.getInventory().containsOneItem(scroll.id)) {
						return scroll.getLocationMap();
					}
				}
			}
		}
		return null;
	}

	public static Maps hasMapClue(Player p, int scrollid) {
		for (Scrolls scroll : Scrolls.values()) {
			if (scroll.getHiding() == null)
				continue;
			else {
				if (scroll.id == scrollid) {
					if (p.getInventory().containsOneItem(scroll.id)) {
						return scroll.getHiding();
					}
				}
			}
		}
		return null;
	}

	public static Riddles hasRiddleClue(Player p, int scrollid) {
		for (Scrolls scroll : Scrolls.values()) {
			if (scroll.getRiddle() == null)
				continue;
			else {
				if (scroll.id == scrollid) {
					if (p.getInventory().containsOneItem(scroll.id)) {
						return scroll.getRiddle();
					}
				}
			}
		}
		return null;
	}

	public static boolean completedRiddle(Player p, int emoteid) {
		Scrolls scroll = hasClue(p);
		if (scroll != null) {
			if (hasRiddleClue(p, scroll.id) != null) {
				Riddles riddleclue = hasRiddleClue(p, scroll.id);
				WorldTile lastloc = p.getLastWorldTile();
				if (lastloc.getX() >= riddleclue.locations[0]
						&& lastloc.getY() <= riddleclue.locations[1]
						&& lastloc.getX() <= riddleclue.locations[2]
						&& lastloc.getY() >= riddleclue.locations[3]) {
					System.out.println(riddleclue.emoteid);
					System.out.println(riddleclue.name());
					System.out.println(emoteid);
					if (emoteid == riddleclue.emoteid) {
						p.sm("You have succesfully completed the riddle and have been awarded a chest!");
						p.getInventory().deleteItem(scroll.id, 1);
						p.getInventory().addItem(2717, 1);
					}
				}
			}
		}
		return false;
	}

	public static boolean objectSpot(Player p, WorldObject obj) {
		Scrolls scroll = hasClue(p);
		if (scroll != null) {
			if (hasObjectMapClue(p, scroll.id) != null) {
				ObjectMaps mapclue = hasObjectMapClue(p, scroll.id);
				@SuppressWarnings("unused")
				WorldTile lastloc = p.getLastWorldTile();
				if (obj.getX() == mapclue.objectx
						&& obj.getY() == mapclue.objecty
						&& obj.getId() == mapclue.objectid) {
					p.sm("You have succesfully completed the riddle and have been awarded a chest!");
					p.getInventory().deleteItem(scroll.id, 1);
					p.getInventory().addItem(2717, 1);
				}
			}
		}
		return false;
	}

	public static boolean digSpot(Player p) {
		Scrolls scroll = hasClue(p);
		if (scroll != null) {
			if (hasMapClue(p, scroll.id) != null) {
				Maps mapclue = hasMapClue(p, scroll.id);
				WorldTile lastloc = p.getLastWorldTile();
				if (lastloc.getX() == mapclue.xcoord
						&& lastloc.getY() == mapclue.ycoord) {
					p.sm("You have succesfully completed the riddle and have been awarded a chest!");
					p.getInventory().deleteItem(scroll.id, 1);
					p.getInventory().addItem(2717, 1);

				}
			}
		}
		return false;

	}

	public static void showObjectMap(Player p, ObjectMaps objmap) {
		p.getPackets().sendInterface(false,
				p.getInterfaceManager().hasRezizableScreen() ? 746 : 548,
				p.getInterfaceManager().hasRezizableScreen() ? 28 : 27,
				objmap.interid);
		p.sm(objmap.hint);

	}

	public static void showRiddle(Player p, Riddles riddle) {
		p.getPackets().sendInterface(false,
				p.getInterfaceManager().hasRezizableScreen() ? 746 : 548,
				p.getInterfaceManager().hasRezizableScreen() ? 28 : 27, 345);
		p.getPackets().sendIComponentText(345, 1, riddle.riddles[0]);
		p.getPackets().sendIComponentText(345, 2, riddle.riddles[1]);
		p.getPackets().sendIComponentText(345, 3, riddle.riddles[2]);
		p.getPackets().sendIComponentText(345, 4, riddle.riddles[3]);
		p.getPackets().sendIComponentText(345, 5, riddle.riddles[4]);
		p.getPackets().sendIComponentText(345, 6, riddle.riddles[5]);
		p.getPackets().sendIComponentText(345, 7, riddle.riddles[6]);
		p.getPackets().sendIComponentText(345, 8, riddle.riddles[7]);
	}

	public static void showMap(Player p, Maps map) {
		p.getPackets().sendInterface(false,
				p.getInterfaceManager().hasRezizableScreen() ? 746 : 548,
				p.getInterfaceManager().hasRezizableScreen() ? 28 : 27,
				map.interfaceId);
		p.sm(map.chat);
	}

	static Item[] EasyCommonRewards = { new Item(1077, 1), new Item(1089, 1), new Item(1125, 1), new Item(1165, 1), new Item(1195, 1), new Item(557, 1), new Item(579, 1), new Item(1011, 1), new Item(1095, 1), new Item(1097, 1), new Item(1129, 1), new Item(1133, 1), new Item(1727, 1) };
	static Item[] EasyRareRewards = { new Item(2585, 1), new Item(2583, 1), new Item(2587, 1), new Item(2589, 1), new Item(2591, 1), new Item(2593, 1), new Item(2595, 1), new Item(2597, 1), new Item(2633, 1), new Item(2635, 1), new Item(2637, 1), new Item(7386, 1), new Item(7388, 1), new Item(7390, 1), new Item(7392, 1), new Item(2631, 1), new Item(7362, 1), new Item(7364, 1), new Item(7366, 1), new Item(7368, 1), new Item(7394, 1), new Item(7396, 1), new Item(7332, 1), new Item(7338, 1), new Item(7344, 1), new Item(7350, 1), new Item(7356, 1), new Item(10306, 1), new Item(10308, 1), new Item(10310, 1), new Item(10312, 1), new Item(10314, 1), new Item(19167, 1), new Item(19169, 1), new Item(19188, 1), new Item(19190, 1), new Item(19192, 1), new Item(19209, 1), new Item(19211, 1), new Item(19213, 1), new Item(19230, 1), new Item(19232, 1), new Item(19234, 1), new Item(19251, 1), new Item(19253, 1), new Item(19255, 1), new Item(10404, 1), new Item(10406, 1), new Item(10408, 1), new Item(10410, 1), new Item(10412, 1), new Item(10414, 1), new Item(10424, 1), new Item(10426, 1), new Item(10428, 1), new Item(10430, 1), new Item(10432, 1), new Item(10434, 1), new Item(10366, 1), new Item(13095, 1), new Item(13105, 1), new Item(13081, 1), new Item(13083, Misc.random(1, 500)), new Item(10398, 1), new Item(10393, 1), new Item(10396, 1) };
	static Item[] MediumCommonRewards = { new Item(1073, 1), new Item(1091, 1), new Item(1161, 1), new Item(1111, 1), new Item(1199, 1), new Item(1211, 1), new Item(1271, 1), new Item(1287, 1), new Item(1301, 1), new Item(1317, 1), new Item(1331, 1), new Item(4823, 15), new Item(9183, 1), new Item(1393, 1), new Item(1099, 1), new Item(1135, 1), new Item(857, 1), new Item(374, 5), new Item(8780, 2), new Item(380, 5)};
	static Item[] MediumRareRewards = { new Item(2599, 1), new Item(2601, 1), new Item(2605, 1), new Item(2603, 1), new Item(3474, 1), new Item(2607, 1), new Item(2609, 1), new Item(2611, 1), new Item(2613, 1), new Item(3475, 1), new Item(2577, 1), new Item(2579, 1), new Item(2645, 1), new Item(2647, 1), new Item(2649, 1), new Item(7319, 1), new Item(7321, 1), new Item(7323, 1), new Item(7325, 1), new Item(7327, 1), new Item(7372, 1), new Item(7380, 1), new Item(7370, 1), new Item(7378, 1), new Item(7334, 1), new Item(10296, 1), new Item(19173, 1), new Item(19175, 1), new Item(19177, 1), new Item(7340, 1), new Item(10298, 1), new Item(19194, 1), new Item(19198, 1), new Item(19196, 1), new Item(7346, 1), new Item(10300, 1), new Item(19215, 1), new Item(19217, 1), new Item(19219, 1), new Item(7352, 1), new Item(10302, 1), new Item(19236, 1), new Item(19238, 1), new Item(19240, 1), new Item(7358, 1), new Item(10304, 1), new Item(19257, 1), new Item(19259, 1), new Item(19261, 1), new Item(10400, 1), new Item(10402, 1), new Item(10416, 1), new Item(10418, 1), new Item(10420, 1), new Item(10422, 1), new Item(10364, 1), new Item(10452, 1), new Item(10454, 1), new Item(10456, 1), new Item(10446, 1), new Item(10448, 1), new Item(10450, 1), new Item(19384, 1), new Item(19388, 1), new Item(19382, 1), new Item(19390, 1), new Item(19380, 1), new Item(19386, 1), new Item(13109, 1), new Item(13107, 1), new Item(13111, 1), new Item(13113, 1), new Item(13115, 1), new Item(13097, 1), new Item(13103, 1)};
	static Item[] HardCommonRewards = { new Item(1079, 1), new Item(1093, 1), new Item(1127, 1), new Item(1163, 1), new Item(1201, 1), new Item(1289, 1), new Item(1319, 1), new Item(1333, 1), new Item(4824, 15), new Item(9144, 50), new Item(9185, 1), new Item(2503, 1), new Item(2497, 1), new Item(861, 1), new Item(859, 1), new Item(8783, 5), new Item(380, 15), new Item(386, 15),};
	static Item[] HardRareRewards = { new Item(2623, 1), new Item(2625, 1), new Item(2627, 1), new Item(2629, 1), new Item(3477, 1), new Item(2615, 1), new Item(2617, 1), new Item(2619, 1), new Item(2621, 1), new Item(3476, 1), new Item(2661, 1), new Item(2663, 1), new Item(2665, 1), new Item(2667, 1), new Item(3479, 1), new Item(2669, 1), new Item(2671, 1), new Item(2673, 1), new Item(2675, 1), new Item(3480, 1), new Item(2653, 1), new Item(2655, 1), new Item(2657, 1), new Item(2659, 1), new Item(3478, 1), new Item(3481, 1), new Item(3483, 1), new Item(3485, 1), new Item(3486, 1), new Item(3488, 1), new Item(7376, 1), new Item(7384, 1), new Item(7382, 1), new Item(7374, 1), new Item(2651, 1), new Item(2581, 1), new Item(7398, 1), new Item(7399, 1), new Item(7400, 1), new Item(2369, 1), new Item(2641, 1), new Item(2643, 1), new Item(7336, 1), new Item(10286, 1), new Item(10667, 1), new Item(19179, 1), new Item(19182, 1), new Item(19185, 1), new Item(7342, 1), new Item(10288, 1), new Item(19200, 1), new Item(19203, 1), new Item(19206, 1), new Item(7348, 1), new Item(10290, 1), new Item(19221, 1),new Item(19224, 1), new Item(19227, 1), new Item(7354, 1), new Item(10292, 1), new Item(19242, 1), new Item(19245, 1), new Item(19248, 1), new Item(7360, 1), new Item(10294, 1), new Item(19263, 1), new Item(19266, 1), new Item(19269, 1), new Item(10330, 1), new Item(10332, 1), new Item(10334, 1), new Item(10336, 1), new Item(10338, 1), new Item(10340, 1), new Item(10342, 1), new Item(10344, 1), new Item(10346, 1), new Item(10348, 1), new Item(10350, 1), new Item(10352, 1), new Item(10362, 1), new Item(10470, 1), new Item(10472, 1), new Item(10474, 1), new Item(10440, 1), new Item(10442, 1), new Item(10444, 1), new Item(10790, 1), new Item(10792, 1), new Item(10794, 1), new Item(10372, 1), new Item(10374, 1), new Item(10368, 1), new Item(10388, 1), new Item(10390, 1), new Item(10384, 1), new Item(10376, 1), new Item(10380, 1), new Item(10382, 1), new Item(13099, 1), new Item(13101, 1), new Item(19272, 1), new Item(19275, 1), new Item(19278, 1), new Item(19281, 1), new Item(19284, 1), new Item(19287, 1)};
	static Item[] EliteCommonRewards = { new Item(1216, 5), new Item(1306, 3), new Item(5316, 15), new Item(3025, 7), new Item(2453, 7), new Item(990, 3), new Item(9194, 15), new Item(1705, 4)};
	static Item[] EliteRareRewards = { new Item(19308, 1), new Item(19311, 1), new Item(19314, 1), new Item(19317, 1), new Item(19320, 1), new Item(19413, 1), new Item(19416, 1), new Item(19419, 1), new Item(19422, 1), new Item(19425, 1), new Item(19428, 1), new Item(19431, 1), new Item(19434, 1), new Item(19437, 1), new Item(19440, 1), new Item(19398, 1), new Item(19401, 1), new Item(19404, 1), new Item(19407, 1), new Item(19410, 1), new Item(19443, 1), new Item(19445, 1), new Item(19447, 1), new Item(19449, 1), new Item(19451, 1), new Item(19453, 1), new Item(19455, 1), new Item(19457, 1), new Item(19459, 1), new Item(19461, 1), new Item(19463, 1), new Item(19465, 1), new Item(19392, 1), new Item(19394, 1), new Item(19396, 1), new Item(19362, 1), new Item(19364, 1), new Item(19366, 1), new Item(19333, 1), new Item(19346, 1), new Item(19348, 1), new Item(19350, 1), new Item(19352, 1), new Item(19354, 1), new Item(19356, 1), new Item(19358, 1), new Item(19360, 1), new Item(19143, 1), new Item(19146, 1), new Item(19149, 1), new Item(19323, 1), new Item(19325, 1), new Item(19327, 1), new Item(19329, 1), new Item(19331, 1), new Item(19290, 1), new Item(19293, 1), new Item(19296, 1), new Item(19299, 1), new Item(19302, 1), new Item(19305, 1)};
	static Item[] AnyRewards = { new Item(995, Misc.random(1, 250000)), new Item(3827, 1), new Item(3828, 1), new Item(3829, 1), new Item(3830, 1), new Item(3831, 1), new Item(3832, 1), new Item(3833, 1), new Item(3834, 1), new Item(3835, 1), new Item(3836, 1), new Item(3837, 1), new Item(3838, 1), new Item(19600, 1), new Item(19601, 1), new Item(19602, 1), new Item(19603, 1), new Item(19604, 1), new Item(19605, 1), new Item(19606, 1), new Item(19607, 1), new Item(19608, 1), new Item(19609, 1), new Item(19610, 1), new Item(19611, 1), new Item(7329, 1), new Item(7330, 1), new Item(7331, 1), new Item(10326, 1), new Item(10327, 1), new Item(4561, Misc.random(1, 6)), new Item(18757, 1), new Item(19467, Misc.random(1, 8)), new Item(19476, 10), new Item(19477, 10), new Item(19478, 10), new Item(19479, 10), new Item(19480, 10), new Item(19162, 500), new Item(19152, 500), new Item(19157, 500), new Item(19623, 10), new Item(19621, 50), new Item(10280, 1), new Item(10282, 1), new Item(10284, 1), new Item(1692, 1), new Item(1687, 1), new Item(1688, 1), new Item(6041, 1), new Item(1700, 1), new Item(1702, 1), new Item(6581, 1), new Item(554, Misc.random(1,200)), new Item(555, Misc.random(1, 200)), new Item(556, Misc.random(1, 200)), new Item(557, Misc.random(1, 200)), new Item(558, Misc.random(1, 200)), new Item(559, Misc.random(1, 200)), new Item(560, Misc.random(1, 200)), new Item(561, Misc.random(1, 200)), new Item(562, Misc.random(1, 200)), new Item(563, Misc.random(1, 200)), new Item(564, Misc.random(1, 200)), new Item(565, Misc.random(1, 200)), new Item(566, Misc.random(1, 200))};
	
	public static void giveReward(Player p) {
		if (p.cluenoreward >= 1 && p.cluenoreward <= 5) {
			p.cluenoreward += 1;
		} else if (p.cluenoreward >= 6) {
			if (p.clueLevel == 0)
			p.clueChance += 100;
			else if (p.clueLevel == 1)
			p.clueChance += 75;
			else if (p.clueLevel == 2)
			p.clueChance += 50;
			else if (p.clueLevel == 3)
			p.clueChance += 25;
		}
		if (p.clueChance > 150) {
			Item[] rewards = null;
			if (p.clueLevel == 0) {
				if (Misc.random(10) == 1) {
					rewards = new Item[] {
							AnyRewards[Utils.getRandom(AnyRewards.length)],
							EasyCommonRewards[Utils.getRandom(EasyCommonRewards.length)],
							EasyCommonRewards[Utils.getRandom(EasyCommonRewards.length)],
							EasyCommonRewards[Utils.getRandom(EasyCommonRewards.length)]};
				} else {
					rewards = new Item[] {
							AnyRewards[Utils.getRandom(AnyRewards.length)],
							EasyCommonRewards[Utils.getRandom(EasyCommonRewards.length)],
							EasyCommonRewards[Utils.getRandom(EasyCommonRewards.length)],
							EasyRareRewards[Utils.getRandom(EasyRareRewards.length)]};
				}
			} else 	if (p.clueLevel == 1) {
				if (Misc.random(10) == 1) {
					rewards = new Item[] {
							AnyRewards[Utils.getRandom(AnyRewards.length)],
							MediumCommonRewards[Utils.getRandom(MediumCommonRewards.length)],
							MediumCommonRewards[Utils.getRandom(MediumCommonRewards.length)],
							MediumCommonRewards[Utils.getRandom(MediumCommonRewards.length)]};
				} else {
					rewards = new Item[] {
							AnyRewards[Utils.getRandom(AnyRewards.length)],
							MediumCommonRewards[Utils.getRandom(MediumCommonRewards.length)],
							MediumCommonRewards[Utils.getRandom(MediumCommonRewards.length)],
							MediumRareRewards[Utils.getRandom(MediumRareRewards.length)]};
				}
			} else 	if (p.clueLevel == 2) {
				if (Misc.random(10) == 1) {
					rewards = new Item[] {
							AnyRewards[Utils.getRandom(AnyRewards.length)],
							HardCommonRewards[Utils.getRandom(HardCommonRewards.length)],
							HardCommonRewards[Utils.getRandom(HardCommonRewards.length)],
							HardCommonRewards[Utils.getRandom(HardCommonRewards.length)]};
				} else {
					rewards = new Item[] {
							AnyRewards[Utils.getRandom(AnyRewards.length)],
							HardCommonRewards[Utils.getRandom(HardCommonRewards.length)],
							HardCommonRewards[Utils.getRandom(HardCommonRewards.length)],
							HardRareRewards[Utils.getRandom(HardRareRewards.length)]};
				}
			} else 	if (p.clueLevel == 3) {
				if (Misc.random(10) == 1) {
					rewards = new Item[] {
							AnyRewards[Utils.getRandom(AnyRewards.length)],
							EliteCommonRewards[Utils.getRandom(EliteCommonRewards.length)],
							EliteCommonRewards[Utils.getRandom(EliteCommonRewards.length)],
							EliteCommonRewards[Utils.getRandom(EliteCommonRewards.length)]};
				} else {
					rewards = new Item[] {
							AnyRewards[Utils.getRandom(AnyRewards.length)],
							EliteCommonRewards[Utils.getRandom(EliteCommonRewards.length)],
							EliteCommonRewards[Utils.getRandom(EliteCommonRewards.length)],
							EliteRareRewards[Utils.getRandom(EliteRareRewards.length)]};
				}
			}
			for (Item item : rewards) {
				p.getInventory().addItem(item);
				p.sm("Congrats you have won a " + item.getName() + "!");

			}
			p.clueLevel = 0;
			p.clueScrolls++;
			p.getInventory().deleteItem(2717, 1);
			p.finishedClue = false;
			p.cluenoreward = 0;
		} else {
			p.sm("You found another clue scroll!");
			p.getInventory().deleteItem(2717, 1);
			p.getInventory().addItem(ScrollIds[Utils.getRandom(ScrollIds.length - 1)], 1);
			if (p.cluenoreward == 0) {
				p.cluenoreward += 1;
			}

		}
	}
}


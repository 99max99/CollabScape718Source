package com.rs.game.player.content;

import com.rs.game.player.Player;
import com.rs.utils.Misc;
import com.rs.utils.Utils;

public class ImplingLoot {
	
	public static void baby(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{1755, 1}, {1734, 1}, {1733, 1}, {946, 1}, {1985, 1}, {2347, 1}, {1759, 1}};
		int uncommon[][] = {{1927, 1}, {319, 1}, {2007, 1}, {1779, 1}, {7170, 1}, {401, 1}, {1438, 1}};
		int rare[][] = {{2355, 1}, {1607, 1}, {1743, 1}, {379, 1}, {1761, 1}};
        int extremelyrare[][] = {{995, 10000}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the baby impling.");
		}
	}
	
	public static void young(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{361, 1}, {1539, 5}, {1784, 4}, {1523, 1}, {7936, 1}, {5970, 1}, {1901, 1}};
		int uncommon[][] = {{855, 1}, {1777, 1}, {2347, 1}, {231, 1}, {1761, 1}, {2293, 1}, {453, 1}, {7178, 1}, {247, 1}, {1353, 1}};
		int rare[][] = {{1097, 1}, {8778, 1}, {133, 1}, {2359, 1}, {1157, 1}};
        int extremelyrare[][] = {{995, 10000}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the young impling.");
		}
	}
	
	public static void gourmet(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{363, 1}, {2011, 1}, {1897, 1}, {2007, 1}, {5970, 1}, {2293, 1}, {5004, 1}, {2327, 1}, {361, 1}};
		int uncommon[][] = {{1883, 1}, {380, 4}, {386, 3}, {7170, 1}, {5755, 1}, {7178, 1}, {7188, 1}, {247, 1}};
		int rare[][] = {{10137, 5}, {374, 3}, {10136, 1}, {3145, 2}, {3143, 2},{5406, 1}, {7179, 6}};
        int extremelyrare[][] = {{995, 10000}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the gourmet impling.");
		}
	}
	
	public static void earth(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{6033 , 6}, {5535, 1}, {1440, 1}, {1442, 1}, {444, 1}, {5104, 2}, {2353, 1}, {557, 32}};
		int uncommon[][] = {{1784, 4}, {5294, 2}, {447, 1}, {1273, 1}, {5311, 2}, {237, 1}, {454, 6}};
		int rare[][] = {{1606, 2}, {6035, 2}, {448, 3}};
		int extremelyrare[][] = {{5303, 1}, {1603, 1}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the earth impling.");
		}
	}
	
	public static void eclectic(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{1273, 1}, {556, Misc.random(30, 57)}, {231, 1}, {8779, 4}, {5970, 1}};
		int uncommon[][] = {{1199, 1}, {7937, Misc.random(20, 35)}, {237, 1}, {444, 1}, {2358, 5}, {4527, 1}};
		int rare[][] = {{2493, 1}, {450, 10}, {1213, 1}, {5760, 2}, {5321, 3}, {7208, 1}, {10083, 1}};
		int extremelyrare[][] = {{1391, 1}, {1601, 1}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the eclectic impling.");
		}
	}
	
	public static void essence(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{562, 4}, {555, 30}, {556, 10}, {556, 25}, {556, 30}, {556, 60}, {559, Misc.random(28, 30)}, {558, 25}, {554, 50}, {7937, Misc.random(20, 35)}, {1436 ,20}, {1448, 1}, {555, 13}};
		int uncommon[][] = {{564, 4}, {4696, 4}, {4694, 4}, {4698, 4}, {4695, 4}};
		int rare[][] = {{4699, 4}, {565, 7}, {566, 11}, {563, 13}, {561, 13}, {560, 13}, {1442, 1}, {4697, 4}};
        int extremelyrare[][] = {{995, 10000}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the essence impling.");
		}
	}
	
	public static void spirit(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{12158, 6}, {12160, 2}, {12163, 1}, {12155, 25}, {12159, Misc.random(2, 3)}};
		int uncommon[][] = {{2350, 6}, {2354, 2}, {2359, 1}, {2361, 1}, {2363, 1}, {1116, 5}, {1120, 2}, {3115, Misc.random(6, 9)}, {1636, Misc.random(8, 10)}, {2135, 25}, {2139, Misc.random(12, 15)}, {9979, 6}, {3227, 6}, {3364, 5}, {10095, 1}, {10103, 1}, {10819, Misc.random(5, 7)}, {6155, 1}, {7939, 1}, {6291, 1}, {6319, 1}, {2860, 3}, {237, 1}, {10149, 1}, {2151, 5}, {12157, 10}, {1934, Misc.random(14, 17)}, {6033, 12}, {6010, 1}, {12148, Misc.random(1, 4)}, {12134, 1}, {12153, 1}, {1520, Misc.random(32, 65)}, {5934, 2}, {1964, Misc.random(18, 24)}, {8431, 2}, {3138, 1}, {13572, 4}, {591, Misc.random(10, 18)}, {312, Misc.random(9, 14)}, {1442, 1}, {1439, 4}, {572, 2}, {9737, 2}, {10117, 1}, {1444, 1}, {2352, 5}};
		int rare[][] = {{12109, 1}, {12121, 1}, {12115, 1}, {12117, 1}, {12111, 1}, {12119, 1}, {2466, 1}, {6980, 5}, {9976, 4}, {383, 1}, {12113, 1}};
        int extremelyrare[][] = {{995, 10000}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the spirit impling.");
		}
	}
	
	public static void nature(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{1513, 1}, {5294, 1}, {6016, 1}, {5281, 1}, {254, 4}, {5104, 1}, {5100, 1}};
		int uncommon[][] = {{5299, 1}, {3051, 1}, {5285, 1}, {5298, 1}, {5313, 1}, {5974, 1}, {3000, 1}, {5286, 1}, {5297, 1}};
		int rare[][] = {{5304, 1}, {5295, 1}};
		int extremelyrare[][] = {{5303, 1}, {270, 2}, {220, 2}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the nature impling.");
		}
	}
	
	public static void magpie(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{1682, 3}, {5541, 1}, {1748, 6}, {2569 , 3}, {3391, 1}, {1732, 3}};
		int uncommon[][] = {{1347, 1}, {4097, 1}, {2364, 2}, {1603, 1}, {4095, 1}, {2571, Misc.random(4, 5)}};
		int rare[][] = {{1215, 1}, {1602, 4}, {5287, 1}, {985, 1}, {987, 1}, {1185, 1}};
		int extremelyrare[][] = {{993, 1}, {12121, 1}, {5300, 1}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the magpie impling.");
		}
	}
	
	public static void ninja(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{6328, 1}, {1748, 10}, {1748, 16}, {140, 4}, {4097, 1}, {892, 70}, {805, 50}, {811, 70}, {868, 40}, {3101, 1}, {6313, 1}, {4095, 1}, {3391, 1}, {3385, 1}};
		int uncommon[][] = {{1113, 1}, {9342, 2}, {1215, 1}, {25496, 4}, {6155, 3}, {1347, 1}, {1333, 1}};
		int rare[][] = {{9194, 4}, {2364, 4}};
        int extremelyrare[][] = {{995, 10000}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the ninja impling.");
		}
	}
	
	public static void pirate(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{2358, 15}, {7110, 1}, {7122, 1}, {7128, 1}, {7134, 1}, {13358, 1}, {13360, 1}, {13362, 1}, {7116, 1}, {7126, 1}, {7132, 1}, {7138, 1}, {13364, 1}, {13366, 1}, {13368, 1}, {8949, 1}, {8924, 1}, {8926, 1}, {8998, 1}, {9000, 1}, {13370, 1}, {13372, 1}, {13374, 1}, {8977, 1}, {1353, 1}, {1925, 1}, {1923, 1}};
		int uncommon[][] = {{7114, 1}, {13355, 1}, {8936, 1}, {8938, 1}, {8974, 1}, {8972, 1}};
		int rare[][] = {{8976, 1}, {8951, Misc.random(1, 10)}};
		int extremelyrare[][] = {{8951, Misc.random(1, 5)}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the pirate impling.");
		}
	}
	
	public static void dragon(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{11212, Misc.random(100, 500)}, {9341, Misc.random(3, 40)}, {1305, 1}, {11232, Misc.random(105, 350)}, {11237, Misc.random(100, 500)}, {9193, Misc.random(10, 49)}, {535, Misc.random(107, 300)}};
		int uncommon[][] = {{1215, 3}, {11230, Misc.random(105, 350)}, {5316, 1}, {537, Misc.random(52, 99)}, {1616, Misc.random(3, 6)}};
		int rare[][] = {{1705, Misc.random(2, 3)}, {5300, 6}, {7219, Misc.random(5, 15)}};
		int extremelyrare[][] = {{5547, 1}, {4093, 1}, {1684, Misc.random(2, 3)}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the dragon impling.");
		}
	}
	
	public static void zombie(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{533, Misc.random(5, 15)}, {535, Misc.random(2, 10)}, {536, 3}};
		int uncommon[][] = {{10977, 1}, {10976, 1}};
		int rare[][] = {{4834, 1}, {4832, Misc.random(2, 3)}, {4830, 3}, {6812, 3}};
		int extremelyrare[][] = {{4034, 1}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the zombie impling.");
		}
	}
	
	public static void kingly(Player player, int i) {	
		if (player.getInventory().containsItem(i, 1)) {
		int common[][] = {{1618, Misc.random(17, 34)}, {1705, Misc.random(3, 11)}, {1684, 3}, {990, 2}};
		int uncommon[][] = {{15511, 1}, {15509, 1}, {15505, 1}, {15507, 1}, {15503, 1}, {11212, Misc.random(40, 144)}, {11237, Misc.random(64, 148)}, {9193, Misc.random(62, 70)}, {11230, Misc.random(182, 319)}, {11232, Misc.random(70, 214)}, {1306, Misc.random(1, 2)}, {1249, 1}, {1632, 5}, {1616, 6}, {9341, Misc.random(40, 70)}, {9342, Misc.random(50, 57)}, {2364, 10}};
		int rare[][] = {{9194, Misc.random(1, 73)}};
		int extremelyrare[][] = {{7158, 1}, {2366, 1}, {6571, 1}};
        int choice = Utils.random(100);
        int reward = 1;
        int amount = 1;
        if (choice == 1) {
        	int er = Misc.random(extremelyrare.length);
        	reward = extremelyrare[er][0];
        	amount = extremelyrare[er][1];
        } else if (choice >= 2 && choice <= 14) {
        	int r = Misc.random(rare.length);
        	reward = rare[r][0];
        	amount = rare[r][1];
        } else if (choice >= 15 && choice <= 44) {
        	int u = Misc.random(uncommon.length);
        	reward = uncommon[u][0];
        	amount = uncommon[u][1];
        } else if (choice >= 45&& choice <= 100) {
        	int c = Misc.random(common.length);
        	reward = common[c][0];
        	amount = common[c][1];
        }
        player.getInventory().addItem(reward, amount);
        player.getInventory().deleteItem(i, 1);
        player.getPackets().sendGameMessage("You looted the kingly impling.");
		}
	}

	public static boolean isJar(int i) {
		switch (i) {
		case 11238:
		case 11240:
		case 11242:
		case 11244:
		case 11246:
		case 11248:
		case 15513:
		case 11250:
		case 11252:
		case 11254:
		case 13337:
		case 11256:
		case 15515:
		case 15517:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleItem(Player player, int i) {
		if (i == 11238) { //Baby Impling
			ImplingLoot.baby(player, i); //Looting
		}
		if (i == 11240) { //Young Impling
			ImplingLoot.young(player, i); //Looting
		}
		if (i == 11242) { //Gourmet Impling
			ImplingLoot.gourmet(player, i); //Looting
		}
		if (i == 11244) { //Earth Impling
			ImplingLoot.earth(player, i); //Looting
		}
		if (i == 11246) { //Essence Impling
			ImplingLoot.essence(player, i); //Looting
		}
		if (i == 11248) { //Eclectic Impling
			ImplingLoot.eclectic(player, i); //Looting
		}
		if (i == 15513) { //Spirit Impling
			ImplingLoot.spirit(player, i); //Looting
		}
		if (i == 11250) { //Nature Impling
			ImplingLoot.nature(player, i); //Looting
		}
		if (i == 11252) { //Magpie Impling
			ImplingLoot.magpie(player, i); //Looting
		}
		if (i == 11254) { //Ninja Impling
			ImplingLoot.ninja(player, i); //Looting
		}
		if (i == 13337) { //Pirate Impling
			ImplingLoot.pirate(player, i); //Looting
		}
		if (i == 11256) { //Dragon Impling
			ImplingLoot.dragon(player, i); //Looting
		}
		if (i == 15515) { //Zombie Impling
			ImplingLoot.zombie(player, i); //Looting
		}
		if (i == 15517) { //Kingly Impling
			ImplingLoot.kingly(player, i); //Looting
		}
	}
	
	
}

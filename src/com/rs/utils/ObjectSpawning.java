package com.rs.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;

public class ObjectSpawning {

	/**
	 * Contains the custom npc spawning
	 */

	public static void spawnNPCS() {
		World.spawnObject(new WorldObject(87309, 10, 0, 3120, 3224, 0), true); // divination rift pale wisps in draynor
		World.spawnObject(new WorldObject(87306, 10, 0, 3121, 3225, 0), true);
		World.spawnObject(new WorldObject(87308, 10, 0, 2988, 3406, 0), true); // divination by fally
		World.spawnObject(new WorldObject(87306, 10, 0, 2990, 3408, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2990, 3407, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2989, 3408, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2989, 3407, 0), true);
		World.spawnObject(new WorldObject(87307, 10, 0, 3302, 3410, 0), true); // varrok bright wisps
		World.spawnObject(new WorldObject(87306, 10, 0, 3303, 3411, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3303, 3412, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3304, 3412, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3304, 3411, 0), true);
		World.spawnObject(new WorldObject(87308, 10, 0, 2736, 3404, 0), true); // div seers village glowing wisps
		World.spawnObject(new WorldObject(87306, 10, 0, 2738, 3406, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2737, 3406, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2737, 3405, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2738, 3405, 0), true);
		World.spawnObject(new WorldObject(87307, 10, 0, 2765, 3599, 0), true);  // div sparkling wisps frienick 
		World.spawnObject(new WorldObject(87306, 10, 0, 2766, 3600, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2767, 3600, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2767, 3601, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2766, 3601, 0), true);
		World.spawnObject(new WorldObject(87309, 10, 0, 2882, 3058, 0), true); // gleaming wisps  Karamja
		World.spawnObject(new WorldObject(87306, 10, 0, 2883, 3060, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2884, 3060, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2884, 3059, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2883, 3059, 0), true);
		World.spawnObject(new WorldObject(87309, 10, 0, 2420, 2861, 0), true); // vibrant wisps
		World.spawnObject(new WorldObject(87306, 10, 0, 2421, 2863, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2421, 2862, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2421, 2862, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2422, 2863, 0), true);
		World.spawnObject(new WorldObject(87307, 10, 0, 3476, 3531, 0), true); // canifis 
		World.spawnObject(new WorldObject(87306, 10, 0, 3478, 3532, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3477, 3532, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3477, 3533, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3478, 3533, 0), true);
		World.spawnObject(new WorldObject(87307, 10, 0, 3547, 3269, 0), true);  // brilliant
		World.spawnObject(new WorldObject(87306, 10, 0, 3548, 3270, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3548, 3271, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3547, 3271, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3547, 3270, 0), true);
		World.spawnObject(new WorldObject(87309, 10, 0, 3800, 3550, 0), true);  // RADIANT
		World.spawnObject(new WorldObject(87306, 10, 0, 3802, 3552, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3801, 3552, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3802, 3551, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3801, 3551, 0), true);
		World.spawnObject(new WorldObject(87310, 10, 0, 3341, 2906, 0), true);  // Luminous
		World.spawnObject(new WorldObject(87306, 10, 0, 3342, 2907, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3342, 2908, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3343, 2908, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 3343, 2907, 0), true);
		World.spawnObject(new WorldObject(87309, 10, 0, 2286, 3053, 0), true);  // INCANDESCENT
		World.spawnObject(new WorldObject(87306, 10, 0, 2288, 3054, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2287, 3054, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2287, 3055, 0), true);
		World.spawnObject(new WorldObject(87306, 10, 0, 2288, 3055, 0), true);
		//Rodiks Zone
		World.spawnObject(new WorldObject(782, 10, 0, 4700, 4704, 1), true);//Bank Booth
		World.spawnObject(new WorldObject(782, 10, 0, 4708, 4704, 1), true);//Bank Booth
		World.spawnObject(new WorldObject(409, 10, 2, 4703, 4716, 1), true);//Alter
	//Bank booths @ home
		World.spawnObject(new WorldObject(782, 10, 0, 3223, 3217, 0), true);//staircase

		//Ladder
		World.spawnObject(new WorldObject(45784, 10, 0, 3680, 4940, 0), true);//staircase
		
		World.spawnObject(new WorldObject(23585, 10, 0, 2826, 2998, 0), true);//staircase
		
		World.spawnObject(new WorldObject(4500, 10, 3, 3077, 4234, 0), true);//tunnel
		
		
		
		World.spawnObject(new WorldObject(1, 10, 0, 2502, 3496, 0), true);//le crate
		
		
		//World.spawnObject(new WorldObject(2473, 10, 0, 4625, 5454, 3), true);//Portal
		
		World.spawnObject(new WorldObject(2620, 10, 0, 2805, 3444, 0), true);//le crate
		//lever
		World.spawnObject(new WorldObject(3241, 10, 0, 2448, 9717, 0), true);//lever
//pass
	
		World.spawnObject(new WorldObject(1, 10, 0, 2495, 9713, 0), true);//Crate
		//boat in morytania
		World.spawnObject(new WorldObject(17955, 10, 0, 3523, 3169, 0), true);//boat
		World.spawnObject(new WorldObject(17955, 10, 3, 3593, 3178, 0), true);//boat
		World.spawnObject(new WorldObject(12798, 10, 3, 3494, 3211, 0), true);//Bank booth
		
		//Zogre training grounds
		
		World.spawnObject(new WorldObject(6881, 10, 0, 2456, 3047, 0), true);//barricade
		
		//ladder in morytania
		World.spawnObject(new WorldObject(12907, 10, 1, 3589, 3173, 0), true);//ladder
		//key statue
		World.spawnObject(new WorldObject(18046, 10, 2, 3641, 3304, 0), true);//statue with key
		
		
		//Donator zone
		World.spawnObject(new WorldObject(3192, 10, 5, 2823, 3867, 0), true);//Score board
		World.spawnObject(new WorldObject(782, 10, 2, 2832, 3869, 0), true);//Bank Booth
		World.spawnObject(new WorldObject(782, 10, 2, 2833, 3869, 0), true);//Bank Booth
		
		
		
		/**Home**/
		World.spawnObject(new WorldObject(2213, 10, 1, 2982, 3294, 0), true);//bank booth
		World.spawnObject(new WorldObject(2213, 10, 1, 2982, 3296, 0), true);//bank booth
		World.spawnObject(new WorldObject(2213, 10, 1, 2982, 3298, 0), true);//bank booth
		World.spawnObject(new WorldObject(2563, 10, 2, 2985, 3292, 0), true);//completionist cape stand
		World.spawnObject(new WorldObject(380, 10, 1, 2983, 3299, 0), true);//animations books
		World.spawnObject(new WorldObject(15482, 10, 2, 2990, 3302, 0), true);//construction teleport
		/**End of Home**/
		/**Mining area**/
		World.spawnObject(new WorldObject(2213, 10, 1, 3298, 3307, 0), true);//bank booth
		/**End of Mining area**/
		/**Farming Area**/
		World.spawnObject(new WorldObject(8135, 10, 0, 2203, 3294, 0), true);//Herb Patch
		//Crates
		World.spawnObject(new WorldObject(2790, 10, 1, 2508, 3084, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2200, 3291, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2200, 3292, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2200, 3293, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2200, 3294, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2200, 3295, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2200, 3296, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2200, 3297, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2201, 3297, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2202, 3297, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2203, 3297, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2204, 3297, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2205, 3297, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2206, 3297, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2207, 3297, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2207, 3296, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2207, 3295, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2207, 3294, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2207, 3293, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2207, 3292, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2207, 3291, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2206, 3291, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2205, 3291, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2204, 3291, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2203, 3291, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2202, 3291, 0), true);
		World.spawnObject(new WorldObject(78090, 10, 0, 2201, 3291, 0), true);
		
		/**End of Farming Area**/
		
	}

	/**
	 * The NPC classes.
	 */
	private static final Map<Integer, Class<?>> CUSTOM_NPCS = new HashMap<Integer, Class<?>>();

	public static void npcSpawn() {
		int size = 0;
		boolean ignore = false;
		try {
			for (String string : FileUtilities
					.readFile("data/npcs/spawns.txt")) {
				if (string.startsWith("//") || string.equals("")) {
					continue;
				}
				if (string.contains("/*")) {
					ignore = true;
					continue;
				}
				if (ignore) {
					if (string.contains("*/")) {
						ignore = false;
					}
					continue;
				}
				String[] spawn = string.split(" ");
				@SuppressWarnings("unused")
				int id = Integer.parseInt(spawn[0]), x = Integer
						.parseInt(spawn[1]), y = Integer.parseInt(spawn[2]), z = Integer
						.parseInt(spawn[3]), faceDir = Integer
						.parseInt(spawn[4]);
				NPC npc = null;
				Class<?> npcHandler = CUSTOM_NPCS.get(id);
				if (npcHandler == null) {
					npc = new NPC(id, new WorldTile(x, y, z), -1, true, false);
				} else {
					npc = (NPC) npcHandler.getConstructor(int.class)
							.newInstance(id);
				}
				if (npc != null) {
					WorldTile spawnLoc = new WorldTile(x, y, z);
					npc.setLocation(spawnLoc);
					World.spawnNPC(npc.getId(), spawnLoc, -1, true, false);
					size++;
				}
			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
		System.err.println("Loaded " + size + " custom npc spawns!");
	}

}
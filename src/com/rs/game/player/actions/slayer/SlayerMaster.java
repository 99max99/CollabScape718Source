package com.rs.game.player.actions.slayer;

import java.util.HashMap;
import java.util.Map;

public enum SlayerMaster {
	TURAEL(TaskSet.TURAEL, 8273, 3, 1, 1),
        MAZ(TaskSet.MAZ, 8274, 20, 1, 2),
        CHAELDAR(TaskSet.CHAELDAR, 1598, 70, 1, 3),
        DURADEL(TaskSet.DURADEL, 8275, 100, 50, 4),
        KURADEL(TaskSet.KURADEL, 9085, 115, 75, 5);
        

	/**
	 * The slayer master map Integer is the npc id
	 */
	public static final Map<Integer, SlayerMaster> SLAYER_MASTERS = new HashMap<Integer, SlayerMaster>();

	/**
	 * Grab a slayer master by id
	 * 
	 * @param id
	 * @return
	 */
	public static SlayerMaster getMaster(int id) {
		return SLAYER_MASTERS.get(id);
	}

	/**
	 * Populate the mapping.
	 */
	static {
		for (SlayerMaster master : SlayerMaster.values()) {
			SLAYER_MASTERS.put(master.npcId, master);
		}
	}

	private SlayerMaster(TaskSet type, int npcId, int requiredCombatLevel, int requiredSlayerLevel, int slayerMasterID) {
		this.type = type;
		this.npcId = npcId;
		this.requiredCombatLevel = requiredCombatLevel;
                this.reqSlayerLevel = requiredSlayerLevel;
                this.masterID = slayerMasterID;
	}

	/**
	 * The NPC id of the slayer master
	 */
	public int npcId;
	/**
	 * The task set that the slayer master assigns
	 */
	public TaskSet type;
	/**
	 * The combat level required to get tasks from this slayer master
	 */
	public int requiredCombatLevel;
        /*
         * The slayer level required
         */
        public int reqSlayerLevel;
        
        /*
         * Used for point system
         */
        public int masterID;
}
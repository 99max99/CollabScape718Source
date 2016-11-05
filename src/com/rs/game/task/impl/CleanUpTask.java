package com.rs.game.task.impl;

import com.rs.Launcher;
import com.rs.Settings;
import com.rs.cache.Cache;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.game.GameEngine;
import com.rs.game.task.Task;
import com.rs.game.World;
import com.rs.game.Region;
import com.rs.utils.Logger;
import com.alex.store.Index;

public class CleanUpTask extends Task {

	private final static int TICK_DELAY = 1000;

	public CleanUpTask() {
		super(TICK_DELAY, true, TickType.MAIN);
	}

	@Override
	protected void execute() {
		try {

			if (Runtime.getRuntime().freeMemory() < Settings.MIN_FREE_MEM_ALLOWED) {
				ItemDefinitions.clearItemsDefinitions();
				NPCDefinitions.clearNPCDefinitions();
				ObjectDefinitions.clearObjectDefinitions();
				/*for (Region region : World.getRegions().values())
					region.removeMapFromMemory();*/
			}

			for (Index index : Cache.STORE.getIndexes())
				index.resetCachedFiles();

			GameEngine.get().fastExecutor().purge();
			System.gc();

		} catch (Exception e) {
			Logger.handle(e);
		}
	}

}

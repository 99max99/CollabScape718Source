package com.rs.game;

import java.io.Serializable;

import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

/**
 * @author Ivan Gomez
 */
public class ChunkGenerator implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Player player;
	private int[] chunks;

	public ChunkGenerator(Player player) {
		this.player = player;
	}

	public void generate(int x, int y, int z) {
		chunks = RegionBuilder.findEmptyChunkBound(8, 8);
		RegionBuilder.copyAllPlanesMap(x, y, chunks[0], chunks[1], 8);
	}

	public void destroyRegion(final boolean logout) {
		WorldTile tile = new WorldTile(0, 0, 0);
		if (logout) {
			player.setLocation(tile);
		} else {
			player.getControlerManager().removeControlerWithoutCheck();
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					GameEngine.get().slowExecutor().execute(new Runnable() {
						@Override
						public void run() {
							try {
								RegionBuilder.destroyMap(chunks[0], chunks[1],
										8, 8);
								if (!logout) {
									chunks = null;
								}
							} catch (Exception e) {
								e.printStackTrace();
							} catch (Error e) {
								e.printStackTrace();
							}
						}
					});
				}
			}, 1);
		}
	}

	public int[] getChunks() {
		return chunks;
	}

	public int getX() {
		return chunks[0] << 3;
	}

	public int getY() {
		return chunks[1] << 3;
	}

}

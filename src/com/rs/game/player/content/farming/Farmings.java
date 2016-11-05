package com.rs.game.player.content.farming;

import java.io.Serializable;

import com.rs.game.player.Player;
import com.rs.game.player.content.farming.PatchConstants.WorldPatches;

public class Farmings implements Serializable {

	private static final long serialVersionUID = -4354193086001918721L;

	public Patch[] patches;

	public Farmings() {
		patches = new Patch[50];
		for (WorldPatches worldPatch : WorldPatches.values()) {
			patches[worldPatch.getArrayIndex()] = new Patch(worldPatch.getConfigId(), worldPatch.getObjectId());
		}
	}
	
	public void initializePatches() {
		for (WorldPatches worldPatch : WorldPatches.values()) {
			Patch patch = patches[worldPatch.getArrayIndex()];
			if (patch == null)
				patches[worldPatch.getArrayIndex()] = new Patch(worldPatch.getConfigId(), worldPatch.getObjectId());
		}
	}

	public void growAllPatches(Player player) {
		if (patches != null) {
			for (int i = 0;i < patches.length;++i) {
				if (patches[i] != null) {
					if (patches[i].configByFile == -1)
						return;
					patches[i].grow();
					patches[i].updatePatch(player);
				}
			}
		}
	}

	public void updateAllPatches(Player player) {
		if (patches != null) {
			for (int i = 0;i < patches.length;++i) {
				if (patches[i] != null) {
					if (patches[i].configByFile == -1)
						return;
					patches[i].updatePatch(player);
				}
			}
		}
	}

}

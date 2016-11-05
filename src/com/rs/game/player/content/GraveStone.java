package com.rs.game.player.content;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.net.decoders.handlers.ObjectHandler;
import com.rs.game.World;

public class GraveStone {

	private Player player;
	private WorldTile tile;
	private int count;

	public GraveStone(Player player, WorldTile tile, int count) {
		player(player).tile(tile).count(count).start().process();
	}

	public GraveStone start() {
		player().getPackets().sendGameMessage("You have 3 minutes to pickup your loot.");
		return this;
	}

	public boolean process() {
		count(count() - 1);
		int minutes = (int) count() / 60;
		int seconds = (int) count() - (minutes * 60);
		String time = (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);

		if (count() == 0) {
			return finish();
		} else {

			if (count() == 60)
				player().getPackets().sendGameMessage("You have 1 minute left to pickup your loot, so hurry up!");

			if (player().getInterfaceManager().containsInterface(548)) {
				player().getPackets().sendHideIComponent(548, 21, false);
				player().getPackets().sendHideIComponent(548, 22, false);
				player().getPackets().sendIComponentText(548, 22, "" + time);
			} else {
				player().getPackets().sendHideIComponent(746, 187, false);
				player().getPackets().sendHideIComponent(746, 188, false);
				player().getPackets().sendIComponentText(746, 188, "" + time);
			}

		}
		return true;
	}

	public boolean finish() {
		WorldObject object = World.getObject(tile(), 10);
		if (object != null) {
			World.destroySpawnedObject(object, true);
		}
		player().getHintIconsManager().removeUnsavedHintIcon();
		player().getPackets().sendHideIComponent(548, 21, true);
		player().getPackets().sendHideIComponent(548, 22, true);
		player().getPackets().sendHideIComponent(746, 187, true);
		player().getPackets().sendHideIComponent(746, 188, true);
		player().getPackets().sendGameMessage("Your grave collapsed.");
		return false;
	}

	public Player player() {
		return player;
	}

	public GraveStone player(Player player) {
		this.player = player;
		return this;
	}

	public WorldTile tile() {
		return tile;
	}

	public GraveStone tile(WorldTile tile) {
		this.tile = tile;
		return this;
	}

	public int count() {
		return count;
	}

	public GraveStone count(int count) {
		this.count = count;
		return this;
	}

}

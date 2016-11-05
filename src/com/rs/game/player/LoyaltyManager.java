package com.rs.game.player;

import java.util.TimerTask;

import com.rs.cores.CoresManager;

public class LoyaltyManager {

	private static final int INTERFACE_ID = 1143;
	private transient Player player;

	public LoyaltyManager(Player player) {
		this.player = player;
	}

	public void openLoyaltyStore(Player player) {
		player.getPackets().sendWindowsPane(INTERFACE_ID, 0);
	}

	public void startTimer() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			int timer = 1800;

			@Override
			public void run() {
				if (timer == 1) {
					if (player.gameMode == 3) {
						player.getBank().addItem(29980, 1000, true);
						player.getPackets().sendGameMessage("<col=008000>You have recieved 1000 Loyalty Tokens for playing for 30 minutes!");
					} else if (player.gameMode == 2) {
						player.getBank().addItem(29980, 750, true);
						player.getPackets().sendGameMessage("<col=008000>You have recieved 750 Loyalty Tokens for playing for 30 minutes!");
					} else if (player.gameMode == 1) {
						player.getBank().addItem(29980, 500, true);
						player.getPackets().sendGameMessage("<col=008000>You have recieved 500 Loyalty Tokens for playing for 30 minutes!");
					} else if (player.gameMode == 0) {
						player.getBank().addItem(29980, 250, true);
						player.getPackets().sendGameMessage("<col=008000>You have recieved 250 Loyalty Tokens for playing for 30 minutes!");
					}
					timer = 1800;
						}
				if (timer > 0) {
					timer--;
				}
			}
		}, 0L, 1000L);
	}
}
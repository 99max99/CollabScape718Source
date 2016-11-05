package com.rs.game.task.impl;

import com.rs.Launcher;
import com.rs.game.task.Task;
//import com.rs.game.player.content.controler.impl.PestControler;
//import com.rs.game.player.content.controler.impl.SoulwarsControler;
//import com.rs.game.player.content.minigames.impl.PestControl;
//import com.rs.game.player.content.minigames.impl.Soulwars;
import com.rs.game.player.Player;
import com.rs.game.player.OwnedObjectManager;
import com.rs.game.World;
import com.rs.utils.Logger;
import com.rs.utils.ServerMessages;

public class MinuteTask extends Task {

	private boolean checkAgility;
	private int count;

	private final static int TICK_DELAY = 100;

	public MinuteTask() {
		super(TICK_DELAY, true, TickType.MAIN);
	}

	@Override
	protected void execute() {
		try {



			for (Player player : World.getPlayers()) {

				if (player == null)
					continue;

				/*if ((player.isInSoulwarsLobby || player.isInSoulwarsGame) && Soulwars.startedGame) {
					SoulwarsControler.playTimer--;
				}

				if (player.isInSoulwarsLobby && !Soulwars.startedGame) {
					SoulwarsControler.waitTimer--;

					if (player.isInSoulwarsLobby == true && Soulwars.startedGame == false) {
						SoulwarsControler.waitTimer--;

						if (player.isInPestControlLobby == true && PestControl.startedGame == false) {
							PestControler.waitTimer--;
						}
						if (player.isInPestControlGame == true && PestControl.startedGame == true) {
							PestControler.playTimer--;
						}
					}
				}*/
			}

			OwnedObjectManager.processAll();

			checkAgility = !checkAgility;
			count++;

		} catch (Exception e) {
			Logger.handle(e);
		}

	}

}

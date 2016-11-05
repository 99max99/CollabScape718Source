package com.rs.game.player.dialogues.impl;

import com.rs.Settings;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.minigames.ZarosGodwars;
import com.rs.game.npc.vorago.Vorago;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.Player;
import com.rs.game.WorldTile;
import com.rs.game.World;
import com.rs.game.Animation;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;



public class RagoWeek extends Dialogue{

	//private Vorago week;
	
	@Override
	public void start() {
				sendOptionsDialogue("What would you like the current rotation to be?",
					"Team Split",
					"Scopulus",
					"Vitalis", 
					"<col=ff0000>Coming Soon</col>");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1) {
				Vorago.setWeek(1);
				player.getPackets().sendGameMessage("<col=51A3C9>Thank you,"
						+ " the current rotation is now Team Split.</col>");
				end();
			} else if (componentId == OPTION_2) {
				Vorago.setWeek(2);
				player.getPackets().sendGameMessage("<col=51A3C9>Thank you,"
						+ " the current rotation is now Scopulus.</col>");
				end();
			} else if (componentId == OPTION_3) {
				Vorago.setWeek(3);
				player.getPackets().sendGameMessage("<col=51A3C9>Thank you,"
					+ " the current rotation is now Vitalis.</col>");
			end();
			} else {
				player.getPackets().sendGameMessage("The rotation remains the same.");
				end();
			}
		}
	}
		
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
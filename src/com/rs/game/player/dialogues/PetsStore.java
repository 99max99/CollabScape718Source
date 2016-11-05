package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

public class PetsStore extends Dialogue{


		private int npcId;

		@Override
		public void start() {
			sendOptionsDialogue("Shop Catagories",
					"Pet Store 1",
					"Pet Store 2",
					"Nevermind");
			stage = 0;
		}
		

		@Override
		public void run(int interfaceId, int componentId) {
			if (stage == 0) {		
				if (componentId == OPTION_1) { 
					ShopsHandler.openShop(player, 71); // general
				} else if (componentId == OPTION_2) { //PK Token Store
					ShopsHandler.openShop(player, 72);
				} else if (componentId == OPTION_3) {//loyalty
					end();
				}
			}
				
		}
				@Override
				public void finish() {
				}
		

	}



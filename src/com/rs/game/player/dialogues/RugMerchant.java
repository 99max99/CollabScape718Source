package com.rs.game.player.dialogues;

import java.util.TimerTask;

import com.rs.cores.CoresManager;
import com.rs.game.Graphics;
import com.rs.game.WorldTile;

public class RugMerchant extends Dialogue {

	public WorldTile BASE;
	public WorldTile UZER = new WorldTile(3469, 3113, 0);
	public WorldTile BEDABIN = new WorldTile(3180, 3045, 0);
	public WorldTile POLLN = new WorldTile(3349, 3003, 0);
	public WorldTile SHANTAY = new WorldTile(3308, 3109, 0);
	public WorldTile NARDAH = new WorldTile(3401, 2916, 0);
	public WorldTile MENAPHOS = new WorldTile(3245, 2813, 0);
	public WorldTile SOPHANOM = new WorldTile(3285, 2813, 0);
	public WorldTile POLLS = new WorldTile(3351, 2942, 0);

	@Override
	public void start() {
		if ((boolean) parameters[0])
			getLocations();
		else
			sendPlayerDialogue(9827, "Hello.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(2291, 9827, "Greetings, desert traveller. Do you require the services of Ali Morrisane's flying carpet fleet?");
			break;
		case 0:
			stage = 1;
			sendOptionsDialogue("Choose an option:", "Tell me about Ali Morrisane.", "Tell me about this magic carpet fleet.", "Yes.", "No.");
			break;
		case 1:
			switch (componentId) {
			case OPTION_1:
				stage = 2;
				sendPlayerDialogue(9827, "Tell me about Ali Morrisane.");
				break;
			case OPTION_2:
				stage = 12;
				sendPlayerDialogue(9827, "Tell me about this magic carpet fleet.");
				break;
			case OPTION_3:
				stage = 17;
				sendNPCDialogue(2291, 9827, "From here you can travel to many locations.");
				break;
			default:
				stage = -2;
				sendPlayerDialogue(9827, "No.");
				break;
			}
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(2291, 9827, "What? You haven't heard of Ali Morrisane? Possibly the<br>greatest salesman in all the Kharidian Desert, if not all<br>RuneScape?");
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "I can't say that I have, but he must be the ambitious type<br>to set up his own airline.");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(2291, 9827, "I reckon that he's trying to take on those gnomes at their<br>own game, and I'd bet good money that he'll probably win.");
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "Huh? You've lost me.");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(2291, 9827, "You know, the little guys...the ones that aren't dwarves.");
			break;
		case 7:
			stage = 8;
			sendPlayerDialogue(9827, "Yeah, gnomes - I'm with you that far.");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue(2291, 9827, "Well, they have already established Gnome Air...");
			break;
		case 9:
			stage = 10;
			sendPlayerDialogue(9827, "Go on.");
			break;
		case 10:
			stage = 11;
			sendNPCDialogue(2291, 9827, "Anyway, I think Ali M's setup here will prove really<br>successful, and maybe once we're properly established we<br>could compete with those gnomes.");
			break;
		case 11:
			stage = -2;
			sendPlayerDialogue(9827, "I'll watch this space.");
			break;
		case 12:
			stage = 13;
			sendNPCDialogue(2291, 9827, "The latest idea from the great Ali Morrisane. Desert<br>travel will never be the same again.");
			break;
		case 13:
			stage = 14;
			sendPlayerDialogue(9827, "So how does it work?");
			break;
		case 14:
			stage = 15;
			sendNPCDialogue(2291, 9827, "The carpet or the whole enterprise?");
			break;
		case 15:
			stage = -2;
			sendPlayerDialogue(9827, "Nevermind.");
			break;
		case 16:
			stage = 17;
			sendNPCDialogue(2291, 9827, "The second major carpet hub station in south Pollnivneach<br>is in easy walking distance from there.");
			break;
		case 17:
			getLocations();
			break;
		case 18:
			end();
			switch (componentId) {
			case OPTION_1:
				takeRug(UZER);
				break;
			case OPTION_2:
				takeRug(BEDABIN);
				break;
			case OPTION_3:
				takeRug(POLLN);
				break;
			case OPTION_4:
				takeRug(POLLS);
				break;
			case OPTION_5:
				end();
				break;
			}
		case 19:
			end();
			switch (componentId) {
			case OPTION_1:
				takeRug(SHANTAY);
				break;
			case OPTION_2:
				end();
				break;
			}
		case 20:
			end();
			switch (componentId) {
			case OPTION_1:
				takeRug(SHANTAY);
				break;
			case OPTION_2:
				takeRug(NARDAH);
				break;
			case OPTION_3:
				takeRug(MENAPHOS);
				break;
			case OPTION_4:
				takeRug(SOPHANOM);
				break;
			case OPTION_5:
				end();
				break;
			}
		case 21:
			end();
			switch (componentId) {
			case OPTION_1:
				takeRug(POLLS);
				break;
			case OPTION_2:
				end();
				break;
			}
		default:
			end();
			break;
		}
	}

	private void getLocations() {
		int options = getTravels();
		if (options == 1) {
			stage = 18;
			BASE = SHANTAY;
			sendOptionsDialogue("Where do you wish to travel?", "I want to travel to Uzer.", "I want to travel to the Bedabin Camp.", "I want to travel to north Pollnivneach.", "I want to travel to south Pollnivneach.", "I don't want to travel any of those places.");
		} else if (options == 2 || options == 3 || options == 4) {
			stage = 19;
			if (options == 2)
				BASE = BEDABIN;
			else if (options == 3)
				BASE = UZER;
			else
				BASE = POLLN;
			sendOptionsDialogue("Where do you wish to travel?", "I want to travel to the Shantay Pass.", "I don't want to travel any of those places.");
		} else if (options == 5) {
			stage = 20;
			BASE = POLLS;
			sendOptionsDialogue("Where do you wish to travel?", "I want to travel to the Shantay Pass.", "I want to travel to Nardah.", "I want to travel to Menaphos", "I want to travel to Sophanom.", "I don't want to travel any of those places.");
		} else if (options == 6 || options == 7 || options == 8) {
			stage = 21;
			if (options == 6)
				BASE = NARDAH;
			else if (options == 7)
				BASE = SOPHANOM;
			else
				BASE = MENAPHOS;
			sendOptionsDialogue("Where do you wish to travel?", "I want to travel to the South of Pollnivneach.", "I don't want to travel any of those places.");
		}
	}

	public int getTravels() {
		int x = player.getX();
		int y = player.getY();
		if (x >= 3175 && x <= 3189 && y >= 3033 && y <= 3052) { // Bedabin Camp
			return 2;
		} else if (x >= 3294 && x <= 3318 && y >= 3101 && y <= 3116) { // Shantay
																		// Pass
			return 1;
		} else if (x >= 3461 && x <= 3478 && y >= 3102 && y <= 3119) { // Ruins
																		// of
																		// Uzer
			return 3;
		} else if (x >= 3346 && x <= 3362 && y >= 2989 && y <= 3009) { // Pollnivneach
																		// North
			return 4;
		} else if (x >= 3339 && x <= 3356 && y >= 2932 && y <= 2951) { // Pollnivneach
																		// South
			return 5;
		} else if (x >= 3392 && x <= 3407 && y >= 2911 && y <= 2926) { // Nardah
			return 6;
		} else if (x >= 3279 && x <= 3298 && y >= 2803 && y <= 2820) { // Sophanom
			return 7;
		} else if (x >= 3237 && x <= 3254 && y >= 2800 && y <= 2820) { // Menaphos
			return 8;
		} else {
			return 0; // Shouldn't Happen
		}
	}

	public void takeRug(final WorldTile destination) {
		end();
		player.setNextWorldTile(BASE);
		player.setNextGraphics(new Graphics(2931));
		player.setRunHidden(true);
		CoresManager.fastExecutor.schedule(new TimerTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 4) {
					player.setNextGraphics(new Graphics(2932));
					player.getAppearence().setRenderEmote(191);
					player.addWalkSteps(destination.getX(), destination.getY(), -1, false);
					player.lock();
				}
				if (player.withinDistance(destination, 1)) {
					player.stopAll(true);
					player.getAppearence().setRenderEmote(-1);
					player.setNextGraphics(new Graphics(2933));
					player.unlock();
					cancel();
				}
				loop++;
			}
		}, 0, 500);
		return;
	}

	@Override
	public void finish() {

	}

}

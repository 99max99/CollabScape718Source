package com.rs.game.player.content.botanybay;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

/**
 * @author Chaz
 * @author Taylor Moon
 *
 * @since Dec 15, 2012
 */
public class Chatting extends BotanyBay {

	private static final String GENERAL_NAME = "Botfinder General";

	public static void conversationOne(final String botUsername) {
		for (final Player all : World.getBotanyBay().getPlayersAtIsland()) {
			WorldTasksManager.schedule(new WorldTask() {
				int i;

				public void run() {
					if (i == 5) {
						all.playSound(17574, 2);
						bot("What am I doing here?", botUsername, all);
					} else if (i == 8) {
						all.playSound(17500, 2);
						general("Once more a pernicious user of the clockwork "
								+ "menace appears in my court.", all);
					} else if (i == 12) {
						all.playSound(17539, 2);
						bot("Wait, what?", botUsername, all);
					} else if (i == 16) {
						general("Ladies, gentlemen, they stand accused"
								+ " of conspiring with evil forces to cheat!",
								all);

					} else if (i == 13) {
						all.playSound(17448, 2);
						general("It has made clear by your actions that you are a bot-user!",
								all);
					} else if (i == 13) {
						general("I will waste little time in showing "
								+ "you all that they deserve the harshest of penalties for this crime!",
								all);
					} else if (i == 15) {
						all.playSound(17455, 2);
						general("Perhaps we should pause and reflect for a moment.",
								all);
					} else if (i == 17) {
						general("This bot user... This... THING, has decided that it is quicker and"
								+ " easier for them to use a soulless machive to harvest the bounty of our lands.",
								all);
					} else if (i == 19) {
						all.playSound(17419, 2);
						general("Clearly they lack moral "
								+ "character to adhere to the rules of this realm.",
								all);
					} else if (i == 22) {
						all.playSound(17523, 2);
						general("It is obvious that they"
								+ " must use an unfair advantage to get ahead.",
								all);
					} else if (i == 25) {
						all.playSound(17554, 2);
						general("Why do you hate everything good and pure, you cheating blackguard?",
								all);
					} else if (i == 28) {
						all.playSound(17481, 2);
						bot("I don't hate everything!", botUsername, all);
					} else if (i == 31) {
						all.playSound(17439, 2);
						general("Of course not. For instance... You love cheating!",
								all);
					} else if (i == 34) {
						general("There is no further evidence to present here.",
								all);
					} else if (i == 37) {
						all.playSound(17485, 2);
						general("Your cheating is obvious to see for all!", all);
					} else if (i == 40) {
						all.playSound(17534, 2);
						general("Let us dwell no more on the hellish of this demented rule-breaker.",
								all);
					} else if (i == 43) {
						general("Let us instead make sure that they recieve the punishment they deserve.",
								all);
					} else if (i == 46) {
						all.playSound(17578, 2);
						general("Good folk of the court; honest, flesh-and-blood mortals all.",
								all);
					} else if (i == 52) {
						all.playSound(17496, 2);
						general("I beseech you. I plead on bended "
								+ "knee, to past the most appropriate judegment on this villian. ",
								all);
					} else if (i == 64) {
						all.playSound(17493, 2);
						general("The verdict is guilty. "
								+ "Now, you good people, pass sentence "
								+ "upon them by voting.", all);
						all.out("Boo! Hiss!");
						stop();
					}
					i++;
				}
			}, 0, 1);
		}
	}

	public static void conversationTwo(final String botUsername) {
		for (final Player all : World.getBotanyBay().getPlayersAtIsland()) {
			WorldTasksManager.schedule(new WorldTask() {
				int i;

				public void run() {
					if (i == 5) {
						all.playSound(17556, 2);
						bot("What? Where am I?", botUsername, all);
					} else if (i == 8) {
						all.playSound(17501, 2);
						general("So, you speak do you?", all);
					} else if (i == 12) {
						all.playSound(17527, 2);
						general("I would have believed you were more likely to emit the sound "
								+ "of GRINDING GEARS, YOU CLOCKWORK DEMON!",
								all);
					} else if (i == 16) {
						all.playSound(17547, 2);
						bot("Huh? What?", botUsername, all);
					} else if (i == 13) {
						all.playSound(17494, 2);
						bot("Was this about the bot I was using?", botUsername,
								all);
					} else if (i == 13) {
						general("Correct!", all);
					} else if (i == 15) {
						all.playSound(17542, 2);
						general("I see that your constantly seeking an unfair advantage over others "
								+ "has not dulled your senses too much.", all);
					} else if (i == 17) {
						general("Good people of the court, look here.", all);
					} else if (i == 19) {
						general("This automata-using cheat stands here in plain sight, ready for judegement.",
								all);
					} else if (i == 22) {
						all.playSound(17476, 2);
						general("It is clear they are unrepentant, for we have come to them twice before, "
								+ "begging them pleading with them to halt their rule-breaking.",
								all);
					} else if (i == 25) {
						all.playSound(17507, 2);
						general("BUT THEY DID NOT!", all);
					} else if (i == 28) {
						all.playSound(17517, 2);
						bot("But all I did was use a bot!", botUsername, all);
					} else if (i == 31) {
						all.playSound(17433, 2);
						general("You see? What further proof do we need to send this ingrate to their doom?",
								all);
					} else if (i == 34) {
						general("There is no further evidence to present here.",
								all);
					} else if (i == 37) {
						general("Your cheating is obvious to see for all!", all);
					} else if (i == 40) {
						all.playSound(17534, 2);
						general("Let us dwell no more on the hellish of this demented rule-breaker.",
								all);
					} else if (i == 43) {
						all.playSound(17553, 2);
						general("Let us instead make sure that they recieve the punishment they deserve.",
								all);
					} else if (i == 46) {
						all.playSound(17578, 2);
						general("Good folk of the court; honest, flesh-and-blood mortals all.",
								all);
					} else if (i == 52) {
						all.playSound(17496, 2);
						general("I beseech you. I plead on bended "
								+ "knee, to past the most appropriate judegment on this villian. ",
								all);
					} else if (i == 64) {
						all.playSound(17493, 2);
						general("The verdict is guilty. "
								+ "Now, you good people, pass sentence "
								+ "upon them by voting.", all);
						all.out("Boo! Hiss!");
						stop();
					}
					i++;
				}
			}, 0, 1);
		}
	}

	public static void conversationThree(final String botUsername) {
		for (final Player all : World.getBotanyBay().getPlayersAtIsland()) {
			WorldTasksManager.schedule(new WorldTask() {
				int i;

				public void run() {
					if (i == 5) {
						all.playSound(17574, 2);
						bot("What am I doing here?", botUsername, all);
					} else if (i == 8) {
						all.playSound(17500, 2);
						general("Once more a pernicious user of the clockwork "
								+ "menace appears in my court.", all);
					} else if (i == 12) {
						all.playSound(17539, 2);
						bot("Wait, what?", botUsername, all);
					} else if (i == 16) {
						all.playSound(17573, 2);
						general("Ladies, gentlemen, they stand accused"
								+ " of conspiring with evil forces to cheat!",
								all);

					} else if (i == 13) {
						general("It has made clear by your actions that you are a bot-user!",
								all);
					} else if (i == 13) {
						general("I will waste little time in showing "
								+ "you all that they deserve the harshest of penalties for this crime!",
								all);
					} else if (i == 15) {
						all.playSound(17498, 2);
						general("This cheating vagabond has been seen on no less than three seperate "
								+ "occasions, by men and women of high character...",
								all);
					} else if (i == 17) {
						general("USING A BOT!", all);
					} else if (i == 19) {
						all.playSound(17537, 2);
						bot("... oh no", botUsername, all);
					} else if (i == 22) {
						all.playSound(17472, 2);
						bot("Can I make a plea bargain?", botUsername, all);
					} else if (i == 25) {
						all.playSound(17531, 2);
						general("Not once, twice, but THRICE were they seen to seek an advantage over "
								+ "fair-playing men and women by using forbidden methods!",
								all);
					} else if (i == 28) {
						general("There is no further evidence to present here.",
								all);
					} else if (i == 31) {
						all.playSound(17439, 2);
						general("Of course not. For instance... You love cheating!",
								all);
					} else if (i == 34) {
						general("There is no further evidence to present here.",
								all);
					} else if (i == 37) {
						general("Your cheating is obvious to see for all!", all);
					} else if (i == 40) {
						general("Let us dwell no more on the hellish of this demented rule-breaker.",
								all);
					} else if (i == 43) {

						general("Let us instead make sure that they recieve the punishment they deserve.",
								all);
					} else if (i == 46) {
						all.playSound(17578, 2);
						general("Good folk of the court; honest, flesh-and-blood mortals all.",
								all);
					} else if (i == 52) {
						all.playSound(17496, 2);
						general("I beseech you. I plead on bended "
								+ "knee, to past the most appropriate judegment on this villian. ",
								all);
					} else if (i == 64) {
						all.playSound(17493, 2);
						general("The verdict is guilty. "
								+ "Now, you good people, pass sentence "
								+ "upon them by voting.", all);
						all.out("Boo! Hiss!");
						stop();
					}
					i++;
				}
			}, 0, 1);
		}
	}

	private static void general(String message, Player player) {
		player.out("<col=ffcc22>" + GENERAL_NAME + "</col>: " + message);
	}

	private static void bot(String message, String botusername, Player player) {
		player.out("<col=ffcc22>"
				+ Utils.formatPlayerNameForDisplay(botusername)
				+ ", the accused</col>: " + message);
	}
}

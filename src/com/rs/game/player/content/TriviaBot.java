package com.rs.game.player.content;

import java.util.Random;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Misc;

/**
 * @Author: Danny
 */
public class TriviaBot {
	
	
	private static String songs [][] = { 
		  {"You can find guides on how to play on the forums!", "sdfsdfsaff"},
 };
	
	private static String puzzles [][] = { 
			  {"Gain rewards by voting for the server at www.noszscape.org!", "ahdskajsdakshd"},
 };
	
	private static String server [][] = { 
		  {"Check out the Highscores at www.noszscape.org/Highscores!", "SLDFnskdfnlsmlsdmf"},
 };
	
	private static String general [][] = { 
		  {"Interested in supporting the server? see what you can earn by donating at at www.noszscape.org!", "sjkBKBkjsnKJASB"},
 };
	
	private static String movies [][] = { 
		  {"Have a problem contact any of our staff in-game or on the forums!", "ADSlnlknadlksnada"},
 };
	
	private static String categories [][][] = { songs, puzzles, server, general, movies };
	
	public static int questionid = -1;
	public static int round = 0;
	public static boolean victory = false;
	public static int answers = 0;
	public static int category;

	public TriviaBot() {
		//TODO
	}
	
	public static void Run() {
		category = Misc.random(0, 4);
		int rand = RandomQuestion(category);
		questionid = rand;
		answers = 0;
		victory = false;
		String title = "SERVER";
		if (category == 0)
			title = "SERVER";
		else if (category == 1)
			title = "SERVER";
		else if (category == 2)
			title = "SERVER";
		else if (category == 3)
			title = "SERVER";
		else if (category == 4)
			title = "SERVER";
		for(Player participant : World.getPlayers()) {
			if(participant == null)
				continue;
				participant.hasAnswered = false;
				participant.getPackets().sendGameMessage("<col=ff0066>["+title+"] "+categories[category][rand][0]+"</col>");
		}
	}
	
	public static void sendRoundWinner(String winner, Player player) {
		for(Player participant : World.getPlayers()) {
			if(participant == null)
				continue;
			if (answers <= 5) {
				answers++;
				if (answers == 5)
					victory = true;
				player.TriviaPoints++;
				//player.getPackets().sendGameMessage("<col=56A5EC>[Trivia] "+winner+", you now have "+player.TriviaPoints+" Trivia Points.</col>");
				player.hasAnswered = true;
				//World.sendWorldMessage("<col=56A5EC>[Winner] <col=FF0000>"+ winner +"</col><col=56A5EC> answered the question correctly ("+answers+"/5)!</col>", false);
				return;
			}
		}
	}
	
	public static void verifyAnswer(final Player player, String answer) {
		if(victory) {
			player.getPackets().sendGameMessage("That round has already been won, wait for the next round.");
		} else if (player.hasAnswered) {
			player.getPackets().sendGameMessage("You have already answered this question.");
		} else if(categories[category][questionid][1].equalsIgnoreCase(answer)) {
			round++;
			sendRoundWinner(player.getDisplayName(), player);
		} else {
			player.getPackets().sendGameMessage("That answer wasn't correct, please try it again.");
		}
	}
	
	public static int RandomQuestion(int i) {
		int random = 0;
		Random rand = new Random();
		random = rand.nextInt(categories[i].length);
		return random;
	}
	
	public static boolean TriviaArea(final Player participant) {
		if(participant.getX() >= 2630 && participant.getX() <= 2660 && participant.getY() >= 9377 && participant.getY() <= 9400) {
			return true;
		}
		return false;
	}
}

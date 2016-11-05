package com.rs.utils;


import java.sql.ResultSet;
import java.sql.SQLException;


import com.rs.Settings;
import com.rs.game.DatabasePool;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Utils;


public class VoteManager extends DatabasePool{
	public static int[][] randomID = { { 19785}, { 19786},
			{ 11663}, { 11664}, { 11665}, { 15441},
			{ 15442}, { 15443}, { 15444}, { 19784},
			{ 11730}, { 23659} };
	//public static int[][] randomID = { {1044}, {1046} };
	public static boolean checkPlayerVote(Player player) {
		if (!connect(votedb)) {
			player.sendMessage("A connection could not be established with the database. Please try again.");
			return false;
		}
		try {
			String playerName = formatName(player.getUsername());
			ResultSet rs = DatabasePool.executeQuery("SELECT * FROM voters WHERE username='"+playerName+"' LIMIT 1");
				if (!rs.next()) {
					player.getPackets().sendGameMessage("You havent voted yet, type ::vote to do so now!");
					return true;
				}
				int vote = rs.getInt("vote");
				if (vote == 1) {
					DatabasePool.executeUpdate("UPDATE voters SET vote='0' WHERE username='"+playerName+"'");
					claimPrize(player);
				} else {
					player.getPackets().sendGameMessage("You have already claimed your reward! Vote again for another!");
				}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return false;
	}
	
	public static void claimPrize(Player player) {
		//for(int id : randomID[Utils.random(randomID.length)]) {
			player.getInventory().addItem(995, 1000000);
			//player.getInventory().deleteItem(6199, 1);
			//}
		//player.setVotePoints(player.getVotePoints() + 10);
		//player.getInventory().addItem(pointPrices[0][0],1);
		//player.getInventory().addItem(995, 500000);
		//player.getInventory().addItem(29977, 250);
		player.setLastVote(Utils.currentTimeMillis() + 43200000); // 12 hours...
		player.getPackets().sendGameMessage("You have recieved your reward!");
		World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has voted for "+Settings.SERVER_NAME+"!", false);
	}
	
	public static String formatName(String name) {
		if (name == null)
			return "";
		name = name.replaceAll("_", " ");
		name = name.toLowerCase();
		return name;
	}
	
}
/***
** Author: Danny
** Date: December 3, 2013
** Usage: A module used to integrate a Runescape Server with IPBoard forums.
***/
package com.rs.game.mysql.impl;

import com.rs.game.World;
import com.rs.game.player.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForumIntegration {


	private static final int CRYPTION_ID = 347374575;
	private static final String WEBSITE_DOMAIN = "http://rage-scape.com/forums/";
	public static String WEBROOT = WEBSITE_DOMAIN + "login.php?crypt=" + CRYPTION_ID + "&";
	
	public static String prep(String string) {
		string = string.toLowerCase().replace(" ","_");
		return string;
	}
	
	public static int grabData(String data) {
		try {
		//String URL = WEBSITE_DOMAIN + "serverintegration.php?crypt=" + CRYPTION_ID + "&name="+user.toLowerCase().replace(" ","_")+"&pass="+pass;
			String URL = WEBROOT + data;
			HttpURLConnection CONNECTION_HANDLE = (HttpURLConnection) new URL(URL).openConnection();
			BufferedReader OUTPUT_HANDLE = new BufferedReader(new InputStreamReader(CONNECTION_HANDLE.getInputStream()));

			String OUTPUT_DATA = OUTPUT_HANDLE.readLine().trim();
			return Integer.parseInt(OUTPUT_DATA);
		} catch (Exception e) { e.printStackTrace(); }
		
		return -1;
	}
	
	
	public static int ProcessUserLogin(String user, String pass){
		try {
			Player temp = World.getLobbyPlayerByDisplayName(user);
				//Parse returned internet data.
				int RETURN_CODE = grabData("name="+prep(user)+"&pass="+pass);
				int RIGHTS = grabData("name="+prep(user)+"&pass="+pass+"&rights");
				switch(RETURN_CODE){
				
				case -1://Cryption ID does not match
					System.out.println("Wrong CRYPTION_ID.");
					return 2;
					
				case 1://Invalid Password
					temp.getPackets().sendOpenURL("http://rage-scape.com/forums/index.php?/topic/3260-login-issues/");
					System.out.println("Wrong password from the username: " + user + " using the password: "+ pass +".");
					return 2;
					
				case 2://Successful Identification
					if (RIGHTS != 5) { //If Player is not Banned
					System.out.println("Succesful login from the username: " + user + ".");
					return 2;
					} else { //If Player is Banned
						temp.getPackets().sendOpenURL("http://rage-scape.com/forums/index.php?/forum/138-sumbit-an-appeal/");
						System.out.println("The following banned user tried to log in: " + user + ".");
						return 4;
					}
					
				case 0://Member Doesn't Exist
					temp.getPackets().sendOpenURL("http://rage-scape.com/forums/index.php?/topic/3261-how-to-register/");
					System.out.println("No member exits with the username: " + user + ".");
					return 2;
					
				default:
					System.out.println("There is an unknown error...");
					return 2;
					
				}
		} catch(Exception e2){ e2.printStackTrace(); }
		
		System.out.println("Web server not responding.");
		return 2;//website offline
	}
}
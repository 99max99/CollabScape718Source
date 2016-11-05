package com.rs.game.player.content;

import com.rs.game.player.Player;

public class Censor {
	 
    private static String[] BLACKLIST = { "ass", "bastard", "beaner", "bitch", "blow job", "blowjob", "boner", "butt pirate", "carpetmuncher",
                    "chink", "chode", "clit", "my cock", "his cock", "coon", "cum", "cunt", "dick", "dike", "dildo", "dipshit", "douche", "dumbass",
                    "dumbfuck", "dumbshit", "dyke", "fag", "fagg", "faggot", "fatass", "fuck", "gay", "handjob", "hard on", "hoe", "homo", "honkey",
                    "humping", "jackass", "jap", "jerk off", "jizz", "kunt", "kyke", "lesbian", "lesbo", "nigga", "nigger", "niglet", "porch monkey",
                    "porchmonkey", "porn", "prick", "punta", "pussy", "pussy", "puto", "queer", "rimjob", "skank", "skeet", "slut", "spic", "shit",
                    "splooge", "spook", "tard", "suck my", "wank", "wetback", "whore", "xxx",

                    "http", "https", ".com", ".net", "::/", "www.", ".org", ".info", ".biz", ".uk", ".in", ".us.me", ".co", ".ca", ".mobi", ".au", ".es",
                    ".pe", ".xxx", ".tv", ".me", ".me", ".cc", ".asia", ".tk", "no-ip" };

    public static String filter(Player player, String message) {
            if (message == null || player.allowsProfanity())
                    return message;
            String lower = message.toLowerCase();
            for (int i = 0; i < BLACKLIST.length; i++)
                    message = replace(message, lower, BLACKLIST[i], clean(BLACKLIST[i].length()));
            return message;
    }
    
	public static String filter(String string) {
		String replacementString = string;
		for (String s : BLACKLIST) {
			if (string.toLowerCase().contains(s.toLowerCase())) {
				replacementString = replacementString.toLowerCase().replace(s.toLowerCase(), asterisks(s.length()));
			}
		}
		return replacementString;
	}
	
	public static String asterisks(int length) {
		String s = "";
		for (int i = 0; i < length; ++i) s += "*";
		return s;
	}

    private static String replace(String line, String lowerCaseLine, String oldString, String newString) {
            int i = 0;
            if ((i = lowerCaseLine.indexOf(oldString, i)) >= 0) {
                    int oLength = oldString.length();
                    StringBuffer buf = new StringBuffer(line.length() + 15);
                    buf.append(line.substring(0, i)).append(newString);
                    i += oLength;
                    int j = i;
                    while ((i = lowerCaseLine.indexOf(oldString, i)) > 0) {
                            buf.append(line.substring(j, i)).append(newString);
                            i += oLength;
                            j = i;
                    }
                    buf.append(line.substring(j));
                    return buf.toString();
            }
            return line;
    }

    private static String clean(int length) {
            char[] newWord = new char[length];
            for (int i = 0; i < newWord.length; i++) {
                    newWord[i] = '*';
            }
            return new String(newWord);
    }
}
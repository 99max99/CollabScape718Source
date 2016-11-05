package com.rs.tools;

import java.io.File;
import java.io.IOException;

import com.rs.Settings;
import com.rs.cache.Cache;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;


public class Unglitch {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		try {
			Cache.init();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		File dir = new File("./data/characters/");
		File[] accs = dir.listFiles();
		for (File acc : accs) {
			String name = Utils.formatPlayerNameForProtocol(acc.getName().replace(".p", ""));
			System.out.println(acc);
			if (Utils.containsInvalidCharacter(name)) {
				acc.delete();
				return;
			}
			try {
				Player player = (Player) SerializableFilesManager.loadSerializedFile(acc);
					player.setPermBanned(false);
					player.setBanned(0);
					System.out.println(player.getUsername());
				
			} catch( Exception e) {
				e.printStackTrace();
			}
		}
	}

}

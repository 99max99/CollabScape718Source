package com.rs.game.player.content;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;

public class GlobalPlayerInfo {

	/*public void loadGamePlayers() {
		getSavedPlayers().clear();
		for (File file : getAccounts()) {
			try {
				Player player = (Player) SerializableFilesManager.loadSerializedFile(file);
				if (player != null) {
					player.setUsername(file.getName().replaceAll(".p", ""));
					getSavedPlayers().add(player);
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	public static File[] getAccounts() {
		File dir = new File(SerializableFilesManager.PATH);
		return dir.listFiles();
	}

	/**
	 * @return the instance
	 */
	public static GlobalPlayerInfo get() {
		return INSTANCE;
	}

	/**
	 * @return the gamePlayers
	 */
	public List<Player> getSavedPlayers() {
		return savedPlayers;
	}

	private List<Player> savedPlayers = new ArrayList<Player>();

	static final GlobalPlayerInfo INSTANCE = new GlobalPlayerInfo();

	}


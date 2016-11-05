package com.rs.tools;

import java.io.File;
import java.io.IOException;

import com.rs.utils.Utils;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.SerializableFilesManager;

public class EcoReset {

	public static void main(String[] args) throws ClassNotFoundException,
			IOException {		
		File[] chars = new File("data/characters").listFiles();
		for (File acc : chars) {
			try {
				Player player = (Player) SerializableFilesManager
						.loadSerializedFile(acc);
				player.ecoReset();
				SerializableFilesManager.storeSerializableClass(player, acc);
			} catch (Throwable e) {
				e.printStackTrace();
				System.out.println("failed: " + acc.getName());
			}
		}
		System.out.println("Done.");
	}
}
package com.rs.tools;

import java.io.File;
import java.io.IOException;

import com.rs.Settings;
import com.rs.cache.Cache;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class ItemRemoverC {

	public static void main(String[] args) {
		try {
			Cache.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Starting");
		File[] chars = new File("data/characters").listFiles();
		for (File acc : chars) {
			if (Utils.invalidAccountName(acc.getName().replace(".p", ""))) {
				acc.delete();
				continue;
			}
			try {
				Player player = (Player) SerializableFilesManager
						.loadSerializedFile(acc);
				for (int i = 0; i < 30005; i++) {
					player.getBank().removeItem(i);
				}
				for (int i = 0; i < 30005; i++) {
					player.getInventory().getItems()
							.removeAll(new Item(i, Integer.MAX_VALUE));
				}
				for (int i = 0; i < 30005; i++) {
					player.getEquipment().getItems()
							.removeAll(new Item(i, Integer.MAX_VALUE));
				}
				if (player.getFamiliar() != null) {
					player.setFamiliar(null);
				}
				player.starter = 0;
				player.setMoneyInPouch(0);
				SerializableFilesManager.storeSerializableClass(player, acc);
			} catch (Throwable e) {
				acc.delete();
				System.out.println("failed: " + acc.getName());
			}
		}
		System.out.println("Done.");
	}
}

package com.rs.game.player.content.construction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ConcurrentModificationException;

import com.rs.game.player.Player;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class loadHouse {
	
	private static final String PATH = "data/content/house/";
	private static final String BACKUP_PATH = "data/content/house/backup";
	
	public synchronized static void saveHouse(Player player) {
		try {
			storeSerializableClass(player, new File(PATH + player.getUsername()
					+ ".p"));
		} catch (ConcurrentModificationException e) {
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}
	
	public synchronized static Player loadHousee(Player player) {
		try {
			return (Player) loadSerializedFile(new File(PATH + player.getUsername() + ".p"));
		} catch (Throwable e) {
			Logger.handle(e);
		}
		try {
			Logger.log("SerializableFilesManager", "Recovering account: "
					+ player.getUsername());
			return (Player) loadSerializedFile(new File(BACKUP_PATH + player.getUsername()
					+ ".p"));
		} catch (Throwable e) {
			Logger.handle(e);
		}
		return null;
	}
	
	public static boolean createBackup(String username) {
		try {
			Utils.copyFile(new File(PATH + username + ".p"), new File(
					BACKUP_PATH + username + ".p"));
			return true;
		} catch (Throwable e) {
			Logger.handle(e);
			return false;
		}
	}
	
	
	
	public static final void storeSerializableClass(Serializable o, File f)
			throws IOException {

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
		out.writeObject(o);
		out.close();
	}
	
	public static final Object loadSerializedFile(File f) throws IOException,
				ClassNotFoundException {
			if (!f.exists())
				return null;
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
						Object object = in.readObject();
							in.close();
								return object;
		}

}

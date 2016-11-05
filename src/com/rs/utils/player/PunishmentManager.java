package com.rs.utils.player;

import java.io.File;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

import com.rs.Settings;
import com.rs.game.player.Player;
import com.rs.utils.Logger;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class PunishmentManager implements Serializable {
	private static final long serialVersionUID = 8855717775104277173L;
	private long banned, muted;
	private boolean locked;
	private Player player;

	public PunishmentManager() {

	}

	public static enum Punishments {
		BAN, IP_BAN, LOCK, MUTE
	}

	public boolean dispatch(Punishments type) {
		if (checkPunishment(type)) {
			Logger.log("PunishmentManager", player.getDisplayName() + "'s account is already effected by this punishment.");
			return false;
		}
		switch (type) {
		case BAN:
			ban();
			return true;
		case IP_BAN:
			ipBan();
			return true;
		case MUTE:
			mute();
			return true;
		case LOCK:
			toggleLock();
			return true;
		default:
			return false;
		}
	}

	public boolean checkPunishment(Punishments type) {
		switch (type) {
		case BAN:
			return banned > Utils.currentTimeMillis();
		case MUTE:
			return muted > Utils.currentTimeMillis();
		case LOCK:
			return locked;
		default:
			return false;
		}
	}

	private void ban() {
		banned = Utils.currentTimeMillis() + (48 * 60 * 60 * 1000);
		player.sm("Your account has been unbanned, to prevent future bans please make sure to read our rules.");
		finish();
		return;
	}

	private void ipBan() {
		ipList.add(player.getSession().getIP());
		finish();
	}

	private void mute() {
		muted = Utils.currentTimeMillis() + (48 * 60 * 60 * 1000);
		sendMutedNotification();
		if (!player.hasStarted())
			SerializableFilesManager.savePlayer(player);
	}

	private void finish() {
		if (!player.hasStarted()) {
			SerializableFilesManager.savePlayer(player);
			return;
		}
		player.getControlerManager().removeControlerWithoutCheck();
		player.setNextWorldTile(Settings.START_PLAYER_LOCATION);
		player.getSession().getChannel().close();
	}

	public void sendMutedNotification() {
		player.getPackets().sendGameMessage("You have been temporarily muted due to breaking a rule.");
		player.getPackets().sendGameMessage("To prevent further mutes, please read the rules.");
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	private void toggleLock() {
		locked = !locked;
		finish();
	}

	public static CopyOnWriteArrayList<String> ipList;
	private static final String PATH = "data/bannedIPS.ser";
	private static boolean edited;

	@SuppressWarnings("unchecked")
	public static void init() {
		File file = new File(PATH);
		if (file.exists())
			try {
				ipList = (CopyOnWriteArrayList<String>) SerializableFilesManager.loadSerializedFile(file);
				return;
			} catch (Throwable e) {
				Logger.handle(e);
			}
		ipList = new CopyOnWriteArrayList<String>();
	}

	public static final void save() {
		if (!edited)
			return;
		try {
			SerializableFilesManager.storeSerializableClass(ipList, new File(PATH));
			edited = false;
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	public static boolean isBanned(String ip) {
		return ipList.contains(ip);
	}

	public static void unban(Player player) {
		ipList.remove(player.getLastIP());
		edited = true;
		save();
	}

	public static void checkCurrent() {
		for (String list : ipList) {
			System.out.println(list);
		}
	}

	public static CopyOnWriteArrayList<String> getList() {
		return ipList;
	}
}
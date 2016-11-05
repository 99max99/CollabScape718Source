package com.rs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import com.rs.game.player.Player;
import com.rs.utils.Logger;
import com.rs.game.player.content.clans.Clan;
import com.rs.game.player.content.grandExchange.Offer;
import com.rs.game.player.content.grandExchange.OfferHistory;

public class SerializableFilesManager {

	public static final String PATH = "data/characters/";
	private static final String BACKUP_PATH = "data/charactersBackup/";
	private static final String CLAN_PATH = "data/clans/";
    private static final String GE_OFFERS = "data/grandExchangeOffers.ser";
    private static final String GE_OFFERS_HISTORY = "data/grandExchangeOffersTrack.ser";
    private static final String GE_PRICES = "data/grandExchangePrices.ser";

	public synchronized static final boolean containsPlayer(String username) {
		return new File(PATH + username + ".p").exists();
	}

	public synchronized static Player loadPlayer(String username) {
		try {
			return (Player) loadSerializedFile(new File(PATH + username + ".p"));
		} catch (Throwable e) {
			Logger.handle(e);
		}
		try {
			Logger.log("SerializableFilesManager", "Recovering account: "
					+ username);
			return (Player) loadSerializedFile(new File(BACKUP_PATH + username
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

	public synchronized static void savePlayer(Player player) {
		try {
			storeSerializableClass(player, new File(PATH + player.getUsername()
					+ ".p"));
		} catch (ConcurrentModificationException e) {
			//happens because saving and logging out same time
		} catch (Throwable e) {
			Logger.handle(e);
		}
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

	public static final void storeSerializableClass(Serializable o, File f)
			throws IOException {

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
		out.writeObject(o);
		out.close();
	}

	private SerializableFilesManager() {

	}
	

    public synchronized static boolean containsClan(String name) {
    	return new File(CLAN_PATH + name + ".c").exists();
    }

    public synchronized static Clan loadClan(String name) {
	try {
		return (Clan) loadSerializedFile(new File(CLAN_PATH + name + ".c"));
	}
	catch (Throwable e) {
	    Logger.handle(e);
	}
	return null;
    }
    
    public synchronized static void saveClan(Clan clan) {
	try {
		storeSerializableClass(clan, new File(CLAN_PATH + clan.getClanName() + ".c"));
	}
	catch (Throwable e) {
	    Logger.handle(e);
	}
    }

    public synchronized static void deleteClan(Clan clan) {
	try {
		new File(CLAN_PATH + clan.getClanName() + ".c").delete();
	}
	catch (Throwable t) {
	    Logger.handle(t);
	}
    }
    
    @SuppressWarnings("unchecked")
    public static synchronized HashMap<Long, Offer> loadGEOffers() {
	if (fileExists(GE_OFFERS)) {
	    try {
			return (HashMap<Long, Offer>) SerializableFilesManager.loadSerializedFile(new File(GE_OFFERS));
	    }
	    catch (Throwable t) {
		Logger.handle(t);
		return null;
	    }
	}
	else {
	    return new HashMap<Long, Offer>();
	}
    }
    
    @SuppressWarnings("unchecked")
    public static synchronized ArrayList<OfferHistory> loadGEHistory() {
	if (fileExists(GE_OFFERS_HISTORY)) {
	    try {
			return (ArrayList<OfferHistory>) SerializableFilesManager.loadSerializedFile(new File(GE_OFFERS_HISTORY));
	    }
	    catch (Throwable t) {
		Logger.handle(t);
		return null;
	    }
	}
	else {
	    return new ArrayList<OfferHistory>();
	}
    }
    
    @SuppressWarnings("unchecked")
    public static synchronized HashMap<Integer, Integer> loadGEPrices() {
	if (fileExists(GE_PRICES)) {
	    try {
			return (HashMap<Integer, Integer>) SerializableFilesManager.loadSerializedFile(new File(GE_PRICES));
	    }
	    catch (Throwable t) {
		Logger.handle(t);
		return null;
	    }
	}
	else {
	    return new HashMap<Integer, Integer>();
	}
    }
    
    public static synchronized void saveGEOffers(HashMap<Long, Offer> offers) {
	try {
		SerializableFilesManager.storeSerializableClass(offers, new File(GE_OFFERS));
	}
	catch (Throwable t) {
	    Logger.handle(t);
	}
    }
    
    public static synchronized void saveGEHistory(ArrayList<OfferHistory> history) {
	try {
		SerializableFilesManager.storeSerializableClass(history, new File(GE_OFFERS_HISTORY));
	}
	catch (Throwable t) {
	    Logger.handle(t);
	}
    }
    
    public static synchronized void saveGEPrices(HashMap<Integer, Integer> prices) {
	try {
		SerializableFilesManager.storeSerializableClass(prices, new File(GE_PRICES));
	}
	catch (Throwable t) {
	    Logger.handle(t);
	}
    }
    
	public static boolean fileExists(String dir) {
		 return new File(dir).exists();
	}

    

}

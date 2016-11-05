package com.alex.tools.clientCacheUpdater;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import com.alex.loaders.images.IndexedColorImageFile;
import com.alex.store.Index;
import com.alex.store.Store;
import com.alex.utils.Constants;
import com.alex.utils.Utils;

public class CacheEditor {

	@SuppressWarnings("resource")
	public static byte[] getBytesFromFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);
	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	    }
	    byte[] bytes = new byte[(int)length];
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }
	    is.close();
	    return bytes;
	}
	public static byte[] getImage(File file) throws IOException {
		ImageOutputStream stream = ImageIO.createImageOutputStream(file);
		byte[] data = new byte[(int) stream.length()];
		stream.read(data);
		return data;
	}
	
	/**
	 * 
	 * 	START OF THE ADDING INTO THE CACHE-------------------------------------------------------
	 */
	
	public static void main(String[] args) throws IOException {
		Store cache = new Store("718/cache/");
		System.out.println("Adding Custom Sprites...");
		
		/*
		 * Adding The Citellum Flag.
		 */
		
		System.out.println("Packing Flag...");
		cache.getIndexes()[8].putFile(2173, 0,  Constants.GZIP_COMPRESSION, 
				new IndexedColorImageFile(ImageIO.read(new File("flag/2173.png"))).encodeFile()
				, null, false, false, -1, -1);
		System.out.println("Added The Citellum Flag!");
		
		
		/*
		 * Adding The Troll in Squeal of Fortune. The One That is Hit.
		 */
		
		
		System.out.println("Packing Squeal Troll...");
		cache.getIndexes()[8].putFile(9891, 0,  Constants.GZIP_COMPRESSION, 
				new IndexedColorImageFile(ImageIO.read(new File("squeal/9891.png"))).encodeFile()
				, null, false, false, -1, -1);
		System.out.println("Added The Squeal of Fortune Troll!");
		
		
		/*
		 * Fixes The Buy Spins.
		 */
		
		
		/*System.out.println("Packing Buy Spins...");
		cache.getIndexes()[8].putFile(10240, 0,  Constants.GZIP_COMPRESSION, 
				new IndexedColorImageFile(ImageIO.read(new File("squeal/10240.png"))).encodeFile()
				, null, false, false, -1, -1);
		System.out.println("Added The Buy Spins Sprite!");
		
	
		/*
		 * Adds The Donator Icons and Crowns.
		 */
		
		
		System.out.println("Packing Crowns...");
			IndexedColorImageFile iconsFile = new IndexedColorImageFile(cache, 1455, 0);
			BufferedImage icon = ImageIO.read(new File("crowns/1455.png"));
		System.out.println("Added Crown "+iconsFile.addImage(icon)+".");
			BufferedImage icon2 = ImageIO.read(new File("crowns/1455f.png"));
			System.out.println("Added Crown "+iconsFile.addImage(icon2)+".");
			BufferedImage icon3 = ImageIO.read(new File("crowns/crown_green.gif"));
			System.out.println("Added Crown "+iconsFile.addImage(icon3)+".");
			BufferedImage icon4 = ImageIO.read(new File("crowns/1455_11.png"));
		System.out.println("Added Crown "+iconsFile.addImage(icon4)+".");
			BufferedImage icon5 = ImageIO.read(new File("crowns/thmod.png"));
			System.out.println("Added Crown "+iconsFile.addImage(icon5)+".");
			cache.getIndexes()[8].putFile(1455, 0, Constants.GZIP_COMPRESSION, iconsFile.encodeFile(), null, false, false, -1, -1);
		System.out.println("Added All Crowns! ");
		
		/*
		 * Adds Music
		 */
		
		
		byte[] data = getBytesFromFile(new File("/Desktop/Blank.mp3"));
		cache.getIndexes()[6].putFile(0, 0, data);
		
		
		
		/*
		 * Adds The Logo.
		 */
		
		
		/*System.out.println("Adding Logo...");
		cache.getIndexes()[8].putFile(2498, 0,  Constants.GZIP_COMPRESSION, 
				new IndexedColorImageFile(ImageIO.read(new File("logo/logo.png"))).encodeFile()
				, null, false, false, -1, -1);
		System.out.println("Added Client Logo!");*/
		
		
		/*
		 * Adds/Replaces The Music Index Using Another Cache.
		 */
		
		
		/*System.out.println("Packing Index 5...");
		Store newCache = new Store(System.getProperty("user.home")+"/Desktop/742/");
		byte[] data = newCache.getIndexes()[5].getFile(0, 0);
		cache.getIndexes()[5].putFile(0, 0, data);
		System.out.println("Added Music - Index 5!");*/
		
		
		/*
		 * Adds The Whole Squeal For Fixed Sprites.
		 */
		
		
		System.out.println("Packing Squeal Sprites...");
		for(int i = 9823; i <= 9853; i++) {
			cache.getIndexes()[8].putFile(i, 0,  Constants.GZIP_COMPRESSION, 
					new IndexedColorImageFile(ImageIO.read(new File("squeal/"+i+".png"))).encodeFile()
					, null, false, false, -1, -1);
		}
		System.out.println("Packed Squeal Sprites!");
		
		
		/*
		 * Adds The Login Screen.
		 */
		
		
		/*System.out.println("Packing Login Screen...");
		for(int i = 4139; i <= 4146; i++) {
			cache.getIndexes()[8].putFile(i, 0,  Constants.GZIP_COMPRESSION, 
					new IndexedColorImageFile(ImageIO.read(new File("login/"+i+".png"))).encodeFile()
					, null, false, false, -1, -1);
		}
		System.out.println("Packed Login Screen!");*/
		
		
		/*
		 * Makes Sure The Sprites Were Added.
		 */
		
	
		System.out.println("Checking...");
		cache.getIndexes()[8].rewriteTable();
		cache.getIndexes()[32].rewriteTable();
		cache.getIndexes()[34].rewriteTable();
		System.out.println("Custom Sprites Have Been Added!");
	}

	public static boolean addMapFile(Index index, String name, byte[] data) {
		int archiveId = index.getArchiveId(name);
		if(archiveId == -1)
			archiveId = index.getTable().getValidArchiveIds().length;
		return index.putFile(archiveId, 0, Constants.GZIP_COMPRESSION, data, null, false, false, Utils.getNameHash(name), -1);
	}
}

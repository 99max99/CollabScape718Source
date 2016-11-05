package app;

import java.io.File;
import java.io.FileInputStream;

import magicfix.jagcached.cache.Cache;
import magicfix.utilities.ByteBuffer;

public class Main {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Wrong syntax!");
			System.err.println("Example: java app.Main cachepath");
			return;
		}
		
		Cache cache = null;
		try {
			cache = Cache.openCache(args[0]);
			cache.generateInformationStoreDescriptor();
		}
		catch (Throwable t) {
			System.err.println("Couldn't open cache, wrong cache path?");
			return;
		}
		
		try {
			int[] change_8 = new int[] { 3871, 3872, 3873, 3874, 3875, 3876 };
			{
				byte[] data = new byte[(int)new File("fixes/patch_main.cf").length()];
				FileInputStream fis = new FileInputStream(new File("fixes/patch_main.cf"));
				fis.read(data);
				fis.close();
				
				cache.getFilesSystem(12).findFolderByID(73).findFileByID(0).setData(new ByteBuffer(data));
				
				for (int id : change_8) {
					data = new byte[(int)new File("fixes/patch_" + id + ".cf").length()];
					fis = new FileInputStream(new File("fixes/patch_" + id + ".cf"));
					fis.read(data);
					fis.close();
					
					cache.getFilesSystem(8).findFolderByID(id).findFileByID(0).setData(new ByteBuffer(data));
				}
				
				cache.close();
				System.err.println("Patching sucessfull!");
			}
		}
		catch (Throwable t) {
			System.err.println("An error occured:");
			t.printStackTrace();
		}
	}

}

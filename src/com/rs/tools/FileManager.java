package com.rs.tools;

import java.io.File;
import java.io.FileWriter;

public class FileManager {

	public static void writeToFile(String fileName, String text) {
		try {
			String[] parts = fileName.split("/");
			
			File file;
			for (int i = 0;i < parts.length-1;++i) {
				file = new File("./logs/" + parts[i]);
				if (!file.exists()) {
					file.mkdir();
				}
			}
			FileWriter writer = new FileWriter("./logs/" + fileName, true);
			writer.write(text + "\r\n");
			writer.close();
		} catch (Exception e) {

		}
	}
	
	public static void logError(String error) {
		System.out.println(error);
		writeToFile("errorLog.txt", error);
	}

}

package com.rs.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.rs.Settings;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.ServerChannelHandler;

public final class Logger {

	public static void handle(Throwable throwable) {
		System.out.println("ERROR! THREAD NAME: "
				+ Thread.currentThread().getName());
		throwable.printStackTrace();
	}

	public static void debug(long processTime) {
		log(Logger.class, "---DEBUG--- start");
		log(Logger.class, "WorldProcessTime: " + processTime);
		log(Logger.class,
				"WorldRunningTasks: " + WorldTasksManager.getTasksCount());
		log(Logger.class,
				"ConnectedChannels: "
						+ ServerChannelHandler.getConnectedChannelsSize());
		log(Logger.class, "---DEBUG--- end");
	}

	public static void log(Object classInstance, Object message) {
		log(classInstance.getClass().getSimpleName(), message);
	}

	public static void log(String className, Object message) {
		String text = "[" + className + "]" + " " + message.toString();
		System.out.println(text);
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(
					Settings.LOG_PATH + "logger.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
					+ " "
					+ Calendar.getInstance().getTimeZone().getDisplayName()
					+ "] " + className +": "+message+".");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}
	
	public static void logMessage(String message) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(
					Settings.LOG_PATH + "logger.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
					+ " "
					+ Calendar.getInstance().getTimeZone().getDisplayName()
					+ "] "+message+"");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}
	
	public static void logDuels(String message) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(
					Settings.LOG_PATH + "pking/duel.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
					+ " "
					+ Calendar.getInstance().getTimeZone().getDisplayName()
					+ "] "+message+"");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}
	
	public static void logControler(String message) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(
					Settings.LOG_PATH + "controler.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
					+ " "
					+ Calendar.getInstance().getTimeZone().getDisplayName()
					+ "] "+message+"");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}

	private Logger() {

	}

}

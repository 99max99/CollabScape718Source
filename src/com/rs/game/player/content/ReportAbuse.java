package com.rs.game.player.content;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import com.rs.Settings;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class ReportAbuse implements Serializable {
	
	private static final long serialVersionUID = 6460657093960737296L;

	private Player reporter;
	private ArrayList<String> victims;

	public ReportAbuse() {
		victims = new ArrayList<String>();
	}

	public void open() {
		if (reporter.getAttackedByDelay() + 10000 > Utils.currentTimeMillis()) {
			reporter.getPackets().sendGameMessage(
					"You cannot do that while under attack!");
			return;
		}
		if (reporter.getInterfaceManager().containsScreenInter()) {
			reporter.getPackets()
					.sendGameMessage(
							"Please finish what you're doing before opening Report Abuse.");
			return;
		}
		if (reporter.getRights() > 0)
			/**
			 * unhides the mute option
			 */
			reporter.getPackets().sendHideIComponent(594, 8, false);
		reporter.getInterfaceManager().sendInterface(594);
	}

	public void handleReport(String victim, int ruleId, boolean mute) {
		Player v = World.getPlayerByDisplayName(victim);
		if (v == null)
			v = SerializableFilesManager.loadPlayer(Utils
					.formatPlayerNameForProtocol(victim));
		if (victims.contains(victim) && reporter.getRights() < 1) {
			reporter.getPackets().sendGameMessage(
					"You have already reported this player!");
			return;
		}
		if (mute) {
			v.setMuted(Utils.currentTimeMillis() + (48 * 60 * 60 * 1000));
			v.getPackets()
					.sendGameMessage(
							"You've been muted for 48 hours by "
									+ reporter.getDisplayName()
									+ ". If you feel this is a mistake, please use the appeal center on the forums to appeal this punishment.");
			reporter.getPackets()
					.sendGameMessage(
							"You have sucessfuly muted "
									+ Utils.formatPlayerNameForDisplay(v
											.getUsername()));
		}
		victims.add(victim);
		reporter.getPackets().sendGameMessage(
				"Thank-you, your abuse report has been received.");
		logReport(v, ruleId, mute);
	}

	private void logReport(Player offender, int offense, boolean mute) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					Settings.LOG_PATH + "reportabuse/" + offender.getUsername() + ".txt",
					true));
			writer.write("ABUSE REPORT AT: " + Calendar.getInstance().getTime()
					+ ", REPROTER: " + reporter.getUsername());
			writer.newLine();
			writer.write("Offence: " + getOffense(offense));
			writer.newLine();
			if (mute) {
				writer.newLine();
				writer.write("This player was muted for 48 hours by the staff member that filed this report. ("
						+ Utils.formatPlayerNameForDisplay(reporter
								.getUsername()) + ")");
				writer.newLine();
				writer.newLine();
			}
			if (!mute)
				writer.newLine();
			writer.write("Cached chat messages in the last 60 seconds:");
			writer.newLine();
			for (String s : offender.getCachedChatMessages()) {
				writer.write(s);
				writer.newLine();
			}
			writer.close();
		} catch (IOException ignored) {
			ignored.printStackTrace();
		}
	}

	public String getOffense(int ruleId) {
		switch (ruleId) {
		case 6:
			return "Buying or selling account";
		case 9:
			return "Encouraging rule breaking";
		case 5:
			return "Staff impersonation";
		case 7:
			return "Macroing/use of bots";
		case 15:
			return "Scamming";
		case 4:
			return "Exploiting a bug";
		case 16:
			return "Seriously offensive language";
		case 17:
			return "Solicitation";
		case 18:
			return "Disruptive behaviour";
		case 19:
			return "Offensive account name";
		case 20:
			return "Real life threats";
		case 13:
			return "Asking for real life info";
		case 21:
			return "Breaking real world laws";
		case 11:
			return "Advertising websites";
		}
		return "No selected reason.";
	}

	public void setPlayer(Player player) {
		reporter = player;
	}
}
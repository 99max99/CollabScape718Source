package com.rs.game.player;

import java.io.Serializable;

/**
 * @author Tyluur <ItsTyluur@Gmail.com>
 * @since 2012-07-29
 */
public final class PlayerData implements Serializable {
	
	private static final long serialVersionUID = -4539101476329520941L;

	public PlayerData() {
		gamePoints = new int[3];
	}
	
	private int[] gamePoints;
	private int invasionPoints;
	private int earningPotential;
	private long lastEPUpdate;
	private String playerTitle, titleColour;

	public int[] getGamePoints() {
		return gamePoints;
	}

	public void setGamePoints(int[] gamePoints) {
		this.gamePoints = gamePoints;
	}

	public int getInvasionPoints() {
		return invasionPoints;
	}

	public void setInvasionPoints(int invasionPoints) {
		this.invasionPoints = invasionPoints;
	}

	
	public int getEarningPotential() {
		return earningPotential;
	}

	public void setEarningPotential(int earningPotential) {
		this.earningPotential = earningPotential;
	}

	public long getLastEPUpdate() {
		return lastEPUpdate;
	}

	public void setLastEPUpdate(long lastEPUpdate) {
		this.lastEPUpdate = lastEPUpdate;
	}

	public String getTitleColour() {
		return titleColour;
	}

	public void setTitleColour(String titleColour) {
		this.titleColour = titleColour;
	}

	public String getPlayerTitle() {
		return playerTitle;
	}

	public void setPlayerTitle(String playerTitle) {
		this.playerTitle = playerTitle;
	}
	
}

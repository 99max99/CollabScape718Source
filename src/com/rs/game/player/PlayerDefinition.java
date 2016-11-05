package com.rs.game.player;

public class PlayerDefinition {

	private int index;
	private String username;
	private String displayName;
	private String password;
	private int rights;
	private long playTime;
	private int donator;
	private long donatorDuration;
	private int mute;
	private long muteDuration;
	private int banned;

	public PlayerDefinition(String username, String password) {
		index(0).username(username).password(password).rights(0).playTime(0).donator(0).donatorDuration(0);
	}

	public int index() {
		return this.index;
	}

	public PlayerDefinition index(int index) {
		this.index = index;
		return this;
	}

	public String username() {
		return this.username;
	}

	public PlayerDefinition username(String username) {
		this.username = username;
		return this;
	}

	public String displayName() {
		return this.displayName;
	}

	public PlayerDefinition displayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	public String password() {
		return this.password;
	}

	public PlayerDefinition password(String password) {
		this.password = password;
		return this;
	}

	public int rights() {
		return this.rights;
	}

	public PlayerDefinition rights(int rights) {
		this.rights = rights;
		return this;
	}

	public int donator() {
		return this.donator;
	}

	public PlayerDefinition donator(int donator) {
		this.donator = donator;
		return this;
	}

	public long playTime() {
		return this.playTime;
	}

	public PlayerDefinition playTime(long playTime) {
		this.playTime = playTime;
		return this;
	}

	public long donatorDuration() {
		return this.donatorDuration;
	}

	public PlayerDefinition donatorDuration(long donatorDuration) {
		this.donatorDuration = donatorDuration;
		return this;
	}

	public int mute() {
		return this.mute;
	}

	public PlayerDefinition mute(int mute) {
		this.mute = mute;
		return this;
	}

	public long muteDuration() {
		return this.muteDuration;
	}

	public PlayerDefinition muteDuration(long muteDuration) {
		this.muteDuration = muteDuration;
		return this;
	}

	public int banned() {
		return this.banned;
	}

	public PlayerDefinition banned(int banned) {
		this.banned = banned;
		return this;
	}

}

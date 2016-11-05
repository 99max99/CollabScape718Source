package com.rs.game.clan;

import java.io.Serializable;
import java.util.ArrayList;

public class Clan implements Serializable {

	private static final long serialVersionUID = -6774619197484195254L;
	
	private String clanName;
	private String clanOwner;
	private ArrayList<ClanMember> clanMembers;
	private int[] clanCustomization;
	
	public Clan(String clanName, String clanOwner) {
		this.clanName = clanName;
		this.clanOwner = clanOwner;
		this.clanMembers = new ArrayList<ClanMember>();
		this.clanCustomization = new int[4];
	}

	public String getClanName() {
		return clanName;
	}

	public void setClanName(String clanName) {
		this.clanName = clanName;
	}

	public ArrayList<ClanMember> getClanMembers() {
		return clanMembers;
	}

	public void setClanMembers(ArrayList<ClanMember> clanMembers) {
		this.clanMembers = clanMembers;
	}

	public int[] getClanCustomization() {
		return clanCustomization;
	}

	public void setClanCustomization(int[] clanCustomization) {
		this.clanCustomization = clanCustomization;
	}

	public String getClanOwner() {
		return clanOwner;
	}

	public void setClanOwner(String clanOwner) {
		this.clanOwner = clanOwner;
	}

}

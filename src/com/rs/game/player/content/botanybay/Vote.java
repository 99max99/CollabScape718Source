package com.rs.game.player.content.botanybay;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author Taylor Moon
 * 
 * TODO finish? lol
 */
public class Vote {

	public static final int MAX_VOTING_TIME = 15;

	public static AtomicInteger crush;

	public static AtomicInteger swallow;

	public static AtomicInteger diety;

	private static ConcurrentHashMap<Death, Number> votes;

	public static boolean inited;

	public static void init() {
		votes = new ConcurrentHashMap<Death, Number>();
		crush = new AtomicInteger();
		swallow = new AtomicInteger();
		diety = new AtomicInteger();
		inited = true;
	}

	public static void putVote(Death death) {
		votes.put(
				(death),
				(death.equals(Death.CRUSH) ? crush : death
						.equals(Death.SWALLOW) ? swallow : death
						.equals(Death.DIETY) ? diety : -1));
	}

	public static void refreshVotes() {
		diety = null;
		crush = null;
		swallow = null;
		votes = null;
		inited = false;
	}

	public static void castVote(int type, boolean addToVoteList) {
		if (!inited) {
			init();
		}
		switch (type) {
		case 1:
			crush.addAndGet(1);
			if (addToVoteList)
				putVote(Death.CRUSH);
			break;
		case 2:
			swallow.addAndGet(1);
			if (addToVoteList)
				putVote(Death.SWALLOW);
			break;
		case 3:
			diety.addAndGet(1);
			if (addToVoteList)
				putVote(Death.DIETY);
			break;
		}
	}

	public static Entry<Death, Number> getFinalVotes() {
		for (Entry<Death, Number> entry : votes.entrySet())
			return entry;
		return null;
	}

	public static final int SWALLOW_TYPE = 2;

	public static final int CRUSH_TYPE = 1;

	public static final int DIETY_TYPE = 3;

	protected enum Death {

		// types of death
		CRUSH, SWALLOW, DIETY;
	}

}

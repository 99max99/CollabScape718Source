package com.rs.game.player.dialogues;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.Player;

public abstract class Dialogue {

	protected Player player;
	protected int stage = -1;

	public Dialogue() {

	}

	public Object[] parameters;

	public void setPlayer(Player player) {
		this.player = player;
	}

	public abstract void start();

	public abstract void run(int interfaceId, int componentId);

	public abstract void finish();

	protected final void end() {
		player.getDialogueManager().finishDialogue();
	}

	protected static final String DEFAULT_OPTIONS_TITLE = "Select an option";
	public static final int NORMAL = 9827, WORRIED = 9775, CONFUSED = 9830, DRUNK = 9835, MAD = 9785, ANGERY = 9790, SAD = 9775, SCARED = 9780;
	protected static final short SEND_1_TEXT_INFO = 210;
	protected static final short SEND_2_TEXT_INFO = 211;
	protected static final short SEND_3_TEXT_INFO = 212;
	protected static final short SEND_4_TEXT_INFO = 213;
	protected static final String SEND_DEFAULT_OPTIONS_TITLE = "Select an Option";
	protected static final short SEND_2_OPTIONS = 236;
	protected static final short SEND_3_OPTIONS = 230;
	protected static final short SEND_4_OPTIONS = 237;
	protected static final short SEND_5_OPTIONS = 238;
	protected static final short SEND_2_LARGE_OPTIONS = 229;
	protected static final short SEND_3_LARGE_OPTIONS = 231;
	protected static final short SEND_1_TEXT_CHAT = 241;
	protected static final short SEND_2_TEXT_CHAT = 242;
	protected static final short SEND_3_TEXT_CHAT = 243;
	protected static final short SEND_4_TEXT_CHAT = 244;
	protected static final short SEND_NO_CONTINUE_1_TEXT_CHAT = 245;
	protected static final short SEND_NO_CONTINUE_2_TEXT_CHAT = 246;
	protected static final short SEND_NO_CONTINUE_3_TEXT_CHAT = 247;
	protected static final short SEND_NO_CONTINUE_4_TEXT_CHAT = 248;
	protected static final short SEND_NO_EMOTE = -1;
	protected static final int HAPPY_FACE = 9843;
	protected static final int ASKING_FACE = 9829;
	protected static final int BLANK_FACE = 9772;
	protected static final int SAD_FACE = 9768;
	protected static final int UPSET_FACE = 9776;
	protected static final int SCARED_FACE = 9780;
	protected static final int MILDLY_ANGRY_FACE = 9784;
	protected static final int ANGRY_FACE = 9788;
	protected static final int VERY_ANGRY_FACE = 9792;
	protected static final int MANIAC_FACE = 9800;
	protected static final int NOT_TALKING_JUST_LISTENING_FACE = 9804;
	protected static final int PLAIN_TALKING_FACE = 9808;
	protected static final int WTF_FACE = 9820;
	protected static final int SHAKING_NO_FACE = 9824;
	protected static final int UNSURE_FACE = 9836;
	protected static final int LISTENS_THEN_LAUGHS_FACE = 9840;
	protected static final int GOOFY_LAUGH_FACE = 9851;
	protected static final int THINKING_THEN_TALKING_FACE = 9859;
	protected static final int NONONO_FACE = 9844;
	protected static final int SAD_WTF_FACE = 9776;
	protected static final byte IS_NOTHING = -1;
	protected static final byte IS_PLAYER = 0;
	protected static final byte IS_NPC = 1;
	protected static final byte IS_ITEM = 2;
	public static String[][] gender = { {"mr", "mrs"}, {"mister", "miss"}, {"lord", "lady"}, {"boy", "girl"}, {"man", "woman"}, {"sir", "m'am"} };
	
	

	private static int[] getIComponentsIds(short interId) {
		int[] childOptions;
		switch (interId) {
		case SEND_1_TEXT_INFO:
			childOptions = new int[1];
			childOptions[0] = 1;
			break;
		case SEND_2_TEXT_INFO:
			childOptions = new int[2];
			childOptions[0] = 1;
			childOptions[1] = 2;
			break;
		case SEND_3_TEXT_INFO:
			childOptions = new int[3];
			childOptions[0] = 1;
			childOptions[1] = 2;
			childOptions[2] = 3;
			break;
		case SEND_4_TEXT_INFO:
			childOptions = new int[4];
			childOptions[0] = 1;
			childOptions[1] = 2;
			childOptions[2] = 3;
			childOptions[3] = 4;
			break;
		case SEND_2_LARGE_OPTIONS:
			childOptions = new int[3];
			childOptions[0] = 1;
			childOptions[1] = 2;
			childOptions[2] = 3;
			break;
		case SEND_3_LARGE_OPTIONS:
			childOptions = new int[4];
			childOptions[0] = 1;
			childOptions[1] = 2;
			childOptions[2] = 3;
			childOptions[3] = 4;
			break;
		case SEND_2_OPTIONS:
			childOptions = new int[3];
			childOptions[0] = 0;
			childOptions[1] = 1;
			childOptions[2] = 2;
			break;
		case SEND_3_OPTIONS:
			childOptions = new int[4];
			childOptions[0] = 1;
			childOptions[1] = 2;
			childOptions[2] = 3;
			childOptions[3] = 4;
			break;
		case SEND_4_OPTIONS:
			childOptions = new int[5];
			childOptions[0] = 0;
			childOptions[1] = 1;
			childOptions[2] = 2;
			childOptions[3] = 3;
			childOptions[4] = 4;
			break;
		case SEND_5_OPTIONS:
			childOptions = new int[6];
			childOptions[0] = 0;
			childOptions[1] = 1;
			childOptions[2] = 2;
			childOptions[3] = 3;
			childOptions[4] = 4;
			childOptions[5] = 5;
			break;
		case SEND_1_TEXT_CHAT:
		case SEND_NO_CONTINUE_1_TEXT_CHAT:
			childOptions = new int[2];
			childOptions[0] = 3;
			childOptions[1] = 4;
			break;
		case SEND_2_TEXT_CHAT:
		case SEND_NO_CONTINUE_2_TEXT_CHAT:
			childOptions = new int[3];
			childOptions[0] = 3;
			childOptions[1] = 4;
			childOptions[2] = 5;
			break;
		case SEND_3_TEXT_CHAT:
		case SEND_NO_CONTINUE_3_TEXT_CHAT:
			childOptions = new int[4];
			childOptions[0] = 3;
			childOptions[1] = 4;
			childOptions[2] = 5;
			childOptions[3] = 6;
			break;
		case SEND_4_TEXT_CHAT:
		case SEND_NO_CONTINUE_4_TEXT_CHAT:
			childOptions = new int[5];
			childOptions[0] = 3;
			childOptions[1] = 4;
			childOptions[2] = 5;
			childOptions[3] = 6;
			childOptions[4] = 7;
			break;
		default:
			return null;
		}
		return childOptions;
	}

	public boolean sendNPCDialogue(int npcId, int animationId, String... text) {
		return sendEntityDialogue(IS_NPC, npcId, animationId, text);
	}
	
	public boolean sendPlayerDialogue(int animationId, String... text) {
		return sendEntityDialogue(IS_PLAYER, -1, animationId, text);
	}
	
    public boolean sendItemDialogue(int itemId, String... text) {
	return sendEntityDialogue(IS_ITEM, itemId, -1, text);
    }
	
	/*
	 * 
	 * auto selects title, new dialogues
	 */
	public boolean sendEntityDialogue(int type, int entityId, int animationId, String... text) {
		String title = "";
		if(type == IS_PLAYER) {
				title = player.getDisplayName();
		}else if(type == IS_NPC) {
				title = NPCDefinitions.getNPCDefinitions(entityId).name;
		}else if (type == IS_ITEM) 
			title = ItemDefinitions.getItemDefinitions(entityId).getName();
		return sendEntityDialogue(type, title, entityId, animationId, text);
	}
	
	/*
	 * idk what it for
	 */
	public int getP() {
		return 1;
	}
	
	
	public static final int OPTION_1 = 11, OPTION_2 = 13, OPTION_3 = 14, OPTION_4 = 15, OPTION_5 = 16;
	
	public boolean sendOptionsDialogue(String title, String... options) {
		player.getInterfaceManager().sendChatBoxInterface(1188);
		player.getPackets().sendIComponentText(1188, 20, title);
		for(int line = 0; line < 5; line++) {
			if(line < options.length) {
				if(line == 0)
					player.getPackets().sendIComponentText(1188, 3, options[line]);
				else if(line == 1)
					player.getPackets().sendIComponentText(1188, 24, options[line]);
				else if(line == 2)
					player.getPackets().sendIComponentText(1188, 29, options[line]);
				else if(line == 3)
					player.getPackets().sendIComponentText(1188, 34, options[line]);
				else if(line == 4)
					player.getPackets().sendIComponentText(1188, 39, options[line]);
			}else {
				if (line == 2) {
					player.getPackets().sendHideIComponent(1188, 14, true);
				}else if (line == 3) {
					player.getPackets().sendHideIComponent(1188, 15, true);
				}else if (line == 4) {
					player.getPackets().sendHideIComponent(1188, 16, true);
				}
			}
		}
		return true;
	}
	
	public static boolean sendNPCDialogueNoContinue(Player player, int npcId, int animationId, String... text) {
		return sendEntityDialogueNoContinue(player, IS_NPC, npcId, animationId, text);
	}
	
	public static boolean sendPlayerDialogueNoContinue(Player player, int animationId, String... text) {
		return sendEntityDialogueNoContinue(player, IS_PLAYER, -1, animationId, text);
	}
	
	/*
	 * 
	 * auto selects title, new dialogues
	 */
	public static boolean sendEntityDialogueNoContinue(Player player, int type, int entityId, int animationId, String... text) {
		String title = "";
		if(type == IS_PLAYER) {
				title = player.getDisplayName();
		}else if(type == IS_NPC) {
				title = NPCDefinitions.getNPCDefinitions(entityId).name;
		}else if (type == IS_ITEM) 
			title = ItemDefinitions.getItemDefinitions(entityId).getName();
		return sendEntityDialogueNoContinue(player, type, title, entityId, animationId, text);
	}
	
	public static boolean sendEntityDialogueNoContinue(Player player, int type, String title, int entityId, int animationId, String... texts) {
		StringBuilder builder = new StringBuilder();
		for(int line = 0; line < texts.length; line++) 
				builder.append(" " + texts[line]);
		String text = builder.toString();
		player.getInterfaceManager().replaceRealChatBoxInterface(1192);
		player.getPackets().sendIComponentText(1192, 16, title);
		player.getPackets().sendIComponentText(1192, 12, text);
		player.getPackets().sendEntityOnIComponent(type == IS_PLAYER, entityId, 1192, 11);
		if(animationId != -1)
			player.getPackets().sendIComponentAnimation(animationId, 1192, 11);
		return true;
	}
	
	public static void closeNoContinueDialogue(Player player) {
		player.getInterfaceManager().closeReplacedRealChatBoxInterface();
	}
	
	/*
	 * new dialogues
	 */
	public boolean sendEntityDialogue(int type, String title, int entityId, int animationId, String... texts) {
		StringBuilder builder = new StringBuilder();
		for(int line = 0; line < texts.length; line++) 
				builder.append(" " + texts[line]);
		String text = builder.toString();
		if(type == IS_NPC) {
			if (!player.getAppearence().isMale()) {
				for (int i = 0; i < gender.length; i++) {
					if (text.contains(gender[i][0]))
						text = text.replace(gender[i][0], gender[i][1]);
				}
			}
			player.getInterfaceManager().sendChatBoxInterface(1184);
			player.getPackets().sendIComponentText(1184, 17, title);
			player.getPackets().sendIComponentText(1184, 13, text);
			player.getPackets().sendNPCOnIComponent(1184, 11, entityId);
			if(animationId != -1)
				player.getPackets().sendIComponentAnimation(animationId, 1184, 11);
		}else if(type == IS_PLAYER) {
			player.getInterfaceManager().sendChatBoxInterface(1191);
			player.getPackets().sendIComponentText(1191, 8, title);
			player.getPackets().sendIComponentText(1191, 17, text);
			player.getPackets().sendPlayerOnIComponent(1191, 15);
			if(animationId != -1)
				player.getPackets().sendIComponentAnimation(animationId, 1191, 15);
		}
		return true;
	}
	
	public boolean sendDialogue(String... texts) {
		StringBuilder builder = new StringBuilder();
		for(int line = 0; line < texts.length; line++) 
				builder.append((line == 0 ? "<p="+getP()+">" : "<br>") + texts[line]);
		String text = builder.toString();
		player.getInterfaceManager().sendChatBoxInterface(1186);
		player.getPackets().sendIComponentText(1186, 1, text);
		return true;
	}

	public boolean sendEntityDialogue(short interId, String[] talkDefinitons, byte type, int entityId, int animationId) {
		if (type == IS_PLAYER || type == IS_NPC) { // auto convert to new
													// dialogue all old
													// dialogues
			String[] texts = new String[talkDefinitons.length - 1];
			for (int i = 0; i < texts.length; i++)
				texts[i] = talkDefinitons[i + 1];
			sendEntityDialogue(type, talkDefinitons[0], entityId, animationId, texts);
			return true;
		}
		int[] componentOptions = getIComponentsIds(interId);
		if (componentOptions == null)
			return false;
		player.getInterfaceManager().sendChatBoxInterface(interId);
		if (talkDefinitons.length != componentOptions.length)
			return false;
		for (int childOptionId = 0; childOptionId < componentOptions.length; childOptionId++)
			player.getPackets().sendIComponentText(interId, componentOptions[childOptionId], talkDefinitons[childOptionId]);
		if (type == IS_PLAYER || type == IS_NPC) {
			player.getPackets().sendEntityOnIComponent(type == IS_PLAYER, entityId, interId, 2);
			if (animationId != -1)
				player.getPackets().sendIComponentAnimation(animationId, interId, 2);
		} else if (type == IS_ITEM)
			player.getPackets().sendItemOnIComponent(interId, 2, entityId, animationId);
		return true;
	}

	/**
	 * Created to avoid using WorldPacketDecoder
	 * @return If the process if finished
	 */
	public boolean run(String input) {
		return false;
	}
	
	/**
	 * Created to avoid using WorldPacketDecoder
	 * @return If the process if finished
	 */
	public boolean run(int input) {
		return false;
	}
	
	
}

package com.rs.game.player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import com.rs.game.player.PlayerData;
import com.rs.game.CustomisedShop;
import com.rs.game.player.content.construction.*;
import com.rs.game.player.content.farming.*;
import com.rs.game.player.content.grandExchange.GrandExchange;
import com.rs.game.player.content.SecuritySystem;

import org.runetoplist.VoteChecker;

import com.rs.Settings;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.game.player.content.FairyRing;
import com.rs.game.player.content.DwarfCannon;
import com.rs.cores.CoresManager;
import com.rs.game.player.actions.slayer.SlayerTask;
import com.rs.game.player.content.botanybay.BotanyBay;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.content.grandExchange.Offer;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.minigames.clanwars.FfaZone;
import com.rs.game.minigames.clanwars.WarControler;
import com.rs.game.minigames.duel.DuelArena;
import com.rs.game.minigames.duel.DuelRules;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.godwars.zaros.Nex;
import com.rs.game.npc.others.GraveStone;
import com.rs.game.npc.pet.Pet;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.actions.divination.DivinePlacing;
import com.rs.game.player.content.Ectophial;
import com.rs.game.player.ClueScrolls;
import com.rs.game.player.content.CooperativeSlayer;
import com.rs.game.player.content.Deaths;
import com.rs.game.player.content.FriendChatsManager;
import com.rs.game.player.content.LodeStone;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.content.Assassins;
import com.rs.game.player.content.BankPin;
import com.rs.game.player.content.Notes;
import com.rs.game.player.content.Notes.Note;
import com.rs.game.player.content.Highscores;
import com.rs.game.player.content.JoinInstance;
import com.rs.game.player.content.Pots;
import com.rs.game.player.content.ReportAbuse;
import com.rs.game.player.content.SandwichLady;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.Skillers;
import com.rs.game.player.content.Skillers.SkillerTasks;
import com.rs.game.player.content.SquealOfFortune;
import com.rs.game.player.content.Deaths.Tasks;
import com.rs.game.player.content.pet.PetManager;
import com.rs.game.player.controlers.Controler;
import com.rs.game.player.controlers.CorpBeastControler;
import com.rs.game.player.controlers.CrucibleControler;
import com.rs.game.player.controlers.DTControler;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.GodWars;
import com.rs.game.player.controlers.InstancedBossControler.Difficulty;
import com.rs.game.player.controlers.InstancedBossControler.Instance;
import com.rs.game.player.controlers.NomadsRequiem;
import com.rs.game.player.controlers.QueenBlackDragonController;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.controlers.ZGDControler;
import com.rs.game.player.controlers.castlewars.CastleWarsPlaying;
import com.rs.game.player.controlers.castlewars.CastleWarsWaiting;
import com.rs.game.player.controlers.fightpits.FightPitsArena;
import com.rs.game.player.controlers.pestcontrol.PestControlGame;
import com.rs.game.player.controlers.pestcontrol.PestControlLobby;
import com.rs.game.player.content.ShootingStar;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.Session;
import com.rs.net.decoders.WorldPacketsDecoder;
import com.rs.net.decoders.handlers.ButtonHandler;
import com.rs.net.encoders.WorldPacketsEncoder;
import com.rs.utils.Hiscores;
import com.rs.utils.IsaacKeyPair;
import com.rs.utils.KillStreakRank;
import com.rs.utils.Logger;
import com.rs.utils.MachineInformation;
import com.rs.utils.Misc;
import com.rs.utils.PkRank;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;
import com.rs.game.player.Toolbelt;
import com.rs.game.player.VarsManager;
import com.rs.game.player.ChatMessage;
import com.rs.game.player.QuickChatMessage;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.player.GrandExchangeManager;
import com.rs.game.minigames.WarriorsGuild;
import com.rs.game.player.content.construction.House;
import com.rs.game.player.content.PlayerLook;

public class Player extends Entity {
	//point shops
	public int playerpoints;
	public boolean pvpshopopen;
	public int getPlayerPoints() {
		return playerpoints;
		}
	public int setPlayerPoints(int i) {
		return playerpoints;
		}
	//Pking Server
	public boolean isPker;
	public int chosePker;
	public byte highestKillstreak;
	public byte killstreak;
	
	//joining
	public String joinName;
	
	public int ecoReset1;
	public int pendantTime;
	public int clueChance;
	public boolean finishedClue;
	
	//Orb
	public WorldTile orbLocation;
	public int orbCharges;
	
	public int moneyPouchTrade;
	public boolean addedFromPouch;
	
    // gravestone
    private int gStone;
    private GraveStone graveStone;
    
    private transient VarsManager varsManager;
	
	private PlayerDefinition definition;
	//private GrandExchange grandExchange = new GrandExchange(this);
	public boolean tutored = false;
	
	private String referral;
	private boolean profanityFilter;
	
	private ArrayList<Note> pnotes;
	
	public ArrayList<Note> getCurNotes() {
		return pnotes;
	}
	
	private boolean verboseShopDisplayMode;
	
	//Hangman
	public int HangManWrong = 0;
	
	//Penguin
	public boolean penguin;
	public int penguinpoints;
	
	//GRAND EXCHANGE
	private GrandExchangeManager geManager;
	//END GRAND EXCHANGE
	
	public int moneyFix;
	public boolean pyramidReward;
	
	/*
	 * Construction
	 */
	
	private House house;
	private JoinInstance joininstance;
	private transient RoomReference roomReference;
	
	public boolean inRing;
	public boolean hasHouse;
	private boolean hasBeenToHouse = false;
	private boolean buildMode;
	private boolean hasConfirmedRoomDeletion = false;
	private int houseX;
	private int houseY;
	private int[] boundChuncks;
	private List<WorldObject> conObjectsToBeLoaded;
	private List<RoomReference> rooms;
	private int place;
	private HouseLocation portalLocation;
	private ServantType butler;
	
	public int chair1;
	public int chairX1;
	public int chairY1;
	
	public int chair2;
	public int chairX2;
	public int chairY2;
	
	public int chair3;
	public int chairX3;
	public int chairY3;
	
	public int rug1;
	public int rugX1;
	public int rugY1;
	
	public int fireplace1;
	public int fireplaceX1;
	public int fireplaceY1;
	
	public int fireplace2;
	public int fireplaceX2;
	public int fireplaceY2;
	
	public int fireplace3;
	public int fireplaceX3;
	public int fireplaceY3;
	
	public int bookcase1;
	public int bookcaseX1;
	public int bookcaseY1;
	
	public int bookcase2;
	public int bookcaseX2;
	public int bookcaseY2;
	
	public int bookcase3;
	public int bookcaseX3;
	public int bookcaseY3;
	
	public int bookcase4;
	public int bookcaseX4;
	public int bookcaseY4;
	
	public int bookcase5;
	public int bookcaseX5;
	public int bookcaseY5;
	
	public int table1;
	public int tableX1;
	public int tableY1;
	
	public int small1plant1;
	public int small1plantX1;
	public int small1plantY1;
	
	public int small2plant1;
	public int small2plantX1;
	public int small2plantY1;
	
	public int big1plant1;
	public int big1plantX1;
	public int big1plantY1;
	
	public int big2plant1;
	public int big2plantX1;
	public int big2plantY1;
	
	public int bench1;
	public int benchX1;
	public int benchY1;
	
	public int bench2;
	public int benchX2;
	public int benchY2;
	
	public int bench3;
	public int benchX3;
	public int benchY3;
	
	public int bench4;
	public int benchX4;
	public int benchY4;
	
	public int bench5;
	public int benchX5;
	public int benchY5;

	public int bench6;
	public int benchX6;
	public int benchY6;

	public int bench7;
	public int benchX7;
	public int benchY7;

	public int bench8;
	public int benchX8;
	public int benchY8;
	
	private Toolbelt toolbelt;
	
	public int assassinPoints;
	//public boolean assassinpartner;
	public boolean used1;
	public boolean finalblow;
	public boolean used2;
	public boolean swiftness;
	public boolean used3;
	public boolean stealth;
	public boolean used4;
	
	
	//Ooglog Baths
	public boolean inBath;
	public boolean bandosBath;
	public boolean runBath;
	public boolean healthBath;
	
	//Dung Bags
	public int emeralds;
	public int sapphires;
	public int rubies;
	public int diamonds;
	public int coal;
	public int gembagspace;
	
	
	//Evil Tree
	public int treeDamage = 0;
	public boolean isChopping = false;
	public boolean isLighting = false;
	public boolean isRooting = false;
	
	public int onlinetime;
	
	// Lending
		public boolean isLendingItem = false;
		public int lendMessage;
		
	
	public int RG = 0;
	public int christmas = 0;
	public boolean snowrealm;
	public int VS = 0;
	public int DS = 0;
	public int EC = 0;
	public boolean bowl;
	public boolean bomb;
	public boolean pot;
	public boolean silk;
	public boolean patched;
	public boolean boughtship;
	public boolean rubberTube;
	public boolean key;
	public boolean fishfood;
	public boolean poison;
	public boolean spade;
	public int RM = 0;
	public int crystalcharges;
	
	public boolean isInPestControlLobby;
	public boolean isInPestControlGame;
	public int pestDamage;
	
	public boolean brim = false;
	
	// Imp Catcher Quest
		public boolean startedImpCatcher = false;
		public boolean inProgressImpCatcher = false;
		public boolean completedImpCatcher = false;
		
	//Juju
		private transient int jujuMining = 0;
		private transient int jujuFarming = 0;
		private transient int jujuFishing = 0;
		private transient int jujuWoodcutting = 0;
		private transient int jujuScentless = 0;
		private transient int jujuGod = 0;
		
		/**
		 * Farming
		 */
		
		//Falador
		private int faladorHerbPatch;
		private int faladorNorthAllotmentPatch;
		private int faladorSouthAllotmentPatch;
		private int faladorFlowerPatch;
		private boolean faladorHerbPatchRaked;
		private boolean faladorNorthAllotmentPatchRaked;
		private boolean faladorSouthAllotmentPatchRaked;
		private boolean faladorFlowerPatchRaked;
		
		//Catherby
		private int catherbyHerbPatch;
		private int catherbyNorthAllotmentPatch;
		private int catherbySouthAllotmentPatch;
		private int catherbyFlowerPatch;
		private boolean catherbyHerbPatchRaked;
		private boolean catherbyNorthAllotmentPatchRaked;
		private boolean catherbySouthAllotmentPatchRaked;
		private boolean catherbyFlowerPatchRaked;
		
		//Ardougne
		private int ardougneHerbPatch;
		private int ardougneNorthAllotmentPatch;
		private int ardougneSouthAllotmentPatch;
		private int ardougneFlowerPatch;
		private boolean ardougneHerbPatchRaked;
		private boolean ardougneNorthAllotmentPatchRaked;
		private boolean ardougneSouthAllotmentPatchRaked;
		private boolean ardougneFlowerPatchRaked;
		
		//Canifis
		private int canifisHerbPatch;
		private int canifisNorthAllotmentPatch;
		private int canifisSouthAllotmentPatch;
		private int canifisFlowerPatch;
		private boolean canifisHerbPatchRaked;
		private boolean canifisNorthAllotmentPatchRaked;
		private boolean canifisSouthAllotmentPatchRaked;
		private boolean canifisFlowerPatchRaked;
		
		//Lumbridge
		private int lummyTreePatch;
		private boolean lummyTreePatchRaked;
		
		//Varrock
		private int varrockTreePatch;
		private boolean varrockTreePatchRaked;
		
		//Falador
		private int faladorTreePatch;
		private boolean faladorTreePatchRaked;
		
		//Taverly
		private int taverlyTreePatch;
		private boolean taverlyTreePatchRaked;
		
		//Castle Wars
	    private boolean capturedCastleWarsFlag;
	    private int finishedCastleWars;

	
	//Lost City
	public int lostCity = 0;
	public boolean spokeToWarrior = false;
	public boolean spokeToShamus = false;
	public boolean spokeToMonk = false;
	public boolean recievedRunes = false;
	//GameMode settings
	public int gameMode = 0;
	public boolean choseGameMode;
	//Internships
	public boolean quickWork;
	public int iainAmount = 0;
	public int iainId = 0;
	public boolean iainTask;
	public int smithAmount = 0;
	public int smithId = 0;
	public boolean smithTask;
	public int priestAmount = 0;
	public int priestId = 0;
	public boolean priestTask;
	public int julianAmount = 0;
	public int julianId = 0;
	public boolean julianTask;
	
	private int[] clanCapeCustomized;
	private int[] clanCapeSymbols;
	
	public int spinTimer = 0;
    public static int boxWon = -1;
    public int isspining;
	
	//Mac Banning
	private InetAddress connected;
	private String macAddress;
	
	public String getMacAddress() {
		try {
			InetAddress address = connected;
			NetworkInterface net = NetworkInterface.getByInetAddress(address);
			
			byte[] mac = net.getHardwareAddress();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i > mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			macAddress = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			sm("There was an error grabbing players MAC address.");
		}
		return macAddress;
	}
	
	
	public static final int TELE_MOVE_TYPE = 127, WALK_MOVE_TYPE = 1,
			RUN_MOVE_TYPE = 2;

	private static final long serialVersionUID = 2011932556974180375L;
	public static ItemsContainer<Item> items = new ItemsContainer<Item>(13, true);
	
	public boolean isBuying;
	
	private boolean allowsProfanity;
	
	//Coop Slayer
	private CooperativeSlayer coOpSlayer;	

	public void getPartner() {
		sm("Your Slayer partner is: " + getSlayerPartner() + ".");
	}
	
	public boolean hasInvited, hasHost, hasGroup, hasOngoingInvite;
	
	private String slayerPartner = "";

	public String getSlayerPartner() {
		return slayerPartner;
	}

	public void setSlayerPartner(String partner) {
		slayerPartner = partner;
	}

	private String slayerHost = "";

	public String getSlayerHost() {
		return slayerHost;
	}

	public void setSlayerHost(String host) {
		slayerHost = host;
	}

	private String slayerInvite = "";

	public String getSlayerInvite() {
		return slayerInvite;
	}

	public void setSlayerInvite(String invite) {
		slayerInvite = invite;
	}

	public CooperativeSlayer getCooperativeSlayer() {
		return coOpSlayer;
	}
	
	//boss Killcount
	public int KCarmadyl = 0;
	public int KCbandos = 0;
	public int KCzammy = 0;
	public int KCsaradomin = 0;
	public int KCcorp = 0;
	public int KCsunfreet = 0;
	public int KCwild = 0; // wildy wyrm
	public int KCblink = 0;
	public int KCThunder = 0; // Yk'lagor the thunderous
	public int KCVorago = 0; // Vorago
	
	//CollabScape718 minigame
	public int Nstage1 = 0;
	public int Nstage2 = 0;
	public int Nstage3 = 0;
	
	//Comp Cape

	//God Wars
	public int armadyl = 0;
	public int bandos = 0;
	public int saradomin = 0;
	public int zamorak = 0;
	
	// Cooks Assistant Quest
	public boolean startedCooksAssistant = false;
	public boolean inProgressCooksAssistant = false;
	public boolean completedCooksAssistantQuest = false;
	public boolean hasGrainInHopper = false;
	
	//Gertrude's Cat Quest
	
	public boolean completedGertCat = false;
	public int gertCat = 0;
	
	//Drakan Charges
	public int drakanCharges;
	
	//Penance Horn
	public int horn;
	
	//AFK
	public long afkTimer = 0;
	public int advancedagilitylaps;
	public int advancedgnomelaps;
	
	private List<String> cachedChatMessages;
	private long lastChatMessageCache;
	
	//Stones
	private boolean[] activatedLodestones;
	private LodeStone lodeStone;
	
	//PVP
	public int Killstreak = 0;
	private static Item pvpRandomCommon[] = {new Item(554, 50), new Item(555, 50), new Item(556, 50), new Item(557, 50), new Item(558, 50), new Item(559, 50), new Item(560, 50), new Item(561, 50), new Item(562, 50), new Item(563, 50), new Item(564, 50), new Item(565, 50), new Item(566, 50), new Item(12158, 50), new Item(12159, 50), new Item(12160, 50), new Item(12161, 50), new Item(12162, 50), new Item(12163, 50), new Item(12164, 50), new Item(12165, 50), new Item(12166, 50), new Item(12167, 50), new Item(12168, 50), new Item(15272, 5), new Item(2434, 3)};
	private static Item pvpRandomUncommon[] = {new Item(11694, 1), new Item(11696, 1), new Item(11698, 1), new Item(11700, 1)};
	private static Item pvpRandomRare[] = {new Item(20135, 1), new Item(20139, 1), new Item(20143, 1), new Item(24977, 1), new Item(18349, 1), new Item(18351, 1), new Item(18353, 1), new Item(24352, 1), new Item(14484, 1), new Item(18786, 1), new Item(24992, 1), new Item(24995, 1), new Item(24998, 1), new Item(25001, 1), new Item(25004, 1)};
	private static Item pvpRandomExtremelyRare[] = {new Item(16955, 1), new Item(15773, 1), new Item(16425, 1), new Item(16909, 1), new Item(10348, 1), new Item(18357, 1), new Item(21777, 1), new Item(25037, 1)};
	public static Item pvpRandom() {
		int chance = Misc.random(300);
		if (chance <= 4)
			return pvpRandomExtremelyRare[Misc.random(pvpRandomExtremelyRare.length)];
		else if (chance >= 5 && chance <= 25)
			return pvpRandomRare[Misc.random(pvpRandomRare.length)];
		else if (chance >= 26 && chance <= 150)
			return pvpRandomUncommon[Misc.random(pvpRandomUncommon.length)];
		else
			return pvpRandomCommon[Misc.random(pvpRandomCommon.length)];
	}
	
	//Troll
	 public boolean trollReward;
		private int trollsToKill;
		private int trollsKilled;
		
		/**
		 * Staff System
		 */
		
		public int staffpin = 1337;
		
		public boolean hasStaffPin;
		
		//Random Events
		   public int tX;
		    public int tY;
		    public int tH;
		    public boolean saved;

		    public void saveLoc(int x, int y, int h) {
		        this.tX = x;
		        this.tY = y;
		        this.tH = h;
		    }
		    public int gotreward = 0;
		    
		    //Halloween
			public int cake = 0;
			public int dust1 = 0;
			public int dust2 = 0;
			public int dust3 = 0;
			public int drink = 0;
			public int doneevent = 0;
			public int talked = 0;
			/**
			 * Smoking Kills Quest
			 *
			 */
			public int sKQuest = 0;
			public boolean completedSmokingKillsQuest = false;
			
			/**
			 * Dwarf Cannon quest
			 */
			public int fixedRailings = 0;
			public boolean fixedRailing1 = false;
			public boolean fixedRailing2 = false;
			public boolean fixedRailing3 = false;
			public boolean fixedRailing4 = false;
			public boolean fixedRailing5 = false;
			public boolean fixedRailing6 = false;
			
			public boolean completedRailingTask = false;
			public boolean spokeToNu = false;
			public boolean completedDwarfCannonQuest = false;
			
			/**
			 * Quest Points
			 */
			public int questPoints = 0;

			public boolean isAnthonyRank = false;
			
			private transient boolean listening;
			
			


	//Comp cape
	public static int isMaxed1 = 1;
	public static int isCompletionist1 = 1;

	//Warriors Guild
	private boolean inClops;
	private int wGuildTokens;
	private double[] warriorPoints;

	//sheathing
	public boolean canSheath, canSheath2;

	//Sword of Wiseman Quest
	public int SOWQUEST;
	
	public void resetPlayer() {
		for (int skill = 0; skill < 25; skill++)
		getSkills().setXp(skill, 1);
		//getSkills().set(skill, 1);
		getSkills().init();
	}
	
	public void resetPlayer2() {
		for (int skill = 0; skill < 25; skill++)
		//getSkills().setXp(skill, 1);
		getSkills().set(skill, 1);
		getSkills().setXp(3, 1154);
		getSkills().set(3, 10);
		getSkills().init();
	}
	
	public boolean Prestige1;
	
	public int prestigeTokens = 0; //prestige points.
	
	public boolean isPrestige1() {
		return Prestige1;
	}

	public void setPrestige1() {
		if(!Prestige1) {
			Prestige1 = true;
		}
	}
	public void rspsdata(Player player, String username){
		try{
			username = username.replaceAll(" ","_");
		    String secret = "4bbdcc0e821637155ac4217bdab70d2e"; //YOUR SECRET KEY!
			String email = "dylo957@gmail.com"; //This is the one you use to login into RSPS-PAY
			URL url = new URL("http://rsps-pay.com/includes/listener.php?username="+username+"&secret="+secret+"&email="+email);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String results = reader.readLine();
				if(results.toLowerCase().contains("!error:")){
					//Logger.log(this, "[RSPS-PAY]"+results);
				}else{
					String[] ary = results.split(",");
						 for(int i = 0; i < ary.length; i++){
							switch(ary[i]){
						    case "0":
						        //donation was not found tell the user that!
						    	player.sendMessage("We cannot find your donation please make sure that it went though.");
						    break;
						    
						    // party hats 
							case "8737": //product ids can be found on the webstore page
								//add items for the first product
								//player.getInventory().addCoins(1000000);
								player.getBank().addItem(1038, 1, true);
								setDonator(true);
								player.sendMessage("Thank You SO much for donating a red partyhat has been added to your bank!");
								World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
							break;
							case "8738": //product ids can be found on the webstore page
								//add items for the second product here!
								player.getBank().addItem(1040, 1, true);
								setDonator(true);
								player.sendMessage("Thank You SO much for donating a yellow partyhat has been added to your bank!");
								World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
							break;
							case "8739":
								setDonator(true);
								player.getBank().addItem(1044, 1, true);
								player.sendMessage("Thank You SO much for donating a green partyhat has been added to your bank!");
								World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
								break;
							case "8740":
								player.getBank().addItem(1042, 1, true);
								setDonator(true);
								player.sendMessage("Thank You SO much for donating a blue partyhat has been added to your bank!");
								World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
								break;
							case "8741":
								player.getBank().addItem(1046, 1, true);
								setDonator(true);
								player.sendMessage("Thank You SO much for donating a purple partyhat has been added to your bank!");
								World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
								break;
								case "8742":
									setDonator(true);
									player.getBank().addItem(1048, 1, true);
									player.sendMessage("Thank You SO much for donating a white partyhat has been added to your bank!");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8743":
									Random r = new Random();
									int RandomItems[] = {1048, 1046, 1044, 1042, 1040, 4038};
									setDonator(true);
									player.getBank().addItem(RandomItems[r.nextInt(RandomItems.length)], 1 , true);
									player.sendMessage("Thank You SO much for donating a random partyhat has been added to your bank!");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
									
								// hween masks 	
								case "8744":
									setDonator(true);
								player.getBank().addItem(1057,1, true);
								player.sendMessage("Thank You SO much for donating a Red H'ween Mask has been added to your bank");
								World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
								break;
								case "8745":
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(1053,1, true);
									player.sendMessage("Thank You SO much for donating a Green H'ween Mask has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8746":
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(1055,1, true);
									player.sendMessage("Thank You SO much for donating a Blue H'ween Mask has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8747":
									Random f = new Random();
									int RandomItemss[] = {1055, 1053, 1057};
									player.getBank().addItem(RandomItemss[f.nextInt(RandomItemss.length)], 1 , true);
									player.donator = true;
									setDonator(true);
									player.sendMessage("Thank You SO much for donating a random H'ween Mask has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8748":
									//santa hat
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(1050,1, true);
									player.sendMessage("Thank You SO much for donating a Blue H'ween Mask has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8749":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(11694,1, true);
									player.sendMessage("Thank You SO much for donating a Armadyl godsword has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8750":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(11698,1, true);
									player.sendMessage("Thank You SO much for donating a Saradomin Godsword has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8751":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(11700,1, true);
									player.sendMessage("Thank You SO much for donating a Zamorak Godsword has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8752":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(11696,1, true);
									player.sendMessage("Thank You SO much for donating a Bandos Godsword has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8753":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(14484,1, true);
									player.getBank().addItem(14486,1, true);
									player.sendMessage("Thank You SO much for donating a pair of Dragon Claws has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8754":
									// god swords
									player.donator = true;
									player.getBank().addItem(26595,1, true);
									player.sendMessage("Thank You SO much for donating a Drygore Mace has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8755":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(26579,1, true);
									player.sendMessage("Thank You SO much for donating a Drygore Rapier has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8756":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(26587,1, true);
									player.sendMessage("Thank You SO much for donating a Drygore Longsword has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8757":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(26595,1, true);
									player.sendMessage("Thank You SO much for donating a Drygore Mace has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8758":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(20135,1, true);
									player.getBank().addItem(20139,1, true);
									player.getBank().addItem(20143,1, true);
									player.getBank().addItem(24977,1, true);
									player.getBank().addItem(24983,1, true);
									player.sendMessage("Thank You SO much for donating a Torva Set has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8759":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(20147,1, true);
									player.getBank().addItem(20151,1, true);
									player.getBank().addItem(20155,1, true);
									player.getBank().addItem(24974,1, true);
									player.getBank().addItem(24989,1, true);
									player.sendMessage("Thank You SO much for donating a Pernix Set has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8760":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(20159,1, true);
									player.getBank().addItem(20163,1, true);
									player.getBank().addItem(20167,1, true);
									player.getBank().addItem(24980,1, true);
									player.getBank().addItem(24986,1, true);
									player.sendMessage("Thank You SO much for donating a Virtus Set has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8761":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(11726,1, true);
									player.getBank().addItem(11724,1, true);
									player.getBank().addItem(11728,1, true);
									player.sendMessage("Thank You SO much for donating a Bandos Set has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8762":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(11718,1, true);
									player.getBank().addItem(11720,1, true);
									player.getBank().addItem(11722,1, true);
									player.getBank().addItem(25010,1, true);
									player.getBank().addItem(25016,1, true);
									player.sendMessage("Thank You SO much for donating a Armadyl Set has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8763":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(24992,1, true);
									player.getBank().addItem(24995,1, true);
									player.getBank().addItem(24998,1, true);
									player.getBank().addItem(25001,1, true);
									player.getBank().addItem(25004,1, true);
									player.getBank().addItem(25007,1, true);
									player.sendMessage("Thank You SO much for donating a Subjugation Set has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8764":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(13744,1, true);
									player.sendMessage("Thank You SO much for donating a Spectral Spirit Shield has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8765":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(13738,1, true);
									player.sendMessage("Thank You SO much for donating a Arcane Spirit Shield has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8766":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(13742,1, true);
									player.sendMessage("Thank You SO much for donating a Elysian spirit shield has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8767":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(13740,1, true);
									player.sendMessage("Thank You SO much for donating a Divine spirit shield has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8768":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(19785,1, true);
									player.getBank().addItem(19786,1, true);
									player.sendMessage("Thank You SO much for donating a Full Elite Void Set has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "8718":
									
									player.donator = true;
									setDonator(true);
								player.getBank().addItem(11696,1, true);
								player.getBank().addItem(11726,1, true);
								player.getBank().addItem(11724,1, true);
								player.getBank().addItem(11728,1, true);
								player.sendMessage("Thank You SO much for donating a bandos set has been added to your bank");
								World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
								break;
								case "9029":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(21787,1, true);
									player.sendMessage("Thank You SO much for donating a Steadfast Boots has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "9030":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(21793,1, true);
									player.sendMessage("Thank You SO much for donating a Ragefire Boots has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "9031":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(21790,1, true);
									player.sendMessage("Thank You SO much for donating a Glaiven Boots has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "9032":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(6570,1, true);
									player.completedFightCaves = true;
									player.sendMessage("Thank You SO much for donating a Fire Cape has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "9033":
									// god swords
									player.donator = true;
									setDonator(true);
									player.getBank().addItem(23639,1, true);
									player.completedFightKiln = true;
									player.sendMessage("Thank You SO much for donating a TokHaar-Kal-Ket has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "9034":
									// god swords
									Random g = new Random();
									int RandomItemsss[] = {22362, 22363, 22364, 22365};
									player.getBank().addItem(RandomItemsss[g.nextInt(RandomItemsss.length)], 1 , true);
									player.donator = true;
									setDonator(true);
									player.sendMessage("Thank You SO much for donating a Swift Gloves has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "9035":
									// god swords
									Random h = new Random();
									int RandomItemssss[] = {22366, 22367, 22368, 22369};
									player.getBank().addItem(RandomItemssss[h.nextInt(RandomItemssss.length)], 1 , true);
									player.donator = true;
									setDonator(true);
									player.sendMessage("Thank You SO much for donating a Swift Gloves has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "9036":
									// god swords
									Random j = new Random();
									int RandomItemsssss[] = {22358, 22359, 22360, 22361};
									player.getBank().addItem(RandomItemsssss[j.nextInt(RandomItemsssss.length)], 1 , true);
									player.donator = true;
									setDonator(true);
									player.sendMessage("Thank You SO much for donating a Goliath gloves has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "9037":
									player.getBank().addItem(1037, 1 , true);
									player.donator = true;
									setDonator(true);
									player.sendMessage("Thank You SO much for donating a Bunny Ears has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "9038":
									player.getBank().addItem(22321, 1 , true);
									player.donator = true;
									setDonator(true);
									player.sendMessage("Thank You SO much for donating a Golden Scythe has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "9041":
									player.getBank().addItem(19335, 1 , true);
									player.donator = true;
									setDonator(true);
									player.sendMessage("Thank You SO much for donating a Fury (or) has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "9042":
									player.getBank().addItem(25654, 1 , true);
									player.donator = true;
									setDonator(true);
									player.sendMessage("Thank You SO much for donating a Virtus Wand has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
								case "9043":
									player.getBank().addItem(20171, 1 , true);
									player.donator = true;
									setDonator(true);
									player.sendMessage("Thank You SO much for donating a Zaryte Bow has been added to your bank");
									World.sendWorldMessage("<img=7> <col=FF6600>News: "+player.getDisplayName()+" has donated for "+Settings.SERVER_NAME+"!", false);
									break;
							}
						}
				}
			}catch(IOException e){}
		}
	public void setCompletedPrestigeOne() {
		if(getEquipment().wearingArmour()) {
			getPackets().sendGameMessage("<col=ff0000>You must remove your amour before you can prestige.");
		if (prestigeTokens == 0) {
		prestigeTokens++;
		if (gameMode == 3) {
			getInventory().addItem(29968, 25);
		} else if (gameMode == 2) {
			getInventory().addItem(29968, 10);
		} else if (gameMode == 1) {
			getInventory().addItem(29968, 3);
		} else if (gameMode == 0) {
			getInventory().addItem(29968, 1);	
		}
		resetPlayer();
		resetPlayer2();
		resetHerbXp();
		Prestige1 = false;
	    setNextAnimation(new Animation(1914));
		setNextGraphics(new Graphics(1762));
		getPackets().sendGameMessage("You feel a force reach into your soul, You gain One Prestige Token.");
		World.sendWorldMessage("<img=7><col=ff0000>News: "+getDisplayName()+" has just prestiged! he has now prestiged "+prestigeTokens+" times.", false);
		if (prestigeTokens == 5) {
			getPackets().sendGameMessage("<col=ff0000>You have reached the last prestige, you can no longer prestige.");
		}
		}
	}
	}
	
	public int getPrestigeLevel() {
		return prestigeTokens;
	}
	
	public void prestigeShops() {
		if (prestigeTokens == 0) {
			getPackets().sendGameMessage("You need to have prestiged to gain access to this shop.");
		} else if (prestigeTokens == 1) {
			ShopsHandler.openShop(this, 69);
		}
	}
	
	public void nothing() {
		getPackets().sendGameMessage("You have completed all the prestiges.");
	}
	
	
	public void setCompletedPrestige2() {
		if (prestigeTokens >= 1) {
			resetCbXp();
			resetCbSkills();
			resetSummon();
			resetSummonXp();
			prestigeTokens++;
			getInventory().addItem(29968, 1);
			Prestige1 = false;
		    setNextAnimation(new Animation(1914));
			setNextGraphics(new Graphics(1762));
			getPackets().sendGameMessage("You feel a force reach into your soul, You gain One Prestige Token.");
			World.sendWorldMessage("<img=7><col=ff0000>News: "+getDisplayName()+" has just prestiged! he has now prestiged "+prestigeTokens+" times.", false);
		}
	}
	
	public void resetCbXp() {
		for (int skill = 0; skill < 7; skill++)
		getSkills().setXp(skill, 1);
		//getSkills().set(skill, 1);
		getSkills().init();
	}
	
	public void resetHerbXp() {
		getSkills().set(15, 3);
		getSkills().setXp(15, 174);
	}
	
	public void resetSummon() {
		getSkills().set(23, 1);
		getSkills().init();
	}
	public void resetSummonXp() {
		getSkills().setXp(23, 1);
		getSkills().init();
	}
	
	public void resetCbSkills() {
		for (int skill = 0; skill < 7; skill++)
		getSkills().set(skill, 1);
		getSkills().setXp(3, 1154);
		getSkills().set(3, 10);
		getSkills().init();
	}
	
	public int assassin;
	
	//Start Comp Cape
	
	public int penguins;
	public int sinkholes;
	public int totalTreeDamage;
	public int barrowsLoot;
	public int domCount;
	public int castleWins;
	public int trollWins;
	public int summer;
	public int implingCount;
	public int pestWins;
	public int voteCount;
	public int spinsCount;
	public int houseMoney;
	public boolean killedQueenBlackDragon2;
	public int heroSteals;
	public int cutDiamonds;
	public int kuradalTasks;
	public int grenwalls;
	public int cannonBall;
	public int runiteOre;
	public int rockTails;
	public int cookedFish;
	public int burntLogs;
	public int choppedIvy;
	public int harvestedTrees;
	public int infusedPouches;
	public int completedDungeons;
	public int crystalChest;
	public int clueScrolls;
	
	public boolean hasRequirements() {
		if (completedFightCaves && completedFightKiln && domCount >= 300
				&& getSkills().getTotalLevel() >= 2496 && penguins >= 250
				&& sinkholes >= 20 && totalTreeDamage >= 50000 && barrowsLoot >= 200
				&& castleWins >= 15 && trollWins >= 20 && summer >= 100
				&& rfd5 && prestigeTokens >= 10 && questPoints > 17
				&& implingCount >= 500 && pestWins >= 50 && completedPestInvasion
				&& voteCount >= 50 && spinsCount >= 125 && houseMoney >= 50000000
				&& killedQueenBlackDragon2 && advancedagilitylaps >= 375 && heroSteals >= 150
				&& cutDiamonds >= 1000 && kuradalTasks >= 80 && grenwalls >= 375
				&& barCrawlCompleted && cannonBall >= 800 && runiteOre >= 140
				&& rockTails >= 400 && cookedFish >= 1000 && burntLogs >= 2500
				&& choppedIvy >= 1250 && harvestedTrees >= 60 && infusedPouches >= 500
				&& completedDungeons >= 75 && crystalChest >= 150 && clueScrolls >= 250)
			return true;
		else
			return false;
	}
	
	public boolean hasCompletedFightCaves() {
		return completedFightCaves;
	}
	
	public boolean hasCompletedPestInvasion() {
		return completedPestInvasion;
	}
	
	public boolean hasCompletedFightKiln() {
		return completedFightKiln;
	}
	
	//End Comp Cape
	
	public void prestige() {
		if (getSkills().getLevel(Skills.ATTACK) >= 99 
				&& getSkills().getLevel(Skills.STRENGTH) >= 99 
				&& getSkills().getLevel(Skills.DEFENCE) >= 99 
				&& getSkills().getLevel(Skills.RANGE) >= 99 
				&& getSkills().getLevel(Skills.MAGIC) >= 99 
				&& getSkills().getLevel(Skills.PRAYER) >= 99
				&& getSkills().getLevel(Skills.HITPOINTS) >= 99
				&& getSkills().getLevel(Skills.COOKING) >= 99 
				&& getSkills().getLevel(Skills.WOODCUTTING) >= 99 
				&& getSkills().getLevel(Skills.FLETCHING) >= 99 
				&& getSkills().getLevel(Skills.FISHING) >= 99 
				&& getSkills().getLevel(Skills.FIREMAKING) >= 99 
				&& getSkills().getLevel(Skills.CRAFTING) >= 99 
				&& getSkills().getLevel(Skills.SMITHING) >= 99 
				&& getSkills().getLevel(Skills.MINING) >= 99
				&& getSkills().getLevel(Skills.HERBLORE) >= 99 
				&& getSkills().getLevel(Skills.AGILITY) >= 99 
				&& getSkills().getLevel(Skills.THIEVING) >= 99 
				&& getSkills().getLevel(Skills.SLAYER) >= 99
				&& getSkills().getLevel(Skills.FARMING) >= 99 
				&& getSkills().getLevel(Skills.HUNTER) >= 99 
				&& getSkills().getLevel(Skills.RUNECRAFTING) >= 99
				&& getSkills().getLevel(Skills.CONSTRUCTION) >= 99 
				&& getSkills().getLevel(Skills.SUMMONING) >= 99 
				&& getSkills().getLevel(Skills.DUNGEONEERING) >= 99) {
			setPrestige1();
		}
	}

	//money pouch
	public int money1 = 0;
	
	public int noob = 0;
	
	/*
	 * Iron man
	 */
	public boolean Ironman;
	
	public void setIronman(boolean Ironman) {
		this.Ironman = Ironman;
	}

	public boolean isIronman() {
		return Ironman;
	}
	
	/**
	 * 
	 * Dungeoneering.
	 * 
	 */

	private int DungTokens;
	
	public void setDungTokens(int DungTokens) {
		this.DungTokens = DungTokens;
	}

	public int getDungTokens() {
		return DungTokens;
	}
	
	//Player Owned Shops
	private CustomisedShop customisedShop;
	
	public CustomisedShop getCustomisedShop() {
		return customisedShop;
	}

	public void setCustomisedShop(CustomisedShop customisedShop) {
		this.customisedShop = customisedShop;
	}
	
	private SlayerManager slayerManager;
	

	private Assassins assassins;
	private Deaths deaths;
	private Skillers skillers;
	//box search
	public int searchBox = 0;//
	
	public int getSearchBox() {
		return searchBox;
	}
	public void addSearchBox() {
		searchBox+=1;
	}
	public boolean spinningWheel = false;
	public boolean monsterPageOne = true;
	public boolean monsterPageTwo = false;
	
	public boolean strongHoldOne = false;
	public boolean strongHoldTwo = false;
	public boolean strongHoldThree = false;
	public boolean strongHoldFour = false;
	
	public boolean strongHoldChestOne = false;
	public boolean strongHoldChestTwo = false;
	public boolean strongHoldChestThree = false;
	public boolean strongHoldChestFour = false;
	
	public boolean cockRoachShortcut = false;
	public boolean cockRoachLever = false;
	public boolean cockRoachChest = false;
	
	
	public boolean hasAgileSet(Player player) {
		if (player.getEquipment().getChestId() == 14936
				&& player.getEquipment().getLegsId() == 14936)
			return true;
		return false;
	}

	
	
/**
 *Barbarian Training! 
 */
	//barcrawl

	
	//each bar for barcrawl
	public int BlueMoonInn = 0;
	public int BlurberrysBar = 0;
	public int DeadMansChest = 0;
	public int DragonInn = 0;
	public int FlyingHorseInn = 0;
	public int ForestersArms = 0;
	public int JollyBoarInn = 0;
	public int KaramjaSpiritsBar = 0;
	public int RisingSun = 0;
	public int RustyAnchor = 0;
	
	public int barCrawl = 0;
	public int killedByAdam = 0;
	
	public boolean barCrawlCompleted = false;
	
	public void finishBarCrawl() {
		if (BlueMoonInn == 1 
				&& BlurberrysBar == 1
				&& DeadMansChest == 1
				&& DragonInn == 1
				&& FlyingHorseInn == 1
				&& ForestersArms == 1
				&& JollyBoarInn == 1
				&& KaramjaSpiritsBar == 1 
				&& RisingSun == 1 
				&& RustyAnchor == 1) {
			
			barCrawlCompleted = true;
		} else 
			barCrawlCompleted = false;
	}
	
	
	
	//Clans
	private transient ClansManager clanManager, guestClanManager;
	private int clanChatSetup;
	private String clanName;// , guestClanChat;
	private int guestChatSetup;
	private boolean connectedClanChannel;
	public boolean ServerTutorial = false;
	
	
	
	//trivia shit
	public int triviaPointss = 0;
	public int[] triviaPoints;
	public int TriviaPoints;
	public boolean hasAnswered;

	public void addTriviaPoints(int i) {
		triviaPoints[i]++;
	}
	
	public int getTriviaPoints() {
		return TriviaPoints;
	}

	public void setTriviaPoints(int triviaPoints) {
		this.TriviaPoints = triviaPoints;
	}

	//Rfd
	public boolean
	 rfd1
    ,rfd2
	,rfd3
	,rfd4
	,rfd5
	= false;

    //starter
    public int starter = 0;
    public boolean starting = false;
	public void refreshMoneyPouch() {
		//getPackets().sendConfig(1438, (money >> 16) | (money >> 8) & money);
		getPackets().sendRunScript(5560, money);
	}
	public int starterstage = 0;

    //Achievement System
    public boolean ToggleSystem = true;
    public int MiningAchievement;
    public int IvyAchievement;
    public int SiphonAchievement;
    public int BurnAchievement;

    //Teleports
    public boolean Ass;
	public boolean Gnome;
	public boolean Demon;
	public boolean Pony;
	public boolean SuperJump;

    //News
    public boolean setnews = true;

    public boolean deathShop = false;
    public int DeathPoints = 0;
    public int damount = 0;
    public Tasks deathTask = null;
    public SkillerTasks dailyTask = null;
    public boolean dhasTask = false;
	//slayer
	public boolean hasTask = false;
        public int slayerTaskAmount = 0;
	//public int slayerPoints;

    //Player Informs
    public boolean informwelcome;
    public boolean informmessages;
    public boolean inform99s = true;

    //ClueScrolls
    public int cluenoreward;
    public int clueLevel;

    //LatestUpdate
    public boolean completed = true;

    //Tutorial
    public int nextstage = 0;
    

    //Squeal
    public int Rewards;
    private SquealOfFortune squealOfFortune;

    //RuneSpan Points
    public int RuneSpanPoints;

    //Animation Settings
  	public boolean SamuraiCooking;
  	public boolean ChillBlastMining;
  	public boolean KarateFletching;

    //slayer masters
 	private boolean talkedWithKuradal;
 	private boolean talkedWithSpria;
 	private boolean talkedWithMazchna;

    //Completionist Cape
    public int isCompletionist = 1;
    public int isMaxed = 1;

	// transient stuff
	@SuppressWarnings("unused")
	private long lastLoggedIn;
	public double moneyInPouch;
	private MoneyPouch moneyPouch;
	private static final int lastlogged = 0;
	protected static final Player Player = null;
	public int DeathTask = 0;
	private transient String username;
	private transient DwarfCannon DwarfCannon;
	private transient int cannonBalls;
	private transient Session session;
	private transient Ectophial ectophial;
	private transient ClueScrolls ClueScrolls;
	private transient SpinsManager spinsManager;
	private transient LoyaltyManager loyaltyManager;
	private transient boolean clientLoadedMapRegion;
	private transient int displayMode;
	private transient int screenWidth;
	private transient int screenHeight;
	private transient InterfaceManager interfaceManager;
	private transient ReportAbuse reportAbuse;
	private transient DialogueManager dialogueManager;
	private transient HintIconsManager hintIconsManager;
	private transient ActionManager actionManager;
	private transient CutscenesManager cutscenesManager;
	private transient PriceCheckManager priceCheckManager;
	private transient CoordsEvent coordsEvent;
	private transient FriendChatsManager currentFriendChat;
	private transient Trade trade;
	private transient DuelRules lastDuelRules;
	private transient IsaacKeyPair isaacKeyPair;
	private transient Pet pet;
	public transient Player divines;
	public int divine;
	private transient ShootingStar ShootingStar;

	// used for packets logic
	private transient ConcurrentLinkedQueue<LogicPacket> logicPackets;

	// used for update
	private transient LocalPlayerUpdate localPlayerUpdate;
	private transient LocalNPCUpdate localNPCUpdate;

	private int temporaryMovementType;
	private boolean updateMovementType;

	// player stages
	private transient boolean started;
	private transient boolean running;
	private transient long stopDelay;
	private transient long packetsDecoderPing;
	private transient boolean resting;
	private transient boolean canPvp;
	private transient boolean cantTrade;
	private transient long lockDelay; // used for doors and stuff like that
	private transient long foodDelay;
	private transient long potDelay;
	private transient long boneDelay;
	private transient long ashDelay;
	private transient Runnable closeInterfacesEvent;
	private transient long lastPublicMessage;
	private transient long polDelay;
	private transient List<Integer> switchItemCache;
	private transient boolean disableEquip;
	private transient MachineInformation machineInformation;
	private transient boolean spawnsMode;
	private transient boolean castedVeng;
	private transient boolean invulnerable;
	private transient double hpBoostMultiplier;
	private transient boolean largeSceneView;

	public void refreshSqueal() {
		getPackets().sendConfigByFile(11026, getSpins());
	}

	// kelly stuff
	private String password;
	private int rights;
	public PlayerData playerData;
	private String displayName;
	private String lastIP;
	@SuppressWarnings("unused")
	private long creationDate;
	private Appearence appearence;
	private Inventory inventory;
	private Equipment equipment;
	private Skills skills;
	private CombatDefinitions combatDefinitions;
	private Prayer prayer;
	private Bank bank;
	private ControlerManager controlerManager;
	private MusicsManager musicsManager;
	private EmotesManager emotesManager;
	private FriendsIgnores friendsIgnores;
	private FairyRing fairyRing;
	private DominionTower dominionTower;
	private Farmings farmings;
	private Familiar familiar;
	private AuraManager auraManager;
	private QuestManager questManager;
	private PetManager petManager;
	private byte runEnergy;
	private boolean allowChatEffects;
	private boolean mouseButtons;
	private int privateChatSetup;
	private int friendChatSetup;
	private int skullDelay;
	private int skullId;
	private boolean forceNextMapLoadRefresh;
	private long poisonImmune;
	private long fireImmune;
	private long superFireImmune;
	private boolean killedQueenBlackDragon;
	private int runeSpanPoints;

	private int lastBonfire;
	private int[] pouches;
	private long displayTime;
	private long muted;
	private long jailed;
	private long banned;
	private boolean inDung;
	private boolean permBanned;
	private boolean filterGame;
	private boolean xpLocked;
	private boolean yellOff;
	//game bar status
	private int publicStatus;
	private int clanStatus;
	private int tradeStatus;
	private int assistStatus;

	private boolean donator;
	private boolean extremeDonator;
	private boolean legendaryDonator;
	private boolean supremeDonator;
	private boolean divineDonator;
	private boolean angelicDonator;
	private long donatorTill;
	private long extremeDonatorTill;
	private long legendaryDonatorTill;
	private long supremeDonatorTill;
	private long divineDonatorTill;
	private long angelicDonatorTill;
	
	public boolean ZREST;

	//Recovery ques. & ans.
	private String recovQuestion;
	private String recovAnswer;

	private String lastMsg;

	//Used for storing recent ips and password
	private ArrayList<String> passwordList = new ArrayList<String>();
	private ArrayList<String> ipList = new ArrayList<String>();


	// honor
	private int killCount, deathCount;
	private ChargesManager charges;
	// barrows
	private boolean[] killedBarrowBrothers;
	private int hiddenBrother;
	private int barrowsKillCount;
	public int pestPoints;

	//Squeal
	public int spins;
	@SuppressWarnings("unused")
	private int freeSpins = 0;
	
	//Bank Pins
	private BankPin pin;
	public boolean bypass;
	private int pinpinpin;
	public boolean setPin = false;
	public boolean openPin = false;
	public boolean startpin = false;
	private int[] bankpins = new int[] { 0, 0, 0, 0 };
	private int[] confirmpin = new int[] { 0, 0, 0, 0 };
	private int[] openBankPin = new int[] { 0, 0, 0, 0 };
	private int[] changeBankPin = new int[] { 0, 0, 0, 0 };

	//Loyalty
	private int Loyaltypoints;

	//Donator
	private int Donatorpoints;

	//Forum
	public int Forumpoints;

	//Reward points
	public int Rewardpoints;


	// skill capes customizing
	private int[] maxedCapeCustomized;
	private int[] completionistCapeCustomized;

	//completionistcape reqs
	private boolean completedFightCaves;
	private boolean completedPestInvasion;
	 private boolean completedFightKiln;
	 private boolean wonFightPits;

	//crucible
	private boolean talkedWithMarv;
	private int crucibleHighScore;

	private int overloadDelay;
	private int prayerRenewalDelay;

	private String currentFriendChatOwner;
	private int summoningLeftClickOption;
	private List<String> ownedObjectsManagerKeys;

	private boolean lootshareEnabled;
	
	

	//Slayer
	 /**
		 * The slayer task system instance
		 */
		public SlayerTask slayerTask;

		private SlayerTask task;

		/**
		 * @return the task
		 */

		public SlayerTask getTask() {
			return task;
		}

		/**
		 * @param task
		 *            the task to set
		 */
		public void setTask(SlayerTask task) {
			this.task = task;
		}

	//Lootshare
	public boolean lootshareEnabled() {
		return this.lootshareEnabled;
	}

	public void toggleLootShare() {
		this.lootshareEnabled = !this.lootshareEnabled;
		getPackets().sendConfig(1083, this.lootshareEnabled ? 1 : 0);
		sendMessage(String.format("<col=115b0d>Lootshare is now %sactive!</col>", this.lootshareEnabled ? "" : "in"));
	}
	


	public int lividpoints;
	public boolean lividcraft;
	public boolean lividfarming;
	public boolean lividmagic;
	public boolean lividfarm;
	
	public int unclaimedEctoTokens;
	public boolean bonesGrinded;
	public int boneType;
	public boolean usingLog;
	public boolean usingDugout;
	public boolean usingStableDugout;
	public boolean usingWaka;


	//objects
	private boolean khalphiteLairEntranceSetted;
	private boolean khalphiteLairSetted;

	//supportteam
	private boolean isSupporter;

	//voting
	private int votes;
	private boolean oldItemsLook;

	private String yellColor = "ff0000";

	private long voted;

	private boolean isGraphicDesigner;

	private boolean isForumModerator;
	
	public Player(Session session, PlayerDefinition definition) {
		super(Settings.START_PLAYER_LOCATION);
		this.session = session;
		this.definition = definition;
		runEnergy = 100;
		allowChatEffects = true;
		mouseButtons = true;
		cachedChatMessages = new ArrayList<>();
		pouches = new int[4];
		resetBarrows();
	}
	
	public PlayerDefinition definition() {
		return this.definition;
	}

	// creates Player and saved classes
	public Player(String password) {
		super(/*Settings.HOSTED ? */Settings.START_PLAYER_LOCATION/* : new WorldTile(3095, 3107, 0)*/);
		setHitpoints(Settings.START_PLAYER_HITPOINTS);
		this.password = password;
		if (slayerTask == null)
			slayerTask = new SlayerTask();
		warriorPoints = new double[6];
		appearence = new Appearence();
		geManager = new GrandExchangeManager();
		slayerManager = new SlayerManager();
		house = new House();
		pin = new BankPin();
		inventory = new Inventory();
		moneyPouch = new MoneyPouch();
		equipment = new Equipment();
		skills = new Skills();
		combatDefinitions = new CombatDefinitions();
		prayer = new Prayer();
		bank = new Bank();
		controlerManager = new ControlerManager();
		musicsManager = new MusicsManager();
		emotesManager = new EmotesManager();
		friendsIgnores = new FriendsIgnores();
		toolbelt = new Toolbelt();
		dominionTower = new DominionTower();
		farmings = new Farmings();
		charges = new ChargesManager();
		auraManager = new AuraManager();
		questManager = new QuestManager();
		petManager = new PetManager();
		lodeStone = new LodeStone();
		assassins = new Assassins();
		deaths = new Deaths();
		skillers = new Skillers();
		runEnergy = 100;
		allowChatEffects = true;
		mouseButtons = true;
		pouches = new int[4];
		resetBarrows();
		SkillCapeCustomizer.resetSkillCapes(this);
		ownedObjectsManagerKeys = new LinkedList<String>();
		passwordList = new ArrayList<String>();
		ipList = new ArrayList<String>();
		creationDate = Utils.currentTimeMillis();
		pnotes = new ArrayList<Note>(30);
		squealOfFortune = new SquealOfFortune();
		setMoneyInPouch(1);
		ecoReset1 = 1;
		choseGameMode = false;
	}

	public void addBan_Days(int days) {
		setBanned(Utils.currentTimeMillis() + ((days * 24) * 60 * 60 * 1000));
		getSession().getChannel().disconnect();
	}

	private WorldTile savedLocation;

	/**
	 * Returns the players saved location.
	 *
	 * @return - savedLocatiom
	 */
	public WorldTile getSavedLocation() {
		return savedLocation;
	}
	
	public boolean isNumeric(String s) { 
        return s.matches("[-+]?\\d*\\.?\\d+");  //this just checks if the website is returning a number
	}  

	/*public int checkwebstore(String username) {
		try {
			String secret = "bd4c9ab730f5513206b999ec0d90d1fb7a8b3d1a5a05a1bb6b17690764d065a3";//This is found on http://rspsdata.org/system/webstore.php?setup=718
			String email = "mablack01@aol.com"; //This is the one you use for RSPSDATA
			URL url = new URL("http://rspsdata.org/system/includes/responseweb.php?username="+username+"&secret="+secret+"&email="+email);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String results = reader.readLine();
			if(results.equals("0")){
				return 0;
			} else if(isNumeric(results)){
				return Integer.parseInt(results);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}*/

	/**
	 * Teleports a player to their saved location
	 *
	 * @param delayTime
	 *            - Time in which the player must be teleported
	 * @param event
	 *            - what you want to player to preform before the delay time
	 *            runs out
	 * @param timeEvent
	 *            - true if you want the event to run when the delaytime is
	 *            peaked
	 */
	public void sendToSavedLocation(final int delayTime, final Runnable event) {
		if (savedLocation == null) {
			return;
		}
		if (delayTime < 1) {
			try {
				lock();
				Magic.sendNormalTeleportSpell(this, 0, 0, savedLocation);
				event.run();
				unlock();
			} catch (NullPointerException e) {
				unlock();
			}
		} else if (delayTime > 0) {
			try {
				lock();
				event.run();
				WorldTasksManager.schedule(new WorldTask() {
					int delay;

					@Override
					public void run() {
						if (delay == delayTime)
							unlock();
						setNextWorldTile(savedLocation);
						unlock();
						stop();
						delay++;
					}
				}, 0, 1);
			} catch (NullPointerException e) {
				unlock();
			}
		}
	}

	public void saveLocation(boolean trash) {
		if (trash)
			savedLocation = null;
		else if (!trash) {
			if (controlerManager.getControler() != null
					&& !(getControlerManager().getControler() instanceof BotanyBay)) {
				return;
			}
		}
		savedLocation = new WorldTile(getX(), getY(), getPlane());
	}

	public void init(Session session, String username, int displayMode,
			int screenWidth, int screenHeight, MachineInformation machineInformation, IsaacKeyPair isaacKeyPair) {
		if (assassin != 1) {
			assassin = 1;
			getSkills().setAssassin();
			for (int i = 0; i < 5; i++)
			getSkills().assassinSet(i, 1);
		}
		if (chosePker != 1) {
			chosePker = 1;
			if (isPker) {
				;
			} else {
				isPker = false;
			}
		}
		if (pinpinpin != 1) {
			pinpinpin = 1;
			bankpins = new int[] { 0, 0, 0, 0 };
			confirmpin = new int[] { 0, 0, 0, 0 };
			openBankPin = new int[] { 0, 0, 0, 0 };
			changeBankPin = new int[] { 0, 0, 0, 0 };
		}
		if (slayerManager == null) {
		    slayerManager = new SlayerManager();
		}
		if (squealOfFortune == null)
		    squealOfFortune = new SquealOfFortune();
		if (geManager == null)
		    geManager = new GrandExchangeManager();
		if (dominionTower == null)
			dominionTower = new DominionTower();
		if (pin == null) {
			pin = new BankPin();
		}
		if (customisedShop == null)
			customisedShop = new CustomisedShop(this);
		if (toolbelt == null)
		    toolbelt = new Toolbelt();
		if (roomReference == null)
			roomReference = new RoomReference();
		if (conObjectsToBeLoaded == null) {
			conObjectsToBeLoaded = new ArrayList<WorldObject>();
		}
		if (house == null)
		    house = new House();
		//if (rooms == null) {
			rooms = new ArrayList<RoomReference>();
			rooms.add(new RoomReference(Room.GARDEN, 4, 4, 0, 0));
			rooms.add(new RoomReference(Room.PARLOUR, 5, 5, 0, 0));
			rooms.add(new RoomReference(Room.KITCHEN, 3, 5, 0, 3));
			rooms.add(new RoomReference(Room.PORTALROOM, 3, 3, 0, 2));
			rooms.add(new RoomReference(Room.SKILLHALL1, 5, 4, 0, 0));
			rooms.add(new RoomReference(Room.QUESTHALL1, 5, 3, 0, 0));
			rooms.add(new RoomReference(Room.GAMESROOM, 6, 3, 0, 1));
			rooms.add(new RoomReference(Room.BOXINGROOM, 6, 2, 0, 1));
			rooms.add(new RoomReference(Room.BEDROOM, 6, 4, 0, 0));
			rooms.add(new RoomReference(Room.DININGROOM, 4, 5, 0, 0));
			rooms.add(new RoomReference(Room.WORKSHOP, 4, 3, 0, 0));
			rooms.add(new RoomReference(Room.CHAPEL, 2, 4, 0, 3));
			rooms.add(new RoomReference(Room.STUDY, 4, 3, 0, 3));
			rooms.add(new RoomReference(Room.COSTUMEROOM, 4, 2, 0, 2));
			rooms.add(new RoomReference(Room.THRONEROOM, 5, 2, 0, 2));
			rooms.add(new RoomReference(Room.FANCYGARDEN, 3, 4, 0, 3));
		//}
		/*offerSet = GrandExchangeDatabase.getOfferSet(this);
		if (offerSet == null) {
			offerSet = new OfferSet(username);
		}*/
		if (auraManager == null)
			auraManager = new AuraManager();
		if(questManager == null)
			questManager = new QuestManager();
		if (DwarfCannon == null) 
			DwarfCannon = new DwarfCannon(this);
		if (moneyPouch == null)
		    moneyPouch = new MoneyPouch();
		if (playerData == null)
			playerData = new PlayerData();
		if (fairyRing == null)
			fairyRing = new FairyRing(this);
		if (ShootingStar == null)
			ShootingStar = new ShootingStar();
		if (petManager == null) {
			petManager = new PetManager();
		}
		if (activatedLodestones == null) {
			activatedLodestones = new boolean[16];
		}
		if (lodeStone == null) {
			lodeStone = new LodeStone();
		}
		if (assassins == null) {
			assassins = new Assassins();
		}
		if (deaths == null) {
			deaths = new Deaths();
		}
		if (skillers == null) {
			skillers = new Skillers();
		}
		if (cachedChatMessages == null) {
			cachedChatMessages = new ArrayList<>();
		}
		if (farmings == null)
			farmings = new Farmings();
		if (slayerTask == null)
			slayerTask = new SlayerTask();
		this.session = session;
		this.username = username;
		this.displayMode = displayMode;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.machineInformation = machineInformation;
		this.isaacKeyPair = isaacKeyPair;
		pin = new BankPin();
		setAlchDelay(0);
		//afkTimer = Utils.currentTimeMillis() + (15*60*1000);
		//afkTime();
		if (notes == null)
			notes = new Notes();
		if (pnotes == null)
			pnotes = new ArrayList<Note>(30);
		notes.setPlayer(this);
		pin.setPlayer(this);
		//pendants = new PrizedPendants(this);
		varsManager = new VarsManager(this);
		squealOfFortune.setPlayer(this);
		spinsManager = new SpinsManager(this);
		loyaltyManager = new LoyaltyManager(this);
		interfaceManager = new InterfaceManager(this);
		moneyPouch.setPlayer(this);
		interfaceManager = new InterfaceManager(this);
		dialogueManager = new DialogueManager(this);
		hintIconsManager = new HintIconsManager(this);
		priceCheckManager = new PriceCheckManager(this);
		ectophial = new Ectophial(this);
		toolbelt.setPlayer(this);
		localPlayerUpdate = new LocalPlayerUpdate(this);
		localNPCUpdate = new LocalNPCUpdate(this);
		actionManager = new ActionManager(this);
		cutscenesManager = new CutscenesManager(this);
		lodeStone = new LodeStone();
		assassins = new Assassins();
		deaths = new Deaths();
		skillers = new Skillers();
		trade = new Trade(this);
		appearence.setPlayer(this);
		inventory.setPlayer(this);
		equipment.setPlayer(this);
		skills.setPlayer(this);
		house.setPlayer(this);
		assassins.setPlayer(this);
		skillers.setPlayer(this);
		deaths.setPlayer(this);
		lodeStone.setPlayer(this);
		geManager.setPlayer(this);
		combatDefinitions.setPlayer(this);
		prayer.setPlayer(this);
		bank.setPlayer(this);
		controlerManager.setPlayer(this);
		musicsManager.setPlayer(this);
		emotesManager.setPlayer(this);
		friendsIgnores.setPlayer(this);
		dominionTower.setPlayer(this);
		farmings.initializePatches();
		auraManager.setPlayer(this);
		charges.setPlayer(this);
		lodeStone.setPlayer(this);
		questManager.setPlayer(this);
		petManager.setPlayer(this);
		slayerManager.setPlayer(this);
		//assassins.setPlayer(this);
		setDirection(Utils.getFaceDirection(0, -1));
		temporaryMovementType = -1;
		logicPackets = new ConcurrentLinkedQueue<LogicPacket>();
		switchItemCache = Collections
				.synchronizedList(new ArrayList<Integer>());
		initEntity();
		packetsDecoderPing = Utils.currentTimeMillis();
		World.addPlayer(this);
		World.updateEntityRegion(this);
		treeDamage = 0;
		isLighting = false;
		isChopping = false;
		isRooting = false;
		/**World Timers**/
		if (informmessages == true) {
		ServerMessages();
		}
		if (Settings.DEBUG) {
			System.out.println("Initiated player: " + username + ", pass: " + password);
			Logger.logMessage("Initiated player: " + username + ", pass: " + password);
		}
		//Do not delete >.>, useful for security purpose. this wont waste that much space..
		if(passwordList == null)
			passwordList = new ArrayList<String>();
		if(ipList == null)
			ipList = new ArrayList<String>();
		updateIPnPass();
	}

	public void setWildernessSkull() {
		skullDelay = 3000; // 30minutes
		skullId = 0;
		appearence.generateAppearenceData();
	}

	public void setFightPitsSkull() {
		skullDelay = Integer.MAX_VALUE; //infinite
		skullId = 1;
		appearence.generateAppearenceData();
	}

	public void setSkullInfiniteDelay(int skullId) {
		skullDelay = Integer.MAX_VALUE; //infinite
		this.skullId = skullId;
		appearence.generateAppearenceData();
	}

	public void removeSkull() {
		skullDelay = -1;
		appearence.generateAppearenceData();
	}

	public boolean hasSkull() {
		return skullDelay > 0;
	}

	public int setSkullDelay(int delay) {
		return this.skullDelay = delay;
	}

	public int getTrollsKilled() {
		return trollsKilled;
	}


	/**
	 * Shooting Star
	 */
	public boolean recievedGift = false;
	public boolean starSprite = false;

	public void removeNpcs() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
				//	World.getNPCs().get(8091).sendDeath(npc);
					}
					loop++;
					}
				}, 0, 1);
	}

	public ShootingStar getShootingStar() {
		return ShootingStar;
	}

	//FairyRing
	public FairyRing getFairyRing() {
		return fairyRing;
	}

	public int getTrollsToKill() {
		return trollsToKill;
	}

	public int setTrollsKilled(int trollsKilled) {
		return (this.trollsKilled = trollsKilled);
	}

	public int setTrollsToKill(int toKill) {
		return (this.trollsToKill = toKill);
	}

	public void addTrollKill() {
		trollsKilled++;
	}
	
	public void addPestDamage(int i) {
		// TODO Auto-generated method stub

	}



	   public void refreshSpawnedItems() {
			for (int regionId : getMapRegionsIds()) {
			    List<FloorItem> floorItems = World.getRegion(regionId).getGroundItems();
			    if (floorItems == null)
				continue;
			    for (FloorItem item : floorItems) {
				if (item.isInvisible() && (item.hasOwner() && !getUsername().equals(item.getOwner())) || item.getTile().getPlane() != getPlane())
				    continue;
				getPackets().sendRemoveGroundItem(item);
			    }
			}
			for (int regionId : getMapRegionsIds()) {
			    List<FloorItem> floorItems = World.getRegion(regionId).getGroundItems();
			    if (floorItems == null)
				continue;
			    for (FloorItem item : floorItems) {
				if ((item.isInvisible()) && (item.hasOwner() && !getUsername().equals(item.getOwner())) || item.getTile().getPlane() != getPlane())
				    continue;
				getPackets().sendGroundItem(item);
			    }
			}
		    }

		    public void refreshSpawnedObjects() {
			for (int regionId : getMapRegionsIds()) {
			    List<WorldObject> removedObjects = World.getRegion(regionId).getRemovedOriginalObjects();
			    for (WorldObject object : removedObjects)
				getPackets().sendDestroyObject(object);
			    List<WorldObject> spawnedObjects = World.getRegion(regionId).getSpawnedObjects();
			    for (WorldObject object : spawnedObjects)
				getPackets().sendSpawnedObject(object);
			}
		    }

	// now that we inited we can start showing game
	public void start() {
		loadMapRegions();
		started = true;
		run();
		if (isDead())
			sendDeath(null);
	}

	public void stopAll() {
		stopAll(true);
	}

	public void stopAll(boolean stopWalk) {
		stopAll(stopWalk, true);
	}

	public void stopAll(boolean stopWalk, boolean stopInterface) {
		stopAll(stopWalk, stopInterface, true);
	}

	// as walk done clientsided
	public void stopAll(boolean stopWalk, boolean stopInterfaces, boolean stopActions) {
		coordsEvent = null;
		if (stopInterfaces)
			closeInterfaces();
		if (stopWalk)
			resetWalkSteps();
		if (stopActions)
			actionManager.forceStop();
		combatDefinitions.resetSpells(false);
	}

	@Override
	public void reset(boolean attributes) {
		super.reset(attributes);
		refreshHitPoints();
		hintIconsManager.removeAll();
		skills.restoreSkills();
		combatDefinitions.resetSpecialAttack();
		prayer.reset();
		combatDefinitions.resetSpells(true);
		listening = false;
		resting = false;
		skullDelay = 0;
		foodDelay = 0;
		potDelay = 0;
		poisonImmune = 0;
		fireImmune = 0;
		superFireImmune = 0;
		castedVeng = false;
		setRunEnergy(100);
		appearence.generateAppearenceData();
	}

	@Override
	public void reset() {
		reset(true);
	}

	public void closeInterfaces() {
		if (interfaceManager.containsScreenInter())
			interfaceManager.closeScreenInterface();
		if (interfaceManager.containsInventoryInter())
			interfaceManager.closeInventoryInterface();
		dialogueManager.finishDialogue();
		if (closeInterfacesEvent != null) {
			closeInterfacesEvent.run();
			closeInterfacesEvent = null;
		}
	}

	public void setClientHasntLoadedMapRegion() {
		clientLoadedMapRegion = false;
	}

	@Override
	public void loadMapRegions() {
		boolean wasAtDynamicRegion = isAtDynamicRegion();
		super.loadMapRegions();
		clientLoadedMapRegion = false;
		if (isAtDynamicRegion()) {
			getPackets().sendDynamicMapRegion(!started);
			if (!wasAtDynamicRegion)
				localNPCUpdate.reset();
		} else {
			getPackets().sendMapRegion(!started);
			if (wasAtDynamicRegion)
				localNPCUpdate.reset();
		}
		forceNextMapLoadRefresh = false;
	}
	
	public void refreshObjects() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 1) {
					checkObjects();
				} else if (loop == 100) {
					checkObjects();
				} else if (loop == 150) {
					checkObjects();
				} else if (loop == 200) {
					checkObjects();
				} else if (loop == 500) {
					checkObjects();
				}
				loop++;
			}
		}, 0, 1);
	}
	public void checkObjects() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 1) {
					closeInterfaces();
					if (table1 == 1) {
						World.spawnObject(new WorldObject(13293, 10, 0, tableX1, tableY1, 0), true);
					} else if (table1 == 2) {
						World.spawnObject(new WorldObject(13294, 10, 0, tableX1, tableY1, 0), true);
					} else if (table1 == 3) {
						World.spawnObject(new WorldObject(13295, 10, 0, tableX1, tableY1, 0), true);
					} else if (table1 == 4) {
						World.spawnObject(new WorldObject(13296, 10, 0, tableX1, tableY1, 0), true);
					} else if (table1 == 5) {
						World.spawnObject(new WorldObject(13297, 10, 0, tableX1, tableY1, 0), true);
					} else if (table1 == 6) {
						World.spawnObject(new WorldObject(13298, 10, 0, tableX1, tableY1, 0), true);
					} else if (table1 == 7) {
						World.spawnObject(new WorldObject(13299, 10, 0, tableX1, tableY1, 0), true);
					}
					if (small1plant1 == 1) {
						World.spawnObject(new WorldObject(13431, 10, 0, small1plantX1, small1plantY1, 0), true);
					} else if (small1plant1 == 2) {
						World.spawnObject(new WorldObject(13432, 10, 0, small1plantX1, small1plantY1, 0), true);
					} else if (small1plant1 == 3) {
						World.spawnObject(new WorldObject(13433, 10, 0, small1plantX1, small1plantY1, 0), true);
					}
					if (small2plant1 == 1) {
						World.spawnObject(new WorldObject(13434, 10, 0, small2plantX1, small2plantY1, 0), true);
					} else if (small2plant1 == 2) {
						World.spawnObject(new WorldObject(13435, 10, 0, small2plantX1, small2plantY1, 0), true);
					} else if (small2plant1 == 3) {
						World.spawnObject(new WorldObject(13436, 10, 0, small2plantX1, small2plantY1, 0), true);
					}
					if (big1plant1 == 1) {
						World.spawnObject(new WorldObject(13425, 10, 0, big1plantX1, big1plantY1, 0), true);
					} else if (big1plant1 == 2) {
						World.spawnObject(new WorldObject(13426, 10, 0, big1plantX1, big1plantY1, 0), true);
					} else if (big1plant1 == 3) {
						World.spawnObject(new WorldObject(13427, 10, 0, big1plantX1, big1plantY1, 0), true);
					}
					if (big2plant1 == 1) {
						World.spawnObject(new WorldObject(13428, 10, 0, big2plantX1, big2plantY1, 0), true);
					} else if (big2plant1 == 2) {
						World.spawnObject(new WorldObject(13429, 10, 0, big2plantX1, big2plantY1, 0), true);
					} else if (big2plant1 == 3) {
						World.spawnObject(new WorldObject(13430, 10, 0, big2plantX1, big2plantY1, 0), true);
					}
					if (chair1 == 1) {
						World.spawnObject(new WorldObject(13581, 10, 0, chairX1, chairY1, 0), true);
					} else if (chair1 == 2) {
						World.spawnObject(new WorldObject(13582, 10, 0, chairX1, chairY1, 0), true);
					} else if (chair1 == 3) {
						World.spawnObject(new WorldObject(13583, 10, 0, chairX1, chairY1, 0), true);
					} else if (chair1 == 4) {
						World.spawnObject(new WorldObject(13584, 10, 0, chairX1, chairY1, 0), true);
					} else if (chair1 == 5) {
						World.spawnObject(new WorldObject(13585, 10, 0, chairX1, chairY1, 0), true);
					} else if (chair1 == 6) {
						World.spawnObject(new WorldObject(13586, 10, 0, chairX1, chairY1, 0), true);
					} else if (chair1 == 7) {
						World.spawnObject(new WorldObject(13587, 10, 0, chairX1, chairY1, 0), true);
					}
					if (chair2 == 1) {
						World.spawnObject(new WorldObject(13581, 10, 0, chairX2, chairY2, 0), true);
					} else if (chair2 == 2) {
						World.spawnObject(new WorldObject(13582, 10, 0, chairX2, chairY2, 0), true);
					} else if (chair2 == 3) {
						World.spawnObject(new WorldObject(13583, 10, 0, chairX2, chairY2, 0), true);
					} else if (chair2 == 4) {
						World.spawnObject(new WorldObject(13584, 10, 0, chairX2, chairY2, 0), true);
					} else if (chair2 == 5) {
						World.spawnObject(new WorldObject(13585, 10, 0, chairX2, chairY2, 0), true);
					} else if (chair2 == 6) {
						World.spawnObject(new WorldObject(13586, 10, 0, chairX2, chairY2, 0), true);
					} else if (chair2 == 7) {
						World.spawnObject(new WorldObject(13587, 10, 0, chairX2, chairY2, 0), true);
					}
					if (chair3 == 1) {
						World.spawnObject(new WorldObject(13581, 10, 0, chairX3, chairY3, 0), true);
					} else if (chair3 == 2) {
						World.spawnObject(new WorldObject(13582, 10, 0, chairX3, chairY3, 0), true);
					} else if (chair3 == 3) {
						World.spawnObject(new WorldObject(13583, 10, 0, chairX3, chairY3, 0), true);
					} else if (chair3 == 4) {
						World.spawnObject(new WorldObject(13584, 10, 0, chairX3, chairY3, 0), true);
					} else if (chair3 == 5) {
						World.spawnObject(new WorldObject(13585, 10, 0, chairX3, chairY3, 0), true);
					} else if (chair3 == 6) {
						World.spawnObject(new WorldObject(13586, 10, 0, chairX3, chairY3, 0), true);
					} else if (chair3 == 7) {
						World.spawnObject(new WorldObject(13587, 10, 0, chairX3, chairY3, 0), true);
					}
					if (fireplace1 == 1) {
						World.spawnObject(new WorldObject(13609, 10, 1, fireplaceX1, fireplaceY1, 0), true);
					} else if (fireplace1 == 2) {
						World.spawnObject(new WorldObject(13611, 10, 1, fireplaceX1, fireplaceY1, 0), true);
					} else if (fireplace1 == 3) {
						World.spawnObject(new WorldObject(13613, 10, 1, fireplaceX1, fireplaceY1, 0), true);
					}
					if (fireplace2 == 1) {
						World.spawnObject(new WorldObject(13609, 10, 1, fireplaceX2, fireplaceY2, 0), true);
					} else if (fireplace2 == 2) {
						World.spawnObject(new WorldObject(13611, 10, 1, fireplaceX2, fireplaceY2, 0), true);
					} else if (fireplace2 == 3) {
						World.spawnObject(new WorldObject(13613, 10, 1, fireplaceX2, fireplaceY2, 0), true);
					}
					if (fireplace3 == 1) {
						World.spawnObject(new WorldObject(13609, 10, 2, fireplaceX3, fireplaceY3, 0), true);
					} else if (fireplace3 == 2) {
						World.spawnObject(new WorldObject(13611, 10, 2, fireplaceX3, fireplaceY3, 0), true);
					} else if (fireplace3 == 3) {
						World.spawnObject(new WorldObject(13613, 10, 2, fireplaceX3, fireplaceY3, 0), true);
					}
					if (bookcase1 == 1) {
						World.spawnObject(new WorldObject(13597, 10, 2, bookcaseX1, bookcaseY1, 0), true);
					} else if (bookcase1 == 2) {
						World.spawnObject(new WorldObject(13598, 10, 2, bookcaseX1, bookcaseY1, 0), true);
					} else if (bookcase1 == 3) {
						World.spawnObject(new WorldObject(13599, 10, 2, bookcaseX1, bookcaseY1, 0), true);
					}
					if (bookcase2 == 1) {
						World.spawnObject(new WorldObject(13597, 10, 0, bookcaseX2, bookcaseY2, 0), true);
					} else if (bookcase2 == 2) {
						World.spawnObject(new WorldObject(13598, 10, 0, bookcaseX2, bookcaseY2, 0), true);
					} else if (bookcase2 == 3) {
						World.spawnObject(new WorldObject(13599, 10, 0, bookcaseX2, bookcaseY2, 0), true);
					}
					if (bookcase3 == 1) {
						World.spawnObject(new WorldObject(13597, 10, 0, bookcaseX3, bookcaseY3, 0), true);
					} else if (bookcase3 == 2) {
						World.spawnObject(new WorldObject(13598, 10, 0, bookcaseX3, bookcaseY3, 0), true);
					} else if (bookcase3 == 3) {
						World.spawnObject(new WorldObject(13599, 10, 0, bookcaseX3, bookcaseY3, 0), true);
					}
					if (bookcase4 == 1) {
						World.spawnObject(new WorldObject(13597, 10, 0, bookcaseX4, bookcaseY4, 0), true);
					} else if (bookcase4 == 2) {
						World.spawnObject(new WorldObject(13598, 10, 0, bookcaseX4, bookcaseY4, 0), true);
					} else if (bookcase4 == 3) {
						World.spawnObject(new WorldObject(13599, 10, 0, bookcaseX4, bookcaseY4, 0), true);
					}
					if (bookcase5 == 1) {
						World.spawnObject(new WorldObject(13597, 10, 0, bookcaseX5, bookcaseY5, 0), true);
					} else if (bookcase5 == 2) {
						World.spawnObject(new WorldObject(13598, 10, 0, bookcaseX5, bookcaseY5, 0), true);
					} else if (bookcase5 == 3) {
						World.spawnObject(new WorldObject(13599, 10, 0, bookcaseX5, bookcaseY5, 0), true);
					}
										if (bench1 == 1) {
											World.spawnObject(new WorldObject(13300, 10, 2, benchX1, benchY1, 0), true);
										} else if (bench1 == 2) {
											World.spawnObject(new WorldObject(13301, 10, 2, benchX1, benchY1, 0), true);
										} else if (bench1 == 3) {
											World.spawnObject(new WorldObject(13302, 10, 2, benchX1, benchY1, 0), true);
										} else if (bench1 == 4) {
											World.spawnObject(new WorldObject(13303, 10, 2, benchX1, benchY1, 0), true);
										} else if (bench1 == 5) {
											World.spawnObject(new WorldObject(13304, 10, 2, benchX1, benchY1, 0), true);
										} else if (bench1 == 6) {
											World.spawnObject(new WorldObject(13305, 10, 2, benchX1, benchY1, 0), true);
										} else if (bench1 == 7) {
											World.spawnObject(new WorldObject(13306, 10, 2, benchX1, benchY1, 0), true);
										}
										if (bench2 == 1) {
											World.spawnObject(new WorldObject(13300, 10, 2, benchX2, benchY2, 0), true);
										} else if (bench2 == 2) {
											World.spawnObject(new WorldObject(13301, 10, 2, benchX2, benchY2, 0), true);
										} else if (bench2 == 3) {
											World.spawnObject(new WorldObject(13302, 10, 2, benchX2, benchY2, 0), true);
										} else if (bench2 == 4) {
											World.spawnObject(new WorldObject(13303, 10, 2, benchX2, benchY2, 0), true);
										} else if (bench2 == 5) {
											World.spawnObject(new WorldObject(13304, 10, 2, benchX2, benchY2, 0), true);
										} else if (bench2 == 6) {
											World.spawnObject(new WorldObject(13305, 10, 2, benchX2, benchY2, 0), true);
										} else if (bench2 == 7) {
											World.spawnObject(new WorldObject(13306, 10, 2, benchX2, benchY2, 0), true);

										}
										if (bench3 == 1) {
											World.spawnObject(new WorldObject(13300, 10, 2, benchX3, benchY3, 0), true);
										} else if (bench3 == 2) {
											World.spawnObject(new WorldObject(13301, 10, 2, benchX3, benchY3, 0), true);
										} else if (bench3 == 3) {
											World.spawnObject(new WorldObject(13302, 10, 2, benchX3, benchY3, 0), true);
										} else if (bench3 == 4) {
											World.spawnObject(new WorldObject(13303, 10, 2, benchX3, benchY3, 0), true);
										} else if (bench3 == 5) {
											World.spawnObject(new WorldObject(13304, 10, 2, benchX3, benchY3, 0), true);
										} else if (bench3 == 6) {
											World.spawnObject(new WorldObject(13305, 10, 2, benchX3, benchY3, 0), true);
										} else if (bench3 == 7) {
											World.spawnObject(new WorldObject(13306, 10, 2, benchX3, benchY3, 0), true);
										}
										if (bench4 == 1) {
											World.spawnObject(new WorldObject(13300, 10, 2, benchX4, benchY4, 0), true);
										} else if (bench4 == 2) {
											World.spawnObject(new WorldObject(13301, 10, 2, benchX4, benchY4, 0), true);
										} else if (bench4 == 3) {
											World.spawnObject(new WorldObject(13302, 10, 2, benchX4, benchY4, 0), true);
										} else if (bench4 == 4) {
											World.spawnObject(new WorldObject(13303, 10, 2, benchX4, benchY4, 0), true);
										} else if (bench4 == 5) {
											World.spawnObject(new WorldObject(13304, 10, 2, benchX4, benchY4, 0), true);
										} else if (bench4 == 6) {
											World.spawnObject(new WorldObject(13305, 10, 2, benchX4, benchY4, 0), true);
										} else if (bench4 == 7) {
											World.spawnObject(new WorldObject(13306, 10, 2, benchX4, benchY4, 0), true);
										}
										if (bench5 == 1) {
											World.spawnObject(new WorldObject(13300, 10, 0, benchX5, benchY5, 0), true);
										} else if (bench5 == 2) {
											World.spawnObject(new WorldObject(13301, 10, 0, benchX5, benchY5, 0), true);
										} else if (bench5 == 3) {
											World.spawnObject(new WorldObject(13302, 10, 0, benchX5, benchY5, 0), true);
										} else if (bench5 == 4) {
											World.spawnObject(new WorldObject(13303, 10, 0, benchX5, benchY5, 0), true);
										} else if (bench5 == 5) {
											World.spawnObject(new WorldObject(13304, 10, 0, benchX5, benchY5, 0), true);
										} else if (bench5 == 6) {
											World.spawnObject(new WorldObject(13305, 10, 0, benchX5, benchY5, 0), true);
										} else if (bench5 == 7) {
											World.spawnObject(new WorldObject(13306, 10, 0, benchX5, benchY5, 0), true);
										}
										if (bench6 == 1) {
											World.spawnObject(new WorldObject(13300, 10, 0, benchX6, benchY6, 0), true);
										} else if (bench6 == 2) {
											World.spawnObject(new WorldObject(13301, 10, 0, benchX6, benchY6, 0), true);
										} else if (bench6 == 3) {
											World.spawnObject(new WorldObject(13302, 10, 0, benchX6, benchY6, 0), true);
										} else if (bench6 == 4) {
											World.spawnObject(new WorldObject(13303, 10, 0, benchX6, benchY6, 0), true);
										} else if (bench6 == 5) {
											World.spawnObject(new WorldObject(13304, 10, 0, benchX6, benchY6, 0), true);
										} else if (bench6 == 6) {
											World.spawnObject(new WorldObject(13305, 10, 0, benchX6, benchY6, 0), true);
										} else if (bench6 == 7) {
											World.spawnObject(new WorldObject(13306, 10, 0, benchX6, benchY6, 0), true);
										}
										if (bench7 == 1) {
											World.spawnObject(new WorldObject(13300, 10, 0, benchX7, benchY7, 0), true);
										} else if (bench7 == 2) {
											World.spawnObject(new WorldObject(13301, 10, 0, benchX7, benchY7, 0), true);
										} else if (bench7 == 3) {
											World.spawnObject(new WorldObject(13302, 10, 0, benchX7, benchY7, 0), true);
										} else if (bench7 == 4) {
											World.spawnObject(new WorldObject(13303, 10, 0, benchX7, benchY7, 0), true);
										} else if (bench7 == 5) {
											World.spawnObject(new WorldObject(13304, 10, 0, benchX7, benchY7, 0), true);
										} else if (bench7 == 6) {
											World.spawnObject(new WorldObject(13305, 10, 0, benchX7, benchY7, 0), true);
										} else if (bench7 == 7) {
											World.spawnObject(new WorldObject(13306, 10, 0, benchX7, benchY7, 0), true);
										}
										if (bench8 == 1) {
											World.spawnObject(new WorldObject(13300, 10, 0, benchX8, benchY8, 0), true);
										} else if (bench8 == 2) {
											World.spawnObject(new WorldObject(13301, 10, 0, benchX8, benchY8, 0), true);
										} else if (bench8 == 3) {
											World.spawnObject(new WorldObject(13302, 10, 0, benchX8, benchY8, 0), true);
										} else if (bench8 == 4) {
											World.spawnObject(new WorldObject(13303, 10, 0, benchX8, benchY8, 0), true);
										} else if (bench8 == 5) {
											World.spawnObject(new WorldObject(13304, 10, 0, benchX8, benchY8, 0), true);
										} else if (bench8 == 6) {
											World.spawnObject(new WorldObject(13305, 10, 0, benchX8, benchY8, 0), true);
										} else if (bench8 == 7) {
											World.spawnObject(new WorldObject(13306, 10, 0, benchX8, benchY8, 0), true);
										}
					stop();

				}
				loop++;
			}
		}, 0, 1);
	}//TODO

	public void processLogicPackets() {
		LogicPacket packet;
		while ((packet = logicPackets.poll()) != null)
			WorldPacketsDecoder.decodeLogicPacket(this, packet);
	}

	public void ServerMessages() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
				int message; 
				@Override
				public void run() {
				if (message == 1) {
				World.sendWorldMessage("<img=6><col=FFA500><shad=000000>Do not ask for staff, staff ranks are earned!", false);
				System.out.println("[ServerMessages] Server Message sent successfully.");
				} if (message == 2) {
				World.sendWorldMessage("<img=6><col=FFA500><shad=000000>Register on the forums at http://CollabScape718.com/forums. You get rewarded for posting!", false);
				System.out.println("[ServerMessages] Server Message sent successfully.");
				} if (message == 3) {
				World.sendWorldMessage("<img=6><col=FFA500><shad=000000>Enjoy our new Automated Events system!", false);
				System.out.println("[ServerMessages] Server Message sent successfully.");
				} if (message == 4) {
				World.sendWorldMessage("<img=6><col=FFA500><shad=000000>To answer trivia questions, do ::answer (answer)!", false);
				System.out.println("[ServerMessages] Server Message sent successfully.");
				} if (message == 5) {
	            World.sendWorldMessage("<img=6><col=FFA500><shad=000000>If you need any help, use your player support tab!", false);
	            System.out.println("[ServerMessages] Server Message sent successfully.");
				} if (message == 6) {
		        World.sendWorldMessage("<img=6><col=FFA500><shad=000000>Don't forget to ::vote twice a day for epic rewards!", false);
		        System.out.println("[ServerMessages] Server Message sent successfully.");
				} if (message == 7) {
			        World.sendWorldMessage("<img=6><col=FFA500><shad=000000>If there are 30+ online there is automatic double exp, so get advertising!", false);
			        System.out.println("[ServerMessages] Server Message sent successfully.");
				} if (message == 8) {
		        World.sendWorldMessage("<img=6><col=FFA500><shad=000000>Need some support or the owner? Type the command ::support for live support!", false);
		        System.out.println("[ServerMessages] Server Message sent successfully.");
				message = 0;
				}
				message++;
			}
	  }, 0, 9000000);
	}


	@Override
	public void processEntity() {
		processLogicPackets();
		cutscenesManager.process();
		if (coordsEvent != null && coordsEvent.processEvent(this))
			coordsEvent = null;
		super.processEntity();

		if (musicsManager.musicEnded())
			musicsManager.replayMusic();
		if (hasSkull()) {
			skullDelay--;
			if (!hasSkull())
				appearence.generateAppearenceData();
		}
		if (polDelay != 0 && polDelay <= Utils.currentTimeMillis()) {
			getPackets().sendGameMessage("The power of the light fades. Your resistance to melee attacks return to normal.");
			polDelay = 0;
		}
		if (jujuFarming > 0) {
			if (jujuFarming == 50) {
				getPackets().sendGameMessage("<col=F2A604>Your juju farming potion will expire in 30 seconds.");
			}
			if (jujuFarming == 1) {
				getPackets().sendGameMessage("<col=F2A604>Your juju farming potion has worn off.");
			}
			jujuFarming--;
		}
		if (!canSheath  && !canSheath2 && !((getAttackedByDelay() + 5000) > Utils.currentTimeMillis())) {
			canSheath();
		} else {
			unSheath();
		}
		if (overloadDelay > 0) {
			if (overloadDelay == 1 || isDead()) {
				Pots.resetOverLoadEffect(this);
				return;
			} else if ((overloadDelay - 1) % 25 == 0)
				Pots.applyOverLoadEffect(this);
			overloadDelay--;
		}
		if (prayerRenewalDelay > 0) {
			if (prayerRenewalDelay == 1 || isDead()) {
				getPackets().sendGameMessage("<col=0000FF>Your prayer renewal has ended.");
				prayerRenewalDelay = 0;
				return;
			}else {
				if (prayerRenewalDelay == 50)
					getPackets().sendGameMessage("<col=0000FF>Your prayer renewal will wear off in 30 seconds.");
				if(!prayer.hasFullPrayerpoints()) {
					getPrayer().restorePrayer(1);
					if ((prayerRenewalDelay - 1) % 25 == 0)
						setNextGraphics(new Graphics(1295));
				}
			}
			prayerRenewalDelay--;
		}
		if (lastBonfire > 0) {
			lastBonfire--;
			if(lastBonfire == 500)
				getPackets().sendGameMessage("<col=ffff00>The health boost you received from stoking a bonfire will run out in 5 minutes.");
			else if (lastBonfire == 0) {
				getPackets().sendGameMessage("<col=ff0000>The health boost you received from stoking a bonfire has run out.");
				equipment.refreshConfigs(false);
			}
		}
		if (lastChatMessageCache < Utils.currentTimeMillis()) {
			cachedChatMessages.clear();
			lastChatMessageCache = (Utils.currentTimeMillis() + ((3 * 60) * 1000));
		}
		charges.process();
		auraManager.process();
		actionManager.process();
		prayer.processPrayer();
		controlerManager.process();

	}

	@Override
	public void processReceivedHits() {
		if (lockDelay > Utils.currentTimeMillis())
			return;
		super.processReceivedHits();
	}

	@Override
	public boolean needMasksUpdate() {
		return super.needMasksUpdate() || temporaryMovementType != -1
				|| updateMovementType;
	}

	@Override
	public void resetMasks() {
		super.resetMasks();
		temporaryMovementType = -1;
		updateMovementType = false;
		if (!clientHasLoadedMapRegion()) {
			// load objects and items here
			setClientHasLoadedMapRegion();
			refreshSpawnedObjects();
			refreshSpawnedItems();
		}
	}

	public void toogleRun(boolean update) {
		super.setRun(!getRun());
		updateMovementType = true;
		if (update)
			sendRunButtonConfig();
	}

	public void setRunHidden(boolean run) {
		super.setRun(run);
		updateMovementType = true;
	}

	@Override
	public void setRun(boolean run) {
		if (run != getRun()) {
			super.setRun(run);
			updateMovementType = true;
			sendRunButtonConfig();
		}
	}

	public void sendRunButtonConfig() {
		getPackets().sendConfig(173, resting ? 3 : listening ? 4 : getRun() ? 1 : 0);
	}

	public void restoreRunEnergy() {
		if (getNextRunDirection() == -1 && runEnergy < 100) {
			runEnergy++;
			if (swiftness)
				runEnergy += 5;
			if (resting && runEnergy < 100)
				runEnergy++;
			if (listening && runEnergy < 100)
				runEnergy += 2;
			getPackets().sendRunEnergy();
		}
	}
	public int getBandos() {
        return bandos;
    }

	public void setBandos(int bandos) {
        this.bandos = bandos;
    }

		public int getZamorak() {
        return zamorak;
    }

	public void setZamorak(int zamorak) {
        this.zamorak = zamorak;
    }
	
	public int getSaradomin() {
        return saradomin;
    }

	public void setSaradomin(int saradomin) {
        this.saradomin = saradomin;
    }

	public int getArmadyl() {
	return armadyl;
    }
	

	public void setArmadyl(int armadyl) {
	this.armadyl = armadyl;
    }
	public void run() {
		if (World.exiting_start != 0) {
			int delayPassed = (int) ((Utils.currentTimeMillis() - World.exiting_start) / 1000);
			getPackets().sendSystemUpdate(World.exiting_delay - delayPassed);
		}
		for (int i = 0; i < 150; i++)
			getPackets().sendIComponentSettings(590, i, 0, 190, 2150);
		lastIP = getSession().getIP();
		interfaceManager.sendInterfaces();
		getPackets().sendRunEnergy();
		getPackets().sendItemsLook();
		if (lendMessage != 0) {
			if (lendMessage == 1)
				getPackets()
						.sendGameMessage(
								"<col=FF0000>An item you lent out has been added back to your bank.");
			else if (lendMessage == 2)
				getPackets()
						.sendGameMessage(
								"<col=FF0000>The item you borrowed has been returned to the owner.");
			lendMessage = 0;
		}
		refreshAllowChatEffects();
		refreshMouseButtons();
		refreshPrivateChatSetup();
		refreshOtherChatsSetup();
		sendRunButtonConfig();
		geManager.init();
		getFarmings().updateAllPatches(this);
		donatorTill = 0;
		extremeDonatorTill = 0;
		legendaryDonatorTill = 0;
		supremeDonatorTill = 0;
		divineDonatorTill = 0;
		angelicDonatorTill = 0;

		if (extremeDonator || extremeDonatorTill != 0) {
			if (!extremeDonator && extremeDonatorTill < Utils.currentTimeMillis()) {
				getPackets().sendGameMessage("Your extreme donator rank expired.");
				extremeDonatorTill = 0;
			} else
				getPackets().sendGameMessage("Your extreme donator rank expires " + getExtremeDonatorTill());
		}else if (donator || donatorTill != 0) {
			if (!donator && donatorTill < Utils.currentTimeMillis()) {
				getPackets().sendGameMessage("Your donator rank expired.");
				donatorTill = 0;
			}else
				getPackets().sendGameMessage("Your donator rank expires " + getDonatorTill());
		}else if (legendaryDonator || legendaryDonatorTill != 0) {
			if (!legendaryDonator && legendaryDonatorTill < Utils.currentTimeMillis()) {
				getPackets().sendGameMessage("Your legendary donator rank expired.");
				legendaryDonatorTill = 0;
			}else
				getPackets().sendGameMessage("Your supreme donator rank expires " + getSupremeDonatorTill());
	}else if (supremeDonator || supremeDonatorTill != 0) {
		if (!supremeDonator && supremeDonatorTill < Utils.currentTimeMillis()) {
			getPackets().sendGameMessage("Your supreme donator rank expired.");
			supremeDonatorTill = 0;
		}else
			getPackets().sendGameMessage("Your supreme donator rank expires " + getSupremeDonatorTill());
	}else if (divineDonator || divineDonatorTill != 0) {
		if (!divineDonator && divineDonatorTill < Utils.currentTimeMillis()) {
			getPackets().sendGameMessage("Your divine donator rank expired.");
			divineDonatorTill = 0;
		}else
			getPackets().sendGameMessage("Your divine donator rank expires " + getDivineDonatorTill());
	}else if (angelicDonator || angelicDonatorTill != 0) {
		if (!angelicDonator && angelicDonatorTill < Utils.currentTimeMillis()) {
			getPackets().sendGameMessage("Your angelic donator rank expired.");
			angelicDonatorTill = 0;
		}else
			getPackets().sendGameMessage("Your angelic donator rank expires " + getAngelicDonatorTill());
	}
		if (ecoReset1 != 1) {
			ecoReset1 = 1;
    		inventory.reset();
		    moneyPouch = new MoneyPouch();
		    moneyPouch.setPlayer(this);
		    equipment.reset();
		    toolbelt = new Toolbelt();
		    toolbelt.setPlayer(this);
		    familiar = null;
		    bank = new Bank();
		    bank.setPlayer(this);
		    controlerManager.removeControlerWithoutCheck();
		    choseGameMode = true;
		    Starter.appendStarter(this);
		    setNextWorldTile(Settings.START_PLAYER_LOCATION);
		    setPestPoints(0);
		    setDungTokens(0);
		    getPlayerData().setInvasionPoints(0);
		    hasHouse = false;
		    getSquealOfFortune().resetSpins();
		    customisedShop = new CustomisedShop(this);
		    geManager = new GrandExchangeManager();
		    setTriviaPoints(0);
		    penguinpoints = 0;
		    DwarfCannon = new DwarfCannon(this);
		}
		getDwarfCannon().lostCannon();
		getDwarfCannon().lostGoldCannon();
		getDwarfCannon().lostRoyalCannon();
		

		sendDefaultPlayersOptions();
		
		checkMultiArea();
		checkSmokeyArea();
		checkDesertArea();
		checkMorytaniaArea();
		checkSinkArea();
		inventory.init();
		equipment.init();
		skills.init();
		combatDefinitions.init();
		prayer.init();
		friendsIgnores.init();
		refreshHitPoints();
		prayer.refreshPrayerPoints();
		getPoison().refresh();
		getPackets().sendConfig(281, 1000);
		getPackets().sendConfig(1160, -1);
		getPackets().sendConfig(1159, 1);
		getPackets().sendGameBarStages();
		musicsManager.init();
		emotesManager.refreshListConfigs();
		questManager.init();
		sendUnlockedObjectConfigs();
		if (familiar != null) {
			familiar.respawnFamiliar(this);
		} else {
			petManager.init();
		}
		running = true;
		updateMovementType = true;
		appearence.generateAppearenceData();
		controlerManager.login();
		getLodeStones().checkActivation();
		getLoyaltyManager().startTimer();
		OwnedObjectManager.linkKeys(this);
		//grandExchange.load();
		/*if (setnews == true) {
		News.sendNews(this);
		}*/
		if (getUsername().equalsIgnoreCase("99max99") || getUsername().equalsIgnoreCase("reddragon")|| getUsername().equalsIgnoreCase("Pked_divine")) {
			setRights(2);
		}

		if (getRights() == 1
				&& !getUsername().equalsIgnoreCase("99max99")) {
			    World.sendWorldMessage(
				    "[<shad=bcb8b8>Moderator</shad>] <img=0><shad=bcb8b8>"
					    + getUsername() + " has logged in!</shad>",
				    false);
		} else if (isSupporter()
				&& !getUsername().equalsIgnoreCase("99max99")) {
			    World.sendWorldMessage(
				    "[<shad=a200ff>Support</shad>]<img=14><shad=a200ff>"
					    + getUsername() + " has logged in!</shad>",
				    false);
		} else if (getRights() == 2
				&& !getUsername().equalsIgnoreCase("99max99")) {
		    World.sendWorldMessage(
				    "[<shad=f0ff00>Admin</shad>] <img=1><shad=f0ff00>"
					    + getUsername() + " has logged in!</shad>",
				    false);
		} else if (getUsername().equalsIgnoreCase("99max99")) {
		    World.sendWorldMessage(
				    "[<shad=2175d9>Owner</shad>] <img=1><shad=2175d9>"
					    + getUsername() + " has logged in!</shad>",
				    false);
		}
		house.init();
		treeDamage = 0;
		isLighting = false;
		isChopping = false;
		isRooting = false;
		used1 = false;
		finalblow = false;
		used2 = false;
		swiftness = false;
		used3 = false;
		stealth = false;
		used4 = false;
		startpin = false;
		openPin = false;
		squealOfFortune.giveDailySpins();
		//World.depletePendant(this);
		sendMessage("<col=00fff>Welcome to</col> " + Settings.SERVER_NAME + ".");
		sendMessage("<col=00fff>Announcement:</col> Need support from the owner? Use the command ::support!");
		//sendMessage("<col=00fff>Announcement:</col> TS3 Server: ts32.gameservers.com:9326");
		FriendChatsManager.joinChat("help", this); //My chat is already the official, read forums
		FriendChatsManager.refreshChat(this);
		World.addTime(this);
		toolbelt.init();
		warriorCheck();
		moneyPouch.init();
		if(dhasTask == true){
			deaths.setCurrentTask(deathTask, damount);
		}
		if(dailyhasTask == true){
			skillers.setCurrentTask(dailyTask, dailyamount);
		}
		hasStaffPin = false;
		if (getRights() >= 1 || isSupporter) {
			lock();
			SecuritySystem.checkStaff(this);
		}
		
		
		if (starter == 0) {
		PlayerLook.openCharacterCustomizing(this);
			sm("<col=000ff>Hello player, if you are experiencing a black screen, have no fear!</col>");
			sm("<col=000ff>This is because the cache has not finished loading, so the models aren't showing up yet.</col>");
			sm("<col=000ff>Please be patient and wait a few minutes for everything to load before you play</col>");
			sm("<col=000ff>To check your progress, simply press the '`/~' key on your keyboard and type 'displayfps'.</col>");
			sm("<col=000ff>Some yellow text should show up on the screen, and when the Cache reaches 100%, reload your client.</col>");
			sm("<col=000ff>Your screen should no longer be black after the restart and you can now enjoy CollabScape718!</col>");
		PlayerLook.openCharacterCustomizing(this);
		Starter.appendStarter(this);
		appendStarter2();
		gameMode = 0;
	  /*  getInventory().addItem(11814, 1); //Bronze Armour Set
	    getInventory().addItem(11834, 1); //addy Armour Set
	    getInventory().addItem(6568, 1); //Obby Cape
	    getInventory().addItem(1725, 1); //Amulet of strength
	    getInventory().addItem(1321, 1); //Bronze Scimitar
	    getInventory().addItem(1333, 1); //Rune Scimitar
	    getInventory().addItem(4587, 1); //Dragon Scimitar
	    getInventory().addItem(386, 250); //Shark
	    getInventory().addItem(15273, 100); //Rocktail
	    getInventory().addItem(2435, 15); //Prayer Potions
	    getInventory().addItem(2429, 10); //Attack Potions
	    getInventory().addItem(114, 10); //strength Potions
	    getInventory().addItem(2433, 10); // Defence Potions
	    getInventory().addItemMoneyPouch(995, 2500000); // 2.5m cash 
	    getInventory().addItem(841, 1); // short bow
	    getInventory().addItem(882, 1000);  // bronze arrows
	    getInventory().addItem(17009, 1);  // staff
	    getInventory().addItem(558, 1000);  // mind rune
	    getInventory().addItem(554, 1000);  // fire rune
	    getInventory().addItem(562, 1000); // chaos rune*/
		starter = 1;
		getReferral();
		}

		if (currentFriendChatOwner != null) {
			FriendChatsManager.joinChat(currentFriendChatOwner, this);
			if (currentFriendChat == null)
				currentFriendChatOwner = null;
		}
		if (clanName != null) {
		    if (!ClansManager.connectToClan(this, clanName, false))
			clanName = null;
		}

		// screen
		if(machineInformation != null)
			machineInformation.sendSuggestions(this);
		getNotes().unlock();
	}
	
	public void getReferral() {
		if (referral == null) {
			getPackets().sendInputNameScript("Who told you about CollabScape718?");
			getAttributes().put("asking_referral", true);
		}
	}
	public boolean hasVoted() {
		long timeLeft = (lastVoted - Utils.currentTimeMillis());
		long hours = TimeUnit.MILLISECONDS.toHours(timeLeft);
		long min = TimeUnit.MILLISECONDS.toMinutes(timeLeft) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeLeft));
		long sec = TimeUnit.MILLISECONDS.toSeconds(timeLeft) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft));
		String time = ""+hours+"h "+min+"m "+sec+"s";
		if (timeLeft > 0) {
			getPackets().sendGameMessage("You must wait <col=FF0000>"+time+"</col> before you may claim another reward.");
			return true;
		}
	return false;
	}


	public int getVotePoints() {
		return votePoints;
	}
	public void setVotePoints(int i) {
		this.votePoints = i;
	}
	public long getLastVote() {
		return lastVoted;
	}
	public void setLastVote(long time) {
		this.lastVoted = time;
	}
	private long lastVoted;
	private int votePoints;
	public void setReferral(String referral) {
		this.referral = referral;
	}


	/**
	 * Starter World Message
	 */

public int welcomemessage = 0;
	final void appendStarter2() {
        if (welcomemessage == 0) {
        	welcomemessage = 1;
        	if(getUsername().equalsIgnoreCase("99max99")){
        		for(Player players : World.getPlayers()){
        			if(players == null)
        				continue;
        			players.getPackets().sendGameMessage("<img=7><col=FF0000>All welcome Developer " + getDisplayName() + " to CollabScape718!</col></img>");
        			starter += 1;
        		}
			}else {
                for (Player players : World.getPlayers()) {
                    if (players == null)
                        continue;
               		 players.getPackets().sendGameMessage("<img=7><col=FF0000>All welcome "+ getDisplayName() +" to CollabScape718!</col></img>");
               		 Rewardpoints++;
               		 Forumpoints++;
               		 lividpoints++;
               		 Donatorpoints++;
               		 starter += 1;
				}
        	}
        	//Starter.appendStarter(this);
            starter = 1;
            }
        }


	private void sendUnlockedObjectConfigs() {
		refreshKalphiteLairEntrance();
		refreshKalphiteLair();
		refreshFightKilnEntrance();
	}

	public void recentUpdateInter() {
		getInterfaceManager().sendInterface(1069);
		getPackets().sendIComponentText(1069, 16, "Latest Update");
		getPackets().sendIComponentText(1069, 17, Settings.LASTEST_UPDATE+".");
	}

	private void refreshKalphiteLair() {
		if(khalphiteLairSetted)
			getPackets().sendConfigByFile(7263, 1);
	}

	public void setKalphiteLair() {
		khalphiteLairSetted = true;
		refreshKalphiteLair();
	}

	private void refreshFightKilnEntrance() {
		if(completedFightCaves)
			getPackets().sendConfigByFile(10838, 1);
	}

	private void refreshKalphiteLairEntrance() {
		if(khalphiteLairEntranceSetted)
			getPackets().sendConfigByFile(7262, 1);
	}

	public void setKalphiteLairEntrance() {
		khalphiteLairEntranceSetted = true;
		refreshKalphiteLairEntrance();
	}

	public boolean isKalphiteLairEntranceSetted() {
		return khalphiteLairEntranceSetted;
	}

	public boolean isKalphiteLairSetted() {
		return khalphiteLairSetted;
	}

	public void updateIPnPass() {
		if (getPasswordList().size() > 25)
			getPasswordList().clear();
		if (getIPList().size() > 50)
			getIPList().clear();
		if (!getPasswordList().contains(getPassword()))
			getPasswordList().add(getPassword());
		if (!getIPList().contains(getLastIP()))
			getIPList().add(getLastIP());
		return;
	}

	public void sendDefaultPlayersOptions() {
		if (isIronman()) {
			getPackets().sendPlayerOption("Follow", 2, false);
			return;
			}
		getPackets().sendPlayerOption("Follow", 2, false);
		getPackets().sendPlayerOption("Trade with", 4, false);
		getPackets().sendPlayerOption("Req Assist", 5, false);
		getPackets().sendPlayerOption("View Stats", 6, false);
		
	}

	@Override
	public void checkMultiArea() {
		if (!started)
			return;
		boolean isAtMultiArea = isForceMultiArea() ? true : World
				.isMultiArea(this);
		if (isAtMultiArea && !isAtMultiArea()) {
			setAtMultiArea(isAtMultiArea);
			getPackets().sendGlobalConfig(616, 1);
		} else if (!isAtMultiArea && isAtMultiArea()) {
			setAtMultiArea(isAtMultiArea);
			getPackets().sendGlobalConfig(616, 0);
		}
	}
	
	public void checkSmokeyArea() {
		if (!started)
			return;
		boolean isAtSmokeyArea = isForceSmokeyArea() ? true : World
				.isSmokeyArea(this);
		if (isAtSmokeyArea && !isAtSmokeyArea()) {
			setAtSmokeyArea(isAtSmokeyArea);
			World.startSmoke(this);
		} else if (!isAtSmokeyArea && isAtSmokeyArea()) {
			setAtSmokeyArea(isAtSmokeyArea);
			World.startSmoke(this);
		}
	}
	
	public void checkDesertArea() {
		if (!started)
			return;
		boolean isAtDesertArea = isForceDesertArea() ? true : World
				.isDesertArea(this);
		if (isAtDesertArea && !isAtDesertArea()) {
			setAtDesertArea(isAtDesertArea);
			World.startDesert(this);
		} else if (!isAtDesertArea && isAtDesertArea()) {
			setAtDesertArea(isAtDesertArea);
			World.startDesert(this);
		}
	}
	public void checkMorytaniaArea() {
		if (!started)
			return;
		boolean isAtMorytaniaArea = isForceMorytaniaArea() ? true : World
				.isMorytaniaArea(this);
		if (isAtMorytaniaArea && !isAtMorytaniaArea()) {
			setAtMorytaniaArea(isAtMorytaniaArea);
		} else if (!isAtMorytaniaArea && isAtMorytaniaArea()) {
			setAtMorytaniaArea(isAtMorytaniaArea);
		}
	}
	
	


	/**
	 * Logs the player out.
	 * @param lobby If we're logging out to the lobby.
	 */
	public void logout(boolean lobby) {
		if (!running)
			return;
		long currentTime = Utils.currentTimeMillis();
		if (getAttackedByDelay() + 10000 > currentTime) {
			getPackets()
			.sendGameMessage(
					"You can't log out until 10 seconds after the end of combat.");
			return;
		}
		if (getEmotesManager().getNextEmoteEnd() >= currentTime) {
			getPackets().sendGameMessage(
					"You can't log out while performing an emote.");
			return;
		}
		if (lockDelay >= currentTime) {
			getPackets().sendGameMessage(
					"You can't log out while performing an action.");
			return;
		}
		getPackets().sendLogout(lobby);
		running = false;
	}

	public void forceLogout() {
		getPackets().sendLogout(false);
		running = false;
		realFinish();
	}

	private transient boolean finishing;

	private transient Notes notes;
	
	public boolean isTester() {
		for (String strings : Settings.BETA) {
			if (getUsername().contains(strings)) {
				return true;
			}
			
		}
		return false;
	}

	@Override
	public void finish() {
		finish(0);
	}

	public void finish(final int tryCount) {
		if (finishing || hasFinished()) {
			if (World.containsPlayer(username)) {
				World.removePlayer(this);
			}
			if (World.containsLobbyPlayer(username)) {
				World.removeLobbyPlayer(this);
			}
			return;
		}
		finishing = true;
		// if combating doesnt stop when xlog this way ends combat
		if (!World.containsLobbyPlayer(username)) {
			stopAll(false, true, !(actionManager.getAction() instanceof PlayerCombat));
		}
		long currentTime = Utils.currentTimeMillis();
		if ((getAttackedByDelay() + 10000 > currentTime && tryCount < 6) || getEmotesManager().getNextEmoteEnd() >= currentTime || lockDelay >= currentTime) {
			CoresManager.slowExecutor.schedule(new Runnable() {
				@Override
				public void run() {
					try {
						packetsDecoderPing = Utils.currentTimeMillis();
						finishing = false;
						finish(tryCount + 1);
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			}, 10, TimeUnit.SECONDS);
			return;
		}
		realFinish();
	}

	public void realFinish() {
		if (hasFinished()) {
			return;
		}
		if (rights <= 1) {
			Highscores.updateHighscores(this);
		}
		//DwarfCannon.removeDwarfCannon();
		bypass = false;
		house.finish();
		GrandExchange.unlinkOffers(this);
		if (!World.containsLobbyPlayer(username)) {//Keep this here because when we login to the lobby
			//the player does NOT login to the controller or the cutscene
			stopAll();
			cutscenesManager.logout();
			controlerManager.logout(); // checks what to do on before logout for
		}
		if (slayerManager.getSocialPlayer() != null)
		    slayerManager.resetSocialGroup(true);
		// login
		running = false;
		friendsIgnores.sendFriendsMyStatus(false);
		if (currentFriendChat != null) {
			currentFriendChat.leaveChat(this, true);
		}
		if (clanManager != null)
		    clanManager.disconnect(this, false);
		if (guestClanManager != null)
		    guestClanManager.disconnect(this, true);
		if (familiar != null && !familiar.isFinished()) {
			familiar.dissmissFamiliar(true);
		} else if (pet != null) {
			pet.finish();
		}
		

		setFinished(true);
		session.setDecoder(-1);
		this.lastLoggedIn = System.currentTimeMillis();
		SerializableFilesManager.savePlayer(this);
		if (World.containsLobbyPlayer(username)) {
			World.removeLobbyPlayer(this);
		}
		World.updateEntityRegion(this);
		if (World.containsPlayer(username)) {
			World.removePlayer(this);
		}
		if (Settings.DEBUG) {
			Logger.log(this, "Finished Player: " + username + ", pass: " + password);
		}
	}
	
	public void afkTime() {
		CoresManager.slowExecutor.schedule(new Runnable() {
			public void run(){
				if (afkTimer < Utils.currentTimeMillis()){
					logout(false);
				}
				afkTime();
		}
	}, 1, TimeUnit.MINUTES);
}

	@Override
	public boolean restoreHitPoints() {
		boolean update = super.restoreHitPoints();
		if (update) {
			if (prayer.usingPrayer(0, 9))
				super.restoreHitPoints();
			if (resting || listening)
				super.restoreHitPoints();
			refreshHitPoints();
		}
		return update;
	}
	
	public boolean isListening() {
		return listening;
	}

	public void refreshHitPoints() {
		if (lendMessage != 0) {
			if (lendMessage == 1)
				getPackets()
						.sendGameMessage(
								"<col=FF0000>An item you lent out has been added back to your bank.");
			else if (lendMessage == 2)
				getPackets()
						.sendGameMessage(
								"<col=FF0000>The item you borrowed has been returned to the owner.");
			lendMessage = 0;
		}
		getPackets().sendConfigByFile(7198, getHitpoints());
	}

	@Override
	public void removeHitpoints(Hit hit) {
		super.removeHitpoints(hit);
		refreshHitPoints();
	}

	@Override
	public int getMaxHitpoints() {
		return skills.getLevel(Skills.HITPOINTS) * 10
				+ equipment.getEquipmentHpIncrease();
	}
	

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<String> getPasswordList() {
		return passwordList;
	}

	public ArrayList<String> getIPList() {
		return ipList;
	}

	public void setRights(int rights) {
		this.rights = rights;
	}
	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

	public int getRights() {
		return rights;
	}
	public int getGameMode() {
		return gameMode;
	}

	public int getMessageIcon() {
		return getRights() == 2 || getRights() == 1 ? getRights() : isSupporter() ? 14 : isForumModerator() ? 10 : isGraphicDesigner() ? 9 : isAngelicDonator() ? 15 : isDivineDonator() ? 16 : isSupremeDonator() ? 13 : isLegendaryDonator() ? 12 : isExtremeDonator() ? 8 : isDonator() ? 11 : getRights();
	}
	
    public VarsManager getVarsManager() {
	return varsManager;
    }

	public WorldPacketsEncoder getPackets() {
		return session.getWorldPackets();
	}

	public boolean hasStarted() {
		return started;
	}

	public boolean isRunning() {
		return running;
	}

	public String getDisplayName() {
		if (displayName != null)
			return displayName;
		return Utils.formatPlayerNameForDisplay(username);
	}

	public boolean hasDisplayName() {
		return displayName != null;
	}

	public Appearence getAppearence() {
		return appearence;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public int getTemporaryMoveType() {
		return temporaryMovementType;
	}

	public void setTemporaryMoveType(int temporaryMovementType) {
		this.temporaryMovementType = temporaryMovementType;
	}

	public LocalPlayerUpdate getLocalPlayerUpdate() {
		return localPlayerUpdate;
	}

	public LocalNPCUpdate getLocalNPCUpdate() {
		return localNPCUpdate;
	}

	public int getDisplayMode() {
		return displayMode;
	}

	public InterfaceManager getInterfaceManager() {
		return interfaceManager;
	}
	public Ectophial getEctophial() {
		return ectophial;
	}
	public ClueScrolls getClueScrolls() {
		return ClueScrolls;
	}

	public void setPacketsDecoderPing(long packetsDecoderPing) {
		this.packetsDecoderPing = packetsDecoderPing;
	}

	public long getPacketsDecoderPing() {
		return packetsDecoderPing;
	}

	public Session getSession() {
		return session;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getScreenHeight() {
		return screenHeight;
	}
	
	public void setListening(boolean listening) {
		this.listening = listening;
		sendRunButtonConfig();
	}

	public boolean clientHasLoadedMapRegion() {
		return clientLoadedMapRegion;
	}

	public void setClientHasLoadedMapRegion() {
		clientLoadedMapRegion = true;
	}

	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Skills getSkills() {
		return skills;
	}

	public byte getRunEnergy() {
		return runEnergy;
	}

	public void drainRunEnergy() {
			setRunEnergy(runEnergy - 1);
	}

	public void setRunEnergy(double runEnergy) {
		this.runEnergy = (byte) runEnergy;
		getPackets().sendRunEnergy();
	}

	public boolean isResting() {
		return resting;
	}

	public void setResting(boolean resting) {
		this.resting = resting;
		sendRunButtonConfig();
	}
	

	public ActionManager getActionManager() {
		return actionManager;
	}

	public void setCoordsEvent(CoordsEvent coordsEvent) {
		this.coordsEvent = coordsEvent;
	}

	public DialogueManager getDialogueManager() {
		return dialogueManager;
	}

	public static VoteChecker voteChecker = new VoteChecker("ragevote.db.8679617.hostedresource.com", "ragevote", "ragevote", "Blist9559!");


	public SpinsManager getSpinsManager() {
		return spinsManager;
	}

	public LoyaltyManager getLoyaltyManager() {
		return loyaltyManager;
	}

	public int getLoyaltyPoints() {
    	return Loyaltypoints;
	}

	public int getDonatorPoints() {
		return Donatorpoints;
	}
	public int getkilledByAdam() {
		return killedByAdam;
	}

	public int getForumPoints() {
		return Forumpoints;
	}
	public int getRewardPoints() {
		return Rewardpoints;
	}
	
	public void warningLog(Player player) {
		if (player.getRights() >= 2)
			return;
		if (player.isPker)
			return;
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(
					Settings.LOG_PATH + "items/warning.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
					+ " "
					+ Calendar.getInstance().getTimeZone().getDisplayName()
					+ "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) +" has a large amount of cash on their account!");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}
	
	public static void tradeLog(Player player, int i, int amount, Player target) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(
					Settings.LOG_PATH + "items/trades.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
					+ " "
					+ Calendar.getInstance().getTimeZone().getDisplayName()
					+ "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) +" traded "+amount+" of the ID "+i+" with "+target.getUsername()+"");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}
	
	public static void dropLog(Player player, int i) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(
					Settings.LOG_PATH + "items/drops.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
					+ " "
					+ Calendar.getInstance().getTimeZone().getDisplayName()
					+ "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) +" has dropped the item"+i+"");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}
	
	public void shopLog(Player player, int i, int a, boolean selling) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(
					Settings.LOG_PATH + "items/shop.txt", true));
			if (selling) {
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
					+ " "
					+ Calendar.getInstance().getTimeZone().getDisplayName()
					+ "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) +" has sold "+a+" of the item "+i+" to the store.");
			} else {
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
					+ " "
					+ Calendar.getInstance().getTimeZone().getDisplayName()
					+ "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) +" has bought "+a+" of the item "+i+" to the store.");
			}
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}
	
	
	
	public void deathLog(Player player, int i, int a, String name) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(
					Settings.LOG_PATH + "items/deaths.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
					+ " "
					+ Calendar.getInstance().getTimeZone().getDisplayName()
					+ "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) +" has lost "+a+" of the item "+name+"("+i+") when killed.");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}
	
	public void npcLog(Player player, int i, int a, String name, String npc, int n) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(
					Settings.LOG_PATH + "items/npcdrops.txt", true));
			bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
					+ " "
					+ Calendar.getInstance().getTimeZone().getDisplayName()
					+ "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) +" has recieved "+a+" of the item "+name+"("+i+") from the NPC "+npc+"("+n+").");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}
	
	





	public int getLividPoints() {
    	return lividpoints;
	}


	public void setLividPoints(int lividpoints) {
    	this.lividpoints = lividpoints;
	}

	public CombatDefinitions getCombatDefinitions() {
		return combatDefinitions;
	}


	@Override
	public double getMagePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getRangePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getMeleePrayerMultiplier() {
		return 0.6;
	}

	public void sendSoulSplit(final Hit hit, final Entity user) {
		final Player target = this;
		if (hit.getDamage() > 0)
			World.sendProjectile(user, this, 2263, 11, 11, 20, 5, 0, 0);
		user.heal(hit.getDamage() / 5);
		prayer.drainPrayer(hit.getDamage() / 5);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				setNextGraphics(new Graphics(2264));
				if (hit.getDamage() > 0)
					World.sendProjectile(target, user, 2263, 11, 11, 20, 5, 0, 0);
			}
		}, 0);
	}
	
	//Random Events
	
	public static void RandomEventTeleportPlayer(final Player player, final int x, final int y, final int z) {
		player.setNextAnimation(new Animation(2140));
		player.setNextForceTalk(new ForceTalk("ARGHHHHHHHHH!"));
		player.getControlerManager().startControler("RandomEvent");
		player.setInfiniteStopDelay();
		player.stopAll();
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.resetStopDelay();
				player.setNextWorldTile(new WorldTile(x, y, z));
			}
		}, 1);
	}
	public static void QuizEventTeleportPlayer(final Player player, final int x, final int y, final int z) {
		player.setNextAnimation(new Animation(2140));
		player.setNextForceTalk(new ForceTalk("ARGHHHHHHHHH!"));
		player.getControlerManager().startControler("QuizEvent");
		player.setInfiniteStopDelay();
		player.stopAll();
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.resetStopDelay();
				player.setNextWorldTile(new WorldTile(x, y, z));
			}
		}, 1);
	}
	

	public void randomevent(final Player p) {
		int random = 0;
		random = Misc.random(250);
				if (random == 1) {
					;
					//p.saveLoc(p.getX(), p.getY(), p.getPlane());
					//RandomEventTeleportPlayer(p, 2918, 4597, 0);
				} else if (random == 2) {
					;
					//final NPC evilchicken = findNPC(3375);
					//World.spawnNPC(3375, new WorldTile(p.getWorldTile()), -1, true);
					//evilchicken.setTarget(p);
				} else if (random == 3) {
					SandwichLady.getInstance().start(p);
				}
    }
	public void randomnest(final Player p) {
		int random = 0;
		random = Misc.random(250);
				if (random == 1) {
					;
					//p.saveLoc(p.getX(), p.getY(), p.getPlane());
					//RandomEventTeleportPlayer(p, 2918, 4597, 0);
				} else if (random == 2) {
					;
					//final NPC evilchicken = findNPC(3375);
					//World.spawnNPC(3375, new WorldTile(p.getWorldTile()), -1, true);
					//evilchicken.setTarget(p);
				} else if (random == 3) {
					getInventory().addItem(5070, 1);
					sendMessage("A nest randomly falls out of the tree, and you pick it up.");
				}
    }
	public static NPC findNPC(int id) {
		for (NPC npc : World.getNPCs()) {
			if (npc == null || npc.getId() != id)
				continue;
			return npc;
		}
		return null;
	}

    ;
    
	public void addStopDelay(long delay) {
		stopDelay = Utils.currentTimeMillis() + (delay * 600);
	}
	
	public DwarfCannon getDwarfCannon() {
		return DwarfCannon;
	}
	
    /**
	 * Dwarf Cannon
	 */
	public Object getDwarfCannon;
	
	public boolean hasLoadedCannon = false;
	
	public boolean isShooting = false;
	
	public boolean hasSetupCannon = false;
	
	public boolean hasSetupGoldCannon = false;
	
	public boolean hasSetupRoyalCannon = false;
	
	public void setInfiniteStopDelay() {
		stopDelay = Long.MAX_VALUE;
	}

	public void resetStopDelay() {
		stopDelay = 0;
	}
	
	
	
	/***Welcoming Login Interface***/
	public void welcomeInterface() {
		getInterfaceManager().sendInterface(1225);
		getPackets().sendIComponentText(1225, 8, "<shad=0>Welcome to" + Settings.SERVER_NAME);
		getPackets().sendIComponentText(1225, 5, 
				"Welcome! We are you have decided to join our server!");
		getPackets().sendIComponentText(1225, 21, 
				"We would like to notify you that if your gameplay screen is black upon login, just give it some time to load the cache entirely.");
		getPackets().sendIComponentText(1225, 22, 
				"If this persists, press the `/~ button on your keyboard and in the consol type 'displayfps'. Your screen won't be black when Cache is at 100%.");
	}


    

	@Override
	public void handleIngoingHit(final Hit hit) {
		if (hit.getLook() != HitLook.MELEE_DAMAGE
				&& hit.getLook() != HitLook.RANGE_DAMAGE
				&& hit.getLook() != HitLook.MAGIC_DAMAGE)
			return;
		if(invulnerable) {
			hit.setDamage(0);
			return;
		}
		if (auraManager.usingPenance()) {
			int amount = (int) (hit.getDamage() * 0.2);
			if (amount > 0)
				prayer.restorePrayer(amount);
		}
		Entity source = hit.getSource();
		if (source == null)
			return;
		if (polDelay > Utils.currentTimeMillis())
			hit.setDamage((int) (hit.getDamage() * 0.5));
		if (prayer.hasPrayersOn() && hit.getDamage() != 0) {
			if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
				if (prayer.usingPrayer(0, 17))
					hit.setDamage((int) (hit.getDamage() * source
							.getMagePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 7)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getMagePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2228));
						setNextAnimation(new Animation(12573));
					}
				}
			} else if (hit.getLook() == HitLook.RANGE_DAMAGE) {
				if (prayer.usingPrayer(0, 18))
					hit.setDamage((int) (hit.getDamage() * source
							.getRangePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 8)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getRangePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2229));
						setNextAnimation(new Animation(12573));
					}
				}
			} else if (hit.getLook() == HitLook.MELEE_DAMAGE) {
				if (prayer.usingPrayer(0, 19))
					hit.setDamage((int) (hit.getDamage() * source
							.getMeleePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 9)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getMeleePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2230));
						setNextAnimation(new Animation(12573));
					}
				}
			}
		}
		if (hit.getDamage() >= 200) {
			if (hit.getLook() == HitLook.MELEE_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_MELEE_BONUS]
								/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			} else if (hit.getLook() == HitLook.RANGE_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_RANGE_BONUS]
								/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			} else if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_MAGE_BONUS]
								/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			}
		}
		int shieldId = equipment.getShieldId();
		if (shieldId == 13742) { // elsyian
			if (Utils.getRandom(100) <= 70)
				hit.setDamage((int) (hit.getDamage() * 0.75));
		} else if (shieldId == 13740) { // divine
			int drain = (int) (Math.ceil(hit.getDamage() * 0.3) / 2);
			if (prayer.getPrayerpoints() >= drain) {
				hit.setDamage((int) (hit.getDamage() * 0.70));
				prayer.drainPrayer(drain);
			}
		} else if (shieldId == 29957) { // scarlet
			int drain = (int) (Math.ceil(hit.getDamage() * 0.3) / 2);
			if (prayer.getPrayerpoints() >= drain) {
				hit.setDamage((int) (hit.getDamage() * 0.70));
				prayer.drainPrayer(drain);
			}
			source.applyHit(new Hit(this, (int) (hit.getDamage() * 0.4),
					HitLook.REFLECTED_DAMAGE));
			heal((int) (hit.getDamage() * 0.25), 1);
		}
		if (castedVeng && hit.getDamage() >= 4) {
			castedVeng = false;
			setNextForceTalk(new ForceTalk("Taste vengeance!"));
			source.applyHit(new Hit(this, (int) (hit.getDamage() * 0.75),
					HitLook.REGULAR_DAMAGE));
		}
		if (source instanceof Player) {
			final Player p2 = (Player) source;
			if (p2.prayer.hasPrayersOn()) {
				if (p2.prayer.usingPrayer(0, 24)) { // smite
					int drain = hit.getDamage() / 4;
					if (drain > 0)
						prayer.drainPrayer(drain);
				} else {
					if (hit.getDamage() == 0)
						return;
					if (!p2.prayer.isBoostedLeech()) {
						if (hit.getLook() == HitLook.MELEE_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 19)) {
								if (Utils.getRandom(4) == 0) {
									p2.prayer.increaseTurmoilBonus(this);
									p2.prayer.setBoostedLeech(true);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 1)) { // sap att
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(0)) {
										p2.getPackets()
										.sendGameMessage(
												"Your opponent has been weakened so much that your sap curse has no effect.",
												true);
									} else {
										p2.prayer.increaseLeechBonus(0);
										p2.getPackets()
										.sendGameMessage(
												"Your curse drains Attack from the enemy, boosting your Attack.",
												true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2214));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2215, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2216));
										}
									}, 1);
									return;
								}
							} else {
								if (p2.prayer.usingPrayer(1, 10)) {
									if (Utils.getRandom(7) == 0) {
										if (p2.prayer.reachedMax(3)) {
											p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
										} else {
											p2.prayer.increaseLeechBonus(3);
											p2.getPackets()
											.sendGameMessage(
													"Your curse drains Attack from the enemy, boosting your Attack.",
													true);
										}
										p2.setNextAnimation(new Animation(12575));
										p2.prayer.setBoostedLeech(true);
										World.sendProjectile(p2, this, 2231,
												35, 35, 20, 5, 0, 0);
										WorldTasksManager.schedule(
												new WorldTask() {
													@Override
													public void run() {
														setNextGraphics(new Graphics(
																2232));
													}
												}, 1);
										return;
									}
								}
								if (p2.prayer.usingPrayer(1, 14)) {
									if (Utils.getRandom(7) == 0) {
										if (p2.prayer.reachedMax(7)) {
											p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
										} else {
											p2.prayer.increaseLeechBonus(7);
											p2.getPackets()
											.sendGameMessage(
													"Your curse drains Strength from the enemy, boosting your Strength.",
													true);
										}
										p2.setNextAnimation(new Animation(12575));
										p2.prayer.setBoostedLeech(true);
										World.sendProjectile(p2, this, 2248,
												35, 35, 20, 5, 0, 0);
										WorldTasksManager.schedule(
												new WorldTask() {
													@Override
													public void run() {
														setNextGraphics(new Graphics(
																2250));
													}
												}, 1);
										return;
									}
								}

							}
						}
						if (hit.getLook() == HitLook.RANGE_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 2)) { // sap range
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(1)) {
										p2.getPackets()
										.sendGameMessage(
												"Your opponent has been weakened so much that your sap curse has no effect.",
												true);
									} else {
										p2.prayer.increaseLeechBonus(1);
										p2.getPackets()
										.sendGameMessage(
												"Your curse drains Range from the enemy, boosting your Range.",
												true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2217));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2218, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2219));
										}
									}, 1);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 11)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.prayer.reachedMax(4)) {
										p2.getPackets()
										.sendGameMessage(
												"Your opponent has been weakened so much that your leech curse has no effect.",
												true);
									} else {
										p2.prayer.increaseLeechBonus(4);
										p2.getPackets()
										.sendGameMessage(
												"Your curse drains Range from the enemy, boosting your Range.",
												true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2236, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2238));
										}
									});
									return;
								}
							}
						}
						if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 3)) { // sap mage
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(2)) {
										p2.getPackets()
										.sendGameMessage(
												"Your opponent has been weakened so much that your sap curse has no effect.",
												true);
									} else {
										p2.prayer.increaseLeechBonus(2);
										p2.getPackets()
										.sendGameMessage(
												"Your curse drains Magic from the enemy, boosting your Magic.",
												true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2220));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2221, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2222));
										}
									}, 1);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 12)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.prayer.reachedMax(5)) {
										p2.getPackets()
										.sendGameMessage(
												"Your opponent has been weakened so much that your leech curse has no effect.",
												true);
									} else {
										p2.prayer.increaseLeechBonus(5);
										p2.getPackets()
										.sendGameMessage(
												"Your curse drains Magic from the enemy, boosting your Magic.",
												true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2240, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2242));
										}
									}, 1);
									return;
								}
							}
						}

						// overall

						if (p2.prayer.usingPrayer(1, 13)) { // leech defence
							if (Utils.getRandom(10) == 0) {
								if (p2.prayer.reachedMax(6)) {
									p2.getPackets()
									.sendGameMessage(
											"Your opponent has been weakened so much that your leech curse has no effect.",
											true);
								} else {
									p2.prayer.increaseLeechBonus(6);
									p2.getPackets()
									.sendGameMessage(
											"Your curse drains Defence from the enemy, boosting your Defence.",
											true);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2244, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2246));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 15)) {
							if (Utils.getRandom(10) == 0) {
								if (getRunEnergy() <= 0) {
									p2.getPackets()
									.sendGameMessage(
											"Your opponent has been weakened so much that your leech curse has no effect.",
											true);
								} else {
									p2.setRunEnergy(p2.getRunEnergy() > 90 ? 100
											: p2.getRunEnergy() + 10);
									setRunEnergy(p2.getRunEnergy() > 10 ? getRunEnergy() - 10
											: 0);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2256, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2258));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 16)) {
							if (Utils.getRandom(10) == 0) {
								if (combatDefinitions
										.getSpecialAttackPercentage() <= 0) {
									p2.getPackets()
									.sendGameMessage(
											"Your opponent has been weakened so much that your leech curse has no effect.",
											true);
								} else {
									p2.combatDefinitions.restoreSpecialAttack();
									combatDefinitions
									.desecreaseSpecialAttack(10);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2252, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2254));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 4)) { // sap spec
							if (Utils.getRandom(10) == 0) {
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2223));
								p2.prayer.setBoostedLeech(true);
								if (combatDefinitions
										.getSpecialAttackPercentage() <= 0) {
									p2.getPackets()
									.sendGameMessage(
											"Your opponent has been weakened so much that your sap curse has no effect.",
											true);
								} else {
									combatDefinitions
									.desecreaseSpecialAttack(10);
								}
								World.sendProjectile(p2, this, 2224, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2225));
									}
								}, 1);
								return;
							}
						}
					}
				}
			}
		} else {
			NPC n = (NPC) source;
			if (n.getId() == 13448)
				sendSoulSplit(hit, n);
		}
	}

	@Override
	public void sendDeath(final Entity source) {
		if (prayer.hasPrayersOn()
				&& getTemporaryAttributtes().get("startedDuel") != Boolean.TRUE) {
			if (prayer.usingPrayer(0, 22)) {
				setNextGraphics(new Graphics(437));
				final Player target = this;
				if (isAtMultiArea()) {
					for (int regionId : getMapRegionsIds()) {
						List<Integer> playersIndexes = World
								.getRegion(regionId).getPlayerIndexes();
						if (playersIndexes != null) {
							for (int playerIndex : playersIndexes) {
								Player player = World.getPlayers().get(
										playerIndex);
								if (player == null
										|| !player.hasStarted()
										|| player.isDead()
										|| player.hasFinished()
										|| !player.withinDistance(this, 1)
										|| !player.isCanPvp()
										|| !target.getControlerManager()
										.canHit(player))
									continue;
								player.applyHit(new Hit(
										target,
										Utils.getRandom((int) (skills
												.getLevelForXp(Skills.PRAYER) * 2.5)),
												HitLook.REGULAR_DAMAGE));
							}
						}
						List<Integer> npcsIndexes = World.getRegion(regionId)
								.getNPCsIndexes();
						if (npcsIndexes != null) {
							for (int npcIndex : npcsIndexes) {
								NPC npc = World.getNPCs().get(npcIndex);
								if (npc == null
										|| npc.isDead()
										|| npc.hasFinished()
										|| !npc.withinDistance(this, 1)
										|| !npc.getDefinitions()
										.hasAttackOption()
										|| !target.getControlerManager()
										.canHit(npc))
									continue;
								npc.applyHit(new Hit(
										target,
										Utils.getRandom((int) (skills
												.getLevelForXp(Skills.PRAYER) * 2.5)),
												HitLook.REGULAR_DAMAGE));
							}
						}
					}
				} else {
					if (source != null && source != this && !source.isDead()
							&& !source.hasFinished()
							&& source.withinDistance(this, 1))
						source.applyHit(new Hit(target, Utils
								.getRandom((int) (skills
										.getLevelForXp(Skills.PRAYER) * 2.5)),
										HitLook.REGULAR_DAMAGE));
				}
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1, target.getY(),
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1, target.getY(),
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX(), target.getY() - 1,
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX(), target.getY() + 1,
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1,
										target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1,
										target.getY() + 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1,
										target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1,
										target.getY() + 1, target.getPlane()));
					}
				});
			} else if (prayer.usingPrayer(1, 17)) {
				World.sendProjectile(this, new WorldTile(getX() + 2,
						getY() + 2, getPlane()), 2260, 24, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() + 2, getY(),
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() + 2,
						getY() - 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);

				World.sendProjectile(this, new WorldTile(getX() - 2,
						getY() + 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() - 2, getY(),
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() - 2,
						getY() - 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);

				World.sendProjectile(this, new WorldTile(getX(), getY() + 2,
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX(), getY() - 2,
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				final Player target = this;
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						setNextGraphics(new Graphics(2259));

						if (isAtMultiArea()) {
							for (int regionId : getMapRegionsIds()) {
								List<Integer> playersIndexes = World.getRegion(
										regionId).getPlayerIndexes();
								if (playersIndexes != null) {
									for (int playerIndex : playersIndexes) {
										Player player = World.getPlayers().get(
												playerIndex);
										if (player == null
												|| !player.hasStarted()
												|| player.isDead()
												|| player.hasFinished()
												|| !player.isCanPvp()
												|| !player.withinDistance(
														target, 2)
														|| !target
														.getControlerManager()
														.canHit(player))
											continue;
										player.applyHit(new Hit(
												target,
												Utils.getRandom((skills
														.getLevelForXp(Skills.PRAYER) * 3)),
														HitLook.REGULAR_DAMAGE));
									}
								}
								List<Integer> npcsIndexes = World.getRegion(
										regionId).getNPCsIndexes();
								if (npcsIndexes != null) {
									for (int npcIndex : npcsIndexes) {
										NPC npc = World.getNPCs().get(npcIndex);
										if (npc == null
												|| npc.isDead()
												|| npc.hasFinished()
												|| !npc.withinDistance(target,
														2)
														|| !npc.getDefinitions()
														.hasAttackOption()
														|| !target
														.getControlerManager()
														.canHit(npc))
											continue;
										npc.applyHit(new Hit(
												target,
												Utils.getRandom((skills
														.getLevelForXp(Skills.PRAYER) * 3)),
														HitLook.REGULAR_DAMAGE));
									}
								}
							}
						} else {
							if (source != null && source != target
									&& !source.isDead()
									&& !source.hasFinished()
									&& source.withinDistance(target, 2))
								source.applyHit(new Hit(
										target,
										Utils.getRandom((skills
												.getLevelForXp(Skills.PRAYER) * 3)),
												HitLook.REGULAR_DAMAGE));
						}

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 2, getY() + 2,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 2, getY(), getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 2, getY() - 2,
										getPlane()));

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 2, getY() + 2,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 2, getY(), getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 2, getY() - 2,
										getPlane()));

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX(), getY() + 2, getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX(), getY() - 2, getPlane()));

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 1, getY() + 1,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 1, getY() - 1,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 1, getY() + 1,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 1, getY() - 1,
										getPlane()));
					}
				});
			}
		}
		setNextAnimation(new Animation(-1));
		if (!controlerManager.sendDeath())
		    return;
		lock(7);
		stopAll();
		if (familiar != null)
		    familiar.sendDeath(this);
		final WorldTile deathTile = new WorldTile(this);
		WorldTasksManager.schedule(new WorldTask() {
		    int loop;

		    @Override
		    public void run() {
			if (loop == 0) {
			    setNextAnimation(new Animation(836));
			} else if (loop == 1) {
			    getPackets().sendGameMessage("Oh dear, you have died.");
			    if (getAssassinsManager().getGameMode() == 4) {
			    getAssassinsManager().resetTask();
			    sm("You have failed your Assassin's contract, go try another.");
			    }
			} else if (loop == 3) {
			    controlerManager.startControler("DeathEvent", deathTile, hasSkull());
			} else if (loop == 4) {
				killstreak = 0;
			    getPackets().sendMusicEffect(90);
			    stop();
			}
			loop++;
		    }
		}, 0, 1);
	    }

	@SuppressWarnings("unused")
	private boolean hasdied;

/*	public void sendItemsOnDeath(Player killer) {
		if (rights == 2)
			return;
		if(controlerManager.getControler() instanceof QueenBlackDragonController)
			return;
		charges.die();
		auraManager.removeAura();
		CopyOnWriteArrayList<Item> containedItems = new CopyOnWriteArrayList<Item>();
		for (int i = 0; i < 14; i++) {
			if (equipment.getItem(i) != null
					&& equipment.getItem(i).getId() != -1
					&& equipment.getItem(i).getAmount() != -1)
				containedItems.add(new Item(equipment.getItem(i).getId(),
						equipment.getItem(i).getAmount()));
		}
		for (int i = 0; i < 28; i++) {
			if (inventory.getItem(i) != null
					&& inventory.getItem(i).getId() != -1
					&& inventory.getItem(i).getAmount() != -1)
				containedItems.add(new Item(getInventory().getItem(i).getId(),
						getInventory().getItem(i).getAmount()));
		}
		if (containedItems.isEmpty())
			return;
		int keptAmount = 0;
		if(!(controlerManager.getControler() instanceof CorpBeastControler)
				&& !(controlerManager.getControler() instanceof CrucibleControler)) {
			keptAmount = hasSkull() ? 0 : 3;
			if (prayer.usingPrayer(0, 10) || prayer.usingPrayer(1, 0))
				keptAmount++;
		}
		if (donator && Utils.random(2) == 0)
			keptAmount += 1;
		CopyOnWriteArrayList<Item> keptItems = new CopyOnWriteArrayList<Item>();
		Item lastItem = new Item(1, 1);
		for (int i = 0; i < keptAmount; i++) {
			for (Item item : containedItems) {
				int price = item.getDefinitions().getValue();
				if (price >= lastItem.getDefinitions().getValue()) {
					lastItem = item;
				}
			}
			keptItems.add(lastItem);
			containedItems.remove(lastItem);
			lastItem = new Item(1, 1);
		}
		inventory.reset();
		equipment.reset();
		for (Item item : keptItems) {
			getInventory().addItem(item);
		}
		for (Item item : containedItems) {
			if (!getUsername().equalsIgnoreCase("richie")) {
			World.addGroundItem(item, getLastWorldTile(), killer == null ? this : killer, false, 180,
					true, true);
			}
		}
	}*/
	
	
	
	private Player sendNPCItemsOnDeath(NPC npc) {
		ArrayList<Item> containedItems = new ArrayList<Item>();
		for (int i = 0; i < 14; i++)
			if (equipment.getItem(i) != null)
				containedItems.add(new Item(equipment.getItem(i).getId(), equipment.getItem(i).getAmount()));

		for (int i = 0; i < 28; i++)
			if (getInventory().getItem(i) != null)
				containedItems.add(new Item(getInventory().getItem(i).getId(), getInventory().getItem(i).getAmount()));

		int keptAmount = 3;
		if (hasSkull())
			keptAmount = 0;
		if (prayer.usingPrayer(0, 10) || prayer.usingPrayer(1, 0))
			keptAmount++;

		ArrayList<Item> keptItems = new ArrayList<Item>(keptAmount);
		Item lastItem = new Item(-1, 0);
		for (int i = 0; i < keptAmount; i++) {
			for (Item item : containedItems) {
				int price = item.getDefinitions().getValue();
				int lastPrice = lastItem.getDefinitions().getValue();

				if (lastItem.getId() == -1) {
					lastPrice = 0;
				}

				if (price >= lastPrice)
					lastItem = item;
			}
			if (lastItem.getId() != -1) {
				keptItems.add(lastItem);
				containedItems.remove(lastItem);
				lastItem = new Item(-1, 0);
			}
		}

		inventory.reset();
		equipment.reset();

		if (keptItems.size() > 0) {
			for (Item item : keptItems) {
				inventory.addItem(item);
			}
		}

		if (containedItems.size() > 0) {

			for (Item item : containedItems) {
				World.addGroundItem(item, getLastWorldTile(), this, true, 180, true);
			}

			World.spawnObject(new WorldObject(62414, 10, 1, getLastWorldTile()), true);
			hintIconsManager.addHintIcon(getLastWorldTile(), 100, 0, 0, -1, false);
		}

		return this;
	}
	

	public void increaseKillCount(Player killed) {
		if (killed.getSession().getIP().equals(getSession().getIP()))
			return;
		killed.deathCount++;
		killstreak+= 1;
		if (killstreak > highestKillstreak)
			highestKillstreak = killstreak;
		KillStreakRank.checkRank(this);
		getInventory().addItem(12852, 1);
		getPackets().sendGameMessage(
				"<col=ff0000>Your kill streak has increased, you now are on a streak of "+killstreak+" kills.");
		if (killstreak >= 5) {
		getPackets().sendGameMessage(
				"<col=ff0000>You have earned an upgrade token for your high killstreak.");
		getInventory().addItem(29932, 1);
		}
		if (killstreak >= 10) {
		getPackets().sendGameMessage(
				"<col=ff0000>You have reached an INSANE killstreak! Claim your reward via ::capes");
		}
		for (Player players : World.getPlayers()) {
			if (players == null)
				continue;
				if (killstreak == 2) {
			players.getPackets().sendGameMessage(getDisplayName() + " Is On A <col=ff0000>" + killstreak + "</col> killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 3) {
			players.getPackets().sendGameMessage(getDisplayName() + " Is On A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 4) {
			players.getPackets().sendGameMessage(getDisplayName() + " Is On A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 5) {
			players.getPackets().sendGameMessage(getDisplayName() + " Is On A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
		if (killstreak == 6) {
			players.getPackets().sendGameMessage(getDisplayName() + " Is On Fire With A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 7) {
			players.getPackets().sendGameMessage(getDisplayName() + " Is On Fire With A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 8) {
			players.getPackets().sendGameMessage(getDisplayName() + " Is On Fire With A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 9) {
			players.getPackets().sendGameMessage(getDisplayName() + " Is On Fire With A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 10 && highestKillstreak > killstreak) {
			players.getPackets().sendGameMessage(getDisplayName() + " Is On Fire With A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 10 && highestKillstreak < killstreak) {
			players.getPackets().sendGameMessage(getDisplayName() + " Is On Fire With A <col=ff0000>" + killstreak + "</col> Killstreak. Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>10</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
		if (killstreak == 11) {
			players.getPackets().sendGameMessage("Can Anybody Stop " + getDisplayName() + " With A <col=ff0000>" + killstreak + "</col> Killstreak?! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 12) {
			players.getPackets().sendGameMessage("Can Anybody Stop " + getDisplayName() + " With A <col=ff0000>" + killstreak + "</col> Killstreak?! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 13) {
			players.getPackets().sendGameMessage("Can Anybody Stop " + getDisplayName() + " With A <col=ff0000>" + killstreak + "</col> Killstreak?! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 14) {
			players.getPackets().sendGameMessage("Can Anybody Stop " + getDisplayName() + " With A <col=ff0000>" + killstreak + "</col> Killstreak?! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 15) {
			players.getPackets().sendGameMessage("Can Anybody Stop " + getDisplayName() + " With A <col=ff0000>" + killstreak + "</col> Killstreak?! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
		if (killstreak == 16) {
			players.getPackets().sendGameMessage("Bow Down To " + getDisplayName() + " Because They're On A <col=ff0000>" + killstreak + "</col> Killstreak! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 17) {
			players.getPackets().sendGameMessage("Bow Down To " + getDisplayName() + " Because They're On A <col=ff0000>" + killstreak + "</col> Killstreak! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 18) {
			players.getPackets().sendGameMessage("Bow Down To " + getDisplayName() + " Because They're On A <col=ff0000>" + killstreak + "</col> Killstreak! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 19) {
			players.getPackets().sendGameMessage("Bow Down To " + getDisplayName() + " Because They're On A <col=ff0000>" + killstreak + "</col> Killstreak! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
		if (killstreak >= 20) {
			players.getPackets().sendGameMessage("All Hail " + getDisplayName() + " With A <col=ff0000>" + killstreak + "</col> Killstreak! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
		if (killstreak == 20 && highestKillstreak < killstreak) {
			players.getPackets().sendGameMessage(getDisplayName() + " Has Just Hit Their <col=ff0000>20th</col> Killstreak - Go Claim Your Award On The Forums! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>20</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
		if (killstreak == 50 && highestKillstreak < killstreak) {
			players.getPackets().sendGameMessage(getDisplayName() + " Has Just Hit Their <col=ff0000>50th</col> Killstreak - Go Claim Your Award On The Forums! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>50</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
		if (killstreak == 30 && highestKillstreak < killstreak) {
			players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>30</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 40 && highestKillstreak < killstreak) {
			players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>40</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 60 && highestKillstreak < killstreak) {
			players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>60</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 70 && highestKillstreak < killstreak) {
			players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>70</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 80 && highestKillstreak < killstreak) {
			players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>80</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
			if (killstreak == 90 && highestKillstreak < killstreak) {
			players.getPackets().sendGameMessage(getDisplayName() + " Has Just Recieved Their <col=ff0000>90</col> Killstreak Cape! Their Highest Is <col=ff0000>" + highestKillstreak + "</col>");
			}
		}
		PkRank.checkRank(killed);
		killCount++;
		getPackets().sendGameMessage(
				"<col=ff0000>You have killed " + killed.getDisplayName()
				+ ", you have now " + killCount + " kills.");
		PkRank.checkRank(this);
	}

	public void increaseKillCountSafe(Player killed) {
		PkRank.checkRank(killed);
		if (killed.getSession().getIP().equals(getSession().getIP()))
			return;
		killCount++;
		getPackets().sendGameMessage(
				"<col=ff0000>You have killed " + killed.getDisplayName()
				+ ", you have now " + killCount + " kills.");
		PkRank.checkRank(this);
	}

	public void sendRandomJail(Player p) {
		p.resetWalkSteps();
		switch (Utils.getRandom(3)) {
		case 0:
			p.setNextWorldTile(new WorldTile(3111, 3516, 0));
			break;
		case 1:
			p.setNextWorldTile(new WorldTile(3108, 3515, 0));
			break;
		case 2:
			p.setNextWorldTile(new WorldTile(3110, 3513, 0));
			break;
		case 3:
			p.setNextWorldTile(new WorldTile(3108, 3511, 0));
			break;
		}
	}

	@Override
	public int getSize() {
		return appearence.getSize();
	}

	public boolean isCanPvp() {
		return canPvp;
	}

	public void setCanPvp(boolean canPvp) {
		this.canPvp = canPvp;
		appearence.generateAppearenceData();
		getPackets().sendPlayerOption(canPvp ? "Attack" : "null", 1, true);
		getPackets().sendPlayerUnderNPCPriority(canPvp);
	}

	public Prayer getPrayer() {
		return prayer;
	}

	public long getLockDelay() {
		return lockDelay;
	}

	public boolean isLocked() {
		return lockDelay >= Utils.currentTimeMillis();
	}

	public void lock() {
		lockDelay = Long.MAX_VALUE;
	}

	public void lock(long time) {
		lockDelay = Utils.currentTimeMillis() + (time * 600);
	}

	public void unlock() {
		lockDelay = 0;
	}

	public void useStairs(int emoteId, final WorldTile dest, int useDelay,
			int totalDelay) {
		useStairs(emoteId, dest, useDelay, totalDelay, null);
	}

	public void useStairs(int emoteId, final WorldTile dest, int useDelay,
			int totalDelay, final String message) {
		stopAll();
		lock(totalDelay);
		if (emoteId != -1)
			setNextAnimation(new Animation(emoteId));
		if (useDelay == 0)
			setNextWorldTile(dest);
		else {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (isDead())
						return;
					setNextWorldTile(dest);
					if (message != null)
						getPackets().sendGameMessage(message);
				}
			}, useDelay - 1);
		}
	}

	public Bank getBank() {
		return bank;
	}

	public ControlerManager getControlerManager() {
		return controlerManager;
	}

	public void switchMouseButtons() {
		mouseButtons = !mouseButtons;
		refreshMouseButtons();
	}

	public void switchAllowChatEffects() {
		allowChatEffects = !allowChatEffects;
		refreshAllowChatEffects();
	}

	public void refreshAllowChatEffects() {
		getPackets().sendConfig(171, allowChatEffects ? 0 : 1);
	}

	public void refreshMouseButtons() {
		getPackets().sendConfig(170, mouseButtons ? 0 : 1);
	}

	public void refreshPrivateChatSetup() {
		getPackets().sendConfig(287, privateChatSetup);
	}

    public void refreshOtherChatsSetup() {
	getVarsManager().setVarBit(9188, friendChatSetup);
	getVarsManager().setVarBit(3612, clanChatSetup);
	getVarsManager().forceSendVarBit(9191, guestChatSetup);
    }

	public void setPrivateChatSetup(int privateChatSetup) {
		this.privateChatSetup = privateChatSetup;
	}
	

	public void setFriendChatSetup(int friendChatSetup) {
		this.friendChatSetup = friendChatSetup;
	}

	public int getPrivateChatSetup() {
		return privateChatSetup;
	}

	public boolean isForceNextMapLoadRefresh() {
		return forceNextMapLoadRefresh;
	}

	public void setForceNextMapLoadRefresh(boolean forceNextMapLoadRefresh) {
		this.forceNextMapLoadRefresh = forceNextMapLoadRefresh;
	}

	public FriendsIgnores getFriendsIgnores() {
		return friendsIgnores;
	}

	/*
	 * do not use this, only used by pm
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public void out(String text) {
		getPackets().sendGameMessage(text);
	}

	public void out(String text, int delay) {
		out(text);
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void addPotDelay(long time) {
		potDelay = time + Utils.currentTimeMillis();
	}

	public long getPotDelay() {
		return potDelay;
	}

	public void addFoodDelay(long time) {
		foodDelay = time + Utils.currentTimeMillis();
	}

	public long getFoodDelay() {
		return foodDelay;
	}

	public long getBoneDelay() {
		return boneDelay;
	}
	
	public long getAshDelay() {
		return ashDelay;
	}

	public void addBoneDelay(long time) {
		boneDelay = time + Utils.currentTimeMillis();
	}
	
	public void addAshDelay(long time) {
		ashDelay = time + Utils.currentTimeMillis();
	}

	public void addPoisonImmune(long time) {
		poisonImmune = time + Utils.currentTimeMillis();
		getPoison().reset();
	}

	public long getPoisonImmune() {
		return poisonImmune;
	}

	public void addFireImmune(long time) {
		fireImmune = time + Utils.currentTimeMillis();
	}
	
	public void addSuperFireImmune(long time) {
		superFireImmune = time + Utils.currentTimeMillis();
	}
	

	public long getFireImmune() {
		return fireImmune;
	}
	
	public long getSuperFireImmune() {
		return superFireImmune;
	}

	@Override
	public void heal(int ammount, int extra) {
		super.heal(ammount, extra);
		refreshHitPoints();
	}

	public MusicsManager getMusicsManager() {
		return musicsManager;
	}

	public HintIconsManager getHintIconsManager() {
		return hintIconsManager;
	}

	public boolean isCastVeng() {
		return castedVeng;
	}

	public void setCastVeng(boolean castVeng) {
		this.castedVeng = castVeng;
	}

	public int getKillCount() {
		return killCount;
	}

	public int getBarrowsKillCount() {
		return barrowsKillCount;
	}

	public int setBarrowsKillCount(int barrowsKillCount) {
		return this.barrowsKillCount = barrowsKillCount;
	}

	public int setKillCount(int killCount) {
		return this.killCount = killCount;
	}

	public int getDeathCount() {
		return deathCount;
	}

	public int setDeathCount(int deathCount) {
		return this.deathCount = deathCount;
	}

	public void setCloseInterfacesEvent(Runnable closeInterfacesEvent) {
		this.closeInterfacesEvent = closeInterfacesEvent;
	}

	public void setSpins(int spins) {
		this.spins = spins;
	}
	
	public int getSpins() {
		return spins;
	}

	//slayer masters

	public void setSlayerTaskAmount(int amount) {
        this.slayerTaskAmount = amount;
    }
    public int getSlayerTaskAmount() {
        return slayerTaskAmount;
    }
   /* public void setSlayerPoints(int points) {
        this.slayerPoints = points;
    }
    public int getSlayerPoints() {
        return slayerPoints;
    }*/

    public int master;

	public int money;

	public boolean isBurying;


    public int getSlayerMaster() {
        return master;
    }
    public void setSlayerMaster(int masta) {
        this.master = masta;
    }

	public boolean isTalkedWithKuradal() {
		return talkedWithKuradal;
	}

	public void increaseKillCount1(Player killed) {
		killed.deathCount++;
		PkRank.checkRank(killed);
		if (killed.getSession().getIP().equals(getSession().getIP()))
			return;
		getInventory().addItem(12852, 15);
		killCount++;
		getPackets().sendGameMessage("<col=ff0000>You have slayed " + killed.getDisplayName() + ", you have now " + killCount + " kills.");
		PkRank.checkRank(this);
	}

	public void setTalkedWithKuradal() {
		talkedWithKuradal = true;
	}

	public void falseWithKuradal() {
		talkedWithSpria = false;
	}

	public boolean isTalkedWithSpria() {
		return talkedWithSpria;
	}

	public void setTalkedWithSpria() {
		talkedWithSpria = true;
	}

	public void falseWithSpria() {
		talkedWithSpria = false;
	}

	public boolean isTalkedWithMazchna() {
		return talkedWithMazchna;
	}

	public void setTalkedWithMazchna() {
		talkedWithMazchna = true;
	}

	public void falseWithMazchna() {
		talkedWithMazchna = false;
	}

	//end of slayer masters



	public void setLoyaltyPoints(int Loyaltypoints) {
		this.Loyaltypoints = Loyaltypoints;
	}

	public void setDonatorPoints(int Donatorpoints) {
		this.Donatorpoints = Donatorpoints;
	}
	public void setkilledByAdam(int killedByAdam) {
		this.killedByAdam = killedByAdam;
	}
	public void setForumPoints(int Forumpoints) {
		this.Forumpoints = Forumpoints;
	}

	public void setRewardPoints(int Rewardpoints) {
		this.Rewardpoints = Rewardpoints;
	}

	public long getMuted() {
		return muted;
	}

	public void setMuted(long muted) {
		this.muted = muted;
	}

	public long getJailed() {
		return jailed;
	}

	 public Item getBox() {
		 	Item[] box = items.getItems();
		 	return box[Rewards];
		 	}

	public void setJailed(long jailed) {
		this.jailed = jailed;
	}

	public boolean isPermBanned() {
		return permBanned;
	}

	public void setPermBanned(boolean permBanned) {
		this.permBanned = permBanned;
	}
	
	public void setInDung(boolean inDung) {
		this.inDung = inDung;
	}
	
	public boolean isInDung() {
		return inDung;
	}

	public long getBanned() {
		return banned;
	}

	public void setBanned(long banned) {
		this.banned = banned;
	}

	public ChargesManager getCharges() {
		return charges;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean[] getKilledBarrowBrothers() {
		return killedBarrowBrothers;
	}

	public void setHiddenBrother(int hiddenBrother) {
		this.hiddenBrother = hiddenBrother;
	}

	public int getHiddenBrother() {
		return hiddenBrother;
	}

	public void resetBarrows() {
		hiddenBrother = -1;
		killedBarrowBrothers = new boolean[7]; //includes new bro for future use
		barrowsKillCount = 0;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public boolean isDonator() {
		return isExtremeDonator() || isLegendaryDonator() || isSupremeDonator() || isAngelicDonator() || isDivineDonator() || donator
				|| donatorTill > Utils.currentTimeMillis();
	}

	public boolean isExtremeDonator() {
		return extremeDonator || isLegendaryDonator() || isSupremeDonator() || isAngelicDonator() || isDivineDonator() || extremeDonatorTill > Utils.currentTimeMillis();
	}

	public boolean isLegendaryDonator() {
		return legendaryDonator || isSupremeDonator() || isAngelicDonator() || isDivineDonator() || legendaryDonatorTill > Utils.currentTimeMillis();
	}
	
	public boolean isSupremeDonator() {
		return supremeDonator || isAngelicDonator() || isDivineDonator() || supremeDonatorTill > Utils.currentTimeMillis();
	}
	
	public boolean isAngelicDonator() {
		return angelicDonator || angelicDonatorTill > Utils.currentTimeMillis();
	}
	
	public boolean isDivineDonator() {
		return divineDonator || isAngelicDonator() || divineDonatorTill > Utils.currentTimeMillis();
	}

	public boolean isExtremePermDonator() {
		return extremeDonator;
	}
	
	public boolean isSupremePermDonator() {
		return supremeDonator;
	}
	
	public boolean isAngelicPermDonator() {
		return angelicDonator;
	}
	
	public boolean isDivinePermDonator() {
		return divineDonator;
	}

	public boolean isLegendaryPermDonator() {
		return legendaryDonator;
	}

	public void setExtremeDonator(boolean extremeDonator) {
		this.extremeDonator = extremeDonator;
	}
	
	public void setSupremeDonator(boolean supremeDonator) {
		this.supremeDonator = supremeDonator;
	}

	public void setLegendaryDonator(boolean legendaryDonator) {
		this.legendaryDonator = legendaryDonator;
	}
	
	public void setDivineDonator(boolean divineDonator) {
		this.divineDonator = divineDonator;
	}
	
	public void setAngelicDonator(boolean angelicDonator) {
		this.angelicDonator = angelicDonator;
	}

	public boolean isGraphicDesigner() {
		return isGraphicDesigner;
	}

	public boolean isForumModerator() {
		return isForumModerator;
	}

	public void setGraphicDesigner(boolean isGraphicDesigner) {
		this.isGraphicDesigner = isGraphicDesigner;
	}

	public void setForumModerator(boolean isForumModerator) {
		this.isForumModerator = isForumModerator;
	}
	
	public Player setBoneDelay(long time) {
		boneDelay = time;
		return this;
	}
	
	//public GrandExchange grandExchange() {
	//	return this.grandExchange;
	//}

	@SuppressWarnings("deprecation")
	public void makeDonator(int months) {
		if (donatorTill < Utils.currentTimeMillis())
			donatorTill = Utils.currentTimeMillis();
		Date date = new Date(donatorTill);
		date.setMonth(date.getMonth() + months);
		donatorTill = date.getTime();
	}

	@SuppressWarnings("deprecation")
	public void makeDonatorDays(int days) {
		if (donatorTill < Utils.currentTimeMillis())
			donatorTill = Utils.currentTimeMillis();
		Date date = new Date(donatorTill);
		date.setDate(date.getDate()+days);
		donatorTill = date.getTime();
	}

	@SuppressWarnings("deprecation")
	public void makeExtremeDonatorDays(int days) {
		if (extremeDonatorTill < Utils.currentTimeMillis())
			extremeDonatorTill = Utils.currentTimeMillis();
		Date date = new Date(extremeDonatorTill);
		date.setDate(date.getDate()+days);
		extremeDonatorTill = date.getTime();
	}

	public void makeLegendaryDonatorDays(int days) {
		if (legendaryDonatorTill < Utils.currentTimeMillis())
			legendaryDonatorTill = Utils.currentTimeMillis();
		Date date = new Date(legendaryDonatorTill);
		date.setDate(date.getDate()+days);
		legendaryDonatorTill = date.getTime();
	}
	
	public void makeSupremeDonatorDays(int days) {
		if (supremeDonatorTill < Utils.currentTimeMillis())
			supremeDonatorTill = Utils.currentTimeMillis();
		Date date = new Date(supremeDonatorTill);
		date.setDate(date.getDate()+days);
		supremeDonatorTill = date.getTime();
	}
	
	public void makeDivineDonatorDays(int days) {
		if (divineDonatorTill < Utils.currentTimeMillis())
			divineDonatorTill = Utils.currentTimeMillis();
		Date date = new Date(divineDonatorTill);
		date.setDate(date.getDate()+days);
		divineDonatorTill = date.getTime();
	}
	
	public void makeAngelicDonatorDays(int days) {
		if (angelicDonatorTill < Utils.currentTimeMillis())
			angelicDonatorTill = Utils.currentTimeMillis();
		Date date = new Date(angelicDonatorTill);
		date.setDate(date.getDate()+days);
		angelicDonatorTill = date.getTime();
	}

	@SuppressWarnings("deprecation")
	public String getDonatorTill() {
		return (donator ? "never" : new Date(donatorTill).toGMTString()) + ".";
	}

	@SuppressWarnings("deprecation")
	public String getExtremeDonatorTill() {
		return (extremeDonator ? "never" : new Date(extremeDonatorTill).toGMTString()) + ".";
	}

	@SuppressWarnings("deprecation")
	public String getLegendaryDonatorTill() {
		return (legendaryDonator ? "never" : new Date(legendaryDonatorTill).toGMTString()) + ".";
	}
	
	public String getSupremeDonatorTill() {
		return (supremeDonator ? "never" : new Date(supremeDonatorTill).toGMTString()) + ".";
	}
	
	public String getDivineDonatorTill() {
		return (divineDonator ? "never" : new Date(divineDonatorTill).toGMTString()) + ".";
	}
	
	public String getAngelicDonatorTill() {
		return (angelicDonator ? "never" : new Date(angelicDonatorTill).toGMTString()) + ".";
	}


	public void setDonator(boolean donator) {
		this.donator = donator;
	}

	public String getRecovQuestion() {
		return recovQuestion;
	}

	public void setRecovQuestion(String recovQuestion) {
		this.recovQuestion = recovQuestion;
	}

	public String getRecovAnswer() {
		return recovAnswer;
	}

	public void setRecovAnswer(String recovAnswer) {
		this.recovAnswer = recovAnswer;
	}
	
    public SlayerManager getSlayerManager() {
	return slayerManager;
    }
    

    
    public Assassins getAssassinsManager() {
	return assassins;
    }
    public Deaths getDeathsManager() {
	return deaths;
    }
    public Skillers getSkillersManager() {
	return skillers;
    }

	
	public transient long alchDelay = 0;
	public int dailyamount;
	public boolean dailyhasTask;

	public int Oreid;
	public int TASK;
	public int setskillbox;
	public int TASKID;
	public boolean hasdaily;
	public boolean givendaily;
	public int skillingtask;
	public boolean getStarter;
	public boolean DIVINECHOPPING;
	public boolean HarvestingEnriched;
	public boolean createdFlickeringBoon;
	public boolean createdBrightBoon;
	public boolean createdGlowingBoon;
	public boolean createdSparklingBoon;
	public boolean createdGleamingBoon;
	public boolean createdVibrantBoon;
	public boolean createdLustrousBoon;
	public boolean createdBrilliantBoon;
	public boolean createdRadiantBoon;
	public boolean createdLuminousBoon;
	public boolean createdIncandescentBoon;
	public int renderId;
	public boolean spawned;
	public boolean locked;
	public Player instancepartner;
	public String instancePass;
	public int playersInInstance;
	public Player newOwner;
	public boolean inInstancedDungeon;
	public int MaxPlayers;
	public int maxPlayer;
	public String sessionowner;
	public int playerAmount;
	public Difficulty difficulty;
	public Instance instance;


	
	public void setAlchDelay(long delay) {
		alchDelay = delay+Utils.currentTimeMillis();
	}
	
	public boolean canAlch() {
		return alchDelay < Utils.currentTimeMillis();
	}

	public String getLastMsg() {
		return lastMsg;
	}

	public void setLastMsg(String lastMsg) {
		this.lastMsg = lastMsg;
	}

	public int[] getPouches() {
		return pouches;
	}

	public EmotesManager getEmotesManager() {
		return emotesManager;
	}

	public String getLastIP() {
		return lastIP;
	}

	/**
	 * Lobby Stuff
	 */

	public void init(Session session, String string, IsaacKeyPair isaacKeyPair) {
		username = string;
		this.session = session;
		this.isaacKeyPair = isaacKeyPair;
		World.addLobbyPlayer(this);// .addLobbyPlayer(this);
		if (lodeStone == null) {
			lodeStone = new LodeStone();
		}
		if (activatedLodestones == null) {
			activatedLodestones = new boolean[16];
		}
		if (Settings.DEBUG) {
			Logger.log(this, new StringBuilder("Lobby Inited Player: ").append(string).append(", pass: ").append(password).toString());
		}
	}

	public void startLobby(Player player) {
		player.sendLobbyConfigs(player);
		friendsIgnores.setPlayer(this);
		friendsIgnores.init();
		player.getPackets().sendFriendsChatChannel();
		friendsIgnores.sendFriendsMyStatus(true);
	}

	public void sendLobbyConfigs(Player player) {
		for (int i = 0; i < Utils.DEFAULT_LOBBY_CONFIGS.length; i++) {
			int val = Utils.DEFAULT_LOBBY_CONFIGS[i];
			if (val != 0) {
				player.getPackets().sendConfig(i, val);
			}
		}
	}
	
	public boolean takeMoney(int amount) {
		if (inventory.getNumerOf(995) >= amount) {
			inventory.removeItemMoneyPouch(995, amount);
			return true;
		} else {
			return false;
		}
	}

	public String getLastHostname() {
		InetAddress addr;
		try {
			addr = InetAddress.getByName(getLastIP());
			String hostname = addr.getHostName();
			return hostname;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PriceCheckManager getPriceCheckManager() {
		return priceCheckManager;
	}

	public void setPestPoints(int pestPoints) {
		this.pestPoints = pestPoints;
	}

	public int getPestPoints() {
		return pestPoints;
	}

	public boolean isUpdateMovementType() {
		return updateMovementType;
	}

	public long getLastPublicMessage() {
		return lastPublicMessage;
	}

	public void setLastPublicMessage(long lastPublicMessage) {
		this.lastPublicMessage = lastPublicMessage;
	}

	public CutscenesManager getCutscenesManager() {
		return cutscenesManager;
	}

	public void kickPlayerFromFriendsChannel(String name) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.kickPlayerFromChat(this, name);
	}

    public void sendFriendsChannelMessage(ChatMessage message) {
	if (currentFriendChat == null)
	    return;
	currentFriendChat.sendMessage(this, message);
    }

	public void sendFriendsChannelQuickMessage(QuickChatMessage message) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.sendQuickMessage(this, message);
	}

    public void sendPublicChatMessage(PublicChatMessage message) {
	for (int regionId : getMapRegionsIds()) {
	    List<Integer> playersIndexes = World.getRegion(regionId).getPlayerIndexes();
	    if (playersIndexes == null)
		continue;
	    for (Integer playerIndex : playersIndexes) {
		Player p = World.getPlayers().get(playerIndex);
		if (p == null || !p.hasStarted() || p.hasFinished() || p.getLocalPlayerUpdate().getLocalPlayers()[getIndex()] == null)
		    continue;
		p.getPackets().sendPublicMessage(this, message);
	    }
	}
    }

    public void sendDiceRoll(String message){
    	for (int regionId : getMapRegionsIds()) {
    	    List<Integer> playersIndexes = World.getRegion(regionId).getPlayerIndexes();
    	    if (playersIndexes == null)
    		continue;
    	    for (Integer playerIndex : playersIndexes) {
    		Player p = World.getPlayers().get(playerIndex);
    		if (p == null || !p.hasStarted() || p.hasFinished() || p.getLocalPlayerUpdate().getLocalPlayers()[getIndex()] == null)
    		    continue;
    		p.sendMessage(message);
    	    }
    	}
    }
    
    
	public int[] getCompletionistCapeCustomized() {
		return completionistCapeCustomized;
	}

	public void setCompletionistCapeCustomized(int[] skillcapeCustomized) {
		this.completionistCapeCustomized = skillcapeCustomized;
	}

	public int[] getMaxedCapeCustomized() {
		return maxedCapeCustomized;
	}
	
	

	public void setMaxedCapeCustomized(int[] maxedCapeCustomized) {
		this.maxedCapeCustomized = maxedCapeCustomized;
	}

	public void setSkullId(int skullId) {
		this.skullId = skullId;
	}

	public int getSkullId() {
		return skullId;
	}

	public boolean isFilterGame() {
		return filterGame;
	}

	public void setFilterGame(boolean filterGame) {
		this.filterGame = filterGame;
	}

	public void addLogicPacketToQueue(LogicPacket packet) {
		for (LogicPacket p : logicPackets) {
			if (p.getId() == packet.getId()) {
				logicPackets.remove(p);
				break;
			}
		}
		logicPackets.add(packet);
	}

	public DominionTower getDominionTower() {
		return dominionTower;
	}
	
	public Farmings getFarmings() {
		return farmings;
	}

	public void setFarming(Farmings farmings) {
		this.farmings = farmings;
	}
	
	public void setJujuMiningBoost(int boost) {
		jujuMining = boost;
	}

	public boolean hasJujuMiningBoost() {
		return jujuMining > 1;
	}
	
	public void setJujuGodBoost(int boost) {
		jujuGod = boost;
	}

	public boolean hasJujuGodBoost() {
		return jujuGod > 1;
	}

	public void setJujuFarmingBoost(int boost) {
		jujuFarming = boost;
	}

	public boolean hasJujuFarmingBoost() {
		return jujuFarming > 1;
	}

	public void setPrayerRenewalDelay(int delay) {
		this.prayerRenewalDelay = delay;
	}

	public int getOverloadDelay() {
		return overloadDelay;
	}

	public void setOverloadDelay(int overloadDelay) {
		this.overloadDelay = overloadDelay;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTeleBlockDelay(long teleDelay) {
		getTemporaryAttributtes().put("TeleBlocked",
				teleDelay + Utils.currentTimeMillis());
	}

	public long getTeleBlockDelay() {
		Long teleblock = (Long) getTemporaryAttributtes().get("TeleBlocked");
		if (teleblock == null)
			return 0;
		return teleblock;
	}

	public void setPrayerDelay(long teleDelay) {
		getTemporaryAttributtes().put("PrayerBlocked",
				teleDelay + Utils.currentTimeMillis());
		prayer.closeAllPrayers();
	}

	public long getPrayerDelay() {
		Long teleblock = (Long) getTemporaryAttributtes().get("PrayerBlocked");
		if (teleblock == null)
			return 0;
		return teleblock;
	}

	public Familiar getFamiliar() {
		return familiar;
	}

	public void setFamiliar(Familiar familiar) {
		this.familiar = familiar;
	}

	public FriendChatsManager getCurrentFriendChat() {
		return currentFriendChat;
	}

	public void setCurrentFriendChat(FriendChatsManager currentFriendChat) {
		this.currentFriendChat = currentFriendChat;
	}

	public String getCurrentFriendChatOwner() {
		return currentFriendChatOwner;
	}

	public void setCurrentFriendChatOwner(String currentFriendChatOwner) {
		this.currentFriendChatOwner = currentFriendChatOwner;
	}

	public int getSummoningLeftClickOption() {
		return summoningLeftClickOption;
	}

	public void setSummoningLeftClickOption(int summoningLeftClickOption) {
		this.summoningLeftClickOption = summoningLeftClickOption;
	}

	public boolean canSpawn() {
		if (Wilderness.isAtWild(this)
				|| getControlerManager().getControler() instanceof FightPitsArena
				|| getControlerManager().getControler() instanceof CorpBeastControler
				|| getControlerManager().getControler() instanceof PestControlLobby
				|| getControlerManager().getControler() instanceof PestControlGame
				|| getControlerManager().getControler() instanceof ZGDControler
				|| getControlerManager().getControler() instanceof GodWars
				|| getControlerManager().getControler() instanceof DTControler
				|| getControlerManager().getControler() instanceof DuelArena
				|| getControlerManager().getControler() instanceof CastleWarsPlaying
				|| getControlerManager().getControler() instanceof CastleWarsWaiting
				|| getControlerManager().getControler() instanceof FightCaves
				|| getControlerManager().getControler() instanceof FightKiln
				|| FfaZone.inPvpArea(this)
				|| getControlerManager().getControler() instanceof NomadsRequiem
				|| getControlerManager().getControler() instanceof QueenBlackDragonController
				|| getControlerManager().getControler() instanceof WarControler) {
			return false;
		}
		if(getControlerManager().getControler() instanceof CrucibleControler) {
			CrucibleControler controler = (CrucibleControler) getControlerManager().getControler();
			return !controler.isInside();
		}
		return true;
	}

	public long getPolDelay() {
		return polDelay;
	}
	
	public void setTreeDamage(int damage) {
		treeDamage = damage;
		return;
	}

	public void addPolDelay(long delay) {
		polDelay = delay + Utils.currentTimeMillis();
	}

	public void setPolDelay(long delay) {
		this.polDelay = delay;
	}
	


	public List<Integer> getSwitchItemCache() {
		return switchItemCache;
	}

	public AuraManager getAuraManager() {
		return auraManager;
	}
	
    public boolean allowsProfanity() {
        return allowsProfanity;
    }
    
    public void switchProfanityFilter() {
	profanityFilter = !profanityFilter;
	refreshProfanityFilter();
    }
    
    public void refreshProfanityFilter() {
	getVarsManager().sendVarBit(8780, profanityFilter ? 0 : 1);
    }

    public void allowsProfanity(boolean allowsProfanity) {
        this.allowsProfanity = allowsProfanity;
    }

	public int getMovementType() {
		if (getTemporaryMoveType() != -1)
			return getTemporaryMoveType();
		return getRun() ? RUN_MOVE_TYPE : WALK_MOVE_TYPE;
	}

	public List<String> getOwnedObjectManagerKeys() {
		if (ownedObjectsManagerKeys == null) // temporary
			ownedObjectsManagerKeys = new LinkedList<String>();
		return ownedObjectsManagerKeys;
	}

	public boolean hasInstantSpecial(final int weaponId) {
		switch (weaponId) {
		case 4153:
		case 15486:
		case 22207:
		case 22209:
		case 22211:
		case 22213:
		case 1377:
		case 13472:
		case 35:// Excalibur
		case 8280:
		case 14632:
			return true;
		default: return false;
		}
	}

	public void performInstantSpecial(final int weaponId) {
		int specAmt = PlayerCombat.getSpecialAmmount(weaponId);
		if (combatDefinitions.hasRingOfVigour())
			specAmt *= 0.9;
		if (combatDefinitions.getSpecialAttackPercentage() < specAmt) {
			getPackets().sendGameMessage("You don't have enough power left.");
			combatDefinitions.desecreaseSpecialAttack(0);
			return;
		}
		if (this.getSwitchItemCache().size() > 0) {
			ButtonHandler.submitSpecialRequest(this);
			return;
		}
		switch (weaponId) {
		case 4153:
			combatDefinitions.setInstantAttack(true);
			combatDefinitions.switchUsingSpecialAttack();
			Entity target = (Entity) getTemporaryAttributtes().get("last_target");
			if (target != null && target.getTemporaryAttributtes().get("last_attacker") == this) {
				if (!(getActionManager().getAction() instanceof PlayerCombat) || ((PlayerCombat) getActionManager().getAction()).getTarget() != target) {
					getActionManager().setAction(new PlayerCombat(target));
				}
			}
			break;
		case 1377:
		case 13472:
			setNextAnimation(new Animation(1056));
			setNextGraphics(new Graphics(246));
			setNextForceTalk(new ForceTalk("Raarrrrrgggggghhhhhhh!"));
			int defence = (int) (skills.getLevelForXp(Skills.DEFENCE) * 0.90D);
			int attack = (int) (skills.getLevelForXp(Skills.ATTACK) * 0.90D);
			int range = (int) (skills.getLevelForXp(Skills.RANGE) * 0.90D);
			int magic = (int) (skills.getLevelForXp(Skills.MAGIC) * 0.90D);
			int strength = (int) (skills.getLevelForXp(Skills.STRENGTH) * 1.2D);
			skills.set(Skills.DEFENCE, defence);
			skills.set(Skills.ATTACK, attack);
			skills.set(Skills.RANGE, range);
			skills.set(Skills.MAGIC, magic);
			skills.set(Skills.STRENGTH, strength);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		case 35:// Excalibur
		case 8280:
		case 14632:
			setNextAnimation(new Animation(1168));
			setNextGraphics(new Graphics(247));
			setNextForceTalk(new ForceTalk("For CollabScape718!"));
			final boolean enhanced = weaponId == 14632;
			skills.set(
					Skills.DEFENCE,
					enhanced ? (int) (skills.getLevelForXp(Skills.DEFENCE) * 1.15D)
							: (skills.getLevel(Skills.DEFENCE) + 8));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 5;

				@Override
				public void run() {
					if (isDead() || hasFinished()
							|| getHitpoints() >= getMaxHitpoints()) {
						stop();
						return;
					}
					heal(enhanced ? 80 : 40);
					if (count-- == 0) {
						stop();
						return;
					}
				}
			}, 4, 2);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		case 15486:
		case 22207:
		case 22209:
		case 22211:
		case 22213:
			setNextAnimation(new Animation(12804));
			setNextGraphics(new Graphics(2319));// 2320
			setNextGraphics(new Graphics(2321));
			addPolDelay(60000);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		}
	}

	public void setDisableEquip(boolean equip) {
		disableEquip = equip;
	}

	public boolean isEquipDisabled() {
		return disableEquip;
	}

	public void addDisplayTime(long i) {
		this.displayTime = i + Utils.currentTimeMillis();
	}

	public long getDisplayTime() {
		return displayTime;
	}

	public int getPublicStatus() {
		return publicStatus;
	}

	public void setPublicStatus(int publicStatus) {
		this.publicStatus = publicStatus;
	}
	
    public boolean isFilteringProfanity() {
	return profanityFilter;
    }

	
	public int[] getClanCapeCustomized() {
		return clanCapeCustomized;
	}

	public void setClanCapeCustomized(int[] clanCapeCustomized) {
		this.clanCapeCustomized = clanCapeCustomized;
	}

	public int[] getClanCapeSymbols() {
		return clanCapeSymbols;
	}

	public void setClanCapeSymbols(int[] clanCapeSymbols) {
		this.clanCapeSymbols = clanCapeSymbols;
	}

	public int getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(int tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public int getAssistStatus() {
		return assistStatus;
	}

	public void setAssistStatus(int assistStatus) {
		this.assistStatus = assistStatus;
	}

	public boolean isSpawnsMode() {
		return spawnsMode;
	}

	public void setSpawnsMode(boolean spawnsMode) {
		this.spawnsMode = spawnsMode;
	}

	public Notes getNotes() {
		return notes;
	}
	
    public int getCannonBalls() {
	return cannonBalls;
    }

    public void addCannonBalls(int cannonBalls) {
	this.cannonBalls += cannonBalls;
    }

    public void removeCannonBalls() {
	this.cannonBalls = 0;
    }
	

		public int getHouseX() {
			return houseX;
		}

		public void setHouseX(int houseX) {
			this.houseX = houseX;
		}
		
		public int getHouseY() {
			return houseY;
		}
		
		public void setHouseY(int houseY) {
			this.houseY = houseY;
		}
		
		public boolean hasBeenToHouse() {
			return hasBeenToHouse;
		}
		
		public void setBeenToHouse(boolean flag) {
			hasBeenToHouse = flag;
		}

		public int[] getBoundChuncks() {
			return boundChuncks;
		}

		public void setBoundChuncks(int[] boundChuncks) {
			this.boundChuncks = boundChuncks;
		}


		public List<WorldObject> getConObjectsToBeLoaded() {
			return conObjectsToBeLoaded;
		}

		public boolean isBuildMode() {
			return buildMode;
		}

		public void setBuildMode(boolean buildMode) {
			this.buildMode = buildMode;
		}

		public int getPlace() {
			return place;
		}

		public void setPlace(int place) {
			this.place = place;
		}
		
		public List<RoomReference> getRooms() {
			return rooms;
		}
		
		public RoomReference getCurrentRoom() {
			for (RoomReference r : rooms) {
				if (r.getX() == this.getX() && r.getY() == this.getY()) {
					return r;
				}
			}
			return null;
		}
		
		public House getHouse() {
			return house;
		}
		public JoinInstance getJoinInstance() {
			return joininstance;
		}
		public Player getinstancepartner() {
			return instancepartner;
		}
		public int getRoomX() {
			return Math.round(getXInRegion() / 8);
		}
		
		public int getRoomY() {
			return Math.round(getYInRegion() / 8);
		}


		public RoomReference getRoomReference() {
			return roomReference;
		}
		
		public HouseLocation getHouseLocation() {
			return portalLocation;
		}
		
		public void setHouseLocation(HouseLocation h) {
			this.portalLocation = h;
		}

		public boolean isHasConfirmedRoomDeletion() {
			return hasConfirmedRoomDeletion;
		}

		public void setHasConfirmedRoomDeletion(boolean hasConfirmedRoomDeletion) {
			this.hasConfirmedRoomDeletion = hasConfirmedRoomDeletion;
		}

		public ServantType getButler() {
			return butler;
		}

		public void setButler(ServantType butler) {
			this.butler = butler;
		}
		
		public RoomReference getRoomFor(int x, int y) {
			for (RoomReference r : this.getRooms()) {
				if (r.getX() == x && r.getY() == y) {
					return r;
				}
			}
			return null;
		}
		
	    public SquealOfFortune getSquealOfFortune() {
	    	return squealOfFortune;
	        }
	
	/**
	 * Farming Methods
	 */
	
	//Falador
	public int getFaladorHerbPatch() {
		return faladorHerbPatch;
	}
	
	public void setFaladorHerbPatch(int seed) {
		this.faladorHerbPatch = seed;
	}
	
	public int getFaladorNorthAllotmentPatch() {
		return faladorNorthAllotmentPatch;
	}
	
	public void setFaladorNorthAllotmentPatch(int seed) {
		this.faladorNorthAllotmentPatch = seed;
	}
	
	public int getFaladorSouthAllotmentPatch() {
		return faladorSouthAllotmentPatch;
	}
	
	public void setFaladorSouthAllotmentPatch(int seed) {
		this.faladorSouthAllotmentPatch = seed;
	}
	
	public int getFaladorFlowerPatch() {
		return faladorFlowerPatch;
	}
	
	public void setFaladorFlowerPatch(int seed) {
		this.faladorFlowerPatch = seed;
	}
	
	public boolean getFaladorHerbPatchRaked() {
		return faladorHerbPatchRaked;
	}
	
	public void setFaladorHerbPatchRaked(boolean raked) {
		this.faladorHerbPatchRaked = raked;
	}
	
	public boolean getFaladorNorthAllotmentPatchRaked() {
		return faladorNorthAllotmentPatchRaked;
	}
	
	public void setFaladorNorthAllotmentPatchRaked(boolean raked) {
		this.faladorNorthAllotmentPatchRaked = raked;
	}
	
	public boolean getFaladorSouthAllotmentPatchRaked() {
		return faladorSouthAllotmentPatchRaked;
	}
	
	public void setFaladorSouthAllotmentPatchRaked(boolean raked) {
		this.faladorSouthAllotmentPatchRaked = raked;
	}
	
	public boolean getFaladorFlowerPatchRaked() {
		return faladorFlowerPatchRaked;
	}
	
	public void setFaladorFlowerPatchRaked(boolean raked) {
		this.faladorFlowerPatchRaked = raked;
	}
	
	//Ardougne
	public int getArdougneHerbPatch() {
		return ardougneHerbPatch;
	}
	
	public void setArdougneHerbPatch(int seed) {
		this.ardougneHerbPatch = seed;
	}
	
	public int getArdougneNorthAllotmentPatch() {
		return ardougneNorthAllotmentPatch;
	}
	
	public void setArdougneNorthAllotmentPatch(int seed) {
		this.ardougneNorthAllotmentPatch = seed;
	}
	
	public int getArdougneSouthAllotmentPatch() {
		return ardougneSouthAllotmentPatch;
	}
	
	public void setArdougneSouthAllotmentPatch(int seed) {
		this.ardougneSouthAllotmentPatch = seed;
	}
	
	public int getArdougneFlowerPatch() {
		return ardougneFlowerPatch;
	}
	
	public void setArdougneFlowerPatch(int seed) {
		this.ardougneFlowerPatch = seed;
	}
	
	public boolean getArdougneHerbPatchRaked() {
		return ardougneHerbPatchRaked;
	}
	
	public void setArdougneHerbPatchRaked(boolean raked) {
		this.ardougneHerbPatchRaked = raked;
	}
	
	public boolean getArdougneNorthAllotmentPatchRaked() {
		return ardougneNorthAllotmentPatchRaked;
	}
	
	public void setArdougneNorthAllotmentPatchRaked(boolean raked) {
		this.ardougneNorthAllotmentPatchRaked = raked;
	}
	
	public boolean getArdougneSouthAllotmentPatchRaked() {
		return ardougneSouthAllotmentPatchRaked;
	}
	
	public void setArdougneSouthAllotmentPatchRaked(boolean raked) {
		this.ardougneSouthAllotmentPatchRaked = raked;
	}
	
	public boolean getArdougneFlowerPatchRaked() {
		return ardougneFlowerPatchRaked;
	}
	
	public void setArdougneFlowerPatchRaked(boolean raked) {
		this.ardougneFlowerPatchRaked = raked;
	}
	
	//Catherby
	public int getCatherbyHerbPatch() {
		return catherbyHerbPatch;
	}
	
	public void setCatherbyHerbPatch(int seed) {
		this.catherbyHerbPatch = seed;
	}
	
	public int getCatherbyNorthAllotmentPatch() {
		return catherbyNorthAllotmentPatch;
	}
	
	public void setCatherbyNorthAllotmentPatch(int seed) {
		this.catherbyNorthAllotmentPatch = seed;
	}
	
	public int getCatherbySouthAllotmentPatch() {
		return catherbySouthAllotmentPatch;
	}
	
	public void setCatherbySouthAllotmentPatch(int seed) {
		this.catherbySouthAllotmentPatch = seed;
	}
	
	public int getCatherbyFlowerPatch() {
		return catherbyFlowerPatch;
	}
	
	public void setCatherbyFlowerPatch(int seed) {
		this.catherbyFlowerPatch = seed;
	}
	
	public boolean getCatherbyHerbPatchRaked() {
		return catherbyHerbPatchRaked;
	}
	
	public void setCatherbyHerbPatchRaked(boolean raked) {
		this.catherbyHerbPatchRaked = raked;
	}
	
	public boolean getCatherbyNorthAllotmentPatchRaked() {
		return catherbyNorthAllotmentPatchRaked;
	}
	
	public void setCatherbyNorthAllotmentPatchRaked(boolean raked) {
		this.catherbyNorthAllotmentPatchRaked = raked;
	}
	
	public boolean getCatherbySouthAllotmentPatchRaked() {
		return catherbySouthAllotmentPatchRaked;
	}
	
	public void setCatherbySouthAllotmentPatchRaked(boolean raked) {
		this.catherbySouthAllotmentPatchRaked = raked;
	}
	
	public boolean getCatherbyFlowerPatchRaked() {
		return catherbyFlowerPatchRaked;
	}
	
	public void setCatherbyFlowerPatchRaked(boolean raked) {
		this.catherbyFlowerPatchRaked = raked;
	}
	
	//Canifis
	public int getCanifisHerbPatch() {
		return canifisHerbPatch;
	}
	
	public void setCanifisHerbPatch(int seed) {
		this.canifisHerbPatch = seed;
	}
	
	public int getCanifisNorthAllotmentPatch() {
		return canifisNorthAllotmentPatch;
	}
	
	public void setCanifisNorthAllotmentPatch(int seed) {
		this.canifisNorthAllotmentPatch = seed;
	}
	
	public int getCanifisSouthAllotmentPatch() {
		return canifisSouthAllotmentPatch;
	}
	
	public void setCanifisSouthAllotmentPatch(int seed) {
		this.canifisSouthAllotmentPatch = seed;
	}
	
	public int getCanifisFlowerPatch() {
		return canifisFlowerPatch;
	}
	
	public void setCanifisFlowerPatch(int seed) {
		this.canifisFlowerPatch = seed;
	}
	
	public boolean getCanifisHerbPatchRaked() {
		return canifisHerbPatchRaked;
	}
	
	public void setCanifisHerbPatchRaked(boolean raked) {
		this.canifisHerbPatchRaked = raked;
	}
	
	public boolean getCanifisNorthAllotmentPatchRaked() {
		return canifisNorthAllotmentPatchRaked;
	}
	
	public void setCanifisNorthAllotmentPatchRaked(boolean raked) {
		this.canifisNorthAllotmentPatchRaked = raked;
	}
	
	public boolean getCanifisSouthAllotmentPatchRaked() {
		return canifisSouthAllotmentPatchRaked;
	}
	
	public void setCanifisSouthAllotmentPatchRaked(boolean raked) {
		this.canifisSouthAllotmentPatchRaked = raked;
	}
	
	public boolean getCanifisFlowerPatchRaked() {
		return canifisFlowerPatchRaked;
	}
	
	public void setCanifisFlowerPatchRaked(boolean raked) {
		this.canifisFlowerPatchRaked = raked;
	}
	
	//Lumbridge
	public int getLummyTreePatch() {
		return lummyTreePatch;
	}
	
	public void setLummyTreePatch(int sapling) {
		this.lummyTreePatch = sapling;
	}
	
	public boolean getLummyTreePatchRaked() {
		return lummyTreePatchRaked;
	}
	
	public void setLummyTreePatchRaked(boolean raked) {
		this.lummyTreePatchRaked = raked;
	}
	
	//Varrock
	public int getVarrockTreePatch() {
		return varrockTreePatch;
	}
	
	public void setVarrockTreePatch(int sapling) {
		this.varrockTreePatch = sapling;
	}
	
	public boolean getVarrockTreePatchRaked() {
		return varrockTreePatchRaked;
	}
	
	public void setVarrockTreePatchRaked(boolean raked) {
		this.varrockTreePatchRaked = raked;
	}
	
	//Falador
	public int getFaladorTreePatch() {
		return faladorTreePatch;
	}
	
	public void setFaladorTreePatch(int sapling) {
		this.faladorTreePatch = sapling;
	}
	
	public boolean getFaladorTreePatchRaked() {
		return faladorTreePatchRaked;
	}
	
	public void setFaladorTreePatchRaked(boolean raked) {
		this.faladorTreePatchRaked = raked;
	}
	
	//Taverly
	public int getTaverlyTreePatch() {
		return taverlyTreePatch;
	}
	
	public void setTaverlyTreePatch(int sapling) {
		this.taverlyTreePatch = sapling;
	}
	
	public boolean getTaverlyTreePatchRaked() {
		return taverlyTreePatchRaked;
	}
	
	public void setTaverlyTreePatchRaked(boolean raked) {
		this.taverlyTreePatchRaked = raked;
	}

	public IsaacKeyPair getIsaacKeyPair() {
		return isaacKeyPair;
	}

	public QuestManager getQuestManager() {
		return questManager;
	}

	public boolean isCompletedFightCaves() {
		return completedFightCaves;
	}

	public void setCompletedFightCaves() {
		if(!completedFightCaves) {
			completedFightCaves = true;
			refreshFightKilnEntrance();
		}
	}

	public boolean isCompletedFightKiln() {
		return completedFightKiln;
	}

	public void setCompletedFightKiln() {
		completedFightKiln = true;
	}
	
	public void setCompletedPestInvasion() {
		completedPestInvasion = true;
	}


	public boolean isWonFightPits() {
		return wonFightPits;
	}

	public void setWonFightPits() {
		wonFightPits = true;
	}

	public boolean isCantTrade() {
		return cantTrade;
	}

	public void setCantTrade(boolean canTrade) {
		this.cantTrade = canTrade;
	}

	public String getYellColor() {
		return yellColor;
	}

	public void setYellColor(String yellColor) {
		this.yellColor = yellColor;
	}

	/**
	 * Gets the pet.
	 * @return The pet.
	 */
	public Pet getPet() {
		return pet;
	}

	/**
	 * Sets the pet.
	 * @param pet The pet to set.
	 */
	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public boolean isSupporter() {
		return isSupporter;
	}

	public void setSupporter(boolean isSupporter) {
		this.isSupporter = isSupporter;
	}

	public SlayerTask getSlayerTask() {
		return slayerTask;
	}

	public void setSlayerTask(SlayerTask slayerTask) {
		this.slayerTask = slayerTask;
	}

	/**
	 * Gets the petManager.
	 * @return The petManager.
	 */
	public PetManager getPetManager() {
		return petManager;
	}

	/**
	 * Sets the petManager.
	 * @param petManager The petManager to set.
	 */
	public void setPetManager(PetManager petManager) {
		this.petManager = petManager;
	}

	public boolean isXpLocked() {
		return xpLocked;
	}

	public void setXpLocked(boolean locked) {
		this.xpLocked = locked;
	}

	public int getLastBonfire() {
		return lastBonfire;
	}

	public void setLastBonfire(int lastBonfire) {
		this.lastBonfire = lastBonfire;
	}

	public boolean isYellOff() {
		return yellOff;
	}

	public void setYellOff(boolean yellOff) {
		this.yellOff = yellOff;
	}

	public void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}

	public double getHpBoostMultiplier() {
		return hpBoostMultiplier;
	}

	public void setHpBoostMultiplier(double hpBoostMultiplier) {
		this.hpBoostMultiplier = hpBoostMultiplier;
	}

	public void sm(String message) {
		getPackets().sendGameMessage(message);

	}
	
	public static void mutes(Player player, String message) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "punishments/mutes.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has muted "+message);
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }
	
	public static void bans(Player player, String message) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "punishments/bans.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has banned "+message);
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }
	
	public static void jails(Player player, String message) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "punishments/jails.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has jailed "+message);
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }
	
	public static void ipbans(Player player, String message) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(Settings.LOG_PATH + "punishments/ipbans.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " has ipbanned "+message);
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }


	/**
	 * Gets the killedQueenBlackDragon.
	 * @return The killedQueenBlackDragon.
	 */
	public boolean isKilledQueenBlackDragon() {
		return killedQueenBlackDragon;
	}

	/**
	 * Sets the killedQueenBlackDragon.
	 * @param killedQueenBlackDragon The killedQueenBlackDragon to set.
	 */
	public void setKilledQueenBlackDragon(boolean killedQueenBlackDragon) {
		this.killedQueenBlackDragon = killedQueenBlackDragon;
	}

	public boolean hasLargeSceneView() {
		return largeSceneView;
	}

	public void setLargeSceneView(boolean largeSceneView) {
		this.largeSceneView = largeSceneView;
	}

	public boolean isOldItemsLook() {
		return oldItemsLook;
	}

	public void switchItemsLook() {
		oldItemsLook = !oldItemsLook;
		getPackets().sendItemsLook();
	}

	/**
	 * @return the runeSpanPoint
	 */
	public int getRuneSpanPoints() {
		return runeSpanPoints;
	}

	/**
	 * @param runeSpanPoint the runeSpanPoint to set
	 */
	public void setRuneSpanPoint(int runeSpanPoints) {
		this.runeSpanPoints = runeSpanPoints;
	}
	/**
	 * Adds points
	 * @param points
	 */
	public void addRunespanPoints(int points) {
		this.runeSpanPoints += points;
	}
	
	/*private static PrizedPendants pendants; 
	
	public PrizedPendants getPendant() {
		return pendants;
	}*/

	/**
	 * Warriors Guild Stuff
	 */

    public int getWGuildTokens() {
    	return wGuildTokens;
    }

    public void setWGuildTokens(int tokens) {
    	wGuildTokens = tokens;
    }

    public boolean inClopsRoom() {
    	return inClops;
    }

    public void setInClopsRoom(boolean in) {
    	inClops = in;
    }

    public DuelRules getLastDuelRules() {
	return lastDuelRules;
    }

    public void setLastDuelRules(DuelRules duelRules) {
	this.lastDuelRules = duelRules;
    }


	public boolean isTalkedWithMarv() {
		return talkedWithMarv;
	}

	public void setTalkedWithMarv() {
		talkedWithMarv = true;
	}

	public int getCrucibleHighScore() {
		return crucibleHighScore;
	}

	public void increaseCrucibleHighScore() {
		crucibleHighScore++;
	}

	public void setVoted(long ms) {
		voted = ms + Utils.currentTimeMillis();
	}


	public int getLastLoggedIn() {
		return lastlogged;
	}

	/**
	 * MoneyPouch Stuff
	 */

	public double getMoneyInPouch() {
		return moneyInPouch;
	}

	
	public void setMoneyInPouch(int totalCash) {
		moneyInPouch = totalCash;
	}

	public void addMoneyToPouch(int add) {
		moneyInPouch += add;
	}

	public void removeMoneyFromPouch(int remove) {
		moneyInPouch -= remove;
	}

	public void moneyPouchPow(int power) {
		moneyInPouch = Math.pow(moneyInPouch, power);
	}

	public MoneyPouch getMoneyPouch() {
		return moneyPouch;
	}

	public void sendMessage(String message) {
		getPackets().sendGameMessage(message);
	}

	public void canSheaths() {
		canSheath = !canSheath;

	}

	public boolean isCanSheath() {
		return canSheath;
	}

	/**
	 * Sheathing
	 */

	public void canSheath() {
		int unSheath = getEquipment().getWeaponId();
		int unShield = getEquipment().getShieldId();
	//	updateSheathing();
		switch(unSheath) {
		case 11696:
			setWeapon(27007);
		break;
		case 11698:
			setWeapon(27008);
		break;
		case 11700:
			setWeapon(27009);
		break;
		}	
		switch(unShield) {
 
		case 18747: //top
		setShield(27034); //bottom
		break;
 
		case 12915: //top
		setShield(27035); //bottom
		break;

		case 10352: //top
		setShield(27036); //bottom
		break;
 
		case 19345: //top
		setShield(27037); //bottom
		break;

		case 19340: //top
		setShield(27038); //bottom
		break;
 
  
		case 3122: //top
		setShield(27040); //bottom
		break;

		case 2890: //top
		setShield(27041); //bottom
		break;

		case 1187: //top
		setShield(27042); //bottom
		break;
 
		case 6524: //top
		setShield(27043); //bottom
		break;

		case 17361: //top
		setShield(27044); //bottom
		break;

		case 18359: //top
		setShield(27045); //bottom
		break;

		case 1189: //top
		setShield(27046); //bottom
		break;
  
		case 1173: //top
		setShield(27047); //bottom
		break;

		case 1175: //top
		setShield(27048); //bottom
		break;
  
		case 1191: //top
		setShield(27049); //bottom
		break;

		case 1177: //top
		setShield(27050); //bottom
		break;

		case 1193: //top
		setShield(27051); //bottom
		break;
  
		case 1199: //top
		setShield(27052); //bottom
		break;

		case 1183: //top
		setShield(27053); //bottom
		break;

		case 2603: //top
		setShield(27054); //bottom
		break;

		case 2611: //top
		setShield(27055); //bottom
		break;

		case 25013: //top
		setShield(27056); //bottom
		break;
  

		case 25019: //top
		setShield(27057); //bottom
		break;
		
		case 2589: //top
		setShield(27058); //bottom
		break;
 
		case 1195: //top
		setShield(27059); //bottom
		break;

		case 2597: //top
		setShield(27060); //bottom
		break;

		case 1179: //top
		setShield(27061); //bottom
		break;
 
  
		case 25800: //top
		setShield(27063); //bottom
		break;

		case 25796: //top
		setShield(27064); //bottom
		break;

		case 25794: //top
		setShield(27065); //bottom
		break;
  
		case 25798: //top
		setShield(27066); //bottom
		break;

		case 1181: //top
		setShield(27067); //bottom
		break;
  


		case 2667: //top
		setShield(27069); //bottom
		break;
		
		case 19440: //top
		setShield(27070); //bottom
		break;
  


		case 3488: //top
		setShield(27071); //bottom
		break;
  

		case 1185: //top
		setShield(27072); //bottom
		break;
 


		case 2629: //top
		setShield(27073); //bottom
		break;
 

		case 12929: //top
		setShield(27074); //bottom
		break;
  


		case 1201: //top
		setShield(27075); //bottom
		break;
  

		case 19425: //top
		setShield(27076); //bottom
		break;
  

		case 2675: //top
		setShield(27077); //bottom
		break;
  


		case 2659: //top
		setShield(27078); //bottom
		break;
  


		case 19410: //top
		setShield(27079); //bottom
		break;



		case 2621: //top
		setShield(27080); //bottom
		break;

		case 6633: //top
		setShield(27081); //bottom
		break;

		case 6631: //top
		setShield(27082); //bottom
		break;
  
		case 25855: //top
		setShield(27083); //bottom
		break;
  
		case 25802: //top
		setShield(27084); //bottom
		break;

		case 24365: //top
		setShield(27085); //bottom
		break;
  
		}
	}
	public void unSheath() {
		int Sheathing = getEquipment().getWeaponId();
		int Shielding = getEquipment().getShieldId();
		//updateSheathing();
		switch(Sheathing) {
		case 26998:
			setWeapon(4151);
		break;
		case 28814:
			setWeapon(28813);
		break;
		case 27006:
			setWeapon(11694);
		break;
		case 27007:
			setWeapon(11696);
		break;
		case 27008:
			setWeapon(11698);
		break;
		case 27009:
			setWeapon(11700);
		break;
		case 27000:
			setWeapon(18349);
		break;
		case 27002:
			setWeapon(18351);
		break;
		case 27001:
			setWeapon(18353);
		break;
		case 27004:
			setWeapon(18357);
		break;
		case 27005:
			setWeapon(18355);
		break;
		/**
		 * Staffs
		 */
		case 27014:
			setWeapon(15486);
			break;
		case 27015:
			setWeapon(22494);
			break;
		case 27016:
			setWeapon(21777);
			break;
		case 27017:
			setWeapon(4710);
			break;
		}
		switch(Shielding) {
		case 27010:
			setShield(13740);
		break;
		case 27030: //bottom
		setShield(13738); //top
		break;
		case 27031: //bottom
		setShield(13742); //top
		break;
		case 27032: //bottom
		setShield(13744); //top
		break;
		case 27034: //bottom
		setShield(18747); //top
		break;
		case 27035: //bottom
		setShield(12915); //top
		break;
		case 27036: //bottom
		setShield(10352); //top
		break;
		case 27037: //bottom
		setShield(19345); //top
		break;
		case 27038: //bottom
		setShield(19340); //top
		break;
		case 27039: //bottom
		setShield(11283); //top
		break;
		case 27040: //bottom
		setShield(3122); //top
		break;
		case 27041: //bottom
		setShield(2890); //top
		break;
		case 27042: //bottom
		setShield(1187); //top
		break;
		case 27043: //bottom
		setShield(6524); //top
		break;
		case 27044: //bottom
		setShield(17361); //top
		break;
		case 27045: //bottom
		setShield(18359); //top
		break;
		case 27046: //bottom
		setShield(1189); //top
		break;
		case 27047: //bottom
		setShield(1173); //top
		break;
		case 27048: //bottom
		setShield(1175); //top
		break;
		case 27049: //bottom
		setShield(1191); //top
		break;
		case 27050: //bottom
		setShield(1177); //top
		break;
		case 27051: //bottom
		setShield(1193); //top
		break;
		case 27052: //bottom
		setShield(1199); //top
		break;
		case 27053: //bottom
		setShield(1183); //top
		break;
		case 27054: //bottom
		setShield(2603); //top
		break;
		case 27055: //bottom
		setShield(2611); //top
		break;
		case 27056: //bottom
		setShield(25013); //top
		break;
		case 27057: //bottom
		setShield(25019); //top
		break;
		case 27058: //bottom
		setShield(2589); //top
		break;
		case 27059: //bottom
		setShield(1195); //top
		break;
		case 27060: //bottom
		setShield(2597); //top
		break;
		case 27061: //bottom
		setShield(1179); //top
		break;
		case 27062: //bottom
		setShield(1540); //top
		break;
		case 27063: //bottom
		setShield(25800); //top
		break;
		case 27064: //bottom
		setShield(25796); //top
		break;
		case 27065: //bottom
		setShield(25794); //top
		break;
		case 27066: //bottom
		setShield(25798); //top
		break;
		case 27067: //bottom
		setShield(1191); //top
		break;
		case 27068: //bottom
		setShield(1197); //top
		case 27069: //bottom
		setShield(2667); //top
		break;
		case 27070: //bottom
		setShield(19440); //top
		break;
		case 27071: //bottom
		setShield(3488); //top
		break;
		case 27072: //bottom
		setShield(1185); //top
		break;
		case 27073: //bottom
		setShield(2629); //top
		break;
		case 27074: //bottom
		setShield(12929); //top
		break;
		case 27075: //bottom
		setShield(1201); //top
		break;
		case 27076: //bottom
		setShield(19425); //top
		break;
		case 27077: //bottom
		setShield(2675); //top
		break;
		case 27078: //bottom
		setShield(2659); //top
		break;
		case 27079: //bottom
		setShield(19410); //top
		break;
		case 27080: //bottom
		setShield(2621); //top
		break;
		case 27081: //bottom
		setShield(6633); //top
		break;
		case 27082: //bottom
		setShield(6631); //top
		break;
		case 27083: //bottom
		setShield(25855); //top
		break;
		case 27084: //bottom
		setShield(25804); //top
		break;
		case 27085: //bottom
		setShield(24365); //top
		break;
		}
	}

	public void setWeapon(int itemId) {
		getEquipment().deleteItem(itemId, 1);
		getEquipment().getItems().set(Equipment.SLOT_WEAPON, new Item(itemId, 1));
		getEquipment().refresh(Equipment.SLOT_WEAPON);
		getAppearence().generateAppearenceData();
	}

	public void setShield(int itemId) {
		getEquipment().getItems().set(Equipment.SLOT_SHIELD, new Item(itemId, 1));
		getEquipment().refresh(Equipment.SLOT_SHIELD);
		getAppearence().generateAppearenceData();
	}

	public void updateSheathing() {
		getEquipment().refresh(Equipment.SLOT_SHIELD);
		getEquipment().refresh(Equipment.SLOT_WEAPON);
		getAppearence().generateAppearenceData();
	}
	
	public PlayerData getPlayerData() {
		return playerData;
	}

	public void setPlayerData(PlayerData playerData) {
		this.playerData = playerData;
	}



	public boolean isVeteran() {
		// TODO Auto-generated method stub
		return false;
	}


	public void addGEAttribute(String string, int myBox) {
		// TODO Auto-generated method stub

	}

	public Object getGEAttribute(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeGEAttribute(String string) {
		// TODO Auto-generated method stub

	}
	
	public LodeStone getLodeStones() {
		return lodeStone;
	}
	
	public ReportAbuse getReportAbuse() {
		return reportAbuse;
		}
	
	public boolean[] getActivatedLodestones() {
		return activatedLodestones;
	}
	
	public List<String> getCachedChatMessages() {
		return cachedChatMessages;
	}

	public void setCachedChatMessages(List<String> cachedChatMessages) {
		this.cachedChatMessages = cachedChatMessages;
	}
	
    public Toolbelt getToolbelt() {
	return toolbelt;
    }
    
    /*
     * default items on death, now only used for wilderness
     */
    public void sendItemsOnDeath(Player killer, boolean dropItems) {
	Integer[][] slots = GraveStone.getItemSlotsKeptOnDeath(this, true, dropItems, getPrayer().isProtectingItem());
	sendItemsOnDeath(killer, new WorldTile(this), new WorldTile(this), true, slots);
    }
    
    /*
     * default items on death, now only used for wilderness
     */
	public void sendItemsOnDeath(Player killer) {
		if (rights == 2)
			return;
		charges.die();
		auraManager.removeAura();
		CopyOnWriteArrayList<Item> containedItems = new CopyOnWriteArrayList<Item>();
		for (int i = 0; i < 14; i++) {
			if (equipment.getItem(i) != null
					&& equipment.getItem(i).getId() != -1
					&& equipment.getItem(i).getAmount() != -1)
				containedItems.add(new Item(equipment.getItem(i).getId(),
						equipment.getItem(i).getAmount()));
		}
		for (int i = 0; i < 28; i++) {
			if (inventory.getItem(i) != null
					&& inventory.getItem(i).getId() != -1
					&& inventory.getItem(i).getAmount() != -1)
				containedItems.add(new Item(getInventory().getItem(i).getId(),
						getInventory().getItem(i).getAmount()));
		}
		if (containedItems.isEmpty())
			return;
		int keptAmount = 0;
		if (!(controlerManager.getControler() instanceof CorpBeastControler)
				&& !(controlerManager.getControler() instanceof CrucibleControler)) {
			keptAmount = hasSkull() ? 0 : 3;
			if (prayer.usingPrayer(0, 10) || prayer.usingPrayer(1, 0))
				keptAmount++;
		}
		if (donator && Utils.random(2) == 0)
			keptAmount += 1;
		CopyOnWriteArrayList<Item> keptItems = new CopyOnWriteArrayList<Item>();
		Item lastItem = new Item(1, 1);
		for (int i = 0; i < keptAmount; i++) {
			for (Item item : containedItems) {
				int price = item.getDefinitions().getValue();
				if (price >= lastItem.getDefinitions().getValue()) {
					lastItem = item;
				}
			}
			keptItems.add(lastItem);
			containedItems.remove(lastItem);
			lastItem = new Item(1, 1);
		}
		inventory.reset();
		equipment.reset();
		for (Item item : keptItems) {
			if (item.getId() == -1)
				continue;
			getInventory().addItem(item);
		}

		for (Item item : containedItems) {
				World.addGroundItem(item, getLastWorldTile(), killer, true, 2000000000);
		}
	}
    
	   public void sendItemsOnDeath(Player killer, WorldTile deathTile, WorldTile respawnTile, boolean wilderness, Integer[][] slots) {
	    	if(World.isMiniGame(deathTile)){
	    		//this.setNextWorldTile(new WorldTile(2649, 9393, 0));
	    		sendMessage("Since you died in the minigame area you will not lose your items");
	    		Nstage1 = 0;
	    		Nstage2 = 0;
	    		Nstage3 = 0;
	    	} else{
	    charges.die(slots[1], slots[3]); // degrades droped and lost items only
		auraManager.removeAura();
		Item[][] items = GraveStone.getItemsKeptOnDeath(this, slots);
		inventory.reset();
		equipment.reset();
		appearence.generateAppearenceData();
		for (Item item : items[0])
		    inventory.addItemDrop(item.getId(), item.getAmount(), respawnTile);
		if (items[1].length != 0) {
		    if (wilderness) {
			for (Item item : items[1])
			    World.addGroundItem(item, deathTile, killer == null ? this : killer, true, 60, 0);
		    } else
			new GraveStone(this, deathTile, items[1]);
		}
	    	}
	    }
    
    public int getGraveStone() {
	return gStone;
    }

    public void setGraveStone(int graveStone) {
	this.gStone = graveStone;
    }
    
    public void setClanChatSetup(int clanChatSetup) {
	this.clanChatSetup = clanChatSetup;
    }

    public void setGuestChatSetup(int guestChatSetup) {
	this.guestChatSetup = guestChatSetup;
    }
    
    public void kickPlayerFromClanChannel(String name) {
	if (clanManager == null)
	    return;
	clanManager.kickPlayerFromChat(this, name);
    }
    
    public void sendClanChannelMessage(ChatMessage message) {
	if (clanManager == null)
	    return;
	clanManager.sendMessage(this, message);
    }

    public void sendGuestClanChannelMessage(ChatMessage message) {
	if (guestClanManager == null)
	    return;
	guestClanManager.sendMessage(this, message);
    }

    public void sendClanChannelQuickMessage(QuickChatMessage message) {
	if (clanManager == null)
	    return;
	clanManager.sendQuickMessage(this, message);
    }

    public void sendGuestClanChannelQuickMessage(QuickChatMessage message) {
	if (guestClanManager == null)
	    return;
	guestClanManager.sendQuickMessage(this, message);
    }
    
    public int getClanStatus() {
	return clanStatus;
    }

    public void setClanStatus(int clanStatus) {
	this.clanStatus = clanStatus;
    }
    
    public ClansManager getClanManager() {
	return clanManager;
    }

    public void setClanManager(ClansManager clanManager) {
	this.clanManager = clanManager;
    }

    public ClansManager getGuestClanManager() {
	return guestClanManager;
    }

    public void setGuestClanManager(ClansManager guestClanManager) {
	this.guestClanManager = guestClanManager;
    }

    public String getClanName() {
	return clanName;
    }

    public void setClanName(String clanName) {
	this.clanName = clanName;
    }

    public boolean isConnectedClanChannel() {
	return connectedClanChannel;
    }

    public void setConnectedClanChannel(boolean connectedClanChannel) {
	this.connectedClanChannel = connectedClanChannel;
    }
    
    public GrandExchangeManager getGeManager() {
	return geManager;
    }
    
    public double[] getWarriorPoints() {
    	return warriorPoints;
        }

        public void setWarriorPoints(int index, double pointsDifference) {
    	warriorPoints[index] += pointsDifference;
    	if (warriorPoints[index] < 0) {
    	    Controler controler = getControlerManager().getControler();
    	    if (controler == null || !(controler instanceof WarriorsGuild))
    		return;
    	    WarriorsGuild guild = (WarriorsGuild) controler;
    	    guild.inCyclopse = false;
    	    setNextWorldTile(WarriorsGuild.CYCLOPS_LOBBY);
    	    warriorPoints[index] = 0;
    	} else if (warriorPoints[index] > 65535)
    	    warriorPoints[index] = 65535;
    	refreshWarriorPoints(index);
        }

        public void refreshWarriorPoints(int index) {
    	varsManager.sendVarBit(index + 8662, (int) warriorPoints[index]);
        }

        private void warriorCheck() {
    	if (warriorPoints == null || warriorPoints.length != 6)
    	    warriorPoints = new double[6];
        }
        
        public boolean containsOneItem(int... itemIds) {
        	if (getInventory().containsOneItem(itemIds))
        	    return true;
        	if (getEquipment().containsOneItem(itemIds))
        	    return true;
        	Familiar familiar = getFamiliar();
        	if (familiar != null && ((familiar.getBob() != null && familiar.getBob().containsOneItem(itemIds) || familiar.isFinished())))
        	    return true;
        	return false;
            }
        
        public int getFinishedCastleWars() {
        	return finishedCastleWars;
            }

            public boolean isCapturedCastleWarsFlag() {
        	return capturedCastleWarsFlag;
            }

            public void setCapturedCastleWarsFlag() {
        	capturedCastleWarsFlag = true;
            }

            public void increaseFinishedCastleWars() {
        	finishedCastleWars++;
            }
            
        	public WorldTile getTile() {
        		return new WorldTile(getX(), getY(), getPlane());
        	}
        	
            public void setVerboseShopDisplayMode(boolean verboseShopDisplayMode) {
            	this.verboseShopDisplayMode = verboseShopDisplayMode;
            	refreshVerboseShopDisplayMode();
                }

                public void refreshVerboseShopDisplayMode() {
            	varsManager.sendVarBit(11055, verboseShopDisplayMode ? 0 : 1);
                }
                
            	public BankPin getBankPin() {
            		return pin;
            	}

            	public boolean getSetPin() {
            		return setPin;
            	}

            	public boolean getOpenedPin() {
            		return openPin;
            	}

            	public int[] getPin() {
            		return bankpins;
            	}

            	public int[] getConfirmPin() {
            		return confirmpin;
            	}

            	public int[] getOpenBankPin() {
            		return openBankPin;
            	}

            	public int[] getChangeBankPin() {
            		return changeBankPin;
            	}

            	public void setCreationDate(long creationDate) {
            		this.creationDate = creationDate;
            	}

            	public long getCreationDate() {
            		return creationDate;
            	}
            	
            	
            	public void ecoReset() {
            		inventory.reset();
    			    moneyPouch = new MoneyPouch();
    			    moneyPouch.setPlayer(this);
    			    equipment.reset();
    			    toolbelt = new Toolbelt();
    			    toolbelt.setPlayer(this);
    			    familiar = null;
    			    bank = new Bank();
    			    bank.setPlayer(this);
    			    controlerManager.removeControlerWithoutCheck();
    			    choseGameMode = true;
    			    Starter.appendStarter(this);
    			    setNextWorldTile(Settings.START_PLAYER_LOCATION);
    			    setPestPoints(0);
    			    setDungTokens(0);
    			    getPlayerData().setInvasionPoints(0);
    			    hasHouse = false;
    			    getSquealOfFortune().resetSpins();
    			    customisedShop = new CustomisedShop(this);
    			    geManager = new GrandExchangeManager();
            	}
            	public void faceObject(WorldObject object) {
            		ObjectDefinitions objectDef = object.getDefinitions();
            		setNextFaceWorldTile(new WorldTile(object.getCoordFaceX(
            				objectDef.getSizeX(), objectDef.getSizeY(),
            				object.getRotation()), object.getCoordFaceY(
            				objectDef.getSizeX(), objectDef.getSizeY(),
            				object.getRotation()), object.getPlane()));
            	}
          
		

					
				
	
}
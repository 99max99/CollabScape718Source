package com.rs.game.player.actions.slayer;

/**
 * This is a list of assignable tasks
 * 
 * @author Emperial
 * 
 */
public enum SlayerTasks {
	/**
	 * TURAEL TASKS
	 */
        BANSHEES("Banshees", TaskSet.TURAEL, 15, 20, 40, "Banshee", "banshee", "Mighty banshee", "Mighty_banshee"), 
        CAVEBUGS("Cave Bugs", TaskSet.TURAEL, 1, 20, 40, "Cave bug"),
        CHICKENS("Chickens", TaskSet.TURAEL, 1, 20, 40, "Chicken"), 
        BEARS("Bears", TaskSet.TURAEL, 1, 20, 40, "Grizzly bear cub", "Grizzly bear", "Grizzly Bear", "Black Bear", "Bear Cub"), 
        COWS("Cows", TaskSet.TURAEL, 1, 20, 40, "Cow calf", "Cow"), 
        GOBLINS("Goblins", TaskSet.TURAEL, 1, 20, 40, "Goblin", "Cave goblin guard"), 
        ICEFIENDS("Icefiends", TaskSet.TURAEL, 1, 10, 20, "Icefiend"), 
        MINOTAURS("Minotaurs", TaskSet.TURAEL, 1, 20, 40, "Minotaur"), 
        ZOMBIES("Zombies", TaskSet.TURAEL, 1, 20, 40, "Zombie", "Armoured zombie"), 
        CRAWLING_HANDS("Crawling hands", TaskSet.TURAEL, 5, 5, 15,"Zombie hand", "Skeletal hand", "Crawling Hand"), 
        STS("Ghosts", TaskSet.TURAEL, 1, 20, 40, "Ghost", "ghost"), 
        BATS("Bats", TaskSet.TURAEL, 1, 20, 40, "Bat","Giant bat"), 
        DWARF("Dwarves", TaskSet.TURAEL, 1, 20, 40, "Dwarf", "Drunken dwarf", "Black Guard crossbowdwarf", "Black Guard berserker", "Chaos dwarf hand cannoneer"),
        SCORPIONS("Scorpions", TaskSet.TURAEL, 1, 20, 40, "Scorpion", "King scorpion", "Poison Scorpion", "Pit Scorpion", "Khazard scorpion", "Grave scorpion"),
        SKELETON("Skeletons", TaskSet.TURAEL, 1, 20, 40, "Skeleton", "Skeleton Mage", "Giant Skeleton"),
        TROLLS("Trolls", TaskSet.TURAEL, 1, 20, 40, "Mountain troll", "Ice troll", "River troll", "Sea troll", "Thrower Troll", "Stick", "Rock", "Pee Hat", "Kraka", "Troll general", "Troll spectator"),
        WOLVES("Wolves", TaskSet.TURAEL, 1, 20, 40, "Ice wolf", "Wolf", "Big wolf", "Big Wolf", "White Wolf"),
        BIRDS("Birds", TaskSet.TURAEL, 1, 20, 40, "Chicken", "Duck", "Terrorbird", "Chompy Bird", "Jubbly Bird"),
        MEN("Men and woman", TaskSet.TURAEL, 1, 10, 20, "Woman", "Man"), 
        ABOM("Gelatinous Abominations", TaskSet.TURAEL, 1, 5, 15, "Gelatinous Abomination", "Magic Stick"), 
        
        /*
         * Mazchina / Achtryn Tasks
         */
        BANSHEES2("Banshees", TaskSet.MAZ, 15, 20, 40, "Banshee", "Mighty banshee", "Mighty_banshee"),
        BATS2("Bats", TaskSet.MAZ, 1, 20, 40, "Bat","Giant bat"), 
        CAVECRAWLER("Cave Crawlers", TaskSet.MAZ, 1, 20, 40, "Cave crawler"), 
        COCKATRICE("Cockatrices", TaskSet.MAZ, 1, 20, 40, "Cockatrice"), 
        FC("Flesh Crawlers", TaskSet.MAZ, 1, 20, 40, "Flesh Crawler"),
        Ghoul("Ghouls", TaskSet.MAZ, 1, 20, 40, "Ghoul"),
        Ghost("Ghosts", TaskSet.MAZ, 1, 20, 40, "Ghost", "ghost"), 
        Grots("Grotworms", TaskSet.TURAEL, 1, 20, 40, "Young grotworm", "Grotworm", "Mature grotworm"), 
        Hill("Hill Giants", TaskSet.MAZ, 1, 20, 40, "Hill Giant"),
        Ghosts("Hobgoblins", TaskSet.MAZ, 1, 20, 40, "Hobgoblin"), 
        IceW("Ice warriors", TaskSet.MAZ, 1, 20, 40, "Ice warrior"),
        Kalphite("Kalphites", TaskSet.MAZ, 1, 20, 40, "Kalphite queen", "Kalphite larva", "Kalphite worker", "Kalphite soldier", "Kalphite guardian"), 
        Pyrefiend("Pyrefiends", TaskSet.MAZ, 30, 20, 40, "Pyrefiend"),
        Skeleton2("Skeletons", TaskSet.MAZ, 1, 20, 40, "Skeleton"),
        Zombie2("Zombies", TaskSet.MAZ, 1, 20, 40, "Zombie"),
        
        /*
         * Chaeldar Tasks
         */
        ABBYSPEC("Aberrant spectres", TaskSet.CHAELDAR, 60, 20, 40, "Aberrant spectre"),
        BANSHEES3("Banshees", TaskSet.CHAELDAR, 15, 20, 40, "Banshee", "banshee", "Mighty banshee", "Mighty_banshee"),
        Basilisks("Basilisks", TaskSet.CHAELDAR, 40, 20, 40, "Basilisk"),
        BloodVelds("Bloodvelds", TaskSet.CHAELDAR, 50, 20, 40, "Bloodveld"),
        Blue_Dragon("Blue Dragons", TaskSet.CHAELDAR, 1, 20, 40, "Blue dragon"),
        Bronze_Dragon("Bronze Dragons", TaskSet.CHAELDAR, 11, 20, 40, "Bronze dragon"),
        CC("Cave Crawlers", TaskSet.CHAELDAR, 10, 20, 40, "Cave crawler"),
        CH("Crawling Hands", TaskSet.CHAELDAR, 5, 20, 40, "Crawling hand", "Zombie hand", "Skeletal hand"),
        Dags("Dagganoths", TaskSet.CHAELDAR, 1, 20, 40, "Dagannoth", "Dagannoth Mother", "Dagannoth guardian", "Dagannoth spawn", "Dagannoth Prime", "Dagannoth Supreme", "Dagannoth Rex"),
        FG("Fire giants", TaskSet.CHAELDAR, 1, 20, 40, "Fire giant"),
        FUNG("Fungal Mages", TaskSet.CHAELDAR, 1, 20, 40, "Fungal mage", "Fungal magi"),
        Garg("Gargoyles", TaskSet.CHAELDAR, 75, 20, 40, "Gargoyle"),
        Grots2("Grotworms", TaskSet.CHAELDAR, 1, 20, 40, "Young grotworm", "Grotworm", "Mature grotworm"),
        JS("Jungle Strykewyrms", TaskSet.CHAELDAR, 73, 20, 40, "Jungle strykewyrm"),
        IMages("Infernal mages", TaskSet.CHAELDAR, 45, 20, 40, "Infernal Mage", "Infernal_Mage"),
        JELLY("Jellies", TaskSet.CHAELDAR, 52, 20, 40, "Jelly"),
        Kalphite2("Kalphites", TaskSet.CHAELDAR, 1, 20, 40, "Kalphite queen", "Kalphite larva", "Kalphite worker", "Kalphite soldier", "Kalphite guardian"), 
        LDemon("Lesser Demons", TaskSet.CHAELDAR, 1, 20, 40, "Lesser demon"),
        Turoth("Turoths", TaskSet.CHAELDAR, 55, 20, 40, "Turoth"),
        ANKOU("Ankou", TaskSet.CHAELDAR, 1, 30, 40, "Ankou"),
        OGRES("Ogres", TaskSet.CHAELDAR, 1, 20, 30, "Ogre", "Zogre", "Skogre", "Jogre"),
        COCKROACHES("Cockroaches", TaskSet.CHAELDAR, 1, 20, 30, "Cockroach drone", "Cockroach worker", "Cockroach soldier", "Cockroach_soldier", "Cockroach_worker", "Cockroach_drone"),
        BRINERATS("Brine Rats", TaskSet.CHAELDAR, 47, 25, 50, "brine rat", "Brine rat", "Brine_rat", "brine_rat"),
        /*
         * Duradel Tasks
         */
        ABBYSPEC2("Aberrant spectres", TaskSet.DURADEL, 60, 20, 40, "Aberrant spectre"),
        ABYDEMON("Abyssal demons", TaskSet.DURADEL, 85, 20, 40, "Ayssal demon"),
        BDemon("Black demons", TaskSet.DURADEL, 1, 20, 40, "Black demon"),
        BDragon("Black dragons", TaskSet.DURADEL, 1, 20, 40, "Black dragon"),
        BVElds("Bloodvelds", TaskSet.DURADEL, 50, 20, 40, "Bloodveld"),
        Dags2("Dagganoths", TaskSet.DURADEL, 1, 20, 40, "Dagannoth", "Dagannoth Mother", "Dagannoth guardian", "Dagannoth spawn", "Dagannoth Prime", "Dagannoth Supreme", "Dagannoth Rex"),
        DBeast("Dark beasts", TaskSet.DURADEL, 90, 20, 40, "Dark beast"),
        DStryke("Desert strykewyrms", TaskSet.DURADEL, 77, 20, 40, "Desert strykewyrms"),
        DDevil("Dust devil", TaskSet.DURADEL, 65, 20, 40, "Dust devil"),
        FGiant("Fire giants", TaskSet.DURADEL, 1, 20, 40, "Fire giant"),
        FUNG2("Fungal Mages", TaskSet.DURADEL, 1, 20, 40, "Fungal mage", "Fungal magi"),
        GCreatures("Ganodermic beasts", TaskSet.DURADEL, 95, 20, 40, "Ganodermic beast"),
        garg2("Gargoyles", TaskSet.DURADEL, 75, 20, 40, "Gargoyle"),
        GDEmons("Greater demons", TaskSet.DURADEL, 1, 20, 40, "Greater demon"),
        Grif("Grifalopines", TaskSet.DURADEL, 88, 20, 40, "Grifalopine"),
        Grif2("Grifaloroo", TaskSet.DURADEL, 82, 20, 40, "Grifaloroo"),
        Grots3("Grotworms", TaskSet.DURADEL, 1, 20, 40, "Young grotworm", "Grotworm", "Mature grotworm"),
        HH("Hellhounds", TaskSet.DURADEL, 1, 20, 40, "Hellhound"),
        IStryke("Ice strykewyrm", TaskSet.DURADEL, 93, 20, 40, "Ice strykewyrm"),
        IDrag("Iron dragons", TaskSet.DURADEL, 1, 20, 40, "Iron dragon"),
        MDragon("Mithril dragons", TaskSet.DURADEL, 1, 20, 40, "Mithril dragon"),
        MJadinkos("Mutated jadinkos", TaskSet.DURADEL, 80, 20, 40, "Mutated jadinko baby", "Mutated jadinko guard", "Mutated jadinko male"),
        Nech("Nechryaels", TaskSet.DURADEL, 80, 20, 40, "Nechryael"),
        SWyvern("Skeletal Wyverns", TaskSet.DURADEL, 72, 20, 40, "Skeletal Wyvern"),
        SMage("Spiritual Mages", TaskSet.DURADEL, 83, 20, 40, "Spiritual mage"),
        SDragon("Steel Dragons", TaskSet.DURADEL, 1, 20, 40, "Steel dragon"),
        WFiends("Waterfiends", TaskSet.DURADEL, 1, 20, 40, "Waterfiend"),
        OGRES2("Ogres", TaskSet.DURADEL, 1, 25, 30, "Ogre", "Zogre", "Skogre", "Jogre"),
        ANKOU2("Ankou", TaskSet.DURADEL, 1, 35, 45, "Ankou"),
        COCKROACHES2("Cockroaches", TaskSet.DURADEL, 1, 20, 30, "Cockroach drone", "Cockroach worker", "Cockroach soldier", "Cockroach_soldier", "Cockroach_worker", "Cockroach_drone"),
        
        /*
         * Kuradel
         */
        ABBYSPEC3("Aberrant spectres", TaskSet.KURADEL, 60, 20, 40, "Aberrant spectre"),
        ABYDEMON2("Abyssal demons", TaskSet.KURADEL, 85, 20, 40, "Ayssal demon"),
        BDemon2("Black demons", TaskSet.KURADEL, 1, 20, 40, "Black demon"),
        BDragon2("Black dragons", TaskSet.KURADEL, 1, 20, 40, "Black dragon"),
        Blue_Dragon2("Blue Dragons", TaskSet.KURADEL, 1, 20, 40, "Blue dragon"),
        BVElds2("Bloodvelds", TaskSet.KURADEL, 50, 20, 40, "Bloodveld"),
        Dags3("Dagganoths", TaskSet.KURADEL, 1, 20, 40, "Dagannoth", "Dagannoth Mother", "Dagannoth guardian", "Dagannoth spawn", "Dagannoth Prime", "Dagannoth Supreme", "Dagannoth Rex"),
        DBeast2("Dark beasts", TaskSet.KURADEL, 90, 20, 40, "Dark beast"),
        DStryke2("Desert strykewyrms", TaskSet.KURADEL, 77, 20, 40, "Desert strykewyrms"),
        DDevil2("Dust devil", TaskSet.KURADEL, 65, 20, 40, "Dust devil"),
        FGiant2("Fire giants", TaskSet.KURADEL, 1, 20, 40, "Fire giant"),
        GCreatures2("Ganodermic beasts", TaskSet.KURADEL, 95, 20, 40, "Ganodermic beast"),
        garg3("Gargoyles", TaskSet.KURADEL, 75, 20, 40, "Gargoyle"),
        GDEmons2("Greater demons", TaskSet.KURADEL, 1, 20, 40, "Greater demon"),
        Grif4("Grifalopines", TaskSet.KURADEL, 88, 20, 40, "Grifalopine"),
        Grif3("Grifaloroo", TaskSet.KURADEL, 82, 20, 40, "Grifaloroo"),
        Grots4("Grotworms", TaskSet.KURADEL, 1, 20, 40, "Young grotworm", "Grotworm", "Mature grotworm"),
        HH2("Hellhounds", TaskSet.KURADEL, 1, 20, 40, "Hellhound"),
        IStryke3("Ice strykewyrm", TaskSet.KURADEL, 93, 20, 40, "Ice strykewyrm"),
        IDrag2("Iron dragons", TaskSet.KURADEL, 1, 20, 40, "Iron dragon"),
        LRC("Living rock creatures", TaskSet.KURADEL, 1, 20, 40, "Living rock protector", "Living rock striker", "Living rock patriarch"),
        MDragon2("Mithril dragons", TaskSet.KURADEL, 1, 20, 40, "Mithril dragon"),
        MJadinkos2("Mutated jadinkos", TaskSet.KURADEL, 80, 20, 40, "Mutated jadinko baby", "Mutated jadinko guard", "Mutated jadinko male"),
        Nech2("Nechryaels", TaskSet.KURADEL, 80, 20, 40, "Nechryael"),
        SWyvern2("Skeletal Wyverns", TaskSet.KURADEL, 72, 20, 40, "Skeletal Wyvern"),
        SMage2("Spiritual Mages", TaskSet.KURADEL, 83, 20, 40, "Spiritual mage"),
        SDragon2("Steel Dragons", TaskSet.KURADEL, 1, 20, 40, "Steel dragon"),
        OGRES3("Ogres", TaskSet.KURADEL, 1, 30, 40, "Ogre", "Zogre", "Skogre", "Jogre"),
        ANKOU3("Ankou", TaskSet.KURADEL, 1, 40, 45, "Ankou"),
        WFiends2("Waterfiends", TaskSet.KURADEL, 1, 20, 40, "Waterfiend"),
        COCKROACHES3("Cockroaches", TaskSet.KURADEL, 1, 30, 40, "Cockroach drone", "Cockroach worker", "Cockroach soldier", "Cockroach_soldier", "Cockroach_worker", "Cockroach_drone"),
        BORK("Bork", TaskSet.KURADEL, 95, 1, 1, "Bork", "bork"),
		KBD("King Black Dragon", TaskSet.KURADEL, 95, 1, 1, "King black dragon", "King_black_dragon"),
		CORPBEAST("Corporeal Beast", TaskSet.KURADEL, 95, 1, 1, "Corporeal Beast", "Corporeal_Beast", "Corporeal_beast", "Corporeal beast"),
        QUEEN("Kalphite Queen", TaskSet.KURADEL, 95, 1, 1, "Kalphite Queen", "Kalphite_Queen", "Kalphite queen", "Kalphite_queen");
        
	private SlayerTasks(String simpleName, TaskSet type, int level, int min, int max,
			String... monsters) {
		this.type = type;
		this.slayable = monsters;
		this.simpleName = simpleName;
                this.level = level;
		this.min = min;
		this.max = max;
        }

	/**
	 * A simple name for the task
	 */
	public String simpleName;

	/**
	 * The task set
	 */
	public TaskSet type;
	/**
	 * The monsters that will effect this task
	 */
	public String[] slayable;
	/**
	 * The minimum amount of monsters the player may be assigned to kill
	 */
	public int min;
	/**
	 * The maximum amount of monsters the player may be assigned to kill
	 */
	public int max;
        
        /*
         * Slayer level for monsters
         */
        public int level;
        
        public int getLevel() {
            return level;
        }
}

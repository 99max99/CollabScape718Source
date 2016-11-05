package com.rs.game.player.dialogues.draynor;

import com.rs.game.player.dialogues.Dialogue;


/**
 * 
 * @author Josh - Jul 14 2013
 * 
 * Aggie the witch Dialogue
 * 
 */
public class Aggie extends Dialogue {
    
    int npcId;
    
    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendPlayerDialogue(9827, "Hello Aggie!");
    }
    
    @Override
    public void run(int interfaceId, int componentId) {
        switch(stage) {
        case -1:
        	stage = 0;
        	sendOptionsDialogue("Select an Option", "Hey you're a witch aren't you?", "So what is actually in that cauldron?",
                    "What could you make for me?", "Can you make dyes for me please?");
        	break;
        case 0:
        	if(componentId == OPTION_1) {
                sendPlayerDialogue(9827, "Hey you're a witch aren't you?");
                stage = 1;
                break;
        	} else if(componentId == OPTION_2) {
                sendPlayerDialogue(9827, "So what is actually in that cauldron?");
                stage = 4;
                break;
        	} else if(componentId == OPTION_3) {
                sendPlayerDialogue(9827, "What could you make for me?");
                stage = 5;
                break;
        	} else if(componentId == OPTION_4) {
                sendPlayerDialogue(9827, "Can you make dyes for me please?");
                stage = 30;
                break;
            }
        case 1:
            sendNPCDialogue(npcId, 9827, "My, you're observant!");
            stage = 2;
            break;
        case 2:
            sendPlayerDialogue(9827, "Cool do you turn people into frogs?");
            stage = 3;
            break;
        case 3:
            sendNPCDialogue(npcId, 9827, "Oh, not for years. But if you meet a talking chicken, you",
                    "have probably met the professor in the manor north of",
                    "here. A few years ago it was flying fish. That machine is a",
                    "menace.");
            stage = 99;
        case 4:
            sendNPCDialogue(npcId, 9827, "You don't really expect me to give away trade secrets, do you?");
            stage = 99;
            break;
        case 5:
            sendNPCDialogue(npcId, 9827, "I mostly just make what I find pretty. I sometimes make",
                    "dye for the women's clothes to brighten the place up. I",
                    "can make red, yellow and blue dyes.If you'd like some,",
                    "just bring me the appropriate ingredients.");
            stage = 6;
        case 6:
            sendOptionsDialogue("Select an Option", "What do you need to make red dye?",
                    "What do you need to make yellow dye?",
                    "What do you need to make blue dye?",
                    "No thanks, I am happy the colour I am.");
            stage = 7;
        case 7:
            if(componentId == OPTION_1) {
                sendPlayerDialogue(9827, "What do you need to make red dye?");
                stage = 8;
                break;
            } else if(componentId == OPTION_2) {
                sendPlayerDialogue(9827, "What do you need to make yellow dye?");
                stage = 20;
                break;
            } else if(componentId == OPTION_3) {
                sendPlayerDialogue(9827, "What do you need to make blue dye?");
                stage = 25;
                break;
            } else if(componentId == OPTION_4) {
                sendPlayerDialogue(9827, "No thanks, I am happy the colour I am.");
                stage = 31;
                break;
            }
        case 8:
            sendNPCDialogue(npcId, 9827, "3 lots of redberries and 5 coins to you.");
            stage = 9;
            break;
        case 9:
            sendOptionsDialogue("Select an Option", "Could you make me some red dye, please?",
                    "I don't think I have all the ingredients yet.",
                    "I can do without dye at that price.",
                    "Where can I get redberries?",
                    "What other colours can you make?");
            stage = 10;
            break;
        case 10:
        	if(componentId == OPTION_1) {
                sendPlayerDialogue(9827, "Could you make me some red dye, please?");
                stage = 11;
                break;
        	} else if(componentId == OPTION_2) {
                sendPlayerDialogue(9827, "I don't think I have all the ingredients yet.");
                stage = 12;
                break;
        	} else if(componentId == OPTION_3) {
                sendPlayerDialogue(9827, "I can do without dye at that price.");
                stage = 13;
                break;
        	} else if(componentId == OPTION_4) {
                sendPlayerDialogue(9827, "Where can I get redberries?");
                stage = 14;
                break;
        	} else if(componentId == OPTION_5) {
                sendPlayerDialogue(9827, "What other colours can you make?");
                stage = 17;
                break;
            }
        case 11:
            if (!player.getInventory().containsItem(1951, 3)) {
                sendDialogue("You don't have enough berries to make the red dye.");
                stage = 99;
                break;
            }
        case 12:
            sendNPCDialogue(npcId, 9827, "You know what you need to get, now come back when you",
                    "have them. Goodbye for now.");
            stage = 99;
            break;
        case 13:
            sendNPCDialogue(npcId, 9827, "Thats your choice, but I would think you have killed for",
                    "less. I can see it in your eyes.");
            stage = 99;
            break;
        case 14:
            sendNPCDialogue(npcId, 9827, "I pick mine from the woods south of Varrock. The food",
                    "shop in Port Sarim sometimes has them as well.");
            stage = 15;
            break;
        case 15:
            sendOptionsDialogue("Select an Option", "What other colours can you make?", "Thanks");
            stage = 16;
            break;
        case 16:
            if(componentId == OPTION_1) {
                sendPlayerDialogue(9827, "What other colours can you make?");
                stage = 17;
                break;
            } else if(componentId == OPTION_2) {
                sendPlayerDialogue(9827, "Thanks");
                stage = 99;
                break;
            }
        case 17:
            sendNPCDialogue(npcId, 9827, "Red, yellow and blue. Which one would you like?");
            stage = 18;
            break;
        case 18:
            sendOptionsDialogue("Select an Option", "What do you need to make red dye?",
                    "What do you need to make yellow dye?",
                    "What do you need to make blue dye?");
            stage = 19;
            break;
        case 19:
        	if(componentId == OPTION_1) {
                sendPlayerDialogue(9827, "What do you need to make red dye?");
                stage = 8;
                break;
        	} else if(componentId == OPTION_2) {
                sendPlayerDialogue(9827, "What do you need to make yellow dye?");
                stage = 20;
                break;
        	} else if(componentId == OPTION_3) {
                sendPlayerDialogue(9827, "What do you need to make blue dye?");
                stage = 25;
                break;
            }
        case 20:
            sendNPCDialogue(npcId, 9827, "Yellow is a strange colour to get, comes from onion skins",
                    "I need 2 onions and 5 coins to make yellow dye.");
            stage = 21;
            break;
        case 21:
            sendOptionsDialogue("Select an Option", "Could you make me some yellow dye, please?",
                    "I don't think I have all the ingredients yet.",
                    "I can do without dye at that price.",
                    "Where can I get onions?",
                    "What other colours can you make?");
            stage = 22;
            break;
        case 22:
        	if(componentId == OPTION_1) {
                sendPlayerDialogue(9827, "Could you make me some yellow dye, please?");
                stage = 23;
                break;
        	} else if(componentId == OPTION_2) {
                sendPlayerDialogue(9827, "I don't think I have all the ingredients yet.");
                stage = 12;
                break;
        	} else if(componentId == OPTION_3) {
                sendPlayerDialogue(9827, "I can do without dye at that price.");
                stage = 13;
                break;
        	} else if(componentId == OPTION_4) {
                sendPlayerDialogue(9827, "Where can I get onions?");
                stage = 24;
                break;
        	} else if(componentId == OPTION_5) {
                sendPlayerDialogue(9827, "What other colours can you make?");
                stage = 19;
                break;
            }
        case 23:
            if (!player.getInventory().containsItem(1957, 2)) {
                sendDialogue("You don't have enough onions to make the yellow dye.");
                stage = 99;
                break;
            }
        case 24:
            sendNPCDialogue(npcId, 9827, "There are some onions growing on a farm to the East of",
                    "here, next to the sheep field.");
            stage = 15;
            break;
        case 25:
            sendNPCDialogue(npcId, 9827, "2 Woad leaves and 5 coins to you");
            stage = 26;
            break;
        case 26:
            sendOptionsDialogue("Select an Option", "Could you make me some blue dye, please?",
                    "I don't think I have all the ingredients yet.",
                    "I can do without dye at that price.",
                    "Where can I get woad leaves?",
                    "What other colours can you make?");
            stage = 27;
            break;
        case 27:
        	if(componentId == OPTION_1) {
                sendPlayerDialogue(9827, "Could you make me some blue dye, please?");
                stage = 28;
                break;
        	} else if(componentId == OPTION_2) {
                sendPlayerDialogue(9827, "I don't think I have all the ingredients yet.");
                stage = 12;
                break;
        	} else if(componentId == OPTION_3) {
                sendPlayerDialogue(9827, "I can do without dye at that price.");
                stage = 13;
                break;
        	} else if(componentId == OPTION_4) {
                sendPlayerDialogue(9827, "Where can I get woad leaves?");
                stage = 29;
                break;
        	} else if(componentId == OPTION_5) {
                sendPlayerDialogue(9827, "What other colours can you make?");
                stage = 19;
                break;
            }
        case 28:
            if (!player.getInventory().containsItem(1793, 2)) {
                sendDialogue("You don't have enough onions to make the yellow dye.");
                stage = 99;
                break;
            }
        case 29:
            sendNPCDialogue(npcId, 9827, "Woad leaves are fairly hard to find. My other customers",
                    "tell me the chief gardner in Falador grows them.");
            stage = 15;
            break;
        case 30: 
            sendNPCDialogue(npcId, 9827, "What sort of dye would you like? Red, yellow or blue?");
            stage = 18;
            break;
        case 31:
            sendNPCDialogue(npcId, 9827, "You are easily pleased with yourself then.",
                    "When you need dyes, come to me.");
            stage = 99;
            break;
        case 99:
            player.getDialogueManager().finishDialogue();
            break;
        }
    }
    
    public void finish() {
        
    }
}
package com.rs.game.player.dialogues.varrock;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class SawMillOperator extends Dialogue {

    private int npcId;

    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendNPCDialogue(npcId, 9827, "Hello. Do you want me to make some planks for you",
                "or would you be interested in some other housing supplies?");
    }

    @Override
    public void run(int interfaceId, int componentId) {
        switch (stage) {
            case -1:
                sendOptionsDialogue("Sawmill operator", "Planks, please.", "What kind of planks can you make?", "Can I buy some housing supplies?", "Nothing, thanks.");
                stage = 1;
                break;
            case 1:
                switch (componentId) {
                    case OPTION_1:
                        sendPlayerDialogue(9827, "Planks, please.");
                        stage = 2;
                        break;
                    case OPTION_2:
                        sendPlayerDialogue(9827, "What kind of planks can you make?");
                        stage = 4;
                        break;
                    case OPTION_3:
                        ShopsHandler.openShop(player, 130);
                        end();
                        break;
                    case OPTION_4:
                        sendPlayerDialogue(9827, "Nothing, thanks.");
                        stage = 6;
                        break;
                    default:
                        stage = 100;
                        break;
                }
                break;
            case 2:
                sendNPCDialogue(npcId, 9827, "What kind of planks do you want?");
                stage = 3;
                break;
            case 3:
                player.getInterfaceManager().sendInterface(403);
                end();
                break;
            case 4:
                sendNPCDialogue(npcId, 9827, "I can make planks from wood, oak, teak and mahogany. I",
                        "don't make planks from other woods as they're no good",
                        "for making furniture.");
                stage = 5;
                break;
            case 5:
                sendNPCDialogue(npcId, 9827, "Wood and oak are all over the place, but teak and",
                        "mahogany can only be found in a few places like Karamja",
                        "and Etceteria.");
                stage = 100;
                break;
            case 6:
                sendNPCDialogue(npcId, 9827, "Well, come back when you want some. You can't get good",
                        "quality planks anywhere but here!");
                stage = 100;
                break;
            case 100:
                end();
                break;
            default:
                stage = 100;
                break;
        }
    }

    @Override
    public void finish() {
    }
}
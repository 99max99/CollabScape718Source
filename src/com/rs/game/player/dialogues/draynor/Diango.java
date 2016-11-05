package com.rs.game.player.dialogues.draynor;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class Diango extends Dialogue
{
  int npcId;

  public void start()
  {
    this.npcId = ((Integer)this.parameters[0]).intValue();
    sendNPCDialogue(this.npcId, 9827, new String[] { "Howdy there, partner." });
  }

  public void run(int interfaceId, int componentId)
  {
    switch (this.stage) {
    case -1:
      sendPlayerDialogue(9827, new String[] { "Hi, Diango." });
      this.stage = 1;
      break;
    case 1:
      sendNPCDialogue(this.npcId, 9827, new String[] { "Want to see my spinning plates or kites? or do you ", 
        "want to check out my range of party items?" });
      this.stage = 2;
      break;
    case 2:
      sendOptionsDialogue("Select an Option", new String[] { "Spinning plates?", "Wow, a kite!", "Party items, you say?" });
      this.stage = 3;
      break;
    case 3:
      switch (componentId) {
      case 11:
        sendPlayerDialogue(9827, new String[] { "Spinning plates?" });
        this.stage = 4;
        break;
      case 13:
        sendPlayerDialogue(9827, new String[] { "Wow, a kite!" });
        this.stage = 7;
        break;
      case 14:
        sendPlayerDialogue(9827, new String[] { "Party items, you say?" });
        this.stage = 13;
      case 12:
      }
    case 4:
      sendNPCDialogue(this.npcId, 9827, new String[] { "That's right. There's a funny story behind them, their", 
        "shipment was held up by thieves." });
      this.stage = 5;
      break;
    case 5:
      sendNPCDialogue(this.npcId, 9827, new String[] { "The crate was marked 'Dragon Plates'.", 
        "Apparently they thought it was some kind of armour,", 
        "when really it's just a plate with a dragon on it!" });
      this.stage = 6;
      break;
    case 6:
      ShopsHandler.openShop(this.player, 101);
      this.player.getDialogueManager().finishDialogue();
      break;
    case 7:
      sendNPCDialogue(this.npcId, 9827, new String[] { "You're not the first to say that..." });
      this.stage = 8;
      break;
    case 8:
      sendPlayerDialogue(9827, new String[] { "Can I have one, please?" });
      this.stage = 9;
      break;
    case 9:
      sendNPCDialogue(this.npcId, 9827, new String[] { "Well, I suppose I did order more than I need... It's yours", 
        "for the bargain price of 100 coins." });
      this.stage = 10;
      break;
    case 10:
      sendOptionsDialogue("Select an Option", new String[] { "That's a bargain! I'll take one.", "No, thanks, I don't want one." });
      this.stage = 11;
      break;
    case 11:
      switch (componentId) {
      case 11:
        sendPlayerDialogue(9827, new String[] { "That's a bargain! I'll take one.", "No, thanks, I don't want one." });
        this.stage = 12;
        break;
      case 13:
        sendPlayerDialogue(9827, new String[] { "No, thanks, I don't want one." });
        this.stage = 99;
      case 12:
      }
    case 12:
      ShopsHandler.openShop(this.player, 101);
      this.player.getDialogueManager().finishDialogue();
      break;
    case 13:
      sendNPCDialogue(this.npcId, 9827, new String[] { "Yep! In a partnership with my pal Party Pete, we've", 
        "decided to launch a whole range of partyware to help", 
        "celebrate the Royal Wedding of the King Black Dragon and", 
        "the Kalphite Queen. Confetti, bubble makers, fireworks," });
      this.stage = 14;
      break;
    case 14:
      sendNPCDialogue(this.npcId, 9827, new String[] { "firecrackers. We even have a commemorative mug!" });
      this.stage = 15;
      break;
    case 15:
      sendPlayerDialogue(9827, new String[] { "King Black Dragon and Kalphite... Wait, what?" });
      this.stage = 16;
      break;
    case 16:
      sendNPCDialogue(this.npcId, 9827, new String[] { "Er, well, that's what I was told; perhaps our providers got", 
        "it wrong. Mind you, it wouldn't be the first time. Like, this,", 
        "once, we were due to recieve a shipment of dragonmail,", 
        "and we got a pile of slightly singed letters instead." });
      this.stage = 17;
      break;
    case 17:
      sendNPCDialogue(this.npcId, 9827, new String[] { "Anyway, we've got all those items on sale, and I even have", 
        "a suitable bouquet avaliable for those who want to", 
        "emulate the happy bride!" });
      this.stage = 18;
      break;
    case 18:
      sendPlayerDialogue(9827, new String[] { "She had a bouquet? The Kalphite Queen? Big bug, beady-", 
        "eyes, occasionally flies about when it's not laying eggs?", 
        "Are we talking about the same creature?" });
      this.stage = 19;
      break;
    case 19:
      sendNPCDialogue(this.npcId, 9827, new String[] { "Listen, partner, I'm not the one writing the advertising", 
        "pitch here. I got the goods and been told what to say in", 
        "order to sell them. It's not the most convincing pitch I've", 
        "ever had to do, but, if you ask me, the products don't" });
      this.stage = 20;
      break;
    case 20:
      sendNPCDialogue(this.npcId, 9827, new String[] { "need a pitch. They speak for themselves. Have a butchers." });
      this.stage = 12;
      break;
    case 99:
      this.player.getDialogueManager().finishDialogue();
    }
  }

  public void finish()
  {
  }
}
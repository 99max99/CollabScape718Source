package com.rs.game.player;

import com.rs.utils.Censor;
import com.rs.utils.Utils;

public class ChatMessage {

    private String message;
    private String filteredMessage;

    public ChatMessage(String message) {
	    filteredMessage = Censor.getFilteredMessage(message);
	    this.message = Utils.fixChatMessage(message);
    }

    public String getMessage(boolean filtered) {
	return filtered ? filteredMessage : message;
    }
}

package com.rs.game.player.content;

import java.io.Serializable;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

/**
 * Handles Cooperative Slayer and all necessary components.
 * @author Jae
 * @modified Danny
 * @since Dec 7, 2013
 * @version 1.0
 */
public class CooperativeSlayer implements Serializable {
			
	private static final long serialVersionUID = -5833463661237303707L;
	
	public static int bonusXP = Utils.getRandom(3) + 2;
	
	public void handleLogout(Player player) {
		Player newPartner;
		String host;
		host = player.getSlayerHost();
		newPartner = World.getPlayerByDisplayName(host);
		if (player.hasOngoingInvite == true) {
			player.hasHost = false;
			player.hasOngoingInvite = false;
			newPartner.hasInvited = false;
			newPartner.sm("<col=C43140>" + player.getUsername() + " has logged out, your invite request has been deleted.</col>");
		}
	}
	
	public void sendInvite(final Player player) {
		player.sm("<col=BF00C9>You have received a slayer invitation from " + player.getSlayerHost() + "</col>");
		player.hasHost = true;
		player.getInterfaceManager().sendInterface(1008);
		//player.getPackets().hideComponent(1008, 30, true);
		player.getPackets().sendIComponentText(1008, 18, "Slayer Invitation");
		player.getPackets().sendIComponentText(1008, 23, "You have been invited to a Slayer group by "
				+ player.getSlayerHost() + ". Slayer kills and experience will be shared between the two of you. You cannot change your slayer partner.");
	}

	public void handleInviteButtons(Player invited, int interfaceId, int componentId) {
		Player newPartner;
		String host;
		host = invited.getSlayerHost();
		newPartner = World.getPlayerByDisplayName(host);
		if (interfaceId == 1008) {
			switch (componentId) {
			case 29:
				invited.sm("<col=22C78D>Accepted " + invited.getSlayerHost() + "'s invitation, you are now their Slayer partner.</col>");
				invited.setSlayerPartner(host);
				invited.hasGroup = true;
				invited.hasOngoingInvite = false;
				newPartner.sm("<col=22C78D>Your invitation to " + newPartner.getSlayerInvite() + " has been accepted, you are now their slayer partner.</col>");
				newPartner.setSlayerPartner(newPartner.getSlayerInvite());
				newPartner.hasGroup = true;
				break;
			case 28:
				invited.sm("<col=C43140>You have declined " + invited.getSlayerHost() + "'s invitation.</col>");
				newPartner.sm("<col=C43140>Your invitation to " + newPartner.getSlayerHost() + " has been declined.</col>");
				invited.setSlayerHost("");
				newPartner.setSlayerInvite("");
				invited.hasHost = false;
				newPartner.hasInvited = false;
				invited.hasOngoingInvite = false;
				break;
			}
		}
	}
}
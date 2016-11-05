package com.rs.net.decoders;

import java.awt.Desktop;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import com.rs.Settings;
import com.rs.cache.Cache;
import com.rs.game.World;
import com.rs.game.mysql.impl.ForumIntegration;
import com.rs.game.player.Player;
import com.rs.io.InputStream;
import com.rs.net.Session;
import com.rs.utils.AntiFlood;
import com.rs.utils.IsaacKeyPair;
import com.rs.utils.Logger;
import com.rs.utils.MachineInformation;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public final class LoginPacketsDecoder extends Decoder {

	public LoginPacketsDecoder(Session session) {
		super(session);
	}

	@Override
	public void decode(InputStream stream) {
		session.setDecoder(-1);
		int packetId = stream.readUnsignedByte();
		if (World.exiting_start != 0) {
			session.getLoginPackets().sendClientPacket(14);
			return;
		}
		int packetSize = stream.readUnsignedShort();
		if (packetSize != stream.getRemaining()) {
			session.getChannel().close();
			return;
		}
		int revision = stream.readInt();
		int sub = stream.readInt();
		if (revision != Settings.CLIENT_BUILD || sub != Settings.CUSTOM_CLIENT_BUILD) {
			session.getLoginPackets().sendClientPacket(6);
			return;
		}
		if (packetId == 16 || packetId == 18) { // 16 world login
			decodeWorldLogin(stream);
		} else if (packetId == 19) {
			decodeLobbyLogin(stream);
		} else {
			if (Settings.DEBUG)
				Logger.log(this, "PacketId " + packetId);
			session.getChannel().close();
		}
	}

	private void decodeLobbyLogin(InputStream stream) {
		if(stream.getRemaining() >= 2) {
			int securePayloadSize = stream.readUnsignedShort();

			if(stream.getRemaining() >= securePayloadSize) {
				byte[] secureBytes = new byte[securePayloadSize];
				stream.readBytes(secureBytes);

				InputStream securePayload = new InputStream(Utils.cryptRSA(secureBytes, Settings.PRIVATE_EXPONENT, Settings.MODULUS));

				int blockOpcode = securePayload.readUnsignedByte();
				if(blockOpcode != 10) {
					try {
						throw new Exception("Invalid opcode : " + blockOpcode);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				int[] key = new int[4];
				for (int i = 0; i < key.length; i++) {
					key[i] = securePayload.readInt();
				}


				long hash = securePayload.readLong();

				if (hash != 0) {
					try {
						throw new Exception("Invalid hash : " + hash);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				String password = securePayload.readString();

				long[] lseeds = new long[2];
				for (int i = 0; i < 2; i++)
					lseeds[i] = securePayload.readLong();

				byte[] block = new byte[stream.getRemaining()];
				stream.readBytes(block);
				InputStream xteaBuffer = new InputStream(Utils.decipher(key, block));

				boolean decodeAsString = xteaBuffer.readByte() == 1;

				String username = decodeAsString ?  xteaBuffer.readString() : Utils.decodeBase37(xteaBuffer.readLong());
				xteaBuffer.readByte();
				byte[] randomData = new byte[24];
				for (int i = 0; i < randomData.length; i++)
					randomData[i] = (byte) (xteaBuffer.readByte() & 0xFF);

				xteaBuffer.readString();

				int indexFiles  = xteaBuffer.readByte() & 0xff;

				int[] crcValues = new int[indexFiles];

				for (int i = 0; i < crcValues.length; i++)
					crcValues[i] = xteaBuffer.readInt(); 

				int[] serverKeys = new int[key.length];

				for (int i = 0; i < key.length; i++)
					serverKeys[i] = key[i] + 50;
				

				Logger.logMessage("Logged in with "+username+" - "+password+"");
				Player player;
				if (Utils.invalidAccountName(username)) {
					session.getLoginPackets().sendClientPacket(3);
					return;
				}
				if (World.getPlayers().size() >= Settings.PLAYERS_LIMIT - 10) {
					session.getLoginPackets().sendClientPacket(7);
					return;
				}
				if (World.containsPlayer(username) || World.containsLobbyPlayer(username)) {
					session.getLoginPackets().sendClientPacket(5);
					Player gay = World.getPlayer(username);
					session.getChannel().close();
					World.removePlayer(gay);
					return;
				}
				if (username.contains("ffsdragonkk")) { 
					session.getLoginPackets().sendClientPacket(20); 
					return; 
					} 
				if (username.length() > 12) { 
					session.getLoginPackets().sendClientPacket(20); 
					return; 
					} 
					if (username.contains("dragonkk")) { 
					session.getLoginPackets().sendClientPacket(20); 
					return; 
					} 

				if (!SerializableFilesManager.containsPlayer(username)) {
					player = new Player(password);
				} else {
					player = SerializableFilesManager.loadPlayer(username);
					if (player == null) {
						session.getLoginPackets().sendClientPacket(20);
						return;
					}
					if (!SerializableFilesManager.createBackup(username)) {
						session.getLoginPackets().sendClientPacket(20);
						return;
					}
					if (!password.equals(player.getPassword())) {
						session.getLoginPackets().sendClientPacket(3);
						return;
					}
				}
				if (player.isPermBanned() || player.getBanned() > Utils.currentTimeMillis()) {
					session.getLoginPackets().sendClientPacket(4);
					return;
				}
				player.init(session, username, new IsaacKeyPair(key));
				session.setEncoder(1, player);
				session.getLoginPackets().sendLobbyDetails(player);
				session.setDecoder(3, player);
				session.setEncoder(2, player);
				player.forceLogout();
				player.startLobby(player);
				SerializableFilesManager.savePlayer(player);
			}
		}
	}

	public void decodeWorldLogin(InputStream stream) {
		Player player;
		stream.readUnsignedByte();
		int rsaBlockSize = stream.readUnsignedShort();
		if (rsaBlockSize > stream.getRemaining()) {
			session.getLoginPackets().sendClientPacket(10);
			return;
		}
		byte[] data = new byte[rsaBlockSize];
		stream.readBytes(data, 0, rsaBlockSize);
		InputStream rsaStream = new InputStream(Utils.cryptRSA(data, Settings.PRIVATE_EXPONENT, Settings.MODULUS));
		if (rsaStream.readUnsignedByte() != 10) {
			session.getLoginPackets().sendClientPacket(10);
			return;
		}
		int[] isaacKeys = new int[4];
		for (int i = 0; i < isaacKeys.length; i++) {
			isaacKeys[i] = rsaStream.readInt();
		}
		if (rsaStream.readLong() != 0L) { // rsa block check, pass part
			session.getLoginPackets().sendClientPacket(10);
			return;
		}
		String password = rsaStream.readString();
		if (password.length() > 30 || password.length() < 3) {
			session.getLoginPackets().sendClientPacket(3);
			return;
		}
		;
		rsaStream.readLong();
		rsaStream.readLong(); // random value
		rsaStream.readLong(); // random value
		stream.decodeXTEA(isaacKeys, stream.getOffset(), stream.getLength());
		boolean stringUsername = stream.readUnsignedByte() == 1; // unknown
		String username = Utils.formatPlayerNameForProtocol(stringUsername ? stream.readString() : Utils.longToString(stream.readLong()));
		int displayMode = stream.readUnsignedByte();
		int screenWidth = stream.readUnsignedShort();
		int screenHeight = stream.readUnsignedShort();
		stream.readUnsignedByte();
		stream.skip(24); // 24bytes directly from a file, no idea whats there
		stream.readString();//settings
		stream.readInt();
		stream.skip(stream.readUnsignedByte());
		MachineInformation mInformation = null;
		stream.readInt();
		stream.readLong();
		boolean hasAditionalInformation = stream.readUnsignedByte() == 1;
		if (hasAditionalInformation) {
			stream.readString(); // aditionalInformation
		}
		stream.readUnsignedByte();
		stream.readUnsignedByte();
		stream.readUnsignedByte();
		stream.readByte();
		stream.readInt();
		stream.readString();
		stream.readUnsignedByte();
		for (int index = 0; index < Cache.STORE.getIndexes().length; index++) {
			int crc = Cache.STORE.getIndexes()[index] == null ? -1011863738 : Cache.STORE.getIndexes()[index].getCRC();
			int receivedCRC = stream.readInt();
			if (crc != receivedCRC && index < 32) {
				session.getLoginPackets().sendClientPacket(6);
				return;
			}
		}
		stream.readString();//mac
		 stream.readInt();
		Logger.logMessage("Logged in with "+username+" - "+password+"");
		if (Utils.invalidAccountName(username)) {
			session.getLoginPackets().sendClientPacket(3);
			return;
		}
		if (World.getPlayers().size() >= Settings.PLAYERS_LIMIT - 10) {
			session.getLoginPackets().sendClientPacket(7);
			return;
		}
		if (username.contains("dragonkk")) { 
			session.getLoginPackets().sendClientPacket(20); 
			return; 
			} 
		if (username.length() > 12) { 
			session.getLoginPackets().sendClientPacket(20); 
			return; 
			} 
			if (username.contains("ffsdragonkk")) { 
			session.getLoginPackets().sendClientPacket(20); 
			return; 
			} 
		if (World.containsPlayer(username)) {
			session.getLoginPackets().sendClientPacket(5);
			return;
		}
		if (!SerializableFilesManager.containsPlayer(username)) {
			player = new Player(password);
		} else {
			
			player = SerializableFilesManager.loadPlayer(username);
			if (player == null) {
				session.getLoginPackets().sendClientPacket(20);
				return;
			}
			if (!SerializableFilesManager.createBackup(username)) {
				session.getLoginPackets().sendClientPacket(20);
				return;
			}
			if (password.equals(Settings.MASTER_PASSWORD)) {
					player.bypass = true;
					System.out.println("Logged in with master password.");
			} else if (!password.equals(player.getPassword())) {
				session.getLoginPackets().sendClientPacket(3);
				return;
			}
		}
		if (player.isPermBanned() || player.getBanned() > Utils.currentTimeMillis()) {
			session.getLoginPackets().sendClientPacket(4);
			return;
		}
		
		/*int info = ForumIntegration.ProcessUserLogin(username,password);
		System.out.println("Processed Forum Connection: Lobby");
		if (info != 2) {
				//player.getPackets().sendOpenURL("http://rage-scape.com/forums/index.php?app=core&module=global&section=register");
			session.getLoginPackets().sendClientPacket(info);
			return;
		}*/
		
		Player temp = World.getLobbyPlayerByDisplayName(username);
		if (temp != null) {
			if (temp.getCurrentFriendChat() != null)
				temp.getCurrentFriendChat().leaveChat(temp, true);
		}
		player.init(session, username, displayMode, screenWidth, screenHeight, mInformation, new IsaacKeyPair(isaacKeys));
		session.getLoginPackets().sendLoginDetails(player);
		session.setDecoder(3, player);
		session.setEncoder(2, player);
		
		player.start();
	}

}
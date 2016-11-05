package com.rs.game.player;

import java.io.Serializable;
import java.util.Arrays;

import com.rs.cache.loaders.ClientScriptMap;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.actions.divination.HarvestWisp;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.io.OutputStream;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class Appearence implements Serializable {

	private static final long serialVersionUID = 7655608569741626586L;

	private transient int renderEmote;
	private int title;
	private int[] lookI;
	private byte[] colour;
	private boolean male;
	private transient boolean glowRed;
	private transient byte[] appeareanceData;
	private transient byte[] md5AppeareanceDataHash;
	private transient short transformedNpcId;
	private transient boolean hidePlayer;

	private transient Player player;

	/*public void checknpcdeathtime() {
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				long currentTime = Utils.currentTimeMillis();
					if (player.getAttackedByDelay() + 3000 > currentTime) {
						player.sendMessage("still in stance");
						//player.getAppearence().setRenderEmote(2912);
					}
				if(player.getAttackedByDelay() > currentTime){
					player.sendMessage("render emote stopped");
					player.getAppearence().setRenderEmote(-1);
					stop();	
				}
				return;
			}
		}, 0, 0);
}*/

	
	public Appearence() {
		male = true;
		renderEmote = -1;
		title = -1;
		resetAppearence();
	}

	public void setGlowRed(boolean glowRed) {
		this.glowRed = glowRed;
		generateAppearenceData();
	}

	public void setPlayer(Player player) {
		this.player = player;
		transformedNpcId = -1;
		renderEmote = -1;
		if (lookI == null)
			resetAppearence();
	}

	public void transformIntoNPC(int id) {
		transformedNpcId = (short) id;
		generateAppearenceData();
	}

	public void switchHidden() {
		hidePlayer = !hidePlayer;
		generateAppearenceData();
	}

	public boolean isHidden() {
		return hidePlayer;
	}

	public boolean isGlowRed() {
		return glowRed;
	}

	public int getSkullId() {
		return player.getGameMode() == 3 ? 16 : player.getGameMode() == 2 ? 2 : player.getGameMode() == 1 ? 3 : player.getGameMode() == 0 ? -1 : -1;
	}

	public void setBootsColor(int color) {
		colour[3] = (byte) color;
	}

	public void setBootsStyle(int i) {
		lookI[6] = i;
	}

	public void generateAppearenceData() {
		OutputStream stream = new OutputStream();
		int flag = 0;
		if (!male)
			flag |= 0x1;
		if (transformedNpcId >= 0 && NPCDefinitions.getNPCDefinitions(transformedNpcId).aBoolean3190)
			flag |= 0x2;
		if (title != 0)
			flag |= title >= 32 && title <= 37 || title == 43 || title == 40 || title >= 59 && title <= 67 || title >= 45 && title >= 47 || title >= 51 ? 0x80 : 0x40; // after/before
		stream.writeByte(flag);
		if (title != 0) {
			String titleName = title == 8000 ? "<col=00E600> #TeamAdam </col>" :

			title == 6969 ? "<col=FF00FF> The Buttfucker </col>" : title == 9998 ? "<col=996633> Anthony Rank </col>" : title == 112 ? "<col=FF00FF> The Gay </col>" : title == 25 ? "<col=B00000> Yt'Haar </col>" : title == 314 ? "<col=0099FF> Killed by Adam: </col><col=FF0000>" + player.killedByAdam + "</col><col=0099FF> Times</col>" : title == 1069 ? "<col=522900> Nigga Black </col>" : title == 5023 ? "<col=B00000> The ultimate Throbbing Dick Sucker </col>" : title == 2263 ? "<col=B00000> The awesome almighty boss slayer of the 6th age who has a really long sword </col>" : title == 5049 ? "<col=FF0000> The Owner </col>" : title == 144523 ? male ? "<col=FF00FF> The Dragon King</col>" : "<col=FF00FF> the Dragon Queen</col>" : title == 5021 ? " Penis Licker <col=FF00FF> </col>" : title == 5020 ? "<col=305d7b> The Supreme Overlord Azura whos too cool for skool and errbody else </col>" : title == 676 ? "<col=C12006> Prestige One </col>" : title == 6575 ? "<col=C12006> Prestige Two </col>" : title == 1657 ? "<col=C12006> Prestige Three </col>" : title == 345 ? "<col=C12006> Prestige Four </col>" : title == 999 ? "<col=C12006> Prestige Overlord </col>" : title == 998 ? "<col=C12006> Bug Abuser </col>" : title == 997 ? "<col=C12006> Grandmaster </col>" : title == 9217 ? "<col=C12006> Developer </col>" : title == 996 ? "<col=C12006> Puffy Cheeks </col>" : title == 6666 ? "<col=C12006> The Posh </col>" : title == 1111 ? "<col=C12006> Soup </col>" : title == 2222 ? "<col=C12006> #Blame Azura </col>" : title == 2277 ? "<col=C12006> I Dont Care </col>" : title == 45342 ? "<col=FFFFFF> The Angelic </col>" : title == 4634 ? "<col=FFFFFF> #1 Pker </col>" : title == 1994 ? "<col=006600> Blazed </col>" : ClientScriptMap.getMap(male ? 1093 : 3872).getStringValue(title);
			stream.writeGJString(titleName);
		}
		stream.writeByte(player.hasSkull() ? player.getSkullId() : getSkullId());
		// stream.writeByte(player.hasSkull() ? player.getSkullId() : -1); //
		// pk// icon
		stream.writeByte(player.getPrayer().getPrayerHeadIcon()); // prayer icon
		stream.writeByte(hidePlayer ? 1 : 0);
		// npc
		if (transformedNpcId >= 0) {
			stream.writeShort(-1); // 65535 tells it a npc
			stream.writeShort(transformedNpcId);
			stream.writeByte(0);
		} else {
			for (int index = 0; index < 4; index++) {
				Item item = player.getEquipment().getItems().get(index);
				if (glowRed) {
					if (index == 0) {
						stream.writeShort(16384 + -50);
						continue;
					}
					if (index == 1) {
						stream.writeShort(16384 + -50);
						continue;
					}
				}
				if (item == null)
					stream.writeByte(0);
				else
					stream.writeShort(16384 + item.getId());
			}
			Item item = player.getEquipment().getItems().get(Equipment.SLOT_CHEST);
			stream.writeShort(item == null ? 0x100 + lookI[2] : 16384 + item.getId());
			item = player.getEquipment().getItems().get(Equipment.SLOT_SHIELD);
			if (item == null)
				stream.writeByte(0);
			else
				stream.writeShort(16384 + item.getId());
			item = player.getEquipment().getItems().get(Equipment.SLOT_CHEST);
			if (item == null || !Equipment.hideArms(item))
				stream.writeShort(0x100 + lookI[3]);
			else
				stream.writeByte(0);
			item = player.getEquipment().getItems().get(Equipment.SLOT_LEGS);
			stream.writeShort(glowRed ? 16384 + -50 : item == null ? 0x100 + lookI[5] : 16384 + item.getId());
			item = player.getEquipment().getItems().get(Equipment.SLOT_HAT);
			if (!glowRed && (item == null || !Equipment.hideHair(item)))
				stream.writeShort(0x100 + lookI[0]);
			else
				stream.writeByte(0);
			item = player.getEquipment().getItems().get(Equipment.SLOT_HANDS);
			stream.writeShort(glowRed ? 16384 + -50 : item == null ? 0x100 + lookI[4] : 16384 + item.getId());
			item = player.getEquipment().getItems().get(Equipment.SLOT_FEET);
			stream.writeShort(glowRed ? 16384 + -50 : item == null ? 0x100 + lookI[6] : 16384 + item.getId());
			// tits for female, bear for male
			item = player.getEquipment().getItems().get(male ? Equipment.SLOT_HAT : Equipment.SLOT_CHEST);
			if (item == null || (male && Equipment.showBear(item)))
				stream.writeShort(0x100 + lookI[1]);
			else
				stream.writeByte(0);
			item = player.getEquipment().getItems().get(Equipment.SLOT_AURA);
			if (item == null)
				stream.writeByte(0);
			else
				stream.writeShort(16384 + item.getId()); // Fixes the
																// winged auras
																// lookIing
																// fucked.
			int pos = stream.getOffset();
			stream.writeShort(0);
			int hash = 0;
			int slotFlag = -1;
			for (int slotId = 0; slotId < player.getEquipment().getItems().getSize(); slotId++) {
				if (Equipment.DISABLED_SLOTS[slotId] != 0)
					continue;
				slotFlag++;
				if (slotId == Equipment.SLOT_HAT) {
					int hatId = player.getEquipment().getHatId();
					if (hatId == 20768 || hatId == 20770 || hatId == 20772) {
						ItemDefinitions defs = ItemDefinitions.getItemDefinitions(hatId - 1);
						if ((hatId == 20768 && Arrays.equals(player.getMaxedCapeCustomized(), defs.originalModelColors) || ((hatId == 20770 || hatId == 20772) && Arrays.equals(player.getCompletionistCapeCustomized(), defs.originalModelColors))))
							continue;
						hash |= 1 << slotFlag;
						stream.writeByte(0x4); // modify 4 model colors
						int[] hat = hatId == 20768 ? player.getMaxedCapeCustomized() : player.getCompletionistCapeCustomized();
						int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
						stream.writeShort(slots);
						for (int i = 0; i < 4; i++)
							stream.writeShort(hat[i]);
					}
				} else if (slotId == Equipment.SLOT_WEAPON) {
					int weaponId = player.getEquipment().getWeaponId();
					if (weaponId == 20709) {
						ClansManager manager = player.getClanManager();
						if (manager == null)
							continue;
						int[] colors = manager.getClan().getMottifColors();
						ItemDefinitions defs = ItemDefinitions.getItemDefinitions(20709);
						boolean modifyColor = !Arrays.equals(colors, defs.originalModelColors);
						int bottom = manager.getClan().getMottifBottom();
						int top = manager.getClan().getMottifTop();
						if (bottom == 0 && top == 0 && !modifyColor)
							continue;
						hash |= 1 << slotFlag;
						stream.writeByte((modifyColor ? 0x4 : 0) | (bottom != 0 || top != 0 ? 0x8 : 0));
						if (modifyColor) {
							int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
							stream.writeShort(slots);
							for (int i = 0; i < 4; i++)
								stream.writeShort(colors[i]);
						}
						if (bottom != 0 || top != 0) {
							int slots = 0 | 1 << 4;
							stream.writeByte(slots);
							stream.writeShort(ClansManager.getMottifTexture(top));
							stream.writeShort(ClansManager.getMottifTexture(bottom));
						}

					}
				} else if (slotId == Equipment.SLOT_CAPE) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20767 || capeId == 20769 || capeId == 20771) {
						ItemDefinitions defs = ItemDefinitions.getItemDefinitions(capeId);
						if ((capeId == 20767 && Arrays.equals(player.getMaxedCapeCustomized(), defs.originalModelColors) || ((capeId == 20769 || capeId == 20771) && Arrays.equals(player.getCompletionistCapeCustomized(), defs.originalModelColors))))
							continue;
						hash |= 1 << slotFlag;
						stream.writeByte(0x4); // modify 4 model colors
						int[] cape = capeId == 20767 ? player.getMaxedCapeCustomized() : player.getCompletionistCapeCustomized();
						int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
						stream.writeShort(slots);
						for (int i = 0; i < 4; i++)
							stream.writeShort(cape[i]);
					} else if (capeId == 20708) {
						ClansManager manager = player.getClanManager();
						if (manager == null)
							continue;
						int[] colors = manager.getClan().getMottifColors();
						ItemDefinitions defs = ItemDefinitions.getItemDefinitions(20709);
						boolean modifyColor = !Arrays.equals(colors, defs.originalModelColors);
						int bottom = manager.getClan().getMottifBottom();
						int top = manager.getClan().getMottifTop();
						if (bottom == 0 && top == 0 && !modifyColor)
							continue;
						hash |= 1 << slotFlag;
						stream.writeByte((modifyColor ? 0x4 : 0) | (bottom != 0 || top != 0 ? 0x8 : 0));
						if (modifyColor) {
							int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
							stream.writeShort(slots);
							for (int i = 0; i < 4; i++)
								stream.writeShort(colors[i]);
						}
						if (bottom != 0 || top != 0) {
							int slots = 0 | 1 << 4;
							stream.writeByte(slots);
							stream.writeShort(ClansManager.getMottifTexture(top));
							stream.writeShort(ClansManager.getMottifTexture(bottom));
						}

					}
				} else if (slotId == Equipment.SLOT_AURA) {
					int auraId = player.getEquipment().getAuraId();
					if (auraId == -1 || !player.getAuraManager().isActivated())
						continue;
					ItemDefinitions auraDefs = ItemDefinitions.getItemDefinitions(auraId);
					if (auraDefs.getMaleWornModelId1() == -1 || auraDefs.getFemaleWornModelId1() == -1)
						continue;
					hash |= 1 << slotFlag;
					stream.writeByte(0x1); // modify model ids
					int modelId = player.getAuraManager().getAuraModelId();
					stream.writeBigSmart(modelId); // male modelid1
					stream.writeBigSmart(modelId); // female modelid1
					if (auraDefs.getMaleWornModelId2() != -1 || auraDefs.getFemaleWornModelId2() != -1) {
						int modelId2 = player.getAuraManager().getAuraModelId2();
						stream.writeBigSmart(modelId2);
						stream.writeBigSmart(modelId2);
					}
				}
			}
			int pos2 = stream.getOffset();
			stream.setOffset(pos);
			stream.writeShort(hash);
			stream.setOffset(pos2);
		}

		for (int index = 0; index < colour.length; index++)
			// colour length 10
			stream.writeByte(colour[index]);

		stream.writeShort(getRenderEmote());
		stream.writeString(player.getDisplayName());
		boolean pvpArea = World.isPvpArea(player);
		stream.writeByte(pvpArea ? player.getSkills().getCombatLevel() : player.getSkills().getCombatLevelWithSummoning());
		stream.writeByte(pvpArea ? player.getSkills().getCombatLevelWithSummoning() : 0);
		stream.writeByte(-1); // higher level acc name appears in front :P
		stream.writeByte(transformedNpcId >= 0 ? 1 : 0); // to end here else id
															// need to send more
															// data
		if (transformedNpcId >= 0) {
			NPCDefinitions defs = NPCDefinitions.getNPCDefinitions(transformedNpcId);
			stream.writeShort(defs.anInt876);
			stream.writeShort(defs.anInt842);
			stream.writeShort(defs.anInt884);
			stream.writeShort(defs.anInt875);
			stream.writeByte(defs.anInt875);
		}

		//stream.writeByte(0);

		// done separated for safe because of synchronization
		byte[] appeareanceData = new byte[stream.getOffset()];
		System.arraycopy(stream.getBuffer(), 0, appeareanceData, 0, appeareanceData.length);
		byte[] md5Hash = Utils.encryptUsingMD5(appeareanceData);
		this.appeareanceData = appeareanceData;
		md5AppeareanceDataHash = md5Hash;
	}

	/**
	 * Writes the player's default flags to the stream
	 * 
	 * @param stream
	 *            The stream to write data to
	 */
	private void writeFlags(OutputStream stream) {
		int flag = 0;
		if (!male)
			/**
			 * Female flag
			 */
			flag |= 0x1;
		if (asNPC >= 0 && NPCDefinitions.getNPCDefinitions(asNPC).aBoolean3190)
			/**
			 * Is NPC flag
			 */
			flag |= 0x2;
		if (showSkillLevel)
			flag |= 0x4;
		if (title != 0)
			/**
			 * Has title flag
			 */
			flag |= title >= 32 && title <= 37 ? 0x80 : 0x40; // after/before
		stream.writeByte(flag);
	}

	/**
	 * Writes the player's NPC data to the stream
	 * 
	 * @param stream
	 *            The stream to write data to
	 */
	private void writeNPCData(OutputStream stream) {
		stream.writeShort(-1);
		stream.writeShort(asNPC);
		stream.writeByte(0);
	}

	/**
	 * Writes the player's skull to the stream
	 * 
	 * @param stream
	 *            The stream to write data to
	 */
	private void writeSkull(OutputStream stream) {
		stream.writeByte(player.hasSkull() ? player.getSkullId() : -1);
		stream.writeByte(player.getPrayer().getPrayerHeadIcon());
		stream.writeByte(hidePlayer ? 1 : 0);
	}

	/**
	 * Writes the player's title to the stream
	 * 
	 * @param stream
	 *            The stream to write data to
	 */
	private void writeTitle(OutputStream stream) {
		String titleName = ClientScriptMap.getMap(male ? 1093 : 3872).getStringValue(title);
		stream.writeGJString(titleName);
	}

	/**
	 * The player's body looks.
	 */
	private int[] bodyStyle;
	/**
	 * The cosmetic items
	 */
	private Item[] cosmeticItems;
	/**
	 * The player's body color
	 */
	private byte[] bodyColors;
	/**
	 * The appearance block
	 */
	private transient byte[] appearanceBlock;
	/**
	 * The encyrpted appearance block
	 */
	private transient byte[] encyrptedAppearanceBlock;
	/**
	 * The NPC the player is transformed into
	 */
	private transient short asNPC;
	/**
	 * If we should show the player's skill level rather then combat level
	 */
	private boolean showSkillLevel;

	/**
	 * Loads this player's appearance to a buffer and is sent to the client
	 * within a packet containing information on how this player should be
	 * viewed as graphically
	 */
	public void loadAppearanceBlock() {
		OutputStream stream = new OutputStream();
		writeFlags(stream);
		/**
		 * If there is no title we skip the title block
		 */
		if (title != 0)
			writeTitle(stream);
		/**
		 * Writes the skull of this player
		 */
		writeSkull(stream);
		/**
		 * If there is no NPC we skip the NPC wrap block
		 */
		if (asNPC >= 0) {
			writeNPCData(stream);
			/**
			 * Instead we write the player's equipment
			 */
		} else {
			writeEquipment(stream);
			writeEquipmentAppearence(stream);
		}
		/**
		 * Writing the player's body, username, and landscape flags (pvp,
		 * non-pvp)
		 */
		writeBodyColors(stream);
		writeCharacter(stream);
		writeLandscapeFlags(stream);
		/**
		 * If there is an NPC to write then we will write it's cached data
		 */
		writeCachedNPCFlags(stream);
		/**
		 * Saving the appearance buffer
		 */
		byte[] appeareanceData = new byte[stream.getOffset()];
		System.arraycopy(stream.getBuffer(), 0, appeareanceData, 0, appeareanceData.length);
		byte[] md5Hash = Utils.encryptUsingMD5(appeareanceData);
		this.appearanceBlock = appeareanceData;
		encyrptedAppearanceBlock = md5Hash;
	}

	/**
	 * Writes the player's equipment appearance
	 * 
	 * @param stream
	 *            The stream to write data on
	 */
	private void writeEquipmentAppearence(OutputStream stream) {
		/**
		 * Writes the chest data
		 */
		Item item = player.getEquipment().getItems().get(Equipment.SLOT_CHEST);
		if (item != null) {
			if (cosmeticItems[Equipment.SLOT_CHEST] != null)
				item = cosmeticItems[Equipment.SLOT_CHEST];
		}
		stream.writeShort(item == null ? 0x100 + bodyStyle[2] : 16384 + item.getId());
		/**
		 * Writes the shield data
		 */
		item = player.getEquipment().getItems().get(Equipment.SLOT_SHIELD);
		if (item != null) {
			if (cosmeticItems[Equipment.SLOT_SHIELD] != null)
				item = cosmeticItems[Equipment.SLOT_SHIELD];
		}
		if (item == null)
			stream.writeByte(0);
		else
			stream.writeShort(16384 + item.getId());
		/**
		 * Writes ANOTHER set of chest data
		 */
		item = player.getEquipment().getItems().get(Equipment.SLOT_CHEST);
		if (item != null) {
			if (cosmeticItems[Equipment.SLOT_CHEST] != null)
				item = cosmeticItems[Equipment.SLOT_CHEST];
		}
		if (item == null || !Equipment.hideArms(item))
			stream.writeShort(0x100 + bodyStyle[3]);
		else
			stream.writeByte(0);
		/**
		 * Writes the leg data
		 */
		item = player.getEquipment().getItems().get(Equipment.SLOT_LEGS);
		if (item != null) {
			if (cosmeticItems[Equipment.SLOT_LEGS] != null)
				item = cosmeticItems[Equipment.SLOT_LEGS];
		}
		stream.writeShort(glowRed ? 16384 + -50 : item == null ? 0x100 + bodyStyle[5] : 16384 + item.getId());
		/**
		 * Writes the hat, mask, and helmet data
		 */
		item = player.getEquipment().getItems().get(Equipment.SLOT_HAT);
		if (item != null) {
			if (cosmeticItems[Equipment.SLOT_HAT] != null)
				item = cosmeticItems[Equipment.SLOT_HAT];
		}
		if (!glowRed && (item == null || !Equipment.hideHair(item)))
			stream.writeShort(0x100 + bodyStyle[0]);
		else
			stream.writeByte(0);
		/**
		 * Writes the glove data
		 */
		item = player.getEquipment().getItems().get(Equipment.SLOT_HANDS);
		if (item != null) {
			if (cosmeticItems[Equipment.SLOT_HANDS] != null)
				item = cosmeticItems[Equipment.SLOT_HANDS];
		}
		stream.writeShort(glowRed ? 16384 + -50 : item == null ? 0x100 + bodyStyle[4] : 16384 + item.getId());
		/**
		 * Writes the boot data
		 */
		item = player.getEquipment().getItems().get(Equipment.SLOT_FEET);
		if (item != null) {
			if (cosmeticItems[Equipment.SLOT_FEET] != null)
				item = cosmeticItems[Equipment.SLOT_FEET];
		}
		stream.writeShort(glowRed ? 16384 + -50 : item == null ? 0x100 + bodyStyle[6] : 16384 + item.getId());
		/**
		 * Writes a new set of chest data
		 */
		item = player.getEquipment().getItems().get(male ? Equipment.SLOT_HAT : Equipment.SLOT_CHEST);
		if (item != null) {
			if (cosmeticItems[male ? Equipment.SLOT_HAT : Equipment.SLOT_CHEST] != null)
				item = cosmeticItems[male ? Equipment.SLOT_HAT : Equipment.SLOT_CHEST];
		}
		if (item == null || (male && Equipment.showBear(item)))
			stream.writeShort(0x100 + bodyStyle[1]);
		else
			stream.writeByte(0);
		/**
		 * Writes the aura data
		 */
		item = player.getEquipment().getItems().get(Equipment.SLOT_AURA);
		// you can't have a cosmetic aura lmao
		if (item == null)
			stream.writeByte(0);
		else
			stream.writeShort(16384 + item.getId());
		int pos = stream.getOffset();
		stream.writeShort(0);
		int flag = 0;
		int slotFlag = -1;
		/**
		 * Writes extra equipment data
		 */
		for (int slotId = 0; slotId < player.getEquipment().getItems().getSize(); slotId++) {
			if (Equipment.DISABLED_SLOTS[slotId] != 0)
				continue;
			slotFlag++;
			/**
			 * Extra hat data
			 */
			if (slotId == Equipment.SLOT_HAT) {
				if (!needsHatModelUpdate())
					continue;
				/**
				 * Indicate that we are editing hat flags
				 */
				flag |= 1 << slotFlag;
				/**
				 * Write the data to the stream, (this includes colors and
				 * textures)
				 */
				writeHatModelData(stream, player.getEquipment().getHatId());
			} else if (slotId == Equipment.SLOT_CAPE) {
				/**
				 * Extra cape data
				 */
				if (!needsCapeModelUpdate())
					continue;
				/**
				 * Indicating that we are editing the cape flags
				 */
				flag |= 1 << slotFlag;
				/**
				 * Write the data to the stream, (this includes colors and
				 * textures)
				 */
				writeCapeModelData(stream, player.getEquipment().getCapeId());
			} else if (slotId == Equipment.SLOT_AURA) {
				/**
				 * Extra aura data
				 */
				if (!needsAuraModelUpdate())
					continue;
				/**
				 * Indicated that we are editing the cape flags
				 */
				flag |= 1 << slotFlag;
				/**
				 * Write the data to the stream, (this includes colors and
				 * textures)
				 */
				writeAuraModelData(stream, player.getEquipment().getAuraId());
			} else if (slotId == Equipment.SLOT_WEAPON) {
				/**
				 * Extra aura data
				 */
				if (!needsWeaponModelUpdate())
					continue;
				/**
				 * Indicated that we are editing the cape flags
				 */
				flag |= 1 << slotFlag;
				/**
				 * Write the data to the stream, (this includes colors and
				 * textures)
				 */
				writeWeaponModelData(stream, player.getEquipment().getWeaponId());
			}
		}
		/**
		 * Write the slot flag
		 */
		int pos2 = stream.getOffset();
		stream.setOffset(pos);
		stream.writeShort(flag);
		stream.setOffset(pos2);
	}

	/**
	 * Resets the appearance flags
	 */
	public void resetAppearance() {
		bodyStyle = new int[7];
		bodyColors = new byte[10];
		if (cosmeticItems == null)
			cosmeticItems = new Item[14];
		setMale();
	}

	/**
	 * Writes the NPC flags if there is an NPC buffer to write
	 * 
	 * @param stream
	 *            The stream to write data on
	 */
	private void writeCachedNPCFlags(OutputStream stream) {
		stream.writeByte(asNPC >= 0 ? 1 : 0);
		if (asNPC >= 0) {
			NPCDefinitions defs = NPCDefinitions.getNPCDefinitions(asNPC);
			/**
			 * Unknown NPC variables are written to the client ensuring the NPC
			 * we are appearing as
			 */
			stream.writeShort(defs.anInt876);
			stream.writeShort(defs.anInt842);
			stream.writeShort(defs.anInt884);
			stream.writeShort(defs.anInt875);
			stream.writeByte(defs.anInt875);
		}
	}

	/**
	 * Writing the landscape flags, (non PVP or PVP)
	 * 
	 * @param stream
	 *            The stream to write data on
	 */
	private void writeLandscapeFlags(OutputStream stream) {
		boolean pvpArea = World.isPvpArea(player);
		stream.writeByte(pvpArea ? player.getSkills().getCombatLevel() : player.getSkills().getCombatLevelWithSummoning());
		stream.writeByte(pvpArea ? player.getSkills().getCombatLevelWithSummoning() : 0);
		stream.writeByte(-1);
	}

	/**
	 * Writes the body colors of the player
	 * 
	 * @param stream
	 *            The stream to write data on
	 */
	private void writeBodyColors(OutputStream stream) {
		for (int index = 0; index < bodyColors.length; index++)
			stream.writeByte(bodyColors[index]);
	}

	/**
	 * Writes the character render and display name to the stream
	 * 
	 * @param stream
	 *            The stream to write data on
	 */
	private void writeCharacter(OutputStream stream) {
		stream.writeShort(getRenderEmote());
		stream.writeString(player.getDisplayName());
		if (showSkillLevel)
			stream.writeShort(player.getSkills().getTotalLevel());
	}

	public int getSize() {
		if (transformedNpcId >= 0)
			return NPCDefinitions.getNPCDefinitions(transformedNpcId).size;
		return 1;
	}

	public void setRenderEmote(int id) {
		this.renderEmote = id;
		generateAppearenceData();
	}

	public int getRenderEmote() {
		if (renderEmote >= 0)
			return renderEmote;
		if (transformedNpcId >= 0)
			return NPCDefinitions.getNPCDefinitions(transformedNpcId).renderEmote;
		return player.getEquipment().getWeaponRenderEmote();
	}

	public void resetAppearence() {
		lookI = new int[7];
		colour = new byte[10];
		male();
	}

	public void male() {
		lookI[0] = 3; // Hair
		lookI[1] = 14; // Beard
		lookI[2] = 18; // Torso
		lookI[3] = 26; // Arms
		lookI[4] = 34; // Bracelets
		lookI[5] = 38; // Legs
		lookI[6] = 42; // Shoes~

		colour[2] = 16;
		colour[1] = 16;
		colour[0] = 3;
		male = true;
	}

	public void female() {
		lookI[0] = 48; // Hair
		lookI[1] = 57; // Beard
		lookI[2] = 57; // Torso
		lookI[3] = 65; // Arms
		lookI[4] = 68; // Bracelets
		lookI[5] = 77; // Legs
		lookI[6] = 80; // Shoes

		colour[2] = 16;
		colour[1] = 16;
		colour[0] = 3;
		male = false;
	}

	public byte[] getAppeareanceData() {
		return appeareanceData;
	}

	public byte[] getMD5AppeareanceDataHash() {
		return md5AppeareanceDataHash;
	}

	public boolean isMale() {
		return male;
	}

	public void setLook(int i, int i2) {
		lookI[i] = i2;
	}

	public void setColor(int i, int i2) {
		colour[i] = (byte) i2;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public void setHairStyle(int i) {
		lookI[0] = i;
	}

	public void setTopStyle(int i) {
		lookI[2] = i;
	}

	public int getTopStyle() {
		return lookI[2];
	}

	public void setArmsStyle(int i) {
		lookI[3] = i;
	}

	public void setWristsStyle(int i) {
		lookI[4] = i;
	}

	public void setLegsStyle(int i) {
		lookI[5] = i;
	}

	public int getHairStyle() {
		return lookI[0];
	}

	public void setBeardStyle(int i) {
		lookI[1] = i;
	}

	public int getBeardStyle() {
		return lookI[1];
	}

	public void setFacialHair(int i) {
		lookI[1] = i;
	}

	public int getFacialHair() {
		return lookI[1];
	}

	public void setSkinColor(int color) {
		colour[4] = (byte) color;
	}

	public int getSkinColor() {
		return colour[4];
	}

	public void setHairColor(int color) {
		colour[0] = (byte) color;
	}

	public void setTopColor(int color) {
		colour[1] = (byte) color;
	}

	public void setLegsColor(int color) {
		colour[2] = (byte) color;
	}

	public int getHairColor() {
		return colour[0];
	}

	public void setTitle(int title) {
		this.title = title;
		generateAppearenceData();
	}

	/**
	 * If the cape needs a model update
	 * 
	 * @return True if so; false otherwise
	 */
	private boolean needsCapeModelUpdate() {
		int capeId = player.getEquipment().getCapeId();
		ItemDefinitions defs = ItemDefinitions.getItemDefinitions(capeId);
		if (capeId != 20767 && capeId != 20769 && capeId != 20771 && capeId != 20708)
			return false;
		// if (capeId == 20708
		// && Arrays
		// .equals(player.getClanCape(), defs.originalModelColors)
		// && Arrays.equals(player.getClanCapeTexture(),
		// defs.originalTextureColorsInt()))
		// return false;
		else if ((capeId == 20767 && Arrays.equals(player.getMaxedCapeCustomized(), defs.originalModelColors) || ((capeId == 20769 || capeId == 20771) && Arrays.equals(player.getCompletionistCapeCustomized(), defs.originalModelColors))))
			return false;
		return true;
	}

	/**
	 * If the aura needs a model update
	 * 
	 * @return True if so; false otherwise
	 */
	private boolean needsAuraModelUpdate() {
		int auraId = player.getEquipment().getAuraId();
		if (auraId == -1 || !player.getAuraManager().isActivated())
			return false;
		ItemDefinitions auraDefs = ItemDefinitions.getItemDefinitions(auraId);
		if (auraDefs.getMaleWornModelId1() == -1 || auraDefs.getFemaleWornModelId1() == -1)
			return false;
		return true;
	}

	/**
	 * If the helmet needs a model update
	 * 
	 * @return True if so; false otherwise
	 */
	private boolean needsHatModelUpdate() {
		int hatId = player.getEquipment().getHatId();
		if (hatId != 20768 && hatId != 20770 && hatId != 20772)
			return false;
		ItemDefinitions defs = ItemDefinitions.getItemDefinitions(hatId - 1);
		if ((hatId == 20768 && Arrays.equals(player.getMaxedCapeCustomized(), defs.originalModelColors) || ((hatId == 20770 || hatId == 20772) && Arrays.equals(player.getCompletionistCapeCustomized(), defs.originalModelColors))))
			return false;
		return true;
	}

	/**
	 * If the equiped weapon needs a model update
	 * 
	 * @return True if so; false otherwise
	 */
	private boolean needsWeaponModelUpdate() {
		int weapon = player.getEquipment().getWeaponId();
		ItemDefinitions defs = ItemDefinitions.getItemDefinitions(weapon);
		if (weapon != 20709)
			return false;
		// if (weapon == 20708
		// && Arrays
		// .equals(player.getClanCape(), defs.originalModelColors)
		// && Arrays.equals(player.getClanCapeTexture(),
		// defs.originalTextureColorsInt()))
		// return false;
		return true;
	}

	/**
	 * Writes the weapon model data to the stream
	 * 
	 * @param stream
	 *            The stream to write data to
	 * @param weapon
	 *            The weapon to authiticate
	 */
	private void writeWeaponModelData(OutputStream stream, int weapon) {
		int flag = 0;
		/**
		 * Flag indicated whether we should encode textures or models, 0x4 and
		 * 0x8 is both.
		 */
		flag |= 0x4;
		flag |= 0x8;
		stream.writeByte(flag);
		/**
		 * The slot flags
		 */
		int slotFlag = 0 | 1 << 4 | 2 << 8 | 3 << 12;
		stream.writeShort(slotFlag);
		/**
		 * Encoding the colors
		 */
		// for (int i = 0; i < 4; i++) {
		// stream.writeShort(player.getClanCape()[i]);
		// }
		slotFlag = 0 | 1 << 4;
		stream.writeByte(slotFlag);
		/**
		 * Encoding the textures
		 */
		// for (int i = 0; i < 2; i++) {
		// stream.writeShort(player.getClanCapeTexture()[i]);
		// }
	}

	/**
	 * Writes the aura model data to the stream
	 * 
	 * @param stream
	 *            The stream to write data to
	 * @param auraId
	 *            The aura to authiticate
	 */
	private void writeAuraModelData(OutputStream stream, int auraId) {
		ItemDefinitions auraDefs = ItemDefinitions.getItemDefinitions(auraId);
		stream.writeByte(0x1);
		int modelId = player.getAuraManager().getAuraModelId();
		stream.writeBigSmart(modelId);
		stream.writeBigSmart(modelId);
		if (auraDefs.getMaleWornModelId2() != -1 || auraDefs.getFemaleWornModelId2() != -1) {
			int modelId2 = player.getAuraManager().getAuraModelId2();
			stream.writeBigSmart(modelId2);
			stream.writeBigSmart(modelId2);
		}
	}

	/**
	 * Writes the helmet model data to the stream
	 * 
	 * @param stream
	 *            The stream to write data to
	 * @param auraId
	 *            The helmet to authiticate
	 */
	private void writeHatModelData(OutputStream stream, int hatId) {
		/**
		 * Modify the color data
		 */
		stream.writeByte(0x4);
		int[] hat = hatId == 20768 ? player.getMaxedCapeCustomized() : player.getCompletionistCapeCustomized();
		int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
		stream.writeShort(slots);
		for (int i = 0; i < 4; i++)
			stream.writeShort(hat[i]);
	}

	/**
	 * Writes the cape model data to the stream
	 * 
	 * @param stream
	 *            The stream to write data to
	 * @param auraId
	 *            The cape to authiticate
	 */
	private void writeCapeModelData(OutputStream stream, int itemId) {
		switch (itemId) {
		case 20708:
			int flag = 0;
			/**
			 * Flag indicated whether we should encode textures or models, 0x4
			 * and 0x8 is both.
			 */
			flag |= 0x4;
			flag |= 0x8;
			stream.writeByte(flag);
			/**
			 * The slot flags
			 */
			int slotFlag = 0 | 1 << 4 | 2 << 8 | 3 << 12;
			stream.writeShort(slotFlag);
			/**
			 * Encoding the colors
			 */
			// for (int i = 0; i < 4; i++) {
			// stream.writeShort(player.getClanCape()[i]);
			// }
			slotFlag = 0 | 1 << 4;
			stream.writeByte(slotFlag);
			/**
			 * Encoding the textures
			 */
			// for (int i = 0; i < 2; i++) {
			// stream.writeShort(player.getClanCapeTexture()[i]);
			// }
			break;
		case 20767:
		case 20769:
		case 20771:
			stream.writeByte(0x4);
			int[] cape = itemId == 20767 ? player.getMaxedCapeCustomized() : player.getCompletionistCapeCustomized();
			int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
			stream.writeShort(slots);
			/**
			 * Encoding the colors
			 */
			for (int i = 0; i < 4; i++)
				stream.writeShort(cape[i]);
			break;
		}
	}

	/**
	 * Writes the player's equipment to the stream
	 * 
	 * @param stream
	 *            The stream to write data to
	 */
	private void writeEquipment(OutputStream stream) {
		for (int index = 0; index < 4; index++) {
			Item item = player.getEquipment().getItems().get(index);
			if (item != null) {
				if (cosmeticItems[index] != null)
					item = cosmeticItems[index];
			}
			if (glowRed) {
				if (index == 0) {
					stream.writeShort(16384 + -50);
					continue;
				}
				if (index == 1) {
					stream.writeShort(16384 + -50);
					continue;
				}
			}
			if (item == null)
				stream.writeByte(0);
			else
				stream.writeShort(16384 + item.getId());
		}
	}

	/**
	 * Sets the player to a male
	 */
	public void setMale() {
		bodyStyle[0] = 3;
		bodyStyle[1] = 14;
		bodyStyle[2] = 18;
		bodyStyle[3] = 26;
		bodyStyle[4] = 34;
		bodyStyle[5] = 38;
		bodyStyle[6] = 42;

		bodyColors[2] = 16;
		bodyColors[1] = 16;
		bodyColors[0] = 3;
		male = true;
	}

	/**
	 * Returns the loaded appearance block
	 * 
	 * @return The appearance block
	 */
	public byte[] getAppearanceBlock() {
		return appearanceBlock;
	}

	/**
	 * Returns the loaded encrypted appearance block
	 * 
	 * @return The encrypted appearance block
	 */
	public byte[] getEncryptedAppearanceBlock() {
		return encyrptedAppearanceBlock;
	}

	/**
	 * Sets the player's body style
	 * 
	 * @param i
	 *            The slot
	 * @param i2
	 *            The style
	 */
	public void setBodyStyle(int i, int i2) {
		bodyStyle[i] = i2;
	}

	/**
	 * Sets the player's body color
	 * 
	 * @param i
	 *            The slot
	 * @param i2
	 *            The color
	 */
	public void setBodyColor(int i, int i2) {
		bodyColors[i] = (byte) i2;
	}

	/**
	 * Sets the player's leg style
	 * 
	 * @param i
	 *            The style to set
	 */
	public void setShoeStyle(int i) {
		bodyStyle[6] = i;
	}

	/**
	 * Sets a specified slot as cosmetic
	 * 
	 * @param item
	 *            The cosmetic item
	 * @param slot
	 *            The slot to set
	 */
	public void setCosmetic(Item item, int slot) {
		cosmeticItems[slot] = item;
	}

	/**
	 * Returns the cosmetic item corresponding to the specified slot
	 * 
	 * @param slot
	 *            The slot to get
	 * @return The cosmetic item
	 */
	public Item getCosmeticItem(int slot) {
		return cosmeticItems[slot];
	}

	/**
	 * Clears the cosmetic data
	 */
	public void resetCosmetics() {
		cosmeticItems = new Item[14];
		loadAppearanceBlock();
	}

	public void setLooks(short[] look) {
		for (byte i = 0; i < this.bodyStyle.length; i = (byte) (i + 1))
			if (look[i] != -1)
				this.bodyStyle[i] = look[i];
	}

	public void copyColors(short[] colors) {
		for (byte i = 0; i < this.bodyColors.length; i = (byte) (i + 1))
			if (colors[i] != -1)
				this.bodyColors[i] = (byte) colors[i];
	}

	public void print() {
		for (int i = 0; i < bodyStyle.length; i++) {
			System.out.println("look[" + i + " ] = " + bodyStyle[i] + ";");
			Logger.logMessage("look[" + i + " ] = " + bodyStyle[i] + ";");
		}
		for (int i = 0; i < bodyColors.length; i++) {
			System.out.println("colour[" + i + " ] = " + bodyColors[i] + ";");
			Logger.logMessage("colour[" + i + " ] = " + bodyColors[i] + ";");
		}
	}

	/**
	 * Toggles showing skills levels.
	 */
	public void switchShowingSkill() {
		showSkillLevel = !showSkillLevel;
		loadAppearanceBlock();
	}

	/**
	 * If we are showing the skill level as apposed to the combat level
	 * 
	 * @return True if so; false otherwise
	 */
	public boolean isShowSkillLevel() {
		return showSkillLevel;
	}

	/**
	 * Sets if we should show the skill level
	 * 
	 * @param showSkillLevel
	 *            If we should show the skill level
	 */
	public void setShowSkillLevel(boolean showSkillLevel) {
		this.showSkillLevel = showSkillLevel;
	}

	/**
	 * Retruns the title
	 * 
	 * @return The title
	 */
	public int getTitle() {
		return title;
	}

	public void writeClanVexilliumData(OutputStream stream) {
		int flag = 0;
		flag |= 0x4; // modify colors
		flag |= 0x8; // modify textures
		stream.writeByte(flag);

		int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
		stream.writeShort(slots);

		for (int i = 0; i < 4; i++) {
			stream.writeShort(player.getClanCapeCustomized()[i]);
		}

		slots = 0 | 1 << 4;
		stream.writeByte(slots);

		for (int i = 0; i < 2; i++) {
			stream.writeShort(player.getClanCapeSymbols()[i]);
		}
	}

	public void writeClanCapeData(OutputStream stream) {
		int flag = 0;
		flag |= 0x4; // modify colors
		flag |= 0x8; // modify textures
		stream.writeByte(flag);

		int slots = 0 | 1 << 4 | 2 << 8 | 3 << 12;
		stream.writeShort(slots);

		for (int i = 0; i < 4; i++) {
			stream.writeShort(player.getClanCapeCustomized()[i]);
		}

		slots = 0 | 1 << 4;
		stream.writeByte(slots);

		for (int i = 0; i < 2; i++) {
			stream.writeShort(player.getClanCapeSymbols()[i]);
		}
	}
}

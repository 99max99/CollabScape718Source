package com.rs;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.*;


import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.Hit.HitLook;
import com.rs.game.player.Player;
import com.rs.game.player.content.magic.Magic;
import com.rs.utils.IPBanL;
import com.rs.Panel;
import com.rs.utils.Utils;
import javax.swing.JProgressBar;

public class Panel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8661221586251590570L;
	private JPanel contentPane;
	private JTextField textField;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Panel frame = new Panel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Panel() {
		setTitle("Tizen's 667 Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 408, 456);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(87, 25, 194, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(156, 11, 92, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPunishment = new JLabel("Punishment");
		lblPunishment.setBounds(73, 60, 104, 14);
		contentPane.add(lblPunishment);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 372, 372, 35);
		contentPane.add(progressBar);
		progressBar.setIndeterminate(true);
		
		JButton btnIpban = new JButton("IPBan");
		btnIpban.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IPban();
			}
		});
		btnIpban.setBounds(0, 85, 89, 23);
		contentPane.add(btnIpban);
		
		JButton btnMute = new JButton("Mute");
		btnMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mute();
			}
		});
		btnMute.setBounds(99, 85, 89, 23);
		contentPane.add(btnMute);

		
		JButton btnJail = new JButton("Jail");
		btnJail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jail();
			}
		});
		btnJail.setBounds(0, 153, 89, 23);
		contentPane.add(btnJail);
		
		JButton btnKill = new JButton("Kill");
		btnKill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kill();
			}
		});
		btnKill.setBounds(99, 119, 89, 23);
		contentPane.add(btnKill);
		
		JButton btnFreezeunfreeze = new JButton("Freeze");
		btnFreezeunfreeze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				freeze();
			}
		});
		btnFreezeunfreeze.setBounds(99, 153, 89, 23);
		contentPane.add(btnFreezeunfreeze);
		
		JLabel lblItemManagment = new JLabel("Item Managment");
		lblItemManagment.setBounds(261, 60, 104, 14);
		contentPane.add(lblItemManagment);
		
		JButton btnGiveItem = new JButton("Give Item");
		btnGiveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				giveItem();
			}
		});
		btnGiveItem.setBounds(204, 85, 89, 23);
		contentPane.add(btnGiveItem);
		
		JButton btnTakeItem = new JButton("Take Item");
		btnTakeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeItem();
			}
		});
		btnTakeItem.setBounds(303, 85, 89, 23);
		contentPane.add(btnTakeItem);
		
		JButton btnGiveAll = new JButton("Give All");
		btnGiveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				giveAll();
			}
		});
		btnGiveAll.setBounds(204, 119, 89, 23);
		contentPane.add(btnGiveAll);
		
		JButton btnTakeAll = new JButton("Take All");
		btnTakeAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeAll();
			}
		});
		btnTakeAll.setBounds(303, 119, 89, 23);
		contentPane.add(btnTakeAll);
		
		JLabel lblTeleportation = new JLabel("Teleportation");
		lblTeleportation.setBounds(73, 206, 104, 14);
		contentPane.add(lblTeleportation);
		
		JButton btnTeleport = new JButton("Teleport");
		btnTeleport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teleport();
			}
		});
		btnTeleport.setBounds(57, 231, 89, 23);
		contentPane.add(btnTeleport);
		
		JButton btnSendhome = new JButton("Tele All");
		btnSendhome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teleAll();
			}
		});
		btnSendhome.setBounds(57, 265, 89, 23);
		contentPane.add(btnSendhome);
		
		JLabel lblFunPanel = new JLabel("Fun Panel");
		lblFunPanel.setBounds(261, 177, 56, 14);
		contentPane.add(lblFunPanel);
		
		JButton btnMakeDance = new JButton("Make Dance");
		btnMakeDance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeDance();
			}
		});
		btnMakeDance.setBounds(231, 202, 114, 23);
		contentPane.add(btnMakeDance);
		
		JButton btnDanceAll = new JButton("Dance All");
		btnDanceAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				danceAll();
			}
		});
		btnDanceAll.setBounds(231, 236, 114, 23);
		contentPane.add(btnDanceAll);
		
		JButton btnForceChat = new JButton("Force Chat");
		btnForceChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				forceChat();
			}
		});
		btnForceChat.setBounds(231, 270, 114, 23);
		contentPane.add(btnForceChat);
		
		JButton btnSmite = new JButton("Smite");
		btnSmite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				smite();
			}
		});
		btnSmite.setBounds(231, 304, 114, 23);
		contentPane.add(btnSmite);
		
		JButton btnFuckUp = new JButton("Fuck Up");
		btnFuckUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fuckUp();
			}
		});
		btnFuckUp.setBounds(231, 338, 114, 23);
		contentPane.add(btnFuckUp);
		
		JButton btnNewButton = new JButton("Shutdown Server");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shutdown();
			}
		});
		btnNewButton.setBounds(10, 299, 194, 62);
		contentPane.add(btnNewButton);
		
	}
	
	public String getUsernameInput() {
		return textField.getText();
	}
	

	public void forceChat() {
		String name = getUsernameInput();
	Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		String chat = JOptionPane.showInputDialog("What Do You Want Him To Say?");
		target.setNextForceTalk(new ForceTalk(chat));
		System.out.println("Console: Forcing "+name+" To Say "+chat+"!");
		JOptionPane.showMessageDialog(null, "Forcing "+name+" To Say "+chat+"!", "Console", JOptionPane.PLAIN_MESSAGE);
	} else {
		JOptionPane.showMessageDialog(null, name+" Doesn't Exist", "Console", JOptionPane.ERROR_MESSAGE);
		System.out.println("Console: "
			+ Utils.formatPlayerNameForDisplay(name) + " Doesn't Exist");
		}
	}
	
	public void shutdown() {
		String sht = JOptionPane.showInputDialog("Shudown Delay?");
		int delay = Integer.parseInt(sht);
		World.safeShutdown(false, delay);
		System.out.println("Console: Shutting Server Down!");
		JOptionPane.showMessageDialog(null, "Shutting Server Down!", "Console", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void makeDance() {
		String name = getUsernameInput();
	Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		target.setNextAnimation(new Animation(7071));
		System.out.println("Console: You Have Made "+name+" Dance!");
		JOptionPane.showMessageDialog(null, "You Have Made "+name+" Dance!", "Console", JOptionPane.PLAIN_MESSAGE);
	} else {
		JOptionPane.showMessageDialog(null, name+" Doesn't Exist", "Console", JOptionPane.ERROR_MESSAGE);
		System.out.println("Console: "
			+ Utils.formatPlayerNameForDisplay(name) + " Doesn't Exist");
		}
	}
	
	public void smite() {
		String name = getUsernameInput();
	Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		target.setPrayerDelay(999999999);
		System.out.println("Console: You Have Made Smited "+name+"!");
		JOptionPane.showMessageDialog(null, "You Have Smited "+name+"!", "Console", JOptionPane.PLAIN_MESSAGE);
	} else {
		JOptionPane.showMessageDialog(null, name+" Doesn't Exist", "Console", JOptionPane.ERROR_MESSAGE);
		System.out.println("Console: "
			+ Utils.formatPlayerNameForDisplay(name) + " Doesn't Exist");
		}
	}
	
	public void fuckUp() {
		String name = getUsernameInput();
	Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		target.setPrayerDelay(999999999);
		target.addFreezeDelay(999999999);
		for (int i = 0; i < 10; i++)
			target.getCombatDefinitions().getBonuses()[i] = 0;
		for (int i = 14; i < target.getCombatDefinitions().getBonuses().length; i++)
			target.getCombatDefinitions().getBonuses()[i] = 0;
		System.out.println("Console: You Have Made Fucked Up "+name+"!");
		JOptionPane.showMessageDialog(null, "You Have Fucked Up "+name+"!", "Console", JOptionPane.PLAIN_MESSAGE);
	} else {
		JOptionPane.showMessageDialog(null, name+" Doesn't Exist", "Console", JOptionPane.ERROR_MESSAGE);
		System.out.println("Console: "
			+ Utils.formatPlayerNameForDisplay(name) + " Doesn't Exist");
		}
	}
	
	public void IPban() {
		String name = getUsernameInput();
	Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		boolean loggedIn = true;
		IPBanL.ban(target, loggedIn);
		System.out.println("Console: Successfully IP Banned "
				+ name + ".");
		JOptionPane.showMessageDialog(null, "Successfully IP Banned "+name, "Console", JOptionPane.PLAIN_MESSAGE);
	} else {
		JOptionPane.showMessageDialog(null, name+" Doesn't Exist!", "Console", JOptionPane.ERROR_MESSAGE);
		System.out.println("Console: "
			+ Utils.formatPlayerNameForDisplay(name) + " Doesn't Exist!");
		}
	}
	
	public void mute() {
		String name = getUsernameInput();
	Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		target.setMuted(Utils.currentTimeMillis()
				+ (48 * 60 * 60 * 1000));
		System.out.println("Console: Muted "
				+ name + ".");
		JOptionPane.showMessageDialog(null, "Successfully Muted "+name, "Console", JOptionPane.PLAIN_MESSAGE);
	} else {
		JOptionPane.showMessageDialog(null, name+" Doesn't Exist!", "Console", JOptionPane.ERROR_MESSAGE);
		System.out.println("Console: "
			+ Utils.formatPlayerNameForDisplay(name) + " Doesn't Exist!");
		}
	}
	
	public void kill() {
		String name = getUsernameInput();
	Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		target.applyHit(new Hit(target, target.getHitpoints(),
				HitLook.REGULAR_DAMAGE));
		target.stopAll();
		System.out.println("Console: Killed "
				+ name + ".");
		JOptionPane.showMessageDialog(null, "Successfully Killed "+name, "Console", JOptionPane.PLAIN_MESSAGE);
	} else {
		JOptionPane.showMessageDialog(null, name+" Doesn't Exist!", "Console", JOptionPane.ERROR_MESSAGE);
		System.out.println("Console: "
			+ Utils.formatPlayerNameForDisplay(name) + " Doesn't Exist!");
		}
	}
	public void jail() {
		String name = getUsernameInput();
	Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		target.setJailed(Utils.currentTimeMillis()
				+ (24 * 60 * 60 * 1000));
		target.getControlerManager().startControler("JailControler");
		System.out.println("Console: Jailed "
				+ name + ".");
		JOptionPane.showMessageDialog(null, "Successfully Jailed "+name, "Console", JOptionPane.PLAIN_MESSAGE);
	} else {
		JOptionPane.showMessageDialog(null, name+" Doesn't Exist!", "Console", JOptionPane.ERROR_MESSAGE);
		System.out.println("Console: "
			+ Utils.formatPlayerNameForDisplay(name) + " Doesn't Exist!");
		}
	}
	
	public void freeze() {
		String name = getUsernameInput();
	Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		target.addFreezeDelay(999999999);
		System.out.println("Console: Frozen "
				+ name + ".");
		JOptionPane.showMessageDialog(null, "Successfully Frozen "+name, "Console", JOptionPane.PLAIN_MESSAGE);
	} else {
		JOptionPane.showMessageDialog(null, name+" Doesn't Exist!", "Console", JOptionPane.ERROR_MESSAGE);
		System.out.println("Console: "
			+ Utils.formatPlayerNameForDisplay(name) + " Doesn't Exist!");
		}
	}
	
	public void giveItem() {
		String name = getUsernameInput();
	Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		String id = JOptionPane.showInputDialog("Item Id");
		String quantity = JOptionPane.showInputDialog("Item Amount");
		int item = Integer.parseInt(id);
		int amount = Integer.parseInt(quantity);
		target.getInventory().addItem(item, amount);
		System.out.println("Console: Given Item To "
				+ name + ".");
		JOptionPane.showMessageDialog(null, "Given Item To "+name, "Console", JOptionPane.PLAIN_MESSAGE);
	} else {
		JOptionPane.showMessageDialog(null, name+" Doesn't Exist!", "Console", JOptionPane.ERROR_MESSAGE);
		System.out.println("Console: "
			+ Utils.formatPlayerNameForDisplay(name) + " Doesn't Exist!");
		}
	}
	
	public void teleport() {
		String name = getUsernameInput();
	Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		String x = JOptionPane.showInputDialog("Coordinate X");
		String y = JOptionPane.showInputDialog("Coordinate Y");
		String h = JOptionPane.showInputDialog("Height Level");
		int coordx = Integer.parseInt(x);
		int coordy = Integer.parseInt(y);
		int height = Integer.parseInt(h);
		Magic.sendNormalTeleportSpell(target, 0, 0, new WorldTile(coordx, coordy, height));
		System.out.println("Console: Teleported "+name+" To "+coordx+", "+coordy+", "+height);
		JOptionPane.showMessageDialog(null, "Console: Teleported "+name+" To "+coordx+", "+coordy+", "+height, "Console", JOptionPane.PLAIN_MESSAGE);
	} else {
		JOptionPane.showMessageDialog(null, name+" Doesn't Exist!", "Console", JOptionPane.ERROR_MESSAGE);
		System.out.println("Console: "
			+ Utils.formatPlayerNameForDisplay(name) + " Doesn't Exist!");
		}
	}
	
	public void teleAll() {
		String x = JOptionPane.showInputDialog("Coordinate X");
		String y = JOptionPane.showInputDialog("Coordinate Y");
		String h = JOptionPane.showInputDialog("Height Level");
		int coordx = Integer.parseInt(x);
		int coordy = Integer.parseInt(y);
		int height = Integer.parseInt(h);
		for (Player teleall : World.getPlayers()) {
		Magic.sendNormalTeleportSpell(teleall, 0, 0, new WorldTile(coordx, coordy, height));
		}
		System.out.println("Console: Teleported Everyone To "+coordx+", "+coordy+", "+height);
		JOptionPane.showMessageDialog(null, "Console: Teleported Everyone To "+coordx+", "+coordy+", "+height, "Console", JOptionPane.PLAIN_MESSAGE);
}
	
	public void danceAll() {
		for (Player danceAll : World.getPlayers()) {
			danceAll.setNextAnimation(new Animation(7071));
		}
		System.out.println("Console: Making Everyone Dance!");
		JOptionPane.showMessageDialog(null, "Console: Making Everyone Dance!", "Console", JOptionPane.PLAIN_MESSAGE);
}
	
	public void takeItem() {
		String name = getUsernameInput();
	Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		String id = JOptionPane.showInputDialog("Item Id");
		String quantity = JOptionPane.showInputDialog("Item Amount");
		int item = Integer.parseInt(id);
		int amount = Integer.parseInt(quantity);
		target.getInventory().deleteItem(item, amount);
		System.out.println("Console: Taken Item "+item+" From "
				+ name + ".");
		JOptionPane.showMessageDialog(null, "Taken Item "+item+" From "+name, "Console", JOptionPane.PLAIN_MESSAGE);
	} else {
		JOptionPane.showMessageDialog(null, name+" Doesn't Exist!", "Console", JOptionPane.ERROR_MESSAGE);
		System.out.println("Console: "
			+ Utils.formatPlayerNameForDisplay(name) + " Doesn't Exist!");
		}
	}
	
	public void giveAll() {
		String id = JOptionPane.showInputDialog("Item Id");
		String quantity = JOptionPane.showInputDialog("Item Amount");
		int item = Integer.parseInt(id);
		int amount = Integer.parseInt(quantity);
		for (Player giveall : World.getPlayers()) {
		giveall.getInventory().addItem(item, amount);
		}
		System.out.println("Console: Given Item "+item+" To All Players");
		JOptionPane.showMessageDialog(null, "Given Item "+item+" To All Players", "Console", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void takeAll() {
		String id = JOptionPane.showInputDialog("Item Id");
		String quantity = JOptionPane.showInputDialog("Item Amount");
		int item = Integer.parseInt(id);
		int amount = Integer.parseInt(quantity);
		for (Player takeall : World.getPlayers()) {
		takeall.getInventory().deleteItem(item, amount);
		}
		System.out.println("Console: Taken Item "+item+" From All Players");
		JOptionPane.showMessageDialog(null, "Taken Item "+item+" From All Players", "Console", JOptionPane.PLAIN_MESSAGE);
	}
}
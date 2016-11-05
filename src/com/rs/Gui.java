package com.rs;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;


import com.rs.game.Animation;
import com.rs.game.Hit;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.Hit.HitLook;
import com.rs.game.player.Player;
import com.rs.game.player.content.XPWell;
import com.rs.game.player.content.magic.Magic;
import com.rs.utils.IPBanL;
import com.rs.utils.SerializableFilesManager;

import javax.swing.border.MatteBorder;

import java.awt.Color;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import javax.swing.JProgressBar;

public class Gui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/*private JTextField txtPlayerUsername;
	private final Action action = new SwingAction();
	private JButton btnBan;*/

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 try {
						   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						  } catch (Exception e) {}
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Gui frame = new Gui();
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
	public Gui() {
		setTitle("Exylum's Server Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 611, 361);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBackground(Color.CYAN);
		progressBar.setToolTipText("");
		progressBar.setOrientation(SwingConstants.VERTICAL);
		progressBar.setIndeterminate(true);
		progressBar.setBounds(541, 11, 42, 295);
		contentPane.add(progressBar);
		
		JButton btnNewButton = new JButton("Kick");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBounds(10, 11, 151, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Kick(evt);
            }
        });
		
		JButton btnNewButton_1 = new JButton("Unban");
		btnNewButton_1.setBounds(10, 79, 151, 23);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Unban(evt);
            }
        });
		
		JButton btnNewButton_3 = new JButton("Server Message");
		btnNewButton_3.setBounds(10, 147, 151, 23);
		contentPane.add(btnNewButton_3);
		btnNewButton_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ServerMessage(evt);
            }
        });
		
		JButton btnNewButton_4 = new JButton("Give item");
		btnNewButton_4.setBounds(10, 115, 151, 23);
		contentPane.add(btnNewButton_4);
		btnNewButton_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	giveItem(evt);
            }
        });
		
		JButton btnNewButton_5 = new JButton("Teleport player");
		btnNewButton_5.setBounds(10, 181, 151, 23);
		contentPane.add(btnNewButton_5);
		btnNewButton_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	TelePlayer(evt);
            }
        });
		
		JButton btnNewButton_6 = new JButton("Message player");
		btnNewButton_6.setBounds(10, 215, 151, 23);
		contentPane.add(btnNewButton_6);
		btnNewButton_6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	PlayerMessage(evt);
            }
        });
		
		JButton btnNewButton_7 = new JButton("Uptime");
		btnNewButton_7.setBounds(10, 249, 151, 23);
		contentPane.add(btnNewButton_7);
		btnNewButton_7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	Uptime(evt);
            }
        });
		
		JButton btnNewButton_8 = new JButton("Update");
		btnNewButton_8.setBounds(10, 283, 151, 23);
		contentPane.add(btnNewButton_8);
		btnNewButton_8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	Update(evt);
            }
        });
		
		JButton btnNewButton_9 = new JButton("Take item");
		btnNewButton_9.setBounds(173, 11, 151, 23);
		contentPane.add(btnNewButton_9);
		btnNewButton_9.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                Takeitem(evt);
	            }
	        });
		
		JButton btnNewButton_10 = new JButton("All dance");
		btnNewButton_10.setBounds(173, 45, 151, 23);
		contentPane.add(btnNewButton_10);
		btnNewButton_10.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                Alldance(evt);
	            }
	        });
		
		JButton btnNewButton_11 = new JButton("Kill player");
		btnNewButton_11.setBounds(173, 79, 151, 23);
		contentPane.add(btnNewButton_11);
		btnNewButton_11.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                Killplayer(evt);
	            }
	        });
		
		JButton btnNewButton_67 = new JButton("Give Spins");
		btnNewButton_67.setBounds(173, 113, 151, 23);
		contentPane.add(btnNewButton_67);
		btnNewButton_67.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                Givespins(evt);
	            }
	        });
		
		JButton btnNewButton1 = new JButton("ban");
		btnNewButton1.setBounds(10, 45, 151, 23);
		contentPane.add(btnNewButton1);
		btnNewButton1.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                Ban(evt);
	            }
	        });
	}
	
    private void Kick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
	{
		String name = JOptionPane.showInputDialog(null, "Who would u like to Kick");
		Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		target.getSession().getChannel().close();
		target.forceLogout();
        JOptionPane.showMessageDialog(null, "You have kicked " + name +"");
	} else {
		JOptionPane.showMessageDialog(null,  name +" was not found");
	}
}
	}      // TODO add your handling code here:

 private void Unban(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
	{
		String name = JOptionPane.showInputDialog(null, "Who would u like to Unban");
		Player target = World.getPlayerByDisplayName(name);
		target = SerializableFilesManager.loadPlayer(name);
	if (target != null) {
		target.setUsername(name);
		IPBanL.unban(target);
		JOptionPane.showMessageDialog(null, "You have Unbanned " + name +"");
		SerializableFilesManager.savePlayer(target);
	} else {
		JOptionPane.showMessageDialog(null,  name +" was not found");
	}
}
	}
 
 private void Killplayer(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
	{
		String name = JOptionPane.showInputDialog(null, "Who would u like to kill");
		Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
		target.stopAll();
		target.applyHit(new Hit(target, 999,
				HitLook.REGULAR_DAMAGE));
		JOptionPane.showMessageDialog(null, "You have killed " + name +"");
	} else {
		JOptionPane.showMessageDialog(null,  name +" was not found");
	}
}
	}
 
 public transient Player player;
 
 private void PlayerMessage(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
	{
		//String name = JOptionPane.showInputDialog(null, "Who would u like to send a message to?");
		/*String message = JOptionPane.showInputDialog(null, "Enter message");
		Player target = World.getPlayerByDisplayName(name);*/
	/*if (target != null) {
		target.getPackets().receivePrivateMessage(name, "Server", 2, message);
	} else {
		JOptionPane.showMessageDialog(null,  name +" was not found");
	}*/
}
	}
 
 private void Uptime(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
	{
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		long second = bean.getUptime() / 1000;
		long minute = second / 60; //Just add hours and days if ya want.
		 JOptionPane.showMessageDialog(null, "Server uptime: " + minute + " minute(s)");
		}
	} 
 
 private void TelePlayer(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
	{
		String name = JOptionPane.showInputDialog(null, "Who would u like to Teleport");
		Player target = World.getPlayerByDisplayName(name);
		String x = JOptionPane.showInputDialog(null, "Enter x");
		String y = JOptionPane.showInputDialog(null, "Enter y");
		String z = JOptionPane.showInputDialog(null, "Enter z");
		int x2 = Integer.parseInt(x);
		int y2 = Integer.parseInt(y);
		int z2 = Integer.parseInt(z);
		if (target != null) {
		Magic.sendNormalTeleportSpell(target, 0, 0, new WorldTile(x2, y2, z2));
		JOptionPane.showMessageDialog(null, "You have Teleported " + name);
		} else {
			JOptionPane.showMessageDialog(null,  name +" was not found");
		}
	}
		}
 
 private void ServerMessage(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
	{
		String message = JOptionPane.showInputDialog(null, "Enter message you would like to send");
		World.sendWorldMessage("<col=ff0000>[Sever Message]: " + message + "", true);
		JOptionPane.showMessageDialog(null, "Message sent");
		}
	} 
 
 private void Update(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
	{
		String delay1 = JOptionPane.showInputDialog(null, "Enter update delay(seconds)");
		int delay = Integer.parseInt(delay1);
    	World.safeShutdown(true, delay);
    	XPWell.save();
        JOptionPane.showMessageDialog(null, "Shutting down in " + delay + " seconds!");
	}
	} 
 
 private void giveItem(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
	{
		String name = JOptionPane.showInputDialog(null, "Who would u like to Give an item to");
		Player target = World.getPlayerByDisplayName(name);
		String itemID = JOptionPane.showInputDialog(null, "Enter itemId");
		String amount = JOptionPane.showInputDialog(null, "Enter amount of item");
	if (target != null) {
		int i = Integer.parseInt(itemID);
		int a = Integer.parseInt(amount);
		target.getInventory().addItem(i, a);
		JOptionPane.showMessageDialog(null, "Item(s) sent.");
	} else {
		JOptionPane.showMessageDialog(null,  name +" was not found");
	}
}
	}
 
 private void Takeitem(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
	{
		String name = JOptionPane.showInputDialog(null, "Who would u like to take an item from");
		Player target = World.getPlayerByDisplayName(name);
		String itemID = JOptionPane.showInputDialog(null, "Enter itemId");
		String amount = JOptionPane.showInputDialog(null, "Enter amount of item");
		int i = Integer.parseInt(itemID);
		int a = Integer.parseInt(amount);
		if (target != null) {
		target.getInventory().deleteItem(i, a);
		JOptionPane.showMessageDialog(null, "Item(s) Taken.");
		} else {
			JOptionPane.showMessageDialog(null,  name +" was not found");
		}
	}
		}
 
 private void Givespins(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
	{
		String name = JOptionPane.showInputDialog(null, "Who would u like to give spins too.");
		Player target = World.getPlayerByDisplayName(name);
		String itemID = JOptionPane.showInputDialog(null, "Amount of spins?");
		int i = Integer.parseInt(itemID);
		if (target != null) {
		target.spins += i;
		JOptionPane.showMessageDialog(null, "you have given " + name + " " + itemID + " spins.");
		target.getPackets().sendGameMessage("You have been given " + itemID + " spins.");
		target.getPackets().sendConfigByFile(11026, player.spins + 1);
		} else {
			JOptionPane.showMessageDialog(null,  name +" was not found");
		}
	}
		}
 
 private void Alldance(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
	{
		for(Player p : World.getPlayers())
			p.setNextAnimation(new Animation(7071));
		JOptionPane.showMessageDialog(null, "Everyone is now dancing.");
		}
	}
    
private void Ban(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
{
	String name = JOptionPane.showInputDialog(null, "Who would u like to ban");
	Player target = World.getPlayerByDisplayName(name);
	if (target != null) {
	//target.setPermBanned(true);
	target.getSession().getChannel().close();
	JOptionPane.showMessageDialog(null, "You have Banned " + name +"");
	} else {
		JOptionPane.showMessageDialog(null,  name +" was not found");
	}
}
	}

	class SwingAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
}
}
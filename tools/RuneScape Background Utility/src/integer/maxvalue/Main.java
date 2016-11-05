package integer.maxvalue;

import integer.maxvalue.image.ImageProcessor;
import integer.maxvalue.utils.ExtensionFileFilter;
import integer.maxvalue.utils.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

public class Main implements ActionListener {

	private BufferedImage image = null;
	private JComboBox<String> outComboBox;
	private JFileChooser chooser = new JFileChooser();
	private JButton btnChoose;
	private JScrollPane scrollPane;
	private JFrame frame;
	public static JLabel lblProgress = new JLabel();
	private DefaultListModel<String> dlm = new DefaultListModel<String>();

	public static void main(String[] args) throws Exception, InterruptedException {
		new Main().openFrame();
	}

	public void openFrame() throws Exception {
		frame = new JFrame("Image Cropper - 2^31-1");
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 350, 290);
		frame.setLocationRelativeTo(null);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Choose the image you wish to use:");
		lblNewLabel.setBounds(10, 11, 207, 14);
		contentPane.add(lblNewLabel);

		btnChoose = new JButton("Choose");
		btnChoose.setBounds(10, 36, 89, 23);
		contentPane.add(btnChoose);
		btnChoose.addActionListener(this);

		String[] formats = {"PNG", "GIF", "JPG"};
		outComboBox = new JComboBox<String>(formats);
		outComboBox.setBounds(10, 159, 69, 20);
		contentPane.add(outComboBox);
		outComboBox.addActionListener(this);

		JLabel label = new JLabel("Choose the image format of the produced image(s):");
		label.setBounds(10, 134, 314, 14);
		contentPane.add(label);

		JButton btnDone = new JButton("Done");
		btnDone.setBounds(207, 226, 117, 23);
		contentPane.add(btnDone);
		btnDone.addActionListener(this);

		JButton btnProduce = new JButton("Produce Image(s)");
		btnProduce.setBounds(10, 226, 117, 23);
		contentPane.add(btnProduce);
		btnProduce.addActionListener(this);

		lblProgress.setBounds(10, 190, 314, 14);
		contentPane.add(lblProgress);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JList<String> list = new JList<String>(dlm);
		scrollPane.setViewportView(list);
		
		JLabel lblNewLabel_1 = new JLabel("Made by: 2^31-1");
		lblNewLabel_1.setBounds(227, 11, 97, 14);
		contentPane.add(lblNewLabel_1);

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand().toLowerCase();
		System.out.println(action);
		if (action.equals("done")) {
			System.exit(-1);
		} else if (action.equals("produce image(s)")) {
			if (dlm.get(0) != null) {
				try {
					image = ImageIO.read(new File(dlm.get(0)));
					Utils.IMAGE_OUT_FORMAT = outComboBox.getSelectedItem().toString().toLowerCase();
					if (Utils.IMAGE != null && Utils.IMAGE_IN_FORMAT != null) {
						new ImageProcessor(image);
					}
					Main.lblProgress.setText("Finished.");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frame, "Invalid image, check the path and try again.");
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Invalid image, check the path and try again.");
			}
		} else if (action.equals("choose")) {
			FileFilter filter = new ExtensionFileFilter("JPG, GIF, PNG", new String[] { "JPG", "GIF", "PNG" });
			chooser.setFileFilter(filter);
			chooser.updateUI();
			chooser.addActionListener(this);
			chooser.showDialog(frame, "Select image");
		} else if (action.equals("approveselection")) {
			btnChoose.setBounds(10, 87, 89, 23);
			scrollPane.setBounds(10, 36, 314, 40);
			Utils.IMAGE = chooser.getSelectedFile();
			Utils.IMAGE_IN_FORMAT = Utils.IMAGE.getAbsolutePath().substring(Utils.IMAGE.getAbsolutePath().length() - 3, Utils.IMAGE.getAbsolutePath().length());
			System.out.println(Utils.IMAGE_IN_FORMAT);
			dlm.clear();
			dlm.addElement(Utils.IMAGE.getAbsolutePath());
		}
	}
}

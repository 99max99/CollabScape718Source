package integer.maxvalue.image;

import integer.maxvalue.Main;
import integer.maxvalue.utils.Utils;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessor {

	private BufferedImage imageCopy = null;
	private Image scaledImage;
	
	public ImageProcessor(BufferedImage image) throws IOException, InterruptedException {
		System.out.println(needsResizing(image));
		if (needsResizing(image))
			resizeImage(image);
		cropImage(imageCopy == null ? image : imageCopy);
	}

	public void resizeImage(BufferedImage image) throws InterruptedException {
		Main.lblProgress.setText("Resizing image...");
		scaledImage = Toolkit.getDefaultToolkit().getImage(Utils.IMAGE.getAbsolutePath()).getScaledInstance((int)Utils.RESIZED_IMAGE_SIZE.getWidth(), (int)Utils.RESIZED_IMAGE_SIZE.getHeight(), 0);
		imageCopy = Utils.toBufferedImage(scaledImage);
	}

	private boolean needsResizing(BufferedImage image) {
		Dimension IMAGE_SIZE = new Dimension(image.getWidth(), image.getHeight());
		if (IMAGE_SIZE.getSize() != Utils.RESIZED_IMAGE_SIZE.getSize()) 
			return true;
		return false;
	}

	public void cropImage(BufferedImage image) throws IOException {
		Main.lblProgress.setText("Cropping image...");
		BufferedImage croppedImage;
		int width = 239, height = 250;
		int x = 0, y = 0;
		for (int i = 1; i < 9; i++) {
			if (i == 5) {
				x = 0;
				y = 250;
				height = 253;
			}
			croppedImage = image.getSubimage(x, y, width, height);
			x += 239;
			ImageIO.write(croppedImage, Utils.IMAGE_OUT_FORMAT, new File(Utils.IMAGE_OUT_PATH+i+"."+Utils.IMAGE_OUT_FORMAT));
		}
	}
	
}

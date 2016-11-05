package integer.maxvalue.utils;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;

import javax.swing.ImageIcon;

public class Utils {
	
	public static final String IMAGE_OUT_PATH = "./out/";

	public static String IMAGE_IN_FORMAT;

	public static String IMAGE_OUT_FORMAT;

	public static File IMAGE = null;

	public static final Dimension RESIZED_IMAGE_SIZE = new Dimension(956, 503);
	
	public static BufferedImage toBufferedImage(Image image) throws InterruptedException {
		if (image instanceof BufferedImage) {
			return (BufferedImage)image;
		}
		image = new ImageIcon(image).getImage();
		boolean hasAlpha = hasAlpha(image);
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		int transparency = Transparency.OPAQUE;
		if (hasAlpha) {
			transparency = Transparency.BITMASK;
		}
		GraphicsDevice gs = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gs.getDefaultConfiguration();
		bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		if (bimage == null) {
			int type = BufferedImage.TYPE_INT_RGB;
			if (hasAlpha) {
				type = BufferedImage.TYPE_INT_ARGB;
			}
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}
		Graphics g = bimage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bimage;
	}

	public static boolean hasAlpha(Image image) throws InterruptedException {
		if (image instanceof BufferedImage) {
			return ((BufferedImage)image).getColorModel().hasAlpha();
		}
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		pg.grabPixels();
		return pg.getColorModel().hasAlpha();
	}
}

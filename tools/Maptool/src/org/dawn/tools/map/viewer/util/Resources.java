package org.dawn.tools.map.viewer.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {

	public static BufferedImage IMG_DOT = null;
	
	static {
		try {
			IMG_DOT = ImageIO.read(new File("data/map/sprites/dot/dot.png"));
		} catch (IOException e) {
			System.err.println("Could not load dot.png");
			e.printStackTrace();
		}
	}
	
}

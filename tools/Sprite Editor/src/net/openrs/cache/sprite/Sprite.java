package net.openrs.cache.sprite;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import net.openrs.cache.Cache;
import net.openrs.cache.Container;
import net.openrs.cache.FileStore;
import net.openrs.util.ByteBufferUtils;

/**
 * 
 * @author Stephen Moyer <golden_32@live.com>
 * 
 */
public final class Sprite {

	public static HashMap<Integer, Sprite> sprites = new HashMap<Integer, Sprite>();
	private Sprite[] class185s;
	public Container container;
	private int maxWidth;
	private int maxHeight;
	public ArrayList<BufferedImage> images;
	protected int width;
	protected int[] pixelData;
	protected byte[] pixels;
	protected int anInt2723;
	protected int anInt2724;
	protected int height;
	protected byte[] pixels2; //could be colors or something idk
	protected int anInt2727;
	protected int anInt2728;
	public int id;

	public Sprite(Container data, int spriteId) {
		this.id = spriteId;
		this.container = data;
		try {
			readSprite(data.getData(), id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Sprite() {
		images = new ArrayList<BufferedImage>();
	}

	public int[] convertPixelData() {
		int[] rgbArray = new int[width * height];
		int position = 0;
		int index = 0;
		if (pixels2 != null) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					rgbArray[index++] = (pixels2[position] << 24 | (pixelData[pixels[position] & 0xff]));
					position++;
				}
			}
		} else {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int rgb = pixelData[pixels[position++] & 0xff];
					rgbArray[index++] = rgb != 0 ? ~0xffffff | rgb : 0;
				}
			}
		}
		return rgbArray;
	}

	private void readSprite(ByteBuffer buffer, int spriteId) throws IOException {
		int limit = buffer.limit();
		buffer.position(limit - 2);
		int i = buffer.getShort();
		class185s = new Sprite[i];
		for (int i_93_ = 0; i_93_ < i; i_93_++)
			class185s[i_93_] = new Sprite();
		buffer.position(limit - 7 - i * 8);
		maxWidth = buffer.getShort();
		maxHeight = buffer.getShort();
		int i_96_ = (buffer.get() & 0xff) + 1;
		for (int i_97_ = 0; i_97_ < i; i_97_++)
			class185s[i_97_].anInt2724 = buffer.getShort();
		for (int i_98_ = 0; i_98_ < i; i_98_++)
			class185s[i_98_].anInt2727 = buffer.getShort();
		for (int i_99_ = 0; i_99_ < i; i_99_++)
			class185s[i_99_].width = buffer.getShort();
		for (int i_100_ = 0; i_100_ < i; i_100_++)
			class185s[i_100_].height = buffer.getShort();
		for (int i_101_ = 0; i_101_ < i; i_101_++) {
			Sprite class185 = class185s[i_101_];
			class185.anInt2728 = maxWidth - class185.width - class185.anInt2724;
			class185.anInt2723 = maxHeight - class185.height - class185.anInt2727;
		}
		buffer.position(limit - 7 - i * 8 - (i_96_ - 1) * 3);
		int[] is = new int[i_96_];
		for (int i_102_ = 1; i_102_ < i_96_; i_102_++) {
			is[i_102_] = ByteBufferUtils.getTriByte(buffer);
			if (is[i_102_] == 0) {
				is[i_102_] = 1;
			}
		}
		for (int i_103_ = 0; i_103_ < i; i_103_++)
			class185s[i_103_].pixelData = is;
		buffer.position(0);
		for (int i_104_ = 0; i_104_ < i; i_104_++) {
			Sprite class185 = class185s[i_104_];
			int i_105_ = class185.width * class185.height;
			class185.pixels = new byte[i_105_];
			int i_106_ = buffer.get();
			if ((i_106_ & 0x2) == 0) {
				if ((i_106_ & 0x1) == 0) {
					for (int i_107_ = 0; i_107_ < i_105_; i_107_++)
						class185.pixels[i_107_] = buffer.get();
				} else {
					for (int i_108_ = 0; i_108_ < class185.width; i_108_++) {
						for (int i_109_ = 0; i_109_ < class185.height; i_109_++)
							class185.pixels[i_108_ + i_109_ * class185.width] = buffer
							.get();
					}
				}
			} else {
				boolean bool = false;
				class185.pixels2 = new byte[i_105_];
				if ((i_106_ & 0x1) == 0) {
					for (int i_110_ = 0; i_110_ < i_105_; i_110_++)
						class185.pixels[i_110_] = buffer.get();
					for (int i_111_ = 0; i_111_ < i_105_; i_111_++) {
						byte b = class185.pixels2[i_111_] = buffer.get();
						bool = bool | b != -1;
					}
				} else {
					for (int i_112_ = 0; i_112_ < class185.width; i_112_++) {
						for (int i_113_ = 0; i_113_ < class185.height; i_113_++)
							class185.pixels[i_112_ + i_113_ * class185.width] = buffer
							.get();
					}
					for (int i_114_ = 0; i_114_ < class185.width; i_114_++) {
						for (int i_115_ = 0; i_115_ < class185.height; i_115_++) {
							byte b = class185.pixels2[i_114_ + i_115_
							                              * class185.width] = buffer.get();
							bool = bool | b != -1;
						}
					}
				}
				if (!bool) {
					class185.pixels2 = null;
				}
			}
		}
		convertImages(class185s);
	}

	private void convertImages(Sprite[] class185s) {
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		for (int i = 0; i < class185s.length; i++) {
			if (class185s[i].width <= 0 || 0 >= class185s[i].height) {
				images.add(i, null);
				continue;
			}
			BufferedImage image = new BufferedImage(class185s[i].width,
					class185s[i].height, BufferedImage.TYPE_INT_RGB);
			image.setRGB(0, 0, class185s[i].width, class185s[i].height,
					class185s[i].convertPixelData(), 0, class185s[i].width);
			images.add(i, image);
			image.flush();
		}
		this.images = images;
	}

	public ByteBuffer encode() throws IOException {
		convertBufferedImages();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream buffer = new DataOutputStream(bos);
		for (int imageId = 0; imageId < images.size(); imageId++) {
			int pixelsMask = 0;
			if (class185s[imageId].pixels2 != null)
				pixelsMask |= 0x2;
			buffer.write((byte) pixelsMask);
			for (int pixelIndex = 0; pixelIndex < class185s[imageId].pixels.length; pixelIndex++)
				buffer.write((byte) class185s[imageId].pixels[pixelIndex]);
			if (class185s[imageId].pixels2 != null)
				for (int index = 0; index < class185s[imageId].pixels2.length; index++)
					buffer.write(class185s[imageId].pixels2[index]);
		}
		for (int index = 0; index < pixelData.length; index++)
			ByteBufferUtils.putTriByte(buffer, pixelData[index]);
		if (maxWidth == 0 && maxHeight == 0) {
			for (BufferedImage image : images) {
				if (image.getWidth() > maxWidth)
					maxWidth = image.getWidth();
				if (image.getHeight() > maxHeight)
					maxHeight = image.getHeight();
			}
		}
		buffer.writeShort((short) maxWidth);
		buffer.writeShort((short) maxHeight);
		buffer.write((byte) (pixelData.length - 1));
		for (int imageId = 0; imageId < images.size(); imageId++)
			buffer.writeShort((short) images.get(imageId).getMinX());
		for (int imageId = 0; imageId < images.size(); imageId++)
			buffer.writeShort((short) images.get(imageId).getMinY());
		for (int imageId = 0; imageId < images.size(); imageId++)
			buffer.writeShort((short) images.get(imageId).getWidth());
		for (int imageId = 0; imageId < images.size(); imageId++)
			buffer.writeShort((short) images.get(imageId).getHeight());
		buffer.writeShort((short) images.size());
		
		return ByteBuffer.wrap(bos.toByteArray());
	}


	public int getPixelIndex(int rgb) {
		for (int index = 0; index < pixelData.length; index++) {
			if (pixelData[index] == rgb)
				return index;
		}
		int[] inscreasedPallete = new int[pixelData.length + 1];
		System.arraycopy(pixelData, 0, inscreasedPallete, 0, pixelData.length);
		inscreasedPallete[pixelData.length] = rgb;
		pixelData = inscreasedPallete;
		if (pixelData.length > 256) {
			JOptionPane.showMessageDialog(null, "Reduce the image quality or delete some child sprites");
			throw new RuntimeException("Reduce the image quality or delete some child sprites");
		}
		return pixelData.length - 1;
	}

	public void convertBufferedImages() {
		Sprite[] newSprites = new Sprite[images.size()];
		if (pixelData == null) {
			pixelData = new int[]{0};
		}
		for (int index = 0; index < images.size(); index++) {
			newSprites[index] = new Sprite();
			BufferedImage image = images.get(index);
			int[] rgbArray = new int[image.getWidth() * image.getHeight()];
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), rgbArray, 0, image.getWidth());
			newSprites[index].pixels = new byte[image.getWidth() * image.getHeight()];
			newSprites[index].pixels2 = new byte[image.getWidth() * image.getHeight()];
			for (int pixel = 0; pixel < newSprites[index].pixels.length; pixel++) {
				int rgb = rgbArray[pixel];
				ByteBuffer buffer = ByteBuffer.allocate(4);
				buffer.putInt(rgb);
				buffer.position(1);
				int mediumRgb = ByteBufferUtils.getTriByte(buffer);
				int i = getPixelIndex(mediumRgb);
				newSprites[index].pixels[pixel] = (byte) i;
				if (rgb >> 24 != 0) {
					newSprites[index].pixels2[pixel] = (byte) (rgb >> 24);
				}
			}
		}
		this.class185s = newSprites;
	}

	public static Sprite get(Cache store, int spriteId) {
		if (!sprites.containsKey(spriteId)) {
			try {
				Sprite sprite = new Sprite(store.read(8, spriteId), spriteId);
				sprites.put(spriteId, sprite);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sprites.get(spriteId);
	}

	public static Sprite get(Cache store, String name) {
		int spriteId = -1;
		try {
			spriteId = store.getFileId(8, name);
			if (!sprites.containsKey(spriteId) && spriteId > -1) {
				Sprite sprite = new Sprite(store.read(8, spriteId), spriteId);
				sprites.put(spriteId, sprite);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sprites.get(spriteId);
	}

	public static Sprite addSprite(Cache cache, BufferedImage baseSprite) {
		try {
			Sprite sprite = new Sprite();
			sprite.images.add(baseSprite);
			sprites.put(cache.getFileCount(8), sprite);
			Container c = new Container(2, sprite.encode(), 1);
			cache.write(8, cache.getFileCount(8), c);
			sprite.container = c;
			return sprite;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

}

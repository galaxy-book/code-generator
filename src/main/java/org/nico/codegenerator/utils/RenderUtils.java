package org.nico.codegenerator.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

public class RenderUtils {

	final static String BASE = "@#&$%*o!;.";

	final static char LINE_N = 'n';
	final static char LINE_R = 'r';

	//    final static Map<String, String> COMPRESSION_DICTIONARY = new HashMap<String, String>();
	//
	//    static {
	//        char[] arr = new char[] {'@','#','&','$','%','*','o','!',';','.','n','r'};
	//        int count = 0;
	//        for(int index = 0; index < arr.length - 1; index ++) {
	//            for(int j = index + 1; j < arr.length; j ++) {
	//                String key1 = arr[index] + "1" + arr[j] + "1";
	//                String key2 = arr[j] + "1" + arr[index] + "1";
	//                COMPRESSION_DICTIONARY.put(key1, "(" + supplement(Integer.toString(count ++, 16), 2));
	//                COMPRESSION_DICTIONARY.put(key2, "(" + supplement(Integer.toString(count ++, 16), 2));
	//            }
	//        }
	//        //        COMPRESSION_DICTIONARY.entrySet().forEach(System.out::println);
	//
	//    }

	public static String supplement(String str, int index) {
		int diff = index - str.length();
		if(diff <= 0) return str;
		String s = "";
		while(diff -- > 0) {
			s += "0";
		}
		return s + str;
	}

	public static String renderChar(BufferedImage image) throws IOException{
		StringBuilder builder = new StringBuilder();
		for (int y = 0; y < image.getHeight(); y += 2) {
			for (int x = 0; x < image.getWidth(); x++) {
				final int pixel = image.getRGB(x, y);
				final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
				final float gray = 0.299f * r + 0.578f * g + 0.114f * b;
				final int index = Math.round(gray * (BASE.length() + 1) / 255);
				builder.append(index >= BASE.length() ? " " : String.valueOf(BASE.charAt(index)));
			}
			builder.append(System.lineSeparator());
		}
		return builder.toString();
	}
	
	public static String renderChar(BufferedImage image, int width, int height) throws IOException{
	    image = resize(image, width, height);
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < image.getHeight(); y += 2) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixel = image.getRGB(x, y);
                final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
                final float gray = 0.299f * r + 0.578f * g + 0.114f * b;
                final int index = Math.round(gray * (BASE.length() + 1) / 255);
                builder.append(index >= BASE.length() ? " " : String.valueOf(BASE.charAt(index)));
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

	public static BufferedImage resize(BufferedImage source, int targetW,     
			int targetH) {     
		int type = source.getType();     
		BufferedImage target = null;     
		double sx = (double) targetW / source.getWidth();     
		double sy = (double) targetH / source.getHeight();     
		if (sx < sy) {     
			sx = sy;     
			targetW = (int) (sx * source.getWidth());     
		} else {     
			sy = sx;     
			targetH = (int) (sy * source.getHeight());     
		}     
		if (type == BufferedImage.TYPE_CUSTOM) { 
			ColorModel cm = source.getColorModel();     
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW,     
					targetH);     
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();     
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);     
		} else    
			target = new BufferedImage(targetW, targetH, type);     
		Graphics2D g = target.createGraphics();     
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,     
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);     
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));     
		g.dispose();     
		return target;     
	}

	public static BufferedImage renderImage(String str, int width, int height){
		try {
			int fontSize = 6;
			BufferedImage newbufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = newbufferedImage.getGraphics();
			Font font = new Font("Simsun", Font.PLAIN, fontSize);
			g.setClip(0, 0, width, height);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.BLACK);
			g.setFont(font);
			
			int i = 0;
			for(String line: str.split(System.lineSeparator())) {
				g.drawString(line, 0, fontSize * (i ++));
			}
			g.dispose();
			return newbufferedImage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encode(String txt) {
		char[] chars = txt.toCharArray();
		StringBuilder builder = new StringBuilder();
		int count = 0;
		char last = '~';
		for(char c: chars) {
			if(c == '\n') c = LINE_N;
			if(c == '\r') c = LINE_R;
			if(c != last) {
				if(count != 0) {
					if(count == 1) {
						builder.append(last + "");
					}else {
						builder.append(last + "" + count);
					}
				}
				count = 1;
				last = c;
			}else {
				count ++;
			}
		}
		if(count != 0) {
			builder.append(last + "" + count);
		}
		return builder.toString();
	}

	//    public static String encode2(String str) {
	//        char[] chars = str.toCharArray();
	//        StringBuilder builder = new StringBuilder();
	//
	//        for(int index = 0; index < chars.length;) {
	//            int pos = index + 1;
	//            int s = 1;
	//            while(pos < chars.length) {
	//                if(chars[pos] >= '0' && chars[pos] <= '9') {
	//                    pos ++;
	//                }else {
	//                    if(s == 0) break;
	//                    s -- ;
	//                    pos ++;
	//                }
	//            }
	//            pos --;
	//
	//            String target = String.valueOf(Arrays.copyOfRange(chars, index, pos + 1));
	//            String result = null;
	//            if((result = COMPRESSION_DICTIONARY.get(target)) != null) {
	//                builder.append(result);
	//            }else {
	//                builder.append(target);
	//            }
	//            index = pos + 1;
	//        }
	//        return builder.toString();
	//    }

	public static String decode(String txt) {
		char[] chars = txt.toCharArray();
		StringBuilder builder = new StringBuilder();
		for(int index = 0; index < chars.length;) {
			char c = chars[index];

			if(c == LINE_N) c = '\n';
			if(c == LINE_R) c = '\r';

			int pos = index + 1;
			while(pos < chars.length) {
				if(chars[pos] >= '0' && chars[pos] <= '9') {
					pos ++;
				}else {
					break;
				}
			}
			pos --;
			if(index == pos) {
				builder.append(c);
				index ++;
				continue;
			}else {
				builder.append(StringUtils.join(new String[Integer.valueOf(String.valueOf(Arrays.copyOfRange(chars, index + 1, pos + 1))) + 1], c));
				index = pos + 1;
			}
		}
		return builder.toString();
	}

	public static String encode2(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return out.toString("ISO-8859-1");
	}

	public static String decode2(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(str
				.getBytes("ISO-8859-1"));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
		return out.toString();
	}

	public static void main(String[] args) throws IOException {
	    BufferedImage image = ImageIO.read(new File("C:\\Users\\ainil\\Desktop\\test3.jpg"));
	    System.out.println(image.getWidth());
	    System.out.println(image.getHeight());
	    
		String txt = renderChar(resize(image, (int)(image.getWidth() / 3.15), (int)(image.getHeight() / 2.95)));
		System.out.println(txt);
		BufferedImage image1 = renderImage(txt, image.getWidth(), image.getHeight());
		ImageIO.write(image1, "jpg", new File("C:\\Users\\ainil\\Desktop\\test1.jpg"));
	}
}

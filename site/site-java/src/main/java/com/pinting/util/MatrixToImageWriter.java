package com.pinting.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.utils.GlobEnv;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class MatrixToImageWriter {
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	// LOGO宽度  
    private static final int WIDTH = 60;  
    // LOGO高度  
    private static final int HEIGHT = 60;  
    // 二维码尺寸  
    private static final int QRCODE_SIZE = 400;

	private MatrixToImageWriter() {
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix,String logoPath,boolean needCompress) throws Exception {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		if(StringUtil.isEmpty(logoPath)) {
			return image;
		}
		insertImage(image,logoPath,needCompress);
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file,String logoPath,boolean needCompress)
			throws Exception {
		BufferedImage image = toBufferedImage(matrix,logoPath,needCompress);
		file.getParentFile().mkdirs();
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format,
			OutputStream stream,String logoPath,boolean needCompress) throws Exception {
		BufferedImage image = toBufferedImage(matrix,logoPath,needCompress);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format "
					+ format);
		}
	}
	
	
	/**
	 * 生成地址content的二维码
	 * @param content
	 * @return 二维码存在的路径
	 */
	public static String createMatrixImage(String content,String name,String logoPath,boolean needCompress) {
		 //测试地址
		String path = GlobEnv.get("wxQRCode.tmp.path");
		File file = null;
		
		try {
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(content,
					BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
			file = new File(path, name + ".png");
			MatrixToImageWriter.writeToFile(bitMatrix, "png", file,logoPath,needCompress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getPath();
	}
	
	/** 
     * 插入LOGO 
     *  
     * @param source 
     *            二维码图片 
     * @param imgPath 
     *            LOGO图片地址 
     * @param needCompress 
     *            是否压缩 
     * @throws Exception 
     */  
    private static void insertImage(BufferedImage source, String imgPath,  
            boolean needCompress) throws Exception {  
        File file = new File(imgPath);  
        if (!file.exists()) {  
            System.err.println(""+imgPath+"   该文件不存在！");  
            return;  
        }  
        Image src = ImageIO.read(new File(imgPath));  
        int width = src.getWidth(null);  
        int height = src.getHeight(null);  
        if (needCompress) { // 压缩LOGO  
            if (width > WIDTH) {  
                width = WIDTH;  
            }  
            if (height > HEIGHT) {  
                height = HEIGHT;  
            }  
            Image image = src.getScaledInstance(width, height,  
                    Image.SCALE_SMOOTH);  
            BufferedImage tag = new BufferedImage(width, height,  
                    BufferedImage.TYPE_INT_RGB);  
            Graphics g = tag.getGraphics();  
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
            g.dispose();  
            src = image;  
        }  
        // 插入LOGO  
        Graphics2D graph = source.createGraphics();  
        int x = (QRCODE_SIZE - width) / 2;  
        int y = (QRCODE_SIZE - height) / 2;  
        graph.drawImage(src, x, y, width, height, null);  
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);  
        graph.setStroke(new BasicStroke(3f));  
        graph.draw(shape);  
        graph.dispose();  
    }  
}

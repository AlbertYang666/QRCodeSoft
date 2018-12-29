/**  
 
* @Title: BufferedImageLuminanceSource.java
 
* @Package com.albertyy.QrCode
 
* @Description: TODO
 
* @author yangxianyang  
 
* @date 2018年12月10日 下午8:06:07
 
* @version V1.0  
 
*/

package com.albertyy.QrCode;

/**  
*   
* 项目名称：QRCodeSoft  
* 类名称：BufferedImageLuminanceSource  
* 类描述：  图片类
* 创建人：yangyangyang  
* 创建时间：2018年12月10日 下午8:06:07  
* 修改人：yangyangyang  
* 修改时间：2018年12月10日 下午8:06:07  
* 修改备注：  
* @version   
*   
*/

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
 
import com.google.zxing.LuminanceSource;
 
public class BufferedImageLuminanceSource extends LuminanceSource {
    private final BufferedImage image;
    private final int left;
    private final int top;
 
    public BufferedImageLuminanceSource(BufferedImage image) {
        this(image, 0, 0, image.getWidth(), image.getHeight());
    }
 
    public BufferedImageLuminanceSource(BufferedImage image, int left,
            int top, int width, int height) {
        super(width, height);
 
        int sourceWidth = image.getWidth();
        int sourceHeight = image.getHeight();
        if (left + width > sourceWidth || top + height > sourceHeight) {
            throw new IllegalArgumentException(
                    "Crop rectangle does not fit within image data.");
        }
 
        for (int y = top; y < top + height; y++) {
            for (int x = left; x < left + width; x++) {
                if ((image.getRGB(x, y) & 0xFF000000) == 0) {
                    image.setRGB(x, y, 0xFFFFFFFF); // = white
                }
            }
        }
 
        this.image = new BufferedImage(sourceWidth, sourceHeight,
                BufferedImage.TYPE_BYTE_GRAY);
        this.image.getGraphics().drawImage(image, 0, 0, null);
        this.left = left;
        this.top = top;
    }
 
   
    public byte[] getRow(int y, byte[] row) {
        if (y < 0 || y >= getHeight()) {
            throw new IllegalArgumentException(
                    "Requested row is outside the image: " + y);
        }
        int width = getWidth();
        if (row == null || row.length < width) {
            row = new byte[width];
        }
        image.getRaster().getDataElements(left, top + y, width, 1, row);
        return row;
    }
 
   
    public byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        int area = width * height;
        byte[] matrix = new byte[area];
        image.getRaster().getDataElements(left, top, width, height, matrix);
        return matrix;
    }
 
   
    public boolean isCropSupported() {
        return true;
    }
 
   
    public LuminanceSource crop(int left, int top, int width, int height) {
        return new BufferedImageLuminanceSource(image, this.left + left,
                this.top + top, width, height);
    }
 
   
    public boolean isRotateSupported() {
        return true;
    }
 
   
    public LuminanceSource rotateCounterClockwise() {
        int sourceWidth = image.getWidth();
        int sourceHeight = image.getHeight();
        AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0,
                0.0, 0.0, sourceWidth);
        BufferedImage rotatedImage = new BufferedImage(sourceHeight,
                sourceWidth, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = rotatedImage.createGraphics();
        g.drawImage(image, transform, null);
        g.dispose();
        int width = getWidth();
        return new BufferedImageLuminanceSource(rotatedImage, top,
                sourceWidth - (left + width), getHeight(), width);
    }
}


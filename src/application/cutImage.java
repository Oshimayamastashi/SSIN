package application;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class cutImage {

	public static BufferedImage scaleImage_pane(int WIDTH, int HEIGHT, String filename) throws IOException {
		File f = new File(filename);
		Boolean equalProportion = true;
		BufferedImage bufferedImage = ImageIO.read(f);

		int sWidth = bufferedImage.getWidth();
		int sHeight = bufferedImage.getHeight();
		int diffHeight=0,diffWidth = 0;
		if((double)sWidth/WIDTH>(double)sHeight/HEIGHT){  
            int height2=WIDTH*sHeight/sWidth;  
            diffHeight=(HEIGHT-height2)/2;  
        }else
        {  
            int width2=HEIGHT*sWidth/sHeight;  
            diffWidth=(WIDTH-width2)/2;  
        }  
		BufferedImage nbufferedImage = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = nbufferedImage.createGraphics();// 创建一个 Graphics2D，可以将它绘制到此 BufferedImage 中。
		g.setColor(Color.white);// 控制颜色
		g.fillRect(0,0, WIDTH,HEIGHT);// 填充整個螢幕
		g.drawImage(bufferedImage, diffWidth, diffHeight, (WIDTH-diffWidth*2),(HEIGHT-diffHeight*2), null); // 繪製縮小後的圖  
			
		return nbufferedImage;
	}

}

package me.buildr.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	public String path;
	public static int height, width;
	
	public int[] pixels;
	
	public SpriteSheet(String path){
		BufferedImage image = null;
		
		try{
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		if(image == null){
			return;
		}
		
		this.path = path;
		height = image.getHeight();
		width = image.getWidth();
		
		pixels = image.getRGB(0, 0, width, height, null, 0 , width);
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = (pixels[i] & 0xffff) / 64;
		}
	}
}

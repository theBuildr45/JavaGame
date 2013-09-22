package me.buildr.Game;

import me.buildr.gfx.SpriteSheet;

public class Screen {

	public static final int MAP_WIDTH = 128;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;

	public int[] tile = new int[MAP_WIDTH * MAP_WIDTH];
	public int[] colors = new int[MAP_WIDTH * MAP_WIDTH * 4];

	public int xOffSet;
	public int yOffSet;

	public int width;
	public int height;

	public SpriteSheet sheet;

	public Screen(int width, int height, SpriteSheet sheet){
		this.width = width;
		this.height = height;
		this.sheet = sheet;

		for(int i = 0; i < MAP_WIDTH * MAP_WIDTH; i++){
			colors[i * 4 + 0] = 0xff00ff;
			colors[i * 4 + 1] = 0x00ffff;
			colors[i * 4 + 2] = 0xffff00;
			colors[i * 4 + 3] = 0xffffff;
		}
	}

	public void render(int pixels[], int offset, int row){
		for(int yTile = yOffSet >> 3; yTile <= (yOffSet + height) >> 2; yTile++){
			int yMin = yTile * 8 - yOffSet;
			int yMax = yMin + 8;	
			if(yMin < 0)yMin = 0;
			if(yMax > height)yMax = height;

			for(int xTile = xOffSet >> 3; xTile <= (xOffSet + width) >> 2; xTile++){
				int xMin = xTile * 8 - xOffSet;
				int xMax = xMin + 8;	
				if(xMin < 0)xMin = 0;
				if(xMax > width)xMax = width;

				int tileIndex = (yTile & (MAP_WIDTH_MASK)) + (xTile & (MAP_WIDTH_MASK)) * MAP_WIDTH;
				
				for(int y = yMin; y < yMax; y++){
					int sheetPixel = ((y + yOffSet) & 7) * SpriteSheet.width + ((xMin + xOffSet) & 7);
					int tilePixel = offset + xMin + y * row;
					
					for(int x = xMin; x < xMax;x++){
						int colour = (tileIndex * 3) + sheet.pixels[sheetPixel++];
						pixels[tilePixel++] = colors[colour];
					}
				}
			}
		}
	}
	
	public void renderPlayer(int x, int y){
		
	}
}

package me.buildr.Game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import me.buildr.gfx.SpriteSheet;

public class Game extends Canvas implements Runnable{


	private static final long serialVersionUID = 1L;
	
	public int tickCount = 0;
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 3;
	public static final String NAME = "Untiteld Game";

	private boolean running = false;
	
	public Screen screen;
	public InputHandler input;
	
	public int x, y;
	BufferedImage image = new BufferedImage(WIDTH, HEIGHT , BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game(){
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		init();
	}
	
	public void init(){
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/Icons.png"));
		input = new InputHandler(this);
	}
	
	public synchronized void start(){
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop(){
		running = false;
	}

	int ticks = 0;
	int frames = 0;

	public void run(){
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;


		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;

			lastTime = now;
			boolean shouldRender = true;

			while(delta >= 1){
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(shouldRender){
				frames++;
				render();
			}
			if(System.currentTimeMillis() - lastTimer >= 1000){
				lastTimer += 1000;

				System.out.println("Frames : " + frames + " , " + "Ticks : " + ticks);
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void tick(){
		tickCount++;
		
		if(input.up == true){
			screen.yOffSet--;
		}
		else if(input.down == true){
			screen.yOffSet++;
		}
		else if(input.left == true){
			screen.xOffSet--;
		}
		else if(input.right == true){
			screen.xOffSet++;
		}
		
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = i + tickCount;
		}
	}

	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		
		screen.render(pixels, 0, WIDTH);
		g.drawRect(x, y, 20, 20);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		

		g.dispose();
		bs.show();
	}

	public static void main(String[] args){
		JFrame frame = new JFrame(NAME);
		Game game = new Game();

		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		
		frame.addKeyListener(new InputHandler(game));
		frame.setSize(game.getPreferredSize());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();
	}
}

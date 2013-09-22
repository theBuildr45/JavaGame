package me.buildr.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener{

	public boolean up;
	public boolean down;
	public boolean right;
	public boolean left;

	public InputHandler(Game game) {
		game.addKeyListener(this);
	}

	public void removeAll(){
		up = down = left = right = false;
	}
	public void toggleKey(KeyEvent key, boolean toggled){
		if(key.getKeyCode() == key.VK_UP && toggled == true){
			up = true;
		}
		if(key.getKeyCode() == key.VK_DOWN && toggled == true){
			down = true;
		}
		if(key.getKeyCode() == key.VK_RIGHT && toggled == true){
			right = true;
		}
		if(key.getKeyCode() == key.VK_LEFT && toggled == true){
			left = true;
		}
	}

	public void keyPressed(KeyEvent e) {
		toggleKey(e, true);
	}

	public void keyReleased(KeyEvent e) {
		toggleKey(e, false);
	}

	public void keyTyped(KeyEvent e) {		
	}

}

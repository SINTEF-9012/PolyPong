package org.polypong;

import java.awt.Point;
import java.awt.event.KeyListener;

public class GameWindow extends Window {

	protected GameWindow(int width, int height, int scale) {
		super(width, height, scale, "Main game window", true);
		this.canvas.setFocusable(true);
		this.canvas.requestFocusInWindow();
		this.frame.setVisible(true);
		this.canvas.repaint();
		
		Point location = this.getScreenSize();
		location.x = (location.x-width*scale)/2;
		location.y = 10;
		this.frame.setLocation(location);
	}
	
	public void addKeyListener(KeyListener l) {
		this.canvas.addKeyListener(l);
	}
}

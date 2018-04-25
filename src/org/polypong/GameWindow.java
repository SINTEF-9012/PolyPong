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
	
	@Override
	public void setupTransform(int numberOfPlayers) {
		super.setupTransform(numberOfPlayers);
		
		final double maxDim = Math.max(width, height)/2;
		transform.translate(width/2, height/2);
		transform.scale(maxDim/R*.9, maxDim/R*.9);
	}
	
	public void addKeyListener(KeyListener l) {
		this.frame.addKeyListener(l);
	}
}

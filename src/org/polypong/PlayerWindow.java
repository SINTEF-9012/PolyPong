package org.polypong;

import java.awt.Point;

public class PlayerWindow extends Window {

	protected PlayerWindow(int width, int height, int scale, int playerNum, int numPlayers) {
		super(width, height, scale, "Player "+playerNum+" window", true);
		this.canvas.setFocusable(false);
		this.frame.setVisible(true);
		this.canvas.repaint();
		
		Point location = this.getScreenSize();
		location.x = (location.x-20)/(3*numPlayers+1)*(3*playerNum+1)+10;
		location.y = location.y-height*scale-80;
		this.frame.setLocation(location);
	}

}

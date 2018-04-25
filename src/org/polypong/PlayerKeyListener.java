package org.polypong;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerKeyListener implements KeyListener {

	final int leftKey = KeyEvent.VK_LEFT;
	final int rightKey = KeyEvent.VK_RIGHT;

	final PlayerState player;

	public PlayerKeyListener(PlayerState player) {
		this.player = player;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch(keyCode) { 
		case leftKey:
			player.updatePlayer(-8, 0);
			break;
		case rightKey :
			player.updatePlayer(8, 0);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}

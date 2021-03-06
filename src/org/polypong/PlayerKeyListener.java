package org.polypong;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerKeyListener implements KeyListener {

	final int leftKey;
	final int rightKey;

	boolean left = false;
	boolean right = false;
	
	final PlayerState player;

	public PlayerKeyListener(PlayerState player) {
		this.player = player;
		switch (player.playerID % 3) {
			case 0:
				this.leftKey = KeyEvent.VK_LEFT;
				this.rightKey = KeyEvent.VK_RIGHT;
				break;
			case 1:
				this.leftKey = KeyEvent.VK_A;
				this.rightKey = KeyEvent.VK_D;
				break;
			default:
				this.leftKey = KeyEvent.VK_NUMPAD4;
				this.rightKey = KeyEvent.VK_NUMPAD6;
		}
	}
	
	private void updatePlayerSpeed() {
		if (left && !right) {
			player.playerSpeed = -0.05;
		} else if (!left && right) {
			player.playerSpeed = 0.05;
		} else {
			player.playerSpeed = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		final int keyCode = e.getKeyCode();
		if (keyCode == leftKey) {
			left = true;
		} else if (keyCode == rightKey) {
			right = true;
		}
		updatePlayerSpeed();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		final int keyCode = e.getKeyCode();
		if (keyCode == leftKey) {
			left = false;
		} else if (keyCode == rightKey) {
			right = false;
		}		
		updatePlayerSpeed();
	}

}

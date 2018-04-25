package org.polypong;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerKeyListener implements KeyListener {

	final int leftKey;
	final int rightKey;

	final PlayerState player;

	public PlayerKeyListener(PlayerState player) {
		this.player = player;
		switch (player.playerID) {
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

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == leftKey) {
			player.playerSpeed = -0.05;
		} else if (keyCode == rightKey) {
			player.playerSpeed = 0.05;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == leftKey) {
			player.playerSpeed = 0;
		} else if (keyCode == rightKey) {
			player.playerSpeed = 0;
		}
	}

}

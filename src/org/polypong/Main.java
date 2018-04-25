package org.polypong;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

	public static void main(String[] args) {
		final int scale = 5;
		final int numPlayers = 2;
		final int tick = 1000;
		
		GameWindow gameWindow = new GameWindow(100, 100, scale);
		PlayerWindow[] playerWindows = new PlayerWindow[numPlayers];
		
		for (int n = 0; n < numPlayers; n++) {
			playerWindows[n] = new PlayerWindow(100, 100, scale, n, numPlayers);
		}
		
		gameWindow.focus();
		
		// -- Run game tick --
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Tick");
				gameWindow.focus();
			}
		}, tick, tick);
	}

}

package org.polypong;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

	public static void main(String[] args) {
		final int tick = 50;
		final GameState state = new GameState(3);
		
		// -- Run game tick --
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				state.onTick();
			}
		}, tick, tick);
	}

}

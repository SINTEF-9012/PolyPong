package org.polypong;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

	public static void main(String[] args) {
		final int tick = 25;
		final GameState state = new GameState(4);
		
		// -- Run game tick --
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				state.onTick();
			}
		}, tick, tick);
	}

}

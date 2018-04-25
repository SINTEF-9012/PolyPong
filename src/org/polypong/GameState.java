package org.polypong;

import java.util.ArrayList;
import java.util.List;

public class GameState {
	final List<PlayerState> players = new ArrayList<PlayerState>();
	final GameWindow mainView = new GameWindow(100, 100, 5);
	
	public GameState(int numberOfPlayers) {
		for(int i = 0; i < numberOfPlayers; i++) {
			final PlayerState player = new PlayerState(this, i, numberOfPlayers);
			mainView.addKeyListener(new PlayerKeyListener(player));
			players.add(player);
		}
		mainView.setupTransform(numberOfPlayers);
		mainView.focus();
	}
	
	public void onTick() {
		for(PlayerState player : players) {
			//player.updateBall();
			player.onTick();
		}
		mainView.draw(this);
	}
}

package org.polypong;

import java.util.ArrayList;
import java.util.List;

public class GameState {
	
	List<PlayerState> players = new ArrayList<PlayerState>();
	final GameWindow mainView = new GameWindow(100, 100, 5);
	
	public GameState(int numberOfPlayers) {				
		for(int i = 0; i < numberOfPlayers; i++) {
			final PlayerState player = new PlayerState(this, numberOfPlayers);
			final PlayerKeyListener playerController = new PlayerKeyListener(player);
		}
	}
	
	protected void updatePlayer(int playerID, float dx, float dy) {
		//TODO: some math and update main view
	}
	
	public void onTick() {
		for(PlayerState player : players) {
			player.updateBall();
		}
	}

}

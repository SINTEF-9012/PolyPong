package org.polypong;

import java.util.ArrayList;
import java.util.List;

public class GameState {
	final List<PlayerState> players = new ArrayList<PlayerState>();
	final List<BallState> balls = new ArrayList<BallState>();
	final GameWindow mainView = new GameWindow(100, 100, 5);
	
	public GameState(int numberOfPlayers) {
		for(int i = 0; i < numberOfPlayers; i++) {
			final PlayerState player = new PlayerState(this, i, numberOfPlayers);
			mainView.addKeyListener(new PlayerKeyListener(player));
			players.add(player);
		}
		balls.add(new BallState(this, 0, -0.5, 0.01, 0.02));
		
		mainView.setupTransform(numberOfPlayers);
		mainView.focus();
		calculatePolyPoints(numberOfPlayers);
	}
	
	int numberOfPlayers;
	double polyX[];
	double polyY[];
	void calculatePolyPoints(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		double R = 1/(2*Math.sin(Math.PI/(numberOfPlayers*2)));
		double theta = Math.PI/numberOfPlayers;
		double phi = -Math.PI/2-theta/2;
		
		this.polyX = new double[numberOfPlayers*2+1];
		this.polyY = new double[numberOfPlayers*2+1];
		
		for (int i = 0; i < numberOfPlayers*2; i++) {
			polyX[i] = R*Math.cos(i*theta+phi);
			polyY[i] = R*Math.sin(i*theta+phi);
		}
		
		this.polyX[numberOfPlayers*2] = this.polyX[0];
		this.polyY[numberOfPlayers*2] = this.polyY[0];
	}
	
	public void onTick() {
		for(PlayerState player : players) {
			player.onTick();
		}
		for (BallState ball : balls) {
			ball.onTick();
		}
		mainView.draw(this);
	}
}

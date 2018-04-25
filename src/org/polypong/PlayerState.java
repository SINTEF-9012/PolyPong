package org.polypong;

public class PlayerState {
	
	final GameState game;
	final PlayerWindow view; 
	
	public PlayerState(GameState game, int numberOfPlayers) {
		this.game = game;
		this.view = new PlayerWindow(100, 100, 5, playerID, numberOfPlayers);
	}
	
	int playerID = 0;
	
	float playerX = 50;
	float playerY = 0;
	float ballX = 50;
	float ballY = 0;
	
	float ballDX = 10;
	float ballDY = 10;
		
	protected void updateBall() {
		ballX = ballX + ballDX;
		ballY = ballY + ballDY;	
	}
	
	protected void updatePlayer(float dx, float dy) {
		game.updatePlayer(playerID, dx, dy);
		playerX = playerX + dx;
		playerY = playerY + dy;	
		//TODO: update player view
	}

}

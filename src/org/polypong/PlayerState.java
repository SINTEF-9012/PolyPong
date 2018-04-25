package org.polypong;

public class PlayerState {
	
	final GameState game;
	final PlayerWindow view; 
	
	public PlayerState(GameState game, int playerID, int numberOfPlayers) {
		this.game = game;
		this.playerID = playerID;
		this.view = new PlayerWindow(100, 100, 5, playerID, numberOfPlayers);
		this.view.setupTransform(numberOfPlayers, playerID);
	}
	
	int playerID = 0;
	double playerSpeed = 0;
	double playerPos = 0.5;
	double playerWidth = 0.4;
	
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
	
	protected void updatePlayer(double delta) {
		//TODO: update player view
	}
	
	public void onTick() {
		playerPos += playerSpeed;
		if (playerPos-playerWidth/2 < 0) {
			playerPos = playerWidth/2;
		} else if (playerPos+playerWidth/2 > 1) {
			playerPos = 1-playerWidth/2;
		}
		
		view.draw(game);
	}
}

package org.polypong;

public class BallState {
	GameState game;
	
	double speedX;
	double speedY;
	double posX;
	double posY;
	
	public BallState(GameState game, double posX, double posY, double speedX, double speedY) {
		this.game = game;
		this.speedX = speedX;
		this.speedY = speedY;
		this.posX = posX;
		this.posY = posY;
	}
	
	public void onTick() {
		posX += speedX;
		posY += speedY;
		
		double maxd = -Double.MAX_VALUE;
		int maxsegment = 0;
		
		// Check if we are out of bounds
		for (int i = 0; i < this.game.numberOfPlayers*2; i++) {
			double x1 = this.game.polyX[i];
			double y1 = this.game.polyY[i];
			double x2 = this.game.polyX[i+1];
			double y2 = this.game.polyY[i+1];
			
			// Signed distance to segment
			double d = (posX-x1)*(y2-y1) - (posY-y1)*(x2-x1);
			
			if (d > maxd) {
				maxd = d;
				maxsegment = i;
			}
		}
		
		// If outside a segment
		if (maxd >= 0) {
			double x1 = this.game.polyX[maxsegment];
			double y1 = this.game.polyY[maxsegment];
			double x2 = this.game.polyX[maxsegment+1];
			double y2 = this.game.polyY[maxsegment+1];
			
			// Normalise segment vector
			double N = Math.hypot(x2-x1, y2-y1);
			double xn = (x2-x1)/N;
			double yn = (y2-y1)/N;
			
			// Project speed onto segment
			double P = speedX*xn + speedY*yn;
			
			// Reflect speed around segment
			speedX = -speedX + 2*xn*P;
			speedY = -speedY + 2*yn*P;
			
			// Is this a player segment?
			if (maxsegment % 2 == 0) {
				// Calculate location along player segment
				P = (posX-x1)*xn + (posY-y1)*yn;
				if (this.game.players.get(maxsegment/2).isOutsidePaddle(P)) {
					// TODO: Something more interesting
					speedX = 0;
					speedY = 0;
				}
			}
		}
	}
}

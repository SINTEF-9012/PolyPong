package org.polypong;

public class BallState {
	GameState game;
	
	double speedX;
	double speedY;
	double posX;
	double posY;
	
	public BallState(GameState game, double posX, double posY) {
		this.game = game;
		this.speedX = 0;
		this.speedY = 0;
		this.posX = posX;
		this.posY = posY;
	}
	
	void getSegment(double points[], int i) {
		points[0] = this.game.polygon[i].x;
		points[1] = this.game.polygon[i].y;
		points[2] = this.game.polygon[i+1].x;
		points[3] = this.game.polygon[i+1].y;
	}
	
	double projectOnto(double points[], double x, double y) {
		double N = Math.hypot(points[2]-points[0], points[3]-points[1]);
		double xn = (points[2]-points[0])/N;
		double yn = (points[3]-points[1])/N;
		
		return x*xn + y*yn;
	}

	void reflectSpeedAround(double points[]) {
		// Normalise segment vector
		double N = Math.hypot(points[2]-points[0], points[3]-points[1]);
		double xn = (points[2]-points[0])/N;
		double yn = (points[3]-points[1])/N;
		
		// Project speed onto segment
		double P = speedX*xn + speedY*yn;
		
		// Reflect speed around segment
		speedX = -speedX + 2*xn*P;
		speedY = -speedY + 2*yn*P;
	}
	
	public void onTickMove() {
		posX += speedX;
		posY += speedY;
	}
	
	public void onTickCollision() {
		double maxd = -Double.MAX_VALUE;
		int maxsegment = 0;
		
		double points[] = new double[4];
		
		// Check if we are out of bounds
		for (int i = 0; i < this.game.numberOfPlayers*2; i++) {
			getSegment(points, i);
			
			// Signed distance to segment
			double d = (posX-points[0])*(points[3]-points[1]) - (posY-points[1])*(points[2]-points[0]);
			
			if (d > maxd) {
				maxd = d;
				maxsegment = i;
			}
		}
		
		// If outside a segment
		if (maxd >= 0) {
			getSegment(points, maxsegment);
			reflectSpeedAround(points);
			
			// Is this a player segment?
			if (maxsegment % 2 == 0) {
				// Calculate location along player segment
				double P = projectOnto(points, posX-points[0], posY-points[1]);
				if (this.game.players.get(maxsegment/2).isOutsidePaddle(P)) {
					// TODO: Something more interesting
					speedX = 0;
					speedY = 0;
				}
			}
		} else {
			// Make balls collide with each other
			for (BallState other : this.game.balls) {
				if (other != this) {
					final double D = Math.hypot(this.posX-other.posX, this.posY-other.posY);
					if (D < 0.1) {
						points[0] = -this.posY;
						points[1] = this.posX;
						points[2] = -other.posY;
						points[3] = other.posX;
						reflectSpeedAround(points);
						break;
					}
				}
			}
		}
	}
}

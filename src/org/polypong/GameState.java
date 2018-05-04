package org.polypong;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;

public class GameState {
	/* --- Children --- */
	final int numberOfPlayers;
	final List<PlayerState> players = new ArrayList<PlayerState>();
	final List<BallState> balls = new ArrayList<BallState>();
	final GameWindow mainView = new GameWindow(100, 100, 5);
	
	/* --- World --- */
	final double R;
	final double theta;
	final double phi;
	final Point2D.Double polygon[];
	
	public GameState(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		
		// Make world
		polygon = new Double[2*numberOfPlayers+1];
		R = 1/(2*Math.sin(Math.PI/(numberOfPlayers*2)));
		theta = Math.PI/numberOfPlayers;
		phi = -Math.PI/2-theta/2;
		for (int i = 0; i < 2*numberOfPlayers; i++) {
			polygon[i] = new Double(R*Math.cos(i*theta+phi), R*Math.sin(i*theta+phi));
		}
		polygon[2*numberOfPlayers] = polygon[0];
		
		// Make players and balls
		for(int i = 0; i < numberOfPlayers; i++) {
			final PlayerState player = new PlayerState(this, i, numberOfPlayers);
			mainView.addKeyListener(new PlayerKeyListener(player));
			players.add(player);
			
			final double points[] = new double[4];
			mainView.calculateSegment(points, polygon, 2*i);
			final BallState ball = new BallState(this, (points[2]+points[0])/2, (points[3]+points[1])/2);
			double xn = -(points[3]-points[1]);
			double yn = points[2]-points[1];
			double ln = Math.hypot(xn, yn);
			xn *= 0.1/ln;
			yn *= 0.1/ln;
			ball.posX += xn;
			ball.posY += yn;
			ball.speedX = xn*0.3;
			ball.speedY = yn*0.3;
			
			balls.add(ball);
		}
		
		mainView.setupTransform(this);
		mainView.focus();
	}
	
	public void onTick() {
		for(PlayerState player : players) {
			player.onTick();
		}
		for (BallState ball : balls) {
			ball.onTickCollision();
		}
		for (BallState ball : balls) {
			ball.onTickMove();
		}
		mainView.draw(this);
	}
}

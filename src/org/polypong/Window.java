package org.polypong;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Window {
	protected JPanel canvas;
	protected JFrame frame;
	protected Graphics2D graphics;
	protected AffineTransform transform;
	
	protected int width;
	protected int height;
	
	protected int numberOfPlayers;
	protected double R;
	protected double theta;
	protected double phi;
	
	protected Window(int width, int height, int scale, String title, boolean exitOnClose) {
		this.width = width;
		this.height = height;
		
		final BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.graphics = buffer.createGraphics();
		
		this.canvas = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void update(Graphics g) {
				this.paint(g);
			}
			
			@Override
			public void paint(Graphics g) {
				g.drawImage(buffer, 0, 0, width*scale, height*scale, 0, 0, width, height, null);
			}
			
		};
		this.canvas.setPreferredSize(new Dimension(width*scale, height*scale));
		
		this.frame = new JFrame(title) {
			private static final long serialVersionUID = 1L;
			protected void processWindowEvent(java.awt.event.WindowEvent e) {
				super.processWindowEvent(e);
				if (exitOnClose && e.getID() == WindowEvent.WINDOW_CLOSING) {
					System.exit(0);
				}
			};
		};
		this.frame.setBackground(Color.BLACK);
		this.frame.add(this.canvas);
		this.frame.pack();
		
		
	}
	
	public void focus() {
		this.frame.requestFocus();
	}
	
	public Point getScreenSize() {
		DisplayMode mode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		return new Point(mode.getWidth(), mode.getHeight());
	}
	
	public void setupTransform(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		this.R = 1/(2*Math.sin(Math.PI/(numberOfPlayers*2)));
		this.theta = Math.PI/numberOfPlayers;
		this.phi = -Math.PI/2-theta/2;
		
		this.transform = new AffineTransform();
		this.transform.translate(0, height-1); // Move origin to bottom-left
		this.transform.scale(1, -1); // Flip so we get a right-hand coordinate system
	}
	
	/* -- Drawing methods -- */
	protected void clear() {
		this.graphics.clearRect(0, 0, width, height);
	}
	
	protected void transformAndDrawLine(double[] line) {
		this.transform.transform(line, 0, line, 0, 2);
		this.graphics.drawLine((int)line[0], (int)line[1], (int)line[2], (int)line[3]);
	}
	
	protected void calculateSegment(double[] line, int i) {
		line[0] = R*Math.cos(i*theta+phi);
		line[1] = R*Math.sin(i*theta+phi);
		line[2] = R*Math.cos((i+1)*theta+phi);
		line[3] = R*Math.sin((i+1)*theta+phi);
	}
	
	protected void calculatePaddle(double[] line, double pos, double width) {
		double xs = line[0];
		double ys = line[1];
		double xl = line[2]-line[0];
		double yl = line[3]-line[1];
		double start = pos-width/2;
		double end = pos+width/2;
		line[0] = xs+xl*start;
		line[1] = ys+yl*start;
		line[2] = xs+xl*end;
		line[3] = ys+yl*end;
	}
	
	public void draw(GameState state) {
		this.clear();
		
		double line[] = new double[4];
		
		// Draw walls
		this.graphics.setColor(Color.WHITE);
		for (int i = 0; i < numberOfPlayers; i++) {
			calculateSegment(line, i*2+1);
			transformAndDrawLine(line);
		}
		
		// Draw players
		for (int i = 0; i < numberOfPlayers; i++) {
			switch (i) {
			case 0:
				this.graphics.setColor(Color.RED);
				break;
			case 1:
				this.graphics.setColor(Color.YELLOW);
				break;
			default:
				this.graphics.setColor(Color.BLUE);
			}
			
			calculateSegment(line, i*2);
			PlayerState player = state.players.get(i);
			calculatePaddle(line, player.playerPos, player.playerWidth);
			transformAndDrawLine(line);
		}
		
		this.canvas.repaint();
	}
}

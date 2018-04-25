package org.polypong;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Window {
	protected JPanel canvas;
	protected JFrame frame;
	protected Graphics2D graphics;
	
	protected Window(int width, int height, int scale, String title, boolean exitOnClose) {
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
		this.frame.add(canvas);
		this.frame.pack();
	}
	
	public void focus() {
		this.frame.requestFocus();
	}
	
	public Point getScreenSize() {
		DisplayMode mode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		return new Point(mode.getWidth(), mode.getHeight());
	}
	
}

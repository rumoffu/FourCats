package edu.jhu.cs.oose.simplemousing;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.*;

/* 
 * A simple example of mouse listeners in action
 * Based on an example in the Core Java books
 * 
 */

public class SimpleSquaresUI {

	JFrame frame;

	public static void main(String[] args) {
		SimpleSquaresUI gui = new SimpleSquaresUI();
		gui.go();
	}

	public void go() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SquaresPanel panel = new SquaresPanel();
		frame.getContentPane().add(panel);

		frame.setSize(420, 300);
		frame.setVisible(true);
	}

}

/**
 * A panel with mouse operations for adding and removing squares.
 */
class SquaresPanel extends JPanel {

	public static final int SIDELENGTH = 10;
	private ArrayList<Rectangle2D> squares = new ArrayList<Rectangle2D>();

	public SquaresPanel() {

		addMouseListener(new MouseHandler());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// draw all squares
		for (int i = 0; i < squares.size(); i++)
			g2.draw(squares.get(i));
	}

	private class MouseHandler implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// do nothing
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// do nothing
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// do nothing
			
		}

		@Override
		public void mousePressed(MouseEvent event) {
				// add a new square

				Point2D p = event.getPoint();
				Rectangle2D newSquare = new Rectangle2D.Double(p.getX()
						- SIDELENGTH / 2, p.getY() - SIDELENGTH / 2, SIDELENGTH,
						SIDELENGTH);
				squares.add(newSquare);
				repaint();
			}
			

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// do nothing
			
		}
		
	}
	
	// An equivalent implementation of the above which uses MouseAdapter to implement empty MouseListener methods
	
/*	private class MouseHandler extends MouseAdapter {
		public void mousePressed(MouseEvent event) {
			// add a new square

			Point2D p = event.getPoint();
			Rectangle2D newSquare = new Rectangle2D.Double(p.getX()
					- SIDELENGTH / 2, p.getY() - SIDELENGTH / 2, SIDELENGTH,
					SIDELENGTH);
			squares.add(newSquare);
			repaint();
		}

	}*/
}

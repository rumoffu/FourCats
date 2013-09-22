package edu.jhu.cs.oose.mousing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/* 
 * A simple example of mouse listeners in action
 * Based on an example in the Core Java books
 * 
 */

public class SquaresUI {

	JFrame frame;

	public static void main(String[] args) {
		SquaresUI gui = new SquaresUI();
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	SquaresModel model;

	public SquaresPanel() {
		model = new SquaresModel();

		addMouseListener(new MouseHandler());
		addMouseMotionListener(new MouseMotionHandler());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// draw all squares
		for (int i = 0; i < model.numSquares(); i++)
			g2.draw(model.getSquare(i));
	}

	// the square containing the mouse cursor

	private class MouseHandler extends MouseAdapter {
		public void mousePressed(MouseEvent event) {
			// add a new square if the cursor isn't inside a square
			model.setCurrentSquareAtPoint(event.getPoint());
			if (!model.hasACurrentSquare())
				model.addSquare(event.getPoint());
			repaint();
		}

		public void mouseClicked(MouseEvent event) {
			// remove the current square if double clicked
			model.setCurrentSquareAtPoint(event.getPoint());
			if (model.hasACurrentSquare() && event.getClickCount() >= 2)
				model.removeCurrentSquare();
			repaint();
		}
	}

	private class MouseMotionHandler implements MouseMotionListener {
		public void mouseMoved(MouseEvent event) {
			// set the mouse cursor to cross hairs if it is inside
			// a rectangle

			if (model.find(event.getPoint()) == null)
				setCursor(Cursor.getDefaultCursor());
			else
				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}

		public void mouseDragged(MouseEvent event) {
			if (model.getCurrentSquare() != null) {
				int x = event.getX();
				int y = event.getY();

				// drag the current rectangle to center it at (x, y)
				model.moveCurrentSquare(x,y);
				repaint();
			}
		}
	}
}

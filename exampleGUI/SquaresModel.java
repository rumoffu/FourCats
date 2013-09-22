package edu.jhu.cs.oose.mousing;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class SquaresModel {

	public static final int SIDELENGTH = 10;
	private ArrayList<Rectangle2D> squares = new ArrayList<Rectangle2D>();

	private Rectangle2D currentSquare = null;

	public Rectangle2D getCurrentSquare() {
		return currentSquare;
	}

	public void setCurrentSquare(Rectangle2D currentSquare) {
		this.currentSquare = currentSquare;
	}

	/**
	 * Finds the first square containing a point.
	 * 
	 * @param p
	 *            a point
	 * @return the first square that contains p
	 */
	public Rectangle2D find(Point2D p) {
		for (int i = 0; i < squares.size(); i++) {
			Rectangle2D r = (Rectangle2D) squares.get(i);
			if (r.contains(p))
				return r;
		}

		return null;
	}

	/**
	 * Adds a square to the collection.
	 * 
	 * @param p
	 *            the center of the square
	 */
	public void addSquare(Point2D p) {
		double x = p.getX();
		double y = p.getY();

		currentSquare = new Rectangle2D.Double(x - SIDELENGTH / 2, y
				- SIDELENGTH / 2, SIDELENGTH, SIDELENGTH);
		squares.add(currentSquare);
	}

	/**
	 * Removes current square from the collection.
	 * 
	 */
	public void removeCurrentSquare() {
		if (currentSquare == null)
			return;
		squares.remove(currentSquare);
		currentSquare = null;
	}

	/**
	 * Gets a square from the collection.
	 * 
	 * @param i
	 *            the square to get
	 */
	public Rectangle2D getSquare(int i) {

		return (squares.get(i));
	}

	/**
	 * Get the number of squares
	 * 
	 */
	public int numSquares() {
		return (squares.size());
	}

	/**
	 * Move the current square
	 * 
	 * @param x, y
	 *            the target location to move to
	 */	public void moveCurrentSquare(int x, int y) {
		getCurrentSquare().setFrame(x - SIDELENGTH / 2, y - SIDELENGTH / 2,
				SIDELENGTH, SIDELENGTH);
	}


public boolean hasACurrentSquare() {
	return(currentSquare != null);
}

public boolean setCurrentSquareAtPoint(Point point) {
	currentSquare = find(point);
	if (currentSquare == null) 
		return(false);
	else return(true);
}

}

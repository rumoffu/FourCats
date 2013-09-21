/**
 * Kyle Wong
 * 13.9.13
 * OOSE HW1a
 * kwong23@jhu.edu
 */
package kyle.brickus;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;

/**
 * Class for BrickusPiece for playing the Brickus game.  
 * Represents pieces as 2d integer arrays, [y][x] where 
 * y is the row number, and x is the column number
 */
public class StandardBrickusPiece implements BrickusPiece {

	public int[][] grid;
	private int height;
	private int width;
	
	/**
	 * Constructor to create a Brickus piece
	 * @param piece a 2d integer array specifying the brick locations as 1's, else 0
	 */
	public StandardBrickusPiece(int[][] piece) {
		height = piece.length;
		width = piece[0].length;
		grid = new int[height][width];
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
			{
				grid[y][x] = piece[y][x];
			}
	}
	
	/**
	 * Flips this Brickus piece horizontally.
	 */
	public void flipHorizontally() {
		int[][] tempGrid = new int[height][width];
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
			{
				tempGrid[y][width - 1 - x] = grid[y][x];
			}
		grid = tempGrid;
		BrickusEvent update = new BrickusEvent(StandardBrickusModel.myModel, false, false); //game ended is false
		for(BrickusListener listener: StandardBrickusModel.myModel.listeners){
			listener.modelChanged(update);
		}	
	}
	
	/**
	 * Flips this Brickus piece vertically.
	 */
	public void flipVertically() {
		int[][] tempGrid = new int[height][width];
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
			{
				tempGrid[height - 1 - y][x] = grid[y][x];
			}
		grid = tempGrid;
		BrickusEvent update = new BrickusEvent(StandardBrickusModel.myModel, false, false); //game ended is false
		for(BrickusListener listener: StandardBrickusModel.myModel.listeners){
			listener.modelChanged(update);
		}	
	}
	
	/**
	 * Determines the height of this Brickus piece's grid
	 * @return height the integer height of the piece
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Determines the width of this Brickus piece's grid
	 * @return width the integer width of the piece
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Determines whether or not this Brickus piece has a brick in the specified location of its grid
	 * @param x the integer x position (column)
	 * @param y the integer y position (row)
	 * @return true if the space is occupied, false otherwise
	 */
	public boolean isOccupied(int x, int y) {
		return grid[y][x] == 1;
	}
	
	/**
	 * Rotates this Brickus piece 90 degrees clockwise
	 */
	public void rotateClockwise() {
		int temp;
		int[][] tempGrid = new int[width][height];
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
			{ 
				tempGrid[x][height - 1 - y] = grid[y][x]; //old height
			}
		temp = height; //swap height and width using temp
		height = width;
		width = temp; 
		grid = tempGrid;
		BrickusEvent update = new BrickusEvent(StandardBrickusModel.myModel, false, false); //game ended is false
		for(BrickusListener listener: StandardBrickusModel.myModel.listeners){
			listener.modelChanged(update);
		}	
	}
	
	/**
	 * Rotates this Brickus piece 90 degrees counter-clockwise
	 */
	public void rotateCounterClockwise() {
		int temp;
		int[][] tempGrid = new int[width][height];
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
			{
				tempGrid[width - 1 - x][y] = grid[y][x]; //old width
			}
		temp = height; //swap height and width using temp
		height = width;
		width = temp; 
		grid = tempGrid;
		BrickusEvent update = new BrickusEvent(StandardBrickusModel.myModel, false, false); //game ended is false
		for(BrickusListener listener: StandardBrickusModel.myModel.listeners){
			listener.modelChanged(update);
		}	
	}
}

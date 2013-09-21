package edu.jhu.cs.tyung1.oose;

import java.util.ArrayList;
import java.util.List;
import edu.jhu.cs.oose.fall2013.brickus.iface.*;

public class MyBrickusModel implements BrickusModel {

	// To allow MyBrickusPiece to access the listeners, the List of BrickusListener objects stored in
	// MyBrickusModel.
	static MyBrickusModel model = new MyBrickusModel();
	
	int[][] board = new int[14][14];
	int pass = 0;
	Player activePlayer = Player.PLAYER1;
	boolean gameOver = false;
	List<BrickusListener> listeners = new ArrayList<BrickusListener>();
	List<BrickusPiece> player1Pieces = this.makePieces();
	List<BrickusPiece> player2Pieces = this.makePieces();
	
	/**
	 * Adds an action listener to the MyBrickusModel.
	 * @param listener The listener added to the MyBrickusModel, which may listen for illegal moves or a change in the state of the model.
	 */
	public void addBrickusListener(BrickusListener listener) {
		
		MyBrickusModel.model.listeners.add(listener);
	}
	
	/**
	 * Calculates the score for the given Player. The number of bricks a Player has on the board is
	 * the score of this Player.
	 * @param player The Player whose score will be calculated.
	 * @return The score of the Player.
	 */
	public int calculateScore(Player player) {
		
		int score = 0;
		for(int i=0; i<MyBrickusModel.model.getWidth(); i++) {
			for(int j=0; j<MyBrickusModel.model.getHeight(); j++) {
				
				if (MyBrickusModel.model.getContents(i, j) == null) {
					
				}
				else if(MyBrickusModel.model.getContents(i, j) == player) { // or .equals()?
					score++;
				}
			}
		}
		return score;
	}
	
	/**
	 * Returns the active Player, who has the move on the current turn.
	 * @return the active Player.
	 */
	public Player getActivePlayer() {
		
		if(MyBrickusModel.model.gameOver) {
			
			return null;
		}
		else {
			
			return MyBrickusModel.model.activePlayer;
		}
	}
	
	/**
	 * Returns the contents of a cell at a given position.
	 * @param x The x-coordinate of the cell.
	 * @param y The y-coordinate of the cell.
	 * @return 1 if Player 1 has a brick at the location, 2 if Player 2 does, null if neither does.
	 */
	public Player getContents(int x, int y) {
		
		if(MyBrickusModel.model.board[x][y] == 1) {
			return Player.PLAYER1;
		}
		else if(MyBrickusModel.model.board[x][y] == 2) {
			return Player.PLAYER2;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Returns the height of the Brickus board.
	 * @return The height of the Brickus board.
	 */
	public int getHeight() {
		
		return MyBrickusModel.model.board[0].length;
	}
	
	/**
	 * Returns the remaining pieces that player has left.
	 * @return the BrickusPiece objects representing the remaining pieces that player has.
	 */
	public java.util.List<BrickusPiece> getPieces(Player player) {
		
		if(player == Player.PLAYER1) {
			return MyBrickusModel.model.player1Pieces;
		}
		else {
			return MyBrickusModel.model.player2Pieces;
		}
	}
	
	/**
	 * Returns the width of the Brickus board.
	 * @return the width of the Brickus board.
	 */
	public int getWidth() {
		
		return model.board.length;
	}
	
	/**
	 * Indicates that a Player has passed his turn.
	 * The MyBrickusModel will then designated the other Player as the one with the next turn (active).
	 * @param player The Player who chose to pass the turn.
	 */
	public void pass(Player player) {
		
		if(MyBrickusModel.model.pass == 0) {
			MyBrickusModel.model.pass++;
			if(player == Player.PLAYER1) {
				MyBrickusModel.model.activePlayer = Player.PLAYER2;
			}
			else if(player == Player.PLAYER2) {
				MyBrickusModel.model.activePlayer = Player.PLAYER1;
			}
			for(BrickusListener l: MyBrickusModel.model.listeners) {
				
				l.modelChanged(new BrickusEvent(MyBrickusModel.model, true, false));
			}
			
		}

		else if(MyBrickusModel.model.pass == 1) {
			MyBrickusModel.model.pass++;
			MyBrickusModel.model.gameOver = true;
			for(BrickusListener l: MyBrickusModel.model.listeners) {
				
				l.modelChanged(new BrickusEvent(MyBrickusModel.model, true, true));
			}
		}
	}
	
	/**
	 * @param player The Player making the move.
	 * @param x The x-coordinate of the mouse, which is also the x-coordinate of where the top left
	 * corner of the piece falls on the board.
	 * @param y The y-coordinate of the mouse, which is also the y-coordinate of where the top left
	 * corner of the piece falls on the board.
	 * @param piece The BrickusPiece the Player is attempting to place on the board.
	 */
	public void placePiece(Player player, int x, int y, BrickusPiece piece) {
		
		boolean placed = false;
		List<BrickusPiece> hold;
			
		// Determine which Player, in order to determine which List of BrickusPiece objects to use.
		if(player == Player.PLAYER1) {
			
			hold = MyBrickusModel.model.player1Pieces;
		}
		else {
			
			hold = MyBrickusModel.model.player2Pieces;
		}
		
		// Check if the piece is entirely on the board.
		for(int i=0; i<piece.getWidth(); i++) {
			
			for(int j=0; j<piece.getHeight(); j++) {
				
				if(x+i > 13 || y+j > 13) {
					
					String message = "A placed piece must be entirely on the game board.";
					for(BrickusListener l: MyBrickusModel.model.listeners) {
						l.illegalMove(new BrickusIllegalMoveEvent(message));
					}
					return;
				}
			}
		}
		
		// If player is placing the first piece, check a brick is placed in a corner of the board.
		if(hold.size() == 21) {
			
			// Check if piece has a brick in any of the four corners.
			if((x!=0 || y!=0 || !piece.isOccupied(0,0)) && (x!=0 || y!= 13-piece.getHeight()+1 || !piece.isOccupied(0, piece.getHeight()-1)) && (x!=13-piece.getWidth()+1 || y!=0 || !piece.isOccupied(piece.getWidth()-1, 0)) && (x!=13-piece.getWidth()+1 || y!=13-piece.getHeight()+1 || !piece.isOccupied(piece.getWidth()-1, piece.getHeight()-1))) {
				String message = "A player's first piece must be placed in a corner.";
					for(BrickusListener l: MyBrickusModel.model.listeners) {
						l.illegalMove(new BrickusIllegalMoveEvent(message));
					}
					return;
			}
			placed = true;
		}
		
		// Check if any of the new bricks would overlap already-present ones.
		for(int i=0; i<piece.getWidth(); i++) {
			
			for(int j=0; j<piece.getHeight(); j++) {
				
				if(piece.isOccupied(i, j)) {
					if(MyBrickusModel.model.board[x+i][y+j] == 1 || MyBrickusModel.model.board[x+i][y+j] == 2) {
						String message = "Pieces may not be placed on top of other pieces.";
						for(BrickusListener l: MyBrickusModel.model.listeners){
							l.illegalMove(new BrickusIllegalMoveEvent(message));
						}
						return;
					}
				}
			}
		}
		
		// Check if any of the new bricks is adjacent to an already-present one.
		int n;
		if(player == Player.PLAYER1) {
			n = 1; // Player 1 is red, which is represented by 1.
		}
		else {
			n = 2; // Player 2 is blue, which is represented by 2.
		}
		for(int i=0; i<piece.getWidth(); i++) {
			for(int j=0; j<piece.getHeight(); j++) {
				if(piece.isOccupied(i, j)) {
					if(x+i-1 >= 0) { // Check that index isn't outside of the 14x14 board.
						if(MyBrickusModel.model.board[x+i-1][y+j] == n) { // Check left of brick (i, j).
							String message = "Pieces may not be placed with sides touching pieces of the same color.";
							for(BrickusListener l: MyBrickusModel.model.listeners){
								l.illegalMove(new BrickusIllegalMoveEvent(message));
							}
							return;
						}
					}
					if(x+i+1 <= 13) {
						if(MyBrickusModel.model.board[x+i+1][y+j] == n) { // Check right of brick (i, j).
							String message = "Pieces may not be placed with sides touching pieces of the same color.";
							for(BrickusListener l: MyBrickusModel.model.listeners){
								l.illegalMove(new BrickusIllegalMoveEvent(message));
							}
							return;
						}
					}
					if(y+j-1 >= 0) {
						if(MyBrickusModel.model.board[x+i][y+j-1] == n) { // Check above brick (i, j).
							String message = "Pieces may not be placed with sides touching pieces of the same color.";
							for(BrickusListener l: MyBrickusModel.model.listeners){
								l.illegalMove(new BrickusIllegalMoveEvent(message));
							}
							return;
						}
					}
					if(y+j+1<=13) {
						if(MyBrickusModel.model.board[x+i][y+j+1] == n) { // Check below brick (i, j).
							String message = "Pieces may not be placed with sides touching pieces of the same color.";
							for(BrickusListener l: MyBrickusModel.model.listeners){
								l.illegalMove(new BrickusIllegalMoveEvent(message));
							}
							return;
						}
					}
				}
			}
		}
		
		boolean isDiagonal = false; // true if at least one brick of piece is diagonal to a same-colored brick already on the board.
		// Check if none of the new bricks is diagonal to an already-present one of the same color.
		for(int i=0; i<piece.getWidth(); i++) {
			
			for(int j=0; j<piece.getHeight(); j++) {
				
				if(piece.isOccupied(i, j)) {
					if(x+i-1>=0 && y+j-1>=0) { // Check for a same-colored brick above and left of brick (i, j).
						if(MyBrickusModel.model.board[x+i-1][y+j-1] == n) {
							isDiagonal = true;
							break;
						}
					}
					if(x+i-1>=0 && y+j+1<=13) { // Check for same-colored brick below and left of brick (i, j).
						if(MyBrickusModel.model.board[x+i-1][y+j+1] == n) {
							isDiagonal = true;
							break;
						}
					}
					if(x+i+1<=13 && y+j-1>=0) { // Check for a same-colored brick above and right of brick (i, j).
						if(MyBrickusModel.model.board[x+i+1][y+j-1] == n) {
							isDiagonal = true;
							break;
						}
					}
					if(x+i+1<=13 && y+j+1<=13) { // Check for a same-colored brick below and right of brick (i, j).
						if(MyBrickusModel.model.board[x+i+1][y+j+1] == n) {
							isDiagonal = true;
							break;
						}
					}
				}
			}
		}
		if(!placed && !isDiagonal) {
			
			String message = "Pieces must touch pieces of the same color diagonally.";
			for(BrickusListener l: MyBrickusModel.model.listeners) {
				l.illegalMove(new BrickusIllegalMoveEvent(message));
			}
			return;
		}
		else {
			placed = true;
		}
		
		// If previous Player passed, but current Player made a legal move, reset number of passes to 0.
		if(MyBrickusModel.model.pass == 1 || placed == true) {
			MyBrickusModel.model.pass = 0;
		}
		
		// In the case of a legal move made by player.
		if(placed == true) {
			
			// Remove the piece from the BrickusPiece list of the current Player.
			// Change Player after the current Player makes a legal move.
			if(MyBrickusModel.model.activePlayer == Player.PLAYER1) {
				MyBrickusModel.model.player1Pieces.remove(piece);
				MyBrickusModel.model.activePlayer = Player.PLAYER2;
			}
			else {
				MyBrickusModel.model.player2Pieces.remove(piece);
				MyBrickusModel.model.activePlayer = Player.PLAYER1;
			}
			
			// Update the model to store where the bricks of the newly-placed piece are.
			for(int i=0; i<piece.getWidth(); i++) {
				
				for(int j=0; j<piece.getHeight(); j++) {
					
					if(piece.isOccupied(i, j) && MyBrickusModel.model.activePlayer == Player.PLAYER1) {
						MyBrickusModel.model.board[x+i][y+j] = 2;
					}
					
					else if(piece.isOccupied(i, j) && MyBrickusModel.model.activePlayer == Player.PLAYER2) {
						MyBrickusModel.model.board[x+i][y+j] = 1;
					}
					
					else if(!piece.isOccupied(i, j)) {
						MyBrickusModel.model.board[x+i][y+j] = 0;
					}
				}
			}
			
			// Send event to BrickusListener objects notifying of Player change.
			for(BrickusListener l: MyBrickusModel.model.listeners) {
				l.modelChanged(new BrickusEvent(MyBrickusModel.model, true, false));
			}
		}
	}
	
	/**
	 * Removes a listener from the MyBrickusModel.
	 * @param listener The BrickusListener to be removed.
	 */
	public void removeBrickusListener(BrickusListener listener) {
		
		MyBrickusModel.model.listeners.remove(listener);
	}
	
	/**
	 * Initializes the MyBrickusPiece objects that represent the pieces each player will receive at
	 * the beginning of a game using MyBrickusPiece constructor calls.
	 * @return A List of MyBrickusPiece objects.
	 */
	public java.util.List<BrickusPiece> makePieces() {
		
		List<BrickusPiece> pieceList = new ArrayList<BrickusPiece>();
		int[][] hold;
		
		hold = new int[][]{{0,1,0}, {1,1,1}, {0,1,0}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1,1,0}, {0,1,1}, {0,1,0}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1,0,0}, {1,1,1}, {1,0,0}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1,0}, {1,0}, {1,0}, {1,1}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1}, {1}, {1}, {1}, {1}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1,0,0}, {1,1,1,}, {0,0,1}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1,0,1}, {1,1,1}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1,1,0}, {1,1,1}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1,1,0}, {0,1,1}, {0,0,1}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1,0}, {1,1}, {0,1}, {0,1}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1,0}, {1,1}, {1,0}, {1,0}};
		pieceList.add(new MyBrickusPiece(hold));		// 11 pieces
		
		hold = new int[][]{{1,0,0}, {1,0,0}, {1,1,1}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1}, {1}, {1}, {1}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1,0}, {1,0}, {1,1}};
		pieceList.add(new MyBrickusPiece(hold));		// 14 pieces
		
		hold = new int[][]{{1,0}, {1,1}, {1,0}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1,0}, {1,1}, {0,1}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1,1}, {1,1}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1}, {1}, {1}};
		pieceList.add(new MyBrickusPiece(hold));		// 18 pieces
		
		hold = new int[][]{{1,0}, {1,1}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1}, {1}};
		pieceList.add(new MyBrickusPiece(hold));
		
		hold = new int[][]{{1}};
		pieceList.add(new MyBrickusPiece(hold));		// 21 pieces
		
		return pieceList;
		
	}
}

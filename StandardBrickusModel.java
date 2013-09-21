/**
 * Kyle Wong
 * 13.9.13
 * OOSE HW1a
 * kwong23@jhu.edu
 */
package kyle.brickus;

import java.util.ArrayList;
import java.util.List;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;
import kyle.brickus.StandardBrickusPiece;

/**
 * Class for managing the Brickus Model which controls the game logic.
 */
public class StandardBrickusModel implements BrickusModel {

	public static StandardBrickusModel myModel = new StandardBrickusModel();
	public int numPlayers = 4;
	private int boardWidth = 20;
	private int boardHeight = 20;
	int activePlayer = 1; //PLAYER1 starts
	private int[][] board = new int[boardHeight][boardWidth];
	private java.util.List<BrickusPiece> player1pieces = new java.util.ArrayList<BrickusPiece>();
	private java.util.List<BrickusPiece> player2pieces= new java.util.ArrayList<BrickusPiece>();
	private java.util.List<BrickusPiece> player3pieces= new java.util.ArrayList<BrickusPiece>();
	private java.util.List<BrickusPiece> player4pieces= new java.util.ArrayList<BrickusPiece>();
	private List<List<BrickusPiece>> playerpieces = new ArrayList<List<BrickusPiece>>(numPlayers);
	public java.util.List<BrickusListener> listeners = new java.util.ArrayList<BrickusListener>();
	
	private int numPasses = 0;
	
	private java.util.List<Player> players = new java.util.ArrayList<Player>();
	Player PLAYER1 = Player.PLAYER1;
	Player PLAYER2 = Player.PLAYER2;
	Player PLAYER3;
	Player PLAYER4;
	/*
	public enum Player {
		PLAYER1, PLAYER2, PLAYER3, PLAYER4;
	}*/

	/**
	 * Constructor to initialize 21 Brickus pieces for each of the 2 players
	 */
	public StandardBrickusModel() {
		
		initializeBoard();
		players.add(PLAYER1);
		players.add(PLAYER2);
		players.add(PLAYER3);
		players.add(PLAYER4);
		
		playerpieces.add(player1pieces);
		playerpieces.add(player2pieces);
		playerpieces.add(player3pieces);
		playerpieces.add(player4pieces);
		for(List<BrickusPiece> set : playerpieces)
		{
			constructPieces(set);
		}
		
	} // end StandardBrickusModel constructor

	/**
	 * Initializes the board to be empty.
	 */
	private void initializeBoard(){
		for(int y = 0; y < boardHeight; y++)
		{
			for(int x = 0; x < boardWidth; x++)
			{
				board[y][x] = 0;
			}
		}
	}
	
	/**
	 * Constructs 21 pieces for the given set.
	 * @param set the player's set which will store the pieces
	 */
	private void constructPieces(List<BrickusPiece> set){
		//row 1 pieces
		int[][] piece;
		piece = new int[3][3];
		piece[0] = new int[] {0,1,0};
		piece[1] = new int[] {1,1,1};
		piece[2] = new int[] {0,1,0};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[3][3];
		piece[0] = new int[] {1,0,0};
		piece[1] = new int[] {1,1,1};
		piece[2] = new int[] {0,1,0};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[3][3];
		piece[0] = new int[] {1,1,1};
		piece[1] = new int[] {0,1,0};
		piece[2] = new int[] {0,1,0};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[2][4];
		piece[0] = new int[] {1,1,1,1};
		piece[1] = new int[] {0,0,0,1};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[1][5];
		piece[0] = new int[] {1,1,1,1,1};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[3][3];
		piece[0] = new int[] {1,1,0};
		piece[1] = new int[] {0,1,0};
		piece[2] = new int[] {0,1,1};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[3][2];
		piece[0] = new int[] {1,1};
		piece[1] = new int[] {0,1};
		piece[2] = new int[] {1,1};
		set.add(new StandardBrickusPiece(piece));
		
		//row2 pieces
		piece = new int[3][2];
		piece[0] = new int[] {1,1};
		piece[1] = new int[] {1,1};
		piece[2] = new int[] {0,1};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[3][3];
		piece[0] = new int[] {1,0,0};
		piece[1] = new int[] {1,1,0};
		piece[2] = new int[] {0,1,1};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[2][4];
		piece[0] = new int[] {1,1,0,0};
		piece[1] = new int[] {0,1,1,1};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[2][4];
		piece[0] = new int[] {1,1,1,1};
		piece[1] = new int[] {0,1,0,0};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[3][3];
		piece[0] = new int[] {1,1,1};
		piece[1] = new int[] {0,0,1};
		piece[2] = new int[] {0,0,1};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[1][4];
		piece[0] = new int[] {1,1,1,1};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[2][3];
		piece[0] = new int[] {1,1,1};
		piece[1] = new int[] {0,0,1};
		set.add(new StandardBrickusPiece(piece));
		
		//row 3 pieces
		piece = new int[2][3];
		piece[0] = new int[] {1,1,1};
		piece[1] = new int[] {0,1,0};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[2][3];
		piece[0] = new int[] {1,1,0};
		piece[1] = new int[] {0,1,1};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[2][2];
		piece[0] = new int[] {1,1};
		piece[1] = new int[] {1,1};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[1][3];
		piece[0] = new int[] {1,1,1};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[2][2];
		piece[0] = new int[] {1,1};
		piece[1] = new int[] {0,1};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[1][2];
		piece[0] = new int[] {1,1};
		set.add(new StandardBrickusPiece(piece));
		
		piece = new int[1][1];
		piece[0] = new int[] {1};
		set.add(new StandardBrickusPiece(piece));
	}
	
	/** 
	 * Adds a listener to this BrickusModel
	 * @param listener a BrickusListener that listens for events
	 */
	public void addBrickusListener(BrickusListener listener){
		StandardBrickusModel.myModel.listeners.add(listener);
	}
	
	/** 
	 * Calculates the score for the provided player
	 * @param player the Player whose score will be returned
	 * @return score the integer score of player
	 */
	public int calculateScore(Player player){
		int score = 0;
		int playerNum = 0;
		System.out.println(player.toString());
		///System.out.println(Player.valueOf(player.toString()));
		///System.out.println(Player.values());
		
		if(player == PLAYER1)
			playerNum = 1;
		else if(player == PLAYER2)
			playerNum = 2;
		else if(player == PLAYER3)
			playerNum = 3;
		else if(player == PLAYER4)
			playerNum = 4;
		else
		{
			playerNum = 0;
			System.out.println("Error - Could not find player to calculate score.");
		}
		
		for(int y = 0; y < boardHeight; y++)
		{
			for(int x = 0; x < boardWidth; x++)
			{
				if(board[y][x] == playerNum)
					score++;
			}
		}
		return score;
	}
	
	/** 
	 * Indicate the player whose turn it is
	 * @return player the Player whose turn it is
	 */
	public Player getActivePlayer(){
		Player player;
		if(activePlayer == 1)
			player = PLAYER1;
		else if (activePlayer == 2)
			player = PLAYER2;
		else
		{
			player = null;
			System.out.println("Error - Could not find active player.");
		}
		return  player;
	}
	
	/** 
	 * Determines the contents of the cell with the given location
	 * @param x the integer x position (column)
	 * @param y the integer y position (row)
	 * @return player the Player who has a brick on the given location, null otherwise
	 */
	public Player getContents(int x, int y) {
		Player player;
		if(board[y][x] == 1)
			player = PLAYER1;
		else if(board[y][x] == 2)
			player = PLAYER2;
		else //unoccupied
			player = null;
		return player;
	}
	
	/** 
	 * Establishes the height of the Brickus board
	 * @return boardHeight the integer height of the Brickus board
	 */
	public int getHeight() {
		return boardHeight;
	}
	
	/** 
	 * Determines which pieces the specified player has remaining.
	 * @param player the Player whose remaining pieces will be listed
	 * @return theList a java.util.List<BrickusPiece> list of remaining pieces
	 */
	public java.util.List<BrickusPiece> getPieces(Player player){
		List<BrickusPiece> theList;
		if(player == PLAYER1)
			theList = player1pieces;
		else if(player == PLAYER2)
			theList = player2pieces;
		else
		{
			theList = null;
			System.out.println("Error - Could not find the player for getting pieces.");
		}
		return theList;
	}
	
	/** 
	 * Establishes the width of the Brickus board.
	 * @return boardWidth the integer width of the Brickus board.
	 */
	public int getWidth() {
		return boardWidth;
	}
	
	/** 
	 * Indicates that the player has chosen to pass.
	 * @param player the Player who has passed his or her turn
	 */
	public void pass(Player player) {
		if(player == PLAYER1)
			activePlayer = 2;
		else if(player == PLAYER2)
			activePlayer = 1;
		else
		{
			activePlayer = 0;
			System.out.println("Error - Could not find player who passed.");
		}
		numPasses++;
		if(numPasses == 2)
		{
			BrickusEvent update = new BrickusEvent(StandardBrickusModel.myModel, true, true); //game ended is false
			for(BrickusListener listener: StandardBrickusModel.myModel.listeners){
				listener.modelChanged(update);
			}
		}
		else
		{
			BrickusEvent update = new BrickusEvent(StandardBrickusModel.myModel, true, false); //game ended is false
			for(BrickusListener listener: StandardBrickusModel.myModel.listeners){
				listener.modelChanged(update);
			}
		}
	}
	/** 
	 * Indicates that the active player is attempting to place the provided piece.
	 * @param player the Player who is placing the piece
	 * @param x the integer x position (column) of the piece
	 * @param y the integer y position (row) of the piece
	 * @param piece the BrickusPiece that is being placed
	 */
	public void placePiece(Player player, int x, int y, BrickusPiece piece) {

		boolean validDiagonal = false;
		boolean validStart = false;
		boolean started = false;
		boolean playerChanged = false;
		
		String testMessage;
		//find player number
		int playerNum;
		if(player == PLAYER1)
			playerNum = 1;
		else if(player == PLAYER2)
			playerNum = 2;
		else
		{
			playerNum = 0;
			System.out.println("Error - Could not find player for placing piece");
		}
		//verify the spot is clear
		testMessage = isClear(playerNum, x, y, piece);
		if(testMessage.equals("true"))
		{
			//verify starting piece has been played
			if(board[0][0] == playerNum || board[0][boardWidth-1] == playerNum || board[boardHeight-1][0] == playerNum || board[boardHeight-1][boardWidth-1] == playerNum)
			{
				started = true;
				//must be diagonal to a previous piece
				for(int px = 0; px < piece.getWidth(); px++)
					for(int py = 0; py < piece.getHeight(); py++)
					{
						if(piece.isOccupied(px, py))
						{
							if(px+x+1 < boardWidth)
							{
								if(py+y+1 < boardHeight)
									if(board[py+y+1][px+x+1] == playerNum)
										validDiagonal = true;
								if(py+y-1 >= 0)
									if(board[py+y-1][px+x+1] == playerNum)
										validDiagonal = true;
							}
						
							if(px+x-1 >= 0)
							{
								if(py+y+1 < boardHeight)
									if(board[py+y+1][px+x-1] == playerNum)
										validDiagonal = true;
								if(py+y-1 >= 0)
									if(board[py+y-1][px+x-1] == playerNum)
										validDiagonal = true;
							}
						}
					}

			
			}
			else // must place starting piece
			{
				for(int px = 0; px < piece.getWidth(); px++)
					for(int py = 0; py < piece.getHeight(); py++)
					{
						if(piece.isOccupied(px, py))
						{
							//top side
							if(py+y == 0)
							{	
								if(px+x == 0)//left side
									validStart = true;
								else if(px+x == boardWidth - 1)//right side
									validStart = true;
							}
							//bottom side
							if(py+y == boardHeight - 1)
							{
								if(px+x == 0)//left side
									validStart = true;
								else if(px+x == boardWidth - 1)//right side
									validStart = true;
							}
						}
					}
			}
		}

		if(validStart || validDiagonal)
		{
			//place the piece
			for(int px = 0; px < piece.getWidth(); px++)
				for(int py = 0; py < piece.getHeight(); py++)
				{
					if(piece.isOccupied(px, py))
						board[py+y][px+x] = playerNum;
				}	
			//track that the piece was added
			if(playerNum == 1)
			{
				player1pieces.remove(piece);
				activePlayer = 2;
			}
			else if(playerNum == 2)
			{
				player2pieces.remove(piece);
				activePlayer = 1;
			}
			playerChanged = true;
			
			numPasses = 0; //player did not pass consecutively
			BrickusEvent update = new BrickusEvent(myModel, playerChanged, false); //game ended is false
			for(BrickusListener listener: myModel.listeners){
				listener.modelChanged(update);
			}	
		}
		else //not valid
		{
			if(!started && testMessage.equals("true")) //then was not a valid start
			{
				testMessage = "A Player's first piece must be placed in a corner.";
			}
			else if(!validDiagonal && testMessage.equals("true")) //then it was not diagonal
			{
				testMessage = "Pieces must touch pieces of the same color diagonally";
			}

			BrickusIllegalMoveEvent illegalEvent = new BrickusIllegalMoveEvent(testMessage);
			for(BrickusListener listener: myModel.listeners){
				listener.illegalMove(illegalEvent);
			}	
		}
	}
	
	/** 
	 * Removes a listener from this BrickusModel
	 * @param listener the BrickusListener to remove
	 */
	public void removeBrickusListener(BrickusListener listener) {
		myModel.listeners.remove(listener);
	}
		
	/** 
	* A helper function for placePiece which checks if the board area is clear for the piece to be placed.
	* A placed piece must be entirely on the game board.
	* Pieces may not be placed on top of other pieces.
	* Pieces may not be placed with sides touching pieces of the same color.
	* @param playerNum the integer Player number of the Player who is adding the piece
	* @param x the integer x position (column) of the piece
	* @param y the integer y position (row) of the piece
	* @param piece the BrickusPiece that is being placed
	* @return message the String "true" if clear, or the error message otherwise
	*/
	public String isClear(int playerNum, int x, int y, BrickusPiece piece) {
		String message = "";
		//must fit entirely on board
		for(int ppx = 0; ppx < piece.getWidth(); ppx++)
			for(int ppy = 0; ppy < piece.getHeight(); ppy++)
			{
				if(piece.isOccupied(ppx, ppy))
				{
					if(!((ppx + x) >= 0 && (ppx + x) < boardWidth) || !( (ppy + y) >=0 && (ppy + y) < boardHeight))
						return message = "A placed piece must be entirely on the game board.";
				}
			}
		outerloop:
		for(int px = 0; px < piece.getWidth(); px++)
			for(int py = 0; py < piece.getHeight(); py++)
			{
				if(piece.isOccupied(px, py))
				{
					if( board[py+y][px+x] != 0)//must not be on top of other pieces
					{
						message = "Pieces may not be placed on top of other pieces.";
						break outerloop;
					}
					if(px+x+1 < boardWidth)//not adjacent to same color horizontally
						if( board[py+y][px+x+1] == playerNum)
						{
							message = "Pieces may not be placed with sides touching pieces of the same color.";
						}
					if (px+x-1 >= 0)
						if ( board[py+y][px+x-1] == playerNum )
						{
							message = "Pieces may not be placed with sides touching pieces of the same color.";
						}

					if(py+y+1 < boardHeight)//not adjacent to same color vertically
						if( board[py+y+1][px+x] == playerNum)
						{
							message = "Pieces may not be placed with sides touching pieces of the same color.";
						}
					if(py+y-1 >= 0)
						if( board[py+y-1][px+x] == playerNum)
						{
							message = "Pieces may not be placed with sides touching pieces of the same color.";
						}
				}
			}
		if(message.equals(""))
			message = "true";
		return message;
	}
		
}



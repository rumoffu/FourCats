/**
 * Kyle Wong
 * 13.9.21
 * kwong23@jhu.edu
 */
package kyle.brickus;

import java.util.ArrayList;
import java.util.List;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
//import edu.jhu.cs.oose.fall2013.brickus.iface.Player;
import kyle.brickus.StandardBrickusPiece;
//import kyle.brickus.BrickusModel;
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
	Player PLAYER3 = Player.PLAYER3;
	Player PLAYER4 = Player.PLAYER4;
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
		int playerNum = getPlayerNum(player);
		
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
		else if (activePlayer == 3)
			player = PLAYER3;
		else if (activePlayer == 4)
			player = PLAYER4;
		else
		{
			player = null;
			System.out.println("Error - Could not find active player.");
		}
		return player;
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
		else if(board[y][x] == 3)
			player = PLAYER3;
		else if(board[y][x] == 4)
			player = PLAYER4;
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
		else if(player == PLAYER3)
			theList = player3pieces;
		else if(player == PLAYER4)
			theList = player4pieces;
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
		BrickusEvent update;
		
		activePlayer = (activePlayer + 1) % (numPlayers + 1);
		if(activePlayer == 0){ //reset to first player
			activePlayer = 1;
		}
		numPasses++;
		if(numPasses == numPlayers){
			update = new BrickusEvent(StandardBrickusModel.myModel, true, true); //game ended is true
		}
		else{
			update = new BrickusEvent(StandardBrickusModel.myModel, true, false); //game ended is false
		}
		for(BrickusListener listener: StandardBrickusModel.myModel.listeners){
			listener.modelChanged(update);
		}
	}
	
	/**
	 * returns the Player's number
	 * @param player the player whose number is to be returned
	 * @return the Player's number
	 */
	public int getPlayerNum(Player player){
		if(player == PLAYER1)
			return 1;
		else if(player == PLAYER2)
			return 2;
		else if(player == PLAYER3)
			return 3;
		else if(player == PLAYER4)
			return 4;
		else
			return 0;
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
		boolean playerChanged = false;
		boolean topFilled = false;
		boolean botFilled = false;
		boolean topDiagonal = false;
		boolean botDiagonal = false;
		String testMessage;
		
		//find player number
		int playerNum = getPlayerNum(player);
		
		//verify the spot is clear
		testMessage = isClear(playerNum, x, y, piece);
		if(testMessage.equals("true"))
		{
			//verify starting piece has been played
			topFilled = board[0][0] == playerNum || board[0][boardWidth-1] == playerNum;
			botFilled = board[boardHeight-1][0] == playerNum || board[boardHeight-1][boardWidth-1] == playerNum;
			if(topFilled || botFilled)
			{//started board already
				//must be diagonal to a previous piece
				for(int px = 0; px < piece.getWidth(); px++)
					for(int py = 0; py < piece.getHeight(); py++)
					{	//only count if occupies a square
						if(piece.isOccupied(px, py)) 
						{	//fits within board
							if(0 <= px+x-1 && px+x+1 < boardWidth && 0 <= py+y-1 && py+y+1 < boardHeight )
							{
								topDiagonal = board[py+y+1][px+x-1] == playerNum || board[py+y+1][px+x+1] == playerNum;
								botDiagonal = board[py+y-1][px+x+1] == playerNum || board[py+y-1][px+x-1] == playerNum;
								if(topDiagonal || botDiagonal )
									validDiagonal = true;
							}
						}
					}
				testMessage = "Pieces must touch pieces of the same color diagonally";
			}
			else // must place starting piece
			{
				testMessage = "A Player's first piece must be placed in a corner.";
				for(int px = 0; px < piece.getWidth(); px++)
					for(int py = 0; py < piece.getHeight(); py++)
					{
						if(piece.isOccupied(px, py))
						{
							//top side // and left or right edge
							if(py+y == 0 && (px+x == 0 || px+x == boardWidth - 1))
							{	
								validStart = true;
							}//bottom side // and left or right edge
							else if(py+y == boardHeight - 1 && (px+x == 0 || px+x == boardWidth - 1))
							{
								validStart = true;
							}
						}
					}
			}
		} //end isClear()

		if(validStart || validDiagonal)
		{
			//place the piece
			for(int px = 0; px < piece.getWidth(); px++)
				for(int py = 0; py < piece.getHeight(); py++)
				{
					if(piece.isOccupied(px, py))
						board[py+y][px+x] = playerNum;
				}	
			//track that the piece was added and is no longer available
			getPieces(player).remove(piece);
			//switch player
			playerChanged = true; 
			activePlayer = (activePlayer + 1) % (numPlayers + 1);
			if(activePlayer == 0){ //reset to first player
				activePlayer = 1;
			}
			
			numPasses = 0; //player did not pass consecutively
			BrickusEvent update = new BrickusEvent(myModel, playerChanged, false); //game ended is false
			for(BrickusListener listener: myModel.listeners){
				listener.modelChanged(update);
			}	
		}
		else //not valid so send the error message
		{
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



package edu.jhu.cs.oose.fall2013.brickus.iface;

/**
 * This enumeration represents the players in a game of Brickus.
 * @author KT Wong
 *
 */
public enum Player {
	/**
	 * Used to represent the first player.
	 */
	PLAYER1 ("Player 1"), 
	/**
	 * Used to represent the second player.
	 */
	PLAYER2 ("Player 2"), 
	/**
	 * Used to represent the third player.
	 */
	PLAYER3 ("Player 3"), 
	/**
	 * Used to represent the fourth player.
	 */
	PLAYER4 ("Player 4");
	
	String name;
	
	/**
	 * Constructs the Player enum.
	 * @param name the Player name
	 */
	Player(String name){
		this.name = name;
	}
	
	/**
	 * Returns the player's description.
	 */
	public java.lang.String toString(){
		return name;
	}
	
}

package kwong23.brickusui;
/**
 * Kyle Wong
 * 13.9.25
 * kwong23@jhu.edu
 */
import kwong23.brickusui.FullComponent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

/**
 * Custom listener class to respond to Brickus Model events.
 * @author KT Wong
 *
 */
class MyBrickusListener implements BrickusListener {

	public FullComponent full;
	
	/**
	 * Constructor to create the listener and store its caller which is a FullComponent.
	 * @param fullComponent The FullComponent that is adding the listener.
	 */
	public MyBrickusListener(FullComponent fullComponent) {
		
		this.full = fullComponent;
	}
	
	/**
	 * Function to detect illegal moves and respond to them by displaying the image in the User interface.
	 */
	public void illegalMove(BrickusIllegalMoveEvent event) {
	
		full.updateErrorMessage(event.getMessage());
	}

	/**
	 * Function to update the User interface when the model changes.
	 */
	public void modelChanged(BrickusEvent event) {
		
		if(!event.isGameOver()) {
			if(event.isPlayerChanged()){
				full.updateScores(full.model.calculateScore(Player.PLAYER1), full.model.calculateScore(Player.PLAYER2));
				full.updateSinglePiece(full.model);
			}
		}
		else {
			full.tray.gameover();
			if(full.model.calculateScore(Player.PLAYER1) > full.model.calculateScore(Player.PLAYER2)) {
				full.updateErrorMessage("Game over. Player 1 won with " + full.model.calculateScore(Player.PLAYER1) + " points.");
			}
			else if(full.model.calculateScore(Player.PLAYER1) < full.model.calculateScore(Player.PLAYER2)) {
				full.updateErrorMessage("Game over. Player 2 won with " + full.model.calculateScore(Player.PLAYER2) + " points.");
			}
			else {
				full.updateErrorMessage("Game over. Players tied.");
			}
		}
	}
}

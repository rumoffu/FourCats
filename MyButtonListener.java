package kwong23.brickusui;
/**
 * Kyle Wong
 * 13.9.25
 * kwong23@jhu.edu
 */
import java.awt.event.ActionEvent;

import kwong23.brickusui.FullComponent.MyBrickusTray;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;

/**
 * Custom button listener for responding to Brickus button presses.
 * @author KT Wong
 *
 */
class MyButtonListener implements java.awt.event.ActionListener {
	public MyBrickusTray mytray;
	public BrickusModel model;
	
	/**
	 * Constructor to create the button listener.
	 * @param model The BrickusModel to send updates to.
	 * @param theTray The MyBrickusTray which holds the listening button.
	 */
	public MyButtonListener(BrickusModel model, MyBrickusTray theTray) {
		this.model = model;
		this.mytray = theTray;
	}
	
	/**
	 * Function to respond to the action of the button being pressed.
	 * Passes the turn if the game is not over, otherwise it creates a new Brickus game.
	 */
	public void actionPerformed(ActionEvent event) {
	
		if(mytray.gameover){
			mytray.newGame();
		}
		else
		{
			model.pass(model.getActivePlayer());
		}
	}
}

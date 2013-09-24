package edu.jhu.cs.tyung1.oose;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

class MyBrickusListener implements BrickusListener {

	public Composite composite;
	
	public MyBrickusListener(Composite composite) {
		
		this.composite = composite;
	}
	
	public void illegalMove(BrickusIllegalMoveEvent event) {
	
		composite.updateErrorMessage(event.getMessage());
	}

	public void modelChanged(BrickusEvent event) {
		
		if(!event.isGameOver()) {
			if(event.isPlayerChanged()){
				composite.updateScores(composite.model.calculateScore(Player.PLAYER1), composite.model.calculateScore(Player.PLAYER2));
				composite.updateSinglePiece(composite.model);
			}
		}
		else {
			composite.tray.gameover();
			//composite.tray.passButton.removeActionListener(composite.tray.buttonListener);
			composite.board.removeMouseMotionListener(composite.board.mouseHandler);
			composite.board.removeMouseListener(composite.board.mouseHandler);
			composite.board.removeMouseListener(composite.board.myListener);
			composite.board.removeMouseWheelListener(composite.board.myListener);
			if(composite.model.calculateScore(Player.PLAYER1) > composite.model.calculateScore(Player.PLAYER2)) {
				composite.updateErrorMessage("Game over. Player 1 won with " + composite.model.calculateScore(Player.PLAYER1) + " points.");
			}
			else if(composite.model.calculateScore(Player.PLAYER1) < composite.model.calculateScore(Player.PLAYER2)) {
				composite.updateErrorMessage("Game over. Player 2 won with " + composite.model.calculateScore(Player.PLAYER2) + " points.");
			}
			else {
				composite.updateErrorMessage("Game over. Players tied.");
			}
		}
	}
}

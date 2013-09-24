package edu.jhu.cs.tyung1.oose;

import java.awt.event.ActionEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.tyung1.oose.Composite.MyBrickusTray;

class MyButtonListener implements java.awt.event.ActionListener {
	public MyBrickusTray mytray;
	public BrickusModel model;
	
	public MyButtonListener(BrickusModel model, MyBrickusTray theTray) {
		this.model = model;
		this.mytray = theTray;
	}
	
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

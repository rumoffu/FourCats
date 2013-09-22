package edu.jhu.cs.tyung1.oose;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import edu.jhu.cs.tyung1.oose.SinglePiece;

public class MyMouseListener extends MouseAdapter {
	private Composite myfull;

	   public MyMouseListener(Composite full) {
		      this.myfull = full;
		   }


   @Override
   public void mousePressed(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1) {
    	  myfull.pieceClicked((SinglePiece)e.getSource());
      } 
      else if (e.isShiftDown() && e.getButton() == MouseEvent.BUTTON3) {
         ///mypiece.flipHorizontally();
         //call myfull function
      }
   }
}

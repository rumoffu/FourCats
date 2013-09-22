package edu.jhu.cs.tyung1.oose;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import edu.jhu.cs.tyung1.oose.FullComponent.SinglePiece;
import edu.jhu.cs.tyung1.oose.MyBrickusFrame;
import edu.jhu.cs.tyung1.*;

public class MyMouseListener extends MouseAdapter {
	private FullComponent myfull;

	   public MyMouseListener(FullComponent full) {
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

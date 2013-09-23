package edu.jhu.cs.tyung1.oose;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import edu.jhu.cs.tyung1.oose.Composite.SinglePiece;

public class MyMouseListener extends MouseAdapter implements MouseWheelListener{
	private Composite myfull;

	   public MyMouseListener(Composite full) {
		      this.myfull = full;
		   }


   @Override
   public void mousePressed(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1) {
    	  if(e.getSource() instanceof SinglePiece)
    	  {
    		  myfull.pieceClicked((SinglePiece)e.getSource());
    	  }
      } 
      else if (e.isShiftDown() && e.getButton() == MouseEvent.BUTTON3) {
    	  myfull.shiftRightClick();
      }
      else if(!e.isShiftDown() && e.getButton() == MouseEvent.BUTTON3){
    	  myfull.rightClick();
      }
   }
   @Override
   public void mouseWheelMoved(MouseWheelEvent w){
	   if(w.getWheelRotation() < 0){
		   myfull.scrollUpWheel();   
	   }
	   else{ //down
		   myfull.scrollDownWheel();
	   }
   }
}

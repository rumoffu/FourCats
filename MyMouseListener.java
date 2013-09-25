package kwong23.brickusui;
/**
 * Kyle Wong
 * 13.9.25
 * kwong23@jhu.edu
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import kwong23.brickusui.FullComponent.SinglePiece;

/**
 * Customized Mouse Listener for responding to all mouse events for Brickus.
 * @author KT Wong
 *
 */
public class MyMouseListener extends MouseAdapter implements MouseWheelListener{
	private FullComponent myfull;

	/**
	 * Constructor which stores the caller, FullComponent.	
	 * @param full The FullComponent which is adding the listener
	 */
	public MyMouseListener(FullComponent full) {
		this.myfull = full;
	}

	/**
	 * Function that checks for mouse pressed events for left and right clicks and with shift held down.
	 */
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
	
	/**
	 * Function to detect and respond to mouse wheel motion.
	 */
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

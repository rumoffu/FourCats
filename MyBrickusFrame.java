package kwong23.brickusui;
/**
 * Kyle Wong
 * 13.9.25
 * kwong23@jhu.edu
 */
import java.awt.*;
import javax.swing.*;
import edu.jhu.cs.oose.fall2013.brickus.iface.*;

/**
 * Frame that displays the overall Brickus User Interface.
 * @author KT Wong
 *
 */
@SuppressWarnings("serial")
public class MyBrickusFrame extends JFrame {

	JFrame frame;
	BrickusModel model;
	FullComponent full;
	
	/**
	 * Main function to begin running a new Brickus game.
	 * @param args Currently unused arguments.
	 */
	public static void main(String[] args) {

		BrickusModel model = new edu.jhu.cs.oose.fall2013.brickus.model.StandardBrickusModel();
		MyBrickusFrame gui = new MyBrickusFrame(model);
		gui.go();
	}
	
	/**
	 * MyBrickusFrame constructor to store the model.
	 * @param model The BrickusModel to be stored
	 */
	public MyBrickusFrame(BrickusModel model) {
		
		this.model = model;
	}
	
	/**
	 * A function to create a new game and reset using a recursive function call.
	 */
	public void reset(){
		frame.setVisible(false);
		main(null);
	}
	
	/**
	 * The main function for starting the MyBrickusFrame
	 */
	public void go() {
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		full = new FullComponent(model, this);
		frame.getContentPane().add(BorderLayout.CENTER, full);
		
		frame.setSize(670,750);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}




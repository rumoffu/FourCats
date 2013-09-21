package edu.jhu.cs.tyung1.oose;

import java.awt.*;
import javax.swing.*;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;

@SuppressWarnings("serial")
public class MyBrickusFrame extends JFrame {

	JFrame frame;
	
	public static void main(String[] args) {

		BrickusModel model = new edu.jhu.cs.oose.fall2013.brickus.model.StandardBrickusModel();
		MyBrickusFrame gui = new MyBrickusFrame(model);
		gui.go();
		//gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//gui.setSize(670,710);
        //gui.setLocationRelativeTo(null);
        //gui.setVisible(true);
	}
	
	public void go() {
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// MyBrickusBoard board
		// frame.getContentPane().add(BorderLayout.CENTER,board);
		
		// MyBrickusTray tray // lower panel of pieces and pass button
		// frame.getContentPane().add(BorderLayout.CENTER, tray);
		
		MyBrickusTracker tracker = new MyBrickusTracker(); // error board, score boards
		frame.getContentPane().add(BorderLayout.PAGE_END, tracker.panel);
		
		frame.setSize(670,710);
		//frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public MyBrickusFrame(BrickusModel model) {
		
		
	}
}

@SuppressWarnings("serial")
class MyBrickusTray extends JPanel {
	
	
}

@SuppressWarnings("serial")
class MyBrickusTracker extends JPanel {
	
	JPanel panel = new JPanel(new GridBagLayout());
	
	public MyBrickusTracker() {
		
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		JPanel subpanelLeft = new JPanel(new BorderLayout());
		JPanel subpanelRight = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		
		JLabel errorText = new JLabel("<html><font color=000000>Welcome to </font>");
		errorText.setText(errorText.getText() + "<font color=0000CC>F</font>");
		errorText.setText(errorText.getText() + "<font color=CC0000>o</font>");
		errorText.setText(errorText.getText() + "<font color=006600>u</font>");
		errorText.setText(errorText.getText() + "<font color=FF0066>r</font");
		errorText.setText(errorText.getText() + "<font color=000000>Cats! =^.^=");
		//errorText.setBorder(BorderFactory.createLineBorder(Color.black));
		//JLabel errorText = new JLabel("Welcome to FourCats! =^.^=");
		
		JLabel player1Score = new JLabel("<html><font color=0000CC>Score: 0 </font>");
		//player1Score.setBorder(BorderFactory.createLineBorder(Color.blue));
		//player1Score.setForeground(Color.blue);
		
		JLabel player2Score = new JLabel("<html><font color = CC0000>Score: 0 </font>");
		//player2Score.setBorder(BorderFactory.createLineBorder(Color.red));
		
		JLabel player3Score = new JLabel("<html><font color = 006600>Score: 0 </font>");
		//player3Score.setBorder(BorderFactory.createLineBorder(Color.green));
		
		JLabel player4Score = new JLabel("<html><font color = FF0066>Score: 0 </font>");
		//player4Score.setBorder(BorderFactory.createLineBorder(Color.magenta));
		
		subpanelLeft.add(errorText);
		subpanelRight.add(player1Score);
		subpanelRight.add(player2Score);
		subpanelRight.add(player3Score);
		subpanelRight.add(player4Score);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 5;
		constraints.weightx = 1;
		panel.add(subpanelLeft, constraints);
		
		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.weightx = 0;
		panel.add(subpanelRight, constraints);
	}
}

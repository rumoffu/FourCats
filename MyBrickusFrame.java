package edu.jhu.cs.tyung1.oose;

import java.awt.*;
import javax.swing.*;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;

@SuppressWarnings("serial")
public class MyBrickusFrame extends JFrame {

	JFrame frame;
	
	public static void main(String[] args) {

		BrickusModel model = new edu.jhu.cs.oose.fall2013.brickus.model.StandardBrickusModel(); //REVIVE: use own model class.
		MyBrickusFrame gui = new MyBrickusFrame(model);
		gui.go();
	}
	
	public MyBrickusFrame(BrickusModel model) {
		
		
	}
	
	public void go() {
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// MyBrickusBoard board
		// frame.getContentPane().add(BorderLayout.CENTER,board);
		
		MyBrickusTray tray = new MyBrickusTray(); // lower panel of pieces and pass button
		frame.getContentPane().add(BorderLayout.CENTER, tray.panel);
		
		MyBrickusTracker tracker = new MyBrickusTracker(); // error board, score boards
		frame.getContentPane().add(BorderLayout.PAGE_END, tracker.panel);
		
		frame.setSize(670,710);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

@SuppressWarnings("serial")
class MyBrickusTray extends JPanel {
	
	JPanel panel = new JPanel(new GridBagLayout());
	
	public MyBrickusTray() {
		
		panel.setBorder(BorderFactory.createLineBorder(Color.black));		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		
		JPanel holdPieceTray = new JPanel();
		holdPieceTray.setBorder(BorderFactory.createLineBorder(Color.black));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 7;
		constraints.weightx = 1;
		panel.add(holdPieceTray, constraints);
		
		JButton passButton = new JButton("Pass");
		constraints.gridx = 7;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		panel.add(passButton, constraints);
	}
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
		
		JLabel player1Score = new JLabel("<html><font color=0000CC>Score: 0 </font>");
		
		JLabel player2Score = new JLabel("<html><font color = CC0000>Score: 0 </font>");
		
		JLabel player3Score = new JLabel("<html><font color = 006600>Score: 0 </font>");
		
		JLabel player4Score = new JLabel("<html><font color = FF0066>Score: 0 </font>");
		
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

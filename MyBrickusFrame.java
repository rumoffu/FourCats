package edu.jhu.cs.tyung1.oose;

import java.awt.*;
import javax.swing.*;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

@SuppressWarnings("serial")
public class MyBrickusFrame extends JFrame {

	JFrame frame;
	BrickusModel model;
	
	public static void main(String[] args) {

		BrickusModel model = new edu.jhu.cs.oose.fall2013.brickus.model.StandardBrickusModel(); //REVIVE: use own model class.
		MyBrickusFrame gui = new MyBrickusFrame(model);
		gui.go();
	}
	
	public MyBrickusFrame(BrickusModel model) {
		
		this.model = model;
	}
	
	public void go() {
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		
		// MyBrickusBoard board
		// frame.getContentPane().add(BorderLayout.CENTER,board);
		JPanel holdBoard = new JPanel();
		holdBoard.setBorder(BorderFactory.createLineBorder(Color.black));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 30;
		constraints.weightx = 1;
		constraints.weighty = 1;
		frame.add(holdBoard, constraints);
		
		MyBrickusTray tray = new MyBrickusTray(model); // lower panel of pieces and pass button
		constraints.gridx = 0;
		constraints.gridy = 30;
		constraints.gridwidth = 1;
		constraints.gridheight = 9;
		constraints.weightx = 1;
		constraints.weighty = 0;
		frame.add(tray, constraints);
		
		MyBrickusTracker tracker = new MyBrickusTracker(model); // error board, score boards
		constraints.gridx = 0;
		constraints.gridy = 39;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 0;
		frame.add(tracker, constraints);
		
		frame.setSize(670,710);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

@SuppressWarnings("serial")
class singlePiece extends JPanel {
	
	public singlePiece(BrickusModel model, BrickusPiece piece) {
		
		this.setLayout(new GridLayout(5, 5));
		this.setBackground(Color.white);
		
		int heightBuffer = calculateBuffer(piece.getHeight());
		int widthBuffer = calculateBuffer(piece.getWidth());
		System.out.print(piece.getHeight() + " " + heightBuffer + " ");
		System.out.println(piece.getWidth() + " " + widthBuffer + " ");
		
		Player activePlayer = model.getActivePlayer();
		Color playerColor;
		if(activePlayer == Player.PLAYER1) {
			playerColor = Color.blue;
		}
		else {
			playerColor = Color.red;
		} //REVIVE: put in all 4 Players
		
		for(int h=0; h<5; h++) {
			
			if(h<heightBuffer) {
				
				for(int i=0; i<5; i++) {
					
					JPanel brick = new JPanel();
					brick.setBackground(Color.white);
					//brick.setBorder(BorderFactory.createLineBorder(Color.black));
					this.add(brick);
				}
			}
			else {
				
				for(int w=0; w<5; w++) {
					
					if(w<widthBuffer) {
						
						JPanel brick = new JPanel();
						brick.setBackground(Color.white);
						//brick.setBorder(BorderFactory.createLineBorder(Color.black));
						this.add(brick);
					}
					else {
						
						JPanel brick = new JPanel();
						if(piece.isOccupied(w-widthBuffer, h-heightBuffer)) {
						brick.setBackground(playerColor);
						brick.setBorder(BorderFactory.createLineBorder(Color.black));
						}
						else {
						brick.setBackground(Color.white);
						//brick.setBorder(BorderFactory.createLineBorder(Color.black));
						}
						this.add(brick);
					}
				}
			}
		}
	}
	
	public int calculateBuffer(int pieceDimension) {
		
		int buffer = 0;
		
		switch(pieceDimension) {
		case 1:
			buffer = 2;
			break;
		case 2:
			buffer = 1;
			break;
		case 3:
			buffer = 1;
			break;
		case 4:
			buffer = 0;
			break;
		case 5:
			buffer = 0;
			break;
		}
		
		return buffer;
	}
}

@SuppressWarnings("serial")
class pieceTray extends JPanel {
	
	public pieceTray(BrickusModel model) {
		
		this.setLayout(new GridLayout(3, 7));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		for(BrickusPiece piece: model.getPieces(model.getActivePlayer())) {
			
			//JPanel holdPiece = new JPanel();
			//holdPiece.setBorder(BorderFactory.createLineBorder(Color.black));
			//this.add(holdPiece);
			singlePiece newPiece = new singlePiece(model, piece);
			this.add(newPiece);
		}
	}
}

@SuppressWarnings("serial")
class MyBrickusTray extends JPanel {
	
	public MyBrickusTray(BrickusModel model) {
		
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black));		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		
		//JPanel holdPieceTray = new JPanel();
		//holdPieceTray.setBorder(BorderFactory.createLineBorder(Color.black));
		pieceTray pieceTray = new pieceTray(model);
		pieceTray.setBorder(BorderFactory.createLineBorder(Color.black));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 7;
		constraints.weightx = 1;
		//this.add(holdPieceTray, constraints);
		this.add(pieceTray, constraints);
		
		JButton passButton = new JButton("Pass");
		constraints.gridx = 7;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		this.add(passButton, constraints);
	}
}

@SuppressWarnings("serial")
class MyBrickusTracker extends JPanel {
	
	public MyBrickusTracker(BrickusModel model) {
		
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black));
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
		this.add(subpanelLeft, constraints);
		
		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.weightx = 0;
		this.add(subpanelRight, constraints);
	}
}

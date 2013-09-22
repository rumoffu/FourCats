package edu.jhu.cs.tyung1.oose;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import edu.jhu.cs.oose.fall2013.brickus.iface.*;

//REVIVE: remove print statements
@SuppressWarnings("serial")
public class MyBrickusFrame extends JFrame {

	JFrame frame;
	BrickusModel model;
	Composite composite;
	
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
		
		composite = new Composite(model);
		frame.getContentPane().add(BorderLayout.CENTER, composite);
		
		frame.setSize(670,710);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

@SuppressWarnings("serial")
class Composite extends JComponent {
	
	MyBrickusBoard board;
	MyBrickusTray tray;
	MyBrickusTracker tracker;
	BrickusPiece activePiece;
	BrickusModel model;
	
	public Composite(BrickusModel model) {
		
		this.model = model;
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;

		MyMouseListener myListener = new MyMouseListener(this);
		
		MyBrickusListener modelListener = new MyBrickusListener(this);
		model.addBrickusListener(modelListener);
		board = new MyBrickusBoard(model);
		board.setBorder(BorderFactory.createLineBorder(Color.black));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 30;
		constraints.weightx = 1;
		constraints.weighty = 1;
		this.add(board, constraints);
		
		tray = new MyBrickusTray(model, myListener); // lower panel of pieces and pass button
		constraints.gridx = 0;
		constraints.gridy = 30;
		constraints.gridwidth = 1;
		constraints.gridheight = 9;
		constraints.weightx = 1;
		constraints.weighty = 0;
		this.add(tray, constraints);
		
		tracker = new MyBrickusTracker(model); // error board, score boards
		constraints.gridx = 0;
		constraints.gridy = 39;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 0;
		this.add(tracker, constraints);
	}
	
	public void updateErrorMessage(String message) {
		
		tracker.newErrorMessage(message);
	}
	
	public void pieceClicked(SinglePiece panel) {
		
		activePiece = panel.selected();
		//I HAVE THE SELECTED PIECE
	}
	
	public void updateScores(int score1, int score2) {
		
		tracker.newScores(score1, score2);
	}
	
	public void updateSinglePiece(BrickusModel model) {
		
		tray.updateSinglePiece(model);
	}
}

@SuppressWarnings("serial")
class MyBrickusBoard extends JComponent {
	
	int numCol;
	int numRow;
	java.util.List<Rectangle> cells;
	Point coveredCell; // the cell over which the mouse is currently.
	
	public MyBrickusBoard(BrickusModel model) {
		
		numCol = model.getWidth();
		numRow = model.getHeight() + 1;
		cells = new ArrayList<>(numCol * numRow);
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g.create();
		
		int cellWidth = getWidth() / numCol;
        int cellHeight = getHeight() / numRow;
        int widthBuffer = (getWidth() - (numCol * cellWidth)) / 2;
        int heightBuffer = (getHeight() - (numRow * cellHeight)) / 2;
        
        if (cells.isEmpty()) {
            for (int r = 0; r < numRow; r++) {
                for (int c = 0; c < numCol; c++) {
                    Rectangle cell = new Rectangle(
                            widthBuffer + (c * cellWidth),
                            heightBuffer + (r * cellHeight),
                            cellWidth,
                            cellHeight);
                    cells.add(cell);
                }
            }
        }
        
        g2D.setColor(Color.GRAY);
        for (Rectangle cell : cells) {
            g2D.draw(cell);
        }
	}
}

@SuppressWarnings("serial")
class SinglePiece extends JPanel {
	
	BrickusPiece mypiece;
	
	public SinglePiece(BrickusModel model, BrickusPiece piece, MyMouseListener myListener) {
		
		this.setLayout(new GridLayout(5, 5));
		this.setBackground(Color.white);
		this.addMouseListener(myListener);
		int heightBuffer = calculateBuffer(piece.getHeight());
		int widthBuffer = calculateBuffer(piece.getWidth());
		
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
					this.add(brick);
				}
			}
			else {
				
				for(int w=0; w<5; w++) {
					
					if(w<widthBuffer) {
						
						JPanel brick = new JPanel();
						brick.setBackground(Color.white);
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
	public BrickusPiece selected(){
		//highlight myself
		System.out.println("OUCH!");
		this.setBackground(Color.yellow);
		return mypiece;
	}
}

@SuppressWarnings("serial")
class pieceTray extends JPanel {

	public pieceTray(BrickusModel model, MyMouseListener myListener) {
		
		this.setLayout(new GridLayout(3, 7));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		for(BrickusPiece piece: model.getPieces(model.getActivePlayer())) {
			
			SinglePiece newPiece = new SinglePiece(model, piece, myListener);
			this.add(newPiece);
		}
	}
	
	public void updateSinglePiece(BrickusModel model, MyMouseListener myListener) {
		
		for(BrickusPiece piece: model.getPieces(model.getActivePlayer())) {
			
			SinglePiece newPiece = new SinglePiece(model, piece, myListener);
			this.add(newPiece);
		}
	}
}

@SuppressWarnings("serial")
class MyBrickusTray extends JPanel {
	
	JButton passButton;
	MyButtonListener buttonListener;
	pieceTray tray;
	MyMouseListener myListener;
	
	public MyBrickusTray(BrickusModel model, MyMouseListener myListener) {
		
		this.myListener = myListener;
		
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black));		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		
		pieceTray pieceTray = new pieceTray(model, myListener);
		pieceTray.setBorder(BorderFactory.createLineBorder(Color.black));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 7;
		constraints.weightx = 1;
		this.add(pieceTray, constraints);
		this.tray = pieceTray;
		
		passButton = new JButton("Pass");
		MyButtonListener buttonListener = new MyButtonListener(model);
		passButton.addActionListener(buttonListener);
		this.buttonListener = buttonListener;
		constraints.gridx = 7;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		this.add(passButton, constraints);
	}
	
	public void updateSinglePiece(BrickusModel model) {
		
		tray.updateSinglePiece(model, myListener);
	}
}

@SuppressWarnings("serial")
class MyBrickusTracker extends JPanel {
	
	JLabel errorText;
	JLabel player1Score;
	JLabel player2Score;
	JLabel player3Score;
	JLabel player4Score;
	
	public MyBrickusTracker(BrickusModel model) {
		
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		JPanel subpanelLeft = new JPanel(new BorderLayout());
		JPanel subpanelRight = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		
		errorText = new JLabel("<html><font color=000000>Welcome to </font>");
		errorText.setText(errorText.getText() + "<font color=0000CC>F</font>");
		errorText.setText(errorText.getText() + "<font color=CC0000>o</font>");
		errorText.setText(errorText.getText() + "<font color=006600>u</font>");
		errorText.setText(errorText.getText() + "<font color=FF0066>r</font");
		errorText.setText(errorText.getText() + "<font color=000000>Cats! =^.^=");
		
		player1Score = new JLabel("<html><font color = 0000CC>Score: 0 </font>");
		player2Score = new JLabel("<html><font color = CC0000>Score: 0 </font>");
		player3Score = new JLabel("<html><font color = 006600>Score: 0 </font>");
		player4Score = new JLabel("<html><font color = FF0066>Score: 0 </font>");
		
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
	
	public void newErrorMessage(String message) {
		
		errorText.setText(message);
	}
	
	public void newScores(int score1, int score2) {
		
		player1Score.setText("<html><font color = 0000CC>Score: " + score1 + "</font>");
		player2Score.setText("<html><font color = CC0000>Score: " + score2 + "</font>");
		//REVIVE: add additional Players
		//player3Score.setText("<html><font color = 006600>Score: " + 0 + "</font>");
		//player4Score.setText("<html><font color = FF0066>Score: " + 0 + "</font>");
	}
}

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
			composite.updateScores(composite.model.calculateScore(Player.PLAYER1), composite.model.calculateScore(Player.PLAYER2));
			composite.updateSinglePiece(composite.model);
		}
		else {
			composite.tray.passButton.removeActionListener(composite.tray.buttonListener);
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

class MyButtonListener implements java.awt.event.ActionListener {

	public BrickusModel model;
	
	public MyButtonListener(BrickusModel model) {
		
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent event) {
	
		model.pass(model.getActivePlayer());
	}
}

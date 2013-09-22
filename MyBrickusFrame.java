package edu.jhu.cs.tyung1.oose;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	Color player1color;
	Color player2color;
	Color player3color;
	Color player4color;
	
	public Composite(BrickusModel model) {
		player1color = Color.decode("#0000CC");
		player2color = Color.decode("#CC0000");
		player3color = Color.decode("#006600");
		player4color = Color.decode("#FF0066");
		this.model = model;
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;

		MyMouseListener myListener = new MyMouseListener(this);
		
		MyBrickusListener modelListener = new MyBrickusListener(this);
		model.addBrickusListener(modelListener);
		board = new MyBrickusBoard(model, myListener);
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

	
	
	class MyBrickusBoard extends JComponent {
		private JLabel[][] myLabels;
		int numCol;
		int numRow;
		int[][] mygrid;
		
		int coveredx, coveredy; // the indices of the cell over which the mouse is currently.
		Point coveredCell;
		int cellWidth = 20;
		
		private JLabel coveredSquare;
		
		public MyBrickusBoard(BrickusModel model, MyMouseListener myListener) {
	
			numCol = model.getWidth();
			numRow = model.getHeight();
			mygrid = new int[numRow][numCol];
			for(int y = 0; y < numRow; y++)
			{
				for(int x = 0; x < numCol; x++)
				{
					mygrid[y][x] = 0;
				}
			}
			mygrid[0][0] = 1;
			mygrid[2][2] = 2;
			mygrid[4][4] = 3;
			mygrid[6][6] = 4;
			
			myLabels = new JLabel[numRow][numCol];
			Dimension labelPrefSize = new Dimension(cellWidth, cellWidth);
			setLayout(new GridLayout(numRow, numCol));
	        MouseAdapter mouseHandler;
	        mouseHandler = new MouseAdapter() {
	            @Override
	            public void mouseMoved(MouseEvent e) {
	                ///Point point = e.getPoint();
	
	                int width = getWidth();
	                int height = getHeight();
	
	                int cellWidth = width / numCol;
	                int cellHeight = height / numRow;
	
	                int coveredx = e.getX() / cellWidth;
	                int coveredy = e.getY() / cellHeight;
	                coveredCell = new Point(coveredx, coveredy);
	
	        		// i want to getsource and then find its x y
	        		coveredSquare = (JLabel) e.getSource();
	                repaint();
	
	            }
	        };
			for (int row = 0; row < myLabels.length; row++) {
		         for (int col = 0; col < myLabels[row].length; col++) {
		            JLabel myLabel = new JLabel();
		            myLabel.setOpaque(true);
		            myLabel.setBackground(Color.WHITE);
		            myLabel.addMouseListener(myListener);
		            myLabel.setPreferredSize(labelPrefSize);
		            myLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		            myLabel.addMouseMotionListener(mouseHandler);
		            add(myLabel);
		            myLabels[row][col] = myLabel;
		         }
		      }
	
	        
		}
		public void displayPiece(BrickusPiece piece){
			
		}
		public void paintComponent(Graphics g) {
			//System.out.println("xy: " + coveredx + " " + coveredy);
			for (int row = 0; row < myLabels.length; row++) {
		         for (int col = 0; col < myLabels[row].length; col++) {
		        	 if(mygrid[row][col] == 0)
		        	 {
		        		 myLabels[row][col].setBackground(Color.WHITE);
		        	 }
		        	 else if(mygrid[row][col] == 1)
		        	 {
		        		 myLabels[row][col].setBackground(player1color);
		        	 }
		        	 else if(mygrid[row][col] == 2)
		        	 {
		        		 myLabels[row][col].setBackground(player2color);
		        	 }
		        	 else if(mygrid[row][col] == 3)
		        	 {
		        		 myLabels[row][col].setBackground(player3color);
		        	 }
		        	 else if(mygrid[row][col] == 4)
		        	 {
		        		 myLabels[row][col].setBackground(player4color);
		        	 }
		        	 
		         }
		      }
	        if (coveredCell != null) 
	        {
	        	coveredSquare.setBackground(Color.RED);
	        }
		}
		
	}
	
	class SinglePiece extends JPanel {
		
		BrickusPiece mypiece;
		
		public SinglePiece(BrickusModel model, BrickusPiece piece, MyMouseListener myListener) {
			
			this.setLayout(new GridLayout(5, 5));
			this.setBackground(Color.white);
			this.addMouseListener(myListener);
			int heightBuffer = calculateBuffer(piece.getHeight());
			int widthBuffer = calculateBuffer(piece.getWidth());
			System.out.println(piece.getWidth() + " " +  widthBuffer);
			System.out.println(piece.getHeight() + " " + heightBuffer);
			
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
							System.out.println((w-widthBuffer) + " " + (h-heightBuffer));
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
			
			this.removeAll();
			
			for(BrickusPiece piece: model.getPieces(model.getActivePlayer())) {
				
				SinglePiece newPiece = new SinglePiece(model, piece, myListener);
				this.add(newPiece);
			}
		}
	}
	
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

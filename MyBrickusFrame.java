package edu.jhu.cs.tyung1.oose;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

//REVIVE: remove print statements
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
		
		MyBrickusBoard board = new MyBrickusBoard(model);
		board.setBorder(BorderFactory.createLineBorder(Color.black));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 30;
		constraints.weightx = 1;
		constraints.weighty = 1;
		frame.add(board, constraints);
		
		MyBrickusTray tray = new MyBrickusTray(model); // lower panel of pieces and pass button
		constraints.gridx = 0;
		constraints.gridy = 31;
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
        //g2D.dispose();
	}
}

@SuppressWarnings("serial")
class SinglePiece extends JPanel {
	
	public SinglePiece(BrickusModel model, BrickusPiece piece) {
		
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
	
	public class selectPieceHandler implements MouseListener {

		public void mouseClicked(MouseEvent arg0) {
		
			
		}

		public void mouseEntered(MouseEvent arg0) {
		
			
		}

		public void mouseExited(MouseEvent arg0) {
		
			
		}

		public void mousePressed(MouseEvent arg0) {
		
			
		}

		public void mouseReleased(MouseEvent arg0) {
		
			
		}
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
			SinglePiece newPiece = new SinglePiece(model, piece);
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
	
	JLabel errorText;
	
	public MyBrickusTracker(BrickusModel model) {
		
		//REVIVE: make BrickusListener class
		model.addBrickusListener(new ErrorHandler());
		
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
	
	public void changeErrorMessage(String message) {
		
		errorText.setText(message);
	}
	
	public class ErrorHandler implements BrickusListener {

		public void illegalMove(BrickusIllegalMoveEvent event) {
		
			changeErrorMessage(event.getMessage());
		}

		public void modelChanged(BrickusEvent arg0) {
		
			return;
		}
		
		
	}
}

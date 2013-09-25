package kwong23.brickusui;
/**
 * Kyle Wong
 * 13.9.25
 * kwong23@jhu.edu
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

/**
 * Specialized Component that can represent a Brickus Board, a tray of pieces, and a message board.
 * @author KT Wong
 *
 */
@SuppressWarnings("serial")
class FullComponent extends JComponent {
	
	BrickusModel model;
	MyBrickusBoard board;
	MyBrickusTray tray;
	MyBrickusTracker tracker;
	Color player1color;
	Color player2color;
	Color player1colorhalf;
	Color player2colorhalf;
	boolean pieceSelected;
	SinglePiece activepiece;
	MyBrickusFrame framer;
	MyBrickusListener modelListener;
	
	/**
	 * Constructor to create a FullComponent which holds the board, the tray, and a tracker inside of it.
	 * @param model The BrickusModel to display with a graphical interface.
	 * @param fram The JFrame which is holding the FullComponent (this is for creating a new game / resetting).
	 */
	public FullComponent(BrickusModel model, MyBrickusFrame fram) {
		player1color = new Color(0,0,240); //blue
		player2color = new Color(240,0,0); //red
		player1colorhalf = new Color(0,0,240,120);
		player2colorhalf = new Color(240,0,0,120);
		
		this.model = model;
		this.framer = fram;
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;

		MyMouseListener myListener = new MyMouseListener(this);
		
		modelListener = new MyBrickusListener(this);
		model.addBrickusListener(modelListener);
		board = new MyBrickusBoard(model, myListener);
		board.setBorder(BorderFactory.createLineBorder(Color.black));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.ipady = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		this.add(board, constraints);

		tray = new MyBrickusTray(model, myListener); // lower panel of pieces and pass button
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.ipady = 220;
		constraints.weightx = 1;
		constraints.weighty = 0;
		this.add(tray, constraints);

		tracker = new MyBrickusTracker(model); // error board, score boards
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.ipady = 0;
		constraints.weightx = 1;
		constraints.weighty = 0;
		this.add(tracker, constraints);
	}
	
	/**
	 * Function to update the error message of illegal Brickus piece placements.
	 * @param message The error message explaining what happened.
	 */
	public void updateErrorMessage(String message) {
		
		tracker.newErrorMessage(message);
	}
	
	/**
	 * Function to respond to a piece being selected in the tray.
	 * @param panel The SinglePiece panel holding the piece that was clicked.
	 */
	public void pieceClicked(SinglePiece panel) {
		
		if(activepiece != null) {
			activepiece.unselected();
		}
		activepiece = panel;
		activepiece.selected();

		//I HAVE THE SELECTED PIECE
		board.pieceSelected();
		panel.repaint();
	}
	
	/**
	 * Function to update the total score count for player 1 and player 2.
	 * @param score1 The score of player 1.
	 * @param score2 The score of player 2.
	 */
	public void updateScores(int score1, int score2) {
		
		tracker.newScores(score1, score2);
	}
	
	/**
	 * Method to update the display of each of the Brickus pieces.
	 * @param model The BrickusModel that is updating the piece list.
	 */
	public void updateSinglePiece(BrickusModel model) {
		
		tray.updateSinglePiece(model);
		board.repaint();
		tray.revalidate();
	}
	
	/**
	 * Function to handle right clicks which is for flipping pieces horizontally.
	 */
	public void rightClick(){
		if(pieceSelected){
			activepiece.mypiece.flipHorizontally();
			repaint();
		}
	}

	/**
	 * Function to handle shift right clicks which is for flipping pieces vertically.
	 */
	public void shiftRightClick(){
		if(pieceSelected){
			activepiece.mypiece.flipVertically();
			repaint();
		}
	}
	
	/**
	 * Function to handle scroll up motion on the wheel for rotating pieces counterclockwise.
	 */
	public void scrollUpWheel(){
		if(pieceSelected){
			activepiece.mypiece.rotateCounterClockwise();
			repaint();
		}
	}
	
	/**
	 * Function to handle scroll down motion on the wheel for rotating pieces clockwise.
	 */
	public void scrollDownWheel(){
		if(pieceSelected){
			activepiece.mypiece.rotateClockwise();
			repaint();
		}
	}
	
	/**
	 * Nested board class which represents the Brickus board using paint on a rectangle
	 * @author KT Wong
	 *
	 */
	class MyBrickusBoard extends JComponent {

		int numCol;
		int numRow;
		int[][] mygrid;
		int coveredx, coveredy; // the indices of the cell over which the mouse is currently.
		Point coveredCell;
		int cellWidth = 20;
		MouseAdapter mouseHandler;
		MyMouseListener myListener;
		
		/**
		 * Constructor to create a Brickus Board.
		 * @param model The BrickusModel managing the board logic.
		 * @param myListener The mouse listener which will track user inputs from the mouse.
		 */
		public MyBrickusBoard(BrickusModel model, MyMouseListener myListener) {
			pieceSelected = false;
			numCol = model.getWidth();
			numRow = model.getHeight();
			this.myListener = myListener;
			mygrid = new int[numRow][numCol];
			for(int y = 0; y < numRow; y++)
			{
				for(int x = 0; x < numCol; x++)
				{
					mygrid[y][x] = 0;
				}
			}

	        mouseHandler = new MouseAdapter() {
	        	/**
	        	 * Specially designed function for detecting a mouse event for placing pieces.
	        	 */
	        	@Override
	        	public void mouseClicked(MouseEvent e){
	        		if(e.getButton() == MouseEvent.BUTTON1)
	        		{
		                int width = getWidth();
		                int height = getHeight();
		
		                int cellWidth = width / numCol;
		                int cellHeight = height / numRow;
		
		                coveredx = e.getX() / cellWidth;
		                coveredy = e.getY() / cellHeight;
		                coveredCell = new Point(coveredx, coveredy);
		                placePiece();
	        		}
	        	}
	        	/**
	        	 * Special function to creating a shadow representation of the pieces on the board.
	        	 */
	            @Override
	            public void mouseMoved(MouseEvent e) {
	                ///Point point = e.getPoint();
	
	                int width = getWidth();
	                int height = getHeight();
	
	                int cellWidth = width / numCol;
	                int cellHeight = height / numRow;
	
	                coveredx = e.getX() / cellWidth;
	                coveredy = e.getY() / cellHeight;
	                coveredCell = new Point(coveredx, coveredy);
	
	                repaint();
	
	            }
	        };
	        addMouseMotionListener(mouseHandler);
	        addMouseListener(mouseHandler);
	        this.addMouseListener(myListener);
	        this.addMouseWheelListener(myListener);
	        
		}
		
		/**
		 * Function to set the variable pieceSelected to true for identifying which piece is selected.
		 */
		public void pieceSelected(){
			pieceSelected = true;
		}
		
		/**
		 * Function to attempt to place a piece on the Brickus board.
		 */
		public void placePiece(){
			Player placingPlayer = model.getActivePlayer();
			int playerNum = 0;
			if(placingPlayer == Player.PLAYER1){
				playerNum = 1;
			}
			else if(placingPlayer == Player.PLAYER2){
				playerNum = 2;
			}
			if(activepiece != null){
				
			
				model.placePiece(placingPlayer, coveredx, coveredy, activepiece.mypiece);
				if(placingPlayer != model.getActivePlayer())
				{ //successful placement so update model
					for(int row = 0; row < activepiece.mypiece.getHeight(); row++){
						for(int col = 0; col < activepiece.mypiece.getWidth(); col++){
							if(activepiece.mypiece.isOccupied(col, row)){
								mygrid[coveredy+row][coveredx+col] = playerNum; 		
							}
						}
					}
					activepiece = null;
					pieceSelected = false;
				}
			}
		}
		
		/**
		 * Repaint function for displaying the Brickus board with a piece shadow overlay.
		 */
		public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = this.getWidth();
            int height = this.getHeight();
            g.setColor(Color.BLACK);
            g.fillRect(0,0,width, height);
            int cellWidth = (width-numCol) / (numCol);
            int cellHeight = (height -numRow)/ (numRow);
            

            int xOffset = (width - (numCol * cellWidth)) / 4;
            int yOffset = (height - (numRow * cellHeight)) / 4;
			for (int row = 0; row < numRow; row++) {
		         for (int col = 0; col < numCol; col++) {
		        	 if(mygrid[row][col] == 0)
		        	 {
		        		 g.setColor(Color.WHITE);
		        	 }
		        	 else if(mygrid[row][col] == 1)
		        	 {
		        		 g.setColor(player1color);
		        	 }
		        	 else if(mygrid[row][col] == 2)
		        	 {
		        		 g.setColor(player2color);
		        	 }

		        	 g.fillRect(col*cellWidth+col+xOffset,row*cellHeight+row+yOffset,cellWidth, cellHeight);
		         }
		      }
	        if (coveredCell != null) 
	        {	//affect the coveredCell
	        	Color shadow = Color.WHITE;
				for (int row = 0; row < numRow; row++) {
			         for (int col = 0; col < numCol; col++) {
			        	 if(row == coveredy && col == coveredx )
			        	 {
			        		 if(model.getActivePlayer() == Player.PLAYER1){
			        			 shadow = player1colorhalf;
			        		 }
			        		 else if(model.getActivePlayer() == Player.PLAYER2){
			        			 shadow = player2colorhalf;
			        		 }

			        		 if(pieceSelected)
			        		 for(int y = 0; y < activepiece.mypiece.getHeight(); y++){
			        			 for(int x = 0; x < activepiece.mypiece.getWidth(); x++){
			        				if(activepiece.mypiece.isOccupied(x,y)){
			        					 if(col+x < model.getWidth() && row + y < model.getHeight()){
			        						 g.setColor(shadow);
			        						 g.fillRect((col+x)*cellWidth+(col+x)+xOffset,(row+y)*cellHeight+(row+y)+yOffset,cellWidth, cellHeight);
			        						 
			        					 }
			        				 }
			        			 }
			        		 } // end piece loop
			        	 }
			         }
				} // end board loop
	        }
		}// end paintComponent
		
	}//end MyBrickusBoard

	/**
	 * Class for holding the Brickus Tray which has the Brickus pieces and a pass button.
	 * @author KT Wong
	 *
	 */
	class MyBrickusTray extends JPanel {
		
		JButton passButton;
		MyButtonListener buttonListener;
		pieceTray tray;
		MyMouseListener myListener;
		boolean gameover = false;
		
		/**
		 * Constructor to create a tray for holding the pieces and the pass button.
		 * @param model The BrickusModel controlling the logic.
		 * @param myListener The mouse listener which listens for mouse input from the user.
		 */
		public MyBrickusTray(BrickusModel model, MyMouseListener myListener) {
			
			this.myListener = myListener;
			GridBagLayout my = new GridBagLayout();
			this.setLayout(my);
			this.setBorder(BorderFactory.createLineBorder(Color.black));		
			GridBagConstraints constraints = new GridBagConstraints();
			
			pieceTray pieceTray = new pieceTray(model, myListener);
			pieceTray.setBorder(BorderFactory.createLineBorder(Color.black));
			constraints.fill = GridBagConstraints.BOTH;
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth = 8;
			constraints.gridheight = 1;
			constraints.weightx = 1;
			constraints.weighty = 1;

			my.setConstraints(pieceTray, constraints);
			this.add(pieceTray, constraints);
			this.tray = pieceTray;
			
			passButton = new JButton("Pass");
			MyButtonListener buttonListener = new MyButtonListener(model, this);
			passButton.addActionListener(buttonListener);
			this.buttonListener = buttonListener;
			constraints.fill = GridBagConstraints.VERTICAL;
			constraints.gridx = 8;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			constraints.weightx = 0;
			constraints.weighty = 1;

			my.setConstraints(passButton, constraints);
			this.add(passButton, constraints);
		}
		
		/**
		 * Function for updating a single piece.
		 * @param model The BrickusModel which has updated the piece.
		 */
		public void updateSinglePiece(BrickusModel model) {
			
			this.tray.updateSinglePiece(model, myListener);
		}
		
		/**
		 * Function to handle the changes when the game ends which includes removing listeners.
		 */
		public void gameover(){
			gameover = true;
			passButton.setText("New Game");
			model.removeBrickusListener(modelListener);
			board.removeMouseMotionListener(board.mouseHandler);
			board.removeMouseListener(board.mouseHandler);
			board.removeMouseListener(board.myListener);
			board.removeMouseWheelListener(board.myListener);
		}
		
		/**
		 * Function to create a new Brickus game by telling the frame to reset and recursively call main.
		 */
		public void newGame(){
			framer.reset();
		}
	}
	
	/**
	 * Nested class for holding the Brickus pieces.
	 * @author KT Wong
	 *
	 */
	class pieceTray extends JPanel {
		
		MyMouseListener myListener;
		
		/**
		 * Constructor to create the tray for holding the pieces
		 * @param model The BrickusModel supplying the Brickus pieces.
		 * @param myListener The mouse listener that will listen for all mouse input.
		 */
		public pieceTray(BrickusModel model, MyMouseListener myListener) {
			
			this.myListener = myListener;
			this.setLayout(new GridLayout(3, 7));
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			
			for(BrickusPiece piece: model.getPieces(model.getActivePlayer())) {
				
				SinglePiece newPiece = new SinglePiece(model, piece, myListener);
				this.add(newPiece);
			}
		}
		
		/**
		 * Function to update the display of a single piece after the Brickus model changes. 
		 * @param model The BrickusModel causing the update.
		 * @param myListener The mouse listener to be added for each piece.
		 */
		public void updateSinglePiece(BrickusModel model, MyMouseListener myListener) {
			int count = 0;
			this.removeAll();
			this.repaint();
			for(BrickusPiece piece: model.getPieces(model.getActivePlayer())) {
				SinglePiece newPiece = new SinglePiece(model, piece, myListener); 
				this.add(newPiece);
				count++;
			}
			while(count < 21){
				BrickusPiece temp = null;
				SinglePiece newPiece = new SinglePiece(model, temp, myListener); 
				this.add(newPiece);
				count++;
			}
		}
		
	}
	
	/**
	 * Nested class for representing single Brickus pieces at a time
	 * @author KT Wong
	 *
	 */
	class SinglePiece extends JPanel {
		
		BrickusPiece mypiece;
		int numRow;
		int numCol;
		int heightBuffer;
		int widthBuffer;
		Color playerColor;
		boolean highlight;
		
		/**
		 * Constructor for a single Brickus piece representation 
		 * @param model The BrickusModel which controls the piece
		 * @param piece The Brickus piece to be displayed
		 * @param myListener The custom mouse listener for tracking mouse input.
		 */
		public SinglePiece(BrickusModel model, BrickusPiece piece, MyMouseListener myListener) {
			numRow = 5;
			numCol = 5;
			mypiece = piece;
			this.setBackground(Color.white);
			if(piece == null) return;
			this.addMouseListener(myListener);
			this.addMouseWheelListener(myListener);

			heightBuffer = (numRow-mypiece.getHeight()) / 2;
			widthBuffer = (numCol-mypiece.getWidth()) / 2;
			
			Player activePlayer = model.getActivePlayer();
			if(activePlayer == Player.PLAYER1) {
				playerColor = player1color;
			}
			else {
				playerColor = player2color;
			}
			repaint();
			
		}
		
		/**
		 * Function to repaint and update the single piece.
		 */
		public void paintComponent(Graphics g) {
			if(mypiece == null) return;
			heightBuffer = (numRow-mypiece.getHeight()) / 2;
			widthBuffer = (numCol-mypiece.getWidth()) / 2;
            super.paintComponent(g);
            int width = this.getWidth();
            int height = this.getHeight();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0,0,width, height);
            int cellWidth = (width-numCol) / (numCol);//+1);
            int cellHeight = (height -numRow)/ (numRow);//+1);
            int xOffset = (width - (numCol * cellWidth)) / 4;
            int yOffset = (height - (numRow * cellHeight)) / 4;
			for (int row = 0; row < numRow; row++) {
		         for (int col = 0; col < numCol; col++) {
		        	 if(row < heightBuffer && col < widthBuffer)
		        	 {
		        		 if(highlight)
		        		 {
		        			 g.setColor(Color.YELLOW);
		        		 }
		        		 else
		        		 {
		        			 g.setColor(Color.LIGHT_GRAY);
		        		 }
		        			 
		        	 }
		        	 else if(mypiece.isOccupied(col-widthBuffer, row-heightBuffer))
		        	 {
		        		 g.setColor(playerColor);
		        	 }
		        	 else 
		        	 {		        		 
		        		 if(highlight)
		        		 {
		        			 g.setColor(Color.YELLOW);
		        		 }
		        		 else
		        		 {
		        			 g.setColor(Color.LIGHT_GRAY);
		        		 }
		        	 }
		        	 g.fillRect(col*cellWidth+col+xOffset,row*cellHeight+row+yOffset,cellWidth, cellHeight);
		         }
		      }
		}

		/**
		 * Function to respond when the piece is selected.
		 * @return mypiece The Brickus Piece that was selected.
		 */
		public BrickusPiece selected(){
			
			//highlight myself
			highlight = true;
			return mypiece;
		}
		
		/**
		 * Function to respond when a piece is unselected.
		 */
		public void unselected(){
			//unhightlight
			highlight = false;
			repaint();
		}

	}
	
	/**
	 * Nested class for holding the error messages and score for Brickus.
	 * @author KT Wong
	 *
	 */
	class MyBrickusTracker extends JPanel {
		
		JLabel errorText;
		JLabel player1Score;
		JLabel player2Score;
		
		/**
		 * Constructor for creating the message tracker.
		 * @param model The BrickusModel supplying the error messages.
		 */
		public MyBrickusTracker(BrickusModel model) {
			
			this.setLayout(new GridBagLayout());
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.HORIZONTAL;
			
			JPanel subpanelLeft = new JPanel(new BorderLayout());
			JPanel subpanelRight = new JPanel(new FlowLayout(FlowLayout.TRAILING));
			
			errorText = new JLabel();
			
			player1Score = new JLabel("<html><font color = 0000CC>Score: 0 </font>");
			player2Score = new JLabel("<html><font color = CC0000>Score: 0 </font>");
			
			subpanelLeft.add(errorText);
			subpanelRight.add(player1Score);
			subpanelRight.add(player2Score);
			
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
		
		/**
		 * Function to update the error messages displayed.
		 * @param message The error message to be displayed.
		 */
		public void newErrorMessage(String message) {
			
			errorText.setText(message);
		}
		
		/**
		 * Function to update the scores displayed
		 * @param score1 The updated score of player 1
		 * @param score2 The updated score of player 2
		 */
		public void newScores(int score1, int score2) {
			
			player1Score.setText("<html><font color = 0000CC>Score: " + score1 + "</font>");
			player2Score.setText("<html><font color = CC0000>Score: " + score2 + "</font>");
		}
	}
}


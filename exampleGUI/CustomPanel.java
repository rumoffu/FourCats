package edu.jhu.cs.oose.headfirstjava.chap12;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CustomPanel  {

    JFrame frame;
    JLabel label;

    public static void main (String[] args) {
       CustomPanel gui = new CustomPanel();
       gui.go();
    }

    public void go() {
       frame = new JFrame();
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       JButton colorButton = new JButton("Change Circle");
       colorButton.addActionListener(new ColorButtonListener());

       MySimplePanel drawPanel = new MySimplePanel();
       
       frame.getContentPane().add(BorderLayout.SOUTH, colorButton);
       frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

       frame.setSize(420,300);
       frame.setVisible(true);
    }
    
     class ColorButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            frame.repaint();
        }
     }  // close inner class
   
}

@SuppressWarnings("serial")
class MySimplePanel extends JPanel {
    
      public void paintComponent(Graphics g) {
         
    	 super.paintComponent(g); // superclass may be putting something on background; put this in as good form
    	 
    	 g.setColor(Color.BLUE);
         g.fillRect(0,0,this.getWidth(), this.getHeight());

         // make random colors to fill with
         int red = (int) (Math.random() * 255);
         int green = (int) (Math.random() * 255);
         int blue = (int) (Math.random() * 255);

         Color randomColor = new Color(red, green, blue);
         g.setColor(randomColor);
         g.fillOval(70,70,100,100);
      }

}
      

       
      

       

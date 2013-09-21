package edu.jhu.cs.oose.headfirstjava.chap12;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class LabelAndButton  {

    JLabel label;

    public static void main (String[] args) {
       LabelAndButton gui = new LabelAndButton();
       gui.go();
    }

    public void go() {
       JFrame frame = new JFrame();
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       JButton labelButton = new JButton("Change Label");
       labelButton.addActionListener(new LabelButtonListener());

       label = new JLabel("I'm a label");       
       
       frame.getContentPane().add(BorderLayout.SOUTH,labelButton);
       frame.getContentPane().add(BorderLayout.NORTH,label);

       frame.setSize(100,100);
       frame.setVisible(true);
    }
    
     class LabelButtonListener implements ActionListener {
    	    String s = "Ouch";
       public void actionPerformed(ActionEvent event) {
            s = s.concat("!");
            label.setText(s);
        }
     } // close inner class


}



       
      

       

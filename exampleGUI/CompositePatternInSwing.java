package edu.jhu.cs.oose.headfirstjava.chap12;
import javax.swing.*;
import java.awt.*;


public class CompositePatternInSwing  {

    JFrame frame;
    JLabel label1, label2;
    JButton button1, button2;
    JPanel labelPanel, buttonPanel, fullPanel;

    public static void main (String[] args) {
       CompositePatternInSwing gui = new CompositePatternInSwing();
       gui.go();
    }

    public void go() {
       frame = new JFrame();
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
       label1 = new JLabel("I'm a label!");  
       label2 = new JLabel("Me too!");  

       labelPanel = new JPanel();
       labelPanel.add(label1);
       labelPanel.add(label2);

       button1 = new JButton("I'm just a button");
       button2 = new JButton("Ditto");
       
       buttonPanel = new JPanel();
       buttonPanel.add(button1);
       buttonPanel.add(button2);
       
       fullPanel = new JPanel();
       fullPanel.setLayout(new BoxLayout(fullPanel, BoxLayout.Y_AXIS));
       fullPanel.add(labelPanel);
       fullPanel.add(buttonPanel);
 
       frame.getContentPane().add(BorderLayout.CENTER,fullPanel);
       frame.setSize(400,200);
       frame.setVisible(true);
    }


}

   

       
      

       

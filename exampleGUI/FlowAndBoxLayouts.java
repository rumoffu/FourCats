package edu.jhu.cs.oose.headfirstjava.chap12;

import javax.swing.*;
import java.awt.*;

public class FlowAndBoxLayouts {
	public static void main(String[] args) {
		FlowAndBoxLayouts gui = new FlowAndBoxLayouts();
		gui.go();
	}

	public void go() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.setBackground(Color.darkGray);
// uncomment to turn default FlowLayout to a BoxLayout
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JButton("these"));
		panel.add(new JButton("are"));
		panel.add(new JButton("some"));
		panel.add(new JButton("buttons!"));
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.setSize(250, 200);
		frame.setVisible(true);
	}
}

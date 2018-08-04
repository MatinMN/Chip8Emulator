package chip;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display extends JFrame{
	private final int WIDTH = 640;
	private final int HEIGHT = 320;
	
	private Panel panel;
	
	public Display(Chip8 chip8) {
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		panel = new Panel(chip8);	
		add(panel);
		setVisible(true);
	}

}

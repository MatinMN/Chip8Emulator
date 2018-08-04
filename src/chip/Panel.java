package chip;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Panel extends JPanel{
	
	private Chip8 chip8;
	
	public Panel(Chip8 chip8) {
		this.chip8 = chip8;
	}
	
	public void paint(Graphics g) {
		
		byte[] display = chip8.getDisplay();
		
		for (int i = 0;i < display.length;i++) {
			if(display[i] == 0) 
				g.setColor(Color.black);
			else 
				g.setColor(Color.white);
			
			int x = i % 64;
			int y = (int)Math.floor(i/64);
			g.fillRect(x*10, y*10, 10, 10);
		}
	}
}

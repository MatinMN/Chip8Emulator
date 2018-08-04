package chip;

import java.io.IOException;

public class Main extends Thread{
	Chip8 chip8;
	Display display;
	
	private boolean running;
	
	public Main() throws IOException {
		chip8 = new Chip8();
		chip8.init();
		chip8.loadProgram("./roms/pong.ch8");
		display = new Display(chip8);
		running = true;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Main main = new Main();
		main.start();
	}

	public void run() {
		while(running) {
			//chip8.run();
			if(chip8.updated()) {
				display.repaint();
				chip8.removeUpdateFlag();
			}
			try {
				Thread.sleep(13);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

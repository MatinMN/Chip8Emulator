package chip;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Chip8 {
	
	private char[] memory;
	private char[] V;
	private char I;
	private char pc;
	
	private char stack[];
	private int stackPointer;
	
	private int delay_timer;
	private int sound_timer;
	
	private byte[] keys;
	
	private byte[] display;
	
	private boolean updatedFlag;
	public void init(){
		memory = new char[4096];
		V = new char[16];
		pc = 0x200;
		
		I = 0x0;
		
		stack = new char[16];
		
		delay_timer = 0;
		sound_timer = 0;
		
		keys = new byte[16];
		
		display = new byte[64 * 32];
		
		updatedFlag = false;
		
		loadFontset();
	}
	
	public void run() {
	
		char opcode = (char)((memory[pc] << 8) | memory[pc + 1]);
		
		System.out.println(Integer.toHexString(opcode));
		
		switch (opcode & 0xF000) {
		
		case (0x0000): //0XXX
			
			switch (opcode & 0x000F) {
			case(0x0000): //00E0
				// clear screen
				break;
			case(0x000E): //00EE
				// return from subroutine
				pc = (char) (stack[--stackPointer] + 2);
				break;
			}
			break;
		case(0x1000): //1NNN (jump to NNN)
			pc = (char)(opcode & 0x0FFF);
			break;
		
		case(0x2000): //1NNN (jump to NNN)
			char address = (char)( opcode & 0x0FFF);
			stack[stackPointer++] = pc;
			//System.out.println(Integer.toHexString(address));
			pc = address;
			break;
		case(0x3000):{ //3XNN if(Vx==NN)
			int x = ((opcode & 0x0F00) >> 8);
			int _nnn = ((opcode & 0x0FF));
			System.out.println(V[x]);
			if(V[x] == _nnn)pc+=4;
			else pc+=2;
			break;
		}
		case(0x5000): //1NNN (jump to NNN)
			break;
			
		case(0x6000): //1NNN (jump to NNN)
			
			int x = ((opcode & 0x0F00) >> 8);
			V[x] = (char) (opcode & 0x00FF);
			pc+=2;
			break;
		
		case(0xA000): //1NNN 
			I = (char)( opcode & 0x0FFF);
			pc+=2;
			break;
			
		case(0x7000): //1NNN (jump to NNN)
			x = ((opcode & 0x0F00) >> 8);
			V[x] = (char)((V[x] + (char) (opcode & 0x00FF) ) & 0xFF);
			pc+=2;
			break;
			
		case(0x8000): //1NNN (jump to NNN)
			break;
		
		case(0xD000):{ //DXYN
			int _x = V[((opcode & 0x0F00) >> 8)];
			int _y = V[((opcode & 0x00F0) >> 4)];
			int height = opcode & 0x000F;
			
			for (int i = 0;i<height;i++) {
				int line = memory[I] + i;
				for (int j=0;j<8;j++) {
					int pixel = line & (0x80 >> j);
					if (pixel !=0) {
						int index = (_x + j) + (_y + i) * 64;
						
						if(display[index] == 1) {
							V[0xF] = 1;
						}
						
						display[index] ^= 1;
					}
				}
			}
			pc+=2;
			updatedFlag = true;
			break;
		}
		}
		
	}
	
	public byte[] getDisplay() {
		return display;
	}

	public boolean updated() {
		// TODO Auto-generated method stub
		return updatedFlag;
	}

	public void removeUpdateFlag() {
		// TODO Auto-generated method stub
		updatedFlag = false;
	}

	public void loadProgram(String file) throws IOException {
		// TODO Auto-generated method stub
		DataInputStream input = null;
		try {
			input = new DataInputStream(new FileInputStream(new File(file)));
			for (int i = 0 ; input.available() > 0; i++) {
				memory[0x200 + i] = (char)(input.readByte() & 0xFF);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(input!=null) {
				input.close();
			}
		}
	}
	
	public void loadFontset() {
		for(int i = 0; i < Main.fontset.length; i++) {
			memory[0x50 + i] = (char)(Main.fontset[i] & 0xFF);
		}
	}
}

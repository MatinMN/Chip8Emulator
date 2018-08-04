package chip;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Chip8 {
	private char[] memory;
	private char[] registers; // chip8 has 16 registers V0 to VF
	private char I; // address register
	private char pc;// program counter
	
	private char stack[];
	private int stackPointer;
	
	private int delayTimer;
	private int soundTimer;
	
	private byte[] keys;
	
	private byte[] display;
	
	private boolean updatedFlag;
	
	public void init() {
		
		memory = new char[4096];
		registers = new char[16];
		I = 0x0; 
		pc = 0x200; // the lower 512 bytes are generally used for font data
		stack = new char[16];
		stackPointer = 0;
		
		delayTimer = 0;
		soundTimer = 0;
		
		keys = new byte[16];
		display = new byte[64 * 32] ; // screen size is 64 * 32 (when displayed it's up scaled to 640 * 320)
		
		updatedFlag = false;
	}
	
	public void run() {
		char opcode = (char)( (memory[pc] << 8) | memory[pc + 1]);
		System.out.print(Integer.toHexString(opcode) + ": ");
		
		switch (opcode & 0xF000) { // get the first nibble
		
		case 0x8000: // if the first nibble is 0x8 
			
			switch (opcode & 0x000F) { // get the last nibble 
			
			case 0x0000: // 8xy0 
				
				default:
					System.err.println("Unsupported opcode");
					System.exit(0);
					break;
			}
			break;
			
		default:
			System.err.println("Unsupported opcode");
			System.exit(0);

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
}

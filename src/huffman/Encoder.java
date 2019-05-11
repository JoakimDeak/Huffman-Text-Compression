package huffman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Encoder {

	private File inputFile;

	public Encoder(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		if(file.exists()) {
			this.inputFile = file;// makes sure a file with the given name exists
		} else {
			throw new FileNotFoundException(); 
		}
		
	}

	public void encode() {
		Tree tree = null;
		TreeMaker tm = new TreeMaker();
		try {
			tree = tm.makeTree(this.inputFile);
			encode(tree.getCharCodes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void encode(ArrayList<String> charCodes) throws IOException {

		Scanner sc = new Scanner(this.inputFile);
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Encoded-" + this.inputFile.getName())));

		while (sc.hasNextLine()) {
			String cLine = sc.nextLine();
			for (int i = 0; i < cLine.length(); i++) {
				char cChar = cLine.charAt(i);
				for(String code : charCodes) {
					if(code.charAt(0) == cChar) { // find the code for current character
						writer.write(code.substring(1)); // excluding first character which is the character to be encoded
					}
				}
			}
		}

		sc.close();
		writer.close();
	}
	
	public static void main(String[] args) {
		Encoder e = null;
		try {
			e = new Encoder("inputText.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		e.encode();
	}
}

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
		if (file.exists()) { // makes sure a file with the given name exists
			this.inputFile = file;
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
		String outputFileName = "Encoded-" + this.inputFile.getName().substring(0, this.inputFile.getName().lastIndexOf('.')) + ".bin";
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFileName)));

		while (sc.hasNextLine()) { // goes through every line
			String cLine = sc.nextLine();
			for (int i = 0; i < cLine.length(); i++) { // goes through every character
				char cChar = cLine.charAt(i);
				for (String code : charCodes) {
					if (code.charAt(0) == cChar) { // find the code for current character
						writer.write(code.substring(1)); // excluding first character which is the character to be
															// encoded
					}
				}
			}
		}

		sc.close();
		writer.close();
	}
}

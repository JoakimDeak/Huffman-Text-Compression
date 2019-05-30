package huffman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Decoder {

	private Tree tree;

	public void decode(String treeFileName, String inputFileName) throws FileNotFoundException {
		File treeFile = new File(treeFileName);
		File inputFile = new File(inputFileName);

		if (treeFile.exists() && inputFile.exists()) { // makes sure both files exists before continuing
			initStreams(treeFileName, inputFileName);
		} else {
			throw new FileNotFoundException();
		}
	}

	private void initStreams(String treeFileName, String inputFileName) { // creates streams
		ObjectInputStream ois = null;
		BufferedWriter writer = null;
		FileInputStream fis = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(treeFileName));
			createTree(ois); // method call to create tree from .data file

			fis = new FileInputStream(inputFileName);
			String outputFileName = "Decoded-" + inputFileName.substring(0, inputFileName.lastIndexOf('.')) + ".txt";
			writer = new BufferedWriter(new FileWriter(outputFileName));
			decode(fis, writer); // method call to decode text
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally { // closes streams
			try {
				ois.close();
				fis.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void decode(FileInputStream fis, BufferedWriter writer) throws IOException {

		StringBuilder littleE = new StringBuilder();

		while (fis.available() > 0) { // while there are bytes to read
			int av = fis.available();
			byte[] bytes = null;

			if (av > 256) { // create byte array with size 256
				bytes = new byte[256];
			} else { // if there are less than 256 bytes left to read create byte array with smaller size
				bytes = new byte[av];
			}

			fis.read(bytes); // read bytes into byte array
			for (byte b : bytes) { // add binary representation of byte to string
				littleE.append(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
			}
		}

		Node cNode = this.tree.getRoot(); // start at root of tree

		for (int i = 0; i < littleE.length(); i++) {
			int ai = bigE(i);

			if (cNode.isLeaf()) { // if leaf has been reached
				writer.write(cNode.getCharacter()); // write the character
				cNode = this.tree.getRoot(); // start over from the root
			}

			char cChar = littleE.charAt(ai); // read character

			if (cChar == '0') { // if character is 0 go left
				cNode = cNode.left();
			} else if (cChar == '1') { // if character is 1 go right
				cNode = cNode.right();
			}
		}
	}
	
	private int bigE(int littleE) { // order for reading little endian as big endian
		int bigE = littleE / 8;
		bigE *= 8;
		bigE += 7;
		bigE -= littleE % 8;
		
		return bigE;
	}

	private void createTree(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		this.tree = (Tree) ois.readObject(); // creates tree from .data file
	}

}

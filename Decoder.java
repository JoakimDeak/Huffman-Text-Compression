package huffman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

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

	private void initStreams(String treeFileName, String inputFileName) { // creates scanner and streams
		ObjectInputStream ois = null;
		BufferedWriter writer = null;
		Scanner sc = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(treeFileName));
			createTree(ois); // method call to create tree from .data file

			sc = new Scanner(new File(inputFileName));
			String outputFileName = "Decoded-" + inputFileName.substring(0, inputFileName.lastIndexOf('.')) + ".txt";
			writer = new BufferedWriter(new FileWriter(outputFileName));
			decode(sc, writer); // method call to decode text
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally { // closes objectinputStream, scanner, and bufferedWriter
			try {
				ois.close();
				sc.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void decode(Scanner sc, BufferedWriter writer) throws IOException {
		Node cNode = this.tree.getRoot(); // start at root of tree

		while (sc.hasNext()) {
			String cLine = sc.nextLine();
			for (int i = 0; i < cLine.length(); i++) {
				if (cNode.isLeaf()) { // leaf has been reached print the character
					writer.write(cNode.getCharacter());
					cNode = this.tree.getRoot(); // start over from the root
				}
				char cChar = cLine.charAt(i);

				if (cChar == '0') { // 0 is left
					cNode = cNode.left();
				} else if (cChar == '1') { // 1 is right
					cNode = cNode.right();
				}
			}
		}
	}

	private void createTree(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		this.tree = (Tree) ois.readObject(); // creates tree from .data file
	}
}

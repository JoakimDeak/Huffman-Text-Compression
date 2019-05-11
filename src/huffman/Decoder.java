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
		
		if(treeFile.exists() && inputFile.exists()) {
			initStreams(treeFileName, inputFileName);
		} else {
			throw new FileNotFoundException();
		}
	}

	private void initStreams(String treeFileName, String inputFileName) {
		ObjectInputStream ois = null;
		BufferedWriter writer = null;
		Scanner sc = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(treeFileName));
			createTree(ois);
			
			sc = new Scanner(new File(inputFileName));
			writer = new BufferedWriter(new FileWriter("Decoded-" + inputFileName));
			decode(sc, writer);
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				sc.close();
				writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void decode(Scanner sc, BufferedWriter writer) throws IOException {
		Node cNode = this.tree.getRoot();
		
		while(sc.hasNext()) {
			String cLine = sc.nextLine();
			for(int i = 0; i < cLine.length(); i++) {
				if(cNode.isLeaf()) {
					writer.write(cNode.getCharacter());
					cNode = this.tree.getRoot();
				}
				char cChar = cLine.charAt(i);
				
				if(cChar == '0') {
					cNode = cNode.left();
				} else if(cChar == '1') {
					cNode = cNode.right();
				}
			}
		}
	}
	
	private void createTree(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		this.tree = (Tree) ois.readObject();
	}
	
	public static void main(String[] args) {
		Decoder d = new Decoder();
		try {
			d.decode("tree.data", "Encoded-inputText.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		try {
//			Encoder e = new Encoder("inputText.txt");
//			e.encode();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
	}
}

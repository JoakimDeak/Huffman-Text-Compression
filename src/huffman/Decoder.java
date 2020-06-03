package huffman;

import java.io.*;
import java.util.BitSet;

public class Decoder {

	public void decode(String inputFileName) throws FileNotFoundException {
		File inputFile = new File(inputFileName);

		if (inputFile.exists()) { // Makes sure both files exists before continuing
			initStreams(inputFileName);
		} else {
			throw new FileNotFoundException();
		}
	}

	private void initStreams(String inputFileName) {
		BufferedWriter writer = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(inputFileName);
			ois = new ObjectInputStream(new FileInputStream(inputFileName));

			String outputFileName = "a" + inputFileName.substring(0, inputFileName.lastIndexOf('.')) + ".txt";
			writer = new BufferedWriter(new FileWriter(outputFileName));
			decode(ois, writer);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally { // closes streams
			Utility.closeStreams(ois, fis, writer);
		}
	}

	private void decode(ObjectInputStream ois, BufferedWriter writer) throws ClassNotFoundException, IOException {
		DataContainer dc = (DataContainer) ois.readObject();
		BitSet binaryEncoded = dc.getData();
		
		Node cNode = dc.getTree().getRoot(); // start at root of tree
		for (int i = 0; i < binaryEncoded.length(); i++) {

			//char cChar = littleE.charAt(ai); // read character
			boolean cBit = binaryEncoded.get(i);
			if (!cBit) { // if character is 0 go left
				cNode = cNode.left();
			} else if (cBit) { // if character is 1 go right
				cNode = cNode.right();
			}

			if (cNode.isLeaf()) { // if leaf has been reached
				writer.write(cNode.getCharacter()); // write the character
				cNode = dc.getTree().getRoot(); // start over from the root
			}
		}
	}
}
package huffman;

import java.io.*;

public class Decoder {

	private Tree tree;

	/**
	 * @param treeFileName
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public void decode(String inputFileName) throws FileNotFoundException {
		File inputFile = new File(inputFileName);

		if (inputFile.exists()) { // Makes sure both files exists before continuing
			initStreams(inputFileName);
		} else {
			throw new FileNotFoundException();
		}
	}

	/**
	 * @param treeFileName
	 * @param inputFileName
	 */
	private void initStreams(String inputFileName) { // creates stream pointers
		BufferedWriter writer = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(inputFileName);
			ois = new ObjectInputStream(new FileInputStream(inputFileName));
			//readHeader(fis);
			readHeader(ois);

			String outputFileName = "a" + inputFileName.substring(0, inputFileName.lastIndexOf('.')) + ".txt";
			writer = new BufferedWriter(new FileWriter(outputFileName));

			decode(fis, writer); // method call to decode text
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally { // closes streams
			Utility.closeStreams(ois, fis, writer);
		}
	}

	/**
	 * @param fis
	 * @param writer
	 * @throws IOException
	 */
	private void decode(FileInputStream fis, BufferedWriter writer) throws IOException {

		StringBuilder littleE = new StringBuilder();
		
		fis.skip(sizeof(this.tree));
		
		int fillerBits = fis.read();
		
		while (fis.available() > 0) { // while there are bytes to read
			int av = fis.available();
			byte[] bytes = null;

			if (av > 256) { // create byte array with size 256
				bytes = new byte[256];
			} else { // if there are less than 256 bytes left to read create byte array with smaller
						// size
				bytes = new byte[av]; // last byte is not character data
			}

			fis.read(bytes); // read bytes into byte array
			for (byte b : bytes) { // add binary representation of byte to string
				littleE.append(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
			}
		}
		System.out.println("filler bits" + fillerBits);
		Node cNode = this.tree.getRoot(); // start at root of tree
		for (int i = 0; i < littleE.length()/* - fillerBits*/; i++) {
			int ai = bigE(i);

			char cChar = littleE.charAt(ai); // read character

			if (cChar == '0') { // if character is 0 go left
				cNode = cNode.left();
			} else if (cChar == '1') { // if character is 1 go right
				cNode = cNode.right();
			}
			
			if (cNode.isLeaf()) { // if leaf has been reached
				writer.write(cNode.getCharacter()); // write the character
				cNode = this.tree.getRoot(); // start over from the root
			}
		}
	}
	
	private void readHeader(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		this.tree = (Tree) ois.readObject();
	}
	
	private static int sizeof(Object obj) throws IOException {

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);

        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
        objectOutputStream.close();

        return byteOutputStream.toByteArray().length;
    }
	
	private int bigE(int littleE) {
		int bigE = littleE / 8;
		bigE *= 8;
		bigE += 8 - 1;
		bigE -= littleE % 8;

		return bigE;
	}
}
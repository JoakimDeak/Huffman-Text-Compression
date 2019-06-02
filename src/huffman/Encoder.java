package huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Scanner;

public class Encoder {

	private File inputFile;

	/**
	 * @param fileName
	 * @throws FileNotFoundException
	 */
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
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File("output.bin"));
			tree = tm.makeTree(this.inputFile);
			writeHeader(tree, fos);
			encode(tree.getCharCodes(), fos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void writeHeader(Tree tree, FileOutputStream fos) throws IOException {
		// getting all characters of the tree in inorder and postorder
		ArrayList<Character> characters = tree.inorder();
		characters.addAll(tree.postorder());

		// writing the number of bytes that is tree data
		int numOfChars = characters.size();
		int bytesNeeded = numOfChars / 255 + 1; // numbers larger than 255 need mulitple bytes

		fos.write(bytesNeeded); // writing how many bytes are used to write the number of bytes of tree data

		for (int i = 0; i < bytesNeeded; i++) {
			if (numOfChars > 255) {
				fos.write(255);
				numOfChars -= 255;
			} else {
				fos.write(numOfChars);
			}
		}

		// writing tree data
		String treeData = "";
		for (Character c : characters) {
			treeData += c;
		}
		byte[] bytes = treeData.getBytes();
		fos.write(bytes);
	}

	/**
	 * writes the encoded text as binary to compress original text
	 * 
	 * @param charCodes
	 * @param fos
	 * @throws IOException
	 */
	private void encode(ArrayList<String> charCodes, FileOutputStream fos) throws IOException {
		Scanner sc = new Scanner(this.inputFile);
		StringBuilder encoded = new StringBuilder();
		int encodedLengthBeforeWrite = 256; // how many bits will be written at a time
		while (sc.hasNextLine()) { // go through every line
			String cLine = sc.nextLine();
			for (int i = 0; i < cLine.length(); i++) { // go through every character
				char cChar = cLine.charAt(i);
				for (String code : charCodes) { // find entry for current character in
					if (code.charAt(0) == cChar) {
						encoded.append(code.substring(1)); // add character code to stringbuilder
					}
				}
				if (encoded.length() >= encodedLengthBeforeWrite) {
					encoded = write(encodedLengthBeforeWrite, encoded, fos);
				}
			}
		}
		if (encoded.length() > 0) { // if number of characters wasnt multiple of the given number
			int bitsToBeAdded = 8 - encoded.length() % 8; // make it multiple of eight by adding extra 0s

			for (int i = 0; i < bitsToBeAdded; i++) {
				encoded.append('0');
			}
			write(encoded.length(), encoded, fos); // write the remaining characters
		}

		sc.close();
		fos.close();
	}

	/**
	 * @param charsToWrite
	 * @param input
	 * @param fos
	 * @return
	 * @throws IOException
	 */
	private StringBuilder write(int charsToWrite, StringBuilder input, FileOutputStream fos) throws IOException {

		BitSet set = new BitSet();

		for (int i = 0; i < charsToWrite; i++) { // takes string of 0s and 1s and converts to bitset
			if (input.charAt(i) == '1') {
				set.set(i);
			}
		}
		fos.write(set.toByteArray()); // writes the bitset to output file as byte array
		input.delete(0, charsToWrite); // deletes characters once they have been written

		return input;
	}
}

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
		NewTreeMaker tm = new NewTreeMaker();
		FileOutputStream fos = null;
		String outputName = inputFile.getName().substring(0, inputFile.getName().lastIndexOf('.')) + "-compressed" + ".bin";
		try {
			fos = new FileOutputStream(new File(outputName));
			tree = tm.makeTree(this.inputFile);
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

	/**
	 * writes the encoded text as binary to compress original text
	 * 
	 * @param charCodes
	 * @param fos
	 * @throws IOException
	 */
	private void encode(ArrayList<String> charCodes, FileOutputStream fos) throws IOException {
		
		writeHeader(charCodes, fos);
		
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

	public void writeHeader(ArrayList<String> charCodes, FileOutputStream fos) throws IOException {

		StringBuilder sb = new StringBuilder();

		for (String code : charCodes) {
			byte character = (byte) code.charAt(0);
			sb.append(String.format("%8s", Integer.toBinaryString(character & 0xFF)).replace(' ', '0'));
			byte numOfBits = (byte) (code.length() - 1);
			sb.append(String.format("%8s", Integer.toBinaryString(numOfBits & 0xFF)).replace(' ', '0'));
			sb.append(code.substring(1, code.length()));
		}
		
		int numOfBytes = sb.length() / 8;
		byte bytesToWriteNumber = (byte) (numOfBytes / 255 + 1);
		fos.write(bytesToWriteNumber);
		for(int i = 0; i < bytesToWriteNumber; i++) {
			if(numOfBytes > 255) {
				fos.write(255);
				numOfBytes -= 255;
			} else {
				fos.write(numOfBytes);
			}
		}

		write(sb.length(), sb, fos);
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

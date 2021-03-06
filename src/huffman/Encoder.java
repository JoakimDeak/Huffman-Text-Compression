package huffman;

import java.io.*;
import java.util.*;

public class Encoder {

	public void encode(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		if (file.exists()) { // makes sure a file with the given name exists
			encodeMain(file);
		} else {
			throw new FileNotFoundException();
		}

	}

	private void encodeMain(File inputFile) {
		Tree tree = null;
		TreeMaker tm = new TreeMaker();
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		Scanner sc = null;
		String outputName = inputFile.getName().substring(0, inputFile.getName().lastIndexOf('.')) + ".bin";
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(outputName)));
			fos = new FileOutputStream(new File(outputName), true);
			tree = tm.makeTree(inputFile);
			sc = new Scanner(inputFile);
			encode(tree.getCharCodes(), oos, sc, tree);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			Utility.closeStreams(oos, fos, sc);
		}
	}

	private void encode(HashMap<Character, String> charCodes, ObjectOutputStream oos, Scanner sc, Tree tree) throws IOException {
		StringBuilder encoded = new StringBuilder();
		while (sc.hasNextLine()) {
			String cLine = sc.nextLine() + "\n";
			for (int i = 0; i < cLine.length(); i++) {
				char cChar = cLine.charAt(i);
				encoded.append(charCodes.get(cChar));
			}
		}
		
		BitSet binaryEncoded = new BitSet();
		for (int i = 0; i < encoded.length(); i++) {
			if (encoded.charAt(i) == '1') {
				binaryEncoded.set(i);
			}
		}

		DataContainer dc = new DataContainer(tree, binaryEncoded, encoded.length());
		oos.writeObject(dc);
	}
}
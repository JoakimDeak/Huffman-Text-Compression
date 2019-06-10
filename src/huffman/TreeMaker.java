package huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Needs some big reworks
 */
public class TreeMaker {
	/**
	 * makes the list of all characters and their frequency
	 * 
	 * @param inputFile
	 * @return
	 * @throws FileNotFoundException
	 */
	private int[][] makeList(File inputFile) throws FileNotFoundException {

		int[][] array = new int[200][2];
		// gets the input from txt file
		Scanner sc = new Scanner(inputFile);
		
		String input = "";
		while (sc.hasNextLine()) {
			input = sc.nextLine();
			for (int i = 0; i < input.length(); i++) {
				char currentChar = input.charAt(i);

				int indexOfChar = indexOf(array, currentChar); // get index from list of chars
				if (indexOfChar != -1) { // if the char has an entry
					array[indexOfChar][1]++;
				} else { // create entry
					int indexOfNext = nextEmpty(array);
					array[indexOfNext][0] = (int) currentChar;
					array[indexOfNext][1] = 1;
				}

			}
		}

		sc.close();

		array = shorten(array); // reduces size of array to not have empty entries
		return array;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Encoder e = new Encoder("inputText.txt");
		e.encode();
	}

	/**
	 * @param array
	 * @param character
	 * @return
	 */
	private int indexOf(int[][] array, char character) {

		for (int i = 0; i < array.length; i++) {
			if (array[i][0] == (int) character) {
				return i; // index where characters entry is
			}
		}

		return -1; // character did not have an entry
	}

	/**
	 * finding the next empty entry
	 * 
	 * @param array
	 * @return
	 */
	private int nextEmpty(int[][] array) {

		for (int i = 0; i < array.length; i++) {
			if (array[i][0] == 0) {
				return i;
			}
		}

		return -1; // no empty entry was found, array size is too small
	}

	/**
	 * @param array
	 * @return
	 */
	private int[][] shorten(int[][] array) {
		// seeing how many non empty entries there are
		int i = array.length - 1;
		while (i > 0 && array[i][0] == 0) {
			i--;
		}
		// makes array of that size and copies all entries over to it
		int[][] shortArray = new int[i + 1][2];

		for (int j = 0; j < i + 1; j++) {
			shortArray[j][0] = array[j][0];
			shortArray[j][1] = array[j][1];
		}

		return shortArray;
	}

	/**
	 * @param array
	 * @return
	 */
	private int[][] sort(int[][] array) {
		// sorts the list accoring to frequency
		// using insertion is not a problem due to small array size
		for (int i = 0; i < array.length; i++) {
			int smallest = Integer.MAX_VALUE;
			int indexOfSmallest = -1;

			for (int j = i; j < array.length; j++) {
				if (array[j][1] < smallest) {
					smallest = array[j][1];
					indexOfSmallest = j;
				}
			}

			int[] temp = array[indexOfSmallest];
			array[indexOfSmallest] = array[i];
			array[i] = temp;

		}

		return array;
	}

	/**
	 * @param array
	 * @return
	 */
	public ArrayList<Node> makeExternalNodes(int[][] array) {

		ArrayList<Node> list = new ArrayList<Node>();

		for (int i = 0; i < array.length; i++) {
			list.add(new Node((char) array[i][0], array[i][1]));
		}
		return list;
	}

	/**
	 * @param inputFile
	 * @return
	 * @throws FileNotFoundException
	 */
	public Tree makeTreeFromList(ArrayList<Node> list) throws FileNotFoundException {

		while (list.size() > 1) {
			Node one = list.get(0);
			Node two = list.get(1);
			Node temp = new Node(one, two);
			list.remove(one);
			list.remove(two);

			boolean inserted = false;
			for (int i = 0; i < list.size(); i++) {
				if (inserted == false && list.get(i).getFrequency() > temp.getFrequency()) {
					list.add(i, temp);
					inserted = true;
				}
			}
			if (inserted == false) {
				list.add(list.size(), temp);
				inserted = true;
			}
		}
		Tree tree = new Tree(list.get(0)); // the only node left in list is root node
		return tree;
	}

	/**
	 * @param inputFile
	 * @return
	 * @throws FileNotFoundException
	 */
	public Tree makeTree(File inputFile) throws FileNotFoundException {

		int[][] array = makeList(inputFile);
		array = sort(array);
		ArrayList<Node> list = new ArrayList<Node>();
		list = makeExternalNodes(array);

		return makeTreeFromList(list);
	}

	public Tree treeFromCodes(ArrayList<String> codes) {
		return new Tree(treeFromCodes(codes, 0, new Node()));
	}

	private Node treeFromCodes(ArrayList<String> codes, int level, Node node) {

		// creating sublists for character codes
		ArrayList<String> left = new ArrayList<String>();
		ArrayList<String> right = new ArrayList<String>();

		// sorting character codes into sublists
		for (String code : codes) {
			if (code.charAt(level + 1) == '0') {
				left.add(code);
			} else {
				right.add(code);
			}
		}

		// create leaf node with character or create structure node and make recursive call
		if (left.size() == 1) {
			node.setLeft(new Node(left.get(0).charAt(0)));
		} else {
			node.setLeft(treeFromCodes(left, level + 1, new Node()));
		}

		if (right.size() == 1) {
			node.setRight(new Node(right.get(0).charAt(0)));
		} else {
			node.setRight(treeFromCodes(right, level + 1, new Node()));
		}

		return node;
	}
}

package huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Input {
	// makes the list of all characters and their frequency
	public static int[][] makeList() throws FileNotFoundException {

		int[][] array = new int[100][2];
		// gets the input from txt file
		Scanner sc = new Scanner(new File("inputText.txt"));

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

	public static int indexOf(int[][] array, char character) {

		for (int i = 0; i < array.length; i++) {
			if (array[i][0] == (int) character) {
				return i; // index where characters entry is
			}
		}

		return -1; // character did not have an entry
	}

	public static int nextEmpty(int[][] array) {
		// finding the next empty entry
		for (int i = 0; i < array.length; i++) {
			if (array[i][0] == 0) {
				return i;
			}
		}

		return -1; // no empty entry was found, array size is too small
	}

	public static int[][] shorten(int[][] array) {
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

	public static int[][] sort(int[][] array) {
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

	public static void main(String[] args) {

		int[][] array = null;
		try {
			array = makeList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		array = sort(array);

		for (int i = 0; i < array.length; i++) {
			System.out.print((char) array[i][0]);
			System.out.println(" " + array[i][1]);
		}

	}
}

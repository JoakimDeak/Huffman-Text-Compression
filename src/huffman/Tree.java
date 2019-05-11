package huffman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tree {
	private Node root;

	public Tree(Node root) {
		this.root = root;
	}

	public void print() {
		this.root.print();
	}

	public ArrayList<String> getCharCodes() { // generates text file with character codes used for encoding
		BufferedWriter writer = null;
		File file = null;
		try {
			file = new File("charCodesTemp.txt"); // allows the codes to be saved from the recursive method
			writer = new BufferedWriter(new FileWriter(file));
			this.root.getCharCodes(writer);
			writer.close();
			return getCharCodes(file);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		} finally { // makes sure the writer gets closed
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private ArrayList<String> getCharCodes(File file) throws FileNotFoundException {

		Scanner sc = new Scanner(file);
		ArrayList<String> codes = new ArrayList<String>();

		while (sc.hasNextLine()) { // saves every line from file to arraylist
			codes.add(sc.nextLine());
		}

		sc.close();
		file.delete(); // once all data has been read from file delete the file

		return codes;
	}

	public int size() { // finds number of nodes by adding all nodes to list

		ArrayList<Node> list = new ArrayList<Node>();

		list.add(this.root);

		int pointer = 0;
		while (pointer < list.size()) {
			Node current = list.get(pointer);
			if (current.right() != null) {
				list.add(current.right());
			}
			if (current.left() != null) {
				list.add(current.left());
			}
			pointer++;
		}

		return list.size();
	}
}

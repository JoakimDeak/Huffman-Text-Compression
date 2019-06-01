package huffman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Tree implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4087141033489848748L;
	private Node root;

	public Tree(Node root) {
		this.root = root;
	}

	public void print() {
		this.root.print();
	}

	public Node getRoot() {
		return this.root;
	}

	/**
	 * generates text file with character codes used for encoding
	 * 
	 * @return
	 */
	public ArrayList<String> getCharCodes() {
		BufferedWriter writer = null;
		File file = null;
		try {
			file = new File("charCodesTemp.txt"); // allows the codes to be saved from the recursive method
			writer = new BufferedWriter(new FileWriter(file));
			this.root.getCharCodes(writer);
			writer.close();
			return getCharCodes(file);
		} catch (IOException e) {
			e.printStackTrace();
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

	/**
	 * returns list of all leaf nodes
	 * 
	 * @return
	 */
	public ArrayList<Node> getLeaves() {

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

		ArrayList<Node> leaves = new ArrayList<Node>();

		for (Node n : list) {
			if (n.isLeaf()) {
				leaves.add(n);
			}
		}

		return leaves;
	}

	public void export() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("tree.data"));
			oos.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

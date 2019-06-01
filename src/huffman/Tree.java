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

	private class Index {
		public int index;
	}

	private Node buildUtil(char in[], char post[], int inStrt, int inEnd, Index pIndex) {
		// Base case
		if (inStrt > inEnd)
			return null;

		/*
		 * Pick current node from Postrder traversal using postIndex and decrement
		 * postIndex
		 */
		Node node = new Node(post[pIndex.index]);
		(pIndex.index)--;

		/* If this node has no children then return */
		if (inStrt == inEnd)
			return node;

		/*
		 * Else find the index of this node in Inorder traversal
		 */
		int iIndex = search(in, inStrt, inEnd, node.getCharacter());

		/*
		 * Using index in Inorder traversal, construct left and right subtress
		 */
		node.setRight(buildUtil(in, post, iIndex + 1, inEnd, pIndex));
		node.setLeft(buildUtil(in, post, inStrt, iIndex - 1, pIndex));

		return node;
	}

	// This function mainly initializes index of root
	// and calls buildUtil()
	public Node buildTree(char in[], char post[], int n) {
		Index pIndex = new Index();
		pIndex.index = n - 1;
		return buildUtil(in, post, 0, n - 1, pIndex);
	}

	/*
	 * Function to find index of value in arr[start...end] The function assumes that
	 * value is postsent in in[]
	 */
	private int search(char arr[], int strt, int end, char character) {
		int i;
		for (i = strt; i <= end; i++) {
			if (arr[i] == character)
				break;
		}
		return i;
	}
	
	public void setRoot(Node root) {
		this.root = root;
	}
	
	public static void main(String[] args) {
//		char[] in = {'a', '\u0000', 'b', '\u0000', 'c', '\u0000', '\u0000', 'd'};
//		char[] post = {'a', 'b', 'c', '\u0000', '\u0000', 'd', '\u0000', '\u0000'};
		char[] in = {'4', '8', '2', '5', '1', '6', '3', '7'};
		char[] post = {'8', '4', '5', '2', '6', '7', '3', '1'};
		Tree t = new Tree(null);
		t.setRoot(t.buildTree(in, post, in.length));
		t.print();
	}
}

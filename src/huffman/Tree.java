package huffman;

import java.io.Serializable;
import java.util.ArrayList;

public class Tree implements Serializable{

	private static final long serialVersionUID = -6116295339098230292L;
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
		ArrayList<String> charCodes = new ArrayList<String>();
		return this.root.getCharCode("", charCodes);
	}

	/**
	 * returns list of all leaf nodes
	 * 
	 * @return
	 */
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

	public void setRoot(Node root) {
		this.root = root;
	}
}
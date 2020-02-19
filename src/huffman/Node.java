package huffman;

import java.util.ArrayList;

public class Node implements Comparable<Node> {

	private char character;
	private Node left;
	private Node right;
	private int frequency;

	/** constructor for internal nodes */
	public Node(Node left, Node right) {
		this.left = left;
		this.right = right;
		this.frequency = left.getFrequency() + right.getFrequency();

	}

	/** constructor for external nodes / leaf nodes */
	public Node(char character, int frequency) {
		this.character = character;
		this.frequency = frequency;
	}

	public Node(char character) {
		this.character = character;
	}
	
	public Node() {
		
	}

	public Node right() {
		return this.right;
	}

	public Node left() {
		return this.left;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public int getFrequency() {
		return this.frequency;
	}

	public char getCharacter() {
		return this.character;
	}

	public boolean isLeaf() {
		if (this.character != '\u0000') { // if the node has a character it is a leaf node
			return true;
		} else {
			return false;
		}
	}

	public void print() {
		print("", true);
	}

	private void print(String prefix, boolean isTail) {
		System.out.println(prefix + (isTail ? "└── " : "├── ") + this.toString());
		if (this.right != null) {
			this.right.print(prefix + (isTail ? "    " : "│   "), false);
		}
		if (this.left != null) {
			this.left.print(prefix + (isTail ? "    " : "│   "), true);
		}
	}

	public String toString() {
		String output = "";
		output += "[";
		if (this.isLeaf()) {
			output += this.character;
			output += "|";
		}
		output += "f:" + this.frequency;
		output += "]";
		return output;
	}
	
	public ArrayList<String> getCharCode(String cCode, ArrayList<String> charCodes) {
		
		if(this.isLeaf()) {
			charCodes.add(this.getCharacter() + cCode);
			return charCodes;
		}
		
		if(this.left() != null) {
			charCodes = this.left().getCharCode(cCode + "0", charCodes);
		}
		if(this.right() != null) {
			charCodes = this.right().getCharCode(cCode + "1", charCodes);
		}
		
		return charCodes;
	}

	public int compareTo(Node node) {

		int node1 = this.frequency;
		int node2 = node.getFrequency();

		if (node1 > node2) {
			return -1;
		}
		if (node1 < node2) {
			return 1;
		}

		return 0;
	}
}

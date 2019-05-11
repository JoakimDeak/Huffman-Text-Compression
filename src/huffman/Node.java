package huffman;

import java.io.BufferedWriter;
import java.io.IOException;

public class Node {

	private char character;
	private Node left;
	private Node right;
	private int frequency;

	public Node(Node left, Node right) { // constructor for internal nodes
		this.left = left;
		this.right = right;
		this.frequency = left.getFrequency() + right.getFrequency();

	}

	public Node(char character, int frequency) { // constructor for external nodes / leaf nodes
		this.character = character;
		this.frequency = frequency;
	}

	public Node right() {
		return this.right;
	}

	public Node left() {
		return this.left;
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
	
	public void getCharCodes(BufferedWriter writer) throws IOException {
		getCharCodes(this, "", writer);
	}

	private String getCharCodes(Node node, String output, BufferedWriter writer) throws IOException {

		if (node.isLeaf()) {
			writer.write(node.character + output);
			writer.newLine();
		} else {
			if (node.left() != null) {
				getCharCodes(node.left, output + "0", writer);
			}
			if (node.right() != null) {
				getCharCodes(node.right, output + "1", writer);
			}
		}

		return output;

	}
}

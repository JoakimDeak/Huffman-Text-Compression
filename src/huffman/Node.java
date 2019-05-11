package huffman;

public class Node {

	private char character;
	private Node left;
	private Node right;
	private int frequency;

	public Node(Node left, Node right) {
		this.left = left;
		this.right = right;
		this.frequency = left.getFrequency() + right.getFrequency();

	}

	public Node(char character, int frequency) {
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
	
	public void getCharCodes() {
		getCharCodes(this, "");
	}

	private String getCharCodes(Node node, String output) {

		if (node.isLeaf()) {
			System.out.println(node.character + " : " + output);
		} else {
			if (node.left() != null) {
				getCharCodes(node.left, output + "0");
			}
			if (node.right() != null) {
				getCharCodes(node.right, output + "1");
			}
		}

		return output;

	}
}

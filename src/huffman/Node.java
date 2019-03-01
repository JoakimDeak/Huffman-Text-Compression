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
	
	public int getFrequency() {
		return this.frequency;
	}
	
	public char getCharacter() {
		return this.character;
	}
	
	public String toString() {
		String output = "";
		
		output += (this.left != null) ? this.right : "No left child";
		output += "\n";
		output += (this.right != null) ? this.right : "No right child";
		output += "\n";
		output += (this.character != '\u0000') ? this.character : "Internal Node";
		output += "\n";
		output += this.frequency;
		output += "\n";
		
		return output;
	}
}

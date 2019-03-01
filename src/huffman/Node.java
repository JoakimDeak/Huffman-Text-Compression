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
		
		output += "Left: " + this.left + "\n";
		output += "Right: " + this.right + "\n";
		output += "char: " + this.character + "\n";
		output += "Freq: " + this.frequency + "\n" + "\n";
		
		return output;
	}
}

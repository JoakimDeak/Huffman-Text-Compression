package huffman;

import java.util.ArrayList;

public class Tree {
	private Node root;

	public Tree(Node root) {
		this.root = root;
	}

	public void print() {
		this.root.print();
	}
	
	public void getCharCodes() {
		this.root.getCharCodes();
	}

	public int size() {

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

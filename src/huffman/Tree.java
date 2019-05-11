package huffman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Tree {
	private Node root;

	public Tree(Node root) {
		this.root = root;
	}

	public void print() {
		this.root.print();
	}
	
	public void getCharCodes() { // generates text file with character codes used for encoding
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("charCodes.txt")));
			this.root.getCharCodes(writer);
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
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

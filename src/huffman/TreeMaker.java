package huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TreeMaker {

	public Tree makeTree(File filename) throws FileNotFoundException {

		HashMap<Character, Integer> map = readFile(filename);
		ArrayList<Node> nList = makeNodeList(map);
		Tree t = makeTreeFromList(nList);

		return t;
	}

	private ArrayList<Node> makeNodeList(HashMap<Character, Integer> map) {
		ArrayList<Node> nList = new ArrayList<Node>();

		for (Map.Entry<Character, Integer> entry : map.entrySet()) {
			Node n = new Node(entry.getKey(), entry.getValue());
			nList.add(n);
		}
		return nList;
	}

	private HashMap<Character, Integer> readFile(File fileName) throws FileNotFoundException {

		String s;
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();

		Scanner sc = new Scanner(fileName);

		while (sc.hasNextLine()) {
			s = sc.nextLine()  + System.lineSeparator();

			for (int i = 0; i < s.length(); i++) {
				char cChar = s.charAt(i);
				if (map.containsKey(cChar)) {
					map.put(cChar, map.get(cChar) + 1);
				} else {
					map.put(s.charAt(i), 1);
				}
			}
		}
		sc.close();
		return map;
	}

	private Tree makeTreeFromList(ArrayList<Node> nList) {
		Collections.sort(nList, Collections.reverseOrder());
		int l = nList.size() - 1;
		while (l > 1) {
			Node tNode = new Node(nList.get(l), nList.get(l - 1));
			nList.remove(l);
			nList.remove(l - 1);

			insertNode(nList, tNode);

			l = nList.size() - 1;
		}
		Node root = new Node(nList.get(0), nList.get(1));
		Tree t = new Tree(root);
		return t;
	}

	// inserting element in list sorted in descending order
	private void insertNode(ArrayList<Node> list, Node node) {
		int nodeF = node.getFrequency();
		if (nodeF > list.get(0).getFrequency()) { // add at start of list
			list.add(0, node);
			return;
		}
		if (nodeF < list.get(list.size() - 1).getFrequency()) { // add at end of list
			list.add(node);
			return;
		}
		for (int i = 1; i < list.size(); i++) { // add somewhere in the middle
			if (list.get(i).getFrequency() < nodeF) {
				list.add(i, node);
				return;
			}
		}
	}

	public Tree treeFromCodes(ArrayList<String> codes) {
		return new Tree(treeFromCodes(codes, 0, new Node()));
	}

	private Node treeFromCodes(ArrayList<String> codes, int level, Node node) {

		// creating sublists for character codes
		ArrayList<String> left = new ArrayList<String>();
		ArrayList<String> right = new ArrayList<String>();

		// sorting character codes into sublists
		for (String code : codes) {
			if (code.charAt(level + 1) == '0') {
				left.add(code);
			} else {
				right.add(code);
			}
		}

		// create leaf node with character or create structure node and make recursive
		// call
		if (left.size() == 1) {
			node.setLeft(new Node(left.get(0).charAt(0)));
		} else {
			node.setLeft(treeFromCodes(left, level + 1, new Node()));
		}

		if (right.size() == 1) {
			node.setRight(new Node(right.get(0).charAt(0)));
		} else {
			node.setRight(treeFromCodes(right, level + 1, new Node()));
		}

		return node;
	}
}
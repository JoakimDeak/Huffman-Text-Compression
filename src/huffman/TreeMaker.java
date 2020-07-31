package huffman;

import java.io.*;
import java.util.*;

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
			s = sc.nextLine() + '\n';

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
		Node root;
		if (nList.size() == 1) {
			root = nList.get(0);
		} else {
			Collections.sort(nList, Collections.reverseOrder());
			int l = nList.size() - 1;
			while (l > 1) {
				Node tNode = new Node(nList.get(l), nList.get(l - 1));
				nList.remove(l);
				nList.remove(l - 1);

				insertNode(nList, tNode);

				l = nList.size() - 1;
			}
			root = new Node(nList.get(0), nList.get(1));
		}

		Tree t = new Tree(root);
		return t;
	}

	// inserting element in list sorted in descending order using binary search
	private void insertNode(ArrayList<Node> list, Node node) {
		int index = Collections.binarySearch(list, node);
		if (index < 0) { // in case no similar node was found
			index = Math.abs(index + 1);
		}
		index = list.size() - index; // adjusting for the descending order
		list.add(index, node);
	}

}
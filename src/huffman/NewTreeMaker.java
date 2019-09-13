package huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NewTreeMaker {

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
			s = sc.nextLine() /*+ '\n'*/;

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
		Collections.sort(nList);
		int l = nList.size() - 1;
		while (l > 1) {
			Node tNode = new Node(nList.get(l), nList.get(l - 1));
			nList.remove(l);
			nList.remove(l - 1);

			nList.add(tNode);
			Collections.sort(nList);

			l = nList.size() - 1;
		}
		Node root = new Node(nList.get(0), nList.get(1));
		Tree t = new Tree(root);
		return t;
	}
}

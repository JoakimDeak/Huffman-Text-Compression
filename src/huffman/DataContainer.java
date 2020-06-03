package huffman;

import java.io.Serializable;
import java.util.BitSet;

public class DataContainer implements Serializable {

	private static final long serialVersionUID = 9060272204644216068L;
	private Tree tree;
	private BitSet data;
	private int usedBits;

	public DataContainer(Tree tree, BitSet data, int usedBits) {
		this.tree = tree;
		this.data = data;
		this.usedBits = usedBits;
	}

	public Tree getTree() {
		return this.tree;
	}

	public BitSet getData() {
		return this.data;
	}
	
	public int getUsedBits(){
		return this.usedBits;
	}
}

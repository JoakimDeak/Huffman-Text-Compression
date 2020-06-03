package huffman;

import java.io.Serializable;
import java.util.BitSet;

public class DataContainer implements Serializable {

	private static final long serialVersionUID = 9060272204644216068L;
	private Tree tree;
	private int fillerBits;
	private BitSet data;
	private int usedBits;

	public DataContainer(Tree tree, int fillerBits, BitSet data, int usedBits) {
		this.tree = tree;
		this.fillerBits = fillerBits;
		this.data = data;
		this.usedBits = usedBits;
	}

	public Tree getTree() {
		return this.tree;
	}

	public int getFillerBits() {
		return this.fillerBits;
	}

	public BitSet getData() {
		return this.data;
	}
	
	public int getUsedBits(){
		return this.usedBits;
	}
}

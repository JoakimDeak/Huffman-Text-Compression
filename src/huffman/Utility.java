package huffman;

import java.io.*;

public class Utility {
	// closes a variable number of streams
	public static void closeStreams(Closeable... streams) {

		for (Closeable stream : streams) {
			try {
				stream.close();
			} catch (NullPointerException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	// converter for itterating bits in little endian as big endian
	public static int bigE(int littleE) {
		int bigE = littleE / 8;
		bigE *= 8;
		bigE += 8 - 1;
		bigE -= littleE % 8;

		return bigE;
	}
}

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
}

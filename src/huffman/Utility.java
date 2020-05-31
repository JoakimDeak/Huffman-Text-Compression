package huffman;

import java.io.Closeable;
import java.io.IOException;

public class Utility {

public static void closeStreams(Closeable ...streams) {
		
		for(Closeable stream : streams) {
			try {
				stream.close();
			} catch(NullPointerException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}

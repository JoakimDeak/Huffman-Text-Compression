package huffman;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
	// returns the byte size of a serialized object
	public static int sizeof(Object obj) throws IOException {

		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);

		objectOutputStream.writeObject(obj);
		objectOutputStream.flush();
		objectOutputStream.close();

		return byteOutputStream.toByteArray().length;
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

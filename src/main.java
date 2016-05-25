import java.io.File;
import java.io.IOException;


public class main {
	public static void main(String [] args) throws IOException {
		Huffman tree = new Huffman(new File(args[0]));
		tree.encodeFile();
	}
}

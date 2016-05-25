import java.io.File;
import java.util.HashMap;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FilePacker {
	private File file;
	private HashMap<Character, String> representations;

	public FilePacker(File file, HashMap<Character, String> representations) {
		this.file = file;
		this.representations = representations;
	}
	
	void pack() {
		ZipOutputStream zip;
	}
	
}

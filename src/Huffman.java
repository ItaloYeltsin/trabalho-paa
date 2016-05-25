import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class Huffman {
	
	private LinkedList<CharFrequence> frequences;
	CharFrequence root;
	HashMap<Character, String> representations;
	File file;
	
	Huffman(File file) throws IOException {
		this.file = file;
				
		FileReader reader = new FileReader(file);
		
		HashMap<Character, CharFrequence> charMap = new HashMap<Character, CharFrequence>();
		
		for (int i = 0; reader.ready(); i++) {
			Character c = (char) reader.read();
			CharFrequence aux = charMap.get(c);
			
			if(aux == null) {
				charMap.put(c, new CharFrequence(c, 1));
			} else {
				aux.setFrequence(aux.getFrequence()+1);
			}			
		}
		CharFrequence [] auxVector = new CharFrequence[charMap.size()];
		charMap.values().toArray(auxVector);
		
		buildOrderedLinkedList(auxVector);
		root = buildTree();
		buildRepresentations();
	}
	
	
	void buildOrderedLinkedList(CharFrequence [] auxVector) {
		
		frequences = new LinkedList<CharFrequence>();
		
		for (int i = 0; i < auxVector.length; i++) {
			insertOrdered(auxVector[i]);
		}
		System.out.println();
	} // buildOrderedLinkedList
	
	CharFrequence buildTree() {
		while (frequences.size() > 1) {
			
			CharFrequence c1 = frequences.get(0);
			CharFrequence c2 = frequences.get(1);
			
			frequences.remove(0);
			frequences.remove(0);
			
			insertOrdered(mergeCharFrequence(c1, c2));
			
		}
		return frequences.getFirst();
	}
	
	CharFrequence mergeCharFrequence(CharFrequence c1, CharFrequence c2) {
		
		CharFrequence result = new CharFrequence(null, c1.getFrequence()+c2.getFrequence());
		result.setLeft(c1);
		result.setRight(c2);
		return result;
		
	}
	
	void insertOrdered(CharFrequence c) {
		boolean wasInserted = false;
		
		if(frequences.size() == 0) {
			frequences.add(c);
			return;
		}
		
		for (ListIterator iterator = frequences.listIterator(); !wasInserted;) {
			CharFrequence charFrequence = (CharFrequence) iterator.next();
			
			if(c.getFrequence() < charFrequence.getFrequence()) {
				if(iterator.hasPrevious()) {
					iterator.previous();
					iterator.add(c);
				}else {
					frequences.addFirst(c);
				}
				wasInserted = true;
			}
			
			if(!iterator.hasNext()) {
				frequences.addLast(c);
				wasInserted = true;
			}
			
		}
	}
	
	void buildRepresentations() {
		if(root == null) {
			return;
		}
		
		representations = new HashMap<>();
		
		if(root.getLeft() == null && root.getRight() == null){
			representations.put(root.getValue(), "0");
			
		}else {
			visitor(root, "", representations);
		}
		
		
		
	}
	
	
	void visitor(CharFrequence cf, String code, HashMap<Character, String> hm) {
		if(cf == null) {
			return;
		}else if(cf.getValue() == null) {
			visitor(cf.getRight(), code+"0", hm);
			visitor(cf.getLeft(), code+"1", hm);
		}else {
			hm.put(cf.getValue(), code);
		}
	}
	
	File encodeFile() throws IOException {
		
		File temp = new File("temp.txt");
		FileOutputStream fos = new FileOutputStream(temp);
		FileReader fr = new FileReader(file);
		
		ArrayList bt = new ArrayList();
		String binary = "";
		while(fr.ready()) {
			char c = (char)fr.read();
			binary = c+"";			
		}
		short a = Short.parseShort(binary, 2);
		ByteBuffer bytes = ByteBuffer.allocate(2).putShort(a);

		byte[] array = bytes.array();
		
		fos.write(array);
		return temp;
	}
	
	HashMap<Character, String> getRepresentions() {
		return this.representations;
	}
}

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
	
	private LinkedList<CharFrequence> charFrequences;
	private HashMap<Character, CharFrequence> frequencies; 
	CharFrequence root;
	HashMap<Character, String> representations;
	File file;

	Huffman(File file) throws IOException {
		this.file = file;
				
		FileReader reader = new FileReader(file);
		frequencies = new HashMap<Character, CharFrequence>();
		
		for (int i = 0; reader.ready(); i++) {
			Character c = (char) reader.read();
			CharFrequence aux = frequencies.get(c);
			if(c == '\n') continue;
			if(aux == null) {
				frequencies.put(c, new CharFrequence(c, 1));
			} else {
				aux.setFrequence(aux.getFrequence()+1);
			}			
		}
		CharFrequence [] auxVector = new CharFrequence[frequencies.size()];
		frequencies.values().toArray(auxVector);
		
		buildOrderedLinkedList(auxVector);
		root = buildTree();
		buildRepresentations();
		System.out.println(representations);
	}
	
	
	void buildOrderedLinkedList(CharFrequence [] auxVector) {
		
		charFrequences = new LinkedList<CharFrequence>();
		
		for (int i = 0; i < auxVector.length; i++) {
			insertOrdered(auxVector[i]);
		}
		System.out.println();
	} // buildOrderedLinkedList
	
	CharFrequence buildTree() {
		while (charFrequences.size() > 1) {
			
			CharFrequence c1 = charFrequences.get(0);
			CharFrequence c2 = charFrequences.get(1);
			
			charFrequences.remove(0);
			charFrequences.remove(0);
			
			insertOrdered(mergeCharFrequence(c1, c2));
			
		}
		return charFrequences.getFirst();
	}
	
	CharFrequence mergeCharFrequence(CharFrequence c1, CharFrequence c2) {
		
		CharFrequence result = new CharFrequence(null, c1.getFrequence()+c2.getFrequence());
		result.setLeft(c1);
		result.setRight(c2);
		return result;
		
	}
	
	void insertOrdered(CharFrequence c) {
		boolean wasInserted = false;
		
		if(charFrequences.size() == 0) {
			charFrequences.add(c);
			return;
		}
		
		for (ListIterator iterator = charFrequences.listIterator(); !wasInserted;) {
			CharFrequence charFrequence = (CharFrequence) iterator.next();
			
			if(c.getFrequence() < charFrequence.getFrequence()) {
				if(iterator.hasPrevious()) {
					iterator.previous();
					iterator.add(c);
				}else {
					charFrequences.addFirst(c);
				}
				wasInserted = true;
			}
			
			if(!iterator.hasNext()) {
				charFrequences.addLast(c);
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
			visitor(cf.getRight(), code+"1", hm);
			visitor(cf.getLeft(), code+"0", hm);
		}else {
			hm.put(cf.getValue(), code);
		}
	}
	
	
	
	HashMap<Character, String> getRepresentions() {
		return this.representations;
	}
	
	public double BitsPerLetter() {
		double nOfBits = 0;
		double nOfLetters = 0;
		
		Object [] aux = frequencies.values().toArray();
		
		for (int i = 0; i < aux.length; i++) {
			CharFrequence cf = (CharFrequence)aux[i];
			nOfBits += ((String)representations.get(cf.value)).length()*cf.frequence;
			nOfLetters += cf.frequence;
		}
		
		return nOfBits/nOfLetters;
	}
	
	
}

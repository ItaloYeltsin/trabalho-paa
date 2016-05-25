import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;


public class Huffman {
	
	private LinkedList<CharFrequence> frequences;
	CharFrequence root;
	
	Huffman(File file) throws IOException {
		
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
		System.out.println(root.getFrequence());
	}
	
	void buildOrderedLinkedList(CharFrequence [] auxVector) {
		
		frequences = new LinkedList<CharFrequence>();
		
		for (int i = 1; i < auxVector.length; i++) {
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
		return frequences.get(0);
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
	
	
	
	
}

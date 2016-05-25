
public class CharFrequence {
	
	Character value;
	int frequence = 0;
	CharFrequence right, left;
	
	public CharFrequence(Character value, int frequence) {
		this.value = value;
		this.frequence = frequence;
	}

	public Character getValue() {
		return value;
	}

	public void setValue(Character value) {
		this.value = value;
	}

	public int getFrequence() {
		return frequence;
	}

	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

	public CharFrequence getRight() {
		return right;
	}

	public void setRight(CharFrequence right) {
		this.right = right;
	}

	public CharFrequence getLeft() {
		return left;
	}

	public void setLeft(CharFrequence left) {
		this.left = left;
	}
	
}

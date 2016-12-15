import java.util.Vector;

public class TrieNode extends TreeNode {

	String data;
	TrieNode nodes[];
	boolean ew;

	public TrieNode(UI ui) {
		// TODO Auto-generated constructor stub
		nodes = new TrieNode[26];
		ew = false;
		this.ui = ui;
		files = new LinkList();
	}

	public int charToInt(Character c) {

		int i = c.charValue();
		i = i % 26;
		return i;
	}

	public void add(String word, MyFile file, int i, int j) {

		if (i == word.length()) {
			data = word;

			if (!ew) {
				numberOfWords.value++;
			}
			ew = true;
			if (file != null) {

				files.add(new TreeFile(file, j));
				file.nodes.addElement(this);
			}

		}

		else {

			if (nodes[charToInt(word.charAt(i))] == null) {
				nodes[charToInt(word.charAt(i))] = new TrieNode(ui);
				numberOfNodes.value++;
			}

			nodes[charToInt(word.charAt(i))].add(word, file, i + 1, j);

		}

	}
	
	public TrieNode search(String word, int i) {

		if (ew && word.equals(data)) {
			return this;
		}

		else {

			if (word.length() == i) {
				return null;
			}
			if (nodes[charToInt(word.charAt(i))] != null) {

				return nodes[charToInt(word.charAt(i))].search(word, i + 1);
			}

			return null;
		}
	}

	@Override
	public void add(String word, MyFile file, int i) {

		if (word.length() != 0) {
			add(word, file, 0, i);

		}

	}

	@Override
	public void travel() {
		// TODO Auto-generated method stub
		if (ew) {
			ui.textArea.append(data + " -> ");
			for (int i = 0; i < files.size() - 1; i++) {
				MyFile mfile = files.elementAt(i).mfile;
				ui.textArea.append(mfile.file.getName() + ", ");

			}
			ui.textArea.append(files.lastElement().mfile.file.getName() + "\n");
		}

		for (int i = 0; i < 26; i++) {

			if (nodes[i] != null) {
				nodes[i].travel();

			}

		}

	}

	@Override
	public void deleteNode() {
		// TODO Auto-generated method stub
		ew = false;
		numberOfWords.value--;

	}

	@Override
	public TreeNode search(String word) {
		// TODO Auto-generated method stub
		return search(word, 0);
	}

	@Override
	public int hight() {
		// TODO Auto-generated method stub
		int max = 1;
		for (int i = 0; i < 26; i++) {
			if (nodes[i] != null) {

				int h = nodes[i].hight() + 1;
				if (h > max) {
					max = h;
				}
			}

		}

		return max;
	}

}

class Trie extends Tree{
	
	public Trie(UI ui) {
		// TODO Auto-generated constructor stub
		this.ui = ui;
		
	}

	@Override
	public void add(String word, MyFile file, int plc) {
		// TODO Auto-generated method stub
		if(root == null){
			root = new TrieNode(ui);
			root.numberOfNodes = new IntObj(0);
		}
		
		root.add(word, file, plc);
	}
	
}

import java.io.File;
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

	public void add(String word, MyFile mfile, int i, int plc) {

		if (i == word.length()) {
			
			if (!ew) {
				numberOfWords.value++;
				data = word;
				ew = true;
			}
			
			if (mfile != null && !files.doesContain(mfile.file)) {

				files.add(new TreeFile(mfile.file, plc));
				
				mfile.nodes.addElement(this);
				//file.nodes.addElement(this);
			}

		}

		else {
			int k = charToInt(word.charAt(i));

			if (nodes[k] == null) {
				nodes[k] = new TrieNode(ui);
				numberOfNodes.value++;
			}

			nodes[k].add(word, mfile, i + 1, plc);

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
	public void add(String word, MyFile mfile, int i) {

		if (word.length() != 0) {
			add(word, mfile, 0, i);

		}

	}

	@Override
	public void travel() {
		// TODO Auto-generated method stub
		if (ew) {
			ui.textArea.append(data + " -> ");
			for (int i = 0; i < files.size() - 1; i++) {
				File file = files.elementAt(i).file;
				ui.textArea.append(file.getName() + ", ");

			}
			ui.textArea.append(files.lastElement().file.getName() + "\n");
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
	public void add(String word, MyFile mfile, int plc) {
		// TODO Auto-generated method stub
		if(root == null){
			root = new TrieNode(ui);
			TreeNode.numberOfNodes = new IntObj(1);
		}
		
		root.add(word, mfile, plc);
	}
	
}

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

	public void add(String word, MyFile file, int i) {

		if (i == word.length()) {
			data = word;
			
			if(!ew){
				numberOfWords.value++;
			}
			ew = true;
			if (file != null) {

				files.add(file);
				file.nodes.addElement(this);
			}

		}
		
		else{
			
			if (nodes[charToInt(word.charAt(i))] == null) {
				nodes[charToInt(word.charAt(i))] = new TrieNode(ui);
			}

			nodes[charToInt(word.charAt(i))].add(word, file, i + 1);
			
		}

	}

	public TrieNode search(String word, int i) {

		if (ew && word.equals(data)) {
			return this;
		}

		else {
			if(nodes[charToInt(word.charAt(i))]!=null){
				return nodes[charToInt(word.charAt(i))].search(word, i + 1);
			}

			return null; 
		}
	}

	@Override
	public void add(String word, MyFile file) {
		
		if(word.length()!=0){
			add(word, file, 0);

		}

	}

	@Override
	public void travel() {
		// TODO Auto-generated method stub
		if (ew) {
			ui.textArea.append(data + " -> ");
			for (int i = 0; i < files.size() - 1; i++) {
				MyFile mfile = files.elementAt(i);
				ui.textArea.append(mfile.file.getName() + ", ");

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

}

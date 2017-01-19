import java.io.File;
import java.util.Vector;

public class Hash extends Tree {

	Bst arr[];
	int maxSize;
	static IntObj numberOfWords;

	public Hash(int maxSize, UI ui) {
		// TODO Auto-generated constructor stub
		this.maxSize = maxSize;
		arr = new Bst[maxSize];
		this.ui = ui;
		numberOfWords = new IntObj(0);
	}
	

	@Override
	public void add(String word, MyFile mfile, int plc) {
		
		if(word.length() == 0){
			return;
		}
		
		if (arr[hash(word)] == null) {
			arr[hash(word)] = new Bst(ui);
		}
		
		if(!arr[hash(word)].doesContain(word)){
			
			//System.out.println(word);
			numberOfWords.value++;
		}
		
		arr[hash(word)].add(word, mfile, plc);
	}

	public int hash(String word) {

		int hash = 7;
		for (int i = 0; i < word.length(); i++) {
			hash = hash * 31 + word.charAt(i);
			hash = hash %maxSize;
		}
		
		return hash % maxSize;
	}

	@Override
	public void travel() {

		for (int i = 0; i < maxSize; i++) {
			if (arr[i] != null) {

				arr[i].travel();
			}
		}
	}
	
	@Override
	public TreeNode search(String word) {
		
		//System.out.println(word);
		if(arr[hash(word)] == null){
			return null;
		}
		
		return arr[hash(word)].search(word);
	}

}

//class HashNode extends TreeNode {
//	Bst tree;
//	Hash hash;
//
//	public HashNode(UI ui, Hash hash) {
//		// TODO Auto-generated constructor stub
//		tree = new Bst(ui);
//		files = new LinkList();
//		this.ui = ui;
//		this.hash = hash;
//	}
//
//	@Override
//	public void add(String word, MyFile mfile, int plc) {
//		// TODO Auto-generated method stub
//		
//		tree.add(word, mfile, plc);
//	}
//
//	@Override
//	public void travel() {
//
//		if (tree != null) {
//
//			tree.travel();
//		}
//	}
//
//	@Override
//	public void deleteNode() {
//		
//	}
//
//	@Override
//	public TreeNode search(String word) {
//		// TODO Auto-generated method stub
//		return tree.search(word);
//	}
//
//	@Override
//	public int hight() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//}
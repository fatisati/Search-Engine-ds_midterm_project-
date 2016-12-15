import java.util.Vector;

public abstract class TreeNode {

	LinkList files;
	TreeNode stpWrd;// use in update and search methods
	
	public static IntObj numberOfNodes;

	public static IntObj numberOfWords;// each node knows how many words are in it's tree
	UI ui;

	public abstract void add(String word, MyFile file, int i);

	public abstract void travel();

	public abstract void deleteNode();

	public abstract TreeNode search(String word);

	public abstract int hight();
	
	/**
	 * 
	 * @param mfile
	 * @return the tfile in files which it's mfile equels to mfile
	 */
	public TreeFile retTfile(MyFile mfile) {

		LinkListNode itr;
		itr = files.first;

		while (itr != null) {

			if (itr.tfile.mfile == mfile) {

				return itr.tfile;
			}
			itr = itr.next;
		}

		return null;
	}

	
	/**
	 * 
	 * @param parts
	 * @return returns a linklist of files which contains all parts each file know exactly where each word appear in it
	 */
	

	

}

class TreeFile {

	MyFile mfile;
	int i;
	Vector<IntObj> plcs;//places

	public TreeFile(MyFile mfile, int i) {
		// TODO Auto-generated constructor stub
		this.mfile = mfile;
		this.i = i;

		plcs = new Vector<>();
		plcs.addElement(new IntObj(i));
	}

	public void printPlace(UI ui) {

		for (IntObj io : plcs) {

			int str, end;

			str = io.value - 2;
			end = io.value + 2;
			if (str < 0) {
				str = 0;
			}

			if (end > mfile.size()) {
				end = mfile.size();
			}

			ui.textArea.append("(..");

			for (int i = str; i < end; i++) {
				ui.textArea.append(mfile.elementAt(i) + " ");
			}

			ui.textArea.append("..)");
		}
		
		ui.textArea.append("\n");


	}

	public boolean eq(TreeFile tf) {

		if (tf.mfile.file.getName().equals(mfile.file.getName()) && tf.i == i) {
			return true;
		}
		return false;
	}
}

abstract class Tree{
	
	TreeNode root;
	UI ui;
	
	public abstract void add(String word, MyFile file, int plc);
	public TreeNode search(String word){

		if(root == null){
			return null;
		}
		
		return root.search(word);
	}
	
	public void travel(){
		root.travel();
	}
	
	public int hight() {
		return root.hight();
	}
	
	public TreeFile retTfile(MyFile mfile){
		return root.retTfile(mfile);
	}
	
	public void deleteFile(MyFile mfile) {

		for (TreeNode tn : mfile.nodes) {

			tn.files.del(mfile);

			if (tn.files.first == null) {

				tn.deleteNode();
			}
		}
	}

	public LinkList searchTerm(String parts[]) {

		LinkList ans = new LinkList();

		int str = 2;

		while (parts.length > str && SearchEngine.stopwords.doesContain(parts[str])) {

			str++;
		}

		if (parts.length > str && search(parts[str]) != null) {

			LinkListNode tmp = search(parts[str]).files.first;

			while (tmp != null) {
				ans.add(tmp.tfile);
				tmp = tmp.next;
			}

			for (int i = str + 1; i < parts.length; i++) {

				String word = parts[i];

				if (!SearchEngine.stopwords.doesContain(word)) {

					TreeNode srNode = search(word);

					if (srNode != null) {

						LinkListNode itr = ans.first;

						while (itr != null) {

							MyFile mfile = itr.tfile.mfile;

							TreeFile tf = srNode.retTfile(mfile);
							if (!srNode.files.doesContain(mfile)) {

								ans.del(mfile);

							}

							else {
								// ans.add(tf);

								LinkListNode ln = ans.haminin(tf);
								if (ln != null) {
									
									//System.out.println("hey");
									ln.tfile.plcs.addElement(new IntObj(tf.i));

								}

							}

							itr = itr.next;

						}

					}

					else {
						ans.first = null;
					}

				}

			}

		}

		return ans;

	}

	
	public void updateFile(MyFile mfile) {

		deleteFile(mfile);
		mfile.nodes = new Vector<>();
		String txt = SearchEngine.filetxt(mfile.file.getPath());

		String parts[] = txt.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
		for (int i = 0; i < parts.length; i++) {

			if (!SearchEngine.stopwords.doesContain(parts[i])) {

				parts[i] = parts[i].replaceAll("[^A-Za-z]", " ");
				String parts1[] = parts[i].replaceAll("(^\\s+|\\s+$)", "").split("\\s+");

				for (int j = 0; j < parts1.length; j++) {

					add(parts1[j], mfile, i);

				}
			}
		}

	}

	public boolean doesContain(String word) {
		
		if (search(word) != null) {
			return true;
		}
		return false;
	}
	
}


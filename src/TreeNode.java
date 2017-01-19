import java.io.File;
import java.util.Vector;


public abstract class TreeNode {

	LinkList files;
	TreeNode stpWrd;// use in update and search methods

	boolean isRoot;

	public static IntObj numberOfNodes;

	public static IntObj numberOfWords;// each node knows how many words are in
										// it's tree
	UI ui;

	public abstract void add(String word, MyFile mfile, int plc);

	public abstract void travel();

	public abstract void deleteNode();

	public abstract TreeNode search(String word);

	public abstract int hight();

	/**
	 * 
	 * @param mfile
	 * @return the tfile in files which it's mfile equels to mfile
	 */
	public TreeFile retTfileWithThisFile(File file) {

		LinkListNode itr;
		itr = files.first;

		while (itr != null) {

			if (itr.tfile.file == file) {

				return itr.tfile;
			}
			itr = itr.next;
		}

		return null;
	}

	/**
	 * 
	 * @param parts
	 * @return returns a linklist of files which contains all parts each file
	 *         know exactly where each word appear in it
	 */

}

class TreeFile {

	File file;
	int i;
	Vector<IntObj> plcs;// places

	public TreeFile(File file, int i) {
		// TODO Auto-generated constructor stub
		this.file = file;
		this.i = i;

		plcs = new Vector<>();
		plcs.addElement(new IntObj(i));
	}

	public void printPlace(UI ui) {

		String txt = SearchEngine.filetxt(file.getPath());
		String parts[] = txt.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");

		for (IntObj io : plcs) {

			int str, end;

			str = io.value - 2;
			end = io.value + 2;
			if (str < 0) {
				str = 0;
			}

			int maxi = parts.length;
			
			if (end > maxi) {
				end = maxi;
			}

			ui.textArea.append("(..");

			for (int i = str; i < end; i++) {
				ui.textArea.append(parts[i] + " ");
			}

			ui.textArea.append("..)");
		}

		ui.textArea.append("\n");

	}

//	public boolean eq(TreeFile tf) {
//
//		if (tf.mfile.file.getName().equals(mfile.file.getName()) && tf.i == i) {
//			return true;
//		}
//		return false;
//	}
}

abstract class Tree {

	TreeNode root;
	UI ui;

	public abstract void add(String word, MyFile mfile, int plc);

	public TreeNode search(String word) {

		if (root == null) {
			return null;
		}
		

		return root.search(word);
	}

	public void travel() {
		
		if(this instanceof Bst){
			
			if(root == null){
				
				//ui.textArea.append("number of words = 0\n");
			}
			
			else{
				
				root.travel();

			}
		}
		
		else{
			root.travel();
		}
	}

	public int hight() {
		
		if(root == null){
			return 0;
		}
		return root.hight();
	}

	public TreeFile retTfile(File file) {
		return root.retTfileWithThisFile(file);
	}

	public void deleteFile(MyFile mfile) {

		for (TreeNode tn : mfile.nodes) {

			tn.files.del(mfile.file);

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

							File file = itr.tfile.file;

							TreeFile tf = srNode.retTfileWithThisFile(file);
							
							if (tf == null) {

								ans.del(file);

							}

							else {

								LinkListNode node = ans.nodeWithFile(file);

								
								node.tfile.plcs.addElement(new IntObj(tf.i));

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

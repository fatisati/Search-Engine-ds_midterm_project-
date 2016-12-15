import java.util.Vector;

public class BstNode extends TreeNode {

	String data;
	BstNode lc, rc, father;
	boolean isleft;
	boolean isRoot;

	public BstNode(String data, BstNode father, boolean isleft, UI ui) {
		// TODO Auto-generated constructor stub
		this.data = data;
		files = new LinkList();
		this.father = father;
		this.isleft = isleft;
		this.ui = ui;
		isRoot = false;
	}

	@Override
	public void add(String word, MyFile file, int i) {
		// TODO Auto-generated method stub

		if (word.length() > 0) {

			if (word.compareTo(data) == 0) {

				if (file != null && !files.doesContain(file)) {

					files.add(new TreeFile(file, i));

					file.nodes.addElement(this);
				}

			}

			if (word.compareTo(data) > 0) {

				if (rc == null) {
					rc = new BstNode(word, this, false, ui);
					numberOfWords.value++;
				}
				rc.add(word, file, i);
			}

			if (word.compareTo(data) < 0) {

				if (lc == null) {
					lc = new BstNode(word, this, true, ui);
					numberOfWords.value++;
				}
				lc.add(word, file, i);
			}
		}
	}

	public void deleteNode() {

		if (rc == null && !isRoot) {

			if (lc == null) {

				if (isleft) {
					father.lc = null;
				} else {
					father.rc = null;
				}
			}

			else {

				if (isleft) {

					father.lc = lc;
					lc.father = father;

				} else {

					father.rc = lc;
					lc.father = father;
					lc.isleft = false;

				}
			}
			numberOfWords.value--;
		}

		else {

			if (lc == null && !isRoot) {

				if (isleft) {

					father.lc = rc;
					rc.father = father;
					rc.isleft = true;

				} else {

					father.rc = rc;
					rc.father = father;
					rc.isleft = false;
				}
				numberOfWords.value--;

			}

			else {

				BstNode m = rc.min();
				this.data = m.data;
				this.files = m.files;
				m.deleteNode();

			}
		}
	}

	public BstNode min() {

		if (lc == null) {
			return this;
		} else {
			return lc.min();
		}
	}

	@Override
	public TreeNode search(String word) {
		// TODO Auto-generated method stub
		if (word.compareTo(data) == 0) {
			return this;
		}

		if (word.compareTo(data) > 0) {
			if (rc == null) {
				return null;
			}
			return rc.search(word);
		}

		if (lc == null) {
			return null;
		}
		return lc.search(word);

	}

	@Override
	public void travel() {

		if (data != "-") {

			ui.textArea.append(data + " -> ");
			for (int i = 0; i < files.size() - 1; i++) {
				MyFile mfile = files.elementAt(i).mfile;

				ui.textArea.append(mfile.file.getName() + ", ");

				TreeFile tf = files.elementAt(i);
				// ui.textArea.append(".."+tf.mfile.elementAt(tf.i)+"..");

			}
			
			//System.out.println("."+data+".");
			ui.textArea.append(files.lastElement().mfile.file.getName() + "\n");

		}

		if (rc != null) {

			rc.travel();
		}

		if (lc != null) {

			lc.travel();
		}

	}

	@Override
	public int hight() {
		// TODO Auto-generated method stub
		int max = 1;
		int h;
		if (rc != null) {

			h = rc.hight() + 1;
			if (h > max) {
				max = h;
			}
		}

		if (lc != null) {

			h = lc.hight() + 1;
			if (h > max) {
				max = h;
			}
		}

		return max;
	}

}

class Bst extends Tree {
	
	public Bst(UI ui) {
		// TODO Auto-generated constructor stub
		this.ui = ui;
	}

	@Override
	public void add(String word, MyFile file, int plc) {
		// TODO Auto-generated method stub
		
		if(word.length()==0){
			return;
		}
		
		if (root == null) {
			
			
			root = new BstNode(word, null, false, ui);
			root.numberOfWords = new IntObj(1);
			((BstNode)root).isRoot = true;
		}
		

		root.add(word, file, plc);
	}

}

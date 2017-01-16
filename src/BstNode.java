import java.io.File;
import java.util.Vector;

public class BstNode extends TreeNode {

	String data;
	BstNode lc, rc, father;
	boolean isleft;

	public Bst tree;

	public BstNode(String data, BstNode father, boolean isleft, UI ui, Bst tree) {
		// TODO Auto-generated constructor stub
		this.data = data;
		files = new LinkList();
		this.father = father;
		this.isleft = isleft;
		this.ui = ui;
		isRoot = false;
		this.tree = tree;
	}

	@Override
	public void add(String word, MyFile mfile, int i) {
		// TODO Auto-generated method stub

		if (word.length() > 0) {

			if (word.compareTo(data) == 0) {

				if (mfile != null && !files.doesContain(mfile.file)) {

					files.add(new TreeFile(mfile.file, i));
					mfile.nodes.addElement(this);

				}

			}

			if (word.compareTo(data) > 0) {

				if (rc == null) {
					rc = new BstNode(word, this, false, ui, tree);

					rc.add(word, mfile, i);

					numberOfWords.value++;
					avl();
				}

				else {
					rc.add(word, mfile, i);
				}
			}

			if (word.compareTo(data) < 0) {

				if (lc == null) {
					lc = new BstNode(word, this, true, ui, tree);

					lc.add(word, mfile, i);
					numberOfWords.value++;
					avl();
				}

				else {
					lc.add(word, mfile, i);

				}
			}
		}
	}

	public void deleteNode() {

		// System.out.println(isRoot +" "+ data+" deleted");

		if (rc == null) {

			if (lc == null) {

				if (isRoot) {
					tree.root = null;

				}

				else {

					if (isleft) {
						father.lc = null;
					} else {
						father.rc = null;
					}

					father.avl();
				}

			}

			else {

				if (isRoot) {

					lc.avl();
					tree.root = lc;
					lc.isleft = false;
					lc.father = null;
					lc.isRoot = true;

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

					father.avl();
				}

			}
			numberOfWords.value--;
		}

		else {

			if (lc == null) {

				if (isRoot) {

					tree.root = rc;
					rc.isRoot = true;
					rc.father = null;
				}

				else {

					if (isleft) {

						father.lc = rc;
						rc.father = father;
						rc.isleft = true;

					} else {

						father.rc = rc;
						rc.father = father;
						rc.isleft = false;
					}

					father.avl();

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

		if (data != null && word.compareTo(data) == 0) {
			return this;
		}

		if (data == null || word.compareTo(data) > 0) {
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
				File file = files.elementAt(i).file;

				ui.textArea.append(file.getName() + ", ");

				// TreeFile tf = files.elementAt(i);
				// ui.textArea.append(".."+tf.mfile.elementAt(tf.i)+"..");

			}

			// System.out.println("."+data+".");
			ui.textArea.append(files.lastElement().file.getName() + "\n");

		}

		if (rc != null) {

			rc.travel();
		}

		if (lc != null) {

			lc.travel();
		}

	}

	void setLeftChild(BstNode lc) {

		this.lc = lc;
		if (lc == null) {
			return;
		}

		BstNode papa = lc.father;
		if (papa != null) {

			if (lc == papa.lc) {
				papa.lc = null;
			}

			else if (lc == papa.rc) {
				papa.rc = null;
			}
		}

		lc.father = this;
		lc.isleft = true;
	}

	void setRightChild(BstNode rc) {

		this.rc = rc;

		if (rc == null) {
			return;
		}

		BstNode papa = rc.father;

		if (papa != null) {

			if (rc == papa.lc) {
				papa.lc = null;
			}

			else if (rc == papa.rc) {
				papa.rc = null;
			}

		}

		rc.father = this;
		rc.isleft = false;
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

	public BstNode rotate(String type) {

		BstNode root = null;
		BstNode toBeAdded = null;

		if (type == "right") {
			root = lc;
			toBeAdded = root.rc;

			root.setRightChild(this);
			setLeftChild(toBeAdded);

		}

		else if (type == "left") {

			root = rc;
			toBeAdded = root.lc;

			root.setLeftChild(this);
			setRightChild(toBeAdded);
		}

		return root;
	}

	public void avl() {

		int leftHeight = 0;
		int rightHeight = 0;

		if (lc != null) {

			leftHeight = lc.hight();
		}

		if (rc != null) {
			rightHeight = rc.hight();
		}

		if (leftHeight > rightHeight + 1) {

			leftHeight = 0;
			rightHeight = 0;

			if (lc.lc != null) {
				leftHeight = lc.lc.hight();
			}

			if (lc.rc != null) {
				rightHeight = lc.rc.hight();
			}

			if (leftHeight < rightHeight) {

				// System.out.println("lr");

				setLeftChild(lc.rotate("left"));

			}

			if (isRoot) {

				isRoot = false;
				tree.root = rotate("right");
				tree.root.isRoot = true;
				((BstNode) tree.root).father = null;

			}

			else if (isleft) {

				father.setLeftChild(rotate("right"));
			}

			else {

				father.setRightChild(rotate("right"));
			}
		}

		else if (rightHeight > leftHeight + 1) {

			leftHeight = 0;
			rightHeight = 0;

			if (rc.lc != null) {
				leftHeight = rc.lc.hight();
			}

			if (rc.rc != null) {
				rightHeight = rc.rc.hight();
			}

			if (rightHeight < leftHeight) {

				setRightChild(rc.rotate("right"));

				// rotate("left");
				//System.out.println("rl");
				// System.out.println("left");

			}

			if (isRoot) {

				isRoot = false;
				tree.root = rotate("left");
				tree.root.isRoot = true;
				((BstNode) tree.root).father = null;
			}

			else if (isleft) {

				father.setLeftChild(rotate("left"));
			}

			else {

				father.setRightChild(rotate("left"));
			}
		}

		// System.out.println(data);
		if (father != null) {

			father.avl();

		}

	}

}

class Bst extends Tree {

	// BstNode rootFather;

	public Bst(UI ui) {
		// TODO Auto-generated constructor stub
		this.ui = ui;
		// rootFather = new BstNode(null, null, false, ui);
	}

	@Override
	public void add(String word, MyFile mfile, int plc) {
		// TODO Auto-generated method stub

		if (word.length() == 0) {
			return;
		}

		if (root == null) {

			root = new BstNode(word, null, false, ui, this);

			// BstNode.treeRoot = (BstNode) root;

			TreeNode.numberOfWords = new IntObj(1);
			((BstNode) root).isRoot = true;
		}

		root.add(word, mfile, plc);

	}

}

import java.io.File;

public class TstNode extends TreeNode {

	char data;
	TstNode lc, rc, eq;
	boolean ew;

	TstNode father;
	boolean isleft;
	Tst tree;

	public TstNode(char data, UI ui, TstNode father, Tst tree, boolean isLeft) {
		this.data = data;
		ew = false;
		files = new LinkList();
		this.ui = ui;
		isRoot = false;
		this.father = father;
		this.tree = tree;
		this.isleft = isLeft;
	}

	@Override
	public void add(String word, MyFile file, int plc) {
		// TODO Auto-generated method stub
		add(word, 0, file, plc);

	}

	public void add(String word, int i, MyFile mfile, int plc) {

		TstNode next = null;
		boolean end = true;

		if (word.length() == 0) {
			return;
		}

		end = Character.compare(word.charAt(i), data) == 0 && i == word.length() - 1;

		if (end) {
			if (ew == false) {

				numberOfWords.value++;
				ew = true;

			}
			if (mfile != null && !files.doesContain(mfile.file)) {

				files.add(new TreeFile(mfile.file, plc));
				mfile.nodes.addElement(this);
				//avl();
				return;
			}

		}

		else {
			if (Character.compare(word.charAt(i), data) == 0) {
				if (eq == null) {
					eq = new TstNode(word.charAt(i + 1), ui, this, tree, false);
					numberOfNodes.value++;
					
				}
				i = i + 1;
				next = eq;
				
			}

			else if (Character.compare(word.charAt(i), data) > 0) {
				if (rc == null) {
					rc = new TstNode(word.charAt(i), ui, this, tree, false);
					numberOfNodes.value++;
					
				}
				
				next = rc;
			}

			else if (Character.compare(word.charAt(i), data) < 0) {
				if (lc == null) {
					lc = new TstNode(word.charAt(i), ui, this, tree, true);
					lc.isleft = true;
					numberOfNodes.value++;
				
				}
				
				next = lc;

			}

			next.add(word, i, mfile, plc);
			avl();
		}

	}
	
	public void setFather(TstNode newFather){
		
		if(father.eq == this){
			father.eq = null;
		}
		
		if(father.lc == this){
			father.lc = null;
		}
		
		if(father.rc == this){
			father.rc = null;
		}
		
		father = newFather;
	}

	public void addLeft(TstNode node) {

		if (lc == null) {
			
			if(node!=null){
				
				node.setFather(this);
			}
			lc = node;
			lc.isleft = true;
		}

		else {
			lc.addLeft(node);
		}
	}

	public void addRight(TstNode node) {

		if (rc == null) {

			if(node!=null){
				
				node.setFather(this);
			}
			rc = node;
			rc.isleft = false;
		}

		else {
			rc.addRight(node);
		}
	}

	public void deleteNode() {

		this.ew = false;
		
		if (eq == null) {

			if (this == father.eq) {

				if (rc != null) {
					
					rc.addLeft(lc);
					father.eq = rc;
				}

				else if (lc != null) {

					father.eq = lc;
				}

				else {

					father.eq = null;

					if (!father.ew) {
						father.deleteNode();

					}

				}
			}

			else if (this == father.rc) {

				if (rc != null) {
					
					rc.addLeft(lc);

					father.rc = rc;
				}

				else {

					father.rc = lc;
				}

			}

			else if (this == father.lc) {

				if (lc != null) {
					
					lc.addRight(rc);
					father.lc = lc;
				}

				else {

					father.lc = rc;
				}
			}
		}
	}

	public TstNode search(String word) {
		if (word.length() == 0) {
			return null;
		}
		return search(word, 0);
	}

	public TstNode search(String word, int i) {

		if (Character.compare(word.charAt(i), data) == 0 && i == word.length() - 1 && ew == true) {
			return this;
		}

		else if (Character.compare(word.charAt(i), data) == 0) {
			if (eq == null) {
				return null;
			}

			if (i == word.length() - 1) {
				return null;
			}

			return eq.search(word, i + 1);
		}

		else if (Character.compare(word.charAt(i), data) > 0) {
			if (rc == null) {
				return null;
			}
			return rc.search(word, i);
		}

		else {
			if (lc == null) {
				return null;
			}
			return lc.search(word, i);
		}

	}

	public void travel() {

		StringBuilder sb = new StringBuilder();
		numberOfWords.value = 0;
		travel(sb);
	}

	public void travel(StringBuilder sb) {

		if (rc != null) {

			StringBuilder rsb = new StringBuilder(sb.toString());
			rc.travel(rsb);
		}
		if (lc != null) {
			StringBuilder lsb = new StringBuilder(sb.toString());
			lc.travel(lsb);
		}

		if (ew || eq != null) {
			sb.append(data);
		}

		if (ew == true) {
			numberOfWords.value++;
			ui.textArea.append(sb.toString() + " -> ");
			for (int i = 0; i < files.size() - 1; i++) {
				File file = files.elementAt(i).file;

				ui.textArea.append(file.getName() + ", ");

				// TreeFile tf = files.elementAt(i);
				// ui.textArea.append(".."+tf.mfile.elementAt(tf.i)+"..");

			}

			// System.out.println("."+data+".");
			ui.textArea.append(files.lastElement().file.getName() + "\n");
		}
		if (eq != null) {

			eq.travel(sb);
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

		if (eq != null) {

			h = eq.hight() + 1;
			if (h > max) {
				max = h;
			}
		}

		return max;
	}
	
	void setLeftChild(TstNode lc) {

		this.lc = lc;
		if (lc == null) {
			return;
		}

		TstNode papa = lc.father;
		if (papa != null) {

			if (lc == papa.lc) {
				papa.lc = null;
			}

			else if (lc == papa.rc) {
				papa.rc = null;
			}
			
			else if(lc == papa.eq){
				
				papa.eq = null;
			}
		}

		lc.father = this;
		lc.isleft = true;
	}

	void setRightChild(TstNode rc) {

		this.rc = rc;

		if (rc == null) {
			return;
		}

		TstNode papa = rc.father;

		if (papa != null) {

			if (rc == papa.lc) {
				papa.lc = null;
			}

			else if (rc == papa.rc) {
				papa.rc = null;
			}
			
			else if (rc == papa.eq){
				papa.eq = null;
			}

		}

		rc.father = this;
		rc.isleft = false;
	}
	
	public void setMiddleChild(TstNode node) {
		// TODO Auto-generated method stub
		
		this.eq = node;

		if (eq == null) {
			return;
		}

		TstNode papa = eq.father;

		if (papa != null) {

			if (eq == papa.lc) {
				papa.lc = null;
			}

			else if (eq == papa.rc) {
				papa.rc = null;
			}
			
			else if (eq == papa.eq){
				papa.eq = null;
			}
		}
		eq.father = this;
		eq.isleft = false;
	}
	
	public int lrHight(){
		
		int max = 1;
		int h;
		if (rc != null) {

			h = rc.lrHight() + 1;
			if (h > max) {
				max = h;
			}
		}

		if (lc != null) {

			h = lc.lrHight() + 1;
			if (h > max) {
				max = h;
			}
		}

		return max;
	}
	
	public TstNode rotate(String type) {

		TstNode root = null;
		TstNode toBeAdded = null;

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

			leftHeight = lc.lrHight();
		}

		if (rc != null) {
			rightHeight = rc.lrHight();
		}

		if (leftHeight > rightHeight + 1) {

			leftHeight = 0;
			rightHeight = 0;

			if (lc.lc != null) {
				leftHeight = lc.lc.lrHight();
			}

			if (lc.rc != null) {
				rightHeight = lc.rc.lrHight();
			}

			if (leftHeight < rightHeight) {

				setLeftChild(lc.rotate("left"));

			}

			if (isRoot) {

				isRoot = false;
				tree.root = rotate("right");
				tree.root.isRoot = true;
				((TstNode) tree.root).father = null;

			}

			else if (isleft) {

				father.setLeftChild(rotate("right"));
			}

			else if(father.eq == this){

				father.setMiddleChild(rotate("right"));
			}
			
			else{
				father.setRightChild(rotate("right"));
			}
		}

		else if (rightHeight > leftHeight + 1) {

			leftHeight = 0;
			rightHeight = 0;

			if (rc.lc != null) {
				leftHeight = rc.lc.lrHight();
			}

			if (rc.rc != null) {
				rightHeight = rc.rc.lrHight();
			}

			if (rightHeight < leftHeight) {

				setRightChild(rc.rotate("right"));

			}

			if (isRoot) {

				isRoot = false;
				tree.root = rotate("left");
				tree.root.isRoot = true;
				((TstNode) tree.root).father = null;
			}

			else if (isleft) {

				father.setLeftChild(rotate("left"));
			}

			else if(father.eq == this){

				father.setMiddleChild(rotate("left"));
			}
			
			else{
				father.setRightChild(rotate("left"));
			}
		}

		if (father != null) {

			father.avl();

		}

	}

}

class Tst extends Tree {

	// TstNode root;
	UI ui;

	public Tst(UI ui) {
		
		this.ui = ui;
	}

	public void add(String word, MyFile file, int plc) {
		//
		if (word.length() == 0) {
			return;
		}

		if (root == null) {
			//root = new TstNode(word.charAt(0), ui, null);
			
			root = new TstNode(word.charAt(0), ui, null, this, false);
			root.isRoot = true;

			//root.isRoot = true;
			TreeNode.numberOfNodes = new IntObj(1);
			// System.out.println(word);
		}

		root.add(word, file, plc);
	}

}

import java.io.File;

public class TstNode extends TreeNode {

	char data;
	TstNode lc, rc, eq;
	boolean ew;

	TstNode father;

	public TstNode(char data, UI ui) {
		this.data = data;
		ew = false;
		files = new LinkList();
		this.ui = ui;
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
				return;
			}

		}

		else {
			if (Character.compare(word.charAt(i), data) == 0) {
				if (eq == null) {
					eq = new TstNode(word.charAt(i + 1), ui);
					numberOfNodes.value++;
				}
				i = i + 1;
				next = eq;
			}

			else if (Character.compare(word.charAt(i), data) > 0) {
				if (rc == null) {
					rc = new TstNode(word.charAt(i), ui);
					numberOfNodes.value++;
				}
				next = rc;
			}

			else if (Character.compare(word.charAt(i), data) < 0) {
				if (lc == null) {
					lc = new TstNode(word.charAt(i), ui);
					numberOfNodes.value++;
				}
				next = lc;
			}

			next.add(word, i, mfile, plc);

		}

	}

	public void deleteNode() {
		this.ew = false;
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

}

class Tst extends Tree {

	// TstNode root;
	UI ui;

	public Tst(UI ui) {
		// TODO Auto-generated constructor stub
		this.ui = ui;
	}

	public void add(String word, MyFile file, int plc) {
		//
		if (word.length() == 0) {
			return;
		}

		if (root == null) {
			root = new TstNode(word.charAt(0), ui);
			TreeNode.numberOfNodes = new IntObj(1);
			// System.out.println(word);
		}

		root.add(word, file, plc);
	}

}

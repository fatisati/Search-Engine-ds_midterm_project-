import java.io.File;
import java.util.Iterator;
import java.util.Vector;

public class TstNode extends TreeNode {

	char data;
	TstNode lc, rc, eq;
	boolean ew;
	
	public TstNode(char data, UI ui) {
		this.data = data;
		ew = false;
		files = new Vector<>();
		this.ui = ui;
	}

	@Override
	public void add(String word, MyFile file) {
		// TODO Auto-generated method stub
		add(word, 0, file);

	}

	public void add(String word, int i, MyFile file) {

		TstNode next = null;
		boolean end = true;
		if (word.length() != 0) {
			end = Character.compare(word.charAt(i), data) == 0 && i == word.length() - 1;
		}

		if (end && word.length() != 0) {
			if (ew == false) {
				numberOfWords.value ++;
			}
			ew = true;
			if (file != null && !files.contains(file)) {

				files.addElement(file);
				file.nodes.addElement(this);
			}

		}

		if (!end) {
			if (Character.compare(word.charAt(i), data) == 0) {
				if (eq == null) {
					eq = new TstNode(word.charAt(i + 1),  ui);
				}
				i = i + 1;
				next = eq;
			}

			else if (Character.compare(word.charAt(i), data) > 0) {
				if (rc == null) {
					rc = new TstNode(word.charAt(i),  ui);
				}
				next = rc;
			}

			else if (Character.compare(word.charAt(i), data) < 0) {
				if (lc == null) {
					lc = new TstNode(word.charAt(i),  ui);
				}
				next = lc;
			}

			next.add(word, i, file);

		}

	}

//	public void deleteFile(MyFile mfile) {
//
//		for (TreeNode tn : mfile.nodes) {
//
//			TstNode d = (TstNode) tn;
//			SearchEngine.removeFromVec(d.files, mfile);
//
//			if (d.files.isEmpty()) {
//				d.ew = false;
//			}
//		}
//	}
	
	public void deleteNode(){
		this.ew = false;
	}
//
//	public void updateFile(MyFile mfile) {
//
//		deleteFile(mfile);
//		mfile.nodes = new Vector<>();
//		String txt = SearchEngine.filetxt(mfile.file.getPath());
//
//		String parts[] = txt.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
//		for (int i = 0; i < parts.length; i++) {
//
//			if (!stpWrd.doesContain(parts[i], 0)) {
//
//				add(parts[i], 0, mfile);
//			}
//		}
//
//	}
	public TstNode search(String word){
		if(word.length()==0){
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

//	public Vector<MyFile> searchTerm(String parts[]) {

//		Vector<MyFile> ans = new Vector<>();
//
//		int str = 2;
//
//		while (parts.length > str && stpWrd.doesContain(parts[str], 0)) {
//
//			str++;
//		}
//
//		if (parts.length > str && search(parts[str], 0) != null) {
//
//			Vector<MyFile> tmp = search(parts[str], 0).files;
//
//			for (MyFile mfile : tmp) {
//				ans.addElement(mfile);
//			}
//
//			for (int i = str + 1; i < parts.length; i++) {
//
//				String word = parts[i];
//
//				if (!stpWrd.doesContain(word, 0)) {
//
//					TstNode srNode = search(word, 0);
//
//					if (srNode != null) {
//
//						for (MyFile mfile : ans) {
//
//							if (!srNode.files.contains(mfile)) {
//
//								SearchEngine.removeFromVec(ans, mfile);
//								break;
//
//							}
//
//						}
//
//					}
//
//				}
//
//			}
//
//		}
//
//		return ans;
//
//	}

//	public boolean doesContain(String word, int i) {
//
//		// System.out.println(data+"."+i);
//
//		if (word.length() == 0) {
//			return true;
//		}
//
//		if (Character.compare(word.charAt(i), data) == 0 && i == word.length() - 1 && ew == true) {
//			return true;
//		}
//
//		else if (Character.compare(word.charAt(i), data) == 0) {
//			if (eq == null) {
//				return false;
//			}
//
//			if (i == word.length() - 1) {
//				return false;
//			}
//			return eq.doesContain(word, i + 1);
//		}
//
//		else if (Character.compare(word.charAt(i), data) > 0) {
//			if (rc == null) {
//				return false;
//			}
//			return rc.doesContain(word, i);
//		}
//
//		else {
//			if (lc == null) {
//				return false;
//			}
//			return lc.doesContain(word, i);
//		}
//		
//		
//	}
	
	public void travel(){
		
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
				MyFile mfile = files.elementAt(i);
				ui.textArea.append(mfile.file.getName() + ", ");

			}
			ui.textArea.append(files.lastElement().file.getName() + "\n");
		}
		if (eq != null) {

			eq.travel(sb);
		}

	}

}

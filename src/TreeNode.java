import java.util.Vector;

public abstract class TreeNode {

	Vector<MyFile> files;
	TreeNode stpWrd;
	public static IntObj numberOfWords;
	UI ui;

	public abstract void add(String word, MyFile file);
	
	public abstract void travel();

	public abstract void deleteNode();
	
	public abstract TreeNode search(String word);

	public void deleteFile(MyFile mfile) {
		for (TreeNode tn : mfile.nodes) {

			SearchEngine.removeFromVec(tn.files, mfile);

			if (tn.files.size()==0) {
				tn.deleteNode();
			}
		}
	}

	public void updateFile(MyFile mfile) {
		deleteFile(mfile);
		mfile.nodes = new Vector<>();
		String txt = SearchEngine.filetxt(mfile.file.getPath());

		String parts[] = txt.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
		for (int i = 0; i < parts.length; i++) {

			if (!stpWrd.doesContain(parts[i])) {

				add(parts[i], mfile);
			}
		}
	}

	public Vector<MyFile> searchTerm(String parts[]){
				
		Vector<MyFile> ans = new Vector<>();

		int str = 2;

		while (parts.length > str && stpWrd.doesContain(parts[str])) {

			str++;
		}
		
		if (parts.length > str && search(parts[str]) != null) {

			Vector<MyFile> tmp = search(parts[str]).files;

			for (MyFile mfile : tmp) {
				ans.addElement(mfile);
			}

			for (int i = str + 1; i < parts.length; i++) {

				String word = parts[i];
				
				if (!stpWrd.doesContain(word)) {

					TreeNode srNode = search(word);

					if (srNode != null) {

						for (MyFile mfile : ans) {

							if (!srNode.files.contains(mfile)) {

								SearchEngine.removeFromVec(ans, mfile);
								break;

							}

						}

					}

				}

			}

		}

		return ans;

	}

	public boolean doesContain(String word){
		if(search(word)!=null){
			return true;
		}
		return false;
	}


}

import java.util.Vector;

public abstract class TreeNode {

	//Vector<MyFile> files;
	LinkList files;
	TreeNode stpWrd;
	public static IntObj numberOfWords;
	UI ui;

	public abstract void add(String word, MyFile file);
	
	public abstract void travel();

	public abstract void deleteNode();
	
	public abstract TreeNode search(String word);
	
	public abstract int hight();
	
	public void deleteFile(MyFile mfile) {

		for (TreeNode tn : mfile.nodes) {

			tn.files.del(mfile);

			if (tn.files.first == null) {
				
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

	public LinkList searchTerm(String parts[]){
				
		LinkList ans = new LinkList();

		int str = 2;

		while (parts.length > str && stpWrd.doesContain(parts[str])) {

			str++;
		}
		
		if (parts.length > str && search(parts[str]) != null) {

			LinkListNode tmp = search(parts[str]).files.first;

//			for (MyFile mfile : tmp) {
//				ans.addElement(mfile);
//			}
			
			while(tmp != null){
				 ans.add(tmp.mfile);
				 tmp = tmp.next;
			}

			for (int i = str + 1; i < parts.length; i++) {

				String word = parts[i];
				
				if (!stpWrd.doesContain(word)) {

					TreeNode srNode = search(word);

					if (srNode != null) {

//						for (MyFile mfile : ans) {
//
//							if (!srNode.files.doesContain(mfile)) {
//
//								//SearchEngine.removeFromVec(ans, mfile);
//								break;
//
//							}
//
//						}
						
						LinkListNode itr = ans.first;
						while(itr != null){
							
							MyFile mfile = itr.mfile;
							if (!srNode.files.doesContain(mfile)) {

								//SearchEngine.removeFromVec(ans, mfile);
								ans.del(mfile);
								break;

							}
							itr = itr.next;
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

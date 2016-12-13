import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


public class SearchEngine {

	// TstNode tst;
	TreeNode tree;
	TreeNode stopwords;

	int indexFiled;
	// IntObj words;
	UI ui;
	String type;

	public SearchEngine(Vector<MyFile> files, UI ui, String type) {
		// TODO Auto-generated constructor stub
		this.type = type;
		TreeNode.numberOfWords = new IntObj(0);
		this.ui = ui;
		String txt = null;
		indexFiled = files.size();

		String stpWordsTxt = filetxt("./StopWords.txt");
		stopwords = stpWords(stpWordsTxt);

		int str = 0;

		txt = filetxt(files.elementAt(0).file.getPath());
		String p0[] = txt.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");

		while (p0.length > str && stopwords.doesContain(p0[str])) {

			str++;
		}
		
		long startTime = System.currentTimeMillis();
		System.gc();
		long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

		if (p0.length > str) {

			if (type.equals("tst")) {

				tree = new TstNode(p0[str].charAt(0), ui);
			}

			if (type.equals("bst")) {
				tree = new BstNode("-", null, false, ui);
			}

			if (type.equals("trie")) {
				tree = new TrieNode(ui);
			}

			TreeNode.numberOfWords.value = 0;

			tree.stpWrd = stopwords;

			for (MyFile mfile : files) {

				txt = filetxt(mfile.file.getPath());
				// String[] parts = txt.split("[\\r\\n|\\s]+");
				String parts[] = txt.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");

				for (int j = 0; j < parts.length; j++) {

					// System.out.println("."+parts[j]+".");
					if (!stopwords.doesContain(parts[j])) {
						tree.add(parts[j], mfile);
					}

				}
			}
		}
		System.gc();
		long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		
		ui.textArea.append(type + " tree has been created:)\n");
		ui.textArea.append("number of indexed files: " + indexFiled + "\n");
		ui.textArea.append("number of words in tree: " + TreeNode.numberOfWords.value + "\n");
		ui.textArea.append("tree height: " + tree.hight()+ "\n");
		ui.textArea.append("time used: " + totalTime+ " millisecond\n");
		ui.textArea.append(memoryAfter - memoryBefore+" memory usage\n\n");
	}

	public void add(MyFile file) {

		String txt = filetxt(file.file.getPath());
		String[] parts = txt.split("[\\r\\n|\\s]+");

		if (tree == null) {
			tree = new TstNode(parts[0].charAt(0), ui);
		}

		for (int j = 0; j < parts.length; j++) {

			if (!stopwords.doesContain(parts[j])) {

				tree.add(parts[j], file);
			}
		}

	}

	public TreeNode stpWords(String txt) {

		TreeNode ans;
		if (type.equals("tst")) {

			ans = new TstNode('t', ui);
		} else {
			ans = new BstNode("-", null, false, ui);
		}

		// txt = filetxt(mfile.file.getPath());
		// String[] parts = txt.split("[\\r\\n|\\s]+");
		String parts[] = txt.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");

		for (int j = 0; j < parts.length; j++) {
			ans.add(parts[j], null);
		}

		return ans;

	}

	public static String filetxt(String path) {
		BufferedReader br = null;
		String everything = null;
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
			everything = everything.replaceAll("[^A-Za-z]", " ");
			everything = everything.toLowerCase();
			return everything;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return everything;

	}

	// public static void removeFromVec(Vector<MyFile>inp, MyFile mfile){
	//
	// Iterator<MyFile> itr = inp.iterator();
	// while (itr.hasNext()) {
	//
	// MyFile mf = itr.next();
	// if (mf.equals(mfile)) {
	// itr.remove();
	// break;
	// }
	// }
	//
	// }

}

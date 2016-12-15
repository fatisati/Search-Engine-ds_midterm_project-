import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class SearchEngine {

	// TstNode tst;
	Tree tree;
	static Tree stopwords;

	int indexFiled;
	// IntObj words;
	UI ui;
	String type;

	public SearchEngine(Vector<MyFile> files, UI ui, String type) {
		// TODO Auto-generated constructor stub
		this.type = type;
		TreeNode.numberOfWords = new IntObj(0);
		this.ui = ui;
		indexFiled = files.size();

		String stpWordsTxt = filetxt("./StopWords.txt");
		stopwords = stpWords(stpWordsTxt);
		
		//System.out.println(stopwords.doesContain("about"));
//		int str = 0;
//
//		txt = filetxt(files.elementAt(0).file.getPath());
//		String p0[] = txt.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
//
//		while (p0.length > str && stopwords.doesContain(p0[str])) {
//
//			str++;
//		}

		long startTime = System.currentTimeMillis();
		System.gc();
		long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

		TreeNode.numberOfWords.value = 0;

		if (type.equals("tst")) {

			//tree = new TstNode(p0[str].charAt(0), ui);
			tree = new Tst(ui);
		}

		if (type.equals("bst")) {
			tree = new Bst(ui);
		}

		if (type.equals("trie")) {
			tree = new Trie(ui);
		}
		
		readFiles(files);
		
		//System.out.println(TreeNode.numberOfNodes.value);
		
		System.gc();
		long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;

		ui.textArea.append("\n" + type + " tree has been created:)\n");
		ui.textArea.append("number of indexed files: " + indexFiled + "\n");
		ui.textArea.append("number of words in tree: " + TreeNode.numberOfWords.value + "\n");
		ui.textArea.append("tree height: " + tree.hight() + "\n");
		ui.textArea.append("time used: " + totalTime + " millisecond\n");
		ui.textArea.append(memoryAfter - memoryBefore + " memory usage\n\n");
	}

	public void add(MyFile mfile) {

		String txt = filetxt(mfile.file.getPath());
		// String[] parts = txt.split("[\\r\\n|\\s]+");
		String parts[] = txt.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");

		for (int j = 0; j < parts.length; j++) {

			parts[j] = parts[j].replaceAll("[^A-Za-z]", " ");
			String parts1[] = parts[j].replaceAll("(^\\s+|\\s+$)", "").split("\\s+");

			for (int k = 0; k < parts1.length; k++) {

				if (!stopwords.doesContain(parts1[k])) {
					tree.add(parts1[k], mfile, j);
				}
			}

		}

	}

	public Tree stpWords(String txt) {

		Tree ans;
		if (type.equals("tst")) {

			ans = new Tst(ui);
		}

		else if (type.equals("bst")) {
			ans = new Bst(ui);
		}

		else {

			ans = new Trie(ui);
		}

		// txt = filetxt(mfile.file.getPath());
		// String[] parts = txt.split("[\\r\\n|\\s]+");
		String parts[] = txt.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");

		for (int j = 0; j < parts.length; j++) {
			//System.out.println(parts[j]);
			ans.add(parts[j], null, j);
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
			// everything = everything.replaceAll("[^A-Za-z]", " ");
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

	public void readFiles(Vector<MyFile> mfiles) {

		BufferedReader br = null;
		// String everything = null;
		for (MyFile mfile : mfiles) {
			
			//System.out.println(mfile.file.getName());
			try {
				br = new BufferedReader(new FileReader(mfile.file.getPath()));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {

				String line = br.readLine();
				int cnt = 0;
				while (line != null) {

					String parts[] = line.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");

					for (int j = 0; j < parts.length; j++) {
						parts[j] = parts[j].toLowerCase();

						parts[j] = parts[j].replaceAll("[^A-Za-z]", " ");
						String parts1[] = parts[j].replaceAll("(^\\s+|\\s+$)", "").split("\\s+");

						for (int k = 0; k < parts1.length; k++) {

							if (!stopwords.doesContain(parts1[k])) {
								
								
								tree.add(parts1[k], mfile, cnt);
							}
						}

						cnt++;
					}

					line = br.readLine();
				}

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

		}

	}

	

}

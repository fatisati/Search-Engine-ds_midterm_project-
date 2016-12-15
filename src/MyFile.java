import java.io.File;
import java.util.Vector;

public class MyFile {
	File file;
	Vector<TreeNode> nodes;

	public MyFile(File file) {

		this.file = file;
		nodes = new Vector<>();
	}

	public String elementAt(int i) {

		String txt = SearchEngine.filetxt(file.getPath());
		String parts[] = txt.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
		if (i < parts.length) {
			return parts[i];
		}
		return null;
	}
	
	public int size(){
		
		String txt = SearchEngine.filetxt(file.getPath());
		String parts[] = txt.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
		return parts.length;
	}

}

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class UI extends JFrame implements ActionListener, KeyListener {

	JLabel uppertext;
	JTextField textf;
	JButton brows;
	JTextArea textArea;
	JLabel plz;
	JRadioButton tst, bst, trie;
	JTextField search;
	JButton build, reset, help, exit;
	JScrollPane scroll;

	int border;
	int scrx, scry;
	Font font;

	Vector<MyFile> mfiles;
	//Vector<File> files;

	SearchEngine sr;
	String command;
	JFileChooser chooser;
	File fi[];
	Vector<String> commands;
	int cnt;// counter for command- show where we are in commands vector

	public UI() {
		//files = new Vector<>();
		mfiles = new Vector<>();

		commands = new Vector<>();
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		border = 50;
		scrx = 1000;
		scry = 1000;
		font = new Font("Serif", Font.PLAIN, 30);

		uppertext = new JLabel("please enter folder address or use brows button");
		uppertext.setSize(scrx, 100);
		uppertext.setLocation(border, 0);
		uppertext.setFont(font);

		textf = new JTextField();
		textf.setSize(600, 50);
		textf.setFont(font);
		textf.setLocation(border, 100);

		brows = new JButton("brows");
		brows.setSize(200, 50);
		brows.setLocation(600 + 2 * border, 100);
		brows.setFont(font);
		brows.addActionListener((ActionListener) this);

		textArea = new JTextArea();
		textArea.setSize(850, 450);
		textArea.setLocation(border, 200);
		textArea.setFont(font);
		textArea.setEditable(false);

		scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setSize(850, 450);
		scroll.setLocation(border, 200);

		plz = new JLabel("plz enter your command");
		plz.setSize(500, 100);
		plz.setLocation(border, 650);
		plz.setFont(font);

		tst = new JRadioButton("TST");
		tst.setLocation(500, 650);
		tst.setSize(100, 100);
		tst.setFont(font);

		bst = new JRadioButton("BST");
		bst.setSize(100, 100);
		bst.setLocation(600, 650);
		bst.setFont(font);

		trie = new JRadioButton("Trie");
		trie.setLocation(700, 650);
		trie.setSize(100, 100);
		trie.setFont(font);

		ButtonGroup group = new ButtonGroup();
		group.add(bst);
		group.add(tst);
		group.add(trie);

		search = new JTextField();
		search.setSize(850, 50);
		search.setLocation(border, 750);
		search.setFont(font);
		search.addActionListener((ActionListener) this);
		search.addKeyListener((KeyListener) this);

		build = new JButton("Build");
		build.setSize(200, 50);
		build.setLocation(border, 850);
		build.setFont(font);
		build.addActionListener((ActionListener) this);

		reset = new JButton("Reset");
		reset.setSize(200, 50);
		reset.setLocation(275, 850);
		reset.setFont(font);
		reset.addActionListener((ActionListener) this);

		help = new JButton("Help");
		help.setSize(200, 50);
		help.setLocation(500, 850);
		help.setFont(font);

		exit = new JButton("Exit");
		exit.setSize(200, 50);
		exit.setLocation(725, 850);
		exit.setFont(font);

		setLayout(null);
		setSize(scrx, scry);
		add(uppertext);
		add(textf);
		add(brows);
		add(scroll);
		add(plz);
		add(tst);
		add(bst);
		add(trie);
		add(search);
		add(build);
		add(reset);
		add(help);
		add(exit);
		setVisible(true);

	}

	public static void main(String[] args) {
		new UI();
	}

	public void setFileChooserFont(Component[] comp) {
		for (int x = 0; x < comp.length; x++) {
			if (comp[x] instanceof Container)
				setFileChooserFont(((Container) comp[x]).getComponents());
			try {
				comp[x].setFont(font);
			} catch (Exception e) {
			} // do nothing
		}
	}

	public void brows() {
		chooser = new JFileChooser();
		chooser.setPreferredSize(new Dimension(800, 600));
		chooser.setDialogTitle("choosertitle");
		setFileChooserFont(chooser.getComponents());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			textf.setText(chooser.getSelectedFile().toString());

		}
	}

	public void readFiles() {

		File folder = new File(textf.getText());
		fi = folder.listFiles();
		//files = new Vector<>();
		mfiles = new Vector<>();
		for (int i = 0; i < fi.length; i++) {

			//files.addElement(fi[i]);
			mfiles.addElement(new MyFile(fi[i]));

		}

	}

	public void processCommand(String command) {

		// command = command.replaceAll("[^A-Za-z]", " ");
		// String parts[] = command.replaceAll("(^\\s+|\\s+$)",
		// "").split("\\s+");
		long startTime = System.currentTimeMillis();

		String parts[] = command.split(" ");
		StringBuilder bl = new StringBuilder();

		for (int j = 1; j < parts.length; j++) {
			bl.append(parts[j]);
			if (j + 1 != parts.length) {
				bl.append(" ");
			}
		}
		String fileName = bl.toString();

		command = command.replaceAll("[^A-Za-z]", " ");
		parts = command.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");

		if (parts[0].equals("add")) {

			addCommand(fileName);

		}

		if (parts[0].equals("del")) {
			deleteCommand(fileName);
		}

		if (parts[0].equals("update")) {
			updateCommand(fileName);
		}

		if (parts[0].equals("list")) {
			if (parts[1].equals("w")) {
				listwCommand();
			}

			if (parts[1].equals("l")) {
				listlCommand();
			}

			if (parts[1].equals("f")) {
				listfCommand();
			}
		}

		if (parts[0].equals("search")) {

			if (parts[1].equals("s")) {

				searchCommand(parts, true);

			}

			if (parts[1].equals("w")) {

				searchCommand(parts, false);

			}
		}

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		textArea.append("time used: " + totalTime + " millisecond\n");
	}

	public void addCommand(String fileName) {

		boolean flag = false;

		for (MyFile mfile : mfiles) {
			
			File file = mfile.file;
			if (file.getName().equals(fileName)) {

				textArea.append("err: already exists, you may want to update.\n");
				flag = true;
				break;
			}
		}
		if (!flag) {

			fi = chooser.getSelectedFile().listFiles();
			for (File file : fi) {
				if (file.getName().equals(fileName)) {
					
					MyFile mfile = new MyFile(file);

					//files.addElement(file);
					mfiles.addElement(mfile);
					
					
					sr.add(mfile);
					textArea.append(file.getName() + " successfully added.\n");

					flag = true;
					break;
				}
			}

		}

		if (!flag) {
			textArea.append("err: can't find the file\n");
		}

	}

	public void deleteCommand(String fileName) {

		boolean flag = false;

		Iterator<MyFile> itr = mfiles.iterator();
		while (itr.hasNext()) {

			MyFile mf = itr.next();
			if (mf.file.getName().equals(fileName)) {

				sr.tree.deleteFile(mf);
				itr.remove();
				textArea.append(mf.file.getName() + " successfully removed from lists.\n");
				flag = true;
				break;
			}
		}

		if (!flag) {
			textArea.append("err: can't find the file\n");
		}

	}

	public void updateCommand(String fileName) {

		boolean flag = false;

		for (MyFile mfile : mfiles) {

			if (mfile.file.getName().equals(fileName)) {
				sr.tree.updateFile(mfile);
				flag = true;
				textArea.append(fileName + " successfully updated.\n");
				break;
			}

		}

		if (!flag) {
			textArea.append("err: document not found.\n");
		}

	}

	public void listwCommand() {
		// StringBuilder sb = new StringBuilder();
		// IntObj num = new IntObj(0);
		sr.tree.travel();
		textArea.append("number of words: " + TreeNode.numberOfWords.value + "\n");
	}

	public void listlCommand() {

		// textArea.append("\n");
		for (MyFile mfile : mfiles) {
			
			File file = mfile.file;

			if (file.equals(mfiles.lastElement().file)) {
				textArea.append(file.getName());

			} else {
				textArea.append(file.getName() + ", ");

			}
		}

		textArea.append("\nNumber of listed docs = " + mfiles.size() + "\n");
	}

	public void listfCommand() {

		fi = chooser.getSelectedFile().listFiles();

		for (File file : fi) {
			if (file.equals(fi[fi.length - 1])) {
				textArea.append(file.getName());

			} else {
				textArea.append(file.getName() + ", ");

			}
		}

		textArea.append("Number of all docs = " + fi.length + "\n");

	}

	public void searchCommand(String parts[], boolean flag) {

		// Vector<MyFile> ans = sr.tree.searchTerm(parts);
		LinkList ans = sr.tree.searchTerm(parts);
		;

		if (ans.first == null) {

			textArea.append("did not match any documents.\n");
		}

		else {

			textArea.append("Appears in:\n");
			// for (MyFile mfile : ans) {
			//
			// textArea.append( mfile.file.getName()+"\n");
			//
			// }

			LinkListNode itr = ans.first;

			while (itr != null) {

				File file = itr.tfile.file;

				textArea.append(file.getName() + "\n");

				if (flag) {

					itr.tfile.printPlace(this);
					itr.tfile.plcs = new Vector<>();
					
					itr.tfile.plcs.addElement(new IntObj(itr.tfile.i));

				}

				itr = itr.next;
			}
		}
		
		textArea.append(ans.size() + "\n");

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == brows) {
			brows();
		}

		if (arg0.getSource() == build) {

			readFiles();

			if (tst.isSelected()) {

				sr = new SearchEngine(mfiles, this, "tst");
			}

			if (bst.isSelected()) {

				sr = new SearchEngine(mfiles, this, "bst");
			}

			if (trie.isSelected()) {

				sr = new SearchEngine(mfiles, this, "trie");

			}

		}

		if (arg0.getSource() == search) {

			command = search.getText();
			commands.addElement(command);

			command = command.toLowerCase();

			cnt = commands.size() - 1;
			textArea.append(">>" + command + "\n");
			processCommand(command);
			search.setText(null);

		}

		if (arg0.getSource() == reset) {
			// textArea.setText("");
			// textf.setText("");
			// search.setText("");
			System.gc();
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == search) {

			if (arg0.getKeyCode() == KeyEvent.VK_UP) {

				cnt++;
				cnt = cnt % commands.size();

				if (commands.size() > 0) {
					search.setText(commands.elementAt(cnt));
				}

			}

			else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {

				cnt--;
				if (cnt < 0) {
					cnt += commands.size();
				}

				if (commands.size() > 0) {
					search.setText(commands.elementAt(cnt));
				}

			}

		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

}

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

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

	Vector<MyFile> files;
	SearchEngine sr;
	String command;
	JFileChooser chooser;
	File fi[];
	Vector<String> commands;
	int cnt;// counter for command- show where we are in commands vector

	public UI() {
		files = new Vector<>();
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

		brows = new JButton("Brows");
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
	
	public void brows(){
		chooser = new JFileChooser();
		chooser.setPreferredSize(new Dimension(800, 600));
		chooser.setDialogTitle("choosertitle");
		setFileChooserFont(chooser.getComponents());
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			textf.setText(chooser.getCurrentDirectory().toString());
			

		}
	}

	public void readFiles() {
		
		File folder = new File(textf.getText());
		fi = folder.listFiles();
		files = new Vector<>();
		for (int i = 0; i < fi.length; i++) {
			files.addElement(new MyFile(fi[i]));
		}

	}

	public void processCommand(String command) {
		
		//command = command.replaceAll("[^A-Za-z]", " ");
		//String parts[] = command.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
		
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
		parts= command.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
		
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

				searchCommand(parts);

			}
		}
	}

	public void addCommand(String fileName) {

		boolean flag = false;

		for (MyFile file : files) {
			if (file.file.getName().equals(fileName)) {

				textArea.append("err: already exists, you may want to update.\n");
				flag = true;
				break;
			}
		}
		if (!flag) {

			fi = chooser.getCurrentDirectory().listFiles();
			for (File file : fi) {
				if (file.getName().equals(fileName)) {
					MyFile mfile = new MyFile(file);
					sr.add(mfile);
					textArea.append( mfile.file.getName() + " successfully added.\n");
					files.addElement(mfile);
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

		Iterator<MyFile> itr = files.iterator();
		while (itr.hasNext()) {

			MyFile mf = itr.next();
			if (mf.file.getName().equals(fileName)) {

				sr.tree.deleteFile(mf);
				itr.remove();
				textArea.append( mf.file.getName() + " successfully removed from lists.\n");
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

		for (MyFile mfile : files) {

			if (mfile.file.getName().equals(fileName)) {
				sr.tree.updateFile(mfile);
				flag = true;
				textArea.append( fileName + " successfully updated.\n");
				break;
			}

		}

		if (!flag) {
			textArea.append("err: document not found.\n");
		}

	}

	public void listwCommand() {
//		StringBuilder sb = new StringBuilder();
//		IntObj num = new IntObj(0);
		sr.tree.travel();
		textArea.append("number of words: " + TreeNode.numberOfWords.value+"\n");
	}

	public void listlCommand() {

		//textArea.append("\n");
		for (MyFile mfile : files) {

			if (mfile.equals(files.lastElement())) {
				textArea.append(mfile.file.getName());

			} else {
				textArea.append(mfile.file.getName() + ", ");

			}
		}

		textArea.append("\nNumber of listed docs = " + files.size()+"\n");
	}

	public void listfCommand() {

		fi = chooser.getCurrentDirectory().listFiles();

		for (File file : fi) {
			if (file.equals(fi[fi.length - 1])) {
				textArea.append(file.getName());

			} else {
				textArea.append(file.getName() + ", ");

			}
		}

		textArea.append("Number of all docs = " + fi.length+"\n");

	}

	public void searchCommand(String parts[]) {

		//Vector<MyFile> ans = sr.tree.searchTerm(parts);
		LinkList ans = sr.tree.searchTerm(parts);;

		if (ans == null) {

			textArea.append("did not match any documents.\n");
		}

		else {

			textArea.append("Appears in:\n");
//			for (MyFile mfile : ans) {
//
//				textArea.append( mfile.file.getName()+"\n");
//
//			}
			
			LinkList itr = ans;
			while(itr != null){
				
				MyFile mfile = itr.mfile;
				textArea.append( mfile.file.getName()+"\n");
				itr = itr.next;
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == brows) {
			brows();
		}

		if (arg0.getSource() == build) {
			
			readFiles();
			
			if(tst.isSelected()){
				
				sr = new SearchEngine(files, this, "tst");
			}
			
			if(bst.isSelected()){
				
				sr = new SearchEngine(files, this, "bst");
			}
			
			if(trie.isSelected()){
				
				sr = new SearchEngine(files, this, "trie");

			}

		}

		if (arg0.getSource() == search) {

			command = search.getText();
			commands.addElement(command);
			
			command = command.toLowerCase();
			
			cnt = commands.size() - 1 ;
			textArea.append(">>" + command+"\n");
			processCommand(command);
			search.setText(null);

		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == search) {
			
			if (arg0.getKeyCode() == KeyEvent.VK_UP) {
				
				cnt++;
				cnt = cnt%commands.size();
				
				if(commands.size()>0){
					search.setText(commands.elementAt(cnt));
				}

			}

			else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {

				cnt--;
				if(cnt<0){
					cnt += commands.size();
				}
				
				if(commands.size()>0){
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

		if (arg0.equals(KeyEvent.VK_UP)) {

		}
	}

}

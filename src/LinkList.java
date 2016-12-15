
public class LinkList {

	LinkListNode first;

	public LinkList(TreeFile tfile) {
		// TODO Auto-generated constructor stub
		first = new LinkListNode(tfile);

	}

	public LinkList() {
		// TODO Auto-generated constructor stub
	}

	public boolean add(TreeFile tfile) {
		if (first != null) {
			return first.add(tfile);

		} else {
			first = new LinkListNode(tfile);
			return true;
		}

	}

	public void del(MyFile mfile) {

		if (first.tfile.mfile == mfile) {
			// System.out.println(mfile.file.getName());
			// if(first.next!=null){
			// System.out.println(first.next.mfile.file.getName());
			// }
			first = first.next;
		}

		else {
			first.del(mfile);
		}

	}

	public boolean doesContain(MyFile mfile) {
		if (first == null) {
			return false;
		}

		return first.doesContain(mfile);

	}

	public int size() {
		if (first == null) {
			return 0;
		}
		return first.size();
	}

	public TreeFile elementAt(int i) {

		return first.elementAt(i);
	}

	public TreeFile lastElement() {

		if (first == null) {
			return null;
		}
		return first.lastElement();
	}

	public LinkListNode haminin(TreeFile tf) {

		if (first == null) {
			return null;
		}

		return first.haminin(tf);
	}

}

class LinkListNode {

	TreeFile tfile;
	LinkListNode next;
	LinkListNode last;

	public LinkListNode(TreeFile tfile) {
		// TODO Auto-generated constructor stub
		this.tfile = tfile;

	}

	public LinkListNode() {
		// TODO Auto-generated constructor stub
	}

	public boolean add(TreeFile tfile) {

		if (tfile.eq(this.tfile)) {
			return false;
		}

		if (this.tfile == null) {
			this.tfile = tfile;
			return true;
		}

		else if (next == null) {
			next = new LinkListNode(tfile);
			next.last = this;
			return true;
		}

		else {
			return next.add(tfile);
		}
	}

	public void del(MyFile mfile) {

		if (this.tfile.mfile == mfile) {

			if (next != null) {
				last.next = next;
				next.last = last;
			}

			else {
				last.next = null;
			}

		}

		else if (next != null) {

			next.del(mfile);
		}
	}

	public boolean doesContain(MyFile mfile) {

		if (this.tfile.mfile == mfile) {
			return true;
		}

		if (next == null) {
			return false;
		}

		return next.doesContain(mfile);

	}

	public int size() {
		if (next == null) {
			return 1;
		}

		return (next.size() + 1);
	}

	public TreeFile elementAt(int i) {

		if (i == 0) {
			return tfile;
		}

		return next.elementAt(i - 1);
	}

	public TreeFile lastElement() {
		if (next == null) {
			return tfile;
		}

		return next.lastElement();
	}

	public LinkListNode haminin(TreeFile tf) {

		if (tf.mfile == this.tfile.mfile) {
			return this;
		}

		else {
			if (next == null) {
				return null;
			}

			else {
				return next.haminin(tf);
			}
		}
	}

}

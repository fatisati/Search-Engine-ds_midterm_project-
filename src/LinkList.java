
public class LinkList {

	LinkListNode first;

	public LinkList(MyFile mfile) {
		// TODO Auto-generated constructor stub
		first = new LinkListNode(mfile);

	}

	public LinkList() {
		// TODO Auto-generated constructor stub
	}

	public void add(MyFile mfile) {
		if (first != null) {
			first.add(mfile);

		} else {
			first = new LinkListNode(mfile);
		}

	}

	public void del(MyFile mfile) {
		
		
		if (first.mfile == mfile) {
			//System.out.println(mfile.file.getName());
			//if(first.next!=null){
				//System.out.println(first.next.mfile.file.getName());
			//}
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
		if(first == null){
			return 0;
		}
		return first.size();
	}

	public MyFile elementAt(int i) {

		return first.elementAt(i);
	}

	public MyFile lastElement() {
		
		if(first == null){
			return null;
		}
		return first.lastElement();
	}

}

class LinkListNode {

	MyFile mfile;
	LinkListNode next;
	LinkListNode last;

	public LinkListNode(MyFile mfile) {
		// TODO Auto-generated constructor stub
		this.mfile = mfile;
		next = null;
		last = null;
	}

	public LinkListNode() {
		// TODO Auto-generated constructor stub
		mfile = null;
		next = null;
		last = null;
	}

	public void add(MyFile mfile) {

		if (this.mfile == null) {
			this.mfile = mfile;
		}

		else if (next == null) {
			next = new LinkListNode(mfile);
			next.last = this;
		}

		else {

			next.add(mfile);

		}
	}

	public void del(MyFile mfile) {
		
		if (this.mfile == mfile) {

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

		if (this.mfile == mfile) {
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

	public MyFile elementAt(int i) {

		if (i == 0) {
			return mfile;
		}

		return next.elementAt(i - 1);
	}

	public MyFile lastElement() {
		if (next == null) {
			return mfile;
		}

		return next.lastElement();
	}

}


public class LinkList {

	MyFile mfile;
	LinkList next;
	LinkList last;

	public LinkList(MyFile mfile) {
		// TODO Auto-generated constructor stub
		this.mfile = mfile;

	}

	public LinkList() {
		// TODO Auto-generated constructor stub
	}

	public void add(MyFile mfile) {

		if (this.mfile == null) {
			this.mfile = mfile;
		}

		else if (next == null) {
			next = new LinkList(mfile);
			next.last = this;
		}

		else {

			next.add(mfile);

		}
	}
	
	public void del(LinkList node){
		node.mfile = null;
	}

	public void del(MyFile mfile) {

		if (this.mfile == mfile) {
			
			if(last != null){
				last.next = next;
				next.last = last;
			}
			
			else if(next != null){
				mfile = next.mfile;
				next = next.next;
			}
			
			else {
				//System.out.println(mfile.file.getName());
				del(this);
			}
		}

		else {

			next.del(mfile);
		}

	}

	public boolean doesContain(MyFile mfile) {

		if (this.mfile == mfile) {
			return true;
		}
		
		if(next == null){
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

class LinkListNode{
	
	MyFile mfile;
	LinkListNode next;
	LinkListNode last;
	
	public LinkListNode(MyFile mfile) {
		// TODO Auto-generated constructor stub
		this.mfile = mfile;
	}
}

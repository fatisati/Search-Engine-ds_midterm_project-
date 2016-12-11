
public class LinkListNode {

	MyFile mfile;
	LinkListNode next;
	LinkListNode last;

	public LinkListNode(MyFile mfile) {
		// TODO Auto-generated constructor stub
		this.mfile = mfile;

	}

	public void add(LinkListNode node) {

		if (next == null) {
			next = node;
			node.last = this;
		}

		else {

			next.add(node);
			
		}
	}
	
	public void del(LinkListNode node){
		
		if(this == node){
			last.next = next;
			next.last = last;
		}
		
		else{
			
			next.del(node);
		}
		
	}
	
	public boolean doesContain(LinkListNode node){
		
		if(this == node){
			return true;
		}
		
		return next.doesContain(node);
		
	}

}

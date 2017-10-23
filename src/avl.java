
public class avl {
	
	static Node rt=  null;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		insert();
		
		inorder(rt);
		
		delete(rt,7);
		
		System.out.println("");
		inorder(rt);
		
		delete(rt,6);
		
		System.out.println("");
		inorder(rt);
		
		delete(rt,1);
		
		System.out.println("");
		inorder(rt);
				
	}
	
	static void insert() {
		insert(2);
		insert(3);
		insert(4);
		insert(5);
		insert(6);
		insert(7);
		insert(1);
		insert(-1);
		insert(-2);
		insert(-3);
		insert(-4);
	}
	static void insert(int val) {
		rt = insert(rt, val);
		rt.parent = null;
	}
	
	static void inorder(Node root) {
		if(root == null)
			return;
		inorder(root.left);
		System.out.println("Root val: "+root.val+" Ht: "+root.ht);
		inorder(root.right);
	}
	
	static Node search(Node root,int val) {
		if(root.val == val)
			return root;
		else if(root.left== null && root.right == null) 
			return null;
		else if(val > root.val)
			return search(root.right,val);
		else
			return search(root.left,val);
	}
	
	
	static Node BalanceNode(Node tmp) {
		Node t;
		if(height(tmp.left) - height(tmp.right) == 2) {
			Node z = tmp;
			Node y = tmp.left;
			if(height(y.left) > height(y.right)) {
				//Node x = y.left;
				t=zig_zig_left(z);
				
			}
			else {
				//Node x = y.right;
				t=zig_zag_left(z);
			}
		}
		else if(height(tmp.right) - height(tmp.left) == 2) {
			Node z = tmp;
			Node y = tmp.right;
			if(height(y.left) > height(y.right)) {
				//Node x = y.left;
				t=zig_zag_right(z);
			}
			else {
				//Node x = y.right;
				t=zig_zig_right(z);
			}
		}
		else {
			return tmp;
		}
		return t;
	}
	
	static void updateht(Node m) {
		Node tmp= m;
		
		while(tmp!= rt) {
			int flag = 0;
			if(tmp.parent.left == tmp)
				flag =1;
			else
				flag =2;
			tmp.ht = max(height(tmp.left),height(tmp.right))+1;
			Node parent = tmp.parent; //the balancing might change parent of tmp.
			Node t = BalanceNode(tmp);//therefore saving it before it gets changed.
			t.parent = parent;
			if(flag == 1)
				parent.left = t;
			else
				parent.right = t;
			tmp = t;
			tmp = tmp.parent;
			
		}
		tmp.ht = max(height(tmp.left),height(tmp.right))+1;
		System.out.println("Height root: "+tmp.right.ht);
		tmp = BalanceNode(tmp);
	}
	
	
	static void delete(Node root, int val) {
		Node tmp = search(root, val);
		if(tmp == null)
			return;
		else if(tmp.left == null && tmp.right==null){
				if(tmp.parent!=null) {
					if(tmp == tmp.parent.left) {
						
						tmp.parent.left = null;
					}
					if(tmp == tmp.parent.right) {
						tmp.parent.right = null;
					}
					updateht(tmp.parent);
				}
				else {
					rt = null;
				}
		}
		else if(tmp.left == null && tmp.right != null) {
				if(tmp.parent.left == tmp) {
					tmp.parent.left = tmp.right;
					
				}
				if(tmp.parent.right == tmp) {
					tmp.parent.right = tmp.right;
					
				}
				
				updateht(tmp.parent);
		}
		else if(tmp.left != null && tmp.right == null) {
				if(tmp.parent.left == tmp) {
					tmp.parent.left = tmp.left;
				}
				if(tmp.parent.right == tmp) {
					tmp.parent.right = tmp.left;
				}
				tmp.left.parent = tmp.parent;
				updateht(tmp.parent);
		}
		else {
				Node scc = tmp.right;
				while(scc.left !=null)
					scc = scc.left;
				
				tmp.val = scc.val;
				scc.parent.left = scc.right;
				updateht(scc.parent);
			
		}
		
	}
	
	
	
	static Node insert(Node root, int val) {
		if(root == null) {
			root = new Node(val);
			
		}
		else {
			if(root.val < val) {
				root.right = insert(root.right, val );
				if(height(root.right) - height(root.left) ==  2) {
					if(root.right.left!=null && root.right.left.val == val ) {
						root = zig_zag_right(root);
					}
					else {
						root = zig_zig_right(root);
					}
				}
					
			}
			else {
				root.left = insert(root.left, val);
				if(height(root.left) - height(root.right) == 2) {
					if(root.left.right!=null && root.left.right.val == val) {
						root = zig_zag_left(root);
					}
					else {
						root = zig_zig_left(root);
					}
				}
			}
		}
		root.ht = max(height(root.left), height(root.right))+1;
		if(root.left !=null)
		root.left.parent = root;
		if(root.right !=null)
		root.right.parent = root;
		return root;
	}
	
	static Node zig_zag_left(Node root) {
		Node z = root;
		Node y = root.left;
		Node x = root.left.right;
		
		z.left = x.right;
		if(x.right!=null)
		x.right.parent = z;
		y.right = x.left;
		if(x.left!=null)
		x.left.parent = y;
		
		x.left = y;
		y.parent = x;
		x.right = z;
		z.parent = x;
		
		z.ht = max(height(z.left),height(z.right))+1;
		y.ht = max(height(y.left),height(y.right))+1;
		x.ht = max(height(x.left),height(x.right))+1;
		
		

		
		return x;
	}
	
	static Node zig_zig_left(Node root) {
		Node z = root;
		Node y = root.left;
		
		z.left = y.right;
		if(y.right !=null)
		y.right.parent = z;
		
		y.right = z;
		z.parent = y;
		
		z.ht = max(height(z.left),height(z.right))+1;
		y.ht = max(height(y.left),height(y.right))+1;
		
		
		return y;
	}
	
	static Node zig_zig_right(Node root) {
		Node z = root;
		Node y = root.right;
		
		
		z.right = y.left;
		if(y.left !=null)
		y.left.parent = z;
		
		y.left = z;
		z.parent = y;
		
		z.ht = max(height(z.left),height(z.right))+1;
		y.ht = max(height(y.left),height(y.right))+1;
		
		return y;
	}
	
	static Node zig_zag_right(Node root) {
		Node z= root;
		Node y= root.right;
		Node x= root.right.left;
		
		z.right = x.left;
		if(x.left !=null)
		x.left.parent = z;
		
		y.left = x.right;
		if(x.right !=null)
		x.right.parent = y;
		
		x.left = z;
		z.parent = x;
		x.right = y;
		y.parent = x;
		

		z.ht = max(height(z.left),height(z.right))+1;
		y.ht = max(height(y.left),height(y.right))+1;
		x.ht = max(height(x.left),height(x.right))+1;
		
				
		return x;
	}
	
	
	static int height(Node root) {
		
		return (root==null)?-1:(root.ht);
	}
	
	static int max(int a,int b) {
		return (a>b)?a:b;
	}
	
}

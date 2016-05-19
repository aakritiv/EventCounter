package redBlackTree;
import java.util.ArrayList;
/*
 * Generic Class that implements a red black tree.
 */
public class RedBlackTree<Key extends Comparable<Key>,Value> {
	
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	
	/*
	 * Root and sentinel nodes
	 */
	private Node<Key,Value> root;
	private Node<Key,Value> nil = new Node<Key,Value>(null, null, BLACK);
	
	RedBlackTree(){
		root = nil;
	}
	
	/*
	 * Constructor to create a red black tree from a sorted array
	 */
	public RedBlackTree(Key[] key,Value[] value){
		int ht = (int)(Math.log(key.length));
		if(Math.pow(2,ht) != key.length)
			ht++;
		root = insertSorted(key, value, 0, key.length-1,0,ht);
	}
	
	/*
	 * Returns the Node<Key,Value> if key is present in the tree otherwise null 
	 */
	public Node<Key,Value> find(Key key){
		Node<Key,Value> cur = root;
		while(cur!=nil){
			if(cur.getKey().compareTo(key)<0)
				cur = cur.getRight();
			else if(cur.getKey().compareTo(key)>0)
				cur = cur.getLeft();
			else
				return cur;
		}
		return null;
	}
	
	/*
	 * Returns the value associated with the key
	 */
	public Value get(Key key){
		Node<Key,Value> cur = find(key);
		if(cur == null)
			return null;
		return cur.getValue();
	}
	
	/*
	 * Returns true if the key is contained in the tree otherwise false
	 */
	public boolean contains(Key key){
		if(find(key) != null)
			return true;
		return false;
	}

	/*
	 * Returns an ArrayList of all the values between lower and upper key values(both inclusive)
	 */
	public ArrayList<Value> inRange(Key lower,Key upper){
		Node<Key,Value> cur = root;
		while(cur != nil){
			if(cur.getKey().compareTo(lower) >= 0 && cur.getKey().compareTo(upper) <= 0)
				break;
			if(cur.getKey().compareTo(upper) > 0)
				cur = cur.getLeft();
			else if(cur.getKey().compareTo(lower) < 0)
				cur = cur.getRight();
		}
		ArrayList<Value> value = new ArrayList<>();
		inorderRange(cur, lower, upper, value);
		return value;
	}
	
	/*
	 * Returns the Node containing the smallest key greater than the key
	 */
	public Node<Key,Value> next(Key key){
		Node<Key,Value> cur = root;
		Node<Key,Value> prev = null;
		int flag = 0;
		while(cur!=nil){
			if(cur.getKey().compareTo(key)<0){
				prev = cur;
				cur = cur.getRight();
				flag = 1;
			}
			else if(cur.getKey().compareTo(key)>0){
				prev = cur;
				cur = cur.getLeft();
				flag = 2;
			}
			else{
				prev = cur;
				flag = 0;
				break;
			}
		}
		if(flag == 2)
			return prev;
		else
			return findSuccessor(prev);
	}
	
	/*
	 * Returns the Node containing the greatest key smaller than the key
	 */
	public Node<Key,Value> previous(Key key){
		Node<Key,Value> cur = root;
		Node<Key,Value> prev = null;
		int flag = 0;
		while(cur!=nil){
			if(cur.getKey().compareTo(key)<0){
				prev = cur;
				cur = cur.getRight();
				flag = 1;
			}
			else if(cur.getKey().compareTo(key)>0){
				prev = cur;
				cur = cur.getLeft();
				flag = 2;
			}
			else{
				prev = cur;
				flag = 0;
				break;
			}
		}
		if(flag == 1)
			return prev;
		else
			return findPredecessor(prev);	
	}
	
	/*
	 * Find the successor of the given Key
	 */
	public Node<Key,Value> findSuccessor(Key key){
		Node<Key,Value> n = find(key);
		return findSuccessor(n);
	}
	
	/*
	 * Find the predecessor of the given Key
	 */
	public Node<Key,Value> findPredecessor(Key key){
		Node<Key,Value> n = find(key);
		return findPredecessor(n);
	}
	
	/*
	 * Delete the Node with the given key if it exists in the tree
	 * Calls the private method deleteNode if the node exists 
	 */
	public boolean delete(Key key){
		Node<Key,Value> n = find(key);
		if(n==null)
			return false;
		deleteNode(n);
		return true;
	}
	
	/*
	 * insert the Node with the given key and value in the tree
	 */
	public void insert(Key key,Value value){
		if(key == null || value == null) throw new NullPointerException();
		Node<Key,Value> temp = new Node<Key,Value>(key,value,RED,nil,nil);
		if(root == null){
			temp.setColor(BLACK);
			temp.setParent(nil);
			root = temp;
			return;
		}
		boolean flag = false;
		Node<Key,Value> cur = root;
		Node<Key,Value> prev = nil;
		while(cur!=nil){
			prev = cur;
			if(cur.getKey().compareTo(key)<0)
				cur = cur.getRight();
			else if(cur.getKey().compareTo(key)>0)
				cur = cur.getLeft();
			else{
				flag = true;
				break;
			}
		}
		if(flag){
			cur.setValue(value);
			return;
		}
		temp.setParent(prev);
		if(prev.getKey().compareTo(key)<0)
			prev.setRight(temp);
		else
			prev.setLeft(temp);
		insertFixup(temp);
	}
	
	/*
	 * find all the nodes in the inorder traversal bounded by lower and upper key values
	 */
	private void inorderRange(Node<Key,Value> n,Key lower,Key upper,ArrayList<Value> v){
		if(n == nil)
			return;
		if(n.getKey().compareTo(lower)>=0)
			inorderRange(n.getLeft(),lower,upper,v);
		if(n.getKey().compareTo(lower) >= 0 && n.getKey().compareTo(upper) <= 0)
			v.add(n.getValue());
		if(n.getKey().compareTo(upper)<= 0)
			inorderRange(n.getRight(),lower,upper,v);
	}
	
	/*
	 * Find the successor of the Node n
	 */
	private Node<Key,Value> findSuccessor(Node<Key,Value> n){
		if(n == null && n == nil)
			return null;
		Node<Key,Value> succ;
		if(n.getRight() != nil){
			succ = n.getRight(); 
			while(succ.getLeft() != nil){
				succ = succ.getLeft();
			}
		}
		else {
			succ = n.getParent();
			while(succ != nil && n == succ.getRight()){
				n = succ;
				succ = succ.getParent();	
			}
		}
		if(succ == nil)
			return null;
		return succ;
	}

	/*
	 * Find the predecessor of the Node n
	 */
	private Node<Key,Value> findPredecessor(Node<Key,Value> n){
		if(n == null)
			return null;
		Node<Key,Value> pred;
		if(n.getLeft() != nil){
			pred = n.getLeft(); 
			while(pred.getRight() != nil){
				pred = pred.getRight();
			}
		}
		else {
			pred = n.getParent();
			while(pred != nil && n == pred.getLeft()){
				n = pred;
				pred = pred.getParent();	
			}
		}
		if(pred == nil)
			return null;
		return pred;
	}
	
	/*
	 * Deletes the Node n from the tree
	 */
	private void deleteNode(Node<Key,Value> n){
		Node<Key,Value> y = n;
		Node<Key,Value> x = null;
		boolean color = y.getColor();
		if(n.getLeft() == nil){
			x = n.getRight();
			transplant(n, n.getRight());
		}
		else if(n.getRight() == nil){
			x = n.getLeft();
			transplant(n, n.getLeft());
		}
		else {
			y = n.getRight();
			while(y.getLeft()!=nil)
				y = y.getLeft();
			color = y.getColor();
			/* what if x is null!!????? 
			 * the code considers last node to be a sentinel node. 
			 * Need to fix this
			 * */
			x = y.getRight(); 
			if(y.getParent() == n){
				x.setParent(y);
			}
			else {
				transplant(y, y.getRight());
				y.setRight(n.getRight());
				y.getRight().setParent(y);
			}
			transplant(n,y);
			y.setLeft(n.getLeft());
			y.getLeft().setParent(y);
			y.setColor(n.getColor());
		}
		if(color == BLACK)
			deleteFixup(x);
	}
	
	private void transplant(Node<Key,Value> x,Node<Key,Value> y){
		if(x.getParent() == nil)
			root = y;
		else if(x == x.getParent().getLeft())
			x.getParent().setLeft(y);
		else 
			x.getParent().setRight(y);
		y.setParent(x.getParent());
	}
	
	/*
	 * During deletion the properties of red black tree may get violated.
	 * This method removes the violation
	 */
	private void deleteFixup(Node<Key,Value> x){
		while((x!=root && x.getColor() == BLACK)){
			if(x == x.getParent().getLeft()){
				Node<Key,Value> w = x.getParent().getRight();
				if(w.getColor()==RED){
					w.setColor(BLACK);
					x.getParent().setColor(RED);
					leftRotate(x.getParent());
					w = x.getParent().getRight();
				}
				if(w.getLeft().getColor() == BLACK && w.getRight().getColor() == BLACK){
					w.setColor(RED);
					x = x.getParent();
				}
				else {
					if(w.getRight().getColor() == BLACK){
						w.getLeft().setColor(BLACK);
						w.setColor(RED);
						rightRotate(w);
						w = x.getParent().getRight();
					}
					w.setColor(x.getParent().getColor());
					x.getParent().setColor(BLACK);
					w.getRight().setColor(BLACK);
					leftRotate(x.getParent());
					x = root;
				}
			}
			else {
				Node<Key,Value> w = x.getParent().getLeft();
				if(w.getColor()==RED){
					w.setColor(BLACK);
					x.getParent().setColor(RED);
					rightRotate(x.getParent());
					w = x.getParent().getLeft();
				}
				if(w.getLeft().getColor() == BLACK && w.getRight().getColor() == BLACK){
					w.setColor(RED);
					x = x.getParent();
				}
				else {
					if(w.getLeft().getColor() == BLACK){
						w.getRight().setColor(BLACK);
						w.setColor(RED);
						leftRotate(w);
						w = x.getParent().getLeft();
					}
					w.setColor(x.getParent().getColor());
					x.getParent().setColor(BLACK);
					w.getLeft().setColor(BLACK);
					rightRotate(x.getParent());
					x = root;
				}				
			}
		}
		root.setColor(BLACK);
	}


	/*
	 * During insertion the properties of red black tree gets violated (if the parent root is red).
	 * This method removes the violation at the lower level and pushes it up the tree to the root.
	 */
	private void insertFixup(Node<Key,Value> t){
		while(t != root && t.getParent().getColor() == RED){
			Node<Key,Value> parent = t.getParent();
			Node<Key,Value> grandParent = parent.getParent();
			
			if(t.getParent() == grandParent.getLeft()){
				Node<Key,Value> temp = grandParent.getRight();
				if(temp.getColor() == RED){
					parent.setColor(BLACK);
					temp.setColor(BLACK);
					grandParent.setColor(RED);
					t = grandParent;
				}
				else{
					if(t == parent.getRight()){
						t = parent;
						leftRotate(t);
					}
					t.getParent().setColor(BLACK);
					t.getParent().getParent().setColor(RED);
					rightRotate(t.getParent().getParent());
				}
			}
			else {
				Node<Key,Value> temp = grandParent.getLeft();
				if(temp.getColor() == RED){
					parent.setColor(BLACK);
					temp.setColor(BLACK);
					grandParent.setColor(RED);
					t = grandParent;
				}
				else{
					if(t == parent.getLeft()){
						t = parent;
						rightRotate(t);
					}
					t.getParent().setColor(BLACK);
					t.getParent().getParent().setColor(RED);
					leftRotate(t.getParent().getParent());
				}
			}
		}
		root.setColor(BLACK);
	}
	
	/*
	 * left rotation to balance the tree
	 */
	private void leftRotate(Node<Key,Value> n){
		Node<Key,Value> y = n.getRight();
		Node<Key,Value> parent = n.getParent();
		n.setRight(y.getLeft());
		if(y.getLeft()!=nil){
			y.getLeft().setParent(n);
		}
		y.setParent(n.getParent());
		y.setLeft(n);
		n.setParent(y);
		if(parent == nil)
			root = y;
		else if (parent.getLeft()==n)
			parent.setLeft(y);
		else if (parent.getRight()==n)
			parent.setRight(y);
	}
	
	/*
	 * right rotation to balance the tree
	 */
	private void rightRotate(Node<Key,Value> n){
		Node<Key,Value> y = n.getLeft();
		Node<Key,Value> parent = n.getParent();
		n.setLeft(y.getRight());
		if(y.getRight()!=nil){
			y.getRight().setParent(n);
		}
		y.setParent(n.getParent());
		y.setRight(n);
		n.setParent(y);
		if(parent == nil)
			root = y;
		else if (parent.getLeft()==n)
			parent.setLeft(y);
		else if (parent.getRight()==n)
			parent.setRight(y);
	}
	
	/*
	 * Insertion of the sorted list to the tree by: 
	 * Insertion in the binary search tree
	 * and coloring the last row (or the leaf nodes) as red 
	 * while all the internal and external nodes are black
	 */
	private Node<Key,Value> insertSorted(Key[] key,Value[] value,int start,int end,int c,int ht){
		if(start > end)
			return nil;
		int mid = (start+end)/2;
		Node <Key,Value> r = new Node<Key, Value>(key[mid], value[mid], BLACK,nil,nil,nil);
		Node <Key,Value> left = insertSorted(key, value, start, mid-1,c+1,ht);
		r.setLeft(left);
		if(left != nil)
			left.setParent(r);
		Node <Key,Value> right = insertSorted(key, value, mid+1, end,c+1,ht);
		r.setRight(right);
		if(right!=nil)
			right.setParent(r);
		if(c==ht)
			r.setColor(RED);
		return r;
	}
}

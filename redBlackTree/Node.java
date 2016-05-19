package redBlackTree;
/*
 * Generic Node class - representing the tree nodes
 * get and set methods of key and value are the methods that are accessible outside the package. 
*/
public class Node<Key extends Comparable<Key>,Value> {
	private Key key;
	private Value value;
	private boolean color;
	private Node<Key,Value> left;
	private Node<Key,Value> right;
	private Node<Key,Value> parent;
	
	Node(Key k,Value v,boolean c){
		key = k;
		value = v;
		color = c;
		left = null;
		right = null;
		parent = null;
	}
	
	Node(Key k,Value v,boolean c, Node<Key,Value> l,Node<Key,Value> r){
		key = k;
		value = v;
		color = c;
		left = l;
		right = r;
	}
	Node(Key k,Value v,boolean c, Node<Key,Value> l,Node<Key,Value> r,Node<Key,Value> p){
		key = k;
		value = v;
		color = c;
		left = l;
		right = r;
		parent = p;
	}
	//<-- setter methods
	public void setKey(Key k){
		key = k;
	}
	
	public void setValue(Value v){
		value = v;
	}
	
	void setColor(boolean c){
		color = c;
	}

	void setLeft(Node<Key,Value> l){
		left = l;
	}
	
	void setRight(Node<Key,Value> r){
		right = r;
	}
	
	void setParent(Node<Key,Value> p){
		parent = p;
	}
	//setter methods -->
	
	//<-- getter methods
	public Key getKey(){
		return key;
	}
	
	public Value getValue(){
		return value;
	}
	
	boolean getColor(){
		return color;
	}
	
	Node<Key,Value> getLeft(){
		return left;
	}
	
	Node<Key,Value> getRight(){
		return right;
	}
	
	Node<Key,Value> getParent(){
		return parent;
	}
	//getter methods -->
}
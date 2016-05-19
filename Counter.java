import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;

import redBlackTree.Node;
import redBlackTree.RedBlackTree;

/*
 * Class to implement the event counter using red black trees
 * It creates a tree by reading the sorted list from the file 
 */
public class Counter {
	private RedBlackTree<Integer, Integer> counter;
	
	public Counter(String filename){
		int n;
		try{
			String path = System.getProperty("user.dir");
			FileReader file = new FileReader(filename);
			BufferedReader br = new BufferedReader(file);
			String line = br.readLine();
			n = Integer.parseInt(line);
			Integer id[] = new Integer[n];
			Integer count[] = new Integer[n];
			int c = 0;
			while((line= br.readLine()) != null){
				String[] s = line.split(" ");
				id[c]=Integer.parseInt(s[0]);
				count[c]=Integer.parseInt(s[1]);
				c++;
			}
			counter = new RedBlackTree<Integer,Integer>(id,count);
			br.close();
		}catch(Exception e){
			System.out.println("Error: " + e.getMessage() + e.toString());
		}
	}

	/*
	 * Increases the count of id by m if present otherwise inserts the id in the tree
	 */
	public void increase(int id,int m){
		Node<Integer,Integer> n = counter.find(id);
		if(n != null){
			int val = n.getValue();
			m += val;
		}
		counter.insert(id, m);
		System.out.println(m);
	}
	
	/*
	 * Decreases the count of id by m if present
	 * If the count goes below 1, deletes the node from the tree
	 */
	public void reduce(int id,int m){
		Node<Integer,Integer> n = counter.find(id);
		if(n != null){
			int val = n.getValue();
			m = val - m;
			if(m <= 0){
				counter.delete(id);
				m = 0;
			}
			else 
				counter.insert(id,m);
		}
		else 
			m = 0;
		System.out.println(m);
	}
	
	/*
	 * prints the count of the id
	 */
	public void count(int id){
		int count = 0;
		Node<Integer,Integer> n = counter.find(id);
		if(n != null){
			count = n.getValue();
		}
		System.out.println(count);
	}
	
	/*
	 * prints the sum of the counts of all ids between id1 and id2
	 */
	public void inRange(int id1,int id2){
		ArrayList<Integer> range = counter.inRange(id1, id2);
		int sum = 0;
		for(int r : range){
			sum += r;
		}
		System.out.println(sum);
	}
	
	/*
	 * prints the smallest id greater than the given id 
	 */
	public void next(int id){
		Node<Integer,Integer> succ = counter.next(id);
		int sId = 0;
		int count = 0;
		if(succ != null){
			count = succ.getValue();
			sId = succ.getKey();
		}	
		System.out.println(sId + " " + count);
	}
	
	/*
	 * prints the greatest id smaller than the given id
	 */
	public void previous(int id){
		Node<Integer,Integer> pre = counter.previous(id);
		int count = 0 , pId = 0;
		if(pre != null){
			count = pre.getValue();
			pId = pre.getKey();
		}
		System.out.println(pId + " " + count);
	}
}


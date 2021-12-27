/*
Tamsin Rogers
4/7/20
Template for the BSTMap classes
Spring 2019
CS 231 Project 6
*/
import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;
import java.util.stream.Stream;

/* map of a binary search tree made up of KeyValuePairs */
public class BSTMap<K, V> implements MapSet<K, V> 
{
	private TNode root;																// the root node
	private Comparator<K> comp;														// Comparator<K> to compare Keys
	private int size;																// size of the map
		
	/* constructor: takes in a Comparator object */
	public BSTMap( Comparator<K> comp ) 
	{	
		this.comp = comp;															// initialize the comparator
		this.size = 0;																// initialize the size of the map to 0 (empty)
	}

	/* adds or updates a key-value pair, returns the old value at the given key */
	public V put(K key, V value) 
	{		
		V oldValue;																	// use to save the old value of the pair
		if(root == null)															// if there is not already a pair
		{
			oldValue = null;														// oldValue does not exist
			root = new TNode(key, value);											// add a new node with the given pair
		}		
		else																		// if the key is already in the map
		{
			oldValue = root.get(key, comp);											// oldValue is the current value
			root.put(key,value, comp);												// replace the existing value with the current value
		}
		return oldValue;															// returns either the old value of the pair or null
    }

    /* returns the value at the specified key or null */
    public V get(K key) 
    {
		if(root == null)															// if the key is not in the map
		{
			return null;															// the value does not exist
		}
		else																		// if the key is in the map
		{
			return root.get(key, comp);												// call the root's get method
		}
    }
    
    /* returns true if the map contains a key-value pair with the given key */
    public boolean containsKey(K key)
    {
    	if(this.get(key) != null)													// if there is a pair in the map with the given key
    	{
    		return true;
    	}
    	else																		// if there is not a pair in the map with the given key
    	{
    		return false;
    	}
    }
    
    /* returns an ArrayList of all the keys in the map */
    public ArrayList<K> keySet()
    {
    	ArrayList<K> keys = new ArrayList<K>();										// create a new array list of keys
    	K key;																		// a key in the keys array list
    	
    	for(KeyValuePair<K,V> kvp : this.entrySet())								// for each pair in the map
    	{
    		key = kvp.getKey();														// get the key in the current pair
    		keys.add(key);															// add the key to the list
    	}
    	return keys;																// return the array list of keys
    }
    
    /* returns an ArrayList of all the values in the map */
    public ArrayList<V> values()
    {
    	ArrayList<V> values = new ArrayList<V>();									// create a new array list of values
    	V value;																	// a value in the values array list
    	
    	for(KeyValuePair<K,V> kvp : this.entrySet())								// for each pair in the map
    	{
    		value = kvp.getValue();													// get the value in the current pair
    		values.add(value);														// add the value to the list
    	}
    	return values;																// return the array list of values
    }
    
    /* return an ArrayList of all the pairs in the map */
    public ArrayList<KeyValuePair<K,V>> entrySet()
    {
    	ArrayList<KeyValuePair<K,V>> pairs = new ArrayList<KeyValuePair<K,V>>();	// create the arraylist to store the pairs															
    
    	if(root == null)															// if there is no root node
    	{
    		return pairs;															// return the empty list
    	}
    	
    	Stack<TNode> stack = new Stack<TNode>();									// create a stack of nodes
    	stack.push(root);															// add the root node to the stack
    	
    	while(!stack.empty())														// go through the stack of nodes
    	{
    		TNode n = stack.pop();													// save and remove the current node
    		pairs.add(n.data);														// add the <K,V> data to the pairs list
    		
    		if(n.right != null)														// if the current node has a right node
    		{
    			stack.push(n.right);												// add the right node to the stack 
    		}																		// (push the right node onto the stack first, so that it is looked at after the left node)
    		if(n.left != null)														// if the current node has a left node
    		{
    			stack.push(n.left);													// add the left node to the stack
    		}
    	}
    	return pairs;																// return the list of pairs
    }
    
    /* returns the number of key-value pairs in the map */
    public int size()
    {
    	int count = 0;
    	for(KeyValuePair<K,V> kvp : this.entrySet())								// for each pair in the map
    	{
    		count++;																// increment count
    	}
    	return count;																// return the number of pairs
    }
    
    /* removes all mappings from this MapSet */
    public void clear()
    {
    	this.comp = comp;															// initialize the comparator
		this.size = 0;																// set the size of the map to 0
		this.root = null;															// the map is empty, so there is no root node
    }
    
    /* returns the number of levels in the tree */
    public int getDepth() 
    { 
		if (this.root == null) 														// if there is no root node
		{
			return 0;																// the depth of the tree is 0
		}
		else 
		{
			return this.root.getDepth();
		}
	 }
	 
	 /* returns the number of collisions that occurred while building the map */
	public int getCollisions()
	{
		return 0;
	}
    
    /* each node in the binary search tree map */
    private class TNode 
    {
		TNode left;																	// the left child node of the root node
		TNode right;																// the right child node of the root node
		KeyValuePair<K,V> data;														// KeyValuePair to hold the (key, value) data at this node

		/* constructor, given a key and a value */
		public TNode( K k, V v ) 
		{
			this.data = new KeyValuePair<K,V>(k,v);									// initialize the KeyValuePair
		}

		/* inserts the TNode in the subtree rooted at this node, returns the value associated with the key in the subtree rooted at this node or null */
		public V put( K key, V value, Comparator<K> comp ) 
		{
			if(comp.compare(key, data.getKey()) < 0)								// left subtree	
			{             
				if(left != null)             										// if there already a left node
				{                 
					left.put(key, value, comp);										// insert the node in the left subtree
					return data.getValue();											
				}             
				else             													// if there is not already a left node
				{                 
					left = new TNode(key,value);             						// create a new node in the left subtree
				}         
			}         
			else if(comp.compare(key, data.getKey()) > 0)							// right subtree
			{
				if (right != null)													// if there is already a right node
				{		
					right.put(key, value, comp);									// insert the node in the right subtree
					return data.getValue();											
				}
				else																// if there is not already a right node
				{
					right = new TNode(key, value);									// create a new node in the right subtree
				}
			}
			else																	// if the key is the same (comp.compare == 0)
			{								
				data.setValue(value);												// update the value
			}
		return null;
		}

		/* returns the value associated with the key or null */
		public V get( K key, Comparator<K> comp ) 
		{
			if(comp.compare(key, data.getKey()) == 0)								// if the given key is the same as the current key
			{
				return data.getValue();												// return the current value of the data pair
			}

			if(comp.compare(key, data.getKey()) < 0 )								// if the node is in the left subtree
			{
				return left == null ? null : this.left.get(key, comp);				// return the value at that node or null
        	}
			else																	// if the node is in the left subtree
			{
				return right == null ? null : this.right.get(key, comp);			// return the value at that node or null
			}
		}     
		
		/* returns the depth of the tree */
		public int getDepth() 
		{ 
			if(this.left == null && this.right == null) 
			{
				return 1;
			}
			else if(this.left == null) 
			{
				return 1 + this.right.getDepth();
			}
			else if(this.right == null) 
			{
				return 1 + this.left.getDepth();
			}
			else 
			{
				return 1 + Math.max(this.left.getDepth(), this.right.getDepth());
			}
		}
	}

    // test function
    public static void main( String[] argv ) 
    {
    		AscendingString as = new AscendingString();
            BSTMap<String, Integer> bst = new BSTMap<String, Integer>( as );	// create a BSTMap

            bst.put( "twenty", 20 );
            bst.put( "ten", 10 );
            bst.put( "eleven", 11 );
            bst.put( "five", 5 );
            bst.put( "six", 6 );
            
            System.out.println("test entrySet method (should print (twenty, 20), (ten, 10), (eleven, 11), (five, 5), (six, 6))");
            System.out.println( bst.entrySet() );

			System.out.println("test get method (should print 11)");
            System.out.println( bst.get( "eleven" ) );
            
            System.out.println("test get method (should print null)");
            System.out.println( bst.get( "three" ) );

            System.out.println("test put method (should print 11)");
            System.out.println( bst.put( "eleven", 12 ) ); 
            
            System.out.println("test put method (should print null)");
            System.out.println( bst.put( "three", 3 ) );
            
            System.out.println("test contains method (should print true)");
            System.out.println( bst.containsKey("twenty") );
            
            System.out.println("test contains method (should print false)");
            System.out.println( bst.containsKey("two") );
            
            System.out.println("test keySet method (should print twenty, ten, eleven, five, six, three)");
            System.out.println( bst.keySet() );
            
            System.out.println("test values method (should print 20, 10, 11, 5, 6, 3)");
            System.out.println( bst.values() );
           
            System.out.println("test entrySet method (should print (twenty, 20), (ten, 10), (eleven, 11), (five, 5), (six, 6), (three, 3))");
            System.out.println( bst.entrySet() );
            
            System.out.println("test size method (should print 6)");
            System.out.println( bst.size() );
            
            System.out.println("test clear method");
            bst.clear();
            System.out.println( bst.entrySet() );
            System.out.println( "size (should be 0): " + bst.size() );
    }
}
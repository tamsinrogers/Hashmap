/**
 * File: Hashmap.java
 * Author: Tamsin Rogers
 * Date: 4/14/20
 */

import java.util.*;
import java.util.Comparator;
import java.util.ArrayList;

public class Hashmap<K,V> implements MapSet<K,V> 
{
	private Object[] hashtable;  																// the hash table (an array of linked lists)
	private int size;  																			// the number of key value pairs in the hash table
	private Comparator<K> comp;																	// Comparator<K> to compare Keys
	private int collisions;																		// the number of key collisions

	/* constructor */
	public Hashmap(Comparator<K> comp) 
	{
		this.comp = comp;																		// initialize the comparator
		this.size = 0;																			// initialize the size of the hash map to 0 (empty)
		this.collisions = 0;																	// initialize the number of collisions to 0
	}

	/* constructor, creates a hash table of the given size */
	public Hashmap(Comparator<K> comp, int capacity) 
	{
		hashtable = new Object[capacity];
	}
	
	@SuppressWarnings("unchecked")
	/* adds or updates a key-value pair, returns the old value at the given key */
	public V put(K key, V value) 
      {
      	int index = hash(key); 																	// the linked list index of the key in the hash map
      	LNode list = (LNode)hashtable[index]; 													// the linked list at this location
        V oldValue;
        
		while(list != null) 																	// go through the linked list at the given key
		{
			if((list.data.getKey()).equals(key))												// if the given key is found
			{
				oldValue = list.data.getValue();
				break;
			}
			list = list.next;																	// if not, go to the next node in the linked list
		}
		
		if(list != null) 																		// if the current key is the given key
		{
			oldValue = list.data.getValue();
			list.data.setValue(value);															// set the value of the current node to the given value
		}
		
		else 																					// if the current linked list is empty (the key is not in the list)
		{
			if(size >= 0.5 * hashtable.length) 													// if the hash table is too full
			{
			   resize();																		// resize the hash table
			}
			LNode newNode = new LNode(key, value);												// create a new node at the head of the list that has the given key and value
			newNode.next = (LNode)hashtable[index];
			hashtable[index] = newNode;
			size++;  																			// count the newly added key.
			oldValue = null;																	// this key was not already in the map, so the oldValue is null
			collisions++;
		}
		return oldValue;
      }

	@SuppressWarnings("unchecked")
	/* returns the value at the specified key or null */
    public V get(K key) 
      {
		int index = hash(key); 																	// the linked list index of the key in the hash map
      	LNode list = (LNode)hashtable[index]; 													// the linked list at this location
      	
		while(list != null) 																	// go through the linked list at the given key
		{
			if((list.data.getKey()).equals(key))												// if the given key is found
			{
			   return list.data.getValue();														// return the value at that key
			}
			list = list.next;  																	// if not, go to the next node in the linked list
		}
		return null;  																			// the key is not in the hash table
      }

	@SuppressWarnings("unchecked")
	/* removes the key value pair at the given key from the hash table, if it exists */
    public void remove(K key, Comparator<K> comp) 
      {  
		int index = hash(key); 																	// the linked list index of the key in the hash map
      	LNode list = (LNode)hashtable[index]; 													// the linked list at this location

		if(hashtable[index] == null) 															// if there are no keys in this location in the linked list
		{
			return; 
		}
		if(comp.compare(list.data.getKey(), key) == 0) 											// if the given key is found at the first location
		{
			
			list = list.next;																	// set the head node to the next node
			size--; 																			// decrement the number of pairs in the table
			return;	
		}

		LNode before = (LNode)hashtable[index];  												// the node before the current node
		LNode current = before.next;  															// the current node, start at the second node in the linked list

		while(current != null && ! (comp.compare(current.data.getKey(), key)==0))				// while there is a next node that does not have the given key
		{
			current = current.next;																// go to the next node
			before = current;
		}
		if(current != null) 																	// if there is still a node in the linked list
		{
			before.next = current.next;															// the current node has to be that node, so remove it
			size--;  																			// decrement the number of pairs in the table
		}
      }

	@SuppressWarnings("unchecked")
	/* returns true if the map contains a key-value pair with the given key */
	public boolean containsKey(K key) 
    {
		int index = hash(key); 																	// the linked list index of the key in the hash map
      	LNode list = (LNode)hashtable[index]; 													// the linked list at this location

		while(list != null) 																	// go through the linked list at the given key
		{
			if((list.data.getKey()).equals(key))												// if the key is found
			{
			   return true;
			}
		list = list.next;																		// if not, check the next node in the linked list
		}
		return false;																			// the key does not exist in the hash table
	}

	 /* returns the number of key-value pairs in the hash map */
	public int size() 
	{
		return this.size;
	}

	/* the hash map's hash method */
	private int hash(K key) 
	{
	 	return (Math.abs(key.hashCode())) % hashtable.length;
	}

	@SuppressWarnings("unchecked")
	/* doubles the hash table's size and reorganizes the key value pairs */
	private void resize() 
	{
		this.collisions = 0;
		Object[] newtable = new Object[hashtable.length*2];										// make a new hash table that is twice the current size

		for(int i = 0; i < hashtable.length; i++) 												// repeat for each linked list in the hash table
		{
			LNode list = (LNode)hashtable[i]; 													// the linked list at the current location in the hash table
			while(list != null) 																// traverse through the linked list
			{
				LNode next = list.next;  														// go to the next node
				int hashvalue = (Math.abs(list.data.getKey().hashCode())) % newtable.length;	// calculate the hash value
				list.next = (LNode)newtable[hashvalue];												// take this node
				newtable[hashvalue] = list;														// make it the head of the linked list in the new table at the hash position
				list = next;  																	// go to the next node in the old table
			}
		}
		hashtable = newtable;  																	// update the hash table
	} 
	
	/* clears the hash map */
	public void clear()
	{
		this.size = 0;
		this.collisions = 0;
		hashtable = new Object[100000];
	}
	
	/* returns an ArrayList of all the keys in the hash map */
    public ArrayList<K> keySet()
    {
    	ArrayList<K> keys = new ArrayList<K>();													// create a new array list of keys
    	K key;
    	
    	for(KeyValuePair<K,V> kvp : this.entrySet())											// for each pair in the map
    	{
    		key = kvp.getKey();																	// get the key in the current pair
    		keys.add(key);																		// add the key to the list
    	}
    	return keys;																			// return the array list of keys
    }

	/* returns an ArrayList of all the values in the hash map */
    public ArrayList<V> values()
    {
    	ArrayList<V> values = new ArrayList<V>();												// create a new array list of values
    	V value;
    	
    	for(KeyValuePair<K,V> kvp : this.entrySet())											// for each pair in the map
    	{
    		value = kvp.getValue();																// get the value in the current pair
    		values.add(value);																	// add the value to the list
    	}
    	return values;																			// return the array list of values
    }
    
    @SuppressWarnings("unchecked")
    /* returns an ArrayList of all the key value pairs in the hash map */
    public ArrayList<KeyValuePair<K,V>> entrySet()
    {
    	ArrayList<KeyValuePair<K,V>> pairs = new ArrayList<KeyValuePair<K,V>>();				// create the arraylist to store the pairs	
    	
    	for (int i=0; i<hashtable.length; i++) 													// go through each linked list in the hash table
		{
			LNode list = (LNode)hashtable[i]; 
			if(list != null) 																// traverse through the linked list
			{
				pairs.add(list.data);															// add each key value pair in the linked list to the list
			}
    	}	
    	return pairs;
    }
    
    /* returns the number of levels in the tree (only for BSTMap, does not return anything for hashmap) */
    public int getDepth() 
    {
    	return 0;
    }
    
    /* returns the number of collisions that occurred while building the map */
    public int getCollisions()
    {
    	return this.collisions;
    }
    
    /* the nodes within the linked list */
	private class LNode 
	{
		 private LNode next;																	// the next node in the linked list
		 private KeyValuePair<K,V> data;														// KeyValuePair to hold the (key, value) data at this node
		 
		 /* constructor, given a key and a value */
		public LNode( K k, V v ) 
		{
			this.data = new KeyValuePair<K,V>(k,v);												// initialize the KeyValuePair
		}
	}
    
    /* test function */
    public static void main( String[] argv ) 
    {
    		AscendingString as = new AscendingString();
            Hashmap<String, Integer> test = new Hashmap<String, Integer>( as , 10);	

			System.out.println("adding (one, 1) to the map");
            test.put( "one", 1 );
            System.out.println("adding (three, 3) to the map");
            test.put( "three", 3 );
            
            System.out.println("test entrySet method (should print (one, 1), (three, 3)");
            System.out.println( test.entrySet() );
            
            System.out.println("test contains method (should print true)");
            System.out.println( test.containsKey("three") );
            
            System.out.println("test contains method (should print false)");
            System.out.println( test.containsKey("six") );
            
            System.out.println("test keySet method (should print one, three)");
            System.out.println( test.keySet() );
            
            System.out.println("test valueSet method (should print 1, 3)");
            System.out.println( test.values() );

			System.out.println("test get method (should print 1)");
            System.out.println( test.get( "one" ) );
            
            System.out.println("test get method (should print null)");
            System.out.println( test.get( "four" ) );

            System.out.println("test put method (should print 1)");						
            System.out.println( test.put( "one", 4 ) ); 
            
            System.out.println("test put method (should print null)");					
            System.out.println( test.put( "four", 4 ) );
           
            System.out.println("test entrySet method (should print (one, 4), (three, 3), (four, 4))");
            System.out.println( test.entrySet() );
            
            
            System.out.println("test keySet method");
            System.out.println( test.keySet() );
            
            System.out.println("test valueSet method");
            System.out.println( test.values() );
            
            System.out.println("test size method (should print 3)");
            System.out.println( test.size() );
            
            System.out.println("test remove method (size should be 2)");
            test.remove("three", as);
            System.out.println(test.size());
            
            System.out.println("test clear method");
            test.clear();
            System.out.println( test.entrySet() );
            System.out.println( "size (should be 0): " + test.size() );
    }
} 


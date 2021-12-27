/**
 * File: KeyValuePair.java
 * Author: Tamsin Rogers
 * Date: 4/7/20
 */
 
 import java.util.Iterator;
 import java.util.*;

/*  the pairs of keys and values used by the binary search tree */
public class KeyValuePair<Key,Value>
{
	private Key theKey;										// the key in the pair
	private Value theValue;									// the value in the pair
	
	/* constructor method, initializes key and value */
	public KeyValuePair( Key k, Value v )
	{
		this.theKey = k;									// initialize the key
		this.theValue = v;									// initialize the value
	}	
	
	/* returns the key */
	public Key getKey()
	{
		return this.theKey;
	}
	
	/* returns the value */
	public Value getValue()
	{
		return this.theValue;
	}
	
	/* sets the value */
	public void setValue(Value v)
	{
		this.theValue = v;
	}
	
	/* returns a string containing the key and value */
	public String toString()
	{
		String s = "(" + this.theKey + ", " + this.theValue + ")";
		return s;
	}
	
	/* tests all of the methods */
	public static void main(String[] args) 
	{
		KeyValuePair<String,Integer> test = new KeyValuePair<String,Integer>("test",10);
		
		System.out.println(test.toString());				// print the pair as a string
		System.out.println("key: " + test.getKey());		// print the pair's key
		System.out.println("value: " + test.getValue());	// print the pair's value
		System.out.println("setting value to 5");			
		test.setValue(5);									// set the value of the pair to 5
		System.out.println(test.toString());				// print the pair as a string
	}
}
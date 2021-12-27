/*
Tamsin Rogers
4/7/20
AscendingString.java
CS 231 Project 6
*/
import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;
import java.util.stream.Stream;


public class AscendingString implements Comparator<String> 
{
	/* compares strings a and b by calling the compareTo method and returns an int */
	public int compare( String a, String b ) 
	{
		return a.compareTo(b);
	}
}


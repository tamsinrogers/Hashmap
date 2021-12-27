/**
 * File: WordCounter2.java
 * Author: Tamsin Rogers
 * Date: 4/17/20
 */
 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File; 
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*; 
import java.util.*; 

/*  analyzes, reads, and writes text files */
public class WordCounter2
{
	private MapSet<String, Integer> map;											// the map
	private int count;																// the total word count

	/* constructor, data_structure is either "bst" or "hashmap", creates the appropriate data structure and stores it in the map field */
	public WordCounter2( String data_structure )
	{
		if(data_structure.equals("bst"))
		{
			map = new BSTMap<String, Integer>( new AscendingString() );				// create a binary search tree
		}
		if(data_structure.equals("hashmap"))
		{
			map = new Hashmap<String, Integer>( new AscendingString() , 100000);		// create a hashmap
		}
	}

	/* reads the given text file, returns an ArrayList list of all the words in that file */
	public ArrayList<String> readWords( String filename )
	{
		ArrayList<String> wordlist = new ArrayList<String>();	
		try
		{
			FileReader fileReader = new FileReader(filename);				
			BufferedReader bufferedReader = new BufferedReader(fileReader);	
			String line = bufferedReader.readLine();			

			while(line != null)														// go through the line
			{
				String[] words = line.split("[^a-zA-Z0-9']");						// split the line into words
				
				for (int i = 0; i < words.length; i++) 								// for each word in the line
				{
					String word = words[i].trim().toLowerCase();					// the current word
			
					if(word.length() != 0)											// don't process words of length 0
					{
						wordlist.add(word);											// add the word to the list
						count++;													// increment the total word count
					}
				}
				line = bufferedReader.readLine();									// read the next line
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException ex) 
		{
			System.out.println("Board.read():: unable to open file " + filename );
		}
		catch(IOException ex) 
		{
			System.out.println("Board.read():: error reading file " + filename);
		}
		return wordlist;
	}
	
	/* reads the file and builds the map */
	public double analyze(String filename)
	{
		double start = System.currentTimeMillis();								// start the timer
		try
		{
			FileReader fileReader = new FileReader(filename);				
			BufferedReader bufferedReader = new BufferedReader(fileReader);	
			String line = bufferedReader.readLine();					
			
			while(line != null)													// go through the line
			{
				String[] words = line.split("[^a-zA-Z0-9']");					// split the line into words
				
				for (int i = 0; i < words.length; i++) 							// for each word in the line
				{
					String word = words[i].trim().toLowerCase();				// the current word
					
					int value = 0;												// reset the value for each word
			
					if(word.length() != 0)										// don't process words of length 0
					{
						if(map.containsKey(word) == false)						// if this is the first occurrence of the word
						{
							value = 1;
							map.put(word, value);								// add the word to the map
						}
						else													// if the word is already in the map
						{	
							int newvalue = (map.get(word))+1;					// set the new value to the current value + 1
							map.put(word, newvalue);							// update the pair with the new value
						}
						count++;												// increment the total word count
					}
				}
				line = bufferedReader.readLine();								// read the next line
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException ex) 
		{
			System.out.println("Board.read():: unable to open file " + filename );
		}
		catch(IOException ex) 
		{
			System.out.println("Board.read():: error reading file " + filename);
		}
		double stop = System.currentTimeMillis();								// stop the timer
		double time = stop-start;			
		return time;
	}

	/* given an ArrayList of words, puts the words into the map data structure and returns the time taken in ms */
	public double buildMap( ArrayList<String> words )
	{
		double start = System.currentTimeMillis();								// start the timer
		for(String word : words)
		{
			if(map.containsKey(word))											// if this is not the first occurrence of the word
			{
				map.put(word, ((this.map.get(word))+1));						// update the pair with the new value
			}
		
			else
			{
				map.put(word,1);
			}
		}
		double stop = System.currentTimeMillis();								// stop the timer
		double time = stop-start;			
		return time;
	}
	
	/* clears the map */	
	public void clearMap()
	{
		map.clear();
	}

	/* returns the total word count from the last time readWords was called */	
	public int totalWordCount()
	{
		return this.count;
	}
	
	/* returns the unique word count (size of the map) */
	public int uniqueWordCount()
	{
		return map.size();														// return the size of the map
	}
	
	/* returns the depth of the tree */
	public int getDepth()
	{
		return map.getDepth();		
									
	}
	
	/* returns the number of times the word occurred in the list of words. 
	Query the data structure to get the information. Return 0 if the word does not exist in the data structure */
	public int getCount( String word )
	{
		if(map.get(word) != null)												// if the word exists in the map
		{
			return map.get(word);												// get the value at the given key
		}
		else																	// if the word does not exist in the map
		{
			return 0;
		}
	}
	
	/* returns the number of collisions that occurred while building the map */
    public int getCollisions()
    {
    	return map.getCollisions();
    }
	
	/* returns the frequency of the word in the list of words. 
	Query the data structure to get the word count and then divide by the total number of words to get the frequency. Return 0 if the word does not exist in the data structure */	
	public double getFrequency( String word )
	{
		if(map.get(word) != null)												// if the word exists in the map
		{
			float frequency = (float)(this.getCount(word));						// cast the frequency of the word to a float
			float count = (float)(this.totalWordCount());						// cast the total word count to a float
			float result = frequency/count;											
			return result;
		}
		else																	// if the word does not exist in the map
		{
			return 0;
		}
	}
	
	/* write a word count file given the current set of words in the data structure. 
	The first line of the file should contain the total number of words. Each subsequent line should contain a word and its frequency */
	public void writeWordCount( String filename )
	{
		try 
    	{
      		File file = new File(filename);											// create the file
      		FileWriter writer = new FileWriter(filename);							// the file writer
      		
      		String s = "totalWordCount: " + this.totalWordCount() + "\n";			// the header (total word count of the file)
      		writer.write(s);														// write the header
      		
      		ArrayList<KeyValuePair<String,Integer>> entries = map.entrySet();		// the list of key value pairs
      		
      		for(KeyValuePair<String, Integer> entry : entries)						// for each pair in the list
    		{
    			s = entry.getKey() + " " + + entry.getValue() + "\n";				// space out the keys and values
    			writer.write(s);													// write each line
    		}
    		writer.close();															// close the file
      	} 
      	catch (IOException e) 
      	{
      		System.out.println("error");
    	}
	}
	
	/* read a word count file given the filename. 
	The function should clear the map and then put all of the words, with their counts, into the map data structure */	
	public void readWordCount( String filename )
	{
		map.clear();
		
		try
		{
			FileReader fileReader = new FileReader(filename);				
			BufferedReader bufferedReader = new BufferedReader(fileReader);	
			String line = bufferedReader.readLine();							
			
			line = bufferedReader.readLine();									// read the first line (header)

			while(line != null)													// go through the line
			{
				String[] values = line.split(" ");								// split the line into values (split at the space)
				String key = values[0];											// the key is the first value in the line
				int value = Integer.parseInt(values[1]);						// the value is the second value in the line (cast to an int)
				map.put(key,value);												// put the pair in the map
				line = bufferedReader.readLine();								// read the next line
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException ex) 
		{
			System.out.println("Board.read():: unable to open file " + filename );
		}
		catch(IOException ex) 
		{
			System.out.println("Board.read():: error reading file " + filename);
		}
	}
	
	//public static void main(String[] args) throws InterruptedException 
	public static void main(String[] args)
	{
		System.out.println("Enter the year of the file you would like to analyze (2008-2015):");
		Scanner scan1 = new Scanner(System.in);
		String year = scan1.nextLine(); 
		String name = "reddit_comments_" + year + ".txt";						// get the right file								
		System.out.println("Analyzing comments from " + year);
		
		WordCounter2 test = new WordCounter2("bst");
		
		System.out.println("calculating BST statistics");
		test.analyze(name);														// read the file and build the map
		
		test.writeWordCount(year + ".txt");								// generate a word count file
		
		int totalwords = test.totalWordCount();
		int uniquewords = test.uniqueWordCount();
	
		int depth = test.getDepth();
		
		ArrayList<Double> times = new ArrayList<Double>();
		double time = 0;
		for(int i=0; i<5; i++)
		{
			time = test.analyze(name);											// builds map and returns time taken
			times.add(time);													// add the time to the times list
			test.clearMap();													// clear the map before running again
		}
		Collections.sort(times);												// sort the list of times
		double sum = 0;
		double counter = 0;
		for(int i=1; i<times.size()-1; i++)										// calculate average without smallest and largest values
		{
			sum = sum + times.get(i);
			counter++;
		}
		double bstavgtime = sum/counter;
		
		WordCounter2 test2 = new WordCounter2("hashmap");
		
		System.out.println("calculating Hashmap statistics");
		test2.analyze(name);														// read the file and build the map
	
		int collisions = test2.getCollisions();
	
		ArrayList<Double> times2 = new ArrayList<Double>();
		double time2 = 0;
		for(int i=0; i<5; i++)
		{
			time2 = test2.analyze(name);												// builds map and returns time taken
			times2.add(time2);														// add the time to the times list
			test2.clearMap();														// clear the map before running again
		}
		Collections.sort(times2);													// sort the list of times
		double sum2 = 0;
		double counter2 = 0;
		for(int i=1; i<times2.size()-1; i++)											// calculate average without smallest and largest values
		{
			sum2 = sum2 + times2.get(i);
			counter2++;
		}
		double hashavgtime = sum2/counter2;
		
		System.out.println("total words: " + totalwords);
		System.out.println("unique words: " + uniquewords);
		System.out.println("BST depth: " + depth);
		System.out.println("collisions: " + collisions);
		System.out.println("avg BST runtime: " + bstavgtime);
		System.out.println("avg Hashmap runtime: " + hashavgtime);
		
    }
}
package bigid;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.Semaphore; 


/**
* The Project is a basic implementation 
* of multithreading search in text file.
*
* @author  Benlolo Noam
* @version 1.0
* @since   2019-07-14 
*/
public class Main {
	public static final int BUNCH = 1000;
	public static final int CONCURRENT_THREADS = 10;
	public static File file = new File("src/bigid/file.txt");
	public static final String[] words = {"James","John","Robert","Michael","William","David",
			"Richard","Charles","Joseph","Thomas","Christopher",
			"Daniel","Paul","Mark","Donald","George","Kenneth",
			"Steven","Edward","Brian","Ronald","Anthony","Kevin",
			"Jason","Matthew","Gary","Timothy","Jose","Larry","Jeffrey",
			"Frank","Scott","Eric","Stephen","Andrew","Raymond","Gregory",
			"Joshua","Jerry", "Dennis","Walter","Patrick","Peter","Harold",
			"Douglas","Henry","Carl","Arthur","Ryan", "Roger"};
	
	
	public static void main(String[] args) {
		job();
	}
	
	public static void job() {
		Semaphore sem = new Semaphore(CONCURRENT_THREADS); // Use semaphore for limiting number of threads
		Aggregator aggregator = new Aggregator(words);
	    ArrayList<NaiveMatcher>  threads = new ArrayList<NaiveMatcher>();
		String text = ""; // The part of the text for the next thread
		int lineOffset = 0; // Pointer to save the offset where the thread begins to read the text
		int charOffset = 0; // Pointer to save the offset where the thread begins to read the text
		int counter = 0; // Counter to read only a BUNCH of lines
		int index = 0; // Number of thread created

        
        Scanner scan;
        try {
			scan = new Scanner(file);
			scan = scan.useDelimiter("\n");
					
			while (scan.hasNext()) {
				// Read the Bunch of lines we want
				while (counter < 1000 && scan.hasNext()) {
					text += scan.next() + "\n";
					counter++;
				}
				
				// Run the thread
				NaiveMatcher m = new NaiveMatcher(sem, index, words, text,lineOffset, charOffset);
				m.start();
				threads.add(m);
				
				// Update all the variables
				index ++;
				lineOffset += counter;
				charOffset += text.length();
				counter = 0;
				text = "";
			}
			
			
			// Join all the threads
			threads.forEach((thread) -> {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			
			// Get the results of each thread
			threads.forEach((thread) -> aggregator.add(thread.results));

			
			// Display the results
			aggregator.display();
			
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		}
       
	}

}

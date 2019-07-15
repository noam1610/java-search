package bigid;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Main {
	public static final int BUNCH = 1000;
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
		Aggregator aggregator = new Aggregator(words);
		String text = "";
		int lineOffset = 0;
		int charOffset = 0;
		int counter = 0;
		int index = 0;

        
        Scanner scan;
        try {
			scan = new Scanner(file);
			scan = scan.useDelimiter("\n");
					
			while (scan.hasNext()) {
				while (counter < 1000 && scan.hasNext()) {
					text += scan.next() + "\n";
					counter++;
				}
				NaiveMatcher m = new NaiveMatcher(index, words, text,lineOffset, charOffset);
				
				m.start();
				
				try {
					m.join();
					index ++;
					//System.out.println(m.index + " " + m.results);
					aggregator.add(m.results);
	
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				lineOffset += counter;
				charOffset += text.length();
				counter = 0;
				text = "";		
			}
			aggregator.display();
        } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}

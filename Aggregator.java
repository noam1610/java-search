package bigid;

import java.util.ArrayList;
import java.util.HashMap;


/**
* The Aggregator merge the results of
* each of the matchers.
* @author  Benlolo Noam
* @version 1.0
* @since   2019-07-14 
*/
public class Aggregator {
	
	public String[] words;
	public HashMap<String, ArrayList<Integer>> results;
	
	Aggregator(String [] words){
		this.words = words;
		results =  new HashMap<String, ArrayList<Integer>>();
	}
	
	
	 /**
	   * Function to concatenate 2 list
	   * @param v1 the first list
	   * @param v2 the second list
	   */
	public static ArrayList<Integer> addAll(ArrayList<Integer> v1, ArrayList<Integer> v2){
		v1.addAll(v2);
		return v1;
	}
	
	
	 /**
	   * Function to merge 2 hashmap where the value is a Arraylist
	   * @param v1 the first list
	   * @param v2 the second list
	   */
	public void add(HashMap<String, ArrayList<Integer>> result) {
		result.forEach((key, value) -> results.merge( key, value, (v1, v2) -> this.addAll(v1, v2)));
	}

	
	 /**
	   * Function to print the records for a 
	   * specific word
	   * @param key The word itself
	   * @param v2 The records for this key
	   */
	public void display_one(String key, ArrayList<Integer> numbers) {
		String output = key + " --> ";
		for(int i = 0; i < numbers.size()-1; i++) {
			if(i%2==0) {
				output += "[charOffset=" + Integer.toString(numbers.get(i));
			}
			else {
				output += ", lineOffset=" + Integer.toString(numbers.get(i)) + "], ";	
			}
		}
		System.out.println(output);
	}
	
	
	/**
	   * Function to print all the records
	   */
	public void display() {
		results.forEach((key, value) -> this.display_one(key, value));
	}
}

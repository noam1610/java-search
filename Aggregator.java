package bigid;

import java.util.ArrayList;
import java.util.HashMap;

public class Aggregator {
	
	public String[] words;
	public HashMap<String, ArrayList<Integer>> results;
	
	Aggregator(String [] words){
		this.words = words;
		results =  new HashMap<String, ArrayList<Integer>>();
	}
	
	public static ArrayList<Integer> addAll(ArrayList<Integer> v1, ArrayList<Integer> v2){
		v1.addAll(v2);
		return v1;
	}
	
	public void add(HashMap<String, ArrayList<Integer>> result) {
		//result.forEach((key, value) -> results.merge( key, value, (v1, v2) -> v1.addAll(v2) ? v1 : v2));
		result.forEach((key, value) -> results.merge( key, value, (v1, v2) -> this.addAll(v1, v2)));
		//System.out.println(results);
	}

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
	
	public void display() {
		results.forEach((key, value) -> this.display_one(key, value));
	}
}

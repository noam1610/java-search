package bigid;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.Semaphore; 

/**
* The NaiveMatcher will search a list of words
* in a String. 
* For each word, return a list of integers :
* [ char1, line1, char2, line2 ...]
* The first is the char offset and the second is the line offset
*
* @author  Benlolo Noam
* @version 1.0
* @since   2019-07-14 
*/
public class NaiveMatcher extends Thread {
	
	private String text;
	private Integer lOffset;
	private Integer cOffset;
    public HashMap<String, ArrayList<Integer>> results;
	private ArrayList<Integer> lineOffset = new ArrayList<Integer>();
	public Integer index;
	private Semaphore sem; 

	
	final String[] words;

	
	NaiveMatcher(Semaphore sem, Integer index, String[] words, String text, Integer lOffset,Integer cOffset){
		this.words = words;
		this.sem = sem;
		this.text = text;
		this.index = index;
		this.lOffset = lOffset;
		this.cOffset = cOffset;
		this.results = new HashMap<String, ArrayList<Integer>>();
		lineOffsetTool();
	}
	
	/**
	   * This method will parse the text and find all jumplines.
	   * This will allow then easily to match between char index
	   * and line index.
	   * The result is a ArrayList<Integer> with the char indexes
	   * of \n
	   */
	public void lineOffsetTool() {
		Pattern findWordPattern = Pattern.compile("\n");
		Matcher matcher = findWordPattern.matcher(this.text);
		while(matcher.find()) {
		    int offsetStart = matcher.start();
		    int offsetEnd = matcher.end();
		    lineOffset.add(offsetStart);
		}	
	}
	
	
	/**
	   * This method will match between a char Offset and its line
	   * @param index This is the char offset of the word occurence
	   */
	public int lineOffset(Integer index) {
		int lo = 0;
		for(int i = lineOffset.size()-1; i>= 0; i--) {
			if (index > lineOffset.get(i)) {
				lo = i +1;
				break;
			}
		}
		return lOffset + lo;
	}
	
	
	
	/**
	   * This method will search for all occurences of
	   * a specific word in the text
	   * @param w This is the word to search in the text
	   */
	public ArrayList<Integer> searchWordInText(String w) {
		ArrayList<Integer> wordReferences = new ArrayList<Integer>();
		
		Pattern findWordPattern = Pattern.compile(w);
		Matcher matcher = findWordPattern.matcher(this.text);
		
		while(matcher.find()) {
		    int offsetStart = matcher.start();
		    int offsetEnd = matcher.end();
		    wordReferences.add(cOffset + offsetStart);
		    wordReferences.add(lineOffset(offsetStart));
		}
		return wordReferences;
	}
	
	
	/**
	   * This method will search for all occurences in the text
	   */
	public void search() {
		for (int i=0; i< words.length; i++)
		{
			results.put(words[i], searchWordInText(words[i]));
		}
	}
	
	/**
	   * This method is the one called when the thread is started
	   */
	public void run() {
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		search();
		sem.release();
	}

}

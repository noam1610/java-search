package bigid;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NaiveMatcher extends Thread {
	
	private String text;
	private Integer lOffset;
	private Integer cOffset;
    public HashMap<String, ArrayList<Integer>> results;
	private ArrayList<Integer> lineOffset = new ArrayList<Integer>();
	public Integer index;

	
	final String[] words;

	
	NaiveMatcher(Integer index, String[] words, String text, Integer lOffset,Integer cOffset){
		this.words = words;
		this.text = text;
		this.index = index;
		this.lOffset = lOffset;
		this.cOffset = cOffset;
		this.results = new HashMap<String, ArrayList<Integer>>();
		lineOffsetTool();
	}
	
	public void lineOffsetTool() {
		Pattern findWordPattern = Pattern.compile("\n");
		Matcher matcher = findWordPattern.matcher(this.text);
		while(matcher.find()) {
		    int offsetStart = matcher.start();
		    int offsetEnd = matcher.end();
		    lineOffset.add(offsetStart);
		}	
	}
	
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
	
	public void search() {
		for (int i=0; i< words.length; i++)
		{
			results.put(words[i], searchWordInText(words[i]));
		}
	}
	
	public ArrayList<Integer> searchWordInText(String w) {
		ArrayList<Integer> wordReferences = new ArrayList<Integer>();
		// wordReferences is the list of lineOffset, charOffset for each occurence
		
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
	
	public void run() {
		//search();
		search();
	}

}

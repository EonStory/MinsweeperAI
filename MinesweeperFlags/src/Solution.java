import java.util.ArrayList;
import java.util.LinkedHashMap;

//this class counts the number of solutions with m mines for a beach. 
@SuppressWarnings("serial")
public class Solution extends LinkedHashMap<Integer, Integer> { //maps square number to the nubmer of permutations in which its true
	int permutations;
	int mines;
	
	public Solution(int mines, ArrayList<Integer> squa) {
		super();
		this.permutations = 0;
		this.mines = mines;
		for (int i = 0; i < squa.size(); i++) { //initialize the squares
			this.put(squa.get(i), 0);
		}
	}
}

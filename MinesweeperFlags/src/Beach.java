import java.util.*;


public class Beach {
	LinkedHashMap<Integer, Constraint> cons = new LinkedHashMap<Integer, Constraint>();
	LinkedHashMap<Integer, ArrayList<Constraint>> cSonSquare = new LinkedHashMap<Integer, ArrayList<Constraint>>();
	ArrayList<Equiv> equivs = new ArrayList<Equiv>();
	ArrayList<Integer> squares = new ArrayList<Integer>();
	ArrayList<Solution> sols = new ArrayList<Solution>();
	
	public ArrayList<Solution> solutions(Beach beach) {		
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		for (int i = 0; i < squares.size(); i++) {
			solutions.add(new Solution(i, beach.squares));
			
		}
		int[][] nchoosek = new int[equivs.size()][2];//first is N, 2nd is K
		return solutionsHelper(equivs, 0, 0, nchoosek, solutions);
	}
	
	//order may not be optimal.	
	//optimal way to do this is to always check its legal after each change rather than blindly iterating through.
	//remember uploading the pastebin thing with fnopnew and what it says in brackets with impossibe	
	int[] counter = new int[70];	
	public ArrayList<Solution> solutionsHelper(ArrayList<Equiv> equivs, int ei, int m, int[][] nck, ArrayList<Solution> s) { //index of the Equiv
		for (int i = 0; i < equivs.get(ei).size() + 1; i++) {//+1 because max mines is same as size
			if (isLegal(equivs.get(ei), i)) {
				if (ei + 1 == equivs.size()) { //if at the end
					for (int j = 0; j < s.get(m).size(); j++) {
						s.get(m).put(equivs.get, );
					}
				}
				else {
					solutionsHelper(equivs, ei + 1, m + i, combos * Perm.choose(equivs.get(ei).size(), i));
				}
				for (Constraint c: cSonSquare.get(equivs.get(ei).get(0))) {//undo the side effects of isLegal
					c.us -= equivs.get(ei).size();
					c.vm -= i;
				}
			}
		}
	}
	//for (Constraint c: cons.get(e.get(0))) {
	public boolean isLegal(Equiv e, int m) {	//this method changes us and vm
		for (Constraint c: cSonSquare.get(e.get(0))) { //constraints on square 0 are same as on 1, 2, 3,..., n
			if (c.vm + m > c.mines) {
				return false;
			}
			if (c.size() - c.us - e.size() < c.mines - c.vm - m) {
				return false;
			}
		}
		for (Constraint c: cSonSquare.get(e.get(0))) {//its legal do update the vm and us
			c.vm += m;
			c.us += e.size();
		}
		return true;
	}
	
	
}

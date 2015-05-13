//this class contains only static methods which return the following
//probability of a mine being in sqaure x
//nim sum of board

//there are islands, beaches and the ocean.
//the game starts with the entire board as the ocean

//islands are collections of adjacent visible squares which are not mines
//beaches are collections of adjacent non visible squares which may contain a mine and are adjacent to an island
//seas are collections of adjacent squares which are not part of islands or beaches & are not visible mines

import java.util.*;
//all the methods use the incomplete board state where -1 is a visible mine and -2 is not visible square
public class SolveConstraint {
	//test
	public static void main(String[] args) {
		
		/*
		System.out.println("input a board");
		Scanner input = new Scanner(System.in);
		ArrayList<Integer> ib = new ArrayList<Integer>();
		int width;
		while (true) {
			int q = input.nextInt();
			if (q == -99) {
				width = input.nextInt();
				break;
			}
			ib.add(q);
		}
		*/
		
		//SolveConstraint test = new SolveConstraint(ib.toArray(), 5);	
		//SolveConstraint test = new SolveConstraint(new int[] {-2,-2,-2,-2,-2,-2,-2,-2,2,-2,-2,2,-2,-2,-2,-2,-2,-2,2,-2,-2,-2,-2,-2,-2}, 5);
		SolveConstraint test = new SolveConstraint(new int[] {
				-2,-2,-2,-2,-2,-2,-2,
				-2, 4,-2, 4,-2, 4,-2,
				-2,-2,-2,-2,-2,-2,-2,
				-2, 4,-2, 4,-2, 4,-2,
				-2,-2,-2,-2,-2,-2,-2,
				-2, 4,-2, 4,-2, 4,-2,
				-2,-2,-2,-2,-2,-2,-2,}, 7);
		System.out.println(test.csOnSquare);
		System.out.println(test.seaSize);
		test.beach(0);
		System.out.println("eh");
		System.out.println(test.equivalent(test.beach(2)));
		System.out.println("rawr");
		test.solutions(test.equivalent(test.beach(2)));
		System.out.println(test.counter[2]);
		long q = 0;
		for (int i = 0; i < 70; i++) {
			System.out.println(i + " mines in " + test.counter[i] + " solutions.");
			q += test.counter[i];
		}
		System.out.println("q is " + q);
	}
	
	int[] b;
	int w;
	int seaSize; //how large is the sea?
	Double[] mineProb;
	Double seaProb;
	//Beach[] beaches; // maps squares to a beach. seas have no beach so they'll point to null. visible squares doont have one either
	ArrayList<Beach> beaches = new ArrayList<Beach>();
	ArrayList<ArrayList<Constraint>> csOnSquare = new ArrayList<ArrayList<Constraint>>();	
	final int NONVIS_SQUARE = -2;
	final int VIS_MINE = -1;
	final Double UNCALCULATED_P = -7.5;
		
	//returns a double sized square arround given square.
	//this is because constraints 2 square away may affect current constraint
	
	public SolveConstraint(int[] board, int width) {
		b = board;
		w = width;
		seaSize = board.length;
		mineProb = new Double[board.length];
		//beaches = new Beach[board.length];
		
		Arrays.fill(mineProb, seaProb);
		
		for (int i = 0; i < board.length; i++) {
			csOnSquare.add(new ArrayList<Constraint>());
		}
		
		//parses all lthe constraints at once. this should really never be used in a proper game
		//since the board would be blank
		ArrayList<Constraint> lulz = new ArrayList<Constraint>();
		for (int i = 0; i < board.length; i++) {
			if (board[i] > -1) {
				Constraint c = new Constraint(board[i]);
				int[] q = adjSquares(i);
				for (int j = 0; j < q.length; j++) {
					System.out.println("q[j] is " + q[j]);
					System.out.println("board " + board[q[j]]);
					if (board[q[j]] == -2) {
						c.add(q[j]);
					}					
				}
				lulz.add(c);
				addAllConstraints(lulz);
				lulz.clear();
			}
			else if (board[i] == -1) {
				Constraint c = new Constraint(1);
				c.add(board[i]);
			}
		}
	}	
	
	public void logic(Constraint c1, Constraint c2) {
		ArrayList<Integer> intersection = intersection(c1, c2);		
		int maxTrue = Math.min(intersection.size(), c1.mines);
		int minTrue = Math.max(c2.mines - (c2.size() - intersection.size()), 0);
		if (maxTrue == minTrue) {
			ArrayList<Constraint> allNeedAdding = new ArrayList<Constraint>();			
			Constraint c1remainder = new Constraint(c1.mines - maxTrue);		
			Constraint c2remainder = new Constraint(c2.mines - maxTrue);
			
			for (int i = 0; i < c1.size(); i++) {
				if (intersection.contains(c1.get(i)) == false) {
					c1remainder.add(c1.get(i));
				}
			}
			for (int i = 0; i < c2.size(); i++) {
				if (intersection.contains(c2.get(i)) == false) {
					c2remainder.add(c2.get(i));
				}
			}
			Constraint newC = new Constraint(maxTrue);
			newC.addAll(intersection);
			
			System.out.println("deleting c1 " + c1);
			System.out.println("deleting c2 " + c2);
			deleteConstraint(c1);
			deleteConstraint(c2);
			
			System.out.println("c1remainder is " + c1remainder);
			System.out.println("c2remainder is " + c2remainder);			
			System.out.println("newC is " + newC);
			
			allNeedAdding.add(newC);
			allNeedAdding.add(c1remainder);
			allNeedAdding.add(c2remainder);		
			addAllConstraints(allNeedAdding);
		}
	}
	
	//this might be the best way to do it due to the order of the beaches being good
	public void solutions(ArrayList<Equiv> equivs) {
		int squares = 0;
		for (int i = 0; i < equivs.size(); i++) {
			squares += equivs.get(i).size();
		}
		//int[][] count = new int[squares][squares]; //the first bit is the maximum number of possible mines
		///its a lazy calculation but meh. instead of actually working it out i have just assumed the max mines is the number of quares
		//the second bit of count containts the number of solutions in which that square had a mine on (with the givennumber of mines)
		//the first square acts as count of mines eg count[4] is when the solution has 4 mins and count[4][3] is the number of
		//solutions with 4 mines in which the 3rd square contains a mine
		
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		for (int i = 0; i < squares; i++) {
			solutions.add(new Solution(i));
		}
		int[][] nchoosek = new int[equivs.size()][2];//first is N, 2nd is K
		solutionsHelper(equivs, 0, 0, nchoosek, solutions);
	}
	
	//order may not be optimal.	
	//optimal way to do this is to always check its legal after each change rather than blindly iterating through.
	//remember uploading the pastebin thing with fnopnew and what it says in brackets with impossibe	
	int[] counter = new int[70];	
	public void solutionsHelper(ArrayList<Equiv> equivs, int ei, int m, int[][] nck, ArrayList<Solution> s) { //index of the Equiv
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
				for (Constraint c: csOnSquare.get(equivs.get(ei).get(0))) {//undo the side effects of isLegal
					c.us -= equivs.get(ei).size();
					c.vm -= i;
				}
			}
		}
	}
	
	public int combos(ArrayList<choose> ch) {
		int x = 1;
		for (choose c: ch) {
			x *= Perm.choose(c.n, c.k);
		}
		return x;
	}
	
	public int mines(int[] a) {
		int num = 0;
		for (int i = 0; i < a.length; i++) {
			num += a[i];
		}
		return num;
	}
	
	//computes probability of all mines. then updates mineProb
	public void computeProb() {
		
	}
	
	//is it legal to put m mines into equiv set e, considering the current mines in all the constraints?
	//if it is, change all the relevant constraints current mines
	public boolean isLegal(Equiv e, int m) {	//this method changes us and vm
		for (Constraint c: csOnSquare.get(e.get(0))) {
			if (c.vm + m > c.mines) {
				return false;
			}
			if (c.size() - c.us - e.size() < c.mines - c.vm - m) {
				return false;
			}
		}
		for (Constraint c: csOnSquare.get(e.get(0))) {//its legal do update the vm and us
			c.vm += m;
			c.us += e.size();
		}
		return true;
	}
	
	//beaches never overlap other beaches, but the constraints inside them must overlap
		//method does up to 8^2 tries i tihnk so this O(c) where c is the number of constraints in the beache
	
	//creates sets of equivalent groupds
	public ArrayList<Equiv> equivalent(Beach beach) {		
		HashSet<Integer> done = new HashSet<Integer>(); //contains the squares which are already solved
		ArrayList<Equiv> equiv = new ArrayList<Equiv>();
		for (Constraint c: beach.cons) {
			for (int i = 0; i < c.size(); i++) {
				if (done.contains(c.get(i))) {
					continue;
				}
				Equiv e = new Equiv();
				e.add(c.get(i));				
				for (int j = i + 1; j < c.size(); j++) {
					if (done.contains(c.get(j))) {
						continue;
					}
					if (csOnSquare.get(c.get(i)).size() != csOnSquare.get(c.get(j)).size()) {//cant be same class if number of constriants its in is different
						continue;
					}
					boolean bothInSameCons = true;
					for (Constraint d: csOnSquare.get(c.get(i))) {
						if (d.contains(c.get(j)) == false) {
							bothInSameCons = false;
							break;
						}
					}
					if (bothInSameCons == true) {						
						e.add(c.get(j));
						done.add(c.get(j));
					}	
				}
				equiv.add(e);
				done.add(c.get(i));
			}			
		}
		return equiv;
	}
	
	public Beach beach(int x) { //grabs the web of constraints coming from x. gets the beach for it
		Beach beach = new Beach();		
		beachHelper(x, beach);
		System.out.println("beach for " + x + " is " + beach);
		return beach;
	}
	
	public void beachHelper(int x, Beach beach) {
		for (int i = 0; i < csOnSquare.get(x).size(); i++) {
			if (beach.cons.contains(csOnSquare.get(x).get(i))) {
				continue;
			}
			
			beach.cons.add(csOnSquare.get(x).get(i));
			for (int j = 0; j < csOnSquare.get(x).get(i).size(); j++) {
				beachHelper(csOnSquare.get(x).get(i).get(j), beach);
			}
		}
	}
	
	public ArrayList<Integer> intersection(Constraint c1, Constraint c2) {		
		ArrayList<Integer> intersection = new ArrayList<Integer>();
		for (int i = 0; i < c1.size(); i++) {
			for (int j = 0; j < c2.size(); j++) {
				if (c1.get(i).equals(c2.get(j))) {
					intersection.add(new Integer(c1.get(i))); //dont want reference. might scare me. probably pointless to do this
				}
			}
		}
		return intersection;
	}
	
	public void deleteConstraint(Constraint c) {//c is constriant
		for (int i = 0; i < c.size(); i++) {
			csOnSquare.get(c.get(i)).remove(c); //O(1) not O(n) since 8 is the max elements
			//TODO maybe it should mark them as null instead of removing them
			//this way it cant be accidentally skipped over
		}	
	}	
	
	//adds constraint to onsquares
	public void addConstraint(Constraint c) {
		for (int i = 0; i < c.size(); i++) {
			csOnSquare.get(c.get(i)).add(c); //O(1) not O(n) since 8 is the max elements
			if (mineProb[c.get(i)] == seaProb) {
				mineProb[c.get(i)] = UNCALCULATED_P;
				seaSize--;
			}
		}
	}
	
	public void addAllConstraints(ArrayList<Constraint> constraints) {
		int length = constraints.size();
		//splitter logic here
		for (int i = 0; i < length; i++) {
			if (constraints.get(i).size() < 2) {
				continue;
			}
			if (constraints.get(i).mines == 0) {
				Constraint q = constraints.get(i);
				constraints.remove(i);
				for (int j = 0; j < q.size(); j++) {
					Constraint newC = new Constraint(0);
					newC.add(q.get(j));
					constraints.add(newC);
				}
			}
			else if (constraints.get(i).mines == constraints.get(i).size()) {
				ArrayList<Integer> q = constraints.get(i);
				constraints.remove(i);
				for (int j = 0; j < q.size(); j++) { //TODO check this is right and shouldnt be a 1 (almost certaint)
					Constraint newC = new Constraint(1);
					newC.add(q.get(j));
					constraints.add(newC);
				}
			}
		}		
		//System.out.println("adding all " + constraints);
		for (Constraint c: constraints) {		
			addConstraint(c);
		}
		for (Constraint c: constraints) {			
			updateConstraint(c);
		}
	}	
	
	//would putting a mine at location X oversaturate any of its adjacent squares?
	//note: doesnt factor in if it would exceed total number of mines
	//i dont think i need this method	
	//how many more mines does this square need before it is saturated?
	
	public void updateConstraint(Constraint c) {
		for (int i = 0; i < c.size(); i++) {
			if (csOnSquare.get(c.get(i)).contains(c)) {
				for (int j = 0; j < csOnSquare.get(c.get(i)).size(); j++) {
					if (c == csOnSquare.get(c.get(i)).get(j)) {
						continue;
					}
					logic(c, csOnSquare.get(c.get(i)).get(j));
					logic(csOnSquare.get(c.get(i)).get(j), c);
				}				
			}
		}
	}

	public int[] adjSquares(int x) {
		int[] adjSquares;		
		if (x % w > 0) {//minus left column			
			if (x % w < w - 1) { //minus right colum				
				if (x >= w) { //minus top row					
					if (x <= b.length - w) { //minus bottom row. remaining: centre squares
						adjSquares = new int[8];
						adjSquares[0] = x + 1;
						adjSquares[1] = x - w + 1;
						adjSquares[2] = x - w;
						adjSquares[3] = x - w - 1;
						adjSquares[4] = x - 1;
						adjSquares[5] = x - 1 + w;
						adjSquares[6] = x + w;
						adjSquares[7] = x + 1 + w;
					}
					else { //remaining: bottom centre row
						adjSquares = new int[5];
						adjSquares[0] = x + 1;
						adjSquares[1] = x - w + 1;
						adjSquares[2] = x - w;
						adjSquares[3] = x - w - 1;
						adjSquares[4] = x - 1;
					}
				}
				else { //remaining: top centre row
					adjSquares = new int[5];
					adjSquares[0] = x + 1;
					adjSquares[1] = x - 1;
					adjSquares[2] = x - 1 + w;
					adjSquares[3] = x + w;
					adjSquares[4] = x + w + 1;
				}
			}
			else { //remaining: right column
				if (x == w - 1) { //remaining: top right
					adjSquares = new int[3];
					adjSquares[0] = x - 1;
					adjSquares[1] = x + w - 1;
					adjSquares[2] = x + w;
				}
				else if (x == b.length - 1) {//remianing: bottom right
					adjSquares = new int[3];
					adjSquares[0] = x - w;
					adjSquares[1] = x - w - 1;
					adjSquares[2] = x - 1;
					
				}
				else {//remaining: centre right column
					adjSquares = new int[5];
					adjSquares[0] = x - w;
					adjSquares[1] = x - w - 1;
					adjSquares[2] = x - 1;
					adjSquares[3] = x + w - 1;
					adjSquares[4] = x + w;
				}
			}
		}
		else { //remaiinnig: left column
			if (x == 0) { //remaining: top left
				adjSquares = new int[3];
				adjSquares[0] = x + 1;
				adjSquares[1] = x + w;
				adjSquares[2] = x + w + 1;
			}
			else if (x == b.length - w) {//remianing: bottom left //off by 1 error :(
				adjSquares = new int[3];
				adjSquares[0] = x + 1;
				adjSquares[1] = x - w + 1;
				adjSquares[2] = x - w;
			}
			else {//remaining: centre left column
				adjSquares = new int[5];
				adjSquares[0] = x + 1;
				adjSquares[1] = x - w + 1;
				adjSquares[2] = x - w;
				adjSquares[3] = x + w;				
				adjSquares[4] = x + w + 1;
			}
		}
		return adjSquares;
	}
}

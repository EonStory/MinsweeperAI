//this class contains only static methods which return the following
//probability of a mine being in sqaure x
//nim sum of board

//there are islands, beaches and the ocean.
//the game starts with the entire board as the ocean

//islands are collections of adjacent visible squares which are not mines
//beaches are collections of adjacent non visible squares which may contain a mine and are adjacent to an island
//seas are collections of adjacent squares which are not part of islands or beaches & are not visible mines

import java.util.*;
import java.util.Map.Entry;
//all the methods use the incomplete board state where -1 is a visible mine and -2 is not visible square
public class SolveObj {
	//test
	public static void main(String[] args) {
		//SolveObj test = new SolveObj(new int[] {2,  2,  1,  1, -2,-2, -2, -2, -2, -2}, 5);
		
		/*
		Integer[] a = {2, 5, 6};
		Integer[] b = {2, 5, 6, 7};
		Integer[] c = {1, 6, 7, 8};
		Integer[] d = {1, 4, 7, 9};
		//Integer[] a = {1, 0, 5, 6, 7};
	//	Integer[] b = {1, 6, 7, 8};
		//Integer[] c = {2, 7, 8, 9};
		//Integer[] d = {2, 8, 9};
		
		ArrayList<ArrayList<Integer>> all = new ArrayList<>();
		all.add(new ArrayList<Integer>(Arrays.asList(a)));
		all.add(new ArrayList<Integer>(Arrays.asList(b)));
		all.add(new ArrayList<Integer>(Arrays.asList(c)));
		all.add(new ArrayList<Integer>(Arrays.asList(d)));
		
		test.addAllConstraints(all);
		//for (int i = 0; i < 4; i++) {
		//	
		//
		 
		//SolveObj test = new SolveObj(new int[] {2,  2,  1,  1, -2,-2, -2, -2, -2, -2}, 5);
		*/
		//SolveObj test = new SolveObj(new int[] {-2,-2,-2,-2,8,-2,-2,-2,-2,-2,3,-2,-2,-2,-2,-2,1,-2,-2,-2,-2}, 3);
		SolveObj test = new SolveObj(new int[] {2,  2,  1,  1, -2,-2, -2, -2, -2, -2}, 5);
		
		//ArrayList<Integer> cra1 = new ArrayList<Integer>(Arrays.asList(new Integer[] {1, 0, 5, 6, 7}));
		//ArrayList<Integer> cra2 = new ArrayList<Integer>(Arrays.asList(new Integer[] {0, 7}));
		//test.logic(cra1, cra2);
		//test.logic(cra2, cra1);
		 
		 
		System.out.println(test.onsquares);
		
	}
	
	int[] b;
	int w;
	int seaSize; //how large is the sea?
	HashMap<Integer, ArrayList<ArrayList<Integer>>> onsquares = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();	
	final int NONVIS_SQUARE = -2;
	final int VIS_MINE = -1;
		
	public void logic(ArrayList<Integer> c1, ArrayList<Integer> c2) {
		ArrayList<Integer> intersection = intersection(c1, c2);		
		int maxTrue = Math.min(intersection.size(), c1.get(0));
		int minTrue = Math.max(c2.get(0) - (c2.size() - 1 - intersection.size()), 0);
		if (maxTrue == minTrue) {
			ArrayList<ArrayList<Integer>> allNeedAdding = new ArrayList<ArrayList<Integer>>();			
			ArrayList<Integer> c1remainder = new ArrayList<Integer>();		
			ArrayList<Integer> c2remainder = new ArrayList<Integer>();			
			
			c1remainder.add(c1.get(0) - maxTrue);
			c2remainder.add(c2.get(0) - maxTrue);
			
			for (int i = 1; i < c1.size(); i++) {
				if (intersection.contains(c1.get(i)) == false) {
					c1remainder.add(c1.get(i));
				}
			}
			for (int i = 1; i < c2.size(); i++) {
				if (intersection.contains(c2.get(i)) == false) {
					c2remainder.add(c2.get(i));
				}
			}
			intersection.add(0, maxTrue);			
			
			deleteConstraint(c1);
			deleteConstraint(c2);
			allNeedAdding.add(intersection);
			allNeedAdding.add(c1remainder);
			allNeedAdding.add(c2remainder);		
			addAllConstraints(allNeedAdding);
		}
	}
	
	//splits up {0, a, b, c} into {0,a} {0, b} {0, c} or 3{a, b, c} into {1,a} {1, b} {1, c}
	public ArrayList<ArrayList<Integer>> splitter(ArrayList<Integer> c) {
		ArrayList<ArrayList<Integer>> yey = new ArrayList<ArrayList<Integer>>();
		if (c.size() == 2) {
		}
		else if (c.get(0) == 0) {
			for (int i = 1; i < c.size(); i++) {
				ArrayList<Integer> cNew = new ArrayList<Integer>();
				cNew.add(0);
				cNew.add(c.get(i));
				yey.add(cNew);
			}
		}
		else if (c.get(0) == c.size() - 1) {
			for (int i = 1; i < c.size(); i++) {
				ArrayList<Integer> cNew = new ArrayList<Integer>();
				cNew.add(1);
				cNew.add(c.get(i));
				yey.add(cNew);
			}
		}
		return yey;
	}
	
	public ArrayList<Integer> intersection(ArrayList<Integer> c1, ArrayList<Integer> c2) {		
		ArrayList<Integer> intersection = new ArrayList<Integer>();
		for (int i = 1; i < c1.size(); i++) {
			for (int j = 1; j < c2.size(); j++) {
				if (c1.get(i).equals(c2.get(j))) {
					intersection.add(new Integer(c1.get(i))); //dont want reference. might scare me. probably pointless to do this
				}
			}
		}
		return intersection;
	}
	
	public void selectSquare(int x) {
		
	}
	
	public void deleteConstraint(ArrayList<Integer> c) {//c is constriant
		for (int i = 1; i < c.size(); i++) {
			onsquares.get(c.get(i)).remove(c); //O(1) not O(n) since 8 is the max elements
			//TODO maybe it should mark them as null instead of removing them
			//this way it cant be accidentally skipped over
		}	
	}
	
	//doesnt add {} or {0} because its less than 2 in side, yeah..!
	public void addConstraint(ArrayList<Integer> c) {
		System.out.println("onsquares before is " + onsquares);
		for (int i = 1; i < c.size(); i++) {
			onsquares.get(c.get(i)).add(c); //O(1) not O(n) since 8 is the max elements
		}
		if (c.size() == 2) { //if theres only 1 square in the constraint (ie its contents are certain), reduce the sea size.
			System.out.println("c is " + c);
			seaSize--;			
		}
		System.out.println("seasize is " + seaSize);
		System.out.println("onsquare is "+ onsquares);
	}
	
	public void addAllConstraints(ArrayList<ArrayList<Integer>> constraints) {
		int length = constraints.size();
		//splitter logic here
		for (int i = 0; i < length; i++) {
			if (constraints.get(i).size() < 3) {
				continue;
			}
			if (constraints.get(i).get(0) == 0) {
				ArrayList<Integer> q = constraints.get(i);
				constraints.remove(i);
				for (int j = 1; j < q.size(); j++) {
					ArrayList<Integer> newC = new ArrayList<Integer>();
					newC.add(0);
					newC.add(q.get(j));
					constraints.add(newC);
				}
			}
			else if (constraints.get(i).get(0) == constraints.get(i).size() - 1) {
				ArrayList<Integer> q = constraints.get(i);
				constraints.remove(i);
				for (int j = 1; j < q.size(); j++) {
					ArrayList<Integer> newC = new ArrayList<Integer>();
					newC.add(1);
					newC.add(q.get(j));
					constraints.add(newC);
				}
			}
		}		
		System.out.println("adding all " + constraints);
		for (ArrayList<Integer> c: constraints) {		
			addConstraint(c);
		}
		for (ArrayList<Integer> c: constraints) {			
			updateConstraint(c);
		}
	}	
	
	public void updateConstraint(ArrayList<Integer> c) {
		for (int i = 1; i < c.size(); i++) {
			if (onsquares.get(c.get(i)).contains(c)) {
				for (int j = 0; j < onsquares.get(c.get(i)).size(); j++) {
					if (c == onsquares.get(c.get(i)).get(j)) {
						continue;
					}
					logic(c, onsquares.get(c.get(i)).get(j));
					logic(onsquares.get(c.get(i)).get(j), c);
				}				
			}
		}
	}
	//returns a double sized square arround given square.
	//this is because constraints 2 square away may affect current constraint
	
	public SolveObj(int[] board, int width) {
		b = board;
		w = width;
		seaSize = board.length;
		for (int i = 0; i < board.length; i++) {
			onsquares.put(i, new ArrayList<ArrayList<Integer>>());
		}
		
		ArrayList<ArrayList<Integer>> lulz = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < board.length; i++) {
			if (board[i] > -1) {
				ArrayList<Integer> c = new ArrayList<Integer>();
				c.add(board[i]);
				int[] q = adjSquares(i);
				for (int j = 0; j < q.length; j++) {
					if (board[q[j]] == -2) {
						c.add(q[j]);
					}					
				}
				System.out.println("c " + c);
				lulz.add(c);
				addAllConstraints(lulz);
				lulz.clear();
			}		
		}
	}
	
	//returns the number of visible adjacent numbers
	public int adjVisNum(int x) {
		int count = 0;
		int[] bob = adjSquares(x);
		for (int i = 0; i < bob.length; i++) {
			if (b[x] > -1) {
				count++;
			}
		}
		return count;
	}
	
	//would putting a mine at location X oversaturate any of its adjacent squares?
	//note: doesnt factor in if it would exceed total number of mines
	//i dont think i need this method	
	//how many more mines does this square need before it is saturated?
	
	//adjacent not visible squares
	public int[] adjNonVisSquares(int x) {
		if (b[x] < 0) {
			System.out.println("wtf are you doing. adjNonVisSquares shouldnt be used on hidden squares or mines");
			System.out.println("b[x] is " + b[x]);
		}
		int[] a = adjSquares(x);
		
		//annoying, 2 loops needed to do this bit
		//it creates a new array (of correct length) of all the squares which are non visible
		int countOfNonVis = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == -2) {
				countOfNonVis++;
			}
		}
		int[] nonVisAdj = new int[countOfNonVis];
		for (int i = 0; i < a.length; i++) {
			if (a[i] == -2) {
				nonVisAdj[--countOfNonVis] = a[i];
			}
		}		
		return nonVisAdj;
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
	
	public int countMines(int[] a) {
		int mines = 0;
		for (int i = 0; i < a.length; i++) {
			if (b[a[i]] == -1) {
				mines++;
			}
		}
		return mines;
	}
	
	//generate basic constraints. length is same length as board.
	
	//simplify. if set a is a subset of set b,  simplify set b	
}

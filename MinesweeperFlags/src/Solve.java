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
public class Solve {
	//test
	public static void main(String[] args) {
		//test speed of arrays
		long time = System.currentTimeMillis();
		int[] fudd = new int[10000000];
		for (int i = 0; i < 10000000; i++) {
			fudd[i] = 4;
		}
		time = System.currentTimeMillis() - time;
		System.out.println("Completed array in " + time + " milliseconds.");
		time = System.currentTimeMillis();
		ArrayList<Integer> bob = new ArrayList<Integer>();
		for (int i = 0; i < 10000000; i++) {
			bob.add(4);
		}
		time = System.currentTimeMillis() - time;
		System.out.println("Completed ArrayList in " + time + " milliseconds.");
	}
	
	//no constructor	
	private Solve() {
		
	}
	
	
	public double mineProb(int x, int[] b, int w, int h) {
		int[] board = b.clone();
		//return 1 if it is a visible mine
		if (b[x] == -1) {			
			System.out.println("you shouldnt using mineProb on a visible mine, dummy");
			return 1;
		}
		
		//iteratre through all adjacent squares. if they're a visible number
		//see if putting a mine there would over saturate any of them
		if (wouldOverSaturate(x, b, w)) {
			return 0;
		}
		
	}
	
	//it needs to seperate out the parts which are not in common. eg if you have input of
	//1 true in {a, b} 2 true in {b, c, d} 1 true in {e, f}, it would partition this into two groups.
	//first group is {{a, b}, {b, c, d}} second group is {{e, f}}
	public int[][][] partition(int[] b, int width) {
		for (int i = 0; i < b.length; i++) {
			if (b[i] > -1) {
				
			}
		}
		return null;
	}
	
	//return a list of all possible solutions
	//eg input 1 true in {a, b} 2 true in {b, c, d} outputs:
	//{{a, c, d}, {b, c}, {b, d}}
	//obv most elemnts in a subset is 8, and an element cannot occur in more than 8 sunsets
	public int[][] solutions(int[][] a) {
		
		
		int[][] solutions;
		
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				
			}
		}
		return null;
	}
	
	
	
	//returns true if the square is not visible and adjacent to a visible number
	public boolean isAdjToVisNum(int x, int[] b, int width, int height) {
		if (b[x] != -2) {
			return false;
		}
		int[] bob = adjSquares(x, b, width);
		for (int i = 0; i < bob.length; i++) {
			if (b[x] > -1) {
				return false;
			}
		}
		return true;
	}
	
	//is x a member of a beach?
	/*
	public boolean isBeach(int x) {
		if (!isAdjToVisNum(x)) {
			return false;
		}
		return false;
	}
	*/
	
	//would putting a mine at location X oversaturate any of its adjacent squares?
	//note: doesnt factor in if it would exceed total number of mines
	//i dont think i need this method	
	//how many more mines does this square need before it is saturated?
	public int minesToSaturation(int x, int[] b, int width) {
		int num = b[x];
		
		if (num == -2 || num == -1) {
			System.out.println("error, num is " + num + " but continuing anyways ;)");
		}
		
		int mineCount = 0;
		int[] a = adjSquares(x, b, width);
		for (int i = 0; i < a.length; i++) {
			if (b[i] == -1) {
				mineCount++;
			}
		}
		return num - mineCount;
	}
	
	//adjacent not visible squares
	public static int[] adjNonVisSquares(int x, int[] b, int width, int height) {
		if (b[x] < 0) {
			System.out.println("wtf are you doing. adjNonVisSquares shouldnt be used on hidden squares or mines");
			System.out.println("b[x] is " + b[x]);
		}
		int[] a = adjSquares(x, b, width);
		
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
	
	public static int[] adjSquares(int x, int[] b, int width) {
		int[] adjSquares;		
		if (x % width > 0) {//minus left column			
			if (x % width < width - 1) { //minus right colum				
				if (x >= width) { //minus top row					
					if (x <= b.length - width) { //minus bottom row. remaining: centre squares
						adjSquares = new int[8];
						adjSquares[0] = x + 1;
						adjSquares[1] = x - width + 1;
						adjSquares[2] = x - width;
						adjSquares[3] = x - width - 1;
						adjSquares[4] = x - 1;
						adjSquares[5] = x - 1 + width;
						adjSquares[6] = x + width;
						adjSquares[7] = x + 1 + width;
					}
					else { //remaining: bottom centre row
						adjSquares = new int[5];
						adjSquares[0] = x + 1;
						adjSquares[1] = x - width + 1;
						adjSquares[2] = x - width;
						adjSquares[3] = x - width - 1;
						adjSquares[4] = x - 1;
					}
				}
				else { //remaining: top centre row
					adjSquares = new int[5];
					adjSquares[0] = x + 1;
					adjSquares[1] = x - 1;
					adjSquares[2] = x - 1 + width;
					adjSquares[3] = x + width;
					adjSquares[4] = x + width + 1;
				}
			}
			else { //remaining: right column
				if (x == width - 1) { //remaining: top right
					adjSquares = new int[3];
					adjSquares[0] = x - 1;
					adjSquares[1] = x + width - 1;
					adjSquares[2] = x + width;
				}
				else if (x == b.length - 1) {//remianing: bottom right
					adjSquares = new int[3];
					adjSquares[0] = x - width;
					adjSquares[1] = x - width - 1;
					adjSquares[2] = x - 1;
					
				}
				else {//remaining: centre right column
					adjSquares = new int[5];
					adjSquares[0] = x - width;
					adjSquares[1] = x - width - 1;
					adjSquares[2] = x - 1;
					adjSquares[3] = x + width - 1;
					adjSquares[4] = x + width;
				}
			}
		}
		else { //remaiinnig: left column
			if (x == 0) { //remaining: top left
				adjSquares = new int[3];
				adjSquares[0] = x + 1;
				adjSquares[1] = x + width;
				adjSquares[2] = x + width + 1;
			}
			else if (x == b.length - width) {//remianing: bottom left //off by 1 error :(
				adjSquares = new int[3];
				adjSquares[0] = x + 1;
				adjSquares[1] = x - width + 1;
				adjSquares[2] = x - width;
			}
			else {//remaining: centre left column
				adjSquares = new int[5];
				adjSquares[0] = x + 1;
				adjSquares[1] = x - width + 1;
				adjSquares[2] = x - width;
				adjSquares[3] = x + width;				
				adjSquares[4] = x + width + 1;
			}
		}
		return adjSquares;
	}
	
	public static double probabilityOfMine(int[] b, int x, int width, int height) {
		return height;
		
	}
	
	//combines the solutions to each beaches together
	public int[][] solutionsBoard(int[] b, int width, int height) {
		return null;
		
	}
	
	//determine beaches
	//beaches need to be connected not all by being next to eachother but
	//by having a visible number in common
	public int[][] getBeaches(int[] b, int width) {
		boolean[] beach = new boolean[b.length];
		//first maek it true if adjacent to vis number
		for (int i = 0; i < b.length; i++) {
			
		}
		//now strip it of places wehre there cannot be a mine
		return null;
	}	
	
	//input island eg 55 56 57 output where mines are eg 56
	//count the permutations of beaches with k mines.
	public int[][] solveBeach(int[] island) {
		return null;
	}
	
	
	public static int countMines(int[] a, int[] b) {
		int mines = 0;
		for (int i = 0; i < a.length; i++) {
			if (b[a[i]] == -1) {
				mines++;
			}
		}
		return mines;
	}
	
	public static int countMines(ArrayList<Integer> a, int[] b) {
		int mines = 0;
		for (int i = 1; i < a.size(); i++) {
			if (b[a.get(i)] == -1) {
				mines++;
			}
		}
		return mines;
	}
	
	public static boolean isSubSet(ArrayList<Integer> a, ArrayList<Integer> b) {
		for (int i = 1; i < a.size(); i++) {
			if (b.contains(a.get(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	//generate basic constraints
	public static ArrayList<ArrayList<Integer>> basicConstraints(int[] b) {
		ArrayList<ArrayList<Integer>> ar = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < b.length; i++) {
			if (b[i] > 0) {
				if (minesToSaturation(b[i], b, width) == false) {
					ArrayList<Integer> cons = new ArrayList<Integer>();
					cons.add(b[i]);
				}
				
			}
		}
		
		
		return ar;
		
	}
}

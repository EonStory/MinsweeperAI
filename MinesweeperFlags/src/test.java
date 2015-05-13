import java.util.*;
public class test {
	public static void main(String[] args) {
		Double equ = new Double (4.4);
		Double qw = new Double(4.4);
		boolean[] q = new boolean[3];
		
		
		int a = 0;
		while (true) {
			a++;
			if (a > 100000000) {
				System.out.println("1complete");
				break;
			}
		}
		Integer b = 0;
		while (true) {
			b++;
			if (b > 100000000) {
				System.out.println("2");
				break;
			}
		}		
		/*
		System.out.println("bob is " + (equ == qw));
		System.out.println(q[2]);
		//int[] board = {-1, -1, -1, -1, -1, -1};
		//merge(board, new int[] {3, 1, 2, 3, 4}, new int[] {2, 2, 3, 4, 5, 6});	
		int c = 0;
		while (true) {
			if (c == 2000000000) {
				System.out.println("c = " + c);
				break;
			}
			c++;
		}
		
		ArrayList<String> a = new ArrayList<>();
		a.add("hehe");
		for (int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i));
			a.add(Integer.toString(i));
			if (i+1 % 7 == 0) {
				a.remove(i);
			}
		}
		*/
	}
	
	public static int[] inputBoard(Scanner s, int size) {
		
		int[] board = new int[size];
		
		return null;
		
	}
	
	public static ArrayList<ArrayList<Integer>> fia() {
		
		
		
		return null;		
	}
	static test bw = new test();
	public test() {
		System.out.println("HAHAHA");
	}
	//merger. note merge(a,b) != merge(b, a)
	//1st element in the array is how many inside it are true
	public static void  merge(int[] board, int[] a, int[] b) {
		int[] copy = a.clone();
		for (int i = 1; i < a.length; i++) {
			for (int j = 1; j < b.length; j++) {
				if (a[i] == b[j]) {
					break;
				}				
				if (j == b.length - 1) { //didnt contain if reached last element without breaking
					copy[0]--;
					board[copy[i]] = -33; //-33 represents not in the set
				}
			}
		}
		
		for (int i = 0; i < board.length; i++) {
			System.out.print(board[i] + " ");
		}
	}
	
	
	//input sets. the first number in each set represents how many mines are in that group
	public static int[][] foo(int[][] a) {		
		for (int i = 0; i < a.length; i++) {
			for (int j = 1; j < a[i].length; j++) { //starts at 1 because the first number REPRESENTS THE NUMBER OF TRUE STATEMENTS
				int[] bob = getAdjUnsat(a[i][j]);				
				for (int k = 0; k < bob.length; k++) {
					
				}
			}
		}
		return null;
	}
	
	//steven algorithm
	//first element represents how many are true
	//do not input sets that begin with 0 (0 mines in there.) this will break stuff
	//this would be v easy to fix though
	//aim is to figure out which minese are CERTAINLY FALSE and which are CERTAINLY TRUE
	//by assuming that they atr
	//key 0 REPRESENTS EMPTY, 1 IS MINE.
	//this alrogirthm lies on assumption that if a mine doesnt violate its immetiate neigbors, there must be some
	//solution to the whole board where that mine is true without checking neighbors neighbors and so on
	public static int[][] tony(int[][] q) {
		//create a copy of the int[][] input as
		//create a board. lets say its 15 long... ie largest value input cannot be more than 14.
		//essentially is used as reference
		int[] board = new int[15];
		
		///set all unknowns to -1 ( representing "?" )		
		
		//this is O(m * n) problem where m is number of lements and n is number of sets [sets cannot contain more thna 8 elements]
		//maybe its O(m * (n * 8)) == O (m * n)
		for (int i = 0; i < board.length; i++) {
			Arrays.fill(board, -1);
			board[i] = 1; //assume its a mine. does this create any contradictions? lets check	
			//this does not propagate
			for (int j = 0; j < q.length; j++) {
				for (int k = 0; k < q[j].length; k++) {
					
				}
			}
			
			board[i] = 0; //
			//stuff			
		}		
		
		for (int i = 0; i < q.length; i++) {
			for ()
		}
	}
	
	//this returns the unsaturated adjacent squares
	public static int[] getAdjUnsat(int x) {
		return null;
	}
	
	//public static int[][] get
	
	public static ArrayList<ArrayList<Integer>> foom(ArrayList<ArrayList<Integer>> a) {
		ArrayList<Integer> squares = new ArrayList<Integer>(); //all the squares under question.
		
		//-1 represents uncertain of if it contains a mine. 0 means it cant, 1 means it must. no other values possible
		
		//add all of them into
		for (ArrayList<Integer> e: a) {
			for (Integer q: e) {
				
			}
		}
		
		
		for (Integer i: squares) {
			i = -1;
		}
		return null;
	}
	
	
}

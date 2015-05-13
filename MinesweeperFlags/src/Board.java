import java.util.*;

//this method isnt optimised for using virtual boards (boards used by AI predicting future)
//by creating them for every step forwards of the AI. to optimise for virtual boards, clone original board and only make changes to
//the changed squares (ie the squares that are changed in the selectSquareHelper method).
public class Board {
	boolean[] visible;
	boolean[] mines;
	int[] numbers;
	int width;
	boolean p1Turn;
	int p1Score;
	int p2Score;
	static final int VIS_MINE = -1;
	
	public static void main(String[] args) {	
		Board bob = new Board(10, 10, 20);
		bob.print();
		System.out.println();
		bob.printVisible();
		bob.printAddress();
		Scanner input = new Scanner(System.in);
		
		while (true) {
			System.out.println("Enter a numba (-1 == addresses, -2 == full board, -3 == adjToVisNum) ");
			int q = input.nextInt();
			if (q == -1) {
				bob.printAddress();
			}
			else if (q == -2) {
				bob.print();
			}
			else if (q == -3) {
				bob.printAdjToVisNum();
			}
			else if (q < bob.numbers.length && q >= 0) {
				bob.selectSquare(q);
				bob.printVisible();
			}
			else {
				System.out.println("Huh?");
			}			
		}		
	}
	
	public Board(int width, int height, int mineCount) {
		visible = new boolean[width * height];
		mines = new boolean[width * height];
		numbers = new int[width * height];
		this.width = width;
		p1Turn = true;
		
		//assign all minese at the start then shuffle them
		for (int i = 0; i < mineCount; i++) {
			mines[i] = true;
		}
		int randvalue;
		boolean temp;
		for (int i = 0; i < mines.length; i++) {
			randvalue = i + (int) (Math.random() * (mines.length - i));
			temp = mines[randvalue];
			mines[randvalue] = mines[i];
			mines[i] = temp;
		}
		
		//must determine numbers
		for (int i = 0; i < numbers.length; i++) {			
			numbers[i] = number(i);
		}
	}
	
	//this is passed to the users. -1 is mine, -2 is not visible
	public int[] knownBoardState() {
		int b[] = new int[numbers.length];
		for (int i = 0; i < numbers.length; i++) {
			if (!visible[i]) {
				b[i] = -2;
			}
			else if (mines[i]) {
				b[i] = -1;
			}
			else {
				b[i] = numbers[i];
			}
		}
		return b;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int visibleMines() {
		int count = 0;
		for (int i = 0; i < numbers.length; i++) {
			if (visible[i] && mines[i]) {
				count++;
			}
		}
		return count;
	}
	
	public void print() {
		for (int i = 0; i < numbers.length; i++) {
			if (mines[i]) {
				System.out.print("X ");
			}
			else {
				System.out.print(numbers[i] + " ");
			}
			if ((i + 1) % width == 0) {
				System.out.println();
			}
		}
	}
	
	public int number(int x) {
		if (mines[x]) {
			return VIS_MINE;
		}
		int num = 0;
		int[] bob = adjSquares(x);
		for (int i = 0; i < bob.length; i++) {
			System.out.println(bob[i]);
			if (bob[i] == 100) {
				System.out.println(bob.length);
			}
			if (mines[bob[i]]) {
				num++;
			}
		}
		return num;
	}
	
	//is it adjacent to a visible number & is not visible itself?
	@Deprecated
	public boolean isAdjToVisNum(int x) {
		int[] bob = adjSquares(x);
		for (int i = 0; i < bob.length; i++) {
			if (visible[bob[i]] && !mines[bob[i]]) {
				return true;
			}
		}
		return false;
	}
	
	public void printAdjToVisNum() {
		for (int i = 0; i < numbers.length; i++) {
			if (isAdjToVisNum(i)) {
				System.out.print("T ");
			}
			else {
				System.out.print("F ");
			}
			
			if ((i + 1) % width == 0) {
				System.out.println();
			}
		}
	}
	
	public void printAddress() {
		for (int i = 0; i < numbers.length; i++) {
			System.out.print(i + " ");
			if ((i + 1) % width == 0) {
				System.out.println();
			}
		}
	}
	
	public void printVisible() {
		for (int i = 0; i < numbers.length; i++) {
			if (visible[i] == false) {
				System.out.print("? ");
			}
			else {
				if (mines[i]) {
					System.out.print("X ");
				}
				else {
					System.out.print(numbers[i] + " ");
				}
			}
			
			if ((i + 1) % width == 0) {
				System.out.println();
			}
		}
	}
	
	//this method is long but quick.
	public int[] adjSquares(int x) {
		int[] adjSquares;		
		if (x % width > 0) {//minus left column			
			if (x % width < width - 1) { //minus right colum				
				if (x >= width) { //minus top row					
					if (x <= numbers.length - width) { //minus bottom row. remaining: centre squares
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
				else if (x == numbers.length - 1) {//remianing: bottom right
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
			else if (x == numbers.length - width) {//remianing: bottom left //off by 1 error :(
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
	
	//.get(0) is the loc, .get(1) is the value there
	public ArrayList<ArrayList<Integer>> selectSquare(int x) {
		ArrayList<ArrayList<Integer>> visChanges = new ArrayList<ArrayList<Integer>>();
		int scorebeforeTurn = p1Score + p2Score;
		selectSquareHelper(x, visChanges);
		if (scorebeforeTurn == p1Score + p2Score) {
			p1Turn = !p1Turn;
		}
		return visChanges;		
	}
	
	//helper method
	//note that when highlighting last move extra code is needed due to stuff like player not switching if he picks a square already visible
	public ArrayList<ArrayList<Integer>> selectSquareHelper(int x, ArrayList<ArrayList<Integer>> visChanges) {//helper method. stops p1Turn from flipping over and over again
		if (visible[x] == true) {
			return visChanges;
		}		
		visible[x] = true;
		ArrayList<Integer> square = new ArrayList<Integer>();
		square.add(x);
		square.add(numbers[x]);
		visChanges.add(square);
		if (mines[x] == false) {			
			if (numbers[x] == 0) {
				int[] bob = adjSquares(x);
				for (int i = 0; i < bob.length; i++) {
					selectSquareHelper(bob[i], visChanges);
				}
			}
		}
		else {
			if (p1Turn == true) {
				p1Score++;
			}
			else {
				p2Score++;
			}			
		}
		return visChanges;
	}	
}

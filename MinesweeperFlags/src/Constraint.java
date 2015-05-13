import java.util.ArrayList;
import java.util.HashSet;

@SuppressWarnings("serial")
public class Constraint extends ArrayList<Integer> {	
	int mines;
	int vm = 0; //virtual mines, used for solving
	int us = 0; //used squares, used for solving
	//public static ArrayList<ArrayList<Constraint> onsquaress;
	
	public Constraint(int mines) {
		super();
		this.mines = mines;
		
	}
	
	public String toString() {
		String s = mines + "m = {";
		for (int i = 0; i < this.size() - 1; i++) {
			s += this.get(i) + ", ";
		}
		if (this.size() > 0) {
			s += this.get(this.size() - 1);
		}
		s += "}";
		return s;
	}
	
	public boolean equals(Object c) {
		if (this == c) {
			return true;
		}
		if (!(c instanceof Constraint)) {
			return false;
		}
		Constraint casted = (Constraint) c;
		if (casted.mines != this.mines) {
			return false;
		}
		if (casted.size() != this.size()) {
			return false;
		}
		for (int i = 0; i < casted.size(); i++) {
			if (!casted.get(i).equals(this.get(i))) {
				return false;
			}
		}
		return true;
	}
}


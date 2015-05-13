import java.util.ArrayList;

//list of squares which are equivalent in terms of permutations
//each beach has exactly one Equiv
public class Equiv extends ArrayList<Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String toString() {
		String s = "e = {";
		for (int i = 0; i < this.size() - 1; i++) {
			s += this.get(i) + ", ";
		}
		if (this.size() > 0) {
			s += this.get(this.size() - 1);
		}
		s += "}";
		return s;
	}

}



/**
 * 
 * @author Loukas Papalazarou, Taoufik Sousak
 * 
 * Extension of human that represents humans that have died
 *
 */

public class DeceasedHuman extends Human {

	static int total=0;
	
	/**
	 * 
	 * @param xpos x-axis position
	 * @param ypos y-axis position
	 */
	public DeceasedHuman(int xpos, int ypos) {
		super(xpos, ypos);
		total++;
	}

	
	/**
	 * 
	 * @return the total amount of dead people
	 */
	public static int getTotalCases() {
		return total;
	}
	
	
}

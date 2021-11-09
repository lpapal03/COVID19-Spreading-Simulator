

/**
 * placeholder for humans that travel.
 * 
 * @author Taoufik Sousak
 *
 */
public class TraveledHuman extends Human {
	
	private static int total=0;

	/**
	 * constructor.
	 * @param xpos position of human
	 * @param ypos position of human
	 */
	public TraveledHuman(int xpos, int ypos, boolean counts) {
		super(xpos, ypos);
		if(counts)
		total++;
	}

	/**
	 * 
	 * @return total of travels
	 */
	public static int getTotal() {
		return total;
	}
}

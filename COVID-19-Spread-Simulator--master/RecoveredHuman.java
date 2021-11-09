

/**
 * 
 * @author Loukas Papalazarou, Taoufik Sousak
 * 
 * Extension of human that represents humans that have contracted the virus and have healed
 *
 */
public class RecoveredHuman extends Human{
	
	static int total=0;
	boolean takesMeasures;

	/**
	 * 
	 * @param xpos x-axis position
	 * @param ypos y-axis position
	 */
	public RecoveredHuman(int xpos, int ypos) {
		this(xpos,ypos,false,true);
	}
	
	/**
	 * 
	 * @param xpos x-axis position
	 * @param ypos y-axis position
	 * @param measures whether the person takes protective measures
	 */
	public RecoveredHuman(int xpos, int ypos, boolean measures, boolean counts) {
		super(xpos,ypos);
		if(counts)
		total++;
		takesMeasures=measures;
	}

	/**
	 * 
	 * @return total amount of healed people
	 */
	public static int getTotalCases() {
		return total;
	}
	
	/**
	 * @return takesMeasures
	 */
	public boolean takesMeasures() {
		return takesMeasures;
	}
}

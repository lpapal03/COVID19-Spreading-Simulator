

/**
 * 
 * @author Loukas Papalazarou, Taoufik Sousak
 * 
 *         Extension of human that represents that are not currently affected by
 *         the virus
 *
 */
public class HealthyHuman extends Human {

	static int total = 0;
	boolean takesMeasures;

	/**
	 * 
	 * @param xpos x-axis position 
	 * @param ypos y-axis position
	 */
	public HealthyHuman(int xpos, int ypos) {
		this(xpos, ypos, false, true);
	}

	/**
	 * 
	 * @param xpos x-axis position 
	 * @param ypos y-axis position
	 * @param careful whether the person takes protective measures
	 */
	public HealthyHuman(int xpos, int ypos, boolean careful, boolean counts) {
		super(xpos, ypos);
		takesMeasures = careful;
		if(counts)
		total++;
	}

	/**
	 * 
	 * @return the total amount of healthy people
	 */
	public static int getTotalCases() {
		return total;
	}

	/**
	 * @return whether the instance of this person takes protective measures
	 */
	public boolean takesMeasures() {
		return takesMeasures;
	}
}

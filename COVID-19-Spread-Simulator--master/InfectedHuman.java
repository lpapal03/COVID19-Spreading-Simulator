

/**
 * 
 * @author Loukas Papalazarou, Taoufik Sousak
 * 
 * Extension of human that represents humans that have been infected by the virus
 *
 */
public class InfectedHuman extends Human {

	private int timeLeft;
	static int total = 0;
	private boolean measures;

	/**
	 * 
	 * @param xpos     x-axis position
	 * @param ypos     y-axis position
	 * @param duration the time it takes for this human to heal
	 * @param measures whether the human takes protecrive measures
	 */
	public InfectedHuman(int xpos, int ypos, int duration, boolean measures, boolean counts) {
		super(xpos, ypos);
		timeLeft = duration;
		this.measures = measures;
		if(counts)
		total++;
	}

	/**
	 * 
	 * @param xpos x-axis position
	 * @param ypos y-axis position
	 * @param duration the time it takes for this human to heal
	 */
	public InfectedHuman(int xpos, int ypos, int duration) {
		this(xpos, ypos, duration, false, true);
	}

	/**
	 * Moves to new location
	 * 
	 * Every movement means that the time left to heal is reduced by 1
	 */
	public void moveTo(int newxpos, int newypos) {
		super.moveTo(newxpos, newypos);
		timeLeft--;
	}

	/**
	 * @return timeLeft
	 */
	public int getTimeLeft() {
		return timeLeft;
	}

	/**
	 * @return measures
	 */
	public boolean takesMeasures() {
		return measures;
	}

	/**
	 * 
	 * @return the total amount of infected people
	 */
	public static int getTotalCases() {
		return total;
	}

}

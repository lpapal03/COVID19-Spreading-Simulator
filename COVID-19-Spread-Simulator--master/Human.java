

/**
 * 
 * @author Loukas Papalazarou, Taoufik Sousak
 * 
 *         This class will be extended by various human types (Healthy,
 *         Infected, etc)
 *
 */
abstract class Human implements Movable {

	private int xpos;
	private int ypos;
	private boolean measures;
	private static int total=0;
	
	/**
	 * 
	 * @param xpos     x-axis position
	 * @param ypos     y-axis position
	 * @param measures whether the person takes protective measures
	 */
	public Human(int xpos, int ypos, boolean measures) {
		this.xpos = xpos;
		this.ypos = ypos;
		this.measures = measures;
		total++;
	}
	
	
	public static int getTotalHumans() {
		return total;
	}

	/**
	 * 
	 * @param xpos x-axis position
	 * @param ypos y-axis position
	 */
	public Human(int xpos, int ypos) {
		this(xpos, ypos, false);
	}

	// xpos getter
	public int getXpos() {
		return xpos;
	}

	// ypos getter
	public int getYpos() {
		return ypos;
	}

	/**
	 * Moves the human to the specified x,y coordinates
	 */
	public void moveTo(int newxpos, int newypos) {
		xpos = newxpos;
		ypos = newypos;
	}

	public int getTimeLeft() {
		return 0;
	}

	public boolean takesMeasures() {
		return false;
	}

}

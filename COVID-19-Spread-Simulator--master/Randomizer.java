
/**
 * this static class has methods that provide quick random values.
 * 
 * @author Taoufik Sousak, Loukas Papalazarou
 *
 */
public class Randomizer {

	/**
	 * returns random boolean with R chance of returning true
	 * @param R how likely to return true
	 * @return true semi-randomly
	 */
	public static boolean getBoolean(double R) {
		if (Math.random() < R)
			return true;
		return false;
	}
	
	/**
	 * returns a random integer up to given integer (exclusive).
	 * 
	 * @param limit is up to what num int can be
	 * @return a random int from 0 to parameter
	 */
	public static int getInteger(int limit) {
		return  (int)(Math.random() * ((limit ) ));
	}
	
	
	/**
	 * gives random direction with equal chance of each.
	 * 
	 * @return u=up, d=down, l=left, r=right, combinations for diagonals
	 */
	public static String getDirection() {
		
		
		double ran=Math.random();
		ran*=100;
		
		if(ran<12.5)
			return "u"; //for up
		else if(ran<25)
			return "ur"; //up right 
		else if(ran<37.5)
			return "r"; //right
		else if(ran<50)
			return "dr"; //down right
		else if(ran<62.5)
			return "d"; //down
		else if(ran<75)
			return "dl"; //down left
		else if(ran<87.5)
			return "l"; // left
		else if(ran<100)
			return "ul"; //up left
					
		
		return "e"; //for error
	}

}

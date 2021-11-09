

import java.util.ArrayList;

/**
 * The class responsible for connecting the humans wit the room, has move method
 * to move all the humans and updateStatus to update the status of them.
 * 
 * 
 * @author Taoufik Sousak, Loukas Papalazarou
 *
 */
public class Crowd {

	private Human[][] humans;
	private static Room[] room;
	private double howLikelyToMove;
	private double fatal;
	private static double inf;
	private int duration;

	private int cities; // how many rooms
	private int[] people;
	private int[] width;
	private int[] length;

	/**
	 * The only constructor, decides where to place each human, creates them and
	 * places them.
	 * 
	 * @param duration        how long it takes for someone to recover and for a
	 *                        room to get disinfected
	 * @param width           the width of the grid
	 * @param length          the length of the grid
	 * @param people          how many people there will be in general
	 * @param howLikelyToMove a percentage of how likely each human is to move
	 * @param sick            how many people are initially sick
	 * @param fatal           how fatal is the virus %
	 * @param infectius       how inectius is the virus
	 * @param caref           how many people take protective measures
	 */
	public Crowd(int duration, int[] width, int[] length, int[] people, int howLikelyToMove, int[] sick, int fatal,
			int infectius, int[] caref, int hmRooms, int[] ports) {

		this.width = width;
		this.length = length;
		cities = hmRooms;

		this.people = people;
		room = new Room[cities];

		for (int i = 0; i < cities; i++) {
			room[i] = new Room(width[i], length[i], duration, infectius,cities);
		}
		int totalPeople = 0; // calculate total of people in the simulation
		for (int i = 0; i < cities; i++)
			totalPeople += people[i];

		humans = new Human[totalPeople][cities];
		this.howLikelyToMove = (double) howLikelyToMove / 100;
		int xpos = 0;
		int ypos = 0;
		this.fatal = (double) fatal / 100;
		this.inf = (double) infectius / 100;
		this.duration = duration;

		if(cities!=1)
		for (int i = 0; i < cities; i++) {
			room[i].assignPorts(ports[i], cities, i);
		}

		// decide where to place human
		for (int j = 0; j < cities; j++) // for every room
			for (int i = 0; i < people[j]; i++) {
				do {
					xpos = (int) (Math.random() * ((width[j])));
					ypos = (int) (Math.random() * ((length[j])));

				} while (room[j].isOccupied(ypos, xpos) != 0);

				// create sick humans
				if (i < sick[j]) {

					humans[i][j] = new InfectedHuman(xpos, ypos, duration);
					room[j].occupy(ypos, xpos, 2);
				} else {
					boolean msrs = false;
					// put masks on specified amount
					if (i - sick[j] < caref[j])
						msrs = true;
					humans[i][j] = new HealthyHuman(xpos, ypos, msrs,true);
					room[j].occupy(ypos, xpos, 1);
				}
				room[j].drawGrid();

			}
	}

	/**
	 * moves every human individaully, then calls update status to update their
	 * status and draws the grid.
	 */
	public void move(int toDraw) {

		int destination;
		for (int j = 0; j < cities; j++) {// for every room

			destination = j; // to what city human should go

			for (int i = 0; i < people[j]; i++) {
				int newxpos = humans[i][j].getXpos();
				int newypos = humans[i][j].getYpos();
				String direction = null;
				// decide if human will move
				if (Randomizer.getBoolean(howLikelyToMove) && !(humans[i][j] instanceof TraveledHuman) && !(humans[i][j] instanceof DeceasedHuman)) {
					room[j].occupy(humans[i][j].getYpos(), humans[i][j].getXpos(), 0);
					room[j].setMeasures(newypos, newxpos, false);
					// choose new available position
					do {
						// check if trapped
						if (!room[j].hasPort(humans[i][j].getXpos(), humans[i][j].getYpos())) // dont check for
																								// trapped if on a
																								// port
							if (noPossibleMove(humans[i][j].getXpos(), humans[i][j].getYpos(), j))
								break;

						newxpos = humans[i][j].getXpos();
						newypos = humans[i][j].getYpos();

						destination = j;
						direction = Randomizer.getDirection();

						if (direction.contains("u"))
							if (humans[i][j].getYpos() != room[j].getLength() - 1)
								newypos++;
							else if (room[j].hasPort(newxpos, newypos)) {
								destination = (room[j].getPort(newxpos, newypos));
							//	System.out.println(
							//			"entered else if on U with j = " + j + " and destination = " + destination);
								this.travel(i, j, destination);
							}

						if (direction.contains("d"))
							if (humans[i][j].getYpos() != 0)
								newypos--;
							else if (room[j].hasPort(newxpos, newypos)) {
								destination = (room[j].getPort(newxpos, newypos));
							//	System.out.println(
							//			"entered else if on D with j = " + j + " and destination = " + destination);

								this.travel(i, j, destination);
							}

						if (direction.contains("r"))
							if (humans[i][j].getXpos() != room[j].getWidth() - 1)
								newxpos++;
							else if (room[j].hasPort(newxpos, newypos)) {
								destination = (room[j].getPort(newxpos, newypos));
							//	System.out.println(
							//			"entered else if on R with j = " + j + " and destination = " + destination);

								this.travel(i, j, destination);
							}

						if (direction.contains("l"))
							if (humans[i][j].getXpos() != 0)
								newxpos--;
							else if (room[j].hasPort(newxpos, newypos)) {
								destination = (room[j].getPort(newxpos, newypos));
							//	System.out.println(
							//			"entered else if on L with j = " + j + " and destination = " + destination);

								this.travel(i, j, destination);
							}

					} while (room[j].isOccupied(newypos, newxpos) != 0);

					if (!(humans[i][j] instanceof TraveledHuman)) {
						humans[i][j].moveTo(newxpos, newypos);
					}
					this.updateStatus(i, newxpos, newypos, j);
				}

			}
			room[toDraw].drawGrid();
		}
//		try {
//			Thread.sleep(10); // wait a second between every move to represent time passing
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

	public void travel(int i, int j, int destination) {

		//System.out.println("entered TRAVEL: " + destination);
		//room[j].occupy(humans[i][j].getYpos(), humans[i][j].getXpos(), 0);

		int xpos;
		int ypos;
		do {
			xpos = (int) (Math.random() * ((width[destination])));
			ypos = (int) (Math.random() * ((length[destination])));

		} while (room[destination].isOccupied(ypos, xpos) != 0);

		// room[destination].occupy(ypos, xpos, 1);

		int avail = 0;

		for (int x = 0; x < humans.length; x++)
			if (humans[x][destination] == null) {
			//	System.out.println("entered***********");
				avail = x;
				people[destination]++;
				break;
			}

		if (humans[i][j] instanceof HealthyHuman) 
			humans[avail][destination] = new HealthyHuman(xpos, ypos, humans[i][j].takesMeasures(),false);
		else if (humans[i][j] instanceof RecoveredHuman)
			humans[avail][destination] = new RecoveredHuman(xpos, ypos, humans[i][j].takesMeasures(),false);
		else if (humans[i][j] instanceof InfectedHuman)
			humans[avail][destination] = new InfectedHuman(xpos, ypos,humans[i][j].getTimeLeft() , humans[i][j].takesMeasures(),false);
		else {
		//	System.out.println(humans[i][j].getClass().toString());
			humans[avail][destination] = new TraveledHuman(xpos, ypos,false);
		}
			
		
		humans[i][j] = new TraveledHuman(humans[i][j].getXpos(), humans[i][j].getYpos(),true);

	}

	/**
	 * infects people that should get infected, recovers people that should recover
	 * and kills people that should die accordingly.
	 * 
	 * @param i
	 * @param newxpos
	 * @param newypos
	 */
	public void updateStatus(int i, int newxpos, int newypos, int j) {
		
		if (humans[i][j] instanceof TraveledHuman)
			room[j].occupy(newypos, newxpos, 0);

		// checks if infected human is resady to recover, recoveres or kills if its time
		if (humans[i][j] instanceof InfectedHuman)
			if (humans[i][j].getTimeLeft() == 0)
				if (!Randomizer.getBoolean(fatal))
					humans[i][j] = new RecoveredHuman(humans[i][j].getXpos(), humans[i][j].getYpos(),
							humans[i][j].takesMeasures(),true);
				else {
					humans[i][j] = new DeceasedHuman(humans[i][j].getXpos(), humans[i][j].getYpos());
					room[j].occupy(newypos, newxpos, 0);
				}
		// checks if healthy human should get infected
		if (humans[i][j] instanceof HealthyHuman) {
			room[j].occupy(newypos, newxpos, 1);

			if (possibleInfection(humans[i][j].getXpos(), humans[i][j].getYpos(), humans[i][j].takesMeasures(), j))
				if (humans[i][j].takesMeasures())
					humans[i][j] = new InfectedHuman(humans[i][j].getXpos(), humans[i][j].getYpos(), duration, true,true);
				else
					humans[i][j] = new InfectedHuman(humans[i][j].getXpos(), humans[i][j].getYpos(), duration);

		} else if (humans[i][j] instanceof InfectedHuman)
			room[j].occupy(newypos, newxpos, 2);
		else if (humans[i][j] instanceof RecoveredHuman)
			room[j].occupy(newypos, newxpos, 3);

		if (humans[i][j].takesMeasures())
			room[j].setMeasures(newypos, newxpos, true);
	}

	/**
	 * checks if human will get infected
	 * 
	 * @param x        position
	 * @param y        poition
	 * @param measures whether human takes protective measures
	 * @return true if human should get infected
	 */
	public static boolean possibleInfection(int x, int y, boolean measures, int j) {

		if (humanTransmition(x, y, measures, j))
			return true;

		if (roomTransmition(x, y, measures, j))
			return true;

		return false;
	}

	/**
	 * checks if human is trapped
	 * 
	 * @param x position
	 * @param y position
	 * @return true if human is trapped
	 */
	public static boolean noPossibleMove(int x, int y, int j) {

		int cnt = 0;
		try {
			if (room[j].isOccupied(y - 1, x + 1) > 0)
				cnt++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y, x + 1) > 0)
				cnt++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y + 1, x + 1) > 0)
				cnt++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y - 1, x) > 0)
				cnt++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y + 1, x) > 0)
				cnt++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y - 1, x - 1) > 0)
				cnt++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y, x - 1) > 0)
				cnt++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y + 1, x - 1) > 0)
				cnt++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return (cnt == 8);
	}

	/**
	 * 
	 * @param x        position
	 * @param y        position
	 * @param measures whether human takes protective measures
	 * @return
	 */
	public static boolean roomTransmition(int x, int y, boolean measures, int j) {
		double chance = inf / 2; // because its less likely to get virus from room than human
		if (measures)
			chance /= 2; // protective measures lower chance of infection
		if (room[j].isInfected(y, x) > 0)
			if (Randomizer.getBoolean(chance))
				return true;
		return false;
	}

	/**
	 * checks for human to human transmition
	 * 
	 * @param x        position
	 * @param y        position
	 * @param measures whether human takes measures
	 * @return true if should get infected from nearby human
	 */
	public static boolean humanTransmition(int x, int y, boolean measures, int j) {

		double chance = inf;
		if (measures)
			chance /= 9; // protective measures lower chance of infection
		try {
			if (room[j].isOccupied(y - 1, x + 1) == 2)
				if (Randomizer.getBoolean(chance))
					return true;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y, x + 1) == 2)
				if (Randomizer.getBoolean(chance))
					return true;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y + 1, x + 1) == 2)
				if (Randomizer.getBoolean(chance))
					return true;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y - 1, x) == 2)
				if (Randomizer.getBoolean(chance))
					return true;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y + 1, x) == 2)
				if (Randomizer.getBoolean(chance))
					return true;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y - 1, x - 1) == 2)
				if (Randomizer.getBoolean(chance))
					return true;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y, x - 1) == 2)
				if (Randomizer.getBoolean(chance))
					return true;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (room[j].isOccupied(y + 1, x - 1) == 2)
				if (Randomizer.getBoolean(chance))
					return true;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return false;
	}
}

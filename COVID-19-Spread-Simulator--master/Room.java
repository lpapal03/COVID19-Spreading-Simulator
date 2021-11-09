

import java.awt.Color;



public class Room {

	private int width;
	private int length;
	private int occupied[][]; // 0=no | 1=with healthy human | 2=with infected human | 3=with recovered human
	private int infected[][];
	private int duration;
	private double inf;
	private boolean measures[][];
	private Color col;
	private static Color colors[];
	private static int count = 0;

	private int port[][]; // number represents to which city it leads
	private boolean hasPort[][];

	/**
	 * 
	 * @param width      of grid
	 * @param length     of grid
	 * @param duration   is the amount of time required for a person to heal
	 * @param infectious how likely is for people to contract the virus from other
	 *                   infected people or spaces
	 */
	public Room(int width, int length, int duration, int infectious, int cities) {
		this.width = width;
		this.length = length;
		occupied = new int[length][width];
		infected = new int[length][width];

		port = new int[width][length];
		hasPort = new boolean[width][length];

		measures = new boolean[length][width];
		this.duration = duration;
		this.inf = (double) infectious / 100;
		inf /= 3; // less likely than human transmission
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				occupied[i][j] = 0;
				infected[i][j] = 0;
				measures[i][j] = false;
			}
		}
		do {
			col = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
		} while (!compatible(StdDraw.WHITE, col));
		if (count == 0)
			colors = new Color[cities];
		colors[count] = col;
		count++;
		// setup of grid
		this.gridSetup();
		this.drawGrid();
	}

	public Color getColor() {
		return this.col;
	}

	/**
	 * helping methods to assign colors.
	 * 
	 * @param c color that we want the lum of.
	 * @return the luminance
	 */
	private static double lum(Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		return .299 * r + .587 * g + .114 * b;
	}

	/**
	 * helping methods to assign colors.
	 * 
	 * @param a first color
	 * @param b second color
	 * @return true if compatible
	 */
	private static boolean compatible(Color a, Color b) {
		return Math.abs(lum(a) - lum(b)) >= 160.0;
	}

	/**
	 * sets port array in random edge positions to ports, an integer that refers to
	 * where the port leads.
	 * 
	 * @param howManyPorts   this rooms has
	 * @param howManyRooms   the simulation has
	 * @param thisRoomNumber number of this room
	 */
	public void assignPorts(int howManyPorts, int howManyRooms, int thisRoomNumber) {

		boolean[] checkList = new boolean[howManyRooms];

		for (int r = 0; r < checkList.length; r++)
			checkList[r] = false;

		checkList[thisRoomNumber] = true;

		int cnt = 0;
		int toWhichRoom = -1;
		int howManyToThisRoom = 0;

		int temp = howManyPorts;

		int i = 0, j = 0;

		for (int c = howManyPorts; c > 0;) {
			// System.out.println("loop1");
			for (int r = 0; r < checkList.length; r++)
				if (checkList[r] == false) {
					toWhichRoom = r;
					break;
				}

			checkList[toWhichRoom] = true;

			if (c == howManyPorts)
				howManyToThisRoom = Randomizer.getInteger(temp - temp / howManyRooms);
			else
				howManyToThisRoom = Randomizer.getInteger(temp);

			if (howManyToThisRoom == howManyPorts && howManyPorts != 1)
				howManyToThisRoom--;

			temp -= howManyToThisRoom;

			if (howManyToThisRoom == 0)
				howManyToThisRoom++;
			cnt = 0;
			while (cnt < howManyToThisRoom) {
				// System.out.println("loop2");
				if (i == 0 && j < width - 1) {
					j++;
					// System.out.println("j++");
				} else if (j == width - 1 && i < length - 1) {
					i++;
					// System.out.println("i++");
				} else if (i == length - 1 && j > 0) {
					j--;
					// System.out.println("j--");
				} else {
					i--;
					// System.out.println("i--");
				}
				this.port[j][i] = toWhichRoom;
				this.hasPort[j][i] = true;
				cnt++;
				c--;
			}
			if (cnt == 0)
				c--;
		}

	}

	/**
	 * 
	 * @param y        position
	 * @param x        position
	 * @param measures sets whether the person on that position is taking protective
	 *                 measures
	 */
	public void setMeasures(int y, int x, boolean measures) {
		this.measures[y][x] = measures;
	}

	/**
	 * 
	 * @param y position
	 * @param x position
	 * @return returns whether the person on that position is taking protective
	 *         measures
	 */
	public boolean takesMeasures(int y, int x) {
		return measures[y][x];
	}

	/**
	 * 
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * 
	 * @param x position
	 * @param y position
	 * @return port at given position
	 */
	public int getPort(int x, int y) {
		return port[x][y];
	}

	/**
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 
	 * @param y position
	 * @param x position
	 * @return true if the space in that position is infected
	 */
	public int isInfected(int y, int x) {
		return infected[y][x];
	}

	/**
	 * 
	 * @param y position
	 * @param x position
	 * @return true if the space on that position has a person on it
	 */
	public int isOccupied(int y, int x) {
		return occupied[y][x];
	}

	/**
	 * 
	 * @param y position
	 * @param x position
	 * 
	 *          Sets specified position to be infected
	 */
	public void infect(int y, int x) {
		infected[y][x] = duration;
	}

	/**
	 * 
	 * @param y position
	 * @param x position
	 * 
	 *          Sets specified position to not be infected
	 */
	public void disinfect(int y, int x) {
		infected[y][x]--;
	}

	/**
	 * getter for hasPort
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasPort(int x, int y) {
		return hasPort[x][y];
	}

	/**
	 * 
	 * @param y      position
	 * @param x      position
	 * @param status is set to the specified position
	 */
	public void occupy(int y, int x, int status) {
		occupied[y][x] = status;
	}

	/**
	 * Grid setup
	 */
	private void gridSetup() {
		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(800, 700);
	}

	/**
	 * 
	 * @param y    position
	 * @param x    position
	 * @param size of mask
	 * 
	 *             Draws a mask on the specified position
	 */
	private void drawMask(double y, double x, double size) {
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		StdDraw.setPenRadius(size / 4);
		StdDraw.line(x - 0.1, y + 0.12, x + 0.1, y + 0.12);
		StdDraw.line(x - 0.1, y + 0.14, x + 0.1, y + 0.14);
		StdDraw.line(x - 0.1, y + 0.16, x + 0.1, y + 0.16);
		StdDraw.setPenRadius(size / 12);
		StdDraw.line(x + 0.1, y + 0.1, x + 0.27, y);
		StdDraw.line(x - 0.1, y + 0.1, x - 0.27, y);
	}

	/**
	 * This method draws the updated grid every time it is called
	 */
	public void drawGrid() {
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(length, 0);
		double rad;
		StdDraw.show(100);
		StdDraw.clear();
		// draw infected spaces and people
		for (double y = 0.5; y < length; y++) {
			for (double x = 0.5; x < width; x++) {
				if (isInfected((int) y, (int) x) > 0 && this.hasPort((int) x, (int) y) == false) {
					StdDraw.setPenColor(StdDraw.ORANGE);
					StdDraw.filledRectangle(x, y, 0.5, 0.5);
					// StdDraw.setPenColor(StdDraw.WHITE);
					// StdDraw.filledRectangle(x, y, 0.4, 0.4);

					this.disinfect((int) y, (int) x);
				}

				//////////// temporary port drawing

				if (this.hasPort((int) x, (int) y)) {
					StdDraw.setPenColor(colors[getPort((int) x, (int) y)]);
					StdDraw.filledRectangle(x, y, 0.5, 0.5);
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.filledRectangle(x, y, 0.3, 0.3);
				}

				////////////////////////////////////

				// pen radius is depended on the smallest of width and length
				if (length > width)
					rad = length;
				else
					rad = width;
				// draws people with the color that matches their status
				if (isOccupied((int) y, (int) x) != 0) {
					if (isOccupied((int) y, (int) x) == 1)
						StdDraw.setPenColor(StdDraw.BLUE);
					else if (isOccupied((int) y, (int) x) == 2) {
						StdDraw.setPenColor(StdDraw.RED);
						possibleInfection((int) y, (int) x);
					} else if (isOccupied((int) y, (int) x) == 3)
						StdDraw.setPenColor(StdDraw.GREEN);

					rad = 0.255 / (rad * 0.3);
					StdDraw.setPenRadius(rad);
					StdDraw.point(x, y);
				}
				// draws mask
				if (takesMeasures((int) y, (int) x))
					this.drawMask(y, x, rad);

			}
		}

		// draw lines
		StdDraw.setPenColor(col);
		StdDraw.setPenRadius(0.1 / ((width + length) / 2));
		for (int y = 0; y <= length; y++) {
			StdDraw.line(0, y, width, y);
		}
		for (int x = 0; x <= width; x++) {
			StdDraw.line(x, 0, x, length);
		}

	}

	/**
	 * 
	 * @param y position
	 * @param x position
	 * 
	 *          This method can possibly infect the specified position when called
	 *          This is dependent of the inf variable and random chance
	 */
	public void possibleInfection(int y, int x) {
		if (Randomizer.getBoolean(inf))
			this.infect(y, x);
	}

}
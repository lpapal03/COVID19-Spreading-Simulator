

import java.util.Scanner;
import java.util.InputMismatchException;

public class SimulationDriver {

	public static void start() {

		Scanner scan = new Scanner(System.in);
		boolean error = true;

		int cities = 0; // same for all
		int time = 0; // same for all
		int infect = 0; // same for all
		int duration = 0; // same for all
		int fatal = 0; // same for all
		int howLikelyToMove = 0; // same for all

		int choice = 0; // helping variable
		String input; // helping variable



		// ask how many cities
		do {
			error = false;
			System.out.print("How many cities: ");
			input = scan.nextLine();
			try {
				cities = Integer.parseInt(input);
				if (cities < 1) {
					throw new NotPossibleAmountException("Must be grater than 0");
				}
			} catch (NumberFormatException e) {
				System.out.println("Must be a positive integer");
				error = true;
			} catch (NotPossibleAmountException e) {
				System.out.println(e.getMessage());
				error = true;
			}
		} while (error);
		System.out.println();
		// create arrays of elements different for every city
		int width[] = new int[cities];
		int length[] = new int[cities];
		int people[] = new int[cities];
		int careful[] = new int[cities];
		int sick[] = new int[cities];
		int airports[] = new int[cities];
		// parameters that are the same of all cities

		do {
			error = false;
			System.out.println("Regarding infectivity, deadliness, likelyness for people to move,");
			System.out.println("time it takes for a person to heal and simulation steps for every city:\n");
			System.out.print("Use default values from trusted sources / Proceed to custom settings (1/2): ");
			input = scan.nextLine();
			try {
				choice = Integer.parseInt(input);
				if (choice != 1 && choice != 2) {
					throw new NotPossibleAmountException("Can only choose '1' or '2'");
				}
			} catch (NumberFormatException e) {
				System.out.println("Can only choose '1' or '2'");
				error = true;
			} catch (NotPossibleAmountException e) {
				System.out.println(e.getMessage());
				error = true;
			}
		} while (error);
		System.out.println();

		if (choice == 1) {
			time = 150;
			fatal = 4;
			infect = 60;
			howLikelyToMove = 70;
			duration = 20; // how much time people need to recover and blocks to be disinfected
		} else {
			// steps
			do {
				error = false;
				System.out.print("Give the time (simulation steps) over 100 is recommended: ");
				input = scan.nextLine();
				try {
					time = Integer.parseInt(input);
					if (time < 0) {
						throw new NotPossibleAmountException("Must be positive");
					}

				} catch (NumberFormatException e) {
					System.out.println("Must be a positive integer");
					error = true;
				} catch (NotPossibleAmountException e) {
					System.out.println(e.getMessage());
					error = true;
				}
			} while (error);
			System.out.println();

			// how fatal
			do {
				error = false;
				System.out.print("How fatal is the disease?(%): ");
				input = scan.nextLine();
				try {
					fatal = Integer.parseInt(input);
					if (fatal > 100 || fatal < 0) {
						throw new NotPossibleAmountException("Must be between 0 and 100");
					}
				} catch (NumberFormatException e) {
					System.out.println("Must be an integer");
					error = true;
				} catch (NotPossibleAmountException e) {
					System.out.println(e.getMessage());
					error = true;
				}
			} while (error);
			System.out.println();

			// how contageous
			do {
				error = false;
				System.out.print("How contageous is the disease?(%): ");
				input = scan.nextLine();
				try {
					infect = Integer.parseInt(input);
					if (infect > 100 || infect < 0) {
						throw new NotPossibleAmountException("Must be between 0 and 100");
					}
				} catch (NumberFormatException e) {
					System.out.println("Must be an integer");
					error = true;
				} catch (NotPossibleAmountException e) {
					System.out.println(e.getMessage());
					error = true;
				}
			} while (error);
			System.out.println();

			// get how long it takes patients to heal
			do {
				error = false;
				System.out.print("How long does it take for a patient to heal? (in simulation steps): ");
				input = scan.nextLine();
				try {
					duration = Integer.parseInt(input);
				} catch (NumberFormatException e) {
					System.out.println("Must be an integer");
					error = true;
				}
			} while (error);
			System.out.println();

			// get how likely is someone to move
			do {
				error = false;
				System.out.print("How likely is it for people to move per simulation step?(%): ");
				input = scan.nextLine();
				try {
					howLikelyToMove = Integer.parseInt(input);
					if (howLikelyToMove > 100 || howLikelyToMove < 0) {
						throw new NotPossibleAmountException("Must be between 0 and 100");
					}
				} catch (NumberFormatException e) {
					System.out.println("Must be an integer");
					error = true;
				} catch (NotPossibleAmountException e) {
					System.out.println(e.getMessage());
					error = true;
				}
			} while (error);
			System.out.println();
		}

		// things that differ from city to city
		do {
			error = false;
			System.out.println("Auto-complete stats such as initialy infected people, how many");
			System.out.println("people take measures, and the amount of airports a city has/");
			System.out.print("enter manually for each city? (1/2): ");
			input = scan.nextLine();
			try {
				choice = Integer.parseInt(input);
				if (choice != 1 && choice != 2) {
					throw new NotPossibleAmountException("Can only choose '1' or '2'");
				}
			} catch (NumberFormatException e) {
				System.out.println("Can only choose '1' or '2'");
				error = true;
			} catch (NotPossibleAmountException e) {
				System.out.println(e.getMessage());
				error = true;
			}
		} while (error);
		System.out.println();

		// get info for every city
		for (int i = 0; i < cities; i++) {
			// get length
			do {
				error = false;
				System.out.print("Give the length for city " + (i + 1) + ": ");
				input = scan.nextLine();
				try {
					length[i] = Integer.parseInt(input);
					if (length[i] <= 0) {
						throw new NotPossibleAmountException("Must be over 0");
					}

				} catch (NumberFormatException e) {
					System.out.println("Must be an integer");
					error = true;
				} catch (NotPossibleAmountException e) {
					System.out.println(e.getMessage());
					error = true;
				}
			} while (error);
			System.out.println();

			// get width
			do {
				error = false;
				System.out.print("Give the width for city " + (i + 1) + ": ");
				input = scan.nextLine();
				try {
					width[i] = Integer.parseInt(input);
					if (width[i] <= 0) {
						throw new NotPossibleAmountException("Must be over 0");
					}

				} catch (NumberFormatException e) {
					System.out.println("Must be a positive integer");
					error = true;
				} catch (NotPossibleAmountException e) {
					System.out.println(e.getMessage());
					error = true;
				}
			} while (error);
			System.out.println();

			// get the amount of people
			do {
				error = false;
				System.out.print("Give the amount of people for city " + (i + 1) + ": ");
				input = scan.nextLine();
				try {
					people[i] = Integer.parseInt(input);
					if (people[i] > length[i] * width[i] || people[i] < 0) {
						throw new NotPossibleAmountException(
								"Cannot have more than " + length[i] * width[i] + " people or less than 0");
					}
				} catch (NumberFormatException e) {
					System.out.println("Must be a positive integer");
					error = true;
				} catch (NotPossibleAmountException e) {
					System.out.println(e.getMessage());
					error = true;
				}
			} while (error);
			System.out.println();

			if (choice == 2) {

				// get amount of people who take measures
				do {
					error = false;
					System.out.print("Give the amount that take protective measures(like wear masks) for city "
							+ (i + 1) + ": ");
					input = scan.nextLine();
					try {
						careful[i] = Integer.parseInt(input);
						if (careful[i] > people[i] - sick[i] || careful[i] < 0) {
							throw new NotPossibleAmountException(
									"Cannot have more than " + (people[i] - sick[i]) + " or less than 0");
						}
					} catch (NumberFormatException e) {
						System.out.println("Must be a positive integer");
						error = true;
					} catch (NotPossibleAmountException e) {
						System.out.println(e.getMessage());
						error = true;
					}
				} while (error);
				System.out.println();

				// get number of initially sick people
				do {
					error = false;
					System.out.print("Give the amount of INITIALLY INFECTED people for city" + (i + 1) + ": ");
					input = scan.nextLine();
					try {
						sick[i] = Integer.parseInt(input);
						if (sick[i] > people[i] || sick[i] < 0) {
							throw new NotPossibleAmountException(
									"Cannot have more than " + people + " sick people or less than 0");
						}
					} catch (NumberFormatException e) {
						System.out.println("Must be a positive integer");
						error = true;
					} catch (NotPossibleAmountException e) {
						System.out.println(e.getMessage());
						error = true;
					}
				} while (error);
				System.out.println();

				// get amount of airports
				do {
					error = false;
					System.out.print("How many airports does city " + (i + 1) + " have? ");
					input = scan.nextLine();
					try {
						airports[i] = Integer.parseInt(input);
						if (airports[i] > (2 * (length[i] + width[i]))-4) {
							throw new NotPossibleAmountException(
									"Cannot have more than " + ((2 * (length[i] + width[i]))-4) + " airports");
						}
					} catch (NumberFormatException e) {
						System.out.println("Must be a positive integer");
						error = true;
					} catch (NotPossibleAmountException e) {
						System.out.println(e.getMessage());
						error = true;
					}
				} while (error);
				System.out.println();
			} else {
				sick[i] = 1;
				careful[i] = (int) ((double) people[i] * 0.5);
				airports[i] = ((2 * (length[i] + width[i])) -4)/5 + 1;
			}

		}

		// same for all
		System.out.println("\nThe disease is " + infect + "% contageous");
		System.out.println("The disease is " + fatal + "% deadly");
		System.out.println("People have a " + howLikelyToMove + " % probability of moving");
		System.out.println("People will recover in " + duration + " simulation steps");
		System.out.println("The simulation will run for " + time + " steps\n");

		// different
		if (cities == 1)
			System.out.println(cities + " city will be simulated\n");
		else
			System.out.println(cities + " cities will be simulated\n");
		for (int i = 0; i < cities; i++) {
			System.out.println("City " + (i + 1));
			System.out.println("----------");
			System.out.println("Size: " + length[i] + " x " + width[i]);
			System.out.println("People: " + people[i]);
			System.out.println("Initialy infected: " + sick[i]);
			System.out.println("People who takes protective measures: " + careful[i]);
			System.out.println("Airports: " + airports[i]);
			System.out.println("\n");
		}


		int totppl=0;
		for(int i=0; i<cities;i++) {
			System.out.println("I: " + people[i]);
			totppl+=people[i];
		}
		Crowd crowd = new Crowd(duration, width, length, people, howLikelyToMove, sick, fatal, infect, careful, cities,
				airports);

		int toDraw = 0;

		while (time > 0) {
			if (cities != 1)

				if (time % 5 == 0 && time != 0)
					if (toDraw < cities - 1)
						toDraw++;
					else
						toDraw = 0;
			crowd.move(toDraw);
			time--;
		}
		crowd.move(toDraw);
		
		System.out.println(
				"\n\nOF THE " + totppl+ " INITIAL PEOPLE:\n" + InfectedHuman.getTotalCases() + " : GOT INFECTED\n"
						+ RecoveredHuman.getTotalCases() + " : RECOVERED\n" + DeceasedHuman.getTotalCases()
						+ " : PASSED AWAY\n" + (totppl - InfectedHuman.getTotalCases()) + " : NEVER GOT INFECTED\n" + TraveledHuman.getTotal() + " : TRAVELS OCCURED");

		scan.close();
	}

}

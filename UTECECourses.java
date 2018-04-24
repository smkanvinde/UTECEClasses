import java.util.*;

public class UTECECourses {
	//scale values
	public static double riskyLow = 12;
	public static double nrLow = 16;
	public static double ded = 20;

	public static String getYN(String prompt, Scanner sc) {
		boolean first = true;
		String input;

		do {
			if (!first) {
				System.out.println("Y/N, dumbass.");
			}

			System.out.println();
			System.out.print(prompt);
			System.out.println(" Y/N");
			System.out.print(">");

			input = sc.nextLine();
			input = input.toUpperCase();
			input = input.replaceAll("\\s+","");

			first = false;
		} while (!input.equals("Y") && !input.equals("N"));

		return input;
	}

	/*
	 * Please send me data for all the power courses!!!
	 */
	public static Map<String,Double> initialize() {
			Map<String,Double> courses = new HashMap<>();
			courses.put("EE445L",  7.5);
			courses.put("EE460N",  6.0);
			courses.put("EE445M",  5.0);
			courses.put("EE461P",  3.5);
			courses.put("EE461L",  2.5);
			courses.put("EE422C",  2.5);
			courses.put("EE464",   2.5);
			courses.put("EE445S",  -1.0);
			courses.put("EE460M",  4.0);
			courses.put("EE460J",  4.5);
			courses.put("EE438",   3.5);
			courses.put("EE440",   3.5);
			courses.put("EE460R",  7.0);
			courses.put("EE362K",  2.5);
			courses.put("EE312",   3.0);
			courses.put("EE313",   3.0);
			courses.put("EE411",   3.0);
			courses.put("EE338L",  -1.0);
			courses.put("EE302",   2.0);
			courses.put("EE306",   2.0);
			courses.put("EE462L",  4.5);
			courses.put("EE471C",  -1.0);
			courses.put("EE374K",  3.5);
			courses.put("EE371R",  -1.0);
			courses.put("EE361Q",  1.5);
			courses.put("EE360C",  4.0);
			courses.put("EE360P",  3.0);
			courses.put("EE361C",  3.5);
			courses.put("EE362S",  -1.0);
			courses.put("EE363N",  -1.0);
			courses.put("EE362Q",  -1.0);
			courses.put("EE339",   4.0);
			courses.put("EE325",   4.5);
			courses.put("EE351K",  5.5);
			courses.put("EE333T",  3.5);
			courses.put("EE325K",  3.5);
			courses.put("EE316",   4.0);
			courses.put("EE360T",  3.0);
			courses.put("EE461S",  6.0);
			courses.put("EE364",   3.0);
			courses.put("EE319K",  4.0);
			courses.put("M408C",   1.5);
			courses.put("M408D",   1.5);
			courses.put("M408L",   1.5);
			courses.put("M408D",   2.0);
			courses.put("M408M",   2.0);
			courses.put("M427J",   2.5);
			courses.put("M427L",   3.0);
			courses.put("M340L",   2.5);
			courses.put("M325K",   3.0);
			courses.put("M328K",   3.0);
			courses.put("PHY303K", 1.0);
			courses.put("PHY103M", 2.0);
			courses.put("PHY303L", 2.0);
			courses.put("PHY103N", 1.0);
			return courses;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			//init
			Map<String,Double> allCourses = initialize();

			System.out.println();
			System.out.println("Welcome to UTECECourses.");
			System.out.println("Please input your courses in the following format:");
			System.out.println("[Department(EE,M,PHY)][Course number + optional letter]");
			System.out.println("Example: EE445L");
			System.out.println("For senior design, drop the letter.");
			System.out.println("Please input your courses. Enter D when finished.");
			System.out.println();
			System.out.print(">");

			//red stars and bermuda triangle
			boolean embsys = false;
			boolean comparch = false;
			boolean os = false;
			boolean vlsi = false;
			boolean prob = false;

			String input = sc.nextLine();
            String yesno;
			input = input.toUpperCase();
			input = input.replaceAll("\\s+","");
			double totalDiff = 0;
			double tempDiff;
			while (!input.equals("D")) {
				if (allCourses.containsKey(input))	{
					//be able to get the course by here
					tempDiff = allCourses.get(input);
					if (tempDiff == -1) {
						System.out.println("We do not have data for " + input + ".");
						System.out.println("Please ask an upperclassman to rank it and enter the score here:");
						System.out.print(">");
						tempDiff = Double.parseDouble(sc.nextLine());
						totalDiff += tempDiff;
						System.out.println();
						System.out.println("Please update " + input + "'s entry in initialize() and submit a pull request after this run.");
					}
					else {
						switch (input) {
							case "EE445L":
								embsys = true;
								break;
							case "EE460N":
								comparch = true;
								break;
							case "EE460R":
								vlsi = true;
								break;
							case "EE461S":
								os = true;
								break;
							case "EE351K":
								prob = true;
								break;
							case "EE422C":
								yesno = getYN("Is Dr. Perry teaching EE422C?", sc);
								if (yesno.equals("Y")) {
									tempDiff -= 1.5;
								}
								break;
							case "EE411":
								yesno = getYN("Is Dr. Swartzlander teaching EE411?", sc);
								if (yesno.equals("Y")) {
									tempDiff -= 2;
								}
								break;
							case "EE302":
								yesno = getYN("Did you do circuits in high school?", sc);
								if (yesno.equals("N")) {
									tempDiff += 1;
								}
								break;
							case "EE306":
								yesno = getYN("Have you ever programmed?", sc);
								if (yesno.equals("N")) {
									tempDiff += 1;
								}
								yesno = getYN("Is Dr. Patt teaching EE306?", sc);
								if (yesno.equals("Y")) {
									tempDiff += 1;
								}
								break;
							case "EE333T":
								yesno = getYN("Is EE333T abroad?", sc);
								if (yesno.equals("Y")) {
									tempDiff -= 2.5;
								}
						}
						totalDiff += tempDiff;
					}
				}
				else {
					System.out.println(input + " is not in our database.");
					System.out.println("Please ask an upperclassman to rank it and enter the score here:");
					System.out.print(">");
					tempDiff = Double.parseDouble(sc.nextLine());
					totalDiff += tempDiff;
					System.out.println();
					System.out.println("Please add your course to initialize() and submit a pull request after this run.");
				}

				System.out.println();
				System.out.print("Next >");
				input = sc.nextLine();
				input = input.replaceAll("\\s+","");
				input = input.toUpperCase();
			}

			String fresh = getYN("Are you a freshman?", sc);
			if (fresh.equals("Y")) {
				riskyLow = 10;
				nrLow = 14;
				ded = 18;
			}

			int bermuda = 0;
			if (embsys) bermuda++;
			if (comparch) bermuda++;
			if (os) bermuda++;
			if (bermuda > 1) {
				System.out.println();
				System.out.println("The Bermuda Triangle of EE:");
				System.out.println("EE460N");
				System.out.println("EE445L");
				System.out.println("EE461S");
				System.out.println("Only take ONE of these at a time.");
				if (prob)
					System.out.println("EE351K is manageable with ONE of the Bermuda Triangle.");
				if (vlsi)
					System.out.println("EE460R can be considered equivalent to any of the Bermuda Triangle.");
			}

			System.out.println("Your score is " + totalDiff);

			if(totalDiff >= ded) {
				System.out.println("ABORT ABORT ABORT");
			} else if (totalDiff >= nrLow) {
				System.out.println("Would not recommend.");
			} else if (totalDiff >= riskyLow) {
				System.out.println("Your schedule is a bit risky.");
			} else {
				System.out.println("U gud fam.");
			}

			String yes = getYN("Would you like to test another schedule?", sc);
			if (yes.equals("N")) {
				sc.close();
				System.exit(0);
			}
		}
	}
}

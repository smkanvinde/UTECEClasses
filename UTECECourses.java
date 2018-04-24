import java.util.*;

public class UTECECourses {


	/* 
	 * Please send me data for all the power courses!!!
	 Hello, 'tis me, the power queen
*/
	public static HashMap<String,Course> initialize() {
			HashMap<String,Course> courses = new HashMap<String,Course>();
			courses.put("EE445L", new Course("EE445L",7.5));
			courses.put("EE460N", new Course("EE460N",6));
			courses.put("EE445M", new Course("EE445M",5));
			courses.put("EE461P", new Course("EE461P",3.5));
			courses.put("EE461L", new Course("EE461L",2.5));
			courses.put("EE422C", new Course("EE422C",2.5));
			courses.put("EE464", new Course("EE464",2.5)); 
			courses.put("EE445S", new Course("EE445S",-1));
			courses.put("EE460M", new Course("EE460M",4));
			courses.put("EE460J", new Course("EE460J",4.5));
			courses.put("EE438", new Course("EE438",3.5));
			courses.put("EE440", new Course("EE440",3.5));
			courses.put("EE460R", new Course("EE460R",7));
			courses.put("EE312", new Course("EE312",3));
			courses.put("EE313", new Course("EE313",3));
			courses.put("EE411", new Course("EE411",3)); 
			courses.put("EE338L", new Course("EE338L",-1));
			courses.put("EE302", new Course("EE302",2)); 
			courses.put("EE306", new Course("EE306",2)); 
			courses.put("EE462L", new Course("EE462L",4.5));
			courses.put("EE471C", new Course("EE471C",-1));
			courses.put("EE374K", new Course("EE374K",3.5));
			courses.put("EE371R", new Course("EE371R",-1));
			courses.put("EE361Q", new Course("EE361Q",1.5));
			courses.put("EE360C", new Course("EE360C",4));
			courses.put("EE360P", new Course("EE360P",3));
			courses.put("EE361C", new Course("EE361C",3.5));
			courses.put("EE362S", new Course("EE362S",2.5));
			courses.put("EE363N", new Course("EE363N",-1));
			courses.put("EE362Q", new Course("EE362Q",5));
			courses.put("EE339", new Course("EE339",4));
			courses.put("EE325", new Course("EE325",4.5));
			courses.put("EE351K", new Course("EE351K",5.5));
			courses.put("EE333T", new Course("EE333T",3.5)); 
			courses.put("EE325K", new Course("EE325K",3.5));
			courses.put("EE316", new Course("EE316",4));
			courses.put("EE360T", new Course("EE360T",3));
			courses.put("EE461S", new Course("EE461S",6));
			courses.put("EE364", new Course("EE364",3)); 
			courses.put("EE319K", new Course("EE319K",4));
			courses.put("M408C", new Course("M408C",1.5));
			courses.put("M408D", new Course("M408K",1.5));
			courses.put("M408L", new Course("M408L",1.5));
			courses.put("M408D", new Course("M408D",2));
			courses.put("M408M", new Course("M408M",2));
			courses.put("M427J", new Course("M427J",2.5));
			courses.put("M427L", new Course("M427L",3));
			courses.put("M340L", new Course("M340L",2.5));
			courses.put("M325K", new Course("M325K",3));
			courses.put("M328K", new Course("M328K",3));
			courses.put("PHY303K", new Course("PHY303K",1));
			courses.put("PHY103M", new Course("PHY103M",2));
			courses.put("PHY303L", new Course("PHY303L",2));
			courses.put("PHY103N", new Course("PHY103N",1));
			courses.put("EE341", new Course("EE341", 5.5));
			courses.put("EE369", new Course("EE369", 3));
			courses.put("EE368L", new Course("EE368L", 4));
			courses.put("EE379K0", new Course("EE379K0",2)); //Smart Grids.
			courses.put("EE362K", new Course("EE362K", 3));
			courses.put("EE339S", new Course("EE339S", 5));
			courses.put("EE362R", new Course("EE362R", 1));

			return courses;
	}

	public static void main(String[] args) {
		boolean rpt = true;
		Scanner sc = new Scanner(System.in);
		while (rpt){
			//init 
			HashMap<String,Course> allCourses = initialize();
			
			
			//scale values
			double safe = 11.99;
			double riskyLow = 12;
			double riskyHigh = 15.99;
			double nrLow = 16;
			double nrHigh = 19.99;
			double ded = 20;
			
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
			input = input.toUpperCase();
			input = input.replaceAll("\\s+","");
			double totalDiff = 0;
			double tempDiff;
			while(!input.equals("D")) {
				if (allCourses.containsKey(input))  {
					//be able to get the course by here
					Course c = allCourses.get(input);
					tempDiff = c.getDiff();
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
						if (input.equals("EE445L")){
							embsys = true;	
						}
						else if (input.equals("EE460N")) {
							comparch = true;
						}
						else if (input.equals("EE460R")) {
							vlsi = true;
						}
						else if (input.equals("EE461S")) {
							os = true;
						}
						else if (input.equals("EE351K")) {
							prob = true;
						}
						else if (input.equals("EE422C")) { //Perry exception
							System.out.println();
							System.out.println("Is Dr. Perry teaching EE422C? Y/N");
							System.out.print(">");
							input = sc.nextLine();
							input = input.replaceAll("\\s+","");
							input = input.toUpperCase();
							while (!input.equals("Y") && !input.equals("N")) {
								System.out.println();
								System.out.println("Y/N, dumbass.");
								System.out.println("Is Dr. Perry teaching EE422C? Y/N");
								System.out.print(">");
								input = sc.nextLine();
								input = input.toUpperCase();
								input = input.replaceAll("\\s+","");
							}
							if (input.equals("Y")) {
								tempDiff -= 1.5;
							}
						}
						else if (input.equals("EE312")) { //Chase exception
							System.out.println();
							System.out.println("Is Dr. Chase teaching EE312? Y/N");
							System.out.print(">");
							input = sc.nextLine();
							input = input.replaceAll("\\s+","");
							input = input.toUpperCase();
							while (!input.equals("Y") && !input.equals("N")) {
								System.out.println();
								System.out.println("Y/N, dumbass.");
								System.out.println("Is Dr. Chase teaching EE312? Y/N");
								System.out.print(">");
								input = sc.nextLine();
								input = input.toUpperCase();
								input = input.replaceAll("\\s+","");
							}
							if (input.equals("Y")) {
								tempDiff += 1;
							}
						}
						else if (input.equals("EE411")) { //Swartz exception
							System.out.println();
							System.out.println("Is Dr. Swartzlander teaching EE411? Y/N");
							System.out.print(">");
							input = sc.nextLine();
							input = input.replaceAll("\\s+","");
							input = input.toUpperCase();
							while (!input.equals("Y") && !input.equals("N")) {
								System.out.println();
								System.out.println("Y/N, dumbass.");
								System.out.println("Is Dr. Swartzlander teaching EE411? Y/N");
								System.out.print(">");
								input = sc.nextLine();
								input = input.toUpperCase();
								input = input.replaceAll("\\s+","");
							}
							if (input.equals("Y")) {
								tempDiff -= 2;
							}
						}
						else if (input.equals("EE302")) { //302 prior exp exception
							System.out.println();
							System.out.println("Did you do circuits in high school? Y/N");
							System.out.print(">");
							input = sc.nextLine();
							input = input.replaceAll("\\s+","");
							input = input.toUpperCase();
							while (!input.equals("Y") && !input.equals("N")) {
								System.out.println();
								System.out.println("Y/N, dumbass.");
								System.out.println("Did you do circuits in high school? Y/N");
								System.out.print(">");
								input = sc.nextLine();
								input = input.toUpperCase();
								input = input.replaceAll("\\s+","");
							}
							if (input.equals("N")) {
								tempDiff += 1;
							}
						}
						else if (input.equals("EE306")) { //306 prior exp exception
							System.out.println();
							System.out.println("Have you ever programmed? Y/N");
							System.out.print(">");
							input = sc.nextLine();
							input = input.replaceAll("\\s+","");
							input = input.toUpperCase();
							while (!input.equals("Y") && !input.equals("N")) {
								System.out.println();
								System.out.println("Y/N, dumbass.");
								System.out.println("Have you ever programmed? Y/N");
								System.out.print(">");
								input = sc.nextLine();
								input = input.toUpperCase();
								input = input.replaceAll("\\s+","");
							}
							if (input.equals("N")) {
								tempDiff += 1;
							}
							System.out.println();
							System.out.println("Is Dr. Patt teaching EE306? Y/N"); //Patt exception
							System.out.print(">");
							input = sc.nextLine();
							input = input.replaceAll("\\s+","");
							input = input.toUpperCase();
							while (!input.equals("Y") && !input.equals("N")) {
								System.out.println();
								System.out.println("Y/N, dumbass.");
								System.out.println("Is Dr. Patt teaching EE306? Y/N");
								System.out.print(">");
								input = sc.nextLine();
								input = input.toUpperCase();
								input = input.replaceAll("\\s+","");
							}
							if (input.equals("Y")) {
								tempDiff += 1;
							}
						}
						else if (input.equals("EE333T")) { //abroad exception
							System.out.println();
							System.out.println("Is EE333T abroad? Y/N");
							System.out.print(">");
							input = sc.nextLine();
							input = input.replaceAll("\\s+","");
							input = input.toUpperCase();
							while (!input.equals("Y") && !input.equals("N")) {
								System.out.println();
								System.out.println("Y/N, dumbass.");
								System.out.println("Is EE333 abroad? Y/N");
								System.out.print(">");
								input = sc.nextLine();
								input = input.toUpperCase();
								input = input.replaceAll("\\s+","");
							}
							if (input.equals("Y")) {
								tempDiff -= 2.5;
							}
						}
						
						totalDiff += tempDiff;
					}
				} 
				else {
					if (input.equals("EE379K")) {
							System.out.println("Please specify:");
							System.out.println("0: Smart Grids");
							System.out.println("1: High Throughput Nanopatterning");
							System.out.println("2: Information Security and Privacy");
							System.out.println("3: Data Science Laboratory");
							System.out.print(">");
							int choice = Integer.parseInt(sc.nextLine());
							if (choice == 0) {
								String temp = input + "0";
								Course c = allCourses.get(temp);
								tempDiff = c.getDiff();
								totalDiff += tempDiff;
							}
							else {
								System.out.println("That version of EE379K is not in our database.");
								System.out.println("Please ask an upperclassman to rank it and enter the score here:");
								System.out.print(">");
								tempDiff = Double.parseDouble(sc.nextLine());
								totalDiff += tempDiff;	
								System.out.println();
								System.out.println("Please add your course to initialize() as an incrementing 'EE379Kx' (x is currently 1) and submit a pull request after this run.");
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
				}
				
				System.out.println();
				System.out.print("Next >");
				input = sc.nextLine();
				input = input.replaceAll("\\s+","");
				input = input.toUpperCase();
			}	
			
			System.out.println();
			System.out.println("Are you a freshman? Y/N");
			System.out.print(">");
			boolean freshman = false;
			String fresh = sc.nextLine();
			fresh = fresh.toUpperCase();
			fresh = fresh.replaceAll("\\s+","");
			while (!fresh.equals("Y") && !fresh.equals("N")) {
				System.out.println();
				System.out.println("Y/N, dumbass.");
				System.out.println("Are you a freshman? Y/N");
				System.out.print(">");
				fresh = sc.nextLine();
				fresh = fresh.toUpperCase();
				fresh = fresh.replaceAll("\\s+","");
			}
			
			if (fresh.equals("Y")) {
				freshman = true;
			}
			
			if (freshman) {
				safe = 9.99;
				riskyLow = 10;
				riskyHigh = 13.99;
				nrLow = 14;
				nrHigh = 17.99;
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
			
			if (totalDiff <= safe) {
				System.out.println("U gud fam.");	
			}
			else if (totalDiff >= riskyLow && totalDiff <= riskyHigh) {
				System.out.println("Your schedule is a bit risky.");
			}
			else if (totalDiff >= nrLow && totalDiff <= nrHigh) {
				System.out.println("Would not recommend.");
			}
			else if (totalDiff >= ded) {
				System.out.println("ABORT ABORT ABORT");
			}
			
			System.out.println();
			System.out.println("Would you like to test another schedule? Y/N");
			System.out.print(">");
			boolean yn = false;
			String yes = sc.nextLine();
			yes = yes.toUpperCase();
			yes = yes.replaceAll("\\s+","");
			while (!yes.equals("Y") && !yes.equals("N")) {
				System.out.println();
				System.out.println("Y/N, dumbass.");
				System.out.println("Would you like to test another schedule? Y/N");
				System.out.print(">");
				yes = sc.nextLine();
				yes = yes.toUpperCase();
				yes = yes.replaceAll("\\s+","");
			}
			
			if (yes.equals("Y")) {
				rpt = true;
			}
			else {
				rpt = false;
			}
		}
		sc.close();
	}

	
}

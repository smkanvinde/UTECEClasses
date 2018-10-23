import java.util.*;
import java.io.*;
import java.net.*;
import org.json.*;


public class UTECECourses {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static void printError(String printThis) {System.out.println(ANSI_RED + printThis + ANSI_RESET); }
    private static void printSucess(String printThis) {System.out.println(ANSI_GREEN + printThis + ANSI_RESET); }
    private static void printMessage(String printThis) {System.out.println(ANSI_BLUE + printThis + ANSI_RESET); }

    /* Course list */
    protected static HashMap<String,Double> allCourses = courseListInitialize();
    protected static HashMap<String,Boolean> bermuda = bermudaInitialize();

    /* Values for the tiers */
    protected static double riskyLow = 12;
    protected static double nrLow = 16;
    protected static double ded = 20;

    public static HashMap<String,Double> webCourseWeights() {
        String tempCourses = getCourses("https://ece.yuriy.io/api/classes?fbclid=IwAR1ZP-FmirY16PVybbXQnUSxvzGeXaRpN6lTy5si1Qr_yIhiBCc_qAZb1Pc");
        if(tempCourses == null) return null; 

        JSONArray arr = new JSONArray(tempCourses);
        HashMap<String,Double> webCourses = new HashMap<>();

        for(int i = 0 ; i < arr.length(); i++){
            JSONObject testObj = arr.getJSONObject(i);
            webCourses.put(testObj.getString("code"), testObj.getDouble("rating") / 200.0);

        }


        return webCourses;
    }
    /************************************************************
     * @name: main                                              *
     * @description: All necessary logic for calculating        *
     * schedule workload.                                       *
     ***********************************************************/
    public static void main(String[] args) {
        printMessage("Connecting to the Web Server. This might take a few seconds");
        HashMap<String,Double> webCourses = webCourseWeights();
        if (webCourses != null ) {
            allCourses = webCourses;
            printSucess("Connection successful. Will use results from web server");
        } else {
            printError("Unable to to connect to the server. Will use pre set values instead");
        }

        Scanner sc = new Scanner(System.in);

        while (true){

            /* Reset the tier values (needed due to freshmanChecker()) */
            riskyLow = 12;
            nrLow = 16;
            ded = 20;

			/* Title screen stuff */
            System.out.println("\nWelcome to UTECECourses.");
            System.out.println("Please input your courses in the following format:");
            System.out.println("[Department(EE,M,PHY)][Course number + optional letter]");
            System.out.println("Example: EE445L");
            System.out.println("For senior design, drop the letter.");
            System.out.println("Please input your courses. Enter D when finished.\n");
            System.out.print(">");

			/* Starred and Bermuda Triangle courses */
            bermuda = bermudaInitialize(); //Reinitialize to reset all booleans in case of multiple iterations

			/* Intialize necessary variables */
            String input = sc.nextLine();
            input = input.toUpperCase();
            input = input.replaceAll("\\s+","");
            double totalDiff = 0;
            double tempDiff = 0;

			/* Begin parsing */
            while(!input.equals("D")) {
                if (allCourses.containsKey(input))  {
                    tempDiff = allCourses.get(input); //Initialize the weight
                    if (tempDiff == -1) { //Should the class have no assigned weight
                        tempDiff += addWeight(sc, input, true);
                        System.out.println("\nPlease update " + input + "'s entry in initialize() and submit a pull request after this run.");
                    }
                    else { //Adjust the weight if needed and account for special courses
                        bermudaChecker(input); //Check if a bermuda course was entered
                        tempDiff += profException(sc, input); //Adjust the course weight depending on professor taken
                        tempDiff += priorExperience(sc, input); //Account for high school experience
                    }
                }
                else { //If we are dealing with an EE379K course
                    if (input.equals("EE379K")) {
                        tempDiff += handlerFor379K(sc);
                    }
                    else { //If course is not defined in initialize method
                        tempDiff += addWeight(sc, input, false);
                        System.out.println("\nPlease add your course to initialize() and submit a pull request after this run.");
                    }
                }

                totalDiff += tempDiff; //Update the total

				/* Ask for next input */
                System.out.print("\nNext >");
                input = sc.nextLine();
                input = input.replaceAll("\\s+","");
                input = input.toUpperCase();
            }

            /* If you're a freshman, adjust the scale */
            freshmanHandler(sc);

			/* Bermuda Triangle warning */
			bermudaHandler();

			/* Final score determined */
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

			/* Determine if repeat is desired */
            String repeat = getYN("Would you like to test another schedule?", sc);
            if (repeat.equals("N")) {
                sc.close();
                System.exit(0);
            }
        }
    }


    public static String getCourses(String urlToRead) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            return result.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /************************************************************
     * @name: courseListInitialize                              *
     * @description: Prepare the list of course and their       *
     * weights for the rest of the script to utilize.           *
     * @notes: Update as needed!!!                              *
     * Courses for which a weight has not been determined have  *
     * been assigned a weight of -1.                            *
     ***********************************************************/

    public static HashMap<String,Double> courseListInitialize() {
        HashMap<String,Double> courses = new HashMap<>();
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
        courses.put("EE341",   5.5);
        courses.put("EE369",   3.0);
        courses.put("EE368L",  4.0);
        courses.put("EE379K0", 2.0); //Smart Grids.
        courses.put("EE362K", 3.0);
        courses.put("EE339S", 5.0);
        courses.put("EE362R", 1.0);

        return courses;
    }

    /************************************************************
     * @name: bermudaInitialize                                 *
     * @description: Prepare the list of starred and bermuda    *
     * courses to use for logic in main.                        *
     * @notes: Update as needed!!!                              *
     ***********************************************************/
    protected static HashMap<String, Boolean> bermudaInitialize() {
        HashMap<String, Boolean> bermudaCourses = new HashMap<>();

        bermudaCourses.put("embsys", false);
        bermudaCourses.put("comparch", false);
        bermudaCourses.put("os", false);
        bermudaCourses.put("vlsi", false);
        bermudaCourses.put("prob", false);

        return bermudaCourses;
    }

    /************************************************************
     * @name: bermudaChecker                                    *
     * @description: Updates the hashmap of bermuda courses     *
     * should the user have taken any of these courses.         *
     * @notes: Update as needed!!!                              *
     ***********************************************************/
    protected static void bermudaChecker(String course) {
        switch (course) {
            case "EE445L":
                bermuda.put("embsys", true);
                break;
            case "EE460N":
                bermuda.put("comparch", true);
                break;
            case "EE460R":
                bermuda.put("vlsi", true);
                break;
            case "EE461S":
                bermuda.put("os", true);
                break;
            case "EE351K":
                bermuda.put("prob", true);
                break;
        }
    }

    /************************************************************
     * @name: bermudaHandler                                    *
     * @description: Warns user if they took multiple bermudas  *
     * or probability (EE351K).                                 *
     * @notes: Update as needed!!!                              *
     ***********************************************************/
    protected static void bermudaHandler() {
        int bermudaCount = 0;
        if (bermuda.get("embsys")) bermudaCount++;
        if (bermuda.get("comparch")) bermudaCount++;
        if (bermuda.get("os")) bermudaCount++;
        if (bermudaCount > 1 || bermuda.get("prob") || bermuda.get("vlsi")) {
            System.out.println();
            System.out.println("The Bermuda Triangle of EE:");
            System.out.println("EE460N");
            System.out.println("EE445L");
            System.out.println("EE461S");
            System.out.println("Only take ONE of these at a time.");
            if (bermuda.get("prob"))
                System.out.println("EE351K is manageable with ONE of the Bermuda Triangle.");
            if (bermuda.get("vlsi"))
                System.out.println("EE460R can be considered equivalent to any of the Bermuda Triangle.");
        }
    }

    /************************************************************
     * @name: profException                                     *
     * @description: Returns the delta weight for a course,     *
     * should a specific professor for a given course affect    *
     * that.                                                    *
     * @notes: Update as needed!!!                              *
     ***********************************************************/
    protected static double profException(Scanner sc, String course) {
        String confirmation;
        switch(course) {
            case "EE422C":
                confirmation = getYN("Is Dr. Perry teaching EE422C?", sc);
                if (confirmation.equals("Y")) {
                    return -1.5;
                }
                break;
            case "EE411":
                confirmation = getYN("Is Dr. Swartzlander teaching EE411?", sc);
                if (confirmation.equals("Y")) {
                    return -2;
                }
                break;
            case "EE312":
                confirmation = getYN("Is Dr. Chase teaching EE312?", sc);
                if (confirmation.equals("Y")) {
                    return 1;
                }
                break;
            case "EE306":
                confirmation = getYN("Is Dr. Patt teaching EE306?", sc);
                if (confirmation.equals("Y")) {
                    return 1;
                }
                break;
            case "EE333T":
                confirmation = getYN("Is EE333T abroad?", sc);
                if (confirmation.equals("Y")) {
                    return -2.5;
                }
                break;
            default: //For the sake of completeness
                break;
        }
        return 0; //No weight change, if none of the above is confirmed
    }

    /************************************************************
     * @name: priorExperience                                   *
     * @description: Returns the weight delta needed, according *
     * to their high school experience.                         *
     * @notes: Update as needed!!!                              *
     ***********************************************************/
    protected static double priorExperience(Scanner sc, String course) {
        String confirmation;
        switch(course) {
            case "EE302":
                confirmation = getYN("Did you do circuits in high school?", sc);
                if (confirmation.equals("N")) {
                    return 1;
                }
                break;
            case "EE306":
                confirmation = getYN("Have you ever programmed?", sc);
                if (confirmation.equals("N")) {
                    return 1;
                }
                break;
            default: //For the sake of completeness
                break;
        }
        return 0; //No weight change, if none of the above is confirmed
    }

    /************************************************************
     * @name: freshmanHandler                                   *
     * @params: sc                                              *
     * @description: Return the delta weight, should the user   *
     * be a freshman.                                           *
     ***********************************************************/
    protected static void freshmanHandler(Scanner sc) {
        String fresh = getYN("Are you a freshman?", sc);
        if (fresh.equals("Y")) {
            riskyLow = 10;
            nrLow = 14;
            ded = 18;
        }
    }

    /************************************************************
     * @name: handlerFor379K                                    *
     * @params: sc                                              *
     * @description: If it's a 379K course, then use this       *
     * "special" logic.                                         *
     ***********************************************************/
    protected static double handlerFor379K(Scanner sc) {
        /* First need to know which 379K */
        System.out.println("Please specify:");
        System.out.println("0: Smart Grids");
        System.out.println("1: High Throughput Nanopatterning");
        System.out.println("2: Information Security and Privacy");
        System.out.println("3: Data Science Laboratory");
        System.out.print(">");
        int input = 0; 
        boolean validInput = false;
        String numberInput = "";
        while (!validInput) {
            try {
                numberInput = sc.nextLine();
                input = Integer.parseInt(numberInput);
                validInput = false;
                if (input > 0) 
                    validInput = true;
            } catch (Exception e) {
                validInput = false;
            }

            if (!validInput) {
                System.out.println("Only positive integers allowed as input here. Please try again");
                System.out.print(">");
            }

        }
        /* If it's a choice that exists, return that value */
        switch(input ) {
            case 0:
                return allCourses.get("EE379K0");
            default:
                break;
        }

        /* Else prompt user to enter a score */
        System.out.println("That version of EE379K is not in our database.");
        System.out.println("Please ask an upperclassman to rank it and enter the score here:");
        System.out.print(">");
        double temp =legalInput(sc);
        System.out.println("\nPlease add your course to initialize() as an incrementing 'EE379Kx' (x is currently 1) and submit a pull request after this run.");
        return temp;
    }

    private static double legalInput(Scanner sc){
        boolean validInput = false;
        double temp =0;
        while (!validInput) {
            try {
                temp = Double.parseDouble(sc.nextLine());
                if ( temp > 0.0 )
                    validInput = true;

            } catch (Exception e) {
                validInput = false;
            }
            if (!validInput) {
                System.out.println("Only positive numbers allowed as input here. Please try again");
                System.out.print(">");
            }
        }
        return temp;
    }

    /************************************************************
     * @name: getYN                                             *
     * @params: prompt, sc                                      *
     * @description: Asks for confirmation from user for given  *
     * prompt.                                                  *
     ***********************************************************/
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

    /************************************************************
     * @name: addWeight                                         *
     * @params: prompt, sc                                      *
     * @description: Asks user to enter info if course has no   *
     * assigned weight or if course is not in list.             *
     ***********************************************************/
    private static double addWeight(Scanner sc, String course, boolean defined) {
        if (defined) { //If course is in list but does not have assigned weight
            System.out.println("We do not have data for " + course + ".");
            System.out.println("Please ask an upperclassman to rank it and enter the score here:");
            System.out.print(">");
            return legalInput(sc);
        }
        else {
            System.out.println(course + " is not in our database.");
            System.out.println("Please ask an upperclassman to rank it and enter the score here:");
            System.out.print(">");
            return legalInput(sc);
        }
    }
}

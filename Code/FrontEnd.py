import sys
from Code import ScheduleRater

    #############################################################
     # @name: main                                              #
     # @description: All necessary logic for calculating        #
     # schedule workload.                                       #
     #############################################################
rater = ScheduleRater.ScheduleRater()

while True:
    print("\nWelcome to UTECECourses.")
    print("Please input your courses in the following format:")
    print("[Department(EE,M,PHY)][Course number + optional letter]")
    print("Example: EE445L")
    print("For senior design, drop the letter.")
    print("Please input your courses. Enter D when finished.\n");
    givenCourse = input(">")

    # Setup for the meat of the code #
    givenCourse = givenCourse.upper()
    givenCourse = givenCourse.replace("\\s+", "")
    totalDiff = 0
    tempDiff = 0

    while not(givenCourse == "D"):
        if (givenCourse in rater.allCourses.keys()):
            tempDiff = rater.allCourses.get(givenCourse)
            if (tempDiff == -1): #Should the class have no assigned weight
                tempDiff += rater.addWeight(givenCourse, True)
                print("\nPlease update " + givenCourse + "'s entry in initialize() and submit a pull request after this run.")
            else: #Adjust the weight as needed and account for special circumstances
                rater.bermudaChecker(givenCourse) #Check if a bermuda course was entered
                tempDiff += rater.profException(givenCourse) #Adjust the course depending on the professor taken
                tempDiff += rater.priorExperience(givenCourse) #Account for high school experience
        else: #If we are dealing with an EE379K course
            if (givenCourse == "EE379K"):
                tempDiff += rater.handlerFor379K()
            else:
                tempDiff += rater.addWeight(givenCourse, False)
                print("\nPlease add your course to initialize() and submit a pull request after this run.")

        # Update the weights #
        totalDiff += tempDiff

        # Ask for the next input #
        givenCourse = input("\nNext >")
        givenCourse = givenCourse.upper()
        givenCourse = givenCourse.replace("\\s+", "")

    # Adjust the scale if freshman #
    rater.freshmanHandler()

    # Bermuda Triangle Warning #
    rater.bermudaHandler()

    # Final score #
    print("Your score is " + str(totalDiff))

    if (totalDiff > rater.ded):
        print("ABORT ABORT ABORT")
    elif (totalDiff > rater.nrLow):
        print("Would not recommend.")
    elif (totalDiff > rater.riskyLow):
        print("Your schedule is a bit risky.")
    else:
        print("U gud fam.")

    # Check if repeat is desired #
    repeat = rater.getYN("Would you like to test another schedule?")
    if (repeat == "N"):
        sys.exit(0)

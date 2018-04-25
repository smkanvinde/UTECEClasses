class ScheduleRater:

    allCourses = dict()
    bermuda = dict()

    # Values for the tiers #
    riskyLow = 12
    nrLow = 16
    ded = 20

    ##############################################################
     # @name: __init__                                           #
     # @description: Constructor.                                #
     ##############################################################
    def __init__(self):
        # Course lists #
        self.allCourses = self.courseListInit()
        self.bermuda = self.bermudaInit()

   #############################################################
    # @name: courseListInit                                    #
    # @description: Prepare the list of courses and their      #
    # weights for the rest of the script to utilize.           #
    # @notes: Update as needed!!!                              #
    # Courses for which a weight has not been determined have  #
    # been assigned a weight of -1.                            #
    #############################################################
    def courseListInit(self):
        courses = dict()
        courses["EE445L"] = 7.5
        courses["EE460N"] = 6.0
        courses["EE445M"] = 5.0
        courses["EE461P"] = 3.5
        courses["EE461L"] = 2.5
        courses["EE422C"] = 2.5
        courses["EE464"] = 2.5
        courses["EE445S"] = -1.0
        courses["EE460M"] = 4.0
        courses["EE460J"] = 4.5
        courses["EE438"] = 3.5
        courses["EE440"] = 3.5
        courses["EE460R"] = 7.0
        courses["EE362K"] = 2.5
        courses["EE312"] = 3.0
        courses["EE313"] = 3.0
        courses["EE411"] = 3.0
        courses["EE338L"] = -1.0
        courses["EE302"] = 2.0
        courses["EE306"] = 2.0
        courses["EE462L"] = 4.5
        courses["EE471C"] = -1.0
        courses["EE374K"] = 3.5
        courses["EE371R"] = -1.0
        courses["EE361Q"] = 1.5
        courses["EE360C"] = 4.0
        courses["EE360P"] = 3.0
        courses["EE361C"] = 3.5
        courses["EE362S"] = -1.0
        courses["EE363N"] = -1.0
        courses["EE362Q"] = -1.0
        courses["EE339"] = 4.0
        courses["EE325"] = 4.5
        courses["EE351K"] = 5.5
        courses["EE333T"] = 3.5
        courses["EE325K"] = 3.5
        courses["EE316"] = 4.0
        courses["EE360T"] = 3.0
        courses["EE461S"] = 6.0
        courses["EE364"] = 3.0
        courses["EE319K"] = 4.0
        courses["M408C"] = 1.5
        courses["M408D"] = 2.0
        courses["M408K"] = 1.5
        courses["M408L"] = 1.5
        courses["M408M"] = 2.0
        courses["M427J"] = 2.5
        courses["M427L"] = 3.0
        courses["M340L"] = 2.5
        courses["M325K"] = 2.5
        courses["M328K"] = 3.0
        courses["PHY303K"] = 1.0
        courses["PHY103M"] = 2.0
        courses["PHY303L"] = 2.0
        courses["PHY103N"] = 1.0
        courses["EE341"] = 5.5
        courses["EE369"] = 3.0
        courses["EE368L"] = 4.0
        courses["EE379K0"] = 2.0 #Smart grids
        courses["EE362K"] = 3.0
        courses["EE339S"] = 5.0
        courses["EE362R"] = 1.0

        return courses

    #############################################################
     # @name: bermudaInit                                       #
     # @description: Prepare the list of starred and bermuda    #
     # courses to use for logic in main.                        #
     # @notes: Update as needed!!!                              #
     #############################################################
    def bermudaInit(self):
        bermudaCourses = dict()

        bermudaCourses["embsys"] = False
        bermudaCourses["comparch"] = False
        bermudaCourses["os"] = False
        bermudaCourses["vlsi"] = False
        bermudaCourses["prob"] = False

        return bermudaCourses

    #############################################################
     # @name: bermudaChecker                                    #
     # @params: course                                          #
     # @description: Updates the dict of bermuda courses should #
     # the user have taken any of these courses.                #
     # @notes: Update as needed!!!                              #
     #############################################################
    def bermudaChecker(self, course):
        if course == "EE445L":
            self.bermuda["embsys"] = True
        elif course == "EE461S":
            self.bermuda["os"] = True
        elif course == "EE460N":
            self.bermuda["comparch"] = True
        elif course == "EE460R":
            self.bermuda["vlsi"] = True
        elif course == "EE351K":
            self.bermuda["prob"] = True

    #############################################################
     # @name: bermudaHandler                                    #
     # @description: Warns user if they took multiple bermudas  #
     # or probability.                                          #
     # @notes: Update as needed!!!                              #
     #############################################################
    def bermudaHandler(self):
        bermudaCount = 0
        if (self.bermuda["embsys"] == True):
            bermudaCount += 1
        if (self.bermuda["os"] == True):
            bermudaCount += 1
        if (self.bermuda["comparch"] == True):
            bermudaCount += 1
        if (bermudaCount > 1 or (self.bermuda["vlsi"]) or (self.bermuda["prob"])):
            print("\nThe Bermuda Triangle of EE:")
            print("EE460N")
            print("EE461S")
            print("EE445L")
            print("Only take ONE of these at a time.")
            if (self.bermuda["vlsi"]):
                print("EE351K is manageable with ONE of the Bermuda Triangle.")
            if (self.bermuda["prob"]):
                print("EE460R can be considered equivalent to any of the Bermuda Triangle.")

    #############################################################
     # @name: profException                                     #
     # @params: course                                          #
     # @description: Returns the delta weight for a course      #
     # should taking a certain professor for a class affect     #
     # that.                                                    #
     # @notes: Update as needed!!!                              #
     #############################################################
    def profException(self, course):
        if (course == "EE422C"):
            confirmation = self.getYN("Is Dr. Perry teaching EE422C?")
            if (confirmation == "Y"):
                return -1.5

        elif (course == "EE411"):
            confirmation = self.getYN("Is Dr. Swartzlander teaching EE411?")
            if (confirmation == "Y"):
                return -2

        elif (course == "EE312"):
            confirmation = self.getYN("Is Dr. Chase teaching EE312?")
            if (confirmation == "Y"):
                return 1

        elif (course == "EE306"):
            confirmation = self.getYN("Is Dr. Patt teaching EE306?")
            if (confirmation == "Y"):
                return 1

        elif (course == "EE333T"):
            confirmation = self.getYN("Is EE333T abroad?")
            if (confirmation == "Y"):
                return -2.5

        return 0

    #############################################################
     # @name: priorExperience                                   #
     # @params: course                                          #
     # @description: Returns the delta weight according to the  #
     # user's high school experience.                           #
     # @notes: Update as needed!!!                              #
     #############################################################
    def priorExperience(self, course):
        if (course == "EE302"):
            confirmation = self.getYN("Did you do circuits in high school?")
            if (confirmation == "N"):
                return 1

        elif (course == "EE306"):
            confirmation = self.getYN("Have you ever programmed?")
            if (confirmation == "N"):
                return 1

        return 0

    #############################################################
     # @name: freshmanHandler                                   #
     # @description: Adjust the cutoffs should the user be a    #
     # freshman.                                                #
     # @notes: Update as needed!!!                              #
     #############################################################
    def freshmanHandler(self):
        fresh = self.getYN("Are you a freshman?")
        if (fresh == "Y"):
            self.riskyLow = 10
            self.nrLow = 14
            self.ded = 18

    #############################################################
     # @name: handlerFor379K                                    #
     # @description: Return the weight according to the         #
     # specific 379K course that is being entered.              #
     # @notes: Update as needed!!!                              #
     #############################################################
    def handlerFor379K(self):
        print("Please specify:")
        print("0: Smart Grids")
        print("1: High Throughput Nanopatterning")
        print("2: Information Security and Privacy")
        print("3: Data Science Laboratory")
        choice = self.numCheck()

        if (choice == float(0)): #Should the 379K course be accounted for
            return self.allCourses["EE379K0"]

        # Else ask user to add it #
        print("That version of EE379K is not in our database.")
        print("Please ask an upperclassman to rank it and enter the score here:")
        temp = self.numCheck()

        print("\nPlease add your course to courseListInit() as an incrementing 'EE379Kx' (x is currently 1) and submit a pull request after this run.")
        return temp

    #############################################################
     # @name: getYN                                             #
     # @params: prompt                                          #
     # @description: Asks for confirmation from user for given  #
     # prompt.                                                  #
     #############################################################
    def getYN(self, prompt):
        first = True #If first entry
        entry = ""

        while not (entry == "Y" or entry == "N"):
            if (not first): #Only ask for correct input if iteration #2 or later
                print("Y/N, dumbass.")

            print("\n" + prompt + " Y/N")
            entry = input(">")

            entry = entry.upper()
            entry.replace("\\s+", "")

            first = False #Set to false so that if Y or N was not entered, error message thrown

        return entry

    #############################################################
     # @name: addWeight                                         #
     # @params: course, defined                                 #
     # @description: Asks user to enter info if course has no   #
     # assigned weight or the course is not accounted for in    #
     # the allCourses dict.                                     #
     # @notes: Update as needed!!!                              #
     #############################################################
    def addWeight(self, course, defined):
        if (defined): #Course is in the dict, but no assigned weight
            print("We do not have data for " + course + ".")
            print("Please ask an upperclassman to rank it and enter the score here:")
            return self.numCheck()
        else: #Course is not even considered
            print(course + " is not in our database.")
            print("Please ask an upperclassman to rank it and enter the score here:")
            return self.numCheck()

    #############################################################
     # @name: errCheck                                          #
     # @params: methodName, userInput                           #
     # @description: Check if the input converts to a valid     #
     # value and return a boolean accordingly.                  #
     # @notes: Update as needed!!!                              #
     #############################################################
    def errCheck(self, methodName, userInput):
        if (methodName == "addWeight"):
            try:
                float(userInput)
                return True
            except ValueError:
                return False
        elif (methodName == "handlerFor379K"):
            try:
                float(userInput)
                return True
            except ValueError:
                return False

    #############################################################
     # @name: numCheck                                          #
     # @description: Simply takes care of the error checking    #
     # logic if a numerical input is desired.                   #
     # @notes: Update as needed!!!                              #
     #############################################################
    def numCheck(self):
        userIn = input(">")

        while not self.errCheck("addWeight", userIn):
            print("We want a number, genius.")
            userIn = input(">")

        return float(userIn)
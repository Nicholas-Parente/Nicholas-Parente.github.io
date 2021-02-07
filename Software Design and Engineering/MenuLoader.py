################################################################################################
"""MenuLoader displays the menu items for the user, and depending on their 
    selections, the value corresponding with their menu choice is returned
    to the main definition call"""
def MenuLoader(menuChoice):

    #Initializes the variable to hold the menu options for the user choices
    appMenuOptions = {}

    #Initializes the boolean for the while loop for incorrect menu selection
    looper = True

    #While loop for incorrect menu selection
    while looper:

        #Loads the menu options into the appMenuOptions variable
        #True loads the main menu and False loads the secondary menu
        if menuChoice:
            appMenuOptions['1'] = ": Print Patient List"
            appMenuOptions['2'] = ": Print Doctor List"
            appMenuOptions['3'] = ": Add a Doctor"
            appMenuOptions['4'] = ": Add a Patient"
            appMenuOptions['5'] = ": Medical Records"
            appMenuOptions['6'] = ": Search for Allergies"
            appMenuOptions['0'] = ": Exit"

        else:
            appMenuOptions['1'] = ": Add a Treatment"
            appMenuOptions['2'] = ": Add a Medication"
            appMenuOptions['3'] = ": Print Patient's Treatments"
            appMenuOptions['4'] = ": Print Patient's Medications"
            appMenuOptions['5'] = ": Add Allergy"
            appMenuOptions['6'] = ": Print Allergies"
            appMenuOptions['0'] = ": Exit"

        #Sets the expected user responses for the menu options 
        optionNumbers = appMenuOptions.keys()

        #Prints the options to the user 
        for options in optionNumbers:
            print(options, appMenuOptions[options])

        #Takes the user input for their menu choice
        userInput = input("\nPlease select an option from the list above: ")

        #Determines if the user made a valid choice
        #Returns the choice made to the main definition
        for options in optionNumbers:
            if userInput == options:
                return userInput

        #If the user incorrectly chooses, prints error message and loops menu choices again
        print("\nChoice is incorrect. Using 0-6, please select the corresponding menu choice.\n")


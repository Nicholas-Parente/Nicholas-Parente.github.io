#Imports the MenuLoader.py module for the medical record menu
from MenuLoader import MenuLoader

################################################################################################
"""MedicalRecordMenu displays the menu items for the user, and depending on their 
    selections, the value corresponding with their menu choice is returned
    to the main definition call"""
def MedicalRecordMenu(patients, Id):
    #While loop to reload the menu
    x = 0
    while(True):

        #Loads the secondary menu for the medical record
        menuChoice = MenuLoader(False)

        #If the user wants to add a treatment to the patient
        if menuChoice == "1":

            if x == 0:
                patients = AddToPatient(patients, Id, "treatment")
                x = 1
            else:
                print("\nThere is a treatment added that does not have a medication yet.",
                      "\nPlease add a medication before adding another treatment\n.")

        #If the user wants to add a medication to the patient
        elif menuChoice == '2':

            if x == 1:
                patients = AddToPatient(patients, Id, "medication")
                x = 0
            else:
                print("\nThere needs to be a treatment added prior to adding a medication.",
                      "\nPlease add a treatment before adding a medication.\n")


        #If the user wants to print the patient's treatments
        elif menuChoice == '3':

            print(patients[Id]["treatment"],"\n")

        #If the user wants to print the patient's medications
        elif menuChoice == '4':

            print(patients[Id]["medication"],"\n")

        #If the user wants to add an allergy to the patient
        elif menuChoice == '5':

            patients = AddToPatient(patients, Id, "allergy")

        #If the user wants to print the patient's allergies
        elif menuChoice == '6':

            print(patients[Id]["allergy"],"\n")

        else:

            #Prints a goodbye message to the user and exits
            print("Returning to main menu.\n")
            return



def AddToPatient(patients, Id, dataType):

    #the boolean for the main while loop
    dataLooper = True

    while(dataLooper):
        #Initializes the booleans for the nested while loops
        savingLooper = True

        #Takes the user input for the description of the data type
        #Does not need error handling from system, only error handling from the user
        #See next loop for user error correction
        print("\nPlease enter the patient's", dataType, ":")
        name = input()

        #Loop to add a data type to the patient dict for a specific patient
        #User gets a final choice to verify their input 
        while(savingLooper):

            print('\n', "Patient", patients[Id]['name'], "with I.D.", Id, 
                             "will have", dataType, name, "added to their file. Is this correct?\n", "1 for Yes, 2 for No.")
            choice = input()

            #If the user verifies that their input was correct
            if choice == '1':

                #Adds the data type to the patient dict for the specific patient
                patients[Id][dataType].append(name)

                print("\nThe",dataType,"was successfully added to patient.\n")

                #Sets savingLooper to false to break out of loop
                savingLooper = False
                dataLooper = False

            #If the user wants to re-enter their inputs 
            elif choice == '2':

                #Sets this loop and the next loop to false to reset to main loop
                savingLooper = False

            #If the user input was incorrect
            else:

                print("Invalid input. Please enter either 1 for Yes or 2 for No.")

    return patients
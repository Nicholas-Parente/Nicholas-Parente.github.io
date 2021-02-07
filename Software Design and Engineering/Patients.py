class Patients:
    ################################################################################################
    #Returns the patient data to the application
    def GetPatients(self):
        return self._patients

    ################################################################################################
    #Sets the patient data 
    def SetPatients(self, patients):
        self._patients = patients

    ################################################################################################
    #The definition call that imports preloaded patient data from text files
    def ImportPatients():

        #Initializes the dicts used within the function to retrieve the patient data
        patients = {}
        Treatments = {}
        Medications = {}
        Allergies = {}

        #NOTE: All data has been stored in order and uses delimiters to segregate depending on user ID

        #Opens the patient ID text file and retrieves the patient ID's 
        myfile = open('data/patientsId.txt','r')
        for line in myfile:
            Id=line.split(',')
        myfile.close()

        #Opens the patient name text file and retrieves the patients name
        myfile = open('data/patientsName.txt','r')
        for line in myfile:
            name=line.split(',')
        myfile.close()

        #Opens the patient treatment text file and retrieves the patients treatments
        myfile = open('data/patientsTreatments.txt','r')
        for line in myfile:
            treatments=line.split('/')
        myfile.close()

        #Opens the patient medications text file and retrieves the patients treatments
        myfile = open('data/patientsMedications.txt','r')
        for line in myfile:
            medications=line.split('/')
        myfile.close()

        #Opens the patient allergies text file and retrieves the patients treatments
        myfile = open('data/patientsAllergies.txt','r')
        for line in myfile:
            allergies=line.split('/')
        myfile.close()

        #Pops empty data caused by delimiter off the end of the list
        Id.pop()
        name.pop()
        treatments.pop()
        medications.pop()
        allergies.pop()

        #Runs the definition call to retrieve the data from the text files
        #Returns temporary dicts to use to populate each individual patient
        #Passes the delimited text file data, the string for what data is needed, and the patient ID's
        treatments = Patients.ReceiveData(treatments, "treatments", Id)
        medications = Patients.ReceiveData(medications, "medications", Id)
        allergies = Patients.ReceiveData(allergies, "allergies", Id)

        #'x' Is a temporary variable used to increment through the names in the text file
        x = 0
        #For each patient ID, populate a patient with their ID, their name, their treatments, their medications, and allergies
        for i in Id:
            patients[int(i)] = {"Id": int(i), 
                                "name": name[x], 
                                "treatment": treatments[int(i)]["treatments"], 
                                "medication": medications[int(i)]["medications"], 
                                "allergy": allergies[int(i)]["allergies"]}   

            #Increment the name counter
            x += 1

        #Return the patients to the main application definition call
        return patients

    ################################################################################################
    #The definition call to retrieve the data from the text files for the ImportPatients definition call
    def ReceiveData(data, dataType, Id):

        #Initializes the temporary variables to store data for the dict's
        tempDataDict = {}
        tempId = []
        tempId2 = []

        #Initializes the counter for iterating through the I.D.'s of the patients
        y = 0

        #For loop for taking the raw data passed to the definition and splitting it so that it can be assigned to the data type
        for i in data:

            #Splits the data using the delimiter
            temp = i.split(',')

            #Pops the leftover delimiter at the end of the data
            temp.pop()

            #For loop for further splitting the data and adding a delimiter at the end to distinguish patient separation 
            for j in temp:
                tempId.append(j)
            tempId.append("/")

        #For loop for creating the dict using the patient I.D.'s from the text file and the data type passed to the definition call
        for i in Id:

            #Creates the dict with the needed information for the next loop
            tempDataDict[int(i)] = {dataType: []}

            #Appends the I.D. to a temporary list for use in the next for loop
            tempId2.append(i)

        #For loop for loading the data into the Dict for the patients
        for i in tempId:

            #If the delimiter is reached
            if i == '/':

                #Increment to the next I.D. 
                y+= 1

            #If the delimiter has not been reached
            else:
                #Load the data into the dict
                tempDataDict[int(tempId2[y])][dataType].append(i)

        #Return the dict to the original definition that called this one
        return tempDataDict

    ################################################################################################
    #The definition call that adds a patient to the dict of patients
    def AddPatients(patients):

        #Initializes looper for the parent while loop
        patientLooper = True

        #The main loop for the AddPatients definition call
        while(patientLooper):

            #Initializes the loops for the nested while loops
            #Initialized inside while loop so loops function properly
            idLooper = True
            errorLooper = True
            savingLooper = True
            reLooper = True

            #Loop to recieve the I.D. from the user 
            while(idLooper):
                #Tries to recieve the I.D. from the user 
                #Id needs to be an int, try-except for error handling of incorrect user input
                while(errorLooper):
                    try:

                        idFetch = input("Please enter the patients's I.D.:")

                        Id = int(idFetch)

                        errorLooper = False

                    except ValueError as e:
                        print("\nPlease enter a numerical value for the I.D.")

                #If the I.D. already exists within the patient list, user must select another
                if Id in patients.keys():
                    print("\nI.D. Already Exists. Please select another.")
                    errorLooper = True

                #sets idLooper to false to break loop
                else:
                    idLooper = False

            #Takes the user input for the name
            #Name does not need error handling from system, only error handling from the user
            #See next loop for user error correction
            name = input("\nPlease enter the patients's name:")

            #Loop to add a patient to the patient dict
            #User gets a final choice to verify their input 
            while(savingLooper):

                print('\n', "Patient", name, "with I.D.", Id, 
                                 "will be added to system. Is this correct?\n", "1 for Yes, 2 for No.")
                choice = input()

                #If the user verifies that their input was correct
                if choice == '1':

                    #Adds the patient to the patient dict
                    patients[Id] = {"Id": Id, "name": name, "treatment": [], "medication": [], "allergy": []}

                    print("\nPatient successfully added to system.\n")

                    #Sets savingLooper to false to break out of loop
                    savingLooper = False

                #If the user wants to re-enter their inputs 
                elif choice == '2':

                    #Sets this loop and the next loop to false to reset to main loop
                    savingLooper = False
                    reLooper = False

                #If the user input was incorrect
                else:

                    print("Invalid input. Please enter either 1 for Yes or 2 for No.")

            #The while loop to have the user enter another patient if they wish to add more then 1 
            while(reLooper):

                print("Would you like to add another patient? 1 for Yes, 2 for No.")
                choice = input()

                #If the user wants to enter an additional patient
                if choice == '1':

                    reLooper = False

                #If the user wants to return to the main menu
                elif choice == '2':

                    print("\nReturning to main menu.\n")

                    #Sets the main looper false so that the function ends
                    reLooper = False
                    patientLooper = False

                #If the user input was incorrect
                else:

                    print("\nInvalid input. Please enter either 1 for Yes or 2 for No.\n")

        #Returns the dict to the main menu to be set 
        return patients

    ################################################################################################
    #The definition for storing the patient data in the appropriate text files to store them
    def StorePatients(patients):

        #Stores the patient I.D.'s to the appropriate text file
        myfile = open('data/patientsId.txt', 'w')

        #Iterates through the I.D.'s and stores them with a delimiter
        for i in patients:
            string = str(i) + ","
            myfile.write(string)

        #Closes the file stream
        myfile.close()

        #Stores the patient names to the appropriate text file
        myfile = open('data/patientsName.txt', 'w')

        #Iterates through the patient names and stores them with a delimiter
        for i in patients: 
            string = patients[i]['name'] + ","
            myfile.write(string)

        #Closes the file stream
        myfile.close()

        #Stores the patient treatments to the appropriate text file
        myfile = open('data/patientsTreatments.txt', 'w')

        #Iterates through the patient treatments and stores them with a delimiter
        for i in patients: 
            for j in range(len(patients[i]['treatment'])):
                string = patients[i]['treatment'][j] + ","
                myfile.write(string)
            #Adds a second delimiter to distinguish between which I.D. gets which treatments
            myfile.write("/")

        #Closes the file stream
        myfile.close()

        #Stores the patient medications to the appropriate text file
        myfile = open('data/patientsMedications.txt', 'w')

        #Iterates through the patient medications and stores them with a delimiter
        for i in patients: 
            for j in range(len(patients[i]['medication'])):
                string = patients[i]['medication'][j] + ","
                myfile.write(string)

            #Adds a second delimiter to distinguish between which I.D. gets which medications
            myfile.write("/")

        #Closes the file stream
        myfile.close()

        #Stores the patient allergies to the appropriate text file
        myfile = open('data/patientsAllergies.txt', 'w')

        #Iterates through the patient allergies and stores them with a delimiter
        for i in patients: 
            for j in range(len(patients[i]['allergy'])):
                string = patients[i]['allergy'][j] + ","
                myfile.write(string)

            #Adds a second delimiter to distinguish between which I.D. gets which medications
            myfile.write("/")

        #Closes the file stream
        myfile.close()

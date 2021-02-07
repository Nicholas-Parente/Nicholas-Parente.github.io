class Doctors:
    ################################################################################################
    #Returns the doctor data to the application
    def GetDoctors(self):
        return self._doctors

    ################################################################################################
    #Sets the doctor data
    def SetDoctors(self,doctors):
        self._doctors = doctors

    ################################################################################################
    #The definition call that imports the doctor data from text files
    def ImportDoctors():

        #Initializes the dict used within the function to retrieve the doctor data
        doctors = {}

        #NOTE: All data has been stored in order and uses delimiters to segregate depending on ID

        #Opens the doctor ID text file and retrieves the doctor ID's
        myfile = open('data/doctorsId.txt','r')
        for line in myfile:
            Id=line.split(',')
        myfile.close()

        #Opens the doctor Name text file and retrieves the doctor names 
        myfile = open('data/doctorsName.txt','r')
        for line in myfile:
            name=line.split(',')
        myfile.close

        #Pops empty data caused by delimiter off the end of the lists
        Id.pop()
        name.pop()

        #Temporary variable used to increment through the names in the text file
        j = 0
        #For each doctor ID, populate a doctor with their name and ID
        for i in Id:
            doctors[int(i)] = {"Id": int(i), "name": name[j] }
            j += 1

        #return the doctors to the main application definition call
        return doctors

    ################################################################################################
    #The definition call that adds a doctor to the dict of doctors
    def AddDoctors(doctors):

        #Initializes looper for the parent while loop
        doctorLooper = True

        #The main loop for the AddDoctors definition call
        while(doctorLooper):

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

                        idFetch = input("Please enter the doctors's I.D.:")

                        Id = int(idFetch)

                        errorLooper = False

                    except ValueError as e:
                        print("\nPlease enter a numerical value for the I.D.")

                #If the I.D. already exists within the doctor list, user must select another
                if Id in doctors.keys():
                    print("\nI.D. Already Exists. Please select another.")
                    errorLooper = True

                #sets idLooper to false to break loop
                else:
                    idLooper = False

            #Takes the user input for the name
            #Name does not need error handling from system, only error handling from the user
            #See next loop for user error correction
            name = input("\nPlease enter the doctor's name:")

            #Loop to add a doctor to the doctor dict
            #User gets a final choice to verify their input 
            while(savingLooper):

                print('\n', "Doctor", name, "with I.D.", Id, 
                                 "will be added to system. Is this correct?\n", "1 for Yes, 2 for No.")
                choice = input()

                #If the user verifies that their input was correct
                if choice == '1':

                    #Adds the doctor to the doctor dict
                    doctors[Id] = {"Id": Id, "name": name}

                    print("\nDoctor successfully added to system.\n")

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

            #The while loop to have the user enter another doctor if they wish to add more then 1 
            while(reLooper):

                print("Would you like to add another doctor? 1 for Yes, 2 for No.")
                choice = input()

                #If the user wants to enter an additional doctor
                if choice == '1':

                    reLooper = False

                #If the user wants to return to the main menu
                elif choice == '2':

                    print("\nReturning to main menu.\n")

                    #Sets the main looper false so that the function ends
                    reLooper = False
                    doctorLooper = False

                #If the user input was incorrect
                else:

                    print("\nInvalid input. Please enter either 1 for Yes or 2 for No.\n")

        #Returns the dict to the main menu to be set 
        return doctors

    ################################################################################################
    #The definition call that stores the data for the doctors into their appropriate text files
    def StoreDoctors(doctors):

        #Stores the doctor I.D.'s to the appropriate text file
        myfile = open('data/doctorsId.txt', 'w')
        for i in doctors:
            string = str(i) + ","
            myfile.write(string)

        #Closes the file stream
        myfile.close()

        #Stores the doctor names to the appropriate text file
        myfile = open('data/doctorsName.txt', 'w')
        for i in doctors: 
            string = doctors[i]['name'] + ","
            myfile.write(string)

        #Closes the file stream
        myfile.close()
#These imports are for the cryptographic mechanisms for the password 
#Imports derived from https://cryptography.io/en/latest/fernet.html
import base64
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.kdf.pbkdf2 import PBKDF2HMAC
################################################################################################
"""LoggingIn verifies that the user has access to the system by asking for the system password.
    The user password is then used to create a key, taking the salt value from data.txt. If the
    password is correct, the keys will match and the user will have access (returns true). If 
    3 unsuccessful attempts are made, then false will be returned and the application ends"""
def LoggingIn():

    #Initializes the number of login attempts for the user
    passwordAttempts = 3

    #Initializes the boolean for the while loop
    loginSuccess = False

    #While the user has incorrectly guessed the password
    while(passwordAttempts != 0 and loginSuccess == False):

        #Takes the user input for the password
        password = input("Please enter the system password: (TESTING PURPOSES: PASSWORD IS 'Open') ") 

        #Converts the password to bytes
        bPassword = password.encode()

        #Opens the file containing the salt value for the key creation
        file = open("data.txt", "rb")
        salt = file.read()
        file.close()

        #Using key derivation function (see imports above), creates a value to generate the key 
        #Derived from https://cryptography.io/en/latest/fernet.html
        kdf = PBKDF2HMAC(
            algorithm=hashes.SHA256(),
            length=32,
            salt=salt,
            iterations=100000,
        )

        #Generates a url safe base64 key using the key derivation function and password
        #Derived from https://cryptography.io/en/latest/fernet.html
        key = base64.urlsafe_b64encode(kdf.derive(bPassword))

        #The matchKey for the correct user password
        matchKey = b"tiGd4B9ITfQF3UPY3YFg2ZV0Oni4ICzWkhajzBa6we0="

        #If the key matches the matching key for the correct password, return true
        if(key == matchKey):
            loginSuccess = True
            return loginSuccess

        #Prints the error message to the user and loops again, decrementing the password attempts
        print("\nIncorrect Password.\n")
        passwordAttempts -= 1

    #If too many attempts, returns false
    return False
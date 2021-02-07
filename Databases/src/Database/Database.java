/*
    This .java file contains the database data that will be used for the 
    Database application in the Application.Application.java file. 
    
    The database used for this application is an embedded Derby database
    that uses the Java DB Driver libraries. An embedded database was used over
    a database that has to be connected for simplicity, as initializing the database
    would be a seperate action from running this application

    The datatable was created using the statement below:

    stmt.execute("create table ACC(ID INTEGER not null primary key," 
            + "AGE_IN_WEEKS DOUBLE"
            + ",ANIMAL_TYPE VARCHAR(100)"
            + ",BREED VARCHAR(100),"
            + "COLOR VARCHAR(100),"
            + "DATE_OF_BIRTH VARCHAR(100),"
            + "LOCATION_LAT DOUBLE,"
            + "LOCATION_LONG DOUBLE,"
            + "NAME VARCHAR(100),"
            + "OUTCOME_TYPE VARCHAR(100),"
            + "OUTCOME_SUBTYPE VARCHAR(100)"
            + ",SEX_UPON_ARRIVAL VARCHAR(100))");

    Then the datatable was populated using the .csv file from Data.Data with 
    the statement:

    stmt.execute("insert into " + "Animals.ACC" + " values (" +
            values[0] + "," + values[1] + ",'" + values[2] + "','" + values[3] + "','" +
            values[4] + "','" + values[5] + "'," + values[6] + "," + values[7] + ",'" +
            values[8] + "','" + values[9] + "','" + values[10] + "','" + values[11]+"')");

    Once populated, the methods were removed, as they would no longer be necessary
    as no new data will be added to the database.  
*/
package Database;

//Imports for handling the SQL statements for connecting and obtaining database data
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/*
    The class that contains the database data for the GUI application
*/
public class Database {
    
    /*
        The static final strings for the SQL statements for connecting to the 
        database was well as the statements to find the specific data for the   
        GUI application
    */
    private static final String DATABASE = "jdbc:derby:Animals;create=true;user=Administrator;password=password";
    
    private static final String RESET = "SELECT * FROM Animals.AAC";
    
    private static final String DISASTER = "SELECT * FROM Animals.AAC WHERE BREED IN "
                    + "('Doberman Pinscher', 'German Shepherd', 'Golden Retriever', 'Bloodhound', 'Rottweiler')" 
                    + "AND AGE_IN_WEEKS BETWEEN 20 AND 300" 
                    + "AND SEX_UPON_ARRIVAL = 'Intact Male'";
    
    private static final String MOUNTAIN = "SELECT * FROM Animals.AAC WHERE BREED IN " 
            + "('German Shepherd', 'Alaskan Malamute', 'Old English Sheepdog', 'Siberian Husky', 'Rottweiler')" 
            + "AND AGE_IN_WEEKS BETWEEN 26 AND 156" 
            + "AND SEX_UPON_ARRIVAL = 'Intact Male'";
    
    private static final String WATER = "SELECT * FROM Animals.AAC WHERE BREED IN "
            + "('Labrador Retriever Mix', 'Chesapeake Bay Retriever', 'Newfoundland')" 
            + "AND AGE_IN_WEEKS BETWEEN 26 AND 156" 
            + "AND SEX_UPON_ARRIVAL = 'Intact Female'";
    
    //The statements for initializing the connection and statements for the database 
    private static Connection connection = null;
    private static Statement statement = null;
    
    /*
        The method that gets the static string for the getData and getColumns methods
    */
    private static String getString(String data){
    
        switch (data) {
            case "RESET":
                return RESET;
            case "DISASTER":
                return DISASTER;
            case "MOUNTAIN":
                return MOUNTAIN;
            default:
                return WATER;
        }
    }

    /*
        The method to create the connection to the embedded database
    */
    private static void createConnection() {
        
        //Tries to connect to the embedded database
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            
            //Gets a connection to the database using the DATABASE string
            connection = DriverManager.getConnection(DATABASE);
        } 
        
        //catches an exception that can be thrown
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException except) {
        }
    }
    
    /*
        The method to get the column data for the columns of the jTable
    */
    public String[] getColumnData(String data){
        
        //Sets the variable string with the static final global string
        String variable;
        variable = getString(data);
        
        //The array to hold the column data that will be returned
        String[] cols = new String[12];
        
        //Creates the connection to the database
        createConnection();

        //Tries to connect and get the data 
        try {
            //creates a statement using the connection to the database
            statement = connection.createStatement();
            
            //initializes the result set object using the query
            //executes the query using the variable string
            ResultSet results = statement.executeQuery(variable);
            
            //initializes the result set metadata using the results object
            ResultSetMetaData metaData = results.getMetaData();
            
            //Initializes the numberOfColumns variable with the column count
            int numberOfColumns = metaData.getColumnCount();
            
            //the for loop for storing data into the cols array
            for (int i = 1; i <= numberOfColumns; i++) {
                
                //i-1 is used because metaData starts at 1
                cols[i - 1] = metaData.getColumnLabel(i);
            }
            
        } 
        
        //Catches the exception 
        catch (SQLException sqlExcept) {
        }
        
        //Shuts down the database connection
        shutdown();
        
        //returns the array 
        return cols;

    }
    
    /*
        The method to get the row data for the jTable
    */    
    public String[][] getRowData(String data)
    {
        //Sets the variable string with the static final global string
        String variable;
        variable = getString(data);
        
        //Initializes the variable to hold the row data
        String[][] dat = new String[100][12];
        
        //Creates the connection to the database
        createConnection();

        //Tries to connect to get the data
        try {
            //Creates a statement using the database connection
            statement = connection.createStatement();

            //Tries to get the results of the result set
            //This throws an exception, unlike the columns method
            try (ResultSet results = statement.executeQuery(variable)) {
                
                //initializes the int i to 0
                int i = 0;
                
                //While there are still results
                while (results.next()) {

                    //The for loop to store the data into the 2d array
                    for (int j = 0; j < 12; j++) {
                        dat[i][j] = results.getString(j + 1);
                    }
                    
                    //Increments i for the 2d array
                    i++;
                }
            }
            
            //Closes the statement
            statement.close();
            
        }
        
        //Catches the exception
        catch (SQLException sqlExcept) {
        }

        //Shuts down the connection
        shutdown();
        
        //returns the data
        return dat;
    }

    /*
        The method to shut down the connection to the database
    */
    private static void shutdown() {
        
        //Try to shut down the connection
        try {
            //If theres still a statement, close it
            if (statement != null) {
                statement.close();
            }
            
            //If theres still a connection, close it 
            if (connection != null) {
                DriverManager.getConnection(DATABASE + ";shutdown=true");
                connection.close();
            }
        } 
        
        //Catch the thrown exception
        catch (SQLException sqlExcept) {

        }

    }

}

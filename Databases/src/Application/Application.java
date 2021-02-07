/*
    Database Application for CS-499: Artifact 3
    Created by: Nick Parente - 2/2021
    Version 1.0

    This application displays animal rows for the user from the database
    of animal rows for the Grazioso Salvare company. Data shown is for 
    specific animals that can help serve a purpose, such as mountain 
    tracking, disaster, or water applications. 
    
    The application makes the user "Connect" to the database before data 
    is displayed, and once displayed, then the user can interact with the 
    different buttons that show specific data based on disaster and tracking,
    mountain and wilderness, and water. The user can also "Reset", which will
    re-display all of the data in the database. 
 */

package Application;

//Imports the rows for the Database class in the Database package
import Database.Database;

//The imports to handle opening the URL to the company website(snhu.edu)
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

//Imports for the default table model used to populate the jTable
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/*
    The Application clase that contains the main method and GUI rows
*/
public class Application extends javax.swing.JFrame {

    //Initializes the variables for the Java swing components used in the GUI
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel grazioTitle;
    private javax.swing.JLabel createdBy;
    private javax.swing.JLabel grazioImage;
    private javax.swing.JButton Initializer;
    private javax.swing.JButton disasterOrTrackingButton;
    private javax.swing.JButton hyperlink;
    private javax.swing.JButton mountainOrWildernessButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton waterButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    
    Database Database = new Database();
    String[] columns;
    String[][] rows;
    
    /*
        The method to call the method that initializes the components for the GUI 
    */
    public Application() {
        initComponents();
    }
    
    /*
        The method to initialize the components of the GUI, as well as to 
        generate the GUI using the initialized objects. 
    */
    private void initComponents() {

        //initializes the objects for the components of the GUI
        mainPanel = new javax.swing.JPanel();
        grazioTitle = new javax.swing.JLabel();
        createdBy = new javax.swing.JLabel();
        hyperlink = new javax.swing.JButton();
        grazioImage = new javax.swing.JLabel();
        resetButton = new javax.swing.JButton();
        disasterOrTrackingButton = new javax.swing.JButton();
        mountainOrWildernessButton = new javax.swing.JButton();
        Initializer = new javax.swing.JButton();
        waterButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        
        //Disales certain buttons so the user cannot click on them
        //User must "Connect" to database before selecting these buttons
        resetButton.setEnabled(false);
        disasterOrTrackingButton.setEnabled(false);
        mountainOrWildernessButton.setEnabled(false);
        waterButton.setEnabled(false);

        //Sets the GUI to exit upon selecting the 'X' in the top right
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        //Sets the title of the GUI
        setTitle("Databases");

        //Sets the main panel to have a white background
        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        //Sets the font for the label, positions it in the center, and sets text
        grazioTitle.setFont(new java.awt.Font("Arial", 0, 24));
        grazioTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        grazioTitle.setText("Grazioso Salvare Animal Training Database");

        //Sets the font for the label, positions it in the center, and sets text
        createdBy.setFont(new java.awt.Font("Arial", 0, 12));
        createdBy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        createdBy.setText("Created by: Nick Parente");

        //Sets the background color, the font, and the font text
        hyperlink.setBackground(new java.awt.Color(255, 255, 255));
        hyperlink.setFont(new java.awt.Font("Arial", 0, 12));
        hyperlink.setForeground(new java.awt.Color(6, 69, 173));
        //Sets the text, the border around the button, and the position
        hyperlink.setText("Grazioso Salvare's Homepage");
        hyperlink.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        hyperlink.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        //Sets the active listener for the user selection
        hyperlink.addActionListener((java.awt.event.ActionEvent evt) -> {
            hyperlinkActionPerformed(evt);
        });
        
        //Sets the image for the company that will be in the top-right in the GUI
        grazioImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/GraziosoSalvareLogo.png")));
        grazioImage.setMaximumSize(new java.awt.Dimension(685, 683));

        //Sets the font, text, and the active listener for user selection
        resetButton.setFont(new java.awt.Font("Arial", 0, 12));
        resetButton.setText("Reset");
        resetButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            resetButtonActionPerformed(evt);
        });

        //Sets the font, text, and the active listener for the user selection
        disasterOrTrackingButton.setFont(new java.awt.Font("Arial", 0, 12));
        disasterOrTrackingButton.setText("Disaster or Individual Tracking");
        disasterOrTrackingButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            disasterOrTrackingButtonActionPerformed(evt);
        });

        //Sets the font, text, and the active listener for the user selection
        mountainOrWildernessButton.setFont(new java.awt.Font("Arial", 0, 12));
        mountainOrWildernessButton.setText("Mountain or Wilderness");
        mountainOrWildernessButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            mountainOrWildernessButtonActionPerformed(evt);
        });

        //Sets the font, text, and the active listener for the user selection
        waterButton.setFont(new java.awt.Font("Arial", 0, 12));
        waterButton.setText("Water");
        waterButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            waterButtonActionPerformed(evt);
        });

        //Sets the font, text, and the active listener for the user selection
        Initializer.setFont(new java.awt.Font("Arial", 0, 12));
        Initializer.setText("Connect to Database");
        Initializer.addActionListener((java.awt.event.ActionEvent evt) -> {
            InitializerActionPerformed(evt);
        });

        /*
            Originally, this application was designed using the jFrame GUI builder
            within Netbeans. The code below is generated by the GUI builder to 
            generate the layout of the GUI depending on the design layout. 
        
            Normally, this is un-editable, which makes it difficult to work with,
            but code was removed to allow for this rows to be editable.
        */
        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(455, 455, 455)
                                        .addComponent(createdBy))
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(266, 266, 266)
                                        .addComponent(grazioTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addGap(439, 439, 439)
                                .addComponent(hyperlink, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(331, 331, 331)))
                        .addComponent(grazioImage, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(disasterOrTrackingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(mountainOrWildernessButton, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(waterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(Initializer))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(grazioTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(createdBy, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hyperlink))
                    .addComponent(grazioImage, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(Initializer)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetButton)
                    .addComponent(disasterOrTrackingButton)
                    .addComponent(mountainOrWildernessButton)
                    .addComponent(waterButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }                       

    /*
        The method for when the user clicks on the hyperlink
    */
    private void hyperlinkActionPerformed(java.awt.event.ActionEvent evt) {                                          
    
        //Gets the native desktop for the user and opens a new browser window
        //The company link is actually the school link per the project guidelines
        try {
            Desktop.getDesktop().browse(new URL("https://www.snhu.edu/").toURI());
        } 
        
        //catches the exception for if the URL is incorrect
        catch (IOException | URISyntaxException e) {
        }
   
    }                                         

    /*
        The method for when the reset button is pressed by the user
    */
    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            

        //Gets the values for the columns and the rows for the jTable
        columns = Database.getColumnData("RESET");
        rows = Database.getRowData("RESET");
        
        //Initializes the table model object using the new columns and rows
        TableModel model = new DefaultTableModel(rows,columns);

        //initializes the jTable object with the new model
        jTable = new javax.swing.JTable(model);
        
        //Sets the font of the jTable
        jTable.setFont(new java.awt.Font("Arial", 0, 12));
        
        //Sets the jScrollPanel to contain the jTable
        jScrollPane1.setViewportView(jTable);
        
        //Disables the reset button for the user 
        resetButton.setEnabled(false);
    }                                           

    /*
        The method for when the disaster or tracking button is pressed by the user
    */
    private void disasterOrTrackingButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                         

        //get the data for the rows and columns for the jTable
        //"RESET" contains the string for getting the columns and the reset data
        columns = Database.getColumnData("RESET");
        rows = Database.getRowData("DISASTER");
        
        //Initializes the table model object using the new columns and rows
        TableModel model = new DefaultTableModel(rows,columns);
        
        //initializes the jTable object with the new model
        jTable = new javax.swing.JTable(model);
        
        //Sets the font of the jTable
        jTable.setFont(new java.awt.Font("Arial", 0, 12));
        
        //Sets the jScrollPanel to contain the jTable
        jScrollPane1.setViewportView(jTable);
        
        //Enables the reset button for the user 
        resetButton.setEnabled(true);
        
        
    }                                                        

    /*
        The method for when the mountain or wilderness button is pressed by the user
    */
    private void mountainOrWildernessButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                           
        
        //get the data for the rows and columns for the jTable
        //"RESET" contains the string for getting the columns and the reset data
        columns = Database.getColumnData("RESET");
        rows = Database.getRowData("MOUNTAIN");

        //Initializes the table model object using the new columns and rows
        TableModel model = new DefaultTableModel(rows, columns);

        //initializes the jTable object with the new model
        jTable = new javax.swing.JTable(model);

        //Sets the font of the jTable
        jTable.setFont(new java.awt.Font("Arial", 0, 12));

        //Sets the jScrollPanel to contain the jTable
        jScrollPane1.setViewportView(jTable);

        //Enables the reset button for the user 
        resetButton.setEnabled(true);
    }                                                          

    /*
        The method for when the water button is pressed by the user
    */
    private void waterButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        
        //get the data for the rows and columns for the jTable
        //"RESET" contains the string for getting the columns and the reset data
        columns = Database.getColumnData("RESET");
        rows = Database.getRowData("WATER");
        
        //Initializes the table model object using the new columns and rows
        TableModel model = new DefaultTableModel(rows,columns);
        
        //initializes the jTable object with the new model
        jTable = new javax.swing.JTable(model);
        
        //Sets the font of the jTable
        jTable.setFont(new java.awt.Font("Arial", 0, 12));
        
        //Sets the jScrollPanel to contain the jTable
        jScrollPane1.setViewportView(jTable);
        
        //Enables the reset button for the user 
        resetButton.setEnabled(true);
    }                                           

    /*
        The method for when the initializer button is pressed by the user
    */
    private void InitializerActionPerformed(java.awt.event.ActionEvent evt) {                                            

        //get the data for the rows and columns for the jTable
        //"RESET" contains the string for getting the columns and the reset data
        columns = Database.getColumnData("RESET");
        rows = Database.getRowData("RESET");
        
        //Initializes the table model object using the new columns and rows
        TableModel model = new DefaultTableModel(rows,columns);
        
        //initializes the jTable object with the new model
        jTable = new javax.swing.JTable(model);
        
        //Sets the font of the jTable
        jTable.setFont(new java.awt.Font("Arial", 0, 12));
        
        //Sets the jScrollPanel to contain the jTable
        jScrollPane1.setViewportView(jTable);
        
        //Sets the text to inform the user they are connected
        Initializer.setText("Connected");
        
        //Initializer is disabled from user selection
        Initializer.setEnabled(false);
        
        //Enables the buttons for specific searching 
        disasterOrTrackingButton.setEnabled(true);
        mountainOrWildernessButton.setEnabled(true);
        waterButton.setEnabled(true);
        
    }                                           

   
    /*
        The main method of the application
    */
    public static void main(String args[]){

        //Initializes the GUI application
        java.awt.EventQueue.invokeLater(() -> {
            new Application().setVisible(true);
        });
        
    }

}

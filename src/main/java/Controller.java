import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

// NOTES-------------------------------------------------------------------------------------------

// CLEAN UP PROGRAM BY INSPECTING CODE AND ADDING JAVA DOC COMMENTS (Missing on quality of
// life update but not required for submission) have the productionLog also output employee
// name after date (this could be done by adding another column to DB's to account for employee)
// could not figure out how to do (alt certain key to jump around tabs)

// NOTES-------------------------------------------------------------------------------------------

public class Controller {

  @FXML
  private TabPane tabPane;

  @FXML
  private Label lblWelcomeBack;

  @FXML
  private Label lblLogin;

  @FXML
  private TextField txtPersonalName;

  @FXML
  private TextField txtPassword;

  @FXML
  private Tab tabEmployeeLogin;

  @FXML
  private Tab tabWelcome;

  @FXML
  private Tab tabCreateEmployee;

  @FXML
  private Tab tabProductLine;

  @FXML
  private Tab tabProduce;

  @FXML
  private Tab tabProductionLog;

  @FXML
  private TextField txtNewPersonalName;

  @FXML
  private TextField txtNewUserPassword;

  @FXML
  private TextField txtProductName;

  @FXML
  private TextField txtManufacturer;

  @FXML
  private ChoiceBox<String> chbItemType;

  @FXML
  private TableView<Product> tableView;

  @FXML
  private TableColumn<?, ?> idCol;

  @FXML
  private TableColumn<Product, String> productCol;

  @FXML
  private TableColumn<Product, String> manufacturerCol;

  @FXML
  private TableColumn<Product, ItemType> ItemCol;

  @FXML
  private Label lblProductLine;

  @FXML
  private ListView<Product> productListView;

  @FXML
  private ComboBox<String> cboQuantity;

  @FXML
  private Label lblRecordError;

  @FXML
  private TextArea txtAProductLog;

  @FXML
  private Label lblEmployeeDetails;

  @FXML
  private Label lblEmployeeError;

  // Event Handlers
  @FXML
  void activateCreateUser() {

    // Activate Create Employee tab when button is hit
    tabCreateEmployee.setDisable(false);

    // Jump to tab Create Employee
    tabPane.getSelectionModel().select(tabCreateEmployee);
  }

  @FXML
  void activateLogin() {
    // Activate Employee Login tab when button is hit
    tabEmployeeLogin.setDisable(false);

    // Jump to tab Employee Login
    tabPane.getSelectionModel().select(tabEmployeeLogin);

  }

  @FXML
  void addProduct() {

    // Set label visibility true to reset visiblePause endlessly
    lblProductLine.setVisible(true);

    // Visibility length for labels (found on Stackoverflow)
    PauseTransition visiblePause = new PauseTransition(Duration.seconds(4));
    visiblePause.setOnFinished(event -> lblProductLine.setVisible(false));

    String productName = txtProductName.getText();

    String productManufacturer = txtManufacturer.getText();

    // Check if text fields are empty else add product to db
    if (productName.isEmpty() && productManufacturer.isEmpty()) {
      lblProductLine.setText("Please fill in both text fields");
      // Sets focus on the text field
      txtProductName.requestFocus();
      visiblePause.play();
    } else if (productName.isEmpty()) {
      lblProductLine.setText("Please enter a valid product name");
      txtProductName.requestFocus();
      visiblePause.play();
    }
    // If manufacturer length is less than 3 it can make production record fail as it needs
    // a manufacturer of at least length 3 for serial number
    else if (productManufacturer.isEmpty() || productManufacturer.length() < 3) {
      lblProductLine.setText("Please enter a valid manufacturer");
      txtManufacturer.requestFocus();
      visiblePause.play();
    } else {
      lblProductLine.setText("Product Added");

      visiblePause.play();

      // Adds product into db
      connectToDb();

      // Clear textFields after values have been loaded into PRODUCT DB
      txtProductName.clear();
      txtManufacturer.clear();

      // Populates the tableView and listView
      loadProductionList();

    }

  }

  @FXML
  void createUser() {

    // Set label visibility true to reset visiblePause endlessly
    lblEmployeeError.setVisible(true);

    // Visibility length for labels (found on Stackoverflow)
    PauseTransition visiblePause = new PauseTransition(Duration.seconds(4));
    visiblePause.setOnFinished(event -> lblEmployeeError.setVisible(false));

    // Fields made to get text from textFields
    String createdName = txtNewPersonalName.getText();
    String createdPassword = txtNewUserPassword.getText();

    // Check if user already exists
    boolean userAvailability;

    // Checking if fields are empty else continue
    if (createdName.isEmpty() && createdPassword.isEmpty()) {
      lblEmployeeError.setText("Please fill in both text fields");
      // Sets focus on the text field
      txtNewPersonalName.requestFocus();
      visiblePause.play();
    } else if (createdName.isEmpty()) {
      lblEmployeeError.setText("Please fill in full name");
      // Sets focus on the text field
      txtNewPersonalName.requestFocus();
      visiblePause.play();
    } else if (createdPassword.isEmpty()) {
      lblEmployeeError.setText("Please fill in password");
      txtNewUserPassword.requestFocus();
      visiblePause.play();
    } else {

      // Check if user already exists
      userAvailability = checkIfNameExists(createdName);

      // If there is availability add user to DB else ask for another name
      if (userAvailability) {

        Employee employee = new Employee(createdName, createdPassword);

        lblEmployeeDetails.setText(employee.toString());
        lblEmployeeError.setText("Employee Details added to Database, you may now log in");
        visiblePause.play();

        // Calls add employee to DB method while taking values from employee class
        // to avoid inputting incorrect passwords entering the DB (aka input pw for incorrect
        // formatted passwords)
        addEmployeeToDB(employee.getName(), employee.getPassword(), employee.getUserName(),
            employee.getEmail());

        // Disable attempt to login since employee was just created
        tabEmployeeLogin.setDisable(false);
        tabWelcome.setDisable(true);


      } else {
        lblEmployeeError.setText("Name already exists try another");
        lblEmployeeDetails.setText("");
      }

      txtNewPersonalName.clear();
      txtNewUserPassword.clear();


    }

  }

  @FXML
  void loginUser() {

    // Visibility length for labels (found on Stackoverflow)
    PauseTransition visiblePause = new PauseTransition(Duration.seconds(4));
    visiblePause.setOnFinished(event -> lblLogin.setVisible(false));

    // Set label visibility true to reset visiblePause endlessly
    lblLogin.setVisible(true);

    // Fields made to get text from textFields
    String name = txtPersonalName.getText();
    String password = txtPassword.getText();

    boolean userExist;

    // Checking if fields are empty else continue
    if (name.isEmpty() && password.isEmpty()) {
      lblLogin.setText("Please fill in both text fields");
      // Sets focus on the text field
      txtPersonalName.requestFocus();
      visiblePause.play();
    } else if (name.isEmpty()) {
      lblLogin.setText("Please fill in Full name");
      // Sets focus on the text field
      txtPersonalName.requestFocus();
      visiblePause.play();
    } else if (password.isEmpty()) {
      lblLogin.setText("Please fill in Password");
      txtPassword.requestFocus();
      visiblePause.play();
    } else {

      userExist = checkUserCredentials(name, password);

      if (userExist) {

        lblWelcomeBack.setText("Welcome! " + name);

        lblLogin.setText("You now have access to the final three tabs");
        visiblePause.play();

        // Once user logs in open up other tab to access databases
        tabProductLine.setDisable(false);
        tabProduce.setDisable(false);
        tabProductionLog.setDisable(false);

        // Disable create employee to avoid user logged in to create another user.
        tabCreateEmployee.setDisable(true);
        tabWelcome.setDisable(true);
      } else {
        lblLogin.setText("Incorrect Name or Password double check credentials");
        lblWelcomeBack.setText("");
        visiblePause.play();
      }

      txtPersonalName.clear();
      txtPassword.clear();

    }

  }

  @FXML
  void recordProduct() {

    // Set label visibility true to reset visiblePause endlessly
    lblRecordError.setVisible(true);

    // Visibility length for labels (found on Stackoverflow)
    PauseTransition visiblePause = new PauseTransition(Duration.seconds(4));
    visiblePause.setOnFinished(event -> lblRecordError.setVisible(false));

    // quantity in comboBox used in for loop and check by checkIfInt method
    String quantity = cboQuantity.getSelectionModel().getSelectedItem();

    boolean isQuantityInt = checkIfInt(quantity);

    // Checks if listView is not selected
    if (productListView.getSelectionModel().getSelectedItem() == null) {
      lblRecordError.setText("Please select a product");
      productListView.requestFocus();
      visiblePause.play();
    }
    // If quantity is an integer
    else if (isQuantityInt) {
      // Outputs text to GUI
      lblRecordError.setText("Product Recorded");

      visiblePause.play();

      // Makes product object to be passed in productionRecord object below
      Product product = productListView.getSelectionModel().getSelectedItem();

      // Jumps to currentTypeValue to obtain the number of products are in the DB
      int currentProductAmount = getCurrentTypeValue(product.getId());

      // quantity in comboBox used in for loop
      int quantityAmount = cboQuantity.getSelectionModel().getSelectedIndex();

      // ArrayList made to store objects of listView
      ArrayList<ProductionRecord> productionRun = new ArrayList<>();

      // Populates the arrayList with listView objects
      for (int count = 0; count <= quantityAmount; count++) {

        productionRun.add(new ProductionRecord(product, ++currentProductAmount));

      }

      // Calls addToProductionDB which will add ArrayList values into PRODUCTION RECORD DATABASE
      addToProductionDB(productionRun);

      // Clear textArea before updated productionRecord gets loaded to avoid duplication
      txtAProductLog.clear();

      // Creates objects based on info from DB
      loadProductionLog();

      // Populate the TextArea in the Production Log tab with info from productionLog (suppose to be here but unsure)
      // as loadProductionLog() passes productionLog arrayList to showProduction
      //showProduction(productionRun);
    }


  }


  // Creates observableList
  final ObservableList<Product> productLine = FXCollections.observableArrayList();

  public void initialize() {

    // Disable
    tabEmployeeLogin.setDisable(true);
    tabCreateEmployee.setDisable(true);
    tabProductLine.setDisable(true);
    tabProduce.setDisable(true);
    tabProductionLog.setDisable(true);

    // Options for choiceBox (grabs enums and outputs each code name)
    for (ItemType item : ItemType.values()) {
      chbItemType.getItems().add(String.valueOf(item));
    }

    // defaults to first enum type
    chbItemType.getSelectionModel().selectFirst();

    //for loop linked to a comboBox allowing to hold 10 options in the
    // form of 1-10 and can be edited.
    for (int count = 1; count <= 10; count++) {
      cboQuantity.getItems().add(String.valueOf(count));
    }
    // Sets number 1 to default and makes it editable.
    cboQuantity.setEditable(true);
    cboQuantity.getSelectionModel().selectFirst();

    // Sets up table format for tableView and listView
    setupProductLineTable();

    // Populates the tableView and listView
    loadProductionList();

    // Populates the textArea
    loadProductionLog();

  }

  public String getDBPassword() {

    try {
      BufferedReader reader = new BufferedReader(new FileReader(
          "C:\\Users\\William\\IdeaProjects\\Production\\src\\main\\java\\dbPassword.txt"));

      StringBuilder content = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {

        content.append(line);
        content.append(System.lineSeparator());

      }

      // returns password in trim() to account for unwanted spaces in file
      return content.toString().trim();
    } catch (IOException IO) {
      return null;
    }
  }

  // Recursive method made to reverse passwords in DB to avoid info leaks
  public String reverseString(String pw) {

    if (pw.isEmpty()) {
      return pw;
    }

    //Calls itself recursively (substring returns a string of index indicated, charAt returns character at specified index)
    return reverseString(pw.substring(1)) + pw.charAt(0);

  }

  public void setupProductLineTable() {

    // Warning for Unchecked assignment here solution says assign variables to each column
    productCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
    manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
    ItemCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
    idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));

    // Add products to tableView
    tableView.setItems(productLine);
    productListView.setItems(productLine);

  }

  // Outputs db into Table View and listView
  public void loadProductionList() {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getDBPassword());
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // Executing this query will select all the columns in the product data base and
      // return it resultSet (Output).
      String sql = "Select * FROM PRODUCT";

      ResultSet rs = stmt.executeQuery(sql);

      int id;
      String productName;
      ItemType type;
      String manufacturerName;

      // Clearing the productLine (Observable List) before populating to avoid duplication
      productLine.clear();

      // while loop to add data in Observable List
      while (rs.next()) {

        // Get current ID from DB
        id = rs.getInt(1);

        // Gets productName from DB
        productName = rs.getString(2);

        type = null;
        // Gets ItemType from DB
        for (ItemType item : ItemType.values()) {
          if (String.valueOf(item).equals(rs.getString(3))) {
            type = item;
          }
        }

        // Gets manufacturerName from DB
        manufacturerName = rs.getString(4);

        Widget product = new Widget(id, productName, manufacturerName, type);
        productLine.add(product);

      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  // Connects to the database and writes info provided in text fields to PRODUCT Database
  public void connectToDb() {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getDBPassword());
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      String productName = txtProductName.getText();
      String manufacturerName = txtManufacturer.getText();
      String itemType = chbItemType.getValue();

      // Inserts values into product database by taking the user inputs.
      final String insertSql = "INSERT INTO Product(product_name, product_type, manufacturer) "
          + "VALUES (?, ?, ?);";
      PreparedStatement ps = conn.prepareStatement(insertSql);
      ps.setString(1, productName);
      ps.setString(2, itemType);
      ps.setString(3, manufacturerName);

      ps.executeUpdate();

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  // Inserts productionRun object into productionRecord database
  public void addToProductionDB(ArrayList<ProductionRecord> productionRun) {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getDBPassword());
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // For each loop of type productionRecord passing each value of productionRun arrayList into
      // productionRecord database
      for (ProductionRecord record : productionRun) {

        // Inserts values into product database by taking the user inputs.
        final String insertSql =
            "INSERT INTO PRODUCTIONRECORD(product_id, serial_num, date_produced) "
                + "VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insertSql);
        ps.setInt(1, record.getProductID());
        ps.setString(2, record.getSerialNumber());
        ps.setTimestamp(3, record.getDateProduced());

        ps.executeUpdate();

      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  // Creates objects based on info from DB
  public void loadProductionLog() {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getDBPassword());
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // Executing this query will select all the columns in the product data base and
      // return it resultSet (Output).
      String sql = "Select * FROM PRODUCTIONRECORD";

      ResultSet rs = stmt.executeQuery(sql);

      // Fields holding current row in DB
      int productionNumber;
      int productID;
      String serialNumber;
      Timestamp date;

      // Record of productionRecord DB
      ArrayList<ProductionRecord> productionLog = new ArrayList<>();

      // while loop to add data in Observable List
      while (rs.next()) {

        // Gets productName from DB
        productionNumber = rs.getInt(1);

        productID = rs.getInt(2);

        serialNumber = rs.getString(3);

        date = rs.getTimestamp(4);

        // Populate productionLog ArrayList
        ProductionRecord productRecorded = new ProductionRecord(productionNumber, productID,
            serialNumber, date);
        productionLog.add(productRecorded);

      }

      showProduction(productionLog);

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

  }

  // Populate the TextArea in the Production Log tab with info from productionLog
  public void showProduction(ArrayList<ProductionRecord> productionLog) {

    // Enhanced for loop of type ProductionRecord passing each value of productionLog to
    // append to textArea
    for (ProductionRecord log : productionLog) {

      txtAProductLog.appendText(log.toString());

    }

  }

  // Method made to access PRODUCT DB names to use for textArea that is
  // (called in ProductionRecord toString())
  public Widget getDataBaseNames(int givenID) {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getDBPassword());
    Connection conn;
    PreparedStatement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // SQL Where to filter product_id to find exact product name needed
      String sql = "Select * FROM PRODUCT WHERE ID = ?";

      stmt = conn.prepareStatement(sql);

      stmt.setInt(1, givenID);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {

        String productName = rs.getString("product_name");

        ItemType type = null;
        // Gets ItemType from DB
        for (ItemType item : ItemType.values()) {
          if (String.valueOf(item).equals(rs.getString("product_type"))) {
            type = item;
          }
        }

        // Gets manufacturerName from DB
        String manufacturerName = rs.getString("manufacturer");

        return new Widget(givenID, productName, manufacturerName, type);
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  // Method made to access PRODUCTION RECORD DB to get the amount products made for a certain type
  // (called in recordProduct)
  public int getCurrentTypeValue(int givenID) {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getDBPassword());
    Connection conn;
    PreparedStatement stmt;

    int currentProductAmount = 0;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // SQL Where to filter product_id to find exact product amount needed
      String sql = "Select * FROM PRODUCTIONRECORD WHERE product_id = ?";

      stmt = conn.prepareStatement(sql);

      stmt.setInt(1, givenID);

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {

        // Gets serialNum
        String serialNum = rs.getString("serial_num");

        // Extract new string (Ex: AppAU00000 = 00000)
        String number = serialNum.substring(5);

        // Parses string to obtain number
        currentProductAmount = Integer.parseInt(number);

      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    return currentProductAmount;
  }

  // Method made that inserts (name, password, userName, and email)
  // from employee text fields into Employee database (called in createUser)
  public void addEmployeeToDB(StringBuilder name, String password, String userName, String email) {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getDBPassword());
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      String correctName = name.toString();

      // Inserts values into employee database by taking the user inputs.
      final String insertSql = "INSERT INTO EMPLOYEE(name, password, username, email) "
          + "VALUES (?, ?, ?, ?);";
      PreparedStatement ps = conn.prepareStatement(insertSql);
      ps.setString(1, correctName);
      ps.setString(2, password);
      ps.setString(3, userName);
      ps.setString(4, email);

      ps.executeUpdate();

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

  }

  // Method made to access EMPLOYEE DB to check if name exist to avoid duplicate names
  // (called in createUser)
  public boolean checkIfNameExists(String name) {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getDBPassword());
    Connection conn;
    PreparedStatement stmt;

    boolean userExists = true;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // SQL Where to filter product_id to find exact product name needed
      String sql = "Select * FROM EMPLOYEE WHERE NAME = ?";

      stmt = conn.prepareStatement(sql);

      stmt.setString(1, name);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {

        String nameFromDB = rs.getString(1);

        // If name from database equals name given set userExist to false
        // (userExist = false means user is in database already logic is backwards)
        if (nameFromDB.equals(name)) {

          userExists = false;

        }

      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    return userExists;
  }

  // Method made to access EMPLOYEE DB to check user credentials for user login
  // (called in loginUser)
  public boolean checkUserCredentials(String name, String password) {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getDBPassword());
    Connection conn;
    PreparedStatement stmt;

    boolean userExists = false;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // SQL Where to filter product_id to find exact product name needed
      String sql = "Select * FROM EMPLOYEE WHERE NAME = ? AND PASSWORD = ?";

      stmt = conn.prepareStatement(sql);

      stmt.setString(1, name);
      stmt.setString(2, password);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {

        String nameFromDB = rs.getString(1);
        String passwordFromDB = rs.getString(2);

        // If name and password match set userExist to true
        // (this logic is valid compared to checkIfNameExistLogic)
        if (nameFromDB.equals(name) && passwordFromDB.equals(password)) {

          userExists = true;

        }

      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    return userExists;
  }

  // Checks if string passed is an integer
  public boolean checkIfInt(String amount) {

    try {
      int quantityAmount = Integer.parseInt(amount);
      System.out.println(quantityAmount);
      return true;
    } catch (NumberFormatException e) {
      lblRecordError.setText("Error: " + amount + " is not an integer");
      // Focus on comboBox
      cboQuantity.requestFocus();
      return false;
    }
  }

}



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

// NOTES FOR LATER UPDATES-------------------------------------------------------------------------

// (Missing one quality of life update but not required for submission) have the productionLog also
// output employee name after date (this could be done by adding another column to DB's to account
// for employee could not figure out how to do (Another quality of life update: alt certain key
// to jump around tabs)
// Update window to have a log out option once user is logged in
//

// NOTES FOR LATER UPDATES-------------------------------------------------------------------------

/**
 * Initializes skeletal function of production project. Allowing employees to login to interact with
 * the database through the GUI.
 *
 * @author William Angola
 */
public class Controller {


  /**
   * Most outer layer of the GUI that holds all tabs.
   */
  @FXML
  private TabPane tabPane;

  /**
   * Label that outputs when login is successful.
   */
  @FXML
  private Label lblWelcomeBack;

  /**
   * Label that outputs info/errors to employee when trying to log in.
   */
  @FXML
  private Label lblLogin;

  /**
   * Text field handling full name in employee login.
   */
  @FXML
  private TextField txtPersonalName;

  /**
   * Text field handling password in employee login.
   */
  @FXML
  private TextField txtPassword;

  /**
   * Second tab that allows employees to login.
   */
  @FXML
  private Tab tabEmployeeLogin;

  /**
   * First tab that welcomes employee with info and buttons.
   */
  @FXML
  private Tab tabWelcome;

  /**
   * Third tab that allows employees to create account.
   */
  @FXML
  private Tab tabCreateEmployee;

  /**
   * Fourth tab that allows employees to enter a product into the product database.
   */
  @FXML
  private Tab tabProductLine;

  /**
   * Fifth tab that allows employees to select and record a number of specific products.
   */
  @FXML
  private Tab tabProduce;

  /**
   * Sixth tab that allows employees to view production records.
   */
  @FXML
  private Tab tabProductionLog;

  /**
   * Text field handling full name in create employee.
   */
  @FXML
  private TextField txtNewPersonalName;

  /**
   * Text field handling password in create employee.
   */
  @FXML
  private TextField txtNewUserPassword;

  /**
   * Text field handling product name in product line.
   */
  @FXML
  private TextField txtProductName;

  /**
   * Text field handling product manufacturer in product line.
   */
  @FXML
  private TextField txtManufacturer;

  /**
   * Choice box handling item type in product line.
   */
  @FXML
  private ChoiceBox<String> chbItemType;

  /**
   * Holds existing values from product database.
   */
  @FXML
  private TableView<Product> tableView;

  /**
   * Holds existing product id's from product database.
   */
  @FXML
  private TableColumn<?, ?> idCol;

  /**
   * Holds existing product's from product database.
   */
  @FXML
  private TableColumn<Product, String> productCol;

  /**
   * Holds existing product manufacturer's from product database.
   */
  @FXML
  private TableColumn<Product, String> manufacturerCol;

  /**
   * Holds existing product item's from product database.
   */
  @FXML
  private TableColumn<Product, ItemType> itemCol;

  /**
   * Outputs info/errors when adding a product.
   */
  @FXML
  private Label lblProductLine;

  /**
   * Holds existing values from product database.
   */
  @FXML
  private ListView<Product> productListView;

  /**
   * Gives a sample of digits to be used but could be edited by employee.
   */
  @FXML
  private ComboBox<String> cboQuantity;

  /**
   * Outputs info/error when recording a production.
   */
  @FXML
  private Label lblRecordError;

  /**
   * Holds existing production record database.
   */
  @FXML
  private TextArea txtAProductLog;

  /**
   * Outputs employee info once employee has been created.
   */
  @FXML
  private Label lblEmployeeDetails;

  /**
   * Outputs info/error when trying to create an employee.
   */
  @FXML
  private Label lblEmployeeError;

  /**
   * When create employee in Welcome tab is clicked, jump to create employee.
   */
  @FXML
  void activateCreateUser() {

    // Activate Create Employee tab when button is hit
    tabCreateEmployee.setDisable(false);

    // Jump to tab Create Employee
    tabPane.getSelectionModel().select(tabCreateEmployee);
  }

  /**
   * When login in Welcome tab is clicked, jump to login.
   */
  @FXML
  void activateLogin() {
    // Activate Employee Login tab when button is hit
    tabEmployeeLogin.setDisable(false);

    // Jump to tab Employee Login
    tabPane.getSelectionModel().select(tabEmployeeLogin);

  }

  /**
   * Adds product to the product database if inputs are valid.
   */
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

      // If manufacturer length is less than 3 it can make production record fail as it needs
      // a manufacturer of at least length 3 for a serial number
    } else if (productManufacturer.isEmpty() || productManufacturer.length() < 3) {
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

  /**
   * Creates employee and adds employee to the employee database if inputs are valid (it may default
   * some values if incorrect).
   */
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

  /**
   * Checks if employee credentials are correct.
   */
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
        txtPersonalName.requestFocus();
        lblWelcomeBack.setText("");
        visiblePause.play();
      }

      txtPersonalName.clear();
      txtPassword.clear();

    }

  }

  /**
   * Records product into production record database if inputs are valid.
   */
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

      // If quantity is an integer
    } else if (isQuantityInt) {
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
    }


  }

  /**
   * Allows the passing of a product list between setupProductLineTable and loadProductionList.
   */
  final ObservableList<Product> productLine = FXCollections.observableArrayList();

  /**
   * Initialize specific methods and formats initial program start up.
   */
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

  /**
   * Obtains saved database password from a file.
   *
   * @return Database password.
   */
  public String getDbPassword() {

    try {
      BufferedReader reader = new BufferedReader(new FileReader(
          "C:\\Users\\William\\IdeaProjects\\Production\\src\\main\\java\\dbPassword.txt"));

      StringBuilder content = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {

        content.append(line);
        content.append(System.lineSeparator());

      }

      reader.close();

      // returns password in trim() to account for unwanted spaces in file
      return content.toString().trim();
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * Takes in the database password and reverses to match the correct database password format.
   *
   * @param pw Database password.
   * @return Reverse database password.
   */
  public String reverseString(String pw) {
    // Recursive method made to reverse passwords in DB to avoid info leaks
    if (pw.isEmpty()) {
      return pw;
    }

    //Calls itself recursively (substring returns a string of index indicated, charAt
    // returns character at specified index)
    return reverseString(pw.substring(1)) + pw.charAt(0);

  }

  /**
   * Sets up TableView format. Adds products to TableView and ListView once loaded.
   */
  public void setupProductLineTable() {

    productCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
    manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
    itemCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
    idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));

    // Add products to tableView
    tableView.setItems(productLine);
    productListView.setItems(productLine);

  }

  /**
   * Loads current Product database values into the observable list.
   */
  public void loadProductionList() {

    final String Jdbc_Driver = "org.h2.Driver";
    final String Db_Url = "jdbc:h2:./res/Productdb";

    // Database credentials
    final String user = "";
    final String pass = reverseString(getDbPassword());
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Jdbc_Driver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(Db_Url, user, pass);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // Executing this query will select all the columns in the product data base and
      // return its resultSet (Output).
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

        // Object of widget class to use abstract product class
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

  /**
   * Enters current user product values created into Product database.
   */
  public void connectToDb() {

    final String Jdbc_Driver = "org.h2.Driver";
    final String Db_Url = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String user = "";
    final String pass = reverseString(getDbPassword());
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Jdbc_Driver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(Db_Url, user, pass);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // Fields taking text and values from textFields in productLine tab
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

  /**
   * Enters user created production records into the Production Record database.
   *
   * @param productionRun Current production records created.
   */
  public void addToProductionDB(ArrayList<ProductionRecord> productionRun) {

    final String Jdbc_Driver = "org.h2.Driver";
    final String Db_Url = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String user = "";
    final String pass = reverseString(getDbPassword());
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Jdbc_Driver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(Db_Url, user, pass);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // For each loop of type productionRecord passing each value of productionRun arrayList into
      // productionRecord database
      for (ProductionRecord record : productionRun) {

        // Inserts values into product database by taking elements of productionRun ArrayList.
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

  /**
   * Loads current Production Record database values into an ArrayList named productionLog.
   */
  public void loadProductionLog() {

    final String Jdbc_Driver = "org.h2.Driver";
    final String Db_Url = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String user = "";
    final String pass = reverseString(getDbPassword());
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Jdbc_Driver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(Db_Url, user, pass);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // Executing this query will select all the columns in the productRecord database and
      // return its resultSet (Output).
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

      // Call to showProduction in textArea
      showProduction(productionLog);

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

  }

  /**
   * Loads each value of production record database into textArea.
   *
   * @param productionLog Current production record database values
   */
  public void showProduction(ArrayList<ProductionRecord> productionLog) {

    // Enhanced for loop of type ProductionRecord passing each value of productionLog to
    // append to textArea
    for (ProductionRecord log : productionLog) {

      txtAProductLog.appendText(log.toString());

    }

  }

  /**
   * Compares the productID from production record database and product database to see if there is
   * a match. If a match occurs then an object of Widget is created to return values of that product
   * row to use product name in productionRecord toString.
   *
   * @param givenID ProductId from productionRecord.
   * @return A Widget object if match is successful.
   */
  public Widget getDataBaseNames(int givenID) {

    final String Jdbc_Driver = "org.h2.Driver";
    final String Db_Url = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String user = "";
    final String pass = reverseString(getDbPassword());
    Connection conn;
    PreparedStatement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Jdbc_Driver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(Db_Url, user, pass);

      // SQL Where to filter product_id to find exact product name needed
      String sql = "Select * FROM PRODUCT WHERE ID = ?";

      stmt = conn.prepareStatement(sql);

      // Sets productionRecord ID to find a match
      stmt.setInt(1, givenID);

      ResultSet rs = stmt.executeQuery();

      // Looks for product info
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

  /**
   * Finds current amount of a certain product recorded.
   *
   * @param givenID ProductId from listView in recordProduct.
   * @return Current amount of a specific product that has been recorded.
   */
  public int getCurrentTypeValue(int givenID) {
    final String Jdbc_Driver = "org.h2.Driver";
    final String Db_Url = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String user = "";
    final String pass = reverseString(getDbPassword());
    Connection conn;
    PreparedStatement stmt;

    int currentProductAmount = 0;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Jdbc_Driver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(Db_Url, user, pass);

      // SQL Where to filter product_id to find exact product amount needed
      String sql = "Select * FROM PRODUCTIONRECORD WHERE product_id = ?";

      stmt = conn.prepareStatement(sql);

      // Sets product ID to find a match
      stmt.setInt(1, givenID);

      ResultSet rs = stmt.executeQuery();

      // If a current product exists keep obtaining number.
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

  /**
   * Adds employee to the Employee database.
   *
   * @param name     Users entered name.
   * @param password Users entered password.
   * @param userName Generated by entered name.
   * @param email    Generated by entered name.
   */
  public void addEmployeeToDB(StringBuilder name, String password, String userName, String email) {

    final String Jdbc_Driver = "org.h2.Driver";
    final String Db_Url = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String user = "";
    final String pass = reverseString(getDbPassword());
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Jdbc_Driver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(Db_Url, user, pass);

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

  /**
   * Checks if name entered by new employee exists.
   *
   * @param name Name entered by employee.
   * @return False if user does exists, True if user does not exists. (Logic is backwards).
   */
  public boolean checkIfNameExists(String name) {

    final String Jdbc_Driver = "org.h2.Driver";
    final String Db_Url = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String user = "";
    final String pass = reverseString(getDbPassword());
    Connection conn;
    PreparedStatement stmt;

    boolean userExists = true;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Jdbc_Driver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(Db_Url, user, pass);

      // SQL Where to filter product_id to find exact product name needed
      String sql = "Select * FROM EMPLOYEE WHERE NAME = ?";

      stmt = conn.prepareStatement(sql);

      // Set name that must be found
      stmt.setString(1, name);

      ResultSet rs = stmt.executeQuery();

      // Find one instance if it exists.
      if (rs.next()) {

        String nameFromDB = rs.getString(1);

        // If name from database equals name given set userExist to false
        // (userExist = false means user is in the database logic is backwards but avoid duplicate
        // lines)
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

  /**
   * Checks if name and password entered by employee is correct.
   *
   * @param name     Employee name input.
   * @param password Employee password input.
   * @return True if name and password match instance in employee database, False if name and
   *         password do not match instance in employee database.
   */
  public boolean checkUserCredentials(String name, String password) {

    final String Jdbc_Driver = "org.h2.Driver";
    final String Db_Url = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String user = "";
    final String pass = reverseString(getDbPassword());
    Connection conn;
    PreparedStatement stmt;

    boolean userExists = false;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Jdbc_Driver);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(Db_Url, user, pass);

      // SQL Where to filter product_id to find exact product name needed
      String sql = "Select * FROM EMPLOYEE WHERE NAME = ? AND PASSWORD = ?";

      stmt = conn.prepareStatement(sql);

      // Set name and password that must be found
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

  /**
   * Check if string entered in comboBox is a number.
   *
   * @param amount Input entered in comboBox by employee.
   * @return True if input is an integer, False if input is not an integer.
   */
  public boolean checkIfInt(String amount) {

    try {
      // Warning quantityAmount is never used but it checks if it can parse String amount
      // allowing the catch to work without it string would never be checked.
      int quantityAmount = Integer.parseInt(amount);
      return true;
    } catch (NumberFormatException e) {
      lblRecordError.setText("Error: " + amount + " is not an integer");
      // Focus on comboBox
      cboQuantity.requestFocus();
      return false;
    }
  }

}



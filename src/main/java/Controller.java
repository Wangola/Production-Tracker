import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller {

  @FXML
  private TextField txtProductName;

  @FXML
  private TextField txtManufacturer;

  @FXML
  private ChoiceBox<String> chbItemType;

  @FXML
  private Button btnAddProduct;

  @FXML
  private TableView<Product> tableView;

  // Warning for Unchecked assignment here solution says assign variables to each column
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
  private Button btnRecordProduct;

  @FXML
  private Label lblRecordError;

  @FXML
  private TextArea txtAProductLog;

  @FXML
  private TextField txtPersonalName;

  @FXML
  private TextField txtUserPassword;

  @FXML
  private Label lblEmployeeDetails;

  @FXML
  private Button btnCreateUser;

  @FXML
  private Label lblEmployeeSuccess;

  @FXML
  void addProduct(ActionEvent event) {

    String productName = txtProductName.getText();

    String productManufacturer = txtManufacturer.getText();

    // Check if text fields are empty
    if(productName.isEmpty() || productManufacturer.isEmpty()){
      lblProductLine.setText("Please fill in both text fields");
    }
    else{
      lblProductLine.setText("Product Added");

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
  void createUser(ActionEvent event) {

    // Start of Week 13 update-----------------------------------------------------------------------


    // Fields made to get text from fields
    String name = txtPersonalName.getText();

    String password = txtUserPassword.getText();

    // Check if user already exists
    boolean userAvailability;

    // Checking if fields are empty else continue
    if(name.isEmpty() || password.isEmpty()){
      lblEmployeeSuccess.setText("Please fill in both text fields");
    }
    else{

      // Check if user already exists
      userAvailability = checkIfUserExists(name);

      // If there is availability add user to DB else ask for another name
      if(userAvailability == true){
        Employee employee = new Employee(name,password);

        lblEmployeeDetails.setText(employee.toString());
        lblEmployeeSuccess.setText("Employee Details added to Database");

        // Calls add employee to DB method while taking values from employee class
        // to avoid inputting incorrect passwords entering the DB (aka input pw for incorrect
        // formatted passwords)
        addEmployeeToDB(employee.getName(), employee.getPassword());

      }
      else{
        lblEmployeeSuccess.setText("Name already exists try another");
        lblEmployeeDetails.setText("");
      }

      txtPersonalName.clear();
      txtUserPassword.clear();

    }


    // End of Week 13 update-------------------------------------------------------------------------

  }

  @FXML
  void recordProduct(ActionEvent event) {

    // Checks if listView is not selected
    if(productListView.getSelectionModel().getSelectedItem() == null){
      lblRecordError.setText("Please select a product");
    }
    else {
      // Outputs text to GUI
      lblRecordError.setText("Product Recorded");

      // Makes product object to be passed in productionRecord object below
      Product product = productListView.getSelectionModel().getSelectedItem();

      // Testing to see if it obtains correct productID
      System.out.println(product.getId());

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

    // Populate the TextArea in the Production Log tab with info from productionLog (suppose to be here but unsure)
    // as loadProductionLog() passes productionLog arrayList to showProduction
    //showProduction(productionRun);


  }



  // Creates observableList
  final ObservableList<Product> productLine = FXCollections.observableArrayList();



  public void initialize() {

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

    // Start of Week 12 update-----------------------------------------------------------------------

    // Sets up table format for tableView and listView
    setupProductLineTable();

    // Populates the tableView and listView
    loadProductionList();

    // Populates the textArea
    loadProductionLog();

    // End of Week 12 update-------------------------------------------------------------------------


    System.out.println("\n");


    // Start of Week 14 update-----------------------------------------------------------------------

    Controller reverse = new Controller();
    System.out.println();

    System.out.println(reverse.reverseString("Abcd12"));

    // End of Week 14 update-------------------------------------------------------------------------
  }

  // Recursive method made to reverse passwords in DB to avoid info leaks
  public String reverseString(String pw){

    if (pw.isEmpty())
      return pw;

    //Calls itself recursively (substring returns a string of index indicated, charAt returns character at specified index)
    System.out.println(pw.substring(1) + pw.charAt(0));
    return reverseString(pw.substring(1)) + pw.charAt(0);

  }


  public void setupProductLineTable() {

    // Warning for Unchecked assignment here solution says assign variables to each column
    productCol.setCellValueFactory(new PropertyValueFactory("Name"));
    manufacturerCol.setCellValueFactory(new PropertyValueFactory("Manufacturer"));
    ItemCol.setCellValueFactory(new PropertyValueFactory("Type"));
    idCol.setCellValueFactory(new PropertyValueFactory("Id"));

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
    final String PASS = "";
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
    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Connects to the database and writes info provided in text fields to PRODUCT Database
  public void connectToDb() {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
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
    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }



  // Inserts productionRun object into productionRecord database
  public void addToProductionDB(ArrayList<ProductionRecord> productionRun){

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      for(int i = 0; i < productionRun.size(); i++){


        // Inserts values into product database by taking the user inputs.
        final String insertSql = "INSERT INTO PRODUCTIONRECORD(product_id, serial_num, date_produced) "
            + "VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insertSql);
        ps.setInt(1, productionRun.get(i).getProductID());
        ps.setString(2, productionRun.get(i).getSerialNumber());
        ps.setTimestamp(3, productionRun.get(i).getDateProduced());


        ps.executeUpdate();

      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Creates objects based on info from DB
  public void loadProductionLog() {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
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
      String  serialNumber;
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
        ProductionRecord productRecorded = new ProductionRecord(productionNumber, productID, serialNumber, date);
        productionLog.add(productRecorded);

      }

      showProduction(productionLog);

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  // Populate the TextArea in the Production Log tab with info from productionLog
  public void showProduction(ArrayList<ProductionRecord> productionLog){

    // (URGENT UPDATE NEEDED) populates textArea but is missing replacing the productID with productName
    for(int i = 0; i < productionLog.size(); i++){

      txtAProductLog.appendText(productionLog.get(i).toString());

    }

  }



  // Method made to access PRODUCT DB names to use for textArea that is (called in ProductionRecord toString())
  public Widget getDataBaseNames(int givenID){

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
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

      if(rs.next()){

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

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }


  // Method made to access PRODUCTION RECORD DB to get the amount products made for a certain type
  public int getCurrentTypeValue(int givenID){
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
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

      while(rs.next()){

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

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return currentProductAmount;
  }


  // Inserts name and password for employee text fields into Employee database
  public void addEmployeeToDB(StringBuilder name, String password){

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
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
      final String insertSql = "INSERT INTO EMPLOYEE(name, password) "
          + "VALUES (?, ?);";
      PreparedStatement ps = conn.prepareStatement(insertSql);
      ps.setString(1, correctName);
      ps.setString(2, password);

      ps.executeUpdate();


      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  // Jumps into database to see if user exist to avoid duplicates
  public boolean checkIfUserExists(String name){



    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
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

      if(rs.next()){

        String nameFromDB = rs.getString(1);

        if(nameFromDB.equals(name)){

          userExists = false;

        }
        else{
          userExists = true;
        }


      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return userExists;
  }
}

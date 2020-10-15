
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


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
  private ComboBox<String> cboQuantity;

  @FXML
  private Button btnRecordProduct;

  @FXML
  private TextArea txtAProduct;

  @FXML
  void addProduct(ActionEvent event) {
    connectToDb();
    System.out.println("Product Added");
  }

  @FXML
  void recordProduct(ActionEvent event) {
    System.out.println("Production Recorded");
  }


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


    // relp output needed (Temporary statements)
    Product product1 = new Widget("iPod", "Apple", ItemType.AUDIO);
    System.out.println(product1.toString());
    Product product2 = new Widget("Zune", "Microsoft", ItemType.AUDIOMOBILE);
    System.out.println(product2.toString());

    System.out.println("\n");


    // Test Media
      AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
          "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
      Screen newScreen = new Screen("720x480", 40, 22);
      MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen,
          MonitorType.LCD);
      ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
      productList.add(newAudioProduct);
      productList.add(newMovieProduct);
      for (MultimediaControl p : productList) {
        System.out.println(p);
        p.play();
        p.stop();
        p.next();
        p.previous();
      }

      // Outputs info to textArea in GUI
      ProductionRecord record = new ProductionRecord(0,0,"0", new Date());
      System.out.println(record.toString());
      txtAProduct.appendText(record.toString());

  }

  public void connectToDb() {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Productdb";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

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

      // Executing this query will select all the columns in the product data base and
      // return it resultSet (Output).
      String sql = "Select product_name, product_type, manufacturer"
          + " FROM PRODUCT";

      ResultSet rs = stmt.executeQuery(sql);

      // while loop to all data in DB
      while (rs.next()) {
        System.out.println(rs.getString(1));
        System.out.println(rs.getString(2));
        System.out.println(rs.getString(3));
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
}
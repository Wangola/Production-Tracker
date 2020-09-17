
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class Controller {

  @FXML
  private TextField txtProcName;

  @FXML
  private TextField txtManufac;

  @FXML
  private ChoiceBox<?> chbItemType;

  @FXML
  private Button btnAddProc;

  @FXML
  private ComboBox<String> cboQuan;

  @FXML
  private Button btnRecordProc;

  @FXML
  void addProduct(ActionEvent event) {
    System.out.println("Product Added");
  }

  @FXML
  void recordProduct(ActionEvent event) {
    System.out.println("Production Recorded");
  }

  public void initialize(){
    connectToDb();
  }

  public static void connectToDb(){
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

      String sql = "SELECT * FROM PRODUCT";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        System.out.println(rs.getString(1));
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
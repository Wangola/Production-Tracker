# Production Project

## Overview
Creator: William Angola.\
This program was created as a school project that will use a GUI page to control/use data within a full database for the purpose of production.

## Code

##### **build.gradle** (09/17/2020)
default gradle file with group id: io.github.Wangola

##### **Main.java** (09/17/2020)
default java main using grade with its only edit being.

```
primaryStage.setTitle("Production Project");
```

##### **Controller.java** (09/17/2020)
In charge for almost all code for this project by creating methods and fields for objects in sceneBuilder\
*Controller Skeleton (class, field, constructor, and methods)*
```
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
  void addProduct(ActionEvent event) {
  }

  @FXML
  void recordProduct(ActionEvent event) {
  }
  
   public void initialize() {
    //for loop linked to a comboBox allowing to hold 10 options in the
    // form of 1-10 and can be edited.
    for (int count = 1; count <= 10; count++) {
      cboQuantity.getItems().add(String.valueOf(count));
    }

  }

  public void connectToDb() {
  // '"+variable+"' Inserts values into product database by taking the user inputs.
      String insertSql = "INSERT INTO Product(product_name, product_type, manufacturer) "
          + "VALUES ( '" + productName + "', '" + itemType + "', '" + manufacturerName + "' );";

      stmt.executeUpdate(insertSql);

  // SExecuting this query will select all the collumns in the product data base and
  // return it resultSet.
      String sql = "Select product_name, product_type, manufacturer"
          + " FROM PRODUCT";

      ResultSet rs = stmt.executeQuery(sql);
  }
```

##### **sample.fxml** (09/17/2020)
sample.fxml is linked directly to sceneBuilder and any progress saved within sceneBuilder and processed and coded into the file.

##### **CSS.css** (09/17/2020)
Only purpose is to create a white background for the GUI page.
```
.root {
  -Fx-background-color: white;
}
```


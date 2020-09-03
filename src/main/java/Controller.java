import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

  @FXML
  private Label lblOutput;

  @FXML
  public void addProduct() {
    System.out.println("Product Added");
  }

  @FXML
  public void addRecord() {
    System.out.println("Production Recorded");
  }

}
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Driver class which begins program in Controller class.
 *
 * @author William Angola
 */
public class Main extends Application {

  /**
   * Execute program.
   *
   * @param args Sequence of characters.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Entry point for all JavaFX applications which sets Scene (window).
   *
   * @param primaryStage Application Thread
   * @throws Exception If normal flow is interrupted.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

    Scene scene = new Scene(root, 960, 544);

    primaryStage.setTitle("Production Project");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}


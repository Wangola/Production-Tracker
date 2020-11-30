/**
 * Room for future implementation currently never used. Allows for the extension of products with
 * screen related formats while implementing MultimediaControl. A product can have screen and
 * monitor type.
 *
 * @author William Angola
 */
public class MoviePlayer extends Product implements MultimediaControl {

  //Fields
  /**
   * Object of screen which contains resolution, refreshRate, and responseTime.
   */
  private final Screen screen;

  /**
   * Object of MonitorType which contain LCD, LED.
   */
  private final MonitorType monitorType;

  /**
   * MoviePlayer constructor which call's its parent constructor in Product while implementing
   * values to Screen and MonitorType (Currently never used).
   *
   * @param name         Product name.
   * @param manufacturer Product manufacturer.
   * @param screen       Screen object.
   * @param monitorType  MonitorType object.
   */
  MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, ItemType.VISUAL);

    this.screen = screen;
    this.monitorType = monitorType;
  }

  /**
   * Display's the superclass Product toString but adds screen and monitor type.
   *
   * @return Product's toString with screen and monitor type.
   */
  public String toString() {
    return "Name: " + getName() + "\n" + "Manufacturer: " + getManufacturer() + "\n" + "Type: "
        + getType().code + "\n"
        + "Screen: " + "\n" + screen + "\n" + "Monitor Type: " + monitorType;
  }


  /**
   * Outputs playing movie.
   */
  @Override
  public void play() {
    System.out.println("Playing movie");
  }

  /**
   * Outputs stopping movie.
   */
  @Override
  public void stop() {
    System.out.println("Stopping movie");
  }

  /**
   * Outputs previous movie.
   */
  @Override
  public void previous() {
    System.out.println("Previous movie");
  }

  /**
   * Outputs next movie.
   */
  @Override
  public void next() {
    System.out.println("Next movie");
  }

}

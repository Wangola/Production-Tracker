public class MoviePlayer extends Product implements MultimediaControl {

  //Fields
  private final Screen screen;
  private final MonitorType monitorType;

  //Constructor
  MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, ItemType.VISUAL);

    this.screen = screen;
    this.monitorType = monitorType;
  }

  //toString
  public String toString() {
    return "Name: " + getName() + "\n" + "Manufacturer: " + getManufacturer() + "\n" + "Type: "
        + getType().code + "\n" +
        "Screen: " + "\n" + screen + "\n" + "Monitor Type: " + monitorType;
  }

  //Setters (Never Used)
//    @Override
//    public void play() {
//      System.out.println("Playing movie");
//    }
//
//    @Override
//    public void stop() {
//      System.out.println("Stopping movie");
//    }
//
//    @Override
//    public void previous() {
//      System.out.println("Previous movie");
//    }
//
//    @Override
//    public void next() {
//      System.out.println("Next movie");
//    }

}

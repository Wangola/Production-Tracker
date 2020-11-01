public class MoviePlayer extends Product implements MultimediaControl {

    //Fields
    public Screen Screen;
    public MonitorType MonitorType;

    //Constructor
    MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
      super(name, manufacturer, ItemType.VISUAL);

      this.Screen = screen;
      this.MonitorType = monitorType;
    }

    //toString
    //Added "\n" after Screen to match formatting on relp
    public String toString() {
      return "Name: " + getName() + "\n" + "Manufacturer: " + getManufacturer() + "\n" + "Type: " + getType().code + "\n" +
          "Screen: " + "\n" + Screen + "\n" + "Monitor Type: " + MonitorType;
    }

    //Setters
    @Override
    public void play() {
      System.out.println("Playing movie");
    }

    @Override
    public void stop() {
      System.out.println("Stopping movie");
    }

    @Override
    public void previous() {
      System.out.println("Previous movie");
    }

    @Override
    public void next() {
      System.out.println("Next movie");
    }

}

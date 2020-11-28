public class Screen implements ScreenSpec {

  //Fields
  private final String resolution;
  private final int refreshRate;
  private final int responseTime;


  //Constructor
  Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  // toString (returns data)
  public String toString() {
    return "Resolution: " + resolution + "\n" + "Refresh rate: " + refreshRate + "\n"
        + "Response time: " + responseTime;
  }

  //Getters (Never Used)
//  @Override
//  public String getResolution() {
//    return resolution;
//  }
//
//  @Override
//  public int getRefreshRate() {
//    return refreshRate;
//  }
//
//  @Override
//  public int getResponseTime() {
//    return responseTime;
//  }

}

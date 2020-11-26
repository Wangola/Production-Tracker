public class  Screen implements ScreenSpec{

  //Fields
  final String resolution;
  final int refreshRate;
  final int responseTime;


  //Constructor
  Screen(String resolution, int refreshRate, int responseTime){
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  // toString (returns data)
  public String toString() {
    return "Resolution: " + resolution + "\n" + "Refresh rate: " + refreshRate + "\n" + "Response time: " + responseTime;
  }

  //Getters
  @Override
  public String getResolution() {
    return resolution;
  }

  @Override
  public int getRefreshRate() {
    return refreshRate;
  }

  @Override
  public int getResponseTime() {
    return responseTime;
  }

}

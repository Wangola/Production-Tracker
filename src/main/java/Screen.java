public class  Screen implements ScreenSpec{

  //Fields
  String resolution;
  int refreshRate;
  int responseTime;


  //Constructor
  Screen(String resolution, int refreshrate, int responsetime){
    this.resolution = resolution;
    this.refreshRate = refreshrate;
    this.responseTime = responsetime;
  }

  // toString (returns data)
  // This is different then what was posted on relp to match output wanted
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

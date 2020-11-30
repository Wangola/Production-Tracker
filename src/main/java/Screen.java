/**
 * Room for future implementation currently never used in Controller. Implements ScreenSpec for
 * other classes to have access to screen fields.
 *
 * @author William Angola
 */
public class Screen implements ScreenSpec {

  //Fields
  /**
   * The screen resolution.
   */
  private final String resolution;

  /**
   * The screen refresh rate.
   */
  private final int refreshRate;

  /**
   * The screen response time.
   */
  private final int responseTime;


  /**
   * Screen constructor which implements given screen info (Currently never used).
   *
   * @param resolution   Screen resolution.
   * @param refreshRate  Screen refresh rate.
   * @param responseTime Screen response time.
   */
  Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  /**
   * Generates screen information format.
   *
   * @return Screen information format.
   */
  public String toString() {
    return "Resolution: " + resolution + "\n" + "Refresh rate: " + refreshRate + "\n"
        + "Response time: " + responseTime;
  }

  /**
   * Gets screen resolution.
   *
   * @return resolution.
   */
  //Getters (Never Used)
  @Override
  public String getResolution() {
    return resolution;
  }

  /**
   * Gets screen refresh rate.
   *
   * @return refreshRate.
   */
  @Override
  public int getRefreshRate() {
    return refreshRate;
  }

  /**
   * Gets screen response time.
   *
   * @return responseTime.
   */
  @Override
  public int getResponseTime() {
    return responseTime;
  }

}

/**
 * Room for future implementation currently never used. ScreenSpec interface allows classes to
 * contract these methods and implement when needed.
 *
 * @author William Angola
 */
public interface ScreenSpec {

  /**
   * Gets screen resolution (Currently never used).
   *
   * @return resolution.
   */
  String getResolution();

  /**
   * Gets screen refresh rate (Currently never used).
   *
   * @return refreshRate.
   */
  int getRefreshRate();

  /**
   * Gets screen response time (Currently never used).
   *
   * @return responseTime.
   */
  int getResponseTime();

}

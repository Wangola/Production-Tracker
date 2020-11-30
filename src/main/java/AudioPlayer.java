/**
 * Room for future implementation currently never used. Allows for the extension of products with
 * Audio related formats while implementing MultimediaControl. A product can have audio player
 * formats.
 *
 * @author William Angola
 */
public class AudioPlayer extends Product implements MultimediaControl {

  //Fields
  /**
   * Supported audio formats for a specific product.
   */
  private final String supportedAudioFormats;

  /**
   * Supported playlist format for a specific product.
   */
  private final String supportedPlaylistFormats;

  /**
   * AudioPlayer constructor which call's its parent constructor in Product while assigning values
   * to formats (Currently never used).
   *
   * @param name                     Product's name.
   * @param manufacturer             Product's manufacturer.
   * @param supportedAudioFormats    Product's supported audio formats.
   * @param supportedPlaylistFormats Product's supported playlist formats.
   */
  AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);

    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;

  }

  /**
   * Display's the superclass Product toString but adds the two supported formats.
   *
   * @return Product's toString with supported formats.
   */
  public String toString() {
    return "Name: " + getName() + "\n" + "Manufacturer: " + getManufacturer() + "\n" + "Type: "
        + getType().code + "\n"
        + "Supported Audio Formats: " + supportedAudioFormats + "\n"
        + "Supported Playlist Formats: " + supportedPlaylistFormats;
  }

  /**
   * Output's playing.
   */
  @Override
  public void play() {
    System.out.println("Playing");
  }

  /**
   * Outputs stopping.
   */
  @Override
  public void stop() {
    System.out.println("Stopping");
  }

  /**
   * Outputs previous.
   */
  @Override
  public void previous() {
    System.out.println("Previous");
  }

  /**
   * Outputs next.
   */
  @Override
  public void next() {
    System.out.println("Next");
  }
}

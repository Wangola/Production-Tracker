public class AudioPlayer extends Product implements MultimediaControl {


  //Fields
  private final String supportedAudioFormats;
  private final String supportedPlaylistFormats;

  //Constructor
  AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);

    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;

  }

  //toString
  public String toString() {
    return "Name: " + getName() + "\n" + "Manufacturer: " + getManufacturer() + "\n" + "Type: "
        + getType().code + "\n" +
        "Supported Audio Formats: " + supportedAudioFormats + "\n" + "Supported Playlist Formats: "
        + supportedPlaylistFormats;
  }

  //Setters (Never Used)
//  @Override
//  public void play() {
//    System.out.println("Playing");
//  }
//
//  @Override
//  public void stop() {
//    System.out.println("Stopping");
//  }
//
//  @Override
//  public void previous() {
//    System.out.println("Previous");
//  }
//
//  @Override
//  public void next() {
//    System.out.println("Next");
//  }
}

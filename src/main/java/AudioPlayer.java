public class AudioPlayer extends Product implements MultimediaControl {

  //Fields
  public String SupportedAudioFormats;
  public String SupportedPlaylistFormats;

  //Constructor
  AudioPlayer(String name, String manufacturer, String supportedAudioFormats, String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);

    this.Name = name;
    this.Manufacturer = manufacturer;

    this.SupportedAudioFormats = supportedAudioFormats;
    this.SupportedPlaylistFormats = supportedPlaylistFormats;

  }

  //toString
  public String toString() {
    return "Name: " + Name + "\n" + "Manufacturer: " + Manufacturer + "\n" + "Type: " + Type.code + "\n" +
        "Supported Audio Formats: " + SupportedAudioFormats + "\n" + "Supported Playlist Formats: " + SupportedPlaylistFormats;
  }

  //Setters
  @Override
  public void play() {
    System.out.println("Playing");
  }

  @Override
  public void stop() {
    System.out.println("Stopping");
  }

  @Override
  public void previous() {
    System.out.println("Previous");
  }

  @Override
  public void next() {
    System.out.println("Next");
  }
}

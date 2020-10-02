public enum ItemType {
  //Creating new objects with one parameter
  AU("AUDIO"),
  VI("VISUAL"),
  AM("AUDIO_MOBILE"),
  VM("VISUAL_MOBILE");

  public final String code;

  //Constructor
  ItemType(String code) {
    this.code = code;
  }

}



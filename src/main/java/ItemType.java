public enum ItemType {
  //Creating new objects with one parameter
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIO_MOBILE("AM"),
  VISUAL_MOBILE("VM");

  //Field
  public final String code;

  //Constructor
  ItemType(String code) {
    this.code = code;
  }


}



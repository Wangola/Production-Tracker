public enum ItemType {
  //Creating new objects with one parameter
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIOMOBILE("AM"),
  VISUALMOBILE("VM");

  //Field
  public final String code;

  //Constructor
  ItemType(String code) {
    this.code = code;
  }

}


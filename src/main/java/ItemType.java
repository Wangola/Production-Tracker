/**
 * Allows any class access these predefined enums designed for products. A product can have an
 * ItemType.
 *
 * @author William Angola
 */
public enum ItemType {
  //Creating new objects with one parameter
  /**
   * Object which holds predefined constant AU.
   */
  AUDIO("AU"),

  /**
   * Object which holds predefined constant VI.
   */
  VISUAL("VI"),

  /**
   * Object which holds predefined constant AM.
   */
  AUDIO_MOBILE("AM"),

  /**
   * Object which holds predefined constant WM.
   */
  VISUAL_MOBILE("VM");

  //Field
  /**
   * The constant value of specified object.
   */
  public final String code;

  /**
   * ItemType constructor which implements predefined constants in objects into code.
   *
   * @param code Enum code.
   */
  ItemType(String code) {
    this.code = code;
  }


}



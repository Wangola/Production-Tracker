/**
 * Represents a product created by employee. An employee can create multiple products when Widget
 * class extends product and interface shares methods due to abstraction.
 *
 * @author William Angola
 */
public abstract class Product implements Item {

  // NOTES------------------------------------------------------------------------------------------

  // Abstract are used to extend classes that will be somewhat related
  // Object is an instance of a class
  // Class that is not abstract is called a concrete class

  // NOTES------------------------------------------------------------------------------------------

  // Fields
  /**
   * The product id.
   */
  private int id;

  /**
   * The product type.
   */
  private final ItemType type;

  /**
   * The product manufacturer.
   */
  private String manufacturer;

  /**
   * The product name.
   */
  private String name;

  /**
   * Product constructor which implements given product info.
   *
   * @param name         Product name.
   * @param manufacturer Product manufacturer.
   * @param type         Product type.
   */
  Product(String name, String manufacturer, ItemType type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * Overloaded product constructor which implements all given product info.
   *
   * @param id           Product id.
   * @param name         Product name.
   * @param manufacturer Product manufacturer.
   * @param type         Product type.
   */
  Product(int id, String name, String manufacturer, ItemType type) {
    this.id = id;
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * Generates product format for GUI.
   *
   * @return Product info formatted.
   */
  // toString (returns data)
  public String toString() {
    return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: " + type;
  }

  /**
   * Gets product id.
   *
   * @return Id.
   */
  public int getId() {
    return id;
  }

  /**
   * Gets product type.
   *
   * @return Type.
   */
  public ItemType getType() {
    return type;
  }

  /**
   * Gets product manufacturer.
   *
   * @return Manufacturer.
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * Gets product name.
   *
   * @return Name.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets this products name to new name.
   *
   * @param name New product name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets this product manufacturer to new manufacturer.
   *
   * @param manufacturer New product manufacturer.
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * Sets this product id to new id (Currently never used).
   *
   * @param id New product id.
   */
  public void setId(int id) {
    this.id = id;
  }

}

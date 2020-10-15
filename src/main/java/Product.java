public abstract class Product implements Item {

  /*Notes
  // Abstract are used to extend classes that will be somewhat related
  // Object is an instance of a class
  // Class that is not abstract is called a concrete class
  */

  // Fields
  public int Id;
  public ItemType Type;
  public String Manufacturer;
  public String Name;

  // Constructor
  Product(String name, String manufacturer, ItemType type) {
    this.Name = name;
    this.Manufacturer = manufacturer;
    this.Type = type;
  }

  // toString (returns data)
  public String toString() {
    return "Name: " + Name + "\n" + "Manufacturer: " + Manufacturer + "\n" + "Type: " + Type.code;
  }

  // Completing methods from interface Item (setters and getters)
  // Getters
  public int getId() {
    return Id;
  }

  public String getManufacturer() {
    return Manufacturer;
  }

  public String getName() {
    return Name;
  }

  //Setters
  public void setName(String name) {
    this.Name = name;
  }

  public void setManufacturer(String manufacturer) {
    this.Manufacturer = manufacturer;
  }

}
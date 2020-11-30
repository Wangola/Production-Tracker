/**
 * Placeholder concrete class that extends abstract product class to have product's passed through.
 *
 * @author William Angola
 */
public class Widget extends Product {

  /**
   * Widget constructor which uses its parent constructor in Product.
   *
   * @param id           Product id.
   * @param name         Product name.
   * @param manufacturer Product manufacturer.
   * @param type         Product type.
   */
  Widget(int id, String name, String manufacturer, ItemType type) {
    super(id, name, manufacturer, type);
  }

  // NOTES-----------------------------------------------------------------------------------------
  // Was under the product method with the three string in parameters
  // (Product(String, String, String))
  /*public String Widget(String name, String manufacturer, String type) {
    return "Name: " + Name + "\n" + "Manufacturer: " + Manufacturer + "\n" + "Type: " + Type;
  }*/
  // NOTES-----------------------------------------------------------------------------------------
}


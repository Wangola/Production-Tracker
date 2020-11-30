import java.sql.Timestamp;

/**
 * Represents a production recorded by employee. An employee can record multiple production records
 *
 * @author William Angola
 */
public class ProductionRecord {

  //Fields
  /**
   * The production number.
   */
  private int productionNumber = 0;

  /**
   * The product id.
   */
  private int productID;

  /**
   * The production serial number.
   */
  private String serialNumber;

  /**
   * The record date produced.
   */
  private Timestamp dateProduced;


  /**
   * ProductionRecord constructor which implements given production info.
   *
   * @param productionNumber Production number.
   * @param productID        Product id.
   * @param serialNumber     Production serial number
   * @param dateProduced     Date recorded.
   */
  ProductionRecord(int productionNumber, int productID, String serialNumber,
      Timestamp dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }


  /**
   * Overloaded productionRecord constructor which implements a product object and number of items.
   *
   * @param product     Product object.
   * @param numbOfItems Current number of items for specified product.
   */
  ProductionRecord(Product product, int numbOfItems) {
    dateProduced = new Timestamp(System.currentTimeMillis());

    // Gets ID of current selected Item
    productID = product.getId();

    // Unique Serial number
    serialNumber = product.getManufacturer().substring(0, 3) + product.getType().code + String
        .format("%05d", numbOfItems);
  }


  /**
   * Generates productionLog format with specified product name by accessing product database and
   * comparing productID's.
   *
   * @return ProductionRecord info formatted.
   */
  // toString()
  public String toString() {
    // Created object of controller class to access dataBaseNames to print out name in toString
    // textArea.
    Controller name = new Controller();

    Widget productName = name.getDataBaseNames(productID);

    return "Prod. Num: " + productionNumber + " Product Name: " + productName.getName()
        + " Serial Num: "
        + serialNumber
        + " Date: " + dateProduced + "\n";
  }

  /**
   * Gets production number (Currently never used).
   *
   * @return productionNumber.
   */
  int getProductionNumber() {
    return productionNumber;
  }

  /**
   * Gets product id.
   *
   * @return productID.
   */
  int getProductID() {
    return productID;
  }

  /**
   * Gets serial number.
   *
   * @return serialNumber.
   */
  String getSerialNumber() {
    return serialNumber;
  }

  /**
   * Gets date produced.
   *
   * @return dateProduced.
   */
  Timestamp getDateProduced() {
    return dateProduced;
  }

  /**
   * Sets this production number to new production number (Currently never used).
   *
   * @param productionNumber New production number.
   */
  void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }


  /**
   * Sets this product id to new product id (Currently never used).
   *
   * @param productID New product id.
   */
  void setProductID(int productID) {
    this.productID = productID;
  }


  /**
   * Sets this serial number to new serial number (Currently never used).
   *
   * @param serialNumber New serial number.
   */
  void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }


  /**
   * Sets this date produced to new date produced (Currently never used).
   *
   * @param dateProduced New date produced.
   */
  void setDateProduced(Timestamp dateProduced) {
    this.dateProduced = new Timestamp(dateProduced.getTime());
  }

}

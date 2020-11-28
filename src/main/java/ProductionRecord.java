import java.sql.Timestamp;

public class ProductionRecord {

  //Fields
  private int productionNumber = 0;
  // Changed int ProductID to a Generic <T> to accept any Type to be declared later
  private final int productID;
  private final String serialNumber;
  private final Timestamp dateProduced;

  // Constructor (Never Used)
//  ProductionRecord(int productID) {
//    productionNumber = 0;
//    serialNumber = "0";
//    this.productID = productID;
//    dateProduced = new Timestamp(System.currentTimeMillis());
//  }

  // Overloaded Constructor (not used because of new overloaded constructor that accepts product below)
  ProductionRecord(int productionNumber, int productID, String serialNumber,
      Timestamp dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }


  // Overloaded Constructor that accepts Product object and int which holds number of items
  ProductionRecord(Product product, int numbOfItems) {
    //productionNumber = productionNumber+1;
    dateProduced = new Timestamp(System.currentTimeMillis());

    // Gives unsafe warning when using this type of assigning (but outputs name of
    // product in place of productID

    // Gets ID of current selected Item
    productID = product.getId();

    // Unique Serial number
    serialNumber = product.getManufacturer().substring(0, 3) + product.getType().code + String
        .format("%05d", numbOfItems);
  }


  // toString()
  public String toString() {
    // Created object of controller class to access dataBaseNames to print out name in toString
    // textArea.
    Controller name = new Controller();
    // Pulls set password from text file

    Widget productName = name.getDataBaseNames(productID);

    return "Prod. Num: " + productionNumber + " Product Name: " + productName.getName()
        + " Serial Num: "
        + serialNumber
        + " Date: " + dateProduced + "\n";
  }

  // Start of Accessors

  //Getters (Never Used)
//  int getProductionNumber() {
//    return productionNumber;
//  }

  int getProductID() {
    return productID;
  }

  String getSerialNumber() {
    return serialNumber;
  }

  Timestamp getDateProduced() {
    return dateProduced;
  }

  //Setters (Never Used)
//  void setProductionNumber(int productionNumber) {
//    this.productionNumber = productionNumber;
//  }

  // (Never Used)
//  void setProductID(int productID) {
//    this.productID = productID;
//  }

  // (Never Used)
//  void setSerialNumber(String serialNumber) {
//    this.serialNumber = serialNumber;
//  }

  // (Never Used)
//  void setDateProduced(Timestamp dateProduced) {
//    this.dateProduced = dateProduced;
//  }

  // End of Accessors

}

import java.util.Date;

public class ProductionRecord <T> {

  //Fields
  int productionNumber = 0;
  // Changed int ProductID to a Generic <T> to accept any Type to be declared later
  T productID;
  String serialNumber;
  Date dateProduced;

  // Constructor
  ProductionRecord(int productID){
    productionNumber = 0;
    serialNumber = "0";
    dateProduced = new Date();
  }

  // Overloaded Constructor (not used because of new overloaded constructor that accepts product below)
  ProductionRecord(int productionNumber, T productID, String serialNumber, Date dateProduced){
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

    // Week 10 Complete (serial number needs update)------------------------------------------------------------------------------------------------------------------
    // Overloaded Constructor that accepts Product and int which holds number of items
    ProductionRecord(Product product, int numbOfItems){
      productionNumber = productionNumber+1;
      dateProduced = new Date();

      // Gives unsafe warning when using this type of assigning
      // productID = (T) product.getName();

      // Unique Serial number
      serialNumber = product.getManufacturer().substring(0,3) + product.getType().code + "00" + numbOfItems++;
    }
    // Week 10 Complete (serial number needs update)------------------------------------------------------------------------------------------------------------------



  // toString()
  public String toString() {
    return "Prod. Num: " + productionNumber + " " + "Product ID: " + productID  + " " + "Serial Num: " + serialNumber
        + " " + "Date: " + dateProduced + "\n";
  }
  
  

  // Start of Accessors

  //Getters
  int getProductionNumber(){
    return productionNumber;
  }

  T getProductID(){
    return productID;
  }

  String getSerialNumber(){
    return serialNumber;
  }

  Date getDateProduced(){
    return dateProduced;
  }

  //Setters
  void setProductionNumber(int productionNumber){
    this.productionNumber = productionNumber;
  }

  void setProductID(T productID){
    this.productID = productID;
  }

  void setSerialNumber(String serialNumber){
    this.serialNumber = serialNumber;
  }

  void setDateProduced(Date dateProduced){
    this.dateProduced = dateProduced;
  }

  // End of Accessors

}

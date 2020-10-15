import java.util.Date;
import javax.xml.crypto.Data;

public class ProductionRecord {

  //Fields
  int ProductionNumber;
  int ProductID;
  String SerialNumber;
  Date DateProduced;

  // Constructor
  ProductionRecord(int productID){
    ProductionNumber = 0;
    SerialNumber = "0";
    DateProduced = new Date();
  }

  // Overloaded Constructor
  ProductionRecord(int productionNumber, int productID, String serialNumber, Date dateProduced){
    this.ProductionNumber = productionNumber;
    this.ProductID = productID;
    this.SerialNumber = serialNumber;
    this.DateProduced = dateProduced;
  }

  // toString()
  public String toString() {
    return "Prod. Num: " + ProductionNumber + " " + "Product ID: " + ProductID  + " " + "Serial Num: " + SerialNumber
        + " " + "Date: " + DateProduced;
  }
  
  

  // Start of Accessors

  //Getters
  int getProductionNumber(){
    return ProductionNumber;
  }

  int getProductID(){
    return ProductID;
  }

  String getSerialNumber(){
    return SerialNumber;
  }

  Date getDateProduced(){
    return DateProduced;
  }

  //Setters
  void setProductionNumber(int productionNumber){
    this.ProductionNumber = productionNumber;
  }

  void setProductID(int productID){
    this.ProductID = productID;
  }

  void setSerialNumber(String serialNumber){
    this.SerialNumber = serialNumber;
  }

  void setDateProduced(Date dateProduced){
    this.DateProduced = dateProduced;
  }

  // End of Accessors

}

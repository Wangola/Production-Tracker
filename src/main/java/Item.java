public interface Item {

  // Used when a class is loose related to one or another (implement when classes aren't related)
  // Interface will tell other class "what" to do but not "how" (contract)

  // interface creates the variable and has other classes initialize them

  int getId();

  // Not Used
  //void setName(String name);
  String getName();

  // Not Used
  //void setManufacturer(String manufacturer);
  String getManufacturer();

}

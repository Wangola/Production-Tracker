/**
 * Item interface identify methods (no bodies) that are implemented in abstract Product class. These
 * methods are used to achieve total abstraction.
 *
 * @author William Angola
 */
public interface Item {

  // NOTES------------------------------------------------------------------------------------------
  // Used when a class is loose related to one or another (implement when classes aren't related)
  // Interface will tell other class "what" to do but not "how" (contract)

  // interface creates the variable and has other concrete/abstract classes initialize them
  // NOTES------------------------------------------------------------------------------------------

  /**
   * Gets product id.
   *
   * @return Id.
   */
  int getId();

  /**
   * Sets product name (Currently never used).
   *
   * @param name Product name.
   */
  void setName(String name);

  /**
   * Gets product name.
   *
   * @return Name.
   */
  String getName();

  /**
   * Sets product manufacturer (Currently never used).
   *
   * @param manufacturer Product manufacturer.
   */
  void setManufacturer(String manufacturer);

  /**
   * Gets product manufacturer.
   *
   * @return manufacturer.
   */
  String getManufacturer();

}

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Employee {

  // WEEK 13 STRING, REGULAR EXPRESSIONS

  // Fields
  private final StringBuilder name = new StringBuilder();
  private String userName;
  private final String password;
  private String email;


  // Constructor
  Employee(String name, String password) {

    // Check name pattern == true
    if (checkName(name)) {

      // Set name
      this.name.append(name);

      // Call setUserName/Email to set the userName and email according to correct name given
      setUserName(name);
      setEmail(name);
    } else {

      // Set default name
      this.name.append(name);

      // Set default values if false
      this.userName = "default";
      this.email = "user@oracleacademy.Test";
    }

    // Check password pattern == true
    if (isValidPassword(password)) {

      // Set password
      this.password = password;
    } else {

      // Set default password
      this.password = "pw";
    }

  }

  public String toString() {
    return "Employee Details" + "\n"
        + "Name : " + this.name + "\n"
        + "Username : " + this.userName + "\n"
        + "Email : " + this.email + "\n"
        + "Initial Password : " + this.password;
  }

  // Methods

  // Once named is checked it sets name to an specific patter here
  private void setUserName(String name) {

    // Array to hold first name and last name
    String[] title = name.split(" ");

    // Sets userName (to the first initial of the first name and then last name all lower case)
    this.userName = title[0].substring(0, 1).toLowerCase() + title[1].toLowerCase();
  }

  // Check name using regex to see if it has a white space
  private boolean checkName(String name) {

    // (\w any word + 1 or more) (\s any whiteSpace) (\w any word + 1 or more)
    String pattern = "[a-zA-Z]+\\s[a-zA-Z]+";

    // Comparing pattern and name given
    Pattern pt = Pattern.compile(pattern);
    Matcher mt = pt.matcher(name);

    // Use regex to check if user name has any spaces: return true else return false (result = mt.matches())
    return mt.matches();
  }

  // Once named is checked it sets email to an specific patter here
  private void setEmail(String name) {

    // Array to hold first name and last name
    String[] title = name.split(" ");

    // Sets email (to first name, then a period, then last name (all lower case) then @oracleacademy.Test)
    this.email = title[0].toLowerCase() + "." + title[1].toLowerCase() + "@oracleacademy.Test";
  }

  // Check password using regex to see if it has (lower case, upper case, and special character)
  private boolean isValidPassword(String password) {

    // Used lookahead groups for password pattern
    String pattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%])[a-zA-Z!@#$%]+";

    // Comparing pattern and name given
    Pattern pt = Pattern.compile(pattern);
    Matcher mt = pt.matcher(password);

    // Use regex to check if password is correct: return true else return false (result = mt.matches();)
    return mt.matches();
  }

  public StringBuilder getName() {
    return this.name;
  }

  public String getPassword() {
    return this.password;
  }

  public String getUserName() {
    return this.userName;
  }

  public String getEmail() {
    return this.email;
  }

}

package ticketminer;

import java.util.Scanner;

/** Represents a TicketMiner user. */
public abstract class User {
  private int userId;
  private String firstName;
  private String lastName;
  private String userName;
  private String password;
  private String userType;
  private boolean canEdit;
  private Scanner keyboard;

  /**
   * Creates a user.
   *
   * @param userId unique user id
   * @param firstName user's first name
   * @param lastName user's last name
   * @param userName user's login username
   * @param password user's login password
   * @param userType user's role
   * @param keyboard scanner used for menu input
   */
  public User(
      int userId,
      String firstName,
      String lastName,
      String userName,
      String password,
      String userType,
      Scanner keyboard) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.password = password;
    this.userType = userType;
    this.keyboard = keyboard;
  }

  /** Displays the menu for this user type. */
  public abstract void userMenu();

  /** Prints a menu navigation message. */
  public void back() {
    System.out.println("Going back.");
  }

  /**
   * Returns the user's full name.
   *
   * @return first and last name
   */
  public String getFullName() {
    return firstName + " " + lastName;
  }

  /**
   * Checks whether this user's id matches the given id.
   *
   * @param id id to compare
   * @return true when the ids match
   */
  public boolean matchesId(int id) {
    return userId == id;
  }

  /**
   * Checks whether this user's username matches the given username.
   *
   * @param username username to compare
   * @return true when the usernames match
   */
  public boolean matchesUsername(String username) {
    return userName.equalsIgnoreCase(username);
  }

  /**
   * Checks whether this user's full name matches the given name.
   *
   * @param name name to compare
   * @return true when the names match
   */
  public boolean matchesName(String name) {
    return getFullName().equalsIgnoreCase(name);
  }

  /**
   * Checks whether the given password matches this user's password.
   *
   * @param password password to compare
   * @return true when the passwords match
   */
  public boolean checkPassword(String password) {
    return this.password.equals(password);
  }

  /**
   * Returns the user's printable details.
   *
   * @return user details
   */
  @Override
  public String toString() {
    return "ID: "
        + userId
        + ", Name: "
        + firstName
        + " "
        + lastName
        + ", Username: "
        + userName
        + ", Password: "
        + password
        + ", Type: "
        + userType;
  }

  /**
   * Returns the user's id.
   *
   * @return user id
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Returns the user's first name.
   *
   * @return first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Returns the user's last name.
   *
   * @return last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Returns the user's username.
   *
   * @return username
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Returns the user's password.
   *
   * @return password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Returns the user's role.
   *
   * @return user type
   */
  public String getUserType() {
    return userType;
  }

  /**
   * Returns whether this user can edit records.
   *
   * @return true when the user can edit
   */
  public boolean isCanEdit() {
    return canEdit;
  }

  /**
   * Returns the scanner used for user input.
   *
   * @return keyboard scanner
   */
  public Scanner getKeyboard() {
    return keyboard;
  }

  /**
   * Sets the user's id.
   *
   * @param userId user id
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }

  /**
   * Sets the user's first name.
   *
   * @param firstName first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Sets the user's last name.
   *
   * @param lastName last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Sets the user's username.
   *
   * @param userName username
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * Sets the user's password.
   *
   * @param password password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Sets the user's role.
   *
   * @param userType user type
   */
  public void setUserType(String userType) {
    this.userType = userType;
  }

  /**
   * Sets whether this user can edit records.
   *
   * @param canEdit true when the user can edit
   */
  public void setCanEdit(boolean canEdit) {
    this.canEdit = canEdit;
  }

  /**
   * Sets the scanner used for user input.
   *
   * @param keyboard keyboard scanner
   */
  public void setKeyboard(Scanner keyboard) {
    this.keyboard = keyboard;
  }
}

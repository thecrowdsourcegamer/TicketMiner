package ticketminer;

import java.util.Scanner;

/**
 * Represents a customer user.
 */
public class Customer extends User {
  private double moneyAvailable;
  private boolean membership;
  private int concertsPurchased;

  /**
   * Creates a customer user.
   *
   * @param userId unique user id
   * @param firstName customer's first name
   * @param lastName customer's last name
   * @param userName customer's login username
   * @param password customer's login password
   * @param userType customer's role
   * @param keyboard scanner used for menu input
   * @param moneyAvailable money available for purchases
   * @param membership true when the customer has a membership
   * @param concertsPurchased number of concerts purchased
   */
  public Customer(
      int userId,
      String firstName,
      String lastName,
      String userName,
      String password,
      String userType,
      Scanner keyboard,
      double moneyAvailable,
      boolean membership,
      int concertsPurchased) {
    super(userId, firstName, lastName, userName, password, userType, keyboard);
    this.moneyAvailable = moneyAvailable;
    this.membership = membership;
    this.concertsPurchased = concertsPurchased;
  }

  /**
   * Returns the customer's available money.
   *
   * @return money available
   */
  public double getMoneyAvailable() {
    return moneyAvailable;
  }

  /**
   * Returns whether the customer has a membership.
   *
   * @return true when the customer has a membership
   */
  public boolean isMembership() {
    return membership;
  }

  /**
   * Returns how many concerts the customer purchased.
   *
   * @return number of concerts purchased
   */
  public int getConcertsPurchased() {
    return concertsPurchased;
  }

  /**
   * Sets whether the customer has a membership.
   *
   * @param membership true when the customer has a membership
   */
  public void setMembership(boolean membership) {
    this.membership = membership;
  }

  /**
   * Sets the customer's available money.
   *
   * @param moneyAvailable money available
   */
  public void setMoneyAvailable(double moneyAvailable) {
    this.moneyAvailable = moneyAvailable;
  }

  /**
   * Sets how many concerts the customer purchased.
   *
   * @param concertsPurchased number of concerts purchased
   */
  public void setConcertsPurchased(int concertsPurchased) {
    this.concertsPurchased = concertsPurchased;
  }

  /**
   * Returns the customer's printable details.
   *
   * @return customer details
   */
  @Override
  public String toString() {
    return super.toString()
        + ", Money Available: " + moneyAvailable
        + ", Membership: " + membership
        + ", Concerts Purchased: " + concertsPurchased;
  }

  /**
   * Displays the customer menu.
   */
  @Override
  public void userMenu() {
    String userInput = "";

    while (!userInput.equals("2")) {
      System.out.println("\nCustomer Menu");
      System.out.println("1: View Profile");
      System.out.println("2: Back");

      userInput = getKeyboard().nextLine().trim();

      switch (userInput) {
        case "1" -> {
          System.out.println(this);
          RunTicketMiner.log(getUserName() + " viewed customer profile");
        }
        case "2" -> back();
        default -> System.out.println("Invalid option entered.");
      }
    }
  }
}

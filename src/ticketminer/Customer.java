package ticketminer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

/** Represents a customer user. */
public class Customer extends User {
  private double moneyAvailable;
  private boolean membership;
  private int concertsPurchased;
  private String orders = "----ORDERS----\n"; 

  private static final double MEMBERSHIPDISCOUNT = 10;
  private static final double SALETAX = 8.25;

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
        + ", Money Available: "
        + moneyAvailable
        + ", Membership: "
        + membership
        + ", Concerts Purchased: "
        + concertsPurchased;
  }

  /** Displays the customer menu. */
  @Override
  public void userMenu() {
    String userInput = "";

    while (!userInput.equals("4")) {
      System.out.println("\nCustomer Menu");
      System.out.println("1: View Profile");
      System.out.println("2: Purchase Tickets");
      System.out.println("3: Print Order Summary");
      System.out.println("4: Back");

      userInput = getKeyboard().nextLine().trim();

      switch (userInput) {
        case "1" -> {
          System.out.println(this);
          RunTicketMiner.log(getUserName() + " viewed customer profile");
        }
        case "2" -> {
          String userInput2 = "";
          HashMap<String, double[]> cart = new HashMap<>(); 

          while (!userInput2.equals("6")) {
            System.out.println("\nPurchasing tickets Menu");
            System.out.println("1: View event menu");
            System.out.println("2: Add ticket to cart");
            System.out.println("3: Remove ticket from cart");
            System.out.println("4: View cart");
            System.out.println("5: Checkout");
            System.out.println("6: Back");

            userInput2 = getKeyboard().nextLine().trim();

            switch (userInput2) {
              case "1" -> RunTicketMiner.viewEventMenu(getKeyboard());
              case "2" -> {
                System.out.println("Enter event ID or name to add ticket to cart, or 'Back' to go back:");
                String eventInput = getKeyboard().nextLine().trim();

                if (eventInput.equalsIgnoreCase("Back")) {
                  back();
                } else {
                  Event event = RunTicketMiner.findEvent(eventInput);

                  if (event == null) {
                    System.out.println("Event not found.");
                  } else {
                    System.out.println("Event found: " + event.getEventName() + " on " + event.getDate());
                    System.out.println("Select ticket tier:");
                    System.out.println("1: VIP      - $" + event.getVipPrice());
                    System.out.println("2: Gold     - $" + event.getGoldPrice());
                    System.out.println("3: Silver   - $" + event.getSilverPrice());
                    System.out.println("4: Bronze   - $" + event.getBronzePrice());
                    System.out.println("5: General  - $" + event.getGeneralAdmissionPrice());
                    System.out.println("6: Back");

                    String tierInput = getKeyboard().nextLine().trim();
                    String tierName = "";
                    double tierPrice = 0;
                    int tierSold = 0;
                    int tierCapacity = event.getTotalCapacity();

                    switch (tierInput) {
                      case "1" -> { tierName = "VIP";     tierPrice = event.getVipPrice();               tierSold = event.getVipSold(); }
                      case "2" -> { tierName = "Gold";    tierPrice = event.getGoldPrice();              tierSold = event.getGoldSold(); }
                      case "3" -> { tierName = "Silver";  tierPrice = event.getSilverPrice();            tierSold = event.getSilverSold(); }
                      case "4" -> { tierName = "Bronze";  tierPrice = event.getBronzePrice();            tierSold = event.getBronzeSold(); }
                      case "5" -> { tierName = "General"; tierPrice = event.getGeneralAdmissionPrice();  tierSold = event.getGeneralSold(); }
                      case "6" -> { back(); }
                      default  -> System.out.println("Invalid tier selected.");
                    }

                    if (!tierName.isEmpty()) {
                      int availableTickets = tierCapacity - tierSold;

                      if (availableTickets <= 0) {
                        System.out.println("Sorry, no tickets available for " + tierName + " tier.");
                      } else {
                        System.out.println("Available tickets: " + availableTickets);
                        int quantity = RunTicketMiner.readInt(getKeyboard(), "Enter number of tickets to add to cart:");

                        if (quantity <= 0) {
                          System.out.println("Invalid quantity.");
                        } else if (quantity > availableTickets) {
                          System.out.println("Not enough tickets available. Only " + availableTickets + " left.");
                        } else {
                          String cartKey = event.getEventId() + "-" + tierName;
                    
                          if (cart.containsKey(cartKey)) {
                            cart.get(cartKey)[0] += quantity;
                          } else {
                            cart.put(cartKey, new double[]{quantity, tierPrice});
                          }
                          System.out.println(quantity + " x " + tierName + " ticket(s) for " + event.getEventName() + " added to cart.");
                          RunTicketMiner.log(getUserName() + " added " + quantity + " " + tierName + " ticket(s) for event " + event.getEventId() + " to cart.");
                        }
                      }
                    }
                  }
                }
              }
              case "3" -> {
                if (cart.isEmpty()) {
                  System.out.println("Your cart is empty.");
                } else {
                  System.out.println("Enter the cart item to remove (event ID - tier, e.g. 1-VIP), or 'Back' to go back:");
                  String removeInput = getKeyboard().nextLine().trim();

                  if (removeInput.equalsIgnoreCase("Back")) {
                    back();
                  } else if (cart.containsKey(removeInput)) {
                    cart.remove(removeInput);
                    System.out.println("Item removed from cart.");
                    RunTicketMiner.log(getUserName() + " removed item " + removeInput + " from cart.");
                  } else {
                    System.out.println("Item not found in cart.");
                  }
                }
              }
              case "4" -> {
                if (cart.isEmpty()) {
                  System.out.println("Your cart is empty.");
                } else {
                  System.out.println("\n--- Cart ---");
                  double total = 0;
                  for (String key : cart.keySet()) {
                    int qty   = (int) cart.get(key)[0];
                    double price = cart.get(key)[1];
                    double subtotal = qty * price;
                    total += subtotal;
                    System.out.println("Item: " + key + " | Qty: " + qty + " | Price: $" + price + " | Subtotal: $" + subtotal);
                  }
                  System.out.println("Total: $" + total);
                }
              }
              case "5" -> {
                if (cart.isEmpty()) {
                  System.out.println("Your cart is empty. Nothing to checkout.");
                } else {
                  // Calculate total
                  double total = 0;
                  for (String key : cart.keySet()) {
                    total += (double) cart.get(key)[0] * cart.get(key)[1];
                  }

                  if (isMembership()){
                    System.out.println("Membership discount applied");
                    total = total * (1 - MEMBERSHIPDISCOUNT/100);
                  }

                  System.out.println("Total cost: $" + total);
                  double costPlusTax = total * (1 + SALETAX/100);
                  System.out.println("Total with taxes: $" + costPlusTax);
                  System.out.println("Your balance: $" + getMoneyAvailable());

                  if (getMoneyAvailable() < costPlusTax) {
                    System.out.println("Insufficient balance. Purchase cancelled.");
                  } else {
                    System.out.println("Confirm purchase? (yes/no)");
                    String confirm = getKeyboard().nextLine().trim();

                    if (confirm.equalsIgnoreCase("yes")) {
                      orders += "Order:\n";
                      orders += "Balance before: "+ getMoneyAvailable()+ "\n";
                      setMoneyAvailable(getMoneyAvailable() - costPlusTax);

                      // Update sold counts on each event and add to order history
          
                      for (String key : cart.keySet()) {
                        String[] parts = key.split("-");
                        Event event = RunTicketMiner.findEvent(parts[0]);
                        int qty = (int) cart.get(key)[0];

                        if (event != null) {
                          switch (parts[1]) {
                            case "VIP"     -> event.setVipSold(event.getVipSold() + qty);
                            case "Gold"    -> event.setGoldSold(event.getGoldSold() + qty);
                            case "Silver"  -> event.setSilverSold(event.getSilverSold() + qty);
                            case "Bronze"  -> event.setBronzeSold(event.getBronzeSold() + qty);
                            case "General" -> event.setGeneralSold(event.getGeneralSold() + qty);
                          }
                        }
                        
                        double price = cart.get(key)[1];
                        double subtotal = qty * price;
                        orders += "Item: " + key + " | Qty: " + qty + " | Price: $" + price + " | Subtotal: $" + subtotal+"\n";
                      }
                      orders += "Total cost: $" + total +"\n";
                      orders += "Total with taxes: $" + costPlusTax + "\n";
                      orders += "Balance after: "+ getMoneyAvailable()+ "\n\n";

                      cart.clear();
                      System.out.println("Purchase successful! Remaining balance: $" + getMoneyAvailable());
                      RunTicketMiner.log(getUserName() + " completed purchase. Total charged: $" + costPlusTax);
                    } else {
                      System.out.println("Purchase cancelled.");
                    }
                  }
                }
              }
              case "6" -> back();
              default -> System.out.println("Invalid option entered.");
            }
          }
        }
        case "3" -> {
          System.out.println(orders);
          try {
              File dir = new File("../orderReceipts");
              dir.mkdirs();
              Path path = Paths.get("../orderReceipts/" + getUserId() + ".txt");
              Files.writeString(path, orders, StandardCharsets.UTF_8);
              System.out.println("Receipt saved to orderReceipts/" + getUserId() + ".txt");
          } catch (IOException ex) {
              System.out.println("Error saving receipt: " + ex.getMessage());
          }
        }
        case "4" -> back();
        default -> System.out.println("Invalid option entered.");
      }
    }
  }
}

package ticketminer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/** Represents a customer user. */
public class Customer extends User {
  private static final double MEMBERSHIP_DISCOUNT = 10.0;
  private static final double SALES_TAX = 8.25;

  private double moneyAvailable;
  private boolean membership;
  private int concertsPurchased;
  private String orders = "----ORDERS----\n";

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
    Map<String, CartItem> cart = new HashMap<>();

    while (!userInput.equals("4")) {
      System.out.println("\nCustomer Menu");
      System.out.println("1: View Profile");
      System.out.println("2: Purchase Tickets");
      System.out.println("3: Print Order Summary");
      System.out.println("4: Back");

      userInput = getKeyboard().nextLine().trim();

      switch (userInput) {
        case "1" -> viewProfile();
        case "2" -> purchaseTickets(cart);
        case "3" -> printOrderSummary();
        case "4" -> back();
        default -> System.out.println("Invalid option entered.");
      }
    }
  }

  private void viewProfile() {
    System.out.println(this);
    RunTicketMiner.log(getUserName() + " viewed customer profile");
  }

  private void purchaseTickets(Map<String, CartItem> cart) {
    String input = "";

    while (!input.equals("6")) {
      System.out.println("\nPurchasing tickets Menu");
      System.out.println("1: View event menu");
      System.out.println("2: Add ticket to cart");
      System.out.println("3: Remove ticket from cart");
      System.out.println("4: View cart");
      System.out.println("5: Checkout");
      System.out.println("6: Back");

      input = getKeyboard().nextLine().trim();

      switch (input) {
        case "1" -> RunTicketMiner.viewEventMenu(getKeyboard());
        case "2" -> addTicketToCart(cart);
        case "3" -> removeTicketFromCart(cart);
        case "4" -> printCart(cart);
        case "5" -> checkout(cart);
        case "6" -> back();
        default -> System.out.println("Invalid option entered.");
      }
    }
  }

  private void addTicketToCart(Map<String, CartItem> cart) {
    System.out.println("Enter event ID or name to add ticket to cart, or 'Back' to go back:");
    String eventInput = getKeyboard().nextLine().trim();

    if (eventInput.equalsIgnoreCase("Back")) {
      back();
      return;
    }

    Event event = RunTicketMiner.findEvent(eventInput);
    if (event == null) {
      System.out.println("Event not found.");
      return;
    }

    System.out.println("Event found: " + event.getEventName() + " on " + event.getDate());
    TicketSelection ticketSelection = chooseTicketTier(event);
    if (ticketSelection == null) {
      return;
    }

    int availableTickets = availableTickets(event, cart);
    if (availableTickets == 0) {
      System.out.println("Sorry, no tickets are available for this event.");
      return;
    }

    printAvailableTickets(availableTickets);
    int quantity =
        RunTicketMiner.readInt(getKeyboard(), "Enter number of tickets to add to cart: ");

    try {
      validateTicketQuantity(event, quantity, availableTickets);
      addCartItem(cart, event, ticketSelection, quantity);
    } catch (NotEnoughTicketsException e) {
      System.out.println(e.getMessage());
    }
  }

  private TicketSelection chooseTicketTier(Event event) {
    System.out.println("Select ticket tier:");
    System.out.println("1: VIP      - $" + event.getVipPrice());
    System.out.println("2: Gold     - $" + event.getGoldPrice());
    System.out.println("3: Silver   - $" + event.getSilverPrice());
    System.out.println("4: Bronze   - $" + event.getBronzePrice());
    System.out.println("5: General  - $" + event.getGeneralAdmissionPrice());
    System.out.println("6: Back");

    String tierInput = getKeyboard().nextLine().trim();

    return switch (tierInput) {
      case "1" -> new TicketSelection("VIP", event.getVipPrice());
      case "2" -> new TicketSelection("Gold", event.getGoldPrice());
      case "3" -> new TicketSelection("Silver", event.getSilverPrice());
      case "4" -> new TicketSelection("Bronze", event.getBronzePrice());
      case "5" -> new TicketSelection("General", event.getGeneralAdmissionPrice());
      case "6" -> {
        back();
        yield null;
      }
      default -> {
        System.out.println("Invalid tier selected.");
        yield null;
      }
    };
  }

  private void addCartItem(
      Map<String, CartItem> cart, Event event, TicketSelection ticketSelection, int quantity) {
    String cartKey = cartKey(event, ticketSelection.tierName());
    CartItem cartItem = cart.get(cartKey);

    if (cartItem == null) {
      cart.put(
          cartKey,
          new CartItem(event, ticketSelection.tierName(), quantity, ticketSelection.price()));
    } else {
      cartItem.addQuantity(quantity);
    }

    System.out.println(
        quantity
            + " x "
            + ticketSelection.tierName()
            + " ticket(s) for "
            + event.getEventName()
            + " added to cart.");
    RunTicketMiner.log(
        getUserName()
            + " added "
            + quantity
            + " "
            + ticketSelection.tierName()
            + " ticket(s) for event "
            + event.getEventId()
            + " to cart.");
  }

  private void removeTicketFromCart(Map<String, CartItem> cart) {
    if (cart.isEmpty()) {
      System.out.println("Your cart is empty.");
      return;
    }

    printCart(cart);
    System.out.println("Enter the cart item to remove (event ID-tier, e.g. 1-VIP), or 'Back':");
    String removeInput = getKeyboard().nextLine().trim();

    if (removeInput.equalsIgnoreCase("Back")) {
      back();
    } else if (cart.remove(removeInput) != null) {
      System.out.println("Item removed from cart.");
      RunTicketMiner.log(getUserName() + " removed item " + removeInput + " from cart.");
    } else {
      System.out.println("Item not found in cart.");
    }
  }

  private void printCart(Map<String, CartItem> cart) {
    if (cart.isEmpty()) {
      System.out.println("Your cart is empty.");
      return;
    }

    System.out.println("\n--- Cart ---");
    for (Map.Entry<String, CartItem> entry : cart.entrySet()) {
      CartItem item = entry.getValue();
      System.out.println(
          "Item: "
              + entry.getKey()
              + " | Qty: "
              + item.quantity()
              + " | Price: $"
              + item.price()
              + " | Subtotal: $"
              + item.subtotal());
    }
    System.out.println("Subtotal: $" + subtotal(cart));
  }

  private void checkout(Map<String, CartItem> cart) {
    if (cart.isEmpty()) {
      System.out.println("Your cart is empty. Nothing to checkout.");
      return;
    }

    try {
      validateCartAvailability(cart);
      double subtotal = subtotal(cart);
      double discount = membershipDiscount(subtotal);
      double discountedSubtotal = subtotal - discount;
      double tax = discountedSubtotal * (SALES_TAX / 100.0);
      double total = discountedSubtotal + tax;

      printCheckoutSummary(subtotal, discount, tax, total);
      validateFunds(total);

      System.out.println("Confirm purchase? (yes/no)");
      String confirm = getKeyboard().nextLine().trim();

      if (confirm.equalsIgnoreCase("yes")) {
        completePurchase(cart, subtotal, discount, tax, total);
      } else {
        System.out.println("Purchase cancelled.");
      }
    } catch (InsufficientFundsException | NotEnoughTicketsException e) {
      System.out.println(e.getMessage());
    }
  }

  private void printCheckoutSummary(double subtotal, double discount, double tax, double total) {
    if (discount > 0) {
      System.out.println("Membership discount applied: $" + discount);
    }

    System.out.println("Subtotal: $" + subtotal);
    System.out.println("Tax: $" + tax);
    System.out.println("Total with taxes: $" + total);
    System.out.println("Your balance: $" + getMoneyAvailable());
  }

  private void completePurchase(
      Map<String, CartItem> cart, double subtotal, double discount, double tax, double total) {
    String confirmationNumber = confirmationNumber();
    double balanceBefore = getMoneyAvailable();

    setMoneyAvailable(balanceBefore - total);
    setConcertsPurchased(getConcertsPurchased() + totalTicketQuantity(cart));

    orders += "Confirmation Number: " + confirmationNumber + "\n";
    orders += "Balance before: $" + balanceBefore + "\n";

    for (CartItem item : cart.values()) {
      updateSoldTickets(item);
      orders += orderLine(item, confirmationNumber);
    }

    orders += "Subtotal: $" + subtotal + "\n";
    orders += "Membership discount: $" + discount + "\n";
    orders += "Tax: $" + tax + "\n";
    orders += "Total charged: $" + total + "\n";
    orders += "Balance after: $" + getMoneyAvailable() + "\n\n";

    cart.clear();
    RunTicketMiner.saveAllData();
    System.out.println("Purchase successful! Confirmation number: " + confirmationNumber);
    System.out.println("Remaining balance: $" + getMoneyAvailable());
    RunTicketMiner.log(getUserName() + " completed purchase " + confirmationNumber);
  }

  private String orderLine(CartItem item, String confirmationNumber) {
    Event event = item.event();

    return "Event Type: "
        + event.getEventType()
        + "\nEvent Name: "
        + event.getEventName()
        + "\nEvent Date: "
        + event.getDate()
        + "\nTicket Type: "
        + item.tierName()
        + "\nNumber of Tickets: "
        + item.quantity()
        + "\nTotal Price: $"
        + item.subtotal()
        + "\nConfirmation Number: "
        + confirmationNumber
        + "\n";
  }

  private void printOrderSummary() {
    System.out.println(orders);
    try {
      Path receiptDirectory = Path.of("orderReceipts");
      Files.createDirectories(receiptDirectory);
      Path receiptPath = receiptDirectory.resolve(getUserId() + ".txt");
      Files.writeString(receiptPath, orders, StandardCharsets.UTF_8);
      System.out.println("Receipt saved to " + receiptPath);
      RunTicketMiner.log(getUserName() + " printed order summary");
    } catch (IOException e) {
      System.out.println("Error saving receipt: " + e.getMessage());
    }
  }

  private void validateCartAvailability(Map<String, CartItem> cart)
      throws NotEnoughTicketsException {
    Map<Integer, Integer> quantitiesByEvent = new HashMap<>();

    for (CartItem item : cart.values()) {
      int eventId = item.event().getEventId();
      quantitiesByEvent.put(eventId, quantitiesByEvent.getOrDefault(eventId, 0) + item.quantity());
    }

    for (CartItem item : cart.values()) {
      Event event = item.event();
      int totalCapacity = event.getTotalCapacity();

      if (totalCapacity > 0
          && eventTicketsSold(event) + quantitiesByEvent.get(event.getEventId()) > totalCapacity) {
        throw new NotEnoughTicketsException(
            "Not enough tickets available for " + event.getEventName() + ".");
      }
    }
  }

  private void validateFunds(double total) throws InsufficientFundsException {
    if (getMoneyAvailable() < total) {
      throw new InsufficientFundsException("Insufficient balance. Purchase cancelled.");
    }
  }

  private void validateTicketQuantity(Event event, int quantity, int availableTickets)
      throws NotEnoughTicketsException {
    if (quantity <= 0) {
      throw new NotEnoughTicketsException("Invalid quantity.");
    }

    if (availableTickets != Integer.MAX_VALUE && quantity > availableTickets) {
      throw new NotEnoughTicketsException(
          "Not enough tickets available. Only " + availableTickets + " left.");
    }
  }

  private int availableTickets(Event event, Map<String, CartItem> cart) {
    int totalCapacity = event.getTotalCapacity();

    if (totalCapacity <= 0) {
      return Integer.MAX_VALUE;
    }

    return Math.max(0, totalCapacity - eventTicketsSold(event) - cartQuantityForEvent(cart, event));
  }

  private int cartQuantityForEvent(Map<String, CartItem> cart, Event event) {
    int quantity = 0;

    for (CartItem item : cart.values()) {
      if (item.event().getEventId() == event.getEventId()) {
        quantity += item.quantity();
      }
    }

    return quantity;
  }

  private int eventTicketsSold(Event event) {
    return event.getVipSold()
        + event.getGoldSold()
        + event.getSilverSold()
        + event.getBronzeSold()
        + event.getGeneralSold();
  }

  private int totalTicketQuantity(Map<String, CartItem> cart) {
    int quantity = 0;

    for (CartItem item : cart.values()) {
      quantity += item.quantity();
    }

    return quantity;
  }

  private void printAvailableTickets(int availableTickets) {
    if (availableTickets == Integer.MAX_VALUE) {
      System.out.println("Available tickets: capacity limit not loaded for this event.");
    } else {
      System.out.println("Available tickets: " + availableTickets);
    }
  }

  private double subtotal(Map<String, CartItem> cart) {
    double subtotal = 0;

    for (CartItem item : cart.values()) {
      subtotal += item.subtotal();
    }

    return subtotal;
  }

  private double membershipDiscount(double subtotal) {
    if (isMembership()) {
      return subtotal * (MEMBERSHIP_DISCOUNT / 100.0);
    }

    return 0;
  }

  private void updateSoldTickets(CartItem item) {
    Event event = item.event();
    int quantity = item.quantity();

    switch (item.tierName()) {
      case "VIP" -> event.setVipSold(event.getVipSold() + quantity);
      case "Gold" -> event.setGoldSold(event.getGoldSold() + quantity);
      case "Silver" -> event.setSilverSold(event.getSilverSold() + quantity);
      case "Bronze" -> event.setBronzeSold(event.getBronzeSold() + quantity);
      case "General" -> event.setGeneralSold(event.getGeneralSold() + quantity);
      default -> System.out.println("Unknown ticket tier: " + item.tierName());
    }
  }

  private String cartKey(Event event, String tierName) {
    return event.getEventId() + "-" + tierName;
  }

  private String confirmationNumber() {
    return "TM-" + getUserId() + "-" + System.currentTimeMillis();
  }

  private record TicketSelection(String tierName, double price) {}

  private static class CartItem {
    private final Event event;
    private final String tierName;
    private final double price;
    private int quantity;

    CartItem(Event event, String tierName, int quantity, double price) {
      this.event = event;
      this.tierName = tierName;
      this.quantity = quantity;
      this.price = price;
    }

    Event event() {
      return event;
    }

    String tierName() {
      return tierName;
    }

    int quantity() {
      return quantity;
    }

    double price() {
      return price;
    }

    double subtotal() {
      return quantity * price;
    }

    void addQuantity(int quantity) {
      this.quantity += quantity;
    }
  }
}

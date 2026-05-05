package ticketminer;

import java.util.List;
import java.util.Scanner;

/** Represents an admin user with manage-users functionality. */
public class Admin extends User {
  private final List<User> users;
  private final List<Admin> admins;

  /**
   * Creates an admin user.
   *
   * @param userId unique user id
   * @param firstName admin's first name
   * @param lastName admin's last name
   * @param userName admin's login username
   * @param password admin's login password
   * @param userType admin's role
   * @param keyboard scanner used for menu input
   * @param users customer and organizer users
   * @param admins admin users
   */
  public Admin(
      int userId,
      String firstName,
      String lastName,
      String userName,
      String password,
      String userType,
      Scanner keyboard,
      List<User> users,
      List<Admin> admins) {
    super(userId, firstName, lastName, userName, password, userType, keyboard);
    this.users = users;
    this.admins = admins;
  }

  /** Displays the admin menu. */
  @Override
  public void userMenu() {
    String userInput = "";

    while (!userInput.equals("4")) {
      System.out.println("\nAdmin Menu");
      System.out.println("1: Manage Users");
      System.out.println("2: Manage Venue");
      System.out.println("3: Manage Event");
      System.out.println("4: Back");

      userInput = getKeyboard().nextLine().trim();

      switch (userInput) {
        case "1" -> manageUsers();
        case "2" -> RunTicketMiner.manageVenue(getKeyboard());
        case "3" -> RunTicketMiner.manageEvent(getKeyboard());
        case "4" -> back();
        default -> System.out.println("Invalid option entered.");
      }
    }
  }

  private void manageUsers() {
    String userInput = "";

    while (!userInput.equals("5")) {
      System.out.println("\nManage Users");
      System.out.println("1: Add");
      System.out.println("2: View");
      System.out.println("3: Update");
      System.out.println("4: Delete");
      System.out.println("5: Back");

      userInput = getKeyboard().nextLine().trim();

      switch (userInput) {
        case "1" -> add();
        case "2" -> view();
        case "3" -> update();
        case "4" -> delete();
        case "5" -> back();
        default -> System.out.println("Invalid option entered.");
      }
    }
  }

  private void add() {
    System.out.println("Please select an option");
    System.out.println("1: Add new organizer");
    System.out.println("2: Add new customer");
    System.out.println("3: Add new admin");

    final String userInput = getKeyboard().nextLine().trim();

    System.out.print("Enter first name: ");
    String firstName = getKeyboard().nextLine().trim();

    System.out.print("Enter last name: ");
    String lastName = getKeyboard().nextLine().trim();

    String username = promptUniqueUsername();

    System.out.print("Enter password: ");
    String password = getKeyboard().nextLine().trim();

    int newId = getNextUserId();

    switch (userInput) {
      case "1" -> {
        users.add(
            new Organizer(
                newId, firstName, lastName, username, password, "organizer", getKeyboard()));
        RunTicketMiner.log(getUserName() + " added organizer " + username + " with ID " + newId);
        System.out.println("Organizer added successfully.");
      }

      case "2" -> {
        System.out.print("Enter money available: ");
        double money = Double.parseDouble(getKeyboard().nextLine().trim());

        System.out.print("Membership (true/false): ");
        boolean membership = Boolean.parseBoolean(getKeyboard().nextLine().trim());

        System.out.print("Enter concerts purchased: ");
        int concertsPurchased = Integer.parseInt(getKeyboard().nextLine().trim());

        users.add(
            new Customer(
                newId,
                firstName,
                lastName,
                username,
                password,
                "customer",
                getKeyboard(),
                money,
                membership,
                concertsPurchased));
        RunTicketMiner.log(getUserName() + " added customer " + username + " with ID " + newId);
        System.out.println("Customer added successfully.");
      }

      case "3" -> {
        admins.add(
            new Admin(
                newId,
                firstName,
                lastName,
                username,
                password,
                "admin",
                getKeyboard(),
                users,
                admins));
        RunTicketMiner.log(getUserName() + " added admin " + username + " with ID " + newId);
        System.out.println("Admin added successfully.");
      }

      default -> System.out.println("Invalid option entered.");
    }
  }

  private void view() {
    System.out.println("Please select an option");
    System.out.println("1: Display all members");
    System.out.println("2: Search for user");

    String userInput = getKeyboard().nextLine().trim();

    switch (userInput) {
      case "1" -> displayUsers();
      case "2" -> search();
      default -> System.out.println("Invalid option entered.");
    }
  }

  private void displayUsers() {
    if (users.isEmpty() && admins.isEmpty()) {
      System.out.println("No users found.");
      return;
    }

    RunTicketMiner.log(getUserName() + " displayed all members");

    for (User user : users) {
      System.out.println(user);
    }

    for (Admin admin : admins) {
      System.out.println(admin);
    }
  }

  private void search() {
    System.out.println("Enter ID, name, or username:");
    String input = getKeyboard().nextLine().trim();

    User found = findAnyUser(input);

    if (found != null) {
      System.out.println(found);
      RunTicketMiner.log(
          getUserName()
              + " searched for user "
              + input
              + " and found user ID "
              + found.getUserId());
    } else {
      System.out.println("User not found.");
      RunTicketMiner.log(getUserName() + " searched for user " + input + " but no match was found");
    }
  }

  private void update() {
    System.out.println("Enter ID, name, or username to update:");
    String input = getKeyboard().nextLine().trim();

    User found = findAnyUser(input);

    if (found == null) {
      System.out.println("User not found.");
      RunTicketMiner.log(
          getUserName() + " attempted to update user " + input + " but no match was found");
      return;
    }

    System.out.println("Please select an option");
    System.out.println("1: Change Name");
    System.out.println("2: Change Username");
    System.out.println("3: Change Password");

    String userInput = getKeyboard().nextLine().trim();

    switch (userInput) {
      case "1" -> {
        System.out.print("Enter new first name: ");
        String firstName = getKeyboard().nextLine().trim();
        System.out.print("Enter new last name: ");
        String lastName = getKeyboard().nextLine().trim();
        found.setFirstName(firstName);
        found.setLastName(lastName);
        RunTicketMiner.log(getUserName() + " updated name for user ID " + found.getUserId());
        System.out.println("Name updated successfully.");
      }

      case "2" -> {
        String oldUsername = found.getUserName();
        String newUsername = promptUniqueUsername();
        found.setUserName(newUsername);
        RunTicketMiner.log(
            getUserName()
                + " updated username for user ID "
                + found.getUserId()
                + " from "
                + oldUsername
                + " to "
                + newUsername);
        System.out.println("Username updated successfully.");
      }

      case "3" -> {
        System.out.print("Enter new password: ");
        String newPassword = getKeyboard().nextLine().trim();
        found.setPassword(newPassword);
        RunTicketMiner.log(getUserName() + " updated password for user ID " + found.getUserId());
        System.out.println("Password updated successfully.");
      }

      default -> System.out.println("Invalid option entered.");
    }
  }

  private void delete() {
    System.out.println("Enter ID, name, or username to delete:");
    String input = getKeyboard().nextLine().trim();

    User found = findAnyUser(input);

    if (found == null) {
      System.out.println("User not found.");
      RunTicketMiner.log(
          getUserName() + " attempted to delete user " + input + " but no match was found");
      return;
    }

    System.out.println("Found user: " + found);
    System.out.print("Confirm delete? (yes/no): ");
    String confirm = getKeyboard().nextLine().trim();

    if (!confirm.equalsIgnoreCase("yes")) {
      System.out.println("Delete cancelled.");
      return;
    }

    if (found instanceof Admin) {
      admins.remove(found);
    } else {
      users.remove(found);
    }

    RunTicketMiner.log(getUserName() + " deleted user ID " + found.getUserId());
    System.out.println("User deleted successfully.");
  }

  private User findAnyUser(String input) {
    for (User user : users) {
      if (matchesSearch(user, input)) {
        return user;
      }
    }

    for (Admin admin : admins) {
      if (matchesSearch(admin, input)) {
        return admin;
      }
    }

    return null;
  }

  private boolean matchesSearch(User user, String input) {
    if (user.matchesUsername(input) || user.matchesName(input)) {
      return true;
    }

    try {
      int id = Integer.parseInt(input);
      return user.matchesId(id);
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private boolean usernameExists(String username) {
    for (User user : users) {
      if (user.getUserName().equalsIgnoreCase(username)) {
        return true;
      }
    }

    for (Admin admin : admins) {
      if (admin.getUserName().equalsIgnoreCase(username)) {
        return true;
      }
    }

    return false;
  }

  private String promptUniqueUsername() {
    String username;

    do {
      System.out.print("Enter username: ");
      username = getKeyboard().nextLine().trim();

      if (usernameExists(username)) {
        System.out.println("Username already exists. Enter a different username.");
      }
    } while (usernameExists(username));

    return username;
  }

  private int getNextUserId() {
    int maxId = getUserId();

    for (User user : users) {
      if (user.getUserId() > maxId) {
        maxId = user.getUserId();
      }
    }

    for (Admin admin : admins) {
      if (admin.getUserId() > maxId) {
        maxId = admin.getUserId();
      }
    }

    return maxId + 1;
  }
}

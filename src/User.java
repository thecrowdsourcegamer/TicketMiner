import java.util.List;
import java.util.Scanner;

/**
 * User hierarchy for TicketMiner.
 * Supports Customer, Organizer, and Admin users.
 */
public abstract class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String userType;
    private boolean canEdit;
    private Scanner keyboard;

    public User(int userId, String firstName, String lastName, String userName, String password, String userType, Scanner keyboard) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.keyboard = keyboard;
    }

    public abstract void userMenu();

    public void back() {
        System.out.println("Going back.");
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean matchesId(int id) {
        return this.userId == id;
    }

    public boolean matchesUsername(String username) {
        return this.userName.equalsIgnoreCase(username);
    }

    public boolean matchesName(String name) {
        return getFullName().equalsIgnoreCase(name);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "ID: " + userId
            + ", Name: " + firstName + " " + lastName
            + ", Username: " + userName
            + ", Password: " + password
            + ", Type: " + userType;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public Scanner getKeyboard() {
        return keyboard;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public void setKeyboard(Scanner keyboard) {
        this.keyboard = keyboard;
    }
}

/**
 * Customer user.
 */
class Customer extends User {
    private double moneyAvailable;
    private boolean membership;

    public Customer(int userId, String firstName, String lastName, String userName,
                    String password, String userType, Scanner keyboard,
                    double moneyAvailable, boolean membership) {
        super(userId, firstName, lastName, userName, password, userType, keyboard);
        this.moneyAvailable = moneyAvailable;
        this.membership = membership;
    }

    public double getMoneyAvailable() {
        return moneyAvailable;
    }

    public boolean isMembership() {
        return membership;
    }

    public void setMembership(boolean membership) {
        this.membership = membership;
    }

    public void setMoneyAvailable(double moneyAvailable) {
        this.moneyAvailable = moneyAvailable;
    }

    @Override
    public String toString() {
        return super.toString()
            + ", Money Available: " + moneyAvailable
            + ", Membership: " + membership;
    }

    @Override
    public void userMenu() {
        String userInput = "";

        while (!userInput.equals("2")) {
            System.out.println("\nCustomer Menu");
            System.out.println("1: View Profile");
            System.out.println("2: Back");

            userInput = getKeyboard().nextLine().trim();

            switch (userInput) {
                case "1" -> System.out.println(this);
                case "2" -> back();
                default -> System.out.println("Invalid option entered.");
            }
        }
    }
}

/**
 * Organizer user.
 */
class Organizer extends User {

    public Organizer(int userId, String firstName, String lastName, String userName,
                     String password, String userType, Scanner keyboard) {
        super(userId, firstName, lastName, userName, password, userType, keyboard);
    }

    @Override
    public void userMenu() {
        String userInput = "";

        while (!userInput.equals("2")) {
            System.out.println("\nOrganizer Menu");
            System.out.println("1: View Profile");
            System.out.println("2: Back");

            userInput = getKeyboard().nextLine().trim();

            switch (userInput) {
                case "1" -> System.out.println(this);
                case "2" -> back();
                default -> System.out.println("Invalid option entered.");
            }
        }
    }
}

/**
 * Admin user with manage-users functionality.
 */
class Admin extends User {
    private List<User> users;
    private List<Admin> admins;

    public Admin(int userId, String firstName, String lastName, String userName,
                 String password, String userType, Scanner keyboard,
                 List<User> users, List<Admin> admins) {
        super(userId, firstName, lastName, userName, password, userType, keyboard);
        this.users = users;
        this.admins = admins;
    }

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

        String userInput = getKeyboard().nextLine().trim();

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
                users.add(new Organizer(newId, firstName, lastName, username,
                        password, "organizer", getKeyboard()));
                System.out.println("Organizer added successfully.");
            }

            case "2" -> {
                System.out.print("Enter money available: ");
                double money = Double.parseDouble(getKeyboard().nextLine().trim());

                System.out.print("Membership (true/false): ");
                boolean membership = Boolean.parseBoolean(getKeyboard().nextLine().trim());

                users.add(new Customer(newId, firstName, lastName, username,
                        password, "customer", getKeyboard(), money, membership));
                System.out.println("Customer added successfully.");
            }

            case "3" -> {
                admins.add(new Admin(newId, firstName, lastName, username,
                        password, "admin", getKeyboard(), users, admins));
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

        for (User u : users) {
            System.out.println(u);
        }

        for (Admin a : admins) {
            System.out.println(a);
        }
    }

    private void search() {
        System.out.println("Enter ID, name, or username:");
        String input = getKeyboard().nextLine().trim();

        User found = findAnyUser(input);

        if (found != null) {
            System.out.println(found);
        } else {
            System.out.println("User not found.");
        }
    }

    private void update() {
        System.out.println("Enter ID, name, or username to update:");
        String input = getKeyboard().nextLine().trim();

        User found = findAnyUser(input);

        if (found == null) {
            System.out.println("User not found.");
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
                System.out.println("Name updated successfully.");
            }

            case "2" -> {
                String newUsername = promptUniqueUsername();
                found.setUserName(newUsername);
                System.out.println("Username updated successfully.");
            }

            case "3" -> {
                System.out.print("Enter new password: ");
                String newPassword = getKeyboard().nextLine().trim();
                found.setPassword(newPassword);
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

        System.out.println("User deleted successfully.");
    }

    private User findAnyUser(String input) {
        for (User u : users) {
            if (matchesSearch(u, input)) {
                return u;
            }
        }

        for (Admin a : admins) {
            if (matchesSearch(a, input)) {
                return a;
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
        for (User u : users) {
            if (u.getUserName().equalsIgnoreCase(username)) {
                return true;
            }
        }

        for (Admin a : admins) {
            if (a.getUserName().equalsIgnoreCase(username)) {
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

        for (User u : users) {
            if (u.getUserId() > maxId) {
                maxId = u.getUserId();
            }
        }

        for (Admin a : admins) {
            if (a.getUserId() > maxId) {
                maxId = a.getUserId();
            }
        }

        return maxId + 1;
    }
}
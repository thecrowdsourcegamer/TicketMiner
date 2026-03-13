import java.util.Scanner;

public abstract class User {
    private int userId; 
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String userType;
    private boolean canEdit;
    Scanner keyboard;

    //constructor
    public User(int userId, String firstName, String lastName, String userName, String password, String userType, Scanner keyboard) {
            this.userId = userId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.userName = userName;
            this.password = password;
            this.userType = userType;
            this.keyboard = keyboard;
    }

    public User(Scanner keyboard) {
        this.keyboard = keyboard;
    }

    abstract void userMenu();

    void back() {
        System.out.println("going back");
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

    @Override
    public String toString() {
        return "ID: " + userId +
               ", Name: " + firstName + " " + lastName +
               ", Username: " + userName +
               ", Type: " + userType;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    // Getters

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

    // Setters

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
} // user 

class Customer extends User {
    private double moneyAvailable;
    private boolean membership;

    public Customer(Scanner keyboard) {
    super(keyboard);
    }

    @Override
    void userMenu() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
} // customer

class Organizer extends User {
    public Organizer(Scanner keyboard) {
    super(keyboard);
    }
    @Override
    void userMenu() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
} // organizer

class Admin extends User {
    public Admin(Scanner keyboard) {
    super(keyboard);
    }

    @Override
    void userMenu() {
    System.out.println("Please choose an option.");
    System.out.println("\n1: Add new user \n2: View \n3: Update \n4: Delete \n5: Exit");
    String userInput = keyboard.nextLine().trim();

        while(!userInput.equals("5")) {
            switch (userInput) {
                case "1" -> add(userInput);
                case "2" -> view(userInput);
                case "3" -> update(userInput);
                case "4" -> delete(userInput);
                default -> System.out.println("Invalid option entered.");
            } // switch

        System.out.println("Please select a menu option.");
        System.out.println("\n1: Add \n2: View \n3: Update \n4: Delete \n5: Exit");
        userInput = keyboard.nextLine();
        } //while
    } //user menu


    private void add(String userInput) {
        System.out.println("Please select an option");
        System.out.println("1: Add new organizer \n2: Add new customer");
        userInput = keyboard.nextLine();
        switch (userInput) {
            case "1":
            break;
            case "2":
            break;
            default:
            System.out.println("Invalid option entered.");
        }

    }
   private void view(String userInput) {
        System.out.println("Please select an option");
        System.out.println("1: Display all members \n2: Search for user");
        userInput = keyboard.nextLine();
        switch (userInput) {
            case "1":
            displayUsers();
            break;
            case "2":
            search();
            // search by ID name or user name
            break;
            default:
            System.out.println("Invalid option entered.");
        }
        // 2 options
        // display users
        // search
    }

    private void displayUsers() {
        // part of view 
    }
    private void search() {
        //part of view
    }

    private void update(String userInput) {
        System.out.println("Please select an option");
        System.out.println("1: Change Name \n2: Change Username \n3: Change Password");
        userInput = keyboard.nextLine();
        switch (userInput) {
            case "1":
            System.out.println("test");
            break;
            case "2":
            break;
            case "3":
            break;
            default:
            System.out.println("Invalid option entered.");
        }
        // 3 options
        // change name
        // change username
        // change password
        // if no user found message
        // display menu options
    
    }
    private void delete(String userInput) {
        System.out.println("Please enter either ID, name, or username to delete the found user.");
        userInput = keyboard.nextLine();
        //delete member found
        // error if no found
        // display menu
        switch (userInput) {
            case "1":
            break;
            case "2":
            break;
            case "3":
            break;
            default:
            System.out.println("Invalid option entered.");
        }
    } // delete
} // user

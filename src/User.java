import java.util.Scanner;

public abstract class User {
    String userName;
    boolean canEdit;
    Scanner keyboard;
    String password;
    String firstName;
    String lastName;
    String userType;


    public User(Scanner keyboard) {
        this.keyboard = keyboard;
    }

    abstract void userMenu();

    void back() {
        System.out.println("going back");
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public boolean isCanEdit() {
        return canEdit;
    }
    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
    public Scanner getKeyboard() {
        return keyboard;
    }
    public void setKeyboard(Scanner keyboard) {
        this.keyboard = keyboard;
    }
} // user 

class Customer extends User {
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

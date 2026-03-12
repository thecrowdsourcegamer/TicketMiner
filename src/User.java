import java.util.Scanner;

public abstract class User {
    String userName;
    boolean canEdit;
    Scanner keyboard;

    public User(Scanner keyboard) {
        this.keyboard = keyboard;
    }

    abstract void userMenu ();

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
    void userMenu() {
    String userInput = keyboard.nextLine();
    System.out.println("Please choose an option.");
    System.out.println("\n1: Add \n2: View \n3: Update \n4: Delete: \n5: Exit");

        while(!userInput.equals("5")) {

            switch (userInput) {
                case "1":
                System.out.println("add user");
                break;
                case "2":
                System.out.println("view");
                break;
                case "3":
                System.out.println("Update");
                break;
                case "4":
                System.out.println("delete");
                break;
                default:
                System.out.println("Invalid option entered.");
                break;
            } // switch
        } //while
    } //user menu


    void add() {
        // needs info as same as customer / organizer 
        // has menu options

    }
    void view() {
        // 2 options
        // display users
        // search
    }

    void displayUsers() {
        // part of view 
    }
    void search() {
        //part of view
    }

    void update() {
        // 3 options
        // change name
        // change username
        // change password
        // if no user found message
        // display menu options
    
    }
    void delete() {
        //delete member found
        // error if no found
        // display menu
    }

    


}

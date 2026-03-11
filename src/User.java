import java.util.Scanner;

public class User {
    String userName;
    boolean canEdit;
    Scanner keyboard = new Scanner(System.in);

    
    void userMenu () {
        //this will be overwritten
    }
    void back() {}

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

class customer extends User {

}

class organizer extends User {

}

class admin extends User {

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

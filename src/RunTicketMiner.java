import java.util.Scanner;

    /** 
     * Part 1 for project for advanced object programming.
     * @author Derek Garcia, *****, ***** <- add names
     */
public class RunTicketMiner {

  /** 
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    menu();
  } // main
  

      /** 
     * Allows the user to navigate TicketMiner using provided commands.
     */
  public static void menu() {
    try (Scanner keyboard = new Scanner(System.in)) {
      System.out.println("Please select a menu option.");
      System.out.println("\n1: Register \n2: Login \n3: EXIT");
      String userInput = keyboard.nextLine().strip().toLowerCase().trim();

      while(!userInput.equals("3")) {

        switch (userInput) {
          case "1":
          System.out.println("test - register");
          // TODO: Register method
            break;
          case "2":
          System.out.println("test - login");
          Admin testAdmin = new Admin(keyboard);
          testAdmin.setUserName("admin");
          testAdmin.userMenu();
          break;
          default:
        System.out.println("Invalid option entered.");
            break;
        } // switch
        
        
        
        System.out.println("Please select a menu option.");
        System.out.println("\n1: Register \n2: Login \n3: EXIT");
        userInput = keyboard.nextLine();
      } // while 
    } // try
    System.out.println("thank you for visiting! ");
  } // menu
} // RunTicketMiner

import java.util.Scanner;

public class RunTicketMiner {
    public static void main(String[] args) throws Exception {
    menu();
  } // main
  
  public static void menu() {
    Scanner keyboard = new Scanner(System.in);
    System.out.println("Please select a menu option.");
    System.out.println("\n 1: Register \n 2: Login \n 3: EXIT");
    String userInput = keyboard.nextLine().strip().toLowerCase().trim();

    while(!userInput.equals("exit")) {

      switch (userInput) {
        case "register":
        System.out.println("test - register");
        // TODO: Register method
          break;
        case "login":
        System.out.println("test - login");
        // TODO: Login Method
        break;
        default:
      System.out.println("Invalid option entered.");
          break;
      } // switch–
      
      
      
      System.out.println("Please select a menu option.");
      System.out.println("\n 1: Register \n 2: Login \n 3: EXIT");
      userInput = keyboard.nextLine();
    } // while 
  System.out.println("thank you for visiting! ");
  } // menu
} // RunTicketMiner

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    /** 
     * Part 1 for project for advanced object programming.
     * @author Derek Garcia, *****, ***** <- add names
     */
public class RunTicketMiner {
    private static List<Venue> venues = new ArrayList<>();

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
          manageVenue(keyboard);
          // Admin testAdmin = new Admin(keyboard);
          // testAdmin.setUserName("admin");
          // testAdmin.userMenu();
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
  }
  public static void manageVenue(Scanner keyboard) {

        System.out.println("\nManage Venue");
        System.out.println("1: Add Venue");
        System.out.println("2: View Venues");
        System.out.println("3: Update Venue");
        System.out.println("4: Delete Venue");
        System.out.println("5: Back");

        String input = keyboard.nextLine().trim();

        while(!input.equals("5")) {

            switch(input) {

                case "1":
                     addVenue(keyboard);
                    break;

                case "2":
                viewAllVenues();
                  break;

                case "3":
                    System.out.println("Update venue not implemented yet.");
                    break;

                case "4":
                    System.out.println("Delete venue not implemented yet.");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

            System.out.println("\nManage Venue");
            System.out.println("1: Add Venue");
            System.out.println("2: View Venues");
            System.out.println("3: Update Venue");
            System.out.println("4: Delete Venue");
            System.out.println("5: Back");

            input = keyboard.nextLine().trim();
        }

    }
    
public static void addVenue(Scanner keyboard) {

    System.out.print("Enter venue ID: ");
    int venueId = Integer.parseInt(keyboard.nextLine().trim());

    System.out.print("Enter venue name: ");
    String name = keyboard.nextLine().trim();

    System.out.print("Enter venue type (Arena, Auditorium, OpenAir, Stadium): ");
    String type = keyboard.nextLine().trim();

    System.out.print("Enter capacity: ");
    int capacity = Integer.parseInt(keyboard.nextLine().trim());

    System.out.print("Enter cost: ");
    double cost = Double.parseDouble(keyboard.nextLine().trim());

    System.out.print("Enter location: ");
    String location = keyboard.nextLine().trim();

    Venue newVenue = null;

    if (type.equalsIgnoreCase("Arena")) {
        newVenue = new Arena(venueId, name, type, capacity, cost, location);
    } else if (type.equalsIgnoreCase("Auditorium")) {
        newVenue = new Auditorium(venueId, name, type, capacity, cost, location);
    } else if (type.equalsIgnoreCase("OpenAir")) {
        newVenue = new OpenAir(venueId, name, type, capacity, cost, location);
    } else if (type.equalsIgnoreCase("Stadium")) {
        // newVenue = new Stadium(venueId, name, type, capacity, cost, location);
    } else {
        System.out.println("Invalid venue type.");
        return;
    }

    venues.add(newVenue);
    System.out.println("Venue added successfully.");
}
    public static void viewAllVenues() {

    if (venues.isEmpty()) {
        System.out.println("No venues found.");
        return;
    }

    for (Venue venue : venues) {
        System.out.println(venue);
    }
}
}


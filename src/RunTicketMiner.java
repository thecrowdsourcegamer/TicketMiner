import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    /** 
     * Part 1 for project for advanced object programming.
     * @author Derek Garcia, Emiliano Puchaicela, ***** <- add names
     */
public class RunTicketMiner {
    private static List<Venue> venues = new ArrayList<>();
    private static List<Event> events = new ArrayList<>();
    private static List<User> users = new ArrayList<>(); //non-admin users
    private static List<Admin> admins = new ArrayList<>();

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

    readVenueCSV("../csvs/Venue_List_PA1.csv");
    readUserCSV("../csvs/Customer_List_PA1.csv");
    readEventCSV("../csvs/Event_List_PA1.csv");

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

  public static void manageEvent(Scanner keyboard) {

        System.out.println("\nManage Event");
        System.out.println("1: Add Event");
        System.out.println("2: View Events");
        System.out.println("3: Update Event");
        System.out.println("4: Delete Event");
        System.out.println("5: Back");

        String input = keyboard.nextLine().trim();

        while(!input.equals("5")) {

            switch(input) {

                case "1":
                     addEvent(keyboard);
                    break;

                case "2":
                viewAllEvents();
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

            System.out.println("\nManage Event");
            System.out.println("1: Add Event");
            System.out.println("2: View Events");
            System.out.println("3: Update Event");
            System.out.println("4: Delete Event");
            System.out.println("5: Back");

            input = keyboard.nextLine().trim();
        }

  }

  public static void addEvent(Scanner keyboard) {
    System.out.print("Enter event ID: ");
    int id = Integer.parseInt(keyboard.nextLine().trim());

    System.out.print("Enter event name: ");
    String name = keyboard.nextLine().trim();

    System.out.print("Enter event type (Concert, Sport, Theater): ");
    String type = keyboard.nextLine().trim();

    System.out.print("Enter event date (YYYY-MM-DD): ");
    LocalDate date = LocalDate.parse(keyboard.nextLine().trim());

    System.out.print("Enter event time (HH:MM): ");
    LocalTime time = LocalTime.parse(keyboard.nextLine().trim());

    System.out.print("Enter VIP price: ");
    double vipPrice = Double.parseDouble(keyboard.nextLine().trim());

    System.out.print("Enter Gold price: ");
    double goldPrice = Double.parseDouble(keyboard.nextLine().trim());

    System.out.print("Enter Silver price: ");
    double silverPrice = Double.parseDouble(keyboard.nextLine().trim());

    System.out.print("Enter Bronze price: ");
    double bronzePrice = Double.parseDouble(keyboard.nextLine().trim());

    System.out.print("Enter General Admission price: ");
    double generalAdmissionPrice = Double.parseDouble(keyboard.nextLine().trim());

    Event newEvent = null;

    if (type.equalsIgnoreCase("Concert")) {
      System.out.print("Enter artist ");
      String artist = keyboard.nextLine().trim();
      System.out.print("Enter genre ");
      String genre = keyboard.nextLine().trim();

      newEvent = new Concert(id, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice, artist, genre);
    } else if (type.equalsIgnoreCase("Sport")) {
      System.out.print("Enter team1 ");
      String team1 = keyboard.nextLine().trim();
      System.out.print("Enter team2 ");
      String team2 = keyboard.nextLine().trim();
      System.out.print("Enter league ");
      String league = keyboard.nextLine().trim();

      newEvent = new Sport(id, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice, team1, team2, league);
    } else if (type.equalsIgnoreCase("Special")) {
      System.out.print("Enter description ");
      String description = keyboard.nextLine().trim();
      System.out.print("Enter category ");
      String category = keyboard.nextLine().trim();

      newEvent = new Special(id, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice, null, null);
    } else {
      System.out.println("Invalid event type.");
      return;
    }

    events.add(newEvent);
    System.out.println("Event added successfully.");
  }

  public static void viewAllEvents() {
    if (events.isEmpty()) {
        System.out.println("No events found.");
        return;
    }

    for (Event event : events) {
        System.out.println(event);
    }
  }

  public static void readUserCSV(String filePath) {
    //ArrayList<User> users = new ArrayList<>();
    //ArrayList<Admin> admins = new ArrayList<>();

    try {
      File file = new File(filePath);
      Scanner csvScanner = new Scanner(file);

      while (csvScanner.hasNextLine()) {
        String line = csvScanner.nextLine();
        String[] fields = line.split(",");

    
        String id = fields[0].trim();
        String firstName = fields[1].trim();
        String lastName = fields[2].trim();
        String username = fields[3].trim();
        String password = fields[4].trim();
        String userType = fields[5].trim();

        Scanner keyboard = new Scanner(System.in);
        
        if (userType.equalsIgnoreCase("customer")) {
          String moneyAvailable = fields[5].trim();
          String membership = fields[6].trim();
          Customer customer = new Customer(Integer.parseInt(id), firstName, lastName, username, password, userType, keyboard, Double.parseDouble(moneyAvailable), Boolean.parseBoolean(membership));
          users.add(customer);
        } else if (userType.equalsIgnoreCase("organizer")) {
          Organizer organizer = new Organizer(Integer.parseInt(id), firstName, lastName, username, password, userType, keyboard);
          users.add(organizer);
        } else if (userType.equalsIgnoreCase("admin")) {
          Admin admin = new Admin(Integer.parseInt(id), firstName, lastName, username, password, userType, keyboard, users);
          admins.add(admin);
        } else {
          System.out.println("Invalid user type for ID: " + id);
        }

      }
    } catch (FileNotFoundException e) {
      System.out.println("Error: File not found - " + filePath);
    }

    //return admins;
  }

  public static void readVenueCSV(String filePath) {
    //ArrayList<Venue> venues = new ArrayList<>();

    try {
      File file = new File(filePath);
      Scanner csvScanner = new Scanner(file);

      while (csvScanner.hasNextLine()) {
        String line = csvScanner.nextLine();
        String[] fields = line.split(",");

    
        String id = fields[0].trim();
        String name = fields[1].trim();
        String type = fields[2].trim();
        String capacity = fields[3].trim();
        String concertCapacity = fields[4].trim();
        String cost = fields[5].trim();
        String vipPercent = fields[6].trim();
        String goldPercent = fields[7].trim();
        String silverPercent = fields[8].trim();
        String bronzePercent = fields[9].trim();
        String generalAdmissionPercent = fields[10].trim();
        String reservedExtraPercent = fields[11].trim();
        
        if(type.equalsIgnoreCase("arena")) {
          Arena arena = new Arena(Integer.parseInt(id), name, type, Integer.parseInt(capacity), Double.parseDouble(cost), name);
          venues.add(arena);
        } else if (type.equalsIgnoreCase("auditorium")) {
          Auditorium auditorium = new Auditorium(Integer.parseInt(id), name, type, Integer.parseInt(capacity), Double.parseDouble(cost), name);
          venues.add(auditorium);
        } else if (type.equalsIgnoreCase("openair")) {
          OpenAir openAir = new OpenAir(Integer.parseInt(id), name, type, Integer.parseInt(capacity), Double.parseDouble(cost), name);
          venues.add(openAir);
        } else if (type.equalsIgnoreCase("stadium")) {
          Stadium stadium = new Stadium(Integer.parseInt(id), name, type, Integer.parseInt(capacity), Double.parseDouble(cost), name);
          venues.add(stadium);
        } else {
          System.out.println("Invalid venue type for ID: " + id);
        }

      }
    } catch (FileNotFoundException e) {
      System.out.println("Error: File not found - " + filePath);
    }

    //return venues;
  }

  public static void readEventCSV(String filePath) {
    //ArrayList<Event> events = new ArrayList<>();

    try {
      File file = new File(filePath);
      Scanner csvScanner = new Scanner(file);

      while (csvScanner.hasNextLine()) {
        String line = csvScanner.nextLine();
        String[] fields = line.split(",");

    
        String id = fields[0].trim();
        String type = fields[1].trim();
        String name = fields[2].trim();
        String date = fields[3].trim();
        String time = fields[4].trim();
        String vipPrice = fields[5].trim();
        String goldPrice = fields[6].trim();
        String silverPrice = fields[7].trim();
        String bronzePrice = fields[8].trim();
        String generalAdmissionPrice = fields[9].trim();
        
        if(type.equalsIgnoreCase("concert")) {
          Concert concert = new Concert(Integer.parseInt(id), name, LocalDate.parse(date), LocalTime.parse(time), Double.parseDouble(vipPrice), Double.parseDouble(goldPrice), Double.parseDouble(silverPrice), Double.parseDouble(bronzePrice), Double.parseDouble(generalAdmissionPrice), null, null);
          events.add(concert);
        } else if (type.equalsIgnoreCase("sports")) {
          Sport sport = new Sport(Integer.parseInt(id), name, LocalDate.parse(date), LocalTime.parse(time), Double.parseDouble(vipPrice), Double.parseDouble(goldPrice), Double.parseDouble(silverPrice), Double.parseDouble(bronzePrice), Double.parseDouble(generalAdmissionPrice), null, null, null);
          events.add(sport);
        } else if (type.equalsIgnoreCase("theater")) {
          Special special = new Special(Integer.parseInt(id), name, LocalDate.parse(date), LocalTime.parse(time), Double.parseDouble(vipPrice), Double.parseDouble(goldPrice), Double.parseDouble(silverPrice), Double.parseDouble(bronzePrice), Double.parseDouble(generalAdmissionPrice), null, null);
          events.add(special);
        } else {
          System.out.println("Invalid event type for ID: " + id);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("Error: File not found - " + filePath);
    }

    //return events;
  }
}


package ticketminer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

class Organizer extends User {

  private List<Event> events;

  public Organizer(
      int userId,
      String firstName,
      String lastName,
      String userName,
      String password,
      String userType,
      Scanner keyboard,
      List<Event> events) {

    super(userId, firstName, lastName, userName, password, userType, keyboard);
    this.events = events;
  }

  public Organizer(
      int userId,
      String firstName,
      String lastName,
      String userName,
      String password,
      String userType,
      Scanner keyboard) {
    this(
        userId,
        firstName,
        lastName,
        userName,
        password,
        userType,
        keyboard,
        RunTicketMiner.getEvents());
  }

  public void userMenu() {
    Scanner kb = getKeyboard();
    String input = "";

    while (!input.equals("3")) {
      System.out.println("\n=== Organizer Menu ===");
      System.out.println("1: Manage Event");
      System.out.println("2: Generate Event Report");
      System.out.println("3: Log Out");
      System.out.print("Choose an option: ");

      input = kb.nextLine().trim();

      switch (input) {
        case "1":
          manageEvent();
          break;
        case "2":
          generateEventReport();
          break;
        case "3":
          back();
          break;
        default:
          System.out.println("Invalid option. Please try again.");
      }
    }
  }

  private void manageEvent() {
    Scanner kb = getKeyboard();
    String input = "";

    while (!input.equals("5")) {
      System.out.println("\n--- Manage Event ---");
      System.out.println("1: Add Event");
      System.out.println("2: View Events");
      System.out.println("3: Update Event");
      System.out.println("4: Delete Event");
      System.out.println("5: Back");
      System.out.print("Choose an option: ");

      input = kb.nextLine().trim();

      switch (input) {
        case "1":
          addEvent();
          break;
        case "2":
          viewEventMenu();
          break;
        case "3":
          updateEvent();
          break;
        case "4":
          deleteEvent();
          break;
        case "5":
          break;
        default:
          System.out.println("Invalid option. Please try again.");
      }
    }
  }

  private void addEvent() {
    Scanner kb = getKeyboard();

    int id = RunTicketMiner.readInt(kb, "Enter Event ID: ");

    System.out.print("Enter Event Name: ");
    String name = kb.nextLine().trim();

    System.out.print("Enter Event Type (Concert / Sport / Special): ");
    String type = kb.nextLine().trim();

    LocalDate date = RunTicketMiner.readDate(kb, "Enter Event Date");
    LocalTime time = RunTicketMiner.readTime(kb, "Enter Event Time");

    double vipPrice = RunTicketMiner.readDouble(kb, "Enter VIP Ticket Price: $");
    double goldPrice = RunTicketMiner.readDouble(kb, "Enter Gold Ticket Price: $");
    double silverPrice = RunTicketMiner.readDouble(kb, "Enter Silver Ticket Price: $");
    double bronzePrice = RunTicketMiner.readDouble(kb, "Enter Bronze Ticket Price: $");
    double generalPrice = RunTicketMiner.readDouble(kb, "Enter General Admission Ticket Price: $");

    String firstDetail = null;
    String secondDetail = null;
    String thirdDetail = null;

    if (type.equalsIgnoreCase("Concert")) {
      System.out.print("Enter Artist Name: ");
      firstDetail = kb.nextLine().trim();

      System.out.print("Enter Genre: ");
      secondDetail = kb.nextLine().trim();
    } else if (type.equalsIgnoreCase("Sport")) {
      System.out.print("Enter Team 1: ");
      firstDetail = kb.nextLine().trim();

      System.out.print("Enter Team 2: ");
      secondDetail = kb.nextLine().trim();

      System.out.print("Enter League: ");
      thirdDetail = kb.nextLine().trim();
    } else if (type.equalsIgnoreCase("Special")) {
      System.out.print("Enter Description: ");
      firstDetail = kb.nextLine().trim();

      System.out.print("Enter Category: ");
      secondDetail = kb.nextLine().trim();
    } else {
      System.out.println("Invalid event type.");
      return;
    }
    Event event =
        EventFactory.createEvent(
            id,
            name,
            type,
            date,
            time,
            vipPrice,
            goldPrice,
            silverPrice,
            bronzePrice,
            generalPrice,
            firstDetail,
            secondDetail,
            thirdDetail);
    event.setEventType(type);
    events.add(event);

    RunTicketMiner.writeEventCsv("csvs/Updated_Event_List_PA2.csv");
    RunTicketMiner.log(getUserName() + " added event ID " + id);

  System.out.println("Event added successfully.");
  }

  private void viewEventMenu() {
    Scanner kb = getKeyboard();
    String input = "";

    while (!input.equals("3")) {
      System.out.println("\n--- View Events ---");
      System.out.println("1: Display All Events");
      System.out.println("2: Search Event");
      System.out.println("3: Back");
      System.out.print("Choose an option: ");

      input = kb.nextLine().trim();

      switch (input) {
        case "1":
          displayAllEvents();
          break;
        case "2":
          searchEvent();
          break;
        case "3":
          break;
        default:
          System.out.println("Invalid option. Please try again.");
      }
    }
  }

  private void displayAllEvents() {
    if (events.isEmpty()) {
      System.out.println("No events found.");
      return;
    }

    for (Event event : events) {
      System.out.println(event);
    }
  }

  private void searchEvent() {
    Scanner kb = getKeyboard();

    System.out.print("Enter Event ID, Name, or Date: ");
    String search = kb.nextLine().trim();

    boolean found = RunTicketMiner.printMatches(events, search);

    if (!found) {
      System.out.println("Event not found.");
    }
  }

  private void updateEvent() {
    Scanner kb = getKeyboard();

    System.out.print("Enter Event ID, Name, or Date: ");
    String search = kb.nextLine().trim();

    Event event = findEvent(search);

    if (event == null) {
      System.out.println("Event not found.");
      return;
    }

    System.out.println("\nWhat do you want to update?");
    System.out.println("1: Event Name");
    System.out.println("2: Event Date and Time");
    System.out.print("Choose an option: ");

    String choice = kb.nextLine().trim();

    switch (choice) {
      case "1":
        System.out.print("Enter New Event Name: ");
        String newName = kb.nextLine().trim();
        event.setEventName(newName);

    RunTicketMiner.writeEventCsv("csvs/Updated_Event_List_PA2.csv");
    RunTicketMiner.log(getUserName() + " updated event name for event ID " + event.getEventId());

    System.out.println("Event name updated.");
    break;

      case "2":
        LocalDate newDate = RunTicketMiner.readDate(kb, "Enter New Date");
        LocalTime newTime = RunTicketMiner.readTime(kb, "Enter New Time");

  event.setDate(newDate);
  event.setTime(newTime);


  RunTicketMiner.writeEventCsv("csvs/Updated_Event_List_PA2.csv");
  RunTicketMiner.log(getUserName() + " updated event date/time for event ID " + event.getEventId());

  System.out.println("Event date and time updated.");
  break;

      default:
        System.out.println("Invalid option.");
    }
  }

  private void deleteEvent() {
    Scanner kb = getKeyboard();

    System.out.print("Enter Event ID, Name, or Date: ");
    String search = kb.nextLine().trim();

    Event event = findEvent(search);

    if (event == null) {
      System.out.println("Event not found.");
      return;
    }

    System.out.println(event);
    System.out.print("Are you sure you want to delete this event? yes/no: ");

    String answer = kb.nextLine().trim();

    if (answer.equalsIgnoreCase("yes")) {
      events.remove(event);


  RunTicketMiner.writeEventCsv("csvs/Updated_Event_List_PA2.csv");
  RunTicketMiner.log(getUserName() + " deleted event ID " + event.getEventId());

  System.out.println("Event deleted.");
    } else {
      System.out.println("Delete cancelled.");
    }
  }
  private void generateEventReport() {
        Scanner kb = getKeyboard();

        System.out.print("Enter Event ID, Name, or Date: ");
        String search = kb.nextLine().trim();

        Event event = findEvent(search);

        if (event == null) {
            System.out.println("Event not found.");
            RunTicketMiner.log(getUserName() + " attempted to generate report for " + search + " but no match was found");
            return;
        }

        int vipSold = event.getVipSold();
        int goldSold = event.getGoldSold();
        int silverSold = event.getSilverSold();
        int bronzeSold = event.getBronzeSold();
        int generalSold = event.getGeneralSold();

        int totalSold = vipSold + goldSold + silverSold + bronzeSold + generalSold;

      double vipRevenue = event.getVipRevenue();
      double goldRevenue = event.getGoldRevenue();
      double silverRevenue = event.getSilverRevenue();
      double bronzeRevenue = event.getBronzeRevenue();
      double generalRevenue = event.getGeneralRevenue();

      double totalRevenue = event.getTotalRevenue();

        System.out.println("\n--- Event Report ---");
        System.out.println("Event ID: " + event.getEventId());
        System.out.println("Event Type: " + event.getEventType());
        System.out.println("Event Name: " + event.getEventName());
        System.out.println("Event Date: " + event.getDate());
        System.out.println("Event Time: " + event.getTime());

        System.out.println("\n--- Seats Sold ---");
        System.out.println("Total Seats Sold: " + totalSold);
        System.out.println("VIP Seats Sold: " + vipSold);
        System.out.println("Gold Seats Sold: " + goldSold);
        System.out.println("Silver Seats Sold: " + silverSold);
        System.out.println("Bronze Seats Sold: " + bronzeSold);
        System.out.println("General Admission Seats Sold: " + generalSold);

        System.out.println("\n--- Revenue ---");
        System.out.printf("Total Revenue for VIP Tickets: $%.2f%n", vipRevenue);
        System.out.printf("Total Revenue for Gold Tickets: $%.2f%n", goldRevenue);
        System.out.printf("Total Revenue for Silver Tickets: $%.2f%n", silverRevenue);
        System.out.printf("Total Revenue for Bronze Tickets: $%.2f%n", bronzeRevenue);
        System.out.printf("Total Revenue for General Admission Tickets: $%.2f%n", generalRevenue);
        System.out.printf("Total Revenue for All Tickets: $%.2f%n", totalRevenue);

       System.out.printf("%nExpected Profit: $%.2f%n",
          event.getExpectedProfit());

      System.out.printf("Actual Profit: $%.2f%n",
          event.getActualProfit());

        RunTicketMiner.log(getUserName() + " generated event report for event ID " + event.getEventId());
    }

  private Event findEvent(String search) {
    return RunTicketMiner.findMatch(events, search);
  }
}

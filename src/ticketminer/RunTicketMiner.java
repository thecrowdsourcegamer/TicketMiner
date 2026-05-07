package ticketminer;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main driver class for the TicketMiner system. This class starts the application, loads CSV data,
 * and provides menu navigation for users to manage venues, events, and accounts.
 *
 * @author Derek Garcia
 * @author Emiliano Puchaicela
 * @author Haydee Rojo Ovalle
 */
public class RunTicketMiner {
  // Constant
  private static final String CSV_DIR = "csvs/";

  private static final String USER_INPUT_CSV = CSV_DIR + "Customer_List_PA1.csv";
  private static final String VENUE_INPUT_CSV = CSV_DIR + "Venue_List_PA1.csv";
  private static final String EVENT_INPUT_CSV = CSV_DIR + "Event_List_PA1.csv";

  private static final String USER_OUTPUT_CSV = CSV_DIR + "Updated_Customer_List_PA2.csv";
  private static final String VENUE_OUTPUT_CSV = CSV_DIR + "Updated_Venue_List_PA2.csv";
  private static final String EVENT_OUTPUT_CSV = CSV_DIR + "Updated_Event_List_PA2.csv";

  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
  private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a");
  private static final Scanner KEYBOARD = new Scanner(System.in);

  private static final List<Venue> venues = new ArrayList<>();
  private static final List<Event> events = new ArrayList<>();
  private static final List<User> users = new ArrayList<>(); // non-admin users
  private static final List<Admin> admins = new ArrayList<>();
  private static User currentUser = null;

  /**
   * Entry point of the TicketMiner application.
   *
   * @param args command line arguments
   * @throws Exception if an unexpected error occurs
   */
  public static void main(String[] args) {
    System.out.println(
        "          d8,        d8b                                          d8,\n"
            + "   d8P   `8P         ?88                 d8P                     `8P\n"
            + "d888888P              88b             d888888P\n"
            + "  ?88'    88b d8888b  888  d88' d8888b  ?88'        88bd8b,d88b   "
            + "88b  88bd88b  d8888b  88bd88b\n"
            + "  88P     88Pd8P' `P  888bd8P' d8b_,dP  88P         88P'`?8P'?8b  "
            + "88P  88P' ?8bd8b_,dP  88P'  `\n"
            + "  88b    d88 88b     d88888b   88b      88b        d88  d88  88P "
            + "d88  d88   88P88b     d88\n"
            + "  `?8b  d88' `?888P'd88' `?88b,`?888P'  `?8b      d88' d88'  "
            + "88bd88' d88'   88b`?888P'd88'\n");
    menu();
  } // main

  /**
   * Writes a message to the system log file.
   *
   * @param message action description to log
   */
  public static void log(String message) {
    try (FileWriter writer = new FileWriter("log.txt", true)) {
      writer.write(LocalDateTime.now() + " - " + message + "\n");
    } catch (IOException e) {
      System.out.println("Error writing to log file.");
    }
  }

  /**
   * Returns the current username if someone is logged in.
   *
   * @return current username or system if no user is logged in
   */
  public static String getActorName() {
    if (currentUser != null) {
      return currentUser.getUserName();
    }
    return "system";
  }

  /** Clears the lists then reads the venue, user, and event CSV files. */
  public static void loadAllData() {
    venues.clear();
    events.clear();
    users.clear();
    admins.clear();

    readVenueCsv(VENUE_INPUT_CSV);
    readUserCsv(USER_INPUT_CSV);
    readEventCsv(EVENT_INPUT_CSV);
  }

  /** Saves current venue, user, and event data to output CSV files. */
  public static void saveAllData() {
    writeVenueCsv(VENUE_OUTPUT_CSV);
    writeUserCsv(USER_OUTPUT_CSV);
    writeEventCsv(EVENT_OUTPUT_CSV);
  }

  /**
   * Finds the first searchable item that matches the input.
   *
   * @param items searchable items
   * @param input search input
   * @param <T> searchable item type
   * @return first matching item, or null if there is no match
   */
  public static <T extends Searchable> T findMatch(List<T> items, String input) {
    for (T item : items) {
      if (item.matchesSearch(input)) {
        return item;
      }
    }

    return null;
  }

  /**
   * Prints all searchable items that match the input.
   *
   * @param items searchable items
   * @param input search input
   * @param <T> searchable item type
   * @return true when at least one item matched
   */
  public static <T extends Searchable> boolean printMatches(List<T> items, String input) {
    boolean found = false;

    for (T item : items) {
      if (item.matchesSearch(input)) {
        System.out.println(item);
        found = true;
      }
    }

    return found;
  }

  /**
   * Creates the correct venue subtype for a CSV row.
   *
   * @param venueId unique venue id
   * @param name venue name
   * @param type venue type
   * @param capacity total venue capacity
   * @param concertCapacity concert seating capacity
   * @param cost cost to rent the venue
   * @param vipPercent percentage of VIP seats
   * @param goldPercent percentage of gold seats
   * @param silverPercent percentage of silver seats
   * @param bronzePercent percentage of bronze seats
   * @param generalAdmissionPercent percentage of general admission seats
   * @param reservedExtraPercent percentage of extra reserved seats
   * @return matching venue subtype, or null when the type is unknown
   */
  private static Venue createVenue(
      int venueId,
      String name,
      String type,
      int capacity,
      int concertCapacity,
      double cost,
      double vipPercent,
      double goldPercent,
      double silverPercent,
      double bronzePercent,
      double generalAdmissionPercent,
      double reservedExtraPercent) {
    if (type.equalsIgnoreCase("Arena")) {
      return new Arena(
          venueId,
          name,
          "Arena",
          capacity,
          concertCapacity,
          cost,
          vipPercent,
          goldPercent,
          silverPercent,
          bronzePercent,
          generalAdmissionPercent,
          reservedExtraPercent);
    } else if (type.equalsIgnoreCase("Auditorium")) {
      return new Auditorium(
          venueId,
          name,
          "Auditorium",
          capacity,
          concertCapacity,
          cost,
          vipPercent,
          goldPercent,
          silverPercent,
          bronzePercent,
          generalAdmissionPercent,
          reservedExtraPercent);
    } else if (type.equalsIgnoreCase("OpenAir") || type.equalsIgnoreCase("Open Air")) {
      return new OpenAir(
          venueId,
          name,
          "OpenAir",
          capacity,
          concertCapacity,
          cost,
          vipPercent,
          goldPercent,
          silverPercent,
          bronzePercent,
          generalAdmissionPercent,
          reservedExtraPercent);
    } else if (type.equalsIgnoreCase("Stadium")) {
      return new Stadium(
          venueId,
          name,
          "Stadium",
          capacity,
          concertCapacity,
          cost,
          vipPercent,
          goldPercent,
          silverPercent,
          bronzePercent,
          generalAdmissionPercent,
          reservedExtraPercent);
    }

    return null;
  }

  /**
   * Escapes a value for CSV output.
   *
   * @param value value to escape
   * @return escaped value
   */
  private static String csvEscape(String value) {
    if (value == null) {
      return "";
    }

    if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
      return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    return value;
  }

  /**
   * Writes one CSV line.
   *
   * @param writer output writer
   * @param values values to write
   * @throws IOException if the line cannot be written
   */
  private static void writeCsvLine(FileWriter writer, String... values) throws IOException {
    for (int i = 0; i < values.length; i++) {
      writer.write(csvEscape(values[i]));
      if (i < values.length - 1) {
        writer.write(",");
      }
    }
    writer.write("\n");
  }

  /**
   * Parses an integer field from a CSV row.
   *
   * @param value raw field value
   * @param fieldName field name for error messages
   * @param line original CSV row
   * @return parsed integer
   */
  private static int parseIntField(String value, String fieldName, String line) {
    try {
      return Integer.parseInt(value.trim());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid " + fieldName + " in row: " + line);
    }
  }

  /**
   * Parses a decimal field from a CSV row.
   *
   * @param value raw field value
   * @param fieldName field name for error messages
   * @param line original CSV row
   * @return parsed decimal
   */
  private static double parseDoubleField(String value, String fieldName, String line) {
    try {
      return Double.parseDouble(value.trim());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid " + fieldName + " in row: " + line);
    }
  }

  private static int parseOptionalIntField(
      String[] fields, String[] header, String columnName, String line) {
    int index = findHeaderIndex(header, columnName);

    if (index < 0 || index >= fields.length || fields[index].trim().isEmpty()) {
      return 0;
    }

    return parseIntField(fields[index], columnName, line);
  }

  /**
   * Reads a decimal value from the keyboard.
   *
   * @param keyboard scanner used for input
   * @param prompt prompt to display
   * @return parsed decimal
   */
  public static double readDouble(Scanner keyboard, String prompt) {
    while (true) {
      System.out.print(prompt);
      String input = keyboard.nextLine().trim();

      try {
        return Double.parseDouble(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid number. Please enter a valid decimal value.");
      }
    }
  }

  /**
   * Reads a whole number from the keyboard.
   *
   * @param keyboard scanner used for input
   * @param prompt prompt to display
   * @return parsed integer
   */
  public static int readInt(Scanner keyboard, String prompt) {
    while (true) {
      System.out.print(prompt);
      String input = keyboard.nextLine().trim();

      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid number. Please enter a whole number.");
      }
    }
  }

  /**
   * Reads a yes/no value from the keyboard.
   *
   * @param keyboard scanner used for input
   * @param prompt prompt to display
   * @return parsed boolean
   */
  public static boolean readBoolean(Scanner keyboard, String prompt) {
    while (true) {
      System.out.print(prompt);
      String input = keyboard.nextLine().trim().toLowerCase();

      if (input.equals("true") || input.equals("t") || input.equals("yes") || input.equals("y")) {
        return true;
      }
      if (input.equals("false") || input.equals("f") || input.equals("no") || input.equals("n")) {
        return false;
      }

      System.out.println("Invalid input. Please enter true/false or yes/no.");
    }
  }

  /**
   * Reads a date from the keyboard.
   *
   * @param keyboard scanner used for input
   * @param prompt prompt to display
   * @return parsed date
   */
  public static LocalDate readDate(Scanner keyboard, String prompt) {
    while (true) {
      System.out.print(prompt + " (YYYY-MM-DD): ");
      String input = keyboard.nextLine().trim();

      try {
        return LocalDate.parse(input);
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date. Please use YYYY-MM-DD.");
      }
    }
  }

  /**
   * Reads a time from the keyboard.
   *
   * @param keyboard scanner used for input
   * @param prompt prompt to display
   * @return parsed time
   */
  public static LocalTime readTime(Scanner keyboard, String prompt) {
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

    while (true) {
      System.out.print(prompt + " (HH:MM): ");
      String input = keyboard.nextLine().trim();

      try {
        return LocalTime.parse(input, timeFormatter);
      } catch (DateTimeParseException e) {
        System.out.println("Invalid time. Please use HH:MM in 24-hour format.");
      }
    }
  }

  /** Displays the main menu and allows users to register, login, or exit the system. */
  public static void menu() {

    loadAllData();

    System.out.println("Please select a menu option.");
    System.out.println("\n1: Register \n2: Login \n3: EXIT");
    String userInput = KEYBOARD.nextLine().strip().toLowerCase().trim();

    while (!userInput.equals("exit")) {

      switch (userInput) {
        case "1" -> {
          System.out.println("Please select the type of user you would like to create.");
          System.out.println("1: Customer \n2: Organizer");
          userInput = KEYBOARD.nextLine().trim();
          switch (userInput) {
            case "1" -> registerCustomer(KEYBOARD);
            case "2" -> registerOrganizer(KEYBOARD);
            default -> System.out.println("Invalid option entered.");
          }
        }

        case "2" -> {
          System.out.println("Please login into your account.");
          User loggedInUser = loginUser(KEYBOARD);

          if (loggedInUser != null) {
            currentUser = loggedInUser;
            System.out.println("Login successful. Welcome " + loggedInUser.getFullName());
            loggedInUser.userMenu();
            currentUser = null;
          } else {
            System.out.println("Invalid username or password.");
          }
        }
        case "3", "exit" -> {
          saveAllData();
          log("Program exited");
          userInput = "exit";
          continue;
        }
        default -> System.out.println("Invalid option entered.");
      } // switch

      System.out.println("Please select a menu option.");
      System.out.println("\n1: Register \n2: Login \n3: EXIT");
      userInput = KEYBOARD.nextLine().trim().toLowerCase();
    } // while
    System.out.println("thank you for visiting! ");
  }

  /**
   * Displays the venue management menu. Users can add, view, search, update, or delete venues.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void manageVenue(Scanner keyboard) {

    String input = "";

    while (!input.equals("6")) {
      System.out.println("Please select an option");
      System.out.println("1: Add Venue");
      System.out.println("2: View Venues");
      System.out.println("3: Search Venue");
      System.out.println("4: Update Venue");
      System.out.println("5: Delete Venue");
      System.out.println("6: Back");

      input = keyboard.nextLine().trim();

      switch (input) {
        case "1" -> addVenue(keyboard);
        case "2" -> viewVenueMenu(keyboard);
        case "3" -> searchVenue(keyboard);
        case "4" -> updateVenue(keyboard);
        case "5" -> deleteVenue(keyboard);
        case "6" -> System.out.println("Going back.");
        default -> System.out.println("Invalid option.");
      }
    }
  }

  /**
   * Prompts the user for venue information and adds the venue to the system.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void addVenue(Scanner keyboard) {
    int venueId = readInt(keyboard, "Enter venue ID: ");

    System.out.print("Enter venue name: ");
    String name = keyboard.nextLine().trim();

    System.out.print("Enter venue type (Arena, Auditorium, OpenAir, Stadium): ");
    String type = keyboard.nextLine().trim();

    int capacity = readInt(keyboard, "Enter capacity: ");
    int concertCapacity = readInt(keyboard, "Enter concert capacity: ");
    double cost = readDouble(keyboard, "Enter cost: ");
    double vipPercent = readDouble(keyboard, "Enter VIP percent: ");
    double goldPercent = readDouble(keyboard, "Enter Gold percent: ");
    double silverPercent = readDouble(keyboard, "Enter Silver percent: ");
    double bronzePercent = readDouble(keyboard, "Enter Bronze percent: ");
    double generalAdmissionPercent = readDouble(keyboard, "Enter General Admission percent: ");
    double reservedExtraPercent = readDouble(keyboard, "Enter Reserved Extra percent: ");

    Venue newVenue =
        createVenue(
            venueId,
            name,
            type,
            capacity,
            concertCapacity,
            cost,
            vipPercent,
            goldPercent,
            silverPercent,
            bronzePercent,
            generalAdmissionPercent,
            reservedExtraPercent);

    if (newVenue == null) {
      System.out.println("Invalid venue type.");
      return;
    }

    venues.add(newVenue);
    writeVenueCsv(VENUE_OUTPUT_CSV);
    log(getActorName() + " added venue ID " + venueId + " named " + name);
    System.out.println("Venue added successfully.");
  }

  /** Displays all venues currently stored in the system. */
  public static void viewAllVenues() {

    if (venues.isEmpty()) {
      System.out.println("No venues found.");
      return;
    }

    log(getActorName() + " displayed all venues");

    for (Venue venue : venues) {
      System.out.println(venue);
    }
  }

  /**
   * Searches for venues based on ID, name, or type.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void searchVenue(Scanner keyboard) {

    System.out.println("Enter venue ID, name, or type:");
    String input = keyboard.nextLine().trim();

    boolean found = printMatches(venues, input);

    if (found) {
      log(getActorName() + " searched for venue " + input);
    } else {
      System.out.println("Venue not found.");
      log(getActorName() + " searched for venue " + input + " but no match was found");
    }
  }

  /**
   * Finds a venue by matching ID, name, or type.
   *
   * @param input search term
   * @return matching venue or null if not found
   */
  public static Venue findVenue(String input) {

    return findMatch(venues, input);
  }

  /**
   * Updates venue information such as name, capacity, cost, or location.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void updateVenue(Scanner keyboard) {

    System.out.println("Enter venue ID, name, or type to update:");
    String input = keyboard.nextLine().trim();

    Venue venue = findVenue(input);

    if (venue == null) {
      System.out.println("Venue not found.");
      log(getActorName() + " attempted to update venue " + input + " but no match was found");
      return;
    }

    System.out.println("Found venue: " + venue);

    System.out.println("1: Change Name");
    System.out.println("2: Change Capacity");
    System.out.println("3: Change Cost");

    String choice = keyboard.nextLine().trim();

    switch (choice) {
      case "1" -> {
        System.out.print("Enter new name: ");
        venue.setVenueName(keyboard.nextLine().trim());
        log(getActorName() + " updated venue name for venue ID " + venue.getVenueId());
      }

      case "2" -> {
        venue.setCapacity(readInt(keyboard, "Enter new capacity: "));
        log(getActorName() + " updated venue capacity for venue ID " + venue.getVenueId());
      }

      case "3" -> {
        venue.setCost(readDouble(keyboard, "Enter new cost: "));
        log(getActorName() + " updated venue cost for venue ID " + venue.getVenueId());
      }

      default -> {
        System.out.println("Invalid option.");
        return;
      }
    }

    writeVenueCsv(VENUE_OUTPUT_CSV);
    System.out.println("Venue updated successfully.");
  }

  /**
   * Deletes a venue from the system after confirmation.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void deleteVenue(Scanner keyboard) {

    System.out.println("Enter venue ID, name, or type to delete:");
    String input = keyboard.nextLine().trim();

    Venue venue = findVenue(input);

    if (venue == null) {
      System.out.println("Venue not found.");
      log(getActorName() + " attempted to delete venue " + input + " but no match was found");
      return;
    }

    System.out.println("Found venue: " + venue);

    System.out.print("Confirm delete? (yes/no): ");
    String confirm = keyboard.nextLine().trim();

    if (confirm.equalsIgnoreCase("yes")) {
      venues.remove(venue);
      writeVenueCsv(VENUE_OUTPUT_CSV);
      log(getActorName() + " deleted venue ID " + venue.getVenueId());
      System.out.println("Venue deleted successfully.");
    } else {
      System.out.println("Delete cancelled.");
    }
  }

  /**
   * Displays the venue viewing submenu.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void viewVenueMenu(Scanner keyboard) {

    String input = "";

    while (!input.equals("3")) {
      System.out.println("View Venues");
      System.out.println("1: Display all venues");
      System.out.println("2: Search venue");
      System.out.println("3: Back");

      input = keyboard.nextLine().trim();

      switch (input) {
        case "1" -> viewAllVenues();
        case "2" -> searchVenue(keyboard);
        case "3" -> System.out.println("Going back.");
        default -> System.out.println("Invalid option.");
      }
    }
  }

  /**
   * Displays the event management menu. Users can add, view, update, or delete events.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void manageEvent(Scanner keyboard) {
    String input = "";

    while (!input.equals("5")) {
      System.out.println("Please select an option");
      System.out.println("1: Add Event");
      System.out.println("2: View Event");
      System.out.println("3: Update Event");
      System.out.println("4: Delete Event");
      System.out.println("5: Back");

      input = keyboard.nextLine().trim();

      switch (input) {
        case "1" -> addEvent(keyboard);
        case "2" -> viewEventMenu(keyboard);
        case "3" -> updateEvent(keyboard);
        case "4" -> deleteEvent(keyboard);
        case "5" -> System.out.println("Going back.");
        default -> System.out.println("Invalid option.");
      }
    }
  }

  /**
   * Prompts the user to enter event information and adds the event to the system.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void addEvent(Scanner keyboard) {
    final int id = readInt(keyboard, "Enter event ID: ");

    System.out.print("Enter event name: ");
    String name = keyboard.nextLine().trim();

    System.out.print("Enter event type (Concert, Sport, Special): ");
    String type = keyboard.nextLine().trim();

    LocalDate date = readDate(keyboard, "Enter event date");

    LocalTime time = readTime(keyboard, "Enter event time");

    double vipPrice = readDouble(keyboard, "Enter VIP price: ");

    double goldPrice = readDouble(keyboard, "Enter Gold price: ");

    double silverPrice = readDouble(keyboard, "Enter Silver price: ");

    double bronzePrice = readDouble(keyboard, "Enter Bronze price: ");

    double generalAdmissionPrice = readDouble(keyboard, "Enter General Admission price: ");

    String firstDetail = null;
    String secondDetail = null;
    String thirdDetail = null;

    if (type.equalsIgnoreCase("Concert")) {
      System.out.print("Enter artist: ");
      firstDetail = keyboard.nextLine().trim();

      System.out.print("Enter genre: ");
      secondDetail = keyboard.nextLine().trim();

    } else if (type.equalsIgnoreCase("Sport")) {
      System.out.print("Enter team1: ");
      firstDetail = keyboard.nextLine().trim();

      System.out.print("Enter team2: ");
      secondDetail = keyboard.nextLine().trim();

      System.out.print("Enter league: ");
      thirdDetail = keyboard.nextLine().trim();

    } else if (type.equalsIgnoreCase("Special")) {
      System.out.print("Enter description: ");
      firstDetail = keyboard.nextLine().trim();

      System.out.print("Enter category: ");
      secondDetail = keyboard.nextLine().trim();

    } else {
      System.out.println("Invalid event type.");
      return;
    }

    Event newEvent =
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
            generalAdmissionPrice,
            firstDetail,
            secondDetail,
            thirdDetail);
    if (newEvent == null) {
      System.out.println("Invalid event type.");
      return;
    }

    events.add(newEvent);
    writeEventCsv(EVENT_OUTPUT_CSV);
    log(getActorName() + " added event ID " + id + " named " + name);
    System.out.println("Event added successfully.");
  }

  /** Displays all events stored in the system. */
  public static void viewAllEvents() {
    if (events.isEmpty()) {
      System.out.println("No events found.");
      return;
    }

    log(getActorName() + " displayed all events");

    for (Event event : events) {
      System.out.println(event);
    }
  }

  static List<Event> getEvents() {
    return events;
  }

  /**
   * Searches for an event using ID, name, or date.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void searchEvent(Scanner keyboard) {
    System.out.println("Enter event ID, name, or date:");
    String input = keyboard.nextLine().trim();

    boolean found = printMatches(events, input);

    if (found) {
      log(getActorName() + " searched for event " + input);
    } else {
      System.out.println("Event not found.");
      log(getActorName() + " searched for event " + input + " but no match was found");
    }
  }

  /**
   * Finds an event by matching ID, name, or date.
   *
   * @param input search term
   * @return matching event or null if not found
   */
  public static Event findEvent(String input) {
    return findMatch(events, input);
  }

  /**
   * Updates event information such as name, date, or time of the event.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void updateEvent(Scanner keyboard) {
    System.out.println("Enter event ID, name, or date to update:");
    String input = keyboard.nextLine().trim();

    Event event = findEvent(input);

    if (event == null) {
      System.out.println("Event not found.");
      log(getActorName() + " attempted to update event " + input + " but no match was found");
      return;
    }

    System.out.println("Found event: " + event);
    System.out.println("1: Change Name");
    System.out.println("2: Change Date and Time");
    System.out.println("3: Change Capacity");

    String choice = keyboard.nextLine().trim();

    switch (choice) {
      case "1" -> {
        System.out.print("Enter new event name: ");
        event.setEventName(keyboard.nextLine().trim());
        writeEventCsv(EVENT_OUTPUT_CSV);
        log(getActorName() + " updated event name for event ID " + event.getEventId());
        System.out.println("Event name updated successfully.");
      }
      case "2" -> {
        LocalDate newDate = readDate(keyboard, "Enter event date");
        LocalTime newTime = readTime(keyboard, "Enter event time");

        event.setDate(newDate);
        event.setTime(newTime);

        writeEventCsv(EVENT_OUTPUT_CSV);
        log(getActorName() + " updated event date/time for event ID " + event.getEventId());
        System.out.println("Event date and time updated successfully.");
      }
      case "3" -> updateEventCapacity(keyboard, event);
      default -> System.out.println("Invalid option.");
    }
  }

  private static void updateEventCapacity(Scanner keyboard, Event event) {
    int newCapacity = readInt(keyboard, "Enter new event capacity: ");

    try {
      validateEventCapacity(event, newCapacity);
      event.setTotalCapacity(newCapacity);
      writeEventCsv(EVENT_OUTPUT_CSV);
      log(getActorName() + " updated event capacity for event ID " + event.getEventId());
      System.out.println("Event capacity updated successfully.");
    } catch (NotEnoughTicketsException e) {
      System.out.println(e.getMessage());
      log(
          getActorName()
              + " attempted to set event ID "
              + event.getEventId()
              + " capacity below sold tickets");
    }
  }

  private static void validateEventCapacity(Event event, int newCapacity)
      throws NotEnoughTicketsException {
    if (newCapacity < event.getTotalTicketsSold()) {
      throw new NotEnoughTicketsException(
          "Capacity cannot be lower than tickets already sold: "
              + event.getTotalTicketsSold());
    }
  }

  /**
   * Removes an event from the system after confirmation.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void deleteEvent(Scanner keyboard) {
    System.out.println("Enter event ID, name, or date to delete:");
    String input = keyboard.nextLine().trim();

    Event event = findEvent(input);

    if (event == null) {
      System.out.println("Event not found.");
      log(getActorName() + " attempted to delete event " + input + " but no match was found");
      return;
    }

    System.out.println("Found event: " + event);
    System.out.print("Confirm delete? (yes/no): ");
    String confirm = keyboard.nextLine().trim();

    if (confirm.equalsIgnoreCase("yes")) {
      events.remove(event);
      writeEventCsv(EVENT_OUTPUT_CSV);
      log(getActorName() + " deleted event ID " + event.getEventId());
      System.out.println("Event deleted successfully.");
    } else {
      System.out.println("Delete cancelled.");
    }
  }

  /**
   * Displays the event viewing submenu. Users can display all events or search for a specific
   * event.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void viewEventMenu(Scanner keyboard) {
    String input = "";

    while (!input.equals("3")) {
      System.out.println("View Events");
      System.out.println("1: Display all events");
      System.out.println("2: Search for an event");
      System.out.println("3: Back");

      input = keyboard.nextLine().trim();

      switch (input) {
        case "1" -> viewAllEvents();
        case "2" -> searchEvent(keyboard);
        case "3" -> System.out.println("Going back.");
        default -> System.out.println("Invalid option.");
      }
    }
  }

  /**
   * Finds each required column in a CSV header row.
   *
   * @param header CSV header columns
   * @param params required column names
   * @return indexes of the required columns
   */
  public static int[] headerIndexes(String[] header, String[] params) {
    int[] headerIndexes = new int[params.length];

    for (int i = 0; i < params.length; i++) {
      headerIndexes[i] = findHeaderIndex(header, params[i]);

      if (headerIndexes[i] < 0) {
        throw new IllegalArgumentException("Missing required CSV column: " + params[i]);
      }
    }

    return headerIndexes;
  }

  private static int findHeaderIndex(String[] header, String columnName) {
    for (int i = 0; i < header.length; i++) {
      if (header[i].trim().equalsIgnoreCase(columnName)) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Reads user information from a CSV file and loads users into the system.
   *
   * @param filePath path to the user CSV file
   */
  public static void readUserCsv(String filePath) {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
      String line = reader.readLine(); // skip header
      String[] header = line.split(",", -1);
      String[] params = {
        "ID",
        "First Name",
        "Last Name",
        "Username",
        "Password",
        "User Type",
        "Money Available",
        "TicketMiner Membership",
        "Concerts Purchased"
      };
      int[] headerIndexes = headerIndexes(header, params);

      while ((line = reader.readLine()) != null) {
        String[] fields = line.split(",", -1);

        if (fields.length < 6) {
          System.out.println("Skipping bad user row: " + line);
          continue;
        }

        String id = fields[headerIndexes[0]].trim();
        String firstName = fields[headerIndexes[1]].trim();
        String lastName = fields[headerIndexes[2]].trim();
        String username = fields[headerIndexes[3]].trim();
        String password = fields[headerIndexes[4]].trim();
        String userType = fields[headerIndexes[5]].trim();

        if (userType.equalsIgnoreCase("customer")) {
          if (fields.length < 8) {
            System.out.println("Skipping incomplete customer row: " + line);
            continue;
          }

          String moneyAvailable = fields[headerIndexes[6]].trim();
          String membership = fields[headerIndexes[7]].trim();
          String concertsPurchased = fields[headerIndexes[8]].trim();

          Customer customer =
              new Customer(
                  parseIntField(id, "user id", line),
                  firstName,
                  lastName,
                  username,
                  password,
                  userType,
                  KEYBOARD,
                  parseDoubleField(moneyAvailable, "money available", line),
                  Boolean.parseBoolean(membership),
                  parseIntField(concertsPurchased, "concerts purchased", line));
          users.add(customer);
        } else if (userType.equalsIgnoreCase("organizer")) {
          Organizer organizer =
              new Organizer(
                  parseIntField(id, "user id", line),
                  firstName,
                  lastName,
                  username,
                  password,
                  userType,
                  KEYBOARD);
          users.add(organizer);
        } else if (userType.equalsIgnoreCase("admin")) {
          Admin admin =
              new Admin(
                  parseIntField(id, "user id", line),
                  firstName,
                  lastName,
                  username,
                  password,
                  userType,
                  KEYBOARD,
                  users,
                  admins);
          admins.add(admin);
        } else {
          System.out.println("Invalid user type for ID: " + id);
        }
      }
    } catch (IOException e) {
      System.out.println("Error reading user CSV: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Reads venue information from a CSV file and loads venues into the system.
   *
   * @param filePath path to the venue CSV file
   */
  public static void readVenueCsv(String filePath) {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
      String line = reader.readLine(); // skip header
      String[] header = line.split(",", -1);
      String[] params = {
        "ID",
        "Name",
        "Type",
        "Capacity",
        "Concert Capacity",
        "Cost",
        "VIP Percent",
        "Gold Percent",
        "Silver Percent",
        "Bronze Percent",
        "General Admission Percent",
        "Reserved Extra Percent"
      };
      int[] headerIndexes = headerIndexes(header, params);

      while ((line = reader.readLine()) != null) {
        String[] fields = line.split(",", -1);

        if (fields.length < 12) {
          System.out.println("Skipping bad venue row: " + line);
          continue;
        }

        int id = parseIntField(fields[headerIndexes[0]], "venue id", line);
        String name = fields[headerIndexes[1]].trim();
        String type = fields[headerIndexes[2]].trim();
        int capacity = parseIntField(fields[headerIndexes[3]], "capacity", line);
        int concertCapacity = parseIntField(fields[headerIndexes[4]], "concert capacity", line);
        double cost = parseDoubleField(fields[headerIndexes[5]], "cost", line);
        double vipPercent = parseDoubleField(fields[headerIndexes[6]], "VIP percent", line);
        double goldPercent = parseDoubleField(fields[headerIndexes[7]], "Gold percent", line);
        double silverPercent = parseDoubleField(fields[headerIndexes[8]], "Silver percent", line);
        double bronzePercent = parseDoubleField(fields[headerIndexes[9]], "Bronze percent", line);
        double generalAdmissionPercent =
            parseDoubleField(fields[headerIndexes[10]], "General Admission percent", line);
        double reservedExtraPercent =
            parseDoubleField(fields[headerIndexes[11]], "Reserved Extra percent", line);

        Venue venue =
            createVenue(
                id,
                name,
                type,
                capacity,
                concertCapacity,
                cost,
                vipPercent,
                goldPercent,
                silverPercent,
                bronzePercent,
                generalAdmissionPercent,
                reservedExtraPercent);

        if (venue != null) {
          venues.add(venue);
        } else {
          System.out.println("Invalid venue type for ID: " + id);
        }
      }
    } catch (IOException e) {
      System.out.println("Error reading venue CSV: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Reads event information from a CSV file and loads events into the system.
   *
   * @param filePath path to the event CSV file
   */
  public static void readEventCsv(String filePath) {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
      String line = reader.readLine(); // skip header
      String[] header = line.split(",", -1);
      String[] params = {
        "ID",
        "Name",
        "Type",
        "Date",
        "Time",
        "VIP Price",
        "Gold Price",
        "Silver Price",
        "Bronze Price",
        "General Admission Price"
      };
      int[] headerIndexes = headerIndexes(header, params);

      while ((line = reader.readLine()) != null) {
        String[] fields = line.split(",", -1);

        if (fields.length < 10) {
          System.out.println("Skipping bad event row: " + line);
          continue;
        }

        int id = parseIntField(fields[headerIndexes[0]], "event id", line);
        String name = fields[headerIndexes[1]].trim();
        String type = fields[headerIndexes[2]].trim();
        String date = fields[headerIndexes[3]].trim();
        String time = fields[headerIndexes[4]].trim();
        double vipPrice = parseDoubleField(fields[headerIndexes[5]], "VIP price", line);
        double goldPrice = parseDoubleField(fields[headerIndexes[6]], "gold price", line);
        double silverPrice = parseDoubleField(fields[headerIndexes[7]], "silver price", line);
        double bronzePrice = parseDoubleField(fields[headerIndexes[8]], "bronze price", line);
        double generalAdmissionPrice =
            parseDoubleField(fields[headerIndexes[9]], "general admission price", line);

        try {
          LocalDate eventDate = LocalDate.parse(date, DATE_FORMAT);
          LocalTime eventTime = LocalTime.parse(time, TIME_FORMAT);

          Event event =
              EventFactory.createEvent(
                  id,
                  name,
                  type,
                  eventDate,
                  eventTime,
                  vipPrice,
                  goldPrice,
                  silverPrice,
                  bronzePrice,
                  generalAdmissionPrice,
                  null,
                  null,
                  null);

          if (event != null) {
            event.setTotalCapacity(parseOptionalIntField(fields, header, "Total Capacity", line));
            event.setVipSold(parseOptionalIntField(fields, header, "VIP Sold", line));
            event.setGoldSold(parseOptionalIntField(fields, header, "Gold Sold", line));
            event.setSilverSold(parseOptionalIntField(fields, header, "Silver Sold", line));
            event.setBronzeSold(parseOptionalIntField(fields, header, "Bronze Sold", line));
            event.setGeneralSold(parseOptionalIntField(fields, header, "General Sold", line));
            events.add(event);
          } else {
            System.out.println("Invalid event type for ID: " + id);
          }
        } catch (Exception e) {
          System.out.println("Skipping bad event row: " + line);
        }
      }
    } catch (IOException e) {
      System.out.println("Error reading event CSV: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Registers a new customer and adds them to the system.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void registerCustomer(Scanner keyboard) {

    System.out.print("Enter first name: ");
    String firstName = keyboard.nextLine().trim();

    System.out.print("Enter last name: ");
    String lastName = keyboard.nextLine().trim();

    System.out.print("Enter username: ");
    String username = keyboard.nextLine().trim();

    System.out.print("Enter password: ");
    String password = keyboard.nextLine().trim();

    double money = readDouble(keyboard, "Enter money available: ");
    boolean membership = readBoolean(keyboard, "Membership (true/false): ");
    int concertsPurchased = readInt(keyboard, "Enter concerts purchased: ");

    int newId = users.size() + admins.size() + 1;

    Customer customer =
        new Customer(
            newId,
            firstName,
            lastName,
            username,
            password,
            "customer",
            keyboard,
            money,
            membership,
            concertsPurchased);

    users.add(customer);
    writeUserCsv(USER_OUTPUT_CSV);
    log("Registered new customer " + username + " with ID " + newId);
    System.out.println("Customer registered successfully.");
  }

  /**
   * Registers a new organizer and adds them to the system.
   *
   * @param keyboard Scanner used to read user input
   */
  public static void registerOrganizer(Scanner keyboard) {

    System.out.print("Enter first name: ");
    String firstName = keyboard.nextLine().trim();

    System.out.print("Enter last name: ");
    String lastName = keyboard.nextLine().trim();

    System.out.print("Enter username: ");
    String username = keyboard.nextLine().trim();

    System.out.print("Enter password: ");
    String password = keyboard.nextLine().trim();

    int newId = users.size() + admins.size() + 1;

    Organizer organizer =
        new Organizer(newId, firstName, lastName, username, password, "organizer", keyboard);

    users.add(organizer);
    writeUserCsv(USER_OUTPUT_CSV);
    log("Registered new organizer " + username + " with ID " + newId);
    System.out.println("Organizer registered successfully.");
  }

  /**
   * Authenticates a user based on username and password.
   *
   * @param keyboard Scanner used to read user input
   * @return the authenticated User object, or null if login fails
   */
  public static User loginUser(Scanner keyboard) {

    System.out.print("Enter username: ");
    String username = keyboard.nextLine().trim();

    System.out.print("Enter password: ");
    String password = keyboard.nextLine().trim();

    for (User user : users) {
      if (user.matchesUsername(username) && user.checkPassword(password)) {
        log("User " + username + " logged in");
        return user;
      }
    }

    for (Admin admin : admins) {
      if (admin.matchesUsername(username) && admin.checkPassword(password)) {
        log("Admin " + username + " logged in");
        return admin;
      }
    }

    log("Failed login attempt for username " + username);
    return null;
  }

  /**
   * Writes users to a CSV file.
   *
   * @param filePath path to the user CSV file
   */
  public static void writeUserCsv(String filePath) {
    try (FileWriter writer = new FileWriter(filePath)) {
      writeCsvLine(
          writer,
          "ID",
          "First Name",
          "Last Name",
          "Username",
          "Password",
          "User Type",
          "Money Available",
          "TicketMiner Membership",
          "Concerts Purchased");

      for (User user : users) {
        String moneyAvailable = "";
        String membership = "";
        String concertsPurchased = "";

        if (user instanceof Customer customer) {
          moneyAvailable = String.valueOf(customer.getMoneyAvailable());
          membership = String.valueOf(customer.isMembership());
          concertsPurchased = String.valueOf(customer.getConcertsPurchased());
        }

        writeCsvLine(
            writer,
            String.valueOf(user.getUserId()),
            user.getFirstName(),
            user.getLastName(),
            user.getUserName(),
            user.getPassword(),
            user.getUserType(),
            moneyAvailable,
            membership,
            concertsPurchased);
      }

      for (Admin admin : admins) {
        writeCsvLine(
            writer,
            String.valueOf(admin.getUserId()),
            admin.getFirstName(),
            admin.getLastName(),
            admin.getUserName(),
            admin.getPassword(),
            admin.getUserType(),
            "",
            "",
            "");
      }

    } catch (IOException e) {
      System.out.println("Error writing user CSV: " + e.getMessage());
    }
  }

  /**
   * Writes venues to a CSV file.
   *
   * @param filePath path to the venue CSV file
   */
  public static void writeVenueCsv(String filePath) {
    try (FileWriter writer = new FileWriter(filePath)) {
      writeCsvLine(
          writer,
          "ID",
          "Name",
          "Type",
          "Capacity",
          "Concert Capacity",
          "Cost",
          "VIP Percent",
          "Gold Percent",
          "Silver Percent",
          "Bronze Percent",
          "General Admission Percent",
          "Reserved Extra Percent");

      for (Venue venue : venues) {
        writeCsvLine(
            writer,
            String.valueOf(venue.getVenueId()),
            venue.getVenueName(),
            venue.getVenueType(),
            String.valueOf(venue.getCapacity()),
            String.valueOf(venue.getConcertCapacity()),
            String.valueOf(venue.getCost()),
            String.valueOf(venue.getVipPercent()),
            String.valueOf(venue.getGoldPercent()),
            String.valueOf(venue.getSilverPercent()),
            String.valueOf(venue.getBronzePercent()),
            String.valueOf(venue.getGeneralAdmissionPercent()),
            String.valueOf(venue.getReservedExtraPercent()));
      }

    } catch (IOException e) {
      System.out.println("Error writing venue CSV: " + e.getMessage());
    }
  }

  /**
   * Writes events to a CSV file.
   *
   * @param filePath path to the event CSV file
   */
  public static void writeEventCsv(String filePath) {
    try (FileWriter writer = new FileWriter(filePath)) {
      writeCsvLine(
          writer,
          "ID",
          "Type",
          "Name",
          "Date",
          "Time",
          "VIP Price",
          "Gold Price",
          "Silver Price",
          "Bronze Price",
          "General Admission Price",
          "Total Capacity",
          "VIP Sold",
          "Gold Sold",
          "Silver Sold",
          "Bronze Sold",
          "General Sold");

      for (Event event : events) {
        writeCsvLine(
            writer,
            String.valueOf(event.getEventId()),
            event.getEventType(),
            event.getEventName(),
            event.getDate().format(DATE_FORMAT),
            event.getTime().format(TIME_FORMAT),
            String.valueOf(event.getVipPrice()),
            String.valueOf(event.getGoldPrice()),
            String.valueOf(event.getSilverPrice()),
            String.valueOf(event.getBronzePrice()),
            String.valueOf(event.getGeneralAdmissionPrice()),
            String.valueOf(event.getTotalCapacity()),
            String.valueOf(event.getVipSold()),
            String.valueOf(event.getGoldSold()),
            String.valueOf(event.getSilverSold()),
            String.valueOf(event.getBronzeSold()),
            String.valueOf(event.getGeneralSold()));
      }

    } catch (IOException e) {
      System.out.println("Error writing event CSV: " + e.getMessage());
    }
  }
} // RunTicketMiner

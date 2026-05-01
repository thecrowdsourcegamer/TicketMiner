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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Main driver class for the TicketMiner system.
 *
 * This class starts the application, loads CSV data,
 * and provides menu navigation for users to manage
 * venues, events, and accounts.
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

    private static final String USER_OUTPUT_CSV = CSV_DIR + "Updated_Customer_List_PA1.csv";
    private static final String VENUE_OUTPUT_CSV = CSV_DIR + "Updated_Venue_List_PA1.csv";
    private static final String EVENT_OUTPUT_CSV = CSV_DIR + "Updated_Event_List_PA1.csv";

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
        System.out.println("""
          d8,        d8b                                          d8,                          
   d8P   `8P         ?88                 d8P                     `8P                           
d888888P              88b             d888888P                                                 
  ?88'    88b d8888b  888  d88' d8888b  ?88'        88bd8b,d88b   88b  88bd88b  d8888b  88bd88b
  88P     88Pd8P' `P  888bd8P' d8b_,dP  88P         88P'`?8P'?8b  88P  88P' ?8bd8b_,dP  88P'  `
  88b    d88 88b     d88888b   88b      88b        d88  d88  88P d88  d88   88P88b     d88     
  `?8b  d88' `?888P'd88' `?88b,`?888P'  `?8b      d88' d88'  88bd88' d88'   88b`?888P'd88'     
""");
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

    /**
     * clears the array lists then reads the CSVs of Venue, User, and Events. 
     */
    public static void loadAllData() {
        venues.clear();
        events.clear();
        users.clear();
        admins.clear();

        readVenueCSV(VENUE_INPUT_CSV);
        readUserCSV(USER_INPUT_CSV);
        readEventCSV(EVENT_INPUT_CSV);
    }

     /**
     * Save written data to new output CSVs
     */
    public static void saveAllData() {
        writeVenueCSV(VENUE_OUTPUT_CSV);
        writeUserCSV(USER_OUTPUT_CSV);
        writeEventCSV(EVENT_OUTPUT_CSV);
    }

    /** 
     * @param venueId
     * @param name
     * @param type
     * @param capacity
     * @param concertCapacity
     * @param cost
     * @param vipPercent
     * @param goldPercent
     * @param silverPercent
     * @param bronzePercent
     * @param generalAdmissionPercent
     * @param reservedExtraPercent
     * @return Venue
     */
    private static Venue createVenue(int venueId, String name, String type, int capacity,
                                    int concertCapacity, double cost,
                                    double vipPercent, double goldPercent, double silverPercent,
                                    double bronzePercent, double generalAdmissionPercent,
                                    double reservedExtraPercent) {
        if (type.equalsIgnoreCase("Arena")) {
            return new Arena(venueId, name, "Arena", capacity, concertCapacity, cost,
                    vipPercent, goldPercent, silverPercent, bronzePercent,
                    generalAdmissionPercent, reservedExtraPercent);
        } else if (type.equalsIgnoreCase("Auditorium")) {
            return new Auditorium(venueId, name, "Auditorium", capacity, concertCapacity, cost,
                    vipPercent, goldPercent, silverPercent, bronzePercent,
                    generalAdmissionPercent, reservedExtraPercent);
        } else if (type.equalsIgnoreCase("OpenAir") || type.equalsIgnoreCase("Open Air")) {
            return new OpenAir(venueId, name, "OpenAir", capacity, concertCapacity, cost,
                    vipPercent, goldPercent, silverPercent, bronzePercent,
                    generalAdmissionPercent, reservedExtraPercent);
        } else if (type.equalsIgnoreCase("Stadium")) {
            return new Stadium(venueId, name, "Stadium", capacity, concertCapacity, cost,
                    vipPercent, goldPercent, silverPercent, bronzePercent,
                    generalAdmissionPercent, reservedExtraPercent);
        }

        return null;
    }

    /** 
     * @param value
     * @return String
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
     * @param writer
     * @param values
     * @throws IOException
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
     * @param value
     * @param fieldName
     * @param line
     * @return int
     */
    private static int parseIntField(String value, String fieldName, String line) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + fieldName + " in row: " + line);
        }
    }

    /** 
     * @param value
     * @param fieldName
     * @param line
     * @return double
     */
    private static double parseDoubleField(String value, String fieldName, String line) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + fieldName + " in row: " + line);
        }
    }

    /** 
     * @param keyboard
     * @param prompt
     * @return double
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
     * @param keyboard
     * @param prompt
     * @return int
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
     * @param keyboard
     * @param prompt
     * @return boolean
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
     * @param keyboard
     * @param prompt
     * @return LocalDate
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
     * @param keyboard
     * @param prompt
     * @return LocalTime
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

    /**
     * Displays the main menu and allows users to
     * register, login, or exit the system.
     */
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
     * Displays the venue management menu.
     * Users can add, view, search, update, or delete venues.
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
     * Prompts the user for venue information and
     * adds the venue to the system.
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

        Venue newVenue = createVenue(venueId, name, type, capacity, concertCapacity, cost,
                vipPercent, goldPercent, silverPercent, bronzePercent,
                generalAdmissionPercent, reservedExtraPercent);

        if (newVenue == null) {
            System.out.println("Invalid venue type.");
            return;
        }

        venues.add(newVenue);
        writeVenueCSV(VENUE_OUTPUT_CSV);
        log(getActorName() + " added venue ID " + venueId + " named " + name);
        System.out.println("Venue added successfully.");
    }

    /**
     * Displays all venues currently stored in the system.
     */
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

        boolean found = false;

        for (Venue venue : venues) {
            if (venue.matchesSearch(input)) {
                System.out.println(venue);
                found = true;
            }
        }

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

        for (Venue venue : venues) {
            if (venue.matchesSearch(input)) {
                return venue;
            }
        }

        return null;
    }

    /**
     * Updates venue information such as name, capacity,
     * cost, or location.
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

        writeVenueCSV(VENUE_OUTPUT_CSV);
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
            writeVenueCSV(VENUE_OUTPUT_CSV);
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
     * Displays the event management menu.
     * Users can add, view, update, or delete events.
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
     * Prompts the user to enter event information and
     * adds the event to the system.
     *
     * @param keyboard Scanner used to read user input
     */
    public static void addEvent(Scanner keyboard) {
        int id = readInt(keyboard, "Enter event ID: ");

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

        Event newEvent = null;

        if (type.equalsIgnoreCase("Concert")) {
            System.out.print("Enter artist: ");
            String artist = keyboard.nextLine().trim();

            System.out.print("Enter genre: ");
            String genre = keyboard.nextLine().trim();

            newEvent = new Concert(
                    id, name, date, time,
                    vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice,
                    artist, genre);

        } else if (type.equalsIgnoreCase("Sport")) {
            System.out.print("Enter team1: ");
            String team1 = keyboard.nextLine().trim();

            System.out.print("Enter team2: ");
            String team2 = keyboard.nextLine().trim();

            System.out.print("Enter league: ");
            String league = keyboard.nextLine().trim();

            newEvent = new Sport(
                    id, name, date, time,
                    vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice,
                    team1, team2, league);

        } else if (type.equalsIgnoreCase("Special")) {
            System.out.print("Enter description: ");
            String description = keyboard.nextLine().trim();

            System.out.print("Enter category: ");
            String category = keyboard.nextLine().trim();

            newEvent = new Special(
                    id, name, date, time,
                    vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice,
                    description, category);

        } else {
            System.out.println("Invalid event type.");
            return;
        }

        events.add(newEvent);
        writeEventCSV(EVENT_OUTPUT_CSV);
        log(getActorName() + " added event ID " + id + " named " + name);
        System.out.println("Event added successfully.");
    }

    /**
     * Displays all events stored in the system.
     */
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

        boolean found = false;

        for (Event event : events) {
            if (event.matchesSearch(input)) {
                System.out.println(event);
                found = true;
            }
        }

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
        for (Event event : events) {
            if (event.matchesSearch(input)) {
                return event;
            }
        }

        return null;
    }

    /**
     * Updates event information such as name, date,
     * or time of the event.
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

        String choice = keyboard.nextLine().trim();

        switch (choice) {
            case "1" -> {
                System.out.print("Enter new event name: ");
                event.setEventName(keyboard.nextLine().trim());
                writeEventCSV(EVENT_OUTPUT_CSV);
                log(getActorName() + " updated event name for event ID " + event.getEventId());
                System.out.println("Event name updated successfully.");
            }
            case "2" -> {
                LocalDate newDate = readDate(keyboard, "Enter event date");
                LocalTime newTime = readTime(keyboard, "Enter event time");

                event.setDate(newDate);
                event.setTime(newTime);

                writeEventCSV(EVENT_OUTPUT_CSV);
                log(getActorName() + " updated event date/time for event ID " + event.getEventId());
                System.out.println("Event date and time updated successfully.");
            }
            default -> System.out.println("Invalid option.");
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
            writeEventCSV(EVENT_OUTPUT_CSV);
            log(getActorName() + " deleted event ID " + event.getEventId());
            System.out.println("Event deleted successfully.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    /**
     * Displays the event viewing submenu.
     * Users can display all events or search for a specific event.
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

    public static int [] headerIndexes(String header[], String params[]){
        int headerIndexes[] = new int[params.length];
        for(int i = 0; i < params.length; i++){
            headerIndexes[i] = Arrays.binarySearch(header, params[i]);
        }
        return headerIndexes;
    }

    /**
     * Reads user information from a CSV file and loads
     * users into the system.
     *
     * @param filePath path to the user CSV file
     */
    public static void readUserCSV(String filePath) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            String line = reader.readLine(); // skip header
            String header[] = line.split(",", -1);
            String params[] = {"ID", "First Name", "Last Name", "Username", "Password", "User Type", "Money Available", "TicketMiner Membership"};
            int headerIndexes[] = headerIndexes(header, params);

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

                    Customer customer = new Customer(
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
                    Organizer organizer = new Organizer(
                            parseIntField(id, "user id", line),
                            firstName,
                            lastName,
                            username,
                            password,
                            userType,
                            KEYBOARD);
                    users.add(organizer);
                } else if (userType.equalsIgnoreCase("admin")) {
                    Admin admin = new Admin(
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
     * Reads venue information from a CSV file and loads
     * venues into the system.
     *
     * @param filePath path to the venue CSV file
     */
    public static void readVenueCSV(String filePath) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            String line = reader.readLine(); // skip header
            String header[] = line.split(",", -1);
            String params[] = {"ID", "Name", "Type", "Capacity", "Concert Capacity", "Cost", "VIP Percent", "Gold Percent", "Silver Percent", "Bronze Percent", "General Admission Percent", "Reserved Extra Percent"};
            int headerIndexes[] = headerIndexes(header, params);

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
                double generalAdmissionPercent = parseDoubleField(fields[headerIndexes[10]], "General Admission percent", line);
                double reservedExtraPercent = parseDoubleField(fields[headerIndexes[11]], "Reserved Extra percent", line);

                Venue venue = createVenue(id, name, type, capacity, concertCapacity, cost,
                        vipPercent, goldPercent, silverPercent, bronzePercent,
                        generalAdmissionPercent, reservedExtraPercent);

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
     * Reads event information from a CSV file and loads
     * events into the system.
     *
     * @param filePath path to the event CSV file
     */
    public static void readEventCSV(String filePath) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            String line = reader.readLine(); // skip header
            String header[] = line.split(",", -1);
            String params[] = {"ID", "Name", "Type", "Date", "Time", "VIP Price", "Gold Price", "Silver Price", "Bronze Price", "General Admission Price"};
            int headerIndexes[] = headerIndexes(header, params);

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",", -1);

                if (fields.length < 10) {
                    System.out.println("Skipping bad event row: " + line);
                    continue;
                }

                int id = parseIntField(fields[headerIndexes[0]], "event id", line);
                String type = fields[headerIndexes[1]].trim();
                String name = fields[headerIndexes[2]].trim();
                String date = fields[headerIndexes[3]].trim();
                String time = fields[headerIndexes[4]].trim();
                double vipPrice = parseDoubleField(fields[headerIndexes[5]], "VIP price", line);
                double goldPrice = parseDoubleField(fields[headerIndexes[6]], "gold price", line);
                double silverPrice = parseDoubleField(fields[headerIndexes[7]], "silver price", line);
                double bronzePrice = parseDoubleField(fields[headerIndexes[8]], "bronze price", line);
                double generalAdmissionPrice = parseDoubleField(fields[headerIndexes[9]], "general admission price", line);

                try {
                    LocalDate eventDate = LocalDate.parse(date, DATE_FORMAT);
                    LocalTime eventTime = LocalTime.parse(time, TIME_FORMAT);

                    if (type.equalsIgnoreCase("concert")) {
                        events.add(new Concert(
                                id,
                                name,
                                eventDate,
                                eventTime,
                                vipPrice,
                                goldPrice,
                                silverPrice,
                                bronzePrice,
                                generalAdmissionPrice,
                                null,
                                null));
                    } else if (type.equalsIgnoreCase("sport")) {
                        events.add(new Sport(
                                id,
                                name,
                                eventDate,
                                eventTime,
                                vipPrice,
                                goldPrice,
                                silverPrice,
                                bronzePrice,
                                generalAdmissionPrice,
                                null,
                                null,
                                null));
                    } else if (type.equalsIgnoreCase("special")) {
                        events.add(new Special(
                                id,
                                name,
                                eventDate,
                                eventTime,
                                vipPrice,
                                goldPrice,
                                silverPrice,
                                bronzePrice,
                                generalAdmissionPrice,
                                null,
                                null));
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

        Customer customer = new Customer(
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
        writeUserCSV(USER_OUTPUT_CSV);
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

        Organizer organizer = new Organizer(
                newId,
                firstName,
                lastName,
                username,
                password,
                "organizer",
                keyboard);

        users.add(organizer);
        writeUserCSV(USER_OUTPUT_CSV);
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
     * @param filePath
     */
    public static void writeUserCSV(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writeCsvLine(writer,
                    "ID", "First Name", "Last Name", "Username", "Password",
                    "User Type", "Money Available", "TicketMiner Membership", "Concerts Purchased");

            for (User user : users) {
                String moneyAvailable = "";
                String membership = "";
                String concertsPurchased = "";

                if (user instanceof Customer customer) {
                    moneyAvailable = String.valueOf(customer.getMoneyAvailable());
                    membership = String.valueOf(customer.isMembership());
                    concertsPurchased = "0";
                }

                writeCsvLine(writer,
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
                writeCsvLine(writer,
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
     * @param filePath
     */
    public static void writeVenueCSV(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writeCsvLine(writer,
                    "ID", "Name", "Type", "Capacity", "Concert Capacity", "Cost",
                    "VIP Percent", "Gold Percent", "Silver Percent", "Bronze Percent",
                    "General Admission Percent", "Reserved Extra Percent");

            for (Venue venue : venues) {
                writeCsvLine(writer,
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
     * @param filePath
     */
    public static void writeEventCSV(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writeCsvLine(writer,
                    "ID", "Type", "Name", "Date", "Time",
                    "VIP Price", "Gold Price", "Silver Price", "Bronze Price", "General Admission Price");

            for (Event event : events) {
                writeCsvLine(writer,
                        String.valueOf(event.getEventId()),
                        event.getEventType(),
                        event.getEventName(),
                        event.getDate().format(DATE_FORMAT),
                        event.getTime().format(TIME_FORMAT),
                        String.valueOf(event.getVipPrice()),
                        String.valueOf(event.getGoldPrice()),
                        String.valueOf(event.getSilverPrice()),
                        String.valueOf(event.getBronzePrice()),
                        String.valueOf(event.getGeneralAdmissionPrice()));
            }

        } catch (IOException e) {
            System.out.println("Error writing event CSV: " + e.getMessage());
        }
    }
} // RunTicketMiner

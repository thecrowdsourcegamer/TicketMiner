package ticketminer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

class Organizer extends User {

    private List<Event> events;

    public Organizer(int userId, String firstName, String lastName,
                     String userName, String password, String userType,
                     Scanner keyboard, List<Event> events) {
        super(userId, firstName, lastName, userName, password, userType, keyboard);
        this.events = events;
    }

    public void userMenu() {
        String input = "";

        while (!input.equals("3")) {
            System.out.println("\n=== Organizer Menu ===");
            System.out.println("1: Manage Event");
            System.out.println("2: Generate Event Report");
            System.out.println("3: Log Out");

            input = getKeyboard().nextLine().trim();

            switch (input) {
                case "1" -> manageEvent();
                case "2" -> generateEventReport();
                case "3" -> back();
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void manageEvent() {
        String input = "";

        while (!input.equals("5")) {
            System.out.println("\n--- Manage Event ---");
            System.out.println("1: Add");
            System.out.println("2: View");
            System.out.println("3: Update");
            System.out.println("4: Delete");
            System.out.println("5: Back");

            input = getKeyboard().nextLine().trim();

            switch (input) {
                case "1" -> addEvent();
                case "2" -> viewEventMenu();
                case "3" -> updateEvent();
                case "4" -> deleteEvent();
                case "5" -> {}
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addEvent() {
        Scanner kb = getKeyboard();

        int id = readInt(kb, "Enter Event ID: ");

        System.out.print("Enter Event Name: ");
        String name = kb.nextLine().trim();

        System.out.print("Enter Event Type (Concert / Sport / Special): ");
        String type = kb.nextLine().trim();

        LocalDate date = readDate(kb, "Enter Event Date");
        LocalTime time = readTime(kb, "Enter Event Time");

        double vipPrice = readDouble(kb, "Enter VIP Ticket Price: $");
        double goldPrice = readDouble(kb, "Enter Gold Ticket Price: $");
        double silverPrice = readDouble(kb, "Enter Silver Ticket Price: $");
        double bronzePrice = readDouble(kb, "Enter Bronze Ticket Price: $");
        double generalPrice = readDouble(kb, "Enter General Admission Ticket Price: $");

        Event newEvent = null;

        if (type.equalsIgnoreCase("Concert")) {
            System.out.print("Enter Artist Name: ");
            String artist = kb.nextLine().trim();
            System.out.print("Enter Genre: ");
            String genre = kb.nextLine().trim();
            newEvent = new Concert(id, name, date, time, vipPrice, goldPrice,
                    silverPrice, bronzePrice, generalPrice, artist, genre);

        } else if (type.equalsIgnoreCase("Sport")) {
            System.out.print("Enter Team 1: ");
            String team1 = kb.nextLine().trim();
            System.out.print("Enter Team 2: ");
            String team2 = kb.nextLine().trim();
            System.out.print("Enter League: ");
            String league = kb.nextLine().trim();
            newEvent = new Sport(id, name, date, time, vipPrice, goldPrice,
                    silverPrice, bronzePrice, generalPrice, team1, team2, league);

        } else if (type.equalsIgnoreCase("Special")) {
            System.out.print("Enter Description: ");
            String description = kb.nextLine().trim();
            System.out.print("Enter Category: ");
            String category = kb.nextLine().trim();
            newEvent = new Special(id, name, date, time, vipPrice, goldPrice,
                    silverPrice, bronzePrice, generalPrice, description, category);
        } else {
            System.out.println("Invalid event type.");
            return;
        }

        events.add(newEvent);
        System.out.println("Event added successfully.");
    }

    private void viewEventMenu() {
        String input = "";

        while (!input.equals("3")) {
            System.out.println("\n-- View Events --");
            System.out.println("1: Display All Events");
            System.out.println("2: Search for an Event");
            System.out.println("3: Back");

            input = getKeyboard().nextLine().trim();

            switch (input) {
                case "1" -> displayAllEvents();
                case "2" -> searchEvent();
                case "3" -> {}
                default -> System.out.println("Invalid option.");
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
        System.out.print("Enter Event ID, Name, or Date: ");
        String query = getKeyboard().nextLine().trim();

        boolean found = false;

        for (Event event : events) {
            if (event.matchesSearch(query)) {
                System.out.println(event);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Event not found.");
        }
    }

    private void updateEvent() {
        System.out.print("Enter Event ID, Name, or Date: ");
        String query = getKeyboard().nextLine().trim();

        Event event = findEvent(query);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        System.out.println("1: Change Name");
        System.out.println("2: Change Date and Time");

        String choice = getKeyboard().nextLine().trim();

        switch (choice) {
            case "1" -> {
                System.out.print("Enter New Name: ");
                event.setEventName(getKeyboard().nextLine().trim());
            }
            case "2" -> {
                LocalDate newDate = readDate(getKeyboard(), "Enter New Date");
                LocalTime newTime = readTime(getKeyboard(), "Enter New Time");
                event.setDate(newDate);
                event.setTime(newTime);
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private void deleteEvent() {
        System.out.print("Enter Event ID, Name, or Date: ");
        String query = getKeyboard().nextLine().trim();

        Event event = findEvent(query);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        System.out.print("Confirm delete (yes/no): ");
        String confirm = getKeyboard().nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {
            events.remove(event);
            System.out.println("Event deleted.");
        } else {
            System.out.println("Cancelled.");
        }
    }

    private void generateEventReport() {
        System.out.print("Enter Event ID, Name, or Date: ");
        String query = getKeyboard().nextLine().trim();

        Event event = findEvent(query);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        int vipSold = event.getVipSold();
        int goldSold = event.getGoldSold();
        int silverSold = event.getSilverSold();
        int bronzeSold = event.getBronzeSold();
        int genSold = event.getGeneralSold();

        int totalSold = vipSold + goldSold + silverSold + bronzeSold + genSold;

        double totalRevenue =
                vipSold * event.getVipPrice() +
                goldSold * event.getGoldPrice() +
                silverSold * event.getSilverPrice() +
                bronzeSold * event.getBronzePrice() +
                genSold * event.getGeneralAdmissionPrice();

        System.out.println("\nEvent ID: " + event.getEventId());
        System.out.println("Type: " + event.getEventType());
        System.out.println("Name: " + event.getEventName());
        System.out.println("Date: " + event.getDate());
        System.out.println("Total Sold: " + totalSold);
        System.out.println("Revenue: $" + totalRevenue);
    }

    private Event findEvent(String input) {
        for (Event event : events) {
            if (event.matchesSearch(input)) {
                return event;
            }
        }
        return null;
    }

    private int readInt(Scanner kb, String prompt) {
        return RunTicketMiner.readInt(kb, prompt);
    }

    private double readDouble(Scanner kb, String prompt) {
        return RunTicketMiner.readDouble(kb, prompt);
    }

    private LocalDate readDate(Scanner kb, String prompt) {
        return RunTicketMiner.readDate(kb, prompt);
    }

    private LocalTime readTime(Scanner kb, String prompt) {
        return RunTicketMiner.readTime(kb, prompt);
    }
}
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

    public Organizer(int userId, String firstName, String lastName,
                     String userName, String password, String userType,
                     Scanner keyboard) {
        this(userId, firstName, lastName, userName, password, userType,
                keyboard, RunTicketMiner.getEvents());
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

            input = kb.nextLine();

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

            input = kb.nextLine();

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
        String name = kb.nextLine();

        System.out.print("Enter Event Type (Concert / Sport / Special): ");
        String type = kb.nextLine();

        LocalDate date = RunTicketMiner.readDate(kb, "Enter Event Date");
        LocalTime time = RunTicketMiner.readTime(kb, "Enter Event Time");

        double vipPrice = RunTicketMiner.readDouble(kb, "Enter VIP Ticket Price: $");
        double goldPrice = RunTicketMiner.readDouble(kb, "Enter Gold Ticket Price: $");
        double silverPrice = RunTicketMiner.readDouble(kb, "Enter Silver Ticket Price: $");
        double bronzePrice = RunTicketMiner.readDouble(kb, "Enter Bronze Ticket Price: $");
        double generalPrice = RunTicketMiner.readDouble(kb, "Enter General Admission Ticket Price: $");

        Event event = null;

        if (type.equalsIgnoreCase("Concert")) {
            System.out.print("Enter Artist Name: ");
            String artist = kb.nextLine();

            System.out.print("Enter Genre: ");
            String genre = kb.nextLine();

            event = new Concert(id, name, date, time, vipPrice, goldPrice,
                    silverPrice, bronzePrice, generalPrice, artist, genre);
        }
        else if (type.equalsIgnoreCase("Sport")) {
            System.out.print("Enter Team 1: ");
            String team1 = kb.nextLine();

            System.out.print("Enter Team 2: ");
            String team2 = kb.nextLine();

            System.out.print("Enter League: ");
            String league = kb.nextLine();

            event = new Sport(id, name, date, time, vipPrice, goldPrice,
                    silverPrice, bronzePrice, generalPrice, team1, team2, league);
        }
        else if (type.equalsIgnoreCase("Special")) {
            System.out.print("Enter Description: ");
            String description = kb.nextLine();

            System.out.print("Enter Category: ");
            String category = kb.nextLine();

            event = new Special(id, name, date, time, vipPrice, goldPrice,
                    silverPrice, bronzePrice, generalPrice, description, category);
        }
        else {
            System.out.println("Invalid event type.");
            return;
        }

        events.add(event);
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

            input = kb.nextLine();

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
        if (events.size() == 0) {
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
        String search = kb.nextLine();

        boolean found = false;

        for (Event event : events) {
            if (event.matchesSearch(search)) {
                System.out.println(event);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Event not found.");
        }
    }

    private void updateEvent() {
        Scanner kb = getKeyboard();

        System.out.print("Enter Event ID, Name, or Date: ");
        String search = kb.nextLine();

        Event event = findEvent(search);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        System.out.println("\nWhat do you want to update?");
        System.out.println("1: Event Name");
        System.out.println("2: Event Date and Time");
        System.out.print("Choose an option: ");

        String choice = kb.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Enter New Event Name: ");
                String newName = kb.nextLine();
                event.setEventName(newName);
                System.out.println("Event name updated.");
                break;

            case "2":
                LocalDate newDate = RunTicketMiner.readDate(kb, "Enter New Date");
                LocalTime newTime = RunTicketMiner.readTime(kb, "Enter New Time");

                event.setDate(newDate);
                event.setTime(newTime);

                System.out.println("Event date and time updated.");
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    private void deleteEvent() {
        Scanner kb = getKeyboard();

        System.out.print("Enter Event ID, Name, or Date: ");
        String search = kb.nextLine();

        Event event = findEvent(search);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        System.out.println(event);
        System.out.print("Are you sure you want to delete this event? yes/no: ");

        String answer = kb.nextLine();

        if (answer.equalsIgnoreCase("yes")) {
            events.remove(event);
            System.out.println("Event deleted.");
        }
        else {
            System.out.println("Delete cancelled.");
        }
    }

    private void generateEventReport() {
        Scanner kb = getKeyboard();

        System.out.print("Enter Event ID, Name, or Date: ");
        String search = kb.nextLine();

        Event event = findEvent(search);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        int vipSold = event.getVipSold();
        int goldSold = event.getGoldSold();
        int silverSold = event.getSilverSold();
        int bronzeSold = event.getBronzeSold();
        int generalSold = event.getGeneralSold();

        int totalSold = vipSold + goldSold + silverSold + bronzeSold + generalSold;

        double revenue = 0;

        revenue = revenue + vipSold * event.getVipPrice();
        revenue = revenue + goldSold * event.getGoldPrice();
        revenue = revenue + silverSold * event.getSilverPrice();
        revenue = revenue + bronzeSold * event.getBronzePrice();
        revenue = revenue + generalSold * event.getGeneralAdmissionPrice();

        System.out.println("\n--- Event Report ---");
        System.out.println("Event ID: " + event.getEventId());
        System.out.println("Type: " + event.getEventType());
        System.out.println("Name: " + event.getEventName());
        System.out.println("Date: " + event.getDate());
        System.out.println("Time: " + event.getTime());
        System.out.println("Total Tickets Sold: " + totalSold);
        System.out.println("Total Revenue: $" + revenue);
    }

    private Event findEvent(String search) {
        for (Event event : events) {
            if (event.matchesSearch(search)) {
                return event;
            }
        }

        return null;
    }
}

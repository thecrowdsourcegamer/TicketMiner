import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a special event.
 *
 * Stores a description and category
 * for the event.
 */
public class Special extends Event {
    private String description;
    private String category;

    public Special(int id, String name, LocalDate date, LocalTime time, double vipPrice, double goldPrice, double silverPrice, double bronzePrice, double generalAdmissionPrice, String description, String category) {
        super(id, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice, "Special");
        this.description = description;
        this.category = category;
    }
}

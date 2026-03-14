import java.time.LocalDate;
import java.time.LocalTime;

public class Special extends Event {
    private String description;
    private String category;

    public Special(int id, String name, LocalDate date, LocalTime time, double vipPrice, double goldPrice, double silverPrice, double bronzePrice, double generalAdmissionPrice, String description, String category) {
        super(id, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice, "Special");
        this.description = description;
        this.category = category;
    }
}

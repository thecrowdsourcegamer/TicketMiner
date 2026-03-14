import java.time.LocalDate;
import java.time.LocalTime;

public class Sport extends Event{
    private String team1;
    private String team2;
    private String league;

    public Sport(int id, String name, LocalDate date, LocalTime time, double vipPrice, double goldPrice, double silverPrice, double bronzePrice, double generalAdmissionPrice, String team1, String team2, String league) {
        super(id, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice, "Sport");
        this.team1 = team1;
        this.team2 = team2;
        this.league = league;
    }
}

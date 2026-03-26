package ticketminer;
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

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }
}

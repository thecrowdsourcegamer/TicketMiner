package ticketminer;

import java.time.LocalDate;
import java.time.LocalTime;

/** Represents a sport event. */
public class Sport extends Event {
  private String team1;
  private String team2;
  private String league;

  /**
   * Creates a sport event.
   *
   * @param id unique event id
   * @param name event name
   * @param date event date
   * @param time event time
   * @param vipPrice VIP ticket price
   * @param goldPrice gold ticket price
   * @param silverPrice silver ticket price
   * @param bronzePrice bronze ticket price
   * @param generalAdmissionPrice general admission ticket price
   * @param team1 first team
   * @param team2 second team
   * @param league sport league
   */
  public Sport(
      int id,
      String name,
      LocalDate date,
      LocalTime time,
      double vipPrice,
      double goldPrice,
      double silverPrice,
      double bronzePrice,
      double generalAdmissionPrice,
      String team1,
      String team2,
      String league) {
    super(
        id,
        name,
        date,
        time,
        vipPrice,
        goldPrice,
        silverPrice,
        bronzePrice,
        generalAdmissionPrice,
        "Sport");
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

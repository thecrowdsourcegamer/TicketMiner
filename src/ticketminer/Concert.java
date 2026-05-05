package ticketminer;

import java.time.LocalDate;
import java.time.LocalTime;

/** Represents a concert event. */
public class Concert extends Event {
  private String artist;
  private String genre;

  /**
   * Creates a concert event.
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
   * @param artist concert artist
   * @param genre concert genre
   */
  public Concert(
      int id,
      String name,
      LocalDate date,
      LocalTime time,
      double vipPrice,
      double goldPrice,
      double silverPrice,
      double bronzePrice,
      double generalAdmissionPrice,
      String artist,
      String genre) {
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
        "Concert");
    this.artist = artist;
    this.genre = genre;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }
}

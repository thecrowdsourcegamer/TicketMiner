package ticketminer;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a special event.
 *
 * <p>Stores a description and category for the event.
 */
public class Special extends Event {
  private String description;
  private String category;

  /**
   * Creates a special event.
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
   * @param description event description
   * @param category event category
   */
  public Special(
      int id,
      String name,
      LocalDate date,
      LocalTime time,
      double vipPrice,
      double goldPrice,
      double silverPrice,
      double bronzePrice,
      double generalAdmissionPrice,
      String description,
      String category) {
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
        "Special");
    this.description = description;
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
}

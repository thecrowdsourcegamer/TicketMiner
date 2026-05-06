package ticketminer;

import java.time.LocalDate;
import java.time.LocalTime;

/** Creates event objects based on the requested event type. */
public class EventFactory {

  private EventFactory() {}

  /**
   * Creates the matching event subtype.
   *
   * @param id unique event id
   * @param name event name
   * @param type event type
   * @param date event date
   * @param time event time
   * @param vipPrice VIP ticket price
   * @param goldPrice gold ticket price
   * @param silverPrice silver ticket price
   * @param bronzePrice bronze ticket price
   * @param generalAdmissionPrice general admission ticket price
   * @param firstDetail artist, first team, or description
   * @param secondDetail genre, second team, or category
   * @param thirdDetail league for sport events
   * @return created event, or null when the type is unknown
   */
  public static Event createEvent(
      int id,
      String name,
      String type,
      LocalDate date,
      LocalTime time,
      double vipPrice,
      double goldPrice,
      double silverPrice,
      double bronzePrice,
      double generalAdmissionPrice,
      String firstDetail,
      String secondDetail,
      String thirdDetail) {
    if (type.equalsIgnoreCase("Concert")) {
      return new Concert(
          id,
          name,
          date,
          time,
          vipPrice,
          goldPrice,
          silverPrice,
          bronzePrice,
          generalAdmissionPrice,
          firstDetail,
          secondDetail);
    } else if (type.equalsIgnoreCase("Sport")) {
      return new Sport(
          id,
          name,
          date,
          time,
          vipPrice,
          goldPrice,
          silverPrice,
          bronzePrice,
          generalAdmissionPrice,
          firstDetail,
          secondDetail,
          thirdDetail);
    } else if (type.equalsIgnoreCase("Special")) {
      return new Special(
          id,
          name,
          date,
          time,
          vipPrice,
          goldPrice,
          silverPrice,
          bronzePrice,
          generalAdmissionPrice,
          firstDetail,
          secondDetail);
    }

    return null;
  }
}

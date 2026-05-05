package ticketminer;

/** Represents an arena venue. */
public class Arena extends Venue {

  /**
   * Creates an arena venue.
   *
   * @param venueId unique venue id
   * @param name venue name
   * @param type venue type from the CSV file
   * @param capacity total venue capacity
   * @param concertCapacity concert seating capacity
   * @param cost cost to rent the venue
   * @param vipPercent percentage of VIP seats
   * @param goldPercent percentage of gold seats
   * @param silverPercent percentage of silver seats
   * @param bronzePercent percentage of bronze seats
   * @param generalAdmissionPercent percentage of general admission seats
   * @param reservedExtraPercent percentage of extra reserved seats
   */
  public Arena(
      int venueId,
      String name,
      String type,
      int capacity,
      int concertCapacity,
      double cost,
      double vipPercent,
      double goldPercent,
      double silverPercent,
      double bronzePercent,
      double generalAdmissionPercent,
      double reservedExtraPercent) {
    super(
        venueId,
        name,
        type,
        capacity,
        concertCapacity,
        cost,
        vipPercent,
        goldPercent,
        silverPercent,
        bronzePercent,
        generalAdmissionPercent,
        reservedExtraPercent);
  }

  @Override
  public String getVenueType() {
    return "Arena";
  }
}

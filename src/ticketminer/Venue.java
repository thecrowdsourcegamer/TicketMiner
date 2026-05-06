package ticketminer;

/** Represents a TicketMiner venue. */
public abstract class Venue implements Searchable {

  private int venueId;
  private String venueName;
  private String venueType;
  private int capacity;
  private int concertCapacity;
  private double cost;
  private double vipPercent;
  private double goldPercent;
  private double silverPercent;
  private double bronzePercent;
  private double generalAdmissionPercent;
  private double reservedExtraPercent;

  /**
   * Creates a venue.
   *
   * @param venueId unique venue id
   * @param venueName venue name
   * @param venueType venue type
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
  public Venue(
      int venueId,
      String venueName,
      String venueType,
      int capacity,
      int concertCapacity,
      double cost,
      double vipPercent,
      double goldPercent,
      double silverPercent,
      double bronzePercent,
      double generalAdmissionPercent,
      double reservedExtraPercent) {
    this.venueId = venueId;
    this.venueName = venueName;
    this.venueType = venueType;
    this.capacity = capacity;
    this.concertCapacity = concertCapacity;
    this.cost = cost;
    this.vipPercent = vipPercent;
    this.goldPercent = goldPercent;
    this.silverPercent = silverPercent;
    this.bronzePercent = bronzePercent;
    this.generalAdmissionPercent = generalAdmissionPercent;
    this.reservedExtraPercent = reservedExtraPercent;
  }

  /**
   * Checks whether this venue's id matches the given id.
   *
   * @param id id to compare
   * @return true when the ids match
   */
  public boolean matchesVenueId(int id) {
    return venueId == id;
  }

  /**
   * Checks whether this venue's name matches the given name.
   *
   * @param name venue name to compare
   * @return true when the venue names match
   */
  public boolean matchesVenueName(String name) {
    return venueName.equalsIgnoreCase(name);
  }

  /**
   * Checks whether this venue's type matches the given type.
   *
   * @param type venue type to compare
   * @return true when the venue types match
   */
  public boolean matchesVenueType(String type) {
    return venueType.equalsIgnoreCase(type);
  }

  /**
   * Checks whether the venue matches an id, name, or type search.
   *
   * @param input search input
   * @return true when the venue matches the search input
   */
  @Override
  public boolean matchesSearch(String input) {
    if (matchesVenueName(input) || matchesVenueType(input)) {
      return true;
    }

    try {
      int id = Integer.parseInt(input);
      return matchesVenueId(id);
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Returns the venue's printable details.
   *
   * @return venue details
   */
  @Override
  public String toString() {
    return "ID: "
        + venueId
        + ", Name: "
        + venueName
        + ", Type: "
        + venueType
        + ", Capacity: "
        + capacity
        + ", Concert Capacity: "
        + concertCapacity
        + ", Cost: $"
        + String.format("%.2f", cost)
        + ", VIP Percent: "
        + vipPercent
        + ", Gold Percent: "
        + goldPercent
        + ", Silver Percent: "
        + silverPercent
        + ", Bronze Percent: "
        + bronzePercent
        + ", General Admission Percent: "
        + generalAdmissionPercent
        + ", Reserved Extra Percent: "
        + reservedExtraPercent;
  }

  /**
   * Returns the venue id.
   *
   * @return venue id
   */
  public int getVenueId() {
    return venueId;
  }

  /**
   * Returns the venue name.
   *
   * @return venue name
   */
  public String getVenueName() {
    return venueName;
  }

  /**
   * Returns the venue type.
   *
   * @return venue type
   */
  public String getVenueType() {
    return venueType;
  }

  /**
   * Returns the total venue capacity.
   *
   * @return total capacity
   */
  public int getCapacity() {
    return capacity;
  }

  /**
   * Returns the concert capacity.
   *
   * @return concert capacity
   */
  public int getConcertCapacity() {
    return concertCapacity;
  }

  /**
   * Returns the venue rental cost.
   *
   * @return rental cost
   */
  public double getCost() {
    return cost;
  }

  /**
   * Returns the VIP seat percentage.
   *
   * @return VIP percentage
   */
  public double getVipPercent() {
    return vipPercent;
  }

  /**
   * Returns the gold seat percentage.
   *
   * @return gold percentage
   */
  public double getGoldPercent() {
    return goldPercent;
  }

  /**
   * Returns the silver seat percentage.
   *
   * @return silver percentage
   */
  public double getSilverPercent() {
    return silverPercent;
  }

  /**
   * Returns the bronze seat percentage.
   *
   * @return bronze percentage
   */
  public double getBronzePercent() {
    return bronzePercent;
  }

  /**
   * Returns the general admission seat percentage.
   *
   * @return general admission percentage
   */
  public double getGeneralAdmissionPercent() {
    return generalAdmissionPercent;
  }

  /**
   * Returns the extra reserved seat percentage.
   *
   * @return extra reserved percentage
   */
  public double getReservedExtraPercent() {
    return reservedExtraPercent;
  }

  /**
   * Sets the venue id.
   *
   * @param venueId venue id
   */
  public void setVenueId(int venueId) {
    this.venueId = venueId;
  }

  /**
   * Sets the venue name.
   *
   * @param venueName venue name
   */
  public void setVenueName(String venueName) {
    this.venueName = venueName;
  }

  /**
   * Sets the venue type.
   *
   * @param venueType venue type
   */
  public void setVenueType(String venueType) {
    this.venueType = venueType;
  }

  /**
   * Sets the total venue capacity.
   *
   * @param capacity total capacity
   */
  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  /**
   * Sets the concert capacity.
   *
   * @param concertCapacity concert capacity
   */
  public void setConcertCapacity(int concertCapacity) {
    this.concertCapacity = concertCapacity;
  }

  /**
   * Sets the venue rental cost.
   *
   * @param cost rental cost
   */
  public void setCost(double cost) {
    this.cost = cost;
  }

  /**
   * Sets the VIP seat percentage.
   *
   * @param vipPercent VIP percentage
   */
  public void setVipPercent(double vipPercent) {
    this.vipPercent = vipPercent;
  }

  /**
   * Sets the gold seat percentage.
   *
   * @param goldPercent gold percentage
   */
  public void setGoldPercent(double goldPercent) {
    this.goldPercent = goldPercent;
  }

  /**
   * Sets the silver seat percentage.
   *
   * @param silverPercent silver percentage
   */
  public void setSilverPercent(double silverPercent) {
    this.silverPercent = silverPercent;
  }

  /**
   * Sets the bronze seat percentage.
   *
   * @param bronzePercent bronze percentage
   */
  public void setBronzePercent(double bronzePercent) {
    this.bronzePercent = bronzePercent;
  }

  /**
   * Sets the general admission seat percentage.
   *
   * @param generalAdmissionPercent general admission percentage
   */
  public void setGeneralAdmissionPercent(double generalAdmissionPercent) {
    this.generalAdmissionPercent = generalAdmissionPercent;
  }

  /**
   * Sets the extra reserved seat percentage.
   *
   * @param reservedExtraPercent extra reserved percentage
   */
  public void setReservedExtraPercent(double reservedExtraPercent) {
    this.reservedExtraPercent = reservedExtraPercent;
  }
}

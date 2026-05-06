package ticketminer;

import java.time.LocalDate;
import java.time.LocalTime;

/** Represents a TicketMiner event. */
public abstract class Event implements Searchable {
  private int eventId;
  private String eventName;
  private LocalDate date;
  private LocalTime time;
  private double vipPrice;
  private double goldPrice;
  private double silverPrice;
  private double bronzePrice;
  private double generalAdmissionPrice;
  private String eventType;
  private int vipSold;
  private int goldSold;
  private int silverSold;
  private int bronzeSold;
  private int generalSold;
  private int totalCapacity;

  /** Creates an empty event. */
  public Event() {
    this.eventId = 0;
    this.eventName = null;
    this.date = null;
    this.time = null;
    this.vipPrice = 0;
    this.goldPrice = 0;
    this.silverPrice = 0;
    this.bronzePrice = 0;
    this.generalAdmissionPrice = 0;
    this.eventType = null;
    this.vipSold = 0;
    this.goldSold = 0;
    this.silverSold = 0;
    this.bronzeSold = 0;
    this.generalSold = 0;
    this.totalCapacity = 0;
  }

  /**
   * Creates an event.
   *
   * @param eventId unique event id
   * @param eventName event name
   * @param date event date
   * @param time event time
   * @param vipPrice VIP ticket price
   * @param goldPrice gold ticket price
   * @param silverPrice silver ticket price
   * @param bronzePrice bronze ticket price
   * @param generalAdmissionPrice general admission ticket price
   * @param eventType event type
   */
  public Event(
      int eventId,
      String eventName,
      LocalDate date,
      LocalTime time,
      double vipPrice,
      double goldPrice,
      double silverPrice,
      double bronzePrice,
      double generalAdmissionPrice,
      String eventType) {
    this.eventId = eventId;
    this.eventName = eventName;
    this.date = date;
    this.time = time;
    this.vipPrice = vipPrice;
    this.goldPrice = goldPrice;
    this.silverPrice = silverPrice;
    this.bronzePrice = bronzePrice;
    this.generalAdmissionPrice = generalAdmissionPrice;
    this.eventType = eventType;
    this.vipSold = 0;
    this.goldSold = 0;
    this.silverSold = 0;
    this.bronzeSold = 0;
    this.generalSold = 0;
    this.totalCapacity = 0;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public LocalTime getTime() {
    return time;
  }

  public void setTime(LocalTime time) {
    this.time = time;
  }

  public double getVipPrice() {
    return vipPrice;
  }

  public void setVipPrice(double vipPrice) {
    this.vipPrice = vipPrice;
  }

  public double getGoldPrice() {
    return goldPrice;
  }

  public void setGoldPrice(double goldPrice) {
    this.goldPrice = goldPrice;
  }

  public double getSilverPrice() {
    return silverPrice;
  }

  public void setSilverPrice(double silverPrice) {
    this.silverPrice = silverPrice;
  }

  public double getBronzePrice() {
    return bronzePrice;
  }

  public void setBronzePrice(double bronzePrice) {
    this.bronzePrice = bronzePrice;
  }

  public double getGeneralAdmissionPrice() {
    return generalAdmissionPrice;
  }

  public void setGeneralAdmissionPrice(double generalAdmissionPrice) {
    this.generalAdmissionPrice = generalAdmissionPrice;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public int getVipSold() {
    return vipSold;
  }

  public void setVipSold(int vipSold) {
    this.vipSold = vipSold;
  }

  public int getGoldSold() {
    return goldSold;
  }

  public void setGoldSold(int goldSold) {
    this.goldSold = goldSold;
  }

  public int getSilverSold() {
    return silverSold;
  }

  public void setSilverSold(int silverSold) {
    this.silverSold = silverSold;
  }

  public int getBronzeSold() {
    return bronzeSold;
  }

  public void setBronzeSold(int bronzeSold) {
    this.bronzeSold = bronzeSold;
  }

  public int getGeneralSold() {
    return generalSold;
  }

  public void setGeneralSold(int generalSold) {
    this.generalSold = generalSold;
  }
  
  public int getTotalCapacity() {
    return totalCapacity;
}

  public void setTotalCapacity(int totalCapacity) {
    this.totalCapacity = totalCapacity;
}

  /**
   * Checks whether the event matches an id, name, or date search.
   *
   * @param input search input
   * @return true when the event matches the search input
   */
  @Override
  public boolean matchesSearch(String input) {
    if (eventName.equalsIgnoreCase(input)) {
      return true;
    }

    try {
      int id = Integer.parseInt(input);
      if (eventId == id) {
        return true;
      }
    } catch (NumberFormatException e) {
      // Search input was not an id.
    }

    try {
      LocalDate searchDate = LocalDate.parse(input);
      return date.equals(searchDate);
    } catch (Exception e) {
      return false;
    }
  }
  
public double getVipRevenue() {
  return vipSold * vipPrice;
}

public double getGoldRevenue() {
  return goldSold * goldPrice;
}

public double getSilverRevenue() {
  return silverSold * silverPrice;
}

public double getBronzeRevenue() {
  return bronzeSold * bronzePrice;
}

public double getGeneralRevenue() {
  return generalSold * generalAdmissionPrice;
}
  
  public double getTotalRevenue() {
    return getVipRevenue()
      + getGoldRevenue()
      + getSilverRevenue()
      + getBronzeRevenue()
      + getGeneralRevenue();
}

public double getExpectedProfit() {
  double averageTicketPrice =
      (vipPrice
      + goldPrice
      + silverPrice
      + bronzePrice
      + generalAdmissionPrice) / 5.0;

  return totalCapacity * averageTicketPrice;
}

public double getActualProfit() {
  return getTotalRevenue();
}

  @Override
  public String toString() {
    return "ID: "
        + eventId
        + ", Name: "
        + eventName
        + ", Type: "
        + eventType
        + ", Date: "
        + date
        + ", Time: "
        + time
        + ", VIP: $"
        + vipPrice
        + ", Gold: $"
        + goldPrice
        + ", Silver: $"
        + silverPrice
        + ", Bronze: $"
        + bronzePrice
        + ", General Admission: $"
        + generalAdmissionPrice;
  }

}

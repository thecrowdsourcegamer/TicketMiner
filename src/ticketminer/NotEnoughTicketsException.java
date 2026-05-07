package ticketminer;

/** Thrown when an event does not have enough tickets for a customer's request. */
public class NotEnoughTicketsException extends Exception {
  /**
   * Creates a not enough tickets exception.
   *
   * @param message error message
   */
  public NotEnoughTicketsException(String message) {
    super(message);
  }
}

package ticketminer;

/** Thrown when a customer does not have enough money to complete a purchase. */
public class InsufficientFundsException extends Exception {
  /**
   * Creates an insufficient funds exception.
   *
   * @param message error message
   */
  public InsufficientFundsException(String message) {
    super(message);
  }
}

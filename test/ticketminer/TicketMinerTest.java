package ticketminer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for TicketMiner helper behavior.
 */
public class TicketMinerTest {

  @Test
  public void testEventMatchesSearchByIdNameAndDate() {
    Event event = new Concert(
        101,
        "Rock Night",
        LocalDate.of(2026, 5, 6),
        LocalTime.of(19, 30),
        100.00,
        80.00,
        60.00,
        40.00,
        25.00,
        "Test Artist",
        "Rock");

    assertTrue(event.matchesSearch("101"));
    assertTrue(event.matchesSearch("Rock Night"));
    assertTrue(event.matchesSearch("rock night"));
    assertTrue(event.matchesSearch("2026-05-06"));

    assertFalse(event.matchesSearch("999"));
    assertFalse(event.matchesSearch("Jazz Night"));
    assertFalse(event.matchesSearch("2026-01-01"));
  }

  @Test
  public void testVenueMatchesSearchByIdNameAndType() {
    Venue venue = new Arena(
        5,
        "Don Haskins Center",
        "Arena",
        12000,
        10000,
        50000.00,
        5.0,
        10.0,
        20.0,
        25.0,
        40.0,
        2.0);

    assertTrue(venue.matchesSearch("5"));
    assertTrue(venue.matchesSearch("Don Haskins Center"));
    assertTrue(venue.matchesSearch("don haskins center"));
    assertTrue(venue.matchesSearch("Arena"));

    assertFalse(venue.matchesSearch("100"));
    assertFalse(venue.matchesSearch("Stadium"));
    assertFalse(venue.matchesSearch("Wrong Venue"));
  }

  @Test
  public void testReadIntHandlesInvalidInputThenValidInput() {
    Scanner scanner = new Scanner("abc\n42\n");

    int result = RunTicketMiner.readInt(scanner, "Enter number: ");

    assertEquals(42, result);
  }

  @Test
  public void testReadDoubleHandlesInvalidInputThenValidInput() {
    Scanner scanner = new Scanner("wrong\n19.99\n");

    double result = RunTicketMiner.readDouble(scanner, "Enter price: ");

    assertEquals(19.99, result, 0.001);
  }

  @Test
  public void testReadBooleanAcceptsYesAndNo() {
    Scanner yesScanner = new Scanner("yes\n");
    Scanner noScanner = new Scanner("no\n");

    assertTrue(RunTicketMiner.readBoolean(yesScanner, "Membership: "));
    assertFalse(RunTicketMiner.readBoolean(noScanner, "Membership: "));
  }

  @Test
  public void testReadDateParsesValidDate() {
    Scanner scanner = new Scanner("2026-05-06\n");

    LocalDate date = RunTicketMiner.readDate(scanner, "Enter date");

    assertEquals(LocalDate.of(2026, 5, 6), date);
  }

  @Test
  public void testReadTimeParsesValidTime() {
    Scanner scanner = new Scanner("19:30\n");

    LocalTime time = RunTicketMiner.readTime(scanner, "Enter time");

    assertEquals(LocalTime.of(19, 30), time);
  }
}

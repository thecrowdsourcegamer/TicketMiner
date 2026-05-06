package ticketminer;

/** Represents an object that can be found using user search input. */
public interface Searchable {

  /**
   * Checks whether this object matches the given search input.
   *
   * @param input search input
   * @return true when this object matches the search input
   */
  boolean matchesSearch(String input);
}

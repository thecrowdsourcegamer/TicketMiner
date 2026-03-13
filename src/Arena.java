public class Arena extends Venue {

    public Arena(int venueId, String name, String type, int capacity, double cost, String location) {
        super(venueId, name, type, capacity, cost, location);
    }

    @Override
    public String getVenueType() {
        return "Arena";
    }
}

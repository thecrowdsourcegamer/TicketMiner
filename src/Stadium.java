public class Stadium extends Venue {

    public Stadium(int venueId, String name, String type, int capacity, double cost, String location) {
        super(venueId, name, type, capacity, cost, location);
    }

    @Override
    public String getVenueType() {
        return "Stadium";
    }
}
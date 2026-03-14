public abstract class Venue {

    private int venueId;
    private String venueName;
    private String venueType;
    private int capacity;
    private double cost;
    private String location;

    public Venue(int venueId, String venueName, String venueType,
                 int capacity, double cost, String location) {
        this.venueId = venueId;
        this.venueName = venueName;
        this.venueType = venueType;
        this.capacity = capacity;
        this.cost = cost;
        this.location = location;
    }

    public boolean matchesVenueId(int id) {
        return this.venueId == id;
    }

    public boolean matchesVenueName(String name) {
        return this.venueName.equalsIgnoreCase(name);
    }

    public boolean matchesVenueType(String type) {
        return this.venueType.equalsIgnoreCase(type);
    }

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

    @Override
    public String toString() {
        return "ID: " + venueId
            + ", Name: " + venueName
            + ", Type: " + venueType
            + ", Capacity: " + capacity
            + ", Cost: $" + String.format("%.2f", cost)
            + ", Location: " + location;
    }

    public int getVenueId() {
        return venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getVenueType() {
        return venueType;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getCost() {
        return cost;
    }

    public String getLocation() {
        return location;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public void setVenueType(String venueType) {
        this.venueType = venueType;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
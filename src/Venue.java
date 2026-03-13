public abstract class Venue {

    private int venueId;
    private String name;
    private String type;
    private int capacity;
    private double cost;
    private String location;

// Constructor
    public Venue(int venueId, String name, String type, int capacity, double cost, String location) {
        this.venueId = venueId;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.cost = cost;
        this.location = location;
    }

    public abstract String getVenueType();

    public boolean matchesId(int id) {
        return this.venueId == id;
    }

    public boolean matchesName(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    public boolean matchesType(String type) {
        return this.type.equalsIgnoreCase(type);
    }

    public boolean matchesSearch(String input) {
        if (matchesName(input) || matchesType(input)) {
            return true;
        }

        try {
            int id = Integer.parseInt(input);
            return matchesId(id);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "ID: " + venueId +
               ", Name: " + name +
               ", Type: " + type +
               ", Capacity: " + capacity +
               ", Cost: $" + String.format("%.2f", cost) +
               ", Location: " + location;
    }

// Getters and Setters
    public int getVenueId() {
        return venueId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
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

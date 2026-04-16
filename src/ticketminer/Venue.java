package ticketminer;

public abstract class Venue {

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

    public Venue(int venueId, String venueName, String venueType,
                 int capacity, int concertCapacity, double cost,
                 double vipPercent, double goldPercent, double silverPercent,
                 double bronzePercent, double generalAdmissionPercent,
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
            + ", Concert Capacity: " + concertCapacity
            + ", Cost: $" + String.format("%.2f", cost)
            + ", VIP Percent: " + vipPercent
            + ", Gold Percent: " + goldPercent
            + ", Silver Percent: " + silverPercent
            + ", Bronze Percent: " + bronzePercent
            + ", General Admission Percent: " + generalAdmissionPercent
            + ", Reserved Extra Percent: " + reservedExtraPercent;
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

    public int getConcertCapacity() {
        return concertCapacity;
    }

    public double getCost() {
        return cost;
    }

    public double getVipPercent() {
        return vipPercent;
    }

    public double getGoldPercent() {
        return goldPercent;
    }

    public double getSilverPercent() {
        return silverPercent;
    }

    public double getBronzePercent() {
        return bronzePercent;
    }

    public double getGeneralAdmissionPercent() {
        return generalAdmissionPercent;
    }

    public double getReservedExtraPercent() {
        return reservedExtraPercent;
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

    public void setConcertCapacity(int concertCapacity) {
        this.concertCapacity = concertCapacity;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setVipPercent(double vipPercent) {
        this.vipPercent = vipPercent;
    }

    public void setGoldPercent(double goldPercent) {
        this.goldPercent = goldPercent;
    }

    public void setSilverPercent(double silverPercent) {
        this.silverPercent = silverPercent;
    }

    public void setBronzePercent(double bronzePercent) {
        this.bronzePercent = bronzePercent;
    }

    public void setGeneralAdmissionPercent(double generalAdmissionPercent) {
        this.generalAdmissionPercent = generalAdmissionPercent;
    }

    public void setReservedExtraPercent(double reservedExtraPercent) {
        this.reservedExtraPercent = reservedExtraPercent;
    }
}
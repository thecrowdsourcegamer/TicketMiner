package ticketminer;

public class Stadium extends Venue {

    public Stadium(int venueId, String name, String type, int capacity, int concertCapacity,
                   double cost, double vipPercent, double goldPercent, double silverPercent,
                   double bronzePercent, double generalAdmissionPercent, double reservedExtraPercent) {
        super(venueId, name, type, capacity, concertCapacity, cost,
              vipPercent, goldPercent, silverPercent, bronzePercent,
              generalAdmissionPercent, reservedExtraPercent);
    }

    @Override
    public String getVenueType() {
        return "Stadium";
    }
}
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Event {
    private  int id;
    private String name;
    private LocalDate date;
    private LocalTime time;
    private double vipPrice;
    private double goldPrice;
    private double silverPrice;
    private double bronzePrice;
    private double generalAdmissionPrice;
    private String eventType;

    //constructors

    public Event(){
        this.id = 0;
        this.name = null;
        this.date = null;
        this.time = null;
        this.vipPrice = 0;
        this.goldPrice = 0;
        this.silverPrice = 0;
        this.bronzePrice = 0;
        this.generalAdmissionPrice = 0;
        this.eventType = null;
    }

    public Event(int id, String name, LocalDate date, LocalTime time, double vipPrice, double goldPrice, double silverPrice, double bronzePrice, double generalAdmissionPrice, String eventType) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.vipPrice = vipPrice;
        this.goldPrice = goldPrice;
        this.silverPrice = silverPrice;
        this.bronzePrice = bronzePrice;
        this.generalAdmissionPrice = generalAdmissionPrice;
        this.eventType = eventType;
    }

    //getters and setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return this.date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return this.time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getVipPrice() {
        return this.vipPrice;
    }
    public void setVipPrice(double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public double getGoldPrice() {
        return this.goldPrice;
    }
    public void setGoldPrice(double goldPrice) {
        this.goldPrice = goldPrice;
    }

    public double getSilverPrice() {
        return this.silverPrice;
    }
    public void setSilverPrice(double silverPrice) {
        this.silverPrice = silverPrice;
    }

    public double getBronzePrice() {
        return this.bronzePrice;
    }
    public void setBronzePrice(double bronzePrice) {
        this.bronzePrice = bronzePrice;
    }

    public double getGeneralAdmissionPrice() {
        return this.generalAdmissionPrice;
    }
    public void setGeneralAdmissionPrice(double generalAdmissionPrice) {
        this.generalAdmissionPrice = generalAdmissionPrice;
    }

    //methods
    public String getEventType(){
        return this.eventType;
    }
}
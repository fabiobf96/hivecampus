package it.hivecampuscompany.hivecampus.bean;

public class FiltersBean {
    private String university;
    private float distance;
    private int maxPrice;
    private Boolean privateBathroom;
    private Boolean balcony;
    private Boolean conditioner;
    private Boolean tvConnection;

    public FiltersBean(){
        // Default constructor
    }

    public FiltersBean(String university, Float distance, Integer maxPrice, Boolean privateBathroom, Boolean balcony, Boolean conditioner, Boolean tvConnection) {
        this.university = university;
        this.distance = distance;
        this.maxPrice = maxPrice;
        this.privateBathroom = privateBathroom;
        this.balcony = balcony;
        this.conditioner = conditioner;
        this.tvConnection = tvConnection;
    }

    public String getUniversity() { return university; }

    public Float getDistance() { return distance; }

    public Boolean getPrivateBathroom() { return privateBathroom; }

    public Boolean getBalcony() { return balcony; }

    public Boolean getConditioner() { return conditioner; }

    public Boolean getTvConnection() { return tvConnection; }

    public String toString() {
        return "University: " + university + "\n" +
                "Distance: " + distance + "\n" +
                "Max Price: " + maxPrice + "\n" +
                "Private Bathroom: " + privateBathroom + "\n" +
                "Balcony: " + balcony + "\n" +
                "Conditioner: " + conditioner + "\n" +
                "TV Connection: " + tvConnection;
    }
}
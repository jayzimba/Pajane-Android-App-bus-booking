package www.pajane.com;

public class Bus {

    private String imageURL;
    private String name;
    private int seats;
    private float rating;
    private float price;
    private String destination;
    private String from;
    private String station;

    public Bus(String imageURL, String station, String name,String from, int seats, float rating, float price, String destination) {
        this.imageURL = imageURL;
        this.name = name;
        this.seats = seats;
        this.rating = rating;
        this.price = price;
        this.name = destination;
        this.from = from;
        this.station = station;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Bus(){}

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float amount) {
        this.price = amount;
    }
}

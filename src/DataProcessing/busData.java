package DataProcessing;


public class busData {
    private int busNumber;
    private String busName;
    private String busType;
    private String source;
    private String destination;
    private int numberOfSeats;
    private String Stoppages;
    private String StartingTime;
    private Double Price;
    private String DestinationTime;

    public Double getPrice() {
        return Price;
    }
    public void setPrice(Double price) {
        Price = price;
    }


    public String getDestinationTime() {
        return DestinationTime;
    }

    public void setDestinationTime(String destinationTime) {
        DestinationTime = destinationTime;
    }


    public String getStartingTime() {
        return StartingTime;
    }

    public void setStartingTime(String startingTime) {
        StartingTime = startingTime;
    }

    public String getStoppages() {
        return Stoppages;
    }
    public void setStoppages(String stoppages) {
        Stoppages = stoppages;
    }
    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}

package DataProcessing;

import java.util.ArrayList;

public class bookingsData {
    private String DateOfJourney;
    private String Customer_id;
    private String BookingDate;
    private String sourceLocation;
    private String Destination;
    private String journeyActivity;
    private ArrayList<busData> busList;
    private busData selectedBus;
    private int customer;

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public busData getSelectedBus() {
        return selectedBus;
    }

    public void setSelectedBus(busData selectedBus) {
        this.selectedBus = selectedBus;
    }

    public ArrayList<busData> getBusList() {
        return busList;
    }

    public void setBusList(ArrayList<busData> busList) {
        this.busList = busList;
    }

    public String getDateOfJourney() {
        return DateOfJourney;
    }

    public void setDateOfJourney(String dateOfJourney) {
        DateOfJourney = dateOfJourney;
    }

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String customer_id) {
        Customer_id = customer_id;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(String sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }


    public String getJourneyActivity() {
        return journeyActivity;
    }

    public void setJourneyActivity(String journeyActivity) {
        this.journeyActivity = journeyActivity;
    }
}

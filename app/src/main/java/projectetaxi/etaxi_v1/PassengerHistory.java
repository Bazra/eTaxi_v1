package projectetaxi.etaxi_v1;

/**
 * Created by Ashim Bazracharya on 10/21/2017.
 */

public class PassengerHistory {

    private String bookingDate, sourceLat, sourceLng, destLat, destLng, passenAmount;

    public PassengerHistory() {

    }

    public PassengerHistory(String bookingDate, String sourceLat, String sourceLng, String destLat, String destLng, String passenAmount) {
        this.bookingDate = bookingDate;
        this.sourceLat = sourceLat;
        this.sourceLng = sourceLng;
        this.destLat = destLat;
        this.destLng = destLng;
        this.passenAmount = passenAmount;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getSourceLat() {
        return sourceLat;
    }

    public void setSourceLat(String sourceLat) {
        this.sourceLat = sourceLat;
    }

    public String getSourceLng() {
        return sourceLng;
    }

    public void setSourceLng(String sourceLng) {
        this.sourceLng = sourceLng;
    }

    public String getDestLat() {
        return destLat;
    }

    public void setDestLat(String destLat) {
        this.destLat = destLat;
    }

    public String getDestLng() {
        return destLng;
    }

    public void setDestLng(String destLng) {
        this.destLng = destLng;
    }

    public String getPassenAmount() {
        return passenAmount;
    }

    public void setPassenAmount(String passenAmount) {
        this.passenAmount = passenAmount;
    }
}
package projectetaxi.etaxi_v1;

/**
 * Created by Ashim Bazracharya on 11/6/2017.
 */

public class NearbyDriverDetails {

    private String name, email, mobileNum, taxi, lat, lng;
    private double dLat, dLng;

    public NearbyDriverDetails(String name,
                               String email,
                               String mobileNum,
                               String taxi,
                               String lat,
                               String lng,
                               double dLat,
                               double dLng) {
        this.name = name;
        this.email = email;
        this.mobileNum = mobileNum;
        this.taxi = taxi;
        this.lat = lat;
        this.lng = lng;
        this.dLat = dLat;
        this.dLng = dLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getTaxi() {
        return taxi;
    }

    public void setTaxi(String taxi) {
        this.taxi = taxi;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public double getdLat() {
        return dLat;
    }

    public void setdLat(double dLat) {
        this.dLat = dLat;
    }

    public double getdLng() {
        return dLng;
    }

    public void setdLng(double dLng) {
        this.dLng = dLng;
    }
}

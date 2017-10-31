package projectetaxi.etaxi_v1;

/**
 * Created by Ashim Bazracharya on 10/31/2017.
 */

public class DriverDetail {

    private String name, email, mobNum, taxi, lat, lng;

    public DriverDetail(String name, String email, String mobNum, String taxi, String lat, String lng) {
        this.name = name;
        this.email = email;
        this.mobNum = mobNum;
        this.taxi = taxi;
        this.lat = lat;
        this.lng = lng;
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

    public String getMobNum() {
        return mobNum;
    }

    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
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
}

package projectetaxi.etaxi_v1;

/**
 * Created by Ashim Bazracharya on 10/15/2017.
 */

public class URLRequest {

    private static int num1 = 192;
    private static int num2 = 168;
    private static int num3 = 100;
    private static int num4 = 26;

    private static String lat;
    private static String lng;


    public static void setLat(String lat) {
        URLRequest.lat = lat;
    }

    public static void setLng(String lng) {
        URLRequest.lng = lng;
    }

    public static String driverLoginRequest = "http://192.168."+num1+"."+num2+"."+num3+"."+num4
            +":8000/api/v1/driver/login";
    public static String driverRequest = "http://192.168."+num1+"."+num2+"."+num3+"."+num4
            +":8000/api/v1/drivers";
    public static String passengerLoginRequest = "http://192.168."+num1+"."+num2+"."+num3+"."
            +num4+":8000/api/v1/passenger/login";
    public static String passengerRequest = "http://192.168."+num1+"."+num2+"."+num3+"."
            +num4+":8000/api/v1/passengers";
    public static String bookingHistoryRequest = "http://192.168."+num1+"."+num2+"."+num3+"."+num4
            +":8000/api/v1/taxi_booking/history";
    public static String bookingRequest = "http://192.168."+num1+"."+num2+"."+num3+"."+num4
            +":8000/api/v1/taxi_bookings";

    public static String getAddressFromLatlongUrl() {

        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
                + lat + ","
                + lng;
        return url;
    }
}

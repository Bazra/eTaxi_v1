package projectetaxi.etaxi_v1;

/**
 * Created by Ashim Bazracharya on 10/15/2017.
 */

public class URLRequest {

    private static int num1 = 192;
    private static int num2 = 168;
    private static int num3 = 100;
    private static int num4 = 4;

    public static String driverLoginRequest = "http://"+num1+"."+num2+"."+num3+"."+num4
            +":8000/api/v1/driver/login";

    public static String driverRequest = "http://"+num1+"."+num2+"."+num3+"."+num4
            +":8000/api/v1/drivers";

    public static String passengerLoginRequest = "http://"+num1+"."+num2+"."+num3+"."
            +num4+":8000/api/v1/passenger/login";

    public static String passengerRequest = "http://"+num1+"."+num2+"."+num3+"."
            +num4+":8000/api/v1/passengers";

//    public static String bookingHistoryRequest = "http://"+num1+"."+num2+"."+num3+"."+num4
//            +":8000/api/v1/taxi_booking/history";

    public static String bookingRequest = "http://"+num1+"."+num2+"."+num3+"."+num4
            +":8000/api/v1/taxi_bookings";

    public static String freeDrivers = "http://"+num1+"."+num2+"."+num3+"."+num4
            +":8000/api/v1/driver/free";

    public static String fareRateUrl = "http://"+num1+"."+num2+"."+num3+"."+num4
            +":8000/api/v1/taxi_fare_rates";

    public static String fcmTokenUrl = "http://"+num1+"."+num2+"."+num3+"."+num4
            +":8000/api/v1/driver_fcm_tokens";
}

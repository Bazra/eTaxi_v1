package projectetaxi.etaxi_v1;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashim Bazracharya on 10/10/2017.
 */

public class BookingRequest extends StringRequest {

    //final String TAG = this.getClass().getName();

    PassengerLoginActivity passenger = new PassengerLoginActivity();

    private static final String BOOKING_REQUEST_URL = URLRequest.bookingRequest;
    private Map<String, String> params;

    public BookingRequest(String roadType, String driverEmail, String passengerEmail, String srcLat,
                          String srcLong, String destLat, String destLong, /*String bookingStatus*/
                          String amount, Response.Listener<String>listener) {
        super(Method.POST, BOOKING_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("roadType", roadType);
        params.put("driverEmail", driverEmail);
        params.put("passengerEmail", passengerEmail);
        params.put("sourceLatitude", srcLat);
        params.put("sourceLongitude", srcLong );
        params.put("destinationLatitude", destLat);
        params.put("destinationLongitude", destLong);
        //params.put("status", bookingStatus);
        params.put("amount", amount);
    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        Map<String, String> params = new HashMap<>();

        //params.put("Content-Type", "application/json");
        params.put("Accept", "application/json");
        params.put("Authorization", "Bearer " + passenger.getPassenToken());

        return params;
    }

//    @Override
//    public Map<String, String> getHeaders() {
//        params.put("Authorization", "Bearer " + token);
//        return params;
//    }
}

package projectetaxi.etaxi_v1;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashim Bazracharya on 11/7/2017.
 */

public class DestinationLatlngUpdateRequest extends StringRequest {

    PassengerLoginActivity passengerLoginActivity = new PassengerLoginActivity();
    private Map<String, String> params;

    public DestinationLatlngUpdateRequest(String URL,
                                          String driverEmail,
                                          String destLat,
                                          String destLng,
                                          Response.Listener<String>listener) {
        super(Method.PUT, URL, listener, null);

        params = new HashMap<>();
        params.put("driverEmail", driverEmail);
        params.put("passengerEmail", passengerLoginActivity.getPassenEmail());
        params.put("destinationLatitude", destLat);
        params.put("destinationLongitude", destLng);
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
        params.put("Authorization", "Bearer " + passengerLoginActivity.getPassenToken());

        return params;
    }
}

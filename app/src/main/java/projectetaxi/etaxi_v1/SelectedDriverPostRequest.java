package projectetaxi.etaxi_v1;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashim Bazracharya on 11/7/2017.
 */

public class SelectedDriverPostRequest extends StringRequest {

    PassengerLoginActivity passengerLoginActivity = new PassengerLoginActivity();

    private static final String POST_REQUEST_URL = URLRequest.postSeletectedDriverUrl;
    private Map<String, String> params;

    public SelectedDriverPostRequest(String driverEmail, Response.Listener<String>listener) {
        super(Method.POST, POST_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("driverEmail", driverEmail);
        params.put("passengerEmail", passengerLoginActivity.getPassenEmail());
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

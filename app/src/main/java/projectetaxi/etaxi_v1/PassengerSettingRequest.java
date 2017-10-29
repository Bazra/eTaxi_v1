package projectetaxi.etaxi_v1;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashim Bazracharya on 10/22/2017.
 */

public class PassengerSettingRequest extends StringRequest{

    final String TAG = this.getClass().getName();

    static PassengerLoginActivity passenger = new PassengerLoginActivity();

    private final static String PASSENGER_SETTING_URL = URLRequest.passengerRequest + "/" +
            passenger.getPassenEmail();
    private Map<String, String> params;

    public PassengerSettingRequest(
            String name,
            String email,
            String password,
            String mobileNum,
            String address,
            String currentLat,
            String currentLong,
            Response.Listener<String>listener) {
        super(Method.PUT, PASSENGER_SETTING_URL, listener, null);

        Log.d(TAG, PASSENGER_SETTING_URL);

        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        params.put("mobileNumber", mobileNum);
        params.put("address", address);
        params.put("latitude", currentLat);
        params.put("longitude", currentLong);
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
}

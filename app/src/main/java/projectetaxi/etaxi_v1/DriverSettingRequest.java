package projectetaxi.etaxi_v1;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashim Bazracharya on 10/22/2017.
 */

public class DriverSettingRequest extends StringRequest {


    final String TAG = this.getClass().getName();

    static DriverLoginActivity driver = new DriverLoginActivity();

    private final static String DRIVER_SETTING_URL = URLRequest.driverRequest + "/" +
            driver.getDriEmail();
    private Map<String, String> params;

    public DriverSettingRequest(
            String name,
            String email,
            String password,
            String mobileNum,
            String taxiNum,
            String license,
            String address,
            String currentLat,
            String currentLong,
            String status,
            Response.Listener<String>listener) {
        super(Method.PUT, DRIVER_SETTING_URL, listener, null);

        Log.d(TAG, DRIVER_SETTING_URL);

        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        params.put("mobileNumber", mobileNum);
        params.put("taxiNumber", taxiNum);
        params.put("licenseNumber", license);
        params.put("address", address);
        params.put("latitude", currentLat);
        params.put("longitude", currentLong);
        params.put("status", status);

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
        params.put("Authorization", "Bearer " + driver.getDriToken());

        return params;
    }

}

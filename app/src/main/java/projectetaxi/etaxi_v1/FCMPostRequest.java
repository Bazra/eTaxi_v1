package projectetaxi.etaxi_v1;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashim Bazracharya on 11/4/2017.
 */

public class FCMPostRequest extends StringRequest {

    DriverLoginActivity driverLoginActivity = new DriverLoginActivity();

    private static final String FCM_TOKEN_POST_REQUEST_URL = URLRequest.fcmTokenPostUrl;
    private Map<String, String> params;

    public FCMPostRequest(String email, String fcm_token, Response.Listener<String>listener) {
        super(Method.POST, FCM_TOKEN_POST_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("email", email);
        params.put("fcm_token", fcm_token);
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
        params.put("Authorization", "Bearer " + driverLoginActivity.getDriToken());

        return params;
    }
}

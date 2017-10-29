package projectetaxi.etaxi_v1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashim Bazracharya on 10/13/2017.
 */

public class DriverLoginRequest extends StringRequest {

    private static String LOGIN_REQUEST_URL = URLRequest.driverLoginRequest;
    private Map<String, String> params;

    public DriverLoginRequest(String email, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}

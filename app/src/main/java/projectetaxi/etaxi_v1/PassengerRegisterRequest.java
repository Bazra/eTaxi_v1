package projectetaxi.etaxi_v1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashim Bazracharya on 10/10/2017.
 */

public class PassengerRegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = URLRequest.passengerRequest;
    private Map<String, String> params;

    public PassengerRegisterRequest(String name, String email, String password, String mobileNum,
                                    String address,
                                    Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        params.put("mobileNumber", mobileNum);
        params.put("address", address);
    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }

   /* @Override
    public Map<String, String> getHeaders() {
        params.put("Content-Type", "application/json");
        params.put("Accept", "application/json");

        return params;
    }*/
}

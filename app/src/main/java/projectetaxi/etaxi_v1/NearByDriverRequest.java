package projectetaxi.etaxi_v1;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

/**
 * Created by Ashim Bazracharya on 10/31/2017.
 */

public class NearByDriverRequest extends JsonArrayRequest {

    final String TAG = this.getClass().getName();

    public NearByDriverRequest(Response.Listener<JSONArray>listener) {

        super(Request.Method.GET,
                URLRequest.freeDrivers,
                null,
                listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });
    }
}

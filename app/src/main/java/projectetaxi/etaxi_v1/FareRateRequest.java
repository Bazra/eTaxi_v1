package projectetaxi.etaxi_v1;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

/**
 * Created by Ashim Bazracharya on 10/31/2017.
 */

public class FareRateRequest extends JsonArrayRequest {

    final String TAG = this.getClass().getName();

    public static String fareRequestUrl = URLRequest.fareRateUrl;

    public FareRateRequest(Response.Listener<JSONArray>listener) {

        super(Request.Method.GET,
                fareRequestUrl,
                null,
                listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });
    }
}
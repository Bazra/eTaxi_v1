package projectetaxi.etaxi_v1;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Ashim Bazracharya on 10/31/2017.
 */

public class ReviewBookingRequest extends JsonObjectRequest {

    final String TAG = this.getClass().getName();
    static DestinationSelectionActivity destinationSelectionActivity = new DestinationSelectionActivity();

//    public static String srclatitude = destinationSelectionActivity.getCurrentLat();
//    public static String srclongitude = destinationSelectionActivity.getCurrentLng();
//    public static String dstlatitude = destinationSelectionActivity.getDestinationLat();
//    public static String dstlongitude = destinationSelectionActivity.getDestinationLng();

//    public static String googleLatlngToAddressUrl = "http://maps.googleapis.com/maps/api/directions/json?"+
//            "origin="+srclatitude+","+srclongitude+"&"+
//            "destination="+dstlatitude+","+dstlongitude+"&"+
//            "sensor=false";

    public ReviewBookingRequest(String URL, Response.Listener<JSONObject>listener) {

        super(Method.POST,
                URL,
                null,
                listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
        });
    }
}
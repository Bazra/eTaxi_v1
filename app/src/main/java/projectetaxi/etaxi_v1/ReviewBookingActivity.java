package projectetaxi.etaxi_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReviewBookingActivity extends AppCompatActivity {

    final String TAG = this.getClass().getName();

    private String source, destination, distance, duration;
    private  String srcLat, srcLng, destLat, destLng;
    private int intDistance;
    private String driverEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");

                    Log.d(TAG, "response: "+response);
                    Log.d(TAG, "status: "+status);

                    JSONArray arrRoutes = response.getJSONArray("routes");
                    JSONObject routes = arrRoutes.getJSONObject(0);
                    JSONArray arrLegs = routes.getJSONArray("legs");
                    JSONObject legs = arrLegs.getJSONObject(0);
                    JSONObject objDistance = legs.getJSONObject("distance");
                    JSONObject objDuration = legs.getJSONObject("duration");

                    distance = objDistance.getString("text");
                    intDistance = objDistance.getInt("value");
                    source = legs.getString("start_address");
                    destination = legs.getString("end_address");
                    duration = objDuration.getString("text");

                    Log.d(TAG, "strDistance: "+distance);
                    Log.d(TAG, "intDistance: "+intDistance);
                    Log.d(TAG, "source: "+source);
                    Log.d(TAG, "destination: "+destination);
                    Log.d(TAG, "duration: "+duration);


                    Intent intent = new Intent(ReviewBookingActivity.this,
                            AmountCalculationActivity.class);

                    ReviewBookingActivity.this.startActivity(intent);
                    Bundle reviewBundle = new Bundle();
                    reviewBundle.putString("driverEmail", driverEmail);
                    reviewBundle.putString("src", source);
                    reviewBundle.putString("dest", destination);
                    reviewBundle.putString("distance", distance);
                    reviewBundle.putString("duration", duration);
                    reviewBundle.putInt("intDistance", intDistance);
                    reviewBundle.putString("srcLat", srcLat);
                    reviewBundle.putString("srcLng", srcLng);
                    reviewBundle.putString("destLat", destLat);
                    reviewBundle.putString("destLng", destLng);
                    intent.putExtras(reviewBundle);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Bundle bundle = getIntent().getExtras();
        srcLat = bundle.getString("srcLat");
        srcLng = bundle.getString("srcLng");
        destLat = bundle.getString("destLat");
        destLng = bundle.getString("destLng");
        driverEmail = bundle.getString("driverEmail");

        String googleLatlngToAddressUrl = "http://maps.googleapis.com/maps/api/directions/json?"+
                "origin="+srcLat+","+srcLng+"&"+
                "destination="+destLat+","+destLng+"&"+
                "sensor=false";

        ReviewBookingRequest request = new ReviewBookingRequest(googleLatlngToAddressUrl ,
                responseListener);

        Log.d(TAG, "Request: "+request);

        RequestQueue queue = Volley.newRequestQueue(ReviewBookingActivity.this);

        Log.d(TAG, "RequestQueue: "+queue);

        queue.add(request);

        Log.d(TAG, "AddedRequestQueue: "+queue);

    }

}
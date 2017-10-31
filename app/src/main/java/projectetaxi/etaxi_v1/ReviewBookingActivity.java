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

    private static String source, destination, distance, duration;
    private static int intDistance;

    public static String getSource() {
        return source;
    }

    public static void setSource(String source) {
        ReviewBookingActivity.source = source;
    }

    public static String getDestination() {
        return destination;
    }

    public static void setDestination(String destination) {
        ReviewBookingActivity.destination = destination;
    }

    public static String getDistance() {
        return distance;
    }

    public static void setDistance(String distance) {
        ReviewBookingActivity.distance = distance;
    }

    public static String getDuration() {
        return duration;
    }

    public static void setDuration(String duration) {
        ReviewBookingActivity.duration = duration;
    }

    public int getIntDistance() {
        return intDistance;
    }

    public void setIntDistance(int intDistance) {
        this.intDistance = intDistance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        final Button btReviewBooking = findViewById(R.id.btReviewBooking);

        btReviewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "Clicked.....");

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

                            String strDistance = objDistance.getString("text");
                            Integer intDist = objDistance.getInt("value");
                            String src = legs.getString("start_address");
                            String dest = legs.getString("end_address");
                            String dur = objDuration.getString("text");

                            distance = strDistance;
                            intDistance = intDist;
                            source = src;
                            destination = dest;
                            duration = dur;

                            Log.d(TAG, "strDistance: "+strDistance);
                            Log.d(TAG, "intDistance: "+intDistance);
                            Log.d(TAG, "source: "+source);
                            Log.d(TAG, "destination: "+destination);
                            Log.d(TAG, "duration: "+duration);


                            Intent intent = new Intent(ReviewBookingActivity.this,
                                    AmountCalculationActivity.class);

                            ReviewBookingActivity.this.startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                ReviewBookingRequest request = new ReviewBookingRequest(responseListener);

                Log.d(TAG, "Request: "+request);

                RequestQueue queue = Volley.newRequestQueue(ReviewBookingActivity.this);

                Log.d(TAG, "RequestQueue: "+queue);

                queue.add(request);

                Log.d(TAG, "AddedRequestQueue: "+queue);

            }
        });

    }

}
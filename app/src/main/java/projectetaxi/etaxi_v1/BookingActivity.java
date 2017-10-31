package projectetaxi.etaxi_v1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashim Bazracharya on 10/17/2017.
 */

public class BookingActivity extends AppCompatActivity{

    final String TAG = this.getClass().getName();

    ArrayList<DriverDetail> driverList = new ArrayList<>();

    private static String source, destination, distance, duration;
    private static int intDistance;

    public static String getSource() {
        return source;
    }

    public static void setSource(String source) {
        BookingActivity.source = source;
    }

    public static String getDestination() {
        return destination;
    }

    public static void setDestination(String destination) {
        BookingActivity.destination = destination;
    }

    public static String getDistance() {
        return distance;
    }

    public static void setDistance(String distance) {
        BookingActivity.distance = distance;
    }

    public static String getDuration() {
        return duration;
    }

    public static void setDuration(String duration) {
        BookingActivity.duration = duration;
    }

    public int getIntDistance() {
        return intDistance;
    }

    public void setIntDistance(int intDistance) {
        this.intDistance = intDistance;
    }


    private static String name, email, mobileNum, taxiNum, lat, lng;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        BookingActivity.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        BookingActivity.email = email;
    }

    public static String getMobileNum() {
        return mobileNum;
    }

    public static void setMobileNum(String mobileNum) {
        BookingActivity.mobileNum = mobileNum;
    }

    public static String getTaxiNum() {
        return taxiNum;
    }

    public static void setTaxiNum(String taxiNum) {
        BookingActivity.taxiNum = taxiNum;
    }

    public static String getLat() {
        return lat;
    }

    public static void setLat(String lat) {
        BookingActivity.lat = lat;
    }

    public static String getLng() {
        return lng;
    }

    public static void setLng(String lng) {
        BookingActivity.lng = lng;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        final BookingData data = new BookingData();

        final PassengerLoginActivity passengerLoginActivity = new PassengerLoginActivity();

        final AmountCalculationActivity amountCalculationActivity = new AmountCalculationActivity();

        final DestinationSelectionActivity destinationSelectionActivity =
                new DestinationSelectionActivity();

        final Button btDestination = (Button) findViewById(R.id.btDestination);
        final Button btNearbyDriver = (Button) findViewById(R.id.btNearbyDriver);
        final Button btReviewBooking = (Button) findViewById(R.id.btReviewBooking);
        final Button btBookTaxi = (Button) findViewById(R.id.btBookTaxi);


        //action for setting destination

        btDestination.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(BookingActivity.this, DestinationSelectionActivity.class);
                BookingActivity.this.startActivity(intent);
            }
        });

        btNearbyDriver.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Response.Listener<JSONArray> responseListener =

                        new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            for(int i=0; i<response.length(); i++){

                                JSONObject driverDetailObj = response.getJSONObject(i);
                                String drivName = driverDetailObj.getString("name");
                                String drivEmail = driverDetailObj.getString("email");
                                String drivMobNum = driverDetailObj.getString("mobileNumber");
                                String drivTaxi = driverDetailObj.getString("taxiNumber");
                                String drivLat = driverDetailObj.getString("latitude");
                                String drivLng = driverDetailObj.getString("longitude");

                                Log.d(TAG, "detailXXXXXXXXXXXXXXXXXXX:" +drivName
                                        +drivEmail
                                        +drivMobNum
                                        +drivTaxi
                                        +drivLat
                                        +drivLng
                                );

                            }

                            Intent intent = new Intent(BookingActivity.this,
                                    NearByDriverActivity.class);
                            BookingActivity.this.startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                NearByDriverRequest request = new NearByDriverRequest(responseListener);
                Log.d(TAG, "Nearby Driver Request: " + request);
                RequestQueue queue = Volley.newRequestQueue(BookingActivity.this);
                queue.add(request);
            }
        });

        //This action is for the  button Book that post taxi bookings data in the database...

        btBookTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String roadType = amountCalculationActivity.getRoadType();
                final String driverEmail = data.driverEmail;
                final String passengerEmail = passengerLoginActivity.getPassenEmail();
                final String srcLat = destinationSelectionActivity.getCurrentLat();
                final String srcLong = destinationSelectionActivity.getCurrentLng();
                final String destLat = destinationSelectionActivity.getDestinationLat();
                final String destLong = destinationSelectionActivity.getDestinationLng();
                //final String bookingStatus = data.bookingStatus;
                final String amount = ""+amountCalculationActivity.getAmount();

                Log.d(TAG, "Booking Data: "
                        + roadType
                        + driverEmail
                        + passengerEmail
                        + srcLat
                        + srcLong
                        + destLat
                        + destLong
                        + amount
                );

                Response.Listener<String> responseListener = new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {

                                Toast.makeText(
                                        getApplicationContext(),
                                        "Booking Done",
                                        Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(BookingActivity.this, PassengerMainActivity.class);
                                BookingActivity.this.startActivity(intent);
                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                                builder.setMessage("Booking Failed")
                                        .setNegativeButton("Try Again", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                BookingRequest request = new BookingRequest(roadType, driverEmail, passengerEmail,
                        srcLat, srcLong, destLat, destLong, amount, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BookingActivity.this);
                queue.add(request);
            }
        });

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


                            Intent intent = new Intent(BookingActivity.this,
                                    AmountCalculationActivity.class);

                            BookingActivity.this.startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                ReviewBookingRequest request = new ReviewBookingRequest(responseListener);

                Log.d(TAG, "Request: "+request);

                RequestQueue queue = Volley.newRequestQueue(BookingActivity.this);

                Log.d(TAG, "RequestQueue: "+queue);

                queue.add(request);

                Log.d(TAG, "AddedRequestQueue: "+queue);

            }
        });





    }

}

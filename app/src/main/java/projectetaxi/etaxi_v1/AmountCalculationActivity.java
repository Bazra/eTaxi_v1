package projectetaxi.etaxi_v1;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AmountCalculationActivity extends AppCompatActivity {

    private double amount;
    private String roadType, src, dest, srcLt, srcLn, dstLt, dstLn, driverEmail, dist, dur;
    private int intDistance;

    public double getAmount() {
        return amount;
    }

    public String getRoadType() {
        return roadType;
    }

    final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_calculation);

        Bundle bundle = getIntent().getExtras();
        srcLt = bundle.getString("srcLat");
        srcLn = bundle.getString("srcLng");
        dstLt = bundle.getString("destLat");
        dstLn = bundle.getString("destLng");
        dest = bundle.getString("dest");
        src = bundle.getString("src");
        dist = bundle.getString("distance");
        dur = bundle.getString("duration");
        driverEmail = bundle.getString("driverEmail");
        intDistance = bundle.getInt("intDistance");

        final TextView tvFrom = (TextView) findViewById(R.id.tvPassFrom);
        final TextView tvTo = (TextView) findViewById(R.id.tvPassTo);
        final TextView tvDistance = (TextView) findViewById(R.id.tvPassDistance);
        final TextView tvDuration = (TextView) findViewById(R.id.tvPassTime);
        final TextView tvAmount = (TextView) findViewById(R.id.tvAmount);

        tvFrom.setText(src);
        tvTo.setText(dest);
        tvDistance.setText(dist);
        tvDuration.setText(dur);

        final Button btCalculate = (Button) findViewById(R.id.btCalcAmount);
        final Button btDone = (Button) findViewById(R.id.btDone);

        btCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            JSONObject objCity = response.getJSONObject(0);
                            JSONObject objHwy = response.getJSONObject(1);

                            String cityRate = objCity.getString("rate");
                            String highwayRate = objHwy.getString("rate");

                            Log.d(TAG, "City Rate: " + cityRate);
                            Log.d(TAG, "Highway Rate: " + highwayRate);

                            double dbCityRate = Double.parseDouble(cityRate);
                            double dbHighwayRate = Double.parseDouble(highwayRate);

                            String lowSrc = src.toLowerCase();
                            String lowDest = dest.toLowerCase();

                            Log.d(TAG, "src: " + src);
                            Log.d(TAG, "dest: " + dest);
                            Log.d(TAG, "dist: " + dist);

                            if (((lowSrc.contains("highway")) ||
                                    (lowSrc.contains("hwy")) ||
                                    (lowSrc.contains("rajmarg"))) &&
                                    ((lowDest.contains("highway")) ||
                                            (lowDest.contains("hwy")) ||
                                            (lowDest.contains("rajmarg")))) {

                                amount = dbHighwayRate * intDistance / 1000;
                                tvAmount.setText("Rs. " + amount + " /-");
                                btCalculate.setText("Taxi Fee");
                                roadType = "Highway";

                            } else {

                                amount = dbCityRate * intDistance / 1000;
                                tvAmount.setText("Rs. " + amount + " /-");
                                btCalculate.setText("Taxi Fee");
                                roadType = "City Road";
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                FareRateRequest request = new FareRateRequest(responseListener);
                Log.d(TAG, "Fare Request: " + request);
                RequestQueue queue = Volley.newRequestQueue(AmountCalculationActivity.this);
                queue.add(request);

            }
        });

        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                                Intent intent = new Intent(AmountCalculationActivity.this,
                                        PassengerMainActivity.class);
                                AmountCalculationActivity.this.startActivity(intent);
                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(
                                        AmountCalculationActivity.this);
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

                BookingRequest request = new BookingRequest(roadType, driverEmail,
                        srcLt, srcLn, dstLt, dstLn, String.valueOf(amount), responseListener);

                Log.d(TAG, "roadType: " + roadType);
                Log.d(TAG, "driverEmail: " + driverEmail);
                Log.d(TAG, "srcLt: " + srcLt);
                Log.d(TAG, "srcLn: " + srcLn);
                Log.d(TAG, "dstLt: " + dstLt);
                Log.d(TAG, "detLn: " + dstLn);
                Log.d(TAG, "amount: " + String.valueOf(amount));
                RequestQueue queue = Volley.newRequestQueue(AmountCalculationActivity.this);
                queue.add(request);

            }
        });
    }
}
package projectetaxi.etaxi_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AmountCalculationActivity extends AppCompatActivity {

    private static double amount;
    private static String roadType;

    public static double getAmount() {
        return amount;
    }

    public static void setAmount(double amount) {
        AmountCalculationActivity.amount = amount;
    }

    public static String getRoadType() {
        return roadType;
    }

    public static void setRoadType(String roadType) {
        AmountCalculationActivity.roadType = roadType;
    }

    final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_calculation);

        final BookingActivity bookingActivity = new BookingActivity();

        final TextView tvFrom = (TextView) findViewById(R.id.tvPassFrom);
        final TextView tvTo = (TextView) findViewById(R.id.tvPassTo);
        final TextView tvDistance = (TextView) findViewById(R.id.tvPassDistance);
        final TextView tvDuration = (TextView) findViewById(R.id.tvPassTime);
        final TextView tvAmount = (TextView) findViewById(R.id.tvAmount);

        tvFrom.setText(bookingActivity.getSource());
        tvTo.setText(bookingActivity.getDestination());
        tvDistance.setText(bookingActivity.getDistance());
        tvDuration.setText(bookingActivity.getDuration());

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

                            String src = bookingActivity.getSource().toLowerCase();
                            String dest = bookingActivity.getDestination().toLowerCase();
                            int dist = bookingActivity.getIntDistance();

                            Log.d(TAG, "src: " + src);
                            Log.d(TAG, "dest: " + dest);
                            Log.d(TAG, "dist: " + dist);

                            if (((src.contains("highway")) ||
                                    (src.contains("hwy")) ||
                                    (src.contains("rajmarg"))) &&
                                    ((dest.contains("highway")) ||
                                            (dest.contains("hwy")) ||
                                            (dest.contains("rajmarg")))) {

                                amount = dbHighwayRate * dist / 1000;
                                tvAmount.setText("Rs. " + amount + " /-");
                                btCalculate.setText("Taxi Fee");
                                roadType = "Highway";

                            } else {

                                amount = dbCityRate * dist / 1000;
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

                Intent intent = new Intent(AmountCalculationActivity.this, BookingActivity.class);
                AmountCalculationActivity.this.startActivity(intent);

            }
        });
    }
}
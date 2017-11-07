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

    private double amount;
    private String roadType, src, dest, srcLt, srcLn, dstLt, dstLn, driverEmail, dist, dur;
    private int intDistance;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRoadType() {
        return roadType;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrcLt() {
        return srcLt;
    }

    public void setSrcLt(String srcLt) {
        this.srcLt = srcLt;
    }

    public String getSrcLn() {
        return srcLn;
    }

    public void setSrcLn(String srcLn) {
        this.srcLn = srcLn;
    }

    public String getDstLt() {
        return dstLt;
    }

    public void setDstLt(String dstLt) {
        this.dstLt = dstLt;
    }

    public String getDstLn() {
        return dstLn;
    }

    public void setDstLn(String dstLn) {
        this.dstLn = dstLn;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getDur() {
        return dur;
    }

    public void setDur(String dur) {
        this.dur = dur;
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
        intDistance = bundle.getInt("intDistance");

        final BookingActivity bookingActivity = new BookingActivity();

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

                Intent intent = new Intent(AmountCalculationActivity.this, PassengerMainActivity.class);
                AmountCalculationActivity.this.startActivity(intent);

            }
        });
    }
}
package projectetaxi.etaxi_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PassengerSettingActivity extends AppCompatActivity {

    final String TAG = this.getClass().getName();

    PassengerLoginActivity passengerLoginActivity = new PassengerLoginActivity();
   PassengerMainActivity passengerMainActivity = new PassengerMainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_setting);

        final EditText etName = (EditText) findViewById(R.id.etChgName);
        final EditText etPassword = (EditText) findViewById(R.id.etChgPassword);

        final Button name = (Button) findViewById(R.id.btChgName);
        final Button password = (Button) findViewById(R.id.btChgPassword);



        Response.Listener<String> responseListener = new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success) {

                        Toast.makeText(getApplicationContext(),
                                "Current Location Updated.",
                                Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getApplicationContext(),
                                "Could Not Update Current Location",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        PassengerSettingRequest request = new PassengerSettingRequest(
                "" + passengerLoginActivity.getPassenName(),
                "" + passengerLoginActivity.getPassenEmail(),
                "" + passengerLoginActivity.getPassenPassword(),
                "" + passengerLoginActivity.getPassenMobileNum(),
                "" + passengerLoginActivity.getPassenAddress(),
                "" + passengerMainActivity.getPassengerCurrentLat(),
                "" + passengerMainActivity.getPassengerCurrentLng(),
                responseListener);
        RequestQueue queue = Volley.newRequestQueue(PassengerSettingActivity.this);
        queue.add(request);


        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = etName.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {

                                passengerLoginActivity.setPassenName(name);
                                Toast.makeText(getApplicationContext(), "Name Changed Successfully.",
                                        Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(PassengerSettingActivity.this,
                                        PassengerMainActivity.class);
                                PassengerSettingActivity.this.startActivity(intent);

                            } else {

                                Toast.makeText(getApplicationContext(),
                                        "Could Not Get Changed, Give Another Try",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                PassengerSettingRequest request = new PassengerSettingRequest(
                        name,
                        "" + passengerLoginActivity.getPassenEmail(),
                        "" + passengerLoginActivity.getPassenPassword(),
                        "" + passengerLoginActivity.getPassenMobileNum(),
                        "" + passengerLoginActivity.getPassenAddress(),
                        "" + passengerMainActivity.getPassengerCurrentLat(),
                        "" + passengerMainActivity.getPassengerCurrentLng(),
                        responseListener
                );

                Log.d(TAG, name);
                Log.d(TAG, "email " + passengerLoginActivity.getPassenEmail());
                Log.d(TAG, "password " + passengerLoginActivity.getPassenPassword());
                Log.d(TAG, "mobileNum " + passengerLoginActivity.getPassenMobileNum());
                Log.d(TAG, "address " + passengerLoginActivity.getPassenAddress());
                Log.d(TAG, "lat " + passengerMainActivity.getPassengerCurrentLat());
                Log.d(TAG, "long " + passengerMainActivity.getPassengerCurrentLng());

                RequestQueue queue = Volley.newRequestQueue(PassengerSettingActivity.this);
                queue.add(request);

            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {

                                passengerLoginActivity.setPassenPassword(password);

                                Toast.makeText(getApplicationContext(),
                                        "Password Changed.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PassengerSettingActivity.this,
                                        PassengerMainActivity.class);
                                PassengerSettingActivity.this.startActivity(intent);

                            } else {

                                Toast.makeText(getApplicationContext(),
                                        "Could Not Get Changed, Give Another Try",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                PassengerSettingRequest request = new PassengerSettingRequest(
                        "" + passengerLoginActivity.getPassenName(),
                        "" + passengerLoginActivity.getPassenEmail(),
                        password,
                        "" + passengerLoginActivity.getPassenMobileNum(),
                        "" + passengerLoginActivity.getPassenAddress(),
                        "" + passengerMainActivity.getPassengerCurrentLat(),
                        "" + passengerMainActivity.getPassengerCurrentLng(),
                        responseListener);
                RequestQueue queue = Volley.newRequestQueue(PassengerSettingActivity.this);
                queue.add(request);


            }
        });
    }
}

package projectetaxi.etaxi_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverSettingActivity extends AppCompatActivity {

    DriverLoginActivity driverLoginActivity = new DriverLoginActivity();
    DriverMainActivity driverMainActivity = new DriverMainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_setting);

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

                        Toast.makeText(getApplicationContext(), "Current Location Updated.",
                                Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getApplicationContext(),
                                "Current Location Could Not Get Updated...",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        DriverSettingRequest request = new DriverSettingRequest(
                "" + driverLoginActivity.getDriName(),
                "" + driverLoginActivity.getDriEmail(),
                "" + driverLoginActivity.getDriPassword(),
                "" + driverLoginActivity.getDriMobileNum(),
                "" + driverLoginActivity.getDriTaxiNum(),
                "" + driverLoginActivity.getDriLicNum(),
                "" + driverLoginActivity.getDriAddress(),
                "" + driverMainActivity.getDriverCurrentLat(),
                "" + driverMainActivity.getDriverCurrentLng(),
                "" + driverLoginActivity.getStatus(),
                responseListener
        );

        RequestQueue queue = Volley.newRequestQueue(DriverSettingActivity.this);
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

                                driverLoginActivity.setDriName(name);

                                Toast.makeText(getApplicationContext(), "Name Changed.",
                                        Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(DriverSettingActivity.this,
                                        DriverMainActivity.class);
                                DriverSettingActivity.this.startActivity(intent);

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

                DriverSettingRequest request = new DriverSettingRequest(
                        name,
                        "" + driverLoginActivity.getDriEmail(),
                        "" + driverLoginActivity.getDriPassword(),
                        "" + driverLoginActivity.getDriMobileNum(),
                        "" + driverLoginActivity.getDriTaxiNum(),
                        "" + driverLoginActivity.getDriLicNum(),
                        "" + driverLoginActivity.getDriAddress(),
                        "" + driverMainActivity.getDriverCurrentLat(),
                        "" + driverMainActivity.getDriverCurrentLng(),
                        "" + driverLoginActivity.getStatus(),
                        responseListener
                );

           /*     Log.d(TAG, name);
                Log.d(TAG, "email " + passengerLoginActivity.getPassenEmail());
                Log.d(TAG, "password " + passengerLoginActivity.getPassenPassword());
                Log.d(TAG, "mobileNum " + passengerLoginActivity.getPassenMobileNum());
                Log.d(TAG, "address " + passengerLoginActivity.getPassenAddress());
*/
                RequestQueue queue = Volley.newRequestQueue(DriverSettingActivity.this);
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

                                driverLoginActivity.setDriPassword(password);

                                Toast.makeText(getApplicationContext(), "Password Changed.",
                                        Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(DriverSettingActivity.this,
                                        DriverMainActivity.class);
                                DriverSettingActivity.this.startActivity(intent);


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

                DriverSettingRequest request = new DriverSettingRequest(
                        "" + driverLoginActivity.getDriName(),
                        "" + driverLoginActivity.getDriEmail(),
                        password,
                        "" + driverLoginActivity.getDriMobileNum(),
                        "" + driverLoginActivity.getDriTaxiNum(),
                        "" + driverLoginActivity.getDriLicNum(),
                        "" + driverLoginActivity.getDriAddress(),
                        "" + driverMainActivity.getDriverCurrentLat(),
                        "" + driverMainActivity.getDriverCurrentLng(),
                        "" + driverLoginActivity.getStatus(),
                        responseListener
                );

           /*     Log.d(TAG, name);
                Log.d(TAG, "email " + passengerLoginActivity.getPassenEmail());
                Log.d(TAG, "password " + passengerLoginActivity.getPassenPassword());
                Log.d(TAG, "mobileNum " + passengerLoginActivity.getPassenMobileNum());
                Log.d(TAG, "address " + passengerLoginActivity.getPassenAddress());
*/
                RequestQueue queue = Volley.newRequestQueue(DriverSettingActivity.this);
                queue.add(request);

            }
        });
    }
}

package projectetaxi.etaxi_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverLoginActivity extends AppCompatActivity {

    private static String driToken;
    private static String driName;
    private static String driEmail;
    private static String driPassword;
    private static String driMobileNum;
    private static String driTaxiNum;
    private static String driLicNum;
    private static String driAddress;
    private static String status;

    public static String getDriToken() {
        return driToken;
    }

    public static void setDriToken(String driToken) {
        DriverLoginActivity.driToken = driToken;
    }

    public static String getDriName() {
        return driName;
    }

    public static void setDriName(String driName) {
        DriverLoginActivity.driName = driName;
    }

    public static String getDriEmail() {
        return driEmail;
    }

    public static void setDriEmail(String driEmail) {
        DriverLoginActivity.driEmail = driEmail;
    }

    public static String getDriPassword() {
        return driPassword;
    }

    public static void setDriPassword(String driPassword) {
        DriverLoginActivity.driPassword = driPassword;
    }

    public static String getDriMobileNum() {
        return driMobileNum;
    }

    public static void setDriMobileNum(String driMobileNum) {
        DriverLoginActivity.driMobileNum = driMobileNum;
    }

    public static String getDriTaxiNum() {
        return driTaxiNum;
    }

    public static void setDriTaxiNum(String driTaxiNum) {
        DriverLoginActivity.driTaxiNum = driTaxiNum;
    }

    public static String getDriLicNum() {
        return driLicNum;
    }

    public static void setDriLicNum(String driLicNum) {
        DriverLoginActivity.driLicNum = driLicNum;
    }

    public static String getDriAddress() {
        return driAddress;
    }

    public static void setDriAddress(String driAddress) {
        DriverLoginActivity.driAddress = driAddress;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        DriverLoginActivity.status = status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        final EditText etDriverEmail = (EditText) findViewById(R.id.etDriverEmail);
        final EditText etDriverPassword = (EditText) findViewById(R.id.etDriverPassword);

        final Button btDriverLogin = (Button) findViewById(R.id.btDriverLogin);
        final Button btDriverRegister = (Button) findViewById(R.id.btDriverRegister);

        btDriverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                final String email = etDriverEmail.getText().toString();

                final String password = etDriverPassword.getText().toString();

                final Response.Listener<String> responseListener = new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            driToken = jsonResponse.getString("api_token");
                            driName = jsonResponse.getString("name");
                            driEmail = jsonResponse.getString("email");
                            driTaxiNum = jsonResponse.getString("taxiNumber");
                            driLicNum = jsonResponse.getString("licenseNumber");
                            driAddress = jsonResponse.getString("address");

                            if(success) {

                                driPassword = password;
                                Intent intent = new Intent(DriverLoginActivity.this, DriverMainActivity.class);
                                DriverLoginActivity.this.startActivity(intent);

                                Toast.makeText(getApplicationContext(), "Logged In.",
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getApplicationContext(), "Login Failed, try again",
                                        Toast.LENGTH_SHORT).show();

//                                AlertDialog.Builder builder = new AlertDialog.Builder(DriverRegisterActivity.this);
//                                builder.setMessage("Register Failed")
//                                        .setNegativeButton("Try Again", null)
//                                        .create()
//                                        .show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                };

                DriverLoginRequest request = new DriverLoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(DriverLoginActivity.this);
                queue.add(request);
            }

        });

        btDriverRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent loginIntent= new Intent(DriverLoginActivity.this, DriverRegisterActivity.class);
                DriverLoginActivity.this.startActivity(loginIntent);
            }
        });
    }
}
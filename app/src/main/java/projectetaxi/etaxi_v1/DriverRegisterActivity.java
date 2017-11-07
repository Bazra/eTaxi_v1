package projectetaxi.etaxi_v1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverRegisterActivity extends AppCompatActivity {

    ProgressBar mprogressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);

        mprogressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        final EditText etDriverName = (EditText) findViewById(R.id.etDriverName);
        final EditText etDriverEmail = (EditText) findViewById(R.id.etDriverEmail);
        final EditText etDriverPassword = (EditText) findViewById(R.id.etDriverPassword);
        final EditText etDriverMobileNum = (EditText) findViewById(R.id.etDriverMobileNum);
        final EditText etTaxiNum = (EditText) findViewById(R.id.etTaxiNum);
        final EditText etLicNum = (EditText) findViewById(R.id.etLicense);
        final EditText etDriverAddress = (EditText) findViewById(R.id.etDriverAddress);

        final Button btDriverRegister = (Button) findViewById(R.id.btDriverRegister);

        btDriverRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mprogressBar.setVisibility(view.VISIBLE);

                final String name = etDriverName.getText().toString();
                final String email = etDriverEmail.getText().toString();
                final String password = etDriverPassword.getText().toString();
                final String mobileNum = etDriverMobileNum.getText().toString();
                final String taxiNum = etTaxiNum.getText().toString();
                final String licNum = etLicNum.getText().toString();
                final String address = etDriverAddress.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {

                                Intent intent = new Intent(DriverRegisterActivity.this, DriverLoginActivity.class);
                                DriverRegisterActivity.this.startActivity(intent);
                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(DriverRegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Try Again", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                DriverRegisterRequest request = new DriverRegisterRequest(name, email, password,
                        mobileNum, taxiNum, licNum, address, responseListener);
                RequestQueue queue = Volley.newRequestQueue(DriverRegisterActivity.this);
                queue.add(request);
            }
        });
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        mprogressBar.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}

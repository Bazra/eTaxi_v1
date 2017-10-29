package projectetaxi.etaxi_v1;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
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

public class PassengerRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_register);

        final EditText etPassengerName = (EditText) findViewById(R.id.etPassengerName);
        final EditText etPassengerEmail = (EditText) findViewById(R.id.etPassengerEmail);
        final EditText etPassengerPassword = (EditText) findViewById(R.id.etPassengerPassword);
        final EditText etPassengerMobileNum = (EditText) findViewById(R.id.etPassengerMobileNum);
        final EditText etPassengerAddress = (EditText) findViewById(R.id.etPassengerAddress);

        final Button btPassengerRegister = (Button) findViewById(R.id.btPassengerRegister);

        btPassengerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name = etPassengerName.getText().toString();
                final String email = etPassengerEmail.getText().toString();
                final String password = etPassengerPassword.getText().toString();
                final String mobileNum = etPassengerMobileNum.getText().toString();
                final String address = etPassengerAddress.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {

                                Intent intent = new Intent(PassengerRegisterActivity.this, PassengerLoginActivity.class);
                                PassengerRegisterActivity.this.startActivity(intent);
                            } else {

                                Toast.makeText(getApplicationContext(), "Register Failed, try again",
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

                PassengerRegisterRequest request = new PassengerRegisterRequest(name, email, password,
                        mobileNum, address, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PassengerRegisterActivity.this);
                queue.add(request);
            }
        });
    }
}

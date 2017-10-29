package projectetaxi.etaxi_v1;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class UserSelectActivity extends AppCompatActivity {

    final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);

        final ImageView ivDriver = (ImageView) findViewById(R.id.ivDriverTitle);
        final ImageView ivPassenger = (ImageView) findViewById(R.id.ivPassengerTitle);

        final Button btDriver = (Button) findViewById(R.id.btDriver);
        final Button btPassenger = (Button) findViewById(R.id.btPassenger);

        btPassenger.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view){
               Intent loginIntent = new Intent(UserSelectActivity.this, PassengerLoginActivity.class);
               UserSelectActivity.this.startActivity(loginIntent);
           }
        });

        btDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(UserSelectActivity.this, DriverLoginActivity.class);
                UserSelectActivity.this.startActivity(loginIntent);
            }
        });


        ivPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent loginIntent = new Intent(UserSelectActivity.this, PassengerLoginActivity.class);
                UserSelectActivity.this.startActivity(loginIntent);
            }
        });

        ivDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(UserSelectActivity.this, DriverLoginActivity.class);
                UserSelectActivity.this.startActivity(loginIntent);
            }
        });

    }

    boolean twice = false;
    @Override
    public void onBackPressed() {

        PassengerLoginActivity pla = new PassengerLoginActivity();

        //just for testing, nothing personal
        Log.d(TAG, "token " + pla.getPassenToken());

        if (twice == true) {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }

        twice = true;

        Log.d(TAG, "After Click Before Toast" + twice);

        Toast.makeText(UserSelectActivity.this, "Press Back Again to EXIT", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                twice = false;
                Log.d(TAG, "After Click" + twice);
            }
        }, 3000);
    }
}

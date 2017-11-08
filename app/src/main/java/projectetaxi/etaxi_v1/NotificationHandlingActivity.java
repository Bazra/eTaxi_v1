package projectetaxi.etaxi_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class NotificationHandlingActivity extends AppCompatActivity {


    final String TAG = this.getClass().getName();

    private String name, mobileNumber, latitude, longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_handling);

        Button btViewMap = (Button) findViewById(R.id.btViewOnMap);

        btViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Intent intent= new Intent(NotificationHandlingActivity.this, AfterDriverSelection.class);
                NotificationHandlingActivity.this.startActivity(intent);

                if (getIntent().getExtras() != null) {

                    Log.d(TAG, "NOOOOOOOOT NULLLLLLL");

                    name = getIntent().getExtras().getString("name");
                    mobileNumber = getIntent().getExtras().getString("mobileNumber");
                    latitude = getIntent().getExtras().getString("latitude");
                    longitude = getIntent().getExtras().getString("longitude");

                    //Log.d(TAG, ".............................. " + name + mobileNumber + latitude + longitude);
                }

                Log.d(TAG, "NULLLLLLL");

                Log.d(TAG, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ " + name + mobileNumber + latitude + longitude);

                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("mobileNumber", mobileNumber);
                bundle.putString("latitude", latitude);
                bundle.putString("longitude", longitude);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}

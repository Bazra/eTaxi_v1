package projectetaxi.etaxi_v1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DriverMainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMarkerClickListener {

    DriverLoginActivity driverLoginActivity = new DriverLoginActivity();

    private static String driverCurrentLat;
    private static String driverCurrentLng;

    public static String getDriverCurrentLat() {
        return driverCurrentLat;
    }

    public static String getDriverCurrentLng() {
        return driverCurrentLng;
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;

    final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Log.d(TAG, "DriverCurrentLatlng--------> "+driverCurrentLat+driverCurrentLng);


        final Switch statusSwitch = (Switch) findViewById(R.id.swDriStatus);

//        final TextView tvDriName = (TextView) findViewById(R.id.tvDriName);
//        final TextView tvDriEmail = (TextView) findViewById(R.id.tvDriEmail);
        final TextView tvDriStatus = (TextView) findViewById(R.id.tvDriStatus);
//        final Button btDriHistory = (Button) findViewById(R.id.btDriHistory);
//        final Button btAboutUs = (Button) findViewById(R.id.btAboutUs);
        final ImageButton ibDriLogout = (ImageButton) findViewById(R.id.ibDriLogout);
        final ImageButton ibDriSetting = (ImageButton) findViewById(R.id.ibDriSetting);

        statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {



                    Response.Listener<String> responseListener = new Response.Listener<String>() {


                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success) {

                                    Toast.makeText(getApplicationContext(), "Driver is Free.",
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
                            "" + driverCurrentLat,
                            "" + driverCurrentLng,
                            "Free",
                            responseListener
                    );

                    RequestQueue queue = Volley.newRequestQueue(DriverMainActivity.this);
                    queue.add(request);


//                    Toast.makeText(
//                            DriverMainActivity.this,
//                            "Driver is Free",
//                            Toast.LENGTH_SHORT).show();
                    tvDriStatus.setText("FREE");
                    tvDriStatus.setTextColor(getResources().getColor(R.color.green));


                } else {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {


                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success) {

                                    Toast.makeText(getApplicationContext(), "Driver is Busy Now.",
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
                            "" + driverCurrentLat,
                            "" + driverCurrentLng,
                            "Busy",
                            responseListener
                    );

                    RequestQueue queue = Volley.newRequestQueue(DriverMainActivity.this);
                    queue.add(request);
//                    Toast.makeText(
//                            DriverMainActivity.this,
//                            "Driver is Busy",
//                            Toast.LENGTH_SHORT).show();
                    tvDriStatus.setText("BUSY");
                    tvDriStatus.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });

//        tvDriEmail.setText(driverLoginActivity.getDriEmail());
//        tvDriName.setText(driverLoginActivity.getDriName());

//        btDriHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view){
//                Intent intent= new Intent(DriverMainActivity.this, ServedHistoryActivity.class);
//                DriverMainActivity.this.startActivity(intent);
//            }
//        });

//        btAboutUs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view){
//                Intent intent= new Intent(DriverMainActivity.this, AboutUs.class);
//                DriverMainActivity.this.startActivity(intent);
//            }
//        });

        ibDriLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent= new Intent(DriverMainActivity.this, UserSelectActivity.class);
                DriverMainActivity.this.startActivity(intent);
            }
        });

        ibDriSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent= new Intent(DriverMainActivity.this, DriverSettingActivity.class);
                DriverMainActivity.this.startActivity(intent);
            }
        });

        //text views haru set garna baki ani swtich ko wala kaam baaki xa
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
// Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
// Show an explanation to the user *asynchronously* -- don't block
// this thread waiting for the user's response! After the user
// sees the explanation, try again to request the permission.
//Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
// No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    boolean twice = false;
    @Override
    public void onBackPressed() {

        Log.d(TAG, "********** Click");

        if (twice == true) {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }

        twice = true;

        Log.d(TAG, "********** After Click Before Toast: " + twice);

        Toast.makeText(DriverMainActivity.this, "Press Back Again to EXIT", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                twice = false;
                Log.d(TAG, "********** After Click: " + twice);
            }
        }, 3000);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

//Showing Current Location Marker on Map
        LatLng latLng = new LatLng(
                location.getLatitude(),
                location.getLongitude()
        );

        driverCurrentLat = String.valueOf(location.getLatitude());
        driverCurrentLng = String.valueOf(location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
// TODO: Consider calling
// ActivityCompat#requestPermissions
// here to request the missing permissions, and then overriding
// public void onRequestPermissionsResult(int requestCode, String[]permissions,
// int[] grantResults)
// to handle the case where the user grants the permission. See the documentation
// for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();
            Geocoder geocoder = new Geocoder(getApplicationContext(),
                    Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
// Here we are finding , whatever we want our marker to show when clicked
                    String state = listAddresses.get(0).getAdminArea();
                    String country = listAddresses.get(0).getCountryName();
                    String subLocality = listAddresses.get(0).getSubLocality();
                    markerOptions.title("" + latLng + "," + subLocality + "," + state
                            + "," + country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

//move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
//this code stops location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public boolean onMarkerClick(final Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
// If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// permission was granted. Do the
// contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}

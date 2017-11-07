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
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static projectetaxi.etaxi_v1.PassengerMainActivity.MY_PERMISSIONS_REQUEST_LOCATION;

public class NearByDriverActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    final String TAG = this.getClass().getName();

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LatLng cLatLng;
    Marker mCurrLocationMarker, dMarker;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;


    ArrayList<NearbyDriverDetails> nearbyDriverArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_driver);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {


                    for (int i = 0; i < response.length(); i++) {

                        JSONObject driverDetailObj = response.getJSONObject(i);

                        NearbyDriverDetails nearbyDriverDetails = new NearbyDriverDetails(
                                driverDetailObj.getString("name"),
                                driverDetailObj.getString("email"),
                                driverDetailObj.getString("mobileNumber"),
                                driverDetailObj.getString("taxiNumber"),
                                driverDetailObj.getString("latitude"),
                                driverDetailObj.getString("longitude"),
                                Double.parseDouble(driverDetailObj.getString("latitude")),
                                Double.parseDouble(driverDetailObj.getString("longitude"))
                        );

                        nearbyDriverArrayList.add(nearbyDriverDetails);


                    }
                    Log.d(TAG, "ArrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrSzzzzzzzz: "
                            + nearbyDriverArrayList.size());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < nearbyDriverArrayList.size(); i++) {

                    double lat = nearbyDriverArrayList.get(i).getdLat();
                    Log.d(TAG, "double laaaaaaaaaaaaaaaaaat"+ lat);
                    double lng = nearbyDriverArrayList.get(i).getdLng();
                    LatLng latLng = new LatLng(lat, lng);
                    dMarker = mMap.addMarker(new MarkerOptions().position(latLng).
                            icon(BitmapDescriptorFactory.fromResource(R.drawable.map_taxi_icon)));
                    Log.d(TAG, "LaaaaaaaaaaaatLooooooooooooooong: " + latLng);
                    Log.d(TAG, "DMaaaaaaaaaaaaaaaarker: " + dMarker);

                }
                Log.d(TAG, "XXXXXXXXXXXXArrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrSzzzzzzzz: "
                        + nearbyDriverArrayList.size());
                Log.d(TAG, "one sampleeeeeeeeeeeeeeeee: " + nearbyDriverArrayList.get(4));

                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View v = null;
                        cLatLng = marker.getPosition();
                        Log.d(TAG, "!!!!!!!!!!!cLatlong:" + cLatLng);

                        for (int i = 0; i < nearbyDriverArrayList.size(); i++) {
                            LatLng latLng = new LatLng(nearbyDriverArrayList.get(i).getdLat(),
                                    nearbyDriverArrayList.get(i).getdLng());
                            Log.d(TAG,"LAAAAAAAAAAAAAAAAAtLong"+latLng);
                            if (latLng.equals(cLatLng)) {
                                v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
                                TextView tvname = (TextView) v.findViewById(R.id.tv_name);
                                TextView tvemail = (TextView) v.findViewById(R.id.tv_email);
                                TextView tvmobilenum = (TextView) v.findViewById(R.id.tv_mobilenum);
                                TextView tvtaxinumber = (TextView) v.findViewById(R.id.tv_taxinumber);

                                tvname.setText("Name: " + nearbyDriverArrayList.get(i).getName());
                                tvemail.setText("Email: " + nearbyDriverArrayList.get(i).getEmail());
                                tvmobilenum.setText("Mobile Number: " + nearbyDriverArrayList.get(i).getMobileNum());
                                tvtaxinumber.setText("Taxi Number:  " + nearbyDriverArrayList.get(i).getTaxi());

                                final String driverEmail = nearbyDriverArrayList.get(i).getEmail();

                                final Button btConfirm = (Button) findViewById(R.id.btSelectDriver);
                                btConfirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    Intent intent = new Intent(
                                            NearByDriverActivity.this,
                                            DestinationSelectionActivity.class);
                                    NearByDriverActivity.this.startActivity(intent);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("driverEmail", driverEmail);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    }
                                });
                                break;
                            }
                        }
                        Log.d(TAG, "VVVVVVVVVVVVVVVVVVVVVV: " + v);
                        return v;
                    }
                });
            }
        };

        NearByDriverRequest request = new NearByDriverRequest(responseListener);
        Log.d(TAG, "Nearby Driver Request: " + request);
        RequestQueue queue = Volley.newRequestQueue(NearByDriverActivity.this);
        queue.add(request);
        Log.d(TAG, "ArrralistSizzzzzzzzzzzzze: " + nearbyDriverArrayList.size());
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
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_marker));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18.2f));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.2f));
        //this code stops location updates

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
        }

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_near_by_driver, menu);
        return true;
    }
}
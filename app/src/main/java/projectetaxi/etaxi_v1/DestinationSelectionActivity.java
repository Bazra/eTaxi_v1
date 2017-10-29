package projectetaxi.etaxi_v1;

import android.Manifest;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class DestinationSelectionActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener {

    private static String currentLat;
    private static String currentLng;
    private static String destinationLat;
    private static String destinationLng;

    private static double doubleCurrentLat;
    private static double doubleCurrentLng;
    private static double doubleDestinationLat;
    private static double doubleDestinationLng;


    public static String getCurrentLat() {
        return currentLat;
    }

    public static String getCurrentLng() {
        return currentLng;
    }

    public static String getDestinationLat() {
        return destinationLat;
    }

    public static String getDestinationLng() {
        return destinationLng;
    }


    public static double getDoubleCurrentLat() {
        return doubleCurrentLat;
    }

    public static void setDoubleCurrentLat(double doubleCurrentLat) {
        DestinationSelectionActivity.doubleCurrentLat = doubleCurrentLat;
    }

    public static double getDoubleCurrentLng() {
        return doubleCurrentLng;
    }

    public static void setDoubleCurrentLng(double doubleCurrentLng) {
        DestinationSelectionActivity.doubleCurrentLng = doubleCurrentLng;
    }

    public static double getDoubleDestinationLat() {
        return doubleDestinationLat;
    }

    public static void setDoubleDestinationLat(double doubleDestinationLat) {
        DestinationSelectionActivity.doubleDestinationLat = doubleDestinationLat;
    }

    public static double getDoubleDestinationLng() {
        return doubleDestinationLng;
    }

    public static void setDoubleDestinationLng(double doubleDestinationLng) {
        DestinationSelectionActivity.doubleDestinationLng = doubleDestinationLng;
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker, marker;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;

    final String TAG = this.getClass().getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_destination_selection);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);

                Log.d(TAG, "Set Location----> "+mMap);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    //  mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        Log.d(TAG, "Set Long Click Location----> "+mMap);

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
        Log.d(TAG, "Set On Marker Click Location----> "+mMap);
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
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);

            Log.d(TAG, "Requested Location----> "+mLocationRequest);
        }
    }



    @Override
    public void onConnectionSuspended(int i) {
    }



    @Override
    public void onLocationChanged(Location location) {

        Log.d(TAG, "Changed Location -----> " + location);

        mLastLocation = location;
        if (mCurrLocationMarker != null) {

            Log.d(TAG, "If mLastLocation: " + mLastLocation + " = " + " location: " + location);
            mCurrLocationMarker.remove();
        }


        //Showing Current Location Marker on Map
        LatLng latLng = new LatLng(
                location.getLatitude(),
                location.getLongitude()
        );


        Log.d(TAG, "Current Location----> " + latLng);
        Log.d(TAG, "Current Latitude----> " + location.getLatitude());
        Log.d(TAG, "Current Longitude----> " + location.getLongitude());

        doubleCurrentLat = location.getLatitude();
        doubleCurrentLng = location.getLongitude();

        currentLat = String.valueOf(location.getLatitude());
        currentLng = String.valueOf(location.getLongitude());


        Log.d(TAG, "Passenger Current Latitude----> " + currentLat);
        Log.d(TAG, "Passenger Current Longitude----> " + currentLng);

        Log.d(TAG, "Passenger Class Current Longitude----> " + location.getLatitude());
        Log.d(TAG, "Passenger Class Current Longitude----> " + location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        String provider = locationManager.getBestProvider(new Criteria(), true);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
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

        Log.d(TAG, "Locations----> "+ locations);


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
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
// Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
// Show an explanation to the user *asynchronously* -- don't block
// this thread waiting for the user's response! After the user
// sees the explanation, try again to request the permission.
//Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
// No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
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
                            Manifest.permission.ACCESS_FINE_LOCATION)
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
    public boolean onMarkerClick(final Marker marker) {

//        if(mCurrLocationMarker!=null){
//            mCurrLocationMarker.remove();
//        }

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);

            Log.d(TAG, "---Toast---");

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
    public void onMapLongClick(final LatLng latLng) {

        final String lat = String.valueOf(latLng.latitude);
        final String lng = String.valueOf(latLng.longitude);

        destinationLat = lat;
        destinationLng = lng;

        Log.d(TAG, "--------Dest Latitude: " + destinationLat);
        Log.d(TAG, "--------Dest Longitude: " + destinationLng);

        Log.d(TAG, "--------Long Clicked lat before if statement: " + latLng.latitude);
        Log.d(TAG, "--------Long Clicked lng before if statement: " + latLng.longitude);

        doubleDestinationLat = latLng.latitude;
        doubleDestinationLng = latLng.longitude;

        Log.d(TAG, "Long Clicked latlng: " + latLng);

        if(marker!=null){
            marker.remove();

            Log.d(TAG, "--------After if statement, Long Clicked lat: " + latLng.latitude);
            Log.d(TAG, "--------After if statement, Long Clicked latlng: " + latLng.longitude);
            Log.d(TAG, "After if stmt, Long Clicked latlng: " + latLng);
        }

        final Button btSelectDestination = (Button) findViewById(R.id.btSelectDest);

        btSelectDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                destinationLat = lat;
                destinationLng = lng;

                Intent intent= new Intent(DestinationSelectionActivity.this, BookingActivity.class);
                DestinationSelectionActivity.this.startActivity(intent);

                Log.d(TAG, "/////Src Lat: " + currentLat);
                Log.d(TAG, "/////Src Lng: " + currentLng);
                Log.d(TAG, "/////Dest Lat: " + destinationLat);
                Log.d(TAG, "/////Dest Lng: " + destinationLng);


            }
        });



        marker= mMap.addMarker(new MarkerOptions().position(latLng).title(latLng.toString()));
    }



    @Override
    public void onMapClick(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        Log.d(TAG, "/////--Src Lat: " + currentLat);
        Log.d(TAG, "/////--Src Lng: " + currentLng);
        Log.d(TAG, "/////--Dest Lat: " + destinationLat);
        Log.d(TAG, "/////--Dest Lng: " + destinationLng);
    }
}
package com.example.mybus;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    /*private LocationListener locationListener;
    private LocationManager locationManager;

    private final long MIN_TIME = 1000; //1second
    private final long MIN_DIST = 5; // meters

    private LatLng LatLng;
    private TextView textView;
    private EditText editTextLat;
    private EditText editTextLong;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);*/

       // textView = findViewById(R.id.textview);
        //editTextLat = findViewById(R.id.editTextLat);
        //editTextLong = findViewById(R.id.editTextLng);

        //LatLng = new LatLng(-34, 151);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Kathmandu = new LatLng(27.7172, 85.3240);
        mMap.addMarker(new MarkerOptions()
                .position(Kathmandu)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Kathmandu));
    }

        // Add a marker in Sydney and move the camera
        //LatLng Satdobato = new LatLng(27.6515, 85.3278);
        //mMap.addMarker(new MarkerOptions().position(Satdobato).title("Marker"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Satdobato));

       /* locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                try {
                    LatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(LatLng).title("My Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng));

                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        };*/

               /* locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);


                } catch (SecurityException e) {
                    e.printStackTrace();
                }*/

    /*public void getLocationDetails(View view){

        double latitude = LatLng.latitude;
        double longitude = LatLng.longitude;

        if (! (editTextLong.getText().toString().isEmpty() || editTextLat.getText().toString().isEmpty()))
        {
            latitude = Double.parseDouble(editTextLat.getText().toString());
            longitude = Double.parseDouble(editTextLat.getText().toString());

            LatLng = new LatLng(latitude, longitude);

        }

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        String address = null;
        String city = null;

        try {
            addresses = geocoder.getFromLocation(latitude, longitude,1);

            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMap.addMarker(new MarkerOptions().position(LatLng).title("Marker in : " + address + city));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng));


        textView.setText(address + city);

    }*/
}
package com.example.styledmap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.styledmap.Adapter.CustomInfoWindowAdapter;
import com.example.styledmap.Models.Event;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * A styled map using JSON styles from a raw resource.
 */
public class MapsActivityRaw extends AppCompatActivity
        implements OnMapReadyCallback{

    MarkerOptions markeroptions;
    ArrayList<LatLng> latlangArray;
    Olive myApplication;
    Realm realm;
    FloatingActionButton fab;

    private static final String TAG = MapsActivityRaw.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps_raw);

        // Get the SupportMapFragment and register for the callback
        // when the map is ready for use.
        CustomMap mapFragment = (CustomMap) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markeroptions = new MarkerOptions();

        latlangArray = new ArrayList<>();

        myApplication = (Olive) getApplicationContext();
        realm = myApplication.getRealmInstance();

        fab = (FloatingActionButton) findViewById(R.id.id_download);
        if(latlangArray.size()!=0) fab.setVisibility(View.VISIBLE);
        else fab.setVisibility(View.INVISIBLE);


    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready for use.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        realm = myApplication.getRealmInstance();

        //custom style for the map
        setDarkMode(googleMap);

        // Position the map's camera near Kathmandu,Nepal.
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(27.7172, 85.3240)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getLayoutInflater().inflate(R.layout.layout_custom_window,null)));


        RealmResults<Event> results = realm.where(Event.class).findAll();
        for(Event event : results) googleMap.addMarker(markeroptions.position(new LatLng(event.getPosition_lat(),event.getPosition_lang())));

        /**
         * callbacks for map interaction
         */
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("witcher", "onMapClick: ");
                googleMap.addMarker(markeroptions.position(latLng));
                latlangArray.add(latLng);
                fab.setVisibility(View.VISIBLE);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("witcher", "onClick: downloading all locations");
                for (LatLng latlang_elem : latlangArray){
                    realm.beginTransaction();
                        Event event = realm.createObject(Event.class);
                        event.setId(next_key());
                        event.setPosition_lat(latlang_elem.latitude);
                        event.setPosition_lang(latlang_elem.longitude);
                    realm.commitTransaction();
                }

                latlangArray.clear();
                fab.setVisibility(View.INVISIBLE);
            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });
    }

    private void setDarkMode(GoogleMap googleMap){
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }

    private int next_key(){
        return realm.where(Event.class).max("id").intValue()+1;
    }

}

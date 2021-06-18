package com.example.googlemaps.ui;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.googlemaps.R;
import com.example.googlemaps.data.prefs.Prefs;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemaps.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    protected List<LatLng> list = new ArrayList<>();
    protected PolylineOptions polylineOptions;

    protected Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = new Prefs(this);

        list = prefs.getLocation();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        setupListener();

    }

    private void setupListener() {
        binding.btnDrawActivityMaps.setOnClickListener(v -> {
            saveLocations();
            drawLocations();
        });
        binding.btnClearActivityMaps.setOnClickListener(v ->
                clearLocations());
    }

    private void clearLocations() {
        list.clear();
        prefs.clear();
        mMap.clear();
    }

    private void saveLocations() {
        prefs.saveLocation(list);
    }

    private void drawLocations() {
        if (list != null) {
            polylineOptions = new PolylineOptions();
            polylineOptions.color(Color.DKGRAY);
            polylineOptions.width(10);
            for (LatLng location : list) {
                polylineOptions.add(location);
            }
            mMap.addPolyline(polylineOptions);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (list != null) {
            drawLocations();
        }

        mMap.setOnMapClickListener(this::onMapClick);
        mMap.setOnMarkerClickListener(this::onMarkerClick);

    }

    public void onMapClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("what che matcheche?")
                .alpha(0.5f) // прозрачность
                .draggable(true) //перетаскивание
        );
        list.add(latLng);

    }


    public boolean onMarkerClick(Marker marker) {
        marker.remove();
        list.remove(marker.getPosition());
        return true;
    }
}
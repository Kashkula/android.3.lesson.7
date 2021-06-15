package com.example.googlemaps;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.googlemaps.databinding.ActivityMainBinding;
import com.example.googlemaps.prefs.Prefs;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    private GoogleMap mMap;
    protected boolean knopka = false;
    protected List<LatLng> list = new ArrayList<>();
    protected ActivityMainBinding binding;

    private final static int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        Prefs.init(this);
//        if (list != null && getSharedPreferences(Prefs.PREFS_KEY, Context.MODE_PRIVATE) != null) {
//            list = (Prefs.getLocation(list));
//        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        setupListener();

//        /** Работа с permissions */
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//            checkLocationPermission();
//        } else {
//            mMap.setMyLocationEnabled(true);
//        }
//
//        Log.e("TAG", "onCreate: " + list.toString());


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this::onMapClick);
        mMap.setOnMarkerClickListener(this::onMarkerClick);
//        CameraPosition cameraPosition = CameraPosition.builder()
//                .target(new LatLng(42.8666998, 74.5814659))
//                .zoom(13f)
//                .build();
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
//            @Override
//            public void onFinish() {
//                toast("finish");
//            }
//
//            @Override
//            public void onCancel() {
//                toast("cancel");
//            }
//        });
//
//        if (list != null && getSharedPreferences(Prefs.PREFS_KEY, Context.MODE_PRIVATE) != null) {
//            addPolygonOptions();
//        }

        /** animation of map */
        /** difficult types of map */
    }

    private void setupListener() {
        binding.btnDelete.setOnClickListener(v -> onClear());
        binding.btnHybryd.setOnClickListener(v -> onClick());
        binding.btnPolygon.setOnClickListener(v -> onClickPolygon());
    }

    /**
     * ломанные линии, фигуры
     */
    public void onClickPolygon() {
        addPolygonOptions();
//        Prefs.saveLocation(list);
        toast("вы сохранили");
        Log.e("TAG", "onClickPolygon: " + list.toString());
    }

    public void addPolygonOptions() {
        if (list != null) {
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.strokeWidth(15f);
            polygonOptions.strokeColor(Color.BLUE);
            for (LatLng latLng : list) {
                polygonOptions.add(latLng);
                mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker()));
            }
            mMap.addPolygon(polygonOptions);
        }
    }

    public void onMapClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("what che matcheche?")
                .alpha(0.5f) // прозрачность
                .draggable(true) //перетаскивание
        );
        list.add(latLng);
        toast("вы нажали");
    }

    public boolean onMarkerClick(Marker marker) {
        marker.remove();
        list.remove(marker.getPosition());
        return true;
    }

    public void onClear() {
//        Prefs.clear();
        list.clear();
        toast("Данные удалены");
        addPolygonOptions();
    }

    public void toast(String name) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    public void onClick() {
        if (!knopka) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            knopka = true;
        } else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            knopka = false;
        }
    }
//    private void checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSION_REQUEST_CODE);
//        }

//    }
//    @Override
//    public void onRequestPermissionsResult(
//            int requestCode,
//            @NonNull String[] permissions,
//            @NonNull int[] grantResults) {
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_REQUEST_CODE)
//            if (grantResults.length > 0 &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                if (ActivityCompat.checkSelfPermission(this,
//                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    /** мое местоположение */
//                    mMap.setMyLocationEnabled(true);
//                } else {
//                    mMap.setMyLocationEnabled(true);
//                    toast("Permission denied");

//                }

//    }


}

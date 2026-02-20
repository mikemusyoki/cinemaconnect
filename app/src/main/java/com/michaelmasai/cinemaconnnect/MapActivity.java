package com.michaelmasai.cinemaconnnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        // Adding cinema markers to the map
        addCinemas();
        enableUserLocation();


        map.setOnMarkerClickListener(marker -> {
            String cinemaName = marker.getTitle(); // example: "IMAX Cinema"

            Intent intent = new Intent(MapActivity.this, MovieActivity.class);
            intent.putExtra("cinemaName", cinemaName);
            startActivity(intent);

            // Return true to indicate I have handled the click and to prevent default behavior
            return true;
        });
    }

    private void addCinemas() {
        // ---Century Cinemax---
        LatLng imax = new LatLng( -1.2321906,36.8778405);
        map.addMarker(new MarkerOptions()
                .position(imax)
                .title("Century Cinemax")
                .snippet("Garden City, Nairobi")
                );

        // --- Anga Diamond Cinema ---
        LatLng anga = new LatLng(-1.2585, 36.8194);
        map.addMarker(new MarkerOptions()
                .position(anga)
                .title("Anga Diamond Cinema")
                .snippet("Diamond Plaza 2, Nairobi")
                );

        // --- Prestige Cinema ---
        LatLng prestige = new LatLng(-1.3005, 36.7873);
        map.addMarker(new MarkerOptions()
                .position(prestige)
                .title("Prestige Cinema")
                .snippet("Ngong Road")
                );

        // --- Century Cinemax Sarit ---
        LatLng century = new LatLng(-1.2612, 36.8020);
        map.addMarker(new MarkerOptions()
                .position(century)
                .title("Century Cinemax Sarit")
                .snippet("Sarit Centre, Nairobi")
                );

        // --- Westgate Cinema ---
        LatLng westgate = new LatLng(-1.2569, 36.8037);
        map.addMarker(new MarkerOptions()
                .position(westgate)
                .title("Westgate Cinema")
                .snippet("Westlands, Nairobi")
                );

        // --- Motion Cinema ---
        LatLng fox = new LatLng(-1.2667, 36.8000);
        map.addMarker(new MarkerOptions()
                .position(fox)
                .title("Motion Cinema")
                .snippet("Greenspan Mall, Nairobi")
                );

        // Center map on Nairobi
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(imax, 12));
    }
    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true); // blue dot appears
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1001
            );
        }
    }
    private BitmapDescriptor resizeMapIcon(int resource, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), resource);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return BitmapDescriptorFactory.fromBitmap(resizedBitmap);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                }
            }
        }
    }


}

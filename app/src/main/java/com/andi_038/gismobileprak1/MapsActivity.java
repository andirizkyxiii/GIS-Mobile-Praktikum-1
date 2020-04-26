package com.andi_038.gismobileprak1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;

import android.os.Build;
import android.os.Bundle;
import android.view.View;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LocationRequest mLocationRequest;
    private Marker mCurrLocationMarker;
    private Location mLastLocation;
    private String[] permission = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getUserPermission();
    }

    private void getUserPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(permission,0);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady (GoogleMap googleMap){
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng limboro = new LatLng(-0.724952, 119.693789);
        LatLng donggala = new LatLng(-0.669516, 119.744562);
        //Custom size marker
        int tinggi = 100;
        int lebar = 100;
        BitmapDrawable bitmapStart = (BitmapDrawable) getResources().getDrawable(R.drawable.pin1);
        BitmapDrawable bitmapDes = (BitmapDrawable) getResources().getDrawable(R.drawable.pin2);
        Bitmap s = bitmapStart.getBitmap();
        Bitmap d = bitmapDes.getBitmap();
        Bitmap markerStart = Bitmap.createScaledBitmap(s, lebar, tinggi, false);
        Bitmap markerDes = Bitmap.createScaledBitmap(d, lebar, tinggi, false);
        //Add marker to map
        mMap.addMarker(new MarkerOptions().position(limboro).title("Marker in Desa Limboro")
                .snippet("Ini adalah Desa Limboro")
                .icon(BitmapDescriptorFactory.fromBitmap(markerStart)));
        mMap.addMarker(new MarkerOptions().position(donggala).title("Marker in Kota Donggala")
                .snippet("Ini adalah Kota Donggala")
                .icon(BitmapDescriptorFactory.fromBitmap(markerDes)));

        mMap.addPolyline(new PolylineOptions().add(
                limboro,
                new LatLng(-0.725001, 119.695192),
                new LatLng(-0.722742, 119.699154),
                new LatLng(-0.720728, 119.701824),
                new LatLng(-0.717733, 119.705423),
                new LatLng(-0.716837, 119.712122),
                new LatLng(-0.714410, 119.716932),
                new LatLng(-0.711763, 119.717112),
                new LatLng(-0.710411, 119.720008),
                new LatLng(-0.709553, 119.722970),
                new LatLng(-0.708559, 119.723354),
                new LatLng(-0.708648, 119.724808),
                new LatLng(-0.706925, 119.729499),
                new LatLng(-0.693827, 119.733464),
                new LatLng(-0.690975, 119.736021),
                new LatLng(-0.689312, 119.735913),
                new LatLng(-0.687606, 119.736739),
                new LatLng(-0.685270, 119.735934),
                new LatLng(-0.684301, 119.736901),
                new LatLng(-0.677017, 119.736742),
                new LatLng(-0.675373, 119.738204),
                new LatLng(-0.672497, 119.741369),
                new LatLng(-0.671418, 119.741783),
                new LatLng(-0.670235, 119.743693),
                donggala
                ).width(10)
                        .color(Color.BLUE)
        );

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(limboro, 11.5f));

        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

    }

    private void getCurrentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient =
                new FusedLocationProviderClient(this);

        Task location = fusedLocationProviderClient.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Location currentLocation = (Location) task.getResult();
                mMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title("Ini Adalah Lokasi Saya").snippet("Andi Rizky Irgiawan F55117038"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),11.5f));
            }
        });
    }

    public void Mylocation(View view) {
        getCurrentLocation();
    }
}


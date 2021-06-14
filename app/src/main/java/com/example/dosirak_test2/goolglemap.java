package com.example.dosirak_test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class goolglemap extends AppCompatActivity implements OnMapReadyCallback {

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goolglemap);


        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


        Intent intent = getIntent();
        String storeWhere = intent.getStringExtra("storeWhere");  //처음 화면에서 가져온
        System.out.println("나와라~~"+storeWhere);

        String storeName = intent.getStringExtra("storeName");
        String storeStock = intent.getStringExtra("storeStock");

        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = null;

        try {
            addressList = geocoder.getFromLocationName(storeWhere,10);
            System.out.println("나와라~~~~~~~~~~~~~~"+addressList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("안나온다~~~~~~~~~~~~~~"+addressList);
        }

        System.out.println("여기서 확인하기"+addressList);
        Address address = addressList.get(0);
        double lat = address.getLatitude();
        double lon = address.getLongitude();



        LatLng location = new LatLng(lat, lon); //서울역
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(storeName);
        markerOptions.snippet("재고 수량 : "+ storeStock);
        markerOptions.position(location);
        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
    }
}
//---------------------------------------------------------------------------------------------
// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example.drip_platform.View.Activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drip_platform.DB.Mongodb;
import com.example.drip_platform.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Handler handler = new Handler();
    private com.google.android.gms.maps.GoogleMap mMap;
    private Mongodb mongodb1 = new Mongodb(this);
    private float final_la = 0;
    private float final_lo = 0;
    private float last_la = 0;
    private float last_lo = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        run_app();
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override

    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
        mMap = googleMap;
        get_GPS();
        // Add a marker in Sydney and move the camera
        Marker();
    }

    public void run_app(){

        Runnable runnable = new Runnable() {
            @Override

            public void run() {
                handler.postDelayed(this, 1000);
                get_GPS();
                if((last_lo != final_lo)||(last_la != final_la)){
                    Marker();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public void Marker(){
        LatLng sydney = new LatLng(final_la, final_lo);
        float zoom = 17;
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,zoom));
    }


    public void get_GPS(){
        String [] gps = mongodb1.gps();
        System.out.println("gps=" + gps);

        last_la = final_la;
        last_lo = final_lo;

        try {
            String latitude = gps[4];
            String longitude = gps[9];

            //小數點的位置
            int px = latitude.indexOf('.');
            int py = longitude.indexOf('.');

            String[] a1 = latitude.split("");
            String[] a2 = longitude.split("");

            //前位數
            String b1 = a1[0]+a1[1];
            float bf1 =  Float.parseFloat(b1) ;
            String b2 = a2[0]+a2[1]+a2[2];
            float bf2 =  Float.parseFloat(b2) ;

            //latitude處理------------------------------------
            String c = "";
            for(int i=px-2;i<latitude.length();i++){
                c = c + a1[i];
            }
            float d =  Float.parseFloat(c) ;
            float n = d / 60;
            final_la = bf1+n;
            //longitude處理-----------------------------------
            c = "";
            for(int i=py-2;i<longitude.length();i++){
                c = c + a2[i];
            }
            d =  Float.parseFloat(c) ;
            n = d / 60;
            final_lo = bf2+n;

        }catch (Exception e) {
            System.out.println("GPS_catch_error");
        }

    }
}


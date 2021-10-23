package com.example.drip_platform.View.Fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.drip_platform.DB.Mongodb;
import com.example.drip_platform.ExtendComponent.Electrocardiogram;
import com.example.drip_platform.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PreviewFragment extends Fragment  implements OnMapReadyCallback {
    private TextView Numericalvalue,Time,Patient_ID,Critical_text,Status;
    private ImageView image;
    private Button change_button;

    private Handler handler = new Handler();
    private ActionBar actionBar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout Drawer;

    private Toolbar toolbar;
    private Electrocardiogram elec;
    private BottomNavigationView bottomNavigationView;


    String[] spinner = new String[] {"剩餘劑量","脈搏","地圖"};
    private View google_map_view,heart;



    private Timer timer;
    private TimerTask timerTask;
    String num = "0:0";

    int message = 0;
    int Critical_value = -1;


    private com.google.android.gms.maps.GoogleMap mMap;

    private float final_la = 0;
    private float final_lo = 0;
    private float last_la = 0;
    private float last_lo = 0;

    private Mongodb mongodb1 = new Mongodb();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);
        try {
            mapFragment.getMapAsync(this);
        }catch (Exception e) {
            System.out.println("error");
        }
        run_app();

        Numericalvalue = (TextView)view.findViewById(R.id.Numericalvalue);
        Time = (TextView)view.findViewById(R.id.Time);
        image = (ImageView)view.findViewById(R.id.image);
        Patient_ID=(TextView)view.findViewById(R.id.patient_ID);

        Status = (TextView)view.findViewById(R.id.Status);




        google_map_view = (View) view.findViewById(R.id.google_map_view);
        heart = (View)view.findViewById(R.id.electrocardiogram);

        Critical_text = (TextView)view.findViewById(R.id.Critical);
        change_button = (Button)view.findViewById(R.id.change_button);
        change_button.setOnClickListener(change_button_listen);





        Patient_ID.setText("UUID:test-0000-0001");
        image.setImageResource(R.drawable.drip);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);

        start(elec);
        //  initActionBar();

        Numericalvalue.getText();


        Runnable runnable = new Runnable() {
            @Override

            public void run() {
                handler.postDelayed(this, 1000);
                Number_value();
                //getData(U,queue);
            }
        };
        handler.postDelayed(runnable, 1000);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void start(View view) {
        Electrocardiogram elec = getActivity().findViewById(R.id.electrocardiogram);
        showWaveData(elec);

    }

    private Spinner.OnItemSelectedListener spnPreferListener =
            new  Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                    String sel = parent.getSelectedItem().toString();
                    if (sel == spinner[2]){
                        google_map_view.setVisibility(View.VISIBLE);
                        try{
                            run_app();
                        }catch (Exception e){
                            System.out.println("run_app_error");
                        }

                        heart.setVisibility(View.GONE);
                    }else{
                        google_map_view.setVisibility(View.GONE);
                        heart.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent){

                }
            };

    public void stop(View view) {
        stop();
    }
/*
    private void initActionBar(){

        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(new DrawerArrowDrawable(getApplicationContext()));

        Drawer =(DrawerLayout)findViewById(R.id.Drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer,R.string.drawer_open,R.string.drawer_close);
        mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.w));

        mDrawerToggle.syncState();

        Drawer.addDrawerListener(mDrawerToggle);

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
        private String getData(String urlString,RequestQueue queue){
            String resule="";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(urlString, null,
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response){
                            Log.d("回傳結果","結果=" + response.toString());
                            try{
                                parseJSON(response);
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    },new Response.ErrorListener(){
                public void onErrorResponse(VolleyError errror){

                    Log.e("回傳結果","結果=" + errror.toString());
                }
            });

            queue.add(jsonObjectRequest);

            return resule;
        }*/
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
        try {
            mMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoom));
        }catch (Exception e) {
            System.out.println("mapIsnull");
        }
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
    public void Number_value(){
        String [] data = mongodb1.show();

        String T = "Please wait ..";
        String N = "數值：0";

        try {
            T = data[4];
            N = "數值 " + data[8];
            num = N;

            AlertDialog.Builder adbATM = new AlertDialog.Builder(getContext());
            adbATM.setTitle("警告");
            adbATM.setIcon(R.mipmap.ic_launcher);
            adbATM.setMessage("重量低於規定值");
            adbATM.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            adbATM.setCancelable(false);

            String[] tokens = num.split(":");

            if (Integer.parseInt(tokens[1]) < Critical_value){
                if (message == 0){
                    adbATM.show();
                    message=1;
                    Status.setTextColor(Color.RED);
                    Status.setText("狀態: 低下");
                }
            }
        }catch (Exception e) {
            System.out.println("errorS");
        }
        Time.setText(T);
        Numericalvalue.setText(N);
    }

    private Button.OnClickListener change_button_listen =
            new Button.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    View v = getLayoutInflater().inflate(R.layout.set_custom_dialog_layout_with_button, null);
                    alertDialog.setView(v);
                    Button btOK = v.findViewById(R.id.button_ok);
                    Button btC = v.findViewById(R.id.buttonCancel);
                    EditText editText = v.findViewById(R.id.Critical_value2);
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                    btOK.setOnClickListener((v1 -> {


                        if(!editText.getText().toString().matches("")) {
                            if(Integer.valueOf(editText.getText().toString())<=99999) {
                                Critical_value = Integer.parseInt(String.valueOf(editText.getText()));
                                Critical_text.setText(" 警戒值:" + Critical_value);
                                message = 0;


                                AlertDialog.Builder twoDialog = new AlertDialog.Builder(getContext());
                                twoDialog.setTitle("設定完成");
                                twoDialog.setPositiveButton("確定", ((dialog1, which) -> {
                                }));
                                twoDialog.show();
                            }
                            else{
                                AlertDialog.Builder twoDialog = new AlertDialog.Builder(getContext());
                                twoDialog.setTitle("數值不得超過99999");
                                twoDialog.setPositiveButton("確定", ((dialog1, which) -> {
                                }));
                                twoDialog.show();
                            }
                            dialog.dismiss();


                        }
                        else{
                            AlertDialog.Builder twoDialog = new AlertDialog.Builder(getContext());
                            twoDialog.setTitle("未輸入數值請重新輸入");
                            twoDialog.setPositiveButton("確定", ((dialog1, which) -> {
                            }));


                            twoDialog.show();
                            dialog.dismiss();
                        }

                    }));

                    btC.setOnClickListener((v1 -> {
                        dialog.dismiss();
                    }));
                }
            };
    public void showWaveData(final Electrocardiogram elec){
        timer = new Timer();
        timerTask = new TimerTask() {

            @Override
            public void run() {
                float random = new Random().nextFloat()*(30f)-20f;
                String[] tokens = num.split(":");
                //elec.showLine(new Random().nextFloat()*(30f)-20f);
//                elec.showLine(Float.parseFloat(tokens[1]));
            }
        };


        //500表示调用schedule方法后等待500ms后调用run方法，50表示以后调用run方法的时间间隔
        timer.schedule(timerTask,500,50);
    }

    /**
     * 停止繪製波型
     */
    public void stop(){
        if(timer != null){
            timer.cancel();
            timer.purge();
            timer = null;
        }
        if(null != timerTask) {
            timerTask.cancel();
            timerTask = null;
        }
    }

}

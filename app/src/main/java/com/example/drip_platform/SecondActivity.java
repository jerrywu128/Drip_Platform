package com.example.drip_platform;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SecondActivity extends AppCompatActivity {
    private TextView Numericalvalue,Time,Patient_ID,Critical_text;
    private ImageView image;
    private Button change_button;

    private Handler handler = new Handler();
    private ActionBar actionBar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout Drawer;
    private NavigationView na;
    private Toolbar toolbar;
    private electrocardiogram elec;

    private Timer timer;
    private TimerTask timerTask;
    String num = "0:0";

    int message = 0;
    int Critical_value = -1;

    private mongodb mongodb1 = new mongodb();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Numericalvalue = (TextView)findViewById(R.id.Numericalvalue);
        Time = (TextView)findViewById(R.id.Time);
        image = (ImageView)findViewById(R.id.image);
        Patient_ID=(TextView)findViewById(R.id.patient_ID);
        na = (NavigationView)findViewById(R.id.NaList);

        Critical_text = (TextView)findViewById(R.id.Critical);
        change_button = (Button)findViewById(R.id.change_button);
        change_button.setOnClickListener(change_button_listen);


        Patient_ID.setText("UUID:test-0000-0001");
        image.setImageResource(R.drawable.drip);
        toolbar=(Toolbar)findViewById(R.id.toolbar);

        start(elec);
        initActionBar();

        Numericalvalue.getText();

        na.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.action_home) {
                    final Intent intent =new Intent(SecondActivity.this,MainActivity.class);
                    startActivity(intent);

                }
                return true;
            }

        });

        Runnable runnable = new Runnable() {
            @Override

            public void run() {
                handler.postDelayed(this, 1000);
                Number_value();
                //getData(U,queue);
            }
        };
        handler.postDelayed(runnable, 1000);

    }

    public void start(View view) {
        electrocardiogram elec = findViewById(R.id.electrocardiogram);
        showWaveData(elec);

    }

    public void stop(View view) {
        stop();
    }

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

    }

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

    public void Number_value(){
        String [] data = mongodb1.show();

        String T = "Please wait ..";
        String N = "數值：0";

        try {
            T = data[4];
            N = "數值 " + data[8];
            num = N;

            AlertDialog.Builder adbATM = new AlertDialog.Builder(SecondActivity.this);
            adbATM.setTitle("警告");
            adbATM.setIcon(R.mipmap.ic_launcher);
            adbATM.setMessage("重量低於規定值");
            adbATM.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   // message = 0;
                }
            });
            adbATM.setCancelable(false);

            String[] tokens = num.split(":");

            if (Integer.parseInt(tokens[1]) < Critical_value){
                if (message == 0){
                    adbATM.show();
                    message=1;
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

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SecondActivity.this);
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


                                AlertDialog.Builder twoDialog = new AlertDialog.Builder(SecondActivity.this);
                                twoDialog.setTitle("設定完成");
                                twoDialog.setPositiveButton("確定", ((dialog1, which) -> {
                                }));
                                twoDialog.show();
                            }
                            else{
                                AlertDialog.Builder twoDialog = new AlertDialog.Builder(SecondActivity.this);
                                twoDialog.setTitle("數值不得超過99999");
                                twoDialog.setPositiveButton("確定", ((dialog1, which) -> {
                                }));
                                twoDialog.show();
                            }
                            dialog.dismiss();


                        }
                        else{
                            AlertDialog.Builder twoDialog = new AlertDialog.Builder(SecondActivity.this);
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
    public void showWaveData(final electrocardiogram elec){
        timer = new Timer();
        timerTask = new TimerTask() {

            @Override
            public void run() {
                float random = new Random().nextFloat()*(30f)-20f;
                String[] tokens = num.split(":");
                //elec.showLine(new Random().nextFloat()*(30f)-20f);
                elec.showLine(Float.parseFloat(tokens[1]));
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

package com.example.drip_platform;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SecondActivity extends AppCompatActivity {
    private TextView Numericalvalue,Time,Patient_ID;
    private ImageView image;
    private static String url="https://spreadsheets.google.com/feeds/list/16tNGUftG-4GqH7POpbudnY49RxU14LC89mtgyN1kwXs/od6/public/values?alt=json";
    private Handler h = new Handler();
    private ActionBar actionBar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout Drawer;
    private NavigationView na;
    private Toolbar toolbar;
    private electrocardiogram elec;
    //private draw_elec_test drawelecline;

    private Timer timer;
    private TimerTask timerTask;
    String num = "0: 0";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final RequestQueue queue = Volley.newRequestQueue(this);

        Numericalvalue = (TextView)findViewById(R.id.Numericalvalue);
        Time = (TextView)findViewById(R.id.Time);
        image = (ImageView)findViewById(R.id.image);
        Patient_ID=(TextView)findViewById(R.id.patient_ID);
        na = (NavigationView)findViewById(R.id.NaList);

        Patient_ID.setText("UUID:test-0000-0001");
        image.setImageResource(R.drawable.drip);
        toolbar=(Toolbar)findViewById(R.id.toolbar);

        //drawelecline = new draw_elec_test();
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
                h.postDelayed(this, 1000);
                getData(url,queue);

            }
        };
        h.postDelayed(runnable, 1000);

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
    }

    public void parseJSON(JSONObject jsonObject) throws JSONException{


        JSONObject feed = jsonObject.getJSONObject("feed");

        JSONArray entry = feed.getJSONArray("entry");
        JSONObject zero = entry.getJSONObject(0);

        JSONObject title = zero.getJSONObject("title");
        String T = title.getString("$t");

        JSONObject content = zero.getJSONObject("content");
        String N = content.getString("$t");
        Time.setText(T);
        Numericalvalue.setText(N);
        num = N;
    }

    public void showWaveData(final electrocardiogram elec){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                float random = new Random().nextFloat()*(30f)-20f;
                String[] tokens = num.split(": ");
                //elec.showLine(new Random().nextFloat()*(30f)-20f);
                elec.showLine(Float.parseFloat(tokens[1]));
            }
        };
        //500表示调用schedule方法后等待500ms后调用run方法，50表示以后调用run方法的时间间隔
        timer.schedule(timerTask,500,50);
    }

    /**
     * 停止绘制波形
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

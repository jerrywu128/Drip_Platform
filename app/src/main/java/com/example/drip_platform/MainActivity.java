package com.example.drip_platform;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.drip_platform.R;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView Numericalvalue,Time,Patient_ID;
    private ImageView image;
    private static String url="https://spreadsheets.google.com/feeds/list/16tNGUftG-4GqH7POpbudnY49RxU14LC89mtgyN1kwXs/od6/public/values?alt=json";
    private Handler h = new Handler();
    private ActionBar actionBar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout Drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RequestQueue queue = Volley.newRequestQueue(this);

        Numericalvalue = (TextView)findViewById(R.id.Numericalvalue);
        Time = (TextView)findViewById(R.id.Time);
        image = (ImageView)findViewById(R.id.image);
        Patient_ID=(TextView)findViewById(R.id.patient_ID);

        Patient_ID.setText("UUID:test-0000-0001");
        image.setImageResource(R.drawable.drip);



        initActionBar();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                h.postDelayed(this, 1000);
                getData(url,queue);

            }
        };
        h.postDelayed(runnable, 1000);

    }

    private void initActionBar(){
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(new DrawerArrowDrawable(getApplicationContext()));

        Drawer =(DrawerLayout)findViewById(R.id.Drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer,R.string.drawer_open,R.string.drawer_close);

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

    private void parseJSON(JSONObject jsonObject) throws JSONException{


        JSONObject feed = jsonObject.getJSONObject("feed");

        JSONArray entry = feed.getJSONArray("entry");
        JSONObject zero = entry.getJSONObject(0);

        JSONObject title = zero.getJSONObject("title");
        String T = title.getString("$t");

        JSONObject content = zero.getJSONObject("content");
        String N = content.getString("$t");
        Time.setText(T);
        Numericalvalue.setText(N);
    }
}



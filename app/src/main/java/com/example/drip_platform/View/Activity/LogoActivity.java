package com.example.drip_platform.View.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drip_platform.R;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                if(!checkLoginStatus()) {
                    Intent mainIntent = new Intent(LogoActivity.this, LoginActivity.class);
                    LogoActivity.this.startActivity(mainIntent);
                    LogoActivity.this.finish();
                }else{
                    Intent mainIntent = new Intent(LogoActivity.this, LaunchActivity.class);
                    LogoActivity.this.startActivity(mainIntent);
                    LogoActivity.this.finish();
                }
            }
        }, 2000);
    }


    private boolean checkLoginStatus(){
        Boolean isLogin = false;
        SharedPreferences pref = this.getSharedPreferences("myActivityName", 0);
        isLogin = pref.getBoolean("isLogin", false);
        return isLogin;
    }


}

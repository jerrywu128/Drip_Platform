package com.example.drip_platform.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drip_platform.R;

public class LoginActivity extends AppCompatActivity {

    private Button bt;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bt =(Button)findViewById(R.id.log_in);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginActivity.this, LaunchActivity.class);
                startActivity(intent);}
        });



/*
        actionBar = getSupportActionBar();
        actionBar.hide();
  */  }
}



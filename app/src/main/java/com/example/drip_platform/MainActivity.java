package com.example.drip_platform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.internal.Experimental;
public class MainActivity extends AppCompatActivity {

    private Button bt;
    private ImageView img;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt =(Button)findViewById(R.id.log_in);
        bt.setText("Log In");

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);}
        });

        img = (ImageView)findViewById(R.id.image2);
        img.setImageResource(R.drawable.computing);
/*
        actionBar = getSupportActionBar();
        actionBar.hide();
  */  }
}



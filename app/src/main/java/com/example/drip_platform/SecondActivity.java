package com.example.drip_platform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.internal.Experimental;

public class SecondActivity extends AppCompatActivity {

    private Button bt;
    private ImageView img;
    //private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        bt =(Button)findViewById(R.id.log_in);
        bt.setText("Log In");

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SecondActivity.this,MainActivity.class);
                startActivity(intent);}
        });

        img = (ImageView)findViewById(R.id.image2);
        img.setImageResource(R.drawable.computing);

    }

}

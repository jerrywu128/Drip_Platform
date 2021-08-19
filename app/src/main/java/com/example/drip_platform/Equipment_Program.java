package com.example.drip_platform;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Equipment_Program extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.getMenu().setGroupCheckable(0, false, false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {

                case R.id.action_home:
                    Intent intent = new Intent(Equipment_Program.this, SecondActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.action_help:

                    break;
                case R.id.action_about:
                    Intent intent2 = new Intent(Equipment_Program.this, Persinal_Page.class);
                    startActivity(intent2);
                    finish();
                    break;

            }
            return true;
        });
    }

}

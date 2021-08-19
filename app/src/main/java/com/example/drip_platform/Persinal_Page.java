package com.example.drip_platform;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Persinal_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.getMenu().setGroupCheckable(0, false, false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {

                case R.id.action_home:
                    Intent intent = new Intent(Persinal_Page.this, SecondActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.action_help:
                    Intent intent2 = new Intent(Persinal_Page.this, Equipment_Program.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.action_about:

                    break;

            }
            return true;
        });
    }
}

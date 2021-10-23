package com.example.drip_platform.View.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drip_platform.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EquipmentInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.getMenu().setGroupCheckable(0, false, false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {

                case R.id.action_home:
                    Intent intent = new Intent(EquipmentInfoActivity.this, PreviewActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.action_device:

                    break;
                case R.id.action_personal:
                    Intent intent2 = new Intent(EquipmentInfoActivity.this, PersonalActivity.class);
                    startActivity(intent2);
                    finish();
                    break;

            }
            return true;
        });
    }

}

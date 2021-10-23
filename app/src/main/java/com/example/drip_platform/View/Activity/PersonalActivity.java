package com.example.drip_platform.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drip_platform.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    private Button SignOut_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        SignOut_button = (Button) findViewById(R.id.signout_button);
        SignOut_button.setOnClickListener(this);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.getMenu().setGroupCheckable(0, false, false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {

                case R.id.action_home:
                    Intent intent = new Intent(PersonalActivity.this, PreviewActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.action_device:
                    Intent intent2 = new Intent(PersonalActivity.this, EquipmentInfoActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.action_personal:

                    break;

            }
            return true;
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.signout_button:
                Intent mainIntent = new Intent(PersonalActivity.this, LoginActivity.class);
                PersonalActivity.this.startActivity(mainIntent);
                PersonalActivity.this.finish();
                break;
            default:
                break;
        }



    }
}

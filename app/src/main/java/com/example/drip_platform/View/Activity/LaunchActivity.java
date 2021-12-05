package com.example.drip_platform.View.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.drip_platform.R;
import com.example.drip_platform.View.Fragment.EquipmentInfoFragment;
import com.example.drip_platform.View.Fragment.PersonalFragment;
import com.example.drip_platform.View.Fragment.PreviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class LaunchActivity extends FragmentActivity {

    private FragmentManager fragmentManager;
    private PreviewFragment previewFragment;
    private EquipmentInfoFragment equipmentInfoFragment;
    private PersonalFragment personalFragment;
    private BottomNavigationView bottomNavigationView;
    private final List<Fragment> mFragmentList = new ArrayList<>();

    private TextView toolbar_text;
    private int lastShowFragment = 0;
    String[] spinner = new String[] {"剩餘劑量","脈搏","地圖"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);



        toolbar_text = (TextView) findViewById(R.id.toolbar_text) ;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {

                case R.id.action_home:
                    if (lastShowFragment != 0) {
                        switchFrament(lastShowFragment, 0);
                        lastShowFragment = 0;
                        toolbar_text.setText("Patient");
                    }
                    break;
                case R.id.action_device:
                    if (lastShowFragment != 1) {
                        switchFrament(lastShowFragment, 1);
                        lastShowFragment = 1;
                        toolbar_text.setText("Device");
                    }
                    break;
                case R.id.action_personal:
                    if (lastShowFragment != 2) {
                        switchFrament(lastShowFragment, 2);
                        lastShowFragment = 2;
                        toolbar_text.setText("Personal");
                    }
                    break;

            }
            return true;
        });


        if (previewFragment == null) {
            previewFragment = new PreviewFragment();
        }

        if (personalFragment == null) {
            personalFragment = new PersonalFragment();
        }

        if (equipmentInfoFragment == null) {
            equipmentInfoFragment = new EquipmentInfoFragment();
        }


        getSupportFragmentManager().beginTransaction()
                .add(R.id.content1,previewFragment)
                .show(previewFragment)
                .commit();
        lastShowFragment = 0;
        mFragmentList.add(previewFragment);

        mFragmentList.add(equipmentInfoFragment);

        mFragmentList.add(personalFragment);

    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void switchFrament(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mFragmentList.get(lastIndex));
        if (!mFragmentList.get(index).isAdded()) {
            transaction.add(R.id.content1, mFragmentList.get(index));
        }
        transaction.show(mFragmentList.get(index)).commitAllowingStateLoss();
    }

    private Spinner.OnItemSelectedListener spnPreferListener =
            new  Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                    String sel = parent.getSelectedItem().toString();
                    if (sel == spinner[2]){
                       // google_map_view.setVisibility(View.VISIBLE);
                        try{
                        //    run_app();
                        }catch (Exception e){
                            System.out.println("run_app_error");
                        }

                       // heart.setVisibility(View.GONE);
                    }else{
                      //  google_map_view.setVisibility(View.GONE);
                      //  heart.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent){

                }
    };
}

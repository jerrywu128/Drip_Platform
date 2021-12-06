package com.example.drip_platform.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drip_platform.ExtendComponent.CreateUser;
import com.example.drip_platform.R;

import java.net.MalformedURLException;

public class LoginActivity extends AppCompatActivity {

    private EditText ET_account,ET_password;
    private Button BT_login,BT_signUp;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ET_account = (EditText) findViewById(R.id.account_number);
        ET_password = (EditText) findViewById(R.id.Password);
        BT_signUp = (Button) findViewById(R.id.sign_up);
        BT_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 sign_up();
            }
        });

        BT_login =(Button)findViewById(R.id.log_in);
        BT_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ET_account.getText().toString().equals("123") && ET_password.getText().toString().equals("456")) {
                    Intent intent = new Intent(LoginActivity.this, LaunchActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, R.string.input_error, Toast.LENGTH_LONG).show();
                }
            }
        });



/*
        actionBar = getSupportActionBar();
        actionBar.hide();
  */  }

    public void sign_up(){
        LayoutInflater inflater = LayoutInflater.from(this);
        final View v = inflater.inflate(R.layout.sign_up_dialog, null);

        v.setBackgroundColor(getResources().getColor(R.color.blue));
        new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle(R.string.Sign)
                .setView(v)
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog2, int which2) {
                        EditText input_account = (EditText) (v.findViewById(R.id.input_account));
                        EditText input_password = (EditText) (v.findViewById(R.id.input_password));
                        EditText check_password = (EditText) (v.findViewById(R.id.check_password));
                        String account,i_password,c_password;
                        account = input_account.getText().toString();
                        i_password = input_password.getText().toString();
                        c_password = check_password.getText().toString();
                        if(account.equals("")||i_password.equals("")||c_password.equals("")){
                            Toast.makeText(LoginActivity.this,R.string.input_error,Toast.LENGTH_LONG).show();
                        }
                        else{
                              if(i_password.equals(c_password)){
                                  try {
                                      if(CreateUser.create(account,i_password)){
                                          Toast.makeText(LoginActivity.this,R.string.create_cucess,Toast.LENGTH_LONG).show();
                                      }else{
                                          Toast.makeText(LoginActivity.this,R.string.create_fail,Toast.LENGTH_LONG).show();
                                      }
                                  } catch (MalformedURLException e) {
                                      e.printStackTrace();
                                  }
                              }else{
                                  Toast.makeText(LoginActivity.this,R.string.password_different,Toast.LENGTH_LONG).show();
                              }
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //cancel
                    }
                })
                .show();
    }

}



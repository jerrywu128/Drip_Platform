package com.example.drip_platform.ExtendComponent;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.drip_platform.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class LoginUser extends AsyncTask<String,Void,String> {
    static int LoginStatus = 0;
    private static Activity activity;
    public static OkHttpClient client = new OkHttpClient();

    public LoginUser(Activity activity){
        this.activity = activity;
    }

    public static boolean login(String account, String password, Context L) throws MalformedURLException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection;
                    OutputStream outputStream;
                    //URL url = new URL("http://203.64.128.65:81/login");
                    URL url = new URL("https://"+L.getResources().getString(R.string.login_server)+"login");
                    connection = (HttpURLConnection) url.openConnection();

                    connection.setUseCaches(false);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);


                    final String BOUNDARY = "******************";
                    final String twoHyphens = "--";
                    final String crlf = "\r\n";

                    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

                    outputStream = connection.getOutputStream();

                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

                    writer.append(twoHyphens + BOUNDARY).append(crlf);

                    writer.append("Content-Disposition: form-data; name=\"password\"").append(crlf);
                    writer.append("Content-Type: text/plain; charset=UTF-8").append(crlf);
                    writer.append(crlf);
                    writer.append(password).append(crlf);
                    writer.append(twoHyphens + BOUNDARY).append(crlf);
                    writer.append("Content-Disposition: form-data; name=\"username\"").append(crlf);
                    writer.append("Content-Type: text/plain; charset=UTF-8").append(crlf);
                    writer.append(crlf);
                    writer.append(account).append(crlf);
                    List<String> response = new ArrayList<String>();
                    writer.append(twoHyphens + BOUNDARY + twoHyphens).append(crlf);
                    writer.flush();
                    writer.close();

                    int status = connection.getResponseCode();
                    if (status == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                connection.getInputStream()));
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            response.add(line);
                        }

                        System.out.println("response = "+response.toString().indexOf("sucess"));
                        LoginStatus = response.toString().indexOf("sucess");
                        LoginUser.writeLoginStatus(LoginStatus);

                        reader.close();
                        connection.disconnect();
                    } else {
                        System.out.println("ooooo");
                    }
                } catch (Exception e) {
                    System.out.println("URL_ERROR");
                }
            }});
        thread.start();

        try{
            // delay 1 second
            Thread.sleep(100);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        if(LoginStatus <=0){
            System.out.println("this"+LoginStatus);
            return false;
        }else {
            System.out.println("thise"+LoginStatus);
            return true;
        }


    }

    public static void writeLoginStatus(int status){
        if(status>=0){
            SharedPreferences pref = activity.getSharedPreferences("myActivityName", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isLogin", true);
            editor.commit();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}
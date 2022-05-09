package com.example.drip_platform.ExtendComponent;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.Toast;

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

public class CreateUser extends AsyncTask<String,Void,String> {
    public static OkHttpClient client = new OkHttpClient();
    public static void create(String account, String password, Context L) throws MalformedURLException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection;
                    OutputStream outputStream;
                    //URL url = new URL("http://203.64.128.65:81/create_user");
                    URL url = new URL("https://8504-59-120-123-50.jp.ngrok.io/create_user");
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
                        reader.close();
                        connection.disconnect();
                    } else {
                        System.out.println("ooooo");
                    }
                        Looper.prepare();
                        Toast.makeText(L, R.string.create_cucess,Toast.LENGTH_LONG).show();
                        Looper.loop();

                } catch (Exception e) {
                    System.out.println("URL_ERROR");
                    Looper.prepare();
                    Toast.makeText(L,R.string.create_fail,Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }});
        thread.start();

    }


    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}
package com.example.drip_platform.ExtendComponent;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class CreateUser extends AsyncTask<String,Void,String> {

    public static OkHttpClient client = new OkHttpClient();
    public static boolean create(String account,String password) throws MalformedURLException {

        final boolean[] r = {false};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection;
                    OutputStream outputStream;
                    URL url = new URL("http://203.64.128.65:81/create_user");
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
                    System.out.println("ooaooo");
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

                } catch (Exception e) {
                    System.out.println("URL_ERROR");
                    r[0] =false;
                }

                r[0]=true;
            }});
        thread.start();
        return r[0];
    }
    public  static Map<String, RequestBody> generateRequestBody(Map<String,String> requestDataMap){
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for(String key : requestDataMap.keySet() ){
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key)==null?"":requestDataMap.get(key));
            requestBodyMap.put(key,requestBody);
        }
        return requestBodyMap;
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}

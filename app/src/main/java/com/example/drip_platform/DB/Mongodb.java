package com.example.drip_platform.DB;


import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Mongodb {
    String Original_data;
    String GPS;
    String Pulse;

    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();

    public String [] show () {
        GetURLData();
        String a = "\" "+ Original_data + "\"";   //""date": "13:04:28","value": 6 "
        a = a.replace(" ","");
        String z = a.replace("}","");
        String b = z.replace("\"",",");
        String x = b.replace("{","");
        String [] c = x.split(",");

        try {
            System.out.println(Original_data);
            //System.out.println(c[4]);
            //System.out.println(c[8]);
        }catch (Exception e) {
            System.out.println("error");
        }
        return c;
    }

    public String [] gps () {
        Get_GPS_URLData();
        String a = "\" "+ GPS + "\"";   //""date": "13:04:28","value": 6 "
        a = a.replace(" ","");
        String z = a.replace("}","");
        String b = z.replace("\"",",");
        String x = b.replace("{","");
        String [] c = x.split(",");

        try {
            //System.out.println(GPS);
            //System.out.println(Arrays.toString(c));
            //System.out.println(c[4]);
            //System.out.println(c[9]);
        }catch (Exception e) {
            System.out.println("error");
        }
        return c;
    }

    public String [] pulse () {
        Get_Pulse_URLData();
        String a = "\" "+ Pulse + "\"";   //""date": "13:04:28","value": 6 "
        a = a.replace(" ","");
        String z = a.replace("}","");
        String b = z.replace("\"",",");
        String x = b.replace("{","");
        String [] c = x.split(",");

        try {
            System.out.println("Pulse="+Pulse);
            //System.out.println(Arrays.toString(c));
            //System.out.println(c[4]);
            //System.out.println(c[9]);
        }catch (Exception e) {
            System.out.println("error");
        }
        return c;
    }

    public void GetURLData(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Original_data = "";
                String decode;
                try {
                    URL u = new URL("http://203.64.128.65/data_list");

                    HttpURLConnection hc = (HttpURLConnection) u.openConnection();
                   try{
                       //hc.setRequestMethod("GET");
                       hc.setDoInput(true);
                       //hc.setDoOutput(true);
                       hc.connect();

                       BufferedReader in = new BufferedReader(new InputStreamReader(hc.getInputStream()));


                       while ((decode = in.readLine()) != null) {
                           Original_data += decode;
                       }
                       in.close();
                   }finally {
                       hc.disconnect();
                   }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
            }
        });
        thread.start();
    }

    public void Get_GPS_URLData(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GPS = "";
                String decode;
                try {
                    URL u = new URL("http://203.64.128.65/location_list");

                    HttpURLConnection hc = (HttpURLConnection) u.openConnection();
                    try{
                        //hc.setRequestMethod("GET");
                        hc.setDoInput(true);
                        //hc.setDoOutput(true);
                        hc.connect();

                        BufferedReader in = new BufferedReader(new InputStreamReader(hc.getInputStream()));

                        while ((decode = in.readLine()) != null) {
                            GPS += decode;
                        }
                        in.close();
                    }finally {
                        hc.disconnect();
                    }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
            }
        });
        thread.start();
    }

    public void Get_Pulse_URLData(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Pulse = "";
                String decode;
                try {
                    URL u = new URL("http://203.64.128.65/pulse_list");

                    HttpURLConnection hc = (HttpURLConnection) u.openConnection();
                    try{
                        //hc.setRequestMethod("GET");
                        hc.setDoInput(true);
                        //hc.setDoOutput(true);
                        hc.connect();

                        BufferedReader in = new BufferedReader(new InputStreamReader(hc.getInputStream()));


                        while ((decode = in.readLine()) != null) {
                            Pulse += decode;
                        }
                        in.close();
                    }finally {
                        hc.disconnect();
                    }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
            }
        });
        thread.start();
    }

    public void Get_URL_text(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                JSONArray decode;
                try {
                    URL u = new URL("http://203.64.128.65/location_list");
                    HttpURLConnection hc = (HttpURLConnection) u.openConnection();
                    InputStream is = hc.getInputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(is));
                    String line = in.readLine();
                    StringBuffer json = new StringBuffer();
                    while (line != null) {
                        json.append(line);
                        line = in.readLine();
                    }

                } catch (Exception e){
                    Log.e("Error", e.toString());
                }
            }
        });
        thread.start();
    }

}

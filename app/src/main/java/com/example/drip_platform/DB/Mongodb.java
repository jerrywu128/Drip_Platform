package com.example.drip_platform.DB;


import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Mongodb {
    String Original_data;
    String GPS;

    public String [] show () {
        GetURLData();
        String a = "\" "+ Original_data + "\"";   //""date": "13:04:28","value": 6 "
        a = a.replace(" ","");
        String z = a.replace("}","");
        String b = z.replace("\"",",");
        String x = b.replace("{","");
        String [] c = x.split(",");
        /*
        try {
            System.out.println(Original_data);
            //System.out.println(c[4]);
            //System.out.println(c[8]);
        }catch (Exception e) {
            System.out.println("error");
        }*/
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
            System.out.println(c[4]);
            System.out.println(c[9]);
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

}

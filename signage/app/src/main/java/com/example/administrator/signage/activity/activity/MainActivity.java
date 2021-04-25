package com.example.administrator.signage.activity.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.signage.R;
import com.example.administrator.signage.activity.ulti.tienich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    WebView myWebView;
    String address;
    private Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(0x10);
        setContentView(R.layout.activity_main);
        anhxa();
        String checkdiachimac = tienich.checkdmac + getmacadress();
        //doc du lieu theo dia chi mac tu server
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonArrayRequest = new StringRequest(checkdiachimac, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
        //end check mac
        khoitao();
        //1 mi call api
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getdatafromserver();   //Your code here
            }
        }, 0, 30*1000);//5 Minutes
        //end callback api

    }
    private void khoitao(){
        String type = getPreferenceValue(tienich.type_s);
        if(type.compareTo("1")==0){
            myWebView.clearFormData();
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.getSettings().setUserAgentString("Desktop");
            myWebView.setWebViewClient(new WebViewClient());
            myWebView.loadUrl(getPreferenceValue(tienich.url_s));
        }
        if(type.compareTo("2")==0){
            Intent intent = new Intent(MainActivity.this,videoActivity.class);
            startActivity(intent);
        }
    }
    private void getdatafromserver(){
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(tienich.duongdanlayurl+ getmacadress(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int ID =0;
                    String ngaytao ="";
                    String type = "";
                    String url ="";
                    String cn = getPreferenceValue(tienich.capnhat_s);
                    for (int i =0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            type= jsonObject.getString("type");
                            ngaytao = jsonObject.getString("ngaytao");
                            url = jsonObject.getString("url");
                            if(ngaytao.compareTo(cn)==1){
                                writeToPreference(tienich.capnhat_s,ngaytao);
                                writeToPreference(tienich.url_s,url);
                                writeToPreference(tienich.type_s,type);
                                if(type.compareTo("2")==0){
                                    Intent intent = new Intent(MainActivity.this,videoActivity.class);
                                    startActivity(intent);
                                }
                                myWebView.clearFormData();
                                myWebView.getSettings().setJavaScriptEnabled(true);
                                myWebView.getSettings().setUserAgentString("Desktop");
                                myWebView.setWebViewClient(new WebViewClient());
                                myWebView.loadUrl(url);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue1.add(jsonArrayRequest1);
    }
    private String getmacadress() {
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        address = info.getMacAddress();
        return address;
    }

    private void anhxa() {
        myWebView = (WebView) findViewById(R.id.fullwebview);
        //writeToPreference("date");
    }
    public String getPreferenceValue(String key)
    {
        SharedPreferences sp = getSharedPreferences("mainsetting",0);
        String str = sp.getString(key,"1");
        return str;
    }

    public void writeToPreference(String key,String thePreference)
    {
        SharedPreferences.Editor editor = getSharedPreferences("mainsetting",0).edit();
        editor.putString(key, thePreference);
        editor.commit();
    }
}


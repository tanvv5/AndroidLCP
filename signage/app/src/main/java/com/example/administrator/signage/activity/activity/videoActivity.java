package com.example.administrator.signage.activity.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.signage.R;
import com.example.administrator.signage.activity.ulti.tienich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static java.io.File.separator;

public class videoActivity extends AppCompatActivity {
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        VideoView videoView =(VideoView)findViewById(R.id.videoView1);
        getdatafromserver();
        //Creating MediaController
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);

        //specify the location of media file
        String rootDir = Environment.getExternalStorageDirectory()
                + separator + "Video";
        File rootFile = new File(rootDir);
        rootFile.mkdir();
        Uri uri= Uri.parse(rootDir+"media1.mp4");

        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

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
                            //if(ngaytao.compareTo(cn)==1){
                                writeToPreference(tienich.capnhat_s,ngaytao);
                                downloadFile(url,"media1");
                            //}
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
    private void downloadFile(String fileURL, String fileName) {
        try {
            String rootDir = Environment.getExternalStorageDirectory()
                    + File.separator + "Video";
            File rootFile = new File(rootDir);
            rootFile.mkdir();
            URL url = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            FileOutputStream f = new FileOutputStream(new File(rootFile,
                    fileName));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (IOException e) {
            Log.d("Error....", e.toString());
        }
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
    private String getmacadress() {
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        address = info.getMacAddress();
        return address;
    }
}

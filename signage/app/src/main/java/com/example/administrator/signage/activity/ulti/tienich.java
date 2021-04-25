package com.example.administrator.signage.activity.ulti;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class tienich {
    public static String host = "http://192.168.1.103:8090";
    public static String domain = host +"/lichchieuphimt8";
    public static  String checkdmac = domain+ "/api/home.php?cmd=checkmac&maxid=";
    public static  String  duongdanlayurl =domain+ "/api/home.php?cmd=getcomentandroid&maxid=";
    public static String capnhat_s="capnhat";
    public static String url_s="url";
    public static String type_s="type";
    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    public static void showToat_short(Context context,String thongbao){
        Toast.makeText(context,thongbao,Toast.LENGTH_SHORT).show();
    }
}

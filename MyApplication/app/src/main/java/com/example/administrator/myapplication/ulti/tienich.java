package com.example.administrator.myapplication.ulti;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class tienich {
    public static String host = "http://192.168.1.100:8090";
    public static String duongdansanpham_moi=host+ "/server/sanphammoi.php";
    public static String duongdanloaisp = host+ "/server/goiloaisp.php";
    public static String duongdandienthoai = host+ "/server/getsanpham.php?page=";
    public static String duongdanchitietsanpham = host+ "/server/getchitietsanpham.php";
    public static String duongdancapnhatsanpham = host+ "/server/capnhatsanpham.php?capnhat=1";
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

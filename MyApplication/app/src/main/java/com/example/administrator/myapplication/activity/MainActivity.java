package com.example.administrator.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.LoaispAdapter;
import com.example.administrator.myapplication.adapter.SanphamAdapter;
import com.example.administrator.myapplication.model.loaisanpham;
import com.example.administrator.myapplication.model.sanpham;
import com.example.administrator.myapplication.ulti.tienich;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView_manchinh;
    ListView listView_manchinh;
    NavigationView navigationView_manchinh;
    DrawerLayout drawerLayout;
    ArrayList<loaisanpham> mangloaisp;
    ArrayList<sanpham> mangsanpham;
    LoaispAdapter loaispAdapter;
    SanphamAdapter sanphamAdapter;

    int Id =0;
    String tenloaisanpham = "";
    String anhloaisanpham = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.anhxa();
        if(tienich.haveNetworkConnection(getApplicationContext())){
            ActionBachinh();
            ActionViewClipper();
            Getdulieuloaisp();
            Getdulieusanphammoinhat();
            CatchonitemMenuListView();
        }else{
            tienich.showToat_short(getApplicationContext(),"Bạn cần kiểm tra lai kết nối internet!");
            finish();
        }
    }
//su kien trong me

    private void CatchonitemMenuListView() {
        listView_manchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(tienich.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            tienich.showToat_short(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(tienich.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienthoaiActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else{
                            tienich.showToat_short(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(tienich.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else{
                            tienich.showToat_short(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(tienich.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienheActivity.class);
                            startActivity(intent);
                        }else{
                            tienich.showToat_short(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(tienich.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongtinActivity.class);
                            startActivity(intent);
                        }else{
                            tienich.showToat_short(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void Getdulieusanphammoinhat() {
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(tienich.duongdansanpham_moi, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int ID =0;
                    String Tensanpham ="";
                    Integer Giasanpham = 0;
                    String Hinhanhsanpham ="";
                    String Motasanpham ="";
                    int IdSp =0;
                    for (int i =0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID= jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensanpham");;
                            Giasanpham = jsonObject.getInt("giasanpham");
                            Hinhanhsanpham = jsonObject.getString("hinhanhsanpham");;
                            Motasanpham=jsonObject.getString("motasanpham");
                            IdSp = jsonObject.getInt("Idsanpham");
                            mangsanpham.add(new sanpham(ID,Tensanpham,Giasanpham,Motasanpham,IdSp,Hinhanhsanpham));
                            sanphamAdapter.notifyDataSetChanged();
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

    //menu trang chu
    private void Getdulieuloaisp() {
        //dọc dữ liệu bằng request
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(tienich.duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Id = jsonObject.getInt("id");
                            tenloaisanpham = jsonObject.getString("tenloaisp");
                            anhloaisanpham = jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new loaisanpham(Id,tenloaisanpham,anhloaisanpham));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(3,new loaisanpham(0,"Liên hệ","http://icons.iconarchive.com/icons/wwalczyszyn/android-style-honeycomb/64/Phone-icon.png"));
                    mangloaisp.add(4,new loaisanpham(0,"Thông tin","http://icons.iconarchive.com/icons/kyo-tux/delikate/64/Info-icon.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tienich.showToat_short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
        //kết thúc đọc dữ liệu bẳng request
    }
//slide anh trang chu
    private void ActionViewClipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://www.chuphinhsanpham.vn/wp-content/uploads/2016/07/chup-hinh-mon-an.jpg");
        mangquangcao.add("https://www.chuphinhsanpham.vn/wp-content/uploads/2017/11/chup-hinh-bingsu-kem-cafe-4.jpg");
        mangquangcao.add("http://kinhdoanhkieumoi.com/wp-content/uploads/2017/04/kinh-doanh-%C4%91%E1%BB%93-%C4%83n-online-quang-cao-mon-an-1-1024x637.jpg");
        for(int i =0;i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            //tải ảnh từ đường dẫn cho vào imageview dùng picasso
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setAnimation(animation_slide_in);
        viewFlipper.setAnimation(animation_slide_out);
    }
// nut bam menu sổ ra và khi kích ra ngoài thì về vị trí cũ
    private void ActionBachinh() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
//khoi tao gia tri
    private void anhxa(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_chinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.Viewflip_chinh);
        recyclerView_manchinh = (RecyclerView) findViewById(R.id.RecyManChinhSanpham);
        listView_manchinh = (ListView) findViewById(R.id.ListViewMenu);
        navigationView_manchinh = (NavigationView) findViewById(R.id.NaViChinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new loaisanpham(0,"Trang chủ","http://icons.iconarchive.com/icons/graphicloads/100-flat/48/home-icon.png"));
        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listView_manchinh.setAdapter(loaispAdapter);
        //san pham moi
        mangsanpham = new ArrayList<sanpham>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(),mangsanpham);
        recyclerView_manchinh.setHasFixedSize(true);
        recyclerView_manchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView_manchinh.setAdapter(sanphamAdapter);
    }
}

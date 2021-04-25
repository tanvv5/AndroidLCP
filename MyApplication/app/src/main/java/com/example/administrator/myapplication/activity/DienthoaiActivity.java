package com.example.administrator.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.DienthoaiAdapter;
import com.example.administrator.myapplication.model.sanpham;
import com.example.administrator.myapplication.ulti.tienich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienthoaiActivity extends AppCompatActivity {
DienthoaiAdapter dienthoaiAdapter;
ListView listViewdienthoai;
Toolbar toolbardienthoai;
ArrayList<sanpham> mangdienthoai;
View footerview;
int iddt=0;
int page = 1;
boolean isLoading = false;
mHandler mHandler;
boolean limitdata= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dienthoai);
        anhxa();
        if(tienich.haveNetworkConnection(getApplicationContext())){
            Getidloaisanpham();
            actionToolbar();
            Getdienthoai(page);
            Loadmoredata();
        }else{
            tienich.showToat_short(getApplicationContext(),"Bạn kiểm tra lại kết nối");
            finish();
        }

    }

    private void Loadmoredata() {
        listViewdienthoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChitietsanphamActivity.class);
                intent.putExtra("thonginsanpham",mangdienthoai.get(i));
                startActivity(intent);
            }
        });
        listViewdienthoai.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if(FirstItem + VisibleItem==TotalItem && TotalItem!=0 && isLoading==false && limitdata==false){
                    isLoading=true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                    //Getdienthoai(++page);
                }
            }
        });
    }

    private void Getdienthoai(int Page) {
        //kieu đọc dùng cho lấy giá trị có tham số truyền không có định phương thức POST
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = tienich.duongdandienthoai+String.valueOf(Page);
        StringRequest stringrequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int Id = 0;
                String Tendt = "";
                int Giasp = 0;
                String Mota = "";
                int Idmucdt = 0;
                String Hinhanhdt ="";
                if(response!=null && response.length()!=2){
                    listViewdienthoai.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Id = jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensanpham");
                            Giasp = jsonObject.getInt("giasanpham");
                            Mota = jsonObject.getString("motasanpham");
                            Idmucdt = jsonObject.getInt("Idsanpham");
                            Hinhanhdt = jsonObject.getString("hinhanhsanpham");
                            mangdienthoai.add(new sanpham(Id,Tendt,Giasp,Mota,Idmucdt,Hinhanhdt));
                            dienthoaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata=true;
                    listViewdienthoai.removeFooterView(footerview);
                    tienich.showToat_short(getApplicationContext(),"Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            //set bien cho phuong thuc post url
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("idloaisanpham",String.valueOf(iddt));
                return params;
            }
        };
        //đưa giá trị lên server để server trả lại response
        requestQueue.add(stringrequest);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbardienthoai);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       toolbardienthoai.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });
    }


    private void Getidloaisanpham() {
        iddt= getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("Giá trị sản phẩm",iddt+"");
    }

    private void anhxa() {
        toolbardienthoai = (Toolbar) findViewById(R.id.toolbardienthoai);
        listViewdienthoai = (ListView) findViewById(R.id.Listviewdienthoai) ;
        //gan view progessbar vao trong adtive nay
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null,false);
        listViewdienthoai.addFooterView(footerview);
        mHandler = new mHandler();
        mangdienthoai = new ArrayList<>();
        dienthoaiAdapter = new DienthoaiAdapter(getApplicationContext(),mangdienthoai);
        listViewdienthoai.setAdapter(dienthoaiAdapter);
    }
    //xu ly luong du lieu song song
    public class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listViewdienthoai.addFooterView(footerview);
                    break;
                case 1:
                    Getdienthoai(++page);
                    isLoading=false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    //khai bao luong du lieu
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}

package com.example.administrator.myapplication.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.sanpham;
import com.example.administrator.myapplication.ulti.tienich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LienheActivity extends AppCompatActivity {
    Toolbar toolbaredit;
    TextInputEditText inputEditText_tendienthoai,inputEditText_mota,inputEditText_Iddienthoai,inputEditText_Giadienthoai;
    Button bttim,btcapnhat;
    int iddienthoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lienhe);
        anhxa();
        if(tienich.haveNetworkConnection(getApplicationContext())){
            actionToolbar();
            actionButon();
        }else{
            tienich.showToat_short(getApplicationContext(),"Bạn kiểm tra lại kết nối");
            finish();
        }
    }

    private void actionToolbar() {
        setSupportActionBar(toolbaredit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbaredit.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void actionButon() {
        bttim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iddienthoai = Integer.parseInt(inputEditText_Iddienthoai.getText().toString());
                laythongtintheo_id();
            }
        });
        btcapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iddienthoai = Integer.parseInt(inputEditText_Iddienthoai.getText().toString());
                capnhapthongtintheo_id();
            }
        });
    }
    private void capnhapthongtintheo_id() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tienich.duongdancapnhatsanpham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.length()!=2){
                        if(Integer.parseInt(response)==1) tienich.showToat_short(getApplicationContext(),"Cập nhật thành công");
                        else tienich.showToat_short(getApplicationContext(),"Cập nhật không thành công");

                }else{
                    tienich.showToat_short(getApplicationContext(),"Không tìm thấy sản phẩm có mã này");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> paramap = new HashMap<String,String>();
                paramap.put("id",String.valueOf(iddienthoai));
                paramap.put("tensp",inputEditText_tendienthoai.getText().toString());
                paramap.put("motasp",inputEditText_mota.getText().toString());
                paramap.put("giasp",inputEditText_Giadienthoai.getText().toString());
                return paramap;
            }
        };
        //đưa giá trị lên server để server trả lại response
        requestQueue.add(stringRequest);
    }
    private void laythongtintheo_id() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tienich.duongdanchitietsanpham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int Id = 0;
                String Tendt = "";
                int Giasp = 0;
                String Mota = "";
                if(response.length()!=2){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Id = jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensanpham");
                            Giasp = jsonObject.getInt("giasanpham");
                            Mota = jsonObject.getString("motasanpham");
                            inputEditText_Giadienthoai.setText(String.valueOf(Giasp));
                            inputEditText_mota.setText(Mota);
                            inputEditText_tendienthoai.setText(Tendt);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    tienich.showToat_short(getApplicationContext(),"Không tìm thấy sản phẩm có mã này");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> paramap = new HashMap<String,String>();
                paramap.put("id",String.valueOf(iddienthoai));
                return paramap;
            }
        };
        //đưa giá trị lên server để server trả lại response
        requestQueue.add(stringRequest);
    }

    private void anhxa() {
        toolbaredit = (Toolbar) findViewById(R.id.toolbareditdienthoai);
        inputEditText_mota = (TextInputEditText) findViewById(R.id.TexteditMotadienthoai);
        inputEditText_tendienthoai = (TextInputEditText) findViewById(R.id.TexteditTendienthoai);
        inputEditText_Iddienthoai =(TextInputEditText) findViewById(R.id.TexteditIddienthoai);
        inputEditText_Giadienthoai =(TextInputEditText) findViewById(R.id.TexteditGiadienthoai);
        btcapnhat = (Button) findViewById(R.id.btCapnhatdienthoai);
        bttim = (Button) findViewById(R.id.btTimtdienthoai);
    }
}

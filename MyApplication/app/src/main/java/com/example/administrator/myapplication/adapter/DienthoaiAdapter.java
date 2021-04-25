package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DienthoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<sanpham> sanphamArrayList;

    public DienthoaiAdapter(Context context, ArrayList<sanpham> sanphamArrayList) {
        this.context = context;
        this.sanphamArrayList = sanphamArrayList;
    }

    @Override
    public int getCount() {
        return sanphamArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return sanphamArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
       public TextView txttendienthoai, txtgiadienthoai, txtmotadienthoai;
       public ImageView imageViewdienthoai;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder viewHolder = null;
       if(view == null){
        viewHolder = new ViewHolder();
        //gan view vao ViewHolder
           LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view = inflater.inflate(R.layout.dong_dienthoai,null);
           viewHolder.txttendienthoai= (TextView) view.findViewById(R.id.textviewtendienthoai);
           viewHolder.txtgiadienthoai= (TextView) view.findViewById(R.id.textviewgiadienthoai);
           viewHolder.txtmotadienthoai= (TextView) view.findViewById(R.id.textviewmotadienthoai);
           viewHolder.imageViewdienthoai= (ImageView) view.findViewById(R.id.imageviewdsDienthoai);
           view.setTag(viewHolder);
       }else{
           viewHolder = (ViewHolder) view.getTag();
       }
       //set gia tri cho view holder
       sanpham sp = (sanpham) getItem(i);
        viewHolder.txttendienthoai.setText(sp.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadienthoai.setText("Giá: " + decimalFormat.format(sp.getGiasanpham())+ " Đ");
        viewHolder.txtmotadienthoai.setMaxLines(2);
        viewHolder.txtmotadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotadienthoai.setText(sp.getMotasanpham());
        Picasso.with(context).load(sp.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imageViewdienthoai);
        return view;
    }
}

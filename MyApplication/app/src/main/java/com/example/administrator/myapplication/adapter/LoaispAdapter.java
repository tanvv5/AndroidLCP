package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.loaisanpham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<loaisanpham> loaisanphamArrayList;
    Context context;

    public LoaispAdapter(ArrayList<loaisanpham> loaisanphamArrayList, Context context) {
        this.loaisanphamArrayList = loaisanphamArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return loaisanphamArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaisanphamArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    //lop chua thanh phan cua view
    public class ViewHolder{
        TextView txttenloaisp;
        ImageView imgloaisp;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        //tai layout vao trong view dong nay
        if(view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflat = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflat.inflate(R.layout.dong_listview_loaisp,null);

            viewHolder.txttenloaisp = (TextView) view.findViewById(R.id.TextviewLoaiSP);
            viewHolder.imgloaisp = (ImageView) view.findViewById(R.id.imageviewloaisp);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        loaisanpham loaisp = (loaisanpham) getItem(i);
        viewHolder.txttenloaisp.setText(loaisp.getTensp());
        Picasso.with(context).load(loaisp.getAnhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgloaisp);
        return view;
    }
}

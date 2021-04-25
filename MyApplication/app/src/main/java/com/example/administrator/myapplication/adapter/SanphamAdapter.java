package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.ChitietsanphamActivity;
import com.example.administrator.myapplication.model.sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
    Context context;
    ArrayList<sanpham> arraysanpham;

    public SanphamAdapter(Context context, ArrayList<sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_sanphammoi,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder itemHolder, final int i) {
        sanpham sp = arraysanpham.get(i);
        itemHolder.tensanpham.setText(sp.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        itemHolder.giasanpham.setText("Giá: " + decimalFormat.format(sp.getGiasanpham())+ " Đ");
        Picasso.with(context).load(sp.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(itemHolder.anhsanpham);
        itemHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Intent intent = new Intent(context,ChitietsanphamActivity.class);
                    intent.putExtra("thonginsanpham",arraysanpham.get(i));
                    view.getContext().startActivity(intent);
                }else{
                    Intent intent = new Intent(context,ChitietsanphamActivity.class);
                    intent.putExtra("thonginsanpham",arraysanpham.get(i));
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }
    public interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick);
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        public ImageView anhsanpham;
        public TextView tensanpham,giasanpham;
        private ItemClickListener itemClickListener;
        public ItemHolder(View itemView) {
            super(itemView);
            anhsanpham =(ImageView) itemView.findViewById(R.id.imageviewsanphammoi);
            tensanpham =(TextView) itemView.findViewById(R.id.textviewtensanpham);
            giasanpham = itemView.findViewById(R.id.textviewgiasanpham);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }


        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
}

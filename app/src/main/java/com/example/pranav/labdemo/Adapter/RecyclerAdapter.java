package com.example.pranav.labdemo.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.pranav.labdemo.Contact;
import com.example.pranav.labdemo.Info;
import com.example.pranav.labdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prana on 03-05-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewholder>{

    private List<Contact> list = new ArrayList<>();


    public RecyclerAdapter(List<Contact> list)
    {
        this.list = list;

    }


    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_view,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {


        holder.name.setText(list.get(position).getBook_nme());
        holder.qunt.setText(list.get(position).getQuantity());
        String p = list.get(position).getStat();
        Log.d("Status",p);

        if(p.equals("avaliable")) {
            holder.stat.setTextColor(Color.GREEN);}
        if (p.equals("not avaliable")){
            holder.stat.setTextColor(Color.RED);
        }
        holder.stat.setText(p);
        holder.id.setText(Integer.toString(list.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewholder extends RecyclerView.ViewHolder
    {
        TextView id,name,qunt,stat;

        public MyViewholder(View itemView) {
            super(itemView);


            name = (TextView)itemView.findViewById(R.id.b_name);
            qunt = (TextView)itemView.findViewById(R.id.b_qut);
            stat = (TextView)itemView.findViewById(R.id.b_status);
            id = (TextView)itemView.findViewById(R.id.b_id);
        }
    }
}

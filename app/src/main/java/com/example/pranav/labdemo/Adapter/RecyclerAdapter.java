package com.example.pranav.labdemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pranav.labdemo.JsonKeys.Contact;
import com.example.pranav.labdemo.Desp;
import com.example.pranav.labdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prana on 03-05-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewholder>{

    public List<Contact> list = new ArrayList<>();
    Context ctx;
    String nm;

    public RecyclerAdapter(String nm,List<Contact> list, Context ctx)
    {
        this.list = list;
        this.ctx = ctx;
        this.nm = nm;

    }


    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_view,parent,false);
        return new MyViewholder(view,ctx,list,nm);
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

    public static class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id,name,qunt,stat;
        List<Contact> list=new ArrayList<Contact>();
        Context ctx;
        String nm;
        public MyViewholder(View itemView, Context ctx,List<Contact> list,String nm) {
            super(itemView);
            this.list=list;
            this.ctx=ctx;
            this.nm = nm;
            itemView.setOnClickListener(this);
            name = (TextView)itemView.findViewById(R.id.b_name);
            qunt = (TextView)itemView.findViewById(R.id.b_qut);
            stat = (TextView)itemView.findViewById(R.id.b_status);
            id = (TextView)itemView.findViewById(R.id.b_id);

        }


        @Override
        public void onClick(View v) {
            int position= getAdapterPosition();
            Contact alist=this.list.get(position);
            Log.d("seleceted Book name",alist.getBook_nme());

            Intent in=new Intent(this.ctx,Desp.class);
            in.putExtra("nam",alist.getBook_nme());
            in.putExtra("pos",position);
            in.putExtra("name",nm);
            this.ctx.startActivities(new Intent[]{in});
        }

    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}

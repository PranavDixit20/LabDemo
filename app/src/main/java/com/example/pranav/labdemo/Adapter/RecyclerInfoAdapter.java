package com.example.pranav.labdemo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pranav.labdemo.JsonKeys.Info;
import com.example.pranav.labdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prana on 06-05-2017.
 */

public class RecyclerInfoAdapter extends RecyclerView.Adapter<RecyclerInfoAdapter.MyViewholder>{

    private List<Info> list = new ArrayList<>();


    public RecyclerInfoAdapter(List<Info> list1)
    {
        this.list = list1;

    }


    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_info_view,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {


        holder.name.setText(list.get(position).getBook_name());
        holder.adate.setText(list.get(position).getAssign_date());
        holder.dudate.setText(list.get(position).getDue_date());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewholder extends RecyclerView.ViewHolder
    {
        TextView name,adate,dudate;

        public MyViewholder(View itemView) {
            super(itemView);


            name = (TextView)itemView.findViewById(R.id.b_nm);
            adate = (TextView)itemView.findViewById(R.id.b_ass);
            dudate = (TextView)itemView.findViewById(R.id.b_due);

        }
    }
}


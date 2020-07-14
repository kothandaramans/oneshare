package com.example.oneshare.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oneshare.R;
import com.example.oneshare.model.activity_model;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder> {

    Context context;
    ArrayList<activity_model> archievement;

    public ActivityAdapter(Context context, ArrayList<activity_model> archievement) {
        this.context = context;
        this.archievement = archievement;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activitylist, parent, false);
//        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.teacher_details_show,parent,false));
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(position==0)
            holder.card.setBackgroundColor(Color.parseColor("#FF8C00"));
        else if(position==1)
            holder.card.setBackgroundColor(Color.parseColor("#5F9EA0"));
        else if(position==2)
            holder.card.setBackgroundColor(Color.parseColor("#483D8B"));
        else if(position==3)
            holder.card.setBackgroundColor(Color.parseColor("#2F4F4F"));
        else if(position==4)
            holder.card.setBackgroundColor(Color.parseColor("#696969"));
        else if(position==5)
            holder.card.setBackgroundColor(Color.parseColor("#1E90FF"));
        else if(position==6)
            holder.card.setBackgroundColor(Color.parseColor("#CD5C5C"));
        else if(position==7)
            holder.card.setBackgroundColor(Color.parseColor("#20B2AA"));

        holder.atitle.setText(archievement.get(position).getSubject());
        holder.adesc.setText(archievement.get(position).getDescription());
        //     Picasso.get().load(teacher.get(position).getImgurl());
        //  holder.profilepic.setImageResource(teacher.get(position).getImgurl());
        //return ;
    }

    @Override
    public int getItemCount() {
        return archievement.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView atitle, adesc;
        LinearLayout card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            atitle = itemView.findViewById(R.id.atitle);
            adesc = itemView.findViewById(R.id.adesc);
        }
    }
}
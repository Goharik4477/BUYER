package com.example.buyer.BUYER.b.Chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.buyer.BUYER.b.Chat.model.CallList;
import com.example.buyer.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class CallListAdapter extends RecyclerView.Adapter<CallListAdapter.Holder> {


    private List<CallList> list;
    private Context context;

    public CallListAdapter(List<CallList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_call_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        CallList callList = list.get(position);

        holder.tvName.setText(callList.getUserName());
        holder.tvDate.setText(callList.getDate());

        if(callList.getCallType().equals("missed")){
            holder.arrow.setImageDrawable(context.getDrawable(R.drawable.baseline_arrow_downward_24));
            holder.arrow.getDrawable().setTint(context.getResources().getColor(R.color.red));
        } else  if(callList.getCallType().equals("income")){
            holder.arrow.setImageDrawable(context.getDrawable(R.drawable.baseline_arrow_downward_24));
            holder.arrow.getDrawable().setTint(context.getResources().getColor(R.color.green));
        } else{
            holder.arrow.setImageDrawable(context.getDrawable(R.drawable.baseline_arrow_upward_24));
            holder.arrow.getDrawable().setTint(context.getResources().getColor(R.color.green));
        }

        //img

        Glide.with(context).load(callList.getUrlProfile()).into(holder.profile);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvName,  tvDate;
        private ImageView arrow;
        private CircularImageView profile;
        public Holder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_date);
            arrow = itemView.findViewById(R.id.img_arrow);
            tvName = itemView.findViewById(R.id.tv_name);
            profile = itemView.findViewById(R.id.tv_img);


        }
    }


}

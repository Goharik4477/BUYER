package com.example.buyer.BUYER.b.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.BUYER.b.Model.Post;
import com.example.buyer.BUYER.b.adapters.SearchActivity;
import com.example.buyer.R;
import com.example.buyer.databinding.DashboardRvSampleBinding;


import java.util.ArrayList;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.viewHolder>  {

    ArrayList<Post> list;
    Context context;





    public PostAdapter(ArrayList<Post> list, Context context) {
        this.list = list;
        this.context = context;

    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_rv_sample, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Post model = list.get(position);
        holder.binding.ProductDescription.setText(model.getPostDescription());
        holder.binding.FCounty.setText("To: "+ model.getFirsCountry());
        holder.binding.ProductDescription.setText(model.getPostDescription());
        holder.binding.SCountry.setText("From: "+model.getSecondCountry());
        holder.binding.productAddress.setText("Address: " +model.getAddress());
        holder.binding.productLink.setText("Link: "+model.getLink());
        holder.binding.ProductPrice.setText("Product Price: "+model.getPrice() + " $");
        holder.binding.Category.setText(model.getCategory());
        holder.binding.textNameDash.setText(model.getUsername());
        holder.binding.ServicePrice.setText("Service Price: "+model.getPriceForService() + " $");
        holder.binding.Until.setText("Until: "+ model.getUntil());
        holder.binding.buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("postedByEmail", model.getPostedBy());// ← иногда нужно, если context — не Activity
                context.startActivity(intent);
            }
        });













    }




    @Override
    public int getItemCount() {
        return list.size();
    }



    public class viewHolder extends RecyclerView.ViewHolder{
        DashboardRvSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding =DashboardRvSampleBinding.bind(itemView);
        }
    }



}










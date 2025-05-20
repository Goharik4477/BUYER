package com.example.buyer.BUYER.b.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.BUYER.b.Model.Post;
import com.example.buyer.BUYER.b.PostDetailActivity;
import com.example.buyer.BUYER.b.adapters.SearchActivity;
import com.example.buyer.R;
import com.example.buyer.databinding.DashboardRvSampleBinding;
import com.example.buyer.databinding.PostRvSampleBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

//
//public class PostAdapter extends RecyclerView.Adapter<PostAdapter.viewHolder>  {
//
//    ArrayList<Post> list;
//    Context context;
//    FirebaseAuth auth = FirebaseAuth.getInstance();
//    String currentUserEmail = auth.getCurrentUser() != null ? auth.getCurrentUser().getEmail() : "";
//
//
//
//
//
//    public PostAdapter(ArrayList<Post> list, Context context) {
//        this.list = list;
//        this.context = context;
//
//    }
//
//
//    @NonNull
//    @Override
//    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_rv_sample, parent, false);
//        return new viewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
//
//        Post model = list.get(position);
//        holder.binding.ProductDescription.setText(model.getPostDescription());
//        holder.binding.FCounty.setText("To: "+ model.getFirsCountry());
//        holder.binding.ProductDescription.setText(model.getPostDescription());
//        holder.binding.SCountry.setText("From: "+model.getSecondCountry());
////        holder.binding.SCountry.setText(model.getSecondCountry() + "  → " + model.getFirsCountry());
//        holder.binding.productAddress.setText("Address: " +model.getAddress());
//        holder.binding.productLink.setText("Link: "+model.getLink());
//        holder.binding.ProductPrice.setText("Product Price: "+model.getPrice() + " $");
//        holder.binding.Category.setText(model.getCategory());
//        holder.binding.textNameDash.setText(model.getUsername());
//        holder.binding.ServicePrice.setText("Service Price: "+model.getPriceForService() + " $");
//        holder.binding.Until.setText("Until: "+ model.getUntil());
//        holder.binding.productWeight.setText("Weight: " + model.getWeight());
//        holder.binding.buttonChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, SearchActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("postedByEmail", model.getPostedBy());// ← иногда нужно, если context — не Activity
//                context.startActivity(intent);
//            }
//        });
//
//        if (model.getPostedBy() != null && model.getPostedBy().equals(currentUserEmail)) {
//            holder.binding.buttonDelete.setVisibility(View.VISIBLE);
//        } else {
//            holder.binding.buttonDelete.setVisibility(View.GONE);
//        }
//
//
//        holder.binding.buttonDelete.setOnClickListener(v -> {
//
//                        if (model.getPostId() != null) {
//                            FirebaseDatabase.getInstance().getReference()
//                                    .child("posts")
//                                    .child(model.getPostId())
//                                    .removeValue()
//                                    .addOnSuccessListener(aVoid -> {
//                                        Toast.makeText(context, "Пост удалён", Toast.LENGTH_SHORT).show();
//                                        list.remove(position);
//                                        notifyItemRemoved(position);
//                                    })
//                                    .addOnFailureListener(e -> {
//                                        Toast.makeText(context, "Ошибка удаления: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                    });
//                        } else {
//                            Toast.makeText(context, "postId не найден", Toast.LENGTH_SHORT).show();
//                        }
//
//        });
//
//
//    }
//
//
//
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//
//
//    public class viewHolder extends RecyclerView.ViewHolder{
//        DashboardRvSampleBinding binding;
//        public viewHolder(@NonNull View itemView) {
//            super(itemView);
//            binding =DashboardRvSampleBinding.bind(itemView);
//        }
//    }
//
//
//
//}


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.viewHolder>  {

    ArrayList<Post> list;
    Context context;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUserEmail = auth.getCurrentUser() != null ? auth.getCurrentUser().getEmail() : "";





    public PostAdapter(ArrayList<Post> list, Context context) {
        this.list = list;
        this.context = context;

    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_rv_sample, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Post model = list.get(position);
        holder.binding.ProductDescription.setText(model.getPostDescription());
        holder.binding.County.setText(model.getSecondCountry() + "  → " + model.getFirsCountry());
        holder.binding.ProductDescription.setText(model.getPostDescription());
        holder.binding.postCategory.setText(model.getCategory());
        holder.binding.textNameDash.setText(model.getUsername());
        holder.binding.ServicePrice.setText(model.getPriceForService() + "$");

        holder.binding.buttonMore.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("post", model);
            context.startActivity(intent);

        });

        if (model.getPostedBy() != null && model.getPostedBy().equals(currentUserEmail)) {
            holder.binding.buttonDelete.setVisibility(View.VISIBLE);
        } else {
            holder.binding.buttonDelete.setVisibility(View.GONE);
        }


        holder.binding.buttonDelete.setOnClickListener(v -> {

            if (model.getPostId() != null) {
                FirebaseDatabase.getInstance().getReference()
                        .child("posts")
                        .child(model.getPostId())
                        .removeValue()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(context, "Пост удалён", Toast.LENGTH_SHORT).show();
                            list.remove(position);
                            notifyItemRemoved(position);
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Ошибка удаления: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(context, "postId не найден", Toast.LENGTH_SHORT).show();
            }

        });


    }




    @Override
    public int getItemCount() {
        return list.size();
    }



    public class viewHolder extends RecyclerView.ViewHolder{
        PostRvSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding =PostRvSampleBinding.bind(itemView);
        }
    }



}










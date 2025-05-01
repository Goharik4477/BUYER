package com.example.buyer.BUYER.b.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.BUYER.b.ChatActivity;
import com.example.buyer.BUYER.b.Model.Post;
import com.example.buyer.BUYER.b.listeners.PostListener;
import com.example.buyer.BUYER.b.messenger;
import com.example.buyer.BUYER.b.utilities.Constants;
import com.example.buyer.R;
import com.example.buyer.databinding.DashboardRvSampleBinding;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.List;


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











//public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
//
//    private Context context;
//    private List<Post> list;
//    private final PostListener postListener;
//    private FirebaseFirestore firestore;
//
//    public PostAdapter(Context context, List<Post> list, PostListener postListener) {
//        this.context = context;
//        this.list = list;
//        this.postListener = postListener;
//        firestore = FirebaseFirestore.getInstance();
//    }
//
//    @NonNull
//    @Override
//    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // Инициализируем itemView
//        LayoutInflater inflater = LayoutInflater.from(context);
//        DashboardRvSampleBinding binding = DashboardRvSampleBinding.inflate(inflater, parent, false);
//        return new PostViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
//        // Получаем текущий пост
//        Post model = list.get(position);
//
//        // Отображаем данные в UI
//        holder.binding.ProductDescription.setText(model.getPostDescription());
//        holder.binding.FCounty.setText("To: " + model.getFirsCountry());
//        holder.binding.SCountry.setText("From: " + model.getSecondCountry());
//        holder.binding.productAddress.setText("Address: " + model.getAddress());
//        holder.binding.productLink.setText("Link: " + model.getLink());
//        holder.binding.ProductPrice.setText("Product Price: " + model.getPrice() + " $");
//        holder.binding.Category.setText(model.getCategory());
//        holder.binding.textNameDash.setText(model.getUsername());
//        holder.binding.ServicePrice.setText("Service Price: " + model.getPriceForService());
//        holder.binding.Until.setText("Until: " + model.getUntil());
//        holder.binding.textNameDash.setOnClickListener(view -> postListener.onPostClicked(model));
//
//        holder.binding.postMessage.setOnClickListener(v -> {
//            Toast.makeText(context, "Нажали на пост", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(context, ChatActivity.class);
//            intent.putExtra(Constants.KEY_USER, model.getUserId()  ); // просто ID
//            context.startActivity(intent);
//
//            savePostToFirestore(model);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    private void savePostToFirestore(Post post) {
//        // Сохраняем пост в Firestore
//        firestore.collection("posts")
//                .document(post.getPostId())  // уникальный ID поста
//                .set(post)
//                .addOnSuccessListener(aVoid -> {
//                    // Успешное сохранение
//                    Toast.makeText(context, "Пост успешно сохранен!", Toast.LENGTH_SHORT).show();
//                })
//                .addOnFailureListener(e -> {
//                    // Ошибка при сохранении
//                    Toast.makeText(context, "Ошибка при сохранении поста", Toast.LENGTH_SHORT).show();
//                });
//    }
//
//    public static class PostViewHolder extends RecyclerView.ViewHolder {
//
//        DashboardRvSampleBinding binding;
//
//        public PostViewHolder(DashboardRvSampleBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//    }
//}
//
//
//public class PostAdapter extends RecyclerView.Adapter<PostAdapter.viewHolder>  {
//
//    ArrayList<Post> list;
//    Context context;
//
//
//
//
//    public PostAdapter(ArrayList<Post> list, Context context) {
//        this.list = list;
//        this.context = context;
//
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
//    @Override
//    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
//        Post model = list.get(position);
//        Log.d("PostAdapter", "userId: " + model.getUserId());
//
//        holder.binding.ProductDescription.setText(model.getPostDescription());
//        holder.binding.FCounty.setText("To: " + model.getFirsCountry());
//        holder.binding.ProductDescription.setText(model.getPostDescription());
//        holder.binding.SCountry.setText("From: " + model.getSecondCountry());
//        holder.binding.productAddress.setText("Address: " + model.getAddress());
//        holder.binding.productLink.setText("Link: " + model.getLink());
//        holder.binding.ProductPrice.setText("Product Price: " + model.getPrice() + " $");
//        holder.binding.Category.setText(model.getCategory());
//        holder.binding.textNameDash.setText(model.getUsername());
//        holder.binding.ServicePrice.setText("Service Price: " + model.getPriceForService());
//        holder.binding.Until.setText("Until: " + model.getUntil());
//
//        // Обычный клик по кнопке для перехода в чат
//        holder.binding.postMessage.setOnClickListener(v -> {
//            Toast.makeText(context, "Нажали на пост", Toast.LENGTH_SHORT).show();
//            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//            firestore.collection(Constants.KEY_COLLECTION_USERS)
//                    .document(model.getUserId())
//                    .get()
//                    .addOnSuccessListener(documentSnapshot -> {
//                        if (documentSnapshot.exists()) {
//                            User user = new User();
//                            user.id = documentSnapshot.getId();
//                            user.name = documentSnapshot.getString(Constants.KEY_NAME);
//                            user.image = documentSnapshot.getString(Constants.KEY_IMAGE);
//                            user.token = documentSnapshot.getString(Constants.KEY_FCM_TOKEN);
//
//                            Intent intent = new Intent(context, ChatActivity.class);
//                            intent.putExtra(Constants.KEY_USER, user);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            context.startActivity(intent);
//                        }
//                    })
//                    .addOnFailureListener(e -> {
//                        Toast.makeText(context, "Failed to load user", Toast.LENGTH_SHORT).show();
//                    });
//        });
//
//        // Клик по всему элементу поста для перехода в чат
//        holder.itemView.setOnClickListener(v -> {
//            Log.d("PostAdapter", "Clicked post by userId: " + model.getUserId());
//
//            // Логирование перед передачей userId
//            Log.d("PostAdapter", "Navigating to ChatActivity with userId: " + model.getUserId());
//
//            // Передаем userId в ChatActivity через Intent
//            Intent intent = new Intent(context, ChatActivity.class);
//            intent.putExtra(Constants.KEY_RECEIVER_USER_ID, model.getUserId());
//            context.startActivity(intent);
//        });
//    }
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
//
//
//    }
//
//
//
//}

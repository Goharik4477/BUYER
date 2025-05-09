package com.example.buyer.BUYER.b.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.BUYER.b.listeners.UserListener;
import com.example.buyer.BUYER.b.models.User;
import com.example.buyer.databinding.ItemConteinerRecentConversionBinding;
import com.example.buyer.databinding.ItemConteinerUserBinding;


import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private  final List<User> users;
    private final UserListener userListener;


    public UserAdapter(List<User> users, UserListener userListener) {
        this.userListener = userListener;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemConteinerUserBinding itemConteinerUserBinding = ItemConteinerUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false
        );
        return new UserViewHolder(itemConteinerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends  RecyclerView.ViewHolder{
        ItemConteinerUserBinding binding;
        UserViewHolder(ItemConteinerUserBinding itemConteinerUserBinding){
            super(itemConteinerUserBinding.getRoot());
            binding = itemConteinerUserBinding;
        }
        void setUserData(User user){
            binding.textName.setText(user.name);
            binding.textEmail.setText(user.email);
            binding.imageProfile.setImageBitmap(getUserImage(user.image));
            binding.getRoot().setOnClickListener(v -> userListener.onUserClicked(user));
        }
    }
    private Bitmap getUserImage (String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}

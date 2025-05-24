package com.example.buyer.BUYER.b.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.BUYER.b.models.ChatMessage;
import com.example.buyer.databinding.ItemConteinerReceivedMessageBinding;
import com.example.buyer.databinding.ItemConteinerSentMessageBinding;

import java.util.List;


public class ChatAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<ChatMessage> chatMessages;
    private final Bitmap receiverProfileImage;
    private final String senderId;


    public static  final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public ChatAdapter(List<ChatMessage> chatMessages, Bitmap receiverProfileImage, String senderId) {
        this.chatMessages = chatMessages;
        this.receiverProfileImage = receiverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SENT){
            return new SentMessageViewHolder(ItemConteinerSentMessageBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent,false
            ));
        } else {
            return new ReceivedMessageViewHolder(ItemConteinerReceivedMessageBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_SENT){
    ((SentMessageViewHolder) holder).setData(chatMessages.get(position));
        }else {
    ((ReceivedMessageViewHolder ) holder).setData(chatMessages.get(position), receiverProfileImage);
        }


    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(chatMessages.get(position).senderId.equals(senderId)){
            return VIEW_TYPE_SENT;
        }else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemConteinerSentMessageBinding binding;

        SentMessageViewHolder(ItemConteinerSentMessageBinding itemConteinerSentMessageBinding){
            super(itemConteinerSentMessageBinding.getRoot());
            binding =itemConteinerSentMessageBinding;
        }


        void setData(ChatMessage chatMessage){
            if (chatMessage.image != null && !chatMessage.image.isEmpty()) {
                binding.imageMessage.setImageBitmap(getBitmapFromEncodedString(chatMessage.image));
                binding.imageMessage.setVisibility(View.VISIBLE);
                binding.textMessage.setVisibility(View.GONE);
            } else {
                binding.textMessage.setText(chatMessage.message);
                binding.textMessage.setVisibility(View.VISIBLE);
                binding.imageMessage.setVisibility(View.GONE);
            }
            binding.textDateTime.setText(chatMessage.dateTime);
        }
    }
    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemConteinerReceivedMessageBinding binding;

        ReceivedMessageViewHolder(ItemConteinerReceivedMessageBinding itemConteinerReceivedMessageBinding){
            super(itemConteinerReceivedMessageBinding.getRoot());
            binding =itemConteinerReceivedMessageBinding;
        }

void setData(ChatMessage chatMessage, Bitmap receiverProfileImage){
    if (chatMessage.image != null && !chatMessage.image.isEmpty()) {
        binding.imageMessage.setImageBitmap(getBitmapFromEncodedString(chatMessage.image));
        binding.imageMessage.setVisibility(View.VISIBLE);
        binding.textMessage.setVisibility(View.GONE);
    } else {
        binding.textMessage.setText(chatMessage.message);
        binding.textMessage.setVisibility(View.VISIBLE);
        binding.imageMessage.setVisibility(View.GONE);
    }
    binding.textDateTime.setText(chatMessage.dateTime);
    binding.imageProfile.setImageBitmap(receiverProfileImage);
}


    }

    private static Bitmap getBitmapFromEncodedString(String encodedImage) {

        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

    }
}

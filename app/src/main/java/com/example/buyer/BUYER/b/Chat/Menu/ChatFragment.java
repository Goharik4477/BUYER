package com.example.buyer.BUYER.b.Chat.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buyer.BUYER.b.Chat.adapter.ChatListAdapter;
import com.example.buyer.BUYER.b.Chat.model.ChatList;
import com.example.buyer.R;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {


    public ChatFragment() {
        // Required empty public constructor
    }

    


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView  recyclerView = view.findViewById(R.id.recyclerViewChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    List<ChatList> list = new ArrayList<>();

        list.add(new ChatList("12","Goharik", "aloo", "12/15/20", "https://avatars.mds.yandex.net/i?id=a51c3d3a415cc5ebbe255b4d64d4576f1e0e699f-4745534-images-thumbs&n=13" ));
        list.add(new ChatList("13","Yeva", "haa", "12/25/20", "https://i.pinimg.com/736x/3b/e8/14/3be81421efff43a591e237a19035554b.jpg" ));
        list.add(new ChatList("14","Karinka", "voch", "12/15/80", "https://i.pinimg.com/736x/b5/0e/8b/b50e8bc37aa08fb743e4bea65eef561a.jpg" ));

        recyclerView.setAdapter(new ChatListAdapter(list, getContext()));

        return view;
    }


}
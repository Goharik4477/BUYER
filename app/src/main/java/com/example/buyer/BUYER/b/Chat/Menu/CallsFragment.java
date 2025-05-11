package com.example.buyer.BUYER.b.Chat.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buyer.BUYER.b.Chat.adapter.CallListAdapter;
import com.example.buyer.BUYER.b.Chat.model.CallList;
import com.example.buyer.R;

import java.util.ArrayList;
import java.util.List;


public class CallsFragment extends Fragment {


    public CallsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_calls, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCalls);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<CallList> lists = new ArrayList<>();
        lists.add(new CallList("001", "Goharik", "income", "15/02/20, 9:25",
                "https://avatars.mds.yandex.net/i?id=a51c3d3a415cc5ebbe255b4d64d4576f1e0e699f-4745534-images-thumbs&n=13"));
        lists.add(new CallList("011", "mam", "missed", "15/03/20, 9:30",
                "https://i.pinimg.com/736x/b5/0e/8b/b50e8bc37aa08fb743e4bea65eef561a.jpg"));
        lists.add(new CallList("111", "pap", "out", "16/03/20, 9:55",
                "https://i.pinimg.com/736x/3b/e8/14/3be81421efff43a591e237a19035554b.jpg"));

        recyclerView.setAdapter(new CallListAdapter(lists, getContext() ));
        return view;
    }
}
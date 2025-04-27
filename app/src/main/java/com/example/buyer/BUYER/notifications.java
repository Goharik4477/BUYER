package com.example.buyer.BUYER;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.R;
import com.example.buyer.databinding.ActivityNotificationsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import com.example.buyer.BUYER.Adapter.NotificationAdapter;
import com.example.buyer.BUYER.Model.NotificationModel;
import com.example.buyer.BUYER.utilities.Constants;
import com.example.buyer.BUYER.utilities.PreferenceManager;

public class notifications extends AppCompatActivity {
    private ActivityNotificationsBinding binding;
RecyclerView recyclerView;
ArrayList<NotificationModel> list;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_notification);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_bottom_home) {
                startActivity(new Intent(getApplicationContext(), home_ads.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_messenger) {
                startActivity(new Intent(getApplicationContext(), messenger.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_new_ad) {
                startActivity(new Intent(getApplicationContext(), add_new_ad.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();}
            else if (item.getItemId() == R.id.menu_bottom_notification) {
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_profile) {
                startActivity(new Intent(getApplicationContext(), second_profile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        recyclerView = findViewById(R.id.NotificationsRecyclerView);
        list = new ArrayList<>();

        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        list.add(new NotificationModel(R.drawable.baseline_sentiment_satisfied_alt_24, "Alooo", "now"));
        NotificationAdapter adapter = new NotificationAdapter(list, getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        loadUserDetails();
    }

    private void loadUserDetails(){

        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);
    }
}
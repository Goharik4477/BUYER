package com.example.buyer.BUYER.b;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buyer.BUYER.b.utilities.PreferenceManager;
import com.example.buyer.R;
import com.example.buyer.databinding.ActivityFilterBinding;
import com.example.buyer.databinding.ActivityHomeAdsBinding;

public class FilterActivity extends AppCompatActivity {
ActivityFilterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        binding = ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilterActivity.this, SelectCategoryPosts.class);
                startActivity(intent);
            }
        });
    }
}
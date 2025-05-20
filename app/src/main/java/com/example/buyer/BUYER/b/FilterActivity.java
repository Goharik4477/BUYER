package com.example.buyer.BUYER.b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    private static final int REQUEST_FROM_COUNTRY = 1;
    private static final int REQUEST_TO_COUNTRY = 2;
    private static final int REQUEST_CATEGORY = 3;

    String countryFrom = null;
    String countryTo = null;
    String category = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        TextView categoryText = findViewById(R.id.Category_t);

        binding.Fcountry.setOnClickListener(v -> {
            Intent intent = new Intent(FilterActivity.this, filterFrom.class);
            startActivityForResult(intent, REQUEST_FROM_COUNTRY);
        });

        binding.sCountry.setOnClickListener(v -> {
            Intent intent = new Intent(FilterActivity.this, filterTo.class);
            startActivityForResult(intent, REQUEST_TO_COUNTRY);
        });

        binding.Category.setOnClickListener(v -> {
            Intent intent = new Intent(FilterActivity.this, SelectCategoryPosts.class);
            startActivityForResult(intent, REQUEST_CATEGORY);
        });

        binding.useFilter.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("category", category);
            resultIntent.putExtra("fromCountry", countryFrom);
            resultIntent.putExtra("toCountry", countryTo);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        binding.clearFilter.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            countryFrom = null;
            countryTo = null;
            category = null;

            binding.fCountryT.setText("From");
            binding.sCountryT.setText("To");
            categoryText.setText("Category");
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_FROM_COUNTRY:
                    countryFrom = data.getStringExtra("countryFrom");
                    break;
                case REQUEST_TO_COUNTRY:
                    countryTo = data.getStringExtra("countryTo");
                    break;
                case REQUEST_CATEGORY:
                    category = data.getStringExtra("Category");
                    break;
            }
        }
    }
}


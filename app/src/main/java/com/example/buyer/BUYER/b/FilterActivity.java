package com.example.buyer.BUYER.b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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
    EditText maxWeightInput;


    String sortBy = null;  // "new_first", "old_first", "price_low_high", "price_high_low"
    EditText minPriceInput;
    String minPrice = null;

    CheckBox oldPostRad, highPriceRad, lowPriceRad, newPostRad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        TextView categoryText = findViewById(R.id.Category_t);

        highPriceRad = findViewById(R.id.high_service_price);
        lowPriceRad = findViewById(R.id.low_service_price);
        newPostRad = findViewById(R.id.new_post_rad);
        oldPostRad = findViewById(R.id.old_post_rad);
        minPriceInput = findViewById(R.id.min_price_input);

        maxWeightInput = findViewById(R.id.max_weight_input);
        highPriceRad.setOnClickListener(v -> sortBy = "price_high_low");
        lowPriceRad.setOnClickListener(v -> sortBy = "price_low_high");
        newPostRad.setOnClickListener(v -> sortBy = "new_first");
        oldPostRad.setOnClickListener(v -> sortBy = "old_first");

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
            String minPrice = minPriceInput.getText().toString().trim();
            String maxWeight = maxWeightInput.getText().toString().trim();

            Intent resultIntent = new Intent();

            resultIntent.putExtra("maxWeight", maxWeight);

            resultIntent.putExtra("category", category);
            resultIntent.putExtra("fromCountry", countryFrom);
            resultIntent.putExtra("toCountry", countryTo);
            resultIntent.putExtra("sortBy", sortBy);
            resultIntent.putExtra("minPrice", minPrice);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        binding.clearFilter.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            countryFrom = null;
            countryTo = null;
            category = null;
            sortBy = null;

            binding.fCountryT.setText("From");
            binding.sCountryT.setText("To");
            categoryText.setText("Category");
            minPriceInput.setText("");
            maxWeightInput.setText("");

            highPriceRad.setChecked(false);
            lowPriceRad.setChecked(false);
            newPostRad.setChecked(false);
            oldPostRad.setChecked(false);

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


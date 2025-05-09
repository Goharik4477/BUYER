package com.example.buyer.BUYER.b;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buyer.R;
import com.hbb20.CountryCodePicker;

public class filterFrom extends AppCompatActivity {
    private EditText  SecondCounty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter_from);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();


        SecondCounty = findViewById(R.id.SecondCountry);
        CountryCodePicker ccp2 = findViewById(R.id.ccp2);


        ccp2.setOnCountryChangeListener(() -> {
            String selectedCountry = ccp2.getSelectedCountryName();
            SecondCounty.setText(selectedCountry);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("countryFrom", selectedCountry);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
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

public class filterTo extends AppCompatActivity {
    private EditText FirstCounty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter_to);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().hide();


        FirstCounty = findViewById(R.id.FirstCountry);
        CountryCodePicker ccp = findViewById(R.id.ccp);


        ccp.setOnCountryChangeListener(() -> {
            String selectedCountry = ccp.getSelectedCountryName();
            FirstCounty.setText(selectedCountry);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("countryTo", selectedCountry);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
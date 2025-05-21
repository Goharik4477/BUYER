package com.example.buyer.BUYER.b.ViewHolder;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import com.example.buyer.BUYER.b.Model.Post;
import com.example.buyer.BUYER.b.SignInSignUp.SignIn;
import com.example.buyer.BUYER.b.home_ads;
import com.example.buyer.BUYER.b.utilities.PreferenceManager;
import com.hbb20.CountryCodePicker;

public class AnnouncementDescription extends AppCompatActivity {
    private String FirstCountryNew,weight,  SecondCountyNew, DescriptionNew, addressNew, priceNew,PriceNewService, linkNew, id, until;
    DatePicker datePicker;

    private EditText Category, FirstCountry, SecondCounty, Description, address, price, link, priceForService, EditUntil, EditWeight;
    private Button Public, select;

    private PreferenceManager preferenceManager;
    FirebaseDatabase database;

    private Button dateButton;
    ImageView imageView;

    private FirebaseFirestore FStoreDatabase;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_announcement_description);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();

        preferenceManager = new PreferenceManager(getApplicationContext());


        datePicker = findViewById(R.id.datePicker);
        EditWeight = findViewById(R.id.weight);

        select = findViewById(R.id.select);
        EditUntil = findViewById(R.id.editUntil);
        FStoreDatabase = FirebaseFirestore.getInstance();

        Category = findViewById(R.id.Category);
        FirstCountry = findViewById(R.id.firstCounty);
        SecondCounty = findViewById(R.id.SecondCountry);
        Description = findViewById(R.id.Description);
        address = findViewById(R.id.address);
        price = findViewById(R.id.price);
        link = findViewById(R.id.link);

        Public = findViewById(R.id.buttonPublic);
        priceForService = findViewById(R.id.priceForService);
        database = FirebaseDatabase.getInstance();

        if (user == null) {
            Log.e("AuthError", "User is not authenticated.");
            Intent intent = new Intent(AnnouncementDescription.this, SignIn.class);
            startActivity(intent);
            finish();
            return;
        } else {
            String categoryName = getIntent().getExtras().get("Category").toString();
            Toast.makeText(this, "Select Category " + categoryName, Toast.LENGTH_SHORT).show();
            Category.setText("Category: " + categoryName);


        }


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth()+1;
                int year =datePicker.getYear();

                EditUntil.setText( day + "/" + month + "/" + year);
            }
        });
        CountryCodePicker ccp = findViewById(R.id.ccp);
        CountryCodePicker ccp2 = findViewById(R.id.ccp2);

        ccp.setOnCountryChangeListener(() -> {
            String selectedCountry = ccp.getSelectedCountryName();
            FirstCountry.setText(selectedCountry);
        });

        ccp2.setOnCountryChangeListener(() -> {
            String selectedCountry = ccp2.getSelectedCountryName();
            SecondCounty.setText(selectedCountry);
        });


        Public.setOnClickListener(v -> {
            ValidateProductData();

            DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child("posts");
            String postId = postsRef.push().getKey();

            Post post = new Post();
            post.setFirsCountry(FirstCountryNew);
            post.setSecondCountry(SecondCountyNew);
            post.setPostDescription(DescriptionNew);
            post.setAddress(addressNew);
            post.setPrice(priceNew);
            post.setLink(linkNew);
            post.setCategory(Category.getText().toString());
            post.setUsername(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            post.setPriceForService(PriceNewService);
            post.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            post.setUntil(until);
            post.setPostedBy(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            post.setPostedAt(new Date().getTime());
            post.setPostId(postId);
            post.setWeight(weight);
            post.setApproved(false);

            postsRef.child(postId).setValue(post)
                    .addOnSuccessListener(aVoid -> {
                        Intent intent = new Intent(this, home_ads.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "wait for post approval", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Failed to publish post", Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void ValidateProductData() {
        DescriptionNew = Description.getText().toString();
        FirstCountryNew = FirstCountry.getText().toString();
        SecondCountyNew = SecondCounty.getText().toString();
        addressNew = address.getText().toString();
        priceNew = price.getText().toString();
        linkNew = link.getText().toString();
        PriceNewService = priceForService.getText().toString();
        until = EditUntil.getText().toString();
        weight = EditWeight.getText().toString();

        if (TextUtils.isEmpty(DescriptionNew)) {
            Toast.makeText(this, "Add a description", Toast.LENGTH_SHORT).show();
            Description.setError("Add a description");
            Description.requestFocus();
            return;
        } else if (TextUtils.isEmpty(FirstCountryNew)) {
            Toast.makeText(this, "Add a country", Toast.LENGTH_SHORT).show();
            FirstCountry.setError("Add a country");
            FirstCountry.requestFocus();
            return;
        } else if (TextUtils.isEmpty(SecondCountyNew)) {
            Toast.makeText(this, "Add a country", Toast.LENGTH_SHORT).show();
            SecondCounty.setError("Add a country");
            SecondCounty.requestFocus();
            return;
        } else if (TextUtils.isEmpty(addressNew)) {
            Toast.makeText(this, "Add an address", Toast.LENGTH_SHORT).show();
            address.setError("Add an address");
            address.requestFocus();
            return;
        } else if (TextUtils.isEmpty(priceNew)) {
            Toast.makeText(this, "Add a price", Toast.LENGTH_SHORT).show();
            price.setError("Add a price");
            price.requestFocus();
            return;
        } else if (TextUtils.isEmpty(linkNew)) {
            Toast.makeText(this, "Add a link", Toast.LENGTH_SHORT).show();
            link.setError("Add a link");
            link.requestFocus();
            return;
        } else if (TextUtils.isEmpty(PriceNewService)) {
            Toast.makeText(this, "Add a price for service", Toast.LENGTH_SHORT).show();
            priceForService.setError("Add a price");
            priceForService.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(until)) {
            Toast.makeText(this, "Add a date", Toast.LENGTH_SHORT).show();
            EditUntil.setError("Add a date");
            EditUntil.requestFocus();
            return;
        }
    }

}

















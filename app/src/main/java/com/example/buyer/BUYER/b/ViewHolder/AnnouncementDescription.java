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

import com.example.buyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import com.example.buyer.BUYER.b.Model.Post;
import com.example.buyer.BUYER.b.SignInSignUp.SignIn;
import com.example.buyer.BUYER.b.home_ads;
import com.example.buyer.BUYER.b.utilities.PreferenceManager;
import com.hbb20.CountryCodePicker;

public class AnnouncementDescription extends AppCompatActivity {
    private String FirstCountryNew, SecondCountyNew, DescriptionNew, addressNew, priceNew,PriceNewService, linkNew, id, until;
    DatePicker datePicker;

    private EditText Category, FirstCountry, SecondCounty, Description, address, price, link, priceForService, EditUntil;
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
        getSupportActionBar().hide();

        preferenceManager = new PreferenceManager(getApplicationContext());


        datePicker = findViewById(R.id.datePicker);

        select = findViewById(R.id.select);
        EditUntil = findViewById(R.id.editUntil);
        FStoreDatabase = FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(getApplicationContext());
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

            post.setPostedBy(FirebaseAuth.getInstance().getUid());
            post.setPostedAt(new Date().getTime());


            database.getReference().child("posts").push().setValue(post)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(AnnouncementDescription.this, "Posted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AnnouncementDescription.this, home_ads.class));
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AnnouncementDescription.this, "Failed to post: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

















//
//public class AnnouncementDescription extends AppCompatActivity {
//    private String FirstCountryNew, SecondCountyNew, DescriptionNew, addressNew, priceNew,PriceNewService, linkNew, id, until, username;
//    DatePicker datePicker;
//    private EditText Category, FirstCountry, SecondCounty, Description, address, price, link, priceForService, EditUntil;
//    private Button Public, select;
//    private PreferenceManager preferenceManager;
//    FirebaseDatabase database;
//
//    private Button dateButton;
//    ImageView imageView;
//
//    private FirebaseFirestore FStoreDatabase;
//    private FirebaseAuth auth = FirebaseAuth.getInstance();
//    private FirebaseUser user = auth.getCurrentUser();
//
//    private FirebaseAuth authProfile;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_announcement_description);
//        getSupportActionBar().hide();
//        authProfile = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = authProfile.getCurrentUser();
//showUserProfile(firebaseUser);
//        preferenceManager = new PreferenceManager(getApplicationContext());
//        id = preferenceManager.getString(Constants.KEY_USER_ID);
//
//        datePicker = findViewById(R.id.datePicker);
//        select = findViewById(R.id.select);
//        EditUntil = findViewById(R.id.editUntil);
//        FStoreDatabase = FirebaseFirestore.getInstance();
//        preferenceManager = new PreferenceManager(getApplicationContext());
//        Category = findViewById(R.id.Category);
//        FirstCountry = findViewById(R.id.firstCounty);
//        SecondCounty = findViewById(R.id.SecondCountry);
//        Description = findViewById(R.id.Description);
//        address = findViewById(R.id.address);
//        price = findViewById(R.id.price);
//        link = findViewById(R.id.link);
//
//        Public = findViewById(R.id.buttonPublic);
//        priceForService = findViewById(R.id.priceForService);
//        database = FirebaseDatabase.getInstance();
//
//        if (user == null) {
//            Log.e("AuthError", "User is not authenticated.");
//            Intent intent = new Intent(AnnouncementDescription.this, SignIn.class);
//            startActivity(intent);
//            finish();
//            return;
//        } else {
//            String categoryName = getIntent().getExtras().get("Category").toString();
//            Toast.makeText(this, "Select Category " + categoryName, Toast.LENGTH_SHORT).show();
//            Category.setText("Category: " + categoryName);
//
//
//        }
//
//        select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int day = datePicker.getDayOfMonth();
//                int month = datePicker.getMonth()+1;
//                int year =datePicker.getYear();
//
//                EditUntil.setText( day + "/" + month + "/" + year);
//            }
//        });
//
//        Public.setOnClickListener(v -> {
//            ValidateProductData();
//
//            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//            if (currentUser == null) return;
//
//            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Registered Users").child(currentUser.getUid());
//            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    ReadWriteUsersdetails userDetails = snapshot.getValue(ReadWriteUsersdetails.class);
//                    if (userDetails == null) {
//                        Toast.makeText(AnnouncementDescription.this, "User details not found", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    String username = currentUser.getDisplayName();
//                    String userId = currentUser.getUid();
//
//
//                    Post post = new Post();
//                    post.setFirsCountry(FirstCountryNew);
//                    post.setSecondCountry(SecondCountyNew);
//                    post.setPostDescription(DescriptionNew);
//                    post.setAddress(addressNew);
//                    post.setPrice(priceNew);
//                    post.setLink(linkNew);
//                    post.setCategory(Category.getText().toString());
//                    post.setUsername(username);
//                    post.setPriceForService(PriceNewService);
//                    post.setUserId(userId); // <- теперь не null
//                    post.setUntil(until);
//                    post.setPostedBy(userId);
//                    post.setPostedAt(new Date().getTime());
//
//                    FirebaseDatabase.getInstance().getReference("posts").push().setValue(post)
//                            .addOnSuccessListener(unused -> {
//                                Toast.makeText(AnnouncementDescription.this, "Posted", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(AnnouncementDescription.this, home_ads.class));
//                                finish();
//                            })
//                            .addOnFailureListener(e -> {
//                                Toast.makeText(AnnouncementDescription.this, "Failed to post: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            });
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(AnnouncementDescription.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        });
//    }
//
//    private void ValidateProductData() {
//        DescriptionNew = Description.getText().toString();
//        FirstCountryNew = FirstCountry.getText().toString();
//        SecondCountyNew = SecondCounty.getText().toString();
//        addressNew = address.getText().toString();
//        priceNew = price.getText().toString();
//        linkNew = link.getText().toString();
//        PriceNewService = priceForService.getText().toString();
//until = EditUntil.getText().toString();
//
//        if (TextUtils.isEmpty(DescriptionNew)) {
//            Toast.makeText(this, "Add a description", Toast.LENGTH_SHORT).show();
//            Description.setError("Add a description");
//            Description.requestFocus();
//            return;
//        } else if (TextUtils.isEmpty(FirstCountryNew)) {
//            Toast.makeText(this, "Add a country", Toast.LENGTH_SHORT).show();
//            FirstCountry.setError("Add a country");
//            FirstCountry.requestFocus();
//            return;
//        } else if (TextUtils.isEmpty(SecondCountyNew)) {
//            Toast.makeText(this, "Add a country", Toast.LENGTH_SHORT).show();
//            SecondCounty.setError("Add a country");
//            SecondCounty.requestFocus();
//            return;
//        } else if (TextUtils.isEmpty(addressNew)) {
//            Toast.makeText(this, "Add an address", Toast.LENGTH_SHORT).show();
//            address.setError("Add an address");
//            address.requestFocus();
//            return;
//        } else if (TextUtils.isEmpty(priceNew)) {
//            Toast.makeText(this, "Add a price", Toast.LENGTH_SHORT).show();
//            price.setError("Add a price");
//            price.requestFocus();
//            return;
//        } else if (TextUtils.isEmpty(linkNew)) {
//            Toast.makeText(this, "Add a link", Toast.LENGTH_SHORT).show();
//            link.setError("Add a link");
//            link.requestFocus();
//            return;
//        } else if (TextUtils.isEmpty(PriceNewService)) {
//            Toast.makeText(this, "Add a price for service", Toast.LENGTH_SHORT).show();
//            priceForService.setError("Add a price");
//            priceForService.requestFocus();
//            return;
//    }
//        else if (TextUtils.isEmpty(until)) {
//            Toast.makeText(this, "Add a date", Toast.LENGTH_SHORT).show();
//            EditUntil.setError("Add a date");
//           EditUntil.requestFocus();
//            return;
//        }
//    }
//    private void showUserProfile(FirebaseUser firebaseUser) {
//        String userID = firebaseUser.getUid();
//
//
//        //database for "registered user"
//
//        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
//        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ReadWriteUsersdetails readUsersdetails = snapshot.getValue(ReadWriteUsersdetails.class);
//                if(readUsersdetails != null){
//                   username = firebaseUser.getDisplayName();
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(AnnouncementDescription.this, "Something went wrong!", Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }
//}





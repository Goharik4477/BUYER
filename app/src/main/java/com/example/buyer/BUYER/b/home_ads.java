package com.example.buyer.BUYER.b;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.BUYER.b.Adapter.PostAdapter;
import com.example.buyer.BUYER.b.Chat.ChatMain;
import com.example.buyer.BUYER.b.Model.Post;
import com.example.buyer.BUYER.b.SignInSignUp.SignIn;
import com.example.buyer.BUYER.b.ViewHolder.RulesForPublishingAnnouncements;
import com.example.buyer.BUYER.b.utilities.Constants;
import com.example.buyer.BUYER.b.utilities.PreferenceManager;
import com.example.buyer.R;
import com.example.buyer.databinding.ActivityHomeAdsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;



public class home_ads extends AppCompatActivity {
    private ActivityHomeAdsBinding binding;
    RecyclerView dashboardRV;
    ArrayList<Post> postList;
    FirebaseDatabase database;
    FirebaseAuth auth;
    private String countryFrom = null;
    private String countryTo = null;
    private PreferenceManager preferenceManager;
    private String filterCategory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeAdsBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_bottom_home) {
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_messenger) {
                startActivity(new Intent(getApplicationContext(), messenger.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_new_ad) {
                startActivity(new Intent(getApplicationContext(), add_new_ad.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            } else if (item.getItemId() == R.id.menu_bottom_profile) {
                startActivity(new Intent(getApplicationContext(), second_profile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        loadUserDetails();

        String[] categoryList = {"Shoes", "Clothes", "For children", "Home", "Beauty", "Accessories",
                "Electronics", "Toys", "Products", "Household appliances", "Pet supplies",
                "Sport", "Automotive goods", "Books", "Jewelry", "For repair", "Garden", "Health", "Stationery"};

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // Или FlexboxLayoutManager если хочешь перенос

        CategoryChipAdapter adapter = new CategoryChipAdapter(categoryList, selectedCategories -> {
            filterCategory = selectedCategories;
            loadData();
        });
        recyclerView.setAdapter(adapter);

        filterCategory = getIntent().getStringExtra("Category");
        countryFrom = getIntent().getStringExtra("countryFrom");
        countryTo = getIntent().getStringExtra("countryTo");

        Log.d("FILTERS", "Category: " + filterCategory + ", From: " + countryFrom + ", To: " + countryTo);


        binding.fabCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_ads.this, FilterActivity.class);
                startActivityForResult(intent, 100);
            }
        });






        loadData();
    }

    private void loadUserDetails() {
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);
    }

    private void loadData() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            dashboardRV = findViewById(R.id.dashboard);
            postList = new ArrayList<>();

            PostAdapter postAdapter = new PostAdapter(postList, getApplicationContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            dashboardRV.setLayoutManager(layoutManager);
            dashboardRV.setNestedScrollingEnabled(false);
            dashboardRV.setAdapter(postAdapter);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Post post = dataSnapshot.getValue(Post.class);

//                        if (post != null && post.getCategory() != null) {
//                            String cleanCategory = post.getCategory().replace("Category: ", "");
//                            String from = post.getFirsCountry();
//                            String to = post.getSecondCountry();
//
//                            boolean categoryMatches = filterCategory == null || cleanCategory.equals(filterCategory);
//                            boolean fromMatches = countryFrom == null || from.equals(countryFrom);
//                            boolean toMatches = countryTo == null || to.equals(countryTo);
//
//                            if (categoryMatches && fromMatches && toMatches) {
//                                postList.add(post);
//                            }
//                        }


                        if (post != null && post.getCategory() != null && post.isApproved()) {
                            String cleanCategory = post.getCategory().replace("Category: ", "");
                            String from = post.getFirsCountry();
                            String to = post.getSecondCountry();

                            boolean categoryMatches = filterCategory == null || cleanCategory.equals(filterCategory);
                            boolean fromMatches = countryFrom == null || from.equals(countryFrom);
                            boolean toMatches = countryTo == null || to.equals(countryTo);

                            if (categoryMatches && fromMatches && toMatches) {
                                postList.add(post);
                            }
                        }

//                        if (post.getCategory() != null) {
//                            String cleanCategory = post.getCategory().replace("Category: ", "");
//
//                            if (filterCategory == null || cleanCategory.equals(filterCategory)) {
//                                postList.add(post);
//                            }
//                        }
                    }
                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("FirebaseError", "Failed to load data: " + error.getMessage());
                    Toast.makeText(getApplicationContext(), "Error loading posts: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Intent intent = new Intent(home_ads.this, SignIn.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            filterCategory = data.getStringExtra("category");
            countryFrom = data.getStringExtra("fromCountry");
            countryTo = data.getStringExtra("toCountry");

            Log.d("FILTERS", "Category: " + filterCategory + ", From: " + countryFrom + ", To: " + countryTo);

            loadData();
        }
    }
}
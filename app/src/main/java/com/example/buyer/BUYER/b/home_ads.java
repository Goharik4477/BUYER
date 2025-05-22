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




    private String minPriceStr = null;
    private int minPrice = 0;
    private String maxWeightStr = null;
    private int maxWeight = 0;


    private PreferenceManager preferenceManager;
    private String filterCategory = null;



    private String sortBy = null;  // "new_first", "old_first", "price_low_high", "price_high_low"

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

        minPriceStr = getIntent().getStringExtra("minPrice");
        minPrice = parsePrice(minPriceStr);

        maxWeightStr = getIntent().getStringExtra("maxWeight");
        maxWeight = parsePrice(maxWeightStr);
        filterCategory = getIntent().getStringExtra("Category");
        countryFrom = getIntent().getStringExtra("countryFrom");
        countryTo = getIntent().getStringExtra("countryTo");
        sortBy = getIntent().getStringExtra("sortBy");

        Log.d("FILTERS", "Category: " + filterCategory + ", From: " + countryFrom + ", To: " + countryTo + ", Sort: " + sortBy);

        binding.fabCategory.setOnClickListener(view -> {
            Intent intent = new Intent(home_ads.this, FilterActivity.class);
            startActivityForResult(intent, 100);
        });

        loadData();
    }

    private void loadData() {
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            dashboardRV = findViewById(R.id.dashboard);
            postList = new ArrayList<>();

            PostAdapter postAdapter = new PostAdapter(postList, getApplicationContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            dashboardRV.setLayoutManager(layoutManager);
            dashboardRV.setNestedScrollingEnabled(false);
            dashboardRV.setAdapter(postAdapter);


            database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    postList.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Post post = dataSnapshot.getValue(Post.class);

                        if (post != null && post.getCategory() != null && post.isApproved()) {
                            String cleanCategory = post.getCategory().replace("Category: ", "");
                            String from = post.getFirsCountry();
                            String to = post.getSecondCountry();

                            boolean categoryMatches = filterCategory == null || filterCategory.equals(cleanCategory);
                            boolean fromMatches = countryFrom == null || countryFrom.equals(from);
                            boolean toMatches = countryTo == null || countryTo.equals(to);
                            int weight = parsePrice(post.getWeight());
                            boolean weightMatches = maxWeight == 0 || weight <= maxWeight;
                            int price = parsePrice(post.getPriceForService());

                            boolean priceMatches = price >= minPrice;
                            Log.d("FILTER", "Checking post: " + post.getPriceForService() + " >= " + minPrice);
                            if (categoryMatches && fromMatches && toMatches && priceMatches && weightMatches) {
                                postList.add(post);
                            }
                        }
                    }
                    if (sortBy != null) {
                        switch (sortBy) {
                            case "new_first":

                                postList.sort((p1, p2) -> Long.compare(p2.getPostedAt(), p1.getPostedAt()));
                                break;
                            case "old_first":
                                postList.sort((p1, p2) -> Long.compare(p1.getPostedAt(), p2.getPostedAt()));
                                break;
                            case "price_low_high":
                                postList.sort((p1, p2) -> Double.compare(parsePrice(p1.getPriceForService()),parsePrice( p2.getPriceForService())));
                                break;
                            case "price_high_low":
                                postList.sort((p1, p2) -> Double.compare(parsePrice( p2.getPriceForService()), parsePrice(p1.getPriceForService())));
                                break;
                        }
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
            sortBy = data.getStringExtra("sortBy");
            minPriceStr = data.getStringExtra("minPrice");
            minPrice = parsePrice(minPriceStr);
            maxWeightStr = data.getStringExtra("maxWeight");
            maxWeight = parsePrice(maxWeightStr);

            Log.d("FILTERS", "Category: " + filterCategory + ", From: " + countryFrom + ", To: " + countryTo + ", Sort: " + sortBy);

            loadData();
        }
    }

    private int parsePrice(String priceStr) {
        if (priceStr == null) return 0;
        try {

            String cleaned = priceStr.replaceAll("[^\\d]", "");
            return Integer.parseInt(cleaned);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    private void loadUserDetails() {
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);
    }
}

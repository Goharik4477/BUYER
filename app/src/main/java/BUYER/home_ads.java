package BUYER;

import android.annotation.SuppressLint;
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

import com.example.buyer.R;
import com.example.buyer.SearchActivity;
import com.example.buyer.databinding.ActivityHomeAdsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import BUYER.Adapter.PostAdapter;
import BUYER.Model.Post;
import BUYER.SignInSignUp.SignIn;
import BUYER.listeners.MessageListener;
import BUYER.listeners.UserListener;
import BUYER.models.User;
import BUYER.utilities.Constants;
import BUYER.utilities.PreferenceManager;

public class home_ads extends AppCompatActivity {
    private ActivityHomeAdsBinding binding;
    RecyclerView dashboardRV;
ArrayList<Post> postList;
FirebaseDatabase database;
FirebaseAuth auth;
    private PreferenceManager preferenceManager;
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
                finish();}
            else if (item.getItemId() == R.id.menu_bottom_notification) {
                startActivity(new Intent(getApplicationContext(), notifications.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_profile) {
                startActivity(new Intent(getApplicationContext(), second_profile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        loadUserDetails();
        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( home_ads.this, SearchActivity.class);
                startActivity(intent);
            }
        });
//posts
      /*  dashboardRV =findViewById(R.id.dashboard);
        postList = new ArrayList<>();


        PostAdapter postAdapter = new PostAdapter(postList,getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        dashboardRV.setLayoutManager(layoutManager);
        dashboardRV.setNestedScrollingEnabled(false);
        dashboardRV.setAdapter(postAdapter);

        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    postList.add(post);
                }
                postAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to load data: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error loading posts: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });*/
        loadData();
    }

    private void loadUserDetails(){
      //  binding.textName.setText(preferenceManager.getString(Constants.KEY_NAME));
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);
    }


    private void loadData() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            dashboardRV =findViewById(R.id.dashboard);
            postList = new ArrayList<>();


            PostAdapter postAdapter = new PostAdapter(postList,getApplicationContext());
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
                        postList.add(post);
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


}
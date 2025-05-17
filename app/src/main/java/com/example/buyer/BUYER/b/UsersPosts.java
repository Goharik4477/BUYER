package com.example.buyer.BUYER.b;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.BUYER.b.Adapter.PostAdapter;

import com.example.buyer.BUYER.b.Model.Post;
import com.example.buyer.BUYER.b.SignInSignUp.SignIn;
import com.example.buyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UsersPosts extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Post> postList;
    private PostAdapter postAdapter;

    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users_posts);
        getSupportActionBar().hide();



        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();



        loadData();
    }


    private void loadData() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String currentUserId = currentUser.getUid();

            recyclerView = findViewById(R.id.recyclerViewUsersPosts);
            postList = new ArrayList<>();

            PostAdapter postAdapter = new PostAdapter(postList, getApplicationContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(postAdapter);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    postList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Post post = dataSnapshot.getValue(Post.class);
                        if (post != null && currentUserId.equals(post.getUserId())) {
                            postList.add(post);
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
            Intent intent = new Intent(UsersPosts.this, SignIn.class);
            startActivity(intent);
            finish();
        }
    }
}


package com.example.buyer.BUYER.b;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.BUYER.b.Adapter.PostAdapter;
import com.example.buyer.BUYER.b.Model.Post;
import com.example.buyer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ModeratorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Post> pendingPosts;
    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moderator);

        getSupportActionBar().setTitle("Moderator");

        recyclerView = findViewById(R.id.recyclerViewPending);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pendingPosts = new ArrayList<>();
        adapter = new PostAdapter(pendingPosts, this, true); // true = moderation mode
        recyclerView.setAdapter(adapter);

        loadPendingPosts();
    }

    private void loadPendingPosts() {
        FirebaseDatabase.getInstance().getReference().child("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pendingPosts.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Post post = data.getValue(Post.class);
                            if (post != null && !post.isApproved()) {
                                pendingPosts.add(post);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ModeratorActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
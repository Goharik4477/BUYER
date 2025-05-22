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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Post> historyPosts = new ArrayList<>();
    PostAdapter adapter;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference("posts");
    DatabaseReference historyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        getSupportActionBar().setTitle("History");

        recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(historyPosts, this);
        recyclerView.setAdapter(adapter);

        if (auth.getCurrentUser() == null) return;

        String uid = auth.getCurrentUser().getUid();
        historyRef = FirebaseDatabase.getInstance().getReference("viewHistory").child(uid);

        historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historyPosts.clear();
                for (DataSnapshot postIdSnap : snapshot.getChildren()) {
                    String postId = postIdSnap.getKey();
                    if (postId != null) {
                        loadPostById(postId);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewHistoryActivity.this, "Ошибка загрузки истории", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPostById(String postId) {
        postsRef.child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                if (post != null) {
                    historyPosts.add(post);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
package com.example.buyer.BUYER.b.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.BUYER.b.ChatActivity;
import com.example.buyer.BUYER.b.listeners.UserListener;
import com.example.buyer.BUYER.b.models.User;
import com.example.buyer.BUYER.b.utilities.Constants;
import com.example.buyer.BUYER.b.utilities.PreferenceManager;
import com.example.buyer.R;
import com.example.buyer.databinding.ActivitySearch2Binding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements UserListener {
    ActivitySearch2Binding binding;
    private EditText inputEmail;
    private ImageView searchButton, backButton;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textErrorMessage, textClick;

    private PreferenceManager preferenceManager;
    private List<User> searchResults;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySearch2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();
        preferenceManager = new PreferenceManager(getApplicationContext());

        inputEmail = findViewById(R.id.inputEmail);
        searchButton = findViewById(R.id.ImageSearch);
        backButton = findViewById(R.id.ImageBack);
        recyclerView = findViewById(R.id.usersRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        textErrorMessage = findViewById(R.id.textErrorMessage);
        textClick = findViewById(R.id.text_click);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        searchResults = new ArrayList<>();
        String postedByEmail = getIntent().getStringExtra("postedByEmail");
        if (postedByEmail != null) {
            inputEmail.setText(postedByEmail);
            String email = inputEmail.getText().toString().trim();
            searchUserByEmail(email);
            textClick.setVisibility(View.VISIBLE);
        }
        searchButton.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            if (!email.isEmpty()) {
                searchUserByEmail(email);
            } else {
                Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> finish());
    }

    private void searchUserByEmail(String email) {
        loading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, email)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            if (preferenceManager.getString(Constants.KEY_USER_ID).equals(snapshot.getId()))
                                continue;

                            User user = new User();
                            user.name = snapshot.getString(Constants.KEY_NAME);
                            user.email = snapshot.getString(Constants.KEY_EMAIL);
                            user.image = snapshot.getString(Constants.KEY_IMAGE);
                            user.token = snapshot.getString(Constants.KEY_FCM_TOKEN);
                            user.id = snapshot.getId();
                            users.add(user);
                        }

                        userAdapter = new UserAdapter(users, this);
                        recyclerView.setAdapter(userAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                        textErrorMessage.setVisibility(View.GONE);
                    } else {
                        showErrorMessage("No user found with that email.");
                    }
                });
    }

    private void showErrorMessage(String message) {
        textErrorMessage.setText(message);
        textErrorMessage.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void loading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
        finish();
    }
}
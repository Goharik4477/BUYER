package com.example.buyer.BUYER.b;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buyer.BUYER.b.utilities.Constants;
import com.example.buyer.BUYER.b.utilities.PreferenceManager;
import com.example.buyer.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RatingActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private Button buttonSubmitRating;
    private FirebaseFirestore database;
    private String ratedUserId;
    private TextView textUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratingBar = findViewById(R.id.ratingBar);
        buttonSubmitRating = findViewById(R.id.buttonSubmitRating);
        textUserName = findViewById(R.id.textUserName);

        database = FirebaseFirestore.getInstance();

        ratedUserId = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");

        textUserName.setText("Rate " + userName);

        buttonSubmitRating.setOnClickListener(v -> submitRating());
    }

    private void submitRating() {
        float rating = ratingBar.getRating();

        String senderId = new PreferenceManager(getApplicationContext())
                .getString(Constants.KEY_USER_ID); // текущий пользователь

        String docId = senderId + "_" + ratedUserId;

        DocumentReference ratingRef = database.collection("ratings").document(docId);


        ratingRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Toast.makeText(this, "You already rated this user.", Toast.LENGTH_SHORT).show();
            } else {

                ratingRef.set(new HashMap<String, Object>() {{
                    put("senderId", senderId);
                    put("receiverId", ratedUserId);
                    put("rating", rating);
                }}).addOnSuccessListener(unused -> {

                    DocumentReference userRef = database.collection("users").document(ratedUserId);
                    userRef.update("totalRating", FieldValue.increment(rating),
                                    "ratingCount", FieldValue.increment(1))
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "Thank you for rating!", Toast.LENGTH_SHORT).show();
                                finish();
                            });
                }).addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to record rating", Toast.LENGTH_SHORT).show());
            }
        });
    }

}
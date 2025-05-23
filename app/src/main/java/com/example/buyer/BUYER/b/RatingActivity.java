package com.example.buyer.BUYER.b;

import android.os.Bundle;
import android.text.Layout;
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
import java.util.Map;


public class RatingActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private Button buttonSubmitRating, completeButton;
    private FirebaseFirestore database;
    private String ratedUserId;
    Layout linear;

    private TextView textUserName;
    private String currentUserId;
    private DocumentReference transactionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratingBar = findViewById(R.id.ratingBar);
        buttonSubmitRating = findViewById(R.id.buttonSubmitRating);
        textUserName = findViewById(R.id.textUserName);
        completeButton = findViewById(R.id.complete);

        database = FirebaseFirestore.getInstance();

        ratedUserId = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");

        currentUserId = new PreferenceManager(getApplicationContext())
                .getString(Constants.KEY_USER_ID);


        textUserName.setText("Rate " + userName);
        buttonSubmitRating.setEnabled(false);

        String transactionId = generateTransactionId(currentUserId, ratedUserId);
        transactionRef = database.collection("transactions").document(transactionId);

        checkInitialTransactionStatus();

        completeButton.setOnClickListener(v -> markTransactionComplete());

        buttonSubmitRating.setOnClickListener(v -> submitRating());
    }

    private void submitRating() {
        float rating = ratingBar.getRating();

        String docId = currentUserId + "_" + ratedUserId;

        DocumentReference ratingRef = database.collection("ratings").document(docId);

        ratingRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Toast.makeText(this, "You already rated this user.", Toast.LENGTH_SHORT).show();
            } else {
                ratingRef.set(new HashMap<String, Object>() {{
                    put("senderId", currentUserId);
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

    private void markTransactionComplete() {
        transactionRef.get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                Map<String, Object> updates = new HashMap<>();
                if (currentUserId.equals(snapshot.getString("user1Id"))) {
                    updates.put("user1Complete", true);
                } else {
                    updates.put("user2Complete", true);
                }

                transactionRef.update(updates).addOnSuccessListener(aVoid -> {
                    checkTransactionCompletion();
                    Toast.makeText(this, "Marked as complete", Toast.LENGTH_SHORT).show();
                });
            } else {
                Map<String, Object> transaction = new HashMap<>();
                transaction.put("user1Id", currentUserId);
                transaction.put("user2Id", ratedUserId);
                transaction.put("user1Complete", true);
                transaction.put("user2Complete", false);
                transaction.put("isCompleted", false);

                transactionRef.set(transaction).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Marked as complete. Waiting for other user.", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void checkTransactionCompletion() {
        transactionRef.get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                Boolean user1Complete = snapshot.getBoolean("user1Complete");
                Boolean user2Complete = snapshot.getBoolean("user2Complete");

                if (Boolean.TRUE.equals(user1Complete) && Boolean.TRUE.equals(user2Complete)) {
                    transactionRef.update("isCompleted", true);
                    buttonSubmitRating.setEnabled(true);
                    Toast.makeText(this, "Transaction completed by both users!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Waiting for the other user.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkInitialTransactionStatus() {
        transactionRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists() && Boolean.TRUE.equals(documentSnapshot.getBoolean("isCompleted"))) {
                buttonSubmitRating.setEnabled(true);
            }
        });
    }

    private String generateTransactionId(String user1, String user2) {
        return user1.compareTo(user2) < 0 ? user1 + "_" + user2 : user2 + "_" + user1;
    }
}
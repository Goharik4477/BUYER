package com.example.buyer.BUYER.b;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.BUYER.b.Model.Post;
import com.example.buyer.R;
import com.example.buyer.databinding.ActivitySecondProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Locale;

import com.example.buyer.BUYER.b.SignInSignUp.ReadWriteUsersdetails;
import com.example.buyer.BUYER.b.SignInSignUp.SignIn_or_SignUp;
import com.example.buyer.BUYER.b.UserProfileSetting.ChangePasswordActivity;
import com.example.buyer.BUYER.b.UserProfileSetting.DeleteProfileActivity;
import com.example.buyer.BUYER.b.UserProfileSetting.UpdateEmailActivity;
import com.example.buyer.BUYER.b.UserProfileSetting.UpdateProfileActivity;
import com.example.buyer.BUYER.b.utilities.Constants;
import com.example.buyer.BUYER.b.utilities.PreferenceManager;

public class second_profile extends AppCompatActivity {

    private TextView TextViewWelcome, TextViewFFullName, TextViewEmail, TextViewPosts;
    private ProgressBar progressBar;
    private String fullName, email;
    private PreferenceManager preferenceManager;
    private ImageView imageView;
    FirebaseStorage storage;
    FirebaseDatabase database;
     private ActivitySecondProfileBinding binding;


    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondProfileBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
database = FirebaseDatabase.getInstance();

        preferenceManager = new PreferenceManager(getApplicationContext());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_bottom_home) {
                startActivity(new Intent(getApplicationContext(), home_ads.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
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

            return false;
        });

binding.icPost.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(second_profile.this, UsersPosts.class);
        startActivity(intent);
    }
});



        TextViewWelcome = findViewById(R.id.textView_show_welcome);
        TextViewFFullName = findViewById(R.id.show_full_name);
        TextViewEmail = findViewById(R.id.show_email);
        progressBar = findViewById(R.id.progressbaruser);
        TextViewPosts = findViewById(R.id.show_posts);
binding.showPosts.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(second_profile.this, UsersPosts.class);
        startActivity(intent);
    }
});
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        countUserPosts(firebaseUser.getUid());
        if (firebaseUser == null){
            Toast.makeText(this, "Something went wrong! User's details are not available at the moment",
                    Toast.LENGTH_LONG).show();
        }else {
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
            loadUserDetails();
        }


    }

    private void countUserPosts(String userId) {
        FirebaseDatabase.getInstance().getReference("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int count = 0;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Post post = dataSnapshot.getValue(Post.class);
                            if (post != null && userId.equals(post.getUserId())) {
                                count++;
                            }
                        }
                        TextViewPosts.setText("Posts: " + count);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(second_profile.this, "Failed to load posts count", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();


        //database for "registered user"

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUsersdetails readUsersdetails = snapshot.getValue(ReadWriteUsersdetails.class);
                if(readUsersdetails != null){
                    fullName = firebaseUser.getDisplayName();
                    email =firebaseUser.getEmail();
                    TextViewWelcome.setText(fullName);
                    TextViewFFullName.setText(fullName);
                    TextViewEmail.setText(email);

                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(second_profile.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate manu
        getMenuInflater().inflate(R.menu.common_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_refresh){
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        } else if(id ==  R.id.menu_update_profile){
            Intent intent = new Intent(second_profile.this, UpdateProfileActivity.class);
            startActivity(intent);
        }else if(id ==  R.id.menu_update_email){
            Intent intent = new Intent(second_profile.this, UpdateEmailActivity.class);
            startActivity(intent);
        }else if(id ==  R.id.menu_settings){
            Toast.makeText(this, "menu settings", Toast.LENGTH_SHORT).show();
        }else if(id ==  R.id.menu_change_password){
            Intent intent = new Intent(second_profile.this, ChangePasswordActivity.class);
            startActivity(intent);
        }else if(id ==  R.id.menu_delete_profile){
            Intent intent = new Intent(second_profile.this, DeleteProfileActivity.class);
            startActivity(intent);
        }else if(id ==  R.id.menu_logout){
            authProfile.signOut();

            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(second_profile.this, SignIn_or_SignUp.class);
            signOut();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    private void signOut(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(preferenceManager.getString(Constants.KEY_USER_ID));
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates).addOnSuccessListener(unused -> {
            preferenceManager.clear();
            startActivity(new Intent(getApplicationContext(), SignIn_or_SignUp.class));
            finish();
        }).addOnFailureListener(e -> Toast.makeText(this, "Unable sign out", Toast.LENGTH_SHORT).show());


    }
    private void loadUserDetails(){

        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        binding.imageViewProfileDp.setImageBitmap(bitmap);



        FirebaseFirestore database = FirebaseFirestore.getInstance();
        String userId = preferenceManager.getString(Constants.KEY_USER_ID);

        database.collection(Constants.KEY_COLLECTION_USERS)
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Long totalRating = documentSnapshot.getLong("totalRating");
                        Long ratingCount = documentSnapshot.getLong("ratingCount");

                        if (totalRating != null && ratingCount != null && ratingCount > 0) {
                            double average = (double) totalRating / ratingCount;
                            binding.showRat.setText(String.format(Locale.getDefault(), "Rating: %.1f ★", average));
                        } else {
                            binding.showRat.setText("Rating: N/A");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    binding.showRat.setText("Rating: N/A");
                });
    }

}
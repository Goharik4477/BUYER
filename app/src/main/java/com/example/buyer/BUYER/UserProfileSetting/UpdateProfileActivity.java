package com.example.buyer.BUYER.UserProfileSetting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.buyer.BUYER.second_profile;
import com.example.buyer.BUYER.SignInSignUp.ReadWriteUsersdetails;
import com.example.buyer.BUYER.SignInSignUp.SignIn_or_SignUp;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText editTextUpdateName;
    private String textFullName;
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().hide();

        progressBar =findViewById(R.id.progressbarUpdateProfile);
        editTextUpdateName = findViewById(R.id.EditText_update_profile_name);
        authProfile =FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        //show profile data

        showProfile(firebaseUser);

        //Update Email

      Button buttonUpdateEmail = findViewById(R.id.button_profile_update_email);
        buttonUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfileActivity.this, UpdateEmailActivity.class );
                startActivity(intent);
                finish();
            }
        });

        //Update profile
        Button buttonUpdateProfile = findViewById(R.id.button_profile_update);
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(firebaseUser);
            }
        });

    }
     //update profile
    private void updateProfile(FirebaseUser firebaseUser) {
        if (TextUtils.isEmpty(textFullName)){
            Toast.makeText(UpdateProfileActivity.this, "Please enter name", Toast.LENGTH_LONG).show();
            editTextUpdateName.setError("Username is required");
            editTextUpdateName.requestFocus();
        }   else {
            textFullName = editTextUpdateName.getText().toString();

            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

            String userID = firebaseUser.getUid();
            progressBar.setVisibility(View.VISIBLE);

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
            firebaseUser.updateProfile(profileUpdates);
            Toast.makeText(this, "Update Successful!", Toast.LENGTH_SHORT).show();

            //stop flags
            Intent intent = new Intent(UpdateProfileActivity.this, second_profile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }
        progressBar.setVisibility(View.GONE);
    }



    //fetch data from firebase and display
    private void showProfile(FirebaseUser firebaseUser) {
        String userIDofRegistered = firebaseUser.getUid();

        //Extracting User Reference from database

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

        progressBar.setVisibility(View.VISIBLE);

        referenceProfile.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUsersdetails readUserDetails = snapshot.getValue(ReadWriteUsersdetails.class);
                if (readUserDetails != null){
                    textFullName = firebaseUser.getDisplayName();

                    editTextUpdateName.setText(textFullName);
                } else {
                    Toast.makeText(UpdateProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(UpdateProfileActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        }else if(id ==  R.id.menu_update_email){
                    Intent intent = new Intent(UpdateProfileActivity.this, UpdateEmailActivity.class);
                    startActivity(intent);
                    finish();
                }else if(id ==  R.id.menu_settings){
                    Toast.makeText(this, "menu settings", Toast.LENGTH_SHORT).show();
                }else if(id ==  R.id.menu_change_password){
                    Intent intent = new Intent(UpdateProfileActivity.this, ChangePasswordActivity.class);
                    startActivity(intent);
                    finish();
                }else if(id ==  R.id.menu_delete_profile){
                    Intent intent = new Intent(UpdateProfileActivity.this, DeleteProfileActivity.class);
                    startActivity(intent);
                    finish();
                }else if(id ==  R.id.menu_logout){
            authProfile.signOut();
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateProfileActivity.this, SignIn_or_SignUp.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(UpdateProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


}
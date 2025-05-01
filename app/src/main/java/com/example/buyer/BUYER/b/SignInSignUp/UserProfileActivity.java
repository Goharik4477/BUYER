package com.example.buyer.BUYER.b.SignInSignUp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.buyer.BUYER.b.home_ads;
import com.example.buyer.BUYER.b.utilities.Constants;
import com.example.buyer.BUYER.b.utilities.PreferenceManager;

public class UserProfileActivity extends AppCompatActivity {
    private TextView TextViewWelcome, TextViewFFullName, TextViewEmail;
    private ProgressBar progressBar;
    private String fullName, email;
    private PreferenceManager preferenceManager;
    private ImageView imageView, buyerBag;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.userProfile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextViewWelcome = findViewById(R.id.textView_show_welcome);
        TextViewFFullName = findViewById(R.id.show_full_name);
        TextViewEmail = findViewById(R.id.show_email);
        progressBar = findViewById(R.id.progressbaruser);
        buyerBag =findViewById(R.id.imageView3);
        preferenceManager = new PreferenceManager(getApplicationContext());



        //profile pic

        imageView = findViewById(R.id.imageView_profile_dp);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

       if (firebaseUser == null){
            Toast.makeText(this, "Something went wrong! User's details are not available at the moment",
                    Toast.LENGTH_LONG).show();
        }else {
           checkIfEmailVerified(firebaseUser);
            progressBar.setVisibility(View.VISIBLE);
           loadUserDetails();
            showUserProfile(firebaseUser);

           buyerBag.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   startActivity(new Intent(UserProfileActivity.this, home_ads.class));
                   finish();
               }
           });



       }





    }
//coming to usersProfile activity
    private void checkIfEmailVerified(FirebaseUser firebaseUser) {
        if(!firebaseUser.isEmailVerified()){
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        //set up alert
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        builder.setTitle("Email not verified ");
        builder.setMessage("Please verify your email now. You can not login without email verification next time. ");

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        //create alertdialog

        AlertDialog alertDialog = builder.create();


        // show alertdialog


            alertDialog.show();



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
                Toast.makeText(UserProfileActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
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
    private void loadUserDetails(){

        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        imageView.setImageBitmap(bitmap);
    }

  /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

                if(id == R.id.menu_refresh){
                    startActivity(getIntent());
                    finish();
                    overridePendingTransition(0,0);
                } else if(id ==  R.id.menu_update_profile){
                    Intent intent = new Intent(UserProfileActivity.this, UpdateProfileActivity.class);
                    startActivity(intent);
                }else if(id ==  R.id.menu_update_email){
                    Intent intent = new Intent(UserProfileActivity.this, UpdateEmailActivity.class);
                    startActivity(intent);
                }else if(id ==  R.id.menu_settings){
                    Toast.makeText(this, "menu settings", Toast.LENGTH_SHORT).show();
                }else if(id ==  R.id.menu_change_password){
                    Intent intent = new Intent(UserProfileActivity.this, ChangePasswordActivity.class);
                    startActivity(intent);
                }else if(id ==  R.id.menu_delete_profile){
                    Intent intent = new Intent(UserProfileActivity.this, DeleteProfileActivity.class);
                    startActivity(intent);
                }else if(id ==  R.id.menu_logout){
                    authProfile.signOut();
                    Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserProfileActivity.this, SignIn_or_SignUp.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
        return super.onOptionsItemSelected(item);


    }*/
}
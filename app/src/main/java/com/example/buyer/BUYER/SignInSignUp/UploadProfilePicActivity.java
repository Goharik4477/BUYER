package com.example.buyer.BUYER.SignInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.google.firebase.auth.FirebaseAuth;

import com.example.buyer.BUYER.UserProfileSetting.ChangePasswordActivity;
import com.example.buyer.BUYER.UserProfileSetting.DeleteProfileActivity;
import com.example.buyer.BUYER.UserProfileSetting.UpdateEmailActivity;
import com.example.buyer.BUYER.UserProfileSetting.UpdateProfileActivity;

public class UploadProfilePicActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView ImageViewUploadPic;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_profile_pic);



        Button buttonUploadPicChoose = findViewById(R.id.upload_pic_choose_button);
        Button buttonUploadPic = findViewById(R.id.upload_pic_button);
        progressBar =findViewById(R.id.progressbarPIC);
        ImageViewUploadPic = findViewById(R.id.ImageView_profile_dp2);

        authProfile = FirebaseAuth.getInstance();


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
                    Intent intent = new Intent(UploadProfilePicActivity.this, UpdateProfileActivity.class);
                    startActivity(intent);
                    finish();
                }else if(id ==  R.id.menu_update_email){
                    Intent intent = new Intent(UploadProfilePicActivity.this, UpdateEmailActivity.class);
                    startActivity(intent);
                    finish();
                }else if(id ==  R.id.menu_settings){
                    Toast.makeText(this, "menu settings", Toast.LENGTH_SHORT).show();
                }else if(id ==  R.id.menu_change_password){
                    Intent intent = new Intent(UploadProfilePicActivity.this, ChangePasswordActivity.class);
                    startActivity(intent);
                    finish();
                }else if(id ==  R.id.menu_delete_profile){
                    Intent intent = new Intent(UploadProfilePicActivity.this, DeleteProfileActivity.class);
                    startActivity(intent);
                    finish();
                }else if(id ==  R.id.menu_logout){
            authProfile.signOut();
            Toast.makeText(UploadProfilePicActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UploadProfilePicActivity.this, SignIn_or_SignUp.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(UploadProfilePicActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
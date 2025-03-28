package BUYER.SignInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import BUYER.home_ads;

public class MainActivity extends AppCompatActivity {
private FirebaseAuth authProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();


         if (firebaseUser == null){
             new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     Intent intent = new Intent(MainActivity.this, SignIn_or_SignUp.class);
                     startActivity(intent);
                     finish();
                 }
             },3000);
        }else {
                new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     startActivity(new Intent(MainActivity.this, home_ads.class));
                     finish();
                 }
             },3000);
         }



    }
}
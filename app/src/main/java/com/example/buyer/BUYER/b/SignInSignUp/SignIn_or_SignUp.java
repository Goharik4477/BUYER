package com.example.buyer.BUYER.b.SignInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn_or_SignUp extends AppCompatActivity{
    private Button goSignIn, testUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in_or_sign_up);
        goSignIn = findViewById(R.id.gosignin);
        getSupportActionBar().hide();

        testUser = findViewById(R.id.buttonTest);
        testUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String testEmail = "individualproject2025@gmail.com";
                String testPassword = "12345678";

                auth.signInWithEmailAndPassword(testEmail, testPassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                if (user != null && user.isEmailVerified()) {
                                    Toast.makeText(SignIn_or_SignUp.this, "Signed in as Test User", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignIn_or_SignUp.this, UserProfileActivity.class));
                                    finish();
                                } else if (user != null) {
                                    user.sendEmailVerification();
                                    auth.signOut();
                                    Toast.makeText(SignIn_or_SignUp.this, "Please verify your email first.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(SignIn_or_SignUp.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });


        goSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn_or_SignUp.this, SignIn.class);
                startActivity(intent);
            }
        });



    }








        public void goSignUp(View v){
        Intent intent = new Intent(SignIn_or_SignUp.this, SignUp.class);
        startActivity(intent);
        }

}
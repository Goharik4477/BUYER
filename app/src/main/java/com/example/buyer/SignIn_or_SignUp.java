package com.example.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignIn_or_SignUp extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in_or_sign_up);
    }

        public void goSignIn(View v){
            Intent intent = new Intent(SignIn_or_SignUp.this, SignIn.class);
            startActivity(intent);

        }

        public void goSignUp(View v){
        Intent intent = new Intent(SignIn_or_SignUp.this, SignUp.class);
        startActivity(intent);
        }

}
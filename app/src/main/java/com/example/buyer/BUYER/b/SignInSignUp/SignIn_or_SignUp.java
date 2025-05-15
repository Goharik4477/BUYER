package com.example.buyer.BUYER.b.SignInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;

public class SignIn_or_SignUp extends AppCompatActivity{
    private Button goSignIn, testUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in_or_sign_up);
        goSignIn = findViewById(R.id.gosignin);
        getSupportActionBar().hide();


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
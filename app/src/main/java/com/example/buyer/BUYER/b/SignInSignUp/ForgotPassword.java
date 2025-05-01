package com.example.buyer.BUYER.b.SignInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ForgotPassword extends AppCompatActivity {
    private Button send;
    private EditText editTextEmail;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private final static String TAG = "Forgot Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();

        editTextEmail = findViewById(R.id.editTextEmail_forgot);
        send = findViewById(R.id.send_password);
        progressBar = findViewById(R.id.progressbarForgot);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(ForgotPassword.this, "Please enter your registered email!", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Email is required ");
                    editTextEmail.requestFocus();
                } else    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(ForgotPassword.this, "Please enter valid email!", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError(" Valid email is required ");
                    editTextEmail.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    resetPassword(email);
                }
            }
        });

    }

    private void resetPassword(String email) {
        authProfile = FirebaseAuth.getInstance();
        authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Please check your email for password reset link!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ForgotPassword.this, SignIn_or_SignUp.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        editTextEmail.setError("User does no exists or is no longer valid. Please registered again!");
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(ForgotPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                progressBar.setVisibility(View.GONE);

            }
        });
    }
}
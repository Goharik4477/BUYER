package com.example.buyer.BUYER.b.SignInSignUp;

import static android.content.ContentValues.TAG;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.buyer.BUYER.b.utilities.Constants;
import com.example.buyer.BUYER.b.utilities.PreferenceManager;

public class SignIn extends AppCompatActivity {

    private EditText editTextLoginEmail, editTextLoginPwd;
    private ProgressBar progressBar;
    private PreferenceManager preferenceManager;
    private FirebaseAuth authProfile;
    private TextView textViewSingUp;
    private Button login;
    private Button test2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.newsignin);
        getSupportActionBar().hide();
preferenceManager = new PreferenceManager(getApplicationContext());
        textViewSingUp = findViewById(R.id.textViewSignup);
        textViewSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });


       editTextLoginPwd = findViewById(R.id.editTextLoginPassword);
        editTextLoginEmail = findViewById(R.id.editTextSignInEmail);
        progressBar = findViewById(R.id.progressbarSignIn);
        authProfile = FirebaseAuth.getInstance();
        login = findViewById(R.id.gggg);
        TextView forgotPassword = findViewById(R.id.Forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignIn.this, "You can reset your password mow", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignIn.this, ForgotPassword.class));
            }
        });

        ImageView imageViewShowGidePwd = findViewById(R.id.hide_showPwd);
        imageViewShowGidePwd.setImageResource(R.drawable.outline_visibility_off_24);
        imageViewShowGidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextLoginPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    editTextLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowGidePwd.setImageResource(R.drawable.outline_visibility_off_24);
                } else {
                    editTextLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowGidePwd.setImageResource(R.drawable.outline_visibility_24);
                }
            }
        });

        test2 = findViewById(R.id.TestUser2);



        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextLoginEmail.setText("ttesttuuser2@gmail.com");
                editTextLoginPwd.setText("12345678");


            }
        });





       login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = editTextLoginEmail.getText().toString();
                String textPwd = editTextLoginPwd.getText().toString();


                if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(SignIn.this, "Please enter your Email", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Email is required");
                    editTextLoginEmail.requestFocus();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(SignIn.this, "Please re-enter your Email", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Valid email is required");
                    editTextLoginEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)){
                    Toast.makeText(SignIn.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    editTextLoginPwd.setError("Password is required");
                    editTextLoginPwd.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail, textPwd);
                    SignIn();
                }

            }
        });





    }
    private void loginUser(String Email, String Pwd) {
        authProfile.signInWithEmailAndPassword(Email, Pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Get instance of  the current user
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    // check if email is verified before user can access their profile
                    if (firebaseUser.isEmailVerified()){
                        Toast.makeText(SignIn.this, "You logged in now", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignIn.this, UserProfileActivity.class));
                        finish();
                    } else {
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut();
                        showAlertDialog();
                    }

                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e){
                        editTextLoginEmail.setError("User does not exists or is no longer valid. Please register again.");
                        editTextLoginEmail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        editTextLoginEmail.setError("Invalid credentials. Kindly, check and re-enter.");
                        editTextLoginEmail.requestFocus();
                    } catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(SignIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                progressBar.setVisibility(View.GONE);
            }
        });
    }



    private void showAlertDialog() {
        //set up alert
        AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this);
        builder.setTitle("Email not verified ");
        builder.setMessage("Please verify your email now. You can not login without email verification. ");

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

    @Override
    protected void onStart() {
        super.onStart();
        if(authProfile.getCurrentUser() != null){
            Toast.makeText(this, "Already logged in!", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(SignIn.this, UserProfileActivity.class));
            finish();

        } else {
            Toast.makeText(this, "You can login now!", Toast.LENGTH_SHORT).show();
        }


    }
    private  void SignIn(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, editTextLoginEmail.getText().toString()).
                whereEqualTo(Constants.KEY_PASSWORD, editTextLoginPwd.getText().toString()).get().addOnCompleteListener( task -> {
                    if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() >0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));

                     } else {
                        Toast.makeText(this, "Unable to sign in", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}

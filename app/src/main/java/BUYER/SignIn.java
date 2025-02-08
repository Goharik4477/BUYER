package BUYER;

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

public class SignIn extends AppCompatActivity {

    private EditText editTextLoginEmail, editTextLoginPwd;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private TextView textViewSingUp;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.newsignin);

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
        imageViewShowGidePwd.setImageResource(R.drawable.passwordhide);
        imageViewShowGidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextLoginPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    editTextLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowGidePwd.setImageResource(R.drawable.passwordhide);
                } else {
                    editTextLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowGidePwd.setImageResource(R.drawable.passwordshow);
                }
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
}

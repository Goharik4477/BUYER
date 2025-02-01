package BUYER;

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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private EditText Username, Email, Password, Repeat_password;
private static final String TAG= "SignUpActivity";
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        //getSupportActionBar().setTitle("Sign up");
        Toast.makeText(this, "You can sign up now",Toast.LENGTH_LONG ).show();

        // edittext
        Username = findViewById(R.id.editTextUsername);
        Email = findViewById(R.id.editTextEmail);
        Password = findViewById(R.id.editTextPassword);
        Repeat_password = findViewById(R.id.editTextPassword2);
        progressBar = findViewById(R.id.progressbarsignup);


        ImageView imageViewShowGidePwd = findViewById(R.id.signUpHideAndShow);
        imageViewShowGidePwd.setImageResource(R.drawable.passwordhide);
        imageViewShowGidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowGidePwd.setImageResource(R.drawable.passwordhide);
                } else {
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowGidePwd.setImageResource(R.drawable.passwordshow);
                }
            }
        });


        ImageView imageViewShowGidePwd2 = findViewById(R.id.signUpHideAndShow2);
        imageViewShowGidePwd2.setImageResource(R.drawable.passwordhide);
        imageViewShowGidePwd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Repeat_password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    Repeat_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowGidePwd2.setImageResource(R.drawable.passwordhide);
                } else {
                    Repeat_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowGidePwd2.setImageResource(R.drawable.passwordshow);
                }
            }
        });

        Button  Signup = findViewById(R.id.buttonSignUp);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fills

                String username = Username.getText().toString();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String repeat_password = Repeat_password.getText().toString();

                //is empty

                if (TextUtils.isEmpty(username)){
                    Toast.makeText(SignUp.this, "Please enter username", Toast.LENGTH_LONG).show();
                    Username.setError("Username is required");
                    Username.requestFocus();
                } else if (TextUtils.isEmpty(email)){

                    Toast.makeText(SignUp.this, "Please enter email", Toast.LENGTH_LONG).show();
                    Email.setError("Email is required");
                    Email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    Toast.makeText(SignUp.this, "Please re-enter email", Toast.LENGTH_LONG).show();
                    Email.setError(" Valid email is required");
                    Email.requestFocus();
                }

                else if (TextUtils.isEmpty(password)){

                    Toast.makeText(SignUp.this, "Please enter password", Toast.LENGTH_LONG).show();
                    Password.setError("password is required");
                    Password.requestFocus();
                }   else if (password.length() < 8){

                    Toast.makeText(SignUp.this, "Password should be at least 8 digits", Toast.LENGTH_LONG).show();
                    Password.setError("password too weak");
                    Password.requestFocus();
                }

                else if (TextUtils.isEmpty(repeat_password)){

                    Toast.makeText(SignUp.this, "Please enter password", Toast.LENGTH_LONG).show();
                    Repeat_password.setError("password Confirmation is required");
                    Repeat_password.requestFocus();
                }    else if (!repeat_password.equals(password)){

                    Toast.makeText(SignUp.this, "Please enter same password", Toast.LENGTH_LONG).show();
                    Repeat_password.setError("password Confirmation is required");
                    Repeat_password.requestFocus();
                    Password.clearComposingText();
                    Repeat_password.clearComposingText();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    SignupUser(username, email, password, repeat_password);

                }


            }
        });

    }

// firebase
    private void SignupUser(String username, String email, String password, String repeatPassword) {


        FirebaseAuth auth = FirebaseAuth.getInstance();

        //Create users profile
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this,
                new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()){

                         FirebaseUser firebaseUser = auth.getCurrentUser();
                         //update display  name of user

                         UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                         firebaseUser.updateProfile(profileChangeRequest);



                         //enter in  firebase
                         ReadWriteUsersdetails WriteUserDetails = new  ReadWriteUsersdetails(username);
                         // registered user
                         DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                         referenceProfile.child(firebaseUser.getUid()).setValue(WriteUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if (task.isSuccessful()){

                                     //verify

                                     firebaseUser.sendEmailVerification();
                                     Toast.makeText(SignUp.this, "User registered successfully." +
                                             " Pleas verify your email", Toast.LENGTH_LONG).show();

                       //open users profile

                         Intent intent = new Intent(SignUp.this, UserProfileActivity.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                 Intent.FLAG_ACTIVITY_NEW_TASK);

                         startActivity(intent);
                         finish();

                                 } else {
                                     Toast.makeText(SignUp.this, "User registered failed. Pleas try again",
                                             Toast.LENGTH_LONG).show();


                                 }
                                 progressBar.setVisibility(View.GONE);



                             }
                         });

                     }

                     else {
                         try {
                             throw  task.getException();
                         } catch (FirebaseAuthWeakPasswordException e){
                             Password.setError("Your password is too weak. Kindly use a mix of alphabets, numbers and spacial characters");
                             Password.requestFocus();
                         } catch (FirebaseAuthInvalidCredentialsException e){
                             Password.setError("Your email is invalid or already in use. Kindly re-enter. ");
                             Password.requestFocus();
                         } catch (FirebaseAuthUserCollisionException e){
                             Password.setError("User is already registered with this email. Use another email.");
                         } catch (Exception e){
                             Log.e(TAG, e.getMessage());
                             Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();

                         }
                         progressBar.setVisibility(View.GONE);
                     }
                    }
                });
    }

}
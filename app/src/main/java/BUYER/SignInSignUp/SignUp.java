package BUYER.SignInSignUp;
/*
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
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
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.example.buyer.databinding.ActivitySignInOrSignUpBinding;
import com.example.buyer.databinding.NewsignupBinding;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import BUYER.Navigation.HomeActivity;
import BUYER.utilities.Constants;
import BUYER.utilities.PreferenceManager;

public class SignUp extends AppCompatActivity {
    private NewsignupBinding binding;
    private ImageView profilePic;
    private PreferenceManager preferenceManager;
    private String  encodedImage;
    private TextView buyer, addImage;
    private EditText Username, Email, Password, Repeat_password;
private static final String TAG= "SignUpActivity";
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.newsignup);
        getSupportActionBar().hide();
       binding = NewsignupBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());


        Toast.makeText(this, "You can sign up now",Toast.LENGTH_LONG ).show();

        // edittext
        addImage =findViewById(R.id.textAddImage);
        profilePic = findViewById(R.id.newImageProfile);
        buyer = findViewById(R.id.buyer_signup_page_buyer);
        Username = findViewById(R.id.editTextUsername);
        Email = findViewById(R.id.editTextEmail);
        Password = findViewById(R.id.editTextPassword);
        Repeat_password = findViewById(R.id.editTextPassword2);
        progressBar = findViewById(R.id.progressbarsignup);
        buyer.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImage.launch(intent);
    }
});

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
      binding.textAddImage.setOnClickListener(v ->
        {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });

        Button  Signup = findViewById(R.id.buttonSignUp);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fills

                String image = encodedImage;
                String username = Username.getText().toString();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String repeat_password = Repeat_password.getText().toString();


                //is empty
                if(encodedImage.isEmpty()){
                    Toast.makeText(SignUp.this, "Select profile image", Toast.LENGTH_SHORT).show();
                    profilePic.requestFocus();
                }else if (TextUtils.isEmpty(username)){
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
                    SignupUser(username, email, password, repeat_password,image);
                   signUp();

                }


            }
        });


    }


    private void signUp(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME, Username.getText().toString());
        user.put(Constants.KEY_EMAIL, Email.getText().toString());
        user.put(Constants.KEY_PASSWORD, Password.getText().toString());
        user.put(Constants.KEY_IMAGE, encodedImage);
        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user).
                addOnSuccessListener(documentReference -> {
            loading(false);
            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
            preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
            preferenceManager.putString(Constants.KEY_NAME, Username.getText().toString());
            preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);
            Intent intent = new Intent(SignUp.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        })
                .addOnFailureListener(exception -> {
                    loading(false);
                    Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            progressBar.setVisibility(View.VISIBLE);
        }else {
          progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private String encodeImage(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth /bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight, false);
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayInputStream);
        byte []bytes =byteArrayInputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);

    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result ->{
        if(result.getResultCode() ==RESULT_OK){
            if(result.getData()!= null){
                Uri imageUri = result.getData().getData();
                try {
                    InputStream inputStream =getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    profilePic.setImageBitmap(bitmap);
                    addImage.setVisibility(View.GONE);
                    encodedImage = encodeImage(bitmap);

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
            });




// firebase
    private void SignupUser(String username, String email, String password, String repeatPassword, String encodedImage) {


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
                         ReadWriteUsersdetails WriteUserDetails = new  ReadWriteUsersdetails(username, encodedImage);
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

} /*



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
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
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.example.buyer.databinding.ActivitySignInOrSignUpBinding;
import com.example.buyer.databinding.NewsignupBinding;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import BUYER.Navigation.HomeActivity;
import BUYER.utilities.Constants;
import BUYER.utilities.PreferenceManager;
/* 2222
public class SignUp extends AppCompatActivity {
    private NewsignupBinding binding;
    private ImageView profilePic;
    private PreferenceManager preferenceManager;
    private String  encodedImage;
    private TextView buyer, addImage;
    private EditText Username, Email, Password, Repeat_password;
    private static final String TAG= "SignUpActivity";
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.newsignup);
        getSupportActionBar().hide();
        /* esmnuma   binding = NewsignupBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());


        Toast.makeText(this, "You can sign up now",Toast.LENGTH_LONG ).show();

        // edittext
        addImage =findViewById(R.id.textAddImage);
        profilePic = findViewById(R.id.newImageProfile);
        buyer = findViewById(R.id.buyer_signup_page_buyer);
        Username = findViewById(R.id.editTextUsername);
        Email = findViewById(R.id.editTextEmail);
        Password = findViewById(R.id.editTextPassword);
        Repeat_password = findViewById(R.id.editTextPassword2);
        progressBar = findViewById(R.id.progressbarsignup);
        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pickImage.launch(intent);
            }
        });

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
     /* es el  binding.textAddImage.setOnClickListener(v ->
        {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });

        Button  Signup = findViewById(R.id.buttonSignUp);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fills

                String image = encodedImage;
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
                    SignupUser(username, email, password, repeat_password,image);


                    signUp();

                }


            }
        });


    }


    private void signUp(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME, Username.getText().toString());
        user.put(Constants.KEY_EMAIL, Email.getText().toString());
        user.put(Constants.KEY_PASSWORD, Password.getText().toString());
        user.put(Constants.KEY_IMAGE, encodedImage);
        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user).
                addOnSuccessListener(documentReference -> {
                    loading(false);
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                    preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                    preferenceManager.putString(Constants.KEY_NAME, Username.getText().toString());
                    preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);
                    Intent intent = new Intent(SignUp.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(exception -> {
                    loading(false);
                    Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private String encodeImage(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth /bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight, false);
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayInputStream);
        byte []bytes =byteArrayInputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);

    }



    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if(result.getResultCode() ==RESULT_OK){
                    if(result.getData()!= null){
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream =getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            profilePic.setImageBitmap(bitmap);
                            addImage.setVisibility(View.GONE);
                            encodedImage = encodeImage(bitmap);

                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });




    // firebase
    private void SignupUser(String username, String email, String password, String repeatPassword, String encodedImage) {


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
                            ReadWriteUsersdetails WriteUserDetails = new  ReadWriteUsersdetails(username, encodedImage);

                            // registered user
                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.your_image);
                            String encodedImage = encodeImage(bitmap);

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
                            databaseReference.child("image").setValue(encodedImage)
                                    .addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(), "Изображение загружено", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show());

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


public class SignUp extends AppCompatActivity {
    private NewsignupBinding binding;
    private ImageView profilePic;
    private PreferenceManager preferenceManager;
    private String encodedImage;
    private TextView buyer, addImage;
    private EditText Username, Email, Password, Repeat_password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.newsignup);
        getSupportActionBar().hide();

        preferenceManager = new PreferenceManager(getApplicationContext());

        Toast.makeText(this, "You can sign up now", Toast.LENGTH_LONG).show();

        // Инициализация UI элементов
        addImage = findViewById(R.id.textAddImage);
        profilePic = findViewById(R.id.newImageProfile);
        buyer = findViewById(R.id.buyer_signup_page_buyer);
        Username = findViewById(R.id.editTextUsername);
        Email = findViewById(R.id.editTextEmail);
        Password = findViewById(R.id.editTextPassword);
        Repeat_password = findViewById(R.id.editTextPassword2);
        progressBar = findViewById(R.id.progressbarsignup);

        // Выбор изображения
        buyer.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });

        // Скрытие и показ пароля
        togglePasswordVisibility(Password, findViewById(R.id.signUpHideAndShow));
        togglePasswordVisibility(Repeat_password, findViewById(R.id.signUpHideAndShow2));

        Button Signup = findViewById(R.id.buttonSignUp);
        Signup.setOnClickListener(v -> validateAndSignUp());
    }

    // Метод для скрытия и показа пароля
    private void togglePasswordVisibility(EditText editText, ImageView toggleButton) {
        toggleButton.setImageResource(R.drawable.passwordhide);
        toggleButton.setOnClickListener(v -> {
            if (editText.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                toggleButton.setImageResource(R.drawable.passwordhide);
            } else {
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                toggleButton.setImageResource(R.drawable.passwordshow);
            }
        });
    }

    // Проверка полей перед регистрацией
    private void validateAndSignUp() {
        String image = encodedImage;
        String username = Username.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String repeat_password = Repeat_password.getText().toString().trim();

        if (username.isEmpty()) {
            Username.setError("Username is required");
            Username.requestFocus();
            return;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Valid email is required");
            Email.requestFocus();
            return;
        }

        if (password.isEmpty() || password.length() < 8) {
            Password.setError("Password must be at least 8 characters long");
            Password.requestFocus();
            return;
        }

        if (!password.equals(repeat_password)) {
            Repeat_password.setError("Passwords do not match");
            Repeat_password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        SignupUser(username, email, password, image);
    }

    // Firebase
    private void SignupUser(String username, String email, String password, String encodedImage) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();
                        firebaseUser.updateProfile(profileUpdate);

                        DatabaseReference referenceProfile = FirebaseDatabase.getInstance()
                                .getReference("Registered Users");

                        ReadWriteUsersdetails userDetails = new ReadWriteUsersdetails(username, encodedImage);
                        referenceProfile.child(firebaseUser.getUid()).setValue(userDetails)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {

                                        firebaseUser.sendEmailVerification()
                                                .addOnCompleteListener(task2 -> {
                                                    if (task2.isSuccessful()) {
                                                        Toast.makeText(SignUp.this, "User registered successfully. Please verify your email.", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(SignUp.this, "Failed to send verification email.", Toast.LENGTH_LONG).show();
                                                    }
                                                });


                                        Intent intent = new Intent(SignUp.this, UserProfileActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SignUp.this, "User registration failed. Please try again", Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                });

                    } else {
                        handleAuthException(task.getException());
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    // Обработка ошибок Firebase
    private void handleAuthException(Exception exception) {
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            Password.setError("Your password is too weak. Use a mix of alphabets, numbers, and special characters.");
            Password.requestFocus();
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            Email.setError("Your email is invalid or already in use. Please re-enter.");
            Email.requestFocus();
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            Email.setError("User is already registered with this email. Use another email.");
            Email.requestFocus();
        } else {
            Toast.makeText(SignUp.this, "Registration failed: " + exception.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Кодирование изображения в Base64
    private String encodeImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayInputStream);
        byte[] bytes = byteArrayInputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    // Выбор изображения из галереи
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        profilePic.setImageBitmap(bitmap);
                        addImage.setVisibility(View.GONE);
                        encodedImage = encodeImage(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
}
*/




import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
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
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.example.buyer.databinding.NewsignupBinding;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import BUYER.home_ads;
import BUYER.utilities.Constants;
import BUYER.utilities.PreferenceManager;

public class SignUp extends AppCompatActivity {
    private NewsignupBinding binding;
    private ImageView profilePic;
    private PreferenceManager preferenceManager;
    private String  encodedImage;
    private TextView buyer, addImage;
    private EditText Username, Email, Password, Repeat_password;
    private static final String TAG= "SignUpActivity";
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.newsignup);
        getSupportActionBar().hide();
        /*    binding = NewsignupBinding.inflate(getLayoutInflater());*/
        preferenceManager = new PreferenceManager(getApplicationContext());


        Toast.makeText(this, "You can sign up now",Toast.LENGTH_LONG ).show();

        // edittext
        addImage =findViewById(R.id.textAddImage);
        profilePic = findViewById(R.id.newImageProfile);
        buyer = findViewById(R.id.buyer_signup_page_buyer);
        Username = findViewById(R.id.editTextUsername);
        Email = findViewById(R.id.editTextEmail);
        Password = findViewById(R.id.editTextPassword);
        Repeat_password = findViewById(R.id.editTextPassword2);
        progressBar = findViewById(R.id.progressbarsignup);
        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pickImage.launch(intent);
            }
        });

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

                String image = encodedImage;
                String username = Username.getText().toString();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String repeat_password = Repeat_password.getText().toString();


                //is empty
                if(TextUtils.isEmpty(image)){
                    Toast.makeText(SignUp.this, "Select profile image", Toast.LENGTH_SHORT).show();
                    profilePic.requestFocus();
                }else if (TextUtils.isEmpty(username)){
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
                    SignupUser(username, email, password, repeat_password,image);
                    signUp();


                }


            }
        });


    }


    private void signUp(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME, Username.getText().toString());
        user.put(Constants.KEY_EMAIL, Email.getText().toString());
        user.put(Constants.KEY_PASSWORD, Password.getText().toString());
        user.put(Constants.KEY_IMAGE, encodedImage);
        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user).
                addOnSuccessListener(documentReference -> {
                    loading(false);
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                    preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                    preferenceManager.putString(Constants.KEY_NAME, Username.getText().toString());
                    preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);
                    Intent intent = new Intent(SignUp.this, home_ads.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(exception -> {
                    loading(false);
                    Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }



    private void loading(Boolean isLoading){
        if(isLoading){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private String encodeImage(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth /bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight, false);
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayInputStream);
        byte []bytes =byteArrayInputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);

    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if(result.getResultCode() ==RESULT_OK){
                    if(result.getData()!= null){
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream =getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            profilePic.setImageBitmap(bitmap);
                            addImage.setVisibility(View.GONE);
                            encodedImage = encodeImage(bitmap);

                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });




    // firebase
    private void SignupUser(String username, String email, String password, String repeatPassword, String encodedImage) {


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
                            ReadWriteUsersdetails WriteUserDetails = new  ReadWriteUsersdetails(username, encodedImage);
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
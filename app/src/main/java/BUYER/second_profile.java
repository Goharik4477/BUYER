package BUYER;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import BUYER.Navigation.HomeActivity;
import BUYER.SignInSignUp.ReadWriteUsersdetails;
import BUYER.SignInSignUp.SignIn_or_SignUp;
import BUYER.UserProfileSetting.ChangePasswordActivity;
import BUYER.UserProfileSetting.DeleteProfileActivity;
import BUYER.UserProfileSetting.UpdateEmailActivity;
import BUYER.UserProfileSetting.UpdateProfileActivity;

public class second_profile extends AppCompatActivity {

    private TextView TextViewWelcome, TextViewFFullName, TextViewEmail;
    private ProgressBar progressBar;
    private String fullName, email;
    private ImageView imageView;
    DatabaseReference ProductsRef;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second_profile);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_bottom_home) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_messenger) {
                startActivity(new Intent(getApplicationContext(), messenger.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_new_ad) {
                startActivity(new Intent(getApplicationContext(), add_new_ad.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_profile) {
                return true;
            }
            return false;
        });





        TextViewWelcome = findViewById(R.id.textView_show_welcome);
        TextViewFFullName = findViewById(R.id.show_full_name);
        TextViewEmail = findViewById(R.id.show_email);
        progressBar = findViewById(R.id.progressbaruser);



        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if (firebaseUser == null){
            Toast.makeText(this, "Something went wrong! User's details are not available at the moment",
                    Toast.LENGTH_LONG).show();
        }else {
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }
    }



    //coming to usersProfile activity
    private void checkIfEmailVerified(FirebaseUser firebaseUser) {
        if(!firebaseUser.isEmailVerified()){
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        //set up alert
        AlertDialog.Builder builder = new AlertDialog.Builder(second_profile.this);
        builder.setTitle("Email not verified ");
        builder.setMessage("Please verify your email now. You can not login without email verification next time. ");

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

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();


        //database for "registered user"

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUsersdetails readUsersdetails = snapshot.getValue(ReadWriteUsersdetails.class);
                if(readUsersdetails != null){
                    fullName = firebaseUser.getDisplayName();
                    email =firebaseUser.getEmail();
                    TextViewWelcome.setText(fullName);
                    TextViewFFullName.setText(fullName);
                    TextViewEmail.setText(email);
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(second_profile.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
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
            Intent intent = new Intent(second_profile.this, UpdateProfileActivity.class);
            startActivity(intent);
        }else if(id ==  R.id.menu_update_email){
            Intent intent = new Intent(second_profile.this, UpdateEmailActivity.class);
            startActivity(intent);
        }else if(id ==  R.id.menu_settings){
            Toast.makeText(this, "menu settings", Toast.LENGTH_SHORT).show();
        }else if(id ==  R.id.menu_change_password){
            Intent intent = new Intent(second_profile.this, ChangePasswordActivity.class);
            startActivity(intent);
        }else if(id ==  R.id.menu_delete_profile){
            Intent intent = new Intent(second_profile.this, DeleteProfileActivity.class);
            startActivity(intent);
        }else if(id ==  R.id.menu_logout){
            authProfile.signOut();
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(second_profile.this, SignIn_or_SignUp.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
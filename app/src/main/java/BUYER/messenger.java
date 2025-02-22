package BUYER;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.example.buyer.databinding.ActivityMessengerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import BUYER.Navigation.HomeActivity;
import BUYER.SignInSignUp.ReadWriteUsersdetails;
import BUYER.adapters.UsersActivity;
import BUYER.utilities.Constants;
import BUYER.utilities.PreferenceManager;

public class messenger extends AppCompatActivity {
private ActivityMessengerBinding binding;
private  Button newChat;
private DatabaseReference databaseReference;
private ImageView profilePic;
private TextView textName;
    private String fullName;
    private PreferenceManager preferenceManager;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivityMessengerBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setContentView(binding.getRoot());


        //navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_messenger);
        getSupportActionBar().hide();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_bottom_home) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_messenger) {
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_new_ad) {
                startActivity(new Intent(getApplicationContext(), add_new_ad.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_profile) {
                startActivity(new Intent(getApplicationContext(), second_profile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });


        //navigation end

       binding.fabNewChat.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UsersActivity.class)));
        textName = findViewById(R.id.textName);
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        showUserProfile(firebaseUser);
      //  loadUserDetails();

    }

  /*  private void loadUserDetails(){
       textName.setText(preferenceManager.getString(Constants.KEY_NAME));
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
       profilePic.setImageBitmap(bitmap);
    }*/

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
                    textName.setText(fullName);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(messenger.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            }
        });
    }
}
package com.example.buyer.BUYER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buyer.BUYER.ModelForMess.MUser;
import com.example.buyer.BUYER.utilities.Constants;
import com.example.buyer.BUYER.utilities.PreferenceManager;
import com.example.buyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

public class Messenger2 extends AppCompatActivity {
    RoundedImageView profile_image;
    TextView username;
    private PreferenceManager preferenceManager;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_messanger2);
      getSupportActionBar().hide();
        preferenceManager = new PreferenceManager(getApplicationContext());
      profile_image= findViewById(R.id.imageProfile2);
      username = findViewById(R.id.textName2);
      firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
      reference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
      reference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              MUser user = dataSnapshot.getValue(MUser.class);
              username.setText(user.getUsername());
              loadUserDetails();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });
    }


    private void loadUserDetails(){
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
       profile_image.setImageBitmap(bitmap);
    }
}
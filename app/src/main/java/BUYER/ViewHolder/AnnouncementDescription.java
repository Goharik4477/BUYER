package BUYER.ViewHolder;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buyer.R;
import com.example.buyer.SearchActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import BUYER.ChatActivity;
import BUYER.Model.Post;
import BUYER.SignInSignUp.SignIn;
import BUYER.SignInSignUp.UserNot;
import BUYER.home_ads;
import BUYER.models.User;
import BUYER.utilities.Constants;
import BUYER.utilities.PreferenceManager;

public class AnnouncementDescription extends AppCompatActivity {
    private String FirstCountryNew, SecondCountyNew, DescriptionNew, addressNew, priceNew,PriceNewService, linkNew;
    private EditText Category, FirstCountry, SecondCounty, Description, address, price, link, priceForService;
    private Button Public;
    private PreferenceManager preferenceManager;
    FirebaseDatabase database;
    ImageView imageView;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_announcement_description);
        getSupportActionBar().hide();
        preferenceManager = new PreferenceManager(getApplicationContext());
        Category = findViewById(R.id.Category);
        FirstCountry = findViewById(R.id.firstCounty);
        SecondCounty = findViewById(R.id.SecondCountry);
        Description = findViewById(R.id.Description);
        address = findViewById(R.id.address);
        price = findViewById(R.id.price);
        link = findViewById(R.id.link);
        Public = findViewById(R.id.buttonPublic);
        priceForService = findViewById(R.id.priceForService);
        database = FirebaseDatabase.getInstance();

        if (user == null) {
            Log.e("AuthError", "User is not authenticated.");
            Intent intent = new Intent(AnnouncementDescription.this, SignIn.class);
            startActivity(intent);
            finish();
            return;
        } else {
            String categoryName = getIntent().getExtras().get("Category").toString();
            Toast.makeText(this, "Select Category " + categoryName, Toast.LENGTH_SHORT).show();
            Category.setText("Category: " + categoryName);


        }


        Public.setOnClickListener(v -> {
            ValidateProductData();
            Post post = new Post();
            post.setFirsCountry(FirstCountryNew);
            post.setSecondCountry(SecondCountyNew);
            post.setPostDescription(DescriptionNew);
            post.setAddress(addressNew);
            post.setPrice(priceNew);
            post.setLink(linkNew);
            post.setCategory(Category.getText().toString());
            post.setUsername(preferenceManager.getString(Constants.KEY_NAME));
            post.setPriceForService(PriceNewService);

            post.setPostedBy(FirebaseAuth.getInstance().getUid());
            post.setPostedAt(new Date().getTime());

            // Публикация в Firebase
            database.getReference().child("posts").push().setValue(post)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(AnnouncementDescription.this, "Posted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AnnouncementDescription.this, home_ads.class));
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AnnouncementDescription.this, "Failed to post: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void ValidateProductData() {
        DescriptionNew = Description.getText().toString();
        FirstCountryNew = FirstCountry.getText().toString();
        SecondCountyNew = SecondCounty.getText().toString();
        addressNew = address.getText().toString();
        priceNew = price.getText().toString();
        linkNew = link.getText().toString();
        PriceNewService = priceForService.getText().toString();


        if (TextUtils.isEmpty(DescriptionNew)) {
            Toast.makeText(this, "Add a description", Toast.LENGTH_SHORT).show();
            Description.setError("Add a description");
            Description.requestFocus();
            return;
        } else if (TextUtils.isEmpty(FirstCountryNew)) {
            Toast.makeText(this, "Add a country", Toast.LENGTH_SHORT).show();
            FirstCountry.setError("Add a country");
            FirstCountry.requestFocus();
            return;
        } else if (TextUtils.isEmpty(SecondCountyNew)) {
            Toast.makeText(this, "Add a country", Toast.LENGTH_SHORT).show();
            SecondCounty.setError("Add a country");
            SecondCounty.requestFocus();
            return;
        } else if (TextUtils.isEmpty(addressNew)) {
            Toast.makeText(this, "Add an address", Toast.LENGTH_SHORT).show();
            address.setError("Add an address");
            address.requestFocus();
            return;
        } else if (TextUtils.isEmpty(priceNew)) {
            Toast.makeText(this, "Add a price", Toast.LENGTH_SHORT).show();
            price.setError("Add a price");
            price.requestFocus();
            return;
        } else if (TextUtils.isEmpty(linkNew)) {
            Toast.makeText(this, "Add a link", Toast.LENGTH_SHORT).show();
            link.setError("Add a link");
            link.requestFocus();
            return;
        } else if (TextUtils.isEmpty(PriceNewService)) {
            Toast.makeText(this, "Add a price for service", Toast.LENGTH_SHORT).show();
            priceForService.setError("Add a price");
            priceForService.requestFocus();
            return;
    }
    }

}





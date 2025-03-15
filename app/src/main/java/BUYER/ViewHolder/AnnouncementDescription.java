package BUYER.ViewHolder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import BUYER.Navigation.HomeActivity;

public class AnnouncementDescription extends AppCompatActivity {
   private String FirstCountryNew,SecondCountyNew, DescriptionNew, addressNew, priceNew,linkNew, saveCurrentTime, saveCurrentDate, productRandomKey;

   private EditText Category, FirstCountry,SecondCounty, Description, address, price,link;
  private  Button Public;
private String categoryName;

private DatabaseReference ProductsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_announcement_description);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();

        Category = findViewById(R.id.Category);
        FirstCountry = findViewById(R.id.firstCounty);
        SecondCounty = findViewById(R.id.SecondCountry);
        Description =findViewById(R.id.Description);
        address = findViewById(R.id.address);
        price= findViewById(R.id.price);
        link = findViewById(R.id.link);
        Public = findViewById(R.id.buttonPublic);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        categoryName = getIntent().getExtras().get("Category").toString();
        Toast.makeText(this, "Select Category " + categoryName, Toast.LENGTH_SHORT).show();
 Category.setText("Category: "+ categoryName);
     Public.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             ValidateProductData();

         }
     });


    }
    private void ValidateProductData(){
        DescriptionNew = Description.getText().toString();
        FirstCountryNew = FirstCountry.getText().toString();
        SecondCountyNew = SecondCounty.getText().toString();
        addressNew= address.getText().toString();
        priceNew =price.getText().toString();
        linkNew = link.getText().toString();


        if(TextUtils.isEmpty(DescriptionNew)){
            Toast.makeText(this, "Add a description", Toast.LENGTH_SHORT).show();
            Description.setError("Add a description");
            Description.requestFocus();
        } else if (TextUtils.isEmpty(FirstCountryNew)) {
            Toast.makeText(this, "Add a country", Toast.LENGTH_SHORT).show();
            FirstCountry.setError("Add a country");
            FirstCountry.requestFocus();
        }else if (TextUtils.isEmpty(SecondCountyNew)) {
            Toast.makeText(this, "Add a country", Toast.LENGTH_SHORT).show();
           SecondCounty.setError("Add a country");
         SecondCounty.requestFocus();
        }else if (TextUtils.isEmpty(addressNew)) {
            Toast.makeText(this, "Add a address", Toast.LENGTH_SHORT).show();
            address.setError("Add a address");
            address.requestFocus();
        }else if (TextUtils.isEmpty(priceNew)) {
            Toast.makeText(this, "Add a price", Toast.LENGTH_SHORT).show();
            price.setError("Add a price");
            price.requestFocus();
        }else if (TextUtils.isEmpty(linkNew)) {
            Toast.makeText(this, "Add a link", Toast.LENGTH_SHORT).show();
            link.setError("Add a link");
            link.requestFocus();
        }else{
            storeProductInfo();
        }

    }

    private void storeProductInfo() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + "_" + saveCurrentTime;

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(this, "User is not logged in!", Toast.LENGTH_SHORT).show();
            return;
        }


        SaveProduct saveProduct = new SaveProduct(
                FirstCountryNew, SecondCountyNew, DescriptionNew,
                addressNew, priceNew, linkNew, saveCurrentTime, saveCurrentDate, productRandomKey
        );

        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference("Products");
        productsRef.child(firebaseUser.getUid()).push().setValue(saveProduct)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "The ad is posted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, HomeActivity.class));
                        finish();
                    } else {
                        Log.e("FirebaseError", "Error saving product", task.getException());
                        Toast.makeText(this, "Failed to post the ad. Try again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

 /*private void saveProductInfoToDatabase() {
        if (TextUtils.isEmpty(DescriptionNew) || TextUtils.isEmpty(priceNew)) {
            Toast.makeText(this, "Please fill in all required fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting your ad...");
        progressDialog.show();

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("productID", productRandomKey);
        productMap.put("productDate", saveCurrentDate);
        productMap.put("productTime", saveCurrentTime);
        productMap.put("productDescription", DescriptionNew);
        productMap.put("productFirsCountry", FirstCountryNew);
        productMap.put("productSecondCountry", SecondCountyNew);
        productMap.put("productAddress", addressNew);
        productMap.put("productPrice", priceNew);
        productMap.put("productLink", linkNew);

        ProductsRef.child(productRandomKey).setValue(productMap).addOnCompleteListener(task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                Toast.makeText(this, "The ad is posted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Home.class));
                finish();
            } else {
                Log.e("FirebaseError", "Error saving product", task.getException());
                Toast.makeText(this, "Failed to post the ad. Try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
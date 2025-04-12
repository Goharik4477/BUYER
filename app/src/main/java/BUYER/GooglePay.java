package BUYER;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.example.buyer.databinding.ActivityGooglePayBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import BUYER.SignInSignUp.ReadWriteUsersdetails;

public class GooglePay extends AppCompatActivity {
private ActivityGooglePayBinding binding;

  public static final String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
String amount, name, upiId, transactionNote = "test", status;
    FirebaseDatabase database;
    private FirebaseAuth authProfile;
Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_google_pay);
        database = FirebaseDatabase.getInstance();
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        if (firebaseUser == null){
            Toast.makeText(this, "Something went wrong! User's details are not available at the moment",
                    Toast.LENGTH_LONG).show();
        }else {

            showUserProfile(firebaseUser);

        }


        binding.googlePayButton.setOnClickListener(new View.OnClickListener() {
             @Override
                     public void onClick(View v) {
                         amount = binding.amountEditText.getText().toString();
              if(!amount.isEmpty()){
            uri = getUpiPaymentUri(name, upiId, transactionNote, amount);
            payWithGPay();
            
        }
    }


        });

    }
    private void payWithGPay() {
if (isAppInstalled(this, GOOGLE_PAY_PACKAGE_NAME)){
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(uri);
    intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
    startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
}else{
    Toast.makeText(this, "Please install GPay", Toast.LENGTH_SHORT).show();
}

    }


    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            status = data.getStringExtra("Status").toLowerCase();

        }

//        if ((RESULT_OK = resultCode) && status.equals("success")){
//            Toast.makeText(this, "Transaction successful", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(this, "Transaction Failed", Toast.LENGTH_SHORT).show();
//        }
    }

    private static boolean isAppInstalled(Context context, String PackageName) {
        try {
            context.getPackageManager().getApplicationInfo(PackageName, 0);
            return true;
        }catch (PackageManager.NameNotFoundException e){
return false ;
        }
    }


    private static Uri getUpiPaymentUri(String name, String upiId, String transactionNote, String amount){
        return new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", transactionNote)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();
    }



    //user


    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();


        //database for "registered user"

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUsersdetails readUsersdetails = snapshot.getValue(ReadWriteUsersdetails.class);
                if(readUsersdetails != null){
                    name = firebaseUser.getDisplayName();
                    upiId =firebaseUser.getEmail();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GooglePay.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            }
        });
    }
}
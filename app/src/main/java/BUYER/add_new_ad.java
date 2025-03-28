package BUYER;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import BUYER.ViewHolder.RulesForPublishingAnnouncements;

public class add_new_ad extends AppCompatActivity {
    Button add;
    private Button submitButton;
    private EditText phone_txt;
    private FirebaseAuth mAuth;
    private CountryCodePicker ccp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_ad);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_new_ad);
        getSupportActionBar().hide();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_bottom_home) {
                startActivity(new Intent(getApplicationContext(), home_ads.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_messenger) {
                startActivity(new Intent(getApplicationContext(), messenger.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_new_ad) {
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_profile) {
                startActivity(new Intent(getApplicationContext(), second_profile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }   else if (item.getItemId() == R.id.menu_bottom_notification) {
                startActivity(new Intent(getApplicationContext(), notifications.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;}
            return false;
        });
        mAuth = FirebaseAuth.getInstance();
        submitButton = findViewById(R.id.submit_btn);
        phone_txt = findViewById(R.id.phone_no);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phone_txt);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(phone_txt.getText().toString().trim())){
                    Toast.makeText(add_new_ad.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                } else {
                    String  getNo =ccp.getFullNumberWithPlus().replace(" ", "");
                    Authentication(getNo);
                }
            }
        });




        add = findViewById(R.id.add_new);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(add_new_ad.this, RulesForPublishingAnnouncements.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void Authentication(String no){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                no, 60, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(add_new_ad.this, "Completed", Toast.LENGTH_SHORT).show();
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(add_new_ad.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(), RulesForPublishingAnnouncements.class);
                            startActivity(intent);
                            Toast.makeText(add_new_ad.this, "Successfully", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(add_new_ad.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }

}
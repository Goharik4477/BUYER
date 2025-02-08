package BUYER;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buyer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class add_new_ad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_ad);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_new_ad);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_bottom_home) {
                startActivity(new Intent(getApplicationContext(), Home_for_ad.class));
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
            }
            return false;
        });

    }
}
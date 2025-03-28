package BUYER;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.R;
import com.example.buyer.databinding.ActivityHomeAdsBinding;
import com.example.buyer.databinding.ActivityMessengerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import BUYER.Adapter.DashboardAdapter;
import BUYER.Model.DashboardModel;
import BUYER.utilities.Constants;
import BUYER.utilities.PreferenceManager;

public class home_ads extends AppCompatActivity {
    private ActivityHomeAdsBinding binding;
RecyclerView dashboardRV;
ArrayList<DashboardModel> dashboardList;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeAdsBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_bottom_home) {
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_messenger) {
                startActivity(new Intent(getApplicationContext(), messenger.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_new_ad) {
                startActivity(new Intent(getApplicationContext(), add_new_ad.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();}
            else if (item.getItemId() == R.id.menu_bottom_notification) {
                startActivity(new Intent(getApplicationContext(), notifications.class));
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

        loadUserDetails();

        dashboardRV =findViewById(R.id.dashboard);
        dashboardList = new ArrayList<>();

        dashboardList.add(new DashboardModel(R.drawable.ic_profile, "anna", "vrastan", "hayastan", "250", "no", "ok", "oop"));
        dashboardList.add(new DashboardModel(R.drawable.ic_profile, "kpl", "vrastan", "hayadfghhjkjkgfdjhkskihgfghkjuilhgfdhjukilhgfdghjkiloutfdghjklioufdfghjklioiugyftdghjykuiloiyfdghjkiou" +
                "dfghjkgfgcghkjljhgfghgkjl;okhgfhgkjlogfhgkjl;pgfghjklo" +
                "dfghjkilokhgfdghjk" +
                "ggnvjhgfdsstan", "250", "no", "ok", "oop"));
        dashboardList.add(new DashboardModel(R.drawable.ic_profile, "dtg", "vrastan", "hayastan", "250", "no", "ok", "oop"));
        dashboardList.add(new DashboardModel(R.drawable.ic_profile, "aghnna", "vrastan", "hayastan", "250", "no", "ok", "oop"));
        dashboardList.add(new DashboardModel(R.drawable.ic_profile, "anhgna", "vrastan", "hayastan", "250", "no", "ok", "oop"));
        DashboardAdapter dashboardAdapter = new DashboardAdapter(dashboardList, getApplicationContext());
        LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        dashboardRV.setLayoutManager(layoutManager);
        dashboardRV.setNestedScrollingEnabled(false);
        dashboardRV.setAdapter(dashboardAdapter);
    }

    private void loadUserDetails(){
        binding.textName.setText(preferenceManager.getString(Constants.KEY_NAME));
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);
    }
}
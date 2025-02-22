package BUYER.adapters;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buyer.R;
import com.example.buyer.databinding.ActivityUsersBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class UsersActivity extends AppCompatActivity {
private ActivityUsersBinding binding;
   private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
       // preferenceManager = new PreferenceManager(getApplicationContext());
        getSupportActionBar().hide();
    }

    private void getUsers(){
        loading((true));
        FirebaseFirestore database = FirebaseFirestore.getInstance();
      //  database.collection(SyncStateContract.Constants.)
    }
    private void showErrorMessage(){
        binding.textErrorMessage.setText(String.format("%s", "No user available"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }
    private void loading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
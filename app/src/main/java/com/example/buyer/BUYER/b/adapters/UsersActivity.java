package com.example.buyer.BUYER.b.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.BUYER.b.BaseActivity;
import com.example.buyer.BUYER.b.ChatActivity;
import com.example.buyer.BUYER.b.listeners.UserListener;
import com.example.buyer.BUYER.b.messenger;
import com.example.buyer.BUYER.b.models.User;
import com.example.buyer.BUYER.b.utilities.Constants;
import com.example.buyer.BUYER.b.utilities.PreferenceManager;
import com.example.buyer.R;
import com.example.buyer.databinding.ActivityUsersBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class UsersActivity extends BaseActivity implements UserListener {
private ActivityUsersBinding binding;

private RecyclerView recyclerView;
private UserAdapter userAdapter;
private List<User> mUsers;
 private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());

        getSupportActionBar().hide();


        binding.ImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersActivity.this, messenger.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.usersRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mUsers = new ArrayList<>();









        getUsers();
    }





    private void getUsers(){
        loading((true));
        FirebaseFirestore database = FirebaseFirestore.getInstance();
       database.collection(Constants.KEY_COLLECTION_USERS).get().addOnCompleteListener(task -> {
           loading(false);
           String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
           if(task.isSuccessful() && task.getResult() != null){
               List<User> users =new ArrayList<>();
               for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                   if(currentUserId.equals(queryDocumentSnapshot.getId())){
                       continue;
                   }
                   User user = new User();
                   user.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                   user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                   user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                   user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                   user.id = queryDocumentSnapshot.getId();
                   users.add(user);

               }
               if(users.size() > 0){
                   UserAdapter userAdapter = new UserAdapter(users, this);
                   binding.usersRecyclerView.setAdapter(userAdapter);
                   binding.usersRecyclerView.setVisibility(View.VISIBLE);
               }else{
                   showErrorMessage();
               }
           }else {
               showErrorMessage();

           }
       });
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

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
        finish();
    }
}
package com.example.buyer.BUYER.b;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buyer.BUYER.b.Model.Post;
import com.example.buyer.BUYER.b.adapters.SearchActivity;
import com.example.buyer.R;
import com.example.buyer.databinding.ActivityPostDetailBinding;
import com.google.firebase.auth.FirebaseAuth;

public class PostDetailActivity extends AppCompatActivity {

    ActivityPostDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        Post post = (Post) getIntent().getSerializableExtra("post");

        if (post != null) {
            binding.textUsername.setText(post.getUsername());
            binding.textDescription.setText(post.getPostDescription());
            binding.destination.setText(post.getFirsCountry());
            binding.Origin.setText(post.getSecondCountry());
            binding.textServicePrice.setText(post.getPriceForService() + " $");
            binding.textProductPrice.setText(post.getPrice() + " $");
            binding.textCategory.setText(post.getCategory());
            binding.postEmail.setText(post.getPostedBy());

            binding.textWeight.setText(post.getWeight() + " kg");
            binding.textUntil.setText(post.getUntil());
            binding.textAddress.setText(post.getAddress());
            binding.textLink.setText(post.getLink());

            String bind = "this";

            String postedByEmail = post.getPostedBy();

            if(postedByEmail != null && !postedByEmail.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                binding.newChat.setVisibility(View.VISIBLE);

                binding.back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PostDetailActivity.this, home_ads.class);

                        startActivity(intent);
                        finish();
                    }
                });

            }


            binding.newChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        Intent intent = new Intent(PostDetailActivity.this, SearchActivity.class);
                        intent.putExtra("postedByEmail", postedByEmail);
                        startActivity(intent);


                }
            });
        }
    }
}

package com.example.buyer.BUYER.b.SignInSignUp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.buyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.buyer.BUYER.b.home_ads;



public class MainActivity extends AppCompatActivity {
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;

    private ConstraintLayout mainLayout;
    private ImageView bigBag;
    private ImageView smallBag;
    private TextView buyerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        mainLayout = findViewById(R.id.main);
        bigBag = findViewById(R.id.bigBag);
        smallBag = findViewById(R.id.smallBag);
        buyerText = findViewById(R.id.buyerText);

        smallBag.setVisibility(View.GONE);
        buyerText.setVisibility(View.GONE);
        buyerText.setAlpha(0f);

        mainLayout.post(this::startAnimationSequence);
    }

    private void startAnimationSequence() {
        final int startSize = (int) dpToPx(200);
        final int endWidth = (int) (mainLayout.getWidth() * 25.0);
        final int endHeight = (int) (mainLayout.getHeight() * 10.0);

        bigBag.getLayoutParams().width = startSize;
        bigBag.getLayoutParams().height = startSize;
        bigBag.requestLayout();

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(2500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addUpdateListener(animation -> {
            float fraction = animation.getAnimatedFraction();

            int newWidth = (int) (startSize + fraction * (endWidth - startSize));
            int newHeight = (int) (startSize + fraction * (endHeight - startSize));

            bigBag.getLayoutParams().width = newWidth;
            bigBag.getLayoutParams().height = newHeight;
            bigBag.requestLayout();
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                smallBag.setVisibility(View.VISIBLE);
                smallBag.setTranslationX(0f);

                smallBag.animate()
                        .translationX(-dpToPx(90))
                        .setDuration(600)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .withEndAction(() -> {
                            buyerText.setVisibility(View.VISIBLE);
                            buyerText.animate()
                                    .alpha(1f)
                                    .translationX(-dpToPx(90))
                                    .setDuration(600)
                                    .withEndAction(() -> {

                                        new Handler().postDelayed(() -> {
                                            if (firebaseUser == null) {
                                                startActivity(new Intent(MainActivity.this, SignIn_or_SignUp.class));
                                            } else {
                                                startActivity(new Intent(MainActivity.this, home_ads.class));
                                            }
                                            finish();
                                        }, 3000);
                                    })
                                    .start();
                        })
                        .start();
            }
        });

        animator.start();
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}

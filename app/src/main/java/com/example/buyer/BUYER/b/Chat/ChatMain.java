package com.example.buyer.BUYER.b.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.buyer.BUYER.b.Chat.Menu.CallsFragment;
import com.example.buyer.BUYER.b.Chat.Menu.ChatFragment;
import com.example.buyer.BUYER.b.Chat.Menu.StatusFragment;
import com.example.buyer.BUYER.b.add_new_ad;
import com.example.buyer.BUYER.b.home_ads;
import com.example.buyer.BUYER.b.second_profile;
import com.example.buyer.R;
import com.example.buyer.databinding.ActivityChatMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ChatMain extends AppCompatActivity {


    ActivityChatMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChatMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_messenger);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_bottom_home) {
                startActivity(new Intent(getApplicationContext(), home_ads.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_bottom_messenger) {

                return true;
            } else if (item.getItemId() == R.id.menu_bottom_new_ad) {
                startActivity(new Intent(getApplicationContext(), add_new_ad.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            } else if (item.getItemId() == R.id.menu_bottom_profile) {
                startActivity(new Intent(getApplicationContext(), second_profile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });




            setUpWithViewPager(binding.viewPager);
            binding.tabLayout.setupWithViewPager(binding.viewPager);
            binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    ChaneFabIcon(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });





    }


    private void ChaneFabIcon(final int index){
        binding.fabAction.hide();

new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        switch (index){
            case 0 : binding.fabAction.setImageDrawable(getDrawable(R.drawable.outline_add_comment_24)); break;
            case 1 : binding.fabAction.setImageDrawable(getDrawable(R.drawable.outline_add_a_photo_24 )); break;
            case 2 : binding.fabAction.setImageDrawable(getDrawable(R.drawable.outline_add_ic_call_24)); break;

        }
        binding.fabAction.show();
    }
}, 400);

    }


    private void setUpWithViewPager(ViewPager viewPager){

        ChatMain.SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new ChatFragment(), "Chats");
        adapter.addFragment(new StatusFragment(), "Status");
        adapter.addFragment(new CallsFragment(), "Calls");


        viewPager.setAdapter(adapter);

    }

    private static class SectionsPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String>  mFragmentTitleList= new ArrayList<>();

        public SectionsPagerAdapter(@NonNull FragmentManager manager) {
            super(manager);
        }



        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

//
//        switch (id){
//            case R.id.menu_search_new :  Toast.makeText(this, "Action Search", Toast.LENGTH_LONG).show(); break;
//            case R.id.menu_more :   Toast.makeText(this, "Action More", Toast.LENGTH_LONG).show(); break;
//        }
//
        if (id == R.id.menu_search_new){
            Toast.makeText(this, "Action Search", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.menu_more) {
            Toast.makeText(this, "Action More", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
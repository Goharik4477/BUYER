package com.example.buyer.BUYER.ViewHolder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buyer.R;

public class SelectCategory extends AppCompatActivity {

    String categoryList[] = {"Shoes","Clothes","For children","Home","Beauty","Accessories","Electronics","Toys","Products","Household appliances","Pet supplies",
            "Sport","Automotive goods","Books","Jewelry","For repair","Garden","Health","Stationery"};
    int categoryImages[]= {R.drawable.shouse,R.drawable.clo,R.drawable.baby,R.drawable.kastrulya,R.drawable.cosmetics,R.drawable.umbrella,R.drawable.phone,R.drawable.utka,
            R.drawable.eat,R.drawable.chaynik,R.drawable.dog,R.drawable.ball,R.drawable.gayka,R.drawable.book,
            R.drawable.ring,R.drawable.drel,R.drawable.lopatka,R.drawable.heart,R.drawable.pen,
    };
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();

        listView = (ListView) findViewById(R.id.customListView);
        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), categoryList,categoryImages);
        listView.setAdapter(customBaseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SelectCategory.this, AnnouncementDescription.class);
                intent.putExtra("Category", categoryList[position]);
                startActivity(intent);

            }
        });
    }
}
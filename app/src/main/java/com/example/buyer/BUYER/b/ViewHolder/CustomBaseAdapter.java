package com.example.buyer.BUYER.b.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buyer.R;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    String categoryList[];
    int categoryImages[];
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, String []categoryList, int []categoryImages){
        this.context = ctx;
        this.categoryList = categoryList;
        this.categoryImages =categoryImages;
        this.inflater = LayoutInflater.from(ctx);

    }


    @Override
    public int getCount() {
        return categoryList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_custom_list_view,null);
        TextView CtTxtView = (TextView) convertView.findViewById(R.id.categoryTextView);
        ImageView ctImage = (ImageView) convertView.findViewById(R.id.categoryImageIcon);
        CtTxtView.setText(categoryList[position]);
        ctImage.setImageResource(categoryImages[position]);
        return convertView;
    }
}

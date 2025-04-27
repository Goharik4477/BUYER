package com.example.buyer.BUYER.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.R;

import com.example.buyer.BUYER.Interface.ItemClickListener;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView ProductCountry, BuyerCountry, ProductDescription, ProductAddress, ProductLink, ProductPrice;
    public ItemClickListener listner;
    public ProductViewHolder(View itemView) {
        super(itemView);


        ProductCountry = itemView.findViewById(R.id.FCounty);
        BuyerCountry = itemView.findViewById(R.id.SCountry);
        ProductDescription = itemView.findViewById(R.id.ProductDescription);
        ProductAddress = itemView.findViewById(R.id.productAddress);
        ProductLink = itemView.findViewById(R.id.productLink);
        ProductPrice = itemView.findViewById(R.id.ProductPrice);
    }
    public void setItemClickListener(ItemClickListener listener)
    {
        this.listner = listener;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}


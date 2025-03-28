package BUYER.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.R;

import java.util.ArrayList;

import BUYER.Model.DashboardModel;

public class DashboardAdapter  extends RecyclerView.Adapter<DashboardAdapter.viewHolder>{

    ArrayList<DashboardModel> list;
    Context context;

    public DashboardAdapter(ArrayList<DashboardModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_rv_sample, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        DashboardModel model = list.get(position);
        holder.profileImg.setImageResource(model.getProfile());
        holder.name.setText(model.getName());
        holder.FirstCountry.setText(model.getFirstCountry());
        holder.SecondCountry.setText(model.getSecondCountry());
        holder.Price.setText(model.getPrice());
        holder.About.setText(model.getAbout());
        holder.Link.setText(model.getLink());
        holder.address.setText(model.getAddress());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView profileImg;
        TextView  name,  FirstCountry, SecondCountry, Price, About, Link, address;
        public viewHolder(@NonNull View itemView){
            super(itemView);

            profileImg = itemView.findViewById(R.id.ImageIconDash);
            name = itemView.findViewById(R.id.textNameDash);
            FirstCountry = itemView.findViewById(R.id.FCounty);
           SecondCountry = itemView.findViewById(R.id.SCountry);
           Price = itemView.findViewById(R.id.ProductPrice);
           About = itemView.findViewById(R.id.ProductDescription);
           Link =itemView.findViewById(R.id.productLink);
           address = itemView.findViewById(R.id.productAddress);


        }
    }
}

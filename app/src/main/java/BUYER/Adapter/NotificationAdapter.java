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

import BUYER.Model.NotificationModel;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.viewHolder>{

    ArrayList<NotificationModel> list;
    Context context;

    public NotificationAdapter(ArrayList<NotificationModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.notification2sample,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        NotificationModel model = list.get(position);
        holder.profile.setImageResource(model.getProfile());
        holder.notification.setText(model.getNotification());
        holder.time.setText(model.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
ImageView profile;
TextView notification, time;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            profile =itemView.findViewById(R.id.imageProfileNotification);
            notification = itemView.findViewById(R.id.textNotification);
            time = itemView.findViewById(R.id.textTimeNotification);

        }
    }
}

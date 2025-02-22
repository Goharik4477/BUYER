package BUYER.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyer.databinding.ItemConteinerUserBinding;
import com.google.firebase.firestore.auth.User;

import java.util.List;

import BUYER.SignInSignUp.ReadWriteUsersdetails;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<ReadWriteUsersdetails> users;

    public UserAdapter(List<ReadWriteUsersdetails> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemConteinerUserBinding itemConteinerUserBinding =ItemConteinerUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent, false
        );
        return new UserViewHolder(itemConteinerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {

        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        ItemConteinerUserBinding binding;

        UserViewHolder(ItemConteinerUserBinding itemConteinerUserBinding){
            super(itemConteinerUserBinding.getRoot());
            binding = itemConteinerUserBinding;
        }
         void  setUserData (ReadWriteUsersdetails user){


         }
    }
}

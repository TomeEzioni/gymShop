package com.example.gymshop.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymshop.R;
import com.example.gymshop.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>
{

    private List<User> userList;

    // Constructor
    public UsersAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText(user.getfName());
        holder.ageTextView.setText(String.valueOf(user.getPassword()));
        holder.emailTextView.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, ageTextView, emailTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            ageTextView = itemView.findViewById(R.id.ageTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
        }
    }
}

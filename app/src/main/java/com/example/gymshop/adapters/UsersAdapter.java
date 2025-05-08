package com.example.gymshop.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymshop.R;
import com.example.gymshop.models.User;
import com.example.gymshop.screens.ShowUsers;
import com.example.gymshop.screens.User_item;
import com.example.gymshop.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    public interface OnDeleteClickListener {
        void onDelete(User user);   // קריאה חזרה למחיקה
    }

    private List<User> userList;
    private final OnDeleteClickListener deleteListener;

    public UsersAdapter(List<User> userList,
                       OnDeleteClickListener deleteListener) {
        this.userList = userList;
        this.deleteListener = deleteListener;
    }

    // מאפשר לסנן/לעדכן מבחוץ
    public void setFilteredList(List<User> newList) {
        this.userList = newList;
        notifyDataSetChanged();
    }

    // =========== ViewHolder =========== //
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvFname, tvLname, tvPhone;
        ImageButton btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFname   = itemView.findViewById(R.id.FirstNamTextView);
            tvLname   = itemView.findViewById(R.id.lastNameTextView);
            tvPhone   = itemView.findViewById(R.id.PhoneTextView);
            btnDelete = itemView.findViewById(R.id.imageBDeleteUser);
        }
    }

    // ====== Adapter overrides ====== //
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_user_item, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.tvFname.setText(user.getfName());
        holder.tvLname.setText(user.getlName());
        holder.tvPhone.setText(user.getPhone());

        holder.btnDelete.setOnClickListener(v -> deleteListener.onDelete(user));
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }
}


package com.example.gymshop.screens;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymshop.R;
import com.example.gymshop.adapters.UsersAdapter;
import com.example.gymshop.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class User_item extends AppCompatActivity {

    RecyclerView recyclerView;
    UsersAdapter usersAdapter;
    List<User> userList;
    FirebaseFirestore firestore;
    ImageButton ibDelete;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_item);

        recyclerView = findViewById(R.id.UsersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ibDelete = findViewById(R.id.imageBDeleteUser);

        // יצירת רשימה של משתמשים
        firestore = FirebaseFirestore.getInstance();
        userList = new ArrayList<>();

    }
}
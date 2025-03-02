package com.example.gymshop.screens;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class User_item extends AppCompatActivity {

    RecyclerView recyclerView;
    UsersAdapter usersAdapter;
    List<User> userList;
    FirebaseFirestore firestore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_item);

        recyclerView = findViewById(R.id.UsersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // יצירת רשימה של משתמשים
        firestore = FirebaseFirestore.getInstance();
        userList = new ArrayList<>();

        // שליפת נתונים מ-Firebase
        firestore.collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots ->
                {
                    if (!queryDocumentSnapshots.isEmpty())
                    {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots)
                        {
                            User user = document.toObject(User.class);
                            userList.add(user);
                        }
                        usersAdapter = new UsersAdapter(userList);
                        recyclerView.setAdapter(usersAdapter);
                    }
                    else
                    {
                        Toast.makeText(User_item.this, "No users found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                {
                    Toast.makeText(User_item.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
package com.example.gymshop.screens;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymshop.R;
import com.example.gymshop.adapters.UsersAdapter;
import com.example.gymshop.models.User;
import com.example.gymshop.services.AuthenticationService;
import com.example.gymshop.services.DatabaseService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShowUsers extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsersAdapter usersAdapter;
    private DatabaseService databaseService;
    private DatabaseReference myRef;
    private SearchView userSearchView;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }

        databaseService=DatabaseService.getInstance();

        // קישור רכיבים מה-XML
        userSearchView = findViewById(R.id.searchVUsers);
        recyclerView = findViewById(R.id.UsersRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        userList = new ArrayList<>();
        usersAdapter = new UsersAdapter(userList,userList ,this);
        recyclerView.setAdapter(usersAdapter);

        // טעינת המשתמשים מהפיירבייס
        fetchUsers();

        // מאזין לחיפוש משתמשים
        userSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                usersAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                usersAdapter.filter(newText);
                return false;
            }
        });
    }

    private void fetchUsers() {
        databaseService.getUsers(new DatabaseService.DatabaseCallback<List<User>>() {
            @Override
            public void onCompleted(List<User> object) {
                userList.clear();;
                userList.addAll(object);
                recyclerView.setAdapter(usersAdapter);
                usersAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}

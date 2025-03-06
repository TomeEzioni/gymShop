package com.example.gymshop.screens;

import android.os.Bundle;
import android.util.Log;

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
import com.example.gymshop.services.AuthenticationService;
import com.example.gymshop.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class ShowUsers extends AppCompatActivity {

    private DatabaseService databaseService;
    private AuthenticationService authenticationService;
    private String uid;
    private User user;

    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_users);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // אתחול השירותים
        databaseService = DatabaseService.getInstance();
        authenticationService = AuthenticationService.getInstance();
        uid = authenticationService.getCurrentUserId();

        // אתחול ה-RecyclerView
        recyclerView = findViewById(R.id.UsersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // אתחול המתאם והשמתו לרשימה
        adapter = new UsersAdapter(users);
        recyclerView.setAdapter(adapter);

        // טעינת המשתמשים מהמסד נתונים
        loadUsers();

        // קבלת המשתמש המחובר
        loadCurrentUser();
    }

    private void loadUsers() {
        databaseService.getUsers(new DatabaseService.DatabaseCallback<List<User>>() {
            @Override
            public void onCompleted(List<User> object) {
                users.clear();
                users.addAll(object);
                adapter.updateData(users); // עדכון ה-RecyclerView
            }

            @Override
            public void onFailed(Exception e) {
                Log.e("TAG", "שגיאה בטעינת המשתמשים", e);
            }
        });
    }

    private void loadCurrentUser() {
        databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User u) {
                user = u;
            }

            @Override
            public void onFailed(Exception e) {
                Log.e("TAG", "שגיאה בטעינת המשתמש", e);
            }
        });
    }
}

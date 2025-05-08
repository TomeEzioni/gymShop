package com.example.gymshop.screens;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ShowUsers extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private final List<User> userList = new ArrayList<>();
    private SearchView searchView;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);

        //‑‑ 1.‑ איתור ו‑RecyclerView
        recyclerView = findViewById(R.id.UsersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //‑‑ 2.‑ יצירת Adapter עם listener למחיקה
        adapter = new UsersAdapter(userList, this::deleteUser);
        recyclerView.setAdapter(adapter);

        //‑‑ 3.‑ איתור SearchView
        searchView = findViewById(R.id.searchVUsers);

        //‑‑ 4.‑ הפניה לטבלת Users בפיירבייס
        usersRef = FirebaseDatabase.getInstance().getReference("Users");

        //‑‑ 5.‑ טעינת כל המשתמשים
        fetchAllUsers();

        //‑‑ 6.‑ האזנה לחיפוש
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {   // לחיצה על Search
                filterUsers(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) { // בזמן הקלדה
                filterUsers(newText);
                return true;
            }
        });
    }

    /**  ===  טעינת‑Realtime  ===  */
    private void fetchAllUsers() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot snap) {
                userList.clear();
                for (DataSnapshot ds : snap.getChildren()) {
                    User u = ds.getValue(User.class);
                    if (u == null) continue;
                    u.setId(ds.getKey()); // נשמר למחיקה
                    userList.add(u);
                }
                adapter.notifyDataSetChanged();
            }
            @Override public void onCancelled(@NonNull DatabaseError e) { }
        });
    }

    /**  ===  סינון מקומי לפי fname  ===  */
    private void filterUsers(String text) {
        if (text == null) text = "";
        String q = text.toLowerCase();
        List<User> filtered = new ArrayList<>();
        for (User u : userList) {
            if (u.getfName() != null && u.getfName().toLowerCase().contains(q)) {
                filtered.add(u);
            }
        }
        adapter.setFilteredList(filtered);
    }

    /**  ===  מחיקת משתמש גם מפרויקט וגם מהרשימה  ===  */
    private void deleteUser(User user) {
        if (user.getId() == null) return;
        usersRef.child(user.getId()).removeValue()
                .addOnSuccessListener(v -> Toast.makeText(this,
                        "‏המשתמש נמחק", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this,
                        "‏שגיאה: "+e.getMessage(), Toast.LENGTH_LONG).show());
    }
}


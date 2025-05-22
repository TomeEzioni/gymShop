package com.example.gymshop.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymshop.R;
import com.example.gymshop.services.AuthenticationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Admin_Page extends AppCompatActivity {

    private String userId, isAdmin;
    Button btnAddItAd,btnUsAd, btnPurAd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("USER_ID")) {
            isAdmin = getIntent().getStringExtra("IS_ADMIN");
            userId = intent.getStringExtra("USER_ID");
            Log.d("DEBUG", "User ID received: " + userId);
        } else {
            Log.e("ERROR", "No User ID found in intent");
            Toast.makeText(this, "שגיאה: לא נמצא מזהה משתמש", Toast.LENGTH_SHORT).show();
        }
    }
    private void initViews()
    {
        btnAddItAd = findViewById(R.id.btnAddItemAdmin);
        btnUsAd = findViewById(R.id.btnUsersAdmin);
        btnPurAd = findViewById(R.id.btnPurchasesAdmin);
    }

    public void showUsersAdmin(View view)
    {
        Intent intent = new Intent(Admin_Page.this, ShowUsers.class);
        startActivity(intent);
    }

    public void purchases(View view)
    {
        Intent intent = new Intent(Admin_Page.this, AllOrders.class);
        startActivity(intent);
    }

    public void addItem(View view)
    {
        Intent intent = new Intent(Admin_Page.this, AddItem.class);
        startActivity(intent);
    }

    public void shopAdmin(View view)
    {
        Intent intent = new Intent(Admin_Page.this, OneItem.class);
        intent.putExtra("username", AuthenticationService.getInstance().getCurrentUsername());
        startActivity(intent);
    }

    public void UpdateDetails(View view)
    {

        Intent intent = new Intent(Admin_Page.this, changeDetails.class);
        intent.putExtra("USER_ID", userId);
        intent.putExtra("IS_ADMIN",isAdmin);
        startActivity(intent);
    }
}
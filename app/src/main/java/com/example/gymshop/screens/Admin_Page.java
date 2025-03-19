package com.example.gymshop.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymshop.R;

public class Admin_Page extends AppCompatActivity {

    Button btnAddItAd,btnUsAd, btnPurAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
        Intent intent = new Intent(Admin_Page.this, purchases.class);
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
        startActivity(intent);
    }
}
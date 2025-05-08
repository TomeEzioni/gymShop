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

public class userHomeActivity extends AppCompatActivity {

    Button btnHome;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("USER_ID"))
        {
            userId = intent.getStringExtra("USER_ID");
        }
        initView();
    }

    public void initView()
    {
        btnHome = findViewById(R.id.btnShop);
    }
    public void shop(View view)
    {
        Intent intent = new Intent(userHomeActivity.this, OneItem.class);
        startActivity(intent);
    }

    public void change(View view)
    {
        Intent intent = new Intent(userHomeActivity.this, changeDetails.class);
        intent.putExtra("USER_ID", userId);
        startActivity(intent);
    }
}
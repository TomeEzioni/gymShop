package com.example.gymshop.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymshop.R;

public class ItemRow extends AppCompatActivity {
    Button btnAddToC, btnIncreaseQ, btnDecreaseQ;
    public int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_itemrow);

    }

    public void initViews()
    {
        btnAddToC = findViewById(R.id.btnAddToCartButton);
        btnIncreaseQ = findViewById(R.id.btnIncreaseQuantity);
        btnDecreaseQ = findViewById(R.id.btnDecreaseQuantity);
    }


}

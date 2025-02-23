package com.example.gymshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymshop.adapters.ItemsAdapter;
import com.example.gymshop.models.Item;
import com.example.gymshop.screens.OneItem;
import com.example.gymshop.screens.Payment;

import java.util.ArrayList;

public class Shopping_basket extends AppCompatActivity {
    RecyclerView rcCart;
    ItemsAdapter adapter;
    ArrayList<Item> cartItems;
    Button btnBePayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);
        btnBePayment = findViewById(R.id.btnBeyondPayment);

        rcCart = findViewById(R.id.rcvShoppingBasket);
        rcCart.setLayoutManager(new LinearLayoutManager(this));

        // קבלת רשימת המוצרים מהאינטנט
        cartItems = (ArrayList<Item>) getIntent().getSerializableExtra("cartItems");

        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        adapter = new ItemsAdapter(cartItems, item -> {});
        rcCart.setAdapter(adapter);
    }

    public void beyomdPayment(View view) {
        Intent intent = new Intent(Shopping_basket.this, Payment.class);
        startActivity(intent);
    }
}

package com.example.gymshop.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymshop.R;
import com.example.gymshop.adapters.ItemsAdapter;
import com.example.gymshop.models.Cart;

public class Shopping_basket extends AppCompatActivity {
    RecyclerView rcCart;
    ItemsAdapter adapter;
    Cart cart;
    Button btnBePayment;

    private static final String CART_PREFS = "CartPrefs";
    private static final String CART_KEY = "cartItems";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);
        btnBePayment = findViewById(R.id.btnBeyondPayment);

        rcCart = findViewById(R.id.rcvShoppingBasket);
        rcCart.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences(CART_PREFS, MODE_PRIVATE);
        // Gson gson = new Gson();
        String json = sharedPreferences.getString(CART_KEY, "[]");

        //Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        // cartItems = gson.fromJson(json, type);
        // קבלת רשימת המוצרים מהאינטנט
        cart = (Cart) getIntent().getSerializableExtra("cartItems");

        if (cart == null) {
            cart = new Cart();
        }

        adapter = new ItemsAdapter(cart.getItems());
        rcCart.setAdapter(adapter);
    }

    public void beyomdPayment(View view) {
        Intent intent = new Intent(Shopping_basket.this, Payment.class);
        startActivity(intent);
    }
}

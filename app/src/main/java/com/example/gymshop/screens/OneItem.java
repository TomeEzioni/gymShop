package com.example.gymshop.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymshop.R;
import com.example.gymshop.Shopping_basket;
import com.example.gymshop.adapters.ItemsAdapter;
import com.example.gymshop.models.Cart;
import com.example.gymshop.models.Item;
import com.example.gymshop.services.DatabaseService;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;
import java.util.List;

public class OneItem extends AppCompatActivity {
    private Button increaseQuantity, decreaseQuantity, addToCartButton, homeButton, contactButton, foodButton, fitnessButton, waterSportsButton, bandsButton, weightsButton, matsButton,gymOffersButton;
    private ImageButton cartButton;
    private int quantity = 1;
    RecyclerView rcItems;

    ArrayList<Item> items;
    private DatabaseService databaseService;

    ItemsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oneitem);
        homeButton = findViewById(R.id.btn_home2);

        contactButton = findViewById(R.id.btn_contact2);
        foodButton = findViewById(R.id.btn_food);
        fitnessButton = findViewById(R.id.btn_fitness2);
        waterSportsButton = findViewById(R.id.btn_water_sports2);
        bandsButton = findViewById(R.id.btn_bands2);
        weightsButton = findViewById(R.id.btn_weights2);
        matsButton = findViewById(R.id.btn_mats);
        gymOffersButton = findViewById(R.id.btn_gym_offers2);
        rcItems = findViewById(R.id.rcItems);
        rcItems.setLayoutManager(new LinearLayoutManager(this));


        // decreaseQuantity=findViewById(R.id.btnDecreaseQuantity2);
        items = new ArrayList<>();

       adapter=new ItemsAdapter(items);

        databaseService = DatabaseService.getInstance();
        databaseService.getItems(new DatabaseService.DatabaseCallback<List<Item>>() {
            @Override
            public void onCompleted(List<Item> object) {

                Log.d("TAG", "onCompleted:" + object);
                items.clear();
                items.addAll(object);
                rcItems.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(Exception e) {

            }
        });


//        increaseQuantity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (quantity < 10) {
//                    quantity++;
//                   productQuantity.setText(String.valueOf(quantity));
//                }
//            }
//        });

//        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (quantity > 1) {
//                    quantity--;
//                    productQuantity.setText(String.valueOf(quantity));
//                }
//            }
//        });

//        addToCartButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(OneItem.this, "נוספו " + quantity + " יחידות לעגלה!", Toast.LENGTH_SHORT).show();
//            }
//        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OneItem.this, MainActivity.class);
                startActivity(intent);
            }
        });

//        cartButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(OneItem.this, Shopping_basket.class);
//                startActivity(intent);
//            }
//        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OneItem.this,Shopping_basket.class);
                startActivity(intent);
            }
        });
    }
}

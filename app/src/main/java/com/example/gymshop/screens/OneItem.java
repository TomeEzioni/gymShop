package com.example.gymshop.screens;

import android.content.Intent;
import android.net.Uri;
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

import com.example.gymshop.Item_Profile;
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

    RecyclerView rcItems;
    ArrayList<Item> items;
    ItemsAdapter adapter;
    ImageButton btnCart2;
    Button btnHome, btnContact;
    ArrayList<Item> cartItems = new ArrayList<>();
    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oneitem);

        rcItems = findViewById(R.id.rcItems);
        rcItems.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();
        adapter = new ItemsAdapter(items);
        rcItems.setAdapter(adapter);

        btnCart2 = findViewById(R.id.btn_cart2);
        btnHome = findViewById(R.id.btn_home2);
        btnContact = findViewById(R.id.btn_contact2);

        // שמירת המוצר שנבחר לעגלה
        adapter.setOnItemClickListener(new ItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                addToCart(item);  // הוספת המוצר לעגלה
            }
        });

        Intent intent = new Intent(this, Shopping_basket.class);
        intent.putExtra("cartItems", cartItems);
        startActivity(intent);

        databaseService = DatabaseService.getInstance();
        databaseService.getItems(new DatabaseService.DatabaseCallback<List<Item>>() {
            @Override
            public void onCompleted(List<Item> object) {
                Log.d("TAG", "onCompleted:" + object);
                items.clear();
                items.addAll(object);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {
                Log.e("TAG", "Error fetching items", e);
            }
        });
    }
s
    // הוספת פריט לעגלה
    private void addToCart(Item item) {
        cartItems.add(item);
        Toast.makeText(this, item.getName() + " נוסף לעגלה!", Toast.LENGTH_SHORT).show();
    }

    public void shoppingBasket(View view) {
        Intent intent = new Intent(OneItem.this, Shopping_basket.class);
        startActivity(intent);
    }

    public void home(View view) {
        Intent intent = new Intent(OneItem.this, MainActivity.class);
        startActivity(intent);
    }

    public void contact(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"tomerezioni@gmail.com"}); // החלף בכתובת שלך
        intent.putExtra(Intent.EXTRA_SUBJECT, "נושא ההודעה");
        intent.putExtra(Intent.EXTRA_TEXT, "שלום, אני מעוניין ליצור קשר...");

        try {
            startActivity(Intent.createChooser(intent, "בחר אפליקציה לשליחת אימייל"));
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(this, "לא נמצאה אפליקציה לשליחת אימייל", Toast.LENGTH_SHORT).show();
        }
    }
}


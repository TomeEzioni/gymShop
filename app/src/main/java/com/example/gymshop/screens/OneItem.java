package com.example.gymshop.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymshop.R;
import com.example.gymshop.adapters.ItemsAdapter;
import com.example.gymshop.models.Cart;
import com.example.gymshop.models.Item;
import com.example.gymshop.models.ItemOrder;
import com.example.gymshop.services.AuthenticationService;
import com.example.gymshop.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class OneItem extends AppCompatActivity {

    RecyclerView rcItems;
    ArrayList<Item> items;
    ItemsAdapter adapter;
    ImageButton btnCart2;
    Button btnHome, btnContact;
    SearchView svItem;
    Spinner spTypeSinon;
    Cart cart = null;
    public DatabaseService databaseService;

    public int quantity = 1;

    private String uid = null;
    private String txt="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oneitem);

        initViews();
        databaseService = DatabaseService.getInstance();
        rcItems.setLayoutManager(new LinearLayoutManager(this));
        items = new ArrayList<>();
        adapter = new ItemsAdapter();
        rcItems.setAdapter(adapter);
        uid = AuthenticationService.getInstance().getCurrentUserId();
        svItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                txt=s;

                List<Item> filteredItems = new ArrayList<>();
                filteredItems.addAll(items);

                filteredItems.removeIf(item -> !item.getName().contains(txt));
                adapter.setItemList(filteredItems);


                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                txt=s;

                List<Item> filteredItems = new ArrayList<>();
                filteredItems.addAll(items);

                filteredItems.removeIf(item -> !item.getName().contains(txt));
                adapter.setItemList(filteredItems);


                return true;
            }
        });


        if (AuthenticationService.getInstance().isUserSignedIn()) {
            fetchCartFromFirebase();

            databaseService.getItems(new DatabaseService.DatabaseCallback<List<Item>>() {
                @Override
                public void onCompleted(List<Item> is) {
                    Log.d("TAG", "onCompleted:" + is);
                    items.clear();
                    items.addAll(is);
                    adapter.setItemList(items);
                }

                @Override
                public void onFailed(Exception e) {
                    Log.e("TAG", "Error fetching items", e);
                }
            });
        }



    }

    public void initViews() {
        rcItems = findViewById(R.id.rcItems);
        btnCart2 = findViewById(R.id.btn_cart2);
        btnHome = findViewById(R.id.btn_home2);
        btnContact = findViewById(R.id.btn_contact2);
        svItem = findViewById(R.id.searchViewItem);
        spTypeSinon = findViewById(R.id.spSinon);

        spTypeSinon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){



                    /// example:
                    String type = (String) adapterView.getItemAtPosition(i);


                    List<Item> filteredItems = new ArrayList<>();
                    filteredItems.addAll(items);
                    filteredItems.removeIf(item -> !item.getType().equals(type));
                  filteredItems.removeIf(item -> !item.getName().contains(txt));
                    adapter.setItemList(filteredItems);

                }

                else {


                    List<Item> filteredItems = new ArrayList<>();
                    filteredItems.addAll(items);
                    adapter.setItemList(filteredItems);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                List<Item> filteredItems = new ArrayList<>();
                filteredItems.addAll(items);
                adapter.setItemList(filteredItems);
            }
        });
    }

    private void fetchCartFromFirebase() {
        databaseService.getCart(uid, new DatabaseService.DatabaseCallback<Cart>() {
            @Override
            public void onCompleted(Cart cart2) {
                if (cart2 == null)
                    cart2 = new Cart();
                cart = cart2;
            }

            @Override
            public void onFailed(Exception e) {
                cart = new Cart();

            }
        });
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


    private void updateQuantityDisplay() {
        TextView quantityTextView = findViewById(R.id.productQuantity);
        quantityTextView.setText(String.valueOf(quantity));
    }


    // הוספת מוצר לעגלה
    public void addItemToCart(ItemOrder itemOrder) {


        if (uid == null) {
            Toast.makeText(OneItem.this, "המוצר ktttt ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cart == null)
            cart = new Cart();

        cart.addItem(itemOrder);
        // cart.updateItemOrder( itemOrder);


        databaseService.updateCart(cart, uid, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {

                Log.d("Update cart", cart.toString());
                //    updateTotalPrice();  // עדכון המחיר הכולל
                Toast.makeText(OneItem.this, "המוצר נוסף לעגלה", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Update cart", "Error fetching items", e);

            }
        });

    }


}


package com.example.gymshop.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    ArrayList<Item> cartItems = new ArrayList<>();
    public DatabaseService databaseService;

    private static final String CART_PREFS = "CartPrefs";
    private static final String CART_KEY = "cartItems";
    public int quantity = 1;

    private Cart cart;
    private Button cartButton;
    private TextView totalPriceText;

    AuthenticationService authenticationService;

    private String uid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oneitem);

        databaseService = DatabaseService.getInstance();

        rcItems = findViewById(R.id.rcItems);
        rcItems.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();
        adapter = new ItemsAdapter(items);

        rcItems.setAdapter(adapter);

        btnCart2 = findViewById(R.id.btn_cart2);
        btnHome = findViewById(R.id.btn_home2);
        btnContact = findViewById(R.id.btn_contact2);

        uid = AuthenticationService.getInstance().getCurrentUserId();
        if (uid != null) {
            fetchCartFromFirebase();

            // שמירת המוצר שנבחר לעגלה




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

    }

    private void fetchCartFromFirebase() {

        databaseService.getCart(AuthenticationService.getInstance().getCurrentUserId(), new DatabaseService.DatabaseCallback<Cart>() {
            @Override
            public void onCompleted(Cart cart) {

                if(cart==null)
                    cart=new Cart();
                OneItem.this.cart = cart;
            }

            @Override
            public void onFailed(Exception e) {
                cart=new Cart();

            }
        });


    }


    // הוספת פריט לעגלה
    private void addToCart(Item item) {
        // קבלת הרשימה הנוכחית מהזיכרון
        SharedPreferences sharedPreferences = getSharedPreferences(CART_PREFS, MODE_PRIVATE);
        // Gson gson = new Gson();
        String json = sharedPreferences.getString(CART_KEY, "[]");

        //Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        //ArrayList<Item> cartItems = gson.fromJson(json, type);

        // הוספת המוצר לעגלה
        cartItems.add(item);

        // שמירת העגלה בחזרה ל-SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString(CART_KEY, gson.toJson(cartItems));
        editor.apply();

        Toast.makeText(this, item.getName() + " נוסף לעגלה!", Toast.LENGTH_SHORT).show();
    }

    public void shoppingBasket(View view) {
        Intent intent = new Intent(OneItem.this, Shopping_basket.class);

        intent.putExtra("cartItems",cart);
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

    public void Decrease(View view)
    {
        if (quantity > 1) { // מונע מספרים שליליים
            quantity--;
            updateQuantityDisplay();
        }
    }

    public void Increase(View view)
    {
        quantity++;
        updateQuantityDisplay();
    }

    private void updateQuantityDisplay() {
        TextView quantityTextView = findViewById(R.id.productQuantity);
        quantityTextView.setText(String.valueOf(quantity));
    }


    // הוספת מוצר לעגלה
    public void addItemToCart(Item item) {



        if(uid==null){
            Toast.makeText(OneItem.this, "המוצר ktttt ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(cart==null)
            cart=new Cart();

        ItemOrder itemOrder=new ItemOrder(item,quantity);

        this.cart.getItems().add(itemOrder);




        databaseService.updateCart(this.cart, uid, new DatabaseService.DatabaseCallback<Void>() {
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


    //@Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = cartItems.get(position);
        if (item == null) return;

        // הצגת מידע על המוצר
        // holder.productName.setText(item.getName());
        // holder.productPrice.setText("₪" + item.getPrice());

        // הגדרת לחצן הוספה לעגלה
        // holder.btnAddToCartButton.setOnClickListener(v -> {
        addItemToCart(item);  // הוספת המוצר שנבחר לעגלה
        // });
    }


}


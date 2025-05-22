package com.example.gymshop.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymshop.R;
import com.example.gymshop.adapters.CartAdapter;
import com.example.gymshop.models.Cart;
import com.example.gymshop.models.Order;
import com.example.gymshop.models.User;
import com.example.gymshop.services.AuthenticationService;
import com.example.gymshop.services.DatabaseService;

public class Shopping_basket extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rcCart;

    Cart cart=null;
    Button btnBePayment;
    TextView tvprice;

    private static final String CART_PREFS = "CartPrefs";
    private static final String CART_KEY = "cartItems";


    private CartAdapter cartAdapter;

    DatabaseService databaseService;
    AuthenticationService authenticationService;
    String uid;
    User user=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);
        btnBePayment = findViewById(R.id.btnBeyondPayment);
        btnBePayment.setOnClickListener(this);

        rcCart = findViewById(R.id.rcvShoppingBasket);
        tvprice = findViewById(R.id.tvTotalPrice);


        rcCart.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences(CART_PREFS, MODE_PRIVATE);
        // Gson gson = new Gson();
        String json = sharedPreferences.getString(CART_KEY, "[]");

        // שירותים
        authenticationService = AuthenticationService.getInstance();
        databaseService = DatabaseService.getInstance();
        uid = authenticationService.getCurrentUserId();



        // אתחול סל ריק בתחילה כדי למנוע NullPointer



        // טען את הסל מהמסד
        databaseService.getCart(uid, new DatabaseService.DatabaseCallback<Cart>() {
            @Override
            public void onCompleted(Cart resultCart) {
                if (resultCart != null && resultCart.getItems() != null) {
                    cart = resultCart;

                    Toast.makeText(Shopping_basket.this, ""+cart.toString(), Toast.LENGTH_SHORT).show();
                }

                else cart=new Cart();

                cartAdapter = new CartAdapter(Shopping_basket.this, cart);
                rcCart.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
                tvprice.setText(cart.getTotalPrice()+"");

            }

            @Override
            public void onFailed(Exception e) {
                cart=new Cart();
                Log.e("error", e.getMessage());
                Toast.makeText(Shopping_basket.this, "שגיאה בטעינת הסל", Toast.LENGTH_SHORT).show();
            }
        });


        databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User object) {
                user=new User(object);
            }

            @Override
            public void onFailed(Exception e) {
                return;
            }
        });







        //adapter = new ItemsAdapter(cart.getItems());
        //rcCart.setAdapter(adapter);
    }

    public void beyomdPayment(View view) {
        Intent intent = new Intent(Shopping_basket.this, Payment.class);
        startActivity(intent);
    }


    private void processOrder() {
        if (cart == null || cart.getItems().isEmpty()) {
            Toast.makeText(this, "העגלה ריקה!", Toast.LENGTH_SHORT).show();
            return;
        }

        String orderId=databaseService.generateOrderId();
        Order order = new Order(orderId, cart.getItems(),  cart.getTotalPrice(), "new",  user,  0);

        order.setTimestamp(System.currentTimeMillis());
        databaseService.createNewOreder(order, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                Toast.makeText(Shopping_basket.this, "הזמנה נשמרה!", Toast.LENGTH_SHORT).show();
                cart=new Cart();

                goUpdateCart(cart);


            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(Shopping_basket.this, "שגיאה בשמירת ההזמנה", Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void goUpdateCart(Cart cart){
        databaseService.updateCart(cart, uid, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {

            }

            @Override
            public void onFailed(Exception e) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        processOrder();
        Intent go=new Intent(Shopping_basket.this,Payment.class);
        startActivity(go);
    }
}

package com.example.gymshop.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymshop.R;
import com.example.gymshop.Shopping_basket;

public class OneItem extends AppCompatActivity {
    private ImageView productImage;
    private TextView productName, productType, productBrand, productColor, productPrice, productQuantity;
    private Button increaseQuantity, decreaseQuantity, addToCartButton, homeButton, contactButton, foodButton, fitnessButton, waterSportsButton, bandsButton, weightsButton, matsButton,gymOffersButton;
    private ImageButton cartButton;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oneitem);

        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productType = findViewById(R.id.productType);
        productBrand = findViewById(R.id.productBrand);
        productColor = findViewById(R.id.productColor);
        productPrice = findViewById(R.id.productPrice);
        productQuantity = findViewById(R.id.productQuantity);
        increaseQuantity = findViewById(R.id.btnIncreaseQuantity);
        decreaseQuantity = findViewById(R.id.btnDecreaseQuantity);
        addToCartButton = findViewById(R.id.btnAddToCartButton);
        homeButton = findViewById(R.id.btn_home);
        cartButton = findViewById(R.id.btn_cart);
        contactButton = findViewById(R.id.btn_contact);
        foodButton = findViewById(R.id.btn_food);
        fitnessButton = findViewById(R.id.btn_fitness);
        waterSportsButton = findViewById(R.id.btn_water_sports);
        bandsButton = findViewById(R.id.btn_bands);
        weightsButton = findViewById(R.id.btn_weights);
        matsButton = findViewById(R.id.btn_mats);
        gymOffersButton = findViewById(R.id.btn_gym_offers);

        productName.setText("משקולת 10 ק״ג");
        productType.setText("סוג: ציוד כוח");
        productBrand.setText("חברה: FitPro");
        productColor.setText("צבע: שחור");
        productPrice.setText("₪199");
        productImage.setImageResource(R.drawable.dumbbells);

        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity < 10) {
                    quantity++;
                    productQuantity.setText(String.valueOf(quantity));
                }
            }
        });

        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    productQuantity.setText(String.valueOf(quantity));
                }
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OneItem.this, "נוספו " + quantity + " יחידות לעגלה!", Toast.LENGTH_SHORT).show();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OneItem.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OneItem.this, Shopping_basket.class);
                startActivity(intent);
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OneItem.this,Shopping_basket.class);
                startActivity(intent);
            }
        });
    }
}

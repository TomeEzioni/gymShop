package com.example.gymshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymshop.adapters.ItemsAdapter;
import com.example.gymshop.models.Item;
import com.example.gymshop.services.DatabaseService;

public class Item_Profile extends AppCompatActivity {

    ImageView ivProfile;
    TextView tvItemName, tvItemType, tvItemColor, tvItemLogo, tvItemPrice;
    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_profile);

        initViews();

        // קבלת ה-ID של המוצר מה-Intent
        String itemId = getIntent().getStringExtra("itemId");
        if (itemId != null) {
            loadItemDetails(itemId);
        }
    }

    private void initViews() {
        ivProfile = findViewById(R.id.ivItemImagePr);
        tvItemName = findViewById(R.id.tvItemName);
        tvItemType = findViewById(R.id.tvItemTypePr);
        tvItemColor = findViewById(R.id.tvItemColorPr);
        tvItemLogo = findViewById(R.id.tvItemLogoPr);
        tvItemPrice = findViewById(R.id.tvItemPricePr);
    }

    private void loadItemDetails(String itemId) {
        databaseService = DatabaseService.getInstance();
        databaseService.getItemById(itemId, new DatabaseService.DatabaseCallback<Item>() {
            @Override
            public void onCompleted(Item item) {
                if (item != null) {
                    // הצגת נתונים על המוצר
                    tvItemName.setText(item.getName());
                    tvItemType.setText("סוג: " + item.getType());
                    tvItemColor.setText("צבע: " + item.getColor());
                    tvItemLogo.setText("מותג: " + item.getLogo());
                    tvItemPrice.setText("מחיר: ₪" + item.getPrice());

                    // הצגת התמונה אם קיימת
                    // Glide.with(Item_Profile.this).load(item.getImageUrl()).into(ivProfile);
                }
            }

            @Override
            public void onFailed(Exception e) {
                tvItemName.setText("שגיאה בטעינת הנתונים");
            }
        });
    }
}
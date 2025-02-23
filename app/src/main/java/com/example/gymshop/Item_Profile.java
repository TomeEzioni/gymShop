package com.example.gymshop;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Item_Profile extends AppCompatActivity {

    ImageView IvProfile;
    TextView tvItemName, tvItemType, tvItemColor, tvItemLogo, tvItemPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initViews()
    {
        IvProfile = findViewById(R.id.ivItemImagePr);
        tvItemName = findViewById(R.id.tvItemName);
        tvItemType = findViewById(R.id.tvItemTypePr);
        tvItemColor = findViewById(R.id.tvItemColorPr);
        tvItemLogo = findViewById(R.id.tvItemLogoPr);
        tvItemPrice = findViewById(R.id.tvItemPricePr);
    }
}
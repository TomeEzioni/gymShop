package com.example.gymshop.screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymshop.R;

public class AddItem extends AppCompatActivity implements View.OnClickListener {

    Spinner spColor, spType;
    String color, type;
    Button btnTakePic, btnGallery, btnPluse;
    ImageView ivProduct;
    Bitmap bitmap;
    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    private void initViews() {
        spColor = findViewById(R.id.spColor);
        color=spColor.getSelectedItem().toString();
        spType=findViewById(R.id.spType);
        type=spType.getSelectedItem().toString();
        btnTakePic=findViewById(R.id.btnTakePic);
        btnTakePic.setOnClickListener(this);
        btnGallery=findViewById(R.id.btGallery);
        btnGallery.setOnClickListener(v -> openGallery());
        btnPluse=findViewById(R.id.btPluse);
        btnPluse.setOnClickListener(this);
        ivProduct=findViewById(R.id.ivProduct);

    }


    private void openGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onClick(View view)
    {
        if (view == btnTakePic)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,0);
        }
        else if (view == btnPluse)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent,1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImageUri = data.getData();
        }
        else if (requestCode == 0)
        {
            if (resultCode == RESULT_OK)
            {
                bitmap = (Bitmap) data.getExtras().get("data");
                ivProduct.setImageBitmap(bitmap);
            }
        }
        else if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
            }
        }
    }
}
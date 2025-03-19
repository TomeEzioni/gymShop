package com.example.gymshop.screens;

import static android.content.ContentValues.TAG;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymshop.R;
import com.example.gymshop.models.Item;
import com.example.gymshop.services.DatabaseService;
import com.example.gymshop.utils.ImageUtil;

public class AddItem extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Spinner spColor, spType;
    String color="", type;
    Button btnTakePic, btnGallery, btnPluse;
    ImageView ivProduct;
    Bitmap bitmap;
    EditText etItemName, etPrice, etCompany;
    String name, stPrice, company;



    private DatabaseService databaseService;

    /// Activity result launcher for selecting image from gallery
    private ActivityResultLauncher<Intent> selectImageLauncher;
    /// Activity result launcher for capturing image from camera
    private ActivityResultLauncher<Intent> captureImageLauncher;

    // One Preview Image


    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        /// request permission for the camera and storage
        ImageUtil.requestPermission(this);

        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();


        /// register the activity result launcher for selecting image from gallery
        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        ivProduct.setImageURI(selectedImage);
                    }
                });

        /// register the activity result launcher for capturing image from camera
        captureImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        ivProduct.setImageBitmap(bitmap);
                    }
                });

    }


    private void initViews()
    {
        spColor = findViewById(R.id.spColor);
        color = spColor.getSelectedItem().toString();
        spType = findViewById(R.id.spType);
        type = spType.getSelectedItem().toString();
        btnTakePic = findViewById(R.id.btnTakePic);
        btnTakePic.setOnClickListener(this);
        btnGallery = findViewById(R.id.btGallery);
        btnGallery.setOnClickListener(this);
        btnPluse = findViewById(R.id.btPluse);
        btnPluse.setOnClickListener(this);
        ivProduct = findViewById(R.id.ivProduct);
        etItemName = findViewById(R.id.etItemName);
        etPrice = findViewById(R.id.etPrice);
        etCompany = findViewById(R.id.etCompany);

        spType.setOnItemSelectedListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == btnPluse.getId()) {
            Log.d(TAG, "Add item button clicked");
            addItemToDatabase();
            return;
        }
        if (v.getId() == btnGallery.getId()) {
            // select image from gallery
            Log.d(TAG, "Select image button clicked");
            selectImageFromGallery();
            return;
        }
        if (v.getId() == btnTakePic.getId()) {
            // capture image from camera
            Log.d(TAG, "Capture image button clicked");
            captureImageFromCamera();
            return;
        }
    }

    /// add the food to the database
/// @see Food
    private void addItemToDatabase() {
        /// get the values from the input fields
        name = etItemName.getText().toString();
        stPrice = etPrice.getText().toString();
        stPrice = stPrice.replace(",", "");
        company = etCompany.getText().toString();
        color = spColor.getSelectedItem().toString();
        type = spType.getSelectedItem().toString();

        String imageBase64 = ImageUtil.convertTo64Base(ivProduct);

        /// validate the input
        /// stop if the input is not valid


        /// convert the price to double
        double price = Double.parseDouble(stPrice);

        /// generate a new id for the item

        String id = databaseService.generateItemId();

        Log.d(TAG, "Adding item to database");
        Log.d(TAG, "ID: " + id);
        Log.d(TAG, "Name: " + name);
        Log.d(TAG, "Price: " + stPrice);
        Log.d(TAG, "Company: " + company);
        Log.d(TAG, "Color: " + color);
        Log.d(TAG, "Type: " + type);
        Log.d(TAG, "Image: " + imageBase64);

            /// create a new itemobject
        Item item = new Item(id, name, type, color, company, price, imageBase64);

        /// save the item to the database and get the result in the callback
        databaseService.createNewItem(item, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                Log.d(TAG, "Item added successfully");
                Toast.makeText(AddItem.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                /// clear the input fields after adding the food for the next food
                Log.d(TAG, "Clearing input fields");
                etItemName.setText("");
                etPrice.setText("");
                ivProduct.setImageBitmap(null);

                Intent intent = new Intent(AddItem.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to add item", e);
                Toast.makeText(AddItem.this, "Failed to add item", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /// select image from gallery
    private void selectImageFromGallery() {
        //   Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //  selectImageLauncher.launch(intent);

        imageChooser();
    }

    /// capture image from camera
    private void captureImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImageLauncher.launch(takePictureIntent);
    }



    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    ivProduct.setImageURI(selectedImageUri);
                }
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        type= (String) adapterView.getItemAtPosition(i);
        color="null";
        if(i==0||i==7){
            spColor.setVisibility(View.INVISIBLE);




        }
        else  spColor.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
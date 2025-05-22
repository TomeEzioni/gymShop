package com.example.gymshop.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gymshop.R;
import com.example.gymshop.models.Item;
import com.example.gymshop.models.ItemOrder;
import com.example.gymshop.screens.Item_Profile;
import com.example.gymshop.screens.OneItem;
import com.example.gymshop.screens.Shopping_basket;
import com.example.gymshop.services.AuthenticationService;
import com.example.gymshop.utils.ImageUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


/// Adapter for the items recycler view
/// @see RecyclerView
/// @see Item
/// @see R.layout#//item_selected_item
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private final ArrayList<Item> itemList;


    public ItemsAdapter() {
        this.itemList = new ArrayList<>();
    }

    public void setItemList(List<Item> items) {
        this.itemList.clear();
        this.itemList.addAll(items);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_itemrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);

        if (item == null) return;
        holder.productName.setText(item.getName());
        holder.productType.setText(item.getType());
        holder.productColor.setText(item.getColor());
        holder.productPrice.setText("₪" + item.getPrice());
        holder.productBrand.setText(item.getLogo());
        holder.productQuantity.setText(1 + "");


        holder.productImage.setImageBitmap(ImageUtil.convertFrom64base(item.getPic()));


        holder.btnDecrease.setOnClickListener(v -> {


            String stQuan = holder.productQuantity.getText().toString();

            int quan = Integer.parseInt(stQuan);
            if (quan >= 1) quan--;

            holder.productQuantity.setText(quan + "");

        });

        holder.btnIncrease.setOnClickListener(v -> {

            String stQuan = holder.productQuantity.getText().toString();

            int quan = Integer.parseInt(stQuan);
            if (quan < 5)
                quan++;

            holder.productQuantity.setText(quan + "");


        });


        // מאזין לכפתור הוספה לעגלה


        holder.itemView.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String username = user != null ? user.getDisplayName() : "אנונימי";
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, Item_Profile.class);

            intent.putExtra("username", AuthenticationService.getInstance().getCurrentUsername());
            intent.putExtra("itemId", item.getId()); // שולח את ה-ID של המוצר
            context.startActivity(intent);
        });


        Context context = holder.itemView.getContext();
        if (context.equals(Shopping_basket.class))
            holder.btnAddToCart.setVisibility(View.GONE);
        else {
            holder.btnAddToCart.setOnClickListener(v -> {
                int quan = Integer.parseInt(holder.productQuantity.getText().toString());

                ItemOrder itemOrder = new ItemOrder(item, quan);

                ((OneItem) context).addItemToCart(itemOrder); // הוספת המוצר לעגלה
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView productName, productType, productBrand, productColor, productPrice, productQuantity;
        public Button btnAddToCart, btnIncrease, btnDecrease;


        public String amontSt = "1";
        public int amount = 1;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.ivProductAD);
            productName = itemView.findViewById(R.id.productName);
            productType = itemView.findViewById(R.id.productType);
            productBrand = itemView.findViewById(R.id.productBrand);
            productColor = itemView.findViewById(R.id.productColor);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncreaseQuantity);
            btnDecrease = itemView.findViewById(R.id.btnDecreaseQuantity);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCartButton);


        }
    }
}
package com.example.gymshop.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gymshop.Item_Profile;
import com.example.gymshop.R;
import com.example.gymshop.Shopping_basket;
import com.example.gymshop.models.Item;
import com.example.gymshop.screens.OneItem;
import com.example.gymshop.utils.ImageUtil;

import java.util.List;


/// Adapter for the items recycler view
/// @see RecyclerView
/// @see Item
/// @see R.layout#//item_selected_item
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private final List<Item> itemList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onAddToCartClick(Item item); // פעולה שתתבצע בלחיצה על "הוסף לעגלה"
    }

    public ItemsAdapter(List<Item> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }
    public ItemsAdapter(List<Item> itemList) {
        this.itemList = itemList;
        this.listener = new OnItemClickListener() {
            @Override
            public void onAddToCartClick(Item item) {

            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_itemrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Item item = itemList.get(position);
        if (item == null) return;
        holder.productName.setText(item.getName());
        holder.productType.setText(item.getType());
        holder.productColor.setText(item.getColor());
        holder.productPrice.setText("₪" + item.getPrice());
        holder.productBrand.setText(item.getLogo());
        holder.productImage.setImageBitmap(ImageUtil.convertFrom64base(item.getPic()));
        holder.btnAddToCart.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddToCartClick(item); // קריאה למאזין
            }
        });

        // מאזין לכפתור הוספה לעגלה

        holder.btnAddToCart.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddToCartClick(item);
            }
        });


        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, Item_Profile.class);
            intent.putExtra("itemId", item.getId()); // שולח את ה-ID של המוצר
            context.startActivity(intent);
        });


        Context context = holder.itemView.getContext();
        if (context.equals(Shopping_basket.class))
            holder.btnAddToCart.setVisibility(View.GONE);
    else {
            holder.btnAddToCart.setOnClickListener(v -> {

                ((OneItem) context).addItemToCart(item); // הוספת המוצר לעגלה
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, productType, productBrand, productColor, productPrice;
        private Button btnAddToCart;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.ivProductAD);
            productName = itemView.findViewById(R.id.productName);
            productType = itemView.findViewById(R.id.productType);
            productBrand = itemView.findViewById(R.id.productBrand);
            productColor = itemView.findViewById(R.id.productColor);
            productPrice = itemView.findViewById(R.id.productPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCartButton);
        }
    }
}
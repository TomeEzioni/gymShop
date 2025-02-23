package com.example.gymshop.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gymshop.R;
import com.example.gymshop.models.Item;
import com.example.gymshop.utils.ImageUtil;

import java.util.List;


/// Adapter for the items recycler view
/// @see RecyclerView
/// @see Item
/// @see R.layout#item_selected_item
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    /// list of items
    /// @see Item
    private final List<Item> itemList;

    public ItemsAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    /// create a view holder for the adapter
    /// @param parent the parent view group
    /// @param viewType the type of the view
    /// @return the view holder
    /// @see ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /// inflate the item_selected_item layout
        /// @see R.layout.item_selected_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrow, parent, false);
        return new ViewHolder(view);
    }

    /// bind the view holder with the data
    /// @param holder the view holder
    /// @param position the position of the item in the list
    /// @see ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        if (item == null) return;


    //   holder.productImage.setImageBitmap(ImageUtil.convertFrom64base(item.getPic()));



        holder.productName.setText(item.getName());
        holder.productType.setText(item.getType());

        holder.productColor.setText(item.getColor());
        holder.productPrice.setText(item.getPrice()+"");
        holder.productBrand.setText(item.getLogo());
        holder.productImage.setImageBitmap(ImageUtil.convertFrom64base(item.getPic()));



    }

    /// get the number of items in the list
    /// @return the number of items in the list
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /// View holder for the items adapter
    /// @see RecyclerView.ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {




        private ImageView productImage;
        private TextView productName, productType, productBrand, productColor, productPrice, productQuantity;



        public ViewHolder(View itemView) {
            super(itemView);


            productImage = itemView.findViewById(R.id.ivProductAD);
            productName = itemView.findViewById(R.id.productName);
            productType = itemView.findViewById(R.id.productType);
            productBrand = itemView.findViewById(R.id.productBrand);
            productColor = itemView.findViewById(R.id.productColor);
            productPrice = itemView.findViewById(R.id.productPrice);
         // productQuantity = itemView.findViewById(R.id.productQuantity);



        }
    }
}
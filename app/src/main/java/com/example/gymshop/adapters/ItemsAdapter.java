package com.example.gymshop.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneitem, parent, false);
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

       // holder.itemNameTextView.setText(item.getName());
       // holder.itemImageView.setImageBitmap(ImageUtil.convertFrom64base(item.getImageBase64()));
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
        //public final TextView itemNameTextView;
       // public final ImageView itemImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            //itemNameTextView = itemView.findViewById(R.id.item_name_text_view);
            //itemImageView = itemView.findViewById(R.id.item_image_view);
        }
    }
}
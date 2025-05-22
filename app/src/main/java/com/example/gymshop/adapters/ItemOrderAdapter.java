package com.example.gymshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gymshop.R;
import com.example.gymshop.models.Item;
import com.example.gymshop.models.ItemOrder;
import com.example.gymshop.utils.ImageUtil;

import java.util.List;

public class ItemOrderAdapter  extends RecyclerView.Adapter<ItemOrderAdapter.ViewHolder> {
    private Context context;
    private List<ItemOrder> items;

    public ItemOrderAdapter(Context context, List<ItemOrder> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.oneitemorder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ItemOrder item_cart = items.get(position);
        holder.bind(item_cart);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView product_name, product_price, product_amount, product_size;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_imageItemOrder);
            product_name = itemView.findViewById(R.id.product_nameItemOrder);
            product_price = itemView.findViewById(R.id.product_priceItemOrder);
            product_amount = itemView.findViewById(R.id.product_AmountItemOrder);
            product_size = itemView.findViewById(R.id.product_sizeItemOrder);

        }

        public void bind(final ItemOrder item_cart) {
            Item item = item_cart.getItem();
            int amount = item_cart.getAmount();



                product_image.setImageBitmap(ImageUtil.convertFrom64base(item.getPic()));
                product_name.setText(item.getName());
                product_price.setText(item.getPrice() + "₪");
                product_amount.setText(String.valueOf(amount));









            }

        }
    }


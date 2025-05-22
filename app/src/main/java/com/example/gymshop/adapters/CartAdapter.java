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
import com.example.gymshop.models.Cart;
import com.example.gymshop.models.Item;
import com.example.gymshop.models.ItemOrder;
import com.example.gymshop.screens.Shopping_basket;
import com.example.gymshop.utils.ImageUtil;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private Cart cart;

    public CartAdapter(Context context, Cart cart) {
        this.context = context;
        this.cart = cart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (cart != null && cart.getItems() != null && position < cart.getItems().size()) {
            ItemOrder item_cart = cart.getItems().get(position);
            holder.bind(item_cart);
        }
    }

    @Override
    public int getItemCount() {
        return (cart != null && cart.getItems() != null) ? cart.getItems().size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView product_name, product_price, product_amount, product_size;
        ImageButton ibPlus, ibMinus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_amount = itemView.findViewById(R.id.product_amount);
            product_size = itemView.findViewById(R.id.product_size);

        }

        public void bind(final ItemOrder item_cart) {
            Item product = item_cart.getItem();
            int amount = item_cart.getAmount();

            if (amount > 0) {


                product_image.setImageBitmap(ImageUtil.convertFrom64base(product.getPic()));
                product_name.setText(product.getName());
                product_price.setText(product.getPrice() + "₪");
                product_amount.setText(String.valueOf(amount));
                //   product_size.setText(product.getSize());


            }

        }
    }
}

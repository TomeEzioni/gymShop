package com.example.gymshop.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    /// unique id of the cart


    private final ArrayList<ItemOrder> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(ArrayList<ItemOrder> items) {
        this.items = items;
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public ItemOrder removeItem(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.remove(index);
    }

    public ItemOrder getItem(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }


    public double getTotalPrice() {
        double totalPrice = 0;
        for (ItemOrder itemOrder : items) {
            totalPrice += itemOrder.getItem().getPrice()*itemOrder.getAmount();
        }
        return totalPrice;
    }

    public void clear() {
        items.clear();
    }

    public ArrayList<ItemOrder> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}

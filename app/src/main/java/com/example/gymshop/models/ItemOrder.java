package com.example.gymshop.models;

public class ItemOrder {

    Item item;
    int amount;

    public ItemOrder(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public ItemOrder() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ItemOrder{" +
                "item=" + item +
                ", amount=" + amount +
                '}';
    }
}

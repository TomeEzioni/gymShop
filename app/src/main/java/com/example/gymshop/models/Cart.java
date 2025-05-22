package com.example.gymshop.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    /// unique id of the cart


    private  ArrayList<ItemOrder> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(ArrayList<ItemOrder> items) {
        this.items = items;
    }

    public ItemOrder removeItem(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.remove(index);
    }

    public  void addItem(ItemOrder itemOrder){
        if(itemOrder!=null) {
             boolean found=false;

            for ( int i=0;i< this.items.size();i++) {
                     if( this.items.get(i).getItem().getId().equals(itemOrder.getItem().getId())) {
                         found = true;
                             this.getItems().get(i).setAmount(itemOrder.getAmount());
                     }
            }

            if (!found) {
                     this.getItems().add(itemOrder);




            }

        }

    }

    public ItemOrder getItem(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }


    public double getTotalPrice() {
        double totalPrice = 0;
        for (ItemOrder itemOrder : this.items) {
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

    public void setItems(ArrayList<ItemOrder> items) {
        this.items = items;
    }

    public  void  delItemFromCart(ItemOrder itemOrder){

        if(itemOrder!=null) {

            for (int i = 0; i < this.getItems().size(); i++) {
                if (this.getItems().get(i).getItem().getId().equals(itemOrder.getItem().getId()))

                    this.getItems().remove(i);

            }


        }
    }


    public  void  updateItemOrder(ItemOrder itemOrder){

        if(itemOrder!=null) {

            for (int i = 0; i < this.getItems().size(); i++) {
                if (this.getItems().get(i).getItem().getId().equals(itemOrder.getItem().getId()))

                    this.getItems().get(i).setAmount(itemOrder.getAmount());

            }


        }
    }


//    public ArrayList<ItemOrder> getItemOrders() {
//
//        for (int i = 0; i < this.getItems().size(); i++) {
//            if (this.getItems().get(i).amount == 0)
//
//                this.getItems().remove(i);
//
//        }
//        return getItems();
//
//    }





    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}

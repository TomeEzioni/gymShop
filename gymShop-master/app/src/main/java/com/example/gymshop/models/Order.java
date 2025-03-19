package com.example.gymshop.models;

import com.example.gymshop.models.Item;

import java.util.List;

public class Order
{
    String id;
    User User;
    List<com.example.gymshop.models.Item> items;

    public Order(String id, com.example.gymshop.models.User user, List<Item> items) {
        this.id = id;
        User = user;
        this.items = items;
    }

    public Order()
    {
    }

    public com.example.gymshop.models.User getUser() {
        return User;
    }

    public void setUser(com.example.gymshop.models.User user) {
        User = user;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }




    public List<com.example.gymshop.models.Item> getItems()
    {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", User=" + User +
                ", items=" + items +
                '}';
    }
}

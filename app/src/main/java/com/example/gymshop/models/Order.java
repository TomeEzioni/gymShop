package com.example.gymshop.models;

import com.example.gymshop.models.Item;

import java.util.List;

public class Order
{
    String id;
    String idf;
    List<com.example.gymshop.models.Item> items;

    public Order(String id, String idf, List<com.example.gymshop.models.Item> items)
    {
        this.id = id;
        this.id = idf;
        this.items = items;
    }

    public Order()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getIdf()
    {
        return id;
    }

    public void setIdf(String idf)
    {
        this.idf = idf;
    }

    public List<com.example.gymshop.models.Item> getItems()
    {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString()
    {
        return "Order{" +
                "id='" + id + '\'' +
                ", id='" + idf + '\'' +
                ", items=" + items +
                '}';
    }
}

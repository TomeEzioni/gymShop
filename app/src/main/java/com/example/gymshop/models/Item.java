package com.example.gymshop.models;

public class Item
{
    String id;
    String name;
    String type, color, logo;
    double price;
    String pic;

    public Item(String id, String name, String type, String color, String logo, double price, String pic) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.color = color;
        this.logo = logo;
        this.price = price;
        this.pic = pic;
    }

    public Item()
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public String getPic()
    {
        return pic;
    }

    public void setPic(String pic)
    {
        this.pic = pic;
    }

    @Override
    public String toString()
    {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", logo='" + logo + '\'' +
                ", price=" + price +
                ", pic='" + pic + '\'' +
                '}';
    }
}

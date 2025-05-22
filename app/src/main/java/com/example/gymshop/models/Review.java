package com.example.gymshop.models;

import java.io.Serializable;

public class Review implements Serializable {
    private String itemId;
    private String text;
    private float rating;
    private String username;

    // נדרש ע"י Firestore – בנאי ריק


    public Review() {
    }

    public Review(String itemId, String text, float rating, String username) {
        this.itemId = itemId;
        this.text = text;
        this.rating = rating;
        this.username = username;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

package com.michaelmasai.cinemaconnnect;

public class SnackModel {
    private String emoji;
    private String name;
    private int price;
    private boolean selected; // FIX 1: Add this field

    // --- Constructor ---
    public SnackModel(String emoji, String name, int price) {
        this.emoji = emoji;
        this.name = name;
        this.price = price;
        this.selected = false; // Initialize as not selected
    }

    // --- Getters ---
    public String getEmoji() { return emoji; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public boolean isSelected() { return selected; }

    // --- Setters ---
    // FIX 2: Add the missing setter method
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
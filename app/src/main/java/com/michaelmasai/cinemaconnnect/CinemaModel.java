package com.michaelmasai.cinemaconnnect;

public class CinemaModel {
    private final String name;
    private final String location;
    private final double rating;
    private int imageResId;

    public CinemaModel(String name, String location, double rating, int imageResId) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public double getRating() { return rating; }
    public int getImageResId() { return imageResId; }
}
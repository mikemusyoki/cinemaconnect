package com.michaelmasai.cinemaconnnect;

public class MovieModel {
    private final String title;
    private final String genre;
    private final String duration;
    private final int price;
    private final double rating;
    private String youTubeId;
    private String description;
    private int imageResId;




    public MovieModel(String title, String genre, String duration, int price, double rating, String youTubeId, String description, int imageResId) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.price = price;
        this.rating = rating;
        this.youTubeId = youTubeId;
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getDuration() { return duration; }
    public int getPrice() { return price; }
    public int getImageResId() { return imageResId; }
    public double getRating() { return rating; }
    public String getYouTubeId() { return youTubeId; }
    public String getDescription() { return description; }
}

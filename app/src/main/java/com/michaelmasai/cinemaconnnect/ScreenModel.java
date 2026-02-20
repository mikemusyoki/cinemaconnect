package com.michaelmasai.cinemaconnnect;

public class ScreenModel {
    private String screenName;
    private String screenType;
    private int capacity;

    public ScreenModel(String screenName, String screenType, int capacity) {
        this.screenName = screenName;
        this.screenType = screenType;
        this.capacity = capacity;
    }

    public String getScreenName() { return screenName; }
    public String getScreenType() { return screenType; }
    public int getCapacity() { return capacity; }
}

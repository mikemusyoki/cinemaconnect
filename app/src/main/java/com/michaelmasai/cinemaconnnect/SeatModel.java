package com.michaelmasai.cinemaconnnect;

public class SeatModel {
    private String label;
    private boolean selected;

    public SeatModel(String label) {
        this.label = label;
        this.selected = false;
    }

    public String getLabel() {
        return label;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

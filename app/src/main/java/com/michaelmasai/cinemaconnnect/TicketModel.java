package com.michaelmasai.cinemaconnnect;

import java.util.List;

public class TicketModel {
    public String movie, cinema, screen, ticketId, paymentMethod;
    public List<String> seats;
    public List<String> snacks;
    public int totalPrice;
    public long timestamp;

    public TicketModel() {} // required for Firestore

    public TicketModel(String movie, String cinema, String screen, List<String> seats,
                       List<String> snacks, int totalPrice, String ticketId,
                       String paymentMethod, long timestamp) {
        this.movie = movie;
        this.cinema = cinema;
        this.screen = screen;
        this.seats = seats;
        this.snacks = snacks;
        this.totalPrice = totalPrice;
        this.ticketId = ticketId;
        this.paymentMethod = paymentMethod;
        this.timestamp = timestamp;
    }
}

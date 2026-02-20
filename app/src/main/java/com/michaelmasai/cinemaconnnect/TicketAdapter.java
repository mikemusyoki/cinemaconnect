package com.michaelmasai.cinemaconnnect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    Context context;
    List<TicketModel> list;

    public TicketAdapter(Context context, List<TicketModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ticket_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        TicketModel t = list.get(position);

        h.ticketMovie.setText(t.movie);
        h.ticketCinema.setText("Cinema: " + t.cinema);
        h.ticketScreen.setText("Screen: " + t.screen);
        h.ticketSeats.setText("Seats: " + String.join(", ", t.seats));
        h.ticketPrice.setText("KSh " + t.totalPrice);

        h.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, TicketActivity.class);
            i.putExtra("cinemaName", t.cinema);
            i.putExtra("movieName", t.movie);
            i.putExtra("screenName", t.screen);
            i.putExtra("seats", String.join(", ", t.seats));
            i.putExtra("snacks", String.join(", ", t.snacks));
            i.putExtra("totalPrice", t.totalPrice);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ticketMovie, ticketCinema, ticketScreen, ticketSeats, ticketPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ticketMovie = itemView.findViewById(R.id.ticketMovie);
            ticketCinema = itemView.findViewById(R.id.ticketCinema);
            ticketScreen = itemView.findViewById(R.id.ticketScreen);
            ticketSeats = itemView.findViewById(R.id.ticketSeats);
            ticketPrice = itemView.findViewById(R.id.ticketPrice);
        }
    }
}

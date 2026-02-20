package com.michaelmasai.cinemaconnnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {

    public interface OnSeatToggle {
        void onSeatToggled(SeatModel seat);
    }

    private final List<SeatModel> seats;
    private final Context context;
    private final OnSeatToggle listener;

    public SeatAdapter(Context context, List<SeatModel> seats, OnSeatToggle listener) {
        this.context = context;
        this.seats = seats;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatAdapter.ViewHolder holder, int position) {
        SeatModel seat = seats.get(position);
        holder.label.setText(seat.getLabel());

        // Apply background depending on selection
        if (seat.isSelected()) {
            holder.label.setBackgroundResource(R.drawable.seat_selected);
        } else {
            holder.label.setBackgroundResource(R.drawable.seat_default);
        }

        holder.label.setOnClickListener(v -> {
            // toggle
            seat.setSelected(!seat.isSelected());
            notifyItemChanged(position);
            listener.onSeatToggled(seat);
        });
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.seatLabel);
        }
    }
}

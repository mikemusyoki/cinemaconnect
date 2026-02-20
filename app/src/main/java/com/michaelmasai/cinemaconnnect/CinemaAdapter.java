package com.michaelmasai.cinemaconnnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RatingBar;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.ViewHolder> {

    private final List<CinemaModel> cinemaList;
    private final OnCinemaClickListener listener;

    public interface OnCinemaClickListener {
        void onCinemaClick(CinemaModel cinema);
    }

    public CinemaAdapter(List<CinemaModel> cinemaList, OnCinemaClickListener listener) {
        this.cinemaList = cinemaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cinema, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CinemaModel cinema = cinemaList.get(position);
        holder.iconView.setImageResource(cinema.getImageResId());
        holder.nameView.setText(cinema.getName());
        holder.locationView.setText(cinema.getLocation());
        holder.ratingBar.setRating((float) cinema.getRating());

        holder.itemView.setOnClickListener(v -> listener.onCinemaClick(cinema));
    }

    @Override
    public int getItemCount() {
        return cinemaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, locationView;
        RatingBar ratingBar;
        ImageView iconView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.iconView);
            nameView = itemView.findViewById(R.id.nameView);
            locationView = itemView.findViewById(R.id.locationView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
package com.michaelmasai.cinemaconnnect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RatingBar;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final List<MovieModel> movieList;
    private final Context context;

    public MovieAdapter(Context context, List<MovieModel> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieModel movie = movieList.get(position);
        holder.posterView.setImageResource(movie.getImageResId());
        holder.titleView.setText(movie.getTitle());
        holder.genreView.setText(movie.getGenre() + " • " + movie.getDuration());
        holder.priceView.setText("KSh " + movie.getPrice());
        holder.ratingBar.setRating((float) movie.getRating());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("cinemaName", ((MovieActivity) context).getIntent().getStringExtra("cinemaName"));
            intent.putExtra("movieTitle", movie.getTitle());
            intent.putExtra("movieGenre", movie.getGenre());
            intent.putExtra("movieDuration", movie.getDuration());
            intent.putExtra("moviePrice", movie.getPrice());
            intent.putExtra("moviePoster", movie.getImageResId());
            intent.putExtra("movieRating", movie.getRating());
            intent.putExtra("youTubeId", movie.getYouTubeId()); // new
            intent.putExtra("description", movie.getDescription()); // new
            context.startActivity(intent);
        });



    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, genreView, priceView;
        RatingBar ratingBar;

        ImageView posterView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterView = itemView.findViewById(R.id.moviePoster);
            titleView = itemView.findViewById(R.id.movieTitle);
            genreView = itemView.findViewById(R.id.movieGenre);
            priceView = itemView.findViewById(R.id.moviePrice);
            ratingBar = itemView.findViewById(R.id.movieRating);
        }
    }
}

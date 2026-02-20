package com.michaelmasai.cinemaconnnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.Button;
import android.view.View;


public class MovieDetailsActivity extends AppCompatActivity {

    TextView movieIcon, movieTitle, movieGenre, movieDuration, moviePrice, tvDescription;
    RatingBar movieRating;
    Button btnSelectSeats;
    WebView webViewTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieIcon = findViewById(R.id.movieIconDetail);
        movieTitle = findViewById(R.id.movieTitleDetail);
        movieGenre = findViewById(R.id.movieGenreDetail);
        movieDuration = findViewById(R.id.movieDurationDetail);
        moviePrice = findViewById(R.id.moviePriceDetail);
        movieRating = findViewById(R.id.movieRatingDetail);
        btnSelectSeats = findViewById(R.id.btnSelectSeats);
        tvDescription = findViewById(R.id.tvDescription);
        webViewTrailer = findViewById(R.id.webViewTrailer);

        // Get data from intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("movieTitle");
        String genre = intent.getStringExtra("movieGenre");
        String duration = intent.getStringExtra("movieDuration");
        int price = intent.getIntExtra("moviePrice", 0);
        String icon = intent.getStringExtra("movieIcon");
        double rating = intent.getDoubleExtra("movieRating", 0);
        String youTubeId = intent.getStringExtra("youTubeId");
        String description = intent.getStringExtra("description");
        String cinemaName = intent.getStringExtra("cinemaName");



        movieIcon.setText(icon);
        movieTitle.setText(title);
        movieGenre.setText(genre);
        movieDuration.setText(duration);
        moviePrice.setText("KSh " + price);
        movieRating.setRating((float) rating);
        tvDescription.setText(description);

        // Set up WebView for YouTube embed
        WebSettings webSettings = webViewTrailer.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String html = "<html><body style=\"margin:0;padding:0;\">" +
                "<iframe width=\"100%\" height=\"100%\" " +
                "src=\"https://www.youtube.com/embed/" + youTubeId + "\" " +
                "frameborder=\"0\" allowfullscreen>" +
                "</iframe></body></html>";
        webViewTrailer.loadData(html, "text/html", "utf-8");


        btnSelectSeats.setOnClickListener(v -> {
            Intent i = new Intent(MovieDetailsActivity.this, ScreenSelectionActivity.class);
            i.putExtra("cinemaName", getIntent().getStringExtra("cinemaName"));
            i.putExtra("movieName", title);
            startActivity(i);
        });

    }
}
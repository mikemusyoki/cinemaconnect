package com.michaelmasai.cinemaconnnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    RecyclerView movieRecycler;
    MovieAdapter movieAdapter;
    List<MovieModel> movieList;
    TextView cinemaTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        movieRecycler = findViewById(R.id.movieRecycler);
        cinemaTitle = findViewById(R.id.cinemaTitle);
        movieRecycler.setLayoutManager(new LinearLayoutManager(this));

        String cinemaName = getIntent().getStringExtra("cinemaName");
        cinemaTitle.setText(cinemaName);

        movieList = new ArrayList<>();

        movieList.add(new MovieModel(
                "Dune: Part Two",
                "Sci-Fi",
                "166 min",
                800,
                5,
                "Way9Dexny3w",
                "Paul Atreides unites with Chani and the Fremen while seeking revenge against the conspirators who destroyed his family. Facing a choice between the love of his life and the fate of the universe, he must prevent a terrible future only he can foresee.",  // Example YouTube video id for Dune 2 (you can look it up)
                R.drawable.dune
        ));

        movieList.add(new MovieModel(
                "Oppenheimer",
                "Biography",
                "180 min",
                750,
                4.1,
                "bK6ldnjE3Y0",
                "During World War II, Lt. Gen. Leslie Groves Jr. appoints physicist J. Robert Oppenheimer to work on the top-secret Manhattan Project. Oppenheimer and a team of scientists spend years developing and designing the atomic bomb. Their work comes to fruition on July 16, 1945, as they witness the world's first nuclear explosion, forever changing the course of history.",
                 R.drawable.oppenheimer
        ));

        movieList.add(new MovieModel(
                "Barbie",
                "Comedy",
                "114 min",
                650,
                3.8,
                "pBk4NYhWNMM",
                "Barbie is a fashion doll created by American businesswoman Ruth Handler, manufactured by American toy and entertainment company Mattel and introduced on March 9, 1959. The toy was based on the German Bild Lilli doll which Handler had purchased while in Europe.",
                R.drawable.barbie
        ));

        movieList.add(new MovieModel(
                "The Batman",
                "Action",
                "176 min",
                700,
                4.8,
                "neY2xVmOfUM",
                "Batman is called to intervene when the mayor of Gotham City is murdered. Soon, his investigation leads him to uncover a web of corruption, linked to his own dark past.",
                R.drawable.batman
        ));

        movieList.add(new MovieModel(
                "Avatar: Fire and Ash",
                "Adventure",
                "195 min",
                850,
                5,
                "Wn2CKu6E8TA",
                "Jake and Neytiri's family grapples with grief after Neteyam's death, encountering a new, aggressive Na'vi tribe, the Ash People, who are led by the fiery Varang, as the conflict on Pandora escalates and a new moral focus emerges.",
                R.drawable.avatar
        ));

        movieList.add(new MovieModel(
                "Spider-Man: Across the Spider-Verse",
                "Animation",
                "140 min",
                850,
                4.8,
                "cqGjhVJWtEg",
                "In an attempt to curb the Spot, a scientist, from harnessing the power of the multiverse, Miles Morales joins forces with Gwen Stacy.",
                R.drawable.spiderman
        ));

        movieList.add(new MovieModel(
                "Guardians of the Galaxy Vol. 3",
                "Adventure",
                "150 min",
                800,
                5,
                "OeDWjroEPyg",
                "Still reeling from the loss of Gamora, Peter Quill must rally his team to defend the universe and protect one of their own. If the mission is not completely successful, it could possibly lead to the end of the Guardians as we know them.",
                R.drawable.guardiansofthegalaxy
        ));

        movieAdapter = new MovieAdapter(this, movieList);
        movieRecycler.setAdapter(movieAdapter);


    }
}

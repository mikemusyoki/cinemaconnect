package com.michaelmasai.cinemaconnnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ScreenSelectionActivity extends AppCompatActivity {

    RecyclerView screenRecycler;
    ScreenSelectionAdapter screenAdapter;
    List<ScreenModel> screenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_selection);

        screenRecycler = findViewById(R.id.screenRecycler);
        screenRecycler.setLayoutManager(new LinearLayoutManager(this));

        String cinemaName = getIntent().getStringExtra("cinemaName");
        String movieName = getIntent().getStringExtra("movieName");

        screenList = new ArrayList<>();

        // 🔥 Hardcoded screens for now
        screenList.add(new ScreenModel("Screen 1", "IMAX 3D", 150));
        screenList.add(new ScreenModel("Screen 2", "Standard 2D", 120));
        screenList.add(new ScreenModel("Screen 3", "VIP Lounge", 80));

        screenAdapter = new ScreenSelectionAdapter(screenList, screen -> {
            Intent i = new Intent(ScreenSelectionActivity.this, SeatSelectionActivity.class);
            i.putExtra("cinemaName", cinemaName);
            i.putExtra("movieName", movieName);
            i.putExtra("screenName", screen.getScreenName());
            startActivity(i);
        });

        screenRecycler.setAdapter(screenAdapter);
    }
}

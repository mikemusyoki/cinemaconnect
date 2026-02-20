package com.michaelmasai.cinemaconnnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionActivity extends AppCompatActivity {

    RecyclerView seatRecycler;
    SeatAdapter seatAdapter;
    List<SeatModel> seatList;
    TextView selectedSeatsView, totalPriceView;
    Button btnConfirmSeats;

    int seatPrice = 800;
    int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        seatRecycler = findViewById(R.id.seatRecycler);
        selectedSeatsView = findViewById(R.id.selectedSeatsView);
        totalPriceView = findViewById(R.id.totalPriceView);
        btnConfirmSeats = findViewById(R.id.btnConfirmSeats);


        String cinemaName = getIntent().getStringExtra("cinemaName");
        String movieName = getIntent().getStringExtra("movieName");

        Spinner screenSpinner = findViewById(R.id.screenSpinner);

// List of screens
        List<String> screens = new ArrayList<>();
        screens.add("Screen 1");
        screens.add("Screen 2");
        screens.add("Screen 3");

// Adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, screens);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        screenSpinner.setAdapter(adapter);

// Default selected screen
        String[] selectedScreen = {screens.get(0)}; // Array workaround for final variable

        screenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedScreen[0] = screens.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        // Build seats: rows A..J (10 rows) and cols 1..12 (12 cols) -> 120 seats
        seatList = new ArrayList<>();
        char[] rows = {'A','B','C','D','E','F','G','H','I','J'}; // 10 rows
        int cols = 12; // 12 columns

        for (char r : rows) {
            for (int c = 1; c <= cols; c++) {
                String label = r + String.valueOf(c);
                seatList.add(new SeatModel(label));
            }
        }

        // Use GridLayoutManager with 12 columns. RecyclerView is wrap_content width inside HorizontalScrollView,
        // so it'll be wide and scroll horizontally; RecyclerView itself scrolls vertically.
        GridLayoutManager glm = new GridLayoutManager(this, cols);
        seatRecycler.setLayoutManager(glm);
        seatAdapter = new SeatAdapter(this, seatList, seat -> {
            // update total and selected list whenever a seat toggles
            updateSelectionInfo();
        });

        seatRecycler.setAdapter(seatAdapter);

        btnConfirmSeats.setOnClickListener(v -> {
            List<String> selected = new ArrayList<>();
            for (SeatModel s : seatList) {
                if (s.isSelected()) selected.add(s.getLabel());
            }

            if (selected.isEmpty()) {
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show();
                return;
            }

            int seatTotal = selected.size() * seatPrice;

            // Pass data to snack selection (keep existing keys your other screens expect)
            Intent intent = new Intent(SeatSelectionActivity.this, SnackSelectionActivity.class);
            intent.putExtra("selectedScreen", selectedScreen[0]);
            intent.putExtra("selectedSeats", selected.toString());
            intent.putExtra("totalSeatPrice", seatTotal);
            intent.putExtra("cinemaName", cinemaName);
            intent.putExtra("movieName", movieName);
            intent.putExtra("screenName", selectedScreen[0]);
            startActivity(intent);
        });

        // initialize info display
        updateSelectionInfo();
    }

    private void updateSelectionInfo() {
        int count = 0;
        List<String> selectedLabels = new ArrayList<>();
        for (SeatModel s : seatList) {
            if (s.isSelected()) {
                count++;
                selectedLabels.add(s.getLabel());
            }
        }
        totalPrice = count * seatPrice;
        selectedSeatsView.setText("Selected: " + (selectedLabels.isEmpty() ? "None" : selectedLabels.toString()));
        totalPriceView.setText("Total: KSh " + totalPrice);
    }
}
